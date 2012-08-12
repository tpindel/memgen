package pl.pks.memgen.io.processor;

import static java.util.Arrays.asList;
import pl.pks.memgen.UploadConfiguration;
import pl.pks.memgen.io.processor.handler.ContentLengthHandler;
import pl.pks.memgen.io.processor.handler.ContentTypeHandler;
import pl.pks.memgen.io.processor.handler.download.DownloadImageHandler;
import pl.pks.memgen.io.processor.handler.download.LimitedDownloadStrategy;
import pl.pks.memgen.io.processor.handler.downscale.DownscaleImageHandler;
import pl.pks.memgen.io.processor.handler.downscale.Im4jDownscaleStrategy;

public class ImageProcessorFactory {

    private final UploadConfiguration uploadConfiguration;

    public ImageProcessorFactory(UploadConfiguration uploadConfiguration) {
        this.uploadConfiguration = uploadConfiguration;
    }

    public ImageProcessor create() {
        ImageProcessor imageProcessor = new ImageProcessor();
        int maxSize = uploadConfiguration.getMaxSize();
        imageProcessor.addImageHandler(new ContentTypeHandler(asList("image/jpeg", "image/png")));
        imageProcessor.addImageHandler(new ContentLengthHandler(maxSize));
        imageProcessor.addImageHandler(new DownloadImageHandler(new LimitedDownloadStrategy(maxSize)));
        imageProcessor.addImageHandler(new DownscaleImageHandler(new Im4jDownscaleStrategy(), 400));
        return imageProcessor;
    }

}
