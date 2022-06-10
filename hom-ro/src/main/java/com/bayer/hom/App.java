package com.bayer.hom;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HOM Romania
 *
 */
public class App {
    public static void main(String[] args) throws SQLException {

        final String country = "Romania";
        final String db_filename = "/mnt/hom-ro-result-2021-10-13-19_24_59-Domino.db";

        Map<String, HOMResult> hHOMResult = new HashMap<>();
        Map<String, GSMData> hGSMData = new HashMap<>();
        SQLiteDB db = new SQLiteDB(db_filename);
        hHOMResult = db.getHOMResult();
        hGSMData = db.getGSMData(country);

        for (Entry<String, GSMData> entry : hGSMData.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

    }
}
