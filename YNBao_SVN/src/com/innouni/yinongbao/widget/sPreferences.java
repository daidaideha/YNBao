package com.innouni.yinongbao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


@SuppressLint("CommitPrefEdits")
public class sPreferences {
	// private Context c;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public sPreferences(Context context) {
		sp = context.getSharedPreferences("admin", 0);
		editor = sp.edit();
	}

	/** 获取全局配置文件对象 **/
	public SharedPreferences getSp() {
		return sp;
	}

	/** 更新全局配置文件键�?�? **/
	public boolean updateSp(String key, Object value) {
		if (value instanceof String) {
			editor.putString(key, value.toString());
		} else if (value instanceof Integer) {
			editor.putInt(key, ((Integer) value).intValue());
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, ((Boolean) value).booleanValue());
		} else if (value instanceof Long) {
			editor.putLong(key, ((Long) value).longValue());
		} else if (value instanceof Float) {
			editor.putFloat(key, ((Float) value).floatValue());
		} else {
			editor.putString(key, value.toString());
		}
		return editor.commit();  
	}

	/***
	 * 清除登陆信息
	 */
	public void clearUser() {
		editor.putString("memberId", "");
		editor.putString("username", "");
		editor.putString("avatar", "");
		editor.putString("grouptitle", "");
		editor.putString("credits", "");
		editor.putString("newpm", "");
		editor.putString("follower", "");
		editor.putString("realname", "");
		editor.putString("identity", "");
		editor.commit();
	}
	
	/**
	 * add by Hugj
	 * 
	 * 获取sp信息
	 * @param key
	 * @return
	 */
	public String getStringValues(String key){
		return sp.getString(key, "");
	}
	
	public boolean getBooleanValues(String key){
		return sp.getBoolean(key, false);
	}
	
	public float getFloatValues(String key){
		return sp.getFloat(key, -1);
	}
	
	public int getIntValues(String key){
		return sp.getInt(key, -1);
	}
	
	public Long getLongValues(String key){
		return sp.getLong(key, -1);
	}
	
	public boolean removeShareValues(String key){
		editor.remove(key);
		return editor.commit();
	}
}