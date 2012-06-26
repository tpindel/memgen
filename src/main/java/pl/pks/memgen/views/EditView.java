package pl.pks.memgen.views;

import com.yammer.dropwizard.views.View;

public class EditView extends View {

    private final String id;
    private final String url;

    public EditView(String id, String url) {
        super("/views/edit.ftl");
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
