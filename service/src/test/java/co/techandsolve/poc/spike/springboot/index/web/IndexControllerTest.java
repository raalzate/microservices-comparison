package co.techandsolve.poc.spike.springboot.index.web;

import co.techandsolve.poc.spike.springboot.index.service.IndexService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 16/08/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class IndexControllerTest {

    @MockBean
    private IndexService service;


    private WebTestClient webTestClient;

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        given(service.summary()).willReturn(Mono.just("Hola mundo Summary!"));

        webTestClient = WebTestClient.bindToController(new IndexController(service))
                .configureClient()
                .baseUrl("/")
                .build();
    }

    @Test
    public void showSummary() {
        EntityExchangeResult<String> results = webTestClient.get().uri("/index")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectBody(String.class)
                .returnResult();

        String summary = results.getResponseBody();

        Assert.assertEquals("Hola mundo Summary!", summary);
    }

}
