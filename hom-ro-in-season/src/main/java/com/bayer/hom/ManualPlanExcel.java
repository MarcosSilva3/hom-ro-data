package com.bayer.hom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class ManualPlanExcel {
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);

    private String excel_file_path;
    private Map<String, FieldManualPlan> hFields;

    public ManualPlanExcel(String excel_file_path) throws IOException {
        this.excel_file_path = excel_file_path;
        this.hFields = new HashMap<>();
        readManualPlan();
    }

    private void readManualPlan() throws IOException {
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