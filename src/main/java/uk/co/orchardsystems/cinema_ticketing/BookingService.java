package uk.co.orchardsystems.cinema_ticketing;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.orchardsystems.cinema_ticketing.Exceptions.ScreeningNotFoundException;

import java.util.UUID;

@Service("bookingService")
@Transactional
public class BookingService {

    @Value("${mongo.db}")
    private String dbName;
    @Value("${mongo.db.bookings.collection}")
    private String bookingsCollection;
    @Value("${mongo.db.cinemas.collection}")
    private String cinemasCollection;
    @Autowired
    private MongoBean mongo;

    public String createBooking(String bookingData) {
        DBCollection coll = mongo.getCollection(dbName, bookingsCollection);
        DBObject bookingDataObj = (DBObject) JSON.parse(bookingData);
        String uuid = UUID.randomUUID().toString();
        bookingDataObj.put("bookingRef", uuid);
        DBObject booking = new BasicDBObject("bookings", bookingDataObj);
        DBObject updateQuery = new BasicDBObject("$push", booking);
        DBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", 1);
        coll.update(searchQuery, updateQuery);

        return uuid;
    }

    public String getBooking(String bookingRef) throws JSONException {
        JSONArray bookings = mongo.getCollectionJSON(dbName, bookingsCollection, "bookings");
        return null;
    }

    public void updateScreening(String bookingData, boolean flag) throws JSONException, ScreeningNotFoundException {
        JSONObject bookingDataObj = new JSONObject(bookingData);
        JSONArray bookedSeats = bookingDataObj.getJSONArray("seatNumbers");
        int cinemaId = (Integer)bookingDataObj.get("cinemaId");
        int screenId = (Integer)bookingDataObj.get("screenId");
        int screeningId = (Integer)bookingDataObj.get("screeningId");

//        JSONArray cinemas = mongo.getCollectionJSON(dbName, cinemasCollection, "cinemas");
//        JSONArray screens = cinemas.getJSONObject(cinemaId - 1).getJSONArray("screens");;
//        JSONObject screen = new JSONObject();
//        int i = 0;
//        while (i < screens.length()){
//            screen = screens.getJSONObject(i);
//            screens.remove(i);
//            if((Integer)screen.get("id") == screenId){
//                i = screens.length();
//            }
//            i++;
//        }
//        JSONArray screenings = screen.getJSONArray("screenings");
//        JSONObject screening = new JSONObject();
//        i=0;
//        while (i < screenings.length()){
//            screening = screenings.getJSONObject(i);
//            screenings.remove(i);
//            if((Integer)screening.get("id") == screeningId){
//                i = screenings.length();
//            }
//            i++;
//        }
//        if(screening.get("id") == null){
//            throw new ScreeningNotFoundException("The selected screening could not be found.");
//        }
//        JSONArray seats = screening.getJSONArray("availableSeats");
//        for (i=0; i<bookedSeats.length(); i++){
//            seats.put(bookedSeats.getInt(i)-1, flag);
//        }
//        screening.put("availableSeats", seats);
//        screenings.put(screening);
//        screen.put("screenings", screenings);
//        screens.put(screen);
//        System.out.println(seats.toString());

//        BasicDBObject query = new BasicDBObject();
//        query.put("cinemas.0.id", cinemaId);
//        query.put("screens.0.id", screenId);
//        query.put("screenings.0.id", screeningId);
//        BasicDBObject data = new BasicDBObject();
//        for (int i=0; i<seats.length(); i++){
//            data.put("availableSeats." + i, flag);
//        }
//        DBObject update = new BasicDBObject("$set", data);
//        DBCollection collection = mongo.getCollection(dbName, collectionName);
//        DBObject result = collection.findAndModify(query, update);
//        System.out.println(result.toString());


    }

}
