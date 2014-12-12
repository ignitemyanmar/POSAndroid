
package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.SaleHistoryController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.SaleHistory;
import com.ignite.pos.model.SaleVouncher;

public class UpdateSaleVoucherActivity extends SherlockActivity{

	private ActionBar actionBar;
	private TextView title;
	private TextView txt_item_name;
	private EditText editText_qty, editText_price;
	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> sale_list;
	private List<Object> item_list;
	private List<Object> ledger_list;
	private List<Object> profit_list;
	private List<Object> sale_obj;
	private String ItemID, ItemName, ItemQty, ItemPrice, ItemTotal, GrandTotal, Date, VouID;
	private String Discount;
	private Integer oldItemDisTotal;
	private ScrollView scrollView;
	private RelativeLayout add_layout;
	private String currentDate;
	private String AdminName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_voucher);
		
		//Get Admin Name
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		AdminName = pref.getString("admin_name", "-");
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("POS");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		txt_item_name = (TextView)findViewById(R.id.txt_item_name);
		editText_qty = (EditText)findViewById(R.id.editText_qty);
		editText_price = (EditText)findViewById(R.id.editText_price);
		cancel = (Button)findViewById(R.id.btnCancel);
		update = (Button)findViewById(R.id.btnSave);
		cancel.setOnClickListener(clickListener);
		update.setOnClickListener(clickListener);
		
		Bundle bundle = getIntent().getExtras();
		ItemID = bundle.getString("ItemID");
		ItemName = bundle.getString("ItemName");
		ItemQty = bundle.getString("ItemQty");
		ItemPrice = bundle.getString("ItemPrice");
		ItemTotal = bundle.getString("ItemTotal");
		GrandTotal = bundle.getString("GrandTotal");
		Date = bundle.getString("Date");
		VouID = bundle.getString("VouID");
		Discount = bundle.getString("Discount");
		
		oldItemDisTotal = Integer.valueOf(ItemTotal) - (Integer.valueOf(ItemTotal) * Integer.valueOf(Discount) / 100);
		
		//getFromDB(CatID);
		
		txt_item_name.setText(ItemName);
		editText_qty.setText(ItemQty);
		editText_price.setText(ItemPrice);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == cancel)
			{
				finish();
			}
			if(v == update)
			{
				if (checkFields()) {
					updateData();
					finish();
				}
				
			}
		}
	};
	
	private void updateData()
	{
		dbManager = new SaleVouncherController(this);
		SaleVouncherController sale_control = (SaleVouncherController)dbManager;
		sale_list = new ArrayList<Object>();
		
		//Get New Item Total 
		Integer newItemTotal = Integer.valueOf(editText_qty.getText().toString()) * Integer.valueOf(editText_price.getText().toString());
		Integer newItemDisTotal = Integer.valueOf(newItemTotal) - (Integer.valueOf(newItemTotal) * Integer.valueOf(Discount) / 100);
		
		//Get New Grand Total 
		Integer newGrandTotal = ( Integer.valueOf(GrandTotal) -  oldItemDisTotal) + newItemDisTotal ; 
		
		sale_list.add(new SaleVouncher(ItemID, ItemName, VouID, editText_qty.getText().toString(), editText_price.getText().toString()
				, newItemTotal.toString(), newGrandTotal.toString()));
		
		//Update in Sale Table 
		sale_control.update(sale_list);
		sale_control.updateByVouID(sale_list);
		Log.i("", "After Update in Sale: "+sale_control.selectRecordByVouID(VouID).toString());
		
		//Save in Sale History (Update Qty & Sale Price)
		dbManager = new SaleHistoryController(UpdateSaleVoucherActivity.this);
		SaleHistoryController shControl = (SaleHistoryController)dbManager;
		List<Object> shList = new ArrayList<Object>();
		
		shList.add(new SaleHistory(VouID, ItemID, Integer.valueOf(ItemQty)
				, Integer.valueOf(editText_qty.getText().toString()), currentDate, AdminName, "update"));
		
		shControl.save(shList);
		
		Log.i("", "After save in Sale History (Update Qty & Sale Price): "+shControl.selectRecordByVouID(VouID));
		
			//Update in Ledger Table 
			dbManager = new LedgerController(this);
			LedgerController ledger_control = (LedgerController)dbManager;
			ledger_list = new ArrayList<Object>();
			ledger_list = ledger_control.select(ItemID, Date, Date);
			
			Log.i("", "Ledger List: "+ledger_list.toString());
			
			List<Object> updateLedgerList;
			
			if (ledger_list.size() > 0) {
				//Update New Sale Qty in Ledger 
				Ledger ledger = (Ledger) ledger_list.get(0);
				
				Integer differQty = Integer.valueOf(ItemQty) - Integer.valueOf(editText_qty.getText().toString());
				
				if (differQty < 0) {
					
					//Plus to Sale Qty in Ledger 
					Integer newSaleQty = ledger.getSaleQty() - differQty; 
					
					Log.i("", "new sale qty: "+newSaleQty);
					
					//Get New Stock Qty 
					Integer newStockQty = ( ledger.getOldStockQty() + ledger.getPurchaseQty() ) - newSaleQty;
					
					//Update New Sale Qty 
					updateLedgerList = new ArrayList<Object>();
					updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
							, ledger.getOldStockQty(), ledger.getPurchaseQty(), newSaleQty, newStockQty, 0));
					ledger_control.updateQtyRecord(updateLedgerList);
					
					Log.i("", "After Update Sale Qty in Ledger(Plus): "+ledger_control.select().toString());
				}else {
					//Minus from Sale Qty in Ledger 
					Integer newSaleQty = ledger.getSaleQty() - differQty; 
					
					Log.i("", "new sale qty: "+newSaleQty);
					
					//Get New Stock Qty 
					Integer newStockQty = ( ledger.getOldStockQty() + ledger.getPurchaseQty() ) - newSaleQty;
					
					//Update New Sale Qty 
					updateLedgerList = new ArrayList<Object>();
					updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
							, ledger.getOldStockQty(), ledger.getPurchaseQty(), newSaleQty, newStockQty, 0));
					ledger_control.updateQtyRecord(updateLedgerList);
					
					Log.i("", "After Update Sale Qty in Ledger(Minus): "+ledger_control.select().toString());
				}
			}
			
			//Update in Profit Table
			dbManager = new ProfitController(this);
			ProfitController profit_control = (ProfitController)dbManager;
			profit_list = new ArrayList<Object>();
			profit_list = profit_control.selectByVidItemidDate(VouID, ItemID, Date);
			
			Log.i("", "Profit List: "+profit_list.toString());
			
			List<Object> updateProfitList;
			
			if (profit_list.size() > 0) {
				//Update New Sale Total PRice in Profit Table 
				Profit profit = (Profit) profit_list.get(0);
				
				Log.i("", "Profit: "+profit.toString());
					//Update New Sale Item Total Price 
					updateProfitList = new ArrayList<Object>();
					
					Integer newQty = Integer.valueOf(editText_qty.getText().toString());
					Integer newSalePrice = Integer.valueOf(editText_price.getText().toString());
					
					//Integer profitAmt =  ( profit.getSalePrice() * newQty ) - ( profit.getMarginalPrice() * newQty);
					Integer profitAmt =  ( newSalePrice * newQty ) - ( profit.getMarginalPrice() * newQty);
					
					Log.i("", "new slae price: "+newSalePrice);
					Log.i("", "new qty: "+newQty);
					Log.i("", "marginal price: "+profit.getMarginalPrice());
					Log.i("", "profit: "+profitAmt);
					
					updateProfitList.add(new Profit(profit.getItemId(), profit.getDate(), Integer.valueOf(profit.getMarginalPrice())
							, newSalePrice, newQty, profitAmt, VouID));
					
					profit_control.updateTotalPriceRecord(updateProfitList);
					
					Log.i("", "After Update Sale Total in Profit Table: "+profit_control.selectByVidItemidDate(VouID, ItemID, Date).toString());
					
			}	
			
			//Update in Item Stock Table 
			dbManager = new ItemListController(this);
			ItemListController item_control = (ItemListController)dbManager;
			item_list = new ArrayList<Object>();
			item_list = item_control.select(ItemID);
			
			//Old Qty - New Qty 
			Integer differQty = Integer.valueOf(ItemQty) - Integer.valueOf(editText_qty.getText().toString());
				
				if (item_list.size() > 0) {
					ItemList iteml = (ItemList)item_list.get(0);
					
					if (differQty < 0){
						//Minus from Stock Qty 
						Integer newStockQty = Integer.valueOf(iteml.getQty()) + differQty; 
						
						Log.i("", "new stock qty: "+newStockQty);
						
						//Update New Stock Qty 
						dbManager = new ItemListController(this);
						ItemListController control = (ItemListController)dbManager;
						item_list = new ArrayList<Object>();
						item_list.add(new ItemList(ItemID, newStockQty.toString()));
						control.updateNewStockQty(item_list);
						
						Log.i("", "After Update in Item Stk: "+control.select().toString());
						
					}else {
						
						//Plus to Stock Qty 
						Integer newStockQty = Integer.valueOf(iteml.getQty()) + differQty; 
						
						Log.i("", "new stock qty: "+newStockQty);
						
						//Update New Stock Qty 
						dbManager = new ItemListController(this);
						ItemListController control = (ItemListController)dbManager;
						item_list = new ArrayList<Object>();
						item_list.add(new ItemList(ItemID, newStockQty.toString()));
						control.updateNewStockQty(item_list);
						
						Log.i("", "After Update in Item Stk: "+item_control.select().toString());
					}
				}//End Update in Item Stock Table 			
	}
	
	public boolean checkFields() {
		
		if (editText_qty.getText().toString().length() == 0) {
			editText_qty.setError("Enter Sale Qty");
			return false;
		}
		if (editText_price.getText().toString().length() == 0) {
			editText_price.setError("Enter Sale Price");
			return false;
		}
		return true;
	}
}


