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
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.io.ImageDownloadException;
import pl.pks.memgen.io.ImageFromUrlUploader;
import pl.pks.memgen.views.UploadFormView;
import com.yammer.metrics.annotation.Timed;

@Produces(MediaType.TEXT_HTML)
@Path("upload")
public class UploadResource {

    private final ImageFromUrlUploader imageUploader;

    public UploadResource(ImageFromUrlUploader imageUploader) {
        this.imageUploader = imageUploader;
    }

    @Timed
    @GET
    public UploadFormView newMemePage() {
        return new UploadFormView();
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response uploadNewMeme(@FormParam("url") String url) {
        try {
            Meme uploaded = imageUploader.upload(url);
            return Response.seeOther(UriBuilder.fromResource(EditResource.class).build(uploaded.getId())).build();
        } catch (ImageDownloadException e) {
            return Response.status(500).build();
        }

    }
}
