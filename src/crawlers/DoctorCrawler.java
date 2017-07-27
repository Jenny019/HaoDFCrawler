package crawlers;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import models.Doctor;
import utils.PhantomUtil;

public class DoctorCrawler {

	public static void extractDoctorInfo(Doctor doctor) {
		InputStream is = null;

		try {
			String doctorUrl = doctor.getUrl();
			is = PhantomUtil.runPhantom(doctorUrl);

			Document doctorPage = Jsoup.parse(is, "utf-8", doctorUrl);
			// System.out.println(doctorPage.toString());
			Element doctorInfo_first = doctorPage.select("#bp_doctor_about .doctor_about .middletr table").first();
			while (doctorInfo_first == null) {
				is.close();
				is = PhantomUtil.runPhantom(doctorUrl);
				doctorPage = Jsoup.parse(is, "utf-8", doctorUrl);
				doctorInfo_first = doctorPage.select("#bp_doctor_about .doctor_about .middletr table").first();
			}
			// System.out.println(doctorInfo_first);
			int doctorInfo_first_rowCount = doctorInfo_first.getElementsByTag("tr").size();

			// Elements remarkEle = doctorInfo_first_rowCount == 4
			// ? doctorInfo_first.select("tr:nth-child(1) td:nth-child(3) h2")
			// : doctorInfo_first.select("tr:nth-child(2) td:nth-child(3) h2");
			// String remark = remarkEle.first().text().replace("&nbsp;", "");

			Elements titleEle = doctorInfo_first_rowCount == 4
					? doctorInfo_first.select("tr:nth-child(2) td:nth-child(3)")
					: doctorInfo_first.select("tr:nth-child(3) td:nth-child(3)");
			String title = titleEle.isEmpty() ? "" : titleEle.first().ownText();

			Elements advantageEle = doctorInfo_first_rowCount == 4
					? doctorInfo_first.select("tr:nth-child(3) #full_DoctorSpecialize")
					: doctorInfo_first.select("tr:nth-child(4) #full_DoctorSpecialize");
			String advantage = advantageEle.isEmpty() ? "" : advantageEle.first().ownText().trim();

			Elements jobExpEle = doctorInfo_first_rowCount == 4 ? doctorInfo_first.select("tr:nth-child(4) #full")
					: doctorInfo_first.select("tr:nth-child(5) #full");
			if (jobExpEle.isEmpty()) {
				jobExpEle = doctorInfo_first.select("tr:nth-child(5) td:nth-child(3)");
			}

			String jobExp = jobExpEle.isEmpty() ? "" : jobExpEle.first().ownText().trim();

			// doctor.setRemark(remark);
			doctor.setTitle(title);
			doctor.setAdvantage(advantage);
			doctor.setJobExp(jobExp);

			Elements doctorInfo_second = doctorPage.select("#bp_doctor_getvotestar .doctor_panel .middletr table");
			if (doctorInfo_second.isEmpty()) {
				doctorInfo_second = doctorPage.select("#bp_doctor_getvote .doctor_panel .middletr table");
			}

			// Element clinicalAnchors = doctorInfo_second.select("td").first();
			// String clinicalExp = clinicalAnchors.ownText();
			// doctor.setClinicalExp(clinicalExp);
			Element websiteAnchor = doctorInfo_first.nextElementSibling().nextElementSibling().getElementsByTag("a")
					.first();
			if (null != websiteAnchor) {
				String website = websiteAnchor.attr("href");
				doctor.setWebsite(website);
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Thread.sleep(1000);
	}
}
