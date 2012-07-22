package pl.pks.memgen.memgenerator;

import static org.fest.assertions.Assertions.assertThat;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import pl.pks.memgen.io.FigureDownloader;
import pl.pks.memgen.io.ImageDownloadException;

public class ImageDownloaderIntegrationTest {

    private FigureDownloader imageDownloader = new FigureDownloader();

    @Test
    public void downloadImageFromS3() {
        // given
        String imageUrl = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";

        // when
        InputStream downloadedStream = imageDownloader.download(imageUrl);

        // then
        assertThat(downloadedStream).isNotNull();
    }

    @Test
    public void notAllowToDownloadImageFromFakeAddress() {
        // given
        String fakeImageUrl = "wrong image address";

        // when
        ImageDownloadException thrownException = null;
        try {
            imageDownloader.download(fakeImageUrl);
        } catch (ImageDownloadException e) {
            thrownException = e;
        }

        // then
        assertThat(thrownException.getCause()).isInstanceOf(IOException.class);
    }
}
