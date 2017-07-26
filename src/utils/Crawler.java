package utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import models.Department;
import models.Doctor;

public class Crawler {

	private static final String BASE_URL = "http://www.haodf.com/";

	private static List<Doctor> doctors = new ArrayList<>();
	private static List<Department> departments = new ArrayList<>();
	private static Map<Department, Doctor> map = new HashMap<>();

	private static HSSFWorkbook wb = new HSSFWorkbook();

	private static void getAllDepartments() throws IOException {
		Document doc = Jsoup.connect(BASE_URL).get();
		Elements depts = doc.select(".ask-keshi-mart+.J_tab ul:not(.find_dq) li");
		for (Element e : depts) {
			if (e.hasClass("find_dq")) {
				continue;
			}
			Element anchor = e.getElementsByTag("a").first();
			Element span = anchor.siblingElements().first();
			Department d = new Department();
			d.setUrl(anchor.attr("href"));
			d.setName(anchor.text());
			d.setCount(Integer.parseInt(span.text().replace("(", "").replace(")", "")));

			departments.add(d);
		}
	}

	private static void getDoctorsByDepartment(String s, String dept)
			throws IOException, SAXException, InterruptedException {
		String[] url_splited = s.split("/");
		String code = url_splited[url_splited.length - 1].split("\\.")[0];
		String url = "http://haoping.haodf.com/keshi/" + code + "/daifu.htm";

		Document doc = Jsoup.parse(new URL(url).openStream(), "gb2312", url);
		;
		String totalStr = doc.select(".p_text").first().text();
		totalStr = totalStr.substring(2, totalStr.length() - 2);
		Integer totalPage = Integer.parseInt(totalStr);

		for (int i = 1; i <= 1; i++) {
			url = url + "?prov=&p=" + i;

			Document doctorsPage = Jsoup.connect(url).get();
			Elements doctorRows = doctorsPage.select("table.bluegpanel tbody tr:nth-child(3) table:first-child a.blue");
			
			for (Element e : doctorRows) {
				Doctor doctor = new Doctor();

				String doctorUrl = e.attr("href");
				String name = e.text();
				doctor.setName(name);
				doctor.setDepartment(dept);
				System.out.println(doctorUrl);
				InputStream is = null;

				try {
					is = runPhantom(doctorUrl);
					
					Document doctorPage = Jsoup.parse(is, "utf-8", doctorUrl);
					// System.out.println(doctorPage.toString());
					Element doctorInfo_first = doctorPage.select("#bp_doctor_about .doctor_about .middletr table").first();
					while (doctorInfo_first == null) {
						is.close();
						is = runPhantom(doctorUrl);
						doctorPage = Jsoup.parse(is, "utf-8", doctorUrl);
						doctorInfo_first = doctorPage.select("#bp_doctor_about .doctor_about .middletr table").first();
					}
					//System.out.println(doctorInfo_first);
					int doctorInfo_first_rowCount = doctorInfo_first.getElementsByTag("tr").size();

					Elements remarkEle = doctorInfo_first_rowCount == 4? doctorInfo_first.select("tr:nth-child(1) td:nth-child(3) h2"): doctorInfo_first.select("tr:nth-child(2) td:nth-child(3) h2");
					String remark = remarkEle.first().text().replace("&nbsp;", "");
					
					Elements titleEle = doctorInfo_first_rowCount == 4? doctorInfo_first.select("tr:nth-child(2) td:nth-child(3)"): doctorInfo_first.select("tr:nth-child(3) td:nth-child(3)");
					String title = titleEle.isEmpty()? "": titleEle.first().text();
					
					Elements advantageEle = doctorInfo_first_rowCount == 4? doctorInfo_first.select("tr:nth-child(3) #full_DoctorSpecialize"): doctorInfo_first.select("tr:nth-child(4) #full_DoctorSpecialize");
					String advantage = advantageEle.isEmpty() ? "" : advantageEle.first().text().trim().replace("<< 收起", "");
					
					Elements jobExpEle = doctorInfo_first_rowCount == 4? doctorInfo_first.select("tr:nth-child(4) #full"): doctorInfo_first.select("tr:nth-child(5) #full");
					if (jobExpEle.isEmpty()) {
						jobExpEle = doctorInfo_first.select("tr:nth-child(5) td:nth-child(3)");
					}
					
					String jobExp = jobExpEle.isEmpty() ? "" : jobExpEle.first().text().trim().replace("<< 收起", "").split(" ")[0];
					
					doctor.setRemark(remark);
					doctor.setTitle(title);
					doctor.setAdvantage(advantage);
					doctor.setJobExp(jobExp);
					doctor.setHospital(remark.split("医院")[0] + "医院");

					Elements doctorInfo_second = doctorPage
							.select("#bp_doctor_getvotestar .doctor_panel .middletr table");
					if (doctorInfo_second.isEmpty()) {
						doctorInfo_second = doctorPage
								.select("#bp_doctor_getvote .doctor_panel .middletr table");
					}
					Element clinicalAnchors = doctorInfo_second.select("td").first();

					//String clinicalExp = clinicalAnchors.text().replaceAll("</a>", "").replaceAll("<a.*?>", "")
					//		.replaceAll("&nbsp;", "");
					//doctor.setClinicalExp(clinicalExp);

					System.out.println(doctor);

					doctors.add(doctor);
				} catch (Exception ee) {
					ee.printStackTrace();
				} finally {
					is.close();
				}
				// Thread.sleep(1000);
			}
		}

		writeDataToExcel(dept);
	}
	
