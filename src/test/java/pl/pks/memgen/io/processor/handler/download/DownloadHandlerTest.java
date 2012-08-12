package pl.pks.memgen.io.processor.handler.download;

import static org.apache.commons.io.IOUtils.toInputStream;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;
import pl.pks.memgen.io.UploadedImage;
import pl.pks.memgen.io.processor.handler.AbstractHandlerTest;

public class DownloadHandlerTest extends AbstractHandlerTest {

    private DownloadStrategy downloadStrategy = mock(DownloadStrategy.class);
    private UploadedImage uploadedImage = mock(UploadedImage.class);

    @Override
    public void setup() {
        handler = new DownloadImageHandler(downloadStrategy);
    }

    @Test
    public void shouldDownloadContent() {
        // given
        InputStream testInputStream = toInputStream("test data");
        long downloadedBytes = 512l;
        given(uploadedImage.getDataInputStream()).willReturn(testInputStream);
        given(downloadStrategy.download(eq(testInputStream), any(OutputStream.class))).willReturn(downloadedBytes);
        given(uploadedImage.changeContentLength(eq(downloadedBytes))).willReturn(uploadedImage);
        given(uploadedImage.changeData(any(ByteArrayOutputStream.class))).willReturn(uploadedImage);
        // when
        handler.handleImage(uploadedImage);
        // then
        verify(nextImageHandler).handleImage(eq(uploadedImage));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotTriggerNextHandlerIfDownloadingFails() {
        given(downloadStrategy.download(any(InputStream.class), any(OutputStream.class))).willThrow(
            IllegalArgumentException.class);
        shouldNotTriggerNextHandler(uploadedImage);
    }
}
