package uk.co.orchardsystems.cinema_ticketing.controller;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.orchardsystems.cinema_ticketing.CinemaService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ticketing")
public class TicketServicesController {

    @Resource(name = "cinemaService")
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
//
//    @GetMapping("/screenings")
//    public ResponseEntity<String> getScreenings(@RequestParam("cinemaId") String cinemaId, @RequestParam("movieId") String movieId){}
//
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
