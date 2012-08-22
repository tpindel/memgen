package pl.pks.memgen.resources;

import static javax.ws.rs.core.Response.seeOther;
import static javax.ws.rs.core.UriBuilder.fromPath;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import pl.pks.memgen.generator.GeneratorException;
import com.yammer.dropwizard.logging.Log;

@Provider
public class GeneratorExceptionMapper implements ExceptionMapper<GeneratorException> {

    private static final Log LOG = Log.forClass(GeneratorExceptionMapper.class);

    @Override
    public Response toResponse(GeneratorException e) {
        LOG.error(e, "A problem while generating meme from figure {}", e.getFigureId());
        return seeOther(fromPath("/meme/error").build()).build();
    }
}
