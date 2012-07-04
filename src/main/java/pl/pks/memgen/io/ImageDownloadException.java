package pl.pks.memgen.io;

import java.io.IOException;

@SuppressWarnings("serial")
public class ImageDownloadException extends RuntimeException {

    public ImageDownloadException(IOException e) {
        super(e);
    }
}
