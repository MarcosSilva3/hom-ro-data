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

public class ProductCharacterizationData {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);
    private String token;
    private String log_config_file;
    private String regionCode;
    private String cropCycleCode;
    private Map<String, ProductCharacterization> hProducts;

    public ProductCharacterizationData() {
    }

    public ProductCharacterizationData(final String token, final String log_config_file, final String regionCode,
            final String cropCycleCode) throws IOException, ParseException, JoranException {
        this.token = token;
        this.log_config_file = log_config_file;
        this.regionCode = regionCode;
        this.cropCycleCode = cropCycleCode;
        this.hProducts = new HashMap<>();

        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        final ch.qos.logback.classic.joran.JoranConfigurator configurator = new ch.qos.logback.classic.joran.JoranConfigurator();
        final InputStream configStream = org.apache.commons.io.FileUtils.openInputStream(new File(log_config_file));
        configurator.setContext(loggerContext);
        configurator.doConfigure(configStream); // loads logback file
        configStream.close();

        getProductData();
    }

    private void getProductData() throws UnsupportedOperationException, IOException, ParseException {
        final String url = "https://api01.agro.services/product-characterization/dryer-calls?regionCode="
                + this.regionCode + "&cropCycleCode=" + this.cropCycleCode + "&nametype=manf";
        slf4jLogger.debug("url: {}", url);
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
            final JSONArray products = (JSONArray) obj;
            final Iterator i = products.iterator();
            final Map<String, Boolean> hProductOK = new HashMap<>();
            while (i.hasNext()) {
                final JSONObject p = (JSONObject) i.next();
                final String name = (String) p.get("name");

                if (name == null || name.isEmpty()) {
                    continue;
                }

                final JSONObject characteristics = (JSONObject) p.get("characteristics");
                String highestHarvestMoisture = "NOT CLASSIFIED";
                String lowestHarvestMoisture = "NOT CLASSIFIED";
                String huskingDifficulty = "NOT CLASSIFIED";
                int lowest_rec = 35;
                int highest_rec = 38;

                if (characteristics.get("HIGHEST_HARVEST_MOISTURE") != null) {
                    highestHarvestMoisture = (String) characteristics.get("HIGHEST_HARVEST_MOISTURE");
                }

                if (characteristics.get("LOWEST_HARVEST_MOISTURE") != null) {
                    lowestHarvestMoisture = (String) characteristics.get("LOWEST_HARVEST_MOISTURE");
                }

                if (characteristics.get("HUSKING_DIFFICULTY") != null) {
                    huskingDifficulty = (String) characteristics.get("HUSKING_DIFFICULTY");
                }

                if (characteristics.get("LOWEST_REC") != null
                        && isNumeric((String) characteristics.get("LOWEST_REC"))) {
                    lowest_rec = Integer.parseInt((String) characteristics.get("LOWEST_REC"));
                }

                if (characteristics.get("HIGHEST_REC") != null
                        && isNumeric((String) characteristics.get("HIGHEST_REC"))) {
                    highest_rec = Integer.parseInt((String) characteristics.get("HIGHEST_REC"));
                }

                ProductCharacterization product = new ProductCharacterization(name, highestHarvestMoisture,
                        lowestHarvestMoisture, huskingDifficulty, lowest_rec, highest_rec);
                hProducts.put(name, product);

                slf4jLogger.debug("[Product Characterization] product: {}", product);
                hProductOK.put(name, true);
            }

        } else {
            slf4jLogger.error("[Product Characterization] Error: {} {}", response.getStatusLine(),
                    response.getEntity().getContent().toString());
        }
    }

    /**
     * Check if string looks like a number.
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(final String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLog_config_file() {
        return this.log_config_file;
    }

    public void setLog_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
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

    public Map<String, ProductCharacterization> getHProducts() {
        return this.hProducts;
    }

    public void setHProducts(Map<String, ProductCharacterization> hProducts) {
        this.hProducts = hProducts;
    }

    public ProductCharacterizationData token(String token) {
        this.token = token;
        return this;
    }

    public ProductCharacterizationData log_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
        return this;
    }

    public ProductCharacterizationData regionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public ProductCharacterizationData cropCycleCode(String cropCycleCode) {
        this.cropCycleCode = cropCycleCode;
        return this;
    }

    public ProductCharacterizationData hProducts(Map<String, ProductCharacterization> hProducts) {
        this.hProducts = hProducts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductCharacterizationData)) {
            return false;
        }
        ProductCharacterizationData productCharacterizationData = (ProductCharacterizationData) o;
        return Objects.equals(token, productCharacterizationData.token)
                && Objects.equals(log_config_file, productCharacterizationData.log_config_file)
                && Objects.equals(regionCode, productCharacterizationData.regionCode)
                && Objects.equals(cropCycleCode, productCharacterizationData.cropCycleCode)
                && Objects.equals(hProducts, productCharacterizationData.hProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, log_config_file, regionCode, cropCycleCode, hProducts);
    }

    @Override
    public String toString() {
        return "{" + " token='" + getToken() + "'" + ", log_config_file='" + getLog_config_file() + "'"
                + ", regionCode='" + getRegionCode() + "'" + ", cropCycleCode='" + getCropCycleCode() + "'"
                + ", hProducts='" + getHProducts() + "'" + "}";
    }

}