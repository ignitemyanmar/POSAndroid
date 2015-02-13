package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.CategoriesListAdapter;
import com.ignite.pos.adapter.ItemGridAdapter;
import com.ignite.pos.adapter.PurchaseUpdateItemListAdapter;
import com.ignite.pos.adapter.StringSpinnerAdapter;
import com.ignite.pos.adapter.SubCategoriesListAdapter;
import com.ignite.pos.adapter.SupplierSpinnerAdapter;
import com.ignite.pos.application.DeviceUtil;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SubCategory;
import com.ignite.pos.model.Supplier;
import com.smk.skalertmessage.SKToastMessage;

@SuppressLint("ShowToast")
public class PurchaseUpdateActivity  extends SherlockActivity{

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
	private TextView vouncherno ,login, txt_panel_name;
	private TextView grandTotal;
	private TextView plus, minus;
	private TextView deleteItem, Update;
	private TextView Discount;
	public static PurchaseUpdateItemListAdapter itemAdapter;
	public String currentDate;
	private String AdminName;
	private String VoucherNo;
	List<Object> vouItemList;
	List<Object> oldItems;
	private LinearLayout discount_layout;
	private Spinner sp_supplier_name;
	private Supplier supplier;
	private List<Object> supplier_list;
	private String supplierName;
	private String SupplierName;
	private List<String> SupName;
	
