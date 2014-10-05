package com.innouni.yinongbao.activity.person;

import org.json.JSONObject;

/**
 * 我的帖子
 * @author Hugj
 *
 */
public class MyTiezi {

	public String id;
	public String title;
	public String addtime;
	public String catname;
	public String viewnum;
	public String replynum;
	
	public MyTiezi(JSONObject object) {
		this.id = object.optString("Id");
		this.title = object.optString("title");
		this.addtime = object.optString("addtime");
		this.catname = object.optString("catname");
		this.viewnum = object.optString("viewnum");
		this.replynum = object.optString("replynum");
	}
}
