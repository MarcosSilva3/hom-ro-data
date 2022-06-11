package com.bayer.hom;

import java.util.Objects;

public class HybridData {
    private String hybrid;
    private double highest_harv_moisture;
    private double lowest_harv_moisture;

    public HybridData() {
    }

    public HybridData(String hybrid, double highest_harv_moisture, double lowest_harv_moisture) {
        this.hybrid = hybrid;
        this.highest_harv_moisture = highest_harv_moisture;
        this.lowest_harv_moisture = lowest_harv_moisture;
    }

    public String getHybrid() {
        return this.hybrid;
    }

    public void setHybrid(String hybrid) {
        this.hybrid = hybrid;
    }

    public double getHighest_harv_moisture() {
        return this.highest_harv_moisture;
    }

    public void setHighest_harv_moisture(double highest_harv_moisture) {
        this.highest_harv_moisture = highest_harv_moisture;
    }

    public double getLowest_harv_moisture() {
        return this.lowest_harv_moisture;
    }

    public void setLowest_harv_moisture(double lowest_harv_moisture) {
        this.lowest_harv_moisture = lowest_harv_moisture;
    }

    public HybridData hybrid(String hybrid) {
        this.hybrid = hybrid;
        return this;
    }

    public HybridData highest_harv_moisture(double highest_harv_moisture) {
        this.highest_harv_moisture = highest_harv_moisture;
        return this;
    }

    public HybridData lowest_harv_moisture(double lowest_harv_moisture) {
        this.lowest_harv_moisture = lowest_harv_moisture;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HybridData)) {
            return false;
        }
        HybridData hybridData = (HybridData) o;
        return Objects.equals(hybrid, hybridData.hybrid) && highest_harv_moisture == hybridData.highest_harv_moisture
                && lowest_harv_moisture == hybridData.lowest_harv_moisture;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hybrid, highest_harv_moisture, lowest_harv_moisture);
    }

    @Override
    public String toString() {
        return "{" + " hybrid='" + getHybrid() + "'" + ", highest_harv_moisture='" + getHighest_harv_moisture() + "'"
                + ", lowest_harv_moisture='" + getLowest_harv_moisture() + "'" + "}";
    }

}