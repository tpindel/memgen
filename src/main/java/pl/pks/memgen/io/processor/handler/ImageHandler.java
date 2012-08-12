package pl.pks.memgen.io.processor.handler;

import pl.pks.memgen.io.UploadedImage;

public interface ImageHandler {

    void setNext(ImageHandler nextImageHandler);

    UploadedImage handleImage(UploadedImage uploadedImage);
}
