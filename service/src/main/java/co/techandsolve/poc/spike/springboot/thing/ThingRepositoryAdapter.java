package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.core.persistence.InMemoryThingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ThingRepositoryAdapter extends InMemoryThingRepository {

    public Mono<Void> delete(int thingId) {
        return super.delete(byId(thingId));
    }
}
