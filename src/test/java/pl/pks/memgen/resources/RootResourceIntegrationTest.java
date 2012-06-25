package pl.pks.memgen.resources;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import pl.pks.memgen.db.StorageService;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.yammer.dropwizard.testing.ResourceTest;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;

public class RootResourceIntegrationTest extends ResourceTest {

    private StorageService storage = mock(StorageService.class);

    @Override
    protected void setUpResources() {
        addResource(new RootResource(storage));
        addProvider(ViewMessageBodyWriter.class);
    }

    @Test
    public void shouldPersistImageFromLink() throws Exception {
        // given
        WebResource service = client().resource("/upload");
        final String url = "http://i1.kym-cdn.com/entries/icons/original/000/003/619/Untitled-1.jpg";
        Form form = getFormFixutre(url);
        // when
        service.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
        // then
        // TODO: verify redirect
        verify(storage).save(anyString());
    }

    private Form getFormFixutre(String url) {
        Form form = new Form();
        form.add("url", url);
        return form;
    }

}
