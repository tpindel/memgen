package pl.pks.memgen.db;

import static com.google.common.base.Joiner.*;
import static com.google.common.base.Preconditions.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import pl.pks.memgen.StorageConfiguration;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
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
        List<S3ObjectSummary> objectSummaries = getObjectSummaries();

        List<Meme> memes = new ArrayList<>();

        for (S3ObjectSummary objectSummary : objectSummaries) {
            String key = objectSummary.getKey();
            final String url = getAmazonUrl(key);
            memes.add(new Meme(key, url));
            LOG.info("Generated URL: {}", url);
        }
        return memes;
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
    public Meme save(String url) {
        try {
            HttpURLConnection urlConnection = doHEADRequest(url);
            checkContentType(urlConnection);
            checkContentSize(urlConnection);
            ObjectMetadata objectMetadata = getObjectMetadata(urlConnection.getContentLengthLong());
            
            InputStream inputStream = doGETRequest(url).getInputStream();
            
            String bucket = storageConfiguration.getBucket();
            String key = generateRandomKeyWithExtension(url);
            
            PutObjectRequest request = new PutObjectRequest(bucket, key, inputStream, objectMetadata);
            request.setCannedAcl(CannedAccessControlList.PublicRead);

            amazon.putObject(request);
            LOG.info("{} saved as {}", url, getAmazonUrl(key));

            return new Meme(key, getAmazonUrl(key));

        } catch (IOException e) {
            LOG.error(e, "Could not download file while downloading {}", url);
            throw new RuntimeException(e);
        }
    }

    private void checkContentSize(HttpURLConnection urlConnection) throws IOException {
        long contentLength = urlConnection.getContentLengthLong();
        if (contentLength <= 0) {
            LOG.info("Invalid content length {}", contentLength);
            throw new IllegalArgumentException();
        }
    }

    private void checkContentType(HttpURLConnection urlConnection) throws IOException {
        String contentType = urlConnection.getContentType();
        boolean valid = Arrays.asList("image/jpeg", "image/png").contains(contentType);
        if (!valid) {
            LOG.info("Invalid content type {}", contentType);
            throw new IllegalArgumentException();
        }
    }

    private HttpURLConnection doHEADRequest(String url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("HEAD");
        urlConnection.connect();
        return urlConnection;
    }

    private HttpURLConnection doGETRequest(String url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.connect();
        return urlConnection;
    }

    private ObjectMetadata getObjectMetadata(long contentLength) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        return objectMetadata;
    }

    private String generateRandomKeyWithExtension(String filename) {
        return UUID.randomUUID().toString() + filename.substring(filename.length() - 4);
    }

    public Meme findOne(String id) {
        checkNotNull(id, "meme id can't be null");

        List<S3ObjectSummary> objectSummaries = getObjectSummaries();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            String key = objectSummary.getKey();
            if (id.equals(key)) {
                return new Meme(id, getAmazonUrl(key));
            }
        }
        return null;
    }

}
