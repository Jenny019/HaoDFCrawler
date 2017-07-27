package enums;

public enum ProvinceEnum {
	BEIJING("beijing", "����"),
	SHANGHAI("shanghai", "�Ϻ�"),
	GUANGDONG("guangdong", "�㶫"),
	GUANGXI("guangxi", "����"),
	JIANGSU("jiangsu", "����"),
	ZHEJIANG("zhejiang", "�㽭"),
	ANHUI("anhui", "����"),
	JIANGXI("jiangxi", "����"),
	FUJIAN("fujian", "����"),
	SHANDONG("shandong", "ɽ��"),
	SHANXI1("sx", "ɽ��"),
	HEBEI("hebei", "�ӱ�"),
	HENAN("henan", "����"),
	TIANJIN("tianjin", "���"),
	LIAONING("liaoning", "����"),
	HEILONGJIANG("heilongjiang", "������"),
	JILIN("jilin", "����"),
	HUBEI("hubei", "����"),
	HUNAN("hunan", "����"),
	SICHUAN("sichuan", "�Ĵ�"),
	CHONGQING("chongqing", "����"),
	SHANXI2("shanxi", "����"),
	GANSU("gansu", "����"),
	YUNNAN("yunnan", "����"),
	XINJIANG("xinjiang", "�½�"),
	NEIMENGGU("neimenggu", "���ɹ�"),
	HAINAN("hainan", "����"),
	GUIZHOU("guizhou", "����"),
	QINGHAI("qinghai", "�ຣ"),
	NINGXIA("ningxia", "����"),
	XIZANG("xizang", "����");
	
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
