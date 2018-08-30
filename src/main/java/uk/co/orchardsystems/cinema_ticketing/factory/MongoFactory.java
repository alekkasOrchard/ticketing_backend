package uk.co.orchardsystems.cinema_ticketing.factory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class MongoFactory {
    private static Mongo mongo;

    private final static String HOST = "localhost";
    private final static int PORT = 27017;

    private MongoFactory() {
    }

    public static Mongo getMongo() {
        if (mongo == null) {
            try {
                mongo = new Mongo(HOST, PORT);
            } catch (MongoException ex) {
                ex.printStackTrace();
            }
        }
        return mongo;
    }

    public static DB getDB(String dbName){
        return getMongo().getDB(dbName);
    }

    public static DBCollection getCollection(String dbName, String collectionName){
        return getDB(dbName).getCollection(collectionName);
    }

}

