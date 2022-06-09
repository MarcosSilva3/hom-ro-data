package com.bayer.hom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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