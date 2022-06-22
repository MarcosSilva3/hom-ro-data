package com.bayer.hom;

import java.util.Objects;

public class GSMData {
    private String entityid;
    private String production_field_id;
    private String country;
    private String site_key;
    private String plant;
    private String field_num;
    private int planting_area_id;
    private String planting_area_name;
    private String macrozone;
    private String seedsman_area;
    private String pfo_name;
    private String user_group;
    private String tracking_number;
    private String contract_number;
    private int year;
    private String season;
    private String planting_date;
    private String planting_window;
    private String female_fertile;
    private String female_sterile;
    private String variety;
    private double planted_surface;
    private double tot_female_area;
    private double ff_area;
    private double fs_area;
    private double tot_area;
    private String area_uom;
    private String growth_stage;
    private double gdu_curr;
    private String moist35_date;
    private double drydown_rate;
    private double mst;
    private String mst_date;
    private double mst_imputed;
    private double mst_imputed_field;
    private String optimal_mst_harvest_date;
    private String min_mst_harvest_date;
    private String max_mst_harvest_date;
    private double lat;
    private double lon;
    private String report_date;
    private String region;
    private String wkt;

    public GSMData() {
    }

    public GSMData(String entityid, String production_field_id, String country, String site_key, String plant, String field_num, int planting_area_id, String planting_area_name, String macrozone, String seedsman_area, String pfo_name, String user_group, String tracking_number, String contract_number, int year, String season, String planting_date, String planting_window, String female_fertile, String female_sterile, String variety, double planted_surface, double tot_female_area, double ff_area, double fs_area, double tot_area, String area_uom, String growth_stage, double gdu_curr, String moist35_date, double drydown_rate, double mst, String mst_date, double mst_imputed, double mst_imputed_field, String optimal_mst_harvest_date, String min_mst_harvest_date, String max_mst_harvest_date, double lat, double lon, String report_date, String region, String wkt) {
        this.entityid = entityid;
        this.production_field_id = production_field_id;
        this.country = country;
        this.site_key = site_key;
        this.plant = plant;
        this.field_num = field_num;
        this.planting_area_id = planting_area_id;
        this.planting_area_name = planting_area_name;
        this.macrozone = macrozone;
        this.seedsman_area = seedsman_area;
        this.pfo_name = pfo_name;
        this.user_group = user_group;
        this.tracking_number = tracking_number;
        this.contract_number = contract_number;
        this.year = year;
        this.season = season;
        this.planting_date = planting_date;
        this.planting_window = planting_window;
        this.female_fertile = female_fertile;
        this.female_sterile = female_sterile;
        this.variety = variety;
        this.planted_surface = planted_surface;
        this.tot_female_area = tot_female_area;
        this.ff_area = ff_area;
        this.fs_area = fs_area;
        this.tot_area = tot_area;
        this.area_uom = area_uom;
        this.growth_stage = growth_stage;
        this.gdu_curr = gdu_curr;
        this.moist35_date = moist35_date;
        this.drydown_rate = drydown_rate;
        this.mst = mst;
        this.mst_date = mst_date;
        this.mst_imputed = mst_imputed;
        this.mst_imputed_field = mst_imputed_field;
        this.optimal_mst_harvest_date = optimal_mst_harvest_date;
        this.min_mst_harvest_date = min_mst_harvest_date;
        this.max_mst_harvest_date = max_mst_harvest_date;
        this.lat = lat;
        this.lon = lon;
        this.report_date = report_date;
        this.region = region;
        this.wkt = wkt;
    }

