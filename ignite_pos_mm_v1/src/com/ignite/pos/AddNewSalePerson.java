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
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class AddNewSalePerson extends SherlockActivity{

	private Button Save;
	private EditText username , password;
	private DatabaseManager dbManager;
	private List<Object> salePerson;
	private String str_username, str_password;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_sale_person);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		username = (EditText)findViewById(R.id.editText_username);
		password = (EditText)findViewById(R.id.editText_password);
		
		Save = (Button)findViewById(R.id.btnSave);
		Save.setOnClickListener(clickListener);
		
		//Check Sale Person for Duplicate
		dbManager = new spSalePersonController(this);
		spSalePersonController salePerson_control = (spSalePersonController)dbManager;
		salePerson = new ArrayList<Object>();
		salePerson = salePerson_control.select();
		
		Log.i("", "sale person All: "+salePerson.toString());
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == Save)
			{
				if (checkFields()) {
					str_username = username.getText().toString();
					str_password = password.getText().toString();
					
					//Check Duplicate 
					if (salePerson.size() > 0) {
						
						boolean isExist = false;
						
						for (int i = 0; i < salePerson.size(); i++) {
							
							spSalePerson sp = (spSalePerson)salePerson.get(i);
							
							if (str_username.toLowerCase().equals(sp.getSpusername().toLowerCase())) {
								
								isExist = true;
								
								SKToastMessage.showMessage(getApplicationContext(), str_username +" is already exist!", SKToastMessage.WARNING);
								clearText();
								
								break;
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
	
	private void saveData()
	{
		dbManager = new spSalePersonController(this);
		spSalePersonController salePerson_control = (spSalePersonController)dbManager;
		salePerson = new ArrayList<Object>();
		salePerson.add(new spSalePerson(str_username , str_password));
		
		salePerson_control.save(salePerson);
		
		Log.i("","Saving to Database :" + salePerson.toString());
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Sale Person saved!", SKToastMessage.SUCCESS);
	}
	
	private void clearText()
	{
		username.getText().clear();
		password.getText().clear();
		username.requestFocus();
		
	}
	
	public boolean checkFields() {
		
		if (username.getText().toString().length() == 0) {
			username.setError("Enter Username");
			return false;
		}
		if (password.getText().toString().length() == 0) {
			password.setError("Enter Password");
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
