package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/things", produces = "application/json")
public class ThingsController {

    private final ThingRepository thingRepository;

    @Inject
    public ThingsController(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ThingRepresentation>> all() {
        List<Thing> things = thingRepository.all();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-count", String.valueOf(things.size()));
        return new ResponseEntity<>(things.stream().map(this::toThingRepresentation).collect(toList()), responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ThingRepresentation> byId(@PathVariable("id") String thingId) {
        Optional<Thing> thing = thingRepository.byId(Integer.parseInt(thingId));
        return thing.map(t -> {
            ThingRepresentation rep = toThingRepresentation(t);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ThingRepresentation toThingRepresentation(Thing t) {
        String thingId = String.valueOf(t.getId());
        ThingRepresentation rep = new ThingRepresentation(t);
        rep.add(linkTo(methodOn(ThingsController.class).byId(thingId)).withSelfRel());
        return rep;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ThingRepresentation> createThing(@RequestBody Thing thing) {
        thingRepository.save(thing);
        HttpHeaders responseHeaders = new HttpHeaders();
        Link link = linkTo(methodOn(ThingsController.class).byId(String.valueOf(thing.getId()))).withSelfRel();
        responseHeaders.set("Location", link.getHref());
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
