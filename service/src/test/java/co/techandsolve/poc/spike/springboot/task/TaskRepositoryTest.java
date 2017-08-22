package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.springboot.task.domine.Tag;
import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by admin on 16/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskRepositoryTest {

    @InjectMocks
    private TaskRepositoryAdapter adapter;

    @Mock
    private TaskRepository taskRepository;


    @Test
    public void listWithFilterByTag() {
        Task task1 = new Task(1L, "Task 1");
        task1.setTags(Arrays.asList(new Tag("tag1"), new Tag("tag2")));

        Task task2 = new Task(2L, "Task 2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        Flux<Task> list = adapter.listByTag("tag1");

        assert list.toStream().findAny().isPresent();
        verify(taskRepository).findAll();

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
