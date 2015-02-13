
package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.PurchaseVoucher;

public class UpdatePurchaseVoucherActivity extends SherlockActivity{

	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	private TextView txt_item_name;
	private EditText editText_qty, editText_price;
	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> purchase_list;
	private List<Object> item_list;
	private List<Object> ledger_list;
	private List<Object> profit_list;
	private List<Object> sale_obj;
	private String ItemID, ItemName, ItemQty, ItemPrice, ItemTotal, GrandTotal, Date, VouID;
	private String Discount;
	private Integer oldItemDisTotal;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_purchase_voucher);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		title.setText("Update Purchase Voucher");
		title.setBackgroundResource(R.color.black);
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setVisibility(View.GONE);
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
		//Discount = bundle.getString("Discount");
		
		//oldItemDisTotal = Integer.valueOf(ItemTotal) - (Integer.valueOf(ItemTotal) * Integer.valueOf(Discount) / 100);
		
		//getFromDB(CatID);
		
		txt_item_name.setText(ItemName);
		editText_qty.setText(ItemQty);
		editText_price.setText(ItemPrice);
		
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
		dbManager = new PurchaseVoucherController(this);
		PurchaseVoucherController purchase_control = (PurchaseVoucherController)dbManager;
		purchase_list = new ArrayList<Object>();
		
		//Get New Item Total 
		Integer newItemTotal = Integer.valueOf(editText_qty.getText().toString()) * Integer.valueOf(editText_price.getText().toString());
		//Integer newItemDisTotal = Integer.valueOf(newItemTotal) - (Integer.valueOf(newItemTotal) * Integer.valueOf(Discount) / 100);
		
		//Get New Grand Total 
		//Integer newGrandTotal = ( Integer.valueOf(GrandTotal) -  oldItemDisTotal) + newItemDisTotal ; 
		Integer newGrandTotal = ( Integer.valueOf(GrandTotal) -  Integer.valueOf(ItemTotal)) + newItemTotal ; 
		
		purchase_list.add(new PurchaseVoucher(VouID, ItemID, ItemName, editText_qty.getText().toString()
				, newItemTotal.toString(), newGrandTotal.toString(), editText_price.getText().toString()));
		
		//Update in Purchase Table 
		purchase_control.update(purchase_list);
		purchase_control.updateByVouID(purchase_list);
		
		Log.i("", "After Update in Purchase: "+purchase_control.selectRecordByVouID(VouID).toString());
			
			//Update in Ledger Table 
			dbManager = new LedgerController(this);
			LedgerController ledger_control = (LedgerController)dbManager;
			ledger_list = new ArrayList<Object>();
			ledger_list = ledger_control.select(ItemID, Date, Date);
			
			Log.i("", "Ledger List: "+ledger_list.toString());
			
			List<Object> updateLedgerList;
			
			if (ledger_list.size() > 0) {
				//Update New Purchase Qty in Ledger 
				Ledger ledger = (Ledger) ledger_list.get(0);
				
				Integer differQty = Integer.valueOf(ItemQty) - Integer.valueOf(editText_qty.getText().toString());
				
				if (differQty < 0) {
					
					//Plus to Purchase Qty in Ledger 
					Integer newPurchaseQty = ledger.getPurchaseQty() - differQty; 
					
					Log.i("", "new purchase qty: "+newPurchaseQty);
					
					//Get New Stock Qty 
					Integer newStockQty = ( ledger.getOldStockQty() + newPurchaseQty ) - ledger.getSaleQty();
					
					//Update New Purchase Qty 
					updateLedgerList = new ArrayList<Object>();
					updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
							, ledger.getOldStockQty(), newPurchaseQty, ledger.getSaleQty(), newStockQty, 0));
					ledger_control.updateQtyRecord(updateLedgerList);
					
					Log.i("", "After Update Purchase Qty in Ledger(Plus): "+ledger_control.select(ItemID, Date, Date).toString());
				}else {
					//Minus Purchase Qty in Ledger 
					Integer newPurchaseQty = ledger.getPurchaseQty() - differQty; 
					
					Log.i("", "new purchase qty: "+newPurchaseQty);
					
					//Get New Stock Qty 
					Integer newStockQty = ( ledger.getOldStockQty() + newPurchaseQty ) - ledger.getSaleQty();
					
					//Update New Purchase Qty 
					updateLedgerList = new ArrayList<Object>();
					updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
							, ledger.getOldStockQty(), newPurchaseQty, ledger.getSaleQty(), newStockQty, 0));
					ledger_control.updateQtyRecord(updateLedgerList);
					
					Log.i("", "After Update Purchase Qty in Ledger(Minus): "+ledger_control.select(ItemID, Date, Date).toString());
				}
			}
			
			/*//Update in Profit Table
			dbManager = new ProfitController(this);
			ProfitController profit_control = (ProfitController)dbManager;
			profit_list = new ArrayList<Object>();
			profit_list = profit_control.selectByVidItemidDate(VouID, ItemID, Date);
			
			Log.i("", "Profit List: "+profit_list.toString());
			
			List<Object> updateProfitList;
			
			if (profit_list.size() > 0) {
				//Update New Purchase Total Price in Profit Table 
				Profit profit = (Profit) profit_list.get(0);
				
				Log.i("", "Profit: "+profit.toString());
					//Update New Purchase Item Total Price 
					updateProfitList = new ArrayList<Object>();
					
					Integer newQty = Integer.valueOf(editText_qty.getText().toString());
					Integer newPurchasePrice = Integer.valueOf(editText_price.getText().toString());
					
					Integer profitAmt =  ( profit.getSalePrice() * newQty ) - ( profit.getMarginalPrice() * newQty);
					
					updateProfitList.add(new Profit(profit.getItemId(), profit.getDate(), Integer.valueOf(profit.getMarginalPrice())
							, newPurchasePrice, newQty, profitAmt, VouID));
					
					profit_control.updateTotalPriceRecord(updateProfitList);
					
					Log.i("", "After Update Sale Total in Profit Table: "+profit_control.selectByVidItemidDate(VouID, ItemID, Date).toString());
					
			}*/
			
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
						//Plus Stock Qty 
						Integer newStockQty = Integer.valueOf(iteml.getQty()) - differQty; 
						
						Log.i("", "new stock qty: "+newStockQty);
						
						//Update New Stock Qty 
						dbManager = new ItemListController(this);
						ItemListController control = (ItemListController)dbManager;
						item_list = new ArrayList<Object>();
						item_list.add(new ItemList(ItemID, newStockQty.toString()));
						control.updateNewStockQty(item_list);
						
						Log.i("", "After Update in Item Stk(Plus): "+control.select().toString());
						
					}else {
						
						//Minus to Stock Qty 
						Integer newStockQty = Integer.valueOf(iteml.getQty()) - differQty; 
						
						Log.i("", "new stock qty: "+newStockQty);
						
						//Update New Stock Qty 
						dbManager = new ItemListController(this);
						ItemListController control = (ItemListController)dbManager;
						item_list = new ArrayList<Object>();
						item_list.add(new ItemList(ItemID, newStockQty.toString()));
						control.updateNewStockQty(item_list);
						
						Log.i("", "After Update in Item Stk(Minus): "+item_control.select().toString());
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


