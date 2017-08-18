package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.core.domain.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by admin on 16/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskRepositoryAdapterTest {

    @Spy
    private TaskRepositoryAdapter taskRepositoryAdapter;


    @Test
    public void listWithFilterByTag() {

        Task task1 = new Task(1, "Task 1");
        task1.setTags(Arrays.asList("tag1", "tag2"));

        Task task2 = new Task(2, "Task 2");

        when(taskRepositoryAdapter.all()).thenReturn(Flux.just(task1, task2));

        Flux<Task> list = taskRepositoryAdapter.listByTag("tag1");

        list.hasElements().subscribe(has -> Assert.assertEquals(has, true));
        list.subscribe(task -> Assert.assertEquals(task.getName(), "Task 1"));
        verify(taskRepositoryAdapter).all();

    }


    @Test
    public void listWithFilterByStatusDone() {

        Task task1 = new Task(1, "Task 1");
        task1.setDone(true);
        Task task2 = new Task(2, "Task 2");
        Task task3 = new Task(3, "Task 3");

        when(taskRepositoryAdapter.all()).thenReturn(Flux.just(task1, task2, task3));

        Flux<Task> list = taskRepositoryAdapter.listByStatusDone();

        list.hasElements().subscribe(has -> Assert.assertEquals(true, has));
        list.count().subscribe(count -> Assert.assertEquals(count.intValue(), 1));
        verify(taskRepositoryAdapter).all();

    }


}
