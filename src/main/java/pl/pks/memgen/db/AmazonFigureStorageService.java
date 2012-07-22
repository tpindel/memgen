package pl.pks.memgen.db;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkNotNull;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pl.pks.memgen.StorageConfiguration;
import pl.pks.memgen.api.Figure;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.yammer.dropwizard.logging.Log;

public class AmazonFigureStorageService implements FigureStorageService {

    private static final Log LOG = Log.forClass(AmazonFigureStorageService.class);

    private final AmazonS3 amazon;

    private final StorageConfiguration storageConfiguration;

    public AmazonFigureStorageService(AmazonS3 amazon, StorageConfiguration storageConfiguration) {
        this.amazon = amazon;
        this.storageConfiguration = storageConfiguration;
    }

    @Override
    public List<Figure> findAll() {
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
        return amazon.listObjects(new ListObjectsRequest()
            .withBucketName(storageConfiguration.getBucket())).getObjectSummaries();
    }

    private String getAmazonUrl(String key) {
        return on('/').join(getPublicUrl(storageConfiguration), key);
    }

    private String getPublicUrl(StorageConfiguration storage) {
        return on('/').join(storage.getEndpoint(), storage.getBucket());
    }

    @Override
    public Figure save(ObjectMetadata objectMetadata, InputStream inputStream) {
        String bucket = storageConfiguration.getBucket();
        String key = getExtension(objectMetadata.getContentType());
        PutObjectRequest request = new PutObjectRequest(bucket, key, inputStream, objectMetadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);

        amazon.putObject(request);
        LOG.info("aved as {}", getAmazonUrl(key));

        return new Figure(key, getAmazonUrl(key));
    }

    private String getExtension(String contentType) {
        switch (contentType) {
            case "image/jpeg":
                return UUID.randomUUID().toString() + ".jpg";
            case "image/png":
                return UUID.randomUUID().toString() + ".png";
        }
        throw new IllegalStateException("Content type not supported.");
    }

    public Figure findOne(String id) {
        checkNotNull(id, "figure's id can't be null");

        List<S3ObjectSummary> objectSummaries = getObjectSummaries();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            String key = objectSummary.getKey();
            if (id.equals(key)) {
                return new Figure(id, getAmazonUrl(key));
            }
        }
        return null;
    }

    @Override
    public String findContentType(String id) {
        return amazon.getObject(storageConfiguration.getBucket(), id).getObjectMetadata().getContentType();
    }
}
