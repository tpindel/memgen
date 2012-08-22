package pl.pks.memgen.resources;

import static javax.ws.rs.core.Response.seeOther;
import static javax.ws.rs.core.UriBuilder.fromPath;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import pl.pks.memgen.io.ImageUploadException;
import com.yammer.dropwizard.logging.Log;

@Provider
public class ImageUploadExceptionMapper implements ExceptionMapper<ImageUploadException> {

    private static final Log LOG = Log.forClass(ImageUploadExceptionMapper.class);

    @Override
    public Response toResponse(ImageUploadException e) {
        LOG.error(e, "An error occured while image uploading");
        return seeOther(fromPath("/figure/invalid").build()).build();
    }

}
