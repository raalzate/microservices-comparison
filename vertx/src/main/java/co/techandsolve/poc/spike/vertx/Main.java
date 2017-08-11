package co.techandsolve.poc.spike.vertx;

import co.techandsolve.poc.spike.common.domain.ThingRepository;
import co.techandsolve.poc.spike.common.persistence.InMemoryThingRepository;
import co.techandsolve.poc.spike.vertx.index.IndexService;
import co.techandsolve.poc.spike.vertx.thing.ThingService;
import co.techandsolve.poc.spike.vertx.thing.ThingsService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Main {

    public static void main(String[] args) {

        Json.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Vertx vertx = Vertx.vertx();

        HttpClientOptions clientOptions = new HttpClientOptions();
        HttpClient httpClient = vertx.createHttpClient(clientOptions);

        Router router = Router.router(vertx);

        IndexService indexService = new IndexService(httpClient);

        ThingRepository thingRepository = new InMemoryThingRepository();
        ThingsService thingsService = new ThingsService(thingRepository);
        ThingService thingService = new ThingService(thingRepository);

        router.route("/things*").handler(BodyHandler.create());

        router.get("/").produces("text/html").handler(indexService::index);
        router.get("/things").produces("application/json").handler(thingsService::all);
        router.post("/things").consumes("application/json").handler(thingsService::createThing);
        router.get("/things/:id").produces("application/json").handler(thingService::byId);

        HttpServerOptions serverOptions = new HttpServerOptions().setPort(8443);
        HttpServer server = vertx.createHttpServer(serverOptions);
        server.requestHandler(router::accept).listen();
    }
}
