package pl.pks.memgen;

import pl.pks.memgen.db.AmazonStorageService;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.health.PlaceholderHealthCheck;
import pl.pks.memgen.io.FigureDownloader;
import pl.pks.memgen.io.FigureUploader;
import pl.pks.memgen.memgenerator.FigureTransformer;
import pl.pks.memgen.memgenerator.MemGenerator;
import pl.pks.memgen.memgenerator.im4j.Im4jTransformer;
import pl.pks.memgen.resources.FigureResource;
import pl.pks.memgen.resources.MemeResource;
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
        UploadConfiguration uploadConfiguration = conf.getUpload();

        AmazonS3Client amazonS3Client = initializeAmazonS3Client(storageConfiguration);
        FigureDownloader figureDownloader = new FigureDownloader();
        StorageService storageService = new AmazonStorageService(amazonS3Client, storageConfiguration);
        FigureUploader figureUploader = new FigureUploader(storageService, uploadConfiguration);
        FigureTransformer figureTransformer = new Im4jTransformer();
        MemGenerator memGenerator = new MemGenerator(figureDownloader, storageService, figureTransformer);
        env.addResource(new MemeResource(storageService, memGenerator));
        env.addResource(new FigureResource(storageService, figureUploader));
        env.addResource(new RootResource());
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
