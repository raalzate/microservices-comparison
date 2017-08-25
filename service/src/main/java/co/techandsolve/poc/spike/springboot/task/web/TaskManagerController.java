package co.techandsolve.poc.spike.springboot.task.web;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Esta clase se encarga de realizar el control un poco mas especifico.
 * Depende de una capa de servicio que basicamente es la encargada de procesar la logina necesaria para
 * este control.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@RestController
public class TaskManagerController {


    private TaskService service;

    /**
     * Se inyecta utilizando el constructor para que su dependencia sea fuerte y ademar para apoyar a
     * sistema de testing
     *
     * @param service
     */
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
