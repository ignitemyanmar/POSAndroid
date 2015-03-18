package com.ignite.mm.ticketing;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.ShowListAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.ShowList;

public class ShowListActivity extends SherlockActivity{
	
	private  ArrayList<ShowList> showList;
	private ListView lvShow;
	private com.actionbarsherlock.app.ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_list);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("SHOW");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		getData();
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}

		}
	};
	
	private void getData(){
		showList=new ArrayList<ShowList>();
		showList.add(new ShowList("1","The Big Bag In Show"));
					
		//Log.i("menuList","Menu :" +menuList);
		lvShow = (ListView) findViewById(R.id.listShow);
		lvShow.setAdapter(new ShowListAdapter(this, showList));
		lvShow.setOnItemClickListener(itemClickListener);
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
				Intent nextScreen = new Intent(ShowListActivity.this, TicketPriceActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("id",  showList.get(position).getShowID()); 
				bundle.putString("showname", showList.get(position).getShowName());
				startActivity(nextScreen);
			}
	};
}
