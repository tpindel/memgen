package pl.pks.memgen.db;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkNotNull;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    private static final String FIGURE = "f_";

    private static final String MEME = "m_";

    private static final Log LOG = Log.forClass(AmazonStorageService.class);

    private final AmazonS3 amazon;

    private final StorageConfiguration storageConfiguration;

    public AmazonStorageService(AmazonS3 amazon, StorageConfiguration storageConfiguration) {
        this.amazon = amazon;
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
            LOG.info("Generated URL: {}", url);
        }
        return figures;
    }

    private List<S3ObjectSummary> getObjectSummaries() {
        return amazon.listObjects(storageConfiguration.getBucket(), FIGURE).getObjectSummaries();
    }

    @Override
    public Figure saveFigure(UploadedImage uploadedImage) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadedImage.getContentLength());
        objectMetadata.setContentType(uploadedImage.getContentType());
        String key = save(objectMetadata, uploadedImage.getDataInputStream(), FIGURE);
        return new Figure(key, getAmazonUrl(key));
    }

    private String save(ObjectMetadata objectMetadata, InputStream inputStream, String prefix) {
        String bucket = storageConfiguration.getBucket();
        String key = prefix + getId(objectMetadata.getContentType());

        PutObjectRequest request = new PutObjectRequest(bucket, key, inputStream, objectMetadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);

        amazon.putObject(request);
        LOG.info("Saved as {}", getAmazonUrl(key));
        return key;
    }

    private String getId(String contentType) {
        switch (contentType) {
            case "image/jpeg":
                return UUID.randomUUID().toString() + ".jpg";
            case "image/png":
                return UUID.randomUUID().toString() + ".png";
        }
        throw new IllegalStateException("Content type not supported.");
    }

    public Figure findOneFigure(String id) {
        checkNotNull(id, "figure's id can't be null");

        ObjectMetadata objectMetadata = amazon.getObjectMetadata(storageConfiguration.getBucket(), id);
        if (objectMetadata != null) {
            return new Figure(id, getAmazonUrl(id));
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
        List<S3ObjectSummary> objectSummaries = amazon.listObjects(storageConfiguration.getBucket(), MEME)
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
    public Meme saveMeme(ObjectMetadata objectMetadata, InputStream inputStream) {
        String key = save(objectMetadata, inputStream, MEME);
        return new Meme(key, getAmazonUrl(key), null, null);
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
        return on('/').join(getPublicUrl(storageConfiguration), key);
    }

    private String getPublicUrl(StorageConfiguration storage) {
        return on('/').join(storage.getEndpoint(), storage.getBucket());
    }

}
