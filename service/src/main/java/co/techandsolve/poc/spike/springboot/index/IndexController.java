package co.techandsolve.poc.spike.springboot.index;

import co.techandsolve.poc.spike.core.domain.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@RestController
public class IndexController {

    @Autowired
    private WebClient webClient;


    @GetMapping(value = "/index")
    Mono<String> index() {
        return webClient.get().uri("/things")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMapMany(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToFlux(Thing.class) : Flux
                                .error(new IllegalStateException("Existe algun problema con el servicio."))
                ).count()
                .flatMap(c -> Mono.just(MessageFormat.format("Actualmente tiene {0} item(s).", c.intValue())));
    }

}
