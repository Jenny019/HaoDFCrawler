package models;

import java.util.ArrayList;

public class DiseaseHospital {
	private String city;
	private String name;
	private String level;
	private String category;
	private String url;
	private ArrayList<DiseaseDoctor> doctorList = new ArrayList<>();

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<DiseaseDoctor> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(ArrayList<DiseaseDoctor> doctorList) {
		this.doctorList = doctorList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
