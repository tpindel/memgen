package pl.pks.memgen.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.yammer.metrics.annotation.Timed;

@Path("/placeholder")
@Produces(MediaType.TEXT_PLAIN)
public class PlaceholderResource {

    private final String name;

    public PlaceholderResource(String memGenName) {
        this.name = memGenName;
    }

    @GET
    @Timed
    public String hello() {
        
        return name + " hello";
    }
}
