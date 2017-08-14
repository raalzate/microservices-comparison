package co.techandsolve.poc.spike.core.persistence;

import co.techandsolve.poc.spike.core.domain.Thing;
import co.techandsolve.poc.spike.core.domain.ThingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryThingRepository implements ThingRepository {

    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Map<Integer, Thing> repository = new ConcurrentHashMap<>();

    @Override
    public Mono<Thing> byId(int id) {
        return Mono.justOrEmpty(repository.get(id));
    }

    @Override
    public Flux<Thing> all() {
        return Flux.fromIterable(repository.values());
    }

    @Override
    public Mono<Thing> save(Mono<Thing> thing) {
        return thing.doOnNext(t -> {
            if (t.getId() == null) {
                t.setId(idGenerator.getAndIncrement());
            }
            repository.put(t.getId(), t);
        });
    }

    @Override
    public Mono<Thing> update(Mono<Thing> thing) {
        return save(thing);
    }

    @Override
    public Mono<Void> delete(Mono<Thing> thing) {
        return thing.doOnNext(t -> repository.remove(t.getId()))
                .thenEmpty(Mono.empty());
    }
}
