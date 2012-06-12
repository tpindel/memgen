package pl.pks.memgen.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.views.LayoutView;
import com.yammer.metrics.annotation.Timed;

@Path("/placeholder")
public class PlaceholderResource {

    @Timed
    @GET
    @Produces(MediaType.TEXT_HTML)
    public LayoutView hello() {
        return new LayoutView();
    }
}
