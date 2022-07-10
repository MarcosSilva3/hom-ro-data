package com.bayer.hom;

import java.util.Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.spi.LogbackLock;

public class ScoutData {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);
    private String entity_id;
    private String token;
    private String wkt;
    private double lat;
    private double lon;
    private double yield;

    public ScoutData() {
    }

    public ScoutData(String entity_id, String token) throws ClientProtocolException, IOException, ParseException {
        this.entity_id = entity_id;
        this.token = token;
//        getWkData();
        getYieldData();
    }

    public void getWkData() throws ClientProtocolException, IOException, ParseException {
        String url = "https://geoserver-core-api.location360.ag/geoserver/ows?service=WFS&version=2.0.0&request=GetFeature&typeNames=velocity-scout:scout_points&srsname=EPSG:4326&outputFormat=application/json&cql_filter=";
        // String tmp = "entity_id='" + entity_id + "' and question_code='" +
        // question_code
        // + "' and answered_date_time_utc >= '2021-06-01'";
        String tmp = "entity_id='" + entity_id + "' and answered_date_time_utc >= '2021-06-01'";
        url += URLEncoder.encode(tmp, "UTF-8");
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + token);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(get);
        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode == 200) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            slf4jLogger.debug("[Scout Wkt] Start of entity_id: {}", entity_id);
            final Object obj = new JSONParser().parse(result.toString());
            final JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.containsKey("features")) {
                final JSONArray features = (JSONArray) jsonObject.get("features");
                final Iterator i = features.iterator();
                final JSONObject feature = (JSONObject) i.next();
                if (feature.containsKey("geometry") && feature.containsKey("properties")) {
                    final JSONObject geometry = (JSONObject) feature.get("geometry");
                    final JSONObject properties = (JSONObject) feature.get("properties");
                    try {
                        if (geometry.containsKey("coordinates") && (geometry.get("coordinates") != null)) {

                            final JSONArray coordinates = (JSONArray) geometry.get("coordinates");
                            if (coordinates.size() == 2) {
                                this.lat = ((Number) coordinates.get(0)).doubleValue();
                                this.lon = ((Number) coordinates.get(1)).doubleValue();
                                this.wkt = (String) properties.get("pfo_geom_wkt");
                                slf4jLogger.debug("[Scout Wkt] {} => lat: {}, lon: {}, wkt: {}", entity_id, lat, lon,
                                        wkt);
                            }
                        } else {
                            slf4jLogger.error("[Scout Wkt] entity_id {} with error in coordinates", entity_id);
                        }
                    } catch (Exception ex) {
                        slf4jLogger.error("[Scout Wkt] entity_id {} with error in coordinates", entity_id);
                    }
                }
            } else {
                slf4jLogger.error("[Scout Wkt] entity_id {} without features in JSON returned", entity_id);
            }
        } else {
            slf4jLogger.error("[Scout Wkt] request error for entity_id {} => {}, {}", entity_id,
                    response.getStatusLine(), response.getEntity().getContent());
        }
    }

    /**
     * UOM is T/ha of DS then to translate it in RW we can just multiply by 2
     * (Vincent Garat)
     * 
     * @return the estimated yield
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     */
    public void getYieldData() throws ClientProtocolException, IOException, ParseException {
        this.yield = 0.0;
        String url = "https://geoserver-core-api.location360.ag/geoserver/ows?service=WFS&version=2.0.0&request=GetFeature&typeNames=velocity-scout:scout_points&srsname=EPSG:4326&outputFormat=application/json&cql_filter=";
        String tmp = "entity_id='" + entity_id
                + "' and question_code='ESTYIELD' and answered_date_time_utc >= '2021-06-01'";
        url += URLEncoder.encode(tmp, "UTF-8");
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + token);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(get);
        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode == 200) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            final Object obj = new JSONParser().parse(result.toString());
            final JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.containsKey("features")) {
                final JSONArray features = (JSONArray) jsonObject.get("features");
                final Iterator i = features.iterator();
                final JSONObject feature = (JSONObject) i.next();
                if (feature.containsKey("properties")) {
                    final JSONObject properties = (JSONObject) feature.get("properties");
                    try {
                        this.yield = 2.0 * Double.parseDouble((String) properties.get("value"));
                        slf4jLogger.debug("[Scout Yield] Entity_id: {} :: yield: {}", entity_id, this.yield);
                    } catch (Exception ex) {
                        slf4jLogger.error("[Scout Yield] entity_id {} with error in coordinates", entity_id);
                    }
                }
            } else {
                slf4jLogger.error("[Scout Yield] entity_id {} without features in JSON returned", entity_id);
            }
        } else {
            slf4jLogger.error("[Scout Yield] request error for entity_id {} => {}, {}", entity_id,
                    response.getStatusLine(), response.getEntity().getContent());
        }
    }

    public String getEntity_id() {
        return this.entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWkt() {
        return this.wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getYield() {
        return this.yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public ScoutData entity_id(String entity_id) {
        this.entity_id = entity_id;
        return this;
    }

    public ScoutData token(String token) {
        this.token = token;
        return this;
    }

    public ScoutData wkt(String wkt) {
        this.wkt = wkt;
        return this;
    }

    public ScoutData lat(double lat) {
        this.lat = lat;
        return this;
    }

    public ScoutData lon(double lon) {
        this.lon = lon;
        return this;
    }

    public ScoutData yield(double yield) {
        this.yield = yield;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ScoutData)) {
            return false;
        }
        ScoutData scoutData = (ScoutData) o;
        return Objects.equals(entity_id, scoutData.entity_id) && Objects.equals(token, scoutData.token)
                && Objects.equals(wkt, scoutData.wkt) && lat == scoutData.lat && lon == scoutData.lon
                && yield == scoutData.yield;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity_id, token, wkt, lat, lon, yield);
    }

    @Override
    public String toString() {
        return "{" + " entity_id='" + getEntity_id() + "'" + ", token='" + getToken() + "'" + ", wkt='" + getWkt() + "'"
                + ", lat='" + getLat() + "'" + ", lon='" + getLon() + "'" + ", yield='" + getYield() + "'" + "}";
    }

}
