package pl.pks.memgen;

import pl.pks.memgen.db.AmazonStorageService;
import pl.pks.memgen.db.IdGenerator;
import pl.pks.memgen.db.NameResolver;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.db.StorageStrategy;
import pl.pks.memgen.db.UnderscoreStorageStrategy;
import pl.pks.memgen.db.UrlResolver;
import pl.pks.memgen.generator.Generator;
import pl.pks.memgen.generator.transformer.FigureTransformer;
import pl.pks.memgen.generator.transformer.im4j.Im4jTransformer;
import pl.pks.memgen.health.PlaceholderHealthCheck;
import pl.pks.memgen.io.FigureUploader;
import pl.pks.memgen.io.ImageDownloader;
import pl.pks.memgen.io.processor.ImageProcessorFactory;
import pl.pks.memgen.resources.FigureResource;
import pl.pks.memgen.resources.GeneratorExceptionMapper;
import pl.pks.memgen.resources.ImageUploadExceptionMapper;
import pl.pks.memgen.resources.MemeResource;
import pl.pks.memgen.resources.RootResource;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class MemGenService extends Service<MemGenConfiguration> {

    private MemGenService() {
        super("MemGen");
        addBundle(new ViewBundle());
        addBundle(new AssetsBundle(AssetsBundle.DEFAULT_PATH));
    }

    public static void main(String[] args) throws Exception {
        new MemGenService().run(args);
    }

    @Override
    protected void initialize(MemGenConfiguration conf, Environment env) throws Exception {
        StorageConfiguration storageConfiguration = conf.getStorage();
        UploadConfiguration uploadConfiguration = conf.getUpload();

        AmazonS3Client amazonS3Client = initializeAmazonS3Client(storageConfiguration);
        ImageDownloader figureDownloader = new ImageDownloader();
        ImageProcessorFactory imageProcessorFactory = new ImageProcessorFactory(uploadConfiguration);

        IdGenerator idGenerator = new IdGenerator();
        NameResolver nameResolver = new NameResolver(idGenerator);
        UrlResolver urlResolver = new UrlResolver();
        StorageStrategy storageStrategy = new UnderscoreStorageStrategy(nameResolver, urlResolver);
        StorageService storageService = new AmazonStorageService(amazonS3Client, storageStrategy, storageConfiguration);

        FigureUploader figureUploader = new FigureUploader(storageService, imageProcessorFactory.create(),
            new ImageDownloader());
        FigureTransformer figureTransformer = new Im4jTransformer();
        Generator memGenerator = new Generator(figureDownloader, storageService, figureTransformer);
        env.addResource(new MemeResource(storageService, memGenerator));
        env.addResource(new FigureResource(storageService, figureUploader));
        env.addResource(new RootResource());
        env.addProvider(ImageUploadExceptionMapper.class);
        env.addProvider(GeneratorExceptionMapper.class);
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
