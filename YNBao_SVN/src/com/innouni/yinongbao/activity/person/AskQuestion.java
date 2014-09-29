package com.innouni.yinongbao.activity.person;

import org.json.JSONObject;

/**
 * 问题实体
 * @author Hugj
 *
 */
public class AskQuestion {

	public String id;
	public String title;
	public String addTime;
	public String views;
	public String replies;
	public String status;
	public String username;
	
	public AskQuestion(JSONObject object) {
		id = object.optString("Id");
		title = object.optString("title");
		addTime = object.optString("addtime");
		views = object.optString("views");
		replies = object.optString("replies");
		status = object.optString("status");
		username = object.optString("username");
	}
}
