package com.ignite.mm.ticketing.agent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mm.ticketing.custom.listview.adapter.BusClassAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.BusSeatAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.GroupUserListAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.RemarkListAdapter;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorGroupUser;
import com.ignite.mm.ticketing.sqlite.database.model.Permission;
import com.ignite.mm.ticketing.sqlite.database.model.ReturnComfrim;
import com.ignite.mm.ticketing.sqlite.database.model.Seat;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;
import com.ignite.mm.ticketing.sqlite.database.model.SelectSeat;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;

@SuppressLint("SimpleDateFormat") public class BusSelectSeatActivity extends BaseSherlockActivity{
	
	public static List<BusSeat> Bus_Seat;
	private ListView lvClass;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private GridView mSeat;
	public static String SelectedSeat;
	protected ArrayList<Seat> Seat;
	protected ProgressDialog dialog;
	private ConnectionDetector connectionDetector;
	private LinearLayout mLoadingView;
	private LinearLayout mNoConnection;
	protected ReturnComfrim returnComfirm;
	private String AgentID = "0";
	private String CustName = "";
	private String CustPhone = "";
	private int RemarkType = 0;
	private String Remark = "";
	private String OperatorID = "0";
	private String FromCity;
	private String ToCity;
	private String Time;
	private String Date;
	private TextView txt_operator;
	private TextView txt_classes;
	private TextView txt_price;
	private TextView txt_dept_date;
	private TextView txt_dept_time;
	private ListView lst_group_user;
	private Button btn_booking;
	private String From;
	private String To;
	private Button btn_now_booking;
	private Integer isBooking = 0;
	private Integer NotifyBooking;
	private TextView actionBarNoti;
	private Button btn_check_out;
	private String Classes;
	protected BookingDialog bookingDialog;
	private List<Seat_list> remarkSeats;
	private ListView lst_remark;
	private String BusClasses;
	private LinearLayout layout_remark;
	private TextView actionBarTitle2;
	private String currentDate;
	private String todayDate;
	private String todayTime;
	private String permit_access_token = "";
	private String permit_ip;
	private String operator_name = "";
	private Date tripDate;
	private Date tDate;
	private Date tripTime;
	private Date tTime;
	private String permit_operator_group_id;
	private String permit_agent_id;
	private String client_operator_id;
	private String tripId = "0";
	public static List<BusSeat> BusSeats;
	public static List<OperatorGroupUser> groupUser = new ArrayList<OperatorGroupUser>();
	public static String CheckOut;
	private Permission permission;
	private String permit_operator_id = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_seat_list);

		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		
		actionBarBack.setOnClickListener(clickListener);
		actionBarNoti = (TextView) actionBar.getCustomView().findViewById(R.id.txt_notify_booking);
		actionBarNoti.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		//Get Data from past clicks
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			//permit_access_token = bundle.getString("permit_access_token");
			AgentID = bundle.getString("agent_id");
			OperatorID = bundle.getString("operator_id");
			FromCity = bundle.getString("from_city_id");
			ToCity = bundle.getString("to_city_id");
			From = bundle.getString("from_city");
			To = bundle.getString("to_city");
			Classes = bundle.getString("class_id");
			Time = bundle.getString("trip_time");
			Date = bundle.getString("trip_date");
			//permit_ip = bundle.getString("permit_ip");
			operator_name = bundle.getString("operator_name");
			//permit_operator_group_id = bundle.getString("permit_operator_grou/p_id");
			//permit_agent_id = bundle.getString("permit_agent_id");
			//client_operator_id = bundle.getString("client_operator_id");
			tripId = bundle.getString("tripId");
		}
		
		//Log.i("", "(Bus Select Seat) Permit_operator_group_id : "+permit_operator_group_id+", Permit_agent_id : "+permit_agent_id);
		
		mSeat = (GridView) findViewById(R.id.grid_seat);
		lst_group_user = (ListView) findViewById(R.id.lst_group_user);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		connectionDetector = new ConnectionDetector(this);
		
		//Get TodayDate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		todayDate = sdf.format(new Date());
		
		//Get Current Time
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat writeFormat = new SimpleDateFormat("hh:mm aa");
		Calendar cal = Calendar.getInstance();
		Date tTime = null;
		try {
			tTime = sdf2.parse(sdf2.format(cal.getTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		todayTime = writeFormat.format(tTime);
		
		//Get only 06:00 AM format
		String time = null;
		try {
			if (Time.length() == 8) {
				time = Time;
			}else if (Time.length() < 8) {
				time = "0"+Time;
			}else if (Time.length() > 8) {
				time = Time.substring(0, 8);
			}
		} catch (StringIndexOutOfBoundsException e) {
			// TODO: handle exception
			Log.i("", "Time Out Of Bound Exception: "+e);
		}
		
		SimpleDateFormat serverFormat = new SimpleDateFormat("hh:mm aa");
		Date timeTochange = null;
		try {
			if (time != null) {
				timeTochange = serverFormat.parse(time);
				Log.i("", "Server Time Format: "+serverFormat.format(timeTochange));
			}
			
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			Log.i("", "Server Time Exception: "+e2);
			e2.printStackTrace();
		}				
				
		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.GONE);
			actionBarNoti.setText(NotifyBooking.toString());
		}
		
		actionBarTitle.setText(From+" - "+To+" ["+operator_name+"]");
		actionBarTitle2.setText(changeDate(Date)+" ["+Time+"]");
		
		SelectedSeat 	= "";
		btn_booking		= (Button) findViewById(R.id.btn_booking);
		btn_booking.setOnClickListener(clickListener);
		btn_now_booking = (Button) findViewById(R.id.btn_now_booking);
		btn_now_booking.setOnClickListener(clickListener);
		btn_check_out = (Button) findViewById(R.id.btn_check_out);
		btn_check_out.setOnClickListener(clickListener);
		mLoadingView = (LinearLayout) findViewById(R.id.ly_loading);
		mNoConnection = (LinearLayout) findViewById(R.id.no_internet);
		txt_operator = (CustomTextView) findViewById(R.id.txt_operator);
		txt_classes = (CustomTextView) findViewById(R.id.txt_classes);
		txt_price = (CustomTextView) findViewById(R.id.txt_price);
		txt_dept_date = (CustomTextView) findViewById(R.id.txt_departure_date);
		txt_dept_time = (CustomTextView) findViewById(R.id.txt_departure_time);
		
		txt_dept_date.setText("ထြက္ခြာမည့္ ေန႕ရက္ : "+ Date);
		txt_dept_time.setText("ထြက္ခြာမည့္ အခ်ိန္ : "+ Time);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(SelectedSeat.length() != 0)
			finish();
		if(connectionDetector.isConnectingToInternet())
		{ 	mLoadingView.setVisibility(View.VISIBLE);
			mLoadingView.startAnimation(topInAnimaiton());
			//getOperatorGroupUser();
			
			//Get permission & Get seat plan 
			getPermission();
			
			//getAgent();
		}else {
			connectionDetector.showErrorDialog();
			/*mNoConnection.setVisibility(View.VISIBLE);
			mNoConnection.startAnimation(topInAnimaiton());*/
		}
	}
	
	private void getOperatorGroupUser(){
		
		Log.i("", "Enter Operator Gourp User!!!!!!!!!!!!!!! "+ permit_access_token+" "+AppLoginUser.getId());
		
		String param = MCrypt.getInstance().encrypt(SecureParam.getOperatorGroupUserParam(permit_access_token, OperatorID));
		
		Log.i("", "Param Operator g: "+param);
		
		NetworkEngine.getInstance().getOperatorGroupUser(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					Log.i("", "Error Operator Group: "+arg0.getResponse().getStatus());	
					Log.i("", "Permit A Token: "+permit_access_token+", user id: "+AppLoginUser.getId());			
				}
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				Log.i("", "Operator Group User (Response): "+arg0.toString());
				
				if (arg0 != null) {
					groupUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorGroupUser>>() {}.getType());
					
					if (groupUser != null && groupUser.size() > 0) {
						
						Log.i("","Hello Group User(size): "+ groupUser.size()+"Group User: "+groupUser.toString());
						
						lst_group_user.setAdapter(new GroupUserListAdapter(BusSelectSeatActivity.this, groupUser));
						setListViewHeightBasedOnChildren(lst_group_user);
					}
				}
			}
		});
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
	
	
	private void getAgent(){
		SharedPreferences pref = this.getApplicationContext().getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		String user_id = pref.getString("user_id", null);
		
		String param = MCrypt.getInstance().encrypt(SecureParam.getAllAgentParam(accessToken, user_id));
		NetworkEngine.getInstance().getAllAgent(param, new Callback<Response>() {

			private List<Agent> agentList;
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				//agentList = arg0.getAgents();
				agentList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Agent>>(){}.getType());
				bookingDialog = new BookingDialog(BusSelectSeatActivity.this, agentList);
				bookingDialog.setCallbackListener(new BookingDialog.Callback() {

					public void onCancel() {
						// TODO Auto-generated method stub
						
					}

					public void onSave(String agentId, String custName,
							String custPhone, int remarkType, String remark) {
						// TODO Auto-generated method stub
						isBooking = 1;
						AgentID = agentId;
						CustName = custName;
						CustPhone = custPhone;
						RemarkType = remarkType;
						Remark = remark;
/*						if(!AgentID.equals("0")){
							getServermsg();
						}*/	
					}
				});	
			}
		});
	}
		
	
	private void getPermission() {
		// TODO Auto-generated method stub
		//1. Get Permission
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getPermission(AppLoginUser.getAccessToken(), OperatorID, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
					Log.i("", "Trip Operator ID: "+OperatorID);
				}
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>(){}.getType());
					
					if (permission != null) {
						permit_ip = permission.getIp();
						permit_access_token = permission.getAccess_token();
						permit_operator_id = permission.getOperator_id();
						permit_operator_group_id = permission.getOperatorgroup_id();
						permit_agent_id = permission.getOnlinesaleagent_id();
						
						getSeatPlan();
					}
				}
			}
		});
	}
	
	private void getSeatPlan() {
		
		String param = MCrypt.getInstance().encrypt(SecureParam.getSeatPlanParam(permit_access_token, permit_operator_id, tripId, "", "", "", Date, ""));
		
		Log.i("", "Permit token: "+permit_access_token
				+", Operator Id: "+permit_operator_id
				+", Trip Id: "+tripId+", Date: "+Date);
		
		Log.i("", "Param to get Seats: "+param);
		
		//NetworkEngine.setIP("128.199.81.168");
		NetworkEngine.setIP(permit_ip);
		NetworkEngine.getInstance().getItems(param, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				// Try to get response body
				if (arg0 != null) {
					SelectedSeat = "";
					
					Log.i("","Success Seat Plan: ");
					
					BusSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<BusSeat>>() {}.getType());
					
					if (BusSeats != null && BusSeats.size() > 0) {
						getData();
						mLoadingView.setVisibility(View.GONE);
						mLoadingView.startAnimation(topOutAnimaiton());
					}
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				Log.i("","Hello Seat Error: "+ arg0.getCause());
				Log.i("","Hello Seat Error: "+ arg0.getResponse().getBody());
				Log.i("","Hello Seat Error: "+ arg0.getResponse().getHeaders().toString());
			}
		});
	}
	
	public void postSale()
	{
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        List<SelectSeat> seats = new ArrayList<SelectSeat>();
        
        String[] selectedSeat = SelectedSeat.split(",");
        
		for (int i = 0; i < selectedSeat.length; i++) {
			seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0)
					.getId(), BusSeats.get(0).getSeat_plan().get(0)
					.getSeat_list().get(Integer.valueOf(selectedSeat[i]))
					.getSeat_no().toString()));
		}
		
		final String FromCity = BusSeats.get(0).getSeat_plan().get(0).getFrom().toString();
		final String ToCity = BusSeats.get(0).getSeat_plan().get(0).getTo().toString();
		
        Log.i("","Hello From City: "+FromCity+" , To City : "+ToCity+" and Select Seat -> "+seats.toString());
        
		//if (AppLoginUser.getUserType().equals("agent")) {
			AgentID = String.valueOf(AppLoginUser.getId());
		//} else if (AgentID.length() == 0) {
			//AgentID = "0";
		//}
               
		Log.i("", "Param sale to encrypt: "
		+"access: "+permit_access_token+
		"operatorID: "+permit_operator_id+
		"agent id: "+AgentID+
		"Cus Name: "+CustName+
		"cus ph: "+CustPhone+
		"Remark type: "+RemarkType+
		"Remark: "+Remark+
		"groupid: "+permit_operator_group_id+
		"Seats : "+seats.toString()+
		"trip id: "+BusSeats.get(0).getSeat_plan()
		.get(0).getId()+
		"date: "+Date+
		"from city: "+FromCity+
		"to city: "+ToCity+
		"user id: "+AppLoginUser
		.getId()+
		"device id: "+DeviceUtil.getInstance(this).getID()+
		"is booking: "+isBooking.toString());
		
		//Do Encrypt of Params
		String param = MCrypt.getInstance().encrypt(SecureParam.postSaleParam(permit_access_token
					, permit_operator_id, permit_agent_id, CustName, CustPhone, String
					.valueOf(RemarkType), Remark, permit_operator_group_id, MCrypt.getInstance()
					.encrypt(seats.toString()), BusSeats.get(0).getSeat_plan()
					.get(0).getId().toString(), Date, FromCity, ToCity, String.valueOf(AppLoginUser
					.getId()), DeviceUtil.getInstance(this).getID(), isBooking.toString(),
					String.valueOf(AppLoginUser.getId())));
			
			Log.i("","Hello param (for sale) : "+ param);
		
		
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", param));       
        
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				
				String jsonData = msg.getData().getString("data");
				
				Log.i("ans:","Server Response: "+jsonData);
				
				try {
					
					JSONObject jsonObject = null;
					
					if (jsonData != null) {
						jsonObject = new JSONObject(jsonData);
						
					}
					String SeatLists = "";
					
					if(jsonObject.getString("status").equals("1")){
						
						if(jsonObject.getBoolean("can_buy") && jsonObject.getString("device_id").equals(DeviceUtil.getInstance(BusSelectSeatActivity.this).getID())){
		        			
							//Get Seats No. including (,)
	        				JSONArray jsonArray = jsonObject.getJSONArray("tickets");	        					        			
	        				
	        				for(int i=0; i<jsonArray.length(); i++){
	        					JSONObject obj = jsonArray.getJSONObject(i);
	        					if (i == jsonArray.length() - 1) {
	        						SeatLists += obj.getString("seat_no");
								}else {
									SeatLists += obj.getString("seat_no")+",";
								}
	        				}
	        				
	        				//Buy Ticket
							if(isBooking == 0){
								
		        				Intent nextScreen = new Intent(BusSelectSeatActivity.this, BusConfirmActivity.class);
		        				
			    				Bundle bundle = new Bundle();
			    				bundle.putString("from_intent", "checkout");
			    				bundle.putString("Operator_Name", BusSeats.get(0).getOperator());			    				
			    				bundle.putString("from_to", From+" => "+To);
			    				bundle.putString("time", Time);
			    				bundle.putString("classes", BusClasses);
			    				bundle.putString("date", Date);
			    				bundle.putString("selected_seat",  SeatLists);
			    				bundle.putString("sale_order_no", jsonObject.getString("sale_order_no"));
			    				bundle.putString("bus_occurence", BusSeats.get(0).getSeat_plan().get(0).getId().toString());
			    				bundle.putString("Price", BusSeats.get(0).getSeat_plan().get(0).getPrice()+"");
		        				bundle.putString("ConfirmDate", todayDate);
		        				bundle.putString("ConfirmTime", todayTime);
		        				bundle.putString("CustomerName", AppLoginUser.getUserName());
		        				//Get Seat Count
		        				String[] seats = SeatLists.split(",");
		        				bundle.putString("SeatCount", seats.length+"");
			    				bundle.putString("permit_ip", permit_ip);
			    				bundle.putString("permit_access_token", permit_access_token);
			    				
			    				bundle.putString("permit_operator_group_id", permit_operator_group_id);
								bundle.putString("permit_agent_id", permit_agent_id);
								bundle.putString("permit_operator_id", permit_operator_id);
								
								//bundle.putString("client_operator_id", client_operator_id);
			    				
			    				nextScreen.putExtras(bundle);
			    				startActivity(nextScreen);
		        			}else{ //Booking Finished!
		        				finish();
		        				Log.i("", "Booking ...........................................");
		        				SKToastMessage.showMessage(BusSelectSeatActivity.this, "Booking မွာၿပီးၿပီ ျဖစ္ပါသည္ !", SKToastMessage.SUCCESS);
		        				
		        				isBooking = 0;
		        				
		        				Bundle bundle = new Bundle();
		        				bundle.putString("Operator_Name", BusSeats.get(0).getOperator());			    				
			    				bundle.putString("from_to", From+"-"+To);
			    				bundle.putString("time", Time);
			    				bundle.putString("classes", BusClasses);
			    				bundle.putString("date", changeDateString(Date));
			    				bundle.putString("selected_seat",  SeatLists);
			    				bundle.putString("sale_order_no", jsonObject.getString("sale_order_no"));
			    				bundle.putString("bus_occurence", BusSeats.get(0).getSeat_plan().get(0).getId().toString());
			    				bundle.putString("Price", BusSeats.get(0).getSeat_plan().get(0).getPrice()+"");
		        				bundle.putString("ConfirmDate", todayDate);
		        				bundle.putString("ConfirmTime", todayTime);
		        				bundle.putString("CustomerName", AppLoginUser.getUserName());
		        				//Get Seat Count
		        				String[] seats = SeatLists.split(",");
		        				bundle.putString("SeatCount", seats.length+"");
			    				bundle.putString("permit_ip", permit_ip);
			    				bundle.putString("permit_access_token", permit_access_token);
			    				
		        				//Show Voucher
		        				startActivity(new Intent(BusSelectSeatActivity.this, PDFBusActivity.class).putExtras(bundle));
		        				
		        				Log.i("", "Book Code to save: "+jsonObject.getString("sale_order_no"));
		        				
		        			}
		    				dialog.dismiss(); //finish can buy ticket 
		        		}else{
		        			dialog.dismiss();
		        			SKToastMessage.showMessage(BusSelectSeatActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန့္ပိုင္ အတြင္း တစ္ ျခားသူ ယူ သြားေသာေၾကာင့္ သင္မွာေသာလက္မွတ္မ်ား မရႏိုင္ေတာ့ပါ။ ေက်းဇူးျပဳၿပီး တစ္ျခားလက္ မွတ္ မ်ား ျပန္ေရႊးေပးပါ။။", SKToastMessage.ERROR);
		        			//Get permission & Seat Plan 
		        			getPermission();		
		        		}
					}else{
						isBooking = 0;
						dialog.dismiss();
						SKToastMessage.showMessage(BusSelectSeatActivity.this, "အခ်ိန္ ေနာက္ က် ေနသည့္အတြက္ ၀ယ္ လုိ႔ မရပါ။", SKToastMessage.ERROR);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		HttpConnection lt = new HttpConnection(handler,"POST", "http://"+ permit_ip +"/sale", params);
		lt.execute();
		
		Log.i("", "Permit IP: "+permit_ip);
	}
	
		
	private void getData() {
		
		if(BusSeats.size() > 0){
			
			Log.i("", "Bus Seats: "+BusSeats.toString());
			//Log.i("", "Seats Plan: "+BusSeats.get(0).getSeat_plan());
			
			txt_operator.setText("ကားဂိတ္ : "+ BusSeats.get(0).getOperator());
			txt_classes.setText("ယာဥ္အမ်ိဳးအစား : "+ BusSeats.get(0).getSeat_plan().get(0).getClasses());
			txt_price.setText("ေစ်းႏႈန္း :"+ BusSeats.get(0).getSeat_plan().get(0).getPrice()+" Ks");
			
			BusClasses = BusSeats.get(0).getSeat_plan().get(0).getClasses();
			remarkSeats = new ArrayList<Seat_list>();
			
			Map<Integer, List<Seat_list>> map = new HashMap<Integer, List<Seat_list>>();
			for (Seat_list remarkSeat : BusSeats.get(0).getSeat_plan().get(0).getSeat_list()) {
				if(remarkSeat.getRemark_type() != 0){
					Integer key  = remarkSeat.getRemark_type();
				    if(map.containsKey(key)){
				        map.get(key).add(remarkSeat);
				    }else{
				        List<Seat_list> list = new ArrayList<Seat_list>();
				        list.add(remarkSeat);
				        map.put(key, list);
				    }
				}
			}
			
			layout_remark.removeAllViewsInLayout();
			
			for (Map.Entry<Integer, List<Seat_list>> entry : map.entrySet())
			{
			    ListView lst_remark = new ListView(this);
			    View viewRemarkType = View.inflate(this, R.layout.remark_header, null);
			    TextView txtRemartType = (TextView) viewRemarkType.findViewById(R.id.txt_remark_type);
			    txtRemartType.setText(getRemarkType(entry.getKey()));
			    lst_remark.addHeaderView(viewRemarkType);
				lst_remark.setAdapter(new RemarkListAdapter(this, entry.getValue()));
				Log.i("","Hello = "+ entry.getValue());
				layout_remark.addView(lst_remark);
				setListViewHeightBasedOnChildren(lst_remark);
			}
			
			//Set Seat List in Grid
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(0).getColumn());
			
			Log.i("", "Seat List (mseat): "+BusSeats.get(0).getSeat_plan().get(0).getSeat_list());
			
			mSeat.setAdapter(new BusSeatAdapter(this, BusSeats.get(0).getSeat_plan().get(0).getSeat_list()));	
			
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(0).getColumn()));
			
			lvClass = (ListView)findViewById(R.id.lvBusClass);
			lvClass.setAdapter(new BusClassAdapter(this, BusSeats.get(0).getSeat_plan()));
			lvClass.setOnItemClickListener(itemClickListener);
		}else{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setMessage("There is no bus yet.");
			alertDialog.setCancelable(false);
			alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			alertDialog.show();
		}
	}
	
	private String getRemarkType(int remarkType){
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add("မွတ္ခ်က္ အမ်ိဳးအစား  ေရြးရန္");
		remarkTypes.add("လမ္းၾကိဳ");
		remarkTypes.add("ေတာင္းရန္");
		remarkTypes.add("ခုံေရြ႕ရန္");
		remarkTypes.add("Date Change ရန္");
		remarkTypes.add("စီးျဖတ္");
		remarkTypes.add("ေတာင္းေရာင္း");
		remarkTypes.add("ဆက္သြား");
		return remarkTypes.get(remarkType).toString();
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			txt_operator.setText("ကားဂိတ္ : "+ BusSeats.get(0).getOperator());
			txt_classes.setText("ယာဥ္အမ်ိဳးအစား : "+ BusSeats.get(0).getSeat_plan().get(position).getClasses());
			txt_price.setText("ေစ်းႏႈန္း :"+ BusSeats.get(0).getSeat_plan().get(position).getPrice()+" Ks");
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(position).getColumn());
			mSeat.setAdapter(new BusSeatAdapter(BusSelectSeatActivity.this, BusSeats.get(0).getSeat_plan().get(position).getSeat_list()));	
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(position).getColumn()));
		}
	};
	
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}
			
			if(v == actionBarNoti){
				SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", getToday());
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
			}
			
			if(v == btn_booking){
					if (connectionDetector.isConnectingToInternet()) {
						SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.clear();
						editor.commit();
						editor.putString("order_date", Date);
						editor.putString("from", FromCity);
						editor.putString("to", ToCity);
						editor.putString("time", Time);				
						editor.commit();
						
						Bundle bundle = new Bundle();
						bundle.putString("operator_id", OperatorID);				
			        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class).putExtras(bundle));
					}else{
						connectionDetector.showErrorDialog();
					}
			}
			
			if(v == btn_now_booking){
				if(SelectedSeat.length() != 0){
					if(connectionDetector.isConnectingToInternet()){
						
						isBooking = 1;
						AgentID = "1";
						CustName = AppLoginUser.getUserName();
						CustPhone = "";
						RemarkType = 0;
						Remark = "";
						
						Log.i("", "Agent ID: "+AgentID);
						
						if(!AgentID.equals("0")){
							
							Log.i("", "Enter here + Agent ID: "+AgentID);
							postSale();
						}	
					}else{
						connectionDetector.showErrorDialog();
					}
				}else{
					SKToastMessage.showMessage(BusSelectSeatActivity.this, "ခံု ေရြးပါ။", SKToastMessage.ERROR);
				}
			}
			
			if(v == btn_check_out){
				if(SelectedSeat.length() != 0){									
					
					if(connectionDetector.isConnectingToInternet()){
						postSale();
					}else{
						connectionDetector.showErrorDialog();
					}
						
				}else{
					SKToastMessage.showMessage(BusSelectSeatActivity.this, "ခံု ေရြးပါ။", SKToastMessage.ERROR);
				}
			}				
		}
	};
	
	public void showDialog(String msg){
		LayoutInflater factory = LayoutInflater.from(this);
	    final View msgDialogView = factory.inflate(R.layout.custom_msg_dialog, null);
	    final AlertDialog msgDialog = new AlertDialog.Builder(this).create();
	    msgDialog.setView(msgDialogView);
	    LinearLayout msgContainer = (LinearLayout)msgDialogView.findViewById(R.id.dialog_container);
	    try {
			JSONObject data = new JSONObject(msg);
			JSONArray arr = data.getJSONArray("Seat");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				TextView txtMsg = new TextView(this);
				txtMsg.setText(obj.getString("seatNo")+" is "+obj.getString("seatStatus"));
				msgContainer.addView(txtMsg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    msgDialogView.findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	            //your business logic 
	            msgDialog.dismiss();
	        }
	    });
	    msgDialog.show();
	}
	
	public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int items = listAdapter.getCount();
		int rows = 0;

		View listItem = listAdapter.getView(0, null, gridView);
		listItem.measure(0, 0);
		totalHeight = listItem.getMeasuredHeight();

		float x = 1;
		if (items > columns) {
			x = items / columns;
			rows = (int) (x + 0);
			totalHeight *= rows;
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);

	}
	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		RelativeLayout focuslayout = (RelativeLayout) findViewById(R.id.layout_seat_plan);
		focuslayout.requestFocus();
		super.onStart();
	}
}

