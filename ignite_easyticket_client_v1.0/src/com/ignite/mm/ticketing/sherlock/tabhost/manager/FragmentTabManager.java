package com.ignite.mm.ticketing.sherlock.tabhost.manager;

import java.util.HashMap;

import com.ignite.mm.ticketing.MainFragment;
import com.ignite.mm.ticketing.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * This is a helper class that implements a generic mechanism for
 * associating fragments with the tabs in a tab host.  It relies on a
 * trick.  Normally a tab host has a simple API for supplying a View or
 * Intent that each tab will show.  This is not sufficient for switching
 * between fragments.  So instead we make the content part of the tab host
 * 0dp high (it is not shown) and the TabManager supplies its own dummy
 * view to show as the tab content.  It listens to changes in tabs, and takes
 * care of switch to the correct fragment shown in a separate content area
 * whenever the selected tab changes.
 */
public class FragmentTabManager implements TabHost.OnTabChangeListener {
    private final FragmentActivity mActivity;
    private final TabHost mTabHost;
    private final int mContainerId;
    private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
    TabInfo mLastTab;

    final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
        	v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    public FragmentTabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
        mActivity = activity;
        mTabHost = tabHost;
        mContainerId = containerId;
        mTabHost.setOnTabChangedListener(this);
    }

    public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
        tabSpec.setContent(new DummyTabFactory(mActivity));
        String tag = tabSpec.getTag();

        TabInfo info = new TabInfo(tag, clss, args);

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
        if (info.fragment != null && !info.fragment.isDetached()) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            ft.detach(info.fragment);
            ft.commit();
        }
        
        mTabs.put(tag, info);
        mTabHost.addTab(tabSpec);
        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
        {
        	mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#F2F2F2"));
        	TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.title_tab);
            tv.setTextColor(Color.parseColor("#000000"));
                
        }
        TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).findViewById(R.id.title_tab);
        tv.setTextColor(Color.parseColor("#FF8C26"));
        //mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab_indicator_selected);// selected       
              
    }

    public void onTabChanged(String tabId) {
        TabInfo newTab = mTabs.get(tabId);
        if (mLastTab != newTab) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(mActivity,
                            newTab.clss.getName(), newTab.args);
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }
            for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
            {
            	mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#F2F2F2"));
            	TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.title_tab);
                tv.setTextColor(Color.parseColor("#133d49"));
               
            }
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).findViewById(R.id.title_tab);
            tv.setTextColor(Color.parseColor("#FF8C26"));
            //mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab_indicator_selected);// selected   
            
            mTabHost.getTabWidget().setDividerDrawable(null);
            mLastTab = newTab;
            ft.commit();
            
            if (mTabHost.getCurrentTab() == 0) {
            	MainFragment.actionBarTitle.setText("HOME");
            	           	            	
            }
            if (mTabHost.getCurrentTab() == 1) {
            	MainFragment.actionBarTitle.setText("MOVIE");
            	if(MainFragment.menu != null){
              		MainFragment.menu.findItem(MainFragment.SEARCH).setVisible(false);
    			}
            }
            if (mTabHost.getCurrentTab() == 2) {
              	MainFragment.actionBarTitle.setText("BUS");
              	if(MainFragment.menu != null){
              		MainFragment.menu.findItem(MainFragment.SEARCH).setVisible(true);
    			}
            }
            if (mTabHost.getCurrentTab() == 3) {
            	MainFragment.actionBarTitle.setText("SHOW");
            	if(MainFragment.menu != null){
              		MainFragment.menu.findItem(MainFragment.SEARCH).setVisible(false);
    			}
            }
           
            mActivity.getSupportFragmentManager().executePendingTransactions();
        }
    }
}

