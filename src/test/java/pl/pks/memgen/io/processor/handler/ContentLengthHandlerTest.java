package pl.pks.memgen.io.processor.handler;

import static org.apache.commons.io.IOUtils.toInputStream;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import pl.pks.memgen.io.UploadedImage;

public class ContentLengthHandlerTest extends AbstractHandlerTest {

    static final int LIMIT = 1024;

    @Override
    public void setup() {
        handler = new ContentLengthHandler(LIMIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateHandlerForNonPositiveLimit() {
        long nonPositivieLimit = 0l;
        new ContentLengthHandler(nonPositivieLimit);
    }

    @Test
    public void shouldHandleProperSize() {
        // given
        final int PROPER_SIZE = 512;
        UploadedImage uploadedImage = new UploadedImage("image/jpeg", PROPER_SIZE, toInputStream("test"));
        // when
        handler.handleImage(uploadedImage);
        // then
        verify(nextImageHandler).handleImage(eq(uploadedImage));
    }

    @Test
    public void shouldNotHandleImproperSize() {
        // given
        final int IMPROPER_SIZE = 2048;
        UploadedImage uploadedImage = new UploadedImage("image/jpeg", IMPROPER_SIZE, toInputStream("test"));
        shouldNotTriggerNextHandler(uploadedImage);
    }

}
