package pl.pks.memgen.api;

import org.codehaus.jackson.annotate.JsonProperty;

public class Figure {

    @JsonProperty
    private final String id;

    @JsonProperty
    private final String url;

    public Figure(@JsonProperty("id") String id, @JsonProperty("url") String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

}
