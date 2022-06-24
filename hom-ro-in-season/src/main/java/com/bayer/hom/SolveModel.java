package com.bayer.hom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.LogbackLock;

public class SolveModel {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);
    private String json_input_data;
    private String token;
    private String client_id;
    private String client_secret;
    private String log_config_file;
    HOMParameters hom_parameters;
    private int jobid;
    private String status;
    private String timestamp;
    private String user;

    public SolveModel() {
    }

    public SolveModel(String json_input_data, HOMParameters hom_parameters)
            throws IOException, JoranException, ParseException {
        this.json_input_data = json_input_data;
        this.hom_parameters = hom_parameters;
        setAdditionalData();
    }

    private void setAdditionalData() throws IOException, JoranException, ParseException {
        final Map<String, String> env = System.getenv();
        this.client_id = env.get(hom_parameters.getClientIdEngine());
        this.client_secret = env.get(hom_parameters.getClientSecretEngine());
        this.log_config_file = hom_parameters.getLog_config_file();
        this.token = new ClientToken(client_id, client_secret, log_config_file).getToken();
    }

    public void submitJob() throws IOException, JoranException, ParseException {
        String url = "http://hom-queue.velocity-np.ag/api/v1/solve";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", "Bearer " + token);
        post.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(json_input_data);
        post.setEntity(entity);

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
            this.jobid = ((Number) jo.get("jobid")).intValue();
            this.status = (String) jo.get("status");
            this.timestamp = (String) jo.get("timestamp");
            this.user = (String) jo.get("user");
            slf4jLogger.debug("[HOM Solve Model] Job submitted for execution. Response: {}", result.toString());
        } else {
            slf4jLogger.error("[HOM Solve Model] Error in submitting job for solution: {} {}", response.getStatusLine(),
                    response.getEntity().getContent());
        }
    }

    public String getJobStatus() throws ClientProtocolException, IOException, ParseException {
        String url = "https://hom-queue.velocity-np.ag/api/v1/state";
        String payload = "{\r\n    \"jobid\": " + jobid + "\r\n}";
        // HttpGet get = new HttpGet(url);
        // get.setHeader("Authorization", "Bearer " + token);
        // get.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(payload);
        HttpUriRequest request = RequestBuilder.get(url).setEntity(new StringEntity(payload))
                .setHeader("Authorization", "Bearer " + token).setHeader("Content-Type", "application/json").build();

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
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
            this.status = (String) jo.get("state");
            slf4jLogger.debug("[HOM Solve Model] Job ID: {}, Status: {}", this.jobid, result.toString());

        } else {
            slf4jLogger.error("[HOM Solve Model] Error in getting job status: {} {}", response.getStatusLine(),
                    response.getEntity().getContent());
        }
        return this.status;
    }

    public String getJson_input_data() {
        return this.json_input_data;
    }

    public void setJson_input_data(String json_input_data) {
        this.json_input_data = json_input_data;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getLog_config_file() {
        return this.log_config_file;
    }

    public void setLog_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
    }

    public HOMParameters getHom_parameters() {
        return this.hom_parameters;
    }

    public void setHom_parameters(HOMParameters hom_parameters) {
        this.hom_parameters = hom_parameters;
    }

    public int getJobid() {
        return this.jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public SolveModel json_input_data(String json_input_data) {
        this.json_input_data = json_input_data;
        return this;
    }

    public SolveModel token(String token) {
        this.token = token;
        return this;
    }

    public SolveModel client_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public SolveModel client_secret(String client_secret) {
        this.client_secret = client_secret;
        return this;
    }

    public SolveModel log_config_file(String log_config_file) {
        this.log_config_file = log_config_file;
        return this;
    }

    public SolveModel hom_parameters(HOMParameters hom_parameters) {
        this.hom_parameters = hom_parameters;
        return this;
    }

    public SolveModel jobid(int jobid) {
        this.jobid = jobid;
        return this;
    }

    public SolveModel status(String status) {
        this.status = status;
        return this;
    }

    public SolveModel timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public SolveModel user(String user) {
        this.user = user;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SolveModel)) {
            return false;
        }
        SolveModel solveModel = (SolveModel) o;
        return Objects.equals(json_input_data, solveModel.json_input_data) && Objects.equals(token, solveModel.token)
                && Objects.equals(client_id, solveModel.client_id)
                && Objects.equals(client_secret, solveModel.client_secret)
                && Objects.equals(log_config_file, solveModel.log_config_file)
                && Objects.equals(hom_parameters, solveModel.hom_parameters) && jobid == solveModel.jobid
                && Objects.equals(status, solveModel.status) && Objects.equals(timestamp, solveModel.timestamp)
                && Objects.equals(user, solveModel.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(json_input_data, token, client_id, client_secret, log_config_file, hom_parameters, jobid,
                status, timestamp, user);
    }

    @Override
    public String toString() {
        return "{" + " json_input_data='" + getJson_input_data() + "'" + ", token='" + getToken() + "'"
                + ", client_id='" + getClient_id() + "'" + ", client_secret='" + getClient_secret() + "'"
                + ", log_config_file='" + getLog_config_file() + "'" + ", hom_parameters='" + getHom_parameters() + "'"
                + ", jobid='" + getJobid() + "'" + ", status='" + getStatus() + "'" + ", timestamp='" + getTimestamp()
                + "'" + ", user='" + getUser() + "'" + "}";
    }

}