package pl.pks.memgen.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.views.GalleryView;
import com.yammer.metrics.annotation.Timed;

@Produces(MediaType.TEXT_HTML)
@Path("/")
public class RootResource {

    private final StorageService storageService;

    public RootResource(StorageService storageService) {
        this.storageService = storageService;
    }

    @Timed
    @GET
    public GalleryView hello() {
        return new GalleryView(storageService.findAll());
    }

}
