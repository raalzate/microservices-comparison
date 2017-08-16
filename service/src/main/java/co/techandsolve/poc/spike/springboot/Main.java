package co.techandsolve.poc.spike.springboot;

import co.com.proteccion.jano.SecuredApplication;
import co.com.proteccion.jano.datasource.FlatFileConnection;
import co.techandsolve.poc.spike.springboot.config.JanoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

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

    @Bean
    SecuredApplication securedApplication(@Value("${securedApplication.clientId}") String clientId,
                                          @Value("${securedApplication.tenant}") String tenant,
                                          @Value("${securedApplication.clientSecret}") String clientSecret) {
        SecuredApplication securedApplication = new SecuredApplication();
        securedApplication.setName("appSegura");
        securedApplication.setAllowOrigin("*");
        securedApplication.setClientId(clientId);
        securedApplication.setTenant(tenant);
        securedApplication.setClientSecret(clientSecret);
        securedApplication.setGenericDataSource(getFlatFileConnection());
        return securedApplication;
    }


    private FlatFileConnection getFlatFileConnection() {
        FlatFileConnection flatFileConnection = new FlatFileConnection();
        String pathConfig = Optional.ofNullable(JanoConfiguration.class.getClassLoader().getResource("application.yml"))
                .filter(Objects::nonNull)
                .map(URL::getPath)
                .map(path -> path.replace("/application.yml", ""))
                .orElse(null);
        flatFileConnection.setFilePath(pathConfig);
        return flatFileConnection;
    }


}