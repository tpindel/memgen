package pl.pks.memgen.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.db.FigureStorageService;
import pl.pks.memgen.io.FigureUploader;
import pl.pks.memgen.io.ImageDownloadException;
import pl.pks.memgen.views.figure.AllFigureView;
import pl.pks.memgen.views.figure.NewFigureView;
import com.yammer.metrics.annotation.Timed;

@Produces(MediaType.TEXT_HTML)
@Path("/figure")
public class FigureResource {

    private final FigureStorageService figureStorageService;
    private final FigureUploader figureUploader;

    public FigureResource(FigureStorageService figureStorageService, FigureUploader figureUploader) {
        this.figureStorageService = figureStorageService;
        this.figureUploader = figureUploader;
    }

    @Timed
    @GET
    public AllFigureView all() {
        return new AllFigureView(figureStorageService.findAll());
    }

    @Timed
    @GET
    @Path("/figure/new")
    public NewFigureView newFigure() {
        return new NewFigureView();
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response uploadNewMeme(@FormParam("url") String url) {
        try {
            Figure uploaded = figureUploader.upload(url);
            return Response.seeOther(UriBuilder.fromResource(MemeResource.class).build(uploaded.getId())).build();
        } catch (ImageDownloadException e) {
            return Response.status(500).build();
        }

    }
}
