package com.dawnstep.codemonkey.discovery.newskillget;

import com.dawnstep.codemonkey.R;
import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class NewSkillGetDetailActivity extends Activity {

	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_skill_get_detail);
		Bundle bundle = getIntent().getExtras();
		String newSkillGetId = bundle.getString("newSkillGetId");
		String newSkillGetIdUrl = CodeMonkeyConfig.getNewSkillGetPath() + "/" + newSkillGetId;
		webView = (WebView)findViewById(R.id.newSkillGetDetailWebview);
		webView.loadUrl(newSkillGetIdUrl);
	}
}
