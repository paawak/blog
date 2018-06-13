package com.swayam.demo.web.xls.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class XlsReader {

	public void read(InputStream inputStream) throws IOException {

		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inputStream));
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				String value;
				if (cell.getCellTypeEnum() == CellType.STRING) {
					value = cell.getStringCellValue();
				} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					value = String.valueOf(cell.getNumericCellValue());
				} else {
					throw new UnsupportedOperationException("The type " + cell.getCellTypeEnum() + " is not supported");
				}
				System.out.println(value);
			}
		}
		wb.close();
		inputStream.close();

	}

}
