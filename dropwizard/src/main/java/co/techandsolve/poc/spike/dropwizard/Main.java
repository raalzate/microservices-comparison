package co.techandsolve.poc.spike.dropwizard;

import co.techandsolve.poc.spike.dropwizard.infrastructure.DropwizardApplication;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class Main {
    private Main() {
    }

    public static void main(String[] args) throws Exception {

        String pathConfig = Optional.ofNullable(Main.class.getClassLoader().getResource(args[0]))
                .filter(Objects::nonNull)
                .map(URL::getPath)
                .orElseThrow(() -> new Exception("No existe el recurso YAML"));

        String[] config = new String[]{
                "server", pathConfig
        };
        new DropwizardApplication().run(config);
    }
}
