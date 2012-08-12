package pl.pks.memgen.io;

@SuppressWarnings("serial")
public class ImageDownloadException extends RuntimeException {

    public ImageDownloadException(Throwable e) {
        super(e);
    }

    public ImageDownloadException() {
        super();
    }

}
