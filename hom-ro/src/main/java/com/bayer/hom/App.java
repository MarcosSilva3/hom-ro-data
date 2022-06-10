package com.bayer.hom;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;

/**
 * HOM Romania
 *
 */
public class App {
    public static void main(String[] args) throws SQLException, ClientProtocolException, IOException, ParseException {

        Map<String, String> env = System.getenv();
        String client_id = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_ID");
        String client_secret = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_SECRET");

        final String country = "Romania";
        final String db_filename = "/mnt/hom-ro-result-2021-10-13-19_24_59-Domino.db";
        final String entity_id = "2ee2d94e-b09f-4657-8072-ef3ff5b805b2";
        final String question_code = "PLTDT";

        Map<String, HOMResult> hHOMResult = new HashMap<>();
        Map<String, GSMData> hGSMData = new HashMap<>();
        SQLiteDB db = new SQLiteDB(db_filename);
        hHOMResult = db.getHOMResult();
        hGSMData = db.getGSMData(country);

        for (Entry<String, GSMData> entry : hGSMData.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

        String token = new ClientToken(client_id, client_secret).getToken();
        System.out.println("Token: " + token);

        ScoutWkt wkt = new ScoutWkt(entity_id, question_code, token);
        wkt.getWkData();

    }
}
