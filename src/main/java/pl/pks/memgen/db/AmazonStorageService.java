package pl.pks.memgen.db;

import static com.google.common.base.Joiner.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pl.pks.memgen.StorageConfiguration;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.yammer.dropwizard.logging.Log;

public class AmazonStorageService implements StorageService {

    private static final Log LOG = Log.forClass(AmazonStorageService.class);

    private final AmazonS3 amazon;

    private final StorageConfiguration storageConfiguration;

    public AmazonStorageService(AmazonS3 amazon, StorageConfiguration storageConfiguration) {
        this.amazon = amazon;
        this.storageConfiguration = storageConfiguration;
    }

    @Override
    public List<Meme> findAll() {
        ObjectListing objectListing = amazon.listObjects(new ListObjectsRequest()
            .withBucketName(storageConfiguration.getBucket()));

        List<Meme> memes = new ArrayList<>();

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            final String url = getAmazonUrl(objectSummary.getKey());
            memes.add(new Meme(url));
            LOG.info("Generated URL: {}", url);
        }
        return memes;
    }

    private String getAmazonUrl(String key) {
        return on('/').join(getPublicUrl(storageConfiguration), key);
    }

    private String getPublicUrl(StorageConfiguration storage) {
        return on('/').join(storage.getEndpoint(), storage.getBucket());
    }

    @Override
    public Meme save(String url) {
        try {
            URLConnection urlConnection = new URL(url).openConnection();

            String bucket = storageConfiguration.getBucket();
            String key = generateRandomKeyWithExtension(url);
            InputStream inputStream = urlConnection.getInputStream();
            ObjectMetadata objectMetadata = getObjectMetada(urlConnection.getContentLengthLong());

            PutObjectRequest request = new PutObjectRequest(bucket, key, inputStream, objectMetadata);
            request.setCannedAcl(CannedAccessControlList.PublicRead);

            amazon.putObject(request);
            LOG.info("{} saved", url);

            return new Meme(getAmazonUrl(key));

        } catch (IOException e) {
            LOG.error(e, "Could not download file while downloading {}", url);
            throw new RuntimeException();
        }
    }

    private ObjectMetadata getObjectMetada(long contentLength) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        return objectMetadata;
    }

    private String generateRandomKeyWithExtension(String url) {
        return UUID.randomUUID().toString() + url.substring(url.length() - 4);
    }

}
