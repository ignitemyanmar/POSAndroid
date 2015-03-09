package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.ignite.pos.adapter.ProfitDetailReportLvAdapter;
import com.ignite.pos.database.controller.BusTicketSaleController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.BusTicketSale;

public class BusTicketSaleDetailReportActivity extends BaseSherlockActivity{

	private ActionBar actionBar;
	private TextView title;
	private TextView txt_grand_total;
	private ListView lv_profit_by_voucher;
	private DatabaseManager dbManager;
	private List<Object> listVoucher;
	private String date;
	private ProfitDetailReportLvAdapter lvAdapter;
	private String AdminName;
	private RelativeLayout add_layout;
	private TextView txt_date;
	private TextView txt_bar_code;
	private TextView txt_confirm_date;
	private TextView txt_customer;
	private TextView txt_operator;
	private TextView txt_trip;
	private TextView txt_time;
	private TextView txt_bus_class;
	private TextView txt_seats;
	private TextView txt_seat_count;
	private TextView txt_seat_price;
	private TextView txt_amount;
	private String barcode;
	private List<Object> busDetail;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busticket_detail_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("ဘတ္ (စ္) ကား အ ေရာင္း အေသးစိတ္");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		txt_bar_code = (TextView)findViewById(R.id.txt_bar_code);
		txt_confirm_date = (TextView)findViewById(R.id.txt_confirm_date);
		txt_customer = (TextView)findViewById(R.id.txt_customer);
		txt_operator = (TextView)findViewById(R.id.txt_operator);
		txt_trip = (TextView)findViewById(R.id.txt_trip);
		txt_date = (TextView)findViewById(R.id.txt_date);
		txt_time = (TextView)findViewById(R.id.txt_time);
		txt_bus_class = (TextView)findViewById(R.id.txt_bus_class);
		txt_seats = (TextView)findViewById(R.id.txt_seats);
		txt_seat_count = (TextView)findViewById(R.id.txt_seat_count);
		txt_seat_price = (TextView)findViewById(R.id.txt_seat_price);
		txt_amount = (TextView)findViewById(R.id.txt_amount);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			barcode = bundle.getString("Barcode");
		}
		
		dbManager = new BusTicketSaleController(BusTicketSaleDetailReportActivity.this);
		BusTicketSaleController busControl = (BusTicketSaleController)dbManager;
		busDetail = new ArrayList<Object>();
		busDetail = busControl.select(barcode);
		
		if (busDetail != null && busDetail.size() > 0) {
			
			Log.i("", "Bus Detail : "+busDetail.toString());
			
			BusTicketSale bus = (BusTicketSale)busDetail.get(0);
			
			txt_bar_code.setText("ကုတ္နံပါတ္ : "+bus.getBarcodeNo());
			txt_confirm_date.setText("ေရာင္းသည့္ ေန႔ရက္ : "+bus.getConfirmDate());
			txt_customer.setText(bus.getCustomerName());
			txt_operator.setText(bus.getOperatorName());
			txt_trip.setText(bus.getTrip());
			txt_date.setText(bus.getDate());
			txt_time.setText(bus.getTime());
			txt_bus_class.setText(bus.getBusClass());
			txt_seats.setText(bus.getSeatNo());
			txt_seat_count.setText(bus.getSeatCount()+"");
			txt_seat_price.setText(bus.getSeatPrice()+" က်ပ္");
			txt_amount.setText(bus.getSeatCount() * bus.getSeatPrice()+" က်ပ္");
		}
		
	}
}

