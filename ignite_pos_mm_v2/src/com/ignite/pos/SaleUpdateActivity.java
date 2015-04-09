package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.pos.adapter.BuyerSpinnerAdapter;
import com.ignite.pos.adapter.CategoriesListAdapter;
import com.ignite.pos.adapter.ItemGridAdapter;
import com.ignite.pos.adapter.UpdateItemListAdapter;
import com.ignite.pos.adapter.SubCategoriesListAdapter;
import com.ignite.pos.adapter.UpdateItemListAdapter;
import com.ignite.pos.application.BaseActivity;
import com.ignite.pos.application.DeviceUtil;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.CreditBuyerController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.SaleHistoryController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.Credit;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.SaleHistory;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.SubCategory;
import com.ignite.pos.model.Supplier;
import com.smk.skalertmessage.SKToastMessage;

@SuppressLint("ShowToast")
public class SaleUpdateActivity  extends BaseActivity{

	private ActionBar actionBar;
	private ImageView icon_pos;
	private Button change_mode , categories , search ;
	private LinearLayout scanner_mode , picker_mode;
	private boolean clicked = false;
	private DatabaseManager dbManager;
	private List<Object> list_obj;
	private ListView lvitem_list;
	private EditText scan;
	private String ItemID;
	private List<Object> Cart_Item_List;
	private GridView grid_categories;
	private List<Object> sub_category_Listobj;
	private Category category;
	private SubCategory sub_category;
	private ItemList itemObj;
	private String buyerName;
	private String CatID, SubID;
	private TextView vouncherno ,login;
	private TextView priceTotal;
	private TextView plus, minus;
	private TextView deleteItem, Update;
	private TextView Discount;
	public static UpdateItemListAdapter itemAdapter;
	public String currentDate;
	private String AdminName;
	private String VoucherNo;
	List<Object> vouItemList;
	private TextView txt_panel_name;
	private String currentDateTime;
	private String currentTime;
	private EditText edt_discount_amount;
	private TextView txt_disc_show;
	private Button btn_discount_ok;
	private TextView btn_back;
	private TextView btn_done;
	private LinearLayout credit_mode;
	private TextView txt_total_credit_left_amt;
	private EditText edt_credit_pay_amt;
	private Buyer buyer;
	private List<Object> buyer_obj;
	private Spinner SP_buyername;
	protected Integer buyerID;
	private List<Object> creditList;
	private String BuyerNameStr;
	private String SaleDate;
	private TextView txt_sale_date;
	public static String creditPaidAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vouncher);
		
		//Get Admin Name
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		AdminName = pref.getString("admin_name", "-");
		
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		icon_pos = (ImageView)actionBar.getCustomView().findViewById(R.id.icon_pos);
		icon_pos.setVisibility(View.GONE);
		login = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		txt_panel_name = (TextView)actionBar.getCustomView().findViewById(R.id.txt_panel_name);
		txt_panel_name.setVisibility(View.VISIBLE);
		//txt_panel_name.setText("Update Sale");
		txt_panel_name.setText("အ ေရာင္း ေဘာင္ ခ်ာ ျပင္ျခင္း");
		
		//Get Admin Name 
		Log.i("", "Admin Name: "+AdminName);
		
		if (AdminName.equals("-")) {
			login.setText(AdminName);
			SKToastMessage.showMessage(getApplicationContext(), "Please log in with Admin account first!", SKToastMessage.WARNING);
		}else {
			login.setText(AdminName);
		}
		
		
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setOnClickListener(clickListener);
		categories = (Button)actionBar.getCustomView().findViewById(R.id.btnCategories);
		categories.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);		
		
		txt_sale_date = (TextView)findViewById(R.id.txt_today_date);
		scanner_mode = (LinearLayout)findViewById(R.id.scanner_mode);
		picker_mode = (LinearLayout)findViewById(R.id.picker_mode);
		scan = (EditText)findViewById(R.id.editText_scan);
		search = (Button)findViewById(R.id.btnSearch);
		SP_buyername = (Spinner)findViewById(R.id.sp_buyer_name);
		SP_buyername.setOnItemSelectedListener(buyernameClickListener);
		
		vouncherno = (TextView)findViewById(R.id.txt_vouncher_no);
		Cart_Item_List = new ArrayList<Object>();
		
		lvitem_list = (ListView)findViewById(R.id.lvDetails_vou);
		//Grand Total
		priceTotal = (TextView)findViewById(R.id.txt_grand_total);
		plus = (TextView)findViewById(R.id.btnPlus);
		minus = (TextView)findViewById(R.id.btnMinus);
		Discount = (TextView)findViewById(R.id.txtDiscount);
		edt_discount_amount = (EditText)findViewById(R.id.edt_discount_amount);
		txt_disc_show = (TextView)findViewById(R.id.txt_disc_show);
		btn_discount_ok = (Button)findViewById(R.id.btn_dsicount_ok);
		deleteItem = (TextView)findViewById(R.id.btnDelete_items);
		//deleteItem.setVisibility(View.GONE);
		Update = (TextView)findViewById(R.id.btnCheckout);
		//Update.setText("Update");
		
		//Credit Mode
		btn_back = (TextView)findViewById(R.id.btn_back);
		btn_done = (TextView)findViewById(R.id.btn_done);
		credit_mode =  (LinearLayout)findViewById(R.id.credit_mode);
		txt_total_credit_left_amt = (TextView)findViewById(R.id.txt_total_credit_left_amt);
		edt_credit_pay_amt = (EditText)findViewById(R.id.edt_credit_pay_amt);
		
		btn_discount_ok.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		plus.setOnClickListener(clickListener);
		minus.setOnClickListener(clickListener);
		deleteItem.setOnClickListener(clickListener);
		Update.setOnClickListener(clickListener);
		grid_categories = (GridView)findViewById(R.id.gvCategories);
		lvitem_list.setOnItemLongClickListener(itemLongClickListener);
		btn_back.setOnClickListener(clickListener);
		btn_done.setOnClickListener(clickListener);
		
		//Get Voucher No
		Bundle bundle = getIntent().getExtras();
		VoucherNo = bundle.getString("VoucherNo");
		BuyerNameStr = bundle.getString("BuyerName"); 
		SaleDate = bundle.getString("SaleDate"); 
		
		vouncherno.setText(VoucherNo);
		txt_sale_date.setText(changeDateString(SaleDate));
		
		Log.i("", "Buyer Name to update: "+BuyerNameStr);
		
		getBuyer();
		getVoucherItems();
		
		Log.i("", "Car Item lists + old sale price: "+Cart_Item_List.toString());
		
		itemAdapter = new UpdateItemListAdapter(this,Cart_Item_List);
		itemAdapter.setCallbackListiner(callback);
		lvitem_list.setAdapter(itemAdapter);
		setListViewHeightBasedOnChildren(lvitem_list);		
		
		if (Cart_Item_List.size() > 0) {
			//Discount.setText(((SaleVouncher)Cart_Item_List.get(0)).getDiscount());
			txt_disc_show.setText(((SaleVouncher)Cart_Item_List.get(0)).getDiscount());
			priceTotal.setText(((SaleVouncher)Cart_Item_List.get(0)).getTotal());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		
		//Date & Time Format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		currentDateTime = dateFormat.format(cal.getTime());
		//System.out.println("Current Date Time : " + dateFormat.format(cal.getTime()));
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		currentTime = timeFormat.format(new Date());
		
		getItems();
		
	}
	
	private void getItems(){
		if(!clicked)
		{
			clicked = true;
			//change_mode.setText("Change Scanner Mode");
			change_mode.setText("Scanner ႏွင့္ အသံုးျပဳရန္");
			categories.setVisibility(View.VISIBLE);
			getCategories();
			scanner_mode.setVisibility(LinearLayout.INVISIBLE);
			picker_mode.setVisibility(LinearLayout.VISIBLE);
			scanner_mode.setTranslationX(DeviceUtil.getInstance(SaleUpdateActivity.this).getWidth());
			picker_mode.setTranslationX(0);
		}
		else{
			clicked = false;
			//change_mode.setText("Change Picker Mode");
			change_mode.setText("ပစၥည္းမ်ားေရြး၍ အသံုးျပဳရန္");
			categories.setVisibility(View.GONE);
			scanner_mode.setVisibility(LinearLayout.VISIBLE);
			picker_mode.setVisibility(LinearLayout.INVISIBLE);
			scanner_mode.setTranslationX(0);
			picker_mode.setTranslationX(DeviceUtil.getInstance(SaleUpdateActivity.this).getWidth());
		}
	}
	
	private OnItemSelectedListener buyernameClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			buyer = (Buyer)buyer_obj.get(position);
			buyerName = buyer.getBuyerName();
			buyerID = buyer.getBuyerId();
			
			getVoucherCreditLeftAmount();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getVoucherItems() {
		// TODO Auto-generated method stub
		dbManager = new SaleVouncherController(this);
		SaleVouncherController svControl = (SaleVouncherController)dbManager;
		Cart_Item_List = svControl.selectRecordByVouID(VoucherNo);
		
		dbManager = new ItemListController(this);
		ItemListController itemControl = (ItemListController)dbManager;
		List<Object> itemL = new ArrayList<Object>();
		
		//Get old sale price from item table
		for (int i = 0; i < Cart_Item_List.size(); i++) {
			SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(i);
			itemL = itemControl.select(sv.getItemid());
			
			sv.setOld_sale_price(((ItemList)itemL.get(0)).getSalePrice());
		}
		
		oldVDate = ((SaleVouncher)Cart_Item_List.get(0)).getVdate();
		
		Log.i("", "Item List by Sale Vou ID: "+Cart_Item_List.toString());
		
	}

	private OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {


		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			View childView = (View) lvitem_list.getChildAt(position);
			LinearLayout lstItemView = (LinearLayout) childView.findViewById(R.id.lst_item_view);
			TextView txt_itemName = (TextView) lstItemView.findViewById(R.id.txtItem_name);
			String selected_item_name = txt_itemName.getText().toString(); 		
			removeItemFromList(position, selected_item_name);
			return true;
		}
	};
	
	
	/**
	 * method to remove list item
	 * @param position
	 * @param selected_item_name
	 */
    protected void removeItemFromList(int position, String selected_item_name) {
    	
        final int deletePosition = position;
        
        AlertDialog.Builder alert = new AlertDialog.Builder(
                SaleUpdateActivity.this);
    
       // alert.setTitle("Delete Item - "+selected_item_name+" ?");
		View dialogView = View.inflate(SaleUpdateActivity.this, R.layout.dialog_title, null);
		TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_dialog_title);
		//dialogTitle.setText("Add Prices | "+item_name);
		dialogTitle.setText(selected_item_name+" ကုိဖ်က္မွာလား ?");
		alert.setCustomTitle(dialogView);
        
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        	
        	List<Object> oldItemObj;
        	
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				SaleVouncher newSv = (SaleVouncher)Cart_Item_List.get(deletePosition);
				
				oldItemObj = new ArrayList<Object>();
				dbManager = new SaleVouncherController(SaleUpdateActivity.this);
				SaleVouncherController svControl = (SaleVouncherController)dbManager;
				oldItemObj = svControl.select(VoucherNo, newSv.getItemid());
				
				Log.i("", "Old Item Object in Sale Voucher: "+oldItemObj.toString());
				
				//If the deleted item exists in Old Sale Voucher
				if (oldItemObj.size() > 0 && oldItemObj != null) {
					
						SaleVouncher oldSv = (SaleVouncher) oldItemObj.get(0);
						
						//If Cart List ItemID is equals with ItemID in Database
						if (newSv.getItemid().equals(oldSv.getItemid())) {
							
							//Update in Sale Table
							dbManager = new SaleVouncherController(SaleUpdateActivity.this);
							SaleVouncherController sControl = (SaleVouncherController)dbManager;
							sControl.delete(oldItemObj);
							
							Log.i("", "Sale Voucher after delete Item: "+sControl.selectRecordByVouID(VoucherNo));
							
							//Save in Sale History (Delete)
							dbManager = new SaleHistoryController(SaleUpdateActivity.this);
							SaleHistoryController shControl = (SaleHistoryController)dbManager;
							List<Object> shList = new ArrayList<Object>();
							
							shList.add(new SaleHistory(oldSv.getVid(), oldSv.getItemid(), Integer.valueOf(oldSv.getQty())
									, 0, currentDateTime, login.getText().toString(), "delete"));
							
							shControl.save(shList);
							
							Log.i("", "After save in Sale History (Delete): "+shControl.selectRecordByVouID(oldSv.getVid()));
							
							//Update in Ledger Table 
							dbManager = new LedgerController(SaleUpdateActivity.this);
							LedgerController ledgerControl = (LedgerController)dbManager;
							List<Object> ledgerList = new ArrayList<Object>();
							ledgerList = ledgerControl.select(newSv.getItemid(), newSv.getVdate(), newSv.getVdate());
							
							Log.i("", "Ledger List: "+ledgerList.toString());
							
							List<Object> updateLedgerList;
							
							if (ledgerList.size() > 0) {
								//Update New Sale Qty in Ledger 
								Ledger ledger = (Ledger) ledgerList.get(0);
									
									//Minus to Sale Qty in Ledger 
									Integer newSaleQty = ledger.getSaleQty() - Integer.valueOf(newSv.getQty()); 
									
									Log.i("", "new sale qty: "+newSaleQty);
									
									//Get New Stock Qty 
									Integer newStockQty = ( ledger.getOldStockQty() + ledger.getPurchaseQty() ) - newSaleQty + ledger.getReturnQty();
									
									//Update New Sale Qty 
									updateLedgerList = new ArrayList<Object>();
									updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
											, ledger.getOldStockQty(), ledger.getPurchaseQty(), newSaleQty, newStockQty, ledger.getReturnQty()));
									ledgerControl.updateQtyRecord(updateLedgerList);
									
									Log.i("", "After Update Sale Qty in Ledger(Minus): "+ledgerControl.select(newSv.getItemid(), newSv.getVdate(), newSv.getVdate()).toString());
								}
							
							//Update in Profit Table
							dbManager = new ProfitController(SaleUpdateActivity.this);
							ProfitController profitControl = (ProfitController)dbManager;
							List<Object> profitObj = new ArrayList<Object>();
							profitObj = profitControl.selectByVidItemidDate(VoucherNo, newSv.getItemname(), newSv.getVdate());
							
							if (profitObj.size() > 0 && profitObj != null) {
								
								profitControl.delete(profitObj);
							}
							
							//Update in Item Stock Table 
							dbManager = new ItemListController(SaleUpdateActivity.this);
							ItemListController itemControl = (ItemListController)dbManager;
							List<Object> itemList = new ArrayList<Object>();
							itemList = itemControl.select(newSv.getItemid());
							
							Log.i("", "Item List: "+itemList.toString());
							
							if (itemList.size() > 0) {
								ItemList iteml = (ItemList)itemList.get(0);
											
								//Increase Stock Qty 
								Integer newStockQty = Integer.valueOf(iteml.getQty()) + Integer.valueOf(newSv.getQty()); 
												
								Log.i("", "new stock qty: "+newStockQty);
												
								//Update New Stock Qty 
								itemList.add(new ItemList(newSv.getItemid(), newStockQty.toString()));
								itemControl.updateNewStockQty(itemList);
												
								Log.i("", "After Update in Item Stock(Plus): "+itemControl.select(newSv.getItemid()).toString());
							}
						}//End If (check item id exist in old sale voucher)
				}
				
				Cart_Item_List.remove(deletePosition);
            	itemAdapter.notifyDataSetChanged();
            	setListViewHeightBasedOnChildren(lvitem_list);
            	
            	//Integer newGrandTotal = defaultGrandTotal() -  ( defaultGrandTotal() * Integer.valueOf(Discount.getText().toString()) / 100); 
    			priceTotal.setText(defaultGrandTotal()+"");
    			
    			if(Cart_Item_List.size() == 0){
    				priceTotal.setText("0");
    				//Discount.setText("0");
    			}
			}
		});
        
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
      
        alert.show();
        alert.setCancelable(true);
      
    }

	private void getBuyer()
	{
		dbManager = new BuyerController(this);
		BuyerController control = (BuyerController)dbManager;
		buyer_obj = new ArrayList<Object>();
		buyer_obj.addAll(control.select());
		
		if (buyer_obj != null && buyer_obj.size() > 0) {
			
			buyer_obj.add(new Buyer("other"));			
			SP_buyername.setAdapter(new BuyerSpinnerAdapter(this,buyer_obj));
			
			//Put the old customer name at the first of Spinner
			int i = 0;
			for(Object obj: buyer_obj){
				Buyer buyer = (Buyer) obj;
				
				Log.i("", "Buyer Name for spinner: "+BuyerNameStr);
				
				if(buyer.getBuyerName().equals(BuyerNameStr)){
					SP_buyername.setSelection(i); //Set the current selected buyer name
					break;
				}
				i++;
			}
		}else {
			buyer_obj.add(new Buyer("other", "", "", ""));
			SP_buyername.setAdapter(new BuyerSpinnerAdapter(this,buyer_obj));
			SKToastMessage.showMessage(SaleUpdateActivity.this, "You need to add buyer names ... ", SKToastMessage.WARNING);
		}
		
		Log.i("","Buyer List:" + buyer_obj.toString());
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == change_mode)
			{
				getItems();
			}
			if(v == search)
			{
				if (checkFields()) {
					getScannerItem(ItemID);
					scan.setText("");
					Log.i("","ItemID :" + ItemID);
				}
			}
			if(v == categories)
			{
				getCategories();
			}
			if(v == plus)
			{
				int discount_percent = Integer.valueOf(Discount.getText().toString()) + 1;
				
				if (Cart_Item_List.size() != 0) {
					
					//plus.setClickable(true);
					
					Discount.setText(String.valueOf(discount_percent));
					
					Integer discount_amount = ( defaultGrandTotal() * discount_percent ) / 100; 
					Integer discounted_total = defaultGrandTotal() - discount_amount ;
					//priceTotal.setText(discounted_total+"");
				} else {
					
					//plus.setClickable(false);
					warningAlertMM();
				}
			}
			if(v == minus)
			{
				int discount_percent = Integer.valueOf(Discount.getText().toString()) - 1;
				
				if (Cart_Item_List.size() != 0) {
					
					//minus.setClickable(true);
					
					if (discount_percent > -1) {
						Discount.setText(String.valueOf(discount_percent));
						
						Integer discount_amount = ( defaultGrandTotal() * discount_percent ) / 100; 
						Integer discounted_total = defaultGrandTotal() - discount_amount ;
						//priceTotal.setText(discounted_total+"");
					}
				} else {
					
					//minus.setClickable(false);
					warningAlertMM();
				}
				
			}
			if( v == deleteItem)
			{
				if (Cart_Item_List.size() != 0) {
					
					//deleteItem.setClickable(true);
					removeAllFromList();
	    			
				} else {
					
					//deleteItem.setClickable(false);
					warningAlertMM();
				}
				
			}
			if (v == btn_discount_ok) {
				if (edt_discount_amount.getText().length() == 0) {
					edt_discount_amount.setError("Enter discount amount!");
				}else {
					
					if (Cart_Item_List.size() > 0) {
						
						Integer discount_amount = Integer.valueOf(edt_discount_amount.getText().toString());
						
						//if (discount_amount < defaultGrandTotal()) {
							
							Integer discounted_total = defaultGrandTotal() - discount_amount;
							
							if (discounted_total <= 0) {
								SKToastMessage.showMessage(getApplicationContext(), "Discount amount must be less than total amount!", SKToastMessage.WARNING);
								//priceTotal.setText("0.00");
							}else{
								txt_disc_show.setText(edt_discount_amount.getText().toString());
								//priceTotal.setText(discounted_total+"");
							}
							
							edt_discount_amount.getText().clear();
						//}else {
						//	edt_discount_amount.setError("Check discount amount");
						//}						
						
					} else {
						warningAlertMM();
					}
				}
			}
			if(v == Update)
			{
				if (Cart_Item_List != null && Cart_Item_List.size() > 0) {
					
					if (txt_disc_show.getText() != null) {
						
						btn_back.setVisibility(View.VISIBLE);
						btn_done.setVisibility(View.VISIBLE);
						btn_done.setText("ျပင္ မည္");
						credit_mode.setVisibility(View.VISIBLE);
						
						deleteItem.setVisibility(View.GONE);
						Update.setVisibility(View.GONE);
						picker_mode.setVisibility(View.INVISIBLE);
						scanner_mode.setVisibility(View.INVISIBLE);
						change_mode.setEnabled(false);
						categories.setEnabled(false);
						
						getVoucherCreditLeftAmount();
						
					}else {
						SKToastMessage.showMessage(getApplicationContext(), "Enter discount amount [or] zero", SKToastMessage.WARNING);
					}
				}else {
					warningAlertMM();
				}				
			}
			if (v == btn_back) {
				deleteItem.setVisibility(View.VISIBLE);
				Update.setVisibility(View.VISIBLE);
				picker_mode.setVisibility(View.VISIBLE);
				scanner_mode.setVisibility(View.VISIBLE);
				change_mode.setEnabled(true);
				categories.setEnabled(true);
				
				btn_back.setVisibility(View.GONE);
				btn_done.setVisibility(View.GONE);
				credit_mode.setVisibility(View.INVISIBLE);
			}
			if (v == btn_done) {
				
				Integer voucherTotal = Integer.valueOf(priceTotal.getText().toString()) - Integer.valueOf(txt_disc_show.getText().toString());
				
				//Check the pay amount (Not to over the voucher total)
				if (checkPayAmountFields()) {
					creditPaidAmount = edt_credit_pay_amt.getText().toString();
					
					if ((voucherTotal - Integer.valueOf(creditPaidAmount)) > 0) {
						if (buyerName.equals("other")) {
							SKToastMessage.showMessage(SaleUpdateActivity.this, "Choose Buyer Name to allow Credit!", SKToastMessage.WARNING);
						}else {
							updateCreditAndVoucher();
						}
					}else {
						updateCreditAndVoucher();
					}
				}
			}
		}
	};
	
	/**
	 *  Get Buyer's Credit Left Amount by Voucher
	 */
	private void getVoucherCreditLeftAmount() {
		// TODO Auto-generated method stub
		String sale_vou_no = vouncherno.getText().toString(); 
		
		if (buyerID != null) {
			//Get Credit List by Buyer ID
			dbManager = new CreditBuyerController(SaleUpdateActivity.this);
			CreditBuyerController creditControl = (CreditBuyerController)dbManager;
			creditList = new ArrayList<Object>();
			creditList = creditControl.selectByVoucherID(sale_vou_no);
			
			Log.i("", "Credit List by voucher No: "+creditControl.select().toString());
			
			if (creditList != null && creditList.size() > 0) {								
				
				Credit credit = (Credit)creditList.get(0);
				
				String buyerName = ((Buyer)SP_buyername.getSelectedItem()).getBuyerName();
				
				if (credit.getBuyer_name().equals(buyerName)) {
					txt_total_credit_left_amt.setText("** ေဘာင္ ခ်ာနံပါတ္  "+sale_vou_no+" တြင္  "+buyerName+" ၏ ေႂကြးက်န္ေငြ :    "+credit.getCreditLeftAmount()+" က်ပ္");
				}else {
					txt_total_credit_left_amt.setText("** ေဘာင္ ခ်ာနံပါတ္  "+sale_vou_no+" တြင္  "+buyerName+" ၏ ေႂကြးက်န္ေငြ :    0 က်ပ္");
				}
			}else {
				txt_total_credit_left_amt.setText("** ေဘာင္ ခ်ာနံပါတ္  "+sale_vou_no+" တြင္  "+buyerName+" ၏ ေႂကြးက်န္ေငြ :    0 က်ပ္");
			}
		}else {
			Log.i("", "buyer id: null");
			txt_total_credit_left_amt.setText("** ေဘာင္ ခ်ာနံပါတ္  "+sale_vou_no+" တြင္   ၀ယ္ သူ  ၏ ေႂကြးက်န္ေငြ :     0 က်ပ္");
		}
	}
	
	//Update Credit & Sale Voucher
	private void updateCreditAndVoucher() {
		// TODO Auto-generated method stub
		updateCredit();
		updateVoucher();
		finish();
		
		edt_credit_pay_amt.getText().clear();
		
		deleteItem.setVisibility(View.VISIBLE);
		Update.setVisibility(View.VISIBLE);
		picker_mode.setVisibility(View.VISIBLE);
		scanner_mode.setVisibility(View.VISIBLE);
		change_mode.setEnabled(true);
		categories.setEnabled(true);
		
		btn_back.setVisibility(View.GONE);
		btn_done.setVisibility(View.GONE);
		credit_mode.setVisibility(View.INVISIBLE);
		
		getCategories();
	}
	
	/**
	 *  Update Credit Transaction 
	 */
	private void updateCredit() {
		// TODO Auto-generated method stub
		dbManager = new CreditBuyerController(this);
		CreditBuyerController creditControl = (CreditBuyerController)dbManager;
		List<Object> credits = new ArrayList<Object>();
		
		//Get Voucher Total after discount reduce 
		//Integer voucherTotal = Integer.valueOf(priceTotal.getText().toString()) - Integer.valueOf(txt_disc_show.getText().toString());
		if (creditList != null && creditList.size() > 0) {
			Integer toPay = ((Credit)creditList.get(0)).getCreditLeftAmount();
			Integer creditLeftAmount = toPay - Integer.valueOf(creditPaidAmount);
			
			if (creditLeftAmount >= 0) {
				
				credits.add(new Credit(buyerID, buyerName, vouncherno.getText().toString()
						, currentDate, toPay, Integer.valueOf(creditPaidAmount), creditLeftAmount));
				
				creditControl.save(credits);	
			}
		}

	}
	
	protected List<Object> item_list_obj;
	
	protected void removeAllFromList() {
		// TODO Auto-generated method stub
		
		List<Object> oldItemObj;
		
	        for (int i = 0; i < Cart_Item_List.size(); i++) {
	        	
	        	SaleVouncher newSv = (SaleVouncher)Cart_Item_List.get(i);
	        	
				oldItemObj = new ArrayList<Object>();
				dbManager = new SaleVouncherController(SaleUpdateActivity.this);
				SaleVouncherController svControl = (SaleVouncherController)dbManager;
				oldItemObj = svControl.select(VoucherNo, newSv.getItemid());
				
				Log.i("", "Old Item Object in Sale Voucher: "+oldItemObj.toString());
				
				//If the deleted items exists in Old Sale Voucher
				if (oldItemObj.size() > 0 && oldItemObj != null) {
					
						SaleVouncher oldSv = (SaleVouncher) oldItemObj.get(0);
						
						//If Cart List ItemID is equals with ItemID in Database
						if (newSv.getItemid().equals(oldSv.getItemid())) {
							
							//Update in Sale Table
							dbManager = new SaleVouncherController(SaleUpdateActivity.this);
							SaleVouncherController sControl = (SaleVouncherController)dbManager;
							sControl.delete(oldItemObj);
							
							Log.i("", "Sale Voucher after delete Item: "+sControl.selectRecordByVouID(VoucherNo));
							
							//Save in Sale History (Delete all items)
							dbManager = new SaleHistoryController(SaleUpdateActivity.this);
							SaleHistoryController shControl = (SaleHistoryController)dbManager;
							List<Object> shList = new ArrayList<Object>();
							
							shList.add(new SaleHistory(oldSv.getVid(), oldSv.getItemid(), Integer.valueOf(oldSv.getQty())
									, 0, currentDateTime, login.getText().toString(), "delete"));
							
							shControl.save(shList);
							
							Log.i("", "After save in Sale History (Delete All items): "+shControl.selectRecordByVouID(oldSv.getVid()));
							
							//Update in Ledger Table 
							dbManager = new LedgerController(SaleUpdateActivity.this);
							LedgerController ledgerControl = (LedgerController)dbManager;
							List<Object> ledgerList = new ArrayList<Object>();
							ledgerList = ledgerControl.select(newSv.getItemid(), newSv.getVdate(), newSv.getVdate());
							
							Log.i("", "Ledger List: "+ledgerList.toString());
							
							List<Object> updateLedgerList;
							
							if (ledgerList.size() > 0) {
								//Update New Sale Qty in Ledger 
								Ledger ledger = (Ledger) ledgerList.get(0);
									
									//Minus to Sale Qty in Ledger 
									Integer newSaleQty = ledger.getSaleQty() - Integer.valueOf(newSv.getQty()); 
									
									Log.i("", "new sale qty: "+newSaleQty);
									
									//Get New Stock Qty 
									Integer newStockQty = ( ledger.getOldStockQty() + ledger.getPurchaseQty() ) - newSaleQty + ledger.getReturnQty();
									
									//Update New Sale Qty 
									updateLedgerList = new ArrayList<Object>();
									updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
											, ledger.getOldStockQty(), ledger.getPurchaseQty(), newSaleQty, newStockQty, ledger.getReturnQty()));
									ledgerControl.updateQtyRecord(updateLedgerList);
									
									Log.i("", "After Update Sale Qty in Ledger(Minus): "+ledgerControl.select(newSv.getItemid(), newSv.getVdate(), newSv.getVdate()).toString());
								}
							
							//Update in Profit Table
							dbManager = new ProfitController(SaleUpdateActivity.this);
							ProfitController profitControl = (ProfitController)dbManager;
							List<Object> profitObj = new ArrayList<Object>();
							profitObj = profitControl.selectByVidItemidDate(VoucherNo, newSv.getItemname(), newSv.getVdate());
							
							if (profitObj.size() > 0 && profitObj != null) {
								
								profitControl.delete(profitObj);
							}
							
							//Update in Item Stock Table 
							dbManager = new ItemListController(SaleUpdateActivity.this);
							ItemListController itemControl = (ItemListController)dbManager;
							List<Object> itemList = new ArrayList<Object>();
							itemList = itemControl.select(newSv.getItemid());
							
							Log.i("", "Item List: "+itemList.toString());
							
							if (itemList.size() > 0) {
								ItemList iteml = (ItemList)itemList.get(0);
											
								//Increase Stock Qty 
								Integer newStockQty = Integer.valueOf(iteml.getQty()) + Integer.valueOf(newSv.getQty()); 
												
								Log.i("", "new stock qty: "+newStockQty);
												
								//Update New Stock Qty 
								itemList.add(new ItemList(newSv.getItemid(), newStockQty.toString()));
								itemControl.updateNewStockQty(itemList);
												
								Log.i("", "After Update in Item Stock(Plus): "+itemControl.select(newSv.getItemid()).toString());
							}
						}
				}//End of If
			}//End of For loop of cart list

			Cart_Item_List.clear();
			itemAdapter.notifyDataSetChanged();
			setListViewHeightBasedOnChildren(lvitem_list);
			
			if(Cart_Item_List.size() == 0){
				priceTotal.setText("0");
				txt_disc_show.setText("0");
				//Discount.setText("0");
			}
	}
	
	
	//private BundleListObjet bundleListObjet;
	//private List<Object> listItems;
	//private Integer stock_balance_qty;
	
	private void updateVoucher()
	{
		List<Object> saleVouncher = new ArrayList<Object>();
		dbManager = new SaleVouncherController(this);
		SaleVouncherController control = (SaleVouncherController)dbManager;
		
		View view = null ;
		
		for (int i = 0; i < Cart_Item_List.size(); i++) {
			view = (View) lvitem_list.getChildAt(i);
			
			TextView txt_item_name = (TextView) view.findViewById(R.id.txtItem_name);
			TextView txt_total = (TextView) view.findViewById(R.id.txtTotal);
			TextView txt_qty = (TextView) view.findViewById(R.id.txtQty);
			TextView txt_price = (TextView) view.findViewById(R.id.txtUnitPrice);
			((SaleVouncher)Cart_Item_List.get(i)).setVid(vouncherno.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setItemid(((SaleVouncher) Cart_Item_List.get(i)).getItemid());
			((SaleVouncher)Cart_Item_List.get(i)).setQty(txt_qty.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setPrice(txt_price.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setItemname(txt_item_name.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setItemtotal(txt_total.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setVdate(((SaleVouncher) Cart_Item_List.get(i)).getVdate());
			((SaleVouncher)Cart_Item_List.get(i)).setTotal(priceTotal.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setSalePerson(login.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setDiscount(txt_disc_show.getText().toString());
			
			//Add Sale Voucher
			saleVouncher.add((Object) Cart_Item_List.get(i));
		}
		
		//Update or Add in Sale Voucher of Sale Table
		for (int i = 0; i < saleVouncher.size(); i++) {
			
			SaleVouncher sv = (SaleVouncher) saleVouncher.get(i);
			
			List<Object> itemObj = new ArrayList<Object>();
			dbManager = new SaleVouncherController(SaleUpdateActivity.this);
			SaleVouncherController dbControl = (SaleVouncherController)dbManager;
			itemObj = dbControl.select(VoucherNo, sv.getItemid());
			
			Log.i("", "Sale Item Object from DB: "+itemObj.toString());
			
			if (itemObj != null && itemObj.size() > 0) {
				
				List<Object> updateObj = new ArrayList<Object>();
				updateObj.add(new SaleVouncher(VoucherNo, sv.getItemid()
						, sv.getItemname(), sv.getQty(), sv.getItemtotal()
						, priceTotal.getText().toString(), login.getText().toString()
						, txt_disc_show.getText().toString(), 1, sv.getFree_checked(), sv.getPrice()));
				
				dbControl.updateRecByVouIDItemID(updateObj);
				
				Log.i("", "Voucher after Update: "+dbControl.selectRecordByVouID(VoucherNo).toString());
				
				//Save in Sale History (Update)
				dbManager = new SaleHistoryController(SaleUpdateActivity.this);
				SaleHistoryController shControl = (SaleHistoryController)dbManager;
				List<Object> shList = new ArrayList<Object>();
				
				shList.add(new SaleHistory(sv.getVid(), sv.getItemid(), Integer.valueOf(sv.getOldQty())
						, Integer.valueOf(sv.getQty()), currentDateTime, login.getText().toString(), "update"));
				
				shControl.save(shList);
				
				Log.i("", "After save in Sale History (Update): "+shControl.selectRecordByVouID(sv.getVid()));
				
			}else {
				
				List<Object> itemObj2 = new ArrayList<Object>();
				itemObj2.add(sv);
				dbControl.save(itemObj2);
				
				Log.i("", "Voucher after Add New Item: "+dbControl.selectRecordByVouID(VoucherNo));
				
				//Save in Sale History (Add New)
				dbManager = new SaleHistoryController(SaleUpdateActivity.this);
				SaleHistoryController shControl = (SaleHistoryController)dbManager;
				List<Object> shList = new ArrayList<Object>();
				
				shList.add(new SaleHistory(sv.getVid(), sv.getItemid(), 0
						, Integer.valueOf(sv.getQty()), currentDateTime, login.getText().toString(), "add new"));
				
				shControl.save(shList);
				
				Log.i("", "After save in Sale History (Add New): "+shControl.selectRecordByVouID(sv.getVid()));
				
			}
		}//End For Loop of Cart Items in Sale Voucher
		//End Update or Add New Item in Sale Voucher 
		
			//Update in Ledger Table 
			List<Object> ledger_list;
			dbManager = new LedgerController(this);
			LedgerController ledger_control = (LedgerController)dbManager;
			ledger_list = new ArrayList<Object>();
			
			//Get Stock Items
			dbManager = new ItemListController(SaleUpdateActivity.this);
			ItemListController itemListcontrol = (ItemListController)dbManager;
			List<Object> listItem = new ArrayList<Object>();
			
			for (int i = 0; i < Cart_Item_List.size(); i++) {
				
				SaleVouncher sv = (SaleVouncher) Cart_Item_List.get(i);
				
				Log.i("", "Sale Voucher: "+sv.toString());
				
				ledger_list = ledger_control.selectByItemName(sv.getItemname(), sv.getVdate(), sv.getVdate());
				
				Log.i("", "Ledger List: "+ledger_list.toString());
				
				List<Object> updateLedgerList;
				
				if (ledger_list.size() > 0) {
					//Update New Sale Qty in Ledger 
					Ledger ledger = (Ledger) ledger_list.get(0);
					
					Integer differQty = Integer.valueOf(sv.getOldQty()) - Integer.valueOf(sv.getQty());
					
					if (differQty < 0) {
						
						//Plus to Sale Qty in Ledger 
						Integer newSaleQty = ledger.getSaleQty() - differQty; 
						
						Log.i("", "new sale qty: "+newSaleQty);
						
						//Get New Stock Qty 
						Integer newStockQty = ( ledger.getOldStockQty() + ledger.getPurchaseQty() ) - newSaleQty + ledger.getReturnQty();
						
						//Update New Sale Qty 
						updateLedgerList = new ArrayList<Object>();
						updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
								, ledger.getOldStockQty(), ledger.getPurchaseQty(), newSaleQty, newStockQty, ledger.getReturnQty()));
						ledger_control.updateQtyRecord(updateLedgerList);
						
						Log.i("", "After Update Sale Qty in Ledger(Plus): "+ledger_control.select().toString());
					}else {
						//Minus from Sale Qty in Ledger 
						Integer newSaleQty = ledger.getSaleQty() - differQty; 
						
						Log.i("", "new sale qty: "+newSaleQty);
						
						//Get New Stock Qty 
						Integer newStockQty = ( ledger.getOldStockQty() + ledger.getPurchaseQty() ) - newSaleQty + ledger.getReturnQty();
						
						//Update New Sale Qty 
						updateLedgerList = new ArrayList<Object>();
						updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
								, ledger.getOldStockQty(), ledger.getPurchaseQty(), newSaleQty, newStockQty, ledger.getReturnQty()));
						ledger_control.updateQtyRecord(updateLedgerList);
						
						Log.i("", "After Update Sale Qty in Ledger(Minus): "+ledger_control.select().toString());
					}
				}else {
					
					//Get Item Object by ItemID
					listItem = itemListcontrol.select(sv.getItemid());
					ItemList itemObj = (ItemList) listItem.get(0);
					
					List<Object> ledgerList = new ArrayList<Object>();
					
					Integer oldStkQty = 0;
					Integer newStkQty = 0;
					
					if (itemObj.getQty().equals("")) {
						oldStkQty = 0;
						newStkQty = oldStkQty - Integer.valueOf(sv.getQty());
					}else {
						oldStkQty = Integer.valueOf(itemObj.getQty());
						newStkQty = oldStkQty - Integer.valueOf(sv.getQty());
					}
					
					ledgerList.add(new Ledger(sv.getItemid(), sv.getItemname(), sv.getVdate()
							, oldStkQty, 0, Integer.valueOf(sv.getQty()), newStkQty, 0));
					
					ledger_control.save(ledgerList);
					Log.i("", "Ledger List after save: "+ledger_control.select(sv.getItemid(), sv.getVdate(), sv.getVdate()).toString());
				}
			}//End Loop for Ledger Update
			
			//Update in Profit Table
			List<Object> profit_list;
			dbManager = new ProfitController(this);
			ProfitController profit_control = (ProfitController)dbManager;
			profit_list = new ArrayList<Object>();
			
			for (int i = 0; i < saleVouncher.size(); i++) {
				
				SaleVouncher sv = (SaleVouncher) saleVouncher.get(i);
				
				profit_list = profit_control.selectByVidItemidDate(VoucherNo, sv.getItemname(), sv.getVdate());
				
				Log.i("", "Profit Obj: "+profit_list.toString());
				
				if (profit_list.size() > 0 && profit_list != null) {
					
					List<Object> updateProfitList;
					
					//Update New Sale Total Price in Profit Table 
					Profit profit = (Profit) profit_list.get(0);
					
					updateProfitList = new ArrayList<Object>();
							
					Integer newQty = Integer.valueOf(sv.getQty());
					
					Log.i("", "New Sale Qty in profit: "+newQty);
							
				//	Integer discount = Integer.valueOf(txt_disc_show.getText().toString()) / saleVouncher.size();
					Integer profitFinal = 0;
					Integer salePrice = 0;
					
					if (sv.getFree_checked() != null) {
						//if (sv.getFree_checked().equals("free")) {
							
							salePrice = 0;
							profitFinal = 0 - ( profit.getMarginalPrice() * newQty);
							//profitFinal = profitAmt - discount;
						//}
					}else {
							salePrice = Integer.valueOf(sv.getPrice());
							profitFinal = ( Integer.valueOf(sv.getPrice()) * newQty ) - ( profit.getMarginalPrice() * newQty);
							//profitFinal = profitAmt - discount;
					}
					
					updateProfitList.add(new Profit(profit.getItemId(), profit.getDate(), Integer.valueOf(profit.getMarginalPrice())
								, salePrice
								, newQty, profitFinal, profit.getVid(), profit.getItemName(), Integer.valueOf(txt_disc_show.getText().toString())));
							
					profit_control.updateTotalPriceRecord(updateProfitList);
							
					Log.i("", "After Update Sale Total in Profit: "+profit_control.selectByVidItemidDate(VoucherNo, sv.getItemname(), sv.getVdate()));
							
				}else {
					//Get Item Object by ItemID
					listItem = itemListcontrol.select(sv.getItemid());
					ItemList itemObj = (ItemList) listItem.get(0);
					
					List<Object> profitList = new ArrayList<Object>();
					
					//Integer discount = Integer.valueOf(txt_disc_show.getText().toString()) / saleVouncher.size();
					Integer profitFinal = 0;
					Integer salePrice = 0;
					
					if (sv.getFree_checked() != null) {
						if (sv.getFree_checked().equals("free")) {
							
							salePrice = 0;
							profitFinal = 0 - (Integer.valueOf(itemObj.getMarginalPrice()) * Integer.valueOf(sv.getQty()));
							//profitFinal = profitAmt - discount;
						}
					}else {
							salePrice = Integer.valueOf(sv.getPrice());
							profitFinal = Integer.valueOf(sv.getItemtotal()) - (Integer.valueOf(itemObj.getMarginalPrice()) * Integer.valueOf(sv.getQty()));
							//profitFinal = profitAmt - discount;
					}
					
					profitList.add(new Profit(sv.getItemid(), sv.getVdate(), Integer.valueOf(itemObj.getMarginalPrice())
							, salePrice
							, Integer.valueOf(sv.getQty()), profitFinal, sv.getVid(), sv.getItemname(), Integer.valueOf(txt_disc_show.getText().toString())));
					
					profit_control.save(profitList);
					Log.i("", "Profit List after Save: "+profit_control.selectByVidItemidDate(VoucherNo, sv.getItemname(), sv.getVdate()).toString());
				}
			}//End Loop for Profit Update
			
			//Update in Item Stock Table 
			List<Object> item_list = new ArrayList<Object>();
			dbManager = new ItemListController(this);
			ItemListController item_control = (ItemListController)dbManager;
			item_list = new ArrayList<Object>();
					
					//Items Loop
					for (int j = 0; j < Cart_Item_List.size(); j++) {
								
						SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(j);
								
						item_list = item_control.select(sv.getItemid());
								
						//Old Qty - New Qty 
						Log.i("", "old qty : "+sv.getOldQty());
						Log.i("", "new qty: "+sv.getQty());
									
						Integer differQty = Integer.valueOf(sv.getOldQty()) - Integer.valueOf(sv.getQty());
									
						Log.i("", "DifferQty: "+differQty);
									
						if (item_list.size() > 0) {
							ItemList iteml = (ItemList)item_list.get(0);
										
							if (differQty < 0){
							//Minus from Stock Qty 
							Integer newStockQty = Integer.valueOf(iteml.getQty()) + differQty; 
											
							Log.i("", "new stock qty: "+newStockQty);
											
							//Update New Stock Qty 
							item_list.add(new ItemList(sv.getItemid(), newStockQty.toString()));
							item_control.updateNewStockQty(item_list);
											
							Log.i("", "After Update in Item Stk(Minus): "+item_control.select(sv.getItemid()).toString());
											
							}else {
											
								//Plus to Stock Qty 
								Integer newStockQty = Integer.valueOf(iteml.getQty()) + differQty; 
											
								Log.i("", "new stock qty: "+newStockQty);
											
								//Update New Stock Qty 
								item_list.add(new ItemList(sv.getItemid(), newStockQty.toString()));
								item_control.updateNewStockQty(item_list);
											
								Log.i("", "After Update in Item Stk(Plus): "+item_control.select(sv.getItemid()).toString());
								}
							}
						}//End Update in Item Stock Table			
	}

	private void getScannerItem(String ItemID)
	{
		dbManager = new ItemListController(this);
		ItemListController item_controller = (ItemListController)dbManager;
		list_obj = new ArrayList<Object>();
		list_obj = item_controller.select(scan.getText().toString());
		
		List<SaleVouncher> cartItems = new ArrayList<SaleVouncher>();
		
		Log.i("","Item Stock List from DB :" + list_obj.toString());
		
		if (list_obj.size() > 0) {
			
			boolean isExist = false;
			
			//Stock Qty Control
			for (int i = 0; i < Cart_Item_List.size(); i++) {
				if(((ItemList)list_obj.get(0)).getItemId().equals(((SaleVouncher)Cart_Item_List.get(i)).getItemid()))
				{
					isExist = true;
					
					SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(i);
					
					Integer plusOne = Integer.valueOf(((SaleVouncher)Cart_Item_List.get(i)).getQty()) + 1 ; 
					
						ItemList itemList = (ItemList) list_obj.get(0);
						stock_qty = Integer.valueOf(itemList.getQty());
						
						if (plusOne > stock_qty) {
							SKToastMessage.showMessage(SaleUpdateActivity.this, "Not Enough Stock!", SKToastMessage.WARNING);
						}else {
							((SaleVouncher) Cart_Item_List.get(i)).setQty(plusOne.toString());
						}
					
						List<Object> cartL;
						//Calculate Total after check free items
						if (sv.getFree_checked() != null) {
							if (sv.getFree_checked().equals("free")) {
								
								View view2 = (View) lvitem_list.getChildAt(i);
								TextView txt_sale_price = (TextView) view2.findViewById(R.id.txtUnitPrice);										
								
								((SaleVouncher)Cart_Item_List.get(i)).setPrice(txt_sale_price.getText().toString());

							}else {
								((SaleVouncher)Cart_Item_List.get(i)).setPrice(itemObj.getSalePrice());

							}
						}else {
							((SaleVouncher)Cart_Item_List.get(i)).setPrice(itemObj.getSalePrice());
						}
						
						break;
				}
			}//For Loop of Cart Item List
			
			itemAdapter.notifyDataSetChanged();
			setListViewHeightBasedOnChildren(lvitem_list);
			priceTotal.setText(defaultGrandTotal()+"");
			
			if(!isExist){
				
				String qty = ((ItemList)list_obj.get(0)).getQty();
				
				if (((ItemList)list_obj.get(0)).getSalePrice() != null && qty != null) {
					
					if (((ItemList)list_obj.get(0)).getSalePrice().length() > 0 && Integer.valueOf(qty) > 0) {
						cartItems.add(new SaleVouncher(vouncherno.getText().toString(), buyerName, ((ItemList) list_obj.get(0)).getItemId()
								, ((ItemList)list_obj.get(0)).getItemName(), "1"
								, ((ItemList)list_obj.get(0)).getSalePrice(), ((ItemList)list_obj.get(0)).getCategoryId()
								, ((ItemList)list_obj.get(0)).getSubCategoryId(), "0", currentDate, "0", SaleLoginActivity.strname, "0", ((ItemList)list_obj.get(0)).getSalePrice()));
					}else {
						SKToastMessage.showMessage(getApplicationContext(), "Item No.("+scan.getText().toString()+") no have Stock!", SKToastMessage.INFO);
					}
				}else {
					SKToastMessage.showMessage(getApplicationContext(), "Item No.("+scan.getText().toString()+") no have Stock!", SKToastMessage.INFO);
				}
				
				Cart_Item_List.addAll(cartItems);
				
				itemAdapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(lvitem_list);
				priceTotal.setText(defaultGrandTotal()+"");
			}
		}else {
			SKToastMessage.showMessage(getApplicationContext(), "Item No.("+scan.getText().toString()+") doesn't have!", SKToastMessage.INFO);
		}
	}

	private void getCategories()
	{
		dbManager = new CategoryController(this);
		CategoryController category_controller = (CategoryController)dbManager;
		list_obj = new ArrayList<Object>();
		list_obj = category_controller.select();
		if(list_obj != null)
		{
			grid_categories.setAdapter(new CategoriesListAdapter(this, list_obj));
			grid_categories.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
			setGridViewHeightBasedOnChildren(grid_categories, 2);
			grid_categories.setOnItemClickListener(categoryClickListener);
		}
		
		if (list_obj.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(SaleUpdateActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Category Yet!");
			alert.show();
			alert.setCancelable(true);
		}
		
	}
	
	private OnItemClickListener categoryClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			// Log.i("TAG", "--> onItemClick listener...");    
			dbManager = new SubCategoryController(SaleUpdateActivity.this);
			category = (Category)list_obj.get(position);
			CatID = String.valueOf(category.getCategoryID());
			
			Log.i("TAG", "Category ID -->" + CatID);   
			
			SubCategoryController sub_control = (SubCategoryController)dbManager;
			sub_category_Listobj = new ArrayList<Object>();
			sub_category_Listobj = sub_control.select(CatID);
			
			Log.i("","Get SubCategory List :"+ sub_category_Listobj.toString());
			
			if(sub_category_Listobj != null && sub_category_Listobj.size()>0)
			{
				grid_categories.setAdapter(new SubCategoriesListAdapter(SaleUpdateActivity.this, sub_category_Listobj));
				grid_categories.setOnItemClickListener(subcategoryClickListener);
			}else{
				dbManager = new ItemListController(SaleUpdateActivity.this);
				ItemListController item_control = (ItemListController)dbManager;
				item_list_obj = new ArrayList<Object>();
				item_list_obj = item_control.select(CatID,"0");
				
				Log.i("","Get Item List :"+ item_list_obj.toString());
				
				if(item_list_obj != null && item_list_obj.size() > 0)
				{
					grid_categories.setAdapter(new ItemGridAdapter(SaleUpdateActivity.this, item_list_obj));
					
					grid_categories.setOnItemClickListener(itemClickListener);
				}else{
					SKToastMessage.showMessage(SaleUpdateActivity.this, "There is no item!.", SKToastMessage.WARNING);
				}
			}
		}
	};
	
	private OnItemClickListener subcategoryClickListener = new OnItemClickListener() {

	public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i("TAG", "--> onItemClick listener...");   
			
			sub_category = (SubCategory)sub_category_Listobj.get(position);
			SubID = String.valueOf(sub_category.getSubCategoryID());
			
			Log.i("TAG", "Sub CategoryID --> " + SubID);
			
			dbManager = new ItemListController(SaleUpdateActivity.this);
			ItemListController item_control = (ItemListController)dbManager;
			item_list_obj = new ArrayList<Object>();
			item_list_obj = item_control.select(CatID,SubID);
			
			Log.i("","Get Item List :"+ item_list_obj.toString());
			
			if(item_list_obj != null && item_list_obj.size() > 0)
			{
				grid_categories.setAdapter(new ItemGridAdapter(SaleUpdateActivity.this, item_list_obj));
				
				grid_categories.setOnItemClickListener(itemClickListener);
			}else{
				SKToastMessage.showMessage(SaleUpdateActivity.this, "There is no item!.", SKToastMessage.WARNING);
			}
		}
	};
	
	protected String oldVDate;
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		private List<Object> listItem;
		private Integer stock_qty;

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i("TAG", "--> onItemClick listener...");    

			itemObj = (ItemList)item_list_obj.get(position);
			ItemID = String.valueOf(itemObj.getItemId());
			
			Log.i("TAG", "ItemID --> " + ItemID);
			
			List<SaleVouncher> cartItems = new ArrayList<SaleVouncher>();
			
			if (itemObj != null) {
				
				boolean isExist = false;
				
				//Get Stock Qty
				dbManager = new ItemListController(SaleUpdateActivity.this);
				ItemListController controller = (ItemListController) dbManager;
				listItem = new ArrayList<Object>();
				listItem = controller.select(ItemID);
				
				//Stock Qty Control
				for (int i = 0; i < Cart_Item_List.size(); i++) {
					if(itemObj.getItemId().equals(((SaleVouncher)Cart_Item_List.get(i)).getItemid())){
						
						isExist = true;
						
						SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(i);
						
						Integer plusOne = Integer.valueOf(((SaleVouncher)Cart_Item_List.get(i)).getQty()) + 1 ; 

						if (listItem.size() > 0) {
							ItemList itemList = (ItemList) listItem.get(0);
							stock_qty = Integer.valueOf(itemList.getQty());
							
							if (plusOne > stock_qty) {
								SKToastMessage.showMessage(SaleUpdateActivity.this, "Not Enough Stock!", SKToastMessage.WARNING);
							}else {
								((SaleVouncher)Cart_Item_List.get(i)).setQty(plusOne.toString());
							}
						}
						
						List<Object> cartL;
						//Calculate Total after check free items
						if (sv.getFree_checked() != null) {
							//if (sv.getFree_checked().equals("free")) {
								
								View view2 = (View) lvitem_list.getChildAt(i);
								TextView txt_sale_price = (TextView) view2.findViewById(R.id.txtUnitPrice);										
								
								((SaleVouncher)Cart_Item_List.get(i)).setPrice(txt_sale_price.getText().toString());

							//}else {
								//((SaleVouncher)Cart_Item_List.get(i)).setPrice(itemObj.getSalePrice());

							//}
						}else {
							((SaleVouncher)Cart_Item_List.get(i)).setPrice(itemObj.getSalePrice());
						}
						
						break;
					}
				}//For Loop of Cart Item List
				
				itemAdapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(lvitem_list);
				priceTotal.setText(defaultGrandTotal()+"");
				
				//If no item yet
				if(!isExist){
						
					if (itemObj != null) {
						if (itemObj.getSalePrice().length() > 0 && itemObj.getSalePrice() != null) {
							
							cartItems.add(new SaleVouncher(vouncherno.getText().toString(), buyerName
									, itemObj.getItemId(), itemObj.getItemName(), "1", "0", itemObj.getSalePrice()
									, itemObj.getCategoryId(), itemObj.getSubCategoryId(), "0", oldVDate
									, "0", SaleLoginActivity.strname, "0", "0", "0", "", "", 0, 0, itemObj.getSalePrice()));
						}else {
							SKToastMessage.showMessage(SaleUpdateActivity.this, "Sale Price not available!", SKToastMessage.WARNING);
						}
					}else {
						SKToastMessage.showMessage(SaleUpdateActivity.this, "Sale Price not available!", SKToastMessage.WARNING);
					}
					
					Cart_Item_List.addAll(cartItems);
					Log.i("", "Cart Item List: "+Cart_Item_List.toString());

					itemAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(lvitem_list);
					priceTotal.setText(defaultGrandTotal()+"");
				}
			}
		}
	};

	private Integer defaultGrandTotal()
	{		
			Integer grandTotal = 0; 
			
			for (int i = 0; i < Cart_Item_List.size(); i++) {
				
				if (((SaleVouncher) Cart_Item_List.get(i)).getPrice().length() > 0) {
					Integer total = Integer.valueOf(((SaleVouncher) Cart_Item_List.get(i)).getPrice()) * Integer.valueOf(((SaleVouncher) Cart_Item_List.get(i)).getQty());
					grandTotal += total;
				}
			}
			
			return grandTotal;
	}
	
	private Integer stock_qty; 
	
	//Callback for Item Qty (+) (-)
	private UpdateItemListAdapter.Callback callback = new  UpdateItemListAdapter.Callback() {
		
		public void onPlusClick(Integer pos, Integer price) {
			
			//Grand Total after plus one
			//((SaleVouncher) Cart_Item_List.get(pos)).setQty(String.valueOf(Integer.valueOf(((SaleVouncher) Cart_Item_List.get(pos)).getQty()) + 1));
			//Integer disprice = price -  ( price * Integer.valueOf(Discount.getText().toString()) / 100); 
			Integer grandTotal = Integer.valueOf(priceTotal.getText().toString()) + price;
			
			priceTotal.setText(grandTotal+"");
		}
		
		public void onMinusClick(Integer pos, Integer price) {
			// TODO Auto-generated method stub
			
			//Grand Total after minus one
			((SaleVouncher) Cart_Item_List.get(pos)).setQty(String.valueOf(Integer.valueOf(((SaleVouncher) Cart_Item_List.get(pos)).getQty()) - 1));
			//Integer disprice = price -  ( price * Integer.valueOf(Discount.getText().toString()) / 100); 
			Integer grandTotal = Integer.valueOf(priceTotal.getText().toString()) - price; 
			
			priceTotal.setText(grandTotal+"");
		}
		
		public void onFreeClick(Integer pos, boolean isFreeChecked) {
			// TODO Auto-generated method stub
			
			if (isFreeChecked = true) {
				
				SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(pos);
				sv.setFree_checked("free");		
				
				Log.i("", "On Check Free: "+sv.getFree_checked());
				
				Integer grandTotal = 0; 
				
				//Calculate Grand Total 
				for (int j = 0; j < Cart_Item_List.size(); j++) {
					
					SaleVouncher sv2 = (SaleVouncher)Cart_Item_List.get(j);
					
							View view = (View) lvitem_list.getChildAt(j);
							TextView txt_sale_price = (TextView) view.findViewById(R.id.txtUnitPrice);
							TextView txt_qty = (TextView)view.findViewById(R.id.txtQty);
							
							Log.i("", "Sale Price (Update): "+Integer.valueOf(txt_sale_price.getText().toString()));
							Log.i("", "Qty update: "+Integer.valueOf(txt_qty.getText().toString()));
							
							Integer total = Integer.valueOf(txt_sale_price.getText().toString()) * Integer.valueOf(txt_qty.getText().toString());
							
							Log.i("", "Total amount: "+total);
							
							grandTotal += total;
							
				}
				
				Log.i("", "Grant Total (Update sale): "+grandTotal);
				
				priceTotal.setText(grandTotal+"");
				
			}else{
				SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(pos);
				sv.setFree_checked("-");

				Log.i("", "Grant Total after no_check (Update sale): "+defaultGrandTotal());
				priceTotal.setText(defaultGrandTotal()+"");
				
			}
			
		}

	};
	
	public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null || listAdapter.getCount() == 0) {
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
			rows = (int) (x + 1);
			totalHeight *= rows;
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);
		
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
	
	public boolean checkPayAmountFields() {
		if (edt_credit_pay_amt.getText().toString().length() == 0) {
			edt_credit_pay_amt.setError("Enter Pay amount");
			return false;
		}
		Integer voucherTotal = Integer.valueOf(priceTotal.getText().toString()) - Integer.valueOf(txt_disc_show.getText().toString());
		if (Integer.valueOf(edt_credit_pay_amt.getText().toString()) > voucherTotal) {
			edt_credit_pay_amt.setError("Over the Voucher Amount");
			return false;
		}
		
		return true;
	}
	
	public boolean checkFields() {
		if (scan.getText().toString().length() == 0) {
			scan.setError("Enter Item Code");
			return false;
		}
		
		return true;
	}
	
	private void warningAlertMM() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(SaleUpdateActivity.this);
		alert.setTitle("Warning");
		alert.setMessage("Please choose items!");
		alert.show();
		alert.setCancelable(true);
	}
	
}

	
