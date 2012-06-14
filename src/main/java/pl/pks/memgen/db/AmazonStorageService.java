package pl.pks.memgen.db;

import static com.google.common.base.Joiner.*;
import java.util.ArrayList;
import java.util.List;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.yammer.dropwizard.logging.Log;

public class AmazonStorageService implements StorageService {

    private static final Log LOG = Log.forClass(AmazonStorageService.class);

    private final AmazonS3 amazon;

    private final String urlPrefix;

    private final String bucket;

    public AmazonStorageService(AmazonS3 amazon, String urlPrefix, String bucket) {
        this.amazon = amazon;
        this.urlPrefix = urlPrefix;
        this.bucket = bucket;
    }

    @Override
    public List<Meme> findAll() {
        ObjectListing objectListing = amazon.listObjects(new ListObjectsRequest()
            .withBucketName(bucket));

        List<Meme> memes = new ArrayList<>();

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            final String url = on('/').join(urlPrefix, objectSummary.getKey());
            memes.add(new Meme(url));
            LOG.info("Generated URL: {}", url);
        }
        return memes;
    }
}
