package pl.pks.memgen.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StorageService;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.yammer.dropwizard.logging.Log;

public class ImageFromUrlUploader implements ImageUploader {

    private static final Log LOG = Log.forClass(ImageFromUrlUploader.class);

    private final StorageService storageService;

    public ImageFromUrlUploader(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Meme upload(String url) {
        try {
            HttpURLConnection urlConnection = doHEADRequest(url);
            checkContentType(urlConnection);
            checkContentSize(urlConnection);
            ObjectMetadata objectMetadata = getObjectMetadata(urlConnection.getContentLengthLong());

            InputStream inputStream = doGETRequest(url).getInputStream();

            return storageService.save(url, objectMetadata, inputStream);

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
}
