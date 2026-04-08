package edu.eci.cvds.labReserves.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
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

/**
 * Configuration class for setting up RestTemplate with SSL support.
 */
@Configuration
public class RestTemplateConf {
    /** Path to the truststore resource. */
    @Value("${truststore.path}")
    private Resource trustStoreResource;
    /** Password for the truststore. */
    @Value("${truststore.password}")
    private String trustStorePassword;
     /**
     * Creates and configures a RestTemplate bean.
     *
     * @return The configured RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {

        final PoolingHttpClientConnectionManager connectionManager =
            new PoolingHttpClientConnectionManager();

        final CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        final HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }
    /**
     * Creates an SSLContext using the configured truststore.
     *
     * @return The configured SSLContext.
     * @throws Exception If an error occurs during SSL context creation.
     */
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
