package uk.co.orchardsystems.cinema_ticketing.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.orchardsystems.cinema_ticketing.CinemaService;

@RestController
@RequestMapping("/ticketing")
public class TicketServicesController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/cinemas")
    public ResponseEntity<String> getCinemas(){
        String cinemas = "";
        HttpStatus response = HttpStatus.FOUND;
        try {
            cinemas = cinemaService.getCinemasList();
        } catch (JSONException e) {
            e.printStackTrace();
            response = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(cinemas, response);
    }

    @GetMapping("/movies")
    public ResponseEntity<String> getMoviesAtCinema(@RequestParam("cinemaId") int cinemaId){
        String movies = "";
        HttpStatus response = HttpStatus.FOUND;
        try {
            movies = cinemaService.getMoviesList(cinemaId);
        } catch (JSONException e) {
            e.printStackTrace();
            response = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movies, response);
    }

    @GetMapping("/screenings")
    public ResponseEntity<String> getScreenings(@RequestParam("cinemaId") int cinemaId, @RequestParam("movieId") int movieId){
        String screenings = "";
        HttpStatus response = HttpStatus.FOUND;
        try {
            screenings = cinemaService.getScreeningsForMovie(cinemaId, movieId);
        } catch (JSONException e) {
            e.printStackTrace();
            response = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(screenings, response);
    }

//    //creates a booking and returns the booking ID
//    @PostMapping("/bookTickets")
//    public ResponseEntity<String> bookTickets(@RequestBody Screening screening){}

    @GetMapping("/cinema")
    public ResponseEntity<String> getCinema(@RequestParam("id") int cinemaId) {
        String cinema = "";
        HttpStatus response = HttpStatus.FOUND;
        try {
            cinema = cinemaService.getCinema((cinemaId));
        } catch (JSONException e) {
            e.printStackTrace();
            response = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(cinema, response);
    }


}
