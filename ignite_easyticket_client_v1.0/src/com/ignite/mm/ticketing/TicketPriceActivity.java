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
import com.ignite.mm.ticketing.custom.listview.adapter.TicketListAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.PriceList;

public class TicketPriceActivity extends SherlockActivity{
	
	private  ArrayList<PriceList> ticketList;
	private ListView lvticket;
	private com.actionbarsherlock.app.ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private String ShowId;
	private String ShowName , TicketTypeID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticket_list);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("SHOW");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle bundle=getIntent().getExtras();
		//TicketTypeID = bundle.getString("ticketTypeID");
		ShowId = bundle.getString("show_id");
		ShowName = bundle.getString("showname");
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
		ticketList=new ArrayList<PriceList>();
		ticketList.add(new PriceList("1","2500"));
		ticketList.add(new PriceList("1","4500"));
		ticketList.add(new PriceList("1","7500"));
		ticketList.add(new PriceList("1","10000"));
		ticketList.add(new PriceList("1","15000"));
		/*dbmanager = new DatabasePriceList(getActivity());
		ticketList = ((DatabasePriceList) dbmanager).getAllPriceList();*/
		//Log.i("menuList","Menu :" +menuList);
		lvticket = (ListView) findViewById(R.id.ticketList);
		lvticket.setAdapter(new TicketListAdapter(this, ticketList));
		lvticket.setOnItemClickListener(itemClickListener);
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
				Intent nextScreen = new Intent(TicketPriceActivity.this, ShowInfoActivity.class);
				Bundle bundle = new Bundle();
				//bundle.putString("ticketTypeId", TicketTypeID);
				bundle.putString("showid",ShowId);
				bundle.putString("showname",ShowName);
				bundle.putString("ticket_id",  ticketList.get(position).getID()); 
				bundle.putString("price", ticketList.get(position).getPrice());
				nextScreen.putExtras(bundle);
				startActivity(nextScreen);
			}
	};

}
