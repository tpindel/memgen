package pl.pks.memgen;

import pl.pks.memgen.db.AmazonStorageService;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.health.PlaceholderHealthCheck;
import pl.pks.memgen.resources.RootResource;
import com.amazonaws.auth.PropertiesCredentials;
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
        AmazonS3Client amazonS3Client = new AmazonS3Client(new PropertiesCredentials(
            MemGenService.class.getResourceAsStream("/storage.properties")));
        // TODO: store in properties
        amazonS3Client.setEndpoint("https://s3-eu-west-1.amazonaws.com/");
        StorageService storageService = new AmazonStorageService(amazonS3Client);
        env.addResource(new RootResource(storageService));
        env.addHealthCheck(new PlaceholderHealthCheck());
    }
}
