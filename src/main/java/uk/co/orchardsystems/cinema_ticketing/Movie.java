package uk.co.orchardsystems.cinema_ticketing;

import lombok.Data;

@Data
public class Movie {
    private int id;
    private String title;
    private String description;
    private String rating;
}
