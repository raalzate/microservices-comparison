package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.core.domain.Task;
import co.techandsolve.poc.spike.core.domain.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TaskManagerController {


    private TaskManager taskManager;

    @Autowired
    public TaskManagerController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @GetMapping(value = "/tasks/tag/{tag}")
    public Flux<Task> listByTag(@PathVariable("tag") String tag) {
        return taskManager.listByTag(tag);
    }


    @GetMapping(value = "/tasks/done")
    public Flux<Task> listByStatusDone() {
        return taskManager.listByStatusDone();
    }
}
