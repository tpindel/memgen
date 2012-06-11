package pl.pks.memgen;

import pl.pks.memgen.resources.PlaceholderResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

public class MemGenService extends Service<MemGenConfiguration> {

	private MemGenService() {
		super("MemGen");
	}

	public static void main(String[] args) throws Exception {
		new MemGenService().run(args);
	}

	@Override
	protected void initialize(MemGenConfiguration conf, Environment env)
			throws Exception {
	    String memGenName = conf.getPlaceholder();
	    env.addResource(new PlaceholderResource(memGenName));

	}
}
