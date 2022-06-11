package com.bayer.hom;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;

/**
 * HOM Romania
 *
 */
public class App {
    public static void main(String[] args)
            throws SQLException, ClientProtocolException, IOException, ParseException, java.text.ParseException {

        Map<String, String> env = System.getenv();
        String client_id = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_ID");
        String client_secret = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_SECRET");

        final String country = "Romania";
        final String db_filename = "/mnt/hom-ro-result-2021-10-13-19_24_59-Domino.db";
        final String question_code = "PLTDT";
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

        Map<String, HOMResult> hHOMResult = new HashMap<>();
        Map<String, GSMData> hGSMData = new HashMap<>();
        Map<String, HybridData> hHybridData = new HashMap<>();
        SQLiteDB db = new SQLiteDB(db_filename);
        hHOMResult = db.getHOMResult();
        hHybridData = db.getHybridData();
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
        // System.out.println(entry.getKey() + " => " + entry.getValue());
        // }

        for (Table.Cell<String, Integer, HOMResult> cell : tHOMResult.cellSet()) {
            // System.out.println(cell.getRowKey() + "|" + cell.getColumnKey() + "|" +
            // cell.getValue());
            if (hGSMData.containsKey(cell.getRowKey())) {
                GSMData g = hGSMData.get(cell.getRowKey());
                HOMResult r1 = tHOMResult.get(cell.getRowKey(), 1);

                String plant = g.getSite_key();
                int _crop_year = g.getYear();
                int global_fiscal_year = g.getYear();
                String crop_code = "corn";
                String season = g.getSeason();
                String field = g.getTracking_number();
                String field_name = g.getPfo_name();
                String grower_name = "";
                String feature_id = g.getEntityid();
                String hybrid = g.getVariety();
                String field_supervisor = "";
                String environment = "";
                String seedsman_area = "";
                String picker = r1.getPicker();
                double total_area = r1.getTotal_area();
                double total_weight = r1.getTotal_tonrw();

                String harvest_date_01 = r1.getHarv_date();
                double harvest_date_01_area = r1.getArea_harv();
                double harvest_date_01_weight = r1.getTonrw_harv();
                double harvest_moisture_01 = r1.getMoisture();

                String harvest_date_02 = "";
                double harvest_date_02_area = 0.0;
                double harvest_date_02_weight = 0.0;
                double harvest_moisture_02 = 0.0;

                if (tHOMResult.contains(cell.getRowKey(), 2)) {
                    HOMResult r2 = tHOMResult.get(cell.getRowKey(), 2);
                    harvest_date_02 = r2.getHarv_date();
                    harvest_date_02_area = r2.getArea_harv();
                    harvest_date_02_weight = r2.getTonrw_harv();
                    harvest_moisture_02 = r2.getMoisture();
                }

                String harvest_date_03 = "";
                double harvest_date_03_area = 0.0;
                double harvest_date_03_weight = 0.0;
                double harvest_moisture_03 = 0.0;

                if (tHOMResult.contains(cell.getRowKey(), 3)) {
                    HOMResult r3 = tHOMResult.get(cell.getRowKey(), 3);
                    harvest_date_03 = r3.getHarv_date();
                    harvest_date_03_area = r3.getArea_harv();
                    harvest_date_03_weight = r3.getTonrw_harv();
                    harvest_moisture_03 = r3.getMoisture();
                }

                String harvest_date_04 = "";
                double harvest_date_04_area = 0.0;
                double harvest_date_04_weight = 0.0;
                double harvest_moisture_04 = 0.0;

                if (tHOMResult.contains(cell.getRowKey(), 4)) {
                    HOMResult r4 = tHOMResult.get(cell.getRowKey(), 4);
                    harvest_date_04 = r4.getHarv_date();
                    harvest_date_04_area = r4.getArea_harv();
                    harvest_date_04_weight = r4.getTonrw_harv();
                    harvest_moisture_04 = r4.getMoisture();
                }

                String harvest_date_05 = "";
                double harvest_date_05_area = 0.0;
                double harvest_date_05_weight = 0.0;
                double harvest_moisture_05 = 0.0;

                if (tHOMResult.contains(cell.getRowKey(), 5)) {
                    HOMResult r5 = tHOMResult.get(cell.getRowKey(), 5);
                    harvest_date_05 = r5.getHarv_date();
                    harvest_date_05_area = r5.getArea_harv();
                    harvest_date_05_weight = r5.getTonrw_harv();
                    harvest_moisture_05 = r5.getMoisture();
                }

                double drydown_rate = g.getDrydown_rate();
                double optimal_harvest_moisture_range_min = 28;
                double optimal_harvest_moisture_range_max = 35;

                if (hHybridData.containsKey(g.getVariety())) {
                    optimal_harvest_moisture_range_min = hHybridData.get(g.getVariety()).getLowest_harv_moisture();
                    optimal_harvest_moisture_range_max = hHybridData.get(g.getVariety()).getLowest_harv_moisture();

                }

                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(g.getMin_mst_harvest_date());
                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(r1.getHarv_date());

                int lateness = (int) getDateDiff(date1, date2, TimeUnit.DAYS);
                String hybrid_drying_sensitivity_classification = "B";
                String harvest_type = "ear";
                int estimated_number_of_trucks = (int) (r1.getTonrw_harv() / 23.0);
                double field_moisture = g.getMst();
                String moisture_collected_date = g.getMst_date();
                double field_lat = r1.getLat();
                double field_lon = r1.getLon();
                String wkt = g.getWkt();
                String model_timestamp = timeStamp;

                CSWOutput csw_out = new CSWOutput(country, plant, _crop_year, global_fiscal_year, crop_code, season,
                        field, field_name, grower_name, feature_id, hybrid, field_supervisor, environment,
                        seedsman_area, picker, total_area, total_weight, harvest_date_01, harvest_date_02,
                        harvest_date_03, harvest_date_04, harvest_date_05, harvest_date_01_area, harvest_date_02_area,
                        harvest_date_03_area, harvest_date_04_area, harvest_date_05_area, harvest_date_01_weight,
                        harvest_date_02_weight, harvest_date_03_weight, harvest_date_04_weight, harvest_date_05_weight,
                        harvest_moisture_01, harvest_moisture_02, harvest_moisture_03, harvest_moisture_04,
                        harvest_moisture_05, drydown_rate, optimal_harvest_moisture_range_min,
                        optimal_harvest_moisture_range_max, lateness, hybrid_drying_sensitivity_classification,
                        harvest_type, estimated_number_of_trucks, field_moisture, moisture_collected_date, field_lat,
                        field_lon, wkt, model_timestamp);

                System.out.println(csw_out);
            }

        }

    }

    /**
     * Get a diff between two dates
     * 
     * @param date1    the oldest date
     * @param date2    the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
