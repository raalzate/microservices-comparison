package co.techandsolve.poc.spike.core.persistence;

import co.techandsolve.poc.spike.core.domain.Task;
import co.techandsolve.poc.spike.core.domain.TaskRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryTaskRepository implements TaskRepository {

    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Map<Integer, Task> repository = new ConcurrentHashMap<>();

    @Override
    public Mono<Task> byId(int id) {
        return Mono.justOrEmpty(repository.get(id));
    }

    @Override
    public Flux<Task> all() {
        return Flux.fromIterable(repository.values());
    }

    @Override
    public Mono<Task> save(Mono<Task> thing) {
        return thing.doOnNext(t -> {
            if (t.getId() == null) {
                t.setId(idGenerator.getAndIncrement());
            }
            repository.put(t.getId(), t);
        });
    }

    @Override
    public Mono<Task> update(Mono<Task> thing) {
        return save(thing);
    }

    @Override
    public Mono<Void> delete(int id) {
        return byId(id).doOnNext(t -> repository.remove(t.getId()))
                .thenEmpty(Mono.empty());
    }
}
