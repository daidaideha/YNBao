package com.innouni.yinongbao.widget;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public class httpUtil {
	//通过URL获得HTTPPOST对象
	public static HttpPost getHttpPost(String url){
		HttpPost request = new HttpPost(url);
		return request;
	}
	//通过HTTPPOST获得HTTPRESPONSE对象
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException,IOException{
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		HttpResponse response = client.execute(request);
		return response;
	}
	//通过URL发�?POST请求，返回请求结�?
	public static String queryStringForPost(String weburl,String action,
			List<NameValuePair> params, Context context){
		StringBuilder url = new StringBuilder();
		if (params.size() > 0) {
			url.append(weburl + action + "?");
		} else {
			url.append(weburl + action);
		}
		HttpPost request = httpUtil.getHttpPost(url.toString());
		StringBuilder result = new StringBuilder();
		try{
			//参数封装
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = httpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result.append(EntityUtils.toString(response.getEntity()));
			}else{
				result.append("net_err");
			}
		}catch(ClientProtocolException e){
			result.append("net_err");
		}catch(IOException e){
			result.append("net_err");
		}
		return "{" + result.toString().substring(2, result.toString().length());
	}

	//通过URL发�?POST请求，返回请求结�?
	public static String queryStringForPostForLong(String weburl,
			List<NameValuePair> params, Context context){
		StringBuilder url = new StringBuilder();
		url.append(weburl);
		HttpPost request = httpUtil.getHttpPost(url.toString());
		StringBuilder result = new StringBuilder();
		try{
			//参数封装
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = httpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result.append(EntityUtils.toString(response.getEntity()));
			}else{
				result.append("net_err");
			}
		}catch(ClientProtocolException e){
			result.append("net_err");
		}catch(IOException e){
			result.append("net_err");
		}
		return result.toString();
	}
	
	public static String getResultForHttpGet(String weburl) {  
        //注意字符串连接时不能带空格  
        String result = "";  
        HttpGet httpGet = new HttpGet(weburl);//编者按：与HttpPost区别所在，这里是将参数在地址中传递  
        
		try {
			HttpResponse response;
			HttpClient client = new DefaultHttpClient();
			// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			response = client.execute(httpGet);
	        if(response.getStatusLine().getStatusCode() == 200){  
                HttpEntity entity=response.getEntity();  
                result = EntityUtils.toString(entity, HTTP.UTF_8);  
	        } 
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return result;  
	}
}