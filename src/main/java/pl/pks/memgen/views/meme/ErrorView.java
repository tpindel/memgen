package pl.pks.memgen.views.meme;

import com.yammer.dropwizard.views.View;

public class ErrorView extends View {

    public ErrorView() {
        super("/views/meme/error.ftl");
    }

}
