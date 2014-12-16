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
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class UpdateSalePersonActivity extends SherlockActivity{

	private EditText curr_sp_name, curr_password, new_sp_name, newpassword;
	private Button btn_save;
	private DatabaseManager dbManager;
	public static String newUsername , newPassword , currUsername , currPassword;
	private List<Object> sp_list;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle btn_savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(btn_savedInstanceState);
		setContentView(R.layout.activity_update_sale_person);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		Bundle bundle = getIntent().getExtras();
		String oldUserName = bundle.getString("userName");
		
		Log.i("", "Old sp name"+oldUserName);
		
		curr_sp_name = (EditText)findViewById(R.id.editText_curr_username);
		
		if (oldUserName != null) {
			curr_sp_name.setText(oldUserName);
		}
		
		curr_password = (EditText)findViewById(R.id.editText_curr_password);
		new_sp_name = (EditText)findViewById(R.id.editText_new_username);
		newpassword = (EditText)findViewById(R.id.editText_new_password);
				
		btn_save = (Button)findViewById(R.id.btnSave);
		btn_save.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == btn_save)
				{
					if (checkFields()) {
						currUsername = curr_sp_name.getText().toString();
						currPassword = curr_password.getText().toString();
						updateData();
					}
					
				}
			}
		}; 
	
	private void updateData()
	{
		dbManager = new spSalePersonController(this);
		spSalePersonController sp_control = (spSalePersonController)dbManager;
		sp_list = new ArrayList<Object>();
		sp_list = sp_control.select(currUsername, currPassword);
		
		Log.i("", "Sale Person List(by name+pw): "+sp_list.toString());
		
		if (sp_list != null && sp_list.size() > 0) {
			
			sp_list = new ArrayList<Object>();
			sp_list.add(new spSalePerson(currUsername , currPassword ,new_sp_name.getText().toString() ,newpassword.getText().toString()));
			sp_control.update(sp_list);
			
			Log.i("Tag", "After Update_sale Person:  " + sp_control.select().toString());
			
			//SKToastMessage.showMessage(UpdateSalePersonActivity.this, "Update Success!", SKToastMessage.SUCCESS);
			
			finish();
		}else {
			SKToastMessage.showMessage(UpdateSalePersonActivity.this, "Invalid old username & old password!", SKToastMessage.ERROR);
		}
	}
	
	private void clearText()
	{
		curr_sp_name.getText().clear();
		curr_password.getText().clear();
		new_sp_name.getText().clear();
		newpassword.getText().clear();
		curr_sp_name.requestFocus();
	}
	
	public boolean checkFields() {
		
		if (curr_sp_name.getText().toString().length() == 0) {
			curr_sp_name.setError("Enter current username");
			return false;
		}
		if (curr_password.getText().toString().length() == 0) {
			curr_password.setError("Enter current password");
			return false;
		}
		if (new_sp_name.getText().toString().length() == 0) {
			new_sp_name.setError("Enter new username");
			return false;
		}
		if (newpassword.getText().toString().length() == 0) {
			newpassword.setError("Enter new password");
			return false;
		}
		return true;
	}
}

