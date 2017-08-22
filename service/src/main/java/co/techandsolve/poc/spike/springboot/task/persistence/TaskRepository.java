package co.techandsolve.poc.spike.springboot.task.persistence;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t where t.isDone = 1")
    List<Task> listByDone();

}
