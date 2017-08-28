package co.com.proteccion.base.utils;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;

/**
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com> on 28/08/2017.
 */
public class WebClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(WebClientUtils.class);

    private WebClientUtils() {
    }

    public static WebClient.Builder  webClientSSL(String host, int port) {

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(opt -> {
            try {
                opt.sslContext(SslContextBuilder
                        .forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                        .build());
            } catch (SSLException e) {
                logger.error("Existe problemas en realizar el trust manager", e);
            }
        })).baseUrl(String.format("https://%s:%d", host, port));

    }

    public static WebClient.Builder  webClient(String host, int port) {
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector())
                .baseUrl(String.format("http://%s:%d", host, port));

    }
}
