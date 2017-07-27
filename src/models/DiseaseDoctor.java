package models;

public class DiseaseDoctor extends Doctor {
	private String department;
	private String departmentDescription;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentDescription() {
		return departmentDescription;
	}

	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}

	public String toString() {
		return "{ 姓名：" + name + ", 科室: " + department + ", 科室介绍: " + departmentDescription + ", 职称：" + title + ", 擅长："
				+ advantage + ", 执业经历：" + jobExp + ", 网站: " + website + ", url: " + url + " }\n";
	}

}
