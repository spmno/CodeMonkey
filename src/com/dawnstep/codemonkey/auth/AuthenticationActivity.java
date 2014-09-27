package com.dawnstep.codemonkey.auth;

import com.dawnstep.codemonkey.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AuthenticationActivity extends Activity implements OnClickListener{

	private Button loginButton;
	private Button signupButton;
	private EditText nameText;
	private EditText passwordText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);
		loginButton = (Button)findViewById(R.id.buttonLogin);
		signupButton = (Button)findViewById(R.id.buttonSignUp);
		nameText = (EditText)findViewById(R.id.editTextName);
		passwordText = (EditText)findViewById(R.id.editTextPassword);
		loginButton.setOnClickListener(this);
		signupButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.buttonLogin) {
			String userName = nameText.getText().toString();
			String password = passwordText.getText().toString();
			LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this, userName, password);
			loginAsyncTask.execute();
		} else {
			
		}
	}
}
