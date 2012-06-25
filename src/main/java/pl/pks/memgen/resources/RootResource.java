package pl.pks.memgen.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.views.GalleryView;
import pl.pks.memgen.views.UploadFormView;
import com.yammer.dropwizard.logging.Log;
import com.yammer.metrics.annotation.Timed;

@Produces(MediaType.TEXT_HTML)
@Path("/")
public class RootResource {

    private static final Log LOG = Log.forClass(RootResource.class);

    private final StorageService storageService;

    public RootResource(StorageService storageService) {
        this.storageService = storageService;
    }

    @Timed
    @GET
    public GalleryView hello() {
        return new GalleryView(storageService.findAll());
    }

    @Timed
    @GET
    @Path("upload")
    public UploadFormView newMemePage() {
        return new UploadFormView();
    }

    @Timed
    @POST
    @Path("upload")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UploadFormView uploadNewMeme(@FormParam("url") String url) {

        storageService.save(url);
        
        return new UploadFormView();
    }
}
