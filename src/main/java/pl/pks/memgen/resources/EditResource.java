package pl.pks.memgen.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.views.EditView;
import com.yammer.metrics.annotation.Timed;

@Path("/edit/{id}")
public class EditResource {

    private final StorageService storageService;

    public EditResource(StorageService storageService) {
        this.storageService = storageService;
    }

    @Timed
    @GET
    @Produces(MediaType.TEXT_HTML)
    public EditView editMeme(@PathParam("id") String id) {
        Meme meme = storageService.findOne(id);
        return new EditView(meme.getId(), meme.getUrl());
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String generateMeme(@PathParam("id") String id, @FormParam("topTitle") String topTitle,
                               @FormParam("bottomTitle") String bottomTitle) {

        return "TODO";
    }
}
