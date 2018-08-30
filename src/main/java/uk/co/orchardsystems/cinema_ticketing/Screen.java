package uk.co.orchardsystems.cinema_ticketing;

import lombok.Data;

@Data
public class Screen {

    private int id;
    private Screening[] screenings;

}