	//variables of Prompt of Add Prices
	EditText editTxt_purchase_price;
	EditText editTxt_sale_price;
	TextView tv_marginal_price;
	Button btn_margin_price;
	EditText editTxt_qty;
	String purchase_price;
	String sale_price;
	String marginal_price;
	String purchaseQty;
	String item_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_voucher);
		
		//Get Admin Name
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		AdminName = pref.getString("admin_name", "-");
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		icon_pos = (ImageView)actionBar.getCustomView().findViewById(R.id.icon_pos);
		icon_pos.setVisibility(View.GONE);
		login = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		txt_panel_name = (TextView)actionBar.getCustomView().findViewById(R.id.txt_panel_name);
		txt_panel_name.setVisibility(View.VISIBLE);
		//txt_panel_name.setText("Update Purchase");
		txt_panel_name.setText("အ၀ယ္ ေဘာင္ ခ်ာ ျပင္ျခင္း");
		discount_layout = (LinearLayout) findViewById(R.id.lyDiscount2);
		discount_layout.setVisibility(View.GONE);
		sp_supplier_name = (Spinner)findViewById(R.id.sp_supplier_name);
		//sp_supplier_name.setOnItemSelectedListener(suppliernameClickListener);
		
		//Get Admin Name 
		Log.i("", "Admin Name: "+AdminName);
		
		if (AdminName.equals("-")) {
			login.setText(AdminName);
			SKToastMessage.showMessage(getApplicationContext(), "Please log in with Admin account first!", SKToastMessage.WARNING);
			startActivity(new Intent(PurchaseUpdateActivity.this, LoginActivity.class));
		}else {
			login.setText(AdminName);
		}
		
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setOnClickListener(clickListener);
		categories = (Button)actionBar.getCustomView().findViewById(R.id.btnCategories);
		categories.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);		
		
		scanner_mode = (LinearLayout)findViewById(R.id.scanner_mode);
		picker_mode = (LinearLayout)findViewById(R.id.picker_mode);
		scan = (EditText)findViewById(R.id.editText_scan);
		search = (Button)findViewById(R.id.btnSearch);
		
		vouncherno = (TextView)findViewById(R.id.txt_purchase_vou_no);
		Cart_Item_List = new ArrayList<Object>();
		
		lvitem_list = (ListView)findViewById(R.id.lv_vou_item_list);
		//Grand Total
		grandTotal = (TextView)findViewById(R.id.txt_grand_total);
		plus = (TextView)findViewById(R.id.btnPlus);
		minus = (TextView)findViewById(R.id.btnMinus);
		Discount = (TextView)findViewById(R.id.txtDiscount);
		deleteItem = (TextView)findViewById(R.id.btnDelete_items);
		//deleteItem.setVisibility(View.GONE);
		Update = (TextView)findViewById(R.id.btn_save_vou);
		//Update.setText("Update");
		Update.setText("ျပင္ မည္");
		
		search.setOnClickListener(clickListener);
		plus.setOnClickListener(clickListener);
		minus.setOnClickListener(clickListener);
		deleteItem.setOnClickListener(clickListener);
		Update.setOnClickListener(clickListener);
		grid_categories = (GridView)findViewById(R.id.gvCategories);
		lvitem_list.setOnItemLongClickListener(itemLongClickListener);
		
		//Get Voucher No
		Bundle bundle = getIntent().getExtras();
		VoucherNo = bundle.getString("VoucherNo");
		SupplierName = bundle.getString("SupplierName");
		vouncherno.setText(VoucherNo);
		
		getVoucherItems();
		getSupplier();
		
		itemAdapter = new PurchaseUpdateItemListAdapter(this,Cart_Item_List);
		//itemAdapter.setCallbackListiner(callback);
		lvitem_list.setAdapter(itemAdapter);
		setListViewHeightBasedOnChildren(lvitem_list);
		
		if (Cart_Item_List.size() > 0) {
			//Discount.setText(((purchaseVou)Cart_Item_List.get(0)).getDiscount());
			
			grandTotal.setText(((PurchaseVoucher)Cart_Item_List.get(0)).getGrandtotal());
		}
		
	}
	
	private OnItemSelectedListener suppliernameClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			supplier = (Supplier)supplier_list.get(position);
			selectedSupplierName = supplier.getSupCoName();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getSupplier()
	{
		dbManager = new SupplierController(this);
		SupplierController control = (SupplierController)dbManager;
		supplier_list = new ArrayList<Object>();
		supplier_list = control.select();
		
		sp_supplier_name.setAdapter(new SupplierSpinnerAdapter(this,supplier_list));
		sp_supplier_name.setOnItemSelectedListener(suppliernameClickListener);
		int i = 0;
		for(Object obj: supplier_list){
			Supplier supplier = (Supplier) obj;
			if(supplier.getSupCoName().equals(SupplierName)){
				sp_supplier_name.setSelection(i);
			}
			i++;
		}
		
	}
	
	private void getVoucherItems() {
		// TODO Auto-generated method stub
		dbManager = new PurchaseVoucherController(this);
		PurchaseVoucherController pvControl = (PurchaseVoucherController)dbManager;
		Cart_Item_List = pvControl.selectRecordByVouID(VoucherNo);
		oldVDate = ((PurchaseVoucher)Cart_Item_List.get(0)).getVdate();
		SupplierName = ((PurchaseVoucher)Cart_Item_List.get(0)).getSupplierName();
		
		Log.i("", "Item List by Purchase Vou ID: "+Cart_Item_List.toString());
		
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
                PurchaseUpdateActivity.this);
    
        //alert.setTitle("Delete Item - "+selected_item_name+" ?");
		View dialogView = View.inflate(PurchaseUpdateActivity.this, R.layout.dialog_title, null);
		TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_dialog_title);
		//dialogTitle.setText("Add Prices | "+item_name);
		dialogTitle.setText("Delete Item - "+selected_item_name+" ?");
		alert.setCustomTitle(dialogView);
        
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        	
        	List<Object> oldItemObj;
        	
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				PurchaseVoucher newPv = (PurchaseVoucher)Cart_Item_List.get(deletePosition);
				
				oldItemObj = new ArrayList<Object>();
				dbManager = new PurchaseVoucherController(PurchaseUpdateActivity.this);
				PurchaseVoucherController pvControl = (PurchaseVoucherController)dbManager;
				oldItemObj = pvControl.select(VoucherNo, newPv.getItemid());
				
				Log.i("", "Old Item Object: "+oldItemObj.toString());
				
				//If the deleted item exists in Old Purchase Voucher
				if (oldItemObj.size() > 0 && oldItemObj != null) {
					
					PurchaseVoucher oldPv = (PurchaseVoucher) oldItemObj.get(0);
						
						//If Cart List ItemID is equals with ItemID in Database
						if (newPv.getItemid().equals(oldPv.getItemid())) {
							
							//Update in Purchase Table
							dbManager = new PurchaseVoucherController(PurchaseUpdateActivity.this);
							PurchaseVoucherController pControl = (PurchaseVoucherController)dbManager;
							pControl.delete(oldItemObj);
							
							Log.i("", "Purchase Voucher after delete Item: "+pControl.selectRecordByVouID(VoucherNo));
							
						}
				}
				
				Cart_Item_List.remove(deletePosition);
            	itemAdapter.notifyDataSetChanged();
            	setListViewHeightBasedOnChildren(lvitem_list);
            	
            	grandTotal.setText(defaultGrandTotal()+"");
            	
    			if(Cart_Item_List.size() == 0){
    				grandTotal.setText("0.00");
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

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == change_mode)
			{
				if(!clicked)
				{
					clicked = true;
					//change_mode.setText("Change Scanner Mode");
					change_mode.setText("Scanner ႏွင့္ အသံုးျပဳရန္");
					categories.setVisibility(View.VISIBLE);
					getCategories();
					scanner_mode.setVisibility(LinearLayout.INVISIBLE);
					picker_mode.setVisibility(LinearLayout.VISIBLE);
					scanner_mode.setTranslationX(DeviceUtil.getInstance(PurchaseUpdateActivity.this).getWidth());
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
					picker_mode.setTranslationX(DeviceUtil.getInstance(PurchaseUpdateActivity.this).getWidth());
				}
			}
			if(v == search)
			{
				if (checkFields()) {
					getScannerItem();
					scan.setText("");
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
					grandTotal.setText(discounted_total+"");
				} else {
					
					//plus.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(PurchaseUpdateActivity.this);
					alert.setTitle("Warning");
					alert.setMessage("No Item!");
					alert.show();
					alert.setCancelable(true);
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
						grandTotal.setText(discounted_total+"");
					}
				} else {
					
					//minus.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(PurchaseUpdateActivity.this);
					alert.setTitle("Warning");
					alert.setMessage("No Item!");
					alert.show();
					alert.setCancelable(true);
				}
				
			}
			if( v == deleteItem)
			{
				if (Cart_Item_List.size() != 0) {
					
					removeAllFromList();

				} else {
					
					//deleteItem.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(PurchaseUpdateActivity.this);
					alert.setTitle("Warning");
					alert.setMessage("No Item!");
					alert.show();
					alert.setCancelable(true);
				}
				
			}
			if(v == Update)
			{
				if (Cart_Item_List.size() > 0) {
					
					//Update.setEnabled(true);
					
					if (AdminName.equals("-")) {
						SKToastMessage.showMessage(getApplicationContext(), "Please log in with Admin account first!", SKToastMessage.WARNING);
						startActivity(new Intent(PurchaseUpdateActivity.this, LoginActivity.class));
					}else {
						updateVoucher();
						finish();
					}
					
				} else {
					
					finish();
					
					/*//Update.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(PurchaseUpdateActivity.this);
					alert.setTitle("Warning");
					alert.setMessage("No Item!");
					alert.show();
					alert.setCancelable(true);*/
				}
			}
		}
	};
	protected List<Object> item_list_obj;
	private String selectedSupplierName;
	

	protected void removeAllFromList() {
		// TODO Auto-generated method stub
        List<Object> oldItemObj;
        
        for (int i = 0; i < Cart_Item_List.size(); i++) {
			
        	PurchaseVoucher newPv = (PurchaseVoucher)Cart_Item_List.get(i);
        	
        	oldItemObj = new ArrayList<Object>();
			dbManager = new PurchaseVoucherController(PurchaseUpdateActivity.this);
			PurchaseVoucherController pvControl = (PurchaseVoucherController)dbManager;
			oldItemObj = pvControl.select(VoucherNo, newPv.getItemid());
			
			Log.i("", "Old Item Object: "+oldItemObj.toString());
			
			//If the deleted items exists in Old Purchase Voucher
			if (oldItemObj.size() > 0 && oldItemObj != null) {
				
				PurchaseVoucher oldPv = (PurchaseVoucher) oldItemObj.get(0);
					
					//If Cart List ItemID is equals with ItemID in Database
					if (newPv.getItemid().equals(oldPv.getItemid())) {
						
						//Update in Purchase Table
						dbManager = new PurchaseVoucherController(PurchaseUpdateActivity.this);
						PurchaseVoucherController pControl = (PurchaseVoucherController)dbManager;
						pControl.delete(oldItemObj);
						
						Log.i("", "Purchase Voucher after delete Item: "+pControl.selectRecordByVouID(VoucherNo));
					}
			}//End if
		}//End of For Loop 

        Cart_Item_List.clear();
    	itemAdapter.notifyDataSetChanged();		
    	setListViewHeightBasedOnChildren(lvitem_list);
            	
    	//grandTotal.setText(defaultGrandTotal()+"");
    	
		if(Cart_Item_List.size() == 0){
			grandTotal.setText("0.00");
		}   	
	}
	
	private void updateVoucher()
	{
		List<Object> purchaseVoucher = new ArrayList<Object>();
		dbManager = new PurchaseVoucherController(this);
		PurchaseVoucherController control = (PurchaseVoucherController)dbManager;
		
		View view = null ;
		
		for (int i = 0; i < Cart_Item_List.size(); i++) {
			view = (View) lvitem_list.getChildAt(i);
			
			TextView txt_item_name = (TextView) view.findViewById(R.id.txtItem_name);
			TextView txt_total = (TextView) view.findViewById(R.id.txtTotal);
			TextView txt_qty = (TextView) view.findViewById(R.id.txtQty);
			TextView txt_price = (TextView) view.findViewById(R.id.txtUnitPrice);
			((PurchaseVoucher) Cart_Item_List.get(i)).setVid(vouncherno.getText().toString());
			((PurchaseVoucher) Cart_Item_List.get(i)).setSupplierName(selectedSupplierName );
			((PurchaseVoucher) Cart_Item_List.get(i)).setItemid(((PurchaseVoucher) Cart_Item_List.get(i)).getItemid());
			((PurchaseVoucher) Cart_Item_List.get(i)).setQty(txt_qty.getText().toString());
			((PurchaseVoucher) Cart_Item_List.get(i)).setPurchasePrice(txt_price.getText().toString());
			((PurchaseVoucher) Cart_Item_List.get(i)).setItemname(txt_item_name.getText().toString());
			((PurchaseVoucher) Cart_Item_List.get(i)).setItemtotal(txt_total.getText().toString());
			((PurchaseVoucher) Cart_Item_List.get(i)).setVdate(((PurchaseVoucher) Cart_Item_List.get(i)).getVdate());
			((PurchaseVoucher) Cart_Item_List.get(i)).setGrandtotal(grandTotal.getText().toString());
			
			//Add Purchase Voucher
			purchaseVoucher.add((Object) Cart_Item_List.get(i));
		}
		
		//Update or Add in Purchase Voucher of Purchase Table
		for (int i = 0; i < purchaseVoucher.size(); i++) {
			
			PurchaseVoucher pv = (PurchaseVoucher) purchaseVoucher.get(i);
			
			List<Object> itemObj = new ArrayList<Object>();
			dbManager = new PurchaseVoucherController(PurchaseUpdateActivity.this);
			PurchaseVoucherController dbControl = (PurchaseVoucherController)dbManager;
			itemObj = dbControl.select(VoucherNo, pv.getItemid());
			
			Log.i("", "Purchase Item Object from DB: "+itemObj.toString());
			
			if (itemObj != null && itemObj.size() > 0) {
				
				List<Object> updateObj = new ArrayList<Object>();
				
				updateObj.add(new PurchaseVoucher(VoucherNo, selectedSupplierName, pv.getItemid()
						, pv.getQty(), pv.getItemtotal(), pv.getGrandtotal(), pv.getPurchasePrice()
						, pv.getMarginalPrice(), pv.getSalePrice()));
				
				
				dbControl.updateByVouIDItemID(updateObj);
				
				Log.i("", "Voucher after Update: "+dbControl.selectRecordByVouID(VoucherNo));
				
			}else {
				
				List<Object> itemObj2 = new ArrayList<Object>();
				itemObj2.add(pv);
				dbControl.save(itemObj2);
				
				Log.i("", "Voucher after Add New Item: "+dbControl.selectRecordByVouID(VoucherNo));
				
			}
		}//End For Loop of Cart Items in Purchase Voucher
		//End Update or Add New Item in Purchase Voucher 
		
		}	


	/*
	 * Get Item from Scanner
	 */
	private void getScannerItem()
	{
		dbManager = new ItemListController(this);
		ItemListController item_controller = (ItemListController)dbManager;
		list_obj = new ArrayList<Object>();
		list_obj = item_controller.select(scan.getText().toString());
		
		if (list_obj.size() > 0) {
			
			final ItemList item_obj = (ItemList)list_obj.get(0);
			
			// get prompt_add_price.xml view
						LayoutInflater li = LayoutInflater.from(PurchaseUpdateActivity.this);
						View promptsView = li.inflate(R.layout.prompt_add_price, null);

						final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								PurchaseUpdateActivity.this);
						
						// set prompt_add_price.xml to alertdialog builder
						alertDialogBuilder.setView(promptsView);

						editTxt_purchase_price = (EditText) promptsView.findViewById(R.id.editTxt_purchase_price);
						editTxt_sale_price = (EditText) promptsView.findViewById(R.id.editTxt_sale_price);
						btn_margin_price = (Button) promptsView.findViewById(R.id.btn_marginal_price);
						btn_margin_price.setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								if (checkFieldsforMarginalbtn()) {
									String showMarginalPrice = calculateMarginalPrice(item_obj.getMarginalPrice(), editTxt_purchase_price.getText().toString()
											, item_obj.getQty(), editTxt_qty.getText().toString());
									
											Log.i("", "show Marginal Price: "+showMarginalPrice);
									
											tv_marginal_price.setText(showMarginalPrice);
								}
							}
						});
						tv_marginal_price = (TextView) promptsView.findViewById(R.id.tv_marginal_price);
						editTxt_qty = (EditText) promptsView.findViewById(R.id.editTxt_qty);
						//editTxt_qty.addTextChangedListener(watcher);
						
						// set dialog message
						alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton("Save", new DialogInterface.OnClickListener(){
					            public void onClick(DialogInterface dialog, int which)
					            {
					                //Do nothing here because we override this button later to change the close behaviour. 
					                //However, we still need this because on older versions of Android unless we 
					                //pass a handler the button doesn't get instantiated
					            }
					        })
							.setPositiveButton("Save",
							  new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog,int id) {
								
							    }
							  })
							.setNegativeButton("Cancel",
							  new DialogInterface.OnClickListener() {
							    public void onClick(DialogInterface dialog,int id) {
							    	dialog.cancel();
							    }
							  });
						
						//.setTitle("၀ယ္ေစ်းထည့္ျခင္း | "+item_obj.getItemName())
						
						View dialogView = View.inflate(PurchaseUpdateActivity.this, R.layout.dialog_title, null);
						TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_dialog_title);
						//dialogTitle.setText("Add Prices | "+item_name);
						dialogTitle.setText("၀ယ္ေစ်းထည့္ျခင္း | "+item_obj.getItemName());
						alertDialogBuilder.setCustomTitle(dialogView);
						
						final AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();	
						
						alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
							
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Boolean wantToCloseDialog = false;
					              //Do stuff, possibly set wantToCloseDialog to true then...
					              if (checkFieldsPrompt()){
					            	  wantToCloseDialog = true;
					            	  
					            	    purchase_price = editTxt_purchase_price.getText().toString();
									  //  sale_price = editTxt_sale_price.getText().toString();
									   // marginal_price = tv_marginal_price.getText().toString();
									   purchaseQty = editTxt_qty.getText().toString();
									 
										ItemID = String.valueOf(item_obj.getItemId());
										
										Log.i("TAG", "ItemID --> " + ItemID);
										
										List<PurchaseVoucher> cartItems = new ArrayList<PurchaseVoucher>();
										
										if (item_obj != null) {
											
											boolean isExist = false;
											
											for (int i = 0; i < Cart_Item_List.size(); i++) {
												
												if(item_obj.getItemId().equals(((PurchaseVoucher) Cart_Item_List.get(i)).getItemid())){
													
													isExist = true;
													
													((PurchaseVoucher) Cart_Item_List.get(i)).setPurchasePrice(purchase_price);
													((PurchaseVoucher) Cart_Item_List.get(i)).setQty(purchaseQty);
													//((PurchaseVoucher) Cart_Item_List.get(i)).setMarginalPrice(marginal_price);
													//((PurchaseVoucher) Cart_Item_List.get(i)).setSalePrice(sale_price);
													
													
													Log.i("", "Cart List after add price: "+Cart_Item_List.toString());
													
													break;
												}
											}
											
											if(!isExist){
												
												cartItems.add(new PurchaseVoucher(vouncherno.getText().toString(), supplierName, ItemID
															, item_obj.getItemName(), purchaseQty, ""
															, item_obj.getCategoryId(), item_obj.getSubCategoryId()
															, "0", currentDate, "0", purchase_price, null , null, "0"));
												}
											
											Cart_Item_List.addAll(cartItems);
											
											Log.i("", "Cart List: "+Cart_Item_List.toString());
											
											itemAdapter.notifyDataSetChanged();
											setListViewHeightBasedOnChildren(lvitem_list);
											
											grandTotal.setText(defaultGrandTotal()+"");
											
											}
					              }
					              
					              if(wantToCloseDialog){
					            	  alertDialog.dismiss();
					              }
					              
							}
						});
			
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
		
	}
	
	private OnItemClickListener categoryClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			// Log.i("TAG", "--> onItemClick listener...");    
			dbManager = new SubCategoryController(PurchaseUpdateActivity.this);
			category = (Category)list_obj.get(position);
			CatID = String.valueOf(category.getCategoryID());
			
			Log.i("TAG", "Category ID -->" + CatID);   
			
			SubCategoryController sub_control = (SubCategoryController)dbManager;
			sub_category_Listobj = new ArrayList<Object>();
			sub_category_Listobj = sub_control.select(CatID);
			
			Log.i("","Get SubCategory List :"+ sub_category_Listobj.toString());
			
			if(sub_category_Listobj != null && sub_category_Listobj.size()>0)
			{
				grid_categories.setAdapter(new SubCategoriesListAdapter(PurchaseUpdateActivity.this, sub_category_Listobj));
				grid_categories.setOnItemClickListener(subcategoryClickListener);
			}else{
				dbManager = new ItemListController(PurchaseUpdateActivity.this);
				ItemListController item_control = (ItemListController)dbManager;
				item_list_obj = new ArrayList<Object>();
				item_list_obj = item_control.selectRecordAllByCatSub(CatID,"0");
				
				Log.i("","Get Item List :"+ item_list_obj.toString());
				
				if(item_list_obj != null && item_list_obj.size() > 0)
				{
					grid_categories.setAdapter(new ItemGridAdapter(PurchaseUpdateActivity.this, item_list_obj));
					
					grid_categories.setOnItemClickListener(itemClickListener);
				}else{
					SKToastMessage.showMessage(PurchaseUpdateActivity.this, "There is no item!.", SKToastMessage.WARNING);
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
			
			dbManager = new ItemListController(PurchaseUpdateActivity.this);
			ItemListController item_control = (ItemListController)dbManager;
			item_list_obj = new ArrayList<Object>();
			item_list_obj = item_control.selectRecordAllByCatSub(CatID,SubID);
			
			Log.i("","Get Item List :"+ item_list_obj.toString());
			
			if(item_list_obj != null && item_list_obj.size() > 0)
			{
				grid_categories.setAdapter(new ItemGridAdapter(PurchaseUpdateActivity.this, item_list_obj));
				
				grid_categories.setOnItemClickListener(itemClickListener);
			}else{
				SKToastMessage.showMessage(PurchaseUpdateActivity.this, "There is no item!.", SKToastMessage.WARNING);
			}
		}
	};
	protected String oldVDate;
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, final int position,
				long id) {
			
			// get prompt_add_price.xml view
			LayoutInflater li = LayoutInflater.from(PurchaseUpdateActivity.this);
			View promptsView = li.inflate(R.layout.prompt_add_price, null);

			final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					PurchaseUpdateActivity.this);
			
			// set prompt_add_price.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			editTxt_purchase_price = (EditText) promptsView.findViewById(R.id.editTxt_purchase_price);
			editTxt_sale_price = (EditText) promptsView.findViewById(R.id.editTxt_sale_price);
			btn_margin_price = (Button) promptsView.findViewById(R.id.btn_marginal_price);
			btn_margin_price.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (checkFieldsforMarginalbtn()) {

						String showMarginalPrice = calculateMarginalPrice(itemObj.getMarginalPrice(), editTxt_purchase_price.getText().toString()
						, itemObj.getQty(), editTxt_qty.getText().toString());
				
						Log.i("", "show Marginal Price: "+showMarginalPrice);
				
						tv_marginal_price.setText(showMarginalPrice);
					}
					
				}
			});
			
			tv_marginal_price = (TextView) promptsView.findViewById(R.id.tv_marginal_price);
			editTxt_qty = (EditText) promptsView.findViewById(R.id.editTxt_qty);
			//editTxt_qty.addTextChangedListener(watcher);
			
			dbManager = new ItemListController(PurchaseUpdateActivity.this);
			itemObj = (ItemList)item_list_obj.get(position);
			item_name = itemObj.getItemName();
			
			View dialogView = View.inflate(PurchaseUpdateActivity.this, R.layout.dialog_title, null);
			TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_dialog_title);
			dialogTitle.setText("၀ယ္ေစ်းထည့္ျခင္း | "+item_name);
			alertDialogBuilder.setCustomTitle(dialogView);
			
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Save", new DialogInterface.OnClickListener()
		        {
		            public void onClick(DialogInterface dialog, int which)
		            {
		                //Do nothing here because we override this button later to change the close behaviour. 
		                //However, we still need this because on older versions of Android unless we 
		                //pass a handler the button doesn't get instantiated
		            }
		        })
				.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
				    	dialog.cancel();
				    }
				  });
			
			final AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			
			alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
		      {            
		          public void onClick(View v)
		          {
		              Boolean wantToCloseDialog = false;
		              //Do stuff, possibly set wantToCloseDialog to true then...
		              if (checkFieldsPrompt()) {
		            	  
		            	    wantToCloseDialog = true;
		            	    
				    		purchase_price = editTxt_purchase_price.getText().toString();
						 //   sale_price = editTxt_sale_price.getText().toString();
						  //  marginal_price = tv_marginal_price.getText().toString();
						    purchaseQty = editTxt_qty.getText().toString();
						 
							ItemID = String.valueOf(itemObj.getItemId());
							
							Log.i("TAG", "ItemID --> " + ItemID);
							
							List<PurchaseVoucher> cartItems = new ArrayList<PurchaseVoucher>();
							
							if (itemObj != null) {
								
								boolean isExist = false;
								
								for (int i = 0; i < Cart_Item_List.size(); i++) {
									
									if(itemObj.getItemId().equals(((PurchaseVoucher) Cart_Item_List.get(i)).getItemid())){
										
										isExist = true;
										
										((PurchaseVoucher) Cart_Item_List.get(i)).setPurchasePrice(purchase_price);
										((PurchaseVoucher) Cart_Item_List.get(i)).setQty(purchaseQty);
										//((PurchaseVoucher) Cart_Item_List.get(i)).setMarginalPrice(marginal_price);
									//	((PurchaseVoucher) Cart_Item_List.get(i)).setSalePrice(sale_price);
										
										break;
									}
								}
								
								if(!isExist){
									
									cartItems.add(new PurchaseVoucher(vouncherno.getText().toString(), SupplierName, ItemID
												, itemObj.getItemName(), purchaseQty, ""
												, itemObj.getCategoryId(), itemObj.getSubCategoryId()
												, "0", oldVDate, "0", purchase_price, null , null, "0"));
									}
								
								Cart_Item_List.addAll(cartItems);
								
								Log.i("", "Cart List: "+Cart_Item_List.toString());
								
								itemAdapter.notifyDataSetChanged();
								setListViewHeightBasedOnChildren(lvitem_list);
								
								grandTotal.setText(defaultGrandTotal()+"");
								
								}
						    
						}
		              
		              if(wantToCloseDialog){
		            	  alertDialog.dismiss();
		              }
		          }
		      });
			
		}
	};
	
	private TextWatcher watcher = new TextWatcher() {
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
			SKToastMessage.showMessage(getApplicationContext(), "Press Marginal Price before Sale Price!", SKToastMessage.WARNING);
			
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private String calculateMarginalPrice(String oldMarginPrice, String newPurchasePrice, String oldStockQty, String newPurchaseQty){
		
		String marginalPrice = null;
		
		if (oldMarginPrice != null && oldStockQty != null) {
			
			if(Integer.valueOf(oldMarginPrice) > 0 && Integer.valueOf(oldStockQty) > 0){
				if (Integer.valueOf(newPurchasePrice) > 0 && Integer.valueOf(newPurchaseQty) > 0) {
					 
				 	Integer grandTotal = (Integer.valueOf(newPurchasePrice) * Integer.valueOf(newPurchaseQty)) + (Integer.valueOf(oldMarginPrice) * Integer.valueOf(oldStockQty));
					Integer qtyTotal = Integer.valueOf(newPurchaseQty) + Integer.valueOf(oldStockQty);
					
					if (Integer.valueOf(oldStockQty) > 0) {
						marginalPrice = String.valueOf(grandTotal / qtyTotal); 
					}
				}
			}else{
				marginalPrice = newPurchasePrice;
			}
		}else {
			marginalPrice = newPurchasePrice;
		}
		
		return marginalPrice;
	}
	
	
	private Integer defaultGrandTotal()
	{		
		Integer grandTotal = 0; 
		
		for (int i = 0; i < Cart_Item_List.size(); i++) {
			Integer total = Integer.valueOf(((PurchaseVoucher) Cart_Item_List.get(i)).getPurchasePrice()) * Integer.valueOf(((PurchaseVoucher) Cart_Item_List.get(i)).getQty());
			grandTotal += total;
		}
		
		return grandTotal;
	}
	
	private Integer stock_qty; 
	
	/*//Callback for Item Qty (+) (-)
	private PurchaseUpdateItemListAdapter.Callback callback = new  PurchaseUpdateItemListAdapter.Callback() {
		
		public void onPlusClick(Integer pos, Integer price) {
			
			//Grand Total after plus one
			((purchaseVou) Cart_Item_List.get(pos)).setQty(String.valueOf(Integer.valueOf(((purchaseVou) Cart_Item_List.get(pos)).getQty()) + 1));
			Integer disprice = price -  ( price * Integer.valueOf(Discount.getText().toString()) / 100); 
			Integer grandTotal = Integer.valueOf(grandTotal.getText().toString()) + disprice;
			
			grandTotal.setText(grandTotal+"");
		}
		
		public void onMinusClick(Integer pos, Integer price) {
			// TODO Auto-generated method stub
			
			//Grand Total after minus one
			((purchaseVou) Cart_Item_List.get(pos)).setQty(String.valueOf(Integer.valueOf(((purchaseVou) Cart_Item_List.get(pos)).getQty()) - 1));
			Integer disprice = price -  ( price * Integer.valueOf(Discount.getText().toString()) / 100); 
			Integer grandTotal = Integer.valueOf(grandTotal.getText().toString()) - disprice; 
			
			grandTotal.setText(grandTotal+"");
		}

	};*/
	
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
	
	
	public boolean checkFields() {
		if (scan.getText().toString().length() == 0) {
			scan.setError("Tab here to scan");
			return false;
		}
		
		return true;
	}
	
	public boolean checkFieldsPrompt() {
		
		if (editTxt_purchase_price.getText().toString().length() == 0) {
			editTxt_purchase_price.setError("Enter Purchase Price");
			return false;
		}
		if (editTxt_qty.getText().toString().length() == 0) {
			editTxt_qty.setError("Enter Qty");
			return false;
		}
		/*if (tv_marginal_price.getText().toString().length() == 0) {
			SKToastMessage.showMessage(PurchaseUpdateActivity.this, "Press Marginal Price Button!", SKToastMessage.WARNING);
			return false;
		}
		if (editTxt_sale_price.getText().toString().length() == 0) {
			editTxt_sale_price.setError("Enter Sale Price");
			return false;
		}*/
		
		return true;
	}	
	
	public boolean checkFieldsforMarginalbtn() {
		
		if (editTxt_purchase_price.getText().toString().length() == 0) {
			editTxt_purchase_price.setError("Enter Purchase Price");
			return false;
		}
		if (editTxt_qty.getText().toString().length() == 0) {
			editTxt_qty.setError("Enter Qty");
			return false;
		}
		
		return true;
	}	
	
}

	
