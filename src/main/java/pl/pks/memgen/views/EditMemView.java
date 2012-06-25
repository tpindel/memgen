package pl.pks.memgen.views;

import com.yammer.dropwizard.views.View;

public class EditMemView extends View {

    private final String memUrl;

    public EditMemView(String memUrl) {
        super("/views/editMem.ftl");
        this.memUrl = memUrl;
    }

    public String getMemUrl() {
        return memUrl;
    }
}
