package pl.pks.memgen.io.processor.handler.downscale;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.BDDMockito;
import pl.pks.memgen.io.UploadedImage;
import pl.pks.memgen.io.processor.handler.AbstractHandlerTest;

public class DownscaleHandlerTest extends AbstractHandlerTest {

    private UploadedImage uploadedImage = mock(UploadedImage.class);
    private DownscaleStrategy downscaleStrategy = mock(DownscaleStrategy.class);
    private Integer height = 400;

    @Override
    public void setup() {
        handler = new DownscaleImageHandler(downscaleStrategy, height);
    }

    @Test
    public void shouldDownscaleImage() {
        // given
        InputStream testInputStream = IOUtils.toInputStream("test data");
        given(uploadedImage.getDataInputStream()).willReturn(testInputStream);
        given(uploadedImage.changeData(any(ByteArrayOutputStream.class))).willReturn(uploadedImage);
        given(uploadedImage.changeContentLength(anyLong())).willReturn(uploadedImage);
        // when
        handler.handleImage(uploadedImage);
        // then
        verify(downscaleStrategy).downscale(eq(testInputStream), any(OutputStream.class), anyInt(), eq(height));
        verifyZeroInteractions(nextImageHandler);
    }

    @Test
    public void shouldNotTriggerNextHandlerIfDownscaleFailed() {
        BDDMockito.doThrow(IllegalArgumentException.class).when(downscaleStrategy)
            .downscale(any(InputStream.class), any(OutputStream.class), any(Integer.class),
                any(Integer.class));
        shouldNotTriggerNextHandler(uploadedImage);
    }
}
