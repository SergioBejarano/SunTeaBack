package edu.eci.cvds.labReserves.config;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class RestTemplateConf {

    @Value("${truststore.path}")
    private Resource trustStoreResource;

    @Value("${truststore.password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate restTemplate() {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }

    private SSLContext createSSLContext() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        try (InputStream trustStream = trustStoreResource.getInputStream()) {
            trustStore.load(trustStream, trustStorePassword.toCharArray());
        }

        return SSLContexts.custom()
                .loadTrustMaterial(trustStore, null)
                .build();
    }
}
