package pl.pks.memgen;

import pl.pks.memgen.db.AmazonStorageService;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.health.PlaceholderHealthCheck;
import pl.pks.memgen.resources.EditResource;
import pl.pks.memgen.resources.RootResource;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.common.cache.CacheBuilderSpec;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class MemGenService extends Service<MemGenConfiguration> {

    private MemGenService() {
        super("MemGen");
        addBundle(new ViewBundle());
        addBundle(new AssetsBundle(AssetsBundle.DEFAULT_PATH, CacheBuilderSpec.disableCaching()));
    }

    public static void main(String[] args) throws Exception {
        new MemGenService().run(args);
    }

    @Override
    protected void initialize(MemGenConfiguration conf, Environment env) throws Exception {
        StorageConfiguration storageConfiguration = conf.getStorage();
        AmazonS3Client amazonS3Client = initializeAmazonS3Client(storageConfiguration);
        StorageService storageService = new AmazonStorageService(amazonS3Client, storageConfiguration);
        env.addResource(new RootResource(storageService));
        env.addResource(new EditResource(storageService));
        env.addHealthCheck(new PlaceholderHealthCheck());
    }

    private AmazonS3Client initializeAmazonS3Client(StorageConfiguration conf) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(conf.getAccessKey(),
            conf.getSecretKey());
        AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials);
        amazonS3Client.setEndpoint(conf.getEndpoint());
        return amazonS3Client;
    }

}
