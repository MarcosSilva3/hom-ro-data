package com.bayer.hom;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.AuthResponse;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class BQData {
        private String private_key_file;
        private String project_id;
        private final BigQuery bigquery;

        public BQData(final String private_key_file, final String project_id) throws VaultException, IOException {
                this.private_key_file = private_key_file;
                this.project_id = project_id;
                getBigQueryCredentials();
                this.bigquery = (BigQuery) BigQueryOptions.newBuilder().setProjectId(project_id)
                                .setCredentials(ServiceAccountCredentials
                                                .fromStream(new FileInputStream(private_key_file)))
                                .build().getService();
        }

        private void getBigQueryCredentials() throws VaultException, IOException {
                Map<String, String> result = null;

                final String vault_address = System.getenv("VAULT_URL");
                final String role_id = System.getenv("VAULT_APP_ROLE_ID");
                final String secret_id = System.getenv("VAULT_APP_ROLE_SECRET_ID");
                final String secrets_path = System.getenv("VAULT_PATH_BIGQUERY");
                final Integer engineVersion = 1;

                final VaultConfig config = new VaultConfig().address(vault_address).build();
                final Vault vault = new Vault(config, engineVersion);
                final AuthResponse response = vault.auth().loginByAppRole(role_id, secret_id);
                final String clientAuthToken = response.getAuthClientToken();
                config.token(clientAuthToken);
                result = new HashMap<>(vault.logical().read(secrets_path).getData());
                final String decoded_secret = new String(Base64.getDecoder().decode(result.get("data")));

                try (PrintWriter out1 = new PrintWriter(new FileWriter(private_key_file))) {
                        out1.write(decoded_secret);
                }
        }

        /**
         * Check if a string looks like a number.
         */
        public static boolean isNumeric(final String str) {
                try {
                        Double.parseDouble(str);
                        return true;
                } catch (final NumberFormatException e) {
                        return false;
                }
        }

        public Map<String, GSMData> getGSMData(final String country, final int year)
                        throws InterruptedException, Exception {

                final String QUERY = String.format(
                                "SELECT entityid, production_field_id, country, site_key, plant, field_num, planting_area_id, planting_area_name, macrozone, seedsman_area, pfo_name, user_group, tracking_number, contract_number, year, season, planting_date, planting_window, female_fertile, female_sterile, variety, planted_surface, tot_female_area, ff_area, fs_area, tot_area, area_uom, growth_stage, gdu_curr, moist35_date, drydown_rate, mst, mst_date, mst_imputed, mst_imputed_field, optimal_mst_harvest_date, min_mst_harvest_date, max_mst_harvest_date, lat, lon, report_date, region, wkt FROM `location360-datasets.asp_reporting.growth_stage_predictions` where country=\"%s\" and year=%d",
                                country, year);

                final QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(QUERY).build();
                final Map<String, GSMData> hFieldsGSM = new HashMap<>();
                Job queryJob = this.bigquery.create(JobInfo.newBuilder(queryConfig).build());
                queryJob = queryJob.waitFor();
                // the waitFor method blocks until the job completes
                // and returns `null` if the job doesn't exist anymore
                if (queryJob == null) {
                        throw new Exception("job no longer exists");
                }
                // once the job is done, check if any error occured
                if (queryJob.getStatus().getError() != null) {
                        throw new Exception(queryJob.getStatus().getError().toString());
                }

                final TableResult result = queryJob.getQueryResults();
                for (final FieldValueList row : result.iterateAll()) {
                        final String entityid = Objects.toString(
                                        row.get("entityid").getValue() != null ? row.get("entityid").getValue()
                                                        : row.get("entityid").getValue());

                        final String production_field_id = Objects
                                        .toString(row.get("production_field_id").getValue() != null
                                                        ? row.get("production_field_id").getValue()
                                                        : row.get("production_field_id").getValue());

                        final String site_key = Objects.toString(
                                        row.get("site_key").getValue() != null ? row.get("site_key").getValue()
                                                        : row.get("site_key").getValue());

                        final String plant = Objects
                                        .toString(row.get("plant").getValue() != null ? row.get("plant").getValue()
                                                        : row.get("plant").getValue());

                        final String field_num = Objects.toString(
                                        row.get("field_num").getValue() != null ? row.get("field_num").getValue()
                                                        : row.get("field_num").getValue());

                        String s_planting_area_id = Objects.toString(row.get("planting_area_id").getValue() != null
                                        ? row.get("planting_area_id").getValue()
                                        : row.get("planting_area_id").getValue());

                        int planting_area_id = 0;
                        if (isNumeric(s_planting_area_id)) {
                                planting_area_id = Integer.parseInt(s_planting_area_id);
                        }

                        final String planting_area_name = Objects
                                        .toString(row.get("planting_area_name").getValue() != null
                                                        ? row.get("planting_area_name").getValue()
                                                        : row.get("planting_area_name").getValue());

                        final String macrozone = Objects.toString(
                                        row.get("macrozone").getValue() != null ? row.get("macrozone").getValue()
                                                        : row.get("macrozone").getValue());

                        final String seedsman_area = Objects.toString(row.get("seedsman_area").getValue() != null
                                        ? row.get("seedsman_area").getValue()
                                        : row.get("seedsman_area").getValue());

                        final String pfo_name = Objects.toString(
                                        row.get("pfo_name").getValue() != null ? row.get("pfo_name").getValue()
                                                        : row.get("pfo_name").getValue());

                        final String user_group = Objects.toString(
                                        row.get("user_group").getValue() != null ? row.get("user_group").getValue()
                                                        : row.get("user_group").getValue());

                        final String tracking_number = Objects.toString(row.get("tracking_number").getValue() != null
                                        ? row.get("tracking_number").getValue()
                                        : row.get("tracking_number").getValue());

                        final String contract_number = Objects.toString(row.get("contract_number").getValue() != null
                                        ? row.get("contract_number").getValue()
                                        : row.get("contract_number").getValue());

                        final String season = Objects
                                        .toString(row.get("season").getValue() != null ? row.get("season").getValue()
                                                        : row.get("season").getValue());

                        final String planting_date = Objects.toString(row.get("planting_date").getValue() != null
                                        ? row.get("planting_date").getValue()
                                        : row.get("planting_date").getValue());

                        final String planting_window = Objects.toString(row.get("planting_window").getValue() != null
                                        ? row.get("planting_window").getValue()
                                        : row.get("planting_window").getValue());

                        final String female_fertile = Objects.toString(row.get("female_fertile").getValue() != null
                                        ? row.get("female_fertile").getValue()
                                        : row.get("female_fertile").getValue());

                        final String female_sterile = Objects.toString(row.get("female_sterile").getValue() != null
                                        ? row.get("female_sterile").getValue()
                                        : row.get("female_sterile").getValue());

                        final String variety = Objects
                                        .toString(row.get("variety").getValue() != null ? row.get("variety").getValue()
                                                        : row.get("variety").getValue());

                        final String s_planted_surface = Objects.toString(row.get("planted_surface").getValue() != null
                                        ? row.get("planted_surface").getValue()
                                        : row.get("planted_surface").getValue());

                        double planted_surface = 0.0;
                        if (isNumeric(s_planted_surface)) {
                                planted_surface = Double.parseDouble(s_planted_surface);
                        }

                        final String s_tot_female_area = Objects.toString(row.get("tot_female_area").getValue() != null
                                        ? row.get("tot_female_area").getValue()
                                        : row.get("tot_female_area").getValue());
                        double tot_female_area = 0.0;
                        if (isNumeric(s_tot_female_area)) {
                                tot_female_area = Double.parseDouble(s_tot_female_area);
                        }

                        final String s_ff_area = Objects
                                        .toString(row.get("ff_area").getValue() != null ? row.get("ff_area").getValue()
                                                        : row.get("ff_area").getValue());
                        double ff_area = 0.0;
                        if (isNumeric(s_ff_area)) {
                                ff_area = Double.parseDouble(s_ff_area);
                        }

                        final String s_fs_area = Objects
                                        .toString(row.get("fs_area").getValue() != null ? row.get("fs_area").getValue()
                                                        : row.get("fs_area").getValue());
                        double fs_area = 0.0;
                        if (isNumeric(s_fs_area)) {
                                fs_area = Double.parseDouble(s_fs_area);
                        }

                        final String s_tot_area = Objects.toString(
                                        row.get("tot_area").getValue() != null ? row.get("tot_area").getValue()
                                                        : row.get("tot_area").getValue());
                        double tot_area = 0.0;
                        if (isNumeric(s_tot_area)) {
                                tot_area = Double.parseDouble(s_tot_area);
                        }

                        final String area_uom = Objects.toString(
                                        row.get("area_uom").getValue() != null ? row.get("area_uom").getValue()
                                                        : row.get("area_uom").getValue());

                        final String growth_stage = Objects.toString(
                                        row.get("growth_stage").getValue() != null ? row.get("growth_stage").getValue()
                                                        : row.get("growth_stage").getValue());

                        final double gdu_curr = Double.parseDouble(row.get("gdu_curr").getStringValue());

                        final String moist35_date = Objects.toString(
                                        row.get("moist35_date").getValue() != null ? row.get("moist35_date").getValue()
                                                        : row.get("moist35_date").getValue());
                        final double drydown_rate = Double.parseDouble(row.get("drydown_rate").getStringValue());
                        final double mst = Double.parseDouble(row.get("mst").getStringValue());

                        String mst_date = Objects.toString(
                                        row.get("mst_date").getValue() != null ? row.get("mst_date").getValue()
                                                        : row.get("mst_date").getValue());
                        if(mst_date.equalsIgnoreCase("null")) {
                                mst_date = "0000-00-00";
                        }

                        final String s_mst_inputed = Objects.toString(
                                        row.get("mst_imputed").getValue() != null ? row.get("mst_imputed").getValue()
                                                        : row.get("mst_imputed").getValue());
                        double mst_inputed = 0.0;
                        if (isNumeric(s_mst_inputed)) {
                                mst_inputed = Double.parseDouble(s_mst_inputed);
                        }

                        final String s_mst_inputed_field = Objects
                                        .toString(row.get("mst_imputed_field").getValue() != null
                                                        ? row.get("mst_imputed_field").getValue()
                                                        : row.get("mst_imputed_field").getValue());
                        double mst_inputed_field = 0.0;
                        if (isNumeric(s_mst_inputed_field)) {
                                mst_inputed_field = Double.parseDouble(s_mst_inputed_field);
                        }

                        final String optimal_mst_harvest_date = Objects
                                        .toString(row.get("optimal_mst_harvest_date").getValue() != null
                                                        ? row.get("optimal_mst_harvest_date").getValue()
                                                        : row.get("optimal_mst_harvest_date").getValue());

                        final String min_mst_harvest_date = Objects
                                        .toString(row.get("min_mst_harvest_date").getValue() != null
                                                        ? row.get("min_mst_harvest_date").getValue()
                                                        : row.get("min_mst_harvest_date").getValue());

                        final String max_mst_harvest_date = Objects
                                        .toString(row.get("max_mst_harvest_date").getValue() != null
                                                        ? row.get("max_mst_harvest_date").getValue()
                                                        : row.get("max_mst_harvest_date").getValue());

                        final String s_lat = Objects
                                        .toString(row.get("lat").getValue() != null ? row.get("lat").getValue()
                                                        : row.get("lat").getValue());
                        double lat = 0.0;
                        if (isNumeric(s_lat)) {
                                lat = Double.parseDouble(s_lat);
                        }

                        final String s_lon = Objects
                                        .toString(row.get("lon").getValue() != null ? row.get("lon").getValue()
                                                        : row.get("lon").getValue());
                        double lon = 0.0;
                        if (isNumeric(s_lon)) {
                                lon = Double.parseDouble(s_lon);
                        }

                        final String report_date = Objects.toString(
                                        row.get("report_date").getValue() != null ? row.get("report_date").getValue()
                                                        : row.get("report_date").getValue());

                        final String region = Objects
                                        .toString(row.get("region").getValue() != null ? row.get("region").getValue()
                                                        : row.get("region").getValue());

                        final String wkt = Objects
                                        .toString(row.get("wkt").getValue() != null ? row.get("wkt").getValue()
                                                        : row.get("wkt").getValue());

                        final GSMData g = new GSMData(entityid, production_field_id, country, site_key, plant,
                                        field_num, planting_area_id, planting_area_name, macrozone, seedsman_area,
                                        pfo_name, user_group, tracking_number, contract_number, year, season,
                                        planting_date, planting_window, female_fertile, female_sterile, variety,
                                        planted_surface, tot_female_area, ff_area, fs_area, tot_area, area_uom,
                                        growth_stage, gdu_curr, moist35_date, drydown_rate, mst, mst_date, mst_inputed,
                                        mst_inputed_field, optimal_mst_harvest_date, min_mst_harvest_date,
                                        max_mst_harvest_date, lat, lon, report_date, region, wkt);
                        hFieldsGSM.put(tracking_number, g);
                }
                return hFieldsGSM;
        }

        public String getPrivate_key_file() {
                return this.private_key_file;
        }

        public void setPrivate_key_file(final String private_key_file) {
                this.private_key_file = private_key_file;
        }

        public String getProject_id() {
                return this.project_id;
        }

        public void setProject_id(final String project_id) {
                this.project_id = project_id;
        }

        public BQData private_key_file(final String private_key_file) {
                this.private_key_file = private_key_file;
                return this;
        }

        public BQData project_id(final String project_id) {
                this.project_id = project_id;
                return this;
        }

        @Override
        public boolean equals(final Object o) {
                if (o == this)
                        return true;
                if (!(o instanceof BQData)) {
                        return false;
                }
                final BQData bigQuery = (BQData) o;
                return Objects.equals(private_key_file, bigQuery.private_key_file)
                                && Objects.equals(project_id, bigQuery.project_id);
        }

        @Override
        public int hashCode() {
                return Objects.hash(private_key_file, project_id);
        }

        @Override
        public String toString() {
                return "{" + " private_key_file='" + getPrivate_key_file() + "'" + ", project_id='" + getProject_id()
                                + "'" + "}";
        }

}