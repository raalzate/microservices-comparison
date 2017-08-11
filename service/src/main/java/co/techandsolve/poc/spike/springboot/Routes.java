package co.techandsolve.poc.spike.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Created by admin on 11/08/2017.
 */
@Configuration
@EnableWebFlux
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> routingFunction() {
        return RouterFunctions.route(RequestPredicates.path("/"), req -> ok().build());
    }
}
