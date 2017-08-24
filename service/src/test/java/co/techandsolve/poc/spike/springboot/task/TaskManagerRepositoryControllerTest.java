package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;


/**
 * Created by admin on 16/08/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class TaskManagerRepositoryControllerTest {

    @MockBean
    private TaskAdapterRepository repository;

    @Captor
    private ArgumentCaptor<Mono<Task>> argTask;

    private WebTestClient webTestClient;
    private Task task1 = new Task(1L, "Test 1");
    private Task task2 = new Task(2L, "Test 2");

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        given(repository.listByTag("tag1")).willReturn(Flux.just(task1, task2));
        given(repository.listByStatusDone()).willReturn(Flux.just(task1));

        webTestClient = WebTestClient.bindToController(new TaskManagerController(repository))
                .configureClient()
                .baseUrl("/")
                .build();
    }

    @Test
    public void listByTag() {
        EntityExchangeResult<List<Task>> results = webTestClient.get().uri("/tasks/tag/tag1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectBodyList(Task.class)
                .hasSize(2)
                .returnResult();

        List<Task> list = results.getResponseBody();

        Assert.assertEquals("Test 1", list.get(0).getName());
        Assert.assertEquals("Test 2", list.get(1).getName());
    }

    @Test
    public void listByStatusDone() {
        EntityExchangeResult<List<Task>> results = webTestClient.get().uri("/tasks/done")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectBodyList(Task.class)
                .hasSize(1)
                .returnResult();

        List<Task> list = results.getResponseBody();

        Assert.assertEquals("Test 1", list.get(0).getName());
    }
}
