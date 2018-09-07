package uk.co.orchardsystems.cinema_ticketing.util;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DataLoader {
    private static Properties prop = loadProperties();

    public static void main(String[] args) {
        prop = loadProperties();
        String appData = "";
        try {
            appData = readFile(prop.get("dataFileLocation").toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeDataToDB(appData);

    }

    private static void writeDataToDB(String appData) {
        String host = prop.get("mongo.host").toString();
        int port = Integer.parseInt(prop.get("mongo.port").toString());
        String dbName = prop.get("mongo.db").toString();
        String cinemasCollection = prop.get("mongo.db.cinemas.collection").toString();
        String bookingsCollection = prop.get("mongo.db.bookings.collection").toString();
        Mongo mongo = new Mongo(host, port);
        DB db = mongo.getDB(dbName);
        if (db.collectionExists(cinemasCollection)) {
            DBCollection myCollection = db.getCollection(bookingsCollection);
            myCollection.drop();
            myCollection = db.getCollection(cinemasCollection);
            myCollection.drop();
        }
        DBCollection collection = db.getCollection(cinemasCollection);
        DBObject dbObject = (DBObject) JSON.parse(appData);
        collection.insert(dbObject);
        collection = db.getCollection(bookingsCollection);
        collection.insert((DBObject) JSON.parse("{\"_id\": 1,\"bookings\": [] }"));
    }


    private static Properties loadProperties() {
        InputStream input = null;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("src/main/resources/application.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
