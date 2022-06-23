package com.bayer.hom;

import java.util.Objects;

public class Site {
    private String site_name;
    private int sitekey;
    private int caphy_bulk;
    private int caphy_ear;
    private int caphy_only_ear;
    private double capton_ear;
    private double capton_bulk;
    private double capton_only_ear;
    private int captrucks_ear;
    private int captrucks_bulk;
    private int captrucks_only_ear;
    private String date;
    private String day_of_week;

    public Site() {
    }

    public Site(String site_name, int sitekey, int caphy_bulk, int caphy_ear, int caphy_only_ear, double capton_ear,
            double capton_bulk, double capton_only_ear, int captrucks_ear, int captrucks_bulk, int captrucks_only_ear,
            String date, String day_of_week) {
        this.site_name = site_name;
        this.sitekey = sitekey;
        this.caphy_bulk = caphy_bulk;
        this.caphy_ear = caphy_ear;
        this.caphy_only_ear = caphy_only_ear;
        this.capton_ear = capton_ear;
        this.capton_bulk = capton_bulk;
        this.capton_only_ear = capton_only_ear;
        this.captrucks_ear = captrucks_ear;
        this.captrucks_bulk = captrucks_bulk;
        this.captrucks_only_ear = captrucks_only_ear;
        this.date = date;
        this.day_of_week = day_of_week;
    }

    public String getSite_name() {
        return this.site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public int getSitekey() {
        return this.sitekey;
    }

    public void setSitekey(int sitekey) {
        this.sitekey = sitekey;
    }

    public int getCaphy_bulk() {
        return this.caphy_bulk;
    }

    public void setCaphy_bulk(int caphy_bulk) {
        this.caphy_bulk = caphy_bulk;
    }

    public int getCaphy_ear() {
        return this.caphy_ear;
    }

    public void setCaphy_ear(int caphy_ear) {
        this.caphy_ear = caphy_ear;
    }

    public int getCaphy_only_ear() {
        return this.caphy_only_ear;
    }

    public void setCaphy_only_ear(int caphy_only_ear) {
        this.caphy_only_ear = caphy_only_ear;
    }

    public double getCapton_ear() {
        return this.capton_ear;
    }

    public void setCapton_ear(double capton_ear) {
        this.capton_ear = capton_ear;
    }

    public double getCapton_bulk() {
        return this.capton_bulk;
    }

    public void setCapton_bulk(double capton_bulk) {
        this.capton_bulk = capton_bulk;
    }

    public double getCapton_only_ear() {
        return this.capton_only_ear;
    }

    public void setCapton_only_ear(double capton_only_ear) {
        this.capton_only_ear = capton_only_ear;
    }

    public int getCaptrucks_ear() {
        return this.captrucks_ear;
    }

    public void setCaptrucks_ear(int captrucks_ear) {
        this.captrucks_ear = captrucks_ear;
    }

    public int getCaptrucks_bulk() {
        return this.captrucks_bulk;
    }

    public void setCaptrucks_bulk(int captrucks_bulk) {
        this.captrucks_bulk = captrucks_bulk;
    }

    public int getCaptrucks_only_ear() {
        return this.captrucks_only_ear;
    }

    public void setCaptrucks_only_ear(int captrucks_only_ear) {
        this.captrucks_only_ear = captrucks_only_ear;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay_of_week() {
        return this.day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public Site site_name(String site_name) {
        this.site_name = site_name;
        return this;
    }

    public Site sitekey(int sitekey) {
        this.sitekey = sitekey;
        return this;
    }

    public Site caphy_bulk(int caphy_bulk) {
        this.caphy_bulk = caphy_bulk;
        return this;
    }

    public Site caphy_ear(int caphy_ear) {
        this.caphy_ear = caphy_ear;
        return this;
    }

    public Site caphy_only_ear(int caphy_only_ear) {
        this.caphy_only_ear = caphy_only_ear;
        return this;
    }

    public Site capton_ear(double capton_ear) {
        this.capton_ear = capton_ear;
        return this;
    }

    public Site capton_bulk(double capton_bulk) {
        this.capton_bulk = capton_bulk;
        return this;
    }

    public Site capton_only_ear(double capton_only_ear) {
        this.capton_only_ear = capton_only_ear;
        return this;
    }

    public Site captrucks_ear(int captrucks_ear) {
        this.captrucks_ear = captrucks_ear;
        return this;
    }

    public Site captrucks_bulk(int captrucks_bulk) {
        this.captrucks_bulk = captrucks_bulk;
        return this;
    }

    public Site captrucks_only_ear(int captrucks_only_ear) {
        this.captrucks_only_ear = captrucks_only_ear;
        return this;
    }

    public Site date(String date) {
        this.date = date;
        return this;
    }

    public Site day_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Site)) {
            return false;
        }
        Site site = (Site) o;
        return Objects.equals(site_name, site.site_name) && sitekey == site.sitekey && caphy_bulk == site.caphy_bulk
                && caphy_ear == site.caphy_ear && caphy_only_ear == site.caphy_only_ear && capton_ear == site.capton_ear
                && capton_bulk == site.capton_bulk && capton_only_ear == site.capton_only_ear
                && captrucks_ear == site.captrucks_ear && captrucks_bulk == site.captrucks_bulk
                && captrucks_only_ear == site.captrucks_only_ear && Objects.equals(date, site.date)
                && Objects.equals(day_of_week, site.day_of_week);
    }

    @Override
    public int hashCode() {
        return Objects.hash(site_name, sitekey, caphy_bulk, caphy_ear, caphy_only_ear, capton_ear, capton_bulk,
                capton_only_ear, captrucks_ear, captrucks_bulk, captrucks_only_ear, date, day_of_week);
    }

    @Override
    public String toString() {
        return "{" + " site_name='" + getSite_name() + "'" + ", sitekey='" + getSitekey() + "'" + ", caphy_bulk='"
                + getCaphy_bulk() + "'" + ", caphy_ear='" + getCaphy_ear() + "'" + ", caphy_only_ear='"
                + getCaphy_only_ear() + "'" + ", capton_ear='" + getCapton_ear() + "'" + ", capton_bulk='"
                + getCapton_bulk() + "'" + ", capton_only_ear='" + getCapton_only_ear() + "'" + ", captrucks_ear='"
                + getCaptrucks_ear() + "'" + ", captrucks_bulk='" + getCaptrucks_bulk() + "'" + ", captrucks_only_ear='"
                + getCaptrucks_only_ear() + "'" + ", date='" + getDate() + "'" + ", day_of_week='" + getDay_of_week()
                + "'" + "}";
    }

}