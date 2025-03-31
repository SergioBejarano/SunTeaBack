package edu.eci.cvds.labReserves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilderCustomizer;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;

/**
 * Clase principal de la aplicación LabReserve.
 *
 * Esta clase inicializa la aplicación Spring Boot y configura la serialización de fechas con Jackson.
 */
@SpringBootApplication
public class LabReserve {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos pasados a la aplicación.
     */
	public static void main(String[] args) {
		SpringApplication.run(LabReserve.class, args);
	}

    /**
     * Configura un Bean para personalizar la serialización de fechas en Jackson.
     *
     * Este método realiza las siguientes configuraciones:
     * - Agrega el módulo JavaTimeModule para soportar las clases de fecha y hora de Java 8+.
     * - Deshabilita la escritura de fechas como timestamps para que se serialicen en formato ISO-8601.
     *
     * @return Un Jackson2ObjectMapperBuilderCustomizer configurado.
     */
	@Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            builder.modules(new JavaTimeModule());
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }

}