package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Created by Deadmist on 01.09.2015.
 */
public class Config {

    static String databaseAddress, databaseUsername, databasePassword;
    static int databasePort;

    static String configFile = "config.json";

    public static void setConfigFile(String path) throws FileNotFoundException {
        if(!new File(path).exists()) {
            throw new FileNotFoundException("Config file " + path + " could not be found");
        }
        configFile = path;
    }

    public static void loadConfig() {
        JSONObject config = loadJSONFile(configFile);
        if (config == null) return;

        JSONObject databaseConfig = (JSONObject) config.get("database");
        databaseAddress = databaseConfig.get("address").toString();
        databaseUsername = databaseConfig.get("user").toString();
        databasePassword = databaseConfig.get("password").toString();
        databasePort = Integer.parseInt(databaseConfig.get("port").toString());
    }

    private static JSONObject loadJSONFile(String path) {
        File config = new File(path);
        JSONParser parser = new JSONParser();

        try (FileInputStream stream = new FileInputStream(config)) {
            return (JSONObject) parser.parse(new InputStreamReader(stream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDatabaseAddress() {
        return databaseAddress;
    }

    public static String getDatabasePassword() {
        return databasePassword;
    }

    public static int getDatabasePort() {
        return databasePort;
    }

    public static String getDatabaseUsername() {
        return databaseUsername;
    }
}
