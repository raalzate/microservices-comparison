package co.techandsolve.poc.spike.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableWebFlux
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    WebClient webClient(@Value("${server.host}") String host, @Value("${server.port}") int port) {
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector())
                .baseUrl(String.format("http://%s:%d", host, port)).build();
    }

}