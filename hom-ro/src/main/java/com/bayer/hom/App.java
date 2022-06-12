package com.bayer.hom;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.AuthResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * HOM Romania
 *
 */
public class App {
    public static void main(final String[] args)
            throws SQLException, ClientProtocolException, IOException, ParseException, java.text.ParseException {

        final Map<String, String> env = System.getenv();
        final String client_id = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_ID");
        final String client_secret = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_SECRET");

        final String country = "Romania";
        final String db_filename = "/mnt/hom-ro-result-2021-10-13-19_24_59-Domino.db";
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        final String fileNameTimeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());

        Map<String, String> hAWS = new HashMap<>();
        Map<String, GSMData> hGSMData = new HashMap<>();
        Map<String, HybridData> hHybridData = new HashMap<>();
        final SQLiteDB db = new SQLiteDB(db_filename);
        hHybridData = db.getHybridData();
        hGSMData = db.getGSMData(country);
        try {
            hAWS = getAWSCredentials();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Table<String, Integer, HOMResult> tHOMResult = HashBasedTable.create();
        tHOMResult = db.getHOMResultTable();

        final String token = new ClientToken(client_id, client_secret).getToken();

        // Get wkt for each entity_id.
        for (final Entry<String, GSMData> entry : hGSMData.entrySet()) {
            final GSMData g = entry.getValue();
            final String entity_id = g.getEntityid();
            final ScoutWkt wkt = new ScoutWkt(entity_id, token);
            g.setWkt(wkt.getWkt());
            hGSMData.put(entry.getKey(), g);
        }

        // for (Entry<String, GSMData> entry : hGSMData.entrySet()) {
        // System.out.println(entry.getKey() + " => " + entry.getValue());
        // }

        final List<CSWOutput> lCSWRows = new ArrayList<>();

        for (final Table.Cell<String, Integer, HOMResult> cell : tHOMResult.cellSet()) {
            if (hGSMData.containsKey(cell.getRowKey())) {
                final GSMData g = hGSMData.get(cell.getRowKey());
                final HOMResult r1 = tHOMResult.get(cell.getRowKey(), 1);

                final String plant = g.getSite_key();
                final int _crop_year = g.getYear();
                final int global_fiscal_year = g.getYear();
                final String crop_code = "corn";
                final String season = g.getSeason();
                final String field = g.getTracking_number();
                final String field_name = g.getPfo_name();
                final String grower_name = "";
                final String feature_id = g.getEntityid();
                final String hybrid = g.getVariety();
                final String field_supervisor = "";
                final String environment = "";
                final String seedsman_area = "";
                final String picker = r1.getPicker();
                final double total_area = r1.getTotal_area();
                final double total_weight = r1.getTotal_tonrw();

                final String harvest_date_01 = r1.getHarv_date();
                final double harvest_date_01_area = r1.getArea_harv();
                final double harvest_date_01_weight = r1.getTonrw_harv();
                final double harvest_moisture_01 = r1.getMoisture();

                String harvest_date_02 = "";
                double harvest_date_02_area = 0.0;
                double harvest_date_02_weight = 0.0;
                double harvest_moisture_02 = 0.0;

                if (tHOMResult.contains(cell.getRowKey(), 2)) {
                    final HOMResult r2 = tHOMResult.get(cell.getRowKey(), 2);
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
                    final HOMResult r3 = tHOMResult.get(cell.getRowKey(), 3);
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
                    final HOMResult r4 = tHOMResult.get(cell.getRowKey(), 4);
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
                    final HOMResult r5 = tHOMResult.get(cell.getRowKey(), 5);
                    harvest_date_05 = r5.getHarv_date();
                    harvest_date_05_area = r5.getArea_harv();
                    harvest_date_05_weight = r5.getTonrw_harv();
                    harvest_moisture_05 = r5.getMoisture();
                }

                final double drydown_rate = g.getDrydown_rate();
                double optimal_harvest_moisture_range_min = 28;
                double optimal_harvest_moisture_range_max = 35;

                if (hHybridData.containsKey(g.getVariety())) {
                    optimal_harvest_moisture_range_min = hHybridData.get(g.getVariety()).getLowest_harv_moisture();
                    optimal_harvest_moisture_range_max = hHybridData.get(g.getVariety()).getLowest_harv_moisture();
                }

                final Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(g.getMin_mst_harvest_date());
                final Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(r1.getHarv_date());
                int lateness = (int) getDateDiff(date1, date2, TimeUnit.DAYS);

                if (lateness < 0) {
                    lateness = 0;
                }

                final String hybrid_drying_sensitivity_classification = "B";
                final String harvest_type = "ear";
                final int estimated_number_of_trucks = (int) (r1.getTonrw_harv() / 23.0);
                final double field_moisture = g.getMst();
                final String moisture_collected_date = g.getMst_date();
                final double field_lat = r1.getLat();
                final double field_lon = r1.getLon();
                final String wkt = g.getWkt();
                final String model_timestamp = timeStamp;

                final CSWOutput csw_out = new CSWOutput(country, plant, _crop_year, global_fiscal_year, crop_code,
                        season, field, field_name, grower_name, feature_id, hybrid, field_supervisor, environment,
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
                lCSWRows.add(csw_out);
            }
        }

        generateCSV(lCSWRows, fileNameTimeStamp);

    }

    public static Map<String, String> getAWSCredentials()
            throws VaultException, JsonMappingException, JsonProcessingException {
        Map<String, String> result = new HashMap<>();
        Map<String, String> hAWS = new HashMap<>();
        final String vault_address = System.getenv("VAULT_URL");
        final String role_id = System.getenv("MO_VAULT_APP_ROLE_ID");
        final String secret_id = System.getenv("MO_VAULT_APP_ROLE_SECRET_ID");
        // final String secrets_path = System.getenv("VAULT_PATH_LOC360_AWS_PROD");
        final String secrets_path = System.getenv("VAULT_PATH_LOC360_AWS_NP");

        final Integer engineVersion = 1;

        final VaultConfig config = new VaultConfig().address(vault_address).build();
        final Vault vault = new Vault(config, engineVersion);
        final AuthResponse response = vault.auth().loginByAppRole(role_id, secret_id);
        final String clientAuthToken = response.getAuthClientToken();
        config.token(clientAuthToken);
        result = new HashMap<>(vault.logical().read(secrets_path).getData());

        final String jsonString = result.get("data");
        JsonNode jn = new ObjectMapper().readTree(jsonString);
        final String AccessKeyId = jn.get("AccessKeyId").textValue();
        final String SecretAccessKey = jn.get("SecretAccessKey").textValue();

        hAWS.put("AccessKeyId", AccessKeyId);
        hAWS.put("SecretAccessKey", SecretAccessKey);

        System.out.println("AccessKeyId: " + AccessKeyId);
        System.out.println("SecretAccessKey: " + SecretAccessKey);

        return hAWS;
    }

    /**
     * 
     * @param lCSWRows
     * @param fileNameTimeStamp
     * @throws IOException
     */
    public static void generateCSV(final List<CSWOutput> lCSWRows, String fileNameTimeStamp) throws IOException {
        final String csv_file = "hom_output_" + fileNameTimeStamp + ".csv";
        final FileWriter out = new FileWriter(csv_file);
        final String[] HEADERS = { "country", "plant", "crop_year", "global_fiscal_year", "crop_code", "season",
                "field", "field_name", "grower_name", "feature_id", "hybrid", "field_supervisor", "environment",
                "seedsman_area", "picker", "total_area", "total_weight", "harvest_date_01", "harvest_date_02",
                "harvest_date_03", "harvest_date_04", "harvest_date_05", "harvest_date_01_area", "harvest_date_02_area",
                "harvest_date_03_area", "harvest_date_04_area", "harvest_date_05_area", "harvest_date_01_weight",
                "harvest_date_02_weight", "harvest_date_03_weight", "harvest_date_04_weight", "harvest_date_05_weight",
                "harvest_moisture_01", "harvest_moisture_02", "harvest_moisture_03", "harvest_moisture_04",
                "harvest_moisture_05", "drydown_rate", "optimal_harvest_moisture_range_min",
                "optimal_harvest_moisture_range_max", "lateness", "hybrid_drying_sensitivity_classification",
                "harvest_type", "estimated_number_of_trucks", "field_moisture", "moisture_collected_date", "field_lat",
                "field_lon", "wkt", "model_timestamp" };

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for (final CSWOutput row : lCSWRows) {
                final String country = row.getCountry();
                final String plant = row.getPlant();
                final int crop_year = row.getCrop_year();
                final int global_fiscal_year = row.getGlobal_fiscal_year();
                final String crop_code = row.getCrop_code();
                final String season = row.getSeason();
                final String field = row.getField();
                final String field_name = row.getField_name();
                final String grower_name = row.getGrower_name();
                final String feature_id = row.getFeature_id();
                final String hybrid = row.getHybrid();
                final String field_supervisor = row.getField_supervisor();
                final String environment = row.getEnvironment();
                final String seedsman_area = row.getSeedsman_area();
                final String picker = row.getPicker();
                final double total_area = row.getTotal_area();
                final double total_weight = row.getTotal_weight();
                final String harvest_date_01 = row.getHarvest_date_01();
                final String harvest_date_02 = row.getHarvest_date_02();
                final String harvest_date_03 = row.getHarvest_date_03();
                final String harvest_date_04 = row.getHarvest_date_04();
                final String harvest_date_05 = row.getHarvest_date_05();
                final double harvest_date_01_area = row.getHarvest_date_01_area();
                final double harvest_date_02_area = row.getHarvest_date_02_area();
                final double harvest_date_03_area = row.getHarvest_date_03_area();
                final double harvest_date_04_area = row.getHarvest_date_04_area();
                final double harvest_date_05_area = row.getHarvest_date_05_area();
                final double harvest_date_01_weight = row.getHarvest_date_01_weight();
                final double harvest_date_02_weight = row.getHarvest_date_02_weight();
                final double harvest_date_03_weight = row.getHarvest_date_03_weight();
                final double harvest_date_04_weight = row.getHarvest_date_04_weight();
                final double harvest_date_05_weight = row.getHarvest_date_05_weight();
                final double harvest_moisture_01 = row.getHarvest_moisture_01();
                final double harvest_moisture_02 = row.getHarvest_moisture_02();
                final double harvest_moisture_03 = row.getHarvest_moisture_03();
                final double harvest_moisture_04 = row.getHarvest_moisture_04();
                final double harvest_moisture_05 = row.getHarvest_moisture_05();
                final double drydown_rate = row.getDrydown_rate();
                final double optimal_harvest_moisture_range_min = row.getOptimal_harvest_moisture_range_min();
                final double optimal_harvest_moisture_range_max = row.getOptimal_harvest_moisture_range_max();
                final int lateness = row.getLateness();
                final String hybrid_drying_sensitivity_classification = row
                        .getHybrid_drying_sensitivity_classification();
                final String harvest_type = row.getHarvest_type();
                final int estimated_number_of_trucks = row.getEstimated_number_of_trucks();
                final double field_moisture = row.getField_moisture();
                final String moisture_collected_date = row.getMoisture_collected_date();
                final double field_lat = row.getField_lat();
                final double field_lon = row.getField_lon();
                final String wkt = row.getWkt();
                final String model_timestamp = row.getModel_timestamp();

                printer.printRecord(country, plant, crop_year, global_fiscal_year, crop_code, season, field, field_name,
                        grower_name, feature_id, hybrid, field_supervisor, environment, seedsman_area, picker,
                        total_area, total_weight, harvest_date_01, harvest_date_02, harvest_date_03, harvest_date_04,
                        harvest_date_05, harvest_date_01_area, harvest_date_02_area, harvest_date_03_area,
                        harvest_date_04_area, harvest_date_05_area, harvest_date_01_weight, harvest_date_02_weight,
                        harvest_date_03_weight, harvest_date_04_weight, harvest_date_05_weight, harvest_moisture_01,
                        harvest_moisture_02, harvest_moisture_03, harvest_moisture_04, harvest_moisture_05,
                        drydown_rate, optimal_harvest_moisture_range_min, optimal_harvest_moisture_range_max, lateness,
                        hybrid_drying_sensitivity_classification, harvest_type, estimated_number_of_trucks,
                        field_moisture, moisture_collected_date, field_lat, field_lon, wkt, model_timestamp);

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
    public static long getDateDiff(final Date date1, final Date date2, final TimeUnit timeUnit) {
        final long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
