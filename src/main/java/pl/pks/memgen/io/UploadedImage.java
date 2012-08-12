package pl.pks.memgen.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UploadedImage {

    private final String contentType;
    private final long contentLength;
    private final InputStream dataInputStream;

    public UploadedImage(String contentType, long contentLength, InputStream dataInputStream) {
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.dataInputStream = dataInputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public long getContentLength() {
        return contentLength;
    }

    public InputStream getDataInputStream() {
        return dataInputStream;
    }

    public UploadedImage changeData(ByteArrayOutputStream newData) {
        ByteArrayInputStream input = new ByteArrayInputStream(newData.toByteArray());
        return new UploadedImage(contentType, contentLength, input);
    }

    public UploadedImage changeContentLength(long newContentLength) {
        return new UploadedImage(contentType, newContentLength, dataInputStream);
    }

}
