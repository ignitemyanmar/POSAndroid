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
import com.ignite.mm.ticketing.custom.listview.adapter.MovieListAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.MovieList;

public class MovieListActivity extends SherlockActivity {
	private  ArrayList<MovieList> movieList;
	private ListView lstMovie;
	private com.actionbarsherlock.app.ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_list);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("MOVIE");
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
		movieList=new ArrayList<MovieList>();
		movieList.add(new MovieList("1","OZ The Great And Powerful"));
		movieList.add(new MovieList("2","500 Days Of Summer"));
		movieList.add(new MovieList("3","Harry Potter And The Deadly Hollow Part II"));
		movieList.add(new MovieList("4","No String Attached"));
		movieList.add(new MovieList("5","á�ƒá�‰ á€—á€­á€¯á€€á€¹á€•á€°"));
			
		//Log.i("menuList","Menu :" +menuList);
		lstMovie = (ListView) findViewById(R.id.movieList);
		lstMovie.setAdapter(new MovieListAdapter(this, movieList));
		lstMovie.setOnItemClickListener(itemClickListener);
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Intent nextScreen = new Intent(MovieListActivity.this, MovieDateAndCinemaActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("id",  movieList.get(position).getID()); 
			bundle.putString("movietitle", movieList.get(position).getMovietitle());
			startActivity(nextScreen);
					
			
		}

	};
}
