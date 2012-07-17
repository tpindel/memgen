package pl.pks.memgen.resources;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.db.FigureStorageService;
import pl.pks.memgen.io.FigureUploader;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.yammer.dropwizard.testing.ResourceTest;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;

public class FigureResourceIntegrationTest extends ResourceTest {

    private FigureStorageService storage = mock(FigureStorageService.class);
    private FigureUploader uploader = mock(FigureUploader.class);

    @Override
    protected void setUpResources() {
        addResource(new FigureResource(storage, uploader));
        addProvider(ViewMessageBodyWriter.class);
    }

    @Test
    public void shouldPersistImageFromLink() throws Exception {
        // given
        WebResource service = client().resource("/figure");
        final String url = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";
        Form form = getFormFixutre(url);
        given(uploader.upload(anyString())).willReturn(getFigureFixed());
        // when
        ClientResponse post = service.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
        // then
        String locationHeader = post.getHeaders().getFirst("location");
        assertThat(locationHeader).contains("/meme/foo.jpg");
        verify(uploader).upload(anyString());
    }

    private Figure getFigureFixed() {
        return new Figure("foo.jpg", "http://bar/");
    }

    private Form getFormFixutre(String url) {
        Form form = new Form();
        form.add("url", url);
        return form;
    }

}
