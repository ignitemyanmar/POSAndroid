package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.ActionBar;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.pos.adapter.BuyerSpinnerAdapter;
import com.ignite.pos.adapter.CategoriesListAdapter;
import com.ignite.pos.adapter.ItemGridAdapter;
import com.ignite.pos.adapter.ItemListAdapter;
import com.ignite.pos.adapter.SubCategoriesListAdapter;
import com.ignite.pos.application.BaseActivity;
import com.ignite.pos.application.DeviceUtil;
import com.ignite.pos.database.controller.BusTicketSaleController;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.CreditBuyerController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.BundleListObjet;
import com.ignite.pos.model.BusTicketSale;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.Credit;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.SubCategory;
import com.ignite.pos.model.Supplier;
import com.smk.skalertmessage.SKToastMessage;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("ShowToast")
public class SaleActivity  extends BaseActivity{

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
	private Spinner SP_buyername;
	private Buyer buyer;
	private List<Object> buyer_obj;
	private String buyerName;
	private String CatID, SubID;
	private TextView vouncherno ,login;
	private TextView priceTotal;
	private TextView plus, minus;
	private TextView deleteItem, CheckOut;
	private TextView Discount;
	private EditText edt_discount_amount;
	private Button btn_discount_ok;
	public static ItemListAdapter itemAdapter;
	public String currentDate;
	private VoucherAutoID autoId;
	private String auto_voucher_id;
	private TextView txt_panel_name;
	private TextView txt_disc_show;
	private TextView btn_back;
	private TextView btn_done;
	private LinearLayout credit_mode;
	private TextView txt_total_credit_left_amt;
	private EditText edt_credit_pay_amt;
	private Integer buyerID;
	public static String creditPaidAmount;
	private List<Object> creditList;
	private List<Object> busTicketList;
	private TextView txt_today_date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vouncher);
		
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		icon_pos = (ImageView)actionBar.getCustomView().findViewById(R.id.icon_pos);
		icon_pos.setVisibility(View.VISIBLE);
		login = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		login.setText(SaleLoginActivity.strname);
		//login.setText("Ignite");
		txt_panel_name = (TextView)actionBar.getCustomView().findViewById(R.id.txt_panel_name);
		txt_panel_name.setVisibility(View.GONE);
		//txt_panel_name.setText("Add New Sale");
		txt_panel_name.setText("အ ေရာင္း ေဘာင္ ခ်ာ အသစ္");
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setOnClickListener(clickListener);
		categories = (Button)actionBar.getCustomView().findViewById(R.id.btnCategories);
		categories.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);		
		
		txt_today_date = (TextView)findViewById(R.id.txt_today_date);
		scanner_mode = (LinearLayout)findViewById(R.id.scanner_mode);
		picker_mode = (LinearLayout)findViewById(R.id.picker_mode);
		scan = (EditText)findViewById(R.id.editText_scan);
		SP_buyername = (Spinner)findViewById(R.id.sp_buyer_name);
		SP_buyername.setOnItemSelectedListener(buyernameClickListener);
		search = (Button)findViewById(R.id.btnSearch);
		vouncherno = (TextView)findViewById(R.id.txt_vouncher_no);
		Cart_Item_List = new ArrayList<Object>();
		lvitem_list = (ListView)findViewById(R.id.lvDetails_vou);
		priceTotal = (TextView)findViewById(R.id.txt_grand_total);
		plus = (TextView)findViewById(R.id.btnPlus);
		minus = (TextView)findViewById(R.id.btnMinus);
		Discount = (TextView)findViewById(R.id.txtDiscount);
		edt_discount_amount = (EditText)findViewById(R.id.edt_discount_amount);
		txt_disc_show = (TextView)findViewById(R.id.txt_disc_show);
		btn_discount_ok = (Button)findViewById(R.id.btn_dsicount_ok);
		deleteItem = (TextView)findViewById(R.id.btnDelete_items);
		CheckOut = (TextView)findViewById(R.id.btnCheckout);
		
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
		CheckOut.setOnClickListener(clickListener);
		btn_back.setOnClickListener(clickListener);
		btn_done.setOnClickListener(clickListener);
		grid_categories = (GridView)findViewById(R.id.gvCategories);
		lvitem_list.setOnItemLongClickListener(itemLongClickListener);
		getBuyer();
		
		//Auto Voucher Id
		autoId = new VoucherAutoID(SaleActivity.this);
		auto_voucher_id = autoId.generateAutoID();
				
		vouncherno.setText(auto_voucher_id);
		
		itemAdapter = new ItemListAdapter(this,Cart_Item_List);
		itemAdapter.setCallbackListiner(callback);
		lvitem_list.setAdapter(itemAdapter);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		
		txt_today_date.setText(changeDateString(currentDate));
		
		clicked = false;
		getItems();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {

	    switch(item.getItemId()) {
	        case R.id.menu_settings:
	        	
	        	//Link to Easy Ticket
				Intent intent;
				PackageManager manager = getPackageManager();
				try {
					intent = manager.getLaunchIntentForPackage("com.ignite.mm.ticketing.agent");
					
				    if (intent == null){
				    	Toast.makeText(SaleActivity.this, "Not Found Application!", Toast.LENGTH_SHORT).show();
				    	throw new PackageManager.NameNotFoundException();
				    }else {
				    	intent.addCategory(Intent.CATEGORY_LAUNCHER);
				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					    startActivity(intent);
					}
				} catch (PackageManager.NameNotFoundException e) {
					Toast.makeText(SaleActivity.this, "Not Found Application!", Toast.LENGTH_SHORT).show();
				}
				
	        	return true;
   	   	}
		return false;  
	 }
	
	private OnItemSelectedListener buyernameClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			buyer = (Buyer)buyer_obj.get(position);
			buyerName = buyer.getBuyerName();
			buyerID = buyer.getBuyerId();
			
			getBuyerCreditLeftTotal();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
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
	
	// method to remove list item
    protected void removeItemFromList(int position, String selected_item_name) {
    	
        final int deletePosition = position;
        
        AlertDialog.Builder alert = new AlertDialog.Builder(
                SaleActivity.this);
    
        //alert.setTitle("Delete Item - "+selected_item_name+" ?");
        //alert.setTitle(selected_item_name+" ကုိဖ်က္မွာလား ?");
        
		View dialogView = View.inflate(SaleActivity.this, R.layout.dialog_title, null);
		TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_dialog_title);
		//dialogTitle.setText("Add Prices | "+item_name);
		dialogTitle.setText(selected_item_name+" ကုိဖ်က္မွာလား ?");
		alert.setCustomTitle(dialogView);
        
