package co.com.proteccion.todo.task.persistence;

import co.com.proteccion.todo.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Esta interfaz extiende de la interfaz JpaRepository en la cual extiende basicamente del
 * comportamiento general de un CRUD.
 *
 * Igualmente implementa consultas personalizadas.
 *
 * Recordar que las consultas se debe hacer con respecto a la sintaxis JPQL.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 22/08/2017.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t where t.isDone = 1")
    List<Task> listByDone();

    @Query("SELECT t " +
            "FROM Task t " +
            "INNER JOIN TaskTag tt ON t.id = tt.taskId " +
            "INNER JOIN Tag tg ON tg.id = tt.tagId " +
            "WHERE tg.name = :tag")
    List<Task> listByTag(@Param("tag") String tag);

}
