package co.com.proteccion.todo.index.service;

import co.com.proteccion.todo.task.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

/**
 * Esta clase es la encargada de realizar el consumo debido de un servicio web
 *
 * Su dependencia directa es un cliente web para realizar el consumo del servicio,
 * la clase WebClient es inyectada desde la clase Main y es parametrizada por el archivo application.yml
 *
 * Nota: Se debe usar el estereotipo @Service para indentificar la capa de servicio o negocio.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@Service
public class IndexConsumerService implements IndexService {

    @Autowired
    private WebClient webClient;

    /**
     * Logica necesaria para tratar el servicio, se recomienda que la logica sea manajada con
     * programacion funcional.
     *
     * @return
     */
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
