package pl.pks.memgen.io;

import java.io.InputStream;

public interface FigureDownloader {

    InputStream download(String imageUrl);

}
