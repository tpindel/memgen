package pl.pks.memgen.api;

import org.codehaus.jackson.annotate.JsonProperty;

public class CaptionedMeme extends Meme {

    @JsonProperty
    private final String topTitle;

    @JsonProperty
    private final String bottomTitle;

    public CaptionedMeme(@JsonProperty("id") String id, @JsonProperty("url") String url,
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
