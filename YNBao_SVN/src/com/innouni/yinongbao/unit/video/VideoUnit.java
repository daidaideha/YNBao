package com.innouni.yinongbao.unit.video;

import java.util.List;

import com.innouni.yinongbao.unit.exhibition.ExhibitionUnit;

/***
 * 视频库实体类
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-30
 */
public class VideoUnit {
	/***
	 * 害虫id
	 */
	private String id;
	/***
	 * 视频播放id
	 */
	private String vid;
	/***
	 * 害虫名称
	 */
	private String title;
	/***
	 * 害虫图标
	 */
	private String thumb;
	/***
	 * 害虫简介
	 */
	private String introduce;
	/***
	 * 更新时间
	 */
	private String updatetime;
	/***
	 * 害虫详情图片列表
	 */
	private List<String> pic_urls;
	/***
	 * 防治产品
	 */
	private List<ExhibitionUnit> products;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
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

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public List<ExhibitionUnit> getProducts() {
		return products;
	}

	public void setProducts(List<ExhibitionUnit> products) {
		this.products = products;
	}

}
