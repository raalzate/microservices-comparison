package co.techandsolve.poc.spike.springboot.task.web;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import co.techandsolve.poc.spike.springboot.task.persistence.TaskAdapterRepository;
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
    private TaskAdapterRepository adapter;


    @Captor
    private ArgumentCaptor<Task> argTaskSave;

    private WebTestClient webTestClient;

    private Task task1 = new Task(1L, "Test 1");
    private Task task2 = new Task(2L, "Test 2");

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        given(adapter.all()).willReturn(Flux.just(task1, task2));
        given(adapter.update(argTaskSave.capture())).willReturn(Mono.just(task2));
        given(adapter.save(argTaskSave.capture())).willReturn(Mono.just(task1));
        given(adapter.byId(1L)).willReturn(Mono.empty());

        webTestClient = WebTestClient.bindToController(new TaskController(adapter))
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

        Assert.assertEquals("Test 1", argTaskSave.getValue().getName());
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

        Assert.assertEquals("Test 2", argTaskSave.getValue().getName());
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
