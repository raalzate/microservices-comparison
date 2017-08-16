package co.techandsolve.poc.spike.springboot.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 16/08/2017.
 */
//@Component
public class JanoJerseyConfiguration extends ResourceConfig {
    public JanoJerseyConfiguration() {
        registerEndpoints();
    }
    private void registerEndpoints() {
        packages("co.com.proteccion.jano.service.resources");
    }
}
