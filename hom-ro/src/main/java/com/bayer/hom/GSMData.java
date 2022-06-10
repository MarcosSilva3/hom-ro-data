package com.bayer.hom;

import java.util.Objects;

public class GSMData {

    private String country;
    private String site_key;
    private int year;
    private String season;
    private String tracking_number;
    private String pfo_name;
    private String entityid;
    private String variety;
    private double mst;
    private String mst_date;
    private double drydown_rate;

    public GSMData() {
    }

    public GSMData(String country, String site_key, int year, String season, String tracking_number, String pfo_name,
            String entityid, String variety, double mst, String mst_date, double drydown_rate) {
        this.country = country;
        this.site_key = site_key;
        this.year = year;
        this.season = season;
        this.tracking_number = tracking_number;
        this.pfo_name = pfo_name;
        this.entityid = entityid;
        this.variety = variety;
        this.mst = mst;
        this.mst_date = mst_date;
        this.drydown_rate = drydown_rate;
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

    public String getTracking_number() {
        return this.tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getPfo_name() {
        return this.pfo_name;
    }

    public void setPfo_name(String pfo_name) {
        this.pfo_name = pfo_name;
    }

    public String getEntityid() {
        return this.entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    public String getVariety() {
        return this.variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
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

    public double getDrydown_rate() {
        return this.drydown_rate;
    }

    public void setDrydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
    }

    public GSMData country(String country) {
        this.country = country;
        return this;
    }

    public GSMData site_key(String site_key) {
        this.site_key = site_key;
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

    public GSMData tracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
        return this;
    }

    public GSMData pfo_name(String pfo_name) {
        this.pfo_name = pfo_name;
        return this;
    }

    public GSMData entityid(String entityid) {
        this.entityid = entityid;
        return this;
    }

    public GSMData variety(String variety) {
        this.variety = variety;
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

    public GSMData drydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
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
        return Objects.equals(country, gSMData.country) && Objects.equals(site_key, gSMData.site_key)
                && year == gSMData.year && Objects.equals(season, gSMData.season)
                && Objects.equals(tracking_number, gSMData.tracking_number)
                && Objects.equals(pfo_name, gSMData.pfo_name) && Objects.equals(entityid, gSMData.entityid)
                && Objects.equals(variety, gSMData.variety) && mst == gSMData.mst
                && Objects.equals(mst_date, gSMData.mst_date) && drydown_rate == gSMData.drydown_rate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, site_key, year, season, tracking_number, pfo_name, entityid, variety, mst,
                mst_date, drydown_rate);
    }

    @Override
    public String toString() {
        return "{" + " country='" + getCountry() + "'" + ", site_key='" + getSite_key() + "'" + ", year='" + getYear()
                + "'" + ", season='" + getSeason() + "'" + ", tracking_number='" + getTracking_number() + "'"
                + ", pfo_name='" + getPfo_name() + "'" + ", entityid='" + getEntityid() + "'" + ", variety='"
                + getVariety() + "'" + ", mst='" + getMst() + "'" + ", mst_date='" + getMst_date() + "'"
                + ", drydown_rate='" + getDrydown_rate() + "'" + "}";
    }

}