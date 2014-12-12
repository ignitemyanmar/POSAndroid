package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.PurchaseConfirmLvAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.PurchaseVoucher;
import com.smk.skalertmessage.SKToastMessage;

public class PurchaseConfirmListActivity extends SherlockActivity{

	private ActionBar actionBar;
	private TextView title;
	private Button change_mode, btn_confirm, btn_cancel;
	private TextView txt_vou_no, txt_date, txt_supplier;
	private ListView lv_purchase_confirm;
	private DatabaseManager dbManager;
	private List<Object> listVoucher;
	private String voucherNo, date, supplier_name;
	private PurchaseConfirmLvAdapter lvAdapter;
	private String salePrice;
	private EditText editTxt_sale_price;
	private RelativeLayout add_layout;
	private TextView txt_marginal_price;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_confirm_list);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Purchase Voucher Confirm");
		title.setText("အ၀ယ္ေဘာင္ခ်ာအတည္ျပဳျခင္း");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		txt_vou_no = (TextView)findViewById(R.id.txt_vou_no);
		txt_date  = (TextView)findViewById(R.id.txt_date);
		txt_supplier = (TextView)findViewById(R.id.txt_supplier);
		lv_purchase_confirm = (ListView) findViewById(R.id.lv_vou_detail);
		
		btn_confirm = (Button) findViewById(R.id.btnConfirm);
		btn_confirm.setOnClickListener(clickListener);
		
		btn_cancel = (Button) findViewById(R.id.btnCancel);
		btn_cancel.setOnClickListener(clickListener);
		
		Bundle bundle = getIntent().getExtras();
		voucherNo = bundle.getString("VoucherNo");
		date = bundle.getString("Date");
		supplier_name = bundle.getString("SupplierName");
		
		listVoucher = new ArrayList<Object>();
		
		txt_vou_no.setText("Vou No:  "+voucherNo);
		txt_date.setText("Date:  "+date);
		txt_supplier.setText("Supplier:  "+supplier_name);
		
		getVoucherDetail();
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_confirm)
			{
				confirmVoucher();
			}
			if (v == btn_cancel) {
				finish();
			}
		}
	};



	
	private void confirmVoucher() {
		// TODO Auto-generated method stub
		List<Object> purchaseVoucherList = new ArrayList<Object>();
		dbManager = new PurchaseVoucherController(this);
		PurchaseVoucherController control = (PurchaseVoucherController)dbManager;
		
		View view = null ; 
		
		Log.i("", "List items in voucher: "+listVoucher.toString());
		
		if(checkField()){
			
			for (int i = 0; i < listVoucher.size(); i++) {
				
				PurchaseVoucher pv = (PurchaseVoucher) listVoucher.get(i);
				
				view = (View) lv_purchase_confirm.getChildAt(i);
				
				TextView txt_marginal_price = (TextView) view.findViewById(R.id.txt_marginal_price);
				editTxt_sale_price = (EditText) view.findViewById(R.id.editTxt_sale_price);
				
				((PurchaseVoucher) listVoucher.get(i)).setVid(pv.getVid());
				((PurchaseVoucher) listVoucher.get(i)).setSupplierName(pv.getSupplierName());
				((PurchaseVoucher) listVoucher.get(i)).setItemid(pv.getItemid());
				((PurchaseVoucher) listVoucher.get(i)).setQty(pv.getQty());
				((PurchaseVoucher) listVoucher.get(i)).setPurchasePrice(pv.getPurchasePrice());
				((PurchaseVoucher) listVoucher.get(i)).setItemname(pv.getItemname());
				((PurchaseVoucher) listVoucher.get(i)).setItemtotal(pv.getItemtotal());
				((PurchaseVoucher) listVoucher.get(i)).setVdate(pv.getVdate());
				((PurchaseVoucher) listVoucher.get(i)).setGrandtotal(pv.getGrandtotal());
				((PurchaseVoucher) listVoucher.get(i)).setMarginalPrice(txt_marginal_price.getText().toString());
				
				salePrice = editTxt_sale_price.getText().toString();
				((PurchaseVoucher) listVoucher.get(i)).setSalePrice(salePrice);
				((PurchaseVoucher) listVoucher.get(i)).setStatus(1);
								
				//Add PUrchase Voucher
				purchaseVoucherList.add(listVoucher.get(i));
				
			}
			
				//Update Purchase Voucher in Purchase Table after confirm
				control.updatePurchaseConfirm(purchaseVoucherList);
				Log.i("", "After update Purchase Voucher: "+control.select(voucherNo));
				
				//Save Purchase Qty into Ledger Table 
				dbManager = new LedgerController(PurchaseConfirmListActivity.this);
				LedgerController ledgerControl = (LedgerController)dbManager;
				List<Object> ledgerList = new ArrayList<Object>();
				
				//Get Stock Items
				dbManager = new ItemListController(PurchaseConfirmListActivity.this);
				ItemListController itemListcontrol = (ItemListController)dbManager;
				List<Object> itemList = new ArrayList<Object>();
				
				List<Object> updateLedgerList = new ArrayList<Object>();
				
				for (int i = 0; i < purchaseVoucherList.size(); i++) {
					
					PurchaseVoucher pv = (PurchaseVoucher)purchaseVoucherList.get(i);
					
					//Get Item Object by ItemID
					itemList = itemListcontrol.select(pv.getItemid());
					if (itemList.size() > 0 && itemList != null) {
						ItemList iteml = (ItemList) itemList.get(0);

						//Select Ledger Table 
						List<Object> selectLedgerList = new ArrayList<Object>();
						selectLedgerList = ledgerControl.select(pv.getItemid(), pv.getVdate());
						
						Log.i("", "All in Ledger : "+ledgerControl.select().toString());
						
						//Update If item exist
						if (selectLedgerList.size() > 0) {
							Ledger ledger = (Ledger) selectLedgerList.get(0);
							
							Integer purchaseQty = 0;
							Integer totalPurchaseQty = 0;
							
							if (pv.getItemid().equals(ledger.getItemId())) {
								
								if (ledger.getPurchaseQty().equals("")) {
									purchaseQty = 0;
									totalPurchaseQty = Integer.valueOf(pv.getQty()) + purchaseQty;
									
								}else {
									purchaseQty = Integer.valueOf(ledger.getPurchaseQty());
									totalPurchaseQty = Integer.valueOf(pv.getQty()) + purchaseQty;
								}
								
								Integer newStkQty = (ledger.getOldStockQty() + totalPurchaseQty) - ledger.getSaleQty() + ledger.getReturnQty();
								
								updateLedgerList.add(new Ledger(pv.getItemid(), pv.getItemname(), pv.getVdate()
										, ledger.getOldStockQty(), totalPurchaseQty, ledger.getSaleQty(), newStkQty, ledger.getReturnQty()));
								ledgerControl.updateStockQty(updateLedgerList);
								
								Log.i("", "Ledger List after update: "+ledgerControl.select().toString());
								
								//break;
							}
							
					}else {
						
						Integer oldStkQty = 0;
						Integer newStkQty = 0;
						
						if (iteml.getQty().equals("")) {
							oldStkQty = 0;
							newStkQty = oldStkQty + Integer.valueOf(pv.getQty());
							
						}else {
							oldStkQty = Integer.valueOf(iteml.getQty());
							newStkQty = oldStkQty + Integer.valueOf(pv.getQty());
						}
						
						ledgerList.add(new Ledger(pv.getItemid(), pv.getItemname(), pv.getVdate()
								, oldStkQty, Integer.valueOf(pv.getQty()), 0, newStkQty, 0));
						
						}
					}
					
				}//Purchase Voucher Loop End	
				
				ledgerControl.save(ledgerList);
				
				Log.i("", "Ledger List after save: "+ledgerControl.select().toString());
				
				//Update Prices & stock qty into Itemlist Table
				for (int k = 0; k < purchaseVoucherList.size(); k++) {
					PurchaseVoucher pv = (PurchaseVoucher)purchaseVoucherList.get(k);
					
					//Get Item Object by ItemID
					List<Object> priceList = new ArrayList<Object>();
					itemList = itemListcontrol.select(pv.getItemid());
					
					if (itemList.size() > 0 && itemList != null) {
						
						ItemList iteml = (ItemList) itemList.get(0);
						
						if (pv.getItemid().equals(iteml.getItemId())) {
							
							String totalStockQty = null;
							
							if (iteml.getQty() == null || iteml.getQty().length() == 0) {
								
								pv.setOldStockQty("0");
								totalStockQty = pv.getQty();
							}else {
								
								pv.setOldStockQty(iteml.getQty());
								totalStockQty = String.valueOf(Integer.valueOf(pv.getQty()) + Integer.valueOf(iteml.getQty()));
							}
							
							//Update Notify Status
							Integer notifyStatus = 0;
							
							Log.i("", "NotifyQty: "+iteml.getNotifyQty());
							
							if (iteml.getNotifyQty() == null && iteml.getNotifyQty().equals("")) {
								
								notifyStatus = 0;
								
							}else {
								if (Integer.valueOf(totalStockQty) <= Integer.valueOf(iteml.getNotifyQty())) {
									notifyStatus = 1;
								}else {
									notifyStatus = 0;
								}
							}
							
							priceList.add(new ItemList(pv.getItemid(), pv.getItemname(), pv.getPurchasePrice()
									, pv.getSalePrice(), pv.getMarginalPrice(), totalStockQty, notifyStatus));
							
							itemListcontrol.update(priceList);
							
							Log.i("", "Stock after update: "+itemListcontrol.select(pv.getItemid()).toString());
						}
					}
			
				}//Stock Qty Increase Loop End	
				
				finish();
		}
				
	}


	private void getVoucherDetail() {
		// TODO Auto-generated method stub
		dbManager = new PurchaseVoucherController(PurchaseConfirmListActivity.this);
		PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = pvController.select(voucherNo);
		
		Log.i("", "Purchase List Voucher: "+listVoucher.toString());
		
		lvAdapter = new PurchaseConfirmLvAdapter(PurchaseConfirmListActivity.this, listVoucher);
		lv_purchase_confirm.setAdapter(lvAdapter);
		setListViewHeightBasedOnChildren(lv_purchase_confirm);
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
	
/*	public boolean checkFields() {
		
		
		if (editTxt_sale_price.getText().toString().length() == 0) {
			editTxt_sale_price.setError("Enter Sale Price");
			return false;
		}
		
		return true;
	}*/
	

	private boolean checkField() {
		
		boolean check = true;
		
		for (int i = 0; i < listVoucher.size(); i++) {
			View view = (View) lv_purchase_confirm.getChildAt(i);
			editTxt_sale_price = (EditText) view.findViewById(R.id.editTxt_sale_price);
			txt_marginal_price = (TextView) view.findViewById(R.id.txt_marginal_price);
			
			if(editTxt_sale_price.getText().length() == 0){
				editTxt_sale_price.setError("Enter Sale Price");
				check = false;
				
				editTxt_sale_price.requestFocus();
				
				break;
			}
			
			if(Integer.valueOf(editTxt_sale_price.getText().toString()) <= Integer.valueOf(txt_marginal_price.getText().toString())){
				editTxt_sale_price.setError("Check Sale Price");
				check = false;
				
				editTxt_sale_price.requestFocus();
				
				break;
			}
		}
		
		return check;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}
	
	
}

