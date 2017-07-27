package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import models.Department;
import models.Doctor;
import models.Hospital;

public class ExcelUtil {

	public static void writeDataToExcel(HashMap<String, ArrayList<Hospital>> map) {
		HSSFWorkbook wb = new HSSFWorkbook();
		for (String p: map.keySet()) {
			Sheet sheet = wb.createSheet(p);
			for (Hospital h: map.get(p)) {
				writeSingleHospitalToExcel(sheet, h);
			}
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("data_by_hospital.xls");
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void writeSingleHospitalToExcel(Sheet sheet1, Hospital h) {
		Row header = sheet1.createRow(0);
		Cell cell0 = header.createCell(0);
		cell0.setCellValue("����");
		Cell cell1 = header.createCell(1);
		cell1.setCellValue("���У�����");
		Cell cell2 = header.createCell(2);
		cell2.setCellValue("ҽԺ����");
		Cell cell3 = header.createCell(3);
		cell3.setCellValue("ҽԺ����");
		Cell cell4 = header.createCell(4);
		cell4.setCellValue("ҽԺ�ȼ�");
		Cell cell5 = header.createCell(5);
		cell5.setCellValue("ҽԺ����");
		Cell cell6 = header.createCell(6);
		cell6.setCellValue("��ַ�绰");
		Cell cell7 = header.createCell(7);
		cell7.setCellValue("�����б� һ��");
		Cell cell8 = header.createCell(8);
		cell8.setCellValue("�����б� ����");
		Cell cell9 = header.createCell(9);
		cell9.setCellValue("���ҽ���");
		Cell cell10 = header.createCell(10);
		cell10.setCellValue("ҽ��");
		Cell cell11 = header.createCell(11);
		cell11.setCellValue("ְ��");
		Cell cell12 = header.createCell(12);
		cell12.setCellValue("�ó�");
		Cell cell13 = header.createCell(13);
		cell13.setCellValue("ִҵ����");
		Cell cell14 = header.createCell(14);
		cell14.setCellValue("������վ");

		writeExcelRow(sheet1, h);
	}

	private static void writeExcelRow(Sheet sheet1, Hospital h) {
		int rowNum = 1;

		for (String topDept : h.getMap().keySet()) {
			for (Department dept : h.getMap().get(topDept).keySet()) {
				for (Doctor d : h.getMap().get(topDept).get(dept)) {
					Row row = sheet1.createRow(rowNum);
					Cell c0 = row.createCell(0);
					c0.setCellValue(h.getProvince());
					Cell c1 = row.createCell(1);
					c1.setCellValue(h.getCity());
					Cell c2 = row.createCell(2);
					c2.setCellValue(h.getCategory());
					Cell c3 = row.createCell(3);
					c3.setCellValue(h.getName());
					Cell c4 = row.createCell(4);
					c4.setCellValue(h.getLevel());
					Cell c5 = row.createCell(5);
					c5.setCellValue(h.getDescription());
					Cell c6 = row.createCell(6);
					c6.setCellValue(h.getAddress() + "\n" + h.getTelephone());
					Cell c7 = row.createCell(7);
					c7.setCellValue(topDept);
					Cell c8 = row.createCell(8);
					c8.setCellValue(dept.getName());
					Cell c9 = row.createCell(9);
					c9.setCellValue(dept.getDescription());
					Cell c10 = row.createCell(10);
					c10.setCellValue(d.getName());
					Cell c11 = row.createCell(11);
					c11.setCellValue(d.getTitle());
					Cell c12 = row.createCell(12);
					c12.setCellValue(d.getAdvantage());
					Cell c13 = row.createCell(13);
					c13.setCellValue(d.getJobExp());
					Cell c14 = row.createCell(14);
					c14.setCellValue(d.getWebsite());

					rowNum++;
				}
			}
		}
	}
	
	public static void writeDataToExcel(HashMap<String, ArrayList<Hospital>> map) {
		HSSFWorkbook wb = new HSSFWorkbook();
		for (String p: map.keySet()) {
			Sheet sheet = wb.createSheet(p);
			for (Hospital h: map.get(p)) {
				writeSingleHospitalToExcel(sheet, h);
			}
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("data_by_disease.xls");
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
