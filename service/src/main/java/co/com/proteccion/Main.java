package co.com.proteccion;

import co.com.proteccion.base.utils.WebClientUtils;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebFlux
public class Main {


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    /**
     * Esta funcion se encarga de injectar el cliente consumindor, se parametriza desde el archivo application.yml
     * @param host
     * @param port
     * @return
     */
    @Bean
    WebClient webClient(@Value("${innerClient.host}") String host, @Value("${innerClient.port}") int port) {
        return WebClientUtils.webClientSSL(host, port).build();
    }

}