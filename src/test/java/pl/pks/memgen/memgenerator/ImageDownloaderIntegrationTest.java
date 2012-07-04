package pl.pks.memgen.memgenerator;

import static org.fest.assertions.Assertions.assertThat;
import java.io.IOException;
import org.junit.Test;
import pl.pks.memgen.io.ImageDownloadException;
import pl.pks.memgen.io.ImageDownloader;
import pl.pks.memgen.io.ImageFromUrlDownloader;

public class ImageDownloaderIntegrationTest {

    private ImageDownloader imageDownloader = new ImageFromUrlDownloader();
    private final String imageId = "9857129f-2194-4ec5-832f-276997e8287a.jpg";

    @Test
    public void downloadImageFromS3() {
        // given
        String imageUrl = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";

        // when
        String imageLocation = imageDownloader.download(imageUrl, imageId);

        // then
        assertThat(imageLocation).isNotNull();
    }

    @Test
    public void notAllowToDownloadImageFromFakeAddress() {
        // given
        String fakeImageUrl = "wrong image address";

        // when
        ImageDownloadException thrownException = null;
        try {
            imageDownloader.download(fakeImageUrl, imageId);
        } catch (ImageDownloadException e) {
            thrownException = e;
        }

        // then
        assertThat(thrownException.getCause()).isInstanceOf(IOException.class);
    }
}
