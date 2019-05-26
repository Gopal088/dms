package com.dms.entity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dms.db.Database;

public class GenerateExcel {

	public Workbook getSalaryExcel() {
		Workbook wb = new XSSFWorkbook();
		String[] headers = { "Provider ID", "Provider Name", "Primary Mobile", "Aadhar No", "Bank IFSC", "Account No",
				"Salary Amount" };

		Sheet sheet = wb.createSheet("DMS Salary");
		sheet.setDefaultColumnWidth(20);
		String file = "DMS Salaries.xlsx";
		FileOutputStream out;

		// header row
		Row headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(20);
		Cell headerCell;
		for (int i = 0; i < headers.length; i++) {
			headerCell = headerRow.createCell(i);
			headerCell.setCellValue(headers[i]);	
		}

		try {
			Database.loadDrivers();
			Connection conn = Database.getConnection();

			String sql = "CALL processSalary()";
			 CallableStatement stmt = conn.prepareCall(sql); 
	         
			System.out.println(stmt);

			ResultSet rs = stmt.executeQuery();

			Row row;
			int rowCount = 1;
			while (rs.next()) {
				row = sheet.createRow(rowCount++);
				Cell dataCell;
				for (int j = 0; j < headers.length; j++) {
					dataCell = row.createCell(j);
					dataCell.setCellValue(rs.getString(j + 1));
				}
			}
			rs.close();
			Database.closeConnection();
			out = new FileOutputStream(file);
			wb.write(out);
			
			return wb;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wb;
	}

}
