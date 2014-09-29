package com.innouni.yinongbao.unit.exhibition;

import java.io.Serializable;
import java.util.List;

/***
 * 农资展厅企业实体类
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-29
 */
@SuppressWarnings("serial")
public class ExhibitionCompanyUnit implements Serializable {
	/***
	 * 企业id
	 */
	private String companyid;
	/***
	 * 企业名称
	 */
	private String companyname;
	/***
	 * 联系人
	 */
	private String linkman;
	/***
	 * 手机号码
	 */
	private String bile;
	/***
	 * 电话号码
	 */
	private String telephone;
	/***
	 * 传真号码
	 */
	private String fax;
	/***
	 * 企业地址
	 */
	private String address;
	/***
	 * 企业logo
	 */
	private String logo;
	/***
	 * 企业简介
	 */
	private String introduce;
	/***
	 * 企业登记图标
	 */
	private String icon;
	/***
	 * 企业新闻列表
	 */
	private List<ExhibitionNewsUnit> news;

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getBile() {
		return bile;
	}

	public void setBile(String bile) {
		this.bile = bile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<ExhibitionNewsUnit> getNews() {
		return news;
	}

	public void setNews(List<ExhibitionNewsUnit> news) {
		this.news = news;
	}

}
