package co.techandsolve.poc.spike.springboot.web;

import co.techandsolve.poc.spike.springboot.AccessTokenUtils;
import co.techandsolve.poc.spike.springboot.task.domine.Tag;
import co.techandsolve.poc.spike.springboot.task.domine.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Created by admin on 14/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:truncate.sql")
public class TaskManagerControllerTest {

    private WebClient webClient;

    @LocalServerPort
    private int port;

    private static String token;
    static {
        token = AccessTokenUtils.getToken();
    }

    @Before
    public void setup() {

        this.webClient = WebClient.create("http://localhost:" + this.port);

        Task task1 = new Task(1L, "IT 1");
        Task task2 = new Task(2L, "IT 2");

        task1.setTags(Arrays.asList(new Tag("tag1"), new Tag("tag2")));
        task1.setDone(true);

        webClient.post().uri("/task").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(task1))
                .header("Authorization", token)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();


        webClient.post().uri("/task").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(task2))
                .header("Authorization", token)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();

    }


    @Test
    public void listByTag() {

        webClient.get().uri("/tasks/tag/tag1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .exchange()
                .flatMapMany(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToFlux(Task.class) :
                                Flux.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail())
                .toStream().forEach(task -> Assert.assertEquals("IT 1", task.getName()));

    }


    @Test
    public void listByStatusDone() {

        webClient.get().uri("/tasks/done")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .exchange()
                .flatMapMany(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToFlux(Task.class) :
                                Flux.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail())
                .toStream().forEach(task -> Assert.assertEquals(task.getName(), "IT 1"));

    }


}
