package co.techandsolve.poc.spike.common.hateoas;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Representation {

    private final List<Link> links = new ArrayList<>(1);

    public void addLink(Link link) {
        links.add(link);
    }

    public List<Link> getLinks() {
        return unmodifiableList(links);
    }
}
