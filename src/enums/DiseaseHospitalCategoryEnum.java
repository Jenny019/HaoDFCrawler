package enums;

public enum DiseaseHospitalCategoryEnum {
	�ۺ�ҽԺ(0, "�ۺ�ҽԺ"),
	��ҽԺ(2, "��ҽԺ"),
	����ҽԺ(3, "����ҽԺ"),
	��ͯҽԺ(4, "��ͯҽԺ"),
	��ǻҽԺ(5, "��ǻҽԺ"),
	�ǿ�ҽԺ(6, "�ǿ�ҽԺ"),
	����ҽԺ(7, "����ҽԺ"),
	��Ⱦ��ҽԺ(8, "��Ⱦ��ҽԺ"),
	����ҽԺ(9, "����ҽԺ"),
	����ר��(1, "����ר��");

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
