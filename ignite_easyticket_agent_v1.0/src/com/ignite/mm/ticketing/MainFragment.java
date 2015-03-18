package com.ignite.mm.ticketing;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
//import com.ignite.mm.ticketing.application.FontsTypeface;
import com.ignite.mm.ticketing.application.MyDevice;
import com.ignite.mm.ticketing.sherlock.tabhost.manager.FragmentTabManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainFragment extends SherlockFragmentActivity {
	public static ActionBar actionBar;
	private TabHost mTabHost;
	private FragmentTabManager mTabManager;
	private TabWidget mTabWidget;
	private int selectedTabId;
	public static final int SEARCH = 1;
	public static Menu menu;
	public static SearchView searchView;
	
	private MyDevice mDevice;
	public static TextView actionBarTitle;
	private ImageButton actionBarBack;
	//private FontsTypeface ft;
	public static Activity aty;
	public static String TicketTypeId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		aty = this;
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(R.id.action_bar_back);
		/*ft = new FontsTypeface(this);
		ft.setTypeface(actionBarTitle,  "bauer");*/
		//actionBarTitle.setTextSize(15);
		actionBarTitle.setText("HOME");
		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.fragment_main);
		
		mTabHost = (TabHost)findViewById(R.id.tabhost);
        mTabHost.setup();
           
                       
        mTabManager = new FragmentTabManager(this, mTabHost, R.id.tab1);

        View tabIndicator1   = LayoutInflater.from(this).inflate(R.layout.tab_indicator,mTabHost.getTabWidget(),false);
        ((TextView) tabIndicator1.findViewById(R.id.title_tab)).setText("HOME"); 
       // ft.setTypeface(((TextView) tabIndicator1.findViewById(R.id.title_tab)), "bauer");
        ((ImageView)tabIndicator1.findViewById(R.id.icon_tab)).setImageResource(R.drawable.home);
        
        mTabManager.addTab(mTabHost.newTabSpec("home").setIndicator(tabIndicator1), MenuFragment.class, null);
                    
        View tabIndicator2   = LayoutInflater.from(this).inflate(R.layout.tab_indicator,mTabHost.getTabWidget(),false);
        ((TextView) tabIndicator2.findViewById(R.id.title_tab)).setText("MOVIE"); 
       // ft.setTypeface(((TextView) tabIndicator2.findViewById(R.id.title_tab)),  "bauer");
        ((ImageView)tabIndicator2.findViewById(R.id.icon_tab)).setImageResource(R.drawable.tab_cinema);
                
        mTabManager.addTab(mTabHost.newTabSpec("movie").setIndicator(tabIndicator2), MovieFragment.class, null);
        
        View tabIndicator3   = LayoutInflater.from(this).inflate(R.layout.tab_indicator,mTabHost.getTabWidget(),false);
        ((TextView) tabIndicator3.findViewById(R.id.title_tab)).setText("BUS"); 
       // ft.setTypeface(((TextView) tabIndicator3.findViewById(R.id.title_tab)), "bauer");
        ((ImageView)tabIndicator3.findViewById(R.id.icon_tab)).setImageResource(R.drawable.tab_bus);
        
        mTabManager.addTab(mTabHost.newTabSpec("bus").setIndicator(tabIndicator3), BusFragment.class, null);
        
        View tabIndicator4   = LayoutInflater.from(this).inflate(R.layout.tab_indicator,mTabHost.getTabWidget(),false);
        ((TextView) tabIndicator4.findViewById(R.id.title_tab)).setText("SHOW"); 
      //  ft.setTypeface(((TextView) tabIndicator4.findViewById(R.id.title_tab)),  "bauer");
        ((ImageView)tabIndicator4.findViewById(R.id.icon_tab)).setImageResource(R.drawable.tab_show);
        
       mTabManager.addTab(mTabHost.newTabSpec("show").setIndicator(tabIndicator4), ShowFragment.class, null);
             
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));           
        }
        
        Bundle extra = getIntent().getExtras();
        selectedTabId = extra.getInt("Selected");
        mTabHost.setCurrentTab(selectedTabId);
        TicketTypeId = extra.getString("ticketTypeId");
        //Log.i("","TicketTypeID :"+ TicketTypeId);*/
        mTabWidget = (TabWidget)findViewById(android.R.id.tabs);
                       
        mDevice = new MyDevice(this);
		mTabWidget.getLayoutParams().height = (int)(mDevice.getHeight() / 12);  
		
	}
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
						
		}
	};
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MainFragment.menu = menu;
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		
		/*searchView = new SearchView(getSupportActionBar().getThemedContext());
	    searchView.setQueryHint("Search");*/

	    MainFragment.menu.add(0,SEARCH, 1, "Search")
	        .setIcon(R.drawable.search)
	        .setActionView(searchView)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	    
	    if(selectedTabId == 2)
	    {
	    	menu.findItem(SEARCH).setVisible(true);
	    }
	    else{
	    	menu.findItem(SEARCH).setVisible(false);
	    }
	    	return super.onCreateOptionsMenu(menu);
		
	};
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case SEARCH:
			 		startActivity(new Intent(MainFragment.this,BusSelectSeatActivity.class));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
		
}
