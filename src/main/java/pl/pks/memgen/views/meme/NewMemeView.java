package pl.pks.memgen.views.meme;

import com.yammer.dropwizard.views.View;

public class NewMemeView extends View {

    private final String id;
    private final String url;

    public NewMemeView(String id, String url) {
        super("/views/meme/newMeme.ftl");
        this.id = id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

}
