package co.techandsolve.poc.spike.springboot.task.web;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TaskManagerController {


    private TaskService service;

    @Autowired
    public TaskManagerController(TaskService service) {
        this.service = service;
    }

    @GetMapping(value = "/tasks/tag/{tag}")
    public Flux<Task> listByTag(@PathVariable("tag") String tag) {
        return service.listByTag(tag);
    }

    @GetMapping(value = "/tasks/done")
    public Flux<Task> listByStatusDone() {
        return service.listByStatusDone();
    }
}
