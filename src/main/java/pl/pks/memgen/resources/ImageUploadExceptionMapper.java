package pl.pks.memgen.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import pl.pks.memgen.io.ImageUploadException;

@Provider
public class ImageUploadExceptionMapper implements ExceptionMapper<ImageUploadException> {

    @Override
    public Response toResponse(ImageUploadException exception) {
        return Response.seeOther(UriBuilder.fromPath("/figure/invalid").build()).build();
    }

}
