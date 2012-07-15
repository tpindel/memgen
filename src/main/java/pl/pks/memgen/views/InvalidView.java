package pl.pks.memgen.views;

import com.yammer.dropwizard.views.View;

public class InvalidView extends View {

    public InvalidView() {
        super("/views/invalid.ftl");
    }

}
