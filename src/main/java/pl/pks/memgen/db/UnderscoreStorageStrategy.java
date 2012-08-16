package pl.pks.memgen.db;

import static com.google.common.base.Joiner.on;
import pl.pks.memgen.StorageConfiguration;

public class UnderscoreStorageStrategy implements StorageStrategy {

    private final NameResolver nameResolver;
    private final UrlResolver urlResolver;

    public UnderscoreStorageStrategy(NameResolver nameResolver, UrlResolver urlResolver) {
        this.nameResolver = nameResolver;
        this.urlResolver = urlResolver;
    }

    @Override
    public String resolveFigureName(String contentType) {
        return on("").join(getFiguresPath(), nameResolver.resolve(contentType));
    }

    @Override
    public String resolveMemeName(String contentType) {
        return on("").join(getMemesPath(), nameResolver.resolve(contentType));

    }

    @Override
    public String resolveUrl(String name, StorageConfiguration storageConfiguration) {
        return urlResolver.resolve(name, storageConfiguration.getEndpoint(), storageConfiguration.getBucket());
    }

    @Override
    public String getFiguresPath() {
        return "f_";
    }

    @Override
    public String getMemesPath() {
        return "m_";
    }

}
