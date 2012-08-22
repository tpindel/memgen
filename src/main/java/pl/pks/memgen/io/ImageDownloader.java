package pl.pks.memgen.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.yammer.dropwizard.logging.Log;

public class ImageDownloader {

    private static final Log LOG = Log.forClass(ImageDownloader.class);

    public InputStream getData(String imageUrl) {
        HttpURLConnection uc = setupConnection(imageUrl, "GET");
        try {
            return uc.getInputStream();
        } catch (IOException e) {
            LOG.error(e, "Could not get an input stream");
            throw new ImageUploadException();
        }
    }

    public String getContentType(String imageUrl) {
        HttpURLConnection uc = setupConnection(imageUrl, "HEAD");
        return uc.getContentType();
    }

    public long getContentLength(String imageUrl) {
        HttpURLConnection uc = setupConnection(imageUrl, "HEAD");
        return uc.getContentLength();
    }

    private HttpURLConnection setupConnection(String imageUrl, String method) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setRequestMethod(method);
            return uc;
        } catch (IOException e) {
            LOG.error(e, "Could not setup the connection for {}", imageUrl);
            throw new ImageUploadException();
        }
    }
}
