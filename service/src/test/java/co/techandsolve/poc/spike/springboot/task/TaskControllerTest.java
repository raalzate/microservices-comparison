package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.core.domain.Task;
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
import org.springframework.web.reactive.function.BodyInserters;
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
public class TaskControllerTest {

    @MockBean
    private TaskRepositoryAdapter repository;

    @Captor
    private ArgumentCaptor<Mono<Task>> argTask;

    private WebTestClient webTestClient;
    private Task task1 = new Task(1, "Test 1");
    private Task task2 = new Task(2, "Test 2");

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        given(repository.all()).willReturn(Flux.just(task1, task2));
        given(repository.update(argTask.capture())).willReturn(Mono.just(task2));
        given(repository.save(argTask.capture())).willReturn(Mono.just(task1));
        given(repository.delete(1)).willReturn(Mono.empty());

        webTestClient = WebTestClient.bindToController(new TaskController(repository))
                .configureClient()
                .baseUrl("/")
                .build();
    }

    @Test
    public void create() {
        webTestClient.post().uri("/task").accept(APPLICATION_JSON)
                .body(BodyInserters.fromObject(task1))
                .exchange()
                .expectStatus().is2xxSuccessful();

        argTask.getValue().subscribe(task -> Assert.assertEquals("Test 1", task.getName()));
    }

    @Test
    public void delete() {
        webTestClient.delete().uri("/task/{id}", 1).accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();

        webTestClient.delete().uri("/task").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void update() {
        webTestClient.put().uri("/task").accept(APPLICATION_JSON)
                .body(BodyInserters.fromObject(task2))
                .exchange()
                .expectStatus().is2xxSuccessful();

        argTask.getValue().subscribe(task -> Assert.assertEquals("Test 2", task.getName()));
    }

    @Test
    public void list() {
        EntityExchangeResult<List<Task>> results = webTestClient.get().uri("/tasks").accept(APPLICATION_JSON)
                .exchange()
                .expectBodyList(Task.class)
                .hasSize(2)
                .returnResult();

        List<Task> list = results.getResponseBody();

        Assert.assertEquals("Test 1", list.get(0).getName());
        Assert.assertEquals("Test 2", list.get(1).getName());
    }
}
