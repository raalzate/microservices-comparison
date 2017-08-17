package co.techandsolve.poc.spike.springboot.index;

import co.techandsolve.poc.spike.springboot.thing.ThingRepositoryAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by admin on 16/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

    @Mock
    private WebClient webClient;

    private final WebTestClient client = WebTestClient.bindToController(new IndexController())
            .configureClient()
            .baseUrl("/index")
            .build();


    @Test
    public void index() {

        client.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Hola mundo");

    }
}
