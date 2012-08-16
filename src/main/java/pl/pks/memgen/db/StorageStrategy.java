package pl.pks.memgen.db;

import pl.pks.memgen.StorageConfiguration;

public interface StorageStrategy {

    String resolveFigureName(String contentType);

    String resolveMemeName(String contentType);

    String resolveUrl(String name, StorageConfiguration storageConfiguration);

    String getFiguresPath();

    String getMemesPath();
}
