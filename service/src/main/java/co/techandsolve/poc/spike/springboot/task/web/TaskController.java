package co.techandsolve.poc.spike.springboot.task.web;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TaskController {


    private TaskAdapterRepository repository;

    @Autowired
    public TaskController(TaskAdapterRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks")
    public Flux<Task> all() {
        return repository.all();
    }

    @GetMapping(value = "/task/{id}")
    public Mono<Task> byId(@PathVariable("id") Long thingId) {
        return repository.byId(thingId);
    }

    @PostMapping(value = "/task")
    public Mono<Task> create(@RequestBody Task task) {
        return repository.save(task);
    }

    @PutMapping(value = "/task")
    public Mono<Task> update(@RequestBody Task task) {
        return repository.save(task);
    }

    @DeleteMapping(value = "/task/{id}")
    public Mono<Void> delete(@PathVariable("id") Long thingId) {
        return repository.delete(thingId);
    }


}
