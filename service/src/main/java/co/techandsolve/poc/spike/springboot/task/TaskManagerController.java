package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskReactiveAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TaskManagerController {


    private TaskRepositoryAdapter thingRepository;

    @Autowired
    public TaskManagerController(TaskRepositoryAdapter thingRepository) {
        this.thingRepository = thingRepository;
    }

    @GetMapping(value = "/tasks/tag/{tag}")
    public Flux<Task> listByTag(@PathVariable("tag") String tag) {
        return thingRepository.listByTag(tag);
    }

    @GetMapping(value = "/tasks/done")
    public Flux<Task> listByStatusDone() {
        return thingRepository.listByStatusDone();
    }
}
