package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Disease {
	private String url;
	private String name;
	private ArrayList<DiseaseHospital> hospitalList = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<DiseaseHospital> getHospitalList() {
		return hospitalList;
	}

	public void setHospitalList(ArrayList<DiseaseHospital> hospitalList) {
		this.hospitalList = hospitalList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
