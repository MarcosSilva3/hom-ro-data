package com.bayer.hom;

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

public class ScoutWkt {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);
    private String entity_id;
    private String token;
    private String wkt;
    private double lat;
    private double lon;

    public ScoutWkt() {
    }

    public ScoutWkt(String entity_id, String token) throws ClientProtocolException, IOException, ParseException {
        this.entity_id = entity_id;
        this.token = token;
        getWkData();
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
     * @return String return the entity_id
     */
    public String getEntity_id() {
        return entity_id;
    }

    /**
     * @param entity_id the entity_id to set
     */
    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return String return the wkt
     */
    public String getWkt() {
        return wkt;
    }

    /**
     * @param wkt the wkt to set
     */
    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    /**
     * @return double return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * @return double return the lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * @param lon the lon to set
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

}
