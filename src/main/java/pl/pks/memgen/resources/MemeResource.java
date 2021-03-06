package pl.pks.memgen.resources;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.Response.seeOther;
import static javax.ws.rs.core.UriBuilder.fromResource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.generator.Generator;
import pl.pks.memgen.views.meme.AllMemeView;
import pl.pks.memgen.views.meme.ErrorView;
import pl.pks.memgen.views.meme.GeneratedMemeView;
import pl.pks.memgen.views.meme.NewMemeView;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;

@Path("/meme")
public class MemeResource {

    private final StorageService storageService;
    private final Generator memGenerator;

    public MemeResource(StorageService storageService, Generator memGenerator) {
        this.storageService = storageService;
        this.memGenerator = memGenerator;
    }

    @Timed
    @GET
    @Path("/new/{id}")
    @Produces(TEXT_HTML)
    public NewMemeView newMeme(@PathParam("id") String id) {
        Figure figure = storageService.findOneFigure(id);
        return new NewMemeView(figure.getId(), figure.getUrl());
    }

    @Timed
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response generateMeme(@PathParam("id") String id, @FormParam("topTitle") String topTitle,
                                 @FormParam("bottomTitle") String bottomTitle) {
        String key = memGenerator.generate(new Meme(id, null, topTitle, bottomTitle));
        return seeOther(fromResource(MemeResource.class).build(key)).build();
    }

    @GET
    @Path("/{id}")
    public GeneratedMemeView showOne(@PathParam("id") String id) {
        Meme meme = storageService.findOneMeme(id);
        return new GeneratedMemeView(meme.getUrl());
    }

    @GET
    public AllMemeView showAll() {
        return new AllMemeView(storageService.findAllMemes());
    }

    @GET
    @Path("/error")
    public View showErrorMessage() {
        return new ErrorView();
    }
}
