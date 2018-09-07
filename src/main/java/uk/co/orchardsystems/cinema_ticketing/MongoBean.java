package uk.co.orchardsystems.cinema_ticketing;

import com.mongodb.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class MongoBean {
    private static Mongo mongo;

    private final static String HOST = "localhost";
    private final static int PORT = 27017;

    public MongoBean() {
        mongo = new Mongo(HOST, PORT);
    }

    public Mongo getMongo() {
        return mongo;
    }

    public DB getDB(String dbName) {
        return getMongo().getDB(dbName);
    }

    public DBCollection getCollection(String dbName, String collectionName) {
        return getDB(dbName).getCollection(collectionName);
    }

    public JSONArray getCollectionJSON(String dbName, String collectionName, String splitBy) throws JSONException {
        DBCollection coll = getCollection(dbName, collectionName);
        DBCursor cursor = coll.find();
        return new JSONObject(cursor.next().toString()).getJSONArray(splitBy);
    }

}

