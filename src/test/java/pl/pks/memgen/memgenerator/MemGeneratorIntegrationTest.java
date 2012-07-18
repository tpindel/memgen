package pl.pks.memgen.memgenerator;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StubFigureStorageService;
import pl.pks.memgen.io.FigureFromUrlDownloader;
import pl.pks.memgen.memgenerator.impl.Im4jTransformer;
import pl.pks.memgen.memgenerator.impl.MemGeneratorImpl;

public class MemGeneratorIntegrationTest {

    @Test
    public void generateMem() {
        // given
        MemGenerator memGenerator = new MemGeneratorImpl(new FigureFromUrlDownloader(), new StubFigureStorageService(),
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
