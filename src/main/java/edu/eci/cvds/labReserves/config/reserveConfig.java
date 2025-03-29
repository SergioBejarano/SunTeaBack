
package edu.eci.cvds.labReserves.config;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.LaboratoryMongoRepository;
import edu.eci.cvds.labReserves.repository.mongodb.ReserveMongoRepository;
import edu.eci.cvds.labReserves.repository.mongodb.ScheduleMongoRepository;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para los repositorios de tareas.
 * Esta clase permite seleccionar la implementación del repositorio
 * a utilizar según el valor de la propiedad 'task.repository.type' en los archivos de configuración.
 * Dependiendo de si se especifica 'mongo' o 'text', se devolverá una implementación de
 * TaskMongoRepository o TaskTextRepository.
 */
@Configuration
@EnableMongoRepositories(basePackages = "edu.eci.cvds.labReserves.repository.mongodb")
public class reserveConfig {
    @Autowired
    private final UserMongoRepository userMongoRepository;

    /**
     * Constructor que inyecta las implementaciones de los repositorios.
     */
    public reserveConfig(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailService(){
        return name -> {
            UserMongodb user = userMongoRepository.findByName(name);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getName())
                    .password(user.getPassword())
                    .build();
        };
    }
}
