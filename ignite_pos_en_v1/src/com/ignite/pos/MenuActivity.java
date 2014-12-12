package com.ignite.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class MenuActivity extends SherlockActivity{
	
	private Button admin , sale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		admin = (Button)findViewById(R.id.btnAdmin);
		sale = (Button)findViewById(R.id.btnSale);
		
		admin.setOnClickListener(clickListener);
		sale.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == admin) {
				startActivity(new Intent(MenuActivity.this,LoginActivity.class));
			}
			if(v == sale)
			{
				startActivity(new Intent(MenuActivity.this,SaleLoginActivity.class));
			}
		}
	};
}
