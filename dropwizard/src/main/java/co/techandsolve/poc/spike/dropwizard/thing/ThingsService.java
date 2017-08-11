package co.techandsolve.poc.spike.dropwizard.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/things")
public class ThingsService {

    @Context
    UriInfo uriInfo;

    @Inject
    private ThingRepository thingRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        List<Thing> things = thingRepository.all();
        List<ThingRepresentation> representations = things.stream()
                .map(t -> {
                    ThingRepresentation representation = new ThingRepresentation(t);
                    representation.addLink(
                            co.techandsolve.poc.spike.common.hateoas.Link.self(uriInfo
                                    .getAbsolutePathBuilder()
                                    .build(t.getId())
                                    .toString()));
                    return representation;
                }).collect(toList());
        return Response.ok(representations)
                .header("total-count", String.valueOf(things.size()))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createThing(Thing thing) {
        thingRepository.save(thing);
        return Response.created(UriBuilder.fromResource(ThingsService.class)
                .path("/{id}").build(String.valueOf(thing.getId())))
                .build();
    }

}
