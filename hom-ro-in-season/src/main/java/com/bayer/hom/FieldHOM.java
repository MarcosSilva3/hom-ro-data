package com.bayer.hom;

import java.util.Objects;

public class FieldHOM {
    private String lot;
    private String hybrid;
    private int sitekey;
    private String region;
    private String cluster;
    private String twstart;
    private String twend;
    private double area;
    private double drydown_rate;
    private double latitude;
    private double longitude;
    private int lowest_harvest_moisture;
    private int highest_harvest_moisture;
    private double tonha;
    private double kg;
    private String abc;
    private String harv_type;

    public FieldHOM() {
    }

    public FieldHOM(String lot, String hybrid, int sitekey, String region, String cluster, String twstart, String twend,
            double area, double drydown_rate, double latitude, double longitude, int lowest_harvest_moisture,
            int highest_harvest_moisture, double tonha, double kg, String abc, String harv_type) {
        this.lot = lot;
        this.hybrid = hybrid;
        this.sitekey = sitekey;
        this.region = region;
        this.cluster = cluster;
        this.twstart = twstart;
        this.twend = twend;
        this.area = area;
        this.drydown_rate = drydown_rate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lowest_harvest_moisture = lowest_harvest_moisture;
        this.highest_harvest_moisture = highest_harvest_moisture;
        this.tonha = tonha;
        this.kg = kg;
        this.abc = abc;
        this.harv_type = harv_type;
    }

    public String getLot() {
        return this.lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getHybrid() {
        return this.hybrid;
    }

    public void setHybrid(String hybrid) {
        this.hybrid = hybrid;
    }

    public int getSitekey() {
        return this.sitekey;
    }

    public void setSitekey(int sitekey) {
        this.sitekey = sitekey;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCluster() {
        return this.cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getTwstart() {
        return this.twstart;
    }

    public void setTwstart(String twstart) {
        this.twstart = twstart;
    }

    public String getTwend() {
        return this.twend;
    }

    public void setTwend(String twend) {
        this.twend = twend;
    }

    public double getArea() {
        return this.area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getDrydown_rate() {
        return this.drydown_rate;
    }

    public void setDrydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getLowest_harvest_moisture() {
        return this.lowest_harvest_moisture;
    }

    public void setLowest_harvest_moisture(int lowest_harvest_moisture) {
        this.lowest_harvest_moisture = lowest_harvest_moisture;
    }

    public int getHighest_harvest_moisture() {
        return this.highest_harvest_moisture;
    }

    public void setHighest_harvest_moisture(int highest_harvest_moisture) {
        this.highest_harvest_moisture = highest_harvest_moisture;
    }

    public double getTonha() {
        return this.tonha;
    }

    public void setTonha(double tonha) {
        this.tonha = tonha;
    }

    public double getKg() {
        return this.kg;
    }

    public void setKg(double kg) {
        this.kg = kg;
    }

    public String getAbc() {
        return this.abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public String getHarv_type() {
        return this.harv_type;
    }

    public void setHarv_type(String harv_type) {
        this.harv_type = harv_type;
    }

    public FieldHOM lot(String lot) {
        this.lot = lot;
        return this;
    }

    public FieldHOM hybrid(String hybrid) {
        this.hybrid = hybrid;
        return this;
    }

    public FieldHOM sitekey(int sitekey) {
        this.sitekey = sitekey;
        return this;
    }

    public FieldHOM region(String region) {
        this.region = region;
        return this;
    }

    public FieldHOM cluster(String cluster) {
        this.cluster = cluster;
        return this;
    }

    public FieldHOM twstart(String twstart) {
        this.twstart = twstart;
        return this;
    }

    public FieldHOM twend(String twend) {
        this.twend = twend;
        return this;
    }

    public FieldHOM area(double area) {
        this.area = area;
        return this;
    }

    public FieldHOM drydown_rate(double drydown_rate) {
        this.drydown_rate = drydown_rate;
        return this;
    }

    public FieldHOM latitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public FieldHOM longitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public FieldHOM lowest_harvest_moisture(int lowest_harvest_moisture) {
        this.lowest_harvest_moisture = lowest_harvest_moisture;
        return this;
    }

    public FieldHOM highest_harvest_moisture(int highest_harvest_moisture) {
        this.highest_harvest_moisture = highest_harvest_moisture;
        return this;
    }

    public FieldHOM tonha(double tonha) {
        this.tonha = tonha;
        return this;
    }

    public FieldHOM kg(double kg) {
        this.kg = kg;
        return this;
    }

    public FieldHOM abc(String abc) {
        this.abc = abc;
        return this;
    }

    public FieldHOM harv_type(String harv_type) {
        this.harv_type = harv_type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FieldHOM)) {
            return false;
        }
        FieldHOM fieldHOM = (FieldHOM) o;
        return Objects.equals(lot, fieldHOM.lot) && Objects.equals(hybrid, fieldHOM.hybrid)
                && sitekey == fieldHOM.sitekey && Objects.equals(region, fieldHOM.region)
                && Objects.equals(cluster, fieldHOM.cluster) && Objects.equals(twstart, fieldHOM.twstart)
                && Objects.equals(twend, fieldHOM.twend) && area == fieldHOM.area
                && drydown_rate == fieldHOM.drydown_rate && latitude == fieldHOM.latitude
                && longitude == fieldHOM.longitude && lowest_harvest_moisture == fieldHOM.lowest_harvest_moisture
                && highest_harvest_moisture == fieldHOM.highest_harvest_moisture && tonha == fieldHOM.tonha
                && kg == fieldHOM.kg && Objects.equals(abc, fieldHOM.abc)
                && Objects.equals(harv_type, fieldHOM.harv_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lot, hybrid, sitekey, region, cluster, twstart, twend, area, drydown_rate, latitude,
                longitude, lowest_harvest_moisture, highest_harvest_moisture, tonha, kg, abc, harv_type);
    }

    @Override
    public String toString() {
        return "{" + " lot='" + getLot() + "'" + ", hybrid='" + getHybrid() + "'" + ", sitekey='" + getSitekey() + "'"
                + ", region='" + getRegion() + "'" + ", cluster='" + getCluster() + "'" + ", twstart='" + getTwstart()
                + "'" + ", twend='" + getTwend() + "'" + ", area='" + getArea() + "'" + ", drydown_rate='"
                + getDrydown_rate() + "'" + ", latitude='" + getLatitude() + "'" + ", longitude='" + getLongitude()
                + "'" + ", lowest_harvest_moisture='" + getLowest_harvest_moisture() + "'"
                + ", highest_harvest_moisture='" + getHighest_harvest_moisture() + "'" + ", tonha='" + getTonha() + "'"
                + ", kg='" + getKg() + "'" + ", abc='" + getAbc() + "'" + ", harv_type='" + getHarv_type() + "'" + "}";
    }

}