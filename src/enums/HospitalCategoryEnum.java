package enums;

public enum HospitalCategoryEnum {
	�ۺ�ҽԺ(0, "�ۺ�ҽԺ"),
	����ҽԺ(3, "����ҽԺ"),
	��ͯҽԺ(4, "��ͯҽԺ"),
	�ۿ�ҽԺ(10, "�ۿ�ҽԺ"),
	��ǻҽԺ(5, "��ǻҽԺ"),
	���ҽԺ(11, "���ҽԺ"),
	����ҽԺ(7, "����ҽԺ"),
	�ǿ�ҽԺ(6, "�ǿ�ҽԺ"),
	����ҽԺ(9, "����ҽԺ"),
	�س�ҽԺ(12, "�س�ҽԺ"),
	��Ⱦ��ҽԺ(8, "��Ⱦ��ҽԺ"),
	��ҽҽԺ(2, "��ҽҽԺ"),
	����ҽԺ(13, "����ҽԺ"),
	ѪҺ��ҽԺ(14, "ѪҺ��ҽԺ"),
	�ؿ�ҽԺ(15, "�ؿ�ҽԺ"),
	����ҽԺ(1, "����ҽԺ");
	
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
