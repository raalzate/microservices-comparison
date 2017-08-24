package co.techandsolve.poc.spike.springboot.index.service;

import reactor.core.publisher.Mono;

/**
 * Created by admin on 24/08/2017.
 */
public interface IndexService {

    Mono<String> summary();
}
