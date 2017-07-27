package enums;

public enum DiseaseHospitalCategoryEnum {
	综合医院(0, "综合医院"),
	中医院(2, "中医院"),
	妇产医院(3, "妇产医院"),
	儿童医院(4, "儿童医院"),
	口腔医院(5, "口腔医院"),
	骨科医院(6, "骨科医院"),
	肿瘤医院(7, "肿瘤医院"),
	传染病医院(8, "传染病医院"),
	精神病医院(9, "精神病医院"),
	其他专科(1, "其他专科");

	private int value;
	private String label;

	private DiseaseHospitalCategoryEnum(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
