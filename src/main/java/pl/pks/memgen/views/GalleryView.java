package pl.pks.memgen.views;

import java.util.List;
import pl.pks.memgen.api.Meme;
import com.yammer.dropwizard.views.View;

public class GalleryView extends View {

    public final List<Meme> memes;

    public GalleryView(List<Meme> memes) {
        super("/views/gallery.ftl");
        this.memes = memes;
    }

    public List<Meme> getMemes() {
        return memes;
    }

}
