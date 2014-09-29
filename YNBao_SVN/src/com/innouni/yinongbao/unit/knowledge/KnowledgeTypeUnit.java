package com.innouni.yinongbao.unit.knowledge;

/***
 * 知识库分类数据实体类
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-25
 */
public class KnowledgeTypeUnit {
	/***
	 * id
	 */
	private String fid;
	/***
	 * 拼音首字母
	 */
	private String letter;
	/***
	 * 中文名称
	 */
	private String name;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}