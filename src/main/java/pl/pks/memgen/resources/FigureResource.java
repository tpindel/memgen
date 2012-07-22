package pl.pks.memgen.resources;

import java.io.InputStream;
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
import pl.pks.memgen.views.figure.InvalidFigureView;
import pl.pks.memgen.views.figure.NewFigureFromDisk;
import pl.pks.memgen.views.figure.NewFigureView;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import com.yammer.dropwizard.views.View;
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
    @Path("/new")
    public NewFigureView newFigure() {
        return new NewFigureView();
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addNewFigure(@FormParam("url") String url) {
        try {
            Figure uploaded = figureUploader.fromLink(url);
            return Response.seeOther(UriBuilder.fromResource(MemeResource.class).build(uploaded.getId())).build();
        } catch (ImageDownloadException e) {
            return redirectToErrorPage();
        }

    }

    private Response redirectToErrorPage() {
        return Response.seeOther(UriBuilder.fromPath("/figure/invalid").build()).build();
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
        try {
            Figure uploaded = figureUploader.fromDisk(fileInputStream, bodyPart.getMediaType()
                .toString());
            return Response.seeOther(UriBuilder.fromResource(MemeResource.class).build(uploaded.getId())).build();
        } catch (ImageDownloadException e) {
            return redirectToErrorPage();
        }
    }

    @GET
    @Path("/invalid")
    public View showInvalidMessage() {
        return new InvalidFigureView();
    }
}
