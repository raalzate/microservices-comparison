package co.techandsolve.poc.spike.common.domain;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by admin on 11/08/2017.
 */
public interface ThingHandler {
     Mono<ServerResponse> getThingFromRepository(ServerRequest request);

     Mono<ServerResponse> saveThingToRepository(ServerRequest request);

     Mono<ServerResponse> getAllThingsFromRepository(ServerRequest request);
}
