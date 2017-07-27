package enums;

public enum DiseaseTopCategoryEnum {
	����("xiaoerke", "����"),
	������("fuchanke", "������"),
	�ڿ�("neike", "�ڿ�"),
	���("waike", "���"),
	��ֳ����("shengzhiyixuezhongxin", "��ֳ����"),
	�����("guke", "�����"),
	�ۿ�ѧ("yanke", "�ۿ�ѧ"),
	��ǻ��ѧ("kouqiangke", "��ǻ��ѧ"),
	�ڱ��ʺ�ͷ����("erbiyanhoutoujingke", "�ڱ��ʺ�ͷ����"),
	������("zhongliuke", "������"),
	Ƥ���Բ���("pifuxingbingke", "Ƥ���Բ���"),
	�п�("nanke", "�п�"),
	Ƥ������("yiliaomeirongke", "Ƥ������"),
	���˿�("shaoshangke", "���˿�"),
	���������("jingshenxinlike", "���������"),
	��ҽѧ("zhongyike", "��ҽѧ"),
	����ҽ��Ͽ�("zhongxiyijieheke", "����ҽ��Ͽ�"),
	��Ⱦ����("chuanranbingke", "��Ⱦ����"),
	��˲���("jiehebingke", "��˲���"),
	����ҽѧ��("jieruyixueke", "����ҽѧ��"),
	����ҽѧ��("kangfuyixueke", "����ҽѧ��"),
	�˶�ҽѧ��("yundongyixueke", "�˶�ҽѧ��"),
	����ҽѧ��("mazuiyixueke", "����ҽѧ��"),
	ְҵ����("zhiyebingke", "ְҵ����"),
	�ط�����("difangbingke", "�ط�����"),
	Ӫ����("yingyangke", "Ӫ����"),
	ҽѧӰ��ѧ("yixueyingxiangxue", "ҽѧӰ��ѧ"),
	�����("binglike", "�����"),
	��������("qitakeshi", "��������");
	
	private String value;
	private String label;

	private DiseaseTopCategoryEnum(String value, String label) {
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
