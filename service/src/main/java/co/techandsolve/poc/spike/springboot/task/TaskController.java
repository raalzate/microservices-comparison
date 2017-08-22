package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TaskController {


    private TaskRepositoryAdapter thingRepository;

    @Autowired
    public TaskController(TaskRepositoryAdapter thingRepository) {
        System.out.println(thingRepository);
        this.thingRepository = thingRepository;
    }

    @GetMapping(value = "/tasks")
    public Flux<Task> all() {
        return thingRepository.all();
    }

    @GetMapping(value = "/task/{id}")
    public Mono<Task> byId(@PathVariable("id") Long thingId) {
        return thingRepository.byId(thingId);
    }

    @PostMapping(value = "/task")
    public Mono<Task> create(@RequestBody Task task) {
        return thingRepository.save(task);
    }

    @PutMapping(value = "/task")
    public Mono<Task> update(@RequestBody Task task) {
        return thingRepository.save(task);
    }

    @DeleteMapping(value = "/task/{id}")
    public Mono<Void> delete(@PathVariable("id") Long thingId) {
        return thingRepository.delete(thingId);
    }


}
