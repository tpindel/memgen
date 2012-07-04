package pl.pks.memgen.uploader;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import pl.pks.memgen.StorageConfiguration;
import pl.pks.memgen.db.AmazonStorageService;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.io.ImageFromUrlUploader;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class ImageUploaderIntegrationTest {

    private AmazonS3 amazonS3 = mock(AmazonS3.class);
    private StorageConfiguration storageConfiguration = mock(StorageConfiguration.class);

    private StorageService storageService = new AmazonStorageService(amazonS3, storageConfiguration);
    private ImageFromUrlUploader imageUploader = new ImageFromUrlUploader(storageService);

    @Before
    public void setUp() {
        when(storageConfiguration.getBucket()).thenReturn("memgen");
        when(storageConfiguration.getEndpoint()).thenReturn("https://s3-eu-west-1.amazonaws.com");
    }

    @Test
    public void shouldNotSaveIfNotImage() {
        // given
        final String nonImageFileURL = "https://dl.dropbox.com/u/1114182/memgen/textFile.txt";

        try {
            // when
            imageUploader.upload(nonImageFileURL);
            fail();
        } catch (Exception e) {
            // then
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void shouldSaveJPG() {
        // given
        final String imageURL = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";
        // when
        imageUploader.upload(imageURL);
        // then
        verify(amazonS3).putObject(any(PutObjectRequest.class));
    }

    @Test
    public void shouldNotSaveIfEmpty() {
        // given
        final String emptyImageURL = "https://dl.dropbox.com/u/1114182/memgen/empty.jpg";
        try {
            // when
            imageUploader.upload(emptyImageURL);
            fail();
        } catch (Exception e) {
            // then
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
