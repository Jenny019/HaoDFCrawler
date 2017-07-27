package enums;

public enum DiseaseTopCategoryEnum {
	儿科("xiaoerke", "儿科"),
	妇产科("fuchanke", "妇产科"),
	内科("neike", "内科"),
	外科("waike", "外科"),
	生殖中心("shengzhiyixuezhongxin", "生殖中心"),
	骨外科("guke", "骨外科"),
	眼科学("yanke", "眼科学"),
	口腔科学("kouqiangke", "口腔科学"),
	口鼻咽喉头颈科("erbiyanhoutoujingke", "口鼻咽喉头颈科"),
	肿瘤科("zhongliuke", "肿瘤科"),
	皮肤性病科("pifuxingbingke", "皮肤性病科"),
	男科("nanke", "男科"),
	皮肤美容("yiliaomeirongke", "皮肤美容"),
	烧伤科("shaoshangke", "烧伤科"),
	精神心理科("jingshenxinlike", "精神心理科"),
	中医学("zhongyike", "中医学"),
	中西医结合科("zhongxiyijieheke", "中西医结合科"),
	传染病科("chuanranbingke", "传染病科"),
	结核病科("jiehebingke", "结核病科"),
	介入医学科("jieruyixueke", "介入医学科"),
	康复医学科("kangfuyixueke", "康复医学科"),
	运动医学科("yundongyixueke", "运动医学科"),
	麻醉医学科("mazuiyixueke", "麻醉医学科"),
	职业病科("zhiyebingke", "职业病科"),
	地方病科("difangbingke", "地方病科"),
	营养科("yingyangke", "营养科"),
	医学影像学("yixueyingxiangxue", "医学影像学"),
	病理科("binglike", "病理科"),
	其他科室("qitakeshi", "其他科室");
	
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
