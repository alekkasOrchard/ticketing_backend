package uk.co.orchardsystems.cinema_ticketing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class CinemaTicketingConfiguration {

    @Bean
    public CinemaService cinemaService(){return new CinemaService();}

    @Bean
    public BookingService bookingService(){return  new BookingService();}

    @Bean
    public MongoBean mongoBean(){return new MongoBean();}
}
