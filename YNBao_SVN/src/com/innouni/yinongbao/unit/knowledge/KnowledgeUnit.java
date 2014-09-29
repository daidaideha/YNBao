package com.innouni.yinongbao.unit.knowledge;

import java.util.List;

import com.innouni.yinongbao.unit.exper.ExperThreadUnit;

/***
 * 知识库实体类
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-25
 */
public class KnowledgeUnit {
	/***
	 * ID
	 */
	private String tid;
	/***
	 * 分类ID
	 */
	private String fid;
	/***
	 * 分类名称
	 */
	private String name;
	/***
	 * 主题分类
	 */
	private String type;
	/***
	 * 提问者
	 */
	private String author;
	/***
	 * 提问者ID
	 */
	private String authorid;
	/***
	 * 标题
	 */
	private String title;
	/***
	 * 发布时间
	 */
	private String addtime;
	/***
	 * 浏览数
	 */
	private String views;
	/***
	 * 回复数
	 */
	private String replies;
	/***
	 * 是否有附件
	 */
	private String attachment;
	/***
	 * 头像
	 */
	private String avatar;
	/***
	 * 内容
	 */
	private String content;
	/***
	 * 解答、评论数
	 */
	private String comments_count;
	/***
	 * 图片地址
	 */
	private List<String> urls;
	/***
	 * 专家解答列表
	 */
	private List<KnowledgeAnswersUnit> answers;
	/***
	 * 网友评论列表
	 */
	private List<KnowledgeAnswersUnit> comments;
	/***
	 * 相关问题
	 */
	private List<ExperThreadUnit> relations;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getReplies() {
		return replies;
	}

	public void setReplies(String replies) {
		this.replies = replies;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComments_count() {
		return comments_count;
	}

	public void setComments_count(String comments_count) {
		this.comments_count = comments_count;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public List<KnowledgeAnswersUnit> getAnswers() {
		return answers;
	}

	public void setAnswers(List<KnowledgeAnswersUnit> answers) {
		this.answers = answers;
	}

	public List<KnowledgeAnswersUnit> getComments() {
		return comments;
	}

	public void setComments(List<KnowledgeAnswersUnit> comments) {
		this.comments = comments;
	}

	public List<ExperThreadUnit> getRelations() {
		return relations;
	}

	public void setRelations(List<ExperThreadUnit> relations) {
		this.relations = relations;
	}

}
