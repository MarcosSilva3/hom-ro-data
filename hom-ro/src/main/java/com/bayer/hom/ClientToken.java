package com.bayer.hom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientToken {
    private String client_id;
    private String client_secret;

    public ClientToken() {
    }

    public ClientToken(String client_id, String client_secret) {
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public String getToken() throws ClientProtocolException, IOException, ParseException {
        String token = null;
        String url = "https://login.microsoftonline.com/fcb2b37b-5da0-466b-9b83-0014b67a7c78/oauth2/v2.0/token";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        urlParameters.add(new BasicNameValuePair("client_id", client_id));
        urlParameters.add(new BasicNameValuePair("client_secret", client_secret));
        urlParameters.add(new BasicNameValuePair("scope", client_id + "/.default"));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode == 200) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Object obj = new JSONParser().parse(result.toString());
            JSONObject jo = (JSONObject) obj;
            token = (String) jo.get("access_token");
            System.out.println("Token " + token + " successfully created!");
        } else {
            System.out.println("Error: " + response.getStatusLine());
            System.out.println(response.getEntity().getContent());
            System.exit(1);
        }
        return token;
    }

    public String getClient_id() {
        return this.client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return this.client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public ClientToken client_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public ClientToken client_secret(String client_secret) {
        this.client_secret = client_secret;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ClientToken)) {
            return false;
        }
        ClientToken getToken = (ClientToken) o;
        return Objects.equals(client_id, getToken.client_id) && Objects.equals(client_secret, getToken.client_secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id, client_secret);
    }

    @Override
    public String toString() {
        return "{" + " client_id='" + getClient_id() + "'" + ", client_secret='" + getClient_secret() + "'" + "}";
    }

}