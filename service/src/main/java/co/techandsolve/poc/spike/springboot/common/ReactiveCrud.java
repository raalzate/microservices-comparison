package co.techandsolve.poc.spike.springboot.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 22/08/2017.
 */
public interface ReactiveCrud<T> {
    Flux<T> all();

    Mono<T> byId(long id);

    Mono<T> save(T task);

    Mono<T> update(T task);

    Mono<Void> delete(long id);

}
