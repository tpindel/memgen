package pl.pks.memgen;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import com.yammer.dropwizard.config.Configuration;

public class StorageConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String accessKey;

    @NotEmpty
    @JsonProperty
    private String secretKey;

    @NotEmpty
    @JsonProperty
    private String bucket;

    @NotEmpty
    @JsonProperty
    private String endpoint;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

}
