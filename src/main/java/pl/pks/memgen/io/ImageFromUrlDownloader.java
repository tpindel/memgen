package pl.pks.memgen.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.FileUtils;

public class ImageFromUrlDownloader implements ImageDownloader {

    @Override
    public String download(String imageUrl, String imageId) {
        try {
            URLConnection uc = setupConnection(imageUrl);

            InputStream input = uc.getInputStream();
            File destination = File.createTempFile(imageId, null);
            FileUtils.copyInputStreamToFile(input, destination);
            return destination.getAbsolutePath();
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
