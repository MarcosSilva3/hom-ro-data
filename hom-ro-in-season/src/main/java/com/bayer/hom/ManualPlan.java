package com.bayer.hom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.spi.LogbackLock;

public class ManualPlan {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);

    private String excel_file_path;
    private Map<String, FieldManualPlan> hFields;

    public ManualPlan(String excel_file_path) throws IOException {
        this.excel_file_path = excel_file_path;
        this.hFields = new HashMap<>();
    }

    public void readManualPlanExcel() throws IOException {
        FileInputStream fis = new FileInputStream(new File(excel_file_path));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheet("Corn Harvest Plan 2022");
        int nrows = sheet.getLastRowNum();

        // Skip header starting at row 1.
        for (int i = 14; i <= nrows; i++) {
            Row row = sheet.getRow(i);
            String seed_plant = getStringValue(row.getCell(5));
            if (seed_plant.equalsIgnoreCase("Sinesti")) {
                String region = getStringValue(row.getCell(3));
                String dh_qualifyed = getStringValue(row.getCell(6));
                String grower = getStringValue(row.getCell(7));
                String tracking_number = getStringValue(row.getCell(8));
                String hybrid = getStringValue(row.getCell(9));
                String suspect = getStringValue(row.getCell(10));
                String suspect_comments = getStringValue(row.getCell(11));
                String husking_difficulty = getStringValue(row.getCell(12));
                String female = getStringValue(row.getCell(13));
                String male = getStringValue(row.getCell(14));
                double active_ha = getNumericValue(row.getCell(23));
                double yield_ton_ha = getNumericValue(row.getCell(43));
                String picker_group = getStringValue(row.getCell(42));
                String harvest_date = getDateValue(row.getCell(46));
                String harvest_window_start = getDateValue(row.getCell(47));
                String harvest_window_end = getDateValue(row.getCell(48));
                FieldManualPlan f = new FieldManualPlan(region, seed_plant, dh_qualifyed, grower, tracking_number,
                        hybrid, suspect, suspect_comments, husking_difficulty, female, male, active_ha, yield_ton_ha,
                        picker_group, harvest_date, harvest_window_start, harvest_window_end);

                if (hFields.containsKey(tracking_number)) {
                    slf4jLogger.warn("[Manual Plan Excel] duplicated entry for field {}", tracking_number);
                } else {
                    hFields.put(tracking_number, f);
                }
                slf4jLogger.debug("[Manual Plan Excel] Field {}", f);
            }
        }
    }

    public void readManualPlanDB(final HOMParameters hom_parameters) {
        Connection connection;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Map<String, String> env = System.getenv();
            final String host = env.get(hom_parameters.getEnv_hom_db_host());
            final String port = env.get(hom_parameters.getEnv_hom_db_port());
            final String dbname = hom_parameters.getHom_db_name();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                    + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
            final String dbuser = env.get(hom_parameters.getEnv_hom_db_user());
            final String dbpwd = env.get(hom_parameters.getEnv_hom_db_pwd());
            connection = DriverManager.getConnection(url, dbuser, dbpwd);

            slf4jLogger.debug("[MySQL Site] url: {}", url);
            if (connection.isValid(10000)) {
                slf4jLogger.debug("[MySQL Site] Connected!");
            }
            String query = "SELECT * FROM FieldManualPlan WHERE seed_plant='Sinesti'";

            // create the java statement
            Statement st = connection.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                String region = rs.getString("region");
                String seed_plant = rs.getString("seed_plant");
                String dh_qualifyed = rs.getString("dh_qualifyed");
                String grower = rs.getString("grower");
                String tracking_number = rs.getString("tracking_number");
                String hybrid = rs.getString("hybrid");
                String suspect = rs.getString("suspect");
                String suspect_comments = rs.getString("suspect_comments");
                String husking_difficulty = rs.getString("husking_difficulty");
                String female = rs.getString("female");
                String male = rs.getString("male");
                double active_ha = rs.getDouble("active_ha");
                double yield_ton_ha = rs.getDouble("yield_ton_ha");
                String picker_group = rs.getString("picker_group");
                String harvest_date = rs.getDate("harvest_date").toString();
                String harvest_window_start = rs.getDate("harvest_window_start").toString();
                String harvest_window_end = rs.getDate("harvest_window_end").toString();
                FieldManualPlan f = new FieldManualPlan(region, seed_plant, dh_qualifyed, grower, tracking_number,
                        hybrid, suspect, suspect_comments, husking_difficulty, female, male, active_ha, yield_ton_ha,
                        picker_group, harvest_date, harvest_window_start, harvest_window_end);

                if (hFields.containsKey(tracking_number)) {
                    slf4jLogger.warn("[Manual Plan DB] duplicated entry for field {}", tracking_number);
                } else {
                    hFields.put(tracking_number, f);
                }
                slf4jLogger.debug("[Manual Plan DB] Field {}", f);
            }
            st.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println(exception);
        }
    }

    private String getDateValue(Cell cell) {
        String date = null;

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            // This cell is empty
        } else {
            if (DateUtil.isCellDateFormatted(cell)) {
                date = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
            }
        }
        return date;
    }

    /**
     *
     * @param cell
     * @return String cell value.
     */
    private String getStringValue(Cell cell) {
        String str = "";

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            // This cell is empty
        } else {
            if (cell.getCellType() == CellType.STRING) {
                str = cell.getStringCellValue();
            }

            if (cell.getCellType() == CellType.NUMERIC) {
                str = String.valueOf((int) cell.getNumericCellValue());
            }
        }
        return str;
    }

    /**
     *
     * @param cell
     * @return double cell value.
     */
    private double getNumericValue(Cell cell) {
        double val = 0;

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            // This cell is empty
        } else {
            if (cell.getCellType() == CellType.STRING) {
                String s = cell.getStringCellValue();
                s = s.replaceAll(",", ".");
                val = Double.parseDouble(s);
            }

            if (cell.getCellType() == CellType.NUMERIC) {
                val = cell.getNumericCellValue();
            }
        }
        return val;
    }

    /**
     *
     * @param cell
     * @return time : int value in minutes.
     * @throws ParseException
     */
    private int getTimeValue(Cell cell) throws ParseException {
        int time = 0;

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            // This cell is empty
        } else {
            if (cell.getCellType() == CellType.STRING) {
                String s = cell.getStringCellValue();
                if (s.contains(":")) {
                    String[] t = s.split(":");
                    int hour = 60 * Integer.parseInt(t[0]);
                    int min = Integer.parseInt(t[1]);
                    time = hour + min;
                }
            }
            if (cell.getCellType() == CellType.NUMERIC) {
                double val = 60 * 24 * cell.getNumericCellValue();
                time = (int) Math.ceil(val);
            }
        }
        return time;
    }

    /**
     * @return String return the excel_file_path
     */
    public String getExcel_file_path() {
        return excel_file_path;
    }

    /**
     * @param excel_file_path the excel_file_path to set
     */
    public void setExcel_file_path(String excel_file_path) {
        this.excel_file_path = excel_file_path;
    }

    /**
     * @return Map<String, FieldManualPlan> return the hFields
     */
    public Map<String, FieldManualPlan> getHFields() {
        return hFields;
    }

    /**
     * @param hFields the hFields to set
     */
    public void setHFields(Map<String, FieldManualPlan> hFields) {
        this.hFields = hFields;
    }

}