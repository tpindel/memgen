package pl.pks.memgen;

import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.db.StubStorageService;
import pl.pks.memgen.health.PlaceholderHealthCheck;
import pl.pks.memgen.resources.PlaceholderResource;
import com.google.common.cache.CacheBuilderSpec;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class MemGenService extends Service<MemGenConfiguration> {

    private MemGenService() {
        super("MemGen");
        addBundle(new ViewBundle());
        addBundle(new AssetsBundle(AssetsBundle.DEFAULT_PATH, CacheBuilderSpec.disableCaching()));
    }

    public static void main(String[] args) throws Exception {
        new MemGenService().run(args);
    }

    @Override
    protected void initialize(MemGenConfiguration conf, Environment env) throws Exception {
        StorageService storageService = new StubStorageService();
        env.addResource(new PlaceholderResource(storageService));
        env.addHealthCheck(new PlaceholderHealthCheck());
    }

}
