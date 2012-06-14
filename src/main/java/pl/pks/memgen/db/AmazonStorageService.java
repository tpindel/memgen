package pl.pks.memgen.db;

import java.util.ArrayList;
import java.util.List;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.common.base.Joiner;
import com.yammer.dropwizard.logging.Log;

public class AmazonStorageService implements StorageService {

    private static final Log LOG = Log.forClass(AmazonStorageService.class);
    private static final String ENDPOINT_URL = "https://s3-eu-west-1.amazonaws.com";
    private static final String BUCKET = "memgen";

    private final AmazonS3 amazon;

    public AmazonStorageService(AmazonS3 amazon) {
        this.amazon = amazon;
    }

    @Override
    public List<Meme> findAll() {
        ObjectListing objectListing = amazon.listObjects(new ListObjectsRequest()
            .withBucketName(BUCKET));

        List<Meme> memes = new ArrayList<>();

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            final String url = Joiner.on('/').join(ENDPOINT_URL, BUCKET, objectSummary.getKey());
            LOG.info("generated URL: {}", url);
            memes.add(new Meme(url));
        }
        return memes;
    }
}
