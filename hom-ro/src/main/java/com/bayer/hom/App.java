package com.bayer.hom;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

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
        final String question_code = "PLTDT";

        Map<String, HOMResult> hHOMResult = new HashMap<>();
        Map<String, GSMData> hGSMData = new HashMap<>();
        SQLiteDB db = new SQLiteDB(db_filename);
        hHOMResult = db.getHOMResult();
        hGSMData = db.getGSMData(country);

        Table<String, Integer, HOMResult> tHOMResult = HashBasedTable.create();
        tHOMResult = db.getHOMResultTable();

        String token = new ClientToken(client_id, client_secret).getToken();
        System.out.println("Token: " + token);

        // Get wkt for each entity_id.
        for (Entry<String, GSMData> entry : hGSMData.entrySet()) {
            GSMData g = entry.getValue();
            String entity_id = g.getEntityid();
            ScoutWkt wkt = new ScoutWkt(entity_id, question_code, token);
            g.setWkt(wkt.getWkt());
            hGSMData.put(entry.getKey(), g);
        }

        // for (Entry<String, GSMData> entry : hGSMData.entrySet()) {
        //     System.out.println(entry.getKey() + " => " + entry.getValue());
        // }

        for (Table.Cell<String, Integer, HOMResult> cell : tHOMResult.cellSet()) {
            System.out.println(cell.getRowKey() + "|" + cell.getColumnKey() + "|" + cell.getValue());
        }

    }
}
