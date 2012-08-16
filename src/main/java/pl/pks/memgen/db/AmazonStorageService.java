package pl.pks.memgen.db;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.ArrayList;
import java.util.List;
import pl.pks.memgen.StorageConfiguration;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.io.UploadedImage;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.yammer.dropwizard.logging.Log;

public class AmazonStorageService implements StorageService {

    private static final Log LOG = Log.forClass(AmazonStorageService.class);

    private final AmazonS3 amazon;

    private final StorageConfiguration storageConfiguration;

    private final StorageStrategy storageStrategy;

    public AmazonStorageService(AmazonS3 amazon, StorageStrategy storageStrategy,
                                StorageConfiguration storageConfiguration) {
        this.amazon = amazon;
        this.storageStrategy = storageStrategy;
        this.storageConfiguration = storageConfiguration;
    }

    @Override
    public List<Figure> findAllFigures() {
        List<S3ObjectSummary> objectSummaries = getObjectSummaries();

        List<Figure> figures = new ArrayList<>();

        for (S3ObjectSummary objectSummary : objectSummaries) {
            String key = objectSummary.getKey();
            final String url = getAmazonUrl(key);
            figures.add(new Figure(key, url));
        }
        LOG.info("Found {} figures", objectSummaries.size());
        return figures;
    }

    private List<S3ObjectSummary> getObjectSummaries() {
        return amazon.listObjects(storageConfiguration.getBucket(), storageStrategy.getFiguresPath())
            .getObjectSummaries();
    }

    @Override
    public Figure saveFigure(UploadedImage uploadedImage) {
        String key = storageStrategy.resolveFigureName(uploadedImage.getContentType());
        String resolvedUrl = persist(uploadedImage, key);
        return new Figure(key, resolvedUrl);
    }

    private String persist(UploadedImage uploadedImage, String key) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadedImage.getContentLength());
        objectMetadata.setContentType(uploadedImage.getContentType());
        PutObjectRequest request = new PutObjectRequest(storageConfiguration.getBucket(), key, uploadedImage
            .getDataInputStream(), objectMetadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        amazon.putObject(request);

        String resolvedUrl = storageStrategy.resolveUrl(key, storageConfiguration);

        LOG.info("Saved {} under {}", key, resolvedUrl);
        return resolvedUrl;
    }

    public Figure findOneFigure(String id) {
        checkNotNull(id, "figure's id can't be null");

        ObjectMetadata objectMetadata = amazon.getObjectMetadata(storageConfiguration.getBucket(), id);
        if (objectMetadata != null) {
            Figure figure = new Figure(id, getAmazonUrl(id));
            LOG.info("Found {}", figure.toString());
            return figure;
        } else {
            return null;
        }
    }

    @Override
    public String findContentType(String id) {
        return amazon.getObject(storageConfiguration.getBucket(), id).getObjectMetadata().getContentType();
    }

    @Override
    public List<Meme> findAllMemes() {
        List<S3ObjectSummary> objectSummaries = amazon.listObjects(storageConfiguration.getBucket(),
            storageStrategy.getMemesPath())
            .getObjectSummaries();

        List<Meme> figures = new ArrayList<>();

        for (S3ObjectSummary objectSummary : objectSummaries) {
            String key = objectSummary.getKey();
            final String url = getAmazonUrl(key);
            figures.add(new Meme(key, url, null, null));
        }
        return figures;
    }

    @Override
    public Meme saveMeme(UploadedImage uploadedImage) {
        String key = storageStrategy.resolveMemeName(uploadedImage.getContentType());
        String resolvedUrl = persist(uploadedImage, key);
        return new Meme(key, resolvedUrl, null, null);
    }

    @Override
    public Meme findOneMeme(String id) {
        checkNotNull(id, "meme's id can't be null");

        ObjectMetadata objectMetadata = amazon.getObjectMetadata(storageConfiguration.getBucket(), id);
        if (objectMetadata != null) {
            return new Meme(id, getAmazonUrl(id), null, null);
        } else {
            return null;
        }
    }

    private String getAmazonUrl(String key) {
        return storageStrategy.resolveUrl(key, storageConfiguration);
    }

}
