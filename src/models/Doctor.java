package models;

public class Doctor {
	protected String name;
	protected String hospital;
	protected String title;
	protected String advantage;
	protected String jobExp;
	protected String website;
	protected String remark;
	protected String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getJobExp() {
		return jobExp;
	}

	public void setJobExp(String jobExp) {
		this.jobExp = jobExp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
		return "{ 姓名：" + name + ", 职称：" + title + ", 擅长：" + advantage + ", 执业经历：" + jobExp + ", 网站: " + website
				+ ", url: " + url + " }\n";
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