/*        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Cart_Item_List.remove(deletePosition);
            	itemAdapter.notifyDataSetChanged();
            	setListViewHeightBasedOnChildren(lvitem_list);
            	//Discount.setText("0");
            	Integer newGrandTotal = defaultGrandTotal() -  ( defaultGrandTotal() * Integer.valueOf(Discount.getText().toString()) / 100); 
    			priceTotal.setText(newGrandTotal+"");            	
            	
    			if(Cart_Item_List.size() == 0){
    				priceTotal.setText("0.00");
    				Discount.setText("0");
    			}
			}
		});*/
        
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Cart_Item_List.remove(deletePosition);
            	itemAdapter.notifyDataSetChanged();
            	setListViewHeightBasedOnChildren(lvitem_list);
            	//Discount.setText("0");
            	//Integer newGrandTotal = defaultGrandTotal() -  ( defaultGrandTotal() * Integer.valueOf(Discount.getText().toString()) / 100); 
    			priceTotal.setText(defaultGrandTotal()+"");            	
            	
    			if(Cart_Item_List.size() == 0){
    				priceTotal.setText("0");
    				Discount.setText("0");
    			}
			}
		});
        
/*        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});*/
        
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
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
		}else {
			buyer_obj.add(new Buyer("other", "", "", ""));
			SP_buyername.setAdapter(new BuyerSpinnerAdapter(this,buyer_obj));
			SKToastMessage.showMessage(SaleActivity.this, "You need to add buyer names ... ", SKToastMessage.WARNING);
		}
		
		
		Log.i("","Buyer List:" + buyer_obj.toString());
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
			scanner_mode.setTranslationX(DeviceUtil.getInstance(SaleActivity.this).getWidth());
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
			picker_mode.setTranslationX(DeviceUtil.getInstance(SaleActivity.this).getWidth());
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//clicked = false;
		//getItems();
		super.onResume();
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
			if (v == login)
			{
				login.setText(SaleLoginActivity.strname);
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
					
					//warningAlert();
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
					//warningAlert();
					warningAlertMM();
				}
				
			}
			if( v == deleteItem)
			{
				if (Cart_Item_List.size() != 0) {
					
					//deleteItem.setClickable(true);
					
					Cart_Item_List.clear();
					itemAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(lvitem_list);
	    			if(Cart_Item_List.size() == 0){
	    				priceTotal.setText("0");
	    				txt_disc_show.setText("0");
	    				Discount.setText("0");
	    			}
				} else {
					
					//deleteItem.setClickable(false);
					//warningAlert();
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
			if(v == CheckOut)
			{
				if (Cart_Item_List != null && Cart_Item_List.size() > 0) {
					
					if (txt_disc_show.getText() != null) {
						
						btn_back.setVisibility(View.VISIBLE);
						btn_done.setVisibility(View.VISIBLE);
						credit_mode.setVisibility(View.VISIBLE);
						
						deleteItem.setVisibility(View.GONE);
						CheckOut.setVisibility(View.GONE);
						picker_mode.setVisibility(View.INVISIBLE);
						scanner_mode.setVisibility(View.INVISIBLE);
						change_mode.setEnabled(false);
						categories.setEnabled(false);
						
						getBuyerCreditLeftTotal();
						
					}else {
						SKToastMessage.showMessage(getApplicationContext(), "Enter discount amount [or] zero", SKToastMessage.WARNING);
					}
				}else {
					warningAlertMM();
				}
			}
			if (v == btn_back) {
				deleteItem.setVisibility(View.VISIBLE);
				CheckOut.setVisibility(View.VISIBLE);
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
					
					//If it is Credit
					if ((voucherTotal - Integer.valueOf(creditPaidAmount)) > 0) {
						if (buyerName.equals("other")) {
							SKToastMessage.showMessage(SaleActivity.this, "Choose Buyer Name to allow Credit!", SKToastMessage.WARNING);
						}else {
							saveAndShowSlip();
						}
					}else {
						saveAndShowSlip();
					}
				}
			}
		}

	};
	
	/**
	 *  Get Buyer's Credit Left Total Amount
	 */
	private void getBuyerCreditLeftTotal() {
		// TODO Auto-generated method stub
		if (buyerID != null) {
			//Get Credit List by Buyer ID
			dbManager = new CreditBuyerController(SaleActivity.this);
			CreditBuyerController creditControl = (CreditBuyerController)dbManager;
			creditList = new ArrayList<Object>();
			creditList = creditControl.selectGroupByVoucher(buyerID.toString());
			
			Log.i("", "Credit List by buyer id (groupby voucher id): "+creditControl.select().toString());
			
			if (creditList != null && creditList.size() > 0) {
				
				Integer creditLeftTotal = 0;
				
				for (int i = 0; i < creditList.size(); i++) {
					Credit credit = (Credit)creditList.get(i);
					creditLeftTotal += credit.getCreditLeftAmount();
				}
				
				txt_total_credit_left_amt.setText("** "+buyerName+" ၏ ေႂကြးက်န္ စုစုေပါင္း  :    "+creditLeftTotal+" က်ပ္");
			}else {
				txt_total_credit_left_amt.setText("** "+buyerName+" ၏ ေႂကြးက်န္ စုစုေပါင္း  :    0 က်ပ္");
			}
		}else {
			Log.i("", "buyer id: null");
			txt_total_credit_left_amt.setText("** ၀ယ္ သူ ၏ ေႂကြးက်န္ စုစုေပါင္း :    0 က်ပ္");
		}
	}
	
	/**
	 * Save Credit & Sale Voucher - Show Voucher slip 
	 */
	private void saveAndShowSlip() {
		// TODO Auto-generated method stub
		saveCredit();
		saveVouncher();
		
		//Go to Voucher Slip							
		startActivity(new Intent(getApplicationContext(), 
				VoucherSlipActivity.class).putExtra("saleVoucher", new Gson().toJson(bundleListObjet)));
		
		edt_credit_pay_amt.getText().clear();
		
		deleteItem.setVisibility(View.VISIBLE);
		CheckOut.setVisibility(View.VISIBLE);
		picker_mode.setVisibility(View.VISIBLE);
		scanner_mode.setVisibility(View.VISIBLE);
		change_mode.setEnabled(true);
		categories.setEnabled(true);
		
		btn_back.setVisibility(View.GONE);
		btn_done.setVisibility(View.GONE);
		credit_mode.setVisibility(View.INVISIBLE);
		
		getCategories();
	}
	
	private BundleListObjet bundleListObjet;
	private List<Object> listItems;
	private Integer stock_balance_qty;
	protected List<Object> item_list_obj;
	
	/*
	 *  Save Sale Voucher
	 */
	private void saveVouncher()
	{
		List<Object> saleVouncher = new ArrayList<Object>();
		dbManager = new SaleVouncherController(this);
		SaleVouncherController control = (SaleVouncherController)dbManager;
		
		View view = null ;
		bundleListObjet = new BundleListObjet();
		
		for (int i = 0; i < Cart_Item_List.size(); i++) {
			
			view = (View) lvitem_list.getChildAt(i);
			
			TextView txt_item_name = (TextView) view.findViewById(R.id.txtItem_name);
			TextView txt_total = (TextView) view.findViewById(R.id.txtTotal);
			TextView txt_qty = (TextView) view.findViewById(R.id.txtQty);
			TextView txt_price = (TextView) view.findViewById(R.id.txtUnitPrice);
			((SaleVouncher) Cart_Item_List.get(i)).setVid(vouncherno.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setCusname(buyerName);
			((SaleVouncher) Cart_Item_List.get(i)).setItemid(((SaleVouncher) Cart_Item_List.get(i)).getItemid());
			((SaleVouncher) Cart_Item_List.get(i)).setQty(txt_qty.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setPrice(txt_price.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setItemname(txt_item_name.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setItemtotal(txt_total.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setVdate(currentDate);
			((SaleVouncher) Cart_Item_List.get(i)).setTotal(priceTotal.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setSalePerson(login.getText().toString());
			((SaleVouncher) Cart_Item_List.get(i)).setDiscount(txt_disc_show.getText().toString());
			//((SaleVouncher) Cart_Item_List.get(i)).setOld_sale_price(old_sale_price);
			
			bundleListObjet.getSaleVouncher().add((SaleVouncher) Cart_Item_List.get(i));
			
			//Add Sale Voucher
			saleVouncher.add((Object) Cart_Item_List.get(i));
		}
		
		//Save On Sale Table
		control.save(saleVouncher);
		Log.i("", "Sale Voucher List after Save: " + control.selectRecordByVouID(vouncherno.getText().toString()).toString());
		
		//Save Sale Qty into Ledger Table 
		dbManager = new LedgerController(SaleActivity.this);
		LedgerController ledgerControl = (LedgerController)dbManager;
		List<Object> ledgerList = new ArrayList<Object>();
		
		//Get Stock Items
		dbManager = new ItemListController(SaleActivity.this);
		ItemListController itemListcontrol = (ItemListController)dbManager;
		List<Object> listItem = new ArrayList<Object>();
		
		List<Object> updateLedgerList = new ArrayList<Object>();
		
		for (int i = 0; i < saleVouncher.size(); i++) {
			
			SaleVouncher sv = (SaleVouncher)saleVouncher.get(i);
			
			//Get Item Object by ItemID
			listItem = itemListcontrol.select(sv.getItemid());
			ItemList itemObj = (ItemList) listItem.get(0);
			
			//Select Ledger Table 
			List<Object> selectLedgerList = new ArrayList<Object>();
			selectLedgerList = ledgerControl.select(sv.getItemid(), sv.getVdate());
			
			Log.i("", "All in Ledger : "+ledgerControl.select().toString());
			
			//Update If item exist
			if (selectLedgerList.size() > 0) {
					
						Ledger ledger = (Ledger) selectLedgerList.get(0);
						
						Integer saleQty = 0;
						Integer totalSaleQty = 0;
						
						if (sv.getItemid().equals(ledger.getItemId())) {
							
							if (ledger.getSaleQty().equals("")) {
								saleQty = 0;
								totalSaleQty = Integer.valueOf(sv.getQty()) + saleQty;
							}else {
								saleQty = Integer.valueOf(ledger.getSaleQty());
								totalSaleQty = Integer.valueOf(sv.getQty()) + saleQty;
							}
							
							Integer newStkQty = (ledger.getOldStockQty() + ledger.getPurchaseQty()) - totalSaleQty + ledger.getReturnQty();
							
							updateLedgerList.add(new Ledger(sv.getItemid(), sv.getItemname(), sv.getVdate()
									, ledger.getOldStockQty(), ledger.getPurchaseQty(), totalSaleQty, newStkQty, ledger.getReturnQty()));
							ledgerControl.updateStockQty(updateLedgerList);
							
							Log.i("", "Ledger List after update: "+ledgerControl.select().toString());
							
						}
				
		}else {
			
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
			
		  }
		}//Sale Voucher Loop End
		
		//Save in Ledger Table
		ledgerControl.save(ledgerList);
		Log.i("", "Ledger List after save: "+ledgerControl.select().toString());
		
		//Save in Profit Table 
		dbManager = new ProfitController(SaleActivity.this);
		ProfitController profitControl = (ProfitController)dbManager;
		List<Object> profitList = new ArrayList<Object>();
		
		for (int i = 0; i < saleVouncher.size(); i++) {
			
			SaleVouncher sv = (SaleVouncher)saleVouncher.get(i);
			
			//Get Item Object by ItemID
			listItem = itemListcontrol.select(sv.getItemid());
			ItemList itemObj = (ItemList) listItem.get(0);
			
			Log.i("", "Marginal Price: "+itemObj.getMarginalPrice());
			
			//Integer discount = Integer.valueOf(txt_disc_show.getText().toString()) / saleVouncher.size();
			Integer profitAmt = 0;
			
			if (sv.getFree_checked() != null) {
				if (sv.getFree_checked().equals("free")) {
					
					profitAmt = 0 - (Integer.valueOf(itemObj.getMarginalPrice()) * Integer.valueOf(sv.getQty()));
					//profit = profitAmt - discount;
				}
			}else {
					profitAmt = Integer.valueOf(sv.getItemtotal()) - (Integer.valueOf(itemObj.getMarginalPrice()) * Integer.valueOf(sv.getQty()));
					//profit = profitAmt - discount;
			}

					
			profitList.add(new Profit(sv.getItemid(), sv.getVdate(), Integer.valueOf(itemObj.getMarginalPrice())
					, Integer.valueOf(sv.getPrice())
					, Integer.valueOf(sv.getQty()), profitAmt, sv.getVid(), sv.getItemname(), Integer.valueOf(txt_disc_show.getText().toString())));
		}
		
		profitControl.save(profitList);
		Log.i("", "Profit List after Save: "+profitControl.select().toString());
		
		//Get Item Stock Qty & decrease stock qty after sale
		List<Object> stockList = new ArrayList<Object>();
		dbManager = new ItemListController(SaleActivity.this);
		ItemListController itemControl = (ItemListController)dbManager;
		List<Object> itemList = new ArrayList<Object>();
		
			for (int i = 0; i < saleVouncher.size(); i++) {
				
				SaleVouncher sv = (SaleVouncher)saleVouncher.get(i);
				itemList = itemControl.select(sv.getItemid());
				ItemList itemL = (ItemList)itemList.get(0);
				
				Integer balanceStkQty = Integer.valueOf(itemL.getQty()) - Integer.valueOf(sv.getQty());
				
				Log.i("", "Item name: "+sv.getItemname()+", Balance Qty : "+balanceStkQty);
				
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
				
				stockList.add(new ItemList(sv.getItemid(), sv.getItemname(), itemL.getPurchasePrice()
						, itemL.getSalePrice(), itemL.getMarginalPrice(), balanceStkQty.toString(), notifyStatus));
				
				itemControl.update(stockList);
				
				Log.i("", "Stock List after Update : "+itemControl.select(sv.getItemid()).toString());
			}
		
		//Make Auto Voucher ID after check out
		auto_voucher_id = autoId.generateAutoID();
		vouncherno.setText(auto_voucher_id);
		
		Cart_Item_List.clear();
		txt_disc_show.setText("0");
		
		priceTotal.setText("0");
		Discount.setText("0");
		itemAdapter.notifyDataSetChanged();
		setListViewHeightBasedOnChildren(lvitem_list);
	}
	
	/**
	 *  Save Credit Transaction 
	 */
	private void saveCredit() {
		// TODO Auto-generated method stub
		dbManager = new CreditBuyerController(this);
		CreditBuyerController creditControl = (CreditBuyerController)dbManager;
		List<Object> creditList = new ArrayList<Object>();
		
		//Get Voucher Total after discount reduce 
		Integer voucherTotal = Integer.valueOf(priceTotal.getText().toString()) - Integer.valueOf(txt_disc_show.getText().toString());
		Integer creditLeftAmount = voucherTotal - Integer.valueOf(creditPaidAmount);
		
		if (creditLeftAmount > 0) {
			creditList.add(new Credit(buyerID, buyerName, vouncherno.getText().toString()
					, currentDate, voucherTotal, Integer.valueOf(creditPaidAmount), creditLeftAmount));
			creditControl.save(creditList);	
		}
		
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
							//SKToastMessage.showMessage(SaleActivity.this, "Not Enough Stock!", SKToastMessage.WARNING);
							SKToastMessage.showMessage(SaleActivity.this, "Not Enough Stock!", SKToastMessage.WARNING);
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
			
			AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
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
			dbManager = new SubCategoryController(SaleActivity.this);
			category = (Category)list_obj.get(position);
			CatID = String.valueOf(category.getCategoryID());
			
			Log.i("TAG", "Category ID -->" + CatID);   
			
			SubCategoryController sub_control = (SubCategoryController)dbManager;
			sub_category_Listobj = new ArrayList<Object>();
			sub_category_Listobj = sub_control.select(CatID);
			
			Log.i("","Get SubCategory List :"+ sub_category_Listobj.toString());
			
			if(sub_category_Listobj != null && sub_category_Listobj.size()>0)
			{
				grid_categories.setAdapter(new SubCategoriesListAdapter(SaleActivity.this, sub_category_Listobj));
				grid_categories.setOnItemClickListener(subcategoryClickListener);
			}else{
				dbManager = new ItemListController(SaleActivity.this);
				ItemListController item_control = (ItemListController)dbManager;
				item_list_obj = new ArrayList<Object>();
				item_list_obj = item_control.select(CatID,"0");
				
				Log.i("","Get Item List :"+ item_list_obj.toString());
				
				if(item_list_obj != null && item_list_obj.size() > 0)
				{
					grid_categories.setAdapter(new ItemGridAdapter(SaleActivity.this, item_list_obj));
					
					grid_categories.setOnItemClickListener(itemClickListener);
				}else{
					//SKToastMessage.showMessage(SaleActivity.this, "There is no item!.", SKToastMessage.WARNING);
					SKToastMessage.showMessage(SaleActivity.this, "There is no item!", SKToastMessage.WARNING);
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
			
			dbManager = new ItemListController(SaleActivity.this);
			ItemListController item_control = (ItemListController)dbManager;
			item_list_obj = new ArrayList<Object>();
			item_list_obj = item_control.select(CatID,SubID);
			
			Log.i("","Get Item List :"+ item_list_obj.toString());
			
			if(item_list_obj != null && item_list_obj.size() > 0)
			{
				grid_categories.setAdapter(new ItemGridAdapter(SaleActivity.this, item_list_obj));
				
				grid_categories.setOnItemClickListener(itemClickListener);
			}else{
				//SKToastMessage.showMessage(SaleActivity.this, "There is no item!.", SKToastMessage.WARNING);
				SKToastMessage.showMessage(SaleActivity.this, "There is no 'Item' yet!", SKToastMessage.WARNING);
			}
		}
	};
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		private List<Object> listItem;
		private Integer stock_qty;

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i("TAG", "--> onItemClick listener...");    
			dbManager = new ItemListController(SaleActivity.this);
			itemObj = (ItemList)item_list_obj.get(position);
			ItemID = String.valueOf(itemObj.getItemId());
			
			Log.i("TAG", "ItemID --> " + ItemID);
			
			List<SaleVouncher> cartItems = new ArrayList<SaleVouncher>();
			
			if (itemObj != null) {
				
				boolean isExist = false;
				
				//Get Stock Qty
				dbManager = new ItemListController(SaleActivity.this);
				ItemListController controller = (ItemListController) dbManager;
				listItem = new ArrayList<Object>();
				listItem = controller.select(ItemID);
				
				//Stock Qty Control
				for (int i = 0; i < Cart_Item_List.size(); i++) {
					
					if(itemObj.getItemId().equals(((SaleVouncher)Cart_Item_List.get(i)).getItemid())){
						
						isExist = true;
						
						SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(i);
						
/*						if (sv.getFree_checked() != null) {
							if (sv.getFree_checked().equals("free")) {
								SKToastMessage.showMessage(getApplicationContext(), itemObj.getItemName()+" is free!", SKToastMessage.WARNING);
								sv.setPrice(0+"");
								sv.setTotal(0+"");
								priceTotal.setText(defaultGrandTotal()+"");
							}
						}*/
						//if(sv.getFree_checked() == null || sv.getFree_checked().equals("-")) {
								
								Integer plusOne = Integer.valueOf(((SaleVouncher)Cart_Item_List.get(i)).getQty()) + 1 ; 
								
								if (listItem.size() > 0) {
									ItemList itemList = (ItemList) listItem.get(0);
									stock_qty = Integer.valueOf(itemList.getQty());
									
									if (plusOne > stock_qty) {
										//SKToastMessage.showMessage(SaleActivity.this, "Not Enough Stock!", SKToastMessage.WARNING);
										SKToastMessage.showMessage(SaleActivity.this, "Not Enough Stock!", SKToastMessage.WARNING);
									}else {
										((SaleVouncher)Cart_Item_List.get(i)).setQty(plusOne.toString());
										//priceTotal.setText(defaultGrandTotal()+"");
									}
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
						
					if (itemObj != null) {
						if (itemObj.getSalePrice().length() > 0 && itemObj.getSalePrice() != null) {
							cartItems.add(new SaleVouncher(vouncherno.getText().toString(), buyerName, itemObj.getItemId()
									, itemObj.getItemName(), "1", itemObj.getSalePrice()
									, itemObj.getCategoryId(), itemObj.getSubCategoryId()
									, "0", currentDate, "0", SaleLoginActivity.strname, "0", itemObj.getSalePrice()));
						}else {
							//SKToastMessage.showMessage(SaleActivity.this, "Pls input Sale Price in New Purchase Voucher", SKToastMessage.WARNING);
							SKToastMessage.showMessage(SaleActivity.this, "Sale Price not available!", SKToastMessage.WARNING);
						}
					}else {
						//SKToastMessage.showMessage(SaleActivity.this, "Pls input Sale Price in New Purchase Voucher", SKToastMessage.WARNING);
						SKToastMessage.showMessage(SaleActivity.this, "Sale Price not available!", SKToastMessage.WARNING);
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
				
				Log.i("", "Price of Free: "+Integer.valueOf(((SaleVouncher) Cart_Item_List.get(i)).getPrice()));
				Integer total = Integer.valueOf(((SaleVouncher) Cart_Item_List.get(i)).getPrice()) * Integer.valueOf(((SaleVouncher) Cart_Item_List.get(i)).getQty());
				grandTotal += total;
				
			}
			
			return grandTotal;
			
	}
	
	private List<Object> listItem;
	private Integer stock_qty; 
	
	private ItemListAdapter.Callback callback = new  ItemListAdapter.Callback() {
		
		public void onPlusClick(Integer pos, Integer price) {
			
			//Grand Total after plus one
			((SaleVouncher) Cart_Item_List.get(pos)).setQty(String.valueOf(Integer.valueOf(((SaleVouncher) Cart_Item_List.get(pos)).getQty()) + 1));
			//Integer disprice = price -  ( price * Integer.valueOf(Discount.getText().toString()) / 100); 	
			
			Integer grandTotal = Integer.valueOf(priceTotal.getText().toString()) + price;
			
			//Integer dis_amt = Integer.valueOf(txt_disc_show.getText().toString());
			priceTotal.setText(grandTotal+"");

		}
		
		public void onMinusClick(Integer pos, Integer price) {
			// TODO Auto-generated method stub
			
			//Grand Total after minus one
			((SaleVouncher) Cart_Item_List.get(pos)).setQty(String.valueOf(Integer.valueOf(((SaleVouncher) Cart_Item_List.get(pos)).getQty()) - 1));
			//Integer disprice = price -  ( price * Integer.valueOf(Discount.getText().toString()) / 100); 
			Integer grandTotal = Integer.valueOf(priceTotal.getText().toString()) - price;
			
			//Integer dis_amt = Integer.valueOf(txt_disc_show.getText().toString());
			priceTotal.setText(grandTotal+"");
			
		}

		public void onFreeClick(Integer pos, boolean isFreeChecked) {
			// TODO Auto-generated method stub
			
			if (isFreeChecked = true) {
				
				SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(pos);
				sv.setFree_checked("free");		
				
				Log.i("", "On Check Free: "+sv.getFree_checked());
				
				Integer grandTotal = 0; 
				
				for (int j = 0; j < Cart_Item_List.size(); j++) {
					
					SaleVouncher sv2 = (SaleVouncher)Cart_Item_List.get(j);
					
					if (sv2.getFree_checked() != null) {
						if (sv2.getFree_checked().equals("free")) {
							
							View view = (View) lvitem_list.getChildAt(j);
							TextView txt_sale_price = (TextView) view.findViewById(R.id.txtUnitPrice);
							
							Integer total = Integer.valueOf(txt_sale_price.getText().toString()) * Integer.valueOf(((SaleVouncher) Cart_Item_List.get(j)).getQty());
							grandTotal += total;
						}else {
							Integer total = Integer.valueOf(((SaleVouncher) Cart_Item_List.get(j)).getPrice()) * Integer.valueOf(((SaleVouncher) Cart_Item_List.get(j)).getQty());
							grandTotal += total;
						}
					}else {
						Integer total = Integer.valueOf(((SaleVouncher) Cart_Item_List.get(j)).getPrice()) * Integer.valueOf(((SaleVouncher) Cart_Item_List.get(j)).getQty());
						grandTotal += total;
					}

				}
				
				priceTotal.setText(grandTotal+"");
				
			}else if (isFreeChecked = false) {
				SaleVouncher sv = (SaleVouncher)Cart_Item_List.get(pos);
				sv.setFree_checked("-");

				priceTotal.setText(defaultGrandTotal()+"");
				
			}
			
		}

	};
	
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
	
	public boolean checkFields() {
		if (scan.getText().toString().length() == 0) {
			//scan.setError("Tab here to scan");
			scan.setError("Tap here to scan");
			return false;
		}
		
		return true;
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
	
	private void warningAlert() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
		alert.setTitle("Warning");
		alert.setMessage("No Item!");
		alert.show();
		alert.setCancelable(true);
	}
	
	private void warningAlertMM() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
		alert.setTitle("Warning");
		alert.setMessage("Please choose items!");
		alert.show();
		alert.setCancelable(true);
	}
	
	
	
}

	