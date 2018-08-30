package uk.co.orchardsystems.cinema_ticketing.factory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoFactory {
    private static Mongo mongo;

    @Value("${mongo.host}")
    private static String host;
    @Value("${mongo.port}")
    private static int port;
    @Value("${mongo.db}")
    private static String dbName;
    @Value("${mongo.db.collection}")
    private static String collectionName;

    private MongoFactory() {
    }

    public static Mongo getMongo() {
        if (mongo == null) {
            try {
                mongo = new Mongo(host, port);
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

