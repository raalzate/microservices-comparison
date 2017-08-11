package co.techandsolve.poc.spike.common.persistence;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
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
    public Mono<Void> save(Mono<Thing> thing) {
        Mono<Thing> pMono = thing.doOnNext(t -> {
            if (t.getId() == null) {
                t.setId(idGenerator.getAndIncrement());
            }
            repository.put(t.getId(), t);
        });
        return pMono.thenEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> update(Mono<Thing> thing) {
        return save(thing);
    }

    @Override
    public Mono<Void> delete(Mono<Thing> thing) {
        return thing.doOnNext(t -> repository.remove(t.getId()))
                .thenEmpty(Mono.empty());
    }
}
