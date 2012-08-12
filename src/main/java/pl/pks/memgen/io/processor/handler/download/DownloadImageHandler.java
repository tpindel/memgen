package pl.pks.memgen.io.processor.handler.download;

import java.io.ByteArrayOutputStream;
import pl.pks.memgen.io.UploadedImage;
import pl.pks.memgen.io.processor.handler.AbstractImageHandler;

public class DownloadImageHandler extends AbstractImageHandler {

    private final DownloadStrategy downloadStrategy;

    public DownloadImageHandler(DownloadStrategy copyStrategy) {
        this.downloadStrategy = copyStrategy;
    }

    @Override
    public UploadedImage handleImage(UploadedImage uploadedImage) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        long contentLength = downloadStrategy.download(uploadedImage.getDataInputStream(), output);
        UploadedImage uploadedInMemory = uploadedImage.changeData(output).changeContentLength(contentLength);
        return nextImageHandler.handleImage(uploadedInMemory);
    }
}
