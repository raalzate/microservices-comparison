package co.techandsolve.poc.spike.common.persistence;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class InMemoryThingRepository implements ThingRepository {

    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Map<Integer, Thing> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<Thing> byId(int id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<Thing> all() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public void save(Thing thing) {
        if (thing.getId() == null) {
            thing.setId(idGenerator.getAndIncrement());
        }
        repository.put(thing.getId(), thing);
    }

    @Override
    public void update(Thing thing) {
        save(thing);
    }

    @Override
    public void delete(Thing thing) {
        repository.remove(thing.getId());
    }
}
