package com.bayer.hom;

import java.util.Objects;

public class HOMResult {
    private String field;
    private String picker;
    private String variety;
    private String harv_date;
    private double total_area;
    private double area_harv;
    private double total_tonrw;
    private double tonrw_harv;
    private double lat;
    private double lon;
    private String grower;
    private double moisture;
    private String tracking_number;

    public HOMResult() {
    }

    public HOMResult(String field, String picker, String variety, String harv_date, double total_area, double area_harv,
            double total_tonrw, double tonrw_harv, double lat, double lon, String grower, double moisture,
            String tracking_number) {
        this.field = field;
        this.picker = picker;
        this.variety = variety;
        this.harv_date = harv_date;
        this.total_area = total_area;
        this.area_harv = area_harv;
        this.total_tonrw = total_tonrw;
        this.tonrw_harv = tonrw_harv;
        this.lat = lat;
        this.lon = lon;
        this.grower = grower;
        this.moisture = moisture;
        this.tracking_number = tracking_number;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPicker() {
        return this.picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }

    public String getVariety() {
        return this.variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getHarv_date() {
        return this.harv_date;
    }

    public void setHarv_date(String harv_date) {
        this.harv_date = harv_date;
    }

    public double getTotal_area() {
        return this.total_area;
    }

    public void setTotal_area(double total_area) {
        this.total_area = total_area;
    }

    public double getArea_harv() {
        return this.area_harv;
    }

    public void setArea_harv(double area_harv) {
        this.area_harv = area_harv;
    }

    public double getTotal_tonrw() {
        return this.total_tonrw;
    }

    public void setTotal_tonrw(double total_tonrw) {
        this.total_tonrw = total_tonrw;
    }

    public double getTonrw_harv() {
        return this.tonrw_harv;
    }

    public void setTonrw_harv(double tonrw_harv) {
        this.tonrw_harv = tonrw_harv;
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

    public String getGrower() {
        return this.grower;
    }

    public void setGrower(String grower) {
        this.grower = grower;
    }

    public double getMoisture() {
        return this.moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public String getTracking_number() {
        return this.tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public HOMResult field(String field) {
        this.field = field;
        return this;
    }

    public HOMResult picker(String picker) {
        this.picker = picker;
        return this;
    }

    public HOMResult variety(String variety) {
        this.variety = variety;
        return this;
    }

    public HOMResult harv_date(String harv_date) {
        this.harv_date = harv_date;
        return this;
    }

    public HOMResult total_area(double total_area) {
        this.total_area = total_area;
        return this;
    }

    public HOMResult area_harv(double area_harv) {
        this.area_harv = area_harv;
        return this;
    }

    public HOMResult total_tonrw(double total_tonrw) {
        this.total_tonrw = total_tonrw;
        return this;
    }

    public HOMResult tonrw_harv(double tonrw_harv) {
        this.tonrw_harv = tonrw_harv;
        return this;
    }

    public HOMResult lat(double lat) {
        this.lat = lat;
        return this;
    }

    public HOMResult lon(double lon) {
        this.lon = lon;
        return this;
    }

    public HOMResult grower(String grower) {
        this.grower = grower;
        return this;
    }

    public HOMResult moisture(double moisture) {
        this.moisture = moisture;
        return this;
    }

    public HOMResult tracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HOMResult)) {
            return false;
        }
        HOMResult hOMResult = (HOMResult) o;
        return Objects.equals(field, hOMResult.field) && Objects.equals(picker, hOMResult.picker)
                && Objects.equals(variety, hOMResult.variety) && Objects.equals(harv_date, hOMResult.harv_date)
                && total_area == hOMResult.total_area && area_harv == hOMResult.area_harv
                && total_tonrw == hOMResult.total_tonrw && tonrw_harv == hOMResult.tonrw_harv && lat == hOMResult.lat
                && lon == hOMResult.lon && Objects.equals(grower, hOMResult.grower) && moisture == hOMResult.moisture
                && Objects.equals(tracking_number, hOMResult.tracking_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, picker, variety, harv_date, total_area, area_harv, total_tonrw, tonrw_harv, lat, lon,
                grower, moisture, tracking_number);
    }

    @Override
    public String toString() {
        return "{" + " field='" + getField() + "'" + ", picker='" + getPicker() + "'" + ", variety='" + getVariety()
                + "'" + ", harv_date='" + getHarv_date() + "'" + ", total_area='" + getTotal_area() + "'"
                + ", area_harv='" + getArea_harv() + "'" + ", total_tonrw='" + getTotal_tonrw() + "'" + ", tonrw_harv='"
                + getTonrw_harv() + "'" + ", lat='" + getLat() + "'" + ", lon='" + getLon() + "'" + ", grower='"
                + getGrower() + "'" + ", moisture='" + getMoisture() + "'" + ", tracking_number='"
                + getTracking_number() + "'" + "}";
    }

}