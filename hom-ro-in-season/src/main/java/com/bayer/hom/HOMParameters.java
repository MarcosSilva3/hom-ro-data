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
    private String manual_plan_excel_path;
    private String hom_day_one;
    private String hom_user;
    private int hom_tabu_size;
    private int hom_max_iter;
    private int hom_picker_cap;
    private String hom_region;
    private int hom_max_days;
    private String hom_method;
    private String clientIdEngine;
    private String clientSecretEngine;
    private String awsBucketName;
    private String plantNumber;
    private String env_hom_db_host;
    private String env_hom_db_port;
    private String env_hom_db_user;
    private String env_hom_db_pwd;
    private String hom_db_name;
    private String work_dir;
    private String hom_result_file;

    public HOMParameters(String log_config_file, String country, int year, int year_for_contract, String season, String private_key_file, String project_id, String regionCode, String cropCycleCode, String env_client_id, String env_client_secret, String manual_plan_excel_path, String hom_day_one, String hom_user, int hom_tabu_size, int hom_max_iter, int hom_picker_cap, String hom_region, int hom_max_days, String hom_method, String clientIdEngine, String clientSecretEngine, String awsBucketName, String plantNumber, String env_hom_db_host, String env_hom_db_port, String env_hom_db_user, String env_hom_db_pwd, String hom_db_name, String work_dir, String hom_result_file) {
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
        this.manual_plan_excel_path = manual_plan_excel_path;
        this.hom_day_one = hom_day_one;
        this.hom_user = hom_user;
        this.hom_tabu_size = hom_tabu_size;
        this.hom_max_iter = hom_max_iter;
        this.hom_picker_cap = hom_picker_cap;
        this.hom_region = hom_region;
        this.hom_max_days = hom_max_days;
        this.hom_method = hom_method;
        this.clientIdEngine = clientIdEngine;
        this.clientSecretEngine = clientSecretEngine;
        this.awsBucketName = awsBucketName;
        this.plantNumber = plantNumber;
        this.env_hom_db_host = env_hom_db_host;
        this.env_hom_db_port = env_hom_db_port;
        this.env_hom_db_user = env_hom_db_user;
        this.env_hom_db_pwd = env_hom_db_pwd;
        this.hom_db_name = hom_db_name;
        this.work_dir = work_dir;
        this.hom_result_file = hom_result_file;
    }

    public String getLog_config_file() {
        return log_config_file;
    }

    public void setLog_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear_for_contract() {
        return year_for_contract;
    }

    public void setYear_for_contract(int year_for_contract) {
        this.year_for_contract = year_for_contract;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPrivate_key_file() {
        return private_key_file;
    }

    public void setPrivate_key_file(String private_key_file) {
        this.private_key_file = private_key_file;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCropCycleCode() {
        return cropCycleCode;
    }

    public void setCropCycleCode(String cropCycleCode) {
        this.cropCycleCode = cropCycleCode;
    }

    public String getEnv_client_id() {
        return env_client_id;
    }

    public void setEnv_client_id(String env_client_id) {
        this.env_client_id = env_client_id;
    }

    public String getEnv_client_secret() {
        return env_client_secret;
    }

    public void setEnv_client_secret(String env_client_secret) {
        this.env_client_secret = env_client_secret;
    }

    public String getManual_plan_excel_path() {
        return manual_plan_excel_path;
    }

    public void setManual_plan_excel_path(String manual_plan_excel_path) {
        this.manual_plan_excel_path = manual_plan_excel_path;
    }

    public String getHom_day_one() {
        return hom_day_one;
    }

    public void setHom_day_one(String hom_day_one) {
        this.hom_day_one = hom_day_one;
    }

    public String getHom_user() {
        return hom_user;
    }

    public void setHom_user(String hom_user) {
        this.hom_user = hom_user;
    }

    public int getHom_tabu_size() {
        return hom_tabu_size;
    }

    public void setHom_tabu_size(int hom_tabu_size) {
        this.hom_tabu_size = hom_tabu_size;
    }

    public int getHom_max_iter() {
        return hom_max_iter;
    }

    public void setHom_max_iter(int hom_max_iter) {
        this.hom_max_iter = hom_max_iter;
    }

    public int getHom_picker_cap() {
        return hom_picker_cap;
    }

    public void setHom_picker_cap(int hom_picker_cap) {
        this.hom_picker_cap = hom_picker_cap;
    }

    public String getHom_region() {
        return hom_region;
    }

    public void setHom_region(String hom_region) {
        this.hom_region = hom_region;
    }

    public int getHom_max_days() {
        return hom_max_days;
    }

    public void setHom_max_days(int hom_max_days) {
        this.hom_max_days = hom_max_days;
    }

    public String getHom_method() {
        return hom_method;
    }

    public void setHom_method(String hom_method) {
        this.hom_method = hom_method;
    }

    public String getClientIdEngine() {
        return clientIdEngine;
    }

    public void setClientIdEngine(String clientIdEngine) {
        this.clientIdEngine = clientIdEngine;
    }

    public String getClientSecretEngine() {
        return clientSecretEngine;
    }

    public void setClientSecretEngine(String clientSecretEngine) {
        this.clientSecretEngine = clientSecretEngine;
    }

    public String getAwsBucketName() {
        return awsBucketName;
    }

    public void setAwsBucketName(String awsBucketName) {
        this.awsBucketName = awsBucketName;
    }

    public String getPlantNumber() {
        return plantNumber;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public String getEnv_hom_db_host() {
        return env_hom_db_host;
    }

    public void setEnv_hom_db_host(String env_hom_db_host) {
        this.env_hom_db_host = env_hom_db_host;
    }

    public String getEnv_hom_db_port() {
        return env_hom_db_port;
    }

    public void setEnv_hom_db_port(String env_hom_db_port) {
        this.env_hom_db_port = env_hom_db_port;
    }

    public String getEnv_hom_db_user() {
        return env_hom_db_user;
    }

    public void setEnv_hom_db_user(String env_hom_db_user) {
        this.env_hom_db_user = env_hom_db_user;
    }

    public String getEnv_hom_db_pwd() {
        return env_hom_db_pwd;
    }

    public void setEnv_hom_db_pwd(String env_hom_db_pwd) {
        this.env_hom_db_pwd = env_hom_db_pwd;
    }

    public String getHom_db_name() {
        return hom_db_name;
    }

    public void setHom_db_name(String hom_db_name) {
        this.hom_db_name = hom_db_name;
    }

    public String getWork_dir() {
        return work_dir;
    }

    public void setWork_dir(String work_dir) {
        this.work_dir = work_dir;
    }

    public String getHom_result_file() {
        return hom_result_file;
    }

    public void setHom_result_file(String hom_result_file) {
        this.hom_result_file = hom_result_file;
    }

    @Override
    public String toString() {
        return "HOMParameters{" +
                "log_config_file='" + log_config_file + '\'' +
                ", country='" + country + '\'' +
                ", year=" + year +
                ", year_for_contract=" + year_for_contract +
                ", season='" + season + '\'' +
                ", private_key_file='" + private_key_file + '\'' +
                ", project_id='" + project_id + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", cropCycleCode='" + cropCycleCode + '\'' +
                ", env_client_id='" + env_client_id + '\'' +
                ", env_client_secret='" + env_client_secret + '\'' +
                ", manual_plan_excel_path='" + manual_plan_excel_path + '\'' +
                ", hom_day_one='" + hom_day_one + '\'' +
                ", hom_user='" + hom_user + '\'' +
                ", hom_tabu_size=" + hom_tabu_size +
                ", hom_max_iter=" + hom_max_iter +
                ", hom_picker_cap=" + hom_picker_cap +
                ", hom_region='" + hom_region + '\'' +
                ", hom_max_days=" + hom_max_days +
                ", hom_method='" + hom_method + '\'' +
                ", clientIdEngine='" + clientIdEngine + '\'' +
                ", clientSecretEngine='" + clientSecretEngine + '\'' +
                ", awsBucketName='" + awsBucketName + '\'' +
                ", plantNumber='" + plantNumber + '\'' +
                ", env_hom_db_host='" + env_hom_db_host + '\'' +
                ", env_hom_db_port='" + env_hom_db_port + '\'' +
                ", env_hom_db_user='" + env_hom_db_user + '\'' +
                ", env_hom_db_pwd='" + env_hom_db_pwd + '\'' +
                ", hom_db_name='" + hom_db_name + '\'' +
                ", work_dir='" + work_dir + '\'' +
                ", hom_result_file='" + hom_result_file + '\'' +
                '}';
    }
}