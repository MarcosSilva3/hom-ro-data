package com.bayer.hom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.spi.LogbackLock;

public class ProductCharacterizationManualFile {
	private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LogbackLock.class);

	private String excel_file_path;
	Map<String, ProductCharacterization> hProducts;

	public ProductCharacterizationManualFile(String excel_file_path) throws IOException {
		super();
		this.excel_file_path = excel_file_path;
		hProducts = new HashMap<>();
		readProductDataFromExcel();
	}

	/**
	 * Check if Excel row is empty
	 * 
	 * @param row :: in excel sheet
	 * @return boolean true or false
	 */
	private boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
				return false;
			}
		}
		return true;
	}

	public void readProductDataFromExcel() throws IOException {
		ZipSecureFile.setMinInflateRatio(0);
		final FileInputStream fis = new FileInputStream(excel_file_path);
		final XSSFWorkbook wb = new XSSFWorkbook(fis);
		final XSSFSheet sheet = wb.getSheet("RO Harvest ideal moisture");
		final int nrows = sheet.getLastRowNum();

		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);

		// Skip header starting at row 1.
		for (int i = 1; i <= nrows; i++) {
			final Row row = sheet.getRow(i);
			if (!checkIfRowIsEmpty(row)) {
				objFormulaEvaluator.evaluate(row.getCell(0));
				final String name = objDefaultFormat.formatCellValue(row.getCell(0), objFormulaEvaluator);
				slf4jLogger.debug("[Product Characterization - Reading Excel file] Hybrid name {}", name);

				objFormulaEvaluator.evaluate(row.getCell(6));
				final String highestHarvestMoisture = objDefaultFormat.formatCellValue(row.getCell(6),
						objFormulaEvaluator);
				slf4jLogger.debug("[Product Characterization - Reading Excel file] highestHarvestMoisture {}",
						highestHarvestMoisture);

				objFormulaEvaluator.evaluate(row.getCell(7));
				final String lowestHarvestMoisture = objDefaultFormat.formatCellValue(row.getCell(7),
						objFormulaEvaluator);
				slf4jLogger.debug("[Product Characterization - Reading Excel file] lowestHarvestMoisture {}",
						lowestHarvestMoisture);

				objFormulaEvaluator.evaluate(row.getCell(8));
				final String huskingDifficulty = objDefaultFormat.formatCellValue(row.getCell(8), objFormulaEvaluator);
				slf4jLogger.debug("[Product Characterization - Reading Excel file] huskingDifficulty {}",
						huskingDifficulty);

				objFormulaEvaluator.evaluate(row.getCell(3));
				int lowest_rec = Integer
						.parseInt(objDefaultFormat.formatCellValue(row.getCell(3), objFormulaEvaluator));
				slf4jLogger.debug("[Product Characterization - Reading Excel file] lowest_rec {}",
						String.format("%d", lowest_rec));

				objFormulaEvaluator.evaluate(row.getCell(2));
				int highest_rec = Integer
						.parseInt(objDefaultFormat.formatCellValue(row.getCell(2), objFormulaEvaluator));
				slf4jLogger.debug("[Product Characterization - Reading Excel file] highest_rec {}",
						String.format("%d", highest_rec));

				// Specific for Romania
				int lowest_rec_ro = 0;
				int highest_rec_ro = 0;

				// check if cell is empty
				if (!isCellEmpty(row.getCell(5))) {
					objFormulaEvaluator.evaluate(row.getCell(3));
					lowest_rec_ro = Integer
							.parseInt(objDefaultFormat.formatCellValue(row.getCell(5), objFormulaEvaluator));
					slf4jLogger.debug("[Product Characterization - Reading Excel file] lowest_rec_ro {}",
							String.format("%d", lowest_rec_ro));
				}

				if (!isCellEmpty(row.getCell(4))) {
					objFormulaEvaluator.evaluate(row.getCell(4));
					highest_rec_ro = Integer
							.parseInt(objDefaultFormat.formatCellValue(row.getCell(4), objFormulaEvaluator));
					slf4jLogger.debug("[Product Characterization - Reading Excel file] highest_rec_ro {}",
							String.format("%d", highest_rec_ro));
				}
				
				if(lowest_rec_ro > 0) {
					lowest_rec = lowest_rec_ro;
				}
				
				if(highest_rec_ro > 0) {
					highest_rec = highest_rec_ro;
				}

				ProductCharacterization p = new ProductCharacterization(name, highestHarvestMoisture,
						lowestHarvestMoisture, huskingDifficulty, lowest_rec, highest_rec);

				hProducts.put(name, p);
				slf4jLogger.debug("[Product Characterization - Reading Excel file] Product {}", p);
			}
		}
		wb.close();
	}

	/**
	 * Check if cell is empty.
	 * 
	 * @param cell to be checked
	 * @return true or false
	 */
	private Boolean isCellEmpty(Cell cell) {
		Boolean isEmpty = true;
		if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * @return the excel_file_path
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
	 * @return the hProducts
	 */
	public Map<String, ProductCharacterization> gethProducts() {
		return hProducts;
	}

	/**
	 * @param hProducts the hProducts to set
	 */
	public void sethProducts(Map<String, ProductCharacterization> hProducts) {
		this.hProducts = hProducts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(excel_file_path, hProducts);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCharacterizationManualFile other = (ProductCharacterizationManualFile) obj;
		return Objects.equals(excel_file_path, other.excel_file_path) && Objects.equals(hProducts, other.hProducts);
	}

	@Override
	public String toString() {
		return "ProductCharacterizationManualFile [excel_file_path=" + excel_file_path + ", hProducts=" + hProducts
				+ "]";
	}

}
