package co.techandsolve.poc.spike.springboot.thing;

import co.techandsolve.poc.spike.core.domain.Thing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by admin on 16/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThingsControllerTest {

    @Mock
    private ThingRepositoryAdapter thingRepository;

    @InjectMocks
    private ThingsController thingsController;

    @Captor
    private ArgumentCaptor<Mono<Thing>> argThing;

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void create() {

        when(thingRepository.save(argThing.capture())).thenReturn(Mono.just(new Thing(1, "Test 2")));

        Mono<ThingRepresentation> thing = thingsController.create(new Thing(null, "Test"));

        verify(thingRepository).save(argThing.capture());

        Thing values = argThing.getValue().block();

        assert "Test".equals(values.getName());
        assert "Test 2".equals(thing.block().getName());
    }

    @Test
    public void delete() {
        when(thingRepository.delete(1)).thenReturn(Mono.empty());

        thingsController.delete(1);

        verify(thingRepository).delete(1);
    }

    @Test
    public void update() {
        when(thingRepository.update(argThing.capture())).thenReturn(Mono.just(new Thing(1, "Test 2")));

        Mono<ThingRepresentation> thing = thingsController.update(new Thing(null, "Test"));

        verify(thingRepository).update(argThing.capture());

        Thing values = argThing.getValue().block();

        assert "Test".equals(values.getName());
        assert "Test 2".equals(thing.block().getName());
    }

    @Test
    public void list() {
        when(thingRepository.all()).thenReturn(Flux.just(new Thing(1, "Test 1"), new Thing(2, "Test 2")));

        Flux<ThingRepresentation> things = thingsController.all();

        verify(thingRepository).all();

        assert things.count().block() == 2L;
    }
}
