package co.techandsolve.poc.spike.core.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TaskRepository {

    Mono<Task> byId(int id);

    Flux<Task> all();

    Mono<Task> save(Mono<Task> thing);

    Mono<Task> update(Mono<Task> thing);

    Mono<Void> delete(int id);
}
