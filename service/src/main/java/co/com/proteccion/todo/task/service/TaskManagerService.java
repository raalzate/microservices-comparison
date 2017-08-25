package co.com.proteccion.todo.task.service;

import co.com.proteccion.todo.task.domain.Task;
import co.com.proteccion.todo.task.persistence.TaskAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Esta clase es la encargada de realizar la gestion de las tareas.
 * Implementa TaskService para usar la segregacion de interfaz.
 *
 * Su dependencia directa es el reposositorio, esta dependencia es injectada y no se usa desde el constructor,
 * en la practica se debe usar la interfaz con lo usa TaskManagerController.
 *
 * Nota: Se debe usar el estereotipo @Service para indentificar la capa de servicio o negocio
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@Service
public class TaskManagerService implements TaskService {

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
