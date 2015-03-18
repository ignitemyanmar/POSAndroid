package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mm.ticketing.custom.listview.adapter.MovieTimeListAdapter;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.controller.DatabaseOperator;
import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.Operators;
import com.ignite.mm.ticketing.sqlite.database.model.MovieTime;

public class MovieTimeActivity extends SherlockActivity {

	private ArrayList<MovieTime> mTime;
	private ListView lstTime;
	private com.actionbarsherlock.app.ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private String Date_id;
	private String Date;
	private String Cinema_id;
	private String Cinema;
	private String Movie_id;
	private String Movie_title,TickettypeID;
	private DatabaseManager dbmanager;
	private String TicketTypeID;
	private ConnectionDetector connectionDetector;
	private LinearLayout mLoadingView;
	private LinearLayout mNoConnection;
	private boolean isLoaded;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_list);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("MOVIE");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

	/*	connectionDetector = new ConnectionDetector(getApplicationContext());		
		lstTime = (ListView) findViewById(R.id.listTime);
		mLoadingView = (LinearLayout) findViewById(R.id.ly_loading);
		mNoConnection = (LinearLayout) findViewById(R.id.no_internet);
		if(!isLoaded && connectionDetector.isConnectingToInternet()){
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadingView.startAnimation(topInAnimaiton());
			//getDeperature();
		}
		
		if(!connectionDetector.isConnectingToInternet()){
			mNoConnection.setVisibility(View.VISIBLE);
			mNoConnection.startAnimation(topInAnimaiton());
		}*/
		
		Bundle b = getIntent().getExtras();
		//TickettypeID = b.getString("ticketTypeId");
		Date_id = b.getString("date_id");
		Date = b.getString("date");
		Cinema_id = b.getString("cinema_id");
		Cinema = b.getString("cinemaName");
		Movie_id = b.getString("movie_id");
		Movie_title = b.getString("movie_title");

		getData();
	}

	private Animation topInAnimaiton() {
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.top_in);
		anim.reset();
		return anim;

	}

	private Animation topOutAnimaiton() {
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.top_out);
		anim.reset();
		return anim;

	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}

		}
	};

	/*private void getMovieTime() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ticketTypeId", TickettypeID));
		params.add(new BasicNameValuePair("ticketDescriptionId", BusDesID));
		params.add(new BasicNameValuePair("date", TripDate));
		//Log.i("","getdata :" + params);
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				
				String jsonData = msg.getData().getString("data");
				//Log.i("ans:","Server Response: "+jsonData);
				try {
					JSONObject json = new JSONObject("{\"Array\":" + jsonData +"}");
					JSONArray arr = json.getJSONArray("Array");
					dbmanager = new DatabaseAgent(getApplication());
					//if(offset==1)
					//{
						((DatabaseAgent) dbmanager).deleteAgentsList();
					//}
					
					for (int i = 0; i < arr.length(); i++) {

						JSONObject obj = arr.getJSONObject(i);
						
						//Add record to table
						((DatabaseAgent) dbmanager).add(
								new AgentsList(
										obj.getString("supplierId"),
										obj.getString("supplierName")
																				
										));
						if(i == 5){
							offset++;
							mLoadMore = true;
						}else{
							mLoadMore = false;
						}
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(!isLoaded){
						isLoaded = true;
						try {
							mLoadingView.startAnimation(topOutAnimaiton());
						} catch (NullPointerException e2) {
						}
					}
					mLoading = false;
					getData();
				}
				
			}
		};
		HttpConnection lt = new HttpConnection(handler,"GET", url,params);
		lt.execute();
	}*/
	
	private void getData() {
		mTime = new ArrayList<MovieTime>();
		mTime.add(new MovieTime("1", "10:30 AM MMT"));
		mTime.add(new MovieTime("2", "12:30 PM MMT"));
		mTime.add(new MovieTime("3", "2:30 PM MMT"));
		mTime.add(new MovieTime("4", "4:30 PM MMT"));
		mTime.add(new MovieTime("5", "6:30 PM MMT"));
		/*dbmanager = new DatabaseMovieTime(getApplication());
		mTime = ((DatabaseMovieTime) dbmanager).getAllMovieTime();*/
		// Log.i("menuList","Menu :" +menuList);
		lstTime = (ListView) findViewById(R.id.listTime);
		lstTime.setAdapter(new MovieTimeListAdapter(this, mTime));
		lstTime.setOnItemClickListener(itemClickListener);

	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Intent nextScreen = new Intent(MovieTimeActivity.this,
					SelectingSeatActivity.class);
			Bundle bundle = new Bundle();
			//bundle.putString("ticketTypeId", TickettypeID);
			bundle.putString("movie_id", Movie_id);
			bundle.putString("movie_title",Movie_title );
			bundle.putString("date_id", Date_id);
			bundle.putString("date", Date);
			bundle.putString("cinema_id", Cinema_id);
			bundle.putString("cinema", Cinema);
			bundle.putString("movie_time_id", mTime.get(position).getMovieId());
			bundle.putString("time", mTime.get(position).getMovieTime());
			nextScreen.putExtras(bundle);
			startActivity(nextScreen);

		}

	};
}
