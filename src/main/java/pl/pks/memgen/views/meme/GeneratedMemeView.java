package pl.pks.memgen.views.meme;

import com.yammer.dropwizard.views.View;

public class GeneratedMemeView extends View {

    private final String memPath;

    public GeneratedMemeView(String memPath) {
        super("/views/meme/generatedMeme.ftl");
        this.memPath = memPath;
    }

    public String getMemPath() {
        return memPath;
    }
}
