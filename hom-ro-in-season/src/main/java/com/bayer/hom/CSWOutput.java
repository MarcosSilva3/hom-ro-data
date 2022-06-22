package com.bayer.hom;

import java.util.Objects;

public class CSWOutput {
    private String country;
    private String plant;
    private int crop_year;
    private int global_fiscal_year;
    private String crop_code;
    private String season;
    private String field;
    private String field_name;
    private String grower_name;
    private String feature_id;
    private String hybrid;
    private String field_supervisor;
    private String environment;
    private String seedsman_area;
    private String picker;
    private double total_area;
    private double total_weight;
    private String harvest_date_01;
    private String harvest_date_02;
    private String harvest_date_03;
    private String harvest_date_04;
    private String harvest_date_05;
    private double harvest_date_01_area;
    private double harvest_date_02_area;
    private double harvest_date_03_area;
    private double harvest_date_04_area;
    private double harvest_date_05_area;
    private double harvest_date_01_weight;
    private double harvest_date_02_weight;
    private double harvest_date_03_weight;
    private double harvest_date_04_weight;
    private double harvest_date_05_weight;
    private double harvest_moisture_01;
    private double harvest_moisture_02;
    private double harvest_moisture_03;
    private double harvest_moisture_04;
    private double harvest_moisture_05;
    private double drydown_rate;
    private double optimal_harvest_moisture_range_min;
    private double optimal_harvest_moisture_range_max;
    private int lateness;
    private String hybrid_drying_sensitivity_classification;
    private String harvest_type;
    private int estimated_number_of_trucks;
    private double field_moisture;
    private String moisture_collected_date;
    private double field_lat;
    private double field_lon;
    private String wkt;
    private String model_timestamp;

    public CSWOutput() {
    }

