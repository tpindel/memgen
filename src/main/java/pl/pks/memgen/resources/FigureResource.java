package pl.pks.memgen.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.Response.seeOther;
import static javax.ws.rs.core.UriBuilder.fromResource;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.io.FigureUploader;
import pl.pks.memgen.views.figure.AllFigureView;
import pl.pks.memgen.views.figure.InvalidFigureView;
import pl.pks.memgen.views.figure.NewFigureFromDisk;
import pl.pks.memgen.views.figure.NewFigureView;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;

@Produces(TEXT_HTML)
@Path("/figure")
public class FigureResource {

    private final StorageService figureStorageService;
    private final FigureUploader figureUploader;

    public FigureResource(StorageService figureStorageService, FigureUploader figureUploader) {
        this.figureStorageService = figureStorageService;
        this.figureUploader = figureUploader;
    }

    @Timed
    @GET
    public AllFigureView showAll() {
        return new AllFigureView(figureStorageService.findAllFigures());
    }

    @Timed
    @GET
    @Path("/new")
    public NewFigureView newFigure() {
        return new NewFigureView();
    }

    @Timed
    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response addNewFigure(@FormParam("url") String url) {
        Figure uploaded = figureUploader.fromLink(url);
        return redirectToMemeGeneration(uploaded);

    }

    private Response redirectToMemeGeneration(Figure uploaded) {
        return seeOther(fromResource(MemeResource.class).path("new/{id}").build(uploaded.getId()))
            .build();
    }

    @Timed
    @GET
    @Path("/fromDisk")
    public NewFigureFromDisk newFigureFromDisk() {
        return new NewFigureFromDisk();
    }

    @Timed
    @POST
    @Path("/fromDisk")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addNewFigureFromDisk(@FormDataParam("file") InputStream fileInputStream,
                                         @FormDataParam("file") FormDataBodyPart bodyPart) {
        Figure uploaded = figureUploader.fromDisk(fileInputStream, bodyPart.getMediaType()
            .toString());
        return redirectToMemeGeneration(uploaded);
    }

    @GET
    @Path("/invalid")
    public View showInvalidMessage() {
        return new InvalidFigureView();
    }
}
