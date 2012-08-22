package pl.pks.memgen.resources;

import static javax.ws.rs.core.Response.seeOther;
import static javax.ws.rs.core.UriBuilder.fromResource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class RootResource {

    @GET
    public Response redirectToFigureList() {
        return seeOther(fromResource(FigureResource.class).build()).build();
    }
}
