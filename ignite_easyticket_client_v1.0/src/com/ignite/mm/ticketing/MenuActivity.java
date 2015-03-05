package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.MenuListAdapter;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.controller.DatabaseMenu;
import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.BusDestination;
import com.ignite.mm.ticketing.sqlite.database.model.Menu;
import com.ignite.mm.ticketing.sqlite.database.model.ShowList;

public class MenuActivity extends SherlockActivity {
	private  List<Menu> menuList;
	private GridView lstMenu;
	private com.actionbarsherlock.app.ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_list);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setVisibility(View.INVISIBLE);
		actionBarTitle.setText("HOME");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getData();
		//getTicketType();
	}
	
	/*private void getTicketType() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				
				String jsonData = msg.getData().getString("data");
				Log.i("ans:","Server Response: "+jsonData);
				try {
					JSONObject json = new JSONObject("{\"Array\":" + jsonData +"}");
					JSONArray arr = json.getJSONArray("Array");
					dbmanager = new DatabaseMenuList(getApplication());
					//if(offset==1)
					//{
						((DatabaseMenuList) dbmanager).deleteMenuList();
					//}
					
					for (int i = 0; i < arr.length(); i++) {

						JSONObject obj = arr.getJSONObject(i);
						
						//Add record to table
						((DatabaseMenuList) dbmanager).add(
								new Menu(
										obj.getString("ticketTypeId"),
										obj.getString("ticketTypeName")
																				
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
	
	private void getData(){
		/*dbmanager = new DatabaseMenuList(getApplication());
		menuList = ((DatabaseMenuList) dbmanager).getAllMenuList();*/
		menuList=new ArrayList<Menu>();
		menuList.add(new Menu("2","MOVIE",R.drawable.movie));
		menuList.add(new Menu("1","BUS",R.drawable.bus));
		menuList.add(new Menu("3","SHOW",R.drawable.show));
		menuList.add(new Menu("4","OTHER",R.drawable.index));
		//Log.i("menuList","Menu :" +menuList);
		lstMenu = (GridView) findViewById(R.id.menuList);
		lstMenu.setAdapter(new MenuListAdapter(this, menuList));
		lstMenu.setOnItemClickListener(itemClickListener);
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position == 0){
				
			}
			else if(position==1)
			{
				startActivity(new Intent(getApplicationContext(), BusTripsCityActivity.class));
			}
			else if(position==2)
			{
				Bundle bundle = new Bundle();
				bundle.putInt("Selected",3);
				Intent i = new Intent(MenuActivity.this, MainFragment.class).putExtras(bundle);
				//bundle.putString("ticketTypeId", menuList.get(position).getTicketTypeId());
				i.putExtras(bundle);
				startActivity(i);
			}
			else if(position==3)
			{
				/*Intent nextScreen = new Intent(MenuActivity.this, OtherListActivity.class);
				startActivity(nextScreen);*/
			}
					
			
		}

	};

}
	

