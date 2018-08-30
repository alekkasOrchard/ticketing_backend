package uk.co.orchardsystems.cinema_ticketing;

import lombok.Data;

@Data
public class Cinema {
    private int id;
    private String name;
    private String address;
    private String contactNum;
    private Screen[] screens;

}
