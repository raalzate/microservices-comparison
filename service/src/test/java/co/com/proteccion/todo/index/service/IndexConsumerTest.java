package co.com.proteccion.todo.index.service;

import co.com.proteccion.todo.WebClientMock;
import co.com.proteccion.todo.task.domain.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class IndexConsumerTest {


    @InjectMocks
    private IndexConsumerService service;

    @Mock
    private WebClient client;

    @Before
    public void setup() {
    }

    @Test
    public void summary() {

        new WebClientMock.When(client.get(), "/tasks")
                .thenReturnFor(Task.class,
                        Flux.just(new Task(1L, "Task 1"), new Task(2L, "Task 2"), new Task(2L, "Task 2")));


        assert "Actualmente tiene 3 item(s).".equals(service.summary().block());
    }


}
