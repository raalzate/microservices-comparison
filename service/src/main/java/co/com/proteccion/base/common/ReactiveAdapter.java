package co.com.proteccion.base.common;

import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */

public abstract class ReactiveAdapter<T> implements ReactiveCrud<T> {

    private JpaRepository<T, Long> repository;

    public ReactiveAdapter(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Flux<T> all() {
        return Flux.fromStream(repository.findAll().stream());
    }

    @Override
    public Mono<T> byId(long thingId) {
        return Mono.just(repository.getOne(thingId));
    }

    @Override
    public Mono<T> save(T task) {
        return Mono.just(repository.save(task));
    }

    @Override
    public Mono<T> update(T task) {
        return Mono.just(repository.save(task));
    }

    @Override
    public Mono<Void> delete(long thingId) {
        repository.deleteById(thingId);
        return Mono.empty();
    }

}
