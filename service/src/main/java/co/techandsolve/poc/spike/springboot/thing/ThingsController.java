package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ThingsController {

    @Autowired
    private ThingRepository thingRepository;


    @GetMapping(value = "/things")
    public Flux<ThingRepresentation> all() {
        return thingRepository.all().map(this::toThingRepresentation);
    }

    @GetMapping(value = "/thing/{id}")
    public Mono<ThingRepresentation> byId(@PathVariable("id") int thingId) {
        return thingRepository.byId(thingId).map(this::toThingRepresentation);
    }


    @PostMapping(value = "/thing")
    public Mono<ThingRepresentation> create(@RequestBody Thing thing) {
        return thingRepository.save(Mono.just(thing)).map(this::toThingRepresentation);
    }

    private ThingRepresentation toThingRepresentation(Thing t) {
        ThingRepresentation rep = new ThingRepresentation(t);
        rep.add(linkTo(methodOn(ThingsController.class).byId(t.getId())).withSelfRel());
        return rep;
    }

}
