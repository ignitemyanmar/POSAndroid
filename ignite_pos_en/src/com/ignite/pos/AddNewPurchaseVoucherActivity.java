package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.CategoriesListAdapter;
import com.ignite.pos.adapter.ItemGridAdapter;
import com.ignite.pos.adapter.PurchaseItemListAdapter;
import com.ignite.pos.adapter.SubCategoriesListAdapter;
import com.ignite.pos.adapter.SupplierSpinnerAdapter;
import com.ignite.pos.application.DeviceUtil;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SubCategory;
import com.ignite.pos.model.Supplier;
import com.smk.skalertmessage.SKToastMessage;

@SuppressLint({ "ShowToast", "ResourceAsColor" })
public class AddNewPurchaseVoucherActivity  extends SherlockActivity{

	private ActionBar actionBar;
	private Button change_mode, categories, search;
	private LinearLayout picker_mode, discount_layout;
	private DatabaseManager dbManager;
	private List<Object> list_obj;
	private ListView lvitem_list;
	private String ItemID;
	private List<PurchaseVoucher> Cart_Item_List;
	private GridView grid_categories;
	private List<Object> sub_category_Listobj;
	private Category category;
	private SubCategory sub_category;
	private ItemList itemObj;
	private Spinner sp_supplier_name;
	private Supplier supplier;
	private List<Object> supplier_list;
	private String supplierName;
	private String CatID, SubID;
	private TextView voucher_no ,login;
	private TextView grand_total;
	private TextView plus, minus;
	private TextView deleteItem, Save;
	public static PurchaseItemListAdapter itemAdapter;
	public String currentDate;
	private PurchaseVoucherAutoID autoId;
	private String auto_voucher_id;
	private LinearLayout scanner_mode;
	private EditText scan;
	private boolean clicked = false;
	
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
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar);
		login = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		login.setText("Add New Purchase Voucher");
		login.setBackgroundResource(R.color.black);
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setOnClickListener(clickListener);
		categories = (Button)actionBar.getCustomView().findViewById(R.id.btnCategories);
		categories.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);		
		
		scanner_mode = (LinearLayout)findViewById(R.id.scanner_mode);
		picker_mode = (LinearLayout)findViewById(R.id.picker_mode);
		scan = (EditText)findViewById(R.id.editText_scan);
		discount_layout = (LinearLayout) findViewById(R.id.lyDiscount);
		discount_layout.setVisibility(View.GONE);
		sp_supplier_name = (Spinner)findViewById(R.id.sp_supplier_name);
		sp_supplier_name.setOnItemSelectedListener(suppliernameClickListener);
		search = (Button)findViewById(R.id.btnSearch);
		voucher_no = (TextView)findViewById(R.id.txt_purchase_vou_no);
		Cart_Item_List = new ArrayList<PurchaseVoucher>();
		lvitem_list = (ListView)findViewById(R.id.lv_vou_item_list);
		grand_total = (TextView)findViewById(R.id.txt_grand_total);
		plus = (TextView)findViewById(R.id.btnPlus);
		minus = (TextView)findViewById(R.id.btnMinus);
		deleteItem = (TextView)findViewById(R.id.btnDelete_items);
		Save = (TextView)findViewById(R.id.btn_save_vou);
		
		search.setOnClickListener(clickListener);
		plus.setOnClickListener(clickListener);
		minus.setOnClickListener(clickListener);
		deleteItem.setOnClickListener(clickListener);
		Save.setOnClickListener(clickListener);
		grid_categories = (GridView)findViewById(R.id.gvCategories);
		lvitem_list.setOnItemLongClickListener(itemLongClickListener);
		getSupplier();
		
		//Auto Voucher Id
		autoId = new PurchaseVoucherAutoID(AddNewPurchaseVoucherActivity.this);
		auto_voucher_id = autoId.generateAutoID();
				
		voucher_no.setText(auto_voucher_id);
		
		itemAdapter = new PurchaseItemListAdapter(this,Cart_Item_List);
		//itemAdapter.setCallbackListiner(callback);
		lvitem_list.setAdapter(itemAdapter);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		
	}
	
	private OnItemSelectedListener suppliernameClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			supplier = (Supplier)supplier_list.get(position);
			supplierName = supplier.getSupCoName();
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
                AddNewPurchaseVoucherActivity.this);
    
        alert.setTitle("Delete Item - "+selected_item_name+" ?");
        
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Cart_Item_List.remove(deletePosition);
            	itemAdapter.notifyDataSetChanged();
            	setListViewHeightBasedOnChildren(lvitem_list);
            	
            	grand_total.setText(defaultGrandTotal()+"");
            	
    			if(Cart_Item_List.size() == 0){
    				grand_total.setText("0.00");
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

	private void getSupplier()
	{
		dbManager = new SupplierController(this);
		SupplierController control = (SupplierController)dbManager;
		supplier_list = new ArrayList<Object>();
		supplier_list = control.select();
		sp_supplier_name.setAdapter(new SupplierSpinnerAdapter(this,supplier_list));
		
		Log.i("","Supplier List:" + supplier_list.toString());
		
		if (supplier_list.size() == 0) {
			
			SKToastMessage.showMessage(AddNewPurchaseVoucherActivity.this, "Pls Register 'supplier' first!", SKToastMessage.WARNING);
		}

	}
	
	private void getItems(){
		if(!clicked)
		{
			clicked = true;
			change_mode.setText("Change Scanner Mode");
			categories.setVisibility(View.VISIBLE);
			getCategories();
			scanner_mode.setVisibility(LinearLayout.INVISIBLE);
			picker_mode.setVisibility(LinearLayout.VISIBLE);
			scanner_mode.setTranslationX(DeviceUtil.getInstance(AddNewPurchaseVoucherActivity.this).getWidth());
			picker_mode.setTranslationX(0);
		}
		else{
			clicked = false;
			change_mode.setText("Change Picker Mode");
			categories.setVisibility(View.GONE);
			scanner_mode.setVisibility(LinearLayout.VISIBLE);
			picker_mode.setVisibility(LinearLayout.INVISIBLE);
			scanner_mode.setTranslationX(0);
			picker_mode.setTranslationX(DeviceUtil.getInstance(AddNewPurchaseVoucherActivity.this).getWidth());
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		clicked = false;
		getItems();
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
					getScannerItem();
					scan.setText("");
				}
			}
			if(v == categories)
			{
				getCategories();
			}
			/*if (v == login)
			{
				login.setText(SaleLoginActivity.strname);
			}*/
			/*if(v == plus)
			{
				int discount_percent = Integer.valueOf(Discount.getText().toString()) + 1;
				
				if (Cart_Item_List.size() != 0) {
					
					//plus.setClickable(true);
					
					Discount.setText(String.valueOf(discount_percent));
					
					Integer discount_amount = ( defaultGrandTotal() * discount_percent ) / 100; 
					Integer discounted_total = defaultGrandTotal() - discount_amount ;
					grand_total.setText(discounted_total+"0");
				} else {
					
					//plus.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
					alert.setTitle("No Items!");
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
						grand_total.setText(discounted_total+"0");
					}
				} else {
					
					//minus.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
					alert.setTitle("No Items!");
					alert.show();
					alert.setCancelable(true);
				}
				
			}*/
			if( v == deleteItem)
			{
				if (Cart_Item_List.size() != 0) {
					
					Cart_Item_List.clear();
					itemAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(lvitem_list);

	    			if(Cart_Item_List.size() == 0){
	    				grand_total.setText("0.00");
	    			}
				} else {
					
					//deleteItem.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(AddNewPurchaseVoucherActivity.this);
					alert.setTitle("Warning");
					alert.setMessage("No Item!");
					alert.show();
					alert.setCancelable(true);
				}
				
			}
			if(v == Save)
			{
				if (Cart_Item_List.size() != 0) {
					
					//Save.setEnabled(true);
					if (sp_supplier_name.getCount() == 0) {
						SKToastMessage.showMessage(getApplicationContext(), "Choose Supplier Name", SKToastMessage.WARNING);
					}else {
						saveVouncher();
					}
				} else {
					
					//Save.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(AddNewPurchaseVoucherActivity.this);
					alert.setTitle("Warning");
					alert.setMessage("No Item!");
					alert.show();
					alert.setCancelable(true);
				}
			}
		}
	};
	protected List<Object> item_list_obj;
	
	
	private void saveVouncher()
	{
		List<Object> purchaseVoucher = new ArrayList<Object>();
		dbManager = new PurchaseVoucherController(this);
		PurchaseVoucherController control = (PurchaseVoucherController)dbManager;
		
		View view = null ;
		//bundleListObjet = new BundleListObjet();
		for (int i = 0; i < Cart_Item_List.size(); i++) {
			view = (View) lvitem_list.getChildAt(i);
			
			TextView txt_item_name = (TextView) view.findViewById(R.id.txtItem_name);
			TextView txt_total = (TextView) view.findViewById(R.id.txtTotal);
			TextView txt_qty = (TextView) view.findViewById(R.id.txtQty);
			TextView txt_purchase_price = (TextView) view.findViewById(R.id.txtUnitPrice);
			(Cart_Item_List.get(i)).setVid(voucher_no.getText().toString());
			(Cart_Item_List.get(i)).setSupplierName(supplierName);
			(Cart_Item_List.get(i)).setItemid(Cart_Item_List.get(i).getItemid());
			(Cart_Item_List.get(i)).setQty(txt_qty.getText().toString());
			(Cart_Item_List.get(i)).setPurchasePrice(txt_purchase_price.getText().toString());
			(Cart_Item_List.get(i)).setItemname(txt_item_name.getText().toString());
			(Cart_Item_List.get(i)).setItemtotal(txt_total.getText().toString());
			(Cart_Item_List.get(i)).setVdate(currentDate);
			(Cart_Item_List.get(i)).setGrandtotal(grand_total.getText().toString());
			//(Cart_Item_List.get(i)).setMarginalPrice(Cart_Item_List.get(i).getMarginalPrice());
			
			//bundleListObjet.getSaleVouncher().add(Cart_Item_List.get(i));
			
			//Add Purchase Voucher
			purchaseVoucher.add((Object) Cart_Item_List.get(i));
		}
		
		//Save into Purchase Voucher Table
		control.save(purchaseVoucher);
		Log.i("", "Purchase Voucher List in DB : " + control.select(voucher_no.getText().toString()).toString());
		
		//Make Auto Voucher ID
		auto_voucher_id = autoId.generateAutoID();
		voucher_no.setText(auto_voucher_id);
		
		Cart_Item_List.clear();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Purchase Voucher saved!", SKToastMessage.SUCCESS);
		
		grand_total.setText("0.00");
		itemAdapter.notifyDataSetChanged();
		setListViewHeightBasedOnChildren(lvitem_list);
		
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
						LayoutInflater li = LayoutInflater.from(AddNewPurchaseVoucherActivity.this);
						View promptsView = li.inflate(R.layout.prompt_add_price, null);

						final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								AddNewPurchaseVoucherActivity.this);
						
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
					//	editTxt_qty.addTextChangedListener(watcher);
						
						// set dialog message
						alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton("SAVE", new DialogInterface.OnClickListener(){
					            public void onClick(DialogInterface dialog, int which)
					            {
					                //Do nothing here because we override this button later to change the close behaviour. 
					                //However, we still need this because on older versions of Android unless we 
					                //pass a handler the button doesn't get instantiated
					            }
					        })
							.setPositiveButton("SAVE",
							  new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog,int id) {
								
							    }
							  })
							.setTitle("Add Prices | "+item_obj.getItemName())
							.setNegativeButton("Cancel",
							  new DialogInterface.OnClickListener() {
							    public void onClick(DialogInterface dialog,int id) {
							    	dialog.cancel();
							    }
							  });
						
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
									   // sale_price = editTxt_sale_price.getText().toString();
									  //  marginal_price = tv_marginal_price.getText().toString();
									    purchaseQty = editTxt_qty.getText().toString();
									 
										ItemID = String.valueOf(item_obj.getItemId());
										
										Log.i("TAG", "ItemID --> " + ItemID);
										
										List<PurchaseVoucher> cartItems = new ArrayList<PurchaseVoucher>();
										
										if (item_obj != null) {
											
											boolean isExist = false;
											
											for (int i = 0; i < Cart_Item_List.size(); i++) {
												
												if(item_obj.getItemId().equals(Cart_Item_List.get(i).getItemid())){
													
													isExist = true;
													
													Cart_Item_List.get(i).setPurchasePrice(purchase_price);
													Cart_Item_List.get(i).setQty(purchaseQty);
													//Cart_Item_List.get(i).setMarginalPrice(marginal_price);
												//	Cart_Item_List.get(i).setSalePrice(sale_price);
													
													break;
												}
											}
											
											if(!isExist){
												
												cartItems.add(new PurchaseVoucher(voucher_no.getText().toString(), supplierName, ItemID
															, item_obj.getItemName(), purchaseQty, ""
															, item_obj.getCategoryId(), item_obj.getSubCategoryId()
															, "0", currentDate, "0", purchase_price, "0", "0", "0"));
												}
											
											Cart_Item_List.addAll(cartItems);
											
											Log.i("", "Cart List: "+Cart_Item_List.toString());
											
											itemAdapter.notifyDataSetChanged();
											setListViewHeightBasedOnChildren(lvitem_list);
											
											grand_total.setText(defaultGrandTotal()+"");
											
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
		
		if (list_obj.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(AddNewPurchaseVoucherActivity.this);
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
			dbManager = new SubCategoryController(AddNewPurchaseVoucherActivity.this);
			category = (Category)list_obj.get(position);
			CatID = String.valueOf(category.getCategoryID());
			
			Log.i("TAG", "Category ID -->" + CatID);   
			
			SubCategoryController sub_control = (SubCategoryController)dbManager;
			sub_category_Listobj = new ArrayList<Object>();
			sub_category_Listobj = sub_control.select(CatID);
			
			Log.i("","Get SubCategory List :"+ sub_category_Listobj.toString());
			
			if(sub_category_Listobj != null && sub_category_Listobj.size()>0)
			{
				grid_categories.setAdapter(new SubCategoriesListAdapter(AddNewPurchaseVoucherActivity.this, sub_category_Listobj));
				grid_categories.setOnItemClickListener(subcategoryClickListener);
			}else{
				dbManager = new ItemListController(AddNewPurchaseVoucherActivity.this);
				ItemListController item_control = (ItemListController)dbManager;
				item_list_obj = new ArrayList<Object>();
				item_list_obj = item_control.selectRecordAllByCatSub(CatID, "0");
				
				Log.i("","Get Item List :"+ item_list_obj.toString());
				
				if(item_list_obj != null && item_list_obj.size() > 0)
				{
					grid_categories.setAdapter(new ItemGridAdapter(AddNewPurchaseVoucherActivity.this, item_list_obj));
					grid_categories.setOnItemClickListener(itemClickListener);
				}else{
					SKToastMessage.showMessage(AddNewPurchaseVoucherActivity.this, "There is no item!.", SKToastMessage.WARNING);
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
			
			dbManager = new ItemListController(AddNewPurchaseVoucherActivity.this);
			ItemListController item_control = (ItemListController)dbManager;
			item_list_obj = new ArrayList<Object>();
			item_list_obj = item_control.selectRecordAllByCatSub(CatID, SubID);
			
			Log.i("","Get Item List :"+ item_list_obj.toString());
			
			if(item_list_obj != null && item_list_obj.size() > 0)
			{
				grid_categories.setAdapter(new ItemGridAdapter(AddNewPurchaseVoucherActivity.this, item_list_obj));
				grid_categories.setOnItemClickListener(itemClickListener);
			}else{
				SKToastMessage.showMessage(AddNewPurchaseVoucherActivity.this, "There is no item!.", SKToastMessage.WARNING);
			}
		}
	};
	
	
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, final int position,
				long id) {
			
			// get prompt_add_price.xml view
			LayoutInflater li = LayoutInflater.from(AddNewPurchaseVoucherActivity.this);
			View promptsView = li.inflate(R.layout.prompt_add_price, null);

			final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					AddNewPurchaseVoucherActivity.this);
			
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
		//	editTxt_qty.addTextChangedListener(watcher);
			
			dbManager = new ItemListController(AddNewPurchaseVoucherActivity.this);
			itemObj = (ItemList)item_list_obj.get(position);
			item_name = itemObj.getItemName();
			
			View dialogView = View.inflate(AddNewPurchaseVoucherActivity.this, R.layout.dialog_title, null);
			TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_dialog_title);
			dialogTitle.setText("Add Prices | "+item_name);
			alertDialogBuilder.setCustomTitle(dialogView);
			
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("SAVE", new DialogInterface.OnClickListener()
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
						  //  sale_price = editTxt_sale_price.getText().toString();
						  //  marginal_price = tv_marginal_price.getText().toString();
						    purchaseQty = editTxt_qty.getText().toString();
						 
							ItemID = String.valueOf(itemObj.getItemId());
							
							Log.i("TAG", "ItemID --> " + ItemID);
							
							List<PurchaseVoucher> cartItems = new ArrayList<PurchaseVoucher>();
							
							if (itemObj != null) {
								
								boolean isExist = false;
								
								for (int i = 0; i < Cart_Item_List.size(); i++) {
									
									if(itemObj.getItemId().equals(Cart_Item_List.get(i).getItemid())){
										
										isExist = true;
										
										Cart_Item_List.get(i).setPurchasePrice(purchase_price);
										Cart_Item_List.get(i).setQty(purchaseQty);
									//	Cart_Item_List.get(i).setMarginalPrice(marginal_price);
									//	Cart_Item_List.get(i).setSalePrice(sale_price);
										
										break;
									}
								}
								
								if(!isExist){
									
									cartItems.add(new PurchaseVoucher(voucher_no.getText().toString(), supplierName, ItemID
												, itemObj.getItemName(), purchaseQty, ""
												, itemObj.getCategoryId(), itemObj.getSubCategoryId()
												, "0", currentDate, "0", purchase_price, "0", "0", "0"));
									}
								
								Cart_Item_List.addAll(cartItems);
								
								Log.i("", "Cart List: "+Cart_Item_List.toString());
								
								itemAdapter.notifyDataSetChanged();
								setListViewHeightBasedOnChildren(lvitem_list);
								
								grand_total.setText(defaultGrandTotal()+"");
								
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
			/*String showMarginalPrice = calculateMarginalPrice(itemObj.getMarginalPrice(), editTxt_purchase_price.getText().toString()
					, itemObj.getQty(), editTxt_qty.getText().toString());
			
			Log.i("", "show Marginal Price: "+showMarginalPrice);
			
			tv_marginal_price.setText(showMarginalPrice);*/
			
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
				Integer total = Integer.valueOf(Cart_Item_List.get(i).getPurchasePrice()) * Integer.valueOf(Cart_Item_List.get(i).getQty());
				grandTotal += total;
			}
			
			return grandTotal;
			
	}
	
	/*private PurchaseItemListAdapter.Callback callback = new  PurchaseItemListAdapter.Callback() {
		
		public void onPlusClick(Integer pos, Integer price) {
			// TODO Auto-generated method stub
			
			//Grand Total after plus one
			Cart_Item_List.get(pos).setQty(String.valueOf(Integer.valueOf(Cart_Item_List.get(pos).getQty()) + 1));
			Integer grandTotal = Integer.valueOf(grand_total.getText().toString()) + price;
			grand_total.setText(grandTotal+"");
			
		}
		
		public void onMinusClick(Integer pos, Integer price) {
			// TODO Auto-generated method stub
			
			//Grand Total after minus one
			Cart_Item_List.get(pos).setQty(String.valueOf(Integer.valueOf(Cart_Item_List.get(pos).getQty()) - 1));
			Integer grandTotal = Integer.valueOf(grand_total.getText().toString()) - price;
			grand_total.setText(grandTotal+"");
			
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
			scan.setError("Tab here to scan [or] type item code");
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
			SKToastMessage.showMessage(AddNewPurchaseVoucherActivity.this, "Press Marginal Price Button!", SKToastMessage.WARNING);
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

	