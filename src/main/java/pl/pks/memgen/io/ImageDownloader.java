package pl.pks.memgen.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader {

    public InputStream getData(String imageUrl) throws IOException {
        HttpURLConnection uc = setupConnection(imageUrl, "GET");
        return uc.getInputStream();
    }

    public String getContentType(String imageUrl) throws IOException {
        HttpURLConnection uc = setupConnection(imageUrl, "HEAD");
        return uc.getContentType();
    }

    public long getContentLength(String imageUrl) throws IOException {
        HttpURLConnection uc = setupConnection(imageUrl, "HEAD");
        return uc.getContentLength();
    }

    private HttpURLConnection setupConnection(String imageUrl, String method) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setRequestMethod(method);
        return uc;
    }
}
