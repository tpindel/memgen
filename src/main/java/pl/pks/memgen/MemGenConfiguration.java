package pl.pks.memgen;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import com.yammer.dropwizard.config.Configuration;

public class MemGenConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String placeholder;

    public String getPlaceholder() {
        return placeholder;
    }
}
