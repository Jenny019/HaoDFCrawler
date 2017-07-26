package enums;

public enum HospitalCategoryEnum {
	综合医院(0, "综合医院"),
	妇产医院(3, "妇产医院"),
	儿童医院(4, "儿童医院"),
	眼科医院(10, "眼科医院"),
	口腔医院(5, "口腔医院"),
	五官医院(11, "五官医院"),
	肿瘤医院(7, "肿瘤医院"),
	骨科医院(6, "骨科医院"),
	精神病医院(9, "精神病医院"),
	肛肠医院(12, "肛肠医院"),
	传染病医院(8, "传染病医院"),
	中医医院(2, "中医医院"),
	康复医院(13, "康复医院"),
	血液病医院(14, "血液病医院"),
	胸科医院(15, "胸科医院"),
	其他医院(1, "其他医院");
	
	private int value;
	private String label;

	private HospitalCategoryEnum(int value, String label) {
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
