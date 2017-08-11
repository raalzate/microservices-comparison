package co.techandsolve.poc.spike.common.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ThingRepository {

    Mono<Thing> byId(int id);

    Flux<Thing> all();

    Mono<Void> save(Mono<Thing> thing);

    Mono<Void> update(Mono<Thing> thing);

    Mono<Void> delete(Mono<Thing> thing);
}
