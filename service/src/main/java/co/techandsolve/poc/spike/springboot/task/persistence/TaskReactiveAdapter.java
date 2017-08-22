package co.techandsolve.poc.spike.springboot.task.persistence;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by admin on 22/08/2017.
 */
public interface TaskReactiveAdapter {
    Flux<Task> all();

    Mono<Task> byId(long thingId);

    Mono<Task> save(Task task);

    Mono<Task> update(Task task);

    Mono<Void> delete(long thingId);

    Flux<Task> listByTag(String tag);

    Flux<Task> listByStatusDone();
}
