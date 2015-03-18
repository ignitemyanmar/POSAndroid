package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.ignite.mm.ticketing.application.MyDevice;
import com.ignite.mm.ticketing.custom.listview.adapter.MoviePriceAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.SeatAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Seat;

public class SelectingSeatActivity extends SherlockActivity {

	public static List<Seat> Seat_List;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack, actionBarPrice,upDown;

	private LinearLayout mMenuContainer;
	private ListView mMenuList;
	private GridView mSeat;
	private MyDevice myDevice;
	private String MovieID, TicketTypeId;
	private String MovieTitle;
	private String DateID;
	private String Date;
	private String CinemaID;
	private String Cinema;
	private String MovieTimeID;
	
	private String Time;
	private List<Seat> CoupleSeat_List;
	public static List<Seat> first_seat;
	
	public static int[][] couple_list;
	static boolean menuClicked = false;
	public static Menu mMenu;
	public static String SelectedSeat = "";
	public static String SelectedPriceValue = "";
	private static String[] firstSeatprices = { "1000", "1500", "2000", "2500" ,"2700","3000","3500"};
	private static String[] secondSeatPrices={"4500","6000"};
	public static String DownpriceValue = null;
	public static String UppriceValue=null;
	Boolean fistSeat = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seat_list);

		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarTitle.setText("MOVIE");

		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		myDevice = new MyDevice(this);
		mMenuContainer = (LinearLayout) findViewById(R.id.menu_container);
		mMenuContainer.setTranslationY(myDevice.getHeight());
		mMenuList = (ListView) findViewById(R.id.lvPrice);
		mMenuList.setAdapter(new MoviePriceAdapter(SelectingSeatActivity.this,
				firstSeatprices));
		//mMenuList.setAdapter(new MoviePriceAdapter(SelectingSeatActivity.this,secondSeatPrices));
		mMenuList.setOnItemClickListener(itemClickListener);

		Bundle bundle = getIntent().getExtras();
		//TicketTypeId = bundle.getString("ticketTypeId");
		MovieID = bundle.getString("movie_id");
		MovieTitle = bundle.getString("movie_title");
		DateID = bundle.getString("date_id");
		Date = bundle.getString("date");
		CinemaID = bundle.getString("cinema_id");
		Cinema = bundle.getString("cinema");
		MovieTimeID = bundle.getString("movie_time_id");
		Time = bundle.getString("time");
		getData();
		SelectedSeat = "";

	}

	private void getData() {
		
		Seat_List = new ArrayList<Seat>();
		//price 1000KS
		Seat_List.add(new Seat("1", "A01",  "1000", "0"));
		Seat_List.add(new Seat("2", "A02",  "1000", "0"));
		Seat_List.add(new Seat("3", "A03",  "1000", "0"));
		Seat_List.add(new Seat("4", "A04",  "1000", "0"));
		Seat_List.add(new Seat("5", "A05",  "1000", "0"));
		Seat_List.add(new Seat("6", "A06",  "1000", "0"));
		Seat_List.add(new Seat("7", "A07",  "1000", "0"));
		Seat_List.add(new Seat("8", "A08",  "1000", "3"));
		Seat_List.add(new Seat("9", "A09",  "1000", "0"));
		Seat_List.add(new Seat("10", "A10", "1000", "0"));
		Seat_List.add(new Seat("11", "A11", "1000", "0"));
		Seat_List.add(new Seat("12", "A12", "1000", "0"));
		Seat_List.add(new Seat("13", "A13", "1000", "0"));
		Seat_List.add(new Seat("14", "A14", "1000", "0"));
		Seat_List.add(new Seat("15", "A15", "1000", "0"));
		
		Seat_List.add(new Seat("16", "B01", "1000", "0"));
		Seat_List.add(new Seat("17", "B02", "1000", "0"));
		Seat_List.add(new Seat("18", "B03", "1000", "0"));
		Seat_List.add(new Seat("19", "B04", "1000", "0"));
		Seat_List.add(new Seat("20", "B05", "1000", "0"));
		Seat_List.add(new Seat("21", "B06", "1000", "0"));
		Seat_List.add(new Seat("22", "B07", "1000", "0"));
		Seat_List.add(new Seat("23", "B08", "1000", "3"));
		Seat_List.add(new Seat("24", "B09", "1000", "0"));
		Seat_List.add(new Seat("25", "B10", "1000", "0"));
		Seat_List.add(new Seat("26", "B11", "1000", "0"));
		Seat_List.add(new Seat("27", "B12", "1000", "0"));
		Seat_List.add(new Seat("28", "B13", "1000", "0"));
		Seat_List.add(new Seat("29", "B14", "1000", "0"));
		Seat_List.add(new Seat("30", "B15", "1000", "0"));
		
		//price 1500KS
		Seat_List.add(new Seat("31", "C01", "1500", "0"));
		Seat_List.add(new Seat("32", "C02", "1500", "0"));
		Seat_List.add(new Seat("33", "C03", "1500", "0"));
		Seat_List.add(new Seat("34", "C04", "1500", "0"));
		Seat_List.add(new Seat("35", "C05", "1500", "0"));
		Seat_List.add(new Seat("36", "C06", "1500", "0"));
		Seat_List.add(new Seat("37", "C07", "1500", "0"));
		Seat_List.add(new Seat("38", "C08", "1500", "3"));
		Seat_List.add(new Seat("39", "C09", "1500", "0"));
		Seat_List.add(new Seat("40", "C10", "1500", "0"));
		Seat_List.add(new Seat("41", "C11", "1500", "0"));
		Seat_List.add(new Seat("42", "C12", "1500", "0"));
		Seat_List.add(new Seat("43", "C13", "1500", "0"));
		Seat_List.add(new Seat("44", "C14", "1500", "0"));
		Seat_List.add(new Seat("45", "C15", "1500", "0"));
		
		Seat_List.add(new Seat("46", "D01", "1500", "0"));
		Seat_List.add(new Seat("47", "D02", "1500", "0"));
		Seat_List.add(new Seat("48", "D03", "1500", "0"));
		Seat_List.add(new Seat("49", "D04", "1500", "0"));
		Seat_List.add(new Seat("50", "D05", "1500", "0"));
		Seat_List.add(new Seat("51", "D06", "1500", "0"));
		Seat_List.add(new Seat("52", "D07", "1500", "0"));
		Seat_List.add(new Seat("53", "D08", "1500", "3"));
		Seat_List.add(new Seat("54", "D09", "1500", "0"));
		Seat_List.add(new Seat("55", "D10", "1500", "0"));
		Seat_List.add(new Seat("56", "D11", "1500", "0"));
		Seat_List.add(new Seat("57", "D12", "1500", "0"));
		Seat_List.add(new Seat("58", "D13", "1500", "0"));
		Seat_List.add(new Seat("59", "D14", "1500", "0"));
		Seat_List.add(new Seat("60", "D15", "1500", "0"));
		
		//price 2000KS
		Seat_List.add(new Seat("61", "E01", "2000", "0"));
		Seat_List.add(new Seat("62", "E02", "2000", "0"));
		Seat_List.add(new Seat("63", "E03", "2000", "0"));
		Seat_List.add(new Seat("64", "E04", "2000", "0"));
		Seat_List.add(new Seat("65", "E05", "2000", "0"));
		Seat_List.add(new Seat("66", "E06", "2000", "0"));
		Seat_List.add(new Seat("67", "E07", "2000", "0"));
		Seat_List.add(new Seat("68", "E08", "2000", "3"));
		Seat_List.add(new Seat("69", "E09", "2000", "0"));
		Seat_List.add(new Seat("70", "E10", "2000", "0"));
		Seat_List.add(new Seat("71", "E11", "2000", "0"));
		Seat_List.add(new Seat("72", "E12", "2000", "0"));
		Seat_List.add(new Seat("73", "E13", "2000", "0"));
		Seat_List.add(new Seat("74", "E14", "2000", "0"));
		Seat_List.add(new Seat("75", "E15", "2000", "0"));
		
		Seat_List.add(new Seat("76", "F01", "2000", "0"));
		Seat_List.add(new Seat("77", "F02", "2000", "0"));
		Seat_List.add(new Seat("78", "F03", "2000", "0"));
		Seat_List.add(new Seat("79", "F04", "2000", "0"));
		Seat_List.add(new Seat("80", "F05", "2000", "0"));
		Seat_List.add(new Seat("81", "F06", "2000", "0"));
		Seat_List.add(new Seat("82", "F07", "2000", "0"));
		Seat_List.add(new Seat("83", "F08", "2000", "3"));
		Seat_List.add(new Seat("84", "F09", "2000", "0"));
		Seat_List.add(new Seat("85", "F10", "2000", "0"));
		Seat_List.add(new Seat("86", "F11", "2000", "0"));
		Seat_List.add(new Seat("87", "F12", "2000", "0"));
		Seat_List.add(new Seat("88", "F13", "2000", "0"));
		Seat_List.add(new Seat("89", "F14", "2000", "0"));
		Seat_List.add(new Seat("90", "F15", "2000", "0"));
		
		//price 2500KS
		Seat_List.add(new Seat("91", "G01", "2500", "0"));
		Seat_List.add(new Seat("92", "G02", "2500", "0"));
		Seat_List.add(new Seat("93", "G03", "2500", "0"));
		Seat_List.add(new Seat("94", "G04", "2500", "0"));
		Seat_List.add(new Seat("95", "G05", "2500", "0"));
		Seat_List.add(new Seat("96", "G06", "2500", "0"));
		Seat_List.add(new Seat("97", "G07", "2500", "0"));
		Seat_List.add(new Seat("98", "G08", "2500", "3"));
		Seat_List.add(new Seat("99", "G09", "2500", "0"));
		Seat_List.add(new Seat("100","G10", "2500", "0"));
		Seat_List.add(new Seat("101","G11", "2500", "0"));
		Seat_List.add(new Seat("102","G12", "2500", "0"));
		Seat_List.add(new Seat("103","G13", "2500", "0"));
		Seat_List.add(new Seat("104","G14", "2500", "0"));
		Seat_List.add(new Seat("105","G15", "2500", "0"));
		
		Seat_List.add(new Seat("106", "H01", "2500", "0"));
		Seat_List.add(new Seat("107", "H02", "2500", "0"));
		Seat_List.add(new Seat("108", "H03", "2500", "0"));
		Seat_List.add(new Seat("109", "H04", "2500", "0"));
		Seat_List.add(new Seat("110", "H05", "2500", "0"));
		Seat_List.add(new Seat("111", "H06", "2500", "0"));
		Seat_List.add(new Seat("112", "H07", "2500", "0"));
		Seat_List.add(new Seat("113", "H08", "2500", "3"));
		Seat_List.add(new Seat("114", "H09", "2500", "0"));
		Seat_List.add(new Seat("115", "H10", "2500", "0"));
		Seat_List.add(new Seat("116", "H11", "2500", "0"));
		Seat_List.add(new Seat("117", "H12", "2500", "0"));
		Seat_List.add(new Seat("118", "H13", "2500", "0"));
		Seat_List.add(new Seat("119", "H14", "2500", "0"));
		Seat_List.add(new Seat("120", "H15", "2500", "0"));
		
		//price 2700KS
		Seat_List.add(new Seat("121", "I01", "2700", "0"));
		Seat_List.add(new Seat("122", "I02", "2700", "0"));
		Seat_List.add(new Seat("123", "I03", "2700", "0"));
		Seat_List.add(new Seat("124", "I04", "2700", "0"));
		Seat_List.add(new Seat("125", "I05", "2700", "0"));
		Seat_List.add(new Seat("126", "I06", "2700", "0"));
		Seat_List.add(new Seat("127", "I07", "2700", "0"));
		Seat_List.add(new Seat("128", "I08", "2700", "3"));
		Seat_List.add(new Seat("129", "I09", "2700", "0"));
		Seat_List.add(new Seat("130", "I10", "2700", "0"));
		Seat_List.add(new Seat("131", "I11", "2700", "0"));
		Seat_List.add(new Seat("132", "I12", "2700", "0"));
		Seat_List.add(new Seat("133", "I13", "2700", "0"));
		Seat_List.add(new Seat("134", "I14", "2700", "0"));
		Seat_List.add(new Seat("135", "I15", "2700", "0"));
		
		Seat_List.add(new Seat("136", "J01", "2700", "0"));
		Seat_List.add(new Seat("137", "J02", "2700", "0"));
		Seat_List.add(new Seat("138", "J03", "2700", "0"));
		Seat_List.add(new Seat("139", "J04", "2700", "0"));
		Seat_List.add(new Seat("140", "J05", "2700", "0"));
		Seat_List.add(new Seat("141", "J06", "2700", "0"));
		Seat_List.add(new Seat("142", "J07", "2700", "0"));
		Seat_List.add(new Seat("143", "J08", "2700", "3"));
		Seat_List.add(new Seat("144", "J09", "2700", "0"));
		Seat_List.add(new Seat("145", "J10", "2700", "0"));
		Seat_List.add(new Seat("146", "J11", "2700", "0"));
		Seat_List.add(new Seat("147", "J12", "2700", "0"));
		Seat_List.add(new Seat("148", "J13", "2700", "0"));
		Seat_List.add(new Seat("149", "J14", "2700", "0"));
		Seat_List.add(new Seat("150", "J15", "2700", "0"));
		
		//price 3000KS
		Seat_List.add(new Seat("151", "K01", "3000", "0"));
		Seat_List.add(new Seat("152", "K02", "3000", "0"));
		Seat_List.add(new Seat("153", "K03", "3000", "0"));
		Seat_List.add(new Seat("154", "K04", "3000", "0"));
		Seat_List.add(new Seat("155", "K05", "3000", "0"));
		Seat_List.add(new Seat("156", "K06", "3000", "0"));
		Seat_List.add(new Seat("157", "K07", "3000", "0"));
		Seat_List.add(new Seat("158", "K08", "3000", "3"));
		Seat_List.add(new Seat("159", "K09", "3000", "0"));
		Seat_List.add(new Seat("160", "K10", "3000", "0"));
		Seat_List.add(new Seat("161", "K11", "3000", "0"));
		Seat_List.add(new Seat("162", "K12", "3000", "0"));
		Seat_List.add(new Seat("163", "K13", "3000", "0"));
		Seat_List.add(new Seat("164", "K14", "3000", "0"));
		Seat_List.add(new Seat("165", "K15", "3000", "0"));
		
		Seat_List.add(new Seat("166", "L01", "3000", "0"));
		Seat_List.add(new Seat("167", "L02", "3000", "0"));
		Seat_List.add(new Seat("168", "L03", "3000", "0"));
		Seat_List.add(new Seat("169", "L04", "3000", "0"));
		Seat_List.add(new Seat("170", "L05", "3000", "0"));
		Seat_List.add(new Seat("171", "L06", "3000", "0"));
		Seat_List.add(new Seat("1á�‡á�‡á�‡á�‡á�‡á�‡á�‡72", "L07", "3000", "0"));
		Seat_List.add(new Seat("173", "L08", "3000", "3"));
		Seat_List.add(new Seat("174", "L09", "3000", "0"));
		Seat_List.add(new Seat("175", "L10", "3000", "0"));
		Seat_List.add(new Seat("176", "L11", "3000", "0"));
		Seat_List.add(new Seat("177", "L12", "3000", "0"));
		Seat_List.add(new Seat("178", "L13", "3000", "0"));
		Seat_List.add(new Seat("179", "L14", "3000", "0"));
		Seat_List.add(new Seat("180", "L15", "3000", "0"));
		
		//Couple Seat
		Seat_List.add(new Seat("181", "M01", "3500", "4"));
		Seat_List.add(new Seat("182", "M02", "3500", "4"));
		Seat_List.add(new Seat("183", "M03", "3500", "4"));
		Seat_List.add(new Seat("184", "M04", "3500", "4"));
		Seat_List.add(new Seat("185", "M05", "3500", "4"));
		Seat_List.add(new Seat("186", "M06", "3500", "4"));
		Seat_List.add(new Seat("187", "M07", "3500", "3"));
		Seat_List.add(new Seat("188", "M08", "3500", "3"));
		Seat_List.add(new Seat("189", "M09", "3500", "3"));
		Seat_List.add(new Seat("190", "M10", "3500", "4"));
		Seat_List.add(new Seat("191", "M11", "3500", "4"));
		Seat_List.add(new Seat("192", "M12", "3500", "4"));
		Seat_List.add(new Seat("193", "M13", "3500", "4"));
		Seat_List.add(new Seat("194", "M14", "3500", "4"));
		Seat_List.add(new Seat("195", "M15", "3500", "4"));
		
		//First Floor
		first_seat = new ArrayList<Seat>();
		first_seat.add(new Seat("196", "N01", "4500", "0"));
		first_seat.add(new Seat("197", "N02", "4500", "0"));
		first_seat.add(new Seat("198", "N03", "4500", "0"));
		first_seat.add(new Seat("199", "N04", "4500", "0"));
		first_seat.add(new Seat("200", "N05", "4500", "3"));
		first_seat.add(new Seat("201", "N06", "4500", "0"));
		first_seat.add(new Seat("202", "N07", "4500", "0"));
		first_seat.add(new Seat("203", "N08", "4500", "0"));
		first_seat.add(new Seat("204", "N09", "4500", "0"));
		first_seat.add(new Seat("205", "N10", "4500", "3"));
		first_seat.add(new Seat("206", "N11", "4500", "0"));
		first_seat.add(new Seat("207", "N12", "4500", "0"));
		first_seat.add(new Seat("208", "N13", "4500", "0"));
		first_seat.add(new Seat("209", "N14", "4500", "0"));
		first_seat.add(new Seat("210", "N15", "4500", "3"));
		
		first_seat.add(new Seat("211", "O01", "6000", "0"));
		first_seat.add(new Seat("212", "O02", "6000", "0"));
		first_seat.add(new Seat("213", "O03", "6000", "0"));
		first_seat.add(new Seat("214", "O04", "6000", "0"));
		first_seat.add(new Seat("215", "O05", "6000", "3"));
		first_seat.add(new Seat("216", "O06", "6000", "0"));
		first_seat.add(new Seat("217", "O07", "6000", "0"));
		first_seat.add(new Seat("218", "O08", "6000", "0"));
		first_seat.add(new Seat("219", "O09", "6000", "0"));
		first_seat.add(new Seat("220", "O10", "6000", "3"));
		first_seat.add(new Seat("221", "O11", "6000", "0"));
		first_seat.add(new Seat("222", "O12", "6000", "0"));
		first_seat.add(new Seat("223", "O13", "6000", "0"));
		first_seat.add(new Seat("224", "O14", "6000", "0"));
		first_seat.add(new Seat("225", "O15", "6000", "3"));

		mSeat = (GridView) findViewById(R.id.grid_seat);
		upDown=(ImageButton)findViewById(R.id.imgBtnUpDown);
		upDown.setOnClickListener(clickListener);
		
		mSeat.setNumColumns(15);
		mSeat.setAdapter(new SeatAdapter(this, Seat_List));
		
		CoupleSeat_List = new ArrayList<Seat>();
		for (int i = 0; i < Seat_List.size(); i++) {
			if (Seat_List.get(i).getStatus().equals("4")) {
				CoupleSeat_List.add(Seat_List.get(i));
			}
		}
		
		int j = 0;
	//	Log.i("","hello : "+CoupleSeat_List.size());
		couple_list = new int[CoupleSeat_List.size()/2][2];
		for (int i = 0; i < CoupleSeat_List.size(); i++) {
			if(i % 2 != 0){
				couple_list[j][1] = Integer.valueOf(CoupleSeat_List.get(i).getSeatID()) - 1;
				j++;
			}else{
				couple_list[j][0] = Integer.valueOf(CoupleSeat_List.get(i).getSeatID()) - 1;
			}
			
		}
		
				
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}
			
			if (v == actionBarPrice) {
				if (menuClicked == false) {
					mMenuContainer.setTranslationY(0);
					mMenuList.startAnimation(showMenuAnimation());
					menuClicked = true;
				} else {
					mMenuList.startAnimation(hideMenuDownAnimation());
					mMenuContainer.postDelayed(runnable, 500);
					menuClicked = false;
				}

			}
			
			if(v==upDown)
			{
				if (!fistSeat) {
					upDown.setImageResource(R.drawable.downseat);
					mSeat.setNumColumns(15);
					mSeat.setAdapter(new SeatAdapter(SelectingSeatActivity.this, first_seat));
					mMenuList.setAdapter(new MoviePriceAdapter(SelectingSeatActivity.this,secondSeatPrices));
					mMenuList.setOnItemClickListener(itemClickListener);
					
					fistSeat = true;
				}
				else{
					
					upDown.setImageResource(R.drawable.upseat);
					mSeat.setAdapter(new SeatAdapter(SelectingSeatActivity.this, Seat_List));
					mMenuList.setAdapter(new MoviePriceAdapter(SelectingSeatActivity.this,firstSeatprices));
					mMenuList.setOnItemClickListener(itemClickListener);
					fistSeat = false;
					
				}
			
			}
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//getData();
		//SelectedSeat = "";
		SelectedPriceValue=null;
		
	}
	
	private Runnable runnable = new Runnable() {

		public void run() {
			mMenuContainer.setTranslationY(myDevice.getHeight());
		}
	};

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (fistSeat) {
				mMenuList.startAnimation(hideMenuDownAnimation());
				mMenuContainer.postDelayed(runnable, 500);
				menuClicked = true;
				UppriceValue = secondSeatPrices[position];
				//Log.i("", " Upstair Seat Price :" + UppriceValue);
				SelectedPriceValue = UppriceValue;
				mSeat.setAdapter(new SeatAdapter(SelectingSeatActivity.this,first_seat));
				Log.i("","First Seat :" + first_seat.toString());
				
			}
			else {
				mMenuList.startAnimation(hideMenuDownAnimation());
				mMenuContainer.postDelayed(runnable, 500);
				menuClicked = false;
				DownpriceValue = firstSeatprices[position];
				//Log.i("", " Downstair Seat Price :" + DownpriceValue);
				SelectedPriceValue = DownpriceValue;
				mSeat.setAdapter(new SeatAdapter(SelectingSeatActivity.this,Seat_List));
				
			}
						
		}
	};

	public Animation showMenuAnimation() {
		Animation scaleFace = new ScaleAnimation(0f, 1.0f, 0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0f);
		scaleFace.setDuration(300);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		scaleFace.reset();
		return scaleFace;
	}

	public Animation hideMenuDownAnimation() {
		Animation scaleFace = new ScaleAnimation(1.0f, 0f, 1.0f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0f);
		scaleFace.setDuration(300);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		scaleFace.reset();
		return scaleFace;
	}
}
