package uk.co.orchardsystems.cinema_ticketing;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.orchardsystems.cinema_ticketing.factory.MongoFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service("userService")
@Transactional
public class CinemaService {

    @Value("${mongo.db}")
    private String dbName;
    @Value("${mongo.db.collection}")
    private String collectionName;

    public String getCinema(int cinemaId) throws JSONException {
        JSONArray cinemas = extractCinemasFromCollection(getCinemaCollection());
        return cinemas.getJSONObject(cinemaId - 1).toString();
    }

    public String getCinemasList() throws JSONException {
        JSONArray cinemas = extractCinemasFromCollection(getCinemaCollection());
        for (int i = 0; i < cinemas.length(); i++) {
            cinemas.getJSONObject(i).remove("screen");
        }
        return cinemas.toString();
    }

    public String getMoviesList(int cinemaId) throws JSONException {
        HashSet<Integer> movieIds = new HashSet<Integer>();
        JSONArray movies = new JSONArray();
        JSONArray cinemas = extractCinemasFromCollection(getCinemaCollection());
        JSONArray screens = cinemas.getJSONObject(cinemaId - 1).getJSONArray("screen");
        for (int i=0; i<screens.length(); i++){
            JSONArray screenings = screens.getJSONObject(i).getJSONArray("screening");
            for(int j=0; j<screenings.length(); j++){
                JSONObject movie = screenings.getJSONObject(j).getJSONObject("movie");
                Integer movieId = Integer.parseInt(movie.get("id").toString());
                if(!movieIds.contains(movieId)){
                    movies.put(movie);
                    movieIds.add(movieId);
                }
            }
        }
        return movies.toString();
    }

    private JSONArray extractCinemasFromCollection(String collection) throws JSONException {
        return new JSONObject(collection).getJSONArray("cinema");
    }

    private String getCinemaCollection() {
        DBCollection coll = MongoFactory.getCollection(dbName, collectionName);
        DBCursor cursor = coll.find();
        return cursor.next().toString();

    }

}
