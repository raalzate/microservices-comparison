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
import java.util.function.Function;

/**
 * Created by admin on 14/08/2017.
 */

@RunWith(SpringRunner.class)
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:truncate.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

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
        webClient.post().uri("/task").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Task(1L, "IT 1")))
                .header("Authorization", token)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();


        webClient.post().uri("/task").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Task(1L, "IT 2")))
                .header("Authorization", token)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();
    }

    @Test
    public void saveTask() {
        Task task = new Task(null, "IT");
        task.setTags(Arrays.asList(new Tag("tag1"), new Tag("tag2")));
        webClient.post().uri("/task")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(task))
                .header("Authorization", token)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                )
                .flatMap(thing -> {
                    Assert.assertEquals("IT", thing.getName());
                    return Mono.empty();
                })
                .doOnError(throwable -> Assert.fail())
                .block();
    }

    @Test
    public void listOfAll() {
        webClient.get().uri("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .exchange()
                .flatMapMany(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToFlux(Task.class) :
                                Flux.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail())
                .count().flatMap(aLong -> {
            Assert.assertEquals(2, aLong.intValue());
            return Mono.empty();
        }).block();

    }

    @Test
    public void deleteOne() {

        webClient.delete().uri("/task/1").accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ? Mono.empty() :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail())
                .block();

    }

    @Test
    public void updateOne() {

        webClient.put().uri("/task").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Task(1L, "IT 2")))
                .header("Authorization", token)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                )
                .flatMap(thing -> {
                    Assert.assertEquals("IT 2", thing.getName());
                    return Mono.empty();
                })
                .doOnError(throwable -> Assert.fail(throwable.getMessage()))
                .block();

    }
}
