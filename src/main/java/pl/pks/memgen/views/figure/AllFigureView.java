package pl.pks.memgen.views.figure;

import java.util.List;
import pl.pks.memgen.api.Figure;
import com.yammer.dropwizard.views.View;

public class AllFigureView extends View {

    public final List<Figure> figures;

    public AllFigureView(List<Figure> figures) {
        super("/views/figure/allFigures.ftl");
        this.figures = figures;
    }

    public List<Figure> getFigures() {
        return figures;
    }

}
