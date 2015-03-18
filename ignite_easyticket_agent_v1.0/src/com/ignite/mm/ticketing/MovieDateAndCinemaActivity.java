package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.ExpandableListViewAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.MovieCinema;
import com.ignite.mm.ticketing.sqlite.database.model.MovieDate;

public class MovieDateAndCinemaActivity extends SherlockActivity{
	
	List<MovieDate> movieDate;
	HashMap<Integer,ArrayList<MovieCinema>> movieCinema;
	ExpandableListView expListView;
	private com.actionbarsherlock.app.ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private String MovieID,TicketTypeID,MovieTitle;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moviedate_expandlist);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("MOVIE");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle bdle = getIntent().getExtras();
		//TicketTypeID= bdle.getString("ticketTypeId");
		MovieID = bdle.getString("movieid");
		MovieTitle = bdle.getString("movietitle");
		
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
		movieDate = new ArrayList<MovieDate>();
		
	    // Adding parent data
	    movieDate.add(new MovieDate("1","Friday - 30 JAN '14"));
	    movieDate.add(new MovieDate("2","SAT - 1 FEB '14"));
	    movieDate.add(new MovieDate("3","Sunday - 2 FEB '14"));
	    movieDate.add(new MovieDate("4","Thursday - 6 FEB '14"));
	    movieDate.add(new MovieDate("5","Friday - 7 FEB '14"));
	    
	    // Adding child data
        ArrayList<MovieCinema> friday30 = new ArrayList<MovieCinema>();
        friday30.add(new MovieCinema("1","Mingalar Cinema"));
        friday30.add(new MovieCinema("2","Junction Cineplex"));
        friday30.add(new MovieCinema("3","Shay Saung Cinema"));
        
        ArrayList<MovieCinema> sat1 = new ArrayList<MovieCinema>();
        sat1.add(new MovieCinema("1","Junction Mawtin Cineplex"));
        sat1.add(new MovieCinema("2","Mingalar Cinema"));
        sat1.add(new MovieCinema("3","Junction Cineplex"));
        
        ArrayList<MovieCinema> sunday2 = new ArrayList<MovieCinema>();
        sunday2.add(new MovieCinema("1","Mingalar Cinema"));
        sunday2.add(new MovieCinema("2","Shay Saung Cinema"));
      
        ArrayList<MovieCinema> thursday6 = new ArrayList<MovieCinema>();
        thursday6.add(new MovieCinema("1","Junction Mawtin Cineplex"));
        thursday6.add(new MovieCinema("2","Mingalar Cinema"));
               
        ArrayList<MovieCinema> friday7 = new ArrayList<MovieCinema>();
        friday7.add(new MovieCinema("1","Junction Mawtin Cineplex"));
        friday7.add(new MovieCinema("2","Mingalar Cinema"));
        friday7.add(new MovieCinema("3","Junction Cineplex"));
        friday7.add(new MovieCinema("4","Shay Saung Cinema"));
        
        movieCinema = new HashMap<Integer, ArrayList<MovieCinema>>();
        
        movieCinema.put(0, friday30); // Header, Child data
        movieCinema.put(1, sat1);
        movieCinema.put(2, sunday2);
        movieCinema.put(3, thursday6);
        movieCinema.put(4, friday7);
           
        //Log.i("menuList","Movie :" +movieCinema.get(0).get(0).getCinemaName());
        expListView = (ExpandableListView) findViewById(R.id.lvExpand);
        expListView.setAdapter(new ExpandableListViewAdapter(this, movieDate, movieCinema));
        expListView.setOnChildClickListener(childClickListener);
        expListView.setGroupIndicator(null);
        
	}
	
	private OnChildClickListener childClickListener = new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v,
                int groupPosition, int childPosition, long id) {
			
			Intent nextScreen = new Intent(MovieDateAndCinemaActivity.this, MovieTimeActivity.class);
			Bundle bundle = new Bundle();
			//bundle.putString("ticketTypeId", TicketTypeID);
			bundle.putString("movie_id", MovieID);
			bundle.putString("movie_title",MovieTitle);
			bundle.putString("date_id", movieDate.get(groupPosition).getId());
			bundle.putString("date", movieDate.get(groupPosition).getDate());
			bundle.putString("cinema_id", movieCinema.get(groupPosition).get(childPosition).getId()); 
			bundle.putString("cinemaName", movieCinema.get(groupPosition).get(childPosition).getCinemaName());
			nextScreen.putExtras(bundle);
			startActivity(nextScreen);
			return false;
			
		}

	};
	
}
