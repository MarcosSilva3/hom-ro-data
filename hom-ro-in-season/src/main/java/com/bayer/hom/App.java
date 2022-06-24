package com.bayer.hom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import com.fasterxml.jackson.databind.ObjectWriter;
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
        HOMParameters hom_parameters = readConfigFile("hom-config.json");

        final String log_config_file = hom_parameters.getLog_config_file();
        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        final ch.qos.logback.classic.joran.JoranConfigurator configurator = new ch.qos.logback.classic.joran.JoranConfigurator();
        final InputStream configStream = org.apache.commons.io.FileUtils.openInputStream(new File(log_config_file));
        configurator.setContext(loggerContext);
        configurator.doConfigure(configStream); // loads logback file
        configStream.close();

        final Map<String, String> env = System.getenv();
        final String client_id = env.get(hom_parameters.getEnv_client_id());
        final String client_secret = env.get(hom_parameters.getEnv_client_secret());
        final String country = hom_parameters.getCountry();
        final int year = hom_parameters.getYear();
        final int year_for_contract = hom_parameters.getYear_for_contract();
        final String season = hom_parameters.getSeason();
        final String regionCode = hom_parameters.getRegionCode();
        final String cropCycleCode = hom_parameters.getCropCycleCode();
        final String private_key_file = hom_parameters.getPrivate_key_file();
        final String project_id = hom_parameters.getProject_id();
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        final String fileNameTimeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
        final String token = new ClientToken(client_id, client_secret, log_config_file).getToken();

        Map<String, String> hAWS = new HashMap<>();
        Map<String, GSMData> hFieldsGSM = new HashMap<>();
        final Map<String, Contract> hFieldContract = new HashMap<>();
        Map<String, ProductCharacterization> hProducts = new HashMap<>();
        Map<String, ScoutWkt> hFieldsScout = new HashMap<>();
        Map<String, FieldManualPlan> hFieldsManualPlan = new HashMap<>();
        List<FieldHOM> lFieldsHOM = new ArrayList<>();
        List<Site> lSite = new ArrayList<>();

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

        // Get wkt for each entity_id.
        for (final Entry<String, GSMData> entry : hFieldsGSM.entrySet()) {
            final GSMData g = entry.getValue();
            final String entity_id = g.getEntityid();
            final ScoutWkt wkt = new ScoutWkt(entity_id, token);
            hFieldsScout.put(entity_id, wkt);
        }

        // Get product characterization data
        hProducts = (new ProductCharacterizationData(token, log_config_file, regionCode, cropCycleCode)).getHProducts();

        // Read fields from manual plan in Excel
        hFieldsManualPlan = (new ManualPlanExcel(hom_parameters.getManual_plan_excel_path())).getHFields();

        // Get the list of fields to be included in the optimzation.
        lFieldsHOM = generateFieldsHOM(hFieldsManualPlan, hFieldsGSM, hFieldContract, hFieldsScout, hFieldsPFO,
                hProducts);

        // Get list of site capacity per day.
        lSite = generateSiteCapHOM(hom_parameters);

        // Generate json
        HOMInput hom_input = new HOMInput(lSite, lFieldsHOM, hom_parameters.getHom_day_one(),
                hom_parameters.getHom_user(), hom_parameters.getHom_region(), hom_parameters.getHom_method(),
                hom_parameters.getHom_tabu_size(), hom_parameters.getHom_max_iter(), hom_parameters.getHom_max_days(),
                hom_parameters.getHom_picker_cap());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String hom_input_json = ow.writeValueAsString(hom_input);
        BufferedWriter out = new BufferedWriter(new FileWriter("hom_input.json"));
        out.write(hom_input_json);
        out.close();

        // Check the data
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

        for (final Entry<String, ProductCharacterization> entry : hProducts.entrySet()) {
            slf4jLogger.debug("[Product Characterization] {} => {}}", entry.getKey(), entry.getValue());
        }
        slf4jLogger.debug("[Product Characterization] Total number of products: {}", hProducts.size());

        for (final Entry<String, FieldManualPlan> entry : hFieldsManualPlan.entrySet()) {
            slf4jLogger.debug("[Manual Plan Excel] {} => {}", entry.getKey(), entry.getValue());
        }
        slf4jLogger.debug("[Manual Plan Excel] Total number of fields in excel: {}", hFieldsManualPlan.size());

        // Fields to be included in the optimization
        for (FieldHOM f : lFieldsHOM) {
            slf4jLogger.debug("[Fields HOM-OPT] {}", f);
        }
        slf4jLogger.debug("[Fields HOM-OPT] Total number of fields in excel: {}", lFieldsHOM.size());

    }

    /**
     * Generate site capacity for each day.
     * 
     * @param hom_parameters
     * @return
     * @throws java.text.ParseException
     */
    public static List<Site> generateSiteCapHOM(HOMParameters hom_parameters) throws java.text.ParseException {
        List<Site> lSite = new ArrayList<>();
        final int max_hys = 7;
        final double site_cap_saturday = 1000.;
        final double site_cap_sunday = 0.;
        final double site_cap = 1500.0;
        final String hom_day_one = hom_parameters.getHom_day_one();
        final int hom_max_days = hom_parameters.getHom_max_days();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(hom_day_one);
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = start.plusDays(hom_max_days);

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

            DayOfWeek dow = date.getDayOfWeek();
            String day_of_week = dow.toString();
            String site_name = "sinesti";
            int sitekey = 1299;
            int caphy_bulk = 0;
            int caphy_ear = max_hys;
            int caphy_only_ear = max_hys;
            double capton_ear = site_cap;
            double capton_bulk = 0.;
            double capton_only_ear = site_cap;
            int captrucks_ear = 100;
            int captrucks_bulk = 0;
            int captrucks_only_ear = 100;

            if (dow == DayOfWeek.SATURDAY) {
                capton_ear = site_cap_saturday;
            }

            if (dow == DayOfWeek.SUNDAY) {
                capton_ear = site_cap_sunday;
                caphy_ear = 0;
                caphy_only_ear = 0;
                captrucks_ear = 0;
                captrucks_only_ear = 0;
            }

            Site s = new Site(site_name, sitekey, caphy_bulk, caphy_ear, caphy_only_ear, capton_ear, capton_bulk,
                    capton_only_ear, captrucks_ear, captrucks_bulk, captrucks_only_ear, date.toString(), day_of_week);

            lSite.add(s);
            slf4jLogger.debug("[Site HOM] Site cap. {}", s);
        }
        return lSite;
    }

    /**
     * Create list of fields to be included in the optimization.
     * 
     * @param hFieldsManualPlan
     * @param hFieldsGSM
     * @param hFieldContract
     * @param hFieldsScout
     * @param hFieldsPFO
     * @param hProducts
     * @return
     */
    public static List<FieldHOM> generateFieldsHOM(Map<String, FieldManualPlan> hFieldsManualPlan,
            Map<String, GSMData> hFieldsGSM, Map<String, Contract> hFieldContract, Map<String, ScoutWkt> hFieldsScout,
            Map<String, FieldPFO> hFieldsPFO, Map<String, ProductCharacterization> hProducts) {
        List<FieldHOM> lFieldsHOM = new ArrayList<>();

        for (final Entry<String, FieldManualPlan> entry : hFieldsManualPlan.entrySet()) {
            String lot = entry.getKey();
            FieldManualPlan field_manual_plan = entry.getValue();
            GSMData field_gsm = new GSMData();
            Contract field_contract = new Contract();
            ScoutWkt field_scout = new ScoutWkt();
            FieldPFO field_pfo = new FieldPFO();
            ProductCharacterization product = new ProductCharacterization();
            String entityid = null;
            String abc = "B";

            boolean contains_gsm_data = false;
            boolean contains_contract_data = false;
            boolean contains_scout_data = false;
            boolean contains_product_data = false;
            boolean contains_pfo_data = false;

            String hybrid = field_manual_plan.getHybrid().toUpperCase().replaceAll("\\s+", "").replaceAll("STE", "");
            double latitude = 0.0;
            double longitude = 0.0;
            int lowest_harvest_moisture = 28;
            int highest_harvest_moisture = 33;
            int sitekey = 0;

            if (hFieldsGSM.containsKey(lot)) {
                field_gsm = hFieldsGSM.get(lot);
                entityid = field_gsm.getEntityid();
                latitude = field_gsm.getLat();
                longitude = field_gsm.getLon();
                sitekey = Integer.parseInt(field_gsm.getSite_key());
                contains_gsm_data = true;
            } else {
                slf4jLogger.error("[Fields HOM] Field {} not found in GSM output", lot);
            }

            if (entityid != null && !entityid.isEmpty() && hFieldContract.containsKey(entityid)) {
                field_contract = hFieldContract.get(entityid);
                contains_contract_data = true;
            } else {
                slf4jLogger.error("[Fields HOM] Field {} not found in Contract", lot);
            }

            if (entityid != null && !entityid.isEmpty() && hFieldsScout.containsKey(entityid)) {
                field_scout = hFieldsScout.get(entityid);
                if (latitude == 0. || longitude == 0.) {
                    latitude = field_scout.getLat();
                    longitude = field_scout.getLon();
                }
                contains_scout_data = true;
            } else {
                slf4jLogger.error("[Fields HOM] Field {} not found in Contract", lot);
            }

            if (entityid != null && !entityid.isEmpty() && hFieldsPFO.containsKey(entityid)) {
                field_pfo = hFieldsPFO.get(entityid);
                if (sitekey == 0) {
                    sitekey = Integer.parseInt(field_pfo.getPlant());
                    contains_pfo_data = true;
                }
                contains_scout_data = true;
            } else {
                slf4jLogger.error("[Fields HOM] Field {} not found in PFO", lot);
            }

            if (hProducts.containsKey(hybrid)) {
                product = hProducts.get(hybrid);
                lowest_harvest_moisture = product.getLowest_rec();
                highest_harvest_moisture = product.getHighest_rec();
                abc = product.getLowestHarvestMoisture();
            }

            String region = field_manual_plan.getRegion();
            String cluster = field_manual_plan.getPicker_group();
            String twstart = contains_gsm_data ? field_gsm.getMax_mst_harvest_date()
                    : field_manual_plan.getHarvest_window_start();
            String twend = contains_gsm_data ? field_gsm.getMin_mst_harvest_date()
                    : field_manual_plan.getHarvest_window_end();
            double area = contains_gsm_data ? field_gsm.getFf_area() : field_manual_plan.getActive_ha();
            double drydown_rate = contains_gsm_data ? field_gsm.getDrydown_rate() : 1.0;

            double tonha = field_manual_plan.getYield_ton_ha();
            double kg = tonha * area;

            String harv_type = "ear";
            FieldHOM f = new FieldHOM(lot, hybrid, sitekey, region, cluster, twstart, twend, area, drydown_rate,
                    latitude, longitude, lowest_harvest_moisture, highest_harvest_moisture, tonha, kg, abc, harv_type);
            lFieldsHOM.add(f);
        }
        return lFieldsHOM;
    }

    /**
     * Read parameters file for HOM.
     * 
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public static HOMParameters readConfigFile(String filename)
            throws FileNotFoundException, IOException, ParseException {
        final JSONParser parser = new JSONParser();
        final JSONObject o = (JSONObject) parser.parse(new FileReader(filename));
        String log_config_file = "logback-hom-ro-in-season.xml";
        String country = "Romania";
        int year = 2022;
        int year_for_contract = 2022;
        String season = "Spring";
        String private_key_file = "private_key.json";
        String project_id = "location360-datasets";
        String regionCode = "EME";
        String cropCycleCode = "2022_EME";
        String env_client_id = "ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_ID";
        String env_client_secret = "ANALYTICS_DSSO_HARVEST_OPTIMIZATION_AZURE_PROD_SECRET";
        String manual_plan_excel_path = "/mnt/Corn Harvesting plan RO 2022 v4.xlsx";
        String hom_day_one = "2022-08-01";
        String hom_user = "Domino";
        int hom_tabu_size = 1;
        int hom_max_iter = 2;
        int hom_picker_cap = 45;
        String hom_region = "romania";
        int hom_max_days = 130;
        String hom_method = "ear+bulk";

        if (o.get("log_config_file") != null) {
            log_config_file = (String) o.get("log_config_file");
        }

        if (o.get("country") != null) {
            country = (String) o.get("country");
        }

        if (o.get("year") != null) {
            year = ((Long) o.get("year")).intValue();
        }

        if (o.get("year_for_contract") != null) {
            year_for_contract = ((Long) o.get("year_for_contract")).intValue();
        }

        if (o.get("season") != null) {
            season = (String) o.get("season");
        }

        if (o.get("private_key_file") != null) {
            private_key_file = (String) o.get("private_key_file");
        }

        if (o.get("project_id") != null) {
            project_id = (String) o.get("project_id");
        }

        if (o.get("regionCode") != null) {
            regionCode = (String) o.get("regionCode");
        }

        if (o.get("cropCycleCode") != null) {
            cropCycleCode = (String) o.get("cropCycleCode");
        }

        if (o.get("cropCycleCode") != null) {
            cropCycleCode = (String) o.get("cropCycleCode");
        }

        if (o.get("env_client_id") != null) {
            env_client_id = (String) o.get("env_client_id");
        }

        if (o.get("env_client_secret") != null) {
            env_client_secret = (String) o.get("env_client_secret");
        }

        if (o.get("manual_plan_excel_path") != null) {
            manual_plan_excel_path = (String) o.get("manual_plan_excel_path");
        }

        if (o.get("hom_day_one") != null) {
            hom_day_one = (String) o.get("hom_day_one");
        }

        if (o.get("hom_user") != null) {
            hom_user = (String) o.get("hom_user");
        }

        if (o.get("hom_tabu_size") != null) {
            hom_tabu_size = ((Number) o.get("hom_tabu_size")).intValue();
        }

        if (o.get("hom_max_iter") != null) {
            hom_max_iter = ((Number) o.get("hom_max_iter")).intValue();
        }

        if (o.get("hom_picker_cap") != null) {
            hom_picker_cap = ((Number) o.get("hom_picker_cap")).intValue();
        }

        if (o.get("hom_region") != null) {
            hom_region = (String) o.get("hom_region");
        }

        if (o.get("hom_max_days") != null) {
            hom_max_days = ((Number) o.get("hom_max_days")).intValue();
        }

        if (o.get("hom_method") != null) {
            hom_method = (String) o.get("hom_method");
        }

        HOMParameters p = new HOMParameters(log_config_file, country, year, year_for_contract, season, private_key_file,
                project_id, regionCode, cropCycleCode, env_client_id, env_client_secret, manual_plan_excel_path,
                hom_day_one, hom_user, hom_tabu_size, hom_max_iter, hom_picker_cap, hom_region, hom_max_days,
                hom_method);
        return p;
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
