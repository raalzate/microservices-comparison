package co.techandsolve.poc.spike.springboot.task.service;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Created by admin on 24/08/2017.
 */
@Service
public class TaskManagerService implements TaskService{

    @Autowired
    private TaskAdapterRepository repository;


    @Override
    public Flux<Task> listByTag(String tag) {
        return repository.listByTag(tag);
    }

    @Override
    public Flux<Task> listByStatusDone() {
        return repository.listByStatusDone();
    }
}
