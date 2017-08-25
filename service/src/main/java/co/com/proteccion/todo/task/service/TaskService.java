package co.com.proteccion.todo.task.service;

import co.com.proteccion.todo.task.domain.Task;
import reactor.core.publisher.Flux;

/**
 * Interfaz que garantiza los comportamientos del servicio.
 *
 * Este contrato se encarga de garantizar el listado de tareas por tags y el listado de tareas realizadas
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
public interface TaskService {

    /* tareas por un tag */
    Flux<Task> listByTag(String tag);

    /* tareas realizadas */
    Flux<Task> listByStatusDone();
}
