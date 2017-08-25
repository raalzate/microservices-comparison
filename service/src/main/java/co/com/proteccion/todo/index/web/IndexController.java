package co.com.proteccion.todo.index.web;

import co.com.proteccion.todo.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Esta clase se encarga de realizar el control de un servicio especifico.
 * Depende de una capa de servicio que basicamente es la encargada de procesar la logina necesaria para
 * este control.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@RestController
public class IndexController {

    private IndexService service;

    @Autowired
    public IndexController(IndexService service) {
        this.service = service;
    }

    @GetMapping(value = "/index")
    Mono<String> index() {
        return service.summary();
    }

}
