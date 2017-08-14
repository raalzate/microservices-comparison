package co.techandsolve.poc.spike.springboot.index;

import co.techandsolve.poc.spike.common.domain.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
public class IndexController {

    @Autowired
    private WebClient webClient;


    @GetMapping(value = "/")
    Flux<Thing> index() {
        return webClient.get().uri("/things")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(Thing.class));
    }

}
