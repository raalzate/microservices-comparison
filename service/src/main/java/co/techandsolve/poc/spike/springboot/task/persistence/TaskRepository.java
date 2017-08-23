package co.techandsolve.poc.spike.springboot.task.persistence;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
