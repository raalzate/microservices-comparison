package co.com.proteccion.todo;

import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

/**
 * Created by admin on 28/08/2017.
 */
public class WebClientMock {

    private String uri;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    private WebClientMock(WebClient.RequestHeadersUriSpec requestHeadersUriSpec, String uri){
        this.uri = uri;
        this.requestHeadersUriSpec = requestHeadersUriSpec;
    }

    public  void getForFlux(Class entity, Flux flux, HttpStatus status) {
        when(requestHeadersUriSpec).then(invocation -> {
            WebClient.RequestHeadersUriSpec rq = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
            ClientResponse cr = Mockito.mock(ClientResponse.class);

            when(rq.uri(uri)).thenReturn(rq);
            when(rq.accept(MediaType.APPLICATION_JSON)).thenReturn(rq);

            when(cr.statusCode()).thenReturn(status);
            when(cr.bodyToFlux(entity)).thenReturn(flux);

            when(rq.exchange()).thenReturn(Mono.just(cr));
            return rq;
        });
    }

    public void getOKForFlux(Class entity, Flux flux){
        getForFlux(entity, flux, HttpStatus.OK);
    }

    public static class When {
        private WebClientMock webClientMock;
        public When(WebClient.RequestHeadersUriSpec requestHeadersUriSpec, String uri){
            webClientMock = new WebClientMock(requestHeadersUriSpec, uri);
        }

        public <T> When thenReturnFor(Class<T> entity, Flux<T> flux){
            webClientMock.getOKForFlux(entity, flux);
            return this;
        }
    }

}
