package edu.eci.cvds.labReserves.config;
import edu.eci.cvds.labReserves.model.Resource;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.security.KeyStore;


@Configuration
public class RestTemplateConf {
    @Value("${trust.store}")
    private org.springframework.core.io.Resource trustStore;


    @Value("${trust.store.password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate restTemplate() throws Exception {
        // Load the trust storage
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream trustStoreStream = trustStore.getInputStream()) {
            keyStore.load(trustStoreStream, trustStorePassword.toCharArray());
        }

        // Build the  SSLContext
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(keyStore, null)
                .build();

        // Configure the SSL connection
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
