package pl.pks.memgen.health;

import com.yammer.metrics.core.HealthCheck;

public class PlaceholderHealthCheck extends HealthCheck {

    public PlaceholderHealthCheck() {
        super("placeholder");
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

}
