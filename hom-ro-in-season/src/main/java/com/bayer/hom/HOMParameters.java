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
	private Boolean overwrite_db_data_manual_plan;
	private Boolean overwriteSiteCapacityInDB;
	private Boolean readManualPlanExcel;
	private Boolean useCachedScoutData;
	private Boolean saveResultsInCSW;
	private String productDataExcelPath;
	private Boolean useProductDataFromExcel;
	private Boolean useSiteCapacityFromDB;
	private int qttyPickers;
	private int qttyCombines;

	public HOMParameters() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HOMParameters(String log_config_file, String country, int year, int year_for_contract, String season,
			String private_key_file, String project_id, String regionCode, String cropCycleCode, String env_client_id,
			String env_client_secret, String manual_plan_excel_path, String hom_day_one, String hom_user,
			int hom_tabu_size, int hom_max_iter, int hom_picker_cap, String hom_region, int hom_max_days,
			String hom_method, String clientIdEngine, String clientSecretEngine, String awsBucketName,
			String plantNumber, String env_hom_db_host, String env_hom_db_port, String env_hom_db_user,
			String env_hom_db_pwd, String hom_db_name, String work_dir, String hom_result_file,
			Boolean overwrite_db_data_manual_plan, Boolean overwriteSiteCapacityInDB, Boolean readManualPlanExcel,
			Boolean useCachedScoutData, Boolean saveResultsInCSW, String productDataExcelPath,
			Boolean useProductDataFromExcel, Boolean useSiteCapacityFromDB, int qttyPickers, int qttyCombines) {
		super();
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
		this.overwrite_db_data_manual_plan = overwrite_db_data_manual_plan;
		this.overwriteSiteCapacityInDB = overwriteSiteCapacityInDB;
		this.readManualPlanExcel = readManualPlanExcel;
		this.useCachedScoutData = useCachedScoutData;
		this.saveResultsInCSW = saveResultsInCSW;
		this.productDataExcelPath = productDataExcelPath;
		this.useProductDataFromExcel = useProductDataFromExcel;
		this.useSiteCapacityFromDB = useSiteCapacityFromDB;
		this.qttyPickers = qttyPickers;
		this.qttyCombines = qttyCombines;
	}

	/**
	 * @return the log_config_file
	 */
	public String getLog_config_file() {
		return log_config_file;
	}

	/**
	 * @param log_config_file the log_config_file to set
	 */
	public void setLog_config_file(String log_config_file) {
		this.log_config_file = log_config_file;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the year_for_contract
	 */
	public int getYear_for_contract() {
		return year_for_contract;
	}

	/**
	 * @param year_for_contract the year_for_contract to set
	 */
	public void setYear_for_contract(int year_for_contract) {
		this.year_for_contract = year_for_contract;
	}

	/**
	 * @return the season
	 */
	public String getSeason() {
		return season;
	}

	/**
	 * @param season the season to set
	 */
	public void setSeason(String season) {
		this.season = season;
	}

	/**
	 * @return the private_key_file
	 */
	public String getPrivate_key_file() {
		return private_key_file;
	}

	/**
	 * @param private_key_file the private_key_file to set
	 */
	public void setPrivate_key_file(String private_key_file) {
		this.private_key_file = private_key_file;
	}

	/**
	 * @return the project_id
	 */
	public String getProject_id() {
		return project_id;
	}

	/**
	 * @param project_id the project_id to set
	 */
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the cropCycleCode
	 */
	public String getCropCycleCode() {
		return cropCycleCode;
	}

	/**
	 * @param cropCycleCode the cropCycleCode to set
	 */
	public void setCropCycleCode(String cropCycleCode) {
		this.cropCycleCode = cropCycleCode;
	}

	/**
	 * @return the env_client_id
	 */
	public String getEnv_client_id() {
		return env_client_id;
	}

	/**
	 * @param env_client_id the env_client_id to set
	 */
	public void setEnv_client_id(String env_client_id) {
		this.env_client_id = env_client_id;
	}

	/**
	 * @return the env_client_secret
	 */
	public String getEnv_client_secret() {
		return env_client_secret;
	}

	/**
	 * @param env_client_secret the env_client_secret to set
	 */
	public void setEnv_client_secret(String env_client_secret) {
		this.env_client_secret = env_client_secret;
	}

	/**
	 * @return the manual_plan_excel_path
	 */
	public String getManual_plan_excel_path() {
		return manual_plan_excel_path;
	}

	/**
	 * @param manual_plan_excel_path the manual_plan_excel_path to set
	 */
	public void setManual_plan_excel_path(String manual_plan_excel_path) {
		this.manual_plan_excel_path = manual_plan_excel_path;
	}

	/**
	 * @return the hom_day_one
	 */
	public String getHom_day_one() {
		return hom_day_one;
	}

	/**
	 * @param hom_day_one the hom_day_one to set
	 */
	public void setHom_day_one(String hom_day_one) {
		this.hom_day_one = hom_day_one;
	}

	/**
	 * @return the hom_user
	 */
	public String getHom_user() {
		return hom_user;
	}

	/**
	 * @param hom_user the hom_user to set
	 */
	public void setHom_user(String hom_user) {
		this.hom_user = hom_user;
	}

	/**
	 * @return the hom_tabu_size
	 */
	public int getHom_tabu_size() {
		return hom_tabu_size;
	}

	/**
	 * @param hom_tabu_size the hom_tabu_size to set
	 */
	public void setHom_tabu_size(int hom_tabu_size) {
		this.hom_tabu_size = hom_tabu_size;
	}

	/**
	 * @return the hom_max_iter
	 */
	public int getHom_max_iter() {
		return hom_max_iter;
	}

	/**
	 * @param hom_max_iter the hom_max_iter to set
	 */
	public void setHom_max_iter(int hom_max_iter) {
		this.hom_max_iter = hom_max_iter;
	}

	/**
	 * @return the hom_picker_cap
	 */
	public int getHom_picker_cap() {
		return hom_picker_cap;
	}

	/**
	 * @param hom_picker_cap the hom_picker_cap to set
	 */
	public void setHom_picker_cap(int hom_picker_cap) {
		this.hom_picker_cap = hom_picker_cap;
	}

	/**
	 * @return the hom_region
	 */
	public String getHom_region() {
		return hom_region;
	}

	/**
	 * @param hom_region the hom_region to set
	 */
	public void setHom_region(String hom_region) {
		this.hom_region = hom_region;
	}

	/**
	 * @return the hom_max_days
	 */
	public int getHom_max_days() {
		return hom_max_days;
	}

	/**
	 * @param hom_max_days the hom_max_days to set
	 */
	public void setHom_max_days(int hom_max_days) {
		this.hom_max_days = hom_max_days;
	}

	/**
	 * @return the hom_method
	 */
	public String getHom_method() {
		return hom_method;
	}

	/**
	 * @param hom_method the hom_method to set
	 */
	public void setHom_method(String hom_method) {
		this.hom_method = hom_method;
	}

	/**
	 * @return the clientIdEngine
	 */
	public String getClientIdEngine() {
		return clientIdEngine;
	}

	/**
	 * @param clientIdEngine the clientIdEngine to set
	 */
	public void setClientIdEngine(String clientIdEngine) {
		this.clientIdEngine = clientIdEngine;
	}

	/**
	 * @return the clientSecretEngine
	 */
	public String getClientSecretEngine() {
		return clientSecretEngine;
	}

	/**
	 * @param clientSecretEngine the clientSecretEngine to set
	 */
	public void setClientSecretEngine(String clientSecretEngine) {
		this.clientSecretEngine = clientSecretEngine;
	}

	/**
	 * @return the awsBucketName
	 */
	public String getAwsBucketName() {
		return awsBucketName;
	}

	/**
	 * @param awsBucketName the awsBucketName to set
	 */
	public void setAwsBucketName(String awsBucketName) {
		this.awsBucketName = awsBucketName;
	}

	/**
	 * @return the plantNumber
	 */
	public String getPlantNumber() {
		return plantNumber;
	}

	/**
	 * @param plantNumber the plantNumber to set
	 */
	public void setPlantNumber(String plantNumber) {
		this.plantNumber = plantNumber;
	}

	/**
	 * @return the env_hom_db_host
	 */
	public String getEnv_hom_db_host() {
		return env_hom_db_host;
	}

	/**
	 * @param env_hom_db_host the env_hom_db_host to set
	 */
	public void setEnv_hom_db_host(String env_hom_db_host) {
		this.env_hom_db_host = env_hom_db_host;
	}

	/**
	 * @return the env_hom_db_port
	 */
	public String getEnv_hom_db_port() {
		return env_hom_db_port;
	}

	/**
	 * @param env_hom_db_port the env_hom_db_port to set
	 */
	public void setEnv_hom_db_port(String env_hom_db_port) {
		this.env_hom_db_port = env_hom_db_port;
	}

	/**
	 * @return the env_hom_db_user
	 */
	public String getEnv_hom_db_user() {
		return env_hom_db_user;
	}

	/**
	 * @param env_hom_db_user the env_hom_db_user to set
	 */
	public void setEnv_hom_db_user(String env_hom_db_user) {
		this.env_hom_db_user = env_hom_db_user;
	}

	/**
	 * @return the env_hom_db_pwd
	 */
	public String getEnv_hom_db_pwd() {
		return env_hom_db_pwd;
	}

	/**
	 * @param env_hom_db_pwd the env_hom_db_pwd to set
	 */
	public void setEnv_hom_db_pwd(String env_hom_db_pwd) {
		this.env_hom_db_pwd = env_hom_db_pwd;
	}

	/**
	 * @return the hom_db_name
	 */
	public String getHom_db_name() {
		return hom_db_name;
	}

	/**
	 * @param hom_db_name the hom_db_name to set
	 */
	public void setHom_db_name(String hom_db_name) {
		this.hom_db_name = hom_db_name;
	}

	/**
	 * @return the work_dir
	 */
	public String getWork_dir() {
		return work_dir;
	}

	/**
	 * @param work_dir the work_dir to set
	 */
	public void setWork_dir(String work_dir) {
		this.work_dir = work_dir;
	}

	/**
	 * @return the hom_result_file
	 */
	public String getHom_result_file() {
		return hom_result_file;
	}

	/**
	 * @param hom_result_file the hom_result_file to set
	 */
	public void setHom_result_file(String hom_result_file) {
		this.hom_result_file = hom_result_file;
	}

	/**
	 * @return the overwrite_db_data_manual_plan
	 */
	public Boolean getOverwrite_db_data_manual_plan() {
		return overwrite_db_data_manual_plan;
	}

	/**
	 * @param overwrite_db_data_manual_plan the overwrite_db_data_manual_plan to set
	 */
	public void setOverwrite_db_data_manual_plan(Boolean overwrite_db_data_manual_plan) {
		this.overwrite_db_data_manual_plan = overwrite_db_data_manual_plan;
	}

	/**
	 * @return the overwriteSiteCapacityInDB
	 */
	public Boolean getOverwriteSiteCapacityInDB() {
		return overwriteSiteCapacityInDB;
	}

	/**
	 * @param overwriteSiteCapacityInDB the overwriteSiteCapacityInDB to set
	 */
	public void setOverwriteSiteCapacityInDB(Boolean overwriteSiteCapacityInDB) {
		this.overwriteSiteCapacityInDB = overwriteSiteCapacityInDB;
	}

	/**
	 * @return the readManualPlanExcel
	 */
	public Boolean getReadManualPlanExcel() {
		return readManualPlanExcel;
	}

	/**
	 * @param readManualPlanExcel the readManualPlanExcel to set
	 */
	public void setReadManualPlanExcel(Boolean readManualPlanExcel) {
		this.readManualPlanExcel = readManualPlanExcel;
	}

	/**
	 * @return the useCachedScoutData
	 */
	public Boolean getUseCachedScoutData() {
		return useCachedScoutData;
	}

	/**
	 * @param useCachedScoutData the useCachedScoutData to set
	 */
	public void setUseCachedScoutData(Boolean useCachedScoutData) {
		this.useCachedScoutData = useCachedScoutData;
	}

	/**
	 * @return the saveResultsInCSW
	 */
	public Boolean getSaveResultsInCSW() {
		return saveResultsInCSW;
	}

	/**
	 * @param saveResultsInCSW the saveResultsInCSW to set
	 */
	public void setSaveResultsInCSW(Boolean saveResultsInCSW) {
		this.saveResultsInCSW = saveResultsInCSW;
	}

	/**
	 * @return the productDataExcelPath
	 */
	public String getProductDataExcelPath() {
		return productDataExcelPath;
	}

	/**
	 * @param productDataExcelPath the productDataExcelPath to set
	 */
	public void setProductDataExcelPath(String productDataExcelPath) {
		this.productDataExcelPath = productDataExcelPath;
	}

	/**
	 * @return the useProductDataFromExcel
	 */
	public Boolean getUseProductDataFromExcel() {
		return useProductDataFromExcel;
	}

	/**
	 * @param useProductDataFromExcel the useProductDataFromExcel to set
	 */
	public void setUseProductDataFromExcel(Boolean useProductDataFromExcel) {
		this.useProductDataFromExcel = useProductDataFromExcel;
	}

	/**
	 * @return the useSiteCapacityFromDB
	 */
	public Boolean getUseSiteCapacityFromDB() {
		return useSiteCapacityFromDB;
	}

	/**
	 * @param useSiteCapacityFromDB the useSiteCapacityFromDB to set
	 */
	public void setUseSiteCapacityFromDB(Boolean useSiteCapacityFromDB) {
		this.useSiteCapacityFromDB = useSiteCapacityFromDB;
	}

	/**
	 * @return the qttyPickers
	 */
	public int getQttyPickers() {
		return qttyPickers;
	}

	/**
	 * @param qttyPickers the qttyPickers to set
	 */
	public void setQttyPickers(int qttyPickers) {
		this.qttyPickers = qttyPickers;
	}

	/**
	 * @return the qttyCombines
	 */
	public int getQttyCombines() {
		return qttyCombines;
	}

	/**
	 * @param qttyCombines the qttyCombines to set
	 */
	public void setQttyCombines(int qttyCombines) {
		this.qttyCombines = qttyCombines;
	}

	@Override
	public int hashCode() {
		return Objects.hash(awsBucketName, clientIdEngine, clientSecretEngine, country, cropCycleCode, env_client_id,
				env_client_secret, env_hom_db_host, env_hom_db_port, env_hom_db_pwd, env_hom_db_user, hom_day_one,
				hom_db_name, hom_max_days, hom_max_iter, hom_method, hom_picker_cap, hom_region, hom_result_file,
				hom_tabu_size, hom_user, log_config_file, manual_plan_excel_path, overwriteSiteCapacityInDB,
				overwrite_db_data_manual_plan, plantNumber, private_key_file, productDataExcelPath, project_id,
				qttyCombines, qttyPickers, readManualPlanExcel, regionCode, saveResultsInCSW, season,
				useCachedScoutData, useProductDataFromExcel, useSiteCapacityFromDB, work_dir, year, year_for_contract);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HOMParameters other = (HOMParameters) obj;
		return Objects.equals(awsBucketName, other.awsBucketName)
				&& Objects.equals(clientIdEngine, other.clientIdEngine)
				&& Objects.equals(clientSecretEngine, other.clientSecretEngine)
				&& Objects.equals(country, other.country) && Objects.equals(cropCycleCode, other.cropCycleCode)
				&& Objects.equals(env_client_id, other.env_client_id)
				&& Objects.equals(env_client_secret, other.env_client_secret)
				&& Objects.equals(env_hom_db_host, other.env_hom_db_host)
				&& Objects.equals(env_hom_db_port, other.env_hom_db_port)
				&& Objects.equals(env_hom_db_pwd, other.env_hom_db_pwd)
				&& Objects.equals(env_hom_db_user, other.env_hom_db_user)
				&& Objects.equals(hom_day_one, other.hom_day_one) && Objects.equals(hom_db_name, other.hom_db_name)
				&& hom_max_days == other.hom_max_days && hom_max_iter == other.hom_max_iter
				&& Objects.equals(hom_method, other.hom_method) && hom_picker_cap == other.hom_picker_cap
				&& Objects.equals(hom_region, other.hom_region)
				&& Objects.equals(hom_result_file, other.hom_result_file) && hom_tabu_size == other.hom_tabu_size
				&& Objects.equals(hom_user, other.hom_user) && Objects.equals(log_config_file, other.log_config_file)
				&& Objects.equals(manual_plan_excel_path, other.manual_plan_excel_path)
				&& Objects.equals(overwriteSiteCapacityInDB, other.overwriteSiteCapacityInDB)
				&& Objects.equals(overwrite_db_data_manual_plan, other.overwrite_db_data_manual_plan)
				&& Objects.equals(plantNumber, other.plantNumber)
				&& Objects.equals(private_key_file, other.private_key_file)
				&& Objects.equals(productDataExcelPath, other.productDataExcelPath)
				&& Objects.equals(project_id, other.project_id) && qttyCombines == other.qttyCombines
				&& qttyPickers == other.qttyPickers && Objects.equals(readManualPlanExcel, other.readManualPlanExcel)
				&& Objects.equals(regionCode, other.regionCode)
				&& Objects.equals(saveResultsInCSW, other.saveResultsInCSW) && Objects.equals(season, other.season)
				&& Objects.equals(useCachedScoutData, other.useCachedScoutData)
				&& Objects.equals(useProductDataFromExcel, other.useProductDataFromExcel)
				&& Objects.equals(useSiteCapacityFromDB, other.useSiteCapacityFromDB)
				&& Objects.equals(work_dir, other.work_dir) && year == other.year
				&& year_for_contract == other.year_for_contract;
	}

	@Override
	public String toString() {
		return "HOMParameters [log_config_file=" + log_config_file + ", country=" + country + ", year=" + year
				+ ", year_for_contract=" + year_for_contract + ", season=" + season + ", private_key_file="
				+ private_key_file + ", project_id=" + project_id + ", regionCode=" + regionCode + ", cropCycleCode="
				+ cropCycleCode + ", env_client_id=" + env_client_id + ", env_client_secret=" + env_client_secret
				+ ", manual_plan_excel_path=" + manual_plan_excel_path + ", hom_day_one=" + hom_day_one + ", hom_user="
				+ hom_user + ", hom_tabu_size=" + hom_tabu_size + ", hom_max_iter=" + hom_max_iter + ", hom_picker_cap="
				+ hom_picker_cap + ", hom_region=" + hom_region + ", hom_max_days=" + hom_max_days + ", hom_method="
				+ hom_method + ", clientIdEngine=" + clientIdEngine + ", clientSecretEngine=" + clientSecretEngine
				+ ", awsBucketName=" + awsBucketName + ", plantNumber=" + plantNumber + ", env_hom_db_host="
				+ env_hom_db_host + ", env_hom_db_port=" + env_hom_db_port + ", env_hom_db_user=" + env_hom_db_user
				+ ", env_hom_db_pwd=" + env_hom_db_pwd + ", hom_db_name=" + hom_db_name + ", work_dir=" + work_dir
				+ ", hom_result_file=" + hom_result_file + ", overwrite_db_data_manual_plan="
				+ overwrite_db_data_manual_plan + ", overwriteSiteCapacityInDB=" + overwriteSiteCapacityInDB
				+ ", readManualPlanExcel=" + readManualPlanExcel + ", useCachedScoutData=" + useCachedScoutData
				+ ", saveResultsInCSW=" + saveResultsInCSW + ", productDataExcelPath=" + productDataExcelPath
				+ ", useProductDataFromExcel=" + useProductDataFromExcel + ", useSiteCapacityFromDB="
				+ useSiteCapacityFromDB + ", qttyPickers=" + qttyPickers + ", qttyCombines=" + qttyCombines + "]";
	}

}