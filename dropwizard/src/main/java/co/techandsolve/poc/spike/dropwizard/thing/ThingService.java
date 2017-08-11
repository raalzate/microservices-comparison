package co.techandsolve.poc.spike.dropwizard.thing;

import co.techandsolve.poc.spike.common.domain.Thing;
import co.techandsolve.poc.spike.common.domain.ThingRepository;
import co.techandsolve.poc.spike.common.hateoas.Link;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("/thing/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class ThingService {

    @Context
    UriInfo uriInfo;

    @Inject
    private ThingRepository thingRepository;

    @GET
    public Response byId(@PathParam("id") int thingId) {
        Optional<Thing> thing = thingRepository.byId(thingId);
        return thing.map(t -> {
            ThingRepresentation thingRepresentation = new ThingRepresentation(t);
            thingRepresentation.addLink(Link.self(uriInfo.getAbsolutePathBuilder().build(t.getId()).toString()));
            return Response.ok(thingRepresentation).build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
