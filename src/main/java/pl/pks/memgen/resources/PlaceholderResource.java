package pl.pks.memgen.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.views.GalleryView;
import com.yammer.metrics.annotation.Timed;

@Path("/placeholder")
public class PlaceholderResource {

    private final StorageService storageService;

    public PlaceholderResource(StorageService storageService) {
        this.storageService = storageService;
    }

    @Timed
    @GET
    @Produces(MediaType.TEXT_HTML)
    public GalleryView hello() {
        return new GalleryView(storageService.findAll());
    }
}