	private static InputStream runPhantom(String url) throws IOException {
		Runtime rt = Runtime.getRuntime();
		String exec = "phantomjs.exe code.js " + url + " --load-images=no --disk-cache=yes";
		Process p = rt.exec(exec);
		return p.getInputStream();
	}

	private static void writeDataToExcel(String departmentName) {
		Sheet sheet1 = wb.createSheet(departmentName);
		Row header = sheet1.createRow(0);
		Cell cell0 = header.createCell(0);
		cell0.setCellValue("姓名");
		Cell cell1 = header.createCell(1);
		cell1.setCellValue("医院");
		Cell cell2 = header.createCell(2);
		cell2.setCellValue("科室");
		Cell cell3 = header.createCell(3);
		cell3.setCellValue("职称");
		Cell cell4 = header.createCell(4);
		cell4.setCellValue("擅长");
		Cell cell5 = header.createCell(5);
		cell5.setCellValue("执业经历");
		Cell cell6 = header.createCell(6);
		cell6.setCellValue("临床经验");
		Cell cell7 = header.createCell(7);
		cell7.setCellValue("备注");

		for (int i = 0; i < doctors.size(); i++) {
			Doctor d = doctors.get(i);
			writeExcelRow(d, sheet1, i + 1);
		}
	}

	private static void writeExcelRow(Doctor d, Sheet sheet1, int rowNum) {
		Row row = sheet1.createRow(rowNum);
		Cell c0 = row.createCell(0);
		c0.setCellValue(d.getName());
		Cell c1 = row.createCell(1);
		c1.setCellValue(d.getRemark().split("医院")[0] + "医院");
		Cell c2 = row.createCell(2);
		c2.setCellValue(d.getDepartment());
		Cell c3 = row.createCell(3);
		c3.setCellValue(d.getTitle());
		Cell c4 = row.createCell(4);
		c4.setCellValue(d.getAdvantage());
		Cell c5 = row.createCell(5);
		c5.setCellValue(d.getJobExp());
		Cell c6 = row.createCell(6);
		//c6.setCellValue(d.getClinicalExp());
		Cell c7 = row.createCell(7);
		c7.setCellValue(d.getRemark());
	}
	
	public static void main(String[] args) throws IOException, SAXException, InterruptedException {
		// getAllDepartments();

		getDoctorsByDepartment("http://www.haodf.com/keshi/DE4r0PiRvNoMVGaNgMSP7Glx525DzK.htm", "心血管外科");

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("data.xls");
			wb.write(out);
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
}
