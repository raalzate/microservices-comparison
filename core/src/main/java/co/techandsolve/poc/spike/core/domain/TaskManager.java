package co.techandsolve.poc.spike.core.domain;

import reactor.core.publisher.Flux;

/**
 * Created by admin on 18/08/2017.
 */
public interface TaskManager {
    Flux<Task> listByTag(String tag);

    Flux<Task> listByStatusDone();
}
