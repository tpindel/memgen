package pl.pks.memgen.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.views.EditMemView;
import com.yammer.metrics.annotation.Timed;

@Path("/edit")
public class EditMemResource {

    @Timed
    @GET
    @Produces(MediaType.TEXT_HTML)
    public EditMemView editMeme(@QueryParam("memUrl") String memUrl) {
        return new EditMemView(memUrl);
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String generateMem(@QueryParam("memUrl") String memUrl, @FormParam("topTitle") String topTitle,
                              @FormParam("bottomTitle") String bottomTitle) {

        return "TODO";
    }
}
