package com.bayer.hom;

import java.util.Objects;

public class FieldManualPlan {
    private String region;
    private String seed_plant;
    private String dh_qualifyed;
    private String grower;
    private String tracking_number;
    private String hybrid;
    private String suspect;
    private String suspect_comments;
    private String husking_difficulty;
    private String female;
    private String male;
    private double active_ha;
    private double yield_ton_ha;
    private String picker_group;
    private String harvest_date;
    private String harvest_window_start;
    private String harvest_window_end;

    public FieldManualPlan() {
    }

    public FieldManualPlan(String region, String seed_plant, String dh_qualifyed, String grower, String tracking_number,
            String hybrid, String suspect, String suspect_comments, String husking_difficulty, String female,
            String male, double active_ha, double yield_ton_ha, String picker_group, String harvest_date,
            String harvest_window_start, String harvest_window_end) {
        this.region = region;
        this.seed_plant = seed_plant;
        this.dh_qualifyed = dh_qualifyed;
        this.grower = grower;
        this.tracking_number = tracking_number;
        this.hybrid = hybrid;
        this.suspect = suspect;
        this.suspect_comments = suspect_comments;
        this.husking_difficulty = husking_difficulty;
        this.female = female;
        this.male = male;
        this.active_ha = active_ha;
        this.yield_ton_ha = yield_ton_ha;
        this.picker_group = picker_group;
        this.harvest_date = harvest_date;
        this.harvest_window_start = harvest_window_start;
        this.harvest_window_end = harvest_window_end;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeed_plant() {
        return this.seed_plant;
    }

    public void setSeed_plant(String seed_plant) {
        this.seed_plant = seed_plant;
    }

    public String getDh_qualifyed() {
        return this.dh_qualifyed;
    }

    public void setDh_qualifyed(String dh_qualifyed) {
        this.dh_qualifyed = dh_qualifyed;
    }

    public String getGrower() {
        return this.grower;
    }

    public void setGrower(String grower) {
        this.grower = grower;
    }

    public String getTracking_number() {
        return this.tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getHybrid() {
        return this.hybrid;
    }

    public void setHybrid(String hybrid) {
        this.hybrid = hybrid;
    }

    public String getSuspect() {
        return this.suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    public String getSuspect_comments() {
        return this.suspect_comments;
    }

    public void setSuspect_comments(String suspect_comments) {
        this.suspect_comments = suspect_comments;
    }

    public String getHusking_difficulty() {
        return this.husking_difficulty;
    }

    public void setHusking_difficulty(String husking_difficulty) {
        this.husking_difficulty = husking_difficulty;
    }

    public String getFemale() {
        return this.female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getMale() {
        return this.male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public double getActive_ha() {
        return this.active_ha;
    }

    public void setActive_ha(double active_ha) {
        this.active_ha = active_ha;
    }

    public double getYield_ton_ha() {
        return this.yield_ton_ha;
    }

    public void setYield_ton_ha(double yield_ton_ha) {
        this.yield_ton_ha = yield_ton_ha;
    }

    public String getPicker_group() {
        return this.picker_group;
    }

    public void setPicker_group(String picker_group) {
        this.picker_group = picker_group;
    }

    public String getHarvest_date() {
        return this.harvest_date;
    }

    public void setHarvest_date(String harvest_date) {
        this.harvest_date = harvest_date;
    }

    public String getHarvest_window_start() {
        return this.harvest_window_start;
    }

    public void setHarvest_window_start(String harvest_window_start) {
        this.harvest_window_start = harvest_window_start;
    }

    public String getHarvest_window_end() {
        return this.harvest_window_end;
    }

    public void setHarvest_window_end(String harvest_window_end) {
        this.harvest_window_end = harvest_window_end;
    }

    public FieldManualPlan region(String region) {
        this.region = region;
        return this;
    }

    public FieldManualPlan seed_plant(String seed_plant) {
        this.seed_plant = seed_plant;
        return this;
    }

    public FieldManualPlan dh_qualifyed(String dh_qualifyed) {
        this.dh_qualifyed = dh_qualifyed;
        return this;
    }

    public FieldManualPlan grower(String grower) {
        this.grower = grower;
        return this;
    }

    public FieldManualPlan tracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
        return this;
    }

    public FieldManualPlan hybrid(String hybrid) {
        this.hybrid = hybrid;
        return this;
    }

    public FieldManualPlan suspect(String suspect) {
        this.suspect = suspect;
        return this;
    }

    public FieldManualPlan suspect_comments(String suspect_comments) {
        this.suspect_comments = suspect_comments;
        return this;
    }

    public FieldManualPlan husking_difficulty(String husking_difficulty) {
        this.husking_difficulty = husking_difficulty;
        return this;
    }

    public FieldManualPlan female(String female) {
        this.female = female;
        return this;
    }

    public FieldManualPlan male(String male) {
        this.male = male;
        return this;
    }

    public FieldManualPlan active_ha(double active_ha) {
        this.active_ha = active_ha;
        return this;
    }

    public FieldManualPlan yield_ton_ha(double yield_ton_ha) {
        this.yield_ton_ha = yield_ton_ha;
        return this;
    }

    public FieldManualPlan picker_group(String picker_group) {
        this.picker_group = picker_group;
        return this;
    }

    public FieldManualPlan harvest_date(String harvest_date) {
        this.harvest_date = harvest_date;
        return this;
    }

    public FieldManualPlan harvest_window_start(String harvest_window_start) {
        this.harvest_window_start = harvest_window_start;
        return this;
    }

    public FieldManualPlan harvest_window_end(String harvest_window_end) {
        this.harvest_window_end = harvest_window_end;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FieldManualPlan)) {
            return false;
        }
        FieldManualPlan fieldManualPlan = (FieldManualPlan) o;
        return Objects.equals(region, fieldManualPlan.region) && Objects.equals(seed_plant, fieldManualPlan.seed_plant)
                && Objects.equals(dh_qualifyed, fieldManualPlan.dh_qualifyed)
                && Objects.equals(grower, fieldManualPlan.grower)
                && Objects.equals(tracking_number, fieldManualPlan.tracking_number)
                && Objects.equals(hybrid, fieldManualPlan.hybrid) && Objects.equals(suspect, fieldManualPlan.suspect)
                && Objects.equals(suspect_comments, fieldManualPlan.suspect_comments)
                && Objects.equals(husking_difficulty, fieldManualPlan.husking_difficulty)
                && Objects.equals(female, fieldManualPlan.female) && Objects.equals(male, fieldManualPlan.male)
                && active_ha == fieldManualPlan.active_ha && yield_ton_ha == fieldManualPlan.yield_ton_ha
                && Objects.equals(picker_group, fieldManualPlan.picker_group)
                && Objects.equals(harvest_date, fieldManualPlan.harvest_date)
                && Objects.equals(harvest_window_start, fieldManualPlan.harvest_window_start)
                && Objects.equals(harvest_window_end, fieldManualPlan.harvest_window_end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, seed_plant, dh_qualifyed, grower, tracking_number, hybrid, suspect,
                suspect_comments, husking_difficulty, female, male, active_ha, yield_ton_ha, picker_group, harvest_date,
                harvest_window_start, harvest_window_end);
    }

    @Override
    public String toString() {
        return "{" + " region='" + getRegion() + "'" + ", seed_plant='" + getSeed_plant() + "'" + ", dh_qualifyed='"
                + getDh_qualifyed() + "'" + ", grower='" + getGrower() + "'" + ", tracking_number='"
                + getTracking_number() + "'" + ", hybrid='" + getHybrid() + "'" + ", suspect='" + getSuspect() + "'"
                + ", suspect_comments='" + getSuspect_comments() + "'" + ", husking_difficulty='"
                + getHusking_difficulty() + "'" + ", female='" + getFemale() + "'" + ", male='" + getMale() + "'"
                + ", active_ha='" + getActive_ha() + "'" + ", yield_ton_ha='" + getYield_ton_ha() + "'"
                + ", picker_group='" + getPicker_group() + "'" + ", harvest_date='" + getHarvest_date() + "'"
                + ", harvest_window_start='" + getHarvest_window_start() + "'" + ", harvest_window_end='"
                + getHarvest_window_end() + "'" + "}";
    }

}