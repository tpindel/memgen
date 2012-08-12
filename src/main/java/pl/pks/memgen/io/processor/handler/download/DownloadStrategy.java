package pl.pks.memgen.io.processor.handler.download;

import java.io.InputStream;
import java.io.OutputStream;

public interface DownloadStrategy {

    long download(InputStream input, OutputStream output);

}
