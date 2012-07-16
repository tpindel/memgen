package pl.pks.memgen.resources;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.io.ImageFromUrlUploader;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.yammer.dropwizard.testing.ResourceTest;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;

public class UploadResourceIntegrationTest extends ResourceTest {

    private ImageFromUrlUploader uploader = mock(ImageFromUrlUploader.class);

    @Override
    protected void setUpResources() {
        addResource(new UploadResource(uploader));
        addProvider(ViewMessageBodyWriter.class);
    }

    @Test
    public void shouldPersistImageFromLink() throws Exception {
        // given
        WebResource service = client().resource("/upload");
        final String url = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";
        Form form = getFormFixutre(url);
        given(uploader.upload(anyString())).willReturn(getMemeFixture());
        // when
        ClientResponse post = service.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
        // then
        String locationHeader = post.getHeaders().getFirst("location");
        assertThat(locationHeader).contains("/edit/foo.jpg");
        verify(uploader).upload(anyString());
    }

    private Meme getMemeFixture() {
        return new Meme("foo.jpg", "http://bar/");
    }

    private Form getFormFixutre(String url) {
        Form form = new Form();
        form.add("url", url);
        return form;
    }

}
