package pl.pks.memgen.io;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import pl.pks.memgen.StorageConfiguration;
import pl.pks.memgen.UploadConfiguration;
import pl.pks.memgen.db.StorageService;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class FigureUploaderIntegrationTest {

    private StorageConfiguration storageConfiguration = mock(StorageConfiguration.class);
    private UploadConfiguration uploadConfiguration = mock(UploadConfiguration.class);

    private StorageService storageService = mock(StorageService.class);
    private FigureUploader figureUploader = new FigureUploader(storageService, uploadConfiguration);

    @Before
    public void setUp() {
        when(storageConfiguration.getBucket()).thenReturn("memgen");
        when(storageConfiguration.getEndpoint()).thenReturn("https://s3-eu-west-1.amazonaws.com");
    }

    @Test
    public void shouldNotUploadIfNotAnImage() {
        // given
        final String nonImageFileURL = "https://dl.dropbox.com/u/1114182/memgen/textFile.txt";

        try {
            // when
            figureUploader.fromLink(nonImageFileURL);
            fail();
        } catch (Exception e) {
            // then
            assertThat(e).isInstanceOf(ImageDownloadException.class);
        }
    }

    @Test
    public void shouldUploadJPG() {
        // given
        given(uploadConfiguration.getMaxSize()).willReturn(2 * 1024 * 1024);
        final String imageURL = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";
        // when
        figureUploader.fromLink(imageURL);
        // then
        verify(storageService).saveFigure(any(ObjectMetadata.class), any(InputStream.class));
    }

    @Test
    public void shouldNotUploadIfEmpty() {
        // given
        final String emptyImageURL = "https://dl.dropbox.com/u/1114182/memgen/empty.jpg";
        try {
            // when
            figureUploader.fromLink(emptyImageURL);
            fail();
        } catch (Exception e) {
            // then
            assertThat(e).isInstanceOf(ImageDownloadException.class);
        }
    }

    @Test
    public void shouldNotUploadIfAllowedContentLengthIsExceeded() {
        // given
        final String hugeImageURL = "https://dl.dropbox.com/u/1114182/memgen/over5mb.jpg";
        try {
            // when
            figureUploader.fromLink(hugeImageURL);
            fail();
        } catch (Exception e) {
            // then
            assertThat(e).isInstanceOf(ImageDownloadException.class);
        }
    }

    @Test
    public void shouldNotExceedDownloadLimit() {
        given(uploadConfiguration.getMaxSize()).willReturn(1024);
        figureUploader = new FigureUploader(storageService, uploadConfiguration);
        InputStream tooBigFileStream = getClass().getClassLoader().getResourceAsStream("philosoraptor.jpg");
        // when
        figureUploader.fromDisk(tooBigFileStream, "image/jpeg");
        // then
        ArgumentCaptor<ObjectMetadata> captor = ArgumentCaptor.forClass(ObjectMetadata.class);
        verify(storageService).saveFigure(captor.capture(), any(InputStream.class));
        assertThat(captor.getValue().getContentLength()).isLessThanOrEqualTo(1024);
    }

}
