package co.techandsolve.poc.spike.springboot.index.web;

import co.techandsolve.poc.spike.springboot.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    private IndexService service;

    @Autowired
    public IndexController(IndexService service){
        this.service = service;
    }

    @GetMapping(value = "/index")
    Mono<String> index() {
        return service.summary();
    }

}
