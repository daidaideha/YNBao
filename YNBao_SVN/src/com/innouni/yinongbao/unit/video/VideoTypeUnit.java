package com.innouni.yinongbao.unit.video;

/***
 * 视频库分类实体类
 * 
 * @author LinYuLing
 * @UpdateDate 2014-10-06
 */
public class VideoTypeUnit {
	/***
	 * 分类id
	 */
	private String catId;
	/***
	 * 分类名称
	 */
	private String catName;

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
}
