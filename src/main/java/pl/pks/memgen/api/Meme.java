package pl.pks.memgen.api;

import org.codehaus.jackson.annotate.JsonProperty;

public class Meme extends Figure {

    @JsonProperty
    private final String topTitle;

    @JsonProperty
    private final String bottomTitle;

    public Meme(@JsonProperty("id") String id, @JsonProperty("url") String url,
                @JsonProperty("topTitle") String topTitle,
                @JsonProperty("bottomTitle") String bottomTitle) {
        super(id, url);
        this.topTitle = topTitle;
        this.bottomTitle = bottomTitle;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public String getBottomTitle() {
        return bottomTitle;
    }
}
