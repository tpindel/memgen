package pl.pks.memgen.views;

import com.yammer.dropwizard.views.View;

public class LayoutView extends View {

    public LayoutView() {
        super("/views/layout.ftl");
    }

}
