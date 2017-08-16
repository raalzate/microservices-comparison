package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.core.domain.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ThingsController {

    @Autowired
    private ThingRepositoryAdapter thingRepository;


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

    @PutMapping(value = "/thing")
    public Mono<ThingRepresentation> update(@RequestBody Thing thing) {
        return thingRepository.update(Mono.just(thing)).map(this::toThingRepresentation);
    }

    @DeleteMapping(value = "/thing/{id}")
    public Mono<ResourceSupport> delete(@PathVariable("id") int thingId) {
        return thingRepository.delete(thingId).flatMap(aVoid -> {
            ResourceSupport rep = new ResourceSupport();
            rep.add(linkTo(methodOn(ThingsController.class).all()).withSelfRel());
            return Mono.just(rep);
        });
    }

    private ThingRepresentation toThingRepresentation(Thing t) {
        ThingRepresentation rep = new ThingRepresentation(t);
        rep.add(linkTo(methodOn(ThingsController.class).byId(t.getId())).withSelfRel());
        return rep;
    }

}
