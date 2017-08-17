package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.core.domain.Thing;
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
public class ThingsControllerTest {

    @MockBean
    private ThingRepositoryAdapter repository;

    @Captor
    private ArgumentCaptor<Mono<Thing>> argThing;

    private WebTestClient webTestClient;
    private Thing thing1 = new Thing(1, "Test 1");
    private Thing thing2 = new Thing(2, "Test 2");

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        given(repository.all()).willReturn(Flux.just(thing1, thing2));
        given(repository.update(argThing.capture())).willReturn(Mono.just(thing2));
        given(repository.save(argThing.capture())).willReturn(Mono.just(thing1));
        given(repository.delete(1)).willReturn(Mono.empty());

        webTestClient = WebTestClient.bindToController(new ThingsController(repository))
                .configureClient()
                .baseUrl("/")
                .build();
    }

    @Test
    public void create() {
        webTestClient.post().uri("/thing").accept(APPLICATION_JSON)
                .body(BodyInserters.fromObject(thing1))
                .exchange()
                .expectStatus().is2xxSuccessful();

        Assert.assertEquals("Test 1", argThing.getValue().block().getName());
    }

    @Test
    public void delete() {
        webTestClient.delete().uri("/thing/{id}", 1).accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();

        webTestClient.delete().uri("/thing").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void update() {
        webTestClient.put().uri("/thing").accept(APPLICATION_JSON)
                .body(BodyInserters.fromObject(thing2))
                .exchange()
                .expectStatus().is2xxSuccessful();

        Assert.assertEquals("Test 2", argThing.getValue().block().getName());
    }

    @Test
    public void list() {
        EntityExchangeResult<List<Thing>> results = webTestClient.get().uri("/things").accept(APPLICATION_JSON)
                .exchange()
                .expectBodyList(Thing.class)
                .hasSize(2)
                .returnResult();

        List<Thing> list = results.getResponseBody();

        Assert.assertEquals("Test 1", list.get(0).getName());
        Assert.assertEquals("Test 2", list.get(1).getName());
    }
}
