package pl.pks.memgen.io.processor.handler.downscale;

import java.io.InputStream;
import java.io.OutputStream;

public interface DownscaleStrategy {

    void downscale(InputStream input, OutputStream output, Integer width, Integer height);

}
