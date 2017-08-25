package co.com.proteccion.todo.task.web;

import co.com.proteccion.todo.task.domain.Task;
import co.com.proteccion.todo.task.persistence.TaskAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Esta clase se encarga de realizar el control basico de las tareas.
 * Depende del repositorio para realizar la persistencia y consulta de la informacion de forma directa, no interactua
 * con ninguna capa de negocio.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@RestController
public class TaskController {


    private TaskAdapterRepository repository;

    /**
     * Se inyecta utilizando el constructor para que su dependencia sea fuerte y ademar para apoyar a
     * sistema de testing
     *
     * @param repository
     */
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