    public CSWOutput(String country, String plant, int crop_year, int global_fiscal_year, String crop_code,
            String season, String field, String field_name, String grower_name, String feature_id, String hybrid,
            String field_supervisor, String environment, String seedsman_area, String picker, double total_area,
            double total_weight, String harvest_date_01, String harvest_date_02, String harvest_date_03,
            String harvest_date_04, String harvest_date_05, double harvest_date_01_area, double harvest_date_02_area,
            double harvest_date_03_area, double harvest_date_04_area, double harvest_date_05_area,
            double harvest_date_01_weight, double harvest_date_02_weight, double harvest_date_03_weight,
            double harvest_date_04_weight, double harvest_date_05_weight, double harvest_moisture_01,
            double harvest_moisture_02, double harvest_moisture_03, double harvest_moisture_04,
            double harvest_moisture_05, double drydown_rate, double optimal_harvest_moisture_range_min,
            double optimal_harvest_moisture_range_max, int lateness, String hybrid_drying_sensitivity_classification,
            String harvest_type, int estimated_number_of_trucks, double field_moisture, String moisture_collected_date,
            double field_lat, double field_lon, String wkt, String model_timestamp) {
        this.country = country;
        this.plant = plant;
        this.crop_year = crop_year;
        this.global_fiscal_year = global_fiscal_year;
        this.crop_code = crop_code;
        this.season = season;
        this.field = field;
        this.field_name = field_name;
        this.grower_name = grower_name;
        this.feature_id = feature_id;
        this.hybrid = hybrid;
        this.field_supervisor = field_supervisor;
        this.environment = environment;
        this.seedsman_area = seedsman_area;
        this.picker = picker;
        this.total_area = total_area;
        this.total_weight = total_weight;
        this.harvest_date_01 = harvest_date_01;
        this.harvest_date_02 = harvest_date_02;
        this.harvest_date_03 = harvest_date_03;
        this.harvest_date_04 = harvest_date_04;
        this.harvest_date_05 = harvest_date_05;
        this.harvest_date_01_area = harvest_date_01_area;
        this.harvest_date_02_area = harvest_date_02_area;
        this.harvest_date_03_area = harvest_date_03_area;
        this.harvest_date_04_area = harvest_date_04_area;
        this.harvest_date_05_area = harvest_date_05_area;
        this.harvest_date_01_weight = harvest_date_01_weight;
        this.harvest_date_02_weight = harvest_date_02_weight;
        this.harvest_date_03_weight = harvest_date_03_weight;
        this.harvest_date_04_weight = harvest_date_04_weight;
        this.harvest_date_05_weight = harvest_date_05_weight;
        this.harvest_moisture_01 = harvest_moisture_01;
        this.harvest_moisture_02 = harvest_moisture_02;
        this.harvest_moisture_03 = harvest_moisture_03;
        this.harvest_moisture_04 = harvest_moisture_04;
        this.harvest_moisture_05 = harvest_moisture_05;
        this.drydown_rate = drydown_rate;
        this.optimal_harvest_moisture_range_min = optimal_harvest_moisture_range_min;
        this.optimal_harvest_moisture_range_max = optimal_harvest_moisture_range_max;
        this.lateness = lateness;
        this.hybrid_drying_sensitivity_classification = hybrid_drying_sensitivity_classification;
        this.harvest_type = harvest_type;
        this.estimated_number_of_trucks = estimated_number_of_trucks;
        this.field_moisture = field_moisture;
        this.moisture_collected_date = moisture_collected_date;
        this.field_lat = field_lat;
        this.field_lon = field_lon;
        this.wkt = wkt;
        this.model_timestamp = model_timestamp;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlant() {
        return this.plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public int getCrop_year() {
        return this.crop_year;
    }

    public void setCrop_year(int crop_year) {
        this.crop_year = crop_year;
    }

    public int getGlobal_fiscal_year() {
        return this.global_fiscal_year;
    }

    public void setGlobal_fiscal_year(int global_fiscal_year) {
        this.global_fiscal_year = global_fiscal_year;
    }

    public String getCrop_code() {
        return this.crop_code;
    }

    public void setCrop_code(String crop_code) {
        this.crop_code = crop_code;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField_name() {
        return this.field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getGrower_name() {
        return this.grower_name;
    }

    public void setGrower_name(String grower_name) {
        this.grower_name = grower_name;
    }

    public String getFeature_id() {
        return this.feature_id;
    }

    public void setFeature_id(String feature_id) {
        this.feature_id = feature_id;
    }

    public String getHybrid() {
        return this.hybrid;
    }

    public void setHybrid(String hybrid) {
        this.hybrid = hybrid;
    }

    public String getField_supervisor() {
        return this.field_supervisor;
    }

    public void setField_supervisor(String field_supervisor) {
        this.field_supervisor = field_supervisor;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getSeedsman_area() {
        return this.seedsman_area;
    }

    public void setSeedsman_area(String seedsman_area) {
        this.seedsman_area = seedsman_area;
    }

    public String getPicker() {
        return this.picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }

    public double getTotal_area() {
        return this.total_area;
    }

    public void setTotal_area(double total_area) {
        this.total_area = total_area;
    }

    public double getTotal_weight() {
        return this.total_weight;
    }

    public void setTotal_weight(double total_weight) {
        this.total_weight = total_weight;
    }

    public String getHarvest_date_01() {
        return this.harvest_date_01;
    }

    public void setHarvest_date_01(String harvest_date_01) {
        this.harvest_date_01 = harvest_date_01;
    }

    public String getHarvest_date_02() {
        return this.harvest_date_02;
    }

    public void setHarvest_date_02(String harvest_date_02) {
        this.harvest_date_02 = harvest_date_02;
    }

    public String getHarvest_date_03() {
        return this.harvest_date_03;
    }

    public void setHarvest_date_03(String harvest_date_03) {
        this.harvest_date_03 = harvest_date_03;
    }

    public String getHarvest_date_04() {
        return this.harvest_date_04;
    }

    public void setHarvest_date_04(String harvest_date_04) {
        this.harvest_date_04 = harvest_date_04;
    }

    public String getHarvest_date_05() {
        return this.harvest_date_05;
    }

    public void setHarvest_date_05(String harvest_date_05) {
        this.harvest_date_05 = harvest_date_05;
    }

    public double getHarvest_date_01_area() {
        return this.harvest_date_01_area;
    }

    public void setHarvest_date_01_area(double harvest_date_01_area) {
        this.harvest_date_01_area = harvest_date_01_area;
    }

    public double getHarvest_date_02_area() {
        return this.harvest_date_02_area;
    }

    public void setHarvest_date_02_area(double harvest_date_02_area) {
        this.harvest_date_02_area = harvest_date_02_area;
    }

    public double getHarvest_date_03_area() {
        return this.harvest_date_03_area;
    }

    public void setHarvest_date_03_area(double harvest_date_03_area) {
        this.harvest_date_03_area = harvest_date_03_area;
    }

    public double getHarvest_date_04_area() {
        return this.harvest_date_04_area;
    }

    public void setHarvest_date_04_area(double harvest_date_04_area) {
        this.harvest_date_04_area = harvest_date_04_area;
    }

    public double getHarvest_date_05_area() {
        return this.harvest_date_05_area;
    }

    public void setHarvest_date_05_area(double harvest_date_05_area) {
        this.harvest_date_05_area = harvest_date_05_area;
    }

    public double getHarvest_date_01_weight() {
        return this.harvest_date_01_weight;
    }

    public void setHarvest_date_01_weight(double harvest_date_01_weight) {
        this.harvest_date_01_weight = harvest_date_01_weight;
    }

    public double getHarvest_date_02_weight() {
        return this.harvest_date_02_weight;
    }

    public void setHarvest_date_02_weight(double harvest_date_02_weight) {
        this.harvest_date_02_weight = harvest_date_02_weight;
    }

    public double getHarvest_date_03_weight() {
        return this.harvest_date_03_weight;
    }

    public void setHarvest_date_03_weight(double harvest_date_03_weight) {
        this.harvest_date_03_weight = harvest_date_03_weight;
    }

    public double getHarvest_date_04_weight() {
        return this.harvest_date_04_weight;
    }

    public void setHarvest_date_04_weight(double harvest_date_04_weight) {
        this.harvest_date_04_weight = harvest_date_04_weight;
    }

    public double getHarvest_date_05_weight() {
        return this.harvest_date_05_weight;
    }

    public void setHarvest_date_05_weight(double harvest_date_05_weight) {
        this.harvest_date_05_weight = harvest_date_05_weight;
    }

    public double getHarvest_moisture_01() {
        return this.harvest_moisture_01;
    }

    public void setHarvest_moisture_01(double harvest_moisture_01) {
        this.harvest_moisture_01 = harvest_moisture_01;
    }

    public double getHarvest_moisture_02() {
        return this.harvest_moisture_02;
    }

    public void setHarvest_moisture_02(double harvest_moisture_02) {
        this.harvest_moisture_02 = harvest_moisture_02;
    }

    public double getHarvest_moisture_03() {
        return this.harvest_moisture_03;
    }

    public void setHarvest_moisture_03(double harvest_moisture_03) {
        this.harvest_moisture_03 = harvest_moisture_03;
    }

    public double getHarvest_moisture_04() {
        return this.harvest_moisture_04;
    }

    public void setHarvest_moisture_04(double harvest_moisture_04) {
        this.harvest_moisture_04 = harvest_moisture_04;
    }

    public double getHarvest_moisture_05() {
        return this.harvest_moisture_05;
    }

    public void setHarvest_moisture_05(double harvest_moisture_05) {
        this.harvest_moisture_05 = harvest_moisture_05;
    }

    public double getDrydown_rate() {
        return this.drydown_rate;
    }

    public void setDrydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
    }

    public double getOptimal_harvest_moisture_range_min() {
        return this.optimal_harvest_moisture_range_min;
    }

    public void setOptimal_harvest_moisture_range_min(double optimal_harvest_moisture_range_min) {
        this.optimal_harvest_moisture_range_min = optimal_harvest_moisture_range_min;
    }

    public double getOptimal_harvest_moisture_range_max() {
        return this.optimal_harvest_moisture_range_max;
    }

    public void setOptimal_harvest_moisture_range_max(double optimal_harvest_moisture_range_max) {
        this.optimal_harvest_moisture_range_max = optimal_harvest_moisture_range_max;
    }

    public int getLateness() {
        return this.lateness;
    }

    public void setLateness(int lateness) {
        this.lateness = lateness;
    }

    public String getHybrid_drying_sensitivity_classification() {
        return this.hybrid_drying_sensitivity_classification;
    }

    public void setHybrid_drying_sensitivity_classification(String hybrid_drying_sensitivity_classification) {
        this.hybrid_drying_sensitivity_classification = hybrid_drying_sensitivity_classification;
    }

    public String getHarvest_type() {
        return this.harvest_type;
    }

    public void setHarvest_type(String harvest_type) {
        this.harvest_type = harvest_type;
    }

    public int getEstimated_number_of_trucks() {
        return this.estimated_number_of_trucks;
    }

    public void setEstimated_number_of_trucks(int estimated_number_of_trucks) {
        this.estimated_number_of_trucks = estimated_number_of_trucks;
    }

    public double getField_moisture() {
        return this.field_moisture;
    }

    public void setField_moisture(double field_moisture) {
        this.field_moisture = field_moisture;
    }

    public String getMoisture_collected_date() {
        return this.moisture_collected_date;
    }

    public void setMoisture_collected_date(String moisture_collected_date) {
        this.moisture_collected_date = moisture_collected_date;
    }

    public double getField_lat() {
        return this.field_lat;
    }

    public void setField_lat(double field_lat) {
        this.field_lat = field_lat;
    }

    public double getField_lon() {
        return this.field_lon;
    }

    public void setField_lon(double field_lon) {
        this.field_lon = field_lon;
    }

    public String getWkt() {
        return this.wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getModel_timestamp() {
        return this.model_timestamp;
    }

    public void setModel_timestamp(String model_timestamp) {
        this.model_timestamp = model_timestamp;
    }

    public CSWOutput country(String country) {
        this.country = country;
        return this;
    }

    public CSWOutput plant(String plant) {
        this.plant = plant;
        return this;
    }

    public CSWOutput crop_year(int crop_year) {
        this.crop_year = crop_year;
        return this;
    }

    public CSWOutput global_fiscal_year(int global_fiscal_year) {
        this.global_fiscal_year = global_fiscal_year;
        return this;
    }

    public CSWOutput crop_code(String crop_code) {
        this.crop_code = crop_code;
        return this;
    }

    public CSWOutput season(String season) {
        this.season = season;
        return this;
    }

    public CSWOutput field(String field) {
        this.field = field;
        return this;
    }

    public CSWOutput field_name(String field_name) {
        this.field_name = field_name;
        return this;
    }

    public CSWOutput grower_name(String grower_name) {
        this.grower_name = grower_name;
        return this;
    }

    public CSWOutput feature_id(String feature_id) {
        this.feature_id = feature_id;
        return this;
    }

    public CSWOutput hybrid(String hybrid) {
        this.hybrid = hybrid;
        return this;
    }

    public CSWOutput field_supervisor(String field_supervisor) {
        this.field_supervisor = field_supervisor;
        return this;
    }

    public CSWOutput environment(String environment) {
        this.environment = environment;
        return this;
    }

    public CSWOutput seedsman_area(String seedsman_area) {
        this.seedsman_area = seedsman_area;
        return this;
    }

    public CSWOutput picker(String picker) {
        this.picker = picker;
        return this;
    }

    public CSWOutput total_area(double total_area) {
        this.total_area = total_area;
        return this;
    }

    public CSWOutput total_weight(double total_weight) {
        this.total_weight = total_weight;
        return this;
    }

    public CSWOutput harvest_date_01(String harvest_date_01) {
        this.harvest_date_01 = harvest_date_01;
        return this;
    }

    public CSWOutput harvest_date_02(String harvest_date_02) {
        this.harvest_date_02 = harvest_date_02;
        return this;
    }

    public CSWOutput harvest_date_03(String harvest_date_03) {
        this.harvest_date_03 = harvest_date_03;
        return this;
    }

    public CSWOutput harvest_date_04(String harvest_date_04) {
        this.harvest_date_04 = harvest_date_04;
        return this;
    }

    public CSWOutput harvest_date_05(String harvest_date_05) {
        this.harvest_date_05 = harvest_date_05;
        return this;
    }

    public CSWOutput harvest_date_01_area(double harvest_date_01_area) {
        this.harvest_date_01_area = harvest_date_01_area;
        return this;
    }

    public CSWOutput harvest_date_02_area(double harvest_date_02_area) {
        this.harvest_date_02_area = harvest_date_02_area;
        return this;
    }

    public CSWOutput harvest_date_03_area(double harvest_date_03_area) {
        this.harvest_date_03_area = harvest_date_03_area;
        return this;
    }

    public CSWOutput harvest_date_04_area(double harvest_date_04_area) {
        this.harvest_date_04_area = harvest_date_04_area;
        return this;
    }

    public CSWOutput harvest_date_05_area(double harvest_date_05_area) {
        this.harvest_date_05_area = harvest_date_05_area;
        return this;
    }

    public CSWOutput harvest_date_01_weight(double harvest_date_01_weight) {
        this.harvest_date_01_weight = harvest_date_01_weight;
        return this;
    }

    public CSWOutput harvest_date_02_weight(double harvest_date_02_weight) {
        this.harvest_date_02_weight = harvest_date_02_weight;
        return this;
    }

    public CSWOutput harvest_date_03_weight(double harvest_date_03_weight) {
        this.harvest_date_03_weight = harvest_date_03_weight;
        return this;
    }

    public CSWOutput harvest_date_04_weight(double harvest_date_04_weight) {
        this.harvest_date_04_weight = harvest_date_04_weight;
        return this;
    }

    public CSWOutput harvest_date_05_weight(double harvest_date_05_weight) {
        this.harvest_date_05_weight = harvest_date_05_weight;
        return this;
    }

    public CSWOutput harvest_moisture_01(double harvest_moisture_01) {
        this.harvest_moisture_01 = harvest_moisture_01;
        return this;
    }

    public CSWOutput harvest_moisture_02(double harvest_moisture_02) {
        this.harvest_moisture_02 = harvest_moisture_02;
        return this;
    }

    public CSWOutput harvest_moisture_03(double harvest_moisture_03) {
        this.harvest_moisture_03 = harvest_moisture_03;
        return this;
    }

    public CSWOutput harvest_moisture_04(double harvest_moisture_04) {
        this.harvest_moisture_04 = harvest_moisture_04;
        return this;
    }

    public CSWOutput harvest_moisture_05(double harvest_moisture_05) {
        this.harvest_moisture_05 = harvest_moisture_05;
        return this;
    }

    public CSWOutput drydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
        return this;
    }

    public CSWOutput optimal_harvest_moisture_range_min(double optimal_harvest_moisture_range_min) {
        this.optimal_harvest_moisture_range_min = optimal_harvest_moisture_range_min;
        return this;
    }

    public CSWOutput optimal_harvest_moisture_range_max(double optimal_harvest_moisture_range_max) {
        this.optimal_harvest_moisture_range_max = optimal_harvest_moisture_range_max;
        return this;
    }

    public CSWOutput lateness(int lateness) {
        this.lateness = lateness;
        return this;
    }

    public CSWOutput hybrid_drying_sensitivity_classification(String hybrid_drying_sensitivity_classification) {
        this.hybrid_drying_sensitivity_classification = hybrid_drying_sensitivity_classification;
        return this;
    }

    public CSWOutput harvest_type(String harvest_type) {
        this.harvest_type = harvest_type;
        return this;
    }

    public CSWOutput estimated_number_of_trucks(int estimated_number_of_trucks) {
        this.estimated_number_of_trucks = estimated_number_of_trucks;
        return this;
    }

    public CSWOutput field_moisture(double field_moisture) {
        this.field_moisture = field_moisture;
        return this;
    }

    public CSWOutput moisture_collected_date(String moisture_collected_date) {
        this.moisture_collected_date = moisture_collected_date;
        return this;
    }

    public CSWOutput field_lat(double field_lat) {
        this.field_lat = field_lat;
        return this;
    }

    public CSWOutput field_lon(double field_lon) {
        this.field_lon = field_lon;
        return this;
    }

    public CSWOutput wkt(String wkt) {
        this.wkt = wkt;
        return this;
    }

    public CSWOutput model_timestamp(String model_timestamp) {
        this.model_timestamp = model_timestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CSWOutput)) {
            return false;
        }
        CSWOutput cSWOutput = (CSWOutput) o;
        return Objects.equals(country, cSWOutput.country) && Objects.equals(plant, cSWOutput.plant)
                && crop_year == cSWOutput.crop_year && global_fiscal_year == cSWOutput.global_fiscal_year
                && Objects.equals(crop_code, cSWOutput.crop_code) && Objects.equals(season, cSWOutput.season)
                && Objects.equals(field, cSWOutput.field) && Objects.equals(field_name, cSWOutput.field_name)
                && Objects.equals(grower_name, cSWOutput.grower_name)
                && Objects.equals(feature_id, cSWOutput.feature_id) && Objects.equals(hybrid, cSWOutput.hybrid)
                && Objects.equals(field_supervisor, cSWOutput.field_supervisor)
                && Objects.equals(environment, cSWOutput.environment)
                && Objects.equals(seedsman_area, cSWOutput.seedsman_area) && Objects.equals(picker, cSWOutput.picker)
                && total_area == cSWOutput.total_area && total_weight == cSWOutput.total_weight
                && Objects.equals(harvest_date_01, cSWOutput.harvest_date_01)
                && Objects.equals(harvest_date_02, cSWOutput.harvest_date_02)
                && Objects.equals(harvest_date_03, cSWOutput.harvest_date_03)
                && Objects.equals(harvest_date_04, cSWOutput.harvest_date_04)
                && Objects.equals(harvest_date_05, cSWOutput.harvest_date_05)
                && harvest_date_01_area == cSWOutput.harvest_date_01_area
                && harvest_date_02_area == cSWOutput.harvest_date_02_area
                && harvest_date_03_area == cSWOutput.harvest_date_03_area
                && harvest_date_04_area == cSWOutput.harvest_date_04_area
                && harvest_date_05_area == cSWOutput.harvest_date_05_area
                && harvest_date_01_weight == cSWOutput.harvest_date_01_weight
                && harvest_date_02_weight == cSWOutput.harvest_date_02_weight
                && harvest_date_03_weight == cSWOutput.harvest_date_03_weight
                && harvest_date_04_weight == cSWOutput.harvest_date_04_weight
                && harvest_date_05_weight == cSWOutput.harvest_date_05_weight
                && harvest_moisture_01 == cSWOutput.harvest_moisture_01
                && harvest_moisture_02 == cSWOutput.harvest_moisture_02
                && harvest_moisture_03 == cSWOutput.harvest_moisture_03
                && harvest_moisture_04 == cSWOutput.harvest_moisture_04
                && harvest_moisture_05 == cSWOutput.harvest_moisture_05 && drydown_rate == cSWOutput.drydown_rate
                && optimal_harvest_moisture_range_min == cSWOutput.optimal_harvest_moisture_range_min
                && optimal_harvest_moisture_range_max == cSWOutput.optimal_harvest_moisture_range_max
                && lateness == cSWOutput.lateness
                && Objects.equals(hybrid_drying_sensitivity_classification,
                        cSWOutput.hybrid_drying_sensitivity_classification)
                && Objects.equals(harvest_type, cSWOutput.harvest_type)
                && estimated_number_of_trucks == cSWOutput.estimated_number_of_trucks
                && field_moisture == cSWOutput.field_moisture
                && Objects.equals(moisture_collected_date, cSWOutput.moisture_collected_date)
                && field_lat == cSWOutput.field_lat && field_lon == cSWOutput.field_lon
                && Objects.equals(wkt, cSWOutput.wkt) && Objects.equals(model_timestamp, cSWOutput.model_timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, plant, crop_year, global_fiscal_year, crop_code, season, field, field_name,
                grower_name, feature_id, hybrid, field_supervisor, environment, seedsman_area, picker, total_area,
                total_weight, harvest_date_01, harvest_date_02, harvest_date_03, harvest_date_04, harvest_date_05,
                harvest_date_01_area, harvest_date_02_area, harvest_date_03_area, harvest_date_04_area,
                harvest_date_05_area, harvest_date_01_weight, harvest_date_02_weight, harvest_date_03_weight,
                harvest_date_04_weight, harvest_date_05_weight, harvest_moisture_01, harvest_moisture_02,
                harvest_moisture_03, harvest_moisture_04, harvest_moisture_05, drydown_rate,
                optimal_harvest_moisture_range_min, optimal_harvest_moisture_range_max, lateness,
                hybrid_drying_sensitivity_classification, harvest_type, estimated_number_of_trucks, field_moisture,
                moisture_collected_date, field_lat, field_lon, wkt, model_timestamp);
    }

    @Override
    public String toString() {
        return "{" + " country='" + getCountry() + "'" + ", plant='" + getPlant() + "'" + ", crop_year='"
                + getCrop_year() + "'" + ", global_fiscal_year='" + getGlobal_fiscal_year() + "'" + ", crop_code='"
                + getCrop_code() + "'" + ", season='" + getSeason() + "'" + ", field='" + getField() + "'"
                + ", field_name='" + getField_name() + "'" + ", grower_name='" + getGrower_name() + "'"
                + ", feature_id='" + getFeature_id() + "'" + ", hybrid='" + getHybrid() + "'" + ", field_supervisor='"
                + getField_supervisor() + "'" + ", environment='" + getEnvironment() + "'" + ", seedsman_area='"
                + getSeedsman_area() + "'" + ", picker='" + getPicker() + "'" + ", total_area='" + getTotal_area() + "'"
                + ", total_weight='" + getTotal_weight() + "'" + ", harvest_date_01='" + getHarvest_date_01() + "'"
                + ", harvest_date_02='" + getHarvest_date_02() + "'" + ", harvest_date_03='" + getHarvest_date_03()
                + "'" + ", harvest_date_04='" + getHarvest_date_04() + "'" + ", harvest_date_05='"
                + getHarvest_date_05() + "'" + ", harvest_date_01_area='" + getHarvest_date_01_area() + "'"
                + ", harvest_date_02_area='" + getHarvest_date_02_area() + "'" + ", harvest_date_03_area='"
                + getHarvest_date_03_area() + "'" + ", harvest_date_04_area='" + getHarvest_date_04_area() + "'"
                + ", harvest_date_05_area='" + getHarvest_date_05_area() + "'" + ", harvest_date_01_weight='"
                + getHarvest_date_01_weight() + "'" + ", harvest_date_02_weight='" + getHarvest_date_02_weight() + "'"
                + ", harvest_date_03_weight='" + getHarvest_date_03_weight() + "'" + ", harvest_date_04_weight='"
                + getHarvest_date_04_weight() + "'" + ", harvest_date_05_weight='" + getHarvest_date_05_weight() + "'"
                + ", harvest_moisture_01='" + getHarvest_moisture_01() + "'" + ", harvest_moisture_02='"
                + getHarvest_moisture_02() + "'" + ", harvest_moisture_03='" + getHarvest_moisture_03() + "'"
                + ", harvest_moisture_04='" + getHarvest_moisture_04() + "'" + ", harvest_moisture_05='"
                + getHarvest_moisture_05() + "'" + ", drydown_rate='" + getDrydown_rate() + "'"
                + ", optimal_harvest_moisture_range_min='" + getOptimal_harvest_moisture_range_min() + "'"
                + ", optimal_harvest_moisture_range_max='" + getOptimal_harvest_moisture_range_max() + "'"
                + ", lateness='" + getLateness() + "'" + ", hybrid_drying_sensitivity_classification='"
                + getHybrid_drying_sensitivity_classification() + "'" + ", harvest_type='" + getHarvest_type() + "'"
                + ", estimated_number_of_trucks='" + getEstimated_number_of_trucks() + "'" + ", field_moisture='"
                + getField_moisture() + "'" + ", moisture_collected_date='" + getMoisture_collected_date() + "'"
                + ", field_lat='" + getField_lat() + "'" + ", field_lon='" + getField_lon() + "'" + ", wkt='" + getWkt()
                + "'" + ", model_timestamp='" + getModel_timestamp() + "'" + "}";
    }

}
