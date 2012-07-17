package pl.pks.memgen.views;

import com.yammer.dropwizard.views.View;

public class GeneratedMemView extends View {

    private final String memPath;

    public GeneratedMemView(String memPath) {
        super("/views/generatedMem.ftl");
        this.memPath = memPath;
    }

    public String getMemPath() {
        return memPath;
    }
}
