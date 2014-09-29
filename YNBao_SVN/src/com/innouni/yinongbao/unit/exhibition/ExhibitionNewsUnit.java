package com.innouni.yinongbao.unit.exhibition;

/***
 * 农资展厅企业新闻实体类
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-29
 */
public class ExhibitionNewsUnit {
	/***
	 * 新闻id
	 */
	private String id;
	/***
	 * 新闻标题
	 */
	private String title;
	/***
	 * 新闻添加时间
	 */
	private String inputtime;
	/***
	 * 新闻详情
	 */
	private String content;

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

	public String getInputtime() {
		return inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
