package pl.pks.memgen.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FigureDownloader {

    public InputStream download(String imageUrl) {
        try {
            URLConnection uc = setupConnection(imageUrl);
            InputStream input = uc.getInputStream();

            return input;
        } catch (IOException e) {
            throw new ImageDownloadException(e);
        }
    }

    private URLConnection setupConnection(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection uc = url.openConnection();
        uc.setReadTimeout(10000);
        uc.setConnectTimeout(10000);
        return uc;
    }
}
