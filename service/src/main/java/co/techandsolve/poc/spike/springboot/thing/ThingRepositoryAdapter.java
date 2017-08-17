package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.core.persistence.InMemoryThingRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ThingRepositoryAdapter extends InMemoryThingRepository {

}
