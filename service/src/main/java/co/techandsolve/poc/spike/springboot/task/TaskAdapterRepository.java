package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.common.ReactiveAdapter;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Created by admin on 22/08/2017.
 */
@Repository
public class TaskAdapterRepository extends ReactiveAdapter<Task> {
    private TaskRepository repository;

    @Autowired
    public TaskAdapterRepository(TaskRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Flux<Task> listByTag(String tag) {
        return Flux.fromStream(repository.listByTag(tag).stream());
    }

    public Flux<Task> listByStatusDone() {
        return Flux.fromStream(repository.listByDone().stream());
    }
}
