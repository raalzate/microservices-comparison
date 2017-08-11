package co.techandsolve.poc.spike.dropwizard.index;

import co.techandsolve.poc.spike.dropwizard.infrastructure.DropwizardServerConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class IndexModule extends AbstractModule {

    private Client client;

    @Override
    protected void configure() {

    }

    @Provides
    public Client createHttpClient(Environment environment, DropwizardServerConfiguration configuration) {
        if (client == null) {
            client = new JerseyClientBuilder(environment)
                    .using(configuration.getJerseyClientConfiguration())
                    .build("internal client");
        }
        return client;
    }
}
