package co.techandsolve.poc.spike.springboot.index.service;

import co.techandsolve.poc.spike.springboot.task.domine.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

/**
 * Created by admin on 24/08/2017.
 */
@Service
public class IndexConsumerService implements IndexService {

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<String> summary() {
        return webClient.get().uri("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMapMany(response ->
                        response.statusCode().value() == 200 ?
                                response.bodyToFlux(Task.class) : Flux
                                .error(new IllegalStateException("Existe algun problema con el servicio."))
                ).count()
                .flatMap(c -> Mono.just(MessageFormat.format("Actualmente tiene {0} item(s).", c.intValue())));
    }
}
