package crawlers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import enums.DiseaseHospitalCategoryEnum;
import enums.DiseaseTopCategoryEnum;
import models.Disease;
import models.DiseaseHospital;
import models.DiseaseDoctor;

public class DiseaseCrawler {
	// %s表示一级疾病分类
	private static final String DISEASE_CATEGORY_LIST_URL = "http://www.haodf.com/jibing/%s/list.htm";
	// %s按顺序表示: 疾病, 省, 市, 类型, 页码
	private static final String DISEASE_HOSPITAL_LIST_URL = "http://www.haodf.com/jibing/%s_yiyuan_%s_%s_all_%s_%s.htm";
	// %s按顺序表示：疾病, 医院编号, 页码
	private static final String DISEASE_HOSPITAL_DOCTOR_LIST_URL = "http://www.haodf.com/tuijian/jibing_%s_%s.htm?p=%s";

	private HashMap<String, HashMap<String, ArrayList<Disease>>> getDiseaseList() {
		HashMap<String, HashMap<String, ArrayList<Disease>>> map = new HashMap<>();

		try {
			// 疾病分类一级
			for (DiseaseTopCategoryEnum c_disease : DiseaseTopCategoryEnum.values()) {
				String diseaseCategoryUrl = String.format(DISEASE_CATEGORY_LIST_URL, c_disease);

				Document diseaseCategoryPage = Jsoup.connect(diseaseCategoryUrl).get();
				Element diseaseSubCategoryRootElement = diseaseCategoryPage.getElementById("m_body_html_ctt")
						.getElementsByClass("ksbd").first();
				Elements diseaseSubCategories = diseaseSubCategoryRootElement.getElementsByTag("a");

				HashMap<String, ArrayList<Disease>> subMap = new HashMap<>();
				// 疾病分类二级
				for (Element c_disease_sub : diseaseSubCategories) {
					String subCateogryUrl = c_disease_sub.attr("href");
					String subCategoryName = c_disease_sub.ownText();

					Document diseaseListPage = Jsoup.connect(subCateogryUrl).get();
					Element diseaseListRootElement = diseaseListPage.getElementById("el_result_content");
					Elements diseaseListElements = diseaseListRootElement.select(".m_title_green a");

					ArrayList<Disease> diseaseList = new ArrayList<>();

					// 疾病
					for (Element diseaseElement : diseaseListElements) {
						Disease disease = new Disease();
						disease.setHospitalList(new ArrayList<>());
						String diseaseUrl = diseaseElement.attr("href");
						disease.setUrl(diseaseUrl);
						String diseaseName = diseaseElement.ownText();
						disease.setName(diseaseName);
						String param_disease = diseaseUrl.replaceAll("/jibing/", "").replaceAll(".html", "");

						for (DiseaseHospitalCategoryEnum c_hospital : DiseaseHospitalCategoryEnum.values()) {
							String param_hospital_category = "c" + c_hospital.getValue();
							String hospitalListUrl = String.format(DISEASE_HOSPITAL_LIST_URL, param_disease, "all",
									"all", param_hospital_category, "1");

							// 筛选了类型
							Document hospitalListPage = Jsoup.connect(hospitalListUrl).get();
							Element hospitalListMenuElement = hospitalListPage.getElementsByClass("area_box_list")
									.first();
							Elements hospitalListProvinceAnchors = hospitalListMenuElement.getElementsByTag("a");
							hospitalListProvinceAnchors.remove(0);
							for (int i = 0; i < hospitalListProvinceAnchors.size(); i++) {
								Element hospitalListProvinceAnchor = hospitalListProvinceAnchors.get(i);
								String hospitalListProvinceUrl = hospitalListProvinceAnchor.attr("href");
								if (hospitalListProvinceUrl.startsWith("javascript")) {
									continue;
								}

								// 筛选了类型、省
								Document hospitalListPage2 = Jsoup.connect(hospitalListProvinceUrl).get();
								Element hospitalListMenuElement2 = hospitalListPage2.getElementsByClass("area_box_list")
										.last();
								Elements hospitalListCityAnchors = hospitalListMenuElement2.getElementsByTag("a");
								hospitalListCityAnchors.remove(0);

								for (int j = 0; j < hospitalListCityAnchors.size(); j++) {
									Element hospitalListCityAnchor = hospitalListCityAnchors.get(i);
									String hospitalListCityUrl = hospitalListCityAnchor.attr("href");
									String hospitalListCityName = hospitalListCityAnchor.ownText();
									if (hospitalListCityUrl.startsWith("javascript")) {
										continue;
									}
									// 筛选了类型、省、市
									String[] params = hospitalListCityUrl.split("_");
									String param_province = params[2];
									String param_city = params[3];

									Document hospitalListPage3 = Jsoup.connect(hospitalListCityUrl).get();
									Element totalPageElement = hospitalListPage3.getElementsByClass("page_turn")
											.first();
									String totalPageStr = null;
									if (null == totalPageElement) {
										totalPageStr = "1";
									} else {
										totalPageStr = totalPageElement.select(".page_turn_a font").first().ownText();
									}

									// 循环遍历页码
									for (int k = 1; k <= Integer.parseInt(totalPageStr); k++) {
										String url = String.format(DISEASE_HOSPITAL_LIST_URL, param_disease,
												param_province, param_city, param_hospital_category, k + "");
										// 筛选了类型、省、市、页码
										Document hospitalListPage4 = Jsoup.connect(hospitalListCityUrl).get();
										Element hospitalListRootElement = hospitalListPage4.getElementById("left_con");
										Elements hospitalListTrs = hospitalListRootElement
												.select("table.hp_hos_table>tbody>tr");
										for (Element hospitalListTr : hospitalListTrs) {
											Elements hospitalListTds = hospitalListTr.getElementsByTag("td");
											Element hospitalAnchor = hospitalListTds.get(0).getElementsByTag("a")
													.first();
											String hospitalUrl = hospitalAnchor.attr("href");
											String hospitalName = hospitalAnchor.ownText();
											String hospitalLevel = hospitalListTds.get(2).ownText();

											DiseaseHospital diseaseHospital = new DiseaseHospital();
											diseaseHospital.setCity(hospitalListCityName);
											diseaseHospital.setCategory(c_hospital.getLabel());
											diseaseHospital.setName(hospitalName);
											diseaseHospital.setLevel(hospitalLevel);
											diseaseHospital.setUrl(hospitalUrl);

											getDoctorListForSingleHospital(diseaseHospital);

											disease.getHospitalList().add(diseaseHospital);
										}
									}
								}
							}
						}
						diseaseList.add(disease);
					}
					subMap.put(subCategoryName, diseaseList);
				}
				map.put(c_disease.getLabel(), subMap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	private static void getDoctorListForSingleHospital(DiseaseHospital diseaseHospital) {
		ArrayList<DiseaseDoctor> list = new ArrayList<>();
		diseaseHospital.setDoctorList(list);
		HashMap<String, String> departmentMap = new HashMap<>();

		String baseUrl = diseaseHospital.getUrl();
		Document doc;
		try {
			doc = Jsoup.parse(new URL(baseUrl).openStream(), "gb2312", baseUrl);

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

			// 获取隐藏表单内容
			Elements hiddenInputs = doc.getElementsByAttributeValue("type", "hidden");
			String hospital_id = hiddenInputs.get(0).val();
			String disease_key = hiddenInputs.get(1).val();
			// String faculty_id = hiddenInputs.get(2).val();

			// 循环获取医生信息
			doctorListLoop: for (int i = 1; i <= totalPage; i++) {
				String url = String.format(DISEASE_HOSPITAL_DOCTOR_LIST_URL, disease_key, hospital_id, i);

				Document doctorsPage = Jsoup.connect(url).get();
				Elements doctorRows = doctorsPage.select("table.yy_jb_df3>td>tr:first-child");
				doctorRowLoop: for (Element doctorRow : doctorRows) {
					DiseaseDoctor doctor = new DiseaseDoctor();

					// 获取医生姓名和URL
					Elements doctorInfo = doctorRow.getElementsByTag("tr");
					Element doctorAnchor = doctorInfo.get(0).getElementsByTag("a").first();
					String doctorName = doctorAnchor.ownText();
					doctor.setName(doctorName);
					String doctorUrl = doctorAnchor.attr("href");
					doctor.setUrl(doctorUrl);

					Element doctorDepartmentAnchor = doctorInfo.get(2).getElementsByTag("a").first();
					String doctorDepartmentName = doctorDepartmentAnchor.ownText();
					doctor.setDepartment(doctorDepartmentName);
					String doctorDepartmentUrl = doctorDepartmentAnchor.attr("href");

					// 从缓存中查询是否有该科室信息，没有则获取，并添加到缓存中
					if (departmentMap.containsKey(doctorDepartmentName)) {
						doctor.setDepartmentDescription(departmentMap.get(doctorDepartmentName));
					} else {
						Document deptAboutPage = Jsoup.connect(doctorDepartmentUrl.replace(".htm", "/jieshao.htm"))
								.get();
						String deptAbout = deptAboutPage.getElementById("about_det").ownText();
						doctor.setDepartmentDescription(deptAbout);
						departmentMap.put(doctorDepartmentName, deptAbout);
					}

					// DEBUG INFO
					System.out.println("DOCTOR ===> " + doctorUrl);

					DoctorCrawler.extractDoctorInfo(doctor);

					diseaseHospital.getDoctorList().add(doctor);

					break doctorListLoop;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

}
