package pl.pks.memgen.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/")
public class RootResource {

    @GET
    public Response redirectToFigureList() {
        return Response.seeOther(UriBuilder.fromResource(FigureResource.class).build()).build();
    }
}
