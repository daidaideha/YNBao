package com.innouni.yinongbao.unit.pest;

import java.util.List;

/***
 * 害虫图库首页数据实体类
 * @author LinYuLing
 * @UpdateDate 2014-09-30
 */
public class PestMainUnit {
	/***
	 * 害虫所属分类id
	 */
	private String catId;
	/***
	 * 害虫所属分类
	 */
	private String catName;
	/***
	 * 害虫列表
	 */
	private List<PestUnit> picturelist;

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public List<PestUnit> getPicturelist() {
		return picturelist;
	}

	public void setPicturelist(List<PestUnit> picturelist) {
		this.picturelist = picturelist;
	}

}
