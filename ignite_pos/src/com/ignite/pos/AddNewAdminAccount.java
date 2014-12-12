package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.StringSpinnerAdapter;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Admin;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class AddNewAdminAccount extends SherlockActivity{

	private Button Save;
	private EditText admin_name , password;
	private DatabaseManager dbManager;
	private List<Object> adminList;
	private String str_adminname, str_adminpassword;
	private Spinner spn_user_level;
	private List<String> userLevelList;
	private String selectedUserLevel;
	private StringSpinnerAdapter spnAdapter;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_admin_account);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		admin_name = (EditText)findViewById(R.id.editText_admin_name);
		password = (EditText)findViewById(R.id.editText_admin_pass);
		spn_user_level = (Spinner) findViewById(R.id.spn_user_level);
		
		Save = (Button)findViewById(R.id.btnSave);
		Save.setOnClickListener(clickListener);
		
		spn_user_level.setOnItemSelectedListener(userLevelClickListener);
		
		getUserLevel();
		
		dbManager = new AdminController(AddNewAdminAccount.this);
		AdminController admin_control = (AdminController)dbManager;
		adminList = new ArrayList<Object>();
		adminList = admin_control.select();
		
		Log.i("", "Admin person : "+adminList.toString());
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == Save)
			{
				if (checkFields()) {
					
					if (adminList.size() > 0) {
						
						boolean isExist = false;
						
						for (int i = 0; i < adminList.size(); i++) {
							
							Admin admin = (Admin)adminList.get(i);
							
							if (admin_name.getText().toString().toLowerCase().equals(admin.getAdminname().toLowerCase())) {
								isExist = true;
								
								SKToastMessage.showMessage(getApplicationContext(), admin_name.getText().toString() +" is already exist!", SKToastMessage.WARNING);
								clearText();
							}
						}

						if (!isExist) {
							saveData();	
						}
					}else {
						saveData();	
					}
				}
			}
		}
	}; 
	
	private OnItemSelectedListener userLevelClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedUserLevel = userLevelList.get(arg2);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	

		
	private void getUserLevel()
	{
		userLevelList = new ArrayList<String>();
		userLevelList.add("Manager");
		userLevelList.add("Staff");
		
		spn_user_level.setAdapter(new StringSpinnerAdapter(this, userLevelList));
	}
	
	private void saveData()
	{
		dbManager = new AdminController(this);
		AdminController admin_control = (AdminController)dbManager;
		adminList = new ArrayList<Object>();
		adminList.add(new Admin( admin_name.getText().toString() , password.getText().toString(), selectedUserLevel));
		
		admin_control.save(adminList);
		
		Log.i("","Saving to Database :" + adminList.toString());
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Admin Added!", SKToastMessage.SUCCESS);
	}
	
	public boolean checkFields() {
		
		if (admin_name.getText().toString().length() == 0) {
			admin_name.setError("Enter Admin Name");
			return false;
		}
		if (password.getText().toString().length() == 0) {
			password.setError("Enter Admin Password");
			return false;
		}
		if (password.getText().toString().length() < 6) {
			password.setError("Enter six charactors - minimum");
			return false;
		}
		if (password.getText().toString().length() > 20) {
			password.setError("Enter twenty charactors - maximum");
			return false;
		}
		
		return true;
	}
	
	private void clearText()
	{
		admin_name.getText().clear();
		password.getText().clear();
		admin_name.requestFocus();
		
	}
	
}
