package pl.pks.memgen.io.processor.handler;

import static java.util.Arrays.asList;
import static org.apache.commons.io.IOUtils.toInputStream;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import java.util.List;
import org.junit.Test;
import pl.pks.memgen.io.UploadedImage;

public class ContentTypeHandlerTest extends AbstractHandlerTest {

    static final List<String> CONTENT_TYPES = asList("image/jpeg", "image/png");

    @Override
    public void setup() {
        handler = new ContentTypeHandler(CONTENT_TYPES);
    }

    @Test
    public void shouldHandleJPG() {
        // given
        UploadedImage uploadedImage = new UploadedImage("image/jpeg", 1024, toInputStream("test"));
        // when
        handler.handleImage(uploadedImage);
        // then
        verify(nextImageHandler).handleImage(eq(uploadedImage));
    }

    @Test
    public void shoultNotHandleGif() {
        shouldNotHandleIf("image/gif");
    }

    @Test
    public void shouldNotHandleIfNullGiven() {
        shouldNotHandleIf(null);
    }

    private void shouldNotHandleIf(String value) {
        UploadedImage uploadedImage = new UploadedImage(value, 1024, toInputStream("test"));
        shouldNotTriggerNextHandler(uploadedImage);
    }

}
