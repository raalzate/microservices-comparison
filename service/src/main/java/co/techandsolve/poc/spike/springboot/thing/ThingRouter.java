package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.common.domain.ThingHandler;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by admin on 11/08/2017.
 */
public interface ThingRouter {
    static RouterFunction<ServerResponse> routes(ThingHandler handler) {
        return nest(path("/thing"),
                nest(accept(APPLICATION_JSON),
                        route(GET("/{id}"), handler::getThingFromRepository)
                                .andRoute(method(HttpMethod.GET), handler::getAllThingsFromRepository)
                ).andRoute(POST("/")
                        .and(contentType(APPLICATION_JSON)), handler::saveThingToRepository));
    }
}
