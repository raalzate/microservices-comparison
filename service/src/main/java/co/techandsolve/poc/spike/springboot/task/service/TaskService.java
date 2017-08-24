package co.techandsolve.poc.spike.springboot.task.service;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import reactor.core.publisher.Flux;

/**
 * Created by admin on 24/08/2017.
 */
public interface TaskService  {

    Flux<Task> listByTag(String tag);

    Flux<Task> listByStatusDone();
}
