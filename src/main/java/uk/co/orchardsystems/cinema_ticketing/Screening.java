package uk.co.orchardsystems.cinema_ticketing;

import lombok.Data;

import java.util.List;

@Data
public class Screening {
    private int id;
    private String date;
    private String time;
    private Movie movie;
    private boolean[] availableSeats;
}
