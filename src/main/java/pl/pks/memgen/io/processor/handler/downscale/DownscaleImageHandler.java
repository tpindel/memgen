package pl.pks.memgen.io.processor.handler.downscale;

import java.io.ByteArrayOutputStream;
import pl.pks.memgen.io.UploadedImage;
import pl.pks.memgen.io.processor.handler.AbstractImageHandler;

public class DownscaleImageHandler extends AbstractImageHandler {

    private final DownscaleStrategy resizeStrategy;
    private final int height;

    public DownscaleImageHandler(DownscaleStrategy downscaleStrategy, int height) {
        this.resizeStrategy = downscaleStrategy;
        this.height = height;
    }

    @Override
    public UploadedImage handleImage(UploadedImage uploadedImage) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        resizeStrategy.downscale(uploadedImage.getDataInputStream(), output, null, height);
        return uploadedImage.changeData(output).changeContentLength(output.size());
    }
}
