package pl.pks.memgen.views.meme;

import java.util.List;
import pl.pks.memgen.api.Meme;
import com.yammer.dropwizard.views.View;

public class AllMemeView extends View {

    private final List<Meme> memes;

    public AllMemeView(List<Meme> memes) {
        super("/views/meme/allMemes.ftl");
        this.memes = memes;
    }

    public List<Meme> getMemes() {
        return memes;
    }

}
