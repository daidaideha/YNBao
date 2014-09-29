package com.innouni.yinongbao.unit.exper;

/***
 * 个人文章评论列表实体类
 * @author LinYuLing
 * @UpdateDate 2014-09-25
 */
public class ExperTechnologyCommentUnit {
	/***
	 * 评论用户id
	 */
	private String authorid;
	/***
	 * 评论用户名
	 */
	private String name;
	/***
	 * 评论时间
	 */
	private String addtime;
	/***
	 * 评论内容
	 */
	private String content;
	/***
	 * 评论用户头像
	 */
	private String pic_url;

	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

}
