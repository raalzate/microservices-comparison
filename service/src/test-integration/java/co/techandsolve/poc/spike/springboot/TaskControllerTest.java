package co.techandsolve.poc.spike.springboot;

import co.techandsolve.poc.spike.core.domain.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by admin on 14/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    private WebClient webClient;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        this.webClient = WebClient.create("http://localhost:" + this.port);

        webClient.post().uri("/task").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Task(1, "IT 1")))
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();


        webClient.post().uri("/task").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Task(2, "IT 2")))
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Task.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();
    }

    @Test
    public void saveTask() {
        webClient.post().uri("/task")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Task(null, "IT")))
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
                .body(BodyInserters.fromObject(new Task(2, "IT 2")))
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
