package co.techandsolve.poc.spike.springboot;

import co.techandsolve.poc.spike.core.domain.Thing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

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
        Thing response = webClient.post().uri("/thing")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(0, "IT")))
                .retrieve()
                .bodyToMono(Thing.class)
                .block();

        assert "IT".equals(response.getName());
    }

    @Test
    public void addAndList() {

        webClient.post().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(1, "IT 1")))
                .exchange()
                .block();

        webClient.post().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(2, "IT 2")))
                .exchange()
                .block();

        long counts = webClient.get().uri("/things")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Thing.class)
                .toStream()
                .count();

        assert counts >= 2;
    }

    @Test
    public void addAndDelete(){
        webClient.post().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(3, "IT 3")))
                .exchange()
                .block();

        ClientResponse res = webClient.delete().uri("/thing/1").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .block();

        assert res.statusCode().value() == 200;
    }

    @Test
    public void updateItem(){
        ClientResponse res = webClient.put().uri("/thing").accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new Thing(3, "IT 3")))
                .exchange()
                .block();

        Thing  thing = res.bodyToMono(Thing.class).block();

        assert res.statusCode().value() == 200;
        assert "IT 3".equals(thing.getName());
    }
}
