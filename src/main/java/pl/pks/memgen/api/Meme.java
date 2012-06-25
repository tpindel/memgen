package pl.pks.memgen.api;

import org.codehaus.jackson.annotate.JsonProperty;

public class Meme {

    @JsonProperty
    private final String url;

    public Meme(@JsonProperty("url") String url) {
        this.url = url;

    }

    public String getUrl() {
        return url;
    }

}
