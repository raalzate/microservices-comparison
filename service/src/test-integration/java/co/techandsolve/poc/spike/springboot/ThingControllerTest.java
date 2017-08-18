package co.techandsolve.poc.spike.springboot;

import co.techandsolve.poc.spike.core.domain.Thing;
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
public class ThingControllerTest {

    private WebClient webClient;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        this.webClient = WebClient.create("http://localhost:" + this.port);
    }

    @Test
    public void addOneNew() {
        webClient.post().uri("/thing")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(0, "IT")))
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Thing.class) :
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
    public void addAndList() {

        webClient.post().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(1, "IT 1")))
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Thing.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();


        webClient.post().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(2, "IT 2")))
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Thing.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();

        webClient.get().uri("/things")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMapMany(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToFlux(Thing.class) :
                                Flux.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail())
                .count().flatMap(aLong -> {
            assert aLong.intValue() >= 3;
            return Mono.empty();
        }).block();

    }

    @Test
    public void addAndDelete() {
        webClient.post().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(3, "IT 3")))
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Thing.class) :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail()).block();

        webClient.delete().uri("/thing/1").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ? Mono.empty() :
                                Mono.error(new IllegalStateException())
                ).doOnError(throwable -> Assert.fail())
                .block();

    }

    @Test
    public void updateItem() {

        webClient.put().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(3, "IT 3")))
                .exchange()
                .flatMap(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToMono(Thing.class) :
                                Mono.error(new IllegalStateException())
                )
                .flatMap(thing -> {
                    Assert.assertEquals("IT 3", thing.getName());
                    return Mono.empty();
                })
                .doOnError(throwable -> Assert.fail(throwable.getMessage()))
                .block();


    }
}
