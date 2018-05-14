package edu.immune.dropwizard.core;

import com.bendb.dropwizard.redis.JedisBundle;
import com.bendb.dropwizard.redis.JedisFactory;

import edu.immune.dropwizard.conf.RestConfiguration;
import edu.immune.dropwizard.health.TemplateHealthCheck;
import edu.immune.dropwizard.resources.RestResource;
import edu.immune.dropwizard.resources.ViewResource;
import edu.immune.dropwizard.util.UserRecordPopulator;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * Application Initializer
 * @author lalit
 *
 */
public class RestApplication extends Application<RestConfiguration> {

    public static void main(final String[] args) throws Exception {
        new RestApplication().run(args);
    }

    @Override
    public String getName() {
        return "rest service";
    }

    @Override
    public void initialize(final Bootstrap<RestConfiguration> bootstrap) {
    	bootstrap.addBundle(new ViewBundle<RestConfiguration>());
    	bootstrap.addBundle(new JedisBundle<RestConfiguration>() {
            @Override
            public JedisFactory getJedisFactory(RestConfiguration configuration) {
                return configuration.getJedisFactory();
            }
        });
    }

    @Override
    public void run(final RestConfiguration configuration,
                    final Environment environment) {
    	UserRecordPopulator.populate();
    	
    	final RestResource resource = new RestResource();
    	final ViewResource vuResource = new ViewResource();
    	final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
    	environment.healthChecks().register("template", healthCheck);
    	environment.jersey().register(resource);
    	environment.jersey().register(vuResource);
    }

}
