package com.bayer.hom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PFOData {
    List<String> sites;
    Map<String, FieldPFO> hFieldsPFO;
    private String token;
    private int year;
    private String season;

    public PFOData() {
        this.sites = new ArrayList<>();
        this.hFieldsPFO = new HashMap<>();
    }

    public PFOData(final List<String> sites, final String token, final int year, final String season)
            throws ClientProtocolException, IOException, ParseException {
        this.sites = new ArrayList<>();
        this.hFieldsPFO = new HashMap<>();
        this.sites = sites;
        this.token = token;
        this.year = year;
        this.season = season;
        getFieldData();
    }

    public void getFieldData() throws ClientProtocolException, IOException, ParseException {
        String url = "https://geoserver-core-api.location360.ag/geoserver/ows?service=WFS&version=2.0.0&request=GetFeature&typeName=velocity-pfo:production_fields&outputFormat=application/json&cql_filter=";
        final StringBuilder tmp_str = new StringBuilder();
        tmp_str.append("plant in (");
        String prefix = "";
        for (final String s : sites) {
            tmp_str.append(prefix);
            prefix = ",";
            tmp_str.append("'").append(s).append("'");
        }
        // tmp_str.append(") and year=").append(Integer.toString(year)).append(" and
        // deleted='false' and crop='Corn'");
        tmp_str.append(") and year=").append(Integer.toString(year)).append(" and crop='Corn'").append(" and season='")
                .append(season).append("'");// .append(" and deleted=false");
        url += URLEncoder.encode(tmp_str.toString(), "UTF-8");
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
            final JSONArray features = (JSONArray) jsonObject.get("features");
            final Iterator i = features.iterator();
            while (i.hasNext()) {
                JSONObject feature = (JSONObject) i.next();
                JSONObject prop = (JSONObject) feature.get("properties");
                long id = (long) prop.get("id");
                String feature_id = (String) prop.get("feature_id");
                String group_id = (String) prop.get("group_id");
                String featuretype = (String) prop.get("featuretype");
                String deleteddate = (String) prop.get("deleteddate");
                Boolean deleted = (Boolean) prop.get("deleted");
                String deletedreason = (String) prop.get("deletedreason");
                double areainacres = ((Number) prop.get("areainacres")).doubleValue();
                double areainhectares = ((Number) prop.get("areainhectares")).doubleValue();
                double areainsqfeet = ((Number) prop.get("areainsqfeet")).doubleValue();
                double areainsqmeters = ((Number) prop.get("areainsqmeters")).doubleValue();
                String comments = (String) prop.get("comments");
                String contractnumber = (String) prop.get("contractnumber");
                String countryisocode = (String) prop.get("countryisocode");
                String crop = (String) prop.get("crop");
                String entityid = (String) prop.get("entityid");
                String field = (String) prop.get("field");
                String fieldname = (String) prop.get("fieldname");
                String name = (String) prop.get("name");
                String origin = (String) prop.get("origin");
                String plant = (String) prop.get("plant");
                String plantname = (String) prop.get("plantname");
                String tags = (String) prop.get("tags");
                String status = (String) prop.get("status");
                String season = (String) prop.get("season");
                String monorg_answer = (String) prop.get("monorg_answer");
                String monorg_text = (String) prop.get("monorg_text");
                String usergroupnames = (String) prop.get("usergroupnames");
                String plant_p08 = (String) prop.get("plant_p08");
                String plant_pbc = (String) prop.get("plant_pbc");
                String sap_plant_id = (String) prop.get("sap_plant_id");

                field = field.replaceAll("^0+", "");
                feature_id = feature_id.replaceAll("\\}", "").replaceAll("\\{", "");
                if (comments != null && !comments.isEmpty()) {
                    comments = comments.replaceAll("\\n", "");
                }

                FieldPFO field_pfo = new FieldPFO(id, feature_id, group_id, featuretype, deleteddate, deleted,
                        deletedreason, areainacres, areainhectares, areainsqfeet, areainsqmeters, comments,
                        contractnumber, countryisocode, crop, entityid, field, fieldname, name, origin, plant,
                        plantname, tags, status, season, year, monorg_answer, monorg_text, usergroupnames, plant_p08,
                        plant_pbc, sap_plant_id);

                hFieldsPFO.put(feature_id, field_pfo);
            }
        } else {
            System.out.println("Error: " + response.getStatusLine());
            System.out.println(response.getEntity().getContent());
        }
    }

    public PFOData(List<String> sites, Map<String, FieldPFO> hFieldsPFO, String token, int year, String season) {
        this.sites = sites;
        this.hFieldsPFO = hFieldsPFO;
        this.token = token;
        this.year = year;
        this.season = season;
    }

    public List<String> getSites() {
        return this.sites;
    }

    public void setSites(List<String> sites) {
        this.sites = sites;
    }

    public Map<String, FieldPFO> getHFieldsPFO() {
        return this.hFieldsPFO;
    }

    public void setHFieldsPFO(Map<String, FieldPFO> hFieldsPFO) {
        this.hFieldsPFO = hFieldsPFO;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public PFOData sites(List<String> sites) {
        this.sites = sites;
        return this;
    }

    public PFOData hFieldsPFO(Map<String, FieldPFO> hFieldsPFO) {
        this.hFieldsPFO = hFieldsPFO;
        return this;
    }

    public PFOData token(String token) {
        this.token = token;
        return this;
    }

    public PFOData year(int year) {
        this.year = year;
        return this;
    }

    public PFOData season(String season) {
        this.season = season;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PFOData)) {
            return false;
        }
        PFOData pFOData = (PFOData) o;
        return Objects.equals(sites, pFOData.sites) && Objects.equals(hFieldsPFO, pFOData.hFieldsPFO)
                && Objects.equals(token, pFOData.token) && year == pFOData.year
                && Objects.equals(season, pFOData.season);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sites, hFieldsPFO, token, year, season);
    }

    @Override
    public String toString() {
        return "{" + " sites='" + getSites() + "'" + ", hFieldsPFO='" + getHFieldsPFO() + "'" + ", token='" + getToken()
                + "'" + ", year='" + getYear() + "'" + ", season='" + getSeason() + "'" + "}";
    }

}