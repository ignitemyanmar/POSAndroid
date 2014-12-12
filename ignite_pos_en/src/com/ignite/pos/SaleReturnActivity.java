package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.google.gson.Gson;
import com.ignite.pos.adapter.SaleVoucherAdapter;
import com.ignite.pos.application.DeviceUtil;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.SaleReturnController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.BundleListObj;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.SaleReturn;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;

public class SaleReturnActivity  extends BaseSherlockActivity{

	private ActionBar actionBar;
	private boolean clicked = false;
	private DatabaseManager dbManager;
	public static SaleVoucherAdapter saleVoucherAdapter;
	public String currentDate;
	private SaleReturnVoucherAutoID autoId;
	private String auto_voucher_id;
	private TextView txt_title;
	private RelativeLayout layout_add_new;
	private AutoCompleteTextView autoComTxt_voucher;
	private ImageButton imgBtn_search;
	private ListView lv_sale_voucher, lv_return_voucher;
	private TextView txt_return_vou_no;
	private Button btn_print;
	private List<Object> sale_item_list;
	private List<Object> saleReturnList;
	private List<Object> sale_vou_list;
	private LinearLayout layout_left;
	private LinearLayout layout_right;
	private String AdminName;
	private Adapter returnAdapter;
	private List<Object> profit_list;
	private TextView title;
	private RelativeLayout add_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_return);
		
		//Get Admin Name
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		AdminName = pref.getString("admin_name", "-");
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("Add New Sale Return ");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);	
		
		layout_left = (LinearLayout) findViewById(R.id.lyVoucher);
		layout_right = (LinearLayout) findViewById(R.id.lyReturn);
		
		layout_left.getLayoutParams().width = DeviceUtil.getInstance(this).getWidth() / 2;
		layout_right.getLayoutParams().width = DeviceUtil.getInstance(this).getWidth() / 2;
		
		autoComTxt_voucher = (AutoCompleteTextView)findViewById(R.id.autoComTxt_voucher);
		imgBtn_search = (ImageButton)findViewById(R.id.imgBtn_search);
		lv_sale_voucher = (ListView)findViewById(R.id.lv_sale_voucher);
		txt_return_vou_no = (TextView)findViewById(R.id.txt_return_vou_no);
		btn_print = (Button)findViewById(R.id.btn_print);
		lv_return_voucher = (ListView)findViewById(R.id.lv_return_voucher);
		
		//Get Admin Name 
		Log.i("", "Admin Name: "+AdminName);
		
		imgBtn_search.setOnClickListener(clickListener);
		btn_print.setOnClickListener(clickListener);
		
		//Auto Voucher Id
		autoId = new SaleReturnVoucherAutoID(SaleReturnActivity.this);
		auto_voucher_id = autoId.generateAutoID();
				
		txt_return_vou_no.setText(auto_voucher_id);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		
		//Sale Voucher No. AutoComplete
		getSaleVoucherNo();
		
		setOnLoginListener(loginCallbacks);
	}
	
	private void getSaleVoucherNo() {
		// TODO Auto-generated method stub
		dbManager = new SaleVouncherController(this);
		SaleVouncherController svControl = (SaleVouncherController)dbManager;
		sale_vou_list = new ArrayList<Object>();
		
		//sale_vou_list.add(new SaleVouncher("All"));
		
		sale_vou_list.addAll(svControl.selectVouListGroupBy());
		
		Log.i("", "Sale Voucher List: "+sale_vou_list.toString());
		
		//Change List<Object> to String Array
		String[] vouArray = new String[sale_vou_list.size()];
		
		for (int i = 0; i < sale_vou_list.size(); i++) {
			
			SaleVouncher vouObj = (SaleVouncher)sale_vou_list.get(i);
			
			vouArray[i] = vouObj.getVid();  
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vouArray);
		autoComTxt_voucher.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autoComTxt_voucher.setThreshold(1);
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == imgBtn_search)
			{
				if (checkFields()) {
					getSaleVoucher();
				}
			}
			if(v == btn_print)
			{
				if (AdminName.equals("-")) {
					SKToastMessage.showMessage(SaleReturnActivity.this, "Please log in with Admin account first!", SKToastMessage.WARNING);
					showAdminDialog();
				}else {
					saveReturnVoucher();
				}
			}
		}
	};
	
	private LoginCallbacks loginCallbacks = new LoginCallbacks() {
		
		public void onLogin() {
			// TODO Auto-generated method stub
			
			SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
			AdminName = pref.getString("admin_name", "-");
			
			Log.i("", "After click log in: "+AdminName);
			
			//SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, "Login", SKToastMessage.SUCCESS);
		}
	};
	
	private void getSaleVoucher()
	{
		dbManager = new SaleVouncherController(this);
		SaleVouncherController sv_controller = (SaleVouncherController)dbManager;
		sale_item_list = new ArrayList<Object>();
		sale_item_list = sv_controller.selectRecordByVouID(autoComTxt_voucher.getText().toString());
		
		Log.i("","Sale Item List from DB :" + sale_item_list.toString());
		
		if (sale_item_list != null && sale_item_list.size() > 0) {
			
			saleVoucherAdapter = new SaleVoucherAdapter(this, sale_item_list, lv_return_voucher);
			lv_sale_voucher.setAdapter(saleVoucherAdapter);
			setListViewHeightBasedOnChildren(lv_sale_voucher);
			
		}
	}
	
	private BundleListObj bundleListObj;
	//private List<Object> listItems;
	//private Integer stock_balance_qty;
	
	private void saveReturnVoucher()
	{
		saleReturnList = new ArrayList<Object>();
		dbManager = new SaleReturnController(this);
		SaleReturnController control = (SaleReturnController)dbManager;
		
		returnAdapter = lv_return_voucher.getAdapter();
		
		View view = null ;
		bundleListObj = new BundleListObj();
		
		if (lv_return_voucher.getAdapter() != null && lv_return_voucher.getAdapter().getCount() > 0) {
			
			for (int i = 0; i < lv_return_voucher.getAdapter().getCount(); i++) {
				
				view = (View) lv_return_voucher.getChildAt(i);
				
				TextView txt_sale_vou_id = (TextView) view.findViewById(R.id.txt_sale_vou_id);
				TextView txt_sale_date = (TextView) view.findViewById(R.id.txt_sale_date);
				TextView txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
				TextView txt_item_name = (TextView) view.findViewById(R.id.txt_return_item_name);
				TextView txt_old_qty = (TextView) view.findViewById(R.id.txt_old_qty);
				TextView txt_return_qty = (TextView) view.findViewById(R.id.txt_return_qty);
				TextView txt_refund_price = (TextView) view.findViewById(R.id.txt_refund_price);
				TextView txt_refund_item_total = (TextView) view.findViewById(R.id.txt_refund_item_total);
				
				//Add Sale Return Voucher
				saleReturnList.add(new SaleReturn(txt_return_vou_no.getText().toString()
						, txt_item_code.getText().toString()
						, Integer.valueOf(txt_old_qty.getText().toString())
						, Integer.valueOf(txt_return_qty.getText().toString())
						, Integer.valueOf(txt_refund_price.getText().toString())
						, Integer.valueOf(txt_refund_item_total.getText().toString())
						, currentDate, AdminName, txt_item_name.getText().toString()
						, txt_sale_date.getText().toString()
						, txt_sale_vou_id.getText().toString()));
				
				bundleListObj.getListObj().add(new SaleReturn(txt_return_vou_no.getText().toString()
						, txt_item_code.getText().toString()
						, Integer.valueOf(txt_old_qty.getText().toString())
						, Integer.valueOf(txt_return_qty.getText().toString())
						, Integer.valueOf(txt_refund_price.getText().toString())
						, Integer.valueOf(txt_refund_item_total.getText().toString())
						, currentDate, AdminName, txt_item_name.getText().toString()
						, txt_sale_date.getText().toString()
						, txt_sale_vou_id.getText().toString()));
				
			}
			
			//Save On Sale Return Table
			control.save(saleReturnList);
			Log.i("", "Sale Return List after Save: " + control.selectRecordByVouID(txt_return_vou_no.getText().toString()));
			
			//Save Returnable Qty in Sale Voucher 
			dbManager = new SaleVouncherController(this);
			SaleVouncherController sv_controller = (SaleVouncherController)dbManager;
			sale_item_list = new ArrayList<Object>();
			
			
			List<Object> updateList = new ArrayList<Object>();
			
			for (int i = 0; i < saleReturnList.size(); i++) {
				
				SaleReturn sr = (SaleReturn)saleReturnList.get(i);
				
				sale_item_list = sv_controller.select(sr.getSaleVouId(), sr.getItemid());
				
				Log.i("", "Sale voucher list: "+sale_item_list.toString());
				
				if (sale_item_list != null && sale_item_list.size() > 0) {
					
					SaleVouncher sv = (SaleVouncher)sale_item_list.get(0);
					
					Integer returnableQty;
					
					if (sv.getReturnableQty() == 0) {
						returnableQty = sr.getOldQty() - sr.getReturnQty();
						
						Log.i("", "Old Qty: "+sr.getOldQty());
						Log.i("", "Return Qty: "+sr.getReturnQty());
						Log.i("", "Returnable Qty to save: "+returnableQty);

					}else {
						
						returnableQty = sv.getReturnableQty() - sr.getReturnQty();
						
						Log.i("", "Old Returnable Qty: "+sv.getReturnableQty());
						Log.i("", "Return Qty: "+sr.getReturnQty());
						Log.i("", "Returnable Qty to save: "+returnableQty);
					}
					
					updateList.add(new SaleVouncher(sr.getSaleVouId(), sr.getItemid(), returnableQty));
				}
			}
			
			sv_controller.updateReturnableQty(updateList);
			
			Log.i("","Sale Item List from DB :" + sale_item_list.toString());
			
			//Save Sale Qty into Ledger Table 
			dbManager = new LedgerController(SaleReturnActivity.this);
			LedgerController ledgerControl = (LedgerController)dbManager;
			List<Object> ledgerList = new ArrayList<Object>();
			
			//Get Stock Items
			dbManager = new ItemListController(SaleReturnActivity.this);
			ItemListController itemListcontrol = (ItemListController)dbManager;
			List<Object> listItem = new ArrayList<Object>();
			
			List<Object> updateLedgerList = new ArrayList<Object>();
			
			for (int i = 0; i < saleReturnList.size(); i++) {
				
				SaleReturn sr = (SaleReturn)saleReturnList.get(i);
				
				//Get Item Object by ItemID
				listItem = itemListcontrol.select(sr.getItemid());
				ItemList itemObj = (ItemList) listItem.get(0);
				
				//Select Ledger Table 
				List<Object> selectLedgerList = new ArrayList<Object>();
				selectLedgerList = ledgerControl.select(sr.getItemid(), sr.getSaleDate());
				
				Log.i("", "All in Ledger : "+ledgerControl.select().toString());
				
				//Update If item exist
				if (selectLedgerList.size() > 0) {
						
							Ledger ledger = (Ledger) selectLedgerList.get(0);
							
							Integer returnQty = 0;
							Integer totalReturnQty = 0;
							
							if (sr.getItemid().equals(ledger.getItemId())) {
								
								if (ledger.getReturnQty().equals("")) {
									returnQty = 0;
									totalReturnQty = sr.getReturnQty() + returnQty;
								}else {
									returnQty = ledger.getReturnQty();
									totalReturnQty =  returnQty + sr.getReturnQty();
								}
								
								Integer newStkQty = (ledger.getOldStockQty() + ledger.getPurchaseQty()) - ledger.getSaleQty() + totalReturnQty;
								
								updateLedgerList.add(new Ledger(sr.getItemid(), sr.getItemName(), sr.getSaleDate()
										, ledger.getOldStockQty(), ledger.getPurchaseQty(), ledger.getSaleQty(), newStkQty, totalReturnQty));
								ledgerControl.updateStockQty(updateLedgerList);
								
								Log.i("", "Ledger List after update: "+ledgerControl.select().toString());
								
							}
					
			}else {
				
				
			  }
			}//Sale Voucher Loop End
			
			//Update in Profit Table
			dbManager = new ProfitController(this);
			ProfitController profit_control = (ProfitController)dbManager;
			profit_list = new ArrayList<Object>();
			
			for (int i = 0; i < saleReturnList.size(); i++) {
				
				SaleReturn sr = (SaleReturn)saleReturnList.get(i);
				
				profit_list = profit_control.selectByVidItemidDate(sr.getSaleVouId(), sr.getItemid(), sr.getSaleDate());
				

				Log.i("", "Profit List: "+profit_list.toString());
				
				List<Object> updateProfitList;
				
				if (profit_list.size() > 0) {
					//Update New Sale Total PRice in Profit Table 
					Profit profit = (Profit) profit_list.get(0);
					
					Log.i("", "Profit: "+profit.toString());
						//Update New Sale Item Total Price 
						updateProfitList = new ArrayList<Object>();
						
						Integer newQty = profit.getSaleQty() - sr.getReturnQty();
						Integer profitAmt =  ( profit.getSalePrice() * newQty ) - ( profit.getMarginalPrice() * newQty);
						
						updateProfitList.add(new Profit(profit.getItemId(), profit.getDate(), Integer.valueOf(profit.getMarginalPrice())
								, profit.getSalePrice(), newQty, profitAmt, sr.getSaleVouId()));
						
						profit_control.updateTotalPriceRecord(updateProfitList);
						
						Log.i("", "After Update Sale Total in Profit Table: "+profit_control.selectByVidItemidDate(sr.getSaleVouId(), sr.getItemid(), sr.getSaleDate()).toString());
						
				}
			}
			
			//Get Item Stock Qty & decrease stock qty after sale
			List<Object> stockList = new ArrayList<Object>();
			dbManager = new ItemListController(SaleReturnActivity.this);
			ItemListController itemControl = (ItemListController)dbManager;
			List<Object> itemList = new ArrayList<Object>();
			
				for (int i = 0; i < saleReturnList.size(); i++) {
					
					SaleReturn sr = (SaleReturn)saleReturnList.get(i);
					itemList = itemControl.select(sr.getItemid());
					ItemList itemL = (ItemList)itemList.get(0);
					
					Integer balanceStkQty = Integer.valueOf(itemL.getQty()) + Integer.valueOf(sr.getReturnQty());
					
					Log.i("", "Item name: "+sr.getItemName()+", Balance Qty : "+balanceStkQty);
					
					//Update Notify Status
					Integer notifyStatus = 0;
					
					if (itemL.getNotifyQty() != null) {
						if (balanceStkQty <= Integer.valueOf(itemL.getNotifyQty())) {
							notifyStatus = 1;
						}else {
							notifyStatus = 0;
						}
					}else {
						notifyStatus = 0;
					}
					
					stockList.add(new ItemList(sr.getItemid(), sr.getItemName(), itemL.getPurchasePrice()
							, itemL.getSalePrice(), itemL.getMarginalPrice(), balanceStkQty.toString(), notifyStatus));
					
					itemControl.update(stockList);
					
					Log.i("", "Stock List after Update : "+itemControl.select(sr.getItemid()).toString());
				}
			
			autoComTxt_voucher.setText("");
			
			Log.i("", "list view: "+lv_return_voucher.toString());
			
			//saleReturnList.clear();
			lv_sale_voucher.setAdapter(null);
			lv_return_voucher.setAdapter(null);
			
			sale_item_list.clear();
			
			//SKToastMessage.showMessage(getApplicationContext(), "Return Voucher: "+txt_return_vou_no.getText().toString()+" saved!", SKToastMessage.SUCCESS);
			
			//Make Auto Voucher ID after check out
			auto_voucher_id = autoId.generateAutoID();
			txt_return_vou_no.setText(auto_voucher_id);
			
			//Go to Voucher Slip
			startActivity(new Intent(getApplicationContext(), 
					ReturnVoucherSlipActivity.class).putExtra("SaleReturnVoucher", new Gson().toJson(bundleListObj)));
			
		}else {
			SKToastMessage.showMessage(getApplicationContext(), "No Item Yet!", SKToastMessage.WARNING);
		}
		
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null && listView.getCount() == 0) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	
	public boolean checkFields() {
		if (autoComTxt_voucher.getText().toString().length() == 0) {
			autoComTxt_voucher.setError("Enter Sale Voucher");
			return false;
		}
		
		return true;
	}
	
}

	


