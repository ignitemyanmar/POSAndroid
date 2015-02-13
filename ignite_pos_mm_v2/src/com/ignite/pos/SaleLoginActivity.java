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
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class SaleLoginActivity extends SherlockActivity{

	private Button Login;
	private EditText name , password;
	private DatabaseManager	dbManager;
	private List<Object> spList;
	private ScrollView scrollView;
	public static String strname , strpassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.sale_login_activity);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		name = (EditText)findViewById(R.id.editText_username);
		password = (EditText)findViewById(R.id.editText_password);
		
		Login = (Button)findViewById(R.id.btnLogin);
		Login.setOnClickListener(clickListener);
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == Login)
			{
				if (checkFieldsMM()) {
					strname = name.getText().toString();
					strpassword = password.getText().toString();
					
					getfromSalePerson();
				}
				
			}
		}
	};
	
	private void getfromSalePerson ()
	{
		dbManager = new spSalePersonController(this);
		spSalePersonController saleperson_control = (spSalePersonController)dbManager;
		spList = new ArrayList<Object>();
		spList = saleperson_control.select(strname, strpassword);
		
		Log.i("", "Sale Person List:" + spList.toString());
		
		if(spList != null && spList.size() > 0)
		{
			startActivity(new Intent(SaleLoginActivity.this,SaleActivity.class));	
			finish();
			
		}else {
			//SKToastMessage.showMessage(SaleLoginActivity.this, "Invalid username & password.", SKToastMessage.ERROR);
			SKToastMessage.showMessage(SaleLoginActivity.this, "Invalid username & password.", SKToastMessage.ERROR);
		}
	}
	
	private void clearText()
	{
		name.getText().clear();
		password.getText().clear();
		name.requestFocus();
	}
	
	public boolean checkFields() {
		if (name.getText().toString().length() == 0) {
			name.setError("Enter Username");
			return false;
		}
		if (password.getText().toString().length() == 0) {
			password.setError("Enter Password");
			return false;
		}
		
		return true;
	}
	
	public boolean checkFieldsMM() {
		if (name.getText().toString().length() == 0) {
			name.setError("အမည္႐ုိက္ထည့္ပါ");
			return false;
		}
		if (password.getText().toString().length() == 0) {
			password.setError("လ်ိဳ႕ ၀ွက္နံပါတ္႐ိုက္ထည့္ပါ");
			return false;
		}
		
		return true;
	}
}
