package com.ignite.pos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ignite.pos.application.MyDevice;
import com.ignite.pos.sherlock.tabhost.manager.FragmentTabManager;

public class MainFragment extends SherlockFragmentActivity {
	private TabHost mTabHost;
	private FragmentTabManager mTabManager;
	private TabWidget mTabWidget;
	private MyDevice mDevice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		mTabHost = (TabHost)findViewById(R.id.tabhost);
        mTabHost.setup();
        
        mTabManager = new FragmentTabManager(this, mTabHost, R.id.tab1);
        
        View tabIndicator1   = LayoutInflater.from(this).inflate(R.layout.tab_indicator,mTabHost.getTabWidget(),false);
        ((TextView) tabIndicator1.findViewById(R.id.title_tab)).setText("Admin"); 
        mTabManager.addTab(mTabHost.newTabSpec("Admin").setIndicator(tabIndicator1), LoginActivity.class, null);
                    
        View tabIndicator2   = LayoutInflater.from(this).inflate(R.layout.tab_indicator,mTabHost.getTabWidget(),false);
        ((TextView) tabIndicator2.findViewById(R.id.title_tab)).setText("Sales"); 
        mTabManager.addTab(mTabHost.newTabSpec("Sales").setIndicator(tabIndicator2), SaleActivity.class, null);
        
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));   
        }
        
        Bundle extra = getIntent().getExtras();
        mTabHost.setCurrentTab(extra.getInt("Selected"));
        
        mTabWidget = (TabWidget)findViewById(android.R.id.tabs);
        
        mDevice = new MyDevice(this);
		mTabWidget.getLayoutParams().height = (int)(mDevice.getHeight() / 12);  
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
}
