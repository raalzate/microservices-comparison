package co.techandsolve.poc.spike.springboot.index.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class IndexConsumerTest {


    @InjectMocks
    private IndexConsumerService service;

    @Mock
    private WebClient client;

    @Test
    public void summary() {

    }
}
