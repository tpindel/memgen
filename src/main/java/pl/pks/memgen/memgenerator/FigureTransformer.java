package pl.pks.memgen.memgenerator;

import java.io.InputStream;
import java.io.OutputStream;
import pl.pks.memgen.api.Meme;

public interface FigureTransformer {

    void transform(Meme meme, InputStream input, OutputStream output);
}
