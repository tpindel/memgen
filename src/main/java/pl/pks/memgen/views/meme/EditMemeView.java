package pl.pks.memgen.views.meme;

import com.yammer.dropwizard.views.View;

public class EditMemeView extends View {

    private final String id;
    private final String url;

    public EditMemeView(String id, String url) {
        super("/views/meme/editMeme.ftl");
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
