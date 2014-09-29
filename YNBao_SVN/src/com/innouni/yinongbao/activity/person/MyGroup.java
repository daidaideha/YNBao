package com.innouni.yinongbao.activity.person;

import org.json.JSONObject;

/**
 * 我的群组实体类
 * @author Hugj
 *
 */
public class MyGroup {

	public String id;
	public String name;
	public String description;
	public String membernum;
	public String icon;
	
	public MyGroup (JSONObject object) {
		id = object.optString("fid");
		name = object.optString("name");
		description = object.optString("description");
		membernum = object.optString("membernum");
		icon = object.optString("icon");
	}
}
