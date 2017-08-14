package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.core.domain.Thing;
import org.springframework.hateoas.ResourceSupport;

public class ThingRepresentation extends ResourceSupport {

    private final Thing thing;

    public ThingRepresentation(Thing thing) {
        this.thing = thing;
    }

    public String getName() {
        return thing.getName();
    }

}
