package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskReactiveAdapter;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by admin on 22/08/2017.
 */
@Repository
public class TaskRepositoryAdapter implements TaskReactiveAdapter {

    private TaskRepository thingRepository;

    @Autowired
    public TaskRepositoryAdapter(TaskRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @Override
    public Flux<Task> all() {
        return Flux.fromStream(thingRepository.findAll().stream());
    }

    @Override
    public Mono<Task> byId(long thingId) {
        return Mono.just(thingRepository.getOne(thingId));
    }

    @Override
    public Mono<Task> save(Task task) {
        return Mono.just(thingRepository.save(task));
    }

    @Override
    public Mono<Task> update(Task task) {
        return Mono.just(thingRepository.save(task));
    }

    @Override
    public Mono<Void> delete(long thingId) {
        thingRepository.deleteById(thingId);
        return Mono.empty();
    }

    @Override
    public Flux<Task> listByTag(String tag) {
        return Flux.fromStream(thingRepository.findAll().stream());
    }

    @Override
    public Flux<Task> listByStatusDone() {
        return Flux.fromStream(thingRepository.listByDone().stream());
    }
}
