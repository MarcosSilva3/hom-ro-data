package com.bayer.hom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.parser.ParseException;

public class ScoutWkt {
    private String entity_id;
    private String question_code;
    private String token;
    private String wkt;

    public ScoutWkt() {
    }

    public ScoutWkt(String entity_id, String question_code, String token)
            throws ClientProtocolException, IOException, ParseException {
        this.entity_id = entity_id;
        this.question_code = question_code;
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

            if (result.toString().indexOf("pfo_geom_wkt") > -1) {
                JsonNode jn = new ObjectMapper().readTree(result.toString());
                this.wkt = jn.get("features").get(0).get("properties").get("pfo_geom_wkt").textValue();
            }

        } else {
            System.out.println("Error: " + response.getStatusLine());
            System.out.println(response.getEntity().getContent());
        }
    }

    public String getEntity_id() {
        return this.entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getQuestion_code() {
        return this.question_code;
    }

    public void setQuestion_code(String question_code) {
        this.question_code = question_code;
    }

    public String getWkt() {
        return this.wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public ScoutWkt entity_id(String entity_id) {
        this.entity_id = entity_id;
        return this;
    }

    public ScoutWkt question_code(String question_code) {
        this.question_code = question_code;
        return this;
    }

    public ScoutWkt wkt(String wkt) {
        this.wkt = wkt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ScoutWkt)) {
            return false;
        }
        ScoutWkt scoutWkt = (ScoutWkt) o;
        return Objects.equals(entity_id, scoutWkt.entity_id) && Objects.equals(question_code, scoutWkt.question_code)
                && Objects.equals(wkt, scoutWkt.wkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity_id, question_code, wkt);
    }

    @Override
    public String toString() {
        return "{" + " entity_id='" + getEntity_id() + "'" + ", question_code='" + getQuestion_code() + "'" + ", wkt='"
                + getWkt() + "'" + "}";
    }

}