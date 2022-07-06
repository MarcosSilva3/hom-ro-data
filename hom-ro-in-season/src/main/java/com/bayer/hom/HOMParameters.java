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

    public HOMParameters() {
    }

    public HOMParameters(String log_config_file, String country, int year, int year_for_contract, String season,
            String private_key_file, String project_id, String regionCode, String cropCycleCode, String env_client_id,
            String env_client_secret, String manual_plan_excel_path, String hom_day_one, String hom_user,
            int hom_tabu_size, int hom_max_iter, int hom_picker_cap, String hom_region, int hom_max_days,
            String hom_method, String clientIdEngine, String clientSecretEngine, String awsBucketName,
            String plantNumber, String env_hom_db_host, String env_hom_db_port, String env_hom_db_user,
            String env_hom_db_pwd, String hom_db_name, String work_dir) {
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

    public String getManual_plan_excel_path() {
        return this.manual_plan_excel_path;
    }

    public void setManual_plan_excel_path(String manual_plan_excel_path) {
        this.manual_plan_excel_path = manual_plan_excel_path;
    }

    public String getHom_day_one() {
        return this.hom_day_one;
    }

    public void setHom_day_one(String hom_day_one) {
        this.hom_day_one = hom_day_one;
    }

    public String getHom_user() {
        return this.hom_user;
    }

    public void setHom_user(String hom_user) {
        this.hom_user = hom_user;
    }

    public int getHom_tabu_size() {
        return this.hom_tabu_size;
    }

    public void setHom_tabu_size(int hom_tabu_size) {
        this.hom_tabu_size = hom_tabu_size;
    }

    public int getHom_max_iter() {
        return this.hom_max_iter;
    }

    public void setHom_max_iter(int hom_max_iter) {
        this.hom_max_iter = hom_max_iter;
    }

    public int getHom_picker_cap() {
        return this.hom_picker_cap;
    }

    public void setHom_picker_cap(int hom_picker_cap) {
        this.hom_picker_cap = hom_picker_cap;
    }

    public String getHom_region() {
        return this.hom_region;
    }

    public void setHom_region(String hom_region) {
        this.hom_region = hom_region;
    }

    public int getHom_max_days() {
        return this.hom_max_days;
    }

    public void setHom_max_days(int hom_max_days) {
        this.hom_max_days = hom_max_days;
    }

    public String getHom_method() {
        return this.hom_method;
    }

    public void setHom_method(String hom_method) {
        this.hom_method = hom_method;
    }

    public String getClientIdEngine() {
        return this.clientIdEngine;
    }

    public void setClientIdEngine(String clientIdEngine) {
        this.clientIdEngine = clientIdEngine;
    }

    public String getClientSecretEngine() {
        return this.clientSecretEngine;
    }

    public void setClientSecretEngine(String clientSecretEngine) {
        this.clientSecretEngine = clientSecretEngine;
    }

    public String getAwsBucketName() {
        return this.awsBucketName;
    }

    public void setAwsBucketName(String awsBucketName) {
        this.awsBucketName = awsBucketName;
    }

    public String getPlantNumber() {
        return this.plantNumber;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public String getEnv_hom_db_host() {
        return this.env_hom_db_host;
    }

    public void setEnv_hom_db_host(String env_hom_db_host) {
        this.env_hom_db_host = env_hom_db_host;
    }

    public String getEnv_hom_db_port() {
        return this.env_hom_db_port;
    }

    public void setEnv_hom_db_port(String env_hom_db_port) {
        this.env_hom_db_port = env_hom_db_port;
    }

    public String getEnv_hom_db_user() {
        return this.env_hom_db_user;
    }

    public void setEnv_hom_db_user(String env_hom_db_user) {
        this.env_hom_db_user = env_hom_db_user;
    }

    public String getEnv_hom_db_pwd() {
        return this.env_hom_db_pwd;
    }

    public void setEnv_hom_db_pwd(String env_hom_db_pwd) {
        this.env_hom_db_pwd = env_hom_db_pwd;
    }

    public String getHom_db_name() {
        return this.hom_db_name;
    }

    public void setHom_db_name(String hom_db_name) {
        this.hom_db_name = hom_db_name;
    }

    public String getWork_dir() {
        return this.work_dir;
    }

    public void setWork_dir(String work_dir) {
        this.work_dir = work_dir;
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

    public HOMParameters manual_plan_excel_path(String manual_plan_excel_path) {
        this.manual_plan_excel_path = manual_plan_excel_path;
        return this;
    }

    public HOMParameters hom_day_one(String hom_day_one) {
        this.hom_day_one = hom_day_one;
        return this;
    }

    public HOMParameters hom_user(String hom_user) {
        this.hom_user = hom_user;
        return this;
    }

    public HOMParameters hom_tabu_size(int hom_tabu_size) {
        this.hom_tabu_size = hom_tabu_size;
        return this;
    }

    public HOMParameters hom_max_iter(int hom_max_iter) {
        this.hom_max_iter = hom_max_iter;
        return this;
    }

    public HOMParameters hom_picker_cap(int hom_picker_cap) {
        this.hom_picker_cap = hom_picker_cap;
        return this;
    }

    public HOMParameters hom_region(String hom_region) {
        this.hom_region = hom_region;
        return this;
    }

    public HOMParameters hom_max_days(int hom_max_days) {
        this.hom_max_days = hom_max_days;
        return this;
    }

    public HOMParameters hom_method(String hom_method) {
        this.hom_method = hom_method;
        return this;
    }

    public HOMParameters clientIdEngine(String clientIdEngine) {
        this.clientIdEngine = clientIdEngine;
        return this;
    }

    public HOMParameters clientSecretEngine(String clientSecretEngine) {
        this.clientSecretEngine = clientSecretEngine;
        return this;
    }

    public HOMParameters awsBucketName(String awsBucketName) {
        this.awsBucketName = awsBucketName;
        return this;
    }

    public HOMParameters plantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
        return this;
    }

    public HOMParameters env_hom_db_host(String env_hom_db_host) {
        this.env_hom_db_host = env_hom_db_host;
        return this;
    }

    public HOMParameters env_hom_db_port(String env_hom_db_port) {
        this.env_hom_db_port = env_hom_db_port;
        return this;
    }

    public HOMParameters env_hom_db_user(String env_hom_db_user) {
        this.env_hom_db_user = env_hom_db_user;
        return this;
    }

    public HOMParameters env_hom_db_pwd(String env_hom_db_pwd) {
        this.env_hom_db_pwd = env_hom_db_pwd;
        return this;
    }

    public HOMParameters hom_db_name(String hom_db_name) {
        this.hom_db_name = hom_db_name;
        return this;
    }

    public HOMParameters work_dir(String work_dir) {
        this.work_dir = work_dir;
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
                && Objects.equals(env_client_secret, hOMParameters.env_client_secret)
                && Objects.equals(manual_plan_excel_path, hOMParameters.manual_plan_excel_path)
                && Objects.equals(hom_day_one, hOMParameters.hom_day_one)
                && Objects.equals(hom_user, hOMParameters.hom_user) && hom_tabu_size == hOMParameters.hom_tabu_size
                && hom_max_iter == hOMParameters.hom_max_iter && hom_picker_cap == hOMParameters.hom_picker_cap
                && Objects.equals(hom_region, hOMParameters.hom_region) && hom_max_days == hOMParameters.hom_max_days
                && Objects.equals(hom_method, hOMParameters.hom_method)
                && Objects.equals(clientIdEngine, hOMParameters.clientIdEngine)
                && Objects.equals(clientSecretEngine, hOMParameters.clientSecretEngine)
                && Objects.equals(awsBucketName, hOMParameters.awsBucketName)
                && Objects.equals(plantNumber, hOMParameters.plantNumber)
                && Objects.equals(env_hom_db_host, hOMParameters.env_hom_db_host)
                && Objects.equals(env_hom_db_port, hOMParameters.env_hom_db_port)
                && Objects.equals(env_hom_db_user, hOMParameters.env_hom_db_user)
                && Objects.equals(env_hom_db_pwd, hOMParameters.env_hom_db_pwd)
                && Objects.equals(hom_db_name, hOMParameters.hom_db_name)
                && Objects.equals(work_dir, hOMParameters.work_dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(log_config_file, country, year, year_for_contract, season, private_key_file, project_id,
                regionCode, cropCycleCode, env_client_id, env_client_secret, manual_plan_excel_path, hom_day_one,
                hom_user, hom_tabu_size, hom_max_iter, hom_picker_cap, hom_region, hom_max_days, hom_method,
                clientIdEngine, clientSecretEngine, awsBucketName, plantNumber, env_hom_db_host, env_hom_db_port,
                env_hom_db_user, env_hom_db_pwd, hom_db_name, work_dir);
    }

    @Override
    public String toString() {
        return "{" + " log_config_file='" + getLog_config_file() + "'" + ", country='" + getCountry() + "'" + ", year='"
                + getYear() + "'" + ", year_for_contract='" + getYear_for_contract() + "'" + ", season='" + getSeason()
                + "'" + ", private_key_file='" + getPrivate_key_file() + "'" + ", project_id='" + getProject_id() + "'"
                + ", regionCode='" + getRegionCode() + "'" + ", cropCycleCode='" + getCropCycleCode() + "'"
                + ", env_client_id='" + getEnv_client_id() + "'" + ", env_client_secret='" + getEnv_client_secret()
                + "'" + ", manual_plan_excel_path='" + getManual_plan_excel_path() + "'" + ", hom_day_one='"
                + getHom_day_one() + "'" + ", hom_user='" + getHom_user() + "'" + ", hom_tabu_size='"
                + getHom_tabu_size() + "'" + ", hom_max_iter='" + getHom_max_iter() + "'" + ", hom_picker_cap='"
                + getHom_picker_cap() + "'" + ", hom_region='" + getHom_region() + "'" + ", hom_max_days='"
                + getHom_max_days() + "'" + ", hom_method='" + getHom_method() + "'" + ", clientIdEngine='"
                + getClientIdEngine() + "'" + ", clientSecretEngine='" + getClientSecretEngine() + "'"
                + ", awsBucketName='" + getAwsBucketName() + "'" + ", plantNumber='" + getPlantNumber() + "'"
                + ", env_hom_db_host='" + getEnv_hom_db_host() + "'" + ", env_hom_db_port='" + getEnv_hom_db_port()
                + "'" + ", env_hom_db_user='" + getEnv_hom_db_user() + "'" + ", env_hom_db_pwd='" + getEnv_hom_db_pwd()
                + "'" + ", hom_db_name='" + getHom_db_name() + "'" + ", work_dir='" + getWork_dir() + "'" + "}";
    }

}