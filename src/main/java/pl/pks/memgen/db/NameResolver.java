package pl.pks.memgen.db;

import static com.google.common.base.Joiner.on;

public class NameResolver {

    private final IdGenerator idGenerator;

    public NameResolver(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public String resolve(String contentType) {
        return on("").join(idGenerator.generate(), getExtension(contentType));
    }

    private String getExtension(String contentType) {
        switch (contentType) {
            case "image/jpeg":
                return ".jpg";
            case "image/png":
                return ".png";
        }
        throw new IllegalStateException("Content type not supported.");
    }

}
