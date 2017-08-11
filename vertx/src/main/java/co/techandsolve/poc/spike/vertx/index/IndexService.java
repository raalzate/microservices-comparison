package co.techandsolve.poc.spike.vertx.index;

import co.techandsolve.poc.spike.common.domain.Thing;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class IndexService {

    private final HttpClient httpClient;

    public IndexService(HttpClient httpClient) {

        this.httpClient = httpClient;
    }

    public void index(RoutingContext routingContext) {
        httpClient.getAbs("http://localhost:8443/things")
                .putHeader("Accept", "application/json")
                .putHeader("Authorization", "Bearer token")
                .handler(response ->
                        response.bodyHandler(buffer -> {
                            if (response.statusCode() == 200) {
                                List<Thing> things = new ArrayList<>(asList(Json.decodeValue(buffer.toString(), Thing[].class)));
                                routingContext.response()
                                        .end(things.stream().map(Thing::getName).collect(toList()).toString() + ", Hello World!");
                            } else {
                                routingContext.response()
                                        .setStatusCode(response.statusCode())
                                        .write("Oops, something went wrong: " + response.statusMessage())
                                        .end();
                            }
                        }))
                .end();
    }
}
