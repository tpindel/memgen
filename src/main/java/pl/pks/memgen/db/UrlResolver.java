package pl.pks.memgen.db;

import static com.google.common.base.Joiner.on;

public class UrlResolver {

    public String resolve(String name, String endpoint, String bucket) {
        return on('/').join(endpoint, bucket, name);
    }

}
