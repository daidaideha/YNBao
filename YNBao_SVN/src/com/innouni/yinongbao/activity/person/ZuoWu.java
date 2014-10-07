package com.innouni.yinongbao.activity.person;

import org.json.JSONObject;

/**
 * 作物实体类
 * 
 * @author Hugj
 * 
 */
public class ZuoWu {

	public String id;
	public String name;

	public ZuoWu(JSONObject object) {
		id = object.optString("fid");
		name = object.optString("name");
	}
}
