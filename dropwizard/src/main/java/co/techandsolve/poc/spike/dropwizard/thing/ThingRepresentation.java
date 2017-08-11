package co.techandsolve.poc.spike.dropwizard.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.hateoas.Representation;

public class ThingRepresentation extends Representation {

    private int id;
    private String name;

    public ThingRepresentation(Thing thing) {
        this.id = thing.getId();
        this.name = thing.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
