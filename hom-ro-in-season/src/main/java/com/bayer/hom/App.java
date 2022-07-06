package com.bayer.hom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.AuthResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.spi.LogbackLock;

/**
 * Romania in-season
 */
public class App {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);

    public static void main(final String[] args) throws InterruptedException, Exception {
        final HOMParameters hom_parameters = readConfigFile("hom-config.json");

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
        final String plantNumber = hom_parameters.getPlantNumber();
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        final String fileNameTimeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
        final String token = new ClientToken(client_id, client_secret, log_config_file).getToken();

        Map<String, String> hAWS = new HashMap<>();
        Map<String, GSMData> hFieldsGSM = new HashMap<>();
        Map<String, Contract> hFieldContract = new HashMap<>();
        Map<String, ProductCharacterization> hProducts = new HashMap<>();
        final Map<String, ScoutData> hFieldsScout = new HashMap<>();
        Map<String, FieldManualPlan> hFieldsManualPlan = new HashMap<>();
        List<FieldHOM> lFieldsHOM = new ArrayList<>();
        List<Site> lSite = new ArrayList<>();
        List<Picker> lPickers = new ArrayList<>();
        lPickers = readPickerData("hom-config.json");

        final List<String> lSites = new ArrayList<>();

        lSites.add(plantNumber);

        final PFOData pfo = new PFOData(lSites, token, year, season);
        final Map<String, FieldPFO> hFieldsPFO = pfo.getHFieldsPFO();

        final BQData bq = new BQData(private_key_file, project_id);
        hFieldsGSM = bq.getGSMData(country, year);

        final ContractData contract_data = new ContractData(plantNumber, year_for_contract, token, log_config_file);
        hFieldContract = contract_data.getHContracts();

        // Get wkt for each entity_id.
        for (final Entry<String, GSMData> entry : hFieldsGSM.entrySet()) {
            final GSMData g = entry.getValue();
            final String entity_id = g.getEntityid();
            final ScoutData wkt = new ScoutData(entity_id, token);
            hFieldsScout.put(entity_id, wkt);
        }

        // Get product characterization data
        hProducts = (new ProductCharacterizationData(token, log_config_file, regionCode, cropCycleCode)).getHProducts();

        // Read fields from manual plan in Excel or from the Database
        ManualPlan manual_plan = new ManualPlan(hom_parameters.getManual_plan_excel_path());
        manual_plan.readManualPlanExcel();
//        manual_plan.readManualPlanDB(hom_parameters);
        hFieldsManualPlan = manual_plan.getHFields();


        // Get the list of fields to be included in the optimzation.
        lFieldsHOM = generateFieldsHOM(hFieldsManualPlan, hFieldsGSM, hFieldContract, hFieldsScout, hFieldsPFO,
                hProducts);

        // Get list of site capacity per day.
        lSite = generateSiteCapHOM(hom_parameters);

        // Generate json
        final HOMInput hom_input = new HOMInput(lSite, lFieldsHOM, hom_parameters.getHom_day_one(),
                hom_parameters.getHom_user(), hom_parameters.getHom_region(), hom_parameters.getHom_method(),
                hom_parameters.getHom_tabu_size(), hom_parameters.getHom_max_iter(), hom_parameters.getHom_max_days(),
                lPickers);
        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final String hom_input_json = ow.writeValueAsString(hom_input);
        final BufferedWriter out = new BufferedWriter(new FileWriter("hom_input.json"));
        out.write(hom_input_json);
        out.close();

        // solve model
        final SolveModel solve_model = new SolveModel(hom_input_json, hom_parameters);
        solve_model.submitJob();
        String status = solve_model.getJobStatus();
        while (!status.equalsIgnoreCase("finished")) {
            status = solve_model.getJobStatus();
            TimeUnit.SECONDS.sleep(5);
        }

        // Download results from AWS S3
        final String result_file_path = get_hom_results_s3(hom_parameters, solve_model);

        // Save the results in CSW
        Table<String, Integer, HOMResult> tHOMResult = HashBasedTable.create();
        tHOMResult = getHOMResultTable(result_file_path);
        try {
            hAWS = getAWSCredentials();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final List<CSWOutput> lCSWRows = new ArrayList<>();
        final Map<String, Boolean> hVisited = new HashMap<>();
        for (final Table.Cell<String, Integer, HOMResult> cell : tHOMResult.cellSet()) {
            if (hVisited.containsKey(cell.getRowKey())) {
                continue;
            }
            if (hFieldsGSM.containsKey(cell.getRowKey())) {
                final GSMData g = hFieldsGSM.get(cell.getRowKey());
                final HOMResult r1 = tHOMResult.get(cell.getRowKey(), 1);

                final String plant = g.getSite_key();
                final int _crop_year = g.getYear();
                final int global_fiscal_year = g.getYear();
                final String crop_code = "corn";
                final String field = g.getTracking_number();
                final String field_name = g.getPfo_name();
                final String feature_id = g.getEntityid();

                String grower_name = "";
                if (hFieldContract.containsKey(cell.getRowKey())) {
                    grower_name = hFieldContract.get(cell.getRowKey()).getGrowerName();
                }

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

                if (hProducts.containsKey(g.getVariety())) {
                    optimal_harvest_moisture_range_min = hProducts.get(g.getVariety()).getLowest_rec();
                    optimal_harvest_moisture_range_max = hProducts.get(g.getVariety()).getHighest_rec();
                }

                final int lateness = r1.getLateness();
                final String hybrid_drying_sensitivity_classification = "B";
                final String harvest_type = "ear";
                final int estimated_number_of_trucks = (int) (r1.getTonrw_harv() / 23.0);
                final double field_moisture = g.getMst();
                String moisture_collected_date = g.getMst_date() != null ? g.getMst_date() : "";
                if (moisture_collected_date.equalsIgnoreCase("null")) {
                    moisture_collected_date = "";
                }
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

                slf4jLogger.debug("[HOM-Output] {}", csw_out);
                lCSWRows.add(csw_out);
                hVisited.put(cell.getRowKey(), true);
            }
        }
        generateCSV(lCSWRows, fileNameTimeStamp);
        saveResultsInCSW(fileNameTimeStamp, hAWS);
        saveGSMDataInDB(hom_parameters, hFieldsGSM);
        saveContractDataInDB(hom_parameters, hFieldContract);
        saveFieldManualPlanInDB(hom_parameters, hFieldsManualPlan);
        saveProductsInDB(hom_parameters, hProducts);
        saveScoutDataInDB(hom_parameters, hFieldsScout);
        saveFieldsHOMInDB(hom_parameters, lFieldsHOM);
        saveSiteCapacityInDB(hom_parameters, lSite);
        saveHOMResultInDB(hom_parameters, tHOMResult, hFieldContract, timeStamp);

        // Check the data
        /*
         * for (final Entry<String, GSMData> entry : hFieldsGSM.entrySet()) {
         * slf4jLogger.debug("[GSM] {} => {}}", entry.getKey(), entry.getValue()); }
         * slf4jLogger.debug("[GSM] Total number of fields: {}", hFieldsGSM.size());
         *
         * for (final Entry<String, FieldPFO> entry : hFieldsPFO.entrySet()) {
         * slf4jLogger.debug("[PFO] {} => {}}", entry.getKey(), entry.getValue()); }
         * slf4jLogger.debug("[PFO] Total number of fields: {}", hFieldsPFO.size());
         *
         * for (final Entry<String, Contract> entry : hFieldContract.entrySet()) {
         * slf4jLogger.debug("[Contract] {} => {}}", entry.getKey(), entry.getValue());
         * } slf4jLogger.debug("[Contract] Total number of fields: {}",
         * hFieldContract.size());
         *
         * for (final Entry<String, ProductCharacterization> entry :
         * hProducts.entrySet()) {
         * slf4jLogger.debug("[Product Characterization] {} => {}}", entry.getKey(),
         * entry.getValue()); }
         * slf4jLogger.debug("[Product Characterization] Total number of products: {}",
         * hProducts.size());
         *
         * for (final Entry<String, FieldManualPlan> entry :
         * hFieldsManualPlan.entrySet()) {
         * slf4jLogger.debug("[Manual Plan Excel] {} => {}", entry.getKey(),
         * entry.getValue()); }
         * slf4jLogger.debug("[Manual Plan Excel] Total number of fields in excel: {}",
         * hFieldsManualPlan.size());
         *
         * // Fields to be included in the optimization for (final FieldHOM f :
         * lFieldsHOM) { slf4jLogger.debug("[Fields HOM-OPT] {}", f); }
         * slf4jLogger.debug("[Fields HOM-OPT] Total number of fields in excel: {}",
         * lFieldsHOM.size());
         */
    }

    /**
     * Save HOM results in DB
     *
     * @param hom_parameters
     * @param tHOMResult
     * @param timeStamp
     */
    public static void saveHOMResultInDB(final HOMParameters hom_parameters,
                                         final Table<String, Integer, HOMResult> tHOMResult, final Map<String, Contract> hFieldContract,
                                         final String timeStamp) {

        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL HOMResult] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL HOMResult] Connected!");
            }
            connection.setAutoCommit(false);

            final String query = "INSERT INTO HOMResult VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            final Map<String, Boolean> hVisited = new HashMap<>();
            for (final Table.Cell<String, Integer, HOMResult> cell : tHOMResult.cellSet()) {
                if (hVisited.containsKey(cell.getRowKey())) {
                    continue;
                }
                final HOMResult r1 = tHOMResult.get(cell.getRowKey(), 1);
                slf4jLogger.debug("[MySQL HOMResult] {}", r1);

                final String tracking_number = r1.getTracking_number();
                final int part = cell.getColumnKey();
                final String field = r1.getField();
                final String picker = r1.getPicker();
                final String variety = r1.getVariety();
                final String harv_date = r1.getHarv_date();
                final double total_area = r1.getTotal_area();
                final double area_harv = r1.getArea_harv();
                final double total_tonrw = r1.getTotal_tonrw();
                final double tonrw_harv = r1.getTonrw_harv();
                final double lat = r1.getLat();
                final double lon = r1.getLon();
                String grower = r1.getGrower();
                final double moisture = r1.getMoisture();
                final int lateness = r1.getLateness();

                if (hFieldContract.containsKey(cell.getRowKey())) {
                    grower = hFieldContract.get(cell.getRowKey()).getGrowerName();
                }

                prepStmt.setString(1, tracking_number);
                prepStmt.setInt(2, part);
                prepStmt.setString(3, field);
                prepStmt.setString(4, picker);
                prepStmt.setString(5, variety);
                prepStmt.setDate(6, java.sql.Date.valueOf(harv_date));
                prepStmt.setDouble(7, total_area);
                prepStmt.setDouble(8, area_harv);
                prepStmt.setDouble(9, total_tonrw);
                prepStmt.setDouble(10, tonrw_harv);
                prepStmt.setDouble(11, lat);
                prepStmt.setDouble(12, lon);
                prepStmt.setString(13, grower);
                prepStmt.setDouble(14, moisture);
                prepStmt.setInt(15, lateness);
                prepStmt.setTimestamp(16, java.sql.Timestamp.valueOf(timeStamp));
                prepStmt.addBatch();

                if (tHOMResult.contains(cell.getRowKey(), 2)) {
                    final HOMResult r2 = tHOMResult.get(cell.getRowKey(), 2);
                    prepStmt.setInt(2, 2);
                    prepStmt.setString(3, r2.getField());
                    prepStmt.setDate(6, java.sql.Date.valueOf(r2.getHarv_date()));
                    prepStmt.setDouble(8, r2.getArea_harv());
                    prepStmt.setDouble(10, r2.getTonrw_harv());
                    prepStmt.setDouble(14, r2.getMoisture());
                    prepStmt.setInt(15, r2.getLateness());
                    prepStmt.addBatch();
                }

                if (tHOMResult.contains(cell.getRowKey(), 3)) {
                    final HOMResult r3 = tHOMResult.get(cell.getRowKey(), 3);
                    prepStmt.setInt(2, 3);
                    prepStmt.setString(3, r3.getField());
                    prepStmt.setDate(6, java.sql.Date.valueOf(r3.getHarv_date()));
                    prepStmt.setDouble(8, r3.getArea_harv());
                    prepStmt.setDouble(10, r3.getTonrw_harv());
                    prepStmt.setDouble(14, r3.getMoisture());
                    prepStmt.setInt(15, r3.getLateness());
                    prepStmt.addBatch();
                }

                if (tHOMResult.contains(cell.getRowKey(), 4)) {
                    final HOMResult r4 = tHOMResult.get(cell.getRowKey(), 4);
                    prepStmt.setInt(2, 4);
                    prepStmt.setString(3, r4.getField());
                    prepStmt.setDate(6, java.sql.Date.valueOf(r4.getHarv_date()));
                    prepStmt.setDouble(8, r4.getArea_harv());
                    prepStmt.setDouble(10, r4.getTonrw_harv());
                    prepStmt.setDouble(14, r4.getMoisture());
                    prepStmt.setInt(15, r4.getLateness());
                    prepStmt.addBatch();
                }

                if (tHOMResult.contains(cell.getRowKey(), 5)) {
                    final HOMResult r5 = tHOMResult.get(cell.getRowKey(), 5);
                    prepStmt.setInt(2, 5);
                    prepStmt.setString(3, r5.getField());
                    prepStmt.setDate(6, java.sql.Date.valueOf(r5.getHarv_date()));
                    prepStmt.setDouble(8, r5.getArea_harv());
                    prepStmt.setDouble(10, r5.getTonrw_harv());
                    prepStmt.setDouble(14, r5.getMoisture());
                    prepStmt.setInt(15, r5.getLateness());
                    prepStmt.addBatch();
                }

                hVisited.put(cell.getRowKey(), true);
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL HOMResult] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL HOMResult] Execution {} successful: {}", String.format("%d", i),
                            String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Save site capacity in DB
     *
     * @param hom_parameters
     * @param lSite
     */
    public static void saveSiteCapacityInDB(final HOMParameters hom_parameters, final List<Site> lSite) {
        Connection connection = null;
        Statement _deleteTableDtataStmt = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL Site] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL Site] Connected!");
            }
            connection.setAutoCommit(false);

            // Clear table data first.
            _deleteTableDtataStmt = connection.createStatement();
            final String _deleteTableData = "TRUNCATE TABLE Site";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);

            final String query = "INSERT INTO Site VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            for (final Site d : lSite) {
                slf4jLogger.debug("[MySQL lSite] {}", d);

                final String site_name = d.getSite_name();
                final int sitekey = d.getSitekey();
                final int caphy_bulk = d.getCaphy_bulk();
                final int caphy_ear = d.getCaphy_ear();
                final int caphy_only_ear = d.getCaphy_only_ear();
                final double capton_ear = d.getCapton_ear();
                final double capton_bulk = d.getCapton_bulk();
                final double capton_only_ear = d.getCapton_only_ear();
                final int captrucks_ear = d.getCaptrucks_ear();
                final int captrucks_bulk = d.getCaptrucks_bulk();
                final int captrucks_only_ear = d.getCaptrucks_only_ear();
                final String date = d.getDate();
                final String day_of_week = d.getDay_of_week();

                prepStmt.setString(1, site_name);
                prepStmt.setInt(2, sitekey);
                prepStmt.setInt(3, caphy_bulk);
                prepStmt.setInt(4, caphy_ear);
                prepStmt.setInt(5, caphy_only_ear);
                prepStmt.setDouble(6, capton_ear);
                prepStmt.setDouble(7, capton_bulk);
                prepStmt.setDouble(8, capton_only_ear);
                prepStmt.setInt(9, captrucks_ear);
                prepStmt.setInt(10, captrucks_bulk);
                prepStmt.setInt(11, captrucks_only_ear);
                prepStmt.setDate(12, java.sql.Date.valueOf(date));
                prepStmt.setString(13, day_of_week);
                prepStmt.addBatch();
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL Site] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL Site] Execution {} successful: {}", String.format("%d", i),
                            String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Save fields used in HOM in the database
     *
     * @param hom_parameters
     * @param lFieldsHOM
     */
    public static void saveFieldsHOMInDB(final HOMParameters hom_parameters, final List<FieldHOM> lFieldsHOM) {
        Connection connection = null;
        Statement _deleteTableDtataStmt = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL FieldHOM] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL FieldHOM] Connected!");
            }
            connection.setAutoCommit(false);

            // Clear table data first.
            _deleteTableDtataStmt = connection.createStatement();
            final String _deleteTableData = "TRUNCATE TABLE FieldHOM";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);

            final String query = "INSERT INTO FieldHOM VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            for (final FieldHOM d : lFieldsHOM) {
                slf4jLogger.debug("[MySQL FieldHOM] {}", d);

                final String lot = d.getLot();
                final String hybrid = d.getHybrid();
                final int sitekey = d.getSitekey();
                final String region = d.getRegion();
                final String cluster = d.getCluster();
                final String twstart = d.getTwstart();
                final String twend = d.getTwend();
                final double area = d.getArea();
                final double drydown_rate = d.getDrydown_rate();
                final double latitude = d.getLatitude();
                final double longitude = d.getLongitude();
                final int lowest_harvest_moisture = d.getLowest_harvest_moisture();
                final int highest_harvest_moisture = d.getHighest_harvest_moisture();
                final double tonha = d.getTonha();
                final double kg = d.getKg();
                final String abc = d.getAbc();
                final String harv_type = d.getHarv_type();

                prepStmt.setString(1, lot);
                prepStmt.setString(2, hybrid);
                prepStmt.setInt(3, sitekey);
                prepStmt.setString(4, region);
                prepStmt.setString(5, cluster);
                prepStmt.setDate(6, java.sql.Date.valueOf(twstart));
                prepStmt.setDate(7, java.sql.Date.valueOf(twend));
                prepStmt.setDouble(8, area);
                prepStmt.setDouble(9, drydown_rate);
                prepStmt.setDouble(10, latitude);
                prepStmt.setDouble(11, longitude);
                prepStmt.setInt(12, lowest_harvest_moisture);
                prepStmt.setInt(13, highest_harvest_moisture);
                prepStmt.setDouble(14, tonha);
                prepStmt.setDouble(15, kg);
                prepStmt.setString(16, abc);
                prepStmt.setString(17, harv_type);
                prepStmt.addBatch();
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL FieldHOM] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL FieldHOM] Execution {} successful: {}", String.format("%d", i),
                            String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Save Scout data in DB
     *
     * @param hom_parameters
     * @param hFieldsScout
     */
    public static void saveScoutDataInDB(final HOMParameters hom_parameters,
                                         final Map<String, ScoutData> hFieldsScout) {
        Connection connection = null;
        Statement _deleteTableDtataStmt = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL ScoutData] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL ScoutData] Connected!");
            }
            connection.setAutoCommit(false);

            // Clear table data first.
            _deleteTableDtataStmt = connection.createStatement();
            final String _deleteTableData = "TRUNCATE TABLE ScoutData";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);

            final String query = "INSERT INTO ScoutData VALUES (?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            for (final Entry<String, ScoutData> entry : hFieldsScout.entrySet()) {
                final ScoutData d = entry.getValue();
                slf4jLogger.debug("[MySQL ScoutData] {}", d);

                final String entity_id = d.getEntity_id();
                final String wkt = d.getWkt();
                final double lat = d.getLat();
                final double lon = d.getLon();
                final double yield = d.getYield();

                prepStmt.setString(1, entity_id);
                prepStmt.setString(2, wkt);
                prepStmt.setDouble(3, lat);
                prepStmt.setDouble(4, lon);
                prepStmt.setDouble(5, yield);
                prepStmt.addBatch();
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL ScoutData] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL ScoutData] Execution {} successful: {}", String.format("%d", i),
                            String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Save Product Characterization data in DB
     *
     * @param hom_parameters
     * @param hProducts
     */
    public static void saveProductsInDB(final HOMParameters hom_parameters,
                                        final Map<String, ProductCharacterization> hProducts) {
        Connection connection = null;
        Statement _deleteTableDtataStmt = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL ProductCharacterization] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL ProductCharacterization] Connected!");
            }
            connection.setAutoCommit(false);

            // Clear table data first.
            _deleteTableDtataStmt = connection.createStatement();
            final String _deleteTableData = "TRUNCATE TABLE ProductCharacterization";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);

            final String query = "INSERT INTO ProductCharacterization VALUES (?,?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            for (final Entry<String, ProductCharacterization> entry : hProducts.entrySet()) {
                final ProductCharacterization d = entry.getValue();
                slf4jLogger.debug("[MySQL ProductCharacterization] {}", d);

                final String name = d.getName();
                final String highestHarvestMoisture = d.getHighestHarvestMoisture();
                final String lowestHarvestMoisture = d.getLowestHarvestMoisture();
                final String huskingDifficulty = d.getHuskingDifficulty();
                final int lowest_rec = d.getLowest_rec();
                final int highest_rec = d.getHighest_rec();

                prepStmt.setString(1, name);
                prepStmt.setString(2, highestHarvestMoisture);
                prepStmt.setString(3, lowestHarvestMoisture);
                prepStmt.setString(4, huskingDifficulty);
                prepStmt.setInt(5, lowest_rec);
                prepStmt.setInt(6, highest_rec);
                prepStmt.addBatch();
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL ProductCharacterization] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL ProductCharacterization] Execution {} successful: {}",
                            String.format("%d", i), String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Save list of fields in manual plan in DB
     *
     * @param hom_parameters
     * @param hFieldsManualPlan
     */
    public static void saveFieldManualPlanInDB(final HOMParameters hom_parameters,
                                               final Map<String, FieldManualPlan> hFieldsManualPlan) {
        Connection connection = null;
        Statement _deleteTableDtataStmt = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL FieldManualPlan] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL FieldManualPlan] Connected!");
            }
            connection.setAutoCommit(false);

            // Clear table data first.
            _deleteTableDtataStmt = connection.createStatement();
            final String _deleteTableData = "TRUNCATE TABLE FieldManualPlan";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);

            final String query = "INSERT INTO FieldManualPlan VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            for (final Entry<String, FieldManualPlan> entry : hFieldsManualPlan.entrySet()) {
                final FieldManualPlan d = entry.getValue();
                slf4jLogger.debug("[MySQL FieldManualPlan] {}", d);

                final String region = d.getRegion();
                final String seed_plant = d.getSeed_plant();
                final String dh_qualifyed = d.getDh_qualifyed();
                final String grower = d.getGrower();
                final String tracking_number = d.getTracking_number();
                final String hybrid = d.getHybrid();
                final String suspect = d.getSuspect();
                final String suspect_comments = d.getSuspect_comments();
                final String husking_difficulty = d.getHusking_difficulty();
                final String female = d.getFemale();
                final String male = d.getMale();
                final double active_ha = d.getActive_ha();
                final double yield_ton_ha = d.getYield_ton_ha();
                final String picker_group = d.getPicker_group();
                final String harvest_date = d.getHarvest_date();
                final String harvest_window_start = d.getHarvest_window_start();
                final String harvest_window_end = d.getHarvest_window_end();

                prepStmt.setString(1, region);
                prepStmt.setString(2, seed_plant);
                prepStmt.setString(3, dh_qualifyed);
                prepStmt.setString(4, grower);
                prepStmt.setString(5, tracking_number);
                prepStmt.setString(6, hybrid);
                prepStmt.setString(7, suspect);
                prepStmt.setString(8, suspect_comments);
                prepStmt.setString(9, husking_difficulty);
                prepStmt.setString(10, female);
                prepStmt.setString(11, male);
                prepStmt.setDouble(12, active_ha);
                prepStmt.setDouble(13, yield_ton_ha);
                prepStmt.setString(14, picker_group);
                prepStmt.setDate(15, java.sql.Date.valueOf(harvest_date));
                prepStmt.setDate(16, java.sql.Date.valueOf(harvest_window_start));
                prepStmt.setDate(17, java.sql.Date.valueOf(harvest_window_end));
                prepStmt.addBatch();
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL FieldManualPlan] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL FieldManualPlan] Execution {} successful: {}", String.format("%d", i),
                            String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Add contract data in MySQL database
     *
     * @param hom_parameters
     * @param hFieldContract
     */
    public static void saveContractDataInDB(final HOMParameters hom_parameters,
                                            final Map<String, Contract> hFieldContract) {
        Connection connection = null;
        Statement _deleteTableDtataStmt = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL Contract] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL Contract] Connected!");
            }
            connection.setAutoCommit(false);

            // Clear table data first.
            _deleteTableDtataStmt = connection.createStatement();
            final String _deleteTableData = "TRUNCATE TABLE Contract";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);

            final String query = "INSERT INTO Contract VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            for (final Entry<String, Contract> entry : hFieldContract.entrySet()) {
                final Contract d = entry.getValue();
                slf4jLogger.debug("[MySQL Contract] {}", d);
                final String contractNumber = d.getContractNumber();
                final String plantNumber = d.getPlantNumber();
                final String year = d.getYear();
                final String fieldNumber = d.getFieldNumber();
                final String growerName = d.getGrowerName();
                final String acronym = d.getAcronym();
                final String trackingNumber = d.getTrackingNumber();
                String lastHarvestReceiptDate = d.getLastHarvestReceiptDate();
                if (lastHarvestReceiptDate == null || lastHarvestReceiptDate.equalsIgnoreCase("null")) {
                    lastHarvestReceiptDate = "";
                }
                final String contractLineDeleteFlag = d.getContractLineDeleteFlag();
                final double harvestedFemaleArea = d.getHarvestedFemaleArea();
                final double moisturePercentage = d.getMoisturePercentage();

                prepStmt.setString(1, contractNumber);
                prepStmt.setString(2, plantNumber);
                prepStmt.setString(3, year);
                prepStmt.setString(4, fieldNumber);
                prepStmt.setString(5, growerName);
                prepStmt.setString(6, acronym);
                prepStmt.setString(7, trackingNumber);
                prepStmt.setString(8, lastHarvestReceiptDate);
                prepStmt.setString(9, contractLineDeleteFlag);
                prepStmt.setDouble(10, harvestedFemaleArea);
                prepStmt.setDouble(11, moisturePercentage);
                prepStmt.addBatch();
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL Contract] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL Contract] Execution {} successful: {}", String.format("%d", i),
                            String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Insert GSM data in database
     *
     * @param hom_parameters
     * @param hFieldsGSM
     */
    public static void saveGSMDataInDB(final HOMParameters hom_parameters, final Map<String, GSMData> hFieldsGSM) {
        Connection connection = null;
        Statement _deleteTableDtataStmt = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL GSM] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL GSM] Connected!");
            }
            connection.setAutoCommit(false);

            // Clear table data first.
            _deleteTableDtataStmt = connection.createStatement();
            final String _deleteTableData = "TRUNCATE TABLE GSMData";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);

            final String query = "INSERT INTO GSMData VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            final PreparedStatement prepStmt = connection.prepareStatement(query);

            for (final Entry<String, GSMData> entry : hFieldsGSM.entrySet()) {
                final GSMData d = entry.getValue();
                slf4jLogger.debug("[MySQL GSM] {}", d);
                final String entityid = d.getEntityid();
                final String production_field_id = d.getProduction_field_id();
                final String country = d.getCountry();
                final String site_key = d.getSite_key();
                final String plant = d.getPlant();
                final String field_num = d.getField_num();
                final int planting_area_id = d.getPlanting_area_id();
                final String planting_area_name = d.getPlanting_area_name();
                final String macrozone = d.getMacrozone();
                final String seedsman_area = d.getSeedsman_area();
                final String pfo_name = d.getPfo_name();
                final String user_group = d.getUser_group();
                final String tracking_number = d.getTracking_number();
                final String contract_number = d.getContract_number();
                final int year = d.getYear();
                final String season = d.getSeason();

                String sPlantingDate = d.getPlanting_date();
                if (sPlantingDate == null || sPlantingDate.equalsIgnoreCase("null")) {
                    sPlantingDate = "0000-00-00";
                }
                final Date planting_date = new SimpleDateFormat("yyyy-MM-dd").parse(sPlantingDate);

                final String planting_window = d.getPlanting_window();
                final String female_fertile = d.getFemale_fertile();
                final String female_sterile = d.getFemale_sterile();
                final String variety = d.getVariety();
                final double planted_surface = d.getPlanted_surface();
                final double tot_female_area = d.getTot_female_area();
                final double ff_area = d.getFf_area();
                final double fs_area = d.getFs_area();
                final double tot_area = d.getTot_area();
                final String area_uom = d.getArea_uom();
                final String growth_stage = d.getGrowth_stage();
                final double gdu_curr = d.getGdu_curr();

                String sMoist35Date = d.getMoist35_date();
                if (sMoist35Date == null || sMoist35Date.equalsIgnoreCase("null")) {
                    sMoist35Date = "0000-00-00";
                }
                final Date moist35_date = new SimpleDateFormat("yyyy-MM-dd").parse(sMoist35Date);

                final double drydown_rate = d.getDrydown_rate();
                final double mst = d.getMst();

                String sMstDate = d.getMst_date();
                if (sMstDate == null || sMstDate.equalsIgnoreCase("null")) {
                    sMstDate = "0000-00-00";
                }
                final Date mst_date = new SimpleDateFormat("yyyy-MM-dd").parse(sMstDate);

                final double mst_imputed = d.getMst_imputed();
                final double mst_imputed_field = d.getMst_imputed_field();

                String sOptimalMstHarvestDate = d.getOptimal_mst_harvest_date();
                if (sOptimalMstHarvestDate == null || sOptimalMstHarvestDate.equalsIgnoreCase("null")) {
                    sOptimalMstHarvestDate = "0000-00-00";
                }
                final Date optimal_mst_harvest_date = new SimpleDateFormat("yyyy-MM-dd").parse(sOptimalMstHarvestDate);

                String sMinMstHarvestDate = d.getMin_mst_harvest_date();
                if (sMinMstHarvestDate == null || sMinMstHarvestDate.equalsIgnoreCase("null")) {
                    sMinMstHarvestDate = "0000-00-00";
                }
                final Date min_mst_harvest_date = new SimpleDateFormat("yyyy-MM-dd").parse(sMinMstHarvestDate);

                String sMaxMstHarvestDate = d.getMax_mst_harvest_date();
                if (sMaxMstHarvestDate == null || sMaxMstHarvestDate.equalsIgnoreCase("null")) {
                    sMaxMstHarvestDate = "0000-00-00";
                }
                final Date max_mst_harvest_date = new SimpleDateFormat("yyyy-MM-dd").parse(sMaxMstHarvestDate);

                final double lat = d.getLat();
                final double lon = d.getLon();

                String sReportDate = d.getReport_date();
                if (sReportDate == null || sReportDate.equalsIgnoreCase("null")) {
                    sReportDate = "0000-00-00";
                }
                final Date report_date = new SimpleDateFormat("yyyy-MM-dd").parse(sReportDate);

                final String region = d.getRegion();
                final String wkt = d.getWkt();

                prepStmt.setString(1, entityid);
                prepStmt.setString(2, production_field_id);
                prepStmt.setString(3, country);
                prepStmt.setString(4, site_key);
                prepStmt.setString(5, plant);
                prepStmt.setString(6, field_num);
                prepStmt.setInt(7, planting_area_id);
                prepStmt.setString(8, planting_area_name);
                prepStmt.setString(9, macrozone);
                prepStmt.setString(10, seedsman_area);
                prepStmt.setString(11, pfo_name);
                prepStmt.setString(12, user_group);
                prepStmt.setString(13, tracking_number);
                prepStmt.setString(14, contract_number);
                prepStmt.setInt(15, year);
                prepStmt.setString(16, season);
                prepStmt.setDate(17, new java.sql.Date(planting_date.getTime()));
                prepStmt.setString(18, planting_window);
                prepStmt.setString(19, female_fertile);
                prepStmt.setString(20, female_sterile);
                prepStmt.setString(21, variety);
                prepStmt.setDouble(22, planted_surface);
                prepStmt.setDouble(23, tot_female_area);
                prepStmt.setDouble(24, ff_area);
                prepStmt.setDouble(25, fs_area);
                prepStmt.setDouble(26, tot_area);
                prepStmt.setString(27, area_uom);
                prepStmt.setString(28, growth_stage);
                prepStmt.setDouble(29, gdu_curr);
                prepStmt.setDate(30, new java.sql.Date(moist35_date.getTime()));
                prepStmt.setDouble(31, drydown_rate);
                prepStmt.setDouble(32, mst);
                prepStmt.setDate(33, new java.sql.Date(mst_date.getTime()));
                prepStmt.setDouble(34, mst_imputed);
                prepStmt.setDouble(35, mst_imputed_field);
                prepStmt.setDate(36, new java.sql.Date(optimal_mst_harvest_date.getTime()));
                prepStmt.setDate(37, new java.sql.Date(min_mst_harvest_date.getTime()));
                prepStmt.setDate(38, new java.sql.Date(max_mst_harvest_date.getTime()));
                prepStmt.setDouble(39, lat);
                prepStmt.setDouble(40, lon);
                prepStmt.setDate(41, new java.sql.Date(report_date.getTime()));
                prepStmt.setString(42, region);
                prepStmt.setString(43, wkt);
                prepStmt.addBatch();
            }

            final int[] numUpdates = prepStmt.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    slf4jLogger.debug("[MySQL GSM] Execution {}: unknown number of rows updated",
                            String.format("%d", i));
                else
                    slf4jLogger.debug("[MySQL GSM] Execution {} successful: {}", String.format("%d", i),
                            String.format("%d", numUpdates[i]));
            }
            connection.commit();
            prepStmt.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Read list of pickers in parameters file.
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public static List<Picker> readPickerData(final String filename)
            throws FileNotFoundException, IOException, ParseException {
        final List<Picker> lPickers = new ArrayList<>();
        final JSONParser parser = new JSONParser();
        final JSONObject o = (JSONObject) parser.parse(new FileReader(filename));

        if (o.containsKey("pickers")) {
            final JSONArray pickers = (JSONArray) o.get("pickers");
            final Iterator i = pickers.iterator();
            while (i.hasNext()) {
                final JSONObject picker = (JSONObject) i.next();
                final String id = (String) picker.get("id");
                final String type = (String) picker.get("type");
                final double harvest_capacity = ((Number) picker.get("harvest_capacity")).doubleValue();
                lPickers.add(new Picker(id, type, harvest_capacity));
            }
        } else {
            slf4jLogger.error("[Parameters] Pickers not informed in parameters file.");
        }
        return lPickers;
    }

    /**
     * Download result file in JSON format from AWS S3
     *
     * @param hom_parameters
     * @param model
     * @return result file path in JSON format
     * @throws IOException
     */
    public static String get_hom_results_s3(final HOMParameters hom_parameters, final SolveModel model)
            throws IOException {
        final Map<String, String> env = System.getenv();
        final String AccessKeyId = env.get("AWS_ID_PH");
        final String SecretAccessKey = env.get("AWS_PWD_PH");
        final String timestamp = model.getTimestamp();
        final String user = model.getUser();
        final int jobid = model.getJobid();
        final String result_file = "hom-result-" + timestamp + "-jobid-" + Integer.toString(jobid) + "-" + user
                + ".json";
        final AWSCredentials credentials = new BasicAWSCredentials(AccessKeyId, SecretAccessKey);
        final AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
        final String bucketName = hom_parameters.getAwsBucketName();
        final String key = "harvest-opt/output/archive/json/" + result_file;
        slf4jLogger.debug("[AWS] Result key: {}", key);
        final S3Object s3object = s3client.getObject(bucketName, key);
        final S3ObjectInputStream inputStream = s3object.getObjectContent();
        System.out.println(inputStream);
        // FileUtils.copyInputStreamToFile(inputStream, new File(hom_parameters.getWork_dir() + result_file));
        FileUtils.copyInputStreamToFile(inputStream, new File(hom_parameters.getHom_result_file()));
//        final String result_file_path = hom_parameters.getWork_dir() + result_file;
        final String result_file_path = hom_parameters.getHom_result_file();
        return result_file_path;
    }

    /**
     * Generate site capacity for each day.
     *
     * @param hom_parameters
     * @return
     * @throws java.text.ParseException
     */
    public static List<Site> generateSiteCapHOM(final HOMParameters hom_parameters) throws java.text.ParseException {
        final List<Site> lSite = new ArrayList<>();
        final int max_hys = 7;
        final double site_cap_saturday = 1000.;
        final double site_cap_sunday = 0.;
        final double site_cap = 1500.0;
        final String hom_day_one = hom_parameters.getHom_day_one();
        final int hom_max_days = hom_parameters.getHom_max_days();

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Date startDate = formatter.parse(hom_day_one);
        final LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final LocalDate end = start.plusDays(hom_max_days);

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

            final DayOfWeek dow = date.getDayOfWeek();
            final String day_of_week = dow.toString();
            final String site_name = "sinesti";
            final int sitekey = 1299;
            final int caphy_bulk = 0;
            int caphy_ear = max_hys;
            int caphy_only_ear = max_hys;
            double capton_ear = site_cap;
            final double capton_bulk = 0.;
            double capton_only_ear = site_cap;
            int captrucks_ear = 100;
            final int captrucks_bulk = 0;
            int captrucks_only_ear = 100;

            if (dow == DayOfWeek.SATURDAY) {
                capton_ear = site_cap_saturday;
            }

            if (dow == DayOfWeek.SUNDAY) {
                capton_ear = site_cap_sunday;
                capton_only_ear = site_cap_sunday;
                caphy_ear = 0;
                caphy_only_ear = 0;
                captrucks_ear = 0;
                captrucks_only_ear = 0;
            }

            final Site s = new Site(site_name, sitekey, caphy_bulk, caphy_ear, caphy_only_ear, capton_ear, capton_bulk,
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
    public static List<FieldHOM> generateFieldsHOM(final Map<String, FieldManualPlan> hFieldsManualPlan,
                                                   final Map<String, GSMData> hFieldsGSM, final Map<String, Contract> hFieldContract,
                                                   final Map<String, ScoutData> hFieldsScout, final Map<String, FieldPFO> hFieldsPFO,
                                                   final Map<String, ProductCharacterization> hProducts) {
        final List<FieldHOM> lFieldsHOM = new ArrayList<>();

        for (final Entry<String, FieldManualPlan> entry : hFieldsManualPlan.entrySet()) {
            final String lot = entry.getKey();
            final FieldManualPlan field_manual_plan = entry.getValue();
            GSMData field_gsm = new GSMData();
            Contract field_contract = new Contract();
            ScoutData field_scout = new ScoutData();
            FieldPFO field_pfo = new FieldPFO();
            ProductCharacterization product = new ProductCharacterization();
            String entityid = null;
            String abc = "B";

            boolean contains_gsm_data = false;
            boolean contains_contract_data = false;
            boolean contains_scout_data = false;
            final boolean contains_product_data = false;
            boolean contains_pfo_data = false;

            final String hybrid = field_manual_plan.getHybrid().toUpperCase().replaceAll("\\s+", "").replaceAll("STE",
                    "");
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

            if (entityid != null && !entityid.isEmpty() && hFieldContract.containsKey(lot)) {
                field_contract = hFieldContract.get(lot);
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
            } else {
                slf4jLogger.error("[Fields HOM] Field {} not found in PFO", lot);
            }

            if (hProducts.containsKey(hybrid)) {
                product = hProducts.get(hybrid);
                lowest_harvest_moisture = product.getLowest_rec();
                highest_harvest_moisture = product.getHighest_rec();
                abc = product.getLowestHarvestMoisture();
            }

            final String region = field_manual_plan.getRegion();
            final String cluster = field_manual_plan.getPicker_group();

            /*
             * String twstart = contains_gsm_data ? field_gsm.getMax_mst_harvest_date() :
             * field_manual_plan.getHarvest_window_start(); String twend = contains_gsm_data
             * ? field_gsm.getMin_mst_harvest_date() :
             * field_manual_plan.getHarvest_window_end();
             */
            // Use excel for now while we validate GSM output.
            final String twstart = field_manual_plan.getHarvest_window_start();
            final String twend = field_manual_plan.getHarvest_window_end();

            // double area = contains_gsm_data ? field_gsm.getFf_area() :
            // field_manual_plan.getActive_ha();
            // use manual plan in Excel for now since looks like we have some issues with
            // the area.
            final double area = field_manual_plan.getActive_ha();
            if (area <= 0 || (latitude == 0.0 && longitude == 0.0)) {
                slf4jLogger.error(
                        "[Fields HOM] Field {} removed from the optimization due to missing data (lat, lon, area, ....)",
                        lot);
                continue;
            }

            final double drydown_rate = contains_gsm_data ? field_gsm.getDrydown_rate() : 1.0;
            double tonha = field_manual_plan.getYield_ton_ha();
            if (contains_scout_data && field_scout.getYield() > 0) {
                tonha = field_scout.getYield();
            }
            final double kg = tonha * area;
            final String harv_type = "ear";
            final FieldHOM f = new FieldHOM(lot, hybrid, sitekey, region, cluster, twstart, twend, area, drydown_rate,
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
    public static HOMParameters readConfigFile(final String filename)
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
        String clientIdEngine = "HOM_ENGINE_CLIENT_ID";
        String clientSecretEngine = "HOM_ENGINE_CLIENT_SECRET";
        String awsBucketName = "romania-models";
        String plantNumber = "1299";

        String env_hom_db_host = "HOM_DB_HOST";
        String env_hom_db_port = "HOM_DB_PORT";
        String env_hom_db_user = "HOM_DB_USER";
        String env_hom_db_pwd = "HOM_DB_PWD";
        String hom_db_name = "hom_romania";
        String work_dir = "/mnt/";
        String hom_result_file = "hom_result.json";

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

        if (o.get("clientIdEngine") != null) {
            clientIdEngine = (String) o.get("clientIdEngine");
        }

        if (o.get("clientSecretEngine") != null) {
            clientSecretEngine = (String) o.get("clientSecretEngine");
        }

        if (o.get("awsBucketName") != null) {
            awsBucketName = (String) o.get("awsBucketName");
        }

        if (o.get("plantNumber") != null) {
            plantNumber = (String) o.get("plantNumber");
        }

        if (o.get("env_hom_db_host") != null) {
            env_hom_db_host = (String) o.get("env_hom_db_host");
        }

        if (o.get("env_hom_db_port") != null) {
            env_hom_db_port = (String) o.get("env_hom_db_port");
        }

        if (o.get("env_hom_db_user") != null) {
            env_hom_db_user = (String) o.get("env_hom_db_user");
        }

        if (o.get("env_hom_db_pwd") != null) {
            env_hom_db_pwd = (String) o.get("env_hom_db_pwd");
        }

        if (o.get("hom_db_name") != null) {
            hom_db_name = (String) o.get("hom_db_name");
        }

        if (o.get("work_dir") != null) {
            work_dir = (String) o.get("work_dir");
        }

        if (o.get("hom_result_file") != null) {
            work_dir = (String) o.get("hom_result_file");
        }

        final HOMParameters p = new HOMParameters(log_config_file, country, year, year_for_contract, season,
                private_key_file, project_id, regionCode, cropCycleCode, env_client_id, env_client_secret,
                manual_plan_excel_path, hom_day_one, hom_user, hom_tabu_size, hom_max_iter, hom_picker_cap, hom_region,
                hom_max_days, hom_method, clientIdEngine, clientSecretEngine, awsBucketName, plantNumber,
                env_hom_db_host, env_hom_db_port, env_hom_db_user, env_hom_db_pwd, hom_db_name, work_dir, hom_result_file);
        return p;
    }

    /**
     * @param fileNameTimeStamp
     * @param hAWS
     */
    public static void saveResultsInCSW(final String fileNameTimeStamp, final Map<String, String> hAWS) {
        final AWSCredentials credentials = new BasicAWSCredentials(hAWS.get("AccessKeyId"),
                hAWS.get("SecretAccessKey"));
        final AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();

        final String csv_file = "hom_output_" + fileNameTimeStamp + ".csv";
        final String bucketName = "bayer.loc360-prod.use1.envmt.gsm";
        final String key = "csw/hom/" + csv_file;
        s3client.putObject(bucketName, key, new File(csv_file));
    }

    /**
     * @param lCSWRows
     * @param fileNameTimeStamp
     * @throws IOException
     */
    public static void generateCSV(final List<CSWOutput> lCSWRows, final String fileNameTimeStamp) throws IOException {
        final String csv_file = "hom_output_" + fileNameTimeStamp + ".csv";
        final FileWriter out = new FileWriter(csv_file);
        final String[] HEADERS = {"country", "plant", "crop_year", "global_fiscal_year", "crop_code", "season",
                "field", "field_name", "grower_name", "feature_id", "hybrid", "field_supervisor", "environment",
                "seedsman_area", "picker", "total_area", "total_weight", "harvest_date_01", "harvest_date_02",
                "harvest_date_03", "harvest_date_04", "harvest_date_05", "harvest_date_01_area", "harvest_date_02_area",
                "harvest_date_03_area", "harvest_date_04_area", "harvest_date_05_area", "harvest_date_01_weight",
                "harvest_date_02_weight", "harvest_date_03_weight", "harvest_date_04_weight", "harvest_date_05_weight",
                "harvest_moisture_01", "harvest_moisture_02", "harvest_moisture_03", "harvest_moisture_04",
                "harvest_moisture_05", "drydown_rate", "optimal_harvest_moisture_range_min",
                "optimal_harvest_moisture_range_max", "lateness", "hybrid_drying_sensitivity_classification",
                "harvest_type", "estimated_number_of_trucks", "field_moisture", "moisture_collected_date", "field_lat",
                "field_lon", "wkt", "model_timestamp"};

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
            final String field = (String) result.get("field");
            final int part = Integer.parseInt(field.substring(Math.max(field.length() - 2, 0)));
            final String picker = (String) result.get("picker");
            final String variety = (String) result.get("variety");
            final String harv_date = (String) result.get("harv_date");
            final double total_area = Double.parseDouble((String) result.get("total_area"));
            final double area_harv = Double.parseDouble((String) result.get("area_harv"));
            final double total_tonrw = Double.parseDouble((String) result.get("total_tonrw"));
            final double tonrw_harv = Double.parseDouble((String) result.get("tonrw_harv"));
            final double lat = Double.parseDouble((String) result.get("latitude"));
            final double lon = Double.parseDouble((String) result.get("longitude"));
            final String grower = "NA";
            final double moisture = Double.parseDouble((String) result.get("moisture"));
            final String tracking_number = (String) result.get("field2");
            final int lateness = Integer.parseInt((String) result.get("lateness"));
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
