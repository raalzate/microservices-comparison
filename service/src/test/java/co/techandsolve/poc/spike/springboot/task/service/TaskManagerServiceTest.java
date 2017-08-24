package co.techandsolve.poc.spike.springboot.task.service;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskAdapterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by admin on 24/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskManagerServiceTest {

    @InjectMocks
    private TaskManagerService service;

    @Mock
    private TaskAdapterRepository repository;

    @Test
    public void listByTag() {
        Task task1 = new Task(1L, "Task 1");
        when(repository.listByTag("tag1")).thenReturn(Flux.just(task1));

        Flux<Task> list = service.listByTag("tag1");

        assert list.toStream().findAny().isPresent();
        verify(repository).listByTag("tag1");
    }

    @Test
    public void listByStatusDone() {

        Task task1 = new Task(1L, "Task 1");
        Task task2 = new Task(1L, "Task 2");
        when(repository.listByStatusDone()).thenReturn(Flux.just(task1, task2));

        Flux<Task> list = service.listByStatusDone();

        assert list.toStream().findAny().isPresent();
        verify(repository).listByStatusDone();
    }

}
