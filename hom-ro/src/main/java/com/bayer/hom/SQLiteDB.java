package com.bayer.hom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class SQLiteDB {
    private String db_filename;
    private Connection conn;

    public SQLiteDB() {
    }

    public SQLiteDB(String db_filename) throws SQLException {
        this.db_filename = db_filename;
        final String url = "jdbc:sqlite:" + db_filename;
        this.conn = DriverManager.getConnection(url);
    }

    public Map<String, HOMResult> getHOMResult() throws SQLException {
        final String query = "SELECT field, picker, variety, harv_date, total_area, area_harv, total_tonrw, tonrw_harv, lat, lon, grower, moisture, tracking_number FROM result_all_fields";
        Map<String, HOMResult> hResult = new HashMap<>();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            String field = resultSet.getString("field");
            String picker = resultSet.getString("picker");
            String variety = resultSet.getString("variety");
            String harv_date = resultSet.getString("harv_date");
            double total_area = resultSet.getDouble("total_area");
            double area_harv = resultSet.getDouble("area_harv");
            double total_tonrw = resultSet.getDouble("total_tonrw");
            double tonrw_harv = resultSet.getDouble("tonrw_harv");
            double lat = resultSet.getDouble("lat");
            double lon = resultSet.getDouble("lon");
            String grower = resultSet.getString("grower");
            double moisture = resultSet.getDouble("total_area");
            String tracking_number = resultSet.getString("tracking_number");
            HOMResult r = new HOMResult(field, picker, variety, harv_date, total_area, area_harv, total_tonrw,
                    tonrw_harv, lat, lon, grower, moisture, tracking_number);
            hResult.put(field, r);
        }
        stmt.close();
        resultSet.close();
        return hResult;
    }

    public Table<String, Integer, HOMResult> getHOMResultTable() throws SQLException {
        final String query = "SELECT field, picker, variety, harv_date, total_area, area_harv, total_tonrw, tonrw_harv, lat, lon, grower, moisture, tracking_number FROM result_all_fields";
        Table<String, Integer, HOMResult> hResult = HashBasedTable.create();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            String field = resultSet.getString("field");
            int part = Integer.parseInt(field.substring(Math.max(field.length() - 2, 0)));
            String picker = resultSet.getString("picker");
            String variety = resultSet.getString("variety");
            String harv_date = resultSet.getString("harv_date");
            double total_area = resultSet.getDouble("total_area");
            double area_harv = resultSet.getDouble("area_harv");
            double total_tonrw = resultSet.getDouble("total_tonrw");
            double tonrw_harv = resultSet.getDouble("tonrw_harv");
            double lat = resultSet.getDouble("lat");
            double lon = resultSet.getDouble("lon");
            String grower = resultSet.getString("grower");
            double moisture = resultSet.getDouble("moisture");
            String tracking_number = resultSet.getString("tracking_number");
            HOMResult r = new HOMResult(field, picker, variety, harv_date, total_area, area_harv, total_tonrw,
                    tonrw_harv, lat, lon, grower, moisture, tracking_number);
            hResult.put(tracking_number, part, r);
        }
        stmt.close();
        resultSet.close();
        return hResult;
    }

    public Map<String, GSMData> getGSMData(String country) throws SQLException {
        final String query = "select site_key, year, season, tracking_number, pfo_name, entityid, variety, mst, mst_date, drydown_rate, min_mst_harvest_date, max_mst_harvest_date from gsm where country='"
                + country + "'";
        Map<String, GSMData> hResult = new HashMap<>();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            String site_key = resultSet.getString("site_key");
            int year = resultSet.getInt("year");
            String season = resultSet.getString("season");
            String tracking_number = resultSet.getString("tracking_number");
            String pfo_name = resultSet.getString("pfo_name");
            String entityid = resultSet.getString("entityid");
            String variety = resultSet.getString("variety");
            double mst = resultSet.getDouble("mst");
            String mst_date = resultSet.getString("mst_date");

            if (mst_date.equalsIgnoreCase("undef")) {
                mst_date = "";
            }

            double drydown_rate = resultSet.getDouble("drydown_rate");
            String min_mst_harvest_date = resultSet.getString("min_mst_harvest_date");
            String max_mst_harvest_date = resultSet.getString("max_mst_harvest_date");

            GSMData g = new GSMData(country, site_key, year, season, tracking_number, pfo_name, entityid, variety, mst,
                    mst_date, drydown_rate, min_mst_harvest_date, max_mst_harvest_date);

            hResult.put(tracking_number, g);
        }
        stmt.close();
        resultSet.close();
        return hResult;
    }

    public Map<String, HybridData> getHybridData() throws SQLException {
        final String query = "select hybrid, highest_harv_moisture, lowest_harv_moisture from optimal_moistures";
        Map<String, HybridData> hHybridData = new HashMap<>();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            String hybrid = resultSet.getString("hybrid");
            double highest_harv_moisture = resultSet.getDouble("highest_harv_moisture");
            double lowest_harv_moisture = resultSet.getDouble("lowest_harv_moisture");

            HybridData hd = new HybridData(hybrid, highest_harv_moisture, lowest_harv_moisture);
            hHybridData.put(hybrid, hd);
        }
        stmt.close();
        resultSet.close();
        return hHybridData;
    }

    public String getDb_filename() {
        return this.db_filename;
    }

    public void setDb_filename(String db_filename) {
        this.db_filename = db_filename;
    }

    public Connection getConn() {
        return this.conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public SQLiteDB db_filename(String db_filename) {
        this.db_filename = db_filename;
        return this;
    }

    public SQLiteDB conn(Connection conn) {
        this.conn = conn;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SQLiteDB)) {
            return false;
        }
        SQLiteDB sQLiteDB = (SQLiteDB) o;
        return Objects.equals(db_filename, sQLiteDB.db_filename) && Objects.equals(conn, sQLiteDB.conn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(db_filename, conn);
    }

    @Override
    public String toString() {
        return "{" + " db_filename='" + getDb_filename() + "'" + ", conn='" + getConn() + "'" + "}";
    }

}