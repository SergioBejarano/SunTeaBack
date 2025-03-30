package edu.eci.cvds.labReserves.config;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
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

    @Value("classpath:ssl/truststore.p12")
    private Resource trustStoreResource;

    @Value("${truststore.password}")
    private String trustStorePassword;
    @Bean
    public RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = createSSLContext();

        // 🔹 Configura el socket con TLS seguro usando el truststore
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

        // 🔹 Registra el protocolo HTTPS con la configuración SSL personalizada
        Registry registry = RegistryBuilder.create()
                .register("https", sslSocketFactory)
                .build();

        // 🔹 Usa el PoolingHttpClientConnectionManager con el protocolo registrado
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setDefaultConnectionConfig(ConnectionConfig.custom()
                .setSocketTimeout(Timeout.ofSeconds(30)) // Timeout opcional
                .build());

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager) // 🔹 Usa el connection manager con TLS
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

    private SSLContext createSSLContext() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        try (InputStream trustStream = trustStoreResource.getInputStream()) {
            trustStore.load(trustStream, trustStorePassword.toCharArray());
        }

        return SSLContexts.custom()
                .loadTrustMaterial(trustStore, null) // 🔹 Solo confía en truststore.p12
                .build();
    }
}
