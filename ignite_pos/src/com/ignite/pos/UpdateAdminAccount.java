package com.ignite.pos;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Admin;
import com.smk.skalertmessage.SKToastMessage;

public class UpdateAdminAccount extends SherlockActivity{

	private EditText curr_adminrname, curr_password, newadminr_name, newpassword;
	private Button save;
	private DatabaseManager dbManager;
	public static String newUsername , newPassword , currUsername , currPassword;
	private List<Object>adminList;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_admin_account);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		Bundle bundle = getIntent().getExtras();
		String oldUserName = bundle.getString("userName");
		
		curr_adminrname = (EditText)findViewById(R.id.editText_curr_username);
		curr_adminrname.setText(oldUserName);
		
		curr_password = (EditText)findViewById(R.id.editText_curr_password);
		newadminr_name = (EditText)findViewById(R.id.editText_new_username);
		newpassword = (EditText)findViewById(R.id.editText_new_password);
				
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == save)
				{
					if (checkFields()) {
						currUsername = curr_adminrname.getText().toString();
						currPassword = curr_password.getText().toString();
						updateData();
					}
				}
			}
		}; 
	
	private void updateData()
	{
		dbManager = new AdminController(this);
		AdminController admin_control = (AdminController)dbManager;
		adminList = new ArrayList<Object>();
		adminList = admin_control.select(currUsername , currPassword);
		
		Log.i("", "Admin List(by name+pw): "+adminList.toString());
		
		if (adminList != null && adminList.size() > 0) {
			
			adminList = new ArrayList<Object>();
			adminList.add(new Admin(currUsername , currPassword ,newadminr_name.getText().toString() ,newpassword.getText().toString()));
			admin_control.update(adminList);
			
			Log.i("Tag", "After Update Admin: " + admin_control.select().toString());
			
			finish();
		}else {
			SKToastMessage.showMessage(getApplicationContext(), "Invalid old username & old password!", SKToastMessage.ERROR);
		}
	}
	
	private void clearText()
	{
		curr_adminrname.getText().clear();
		curr_password.getText().clear();
		newadminr_name.getText().clear();
		newpassword.getText().clear();
		curr_adminrname.requestFocus();
	}
	
	public boolean checkFields() {
		
		if (curr_adminrname.getText().toString().length() == 0) {
			curr_adminrname.setError("Enter current username");
			return false;
		}
		if (curr_password.getText().toString().length() == 0) {
			curr_password.setError("Enter current password");
			return false;
		}
		if (newadminr_name.getText().toString().length() == 0) {
			newadminr_name.setError("Enter new username");
			return false;
		}
		if (newpassword.getText().toString().length() == 0) {
			newpassword.setError("Enter new password");
			return false;
		}
		return true;
	}
}
