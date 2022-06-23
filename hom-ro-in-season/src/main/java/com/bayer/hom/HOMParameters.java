package com.bayer.hom;

import java.util.Objects;

public class HOMParameters {
    private String log_config_file;
    private String country;
    private int year;
    private int year_for_contract;
    private String season;
    private String private_key_file;
    private String project_id;
    private String regionCode;
    private String cropCycleCode;
    private String env_client_id;
    private String env_client_secret;

    public HOMParameters() {
    }

    public HOMParameters(String log_config_file, String country, int year, int year_for_contract, String season,
            String private_key_file, String project_id, String regionCode, String cropCycleCode, String env_client_id,
            String env_client_secret) {
        this.log_config_file = log_config_file;
        this.country = country;
        this.year = year;
        this.year_for_contract = year_for_contract;
        this.season = season;
        this.private_key_file = private_key_file;
        this.project_id = project_id;
        this.regionCode = regionCode;
        this.cropCycleCode = cropCycleCode;
        this.env_client_id = env_client_id;
        this.env_client_secret = env_client_secret;
    }

    public String getLog_config_file() {
        return this.log_config_file;
    }

    public void setLog_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear_for_contract() {
        return this.year_for_contract;
    }

    public void setYear_for_contract(int year_for_contract) {
        this.year_for_contract = year_for_contract;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPrivate_key_file() {
        return this.private_key_file;
    }

    public void setPrivate_key_file(String private_key_file) {
        this.private_key_file = private_key_file;
    }

    public String getProject_id() {
        return this.project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCropCycleCode() {
        return this.cropCycleCode;
    }

    public void setCropCycleCode(String cropCycleCode) {
        this.cropCycleCode = cropCycleCode;
    }

    public String getEnv_client_id() {
        return this.env_client_id;
    }

    public void setEnv_client_id(String env_client_id) {
        this.env_client_id = env_client_id;
    }

    public String getEnv_client_secret() {
        return this.env_client_secret;
    }

    public void setEnv_client_secret(String env_client_secret) {
        this.env_client_secret = env_client_secret;
    }

    public HOMParameters log_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
        return this;
    }

    public HOMParameters country(String country) {
        this.country = country;
        return this;
    }

    public HOMParameters year(int year) {
        this.year = year;
        return this;
    }

    public HOMParameters year_for_contract(int year_for_contract) {
        this.year_for_contract = year_for_contract;
        return this;
    }

    public HOMParameters season(String season) {
        this.season = season;
        return this;
    }

    public HOMParameters private_key_file(String private_key_file) {
        this.private_key_file = private_key_file;
        return this;
    }

    public HOMParameters project_id(String project_id) {
        this.project_id = project_id;
        return this;
    }

    public HOMParameters regionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public HOMParameters cropCycleCode(String cropCycleCode) {
        this.cropCycleCode = cropCycleCode;
        return this;
    }

    public HOMParameters env_client_id(String env_client_id) {
        this.env_client_id = env_client_id;
        return this;
    }

    public HOMParameters env_client_secret(String env_client_secret) {
        this.env_client_secret = env_client_secret;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HOMParameters)) {
            return false;
        }
        HOMParameters hOMParameters = (HOMParameters) o;
        return Objects.equals(log_config_file, hOMParameters.log_config_file)
                && Objects.equals(country, hOMParameters.country) && year == hOMParameters.year
                && year_for_contract == hOMParameters.year_for_contract && Objects.equals(season, hOMParameters.season)
                && Objects.equals(private_key_file, hOMParameters.private_key_file)
                && Objects.equals(project_id, hOMParameters.project_id)
                && Objects.equals(regionCode, hOMParameters.regionCode)
                && Objects.equals(cropCycleCode, hOMParameters.cropCycleCode)
                && Objects.equals(env_client_id, hOMParameters.env_client_id)
                && Objects.equals(env_client_secret, hOMParameters.env_client_secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(log_config_file, country, year, year_for_contract, season, private_key_file, project_id,
                regionCode, cropCycleCode, env_client_id, env_client_secret);
    }

    @Override
    public String toString() {
        return "{" + " log_config_file='" + getLog_config_file() + "'" + ", country='" + getCountry() + "'" + ", year='"
                + getYear() + "'" + ", year_for_contract='" + getYear_for_contract() + "'" + ", season='" + getSeason()
                + "'" + ", private_key_file='" + getPrivate_key_file() + "'" + ", project_id='" + getProject_id() + "'"
                + ", regionCode='" + getRegionCode() + "'" + ", cropCycleCode='" + getCropCycleCode() + "'"
                + ", env_client_id='" + getEnv_client_id() + "'" + ", env_client_secret='" + getEnv_client_secret()
                + "'" + "}";
    }

}