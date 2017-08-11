package co.techandsolve.poc.spike.dropwizard.index;

import co.techandsolve.poc.spike.common.domain.Thing;
import com.codahale.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class IndexService {

    @Inject
    private Client client;


    @Inject
    public IndexService() {
    }

    @GET
    @Timed
    public String index() {
        WebTarget target = client.target("http://localhost:8443/app/things");
        Invocation invocation = target.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")
                .build(HttpMethod.GET);
        List<Thing> things = asList(invocation.invoke(Thing[].class));

        return "Hello World! " + things.stream().map(Thing::getName).collect(toList());
    }

}

