package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.core.domain.Task;
import co.techandsolve.poc.spike.core.domain.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class TaskController {


    private TaskRepository thingRepository;

    @Autowired
    public TaskController(TaskRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @GetMapping(value = "/tasks")
    public Flux<Task> all() {
        return thingRepository.all();
    }

    @GetMapping(value = "/task/{id}")
    public Mono<Task> byId(@PathVariable("id") int thingId) {
        return thingRepository.byId(thingId);
    }

    @PostMapping(value = "/task")
    public Mono<Task> create(@RequestBody Task task) {
        return thingRepository.save(Mono.just(task));
    }

    @PutMapping(value = "/task")
    public Mono<Task> update(@RequestBody Task task) {
        return thingRepository.update(Mono.just(task));
    }

    @DeleteMapping(value = "/task/{id}")
    public Mono<Void> delete(@PathVariable("id") int thingId) {
        return thingRepository.delete(thingId);
    }


}
