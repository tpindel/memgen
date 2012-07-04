package pl.pks.memgen.memgenerator;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import pl.pks.memgen.api.CaptionedMeme;
import pl.pks.memgen.db.StubStorageService;
import pl.pks.memgen.io.ImageFromUrlDownloader;
import pl.pks.memgen.memgenerator.impl.MemGeneratorImpl;

public class MemGeneratorIntegrationTest {

    @Test
    public void generateMem() {
        // given
        MemGenerator memGenerator = new MemGeneratorImpl(new ImageFromUrlDownloader(), new StubStorageService());
        String imageId = "9857129f-2194-4ec5-832f-276997e8287a.jpg";
        String url = null;
        String topTitle = "Top title";
        String bottomTitle = "Bottom title";
        CaptionedMeme captionedMeme = new CaptionedMeme(imageId, url, topTitle, bottomTitle);

        // when
        String generatedMemUrl = memGenerator.generate(captionedMeme);

        // then
        assertThat(generatedMemUrl).isNotNull();
    }
}
