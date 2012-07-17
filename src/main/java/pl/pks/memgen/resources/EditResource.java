package pl.pks.memgen.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.api.CaptionedMeme;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.memgenerator.MemGenerator;
import pl.pks.memgen.views.EditView;
import pl.pks.memgen.views.GeneratedMemView;
import com.yammer.metrics.annotation.Timed;

@Path("/edit/{id}")
public class EditResource {

    private final StorageService storageService;
    private final MemGenerator memGenerator;

    public EditResource(StorageService storageService, MemGenerator memGenerator) {
        this.storageService = storageService;
        this.memGenerator = memGenerator;
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
    public GeneratedMemView generateMeme(@PathParam("id") String id, @FormParam("topTitle") String topTitle,
                                         @FormParam("bottomTitle") String bottomTitle) {

        String generatedMemImageFilePath = memGenerator.generate(new CaptionedMeme(id, null, topTitle, bottomTitle));

        return new GeneratedMemView("localhost:8080/display/" + generatedMemImageFilePath);
    }
}
