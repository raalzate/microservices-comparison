package co.techandsolve.poc.spike.dropwizard.infrastructure;

import co.techandsolve.poc.spike.dropwizard.index.IndexModule;
import co.techandsolve.poc.spike.dropwizard.index.IndexService;
import co.techandsolve.poc.spike.dropwizard.thing.ThingModule;
import co.techandsolve.poc.spike.dropwizard.thing.ThingService;
import co.techandsolve.poc.spike.dropwizard.thing.ThingsService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropwizardApplication extends Application<DropwizardServerConfiguration> {

    private GuiceBundle<DropwizardServerConfiguration> guiceBundle;

    @Override
    public void run(DropwizardServerConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(guiceBundle.getInjector().getInstance(IndexService.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(ThingsService.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(ThingService.class));

    }

    @Override
    public String getName() {
        return "Dropwizard Spike Server";
    }

    @Override
    public void initialize(Bootstrap<DropwizardServerConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<DropwizardServerConfiguration>newBuilder()
                .addModule(new IndexModule())
                .addModule(new ThingModule())
                .setConfigClass(DropwizardServerConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new Java8Bundle());

        ObjectMapper objectMapper = bootstrap.getObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
