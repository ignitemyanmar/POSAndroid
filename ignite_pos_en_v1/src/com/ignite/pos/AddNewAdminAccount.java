package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Admin;
import com.smk.skalertmessage.SKToastMessage;

public class AddNewAdminAccount extends SherlockActivity{

	private Button Save;
	private EditText admin_name , password;
	private DatabaseManager dbManager;
	private List<Object> adminList;
	private String str_adminname, str_adminpassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_admin_account);
		
		admin_name = (EditText)findViewById(R.id.editText_admin_name);
		password = (EditText)findViewById(R.id.editText_admin_pass);
		
		Save = (Button)findViewById(R.id.btnSave);
		Save.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == Save)
			{
				if (checkFields()) {
					saveData();
				}
			}
		}
	}; 
	
	private void saveData()
	{
		dbManager = new AdminController(this);
		AdminController admin_control = (AdminController)dbManager;
		adminList = new ArrayList<Object>();
		adminList.add(new Admin( admin_name.getText().toString() , password.getText().toString()));
		
		if (adminList != null && adminList.size() > 0) {
			
			List<Object> list = new ArrayList<Object>();
			list = admin_control.select();
			
			if (list != null && list.size() > 0) {
				for(Object obj:list){
					Admin admin = (Admin) obj;
					
					Log.i("","Admin List in DB : "+admin.toString());
					
					if (admin.getAdminname().equals(admin_name.getText().toString())) {
						SKToastMessage.showMessage(getApplicationContext(), "Your email is already exist!", SKToastMessage.ERROR);
					}else {
						admin_control.save(adminList);
						finish();
					}
				}
			}else {
				admin_control.save(adminList);
				finish();
			}
			
			
		}
		
		
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
	
}
