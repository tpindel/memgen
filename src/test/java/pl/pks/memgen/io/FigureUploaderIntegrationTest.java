package pl.pks.memgen.io;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import pl.pks.memgen.UploadConfiguration;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.io.processor.ImageProcessorFactory;

public class FigureUploaderIntegrationTest {

    private StorageService storageService = mock(StorageService.class);
    private UploadConfiguration uploadConfiguration = new UploadConfiguration();
    private ImageProcessorFactory imageProcessorFactory = new ImageProcessorFactory(uploadConfiguration);

    private FigureUploader figureUploader = new FigureUploader(storageService, imageProcessorFactory.create(),
        new ImageDownloader());

    @Captor
    private ArgumentCaptor<UploadedImage> captor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldUploadJPGFromLink() {
        // given
        final String imageURL = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";
        // when
        figureUploader.fromLink(imageURL);
        // then
        assertUploadedImage();
    }

    @Test
    public void shouldUploadJPGFromDisk() {
        // given
        InputStream fileStream = getClass().getClassLoader().getResourceAsStream("philosoraptor.jpg");
        // when
        figureUploader.fromDisk(fileStream, "image/jpeg");
        // then
        assertUploadedImage();
    }

    private void assertUploadedImage() {
        verify(storageService).saveFigure(captor.capture());
        UploadedImage value = captor.getValue();
        assertThat(value.getContentType()).isEqualTo("image/jpeg");
        assertThat(value.getContentLength()).isGreaterThan(0).isLessThanOrEqualTo(uploadConfiguration.getMaxSize());
        assertThat(value.getDataInputStream()).isNotNull();
    }
}
