package co.techandsolve.poc.spike.vertx.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.hateoas.Representation;

public class ThingRepresentation extends Representation {
    private final String name;
    private final int id;

    public ThingRepresentation(Thing thing) {
        this.name = thing.getName();
        this.id = thing.getId();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
