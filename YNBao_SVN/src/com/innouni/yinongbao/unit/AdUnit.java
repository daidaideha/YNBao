package com.innouni.yinongbao.unit;

/***
 * 广告图片实体类
 * 
 * @author LinYuLing
 * 
 */
public class AdUnit {
	/***
	 * 公司名称
	 */
	private String name;
	/***
	 * 图片地址
	 */
	private String imageUrl;
	/***
	 * 链接地址
	 */
	private String linkUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

}
