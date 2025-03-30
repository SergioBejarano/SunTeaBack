package edu.eci.cvds.labReserves;

import edu.eci.cvds.labReserves.config.RestTemplateConf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateConf restTemplate;

    private String getBaseUrl() {
        return "https://baeldung:" + port;
    }

    @Test
    public void whenGETanHTTPSResource_thenCorrectResponse() throws Exception {
        String url = getBaseUrl() + "/api/auth/login";
        ResponseEntity<String> response = restTemplate.restTemplate().getForEntity(url, String.class, Collections.emptyMap());
        assertEquals("<h1>Welcome to Secured Site</h1>", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}