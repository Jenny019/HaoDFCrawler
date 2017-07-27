package crawlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import enums.HospitalCategoryEnum;
import enums.ProvinceEnum;
import models.Department;
import models.Doctor;
import models.Hospital;
import utils.ExcelUtil;

public class HospitalCrawler {
	private static final String HOSPITAL_LIST_URL = "http://www.haodf.com/yiyuan/%s/list.htm?category=%s";
	private static final String HOSPITAL_DETAIL_URL = "http://www.haodf.com";

	private static HashMap<String, ArrayList<Hospital>> getHospitalList() {
		HashMap<String, ArrayList<Hospital>> map = new HashMap<>();

		provinceLoop: for (ProvinceEnum p : ProvinceEnum.values()) {
			ArrayList<Hospital> list = new ArrayList<>();
			
			categoryLoop: for (HospitalCategoryEnum c : HospitalCategoryEnum.values()) {
				String hospitalListUrl = String.format(HOSPITAL_LIST_URL, p.getValue(), c.getValue());
				try {
					Document hospitalListPage = Jsoup.connect(hospitalListUrl).get();
					Element hospitalListRootElement = hospitalListPage.getElementById("el_result_content")
							.getElementsByClass("ct").first();
					Elements cityElements = hospitalListRootElement.getElementsByClass("m_title_green");
					Elements hospitalElements = hospitalListRootElement.getElementsByClass("m_ctt_green");

					for (int i = 0; i < cityElements.size(); i++) {
						Element city = cityElements.get(i);
						Elements hospitals = hospitalElements.get(i).getElementsByTag("a");
						for (Element hospitalElement : hospitals) {
							Hospital h = new Hospital();
							h.setProvince(p.getLabel());
							h.setCity(city.ownText());
							h.setCategory(c.getLabel());
							h.setName(hospitalElement.ownText());
							h.setUrlCode(hospitalElement.attr("href"));

							// DEBUG INFO
							System.out.println("HOSPITAL ===> " + h.getUrlCode());
							getHospitalDetail(h);

							list.add(h);
							break categoryLoop;
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			map.put(p.getLabel(), list);
			break provinceLoop;
		}
		return map;
	}

	private static Hospital getHospitalDetail(Hospital hospital) {
		String url = HOSPITAL_DETAIL_URL + hospital.getUrlCode();
		try {
			Document hospitalDetailPage = Jsoup.connect(url).get();
			Element hospitalDetailRootElement = hospitalDetailPage.getElementById("contentA");

			Element hospitalInfoPanel = hospitalDetailRootElement.getElementsByClass("panelA_blue").first();
			extractHospitalInfo(hospital, hospitalInfoPanel);

			Element hospitalDepartmentPanel = hospitalDetailRootElement.getElementsByClass("panelB_blue").first();
			extractDepartmentInfo(hospital, hospitalDepartmentPanel);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return hospital;
	}

	private static void extractHospitalInfo(Hospital hospital, Element hospitalInfoPanel) {
		Element toptr = hospitalInfoPanel.getElementsByClass("toptr").first();
		String level = toptr.getElementsByTag("p").first().ownText().replaceAll("[\\(\\)]", "");
		hospital.setLevel(level);

		Element midtr = hospitalInfoPanel.getElementsByClass("midtr").first();

		// 获取医院介绍（详情）
		Element descriptionElement = midtr.getElementsByTag("tr").get(0);
		String descriptionDetailUrl = descriptionElement.getElementsByTag("a").first().attr("href");
		try {
			Document hospitalDescriptionDetailPage = Jsoup.connect(descriptionDetailUrl).get();
			Element hospitalDescriptionDetailTr = hospitalDescriptionDetailPage
					.select(".bluepanel>tbody>tr:nth-child(3)").first();
			String descriptionDetail = hospitalDescriptionDetailTr.select("tbody>tr>td").first().ownText();
			hospital.setDescription(descriptionDetail);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 获取医院地址
		Element addressElement = midtr.getElementsByTag("tr").get(1);
		String address = addressElement.getElementsByTag("td").get(1).ownText();
		hospital.setAddress(address);

		// 获取医院电话
		Element telephoneElement = midtr.getElementsByTag("tr").get(4);
		String telephone = telephoneElement.getElementsByTag("td").get(1).ownText();
		hospital.setTelephone(telephone);
	}

	private static void extractDepartmentInfo(Hospital hospital, Element hospitalDepartmentPanel) {
		Elements departmentTrs = hospitalDepartmentPanel.select(".midtr>.lt>table>tbody>tr");
		firstDepartmentLoop: for (int i = 0; i < departmentTrs.size(); i = i + 2) {
			Element departmentTr = departmentTrs.get(i);
			// 获取一级科室
			String departmentTopLevel = departmentTr.getElementsByTag("td").first().ownText();

			// 获取二级科室列表
			HashMap<Department, ArrayList<Doctor>> deptMap = new HashMap<>();
			Elements departmentTds = departmentTr.getElementsByTag("td").get(1).getElementsByTag("td");
			departmentTds.remove(0);

			secondDepartmentLoop: for (Element departmentElement : departmentTds) {
				Department dept = new Department();

				// 获取二级科室名字，URL地址
				Element deptAnchor = departmentElement.getElementsByTag("a").first();
				String deptName = deptAnchor.ownText();
				dept.setName(deptName);
				String url = deptAnchor.attr("href");
				dept.setUrl(url);

				// DEBUG INFO
				System.out.println("DEPARTMENT ===> " + url);

				// 获取医生人数
				Element countTextElement = departmentElement.getElementsByTag("span").first();
				String countText = countTextElement.attr("title");
				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(countText);
				m.find();
				String count = m.group();
				dept.setCount(Integer.parseInt(count));

				// 获取该部门下的所有医生信息
				ArrayList<Doctor> doctorList = getDoctorListForSingleDepartment(dept);
				deptMap.put(dept, doctorList);
				
				break secondDepartmentLoop;
			}

			hospital.getMap().put(departmentTopLevel, deptMap);
			break firstDepartmentLoop;
		}
	}

	private static ArrayList<Doctor> getDoctorListForSingleDepartment(Department dept) {
		ArrayList<Doctor> list = new ArrayList<>();

		String baseUrl = dept.getUrl();
		Document doc;
		try {
			doc = Jsoup.parse(new URL(baseUrl).openStream(), "gb2312", baseUrl);
			// 获取科室介绍
			Element deptAboutElement = doc.getElementById("about");
			String deptAboutUrl = deptAboutElement.select("tbody>tr>td.tdr_a>a").first().attr("href");
			Document deptAboutPage = Jsoup.connect(deptAboutUrl).get();
			String deptAbout = deptAboutPage.getElementById("about_det").ownText();
			dept.setDescription(deptAbout);
			
			// 获取医生页数
			Element totalStrElement = doc.select(".p_text").first();
			String totalStr;
			if (null == totalStrElement) {
				totalStr = "共 1 页";
			} else {
				totalStr = totalStrElement.ownText();
			}
			totalStr = totalStr.substring(2, totalStr.length() - 2);
			Integer totalPage = Integer.parseInt(totalStr);

			// 循环获取医生信息
			doctorListLoop: for (int i = 1; i <= totalPage; i++) {
				String url = baseUrl + "?prov=&p=" + i;

				Document doctorsPage = Jsoup.connect(url).get();
				Elements doctorRows = doctorsPage.getElementById("doc_list_index")
						.select("tbody>tr>td.tdnew_a a.name");
				doctorRowLoop: for (Element e : doctorRows) {
					Doctor doctor = new Doctor();

					String doctorUrl = e.attr("href");
					String name = e.text();
					doctor.setName(name);
					doctor.setUrl(doctorUrl);

					// DEBUG INFO
					System.out.println("DOCTOR ===> " + doctorUrl);

					DoctorCrawler.extractDoctorInfo(doctor);

					list.add(doctor);

					break doctorListLoop;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		HashMap<String, ArrayList<Hospital>> map = getHospitalList();
		ExcelUtil.writeDataToExcel(map);
	}
}
