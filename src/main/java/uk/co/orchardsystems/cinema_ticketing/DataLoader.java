package uk.co.orchardsystems.cinema_ticketing;

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


    public static void main(String[] args){
        prop = loadProperties();

        String appData = "";
        try {
            appData = readFile(prop.get("dataFileLocation").toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeDataToDB(appData);

    }

    private static void writeDataToDB(String appData)
    {
        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB(prop.get("mongo.db").toString());
        DBCollection collection = db.getCollection("cinemas");

        DBObject dbObject = (DBObject) JSON.parse(appData);
        collection.insert(dbObject);

        //debugging
        DBCursor cursorDoc = collection.find();
        while (cursorDoc.hasNext()) System.out.println(cursorDoc.next());
    }

    private static Properties loadProperties(){
        InputStream input = null;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("src/main/resources/application.properties");
            // load a properties file
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

    private static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
