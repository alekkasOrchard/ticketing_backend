package uk.co.orchardsystems.cinema_ticketing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.orchardsystems.cinema_ticketing.Exceptions.ScreeningsNotFoundException;

import java.util.HashSet;

@Service("cinemaService")
@Transactional
public class CinemaService {

    @Value("${mongo.db}")
    private String dbName;
    @Value("${mongo.db.cinemas.collection}")
    private String collectionName;
    @Autowired
    private MongoBean mongo;

    public String getCinema(int cinemaId) throws JSONException {
        JSONArray cinemas = mongo.getCollectionJSON(dbName, collectionName, "cinemas");
        return cinemas.getJSONObject(cinemaId - 1).toString();
    }

    public String getCinemasList() throws JSONException {
        JSONArray cinemas = mongo.getCollectionJSON(dbName, collectionName, "cinemas");
        for (int i = 0; i < cinemas.length(); i++) {
            cinemas.getJSONObject(i).remove("screens");
        }
        return cinemas.toString();
    }

    public String getMoviesList(int cinemaId) throws JSONException {
        HashSet<Integer> movieIds = new HashSet<Integer>();
        JSONArray movies = new JSONArray();
        JSONArray cinemas = mongo.getCollectionJSON(dbName, collectionName, "cinemas");
        JSONArray screens = cinemas.getJSONObject(cinemaId - 1).getJSONArray("screens");
        for (int i = 0; i < screens.length(); i++) {
            JSONArray screenings = screens.getJSONObject(i).getJSONArray("screenings");
            for (int j = 0; j < screenings.length(); j++) {
                JSONObject movie = screenings.getJSONObject(j).getJSONObject("movie");
                Integer movieId = Integer.parseInt(movie.get("id").toString());
                if (!movieIds.contains(movieId)) {
                    movies.put(movie);
                    movieIds.add(movieId);
                }
            }
        }
        return movies.toString();
    }

    public String getScreeningsForMovie(int cinemaId, int movieId) throws JSONException, ScreeningsNotFoundException {
        JSONArray screeningsForMovie = new JSONArray();
        JSONArray cinemas = mongo.getCollectionJSON(dbName, collectionName, "cinemas");
        JSONArray screens = cinemas.getJSONObject(cinemaId - 1).getJSONArray("screens");
        for (int i = 0; i < screens.length(); i++) {
            JSONArray screenings = screens.getJSONObject(i).getJSONArray("screenings");
            for (int j = 0; j < screenings.length(); j++) {
                JSONObject screening = screenings.getJSONObject(j);
                JSONObject movie = screening.getJSONObject("movie");
                int currentMovieId = Integer.parseInt(movie.get("id").toString());
                if (currentMovieId == movieId) {
                    screening.put("screenId", i + 1);
                    screeningsForMovie.put(screening);
                }
            }
        }
        if(screeningsForMovie.length() == 0){
            throw new ScreeningsNotFoundException("No screenings found for movie with id: " + movieId + " at cinema with id: " + cinemaId);
        }
        return screeningsForMovie.toString();
    }


}
