package com.dawnstep.codemonkey.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
		    StringEntity se;
			se = new StringEntity(param.toString());
		    httpRequest.setEntity(se); 
			HttpResponse httpResponse = client.execute(httpRequest);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());  
			// 生成 JSON 对象  
			JSONObject result = new JSONObject( retSrc);  
			this.token = (String) result.get("token");  
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // 绑定到请求 Entry  
	    return true;
	}
}
