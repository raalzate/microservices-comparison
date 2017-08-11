package co.techandsolve.poc.spike.common.domain;

import java.util.List;
import java.util.Optional;

public interface ThingRepository {

    Optional<Thing> byId(int id);

    List<Thing> all();

    void save(Thing thing);

    void update(Thing thing);

    void delete(Thing thing);
}
