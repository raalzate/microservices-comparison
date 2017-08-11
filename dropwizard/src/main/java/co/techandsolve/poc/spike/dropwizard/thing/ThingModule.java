package co.techandsolve.poc.spike.dropwizard.thing;

import co.techandsolve.poc.spike.common.domain.ThingRepository;
import co.techandsolve.poc.spike.common.persistence.InMemoryThingRepository;
import com.google.inject.AbstractModule;

public class ThingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ThingRepository.class).to(InMemoryThingRepository.class);
    }
}
