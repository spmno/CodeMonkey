package com.dawnstep.codemonkey.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;

public class Authenticator {
	private String token;
	
	public boolean authenticate(String userName, String password) {
		String urlPath = CodeMonkeyConfig.getAuthenticatePath();

	    try {
			HttpClient client = new DefaultHttpClient();
		    HttpPost httpRequest =new HttpPost(urlPath);
			
		    JSONObject param = new JSONObject(); 
		    JSONObject user = new JSONObject();
		    user.put("email", "test2@meixing.com");  
		    user.put("password", "sunqingpeng");  
			param.put("user", user);
			ByteArrayEntity baEntity = new ByteArrayEntity(param.toString().getBytes("UTF8"));
		    baEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		    httpRequest.setEntity(baEntity); 
			HttpResponse httpResponse = client.execute(httpRequest);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());  
			// 生成 JSON 对象  
			JSONObject result = new JSONObject( retSrc);  
			this.token = (String) result.get("auth_token");  
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}   catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    // 绑定到请求 Entry  
	    return true;
	}
}
