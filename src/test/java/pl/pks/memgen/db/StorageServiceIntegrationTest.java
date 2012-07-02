package pl.pks.memgen.db;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import pl.pks.memgen.StorageConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class StorageServiceIntegrationTest {

    private AmazonS3 amazonS3 = mock(AmazonS3.class);
    private StorageConfiguration storageConfiguration = mock(StorageConfiguration.class);

    private StorageService storageService = new AmazonStorageService(amazonS3, storageConfiguration);

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
            storageService.save(nonImageFileURL);
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
        storageService.save(imageURL);
        // then
        verify(amazonS3).putObject(any(PutObjectRequest.class));
    }

    @Test
    public void shouldNotSaveIfEmpty() {
        // given
        final String emptyImageURL = "https://dl.dropbox.com/u/1114182/memgen/empty.jpg";
        try {
            // when
            storageService.save(emptyImageURL);
            fail();
        } catch (Exception e) {
            // then
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
