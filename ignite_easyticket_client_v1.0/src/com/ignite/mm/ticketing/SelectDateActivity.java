package com.ignite.mm.ticketing;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class SelectDateActivity extends SherlockActivity {
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	public static String TypeID,DesID,DesName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_calendar);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("BUS");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle bundle=getIntent().getExtras();
		TypeID =bundle.getString("ticketTypeId");
		DesID=bundle.getString("ticketDescriptionId");
		DesName = bundle.getString("ticketDescriptionName");
		
		final SKCalender skCalender = new SKCalender(this);
		
		  skCalender.setCallbacks(new Callbacks() {

		        public void onChooseDate(String chooseDate) {
		          // TODO Auto-generated method stub
		        	skCalender.dismiss();
		        }
		  });

		  skCalender.show();
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}

		}
	};
}
