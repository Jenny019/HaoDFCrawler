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
		return "{ ������" + name + ", ����: " + department + ", ���ҽ���: " + departmentDescription + ", ְ�ƣ�" + title + ", �ó���"
				+ advantage + ", ִҵ������" + jobExp + ", ��վ: " + website + ", url: " + url + " }\n";
	}

}
