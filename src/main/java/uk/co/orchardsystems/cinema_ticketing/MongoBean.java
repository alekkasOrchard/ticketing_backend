package uk.co.orchardsystems.cinema_ticketing;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
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

}

