package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.smk.skalertmessage.SKToastMessage;

public class LoginActivity extends SherlockActivity{
	private EditText username,password;
	private Button login;
	private String strUsername ,strPassword;
	private DatabaseManager dbManager;
	private List<Object> adminList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.admin_login);
		
		username = (EditText)findViewById(R.id.editText_username);
		password = (EditText)findViewById(R.id.editText_password);
		
		login = (Button)findViewById(R.id.btnLogin);
		login.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == login)
			{
				/*if (checkFields()) {
					
				}*/
				strUsername = username.getText().toString();
				strPassword = password.getText().toString();
				getfromAdmin();
			
			}
		}
	};
	
	private void getfromAdmin()
	{
		/*dbManager = new AdminController(this);
		AdminController admin_control = (AdminController)dbManager;
		adminList = new ArrayList<Object>();
		adminList = admin_control.select(strUsername , strPassword);
		
		Log.i("", "Admin List:" + adminList.toString());
		
		if(adminList != null && adminList.size() > 0)
		{
			startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
		}else {
			SKToastMessage.showMessage(LoginActivity.this, "Invalid username & password.", SKToastMessage.ERROR);
		}*/
		
		
		startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
	}
	
	private void clearText()
	{
		username.getText().clear();
		password.getText().clear();
		username.requestFocus();
	}
	
	public boolean checkFields() {
		if (username.getText().toString().length() == 0) {
			username.setError("Enter Admin Name");
			return false;
		}
		if (password.getText().toString().length() == 0) {
			password.setError("Enter Admin Password");
			return false;
		}
		
		return true;
	}
	
}
