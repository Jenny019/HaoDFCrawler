package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
	private String province;
	private String city;
	private String category;
	private String name;
	private String level;
	private String description;
	private String address;
	private String telephone;
	private HashMap<String, HashMap<Department, ArrayList<Doctor>>> map = new HashMap<>();
	private String urlCode;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	public String toString() {
		return "{" + "地区: " + province + ", 城市: " + city + ", 医院分类: " + category + ", 医院名称: " + name + ", 医院等级: "
				+ level + ", 医院介绍: " + description + ", 地址: " + address + ", 电话: " + telephone + ", url: " + urlCode
				+ ", 字典: " + map + "}";
	}

	public HashMap<String, HashMap<Department, ArrayList<Doctor>>> getMap() {
		return map;
	}

	public void setMap(HashMap<String, HashMap<Department, ArrayList<Doctor>>> map) {
		this.map = map;
	}
}
