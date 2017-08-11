package co.techandsolve.poc.spike.vertx.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import co.techandsolve.poc.spike.common.hateoas.Link;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.Optional;

public class ThingService {

    private final ThingRepository thingRepository;

    public ThingService(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    public void byId(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String thingId = routingContext.request().getParam("id");
        if (thingId == null) {
            response.setStatusCode(400).end();
        } else {
            Optional<Thing> thing = thingRepository.byId(Integer.parseInt(thingId));
            if (thing.isPresent()) {
                ThingRepresentation thingRepresentation = new ThingRepresentation(thing.get());
                thingRepresentation.addLink(Link.self(routingContext.request().absoluteURI()));
                response.putHeader("content-type", "application/json")
                        .end(Json.encode(thingRepresentation));
            } else {
                response.setStatusCode(404).end();
            }
        }
    }
}
