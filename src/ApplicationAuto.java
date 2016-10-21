import org.glassfish.jersey.server.ResourceConfig;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;

public class ApplicationAuto extends ResourceConfig {
	public void MyApplication() {
		packages("com.ricm.websiteproject.services");
		register(new InstrumentedResourceMethodApplicationListener(
				new MetricRegistry()));
	}
}