package pl.pks.memgen.io.processor.handler;

import static com.google.common.base.Preconditions.checkArgument;
import pl.pks.memgen.io.UploadedImage;

public class ContentLengthHandler extends AbstractImageHandler {

    private final long limit;

    public ContentLengthHandler(long limit) {
        checkArgument(limit > 0);
        this.limit = limit;
    }

    @Override
    public UploadedImage handleImage(UploadedImage uploadedImage) {
        checkArgument(uploadedImage.getContentLength() <= limit, "Content exceeds the limit");
        return nextImageHandler.handleImage(uploadedImage);
    }

}
