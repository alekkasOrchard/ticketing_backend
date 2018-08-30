package uk.co.orchardsystems.cinema_ticketing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import uk.co.orchardsystems.cinema_ticketing.util.DataLoader;

@Configuration
@PropertySource("application.properties")
public class CinemaTicketingConfiguration {

    @Bean
    public DataLoader applicationInitializer(){return new DataLoader();}
}
