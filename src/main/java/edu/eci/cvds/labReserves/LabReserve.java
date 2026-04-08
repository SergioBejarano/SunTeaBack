package edu.eci.cvds.labReserves;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase principal de la aplicación LabReserve.
 *
 * Esta clase inicializa la aplicación Spring Boot y configura
 * la serialización de fechas con Jackson.
 */
@SpringBootApplication
public class LabReserve {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(final String[] args) {
        SpringApplication.run(LabReserve.class, args);
    }

    /**
     * Configura un Bean para personalizar la serialización de fechas.
     *
     * @return Un Jackson2ObjectMapperBuilderCustomizer configurado.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            builder.modules(new JavaTimeModule());
            builder.featuresToDisable(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            );
        };
    }

    /**
     * Configura el intercambio de recursos de origen cruzado (CORS).
     *
     * @return Una instancia de WebMvcConfigurer con mapeos CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                            "https://labreserveeci-hcfwbkh6czhhggba"
                                + ".eastus2-01.azurewebsites.net/",
                            "https://labreserveecidevelop-cbfjhdbqb3h5end7"
                                + ".canadacentral-01.azurewebsites.net/",
                            "http://localhost:8080/",
                            "http://localhost:3000/"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE",
                                        "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
