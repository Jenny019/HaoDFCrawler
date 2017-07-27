package enums;

public enum ProvinceEnum {
	BEIJING("beijing", "北京"),
	SHANGHAI("shanghai", "上海"),
	GUANGDONG("guangdong", "广东"),
	GUANGXI("guangxi", "广西"),
	JIANGSU("jiangsu", "江苏"),
	ZHEJIANG("zhejiang", "浙江"),
	ANHUI("anhui", "安徽"),
	JIANGXI("jiangxi", "江西"),
	FUJIAN("fujian", "福建"),
	SHANDONG("shandong", "山东"),
	SHANXI1("sx", "山西"),
	HEBEI("hebei", "河北"),
	HENAN("henan", "河南"),
	TIANJIN("tianjin", "天津"),
	LIAONING("liaoning", "辽宁"),
	HEILONGJIANG("heilongjiang", "黑龙江"),
	JILIN("jilin", "吉林"),
	HUBEI("hubei", "湖北"),
	HUNAN("hunan", "湖南"),
	SICHUAN("sichuan", "四川"),
	CHONGQING("chongqing", "重庆"),
	SHANXI2("shanxi", "陕西"),
	GANSU("gansu", "甘肃"),
	YUNNAN("yunnan", "云南"),
	XINJIANG("xinjiang", "新疆"),
	NEIMENGGU("neimenggu", "内蒙古"),
	HAINAN("hainan", "海南"),
	GUIZHOU("guizhou", "贵州"),
	QINGHAI("qinghai", "青海"),
	NINGXIA("ningxia", "宁夏"),
	XIZANG("xizang", "西藏");
	
	private String value;
	private String label;

	private ProvinceEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
