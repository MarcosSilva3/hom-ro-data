package com.bayer.hom;

import java.util.List;
import java.util.Objects;

public class HOMInput {
    private List<Site> site;
    private List<FieldHOM> lFieldsHOM;
    private String day_one;
    private String user;
    private String region;
    private String method;
    private int tabu_size;
    private int max_iter;
    private int max_days;
    private int picker_cap;

    public HOMInput() {
    }

    public HOMInput(List<Site> site, List<FieldHOM> lFieldsHOM, String day_one, String user, String region,
            String method, int tabu_size, int max_iter, int max_days, int picker_cap) {
        this.site = site;
        this.lFieldsHOM = lFieldsHOM;
        this.day_one = day_one;
        this.user = user;
        this.region = region;
        this.method = method;
        this.tabu_size = tabu_size;
        this.max_iter = max_iter;
        this.max_days = max_days;
        this.picker_cap = picker_cap;
    }

    public List<Site> getSite() {
        return this.site;
    }

    public void setSite(List<Site> site) {
        this.site = site;
    }

    public List<FieldHOM> getLFieldsHOM() {
        return this.lFieldsHOM;
    }

    public void setLFieldsHOM(List<FieldHOM> lFieldsHOM) {
        this.lFieldsHOM = lFieldsHOM;
    }

    public String getDay_one() {
        return this.day_one;
    }

    public void setDay_one(String day_one) {
        this.day_one = day_one;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getTabu_size() {
        return this.tabu_size;
    }

    public void setTabu_size(int tabu_size) {
        this.tabu_size = tabu_size;
    }

    public int getMax_iter() {
        return this.max_iter;
    }

    public void setMax_iter(int max_iter) {
        this.max_iter = max_iter;
    }

    public int getMax_days() {
        return this.max_days;
    }

    public void setMax_days(int max_days) {
        this.max_days = max_days;
    }

    public int getPicker_cap() {
        return this.picker_cap;
    }

    public void setPicker_cap(int picker_cap) {
        this.picker_cap = picker_cap;
    }

    public HOMInput site(List<Site> site) {
        this.site = site;
        return this;
    }

    public HOMInput lFieldsHOM(List<FieldHOM> lFieldsHOM) {
        this.lFieldsHOM = lFieldsHOM;
        return this;
    }

    public HOMInput day_one(String day_one) {
        this.day_one = day_one;
        return this;
    }

    public HOMInput user(String user) {
        this.user = user;
        return this;
    }

    public HOMInput region(String region) {
        this.region = region;
        return this;
    }

    public HOMInput method(String method) {
        this.method = method;
        return this;
    }

    public HOMInput tabu_size(int tabu_size) {
        this.tabu_size = tabu_size;
        return this;
    }

    public HOMInput max_iter(int max_iter) {
        this.max_iter = max_iter;
        return this;
    }

    public HOMInput max_days(int max_days) {
        this.max_days = max_days;
        return this;
    }

    public HOMInput picker_cap(int picker_cap) {
        this.picker_cap = picker_cap;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HOMInput)) {
            return false;
        }
        HOMInput hOMInput = (HOMInput) o;
        return Objects.equals(site, hOMInput.site) && Objects.equals(lFieldsHOM, hOMInput.lFieldsHOM)
                && Objects.equals(day_one, hOMInput.day_one) && Objects.equals(user, hOMInput.user)
                && Objects.equals(region, hOMInput.region) && Objects.equals(method, hOMInput.method)
                && tabu_size == hOMInput.tabu_size && max_iter == hOMInput.max_iter && max_days == hOMInput.max_days
                && picker_cap == hOMInput.picker_cap;
    }

    @Override
    public int hashCode() {
        return Objects.hash(site, lFieldsHOM, day_one, user, region, method, tabu_size, max_iter, max_days, picker_cap);
    }

    @Override
    public String toString() {
        return "{" + " site='" + getSite() + "'" + ", lFieldsHOM='" + getLFieldsHOM() + "'" + ", day_one='"
                + getDay_one() + "'" + ", user='" + getUser() + "'" + ", region='" + getRegion() + "'" + ", method='"
                + getMethod() + "'" + ", tabu_size='" + getTabu_size() + "'" + ", max_iter='" + getMax_iter() + "'"
                + ", max_days='" + getMax_days() + "'" + ", picker_cap='" + getPicker_cap() + "'" + "}";
    }

}