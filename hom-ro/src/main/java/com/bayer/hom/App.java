package com.bayer.hom;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws SQLException {
        Map<String, HOMResult> hHOMResult = new HashMap<>();
        SQLiteDB db = new SQLiteDB("/mnt/hom-ro-result-2021-10-13-19_24_59-Domino.db");
        hHOMResult = db.getHOMResult();

        for (Entry<String, HOMResult> entry : hHOMResult.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

    }
}
