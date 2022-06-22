package com.bayer.hom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.spi.LogbackLock;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.AuthResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * Romania in-season
 *
 */
public class App {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);

    public static void main(String[] args) throws InterruptedException, Exception {
        final String log_config_file = "logback-hom-ro-in-season.xml";
        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        final ch.qos.logback.classic.joran.JoranConfigurator configurator = new ch.qos.logback.classic.joran.JoranConfigurator();
        final InputStream configStream = org.apache.commons.io.FileUtils.openInputStream(new File(log_config_file));
        configurator.setContext(loggerContext);
        configurator.doConfigure(configStream); // loads logback file
        configStream.close();

        final Map<String, String> env = System.getenv();
        final String client_id = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_ID");
        final String client_secret = env.get("ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_SECRET");
        final String country = "Romania";
        final int year = 2022;
        final int year_for_contract = 2022;
        final String season = "Spring";
        final String private_key_file = "private_key.json";
        final String project_id = "location360-datasets";
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        final String fileNameTimeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
        final String token = new ClientToken(client_id, client_secret, log_config_file).getToken();

        Map<String, String> hAWS = new HashMap<>();
        Map<String, GSMData> hFieldsGSM = new HashMap<>();
        final Map<String, HybridData> hHybridData = new HashMap<>();
        final Map<String, Contract> hFieldContract = new HashMap<>();

        final List<String> lSites = new ArrayList<>();

        lSites.add("1299");

        final PFOData pfo = new PFOData(lSites, token, year, season);
        final Map<String, FieldPFO> hFieldsPFO = pfo.getHFieldsPFO();

        final BQData bq = new BQData(private_key_file, project_id);
        hFieldsGSM = bq.getGSMData(country, year);

        for (final Entry<String, FieldPFO> entry : hFieldsPFO.entrySet()) {
            final String plantNumber = entry.getValue().getPlant();
            int iFieldNumber = 0;
            if (isNumeric(entry.getValue().getField())) {
                iFieldNumber = Integer.parseInt(entry.getValue().getField());
            } else {
                slf4jLogger.error("Invalid field number for field {} and site {}", entry.getKey(), plantNumber);
                continue;
            }
            final String fieldNumber = String.format("%05d", iFieldNumber);
            final ContractData contract_data = new ContractData(plantNumber, fieldNumber, year_for_contract, token,
                    log_config_file);
            hFieldContract.put(entry.getKey(), contract_data.getContract());
        }

        for (final Entry<String, GSMData> entry : hFieldsGSM.entrySet()) {
            slf4jLogger.debug("[GSM] {} => {}}", entry.getKey(), entry.getValue());
        }
        slf4jLogger.debug("[GSM] Total number of fields: {}", hFieldsGSM.size());

        for (final Entry<String, FieldPFO> entry : hFieldsPFO.entrySet()) {
            slf4jLogger.debug("[PFO] {} => {}}", entry.getKey(), entry.getValue());
        }
        slf4jLogger.debug("[PFO] Total number of fields: {}", hFieldsPFO.size());

        for (final Entry<String, Contract> entry : hFieldContract.entrySet()) {
            slf4jLogger.debug("[Contract] {} => {}}", entry.getKey(), entry.getValue());
        }
        slf4jLogger.debug("[Contract] Total number of fields: {}", hFieldContract.size());

    }

    /**
     * 
     * @param fileNameTimeStamp
     * @param hAWS
     */
    public static void saveResultsInCSW(String fileNameTimeStamp, Map<String, String> hAWS) {
        AWSCredentials credentials = new BasicAWSCredentials(hAWS.get("AccessKeyId"), hAWS.get("SecretAccessKey"));
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();

        final String csv_file = "hom_output_" + fileNameTimeStamp + ".csv";
        String bucketName = "bayer.loc360-prod.use1.envmt.gsm";
        String key = "csw/hom/" + csv_file;
        s3client.putObject(bucketName, key, new File(csv_file));
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

    /**
     * Get Location360 AWS credentials from Vault
     * 
     * @return
     * @throws VaultException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    public static Map<String, String> getAWSCredentials()
            throws VaultException, JsonMappingException, JsonProcessingException {
        Map<String, String> result = new HashMap<>();
        final Map<String, String> hAWS = new HashMap<>();
        final String vault_address = System.getenv("VAULT_URL");
        final String role_id = System.getenv("MO_VAULT_APP_ROLE_ID");
        final String secret_id = System.getenv("MO_VAULT_APP_ROLE_SECRET_ID");
        final String secrets_path = System.getenv("VAULT_PATH_LOC360_AWS_PROD");
        // final String secrets_path = System.getenv("VAULT_PATH_LOC360_AWS_NP");

        final Integer engineVersion = 1;

        final VaultConfig config = new VaultConfig().address(vault_address).build();
        final Vault vault = new Vault(config, engineVersion);
        final AuthResponse response = vault.auth().loginByAppRole(role_id, secret_id);
        final String clientAuthToken = response.getAuthClientToken();
        config.token(clientAuthToken);
        result = new HashMap<>(vault.logical().read(secrets_path).getData());

        final String jsonString = result.get("data");
        final JsonNode jn = new ObjectMapper().readTree(jsonString);
        final String AccessKeyId = jn.get("AccessKeyId").textValue();
        final String SecretAccessKey = jn.get("SecretAccessKey").textValue();
        hAWS.put("AccessKeyId", AccessKeyId);
        hAWS.put("SecretAccessKey", SecretAccessKey);
        return hAWS;
    }

    /**
     * Get results from json file.
     * 
     * @param hom_result_file_path
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public static Table<String, Integer, HOMResult> getHOMResultTable(final String hom_result_file_path)
            throws FileNotFoundException, IOException, ParseException {
        final Table<String, Integer, HOMResult> hResult = HashBasedTable.create();
        final JSONParser parser = new JSONParser();
        final JSONArray a = (JSONArray) parser.parse(new FileReader(hom_result_file_path));
        for (final Object o : a) {
            final JSONObject result = (JSONObject) o;
            final String field = (String) result.get("Field");
            final int part = Integer.parseInt(field.substring(Math.max(field.length() - 2, 0)));
            final String picker = (String) result.get("Picker");
            final String variety = (String) result.get("Variety");
            final String harv_date = (String) result.get("Harv.Date");
            final double total_area = Double.parseDouble((String) result.get("Total.Area"));
            final double area_harv = Double.parseDouble((String) result.get("Area.Harv"));
            final double total_tonrw = Double.parseDouble((String) result.get("Total.TonRW"));
            final double tonrw_harv = Double.parseDouble((String) result.get("TonRW.Harv"));
            final double lat = Double.parseDouble((String) result.get("Latitude"));
            final double lon = Double.parseDouble((String) result.get("Longitude"));
            final String grower = "NA";
            final double moisture = Double.parseDouble((String) result.get("Moisture"));
            final String tracking_number = (String) result.get("Field2");
            final int lateness = Integer.parseInt((String) result.get("Lateness"));
            final HOMResult r = new HOMResult(field, picker, variety, harv_date, total_area, area_harv, total_tonrw,
                    tonrw_harv, lat, lon, grower, moisture, tracking_number, lateness);
            hResult.put(tracking_number, part, r);
        }
        return hResult;
    }

    /**
     * Check if string looks like a number.
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(final String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

}
