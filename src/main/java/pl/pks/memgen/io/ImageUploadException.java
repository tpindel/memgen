package pl.pks.memgen.io;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings("serial")
public class ImageUploadException extends WebApplicationException {

    public ImageUploadException(Throwable e) {
        super(e);
    }

    public ImageUploadException() {
        super();
    }

}
