package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Created by admin on 16/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskRepositoryTest {

    private TaskAdapterRepository adapter;

    @Mock
    private TaskRepository taskRepository;

    @Before
    public void setup() {
        adapter = spy(new TaskAdapterRepository(taskRepository));
    }

    @Test
    public void listWithFilterByTag() {
        Task task1 = new Task(1L, "Task 1");
        Task task2 = new Task(2L, "Task 2");

        when(taskRepository.listByTag("tag1")).thenReturn(Arrays.asList(task1, task2));

        Flux<Task> list = adapter.listByTag("tag1");

        assert list.toStream().findAny().isPresent();
        verify(taskRepository).listByTag("tag1");

    }


    @Test
    public void listWithFilterByStatusDone() {

        Task task1 = new Task(1L, "Task 1");
        Task task2 = new Task(2L, "Task 2");
        Task task3 = new Task(3L, "Task 3");

        when(taskRepository.listByDone()).thenReturn(Arrays.asList(task1, task2, task3));

        Flux<Task> list = adapter.listByStatusDone();

        assert list.toStream().count() == 3;
        verify(taskRepository).listByDone();

    }


}
