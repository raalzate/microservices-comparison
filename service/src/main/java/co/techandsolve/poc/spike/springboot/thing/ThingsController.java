package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/things", produces = "application/json")
public class ThingsController {

    private final ThingRepository thingRepository;

    @Autowired
    public ThingsController(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Flux<Thing> all() {
        return thingRepository.all();
    }

}
