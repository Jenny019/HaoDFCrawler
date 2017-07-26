package models;

public class Doctor {
	private String name;
	private String hospital;
	private String department;
	private String title;
	private String advantage;
	private String jobExp;
	private String website;
	private String remark;
	private String url;

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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
		return "{ ������" + name + ", ְ�ƣ�" + title + ", �ó���" + advantage + ", ִҵ������" + jobExp + ", ��վ: " + website
				+ ", url: " + url + " }\n";
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
