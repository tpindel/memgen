package pl.pks.memgen.io.processor.handler;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.List;
import pl.pks.memgen.io.UploadedImage;

public class ContentTypeHandler extends AbstractImageHandler {

    private final List<String> allowedContentTypes;

    public ContentTypeHandler(List<String> allowedContentTypes) {
        this.allowedContentTypes = allowedContentTypes;
    }

    @Override
    public UploadedImage handleImage(UploadedImage uploadedImage) {
        checkArgument(allowedContentTypes.contains(uploadedImage.getContentType()),
            "Illegal content type");
        return nextImageHandler.handleImage(uploadedImage);
    }
}
