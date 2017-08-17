package co.techandsolve.poc.spike.springboot.index;

import co.techandsolve.poc.spike.core.domain.Thing;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@RestController
public class IndexController {

    @RequestMapping("/index")
    @ResponseBody
    String index() {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "token");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);

        RestTemplate rest = new RestTemplate();
        ResponseEntity<Thing[]> responseEntity = rest.exchange("http://localhost:8443/things", HttpMethod.GET, requestEntity, Thing[].class);
        List<Thing> things = asList(responseEntity.getBody());

        return "Hello World! " + things.stream().map(Thing::getName).collect(toList());
    }

}
