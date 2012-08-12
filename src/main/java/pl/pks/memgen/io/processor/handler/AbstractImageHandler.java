package pl.pks.memgen.io.processor.handler;

public abstract class AbstractImageHandler implements ImageHandler {

    protected ImageHandler nextImageHandler;

    @Override
    public void setNext(ImageHandler nextImageHandler) {
        this.nextImageHandler = nextImageHandler;
    }

}
