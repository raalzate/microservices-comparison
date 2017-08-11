package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingHandler;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.http.MediaType.APPLICATION_JSON;


/**
 * Created by admin on 11/08/2017.
 */
@Service
public class ThingHandlerImpl implements ThingHandler {


    @Override
    public Mono<ServerResponse> getThingFromRepository(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> saveThingToRepository(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> getAllThingsFromRepository(ServerRequest request) {
        ThingRepository thingRepository = new ThingRepositoryAdapter();
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(thingRepository.all(), Thing.class);
    }
}
