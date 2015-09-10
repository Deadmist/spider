package neo4j;

import data.Config;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Created by Deadmist on 10/09/15.
 */
public class Neo4j {

    public static JSONObject stringQueryDB(String query) throws IOException, ParseException {
        if (!checkConfigSanity()) {
            throw new IllegalStateException("Config not loaded or Database config not valid");
        }
        String cypherEndpoint = String.format("https://%s:%d/db/data/transaction/commit",
                Config.getDatabaseAddress(), Config.getDatabasePort()),
                encodedCredentials = Base64.getEncoder().encodeToString(
                        String.format("%s:%s",
                                Config.getDatabaseUsername(), Config.getDatabasePassword()).getBytes());
        JSONObject jsonBody = new JSONObject();
        JSONArray statements = new JSONArray();
        JSONObject statement = new JSONObject();

        statement.put("statement", query);
        statements.add(statement);
        jsonBody.put("statements", statements);

        URL dbUrl = new URL(cypherEndpoint);
        HttpURLConnection con = (HttpURLConnection) dbUrl.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + encodedCredentials);
        con.setRequestProperty("Content-Type", "application/json");

        con.setDoInput(true);
        con.setDoOutput(true);

        try(OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
            writer.write(jsonBody.toJSONString());
            writer.flush();
        }

        try(InputStreamReader reader = new InputStreamReader(con.getInputStream())) {
            JSONParser parser = new JSONParser();
            JSONObject re = (JSONObject) parser.parse(reader);
            return re;
        }

    }

    /**
     * Checks if the config fields have valid values
     * @return true if valid
     */
    private static boolean checkConfigSanity() {
        if (Config.getDatabaseAddress().isEmpty() ||
                Config.getDatabasePassword().isEmpty() ||
                Config.getDatabasePort() == 0 ||
                Config.getDatabaseUsername().isEmpty()) return false;
        return true;
    }

}
