package com.innouni.yinongbao.unit;

/***
 * 验证码返回实体类
 * @author LinYuLing
 *
 */
public class CodeUnit {
	/***
	 * 验证码判断值
	 */
	private String result;
	/***
	 * 验证码返回提示信息
	 */
	private String message;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
