package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.core.domain.Thing;
import org.springframework.hateoas.ResourceSupport;

public class ThingRepresentation extends ResourceSupport {

    private final String name;

    public ThingRepresentation(Thing thing) {
        this.name = thing.getName();
    }

    public String getName() {
        return name;
    }
}
