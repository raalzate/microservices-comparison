package co.techandsolve.poc.spike.vertx.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import co.techandsolve.poc.spike.common.hateoas.Link;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

import static io.vertx.core.json.Json.encode;
import static java.util.stream.Collectors.toList;

public class ThingsService {

    private final ThingRepository thingRepository;


    public ThingsService(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    public void all(RoutingContext routingContext) {
        List<Thing> all = thingRepository.all();
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json")
                .putHeader("total-count", String.valueOf(all.size()))
                .end(encode(all.stream().map(t -> {
                    ThingRepresentation thingRepresentation = new ThingRepresentation(t);
                    thingRepresentation.addLink(Link.self(routingContext.request().absoluteURI() + "/" + t.getId()));
                    return thingRepresentation;
                }).collect(toList())));
    }

    public void createThing(RoutingContext routingContext) {
        Thing thing = Json.decodeValue(routingContext.getBodyAsString(), Thing.class);
        thingRepository.save(thing);
        HttpServerResponse response = routingContext.response();
        response.putHeader("Location", routingContext.request().absoluteURI() + "/" + thing.getId())
                .setStatusCode(201)
                .end();
    }
}
