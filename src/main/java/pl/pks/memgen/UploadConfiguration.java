package pl.pks.memgen;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.codehaus.jackson.annotate.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class UploadConfiguration extends Configuration {

    @Min(1)
    @Max(20 * 1024 * 1024)
    @JsonProperty
    private int maxSize = 5 * 1024 * 1024;

    public int getMaxSize() {
        return maxSize;
    }

}
