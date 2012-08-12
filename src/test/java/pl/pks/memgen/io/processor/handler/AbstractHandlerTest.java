package pl.pks.memgen.io.processor.handler;

import static junit.framework.Assert.fail;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import org.junit.Before;
import pl.pks.memgen.io.UploadedImage;

public abstract class AbstractHandlerTest {

    protected ImageHandler nextImageHandler = mock(ImageHandler.class);
    protected ImageHandler handler;

    @Before
    public void setUp() {
        setup();
        handler.setNext(nextImageHandler);
    }

    abstract public void setup();

    protected void shouldNotTriggerNextHandler(UploadedImage uploadedImage) {
        // when
        try {
            handler.handleImage(uploadedImage);
            fail();
            // then
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(nextImageHandler);
    }

}
