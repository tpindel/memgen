package pl.pks.memgen.resources;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import java.io.InputStream;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import pl.pks.memgen.UploadConfiguration;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.io.ImageFromUrlUploader;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.yammer.dropwizard.testing.ResourceTest;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;

public class RootResourceIntegrationTest extends ResourceTest {

    private StorageService storage = mock(StorageService.class);

    @Override
    protected void setUpResources() {
        addResource(new RootResource(storage, new ImageFromUrlUploader(storage, new UploadConfiguration())));
        addProvider(ViewMessageBodyWriter.class);
    }

    @Test
    public void shouldPersistImageFromLink() throws Exception {
        // given
        WebResource service = client().resource("/upload");
        final String url = "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg";
        Form form = getFormFixutre(url);
        // when
        service.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
        // then
        // TODO: verify redirect
        verify(storage).save(anyString(), any(ObjectMetadata.class), any(InputStream.class));
    }

    private Form getFormFixutre(String url) {
        Form form = new Form();
        form.add("url", url);
        return form;
    }

}
