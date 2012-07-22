package pl.pks.memgen.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.FigureStorageService;
import pl.pks.memgen.memgenerator.MemGenerator;
import pl.pks.memgen.views.meme.GeneratedMemeView;
import pl.pks.memgen.views.meme.NewMemeView;
import com.yammer.metrics.annotation.Timed;

@Path("/meme/{id}")
public class MemeResource {

    private final FigureStorageService storageService;
    private final MemGenerator memGenerator;

    public MemeResource(FigureStorageService storageService, MemGenerator memGenerator) {
        this.storageService = storageService;
        this.memGenerator = memGenerator;
    }

    @Timed
    @GET
    @Produces(MediaType.TEXT_HTML)
    public NewMemeView newMeme(@PathParam("id") String id) {
        Figure figure = storageService.findOne(id);
        return new NewMemeView(figure.getId(), figure.getUrl());
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public GeneratedMemeView generateMeme(@PathParam("id") String id, @FormParam("topTitle") String topTitle,
                                         @FormParam("bottomTitle") String bottomTitle) {
        String generatedMemImageFilePath = memGenerator.generate(new Meme(id, null, topTitle, bottomTitle));

        return new GeneratedMemeView(generatedMemImageFilePath);
    }
}
