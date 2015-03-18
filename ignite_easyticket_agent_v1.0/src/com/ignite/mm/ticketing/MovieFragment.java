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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.ignite.mm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mm.ticketing.custom.listview.adapter.MovieListAdapter;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.controller.DatabaseBusDestination;
import com.ignite.mm.ticketing.sqlite.database.controller.DatabaseMovie;
import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.BusDestination;
import com.ignite.mm.ticketing.sqlite.database.model.MovieList;


public class MovieFragment extends SherlockFragment {
	private View rootView;
	private ListView lstMovie;
	private  ArrayList<MovieList> movieList;
	private DatabaseManager dbmanager;
	private String TicketTypeID;
	private ConnectionDetector connectionDetector;
	private LinearLayout mLoadingView;
	private LinearLayout mNoConnection;
	private boolean isLoaded;
	public static boolean mLoading = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		connectionDetector = new ConnectionDetector(getActivity());

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.movie_list, container, false); //**
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		lstMovie = (ListView) rootView.findViewById(R.id.movieList);
		/*mLoadingView = (LinearLayout) rootView.findViewById(R.id.ly_loading);
		mNoConnection = (LinearLayout) rootView.findViewById(R.id.no_internet);
		if(!isLoaded && connectionDetector.isConnectingToInternet()){
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadingView.startAnimation(topInAnimaiton());
			//getDestination();
		}
		
		if(!connectionDetector.isConnectingToInternet()){
			mNoConnection.setVisibility(View.VISIBLE);
			mNoConnection.startAnimation(topInAnimaiton());
		}*/
		//TicketTypeID = MainFragment.TicketTypeId;
		getData();
	}
	
	private Animation topInAnimaiton() {
		Animation anim = AnimationUtils.loadAnimation(getActivity(),
				R.anim.top_in);
		anim.reset();
		return anim;

	}

	private Animation topOutAnimaiton() {
		Animation anim = AnimationUtils.loadAnimation(getActivity(),
				R.anim.top_out);
		anim.reset();
		return anim;

	}
	
	/*private void getDestination() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ticketTypeId", TicketTypeID));
		//Log.i("","TicketeTypeID :" + params);
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				
				String jsonData = msg.getData().getString("data");
				//Log.i("ans:","Server Response: "+jsonData);
				try {
					JSONObject json = new JSONObject("{\"Array\":" + jsonData +"}");
					JSONArray arr = json.getJSONArray("Array");
					dbmanager = new DatabaseBusDestination(getActivity());
					//if(offset==1)
					//{
						((DatabaseBusDestination) dbmanager).deleteBusDes();
					//}
					
					for (int i = 0; i < arr.length(); i++) {

						JSONObject obj = arr.getJSONObject(i);
						
						//Add record to table
						((DatabaseBusDestination) dbmanager).add(
								new BusDestination(
										obj.getString("ticketDescriptionId"),
										obj.getString("ticketDescriptionName")
																				
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
	}	*/
	
	private void getData(){
		movieList=new ArrayList<MovieList>();
		movieList.add(new MovieList("1","OZ The Great And Powerful"));
		movieList.add(new MovieList("2","500 Days Of Summer"));
		movieList.add(new MovieList("3","Harry Potter And The Deadly Hollow Part II"));
		movieList.add(new MovieList("4","No String Attached"));
		movieList.add(new MovieList("5","၃၉ ဗိုက္ပူ"));
		/*dbmanager = new DatabaseMovie(getActivity());
		movieList = ((DatabaseMovie) dbmanager).getAllMovieList();	*/
		//Log.i("menuList","Menu :" +menuList);
		
		lstMovie.setAdapter(new MovieListAdapter(getActivity(), movieList));
		lstMovie.setOnItemClickListener(itemClickListener);
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Intent nextScreen = new Intent(getActivity(),MovieDateAndCinemaActivity.class);
			Bundle bundle = new Bundle();
			//bundle.putString("ticketTypeId", TicketTypeID);
			bundle.putString("movieid",  movieList.get(position).getID()); 
			bundle.putString("movietitle", movieList.get(position).getMovietitle());
			nextScreen.putExtras(bundle);
			startActivity(nextScreen);
					
			
		}

	};
}
