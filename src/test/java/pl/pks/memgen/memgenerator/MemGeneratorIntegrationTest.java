package pl.pks.memgen.memgenerator;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StubFigureStorageService;
import pl.pks.memgen.io.FigureDownloader;
import pl.pks.memgen.memgenerator.im4j.Im4jTransformer;

public class MemGeneratorIntegrationTest {

    @Test
    public void generateMem() {
        // given
        MemGenerator memGenerator = new MemGenerator(new FigureDownloader(), new StubFigureStorageService(),
            new Im4jTransformer());
        String imageId = "9857129f-2194-4ec5-832f-276997e8287a.jpg";
        String url = null;
        String topTitle = "Top title";
        String bottomTitle = "Bottom title";
        Meme captionedMeme = new Meme(imageId, url, topTitle, bottomTitle);

        // when
        String generatedMemUrl = memGenerator.generate(captionedMeme);

        // then
        assertThat(generatedMemUrl).isNotNull();
    }
}
