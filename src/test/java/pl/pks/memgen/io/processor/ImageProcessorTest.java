package pl.pks.memgen.io.processor;

import static junit.framework.Assert.fail;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import org.mockito.InOrder;
import pl.pks.memgen.io.UploadedImage;
import pl.pks.memgen.io.processor.handler.ImageHandler;

public class ImageProcessorTest {

    private ImageProcessor imageProcessor = new ImageProcessor();

    @Test
    public void shouldRunOneHandler() {
        // given
        final ImageHandler imageHandler = mock(ImageHandler.class);
        final UploadedImage uploadedImage = mock(UploadedImage.class);
        // when
        imageProcessor.addImageHandler(imageHandler);
        imageProcessor.handle(uploadedImage);
        // then
        verify(imageHandler).handleImage(eq(uploadedImage));
    }

    @Test
    public void shouldAddChainOfHandlers() {
        // given
        final ImageHandler first = mock(ImageHandler.class);
        final ImageHandler second = mock(ImageHandler.class);
        final ImageHandler third = mock(ImageHandler.class);

        // when
        imageProcessor.addImageHandler(first);
        imageProcessor.addImageHandler(second);
        imageProcessor.addImageHandler(third);

        // then
        InOrder inOrder = inOrder(first, second, third);
        inOrder.verify(first).setNext(eq(second));
        inOrder.verify(second).setNext(eq(third));
    }

    @Test
    public void shouldRaiseExceptionIfNoHandlerAdded() {
        // given
        final UploadedImage uploadedImage = mock(UploadedImage.class);
        try {
            // when
            imageProcessor.handle(uploadedImage);
            fail();
        } catch (Exception e) {
            // then
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
    }
}
