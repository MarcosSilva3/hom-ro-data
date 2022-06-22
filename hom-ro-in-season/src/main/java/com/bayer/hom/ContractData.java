package com.bayer.hom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.LogbackLock;

public class ContractData {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);
    private String plantNumber;
    private String fieldNumber;
    private int year;
    private String token;
    private Contract contract;
    private String log_config_file;

    public ContractData() {
    }

    public ContractData(final String plantNumber, final String fieldNumber, final int year, final String token,
            final String log_config_file) throws IOException, ParseException, JoranException {
        this.plantNumber = plantNumber;
        this.fieldNumber = fieldNumber;
        this.year = year;
        this.token = token;
        this.log_config_file = log_config_file;

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        ch.qos.logback.classic.joran.JoranConfigurator configurator = new ch.qos.logback.classic.joran.JoranConfigurator();
        InputStream configStream = org.apache.commons.io.FileUtils.openInputStream(new File(log_config_file));
        configurator.setContext(loggerContext);
        configurator.doConfigure(configStream); // loads logback file
        configStream.close();

        getContractData();
    }

    public void getContractData() throws IOException, ParseException {
        final String url = "https://product360.ag/production-contracts/v2?plantNumber=" + this.plantNumber
                + "&fieldNumber=" + this.fieldNumber + "&year=" + Integer.toString(this.year);

        slf4jLogger.debug("{}_{} => url: {}", this.plantNumber, this.fieldNumber, url);

        final HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + token);
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpResponse response = client.execute(get);
        final int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode == 200) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            final Object obj = new JSONParser().parse(result.toString());
            final JSONObject jsonObject = (JSONObject) obj;
            final JSONArray features = (JSONArray) jsonObject.get("contracts");
            final Iterator i = features.iterator();
            Map<String, Boolean> hFieldOK = new HashMap<>();
            while (i.hasNext()) {
                final JSONObject contract = (JSONObject) i.next();
                final JSONObject contractHeader = (JSONObject) contract.get("contractHeader");
                final JSONArray contractLineItemsArray = (JSONArray) contract.get("contractLineItems");
                if (contractLineItemsArray.isEmpty()) {
                    if (!hFieldOK.containsKey(this.plantNumber + "_" + this.fieldNumber)) {
                        slf4jLogger.error("{}_{} without contract data", this.plantNumber, this.fieldNumber);
                    }
                    continue;
                }
                final JSONObject contractLineItems = (JSONObject) contractLineItemsArray.get(0);
                final JSONObject growingArea = (JSONObject) contractLineItems.get("growingArea");
                final JSONObject yield = (JSONObject) contractLineItems.get("yield");

                final String contractNumber = (String) contract.get("contractNumber");
                final String growerName = (String) contractHeader.get("growerName");
                final String acronym = (String) contractLineItems.get("acronym");
                final String lastHarvestReceiptDate = (String) contractLineItems.get("lastHarvestReceiptDate");
                final String contractLineDeleteFlag = (String) contractLineItems.get("contractLineDeleteFlag");

                // Formula provided by Dustin
                // femaleFertileArea + femaleSterileArea - discardFertileNonpayArea -
                // discardFertilePayArea - discardSterileNonpayArea - discardSterilePayArea
                final double femaleFertileArea = ((Number) growingArea.get("femaleFertileArea")).doubleValue();
                final double femaleSterileArea = ((Number) growingArea.get("femaleSterileArea")).doubleValue();
                final double discardFertileNonpayArea = ((Number) growingArea.get("discardFertileNonpayArea"))
                        .doubleValue();
                final double discardFertilePayArea = ((Number) growingArea.get("discardFertilePayArea")).doubleValue();
                final double discardSterileNonpayArea = ((Number) growingArea.get("discardSterileNonpayArea"))
                        .doubleValue();
                final double discardSterilePayArea = ((Number) growingArea.get("discardSterilePayArea")).doubleValue();
                final double harvestedFemaleArea = femaleFertileArea + femaleSterileArea - discardFertileNonpayArea
                        - discardFertilePayArea - discardSterileNonpayArea - discardSterilePayArea;

                double moisturePercentage = 0.0;
                if (yield.get("moisturePercentage") != null) {
                    moisturePercentage = ((Number) yield.get("moisturePercentage")).doubleValue();
                }

                this.contract = new Contract(contractNumber, plantNumber, Integer.toString(year), fieldNumber,
                        growerName, acronym, lastHarvestReceiptDate, contractLineDeleteFlag, harvestedFemaleArea,
                        moisturePercentage);
                slf4jLogger.debug("{}_{} contract: {}", this.plantNumber, this.fieldNumber, this.contract);
                hFieldOK.put(this.plantNumber + "_" + this.fieldNumber, true);
            }

        } else {
            slf4jLogger.error("ContractData.java Error: " + response.getStatusLine());
            slf4jLogger.error(response.getEntity().getContent().toString());
        }
    }

    public ContractData(String plantNumber, String fieldNumber, int year, String token, Contract contract,
            String log_config_file) {
        this.plantNumber = plantNumber;
        this.fieldNumber = fieldNumber;
        this.year = year;
        this.token = token;
        this.contract = contract;
        this.log_config_file = log_config_file;
    }

    public String getPlantNumber() {
        return this.plantNumber;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public String getFieldNumber() {
        return this.fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getLog_config_file() {
        return this.log_config_file;
    }

    public void setLog_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
    }

    public ContractData plantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
        return this;
    }

    public ContractData fieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
        return this;
    }

    public ContractData year(int year) {
        this.year = year;
        return this;
    }

    public ContractData token(String token) {
        this.token = token;
        return this;
    }

    public ContractData contract(Contract contract) {
        this.contract = contract;
        return this;
    }

    public ContractData log_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ContractData)) {
            return false;
        }
        ContractData contractData = (ContractData) o;
        return Objects.equals(plantNumber, contractData.plantNumber)
                && Objects.equals(fieldNumber, contractData.fieldNumber) && year == contractData.year
                && Objects.equals(token, contractData.token) && Objects.equals(contract, contractData.contract)
                && Objects.equals(log_config_file, contractData.log_config_file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plantNumber, fieldNumber, year, token, contract, log_config_file);
    }

    @Override
    public String toString() {
        return "{" + " plantNumber='" + getPlantNumber() + "'" + ", fieldNumber='" + getFieldNumber() + "'" + ", year='"
                + getYear() + "'" + ", token='" + getToken() + "'" + ", contract='" + getContract() + "'"
                + ", log_config_file='" + getLog_config_file() + "'" + "}";
    }

}