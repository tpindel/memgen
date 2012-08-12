package pl.pks.memgen.io.processor;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.pks.memgen.io.UploadedImage;
import pl.pks.memgen.io.processor.handler.ImageHandler;

public class ImageProcessor {

    private ImageHandler first;
    private ImageHandler tail;

    public void addImageHandler(ImageHandler imageHandler) {
        if (this.first == null) {
            first = imageHandler;
            tail = first;
        } else {
            tail.setNext(imageHandler);
            tail = imageHandler;
        }
    }

    public UploadedImage handle(UploadedImage uploadedImage) {
        checkNotNull(first, "At least one image handler should be added");
        return first.handleImage(uploadedImage);
    }
}