    public String getEntityid() {
        return this.entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    public String getProduction_field_id() {
        return this.production_field_id;
    }

    public void setProduction_field_id(String production_field_id) {
        this.production_field_id = production_field_id;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSite_key() {
        return this.site_key;
    }

    public void setSite_key(String site_key) {
        this.site_key = site_key;
    }

    public String getPlant() {
        return this.plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getField_num() {
        return this.field_num;
    }

    public void setField_num(String field_num) {
        this.field_num = field_num;
    }

    public int getPlanting_area_id() {
        return this.planting_area_id;
    }

    public void setPlanting_area_id(int planting_area_id) {
        this.planting_area_id = planting_area_id;
    }

    public String getPlanting_area_name() {
        return this.planting_area_name;
    }

    public void setPlanting_area_name(String planting_area_name) {
        this.planting_area_name = planting_area_name;
    }

    public String getMacrozone() {
        return this.macrozone;
    }

    public void setMacrozone(String macrozone) {
        this.macrozone = macrozone;
    }

    public String getSeedsman_area() {
        return this.seedsman_area;
    }

    public void setSeedsman_area(String seedsman_area) {
        this.seedsman_area = seedsman_area;
    }

    public String getPfo_name() {
        return this.pfo_name;
    }

    public void setPfo_name(String pfo_name) {
        this.pfo_name = pfo_name;
    }

    public String getUser_group() {
        return this.user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }

    public String getTracking_number() {
        return this.tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getContract_number() {
        return this.contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPlanting_date() {
        return this.planting_date;
    }

    public void setPlanting_date(String planting_date) {
        this.planting_date = planting_date;
    }

    public String getPlanting_window() {
        return this.planting_window;
    }

    public void setPlanting_window(String planting_window) {
        this.planting_window = planting_window;
    }

    public String getFemale_fertile() {
        return this.female_fertile;
    }

    public void setFemale_fertile(String female_fertile) {
        this.female_fertile = female_fertile;
    }

    public String getFemale_sterile() {
        return this.female_sterile;
    }

    public void setFemale_sterile(String female_sterile) {
        this.female_sterile = female_sterile;
    }

    public String getVariety() {
        return this.variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public double getPlanted_surface() {
        return this.planted_surface;
    }

    public void setPlanted_surface(double planted_surface) {
        this.planted_surface = planted_surface;
    }

    public double getTot_female_area() {
        return this.tot_female_area;
    }

    public void setTot_female_area(double tot_female_area) {
        this.tot_female_area = tot_female_area;
    }

    public double getFf_area() {
        return this.ff_area;
    }

    public void setFf_area(double ff_area) {
        this.ff_area = ff_area;
    }

    public double getFs_area() {
        return this.fs_area;
    }

    public void setFs_area(double fs_area) {
        this.fs_area = fs_area;
    }

    public double getTot_area() {
        return this.tot_area;
    }

    public void setTot_area(double tot_area) {
        this.tot_area = tot_area;
    }

    public String getArea_uom() {
        return this.area_uom;
    }

    public void setArea_uom(String area_uom) {
        this.area_uom = area_uom;
    }

    public String getGrowth_stage() {
        return this.growth_stage;
    }

    public void setGrowth_stage(String growth_stage) {
        this.growth_stage = growth_stage;
    }

    public double getGdu_curr() {
        return this.gdu_curr;
    }

    public void setGdu_curr(double gdu_curr) {
        this.gdu_curr = gdu_curr;
    }

    public String getMoist35_date() {
        return this.moist35_date;
    }

    public void setMoist35_date(String moist35_date) {
        this.moist35_date = moist35_date;
    }

    public double getDrydown_rate() {
        return this.drydown_rate;
    }

    public void setDrydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
    }

    public double getMst() {
        return this.mst;
    }

    public void setMst(double mst) {
        this.mst = mst;
    }

    public String getMst_date() {
        return this.mst_date;
    }

    public void setMst_date(String mst_date) {
        this.mst_date = mst_date;
    }

    public double getMst_imputed() {
        return this.mst_imputed;
    }

    public void setMst_imputed(double mst_imputed) {
        this.mst_imputed = mst_imputed;
    }

    public double getMst_imputed_field() {
        return this.mst_imputed_field;
    }

    public void setMst_imputed_field(double mst_imputed_field) {
        this.mst_imputed_field = mst_imputed_field;
    }

    public String getOptimal_mst_harvest_date() {
        return this.optimal_mst_harvest_date;
    }

    public void setOptimal_mst_harvest_date(String optimal_mst_harvest_date) {
        this.optimal_mst_harvest_date = optimal_mst_harvest_date;
    }

    public String getMin_mst_harvest_date() {
        return this.min_mst_harvest_date;
    }

    public void setMin_mst_harvest_date(String min_mst_harvest_date) {
        this.min_mst_harvest_date = min_mst_harvest_date;
    }

    public String getMax_mst_harvest_date() {
        return this.max_mst_harvest_date;
    }

    public void setMax_mst_harvest_date(String max_mst_harvest_date) {
        this.max_mst_harvest_date = max_mst_harvest_date;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getReport_date() {
        return this.report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWkt() {
        return this.wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public GSMData entityid(String entityid) {
        this.entityid = entityid;
        return this;
    }

    public GSMData production_field_id(String production_field_id) {
        this.production_field_id = production_field_id;
        return this;
    }

    public GSMData country(String country) {
        this.country = country;
        return this;
    }

    public GSMData site_key(String site_key) {
        this.site_key = site_key;
        return this;
    }

    public GSMData plant(String plant) {
        this.plant = plant;
        return this;
    }

    public GSMData field_num(String field_num) {
        this.field_num = field_num;
        return this;
    }

    public GSMData planting_area_id(int planting_area_id) {
        this.planting_area_id = planting_area_id;
        return this;
    }

    public GSMData planting_area_name(String planting_area_name) {
        this.planting_area_name = planting_area_name;
        return this;
    }

    public GSMData macrozone(String macrozone) {
        this.macrozone = macrozone;
        return this;
    }

    public GSMData seedsman_area(String seedsman_area) {
        this.seedsman_area = seedsman_area;
        return this;
    }

    public GSMData pfo_name(String pfo_name) {
        this.pfo_name = pfo_name;
        return this;
    }

    public GSMData user_group(String user_group) {
        this.user_group = user_group;
        return this;
    }

    public GSMData tracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
        return this;
    }

    public GSMData contract_number(String contract_number) {
        this.contract_number = contract_number;
        return this;
    }

    public GSMData year(int year) {
        this.year = year;
        return this;
    }

    public GSMData season(String season) {
        this.season = season;
        return this;
    }

    public GSMData planting_date(String planting_date) {
        this.planting_date = planting_date;
        return this;
    }

    public GSMData planting_window(String planting_window) {
        this.planting_window = planting_window;
        return this;
    }

    public GSMData female_fertile(String female_fertile) {
        this.female_fertile = female_fertile;
        return this;
    }

    public GSMData female_sterile(String female_sterile) {
        this.female_sterile = female_sterile;
        return this;
    }

    public GSMData variety(String variety) {
        this.variety = variety;
        return this;
    }

    public GSMData planted_surface(double planted_surface) {
        this.planted_surface = planted_surface;
        return this;
    }

    public GSMData tot_female_area(double tot_female_area) {
        this.tot_female_area = tot_female_area;
        return this;
    }

    public GSMData ff_area(double ff_area) {
        this.ff_area = ff_area;
        return this;
    }

    public GSMData fs_area(double fs_area) {
        this.fs_area = fs_area;
        return this;
    }

    public GSMData tot_area(double tot_area) {
        this.tot_area = tot_area;
        return this;
    }

    public GSMData area_uom(String area_uom) {
        this.area_uom = area_uom;
        return this;
    }

    public GSMData growth_stage(String growth_stage) {
        this.growth_stage = growth_stage;
        return this;
    }

    public GSMData gdu_curr(double gdu_curr) {
        this.gdu_curr = gdu_curr;
        return this;
    }

    public GSMData moist35_date(String moist35_date) {
        this.moist35_date = moist35_date;
        return this;
    }

    public GSMData drydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
        return this;
    }

    public GSMData mst(double mst) {
        this.mst = mst;
        return this;
    }

    public GSMData mst_date(String mst_date) {
        this.mst_date = mst_date;
        return this;
    }

    public GSMData mst_imputed(double mst_imputed) {
        this.mst_imputed = mst_imputed;
        return this;
    }

    public GSMData mst_imputed_field(double mst_imputed_field) {
        this.mst_imputed_field = mst_imputed_field;
        return this;
    }

    public GSMData optimal_mst_harvest_date(String optimal_mst_harvest_date) {
        this.optimal_mst_harvest_date = optimal_mst_harvest_date;
        return this;
    }

    public GSMData min_mst_harvest_date(String min_mst_harvest_date) {
        this.min_mst_harvest_date = min_mst_harvest_date;
        return this;
    }

    public GSMData max_mst_harvest_date(String max_mst_harvest_date) {
        this.max_mst_harvest_date = max_mst_harvest_date;
        return this;
    }

    public GSMData lat(double lat) {
        this.lat = lat;
        return this;
    }

    public GSMData lon(double lon) {
        this.lon = lon;
        return this;
    }

    public GSMData report_date(String report_date) {
        this.report_date = report_date;
        return this;
    }

    public GSMData region(String region) {
        this.region = region;
        return this;
    }

    public GSMData wkt(String wkt) {
        this.wkt = wkt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GSMData)) {
            return false;
        }
        GSMData gSMData = (GSMData) o;
        return Objects.equals(entityid, gSMData.entityid) && Objects.equals(production_field_id, gSMData.production_field_id) && Objects.equals(country, gSMData.country) && Objects.equals(site_key, gSMData.site_key) && Objects.equals(plant, gSMData.plant) && Objects.equals(field_num, gSMData.field_num) && planting_area_id == gSMData.planting_area_id && Objects.equals(planting_area_name, gSMData.planting_area_name) && Objects.equals(macrozone, gSMData.macrozone) && Objects.equals(seedsman_area, gSMData.seedsman_area) && Objects.equals(pfo_name, gSMData.pfo_name) && Objects.equals(user_group, gSMData.user_group) && Objects.equals(tracking_number, gSMData.tracking_number) && Objects.equals(contract_number, gSMData.contract_number) && year == gSMData.year && Objects.equals(season, gSMData.season) && Objects.equals(planting_date, gSMData.planting_date) && Objects.equals(planting_window, gSMData.planting_window) && Objects.equals(female_fertile, gSMData.female_fertile) && Objects.equals(female_sterile, gSMData.female_sterile) && Objects.equals(variety, gSMData.variety) && planted_surface == gSMData.planted_surface && tot_female_area == gSMData.tot_female_area && ff_area == gSMData.ff_area && fs_area == gSMData.fs_area && tot_area == gSMData.tot_area && Objects.equals(area_uom, gSMData.area_uom) && Objects.equals(growth_stage, gSMData.growth_stage) && gdu_curr == gSMData.gdu_curr && Objects.equals(moist35_date, gSMData.moist35_date) && drydown_rate == gSMData.drydown_rate && mst == gSMData.mst && Objects.equals(mst_date, gSMData.mst_date) && mst_imputed == gSMData.mst_imputed && mst_imputed_field == gSMData.mst_imputed_field && Objects.equals(optimal_mst_harvest_date, gSMData.optimal_mst_harvest_date) && Objects.equals(min_mst_harvest_date, gSMData.min_mst_harvest_date) && Objects.equals(max_mst_harvest_date, gSMData.max_mst_harvest_date) && lat == gSMData.lat && lon == gSMData.lon && Objects.equals(report_date, gSMData.report_date) && Objects.equals(region, gSMData.region) && Objects.equals(wkt, gSMData.wkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityid, production_field_id, country, site_key, plant, field_num, planting_area_id, planting_area_name, macrozone, seedsman_area, pfo_name, user_group, tracking_number, contract_number, year, season, planting_date, planting_window, female_fertile, female_sterile, variety, planted_surface, tot_female_area, ff_area, fs_area, tot_area, area_uom, growth_stage, gdu_curr, moist35_date, drydown_rate, mst, mst_date, mst_imputed, mst_imputed_field, optimal_mst_harvest_date, min_mst_harvest_date, max_mst_harvest_date, lat, lon, report_date, region, wkt);
    }

    @Override
    public String toString() {
        return "{" +
            " entityid='" + getEntityid() + "'" +
            ", production_field_id='" + getProduction_field_id() + "'" +
            ", country='" + getCountry() + "'" +
            ", site_key='" + getSite_key() + "'" +
            ", plant='" + getPlant() + "'" +
            ", field_num='" + getField_num() + "'" +
            ", planting_area_id='" + getPlanting_area_id() + "'" +
            ", planting_area_name='" + getPlanting_area_name() + "'" +
            ", macrozone='" + getMacrozone() + "'" +
            ", seedsman_area='" + getSeedsman_area() + "'" +
            ", pfo_name='" + getPfo_name() + "'" +
            ", user_group='" + getUser_group() + "'" +
            ", tracking_number='" + getTracking_number() + "'" +
            ", contract_number='" + getContract_number() + "'" +
            ", year='" + getYear() + "'" +
            ", season='" + getSeason() + "'" +
            ", planting_date='" + getPlanting_date() + "'" +
            ", planting_window='" + getPlanting_window() + "'" +
            ", female_fertile='" + getFemale_fertile() + "'" +
            ", female_sterile='" + getFemale_sterile() + "'" +
            ", variety='" + getVariety() + "'" +
            ", planted_surface='" + getPlanted_surface() + "'" +
            ", tot_female_area='" + getTot_female_area() + "'" +
            ", ff_area='" + getFf_area() + "'" +
            ", fs_area='" + getFs_area() + "'" +
            ", tot_area='" + getTot_area() + "'" +
            ", area_uom='" + getArea_uom() + "'" +
            ", growth_stage='" + getGrowth_stage() + "'" +
            ", gdu_curr='" + getGdu_curr() + "'" +
            ", moist35_date='" + getMoist35_date() + "'" +
            ", drydown_rate='" + getDrydown_rate() + "'" +
            ", mst='" + getMst() + "'" +
            ", mst_date='" + getMst_date() + "'" +
            ", mst_imputed='" + getMst_imputed() + "'" +
            ", mst_imputed_field='" + getMst_imputed_field() + "'" +
            ", optimal_mst_harvest_date='" + getOptimal_mst_harvest_date() + "'" +
            ", min_mst_harvest_date='" + getMin_mst_harvest_date() + "'" +
            ", max_mst_harvest_date='" + getMax_mst_harvest_date() + "'" +
            ", lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", report_date='" + getReport_date() + "'" +
            ", region='" + getRegion() + "'" +
            ", wkt='" + getWkt() + "'" +
            "}";
    }

}