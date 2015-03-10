package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Admin;
import com.smk.skalertmessage.SKToastMessage;

public class LoginActivity extends SherlockActivity{
	private EditText username,password;
	private Button login;
	private String strUsername ,strPassword;
	private DatabaseManager dbManager;
	private List<Object> adminList;
	private ScrollView scrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.admin_login);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
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
				if (checkFields()) {
					strUsername = username.getText().toString();
					strPassword = password.getText().toString();
					
					if(strUsername.equals("IgnitePOS") && strPassword.equals("ignite@721685")){
						SharedPreferences sharedPreferences = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						
						editor.clear();
						editor.commit();
						
						editor.putString("admin_name", "-");
						
						editor.commit();
						
						startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
						finish();
						
					}else{
						dbManager = new AdminController(LoginActivity.this);
						AdminController admin_control = (AdminController)dbManager;
						adminList = new ArrayList<Object>();
						adminList = admin_control.select();
						
						if (adminList != null && adminList.size() > 0) {
							getfromAdmin();
						}else {
							SKToastMessage.showMessage(LoginActivity.this, "No user account yet!.", SKToastMessage.ERROR);
						}
					}
					
				}
			}
		}
	};
	
	private void getfromAdmin()
	{
		dbManager = new AdminController(this);
		AdminController admin_control = (AdminController)dbManager;
		adminList = new ArrayList<Object>();
		adminList = admin_control.select(strUsername , strPassword);
		
		if(adminList != null && adminList.size() > 0)
		{
			Admin admin = (Admin) adminList.get(0);
			
			SharedPreferences sharedPreferences = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			
			editor.clear();
			editor.commit();
			
			editor.putString("admin_id", admin.getID());
			editor.putString("admin_name", admin.getAdminname());
			editor.putString("admin_password", admin.getAdminpassword());
			editor.putString("admin_userlevel", admin.getUserLevel());
			
			editor.commit();
			
			if (admin.getUserLevel().equals("Manager")) {
				Log.i("", "User Level(M): "+admin.getUserLevel());
			}else {
				Log.i("", "User Level(S): "+admin.getUserLevel());
			}
			
			finish();
			startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
		}else {
			SKToastMessage.showMessage(LoginActivity.this, "Invalid username & password.", SKToastMessage.ERROR);
		}
		
		
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
