package pl.pks.memgen;

import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import com.yammer.dropwizard.config.Configuration;

public class MemGenConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String placeholder;

    @NotNull
    @JsonProperty
    private StorageConfiguration storage = new StorageConfiguration();

    @NotNull
    @JsonProperty
    private UploadConfiguration upload = new UploadConfiguration();

    public StorageConfiguration getStorage() {
        return storage;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public UploadConfiguration getUpload() {
        return upload;
    }

}
