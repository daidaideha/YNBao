package com.innouni.yinongbao.unit.exhibition;

import java.util.List;

import org.json.JSONObject;

/***
 * 农资展厅产品实体类
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-27
 */
public class ExhibitionUnit {
	/***
	 * 产品id
	 */
	private String id;
	/***
	 * 产品名称
	 */
	private String title;
	/***
	 * 产品图标
	 */
	private String thumb;
	/***
	 * 产品所属公司
	 */
	private String company;
	/***
	 * 产品所属公司id
	 */
	private String companyId;
	/***
	 * 产品简介
	 */
	private String introduce;
	/***
	 * 产品详情图片列表
	 */
	private List<String> pic_urls;
	/***
	 * 应用案例列表
	 */
	private List<String> caselist;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public List<String> getPic_urls() {
		return pic_urls;
	}

	public void setPic_urls(List<String> pic_urls) {
		this.pic_urls = pic_urls;
	}

	public List<String> getCaselist() {
		return caselist;
	}

	public void setCaselist(List<String> caselist) {
		this.caselist = caselist;
	}

	/**
	 * add by Hugj
	 */
	public ExhibitionUnit() {
	};

	public ExhibitionUnit(JSONObject object) {
		title = object.optString("title");
		id = object.optString("Id");
		thumb = object.optString("thumb");
		companyId = object.optString("companyId");
		company = object.optString("companyName");
	}

}
