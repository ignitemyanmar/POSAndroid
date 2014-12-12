package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.pos.adapter.BuyerSpinnerAdapter;
import com.ignite.pos.adapter.CategoriesListAdapter;
import com.ignite.pos.adapter.ItemGridAdapter;
import com.ignite.pos.adapter.ItemListAdapter;
import com.ignite.pos.adapter.SubCategoriesListAdapter;
import com.ignite.pos.application.DeviceUtil;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.BundleListObjet;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.SubCategory;
import com.smk.skalertmessage.SKToastMessage;

@SuppressLint("ShowToast")
public class SaleActivity  extends SherlockActivity{

	private ActionBar actionBar;
	private Button change_mode , categories , search ;
	private LinearLayout scanner_mode , picker_mode;
	private boolean clicked = false;
	private DatabaseManager dbManager;
	private List<Object> list_obj;
	private ListView lvitem_list;
	private EditText scan;
	private String ItemID;
	private List<SaleVouncher> Cart_Item_List;
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
	public static ItemListAdapter itemAdapter;
	public String currentDate;
	private VoucherAutoID autoId;
	private String auto_voucher_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vouncher);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		login = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		login.setText(SaleLoginActivity.strname);
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setOnClickListener(clickListener);
		categories = (Button)actionBar.getCustomView().findViewById(R.id.btnCategories);
		categories.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);		
		
		scanner_mode = (LinearLayout)findViewById(R.id.scanner_mode);
		picker_mode = (LinearLayout)findViewById(R.id.picker_mode);
		scan = (EditText)findViewById(R.id.editText_scan);
		SP_buyername = (Spinner)findViewById(R.id.sp_buyer_name);
		SP_buyername.setOnItemSelectedListener(buyernameClickListener);
		search = (Button)findViewById(R.id.btnSearch);
		vouncherno = (TextView)findViewById(R.id.txt_vouncher_no);
		Cart_Item_List = new ArrayList<SaleVouncher>();
		lvitem_list = (ListView)findViewById(R.id.lvDetails_vou);
		priceTotal = (TextView)findViewById(R.id.txtTotal);
		plus = (TextView)findViewById(R.id.btnPlus);
		minus = (TextView)findViewById(R.id.btnMinus);
		Discount = (TextView)findViewById(R.id.txtDiscount);
		deleteItem = (TextView)findViewById(R.id.btnDelete_items);
		CheckOut = (TextView)findViewById(R.id.btnCheckout);
		
		search.setOnClickListener(clickListener);
		plus.setOnClickListener(clickListener);
		minus.setOnClickListener(clickListener);
		deleteItem.setOnClickListener(clickListener);
		CheckOut.setOnClickListener(clickListener);
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
		
	}
	
	private OnItemSelectedListener buyernameClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			buyer = (Buyer)buyer_obj.get(position);
			buyerName = buyer.getCusName();
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
			RelativeLayout lstItemView = (RelativeLayout) childView.findViewById(R.id.lst_item_view);
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
    
        alert.setTitle("Delete Item - "+selected_item_name+" ?");
        
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Cart_Item_List.remove(deletePosition);
            	itemAdapter.notifyDataSetChanged();
            	
            	priceTotal.setText(defaultGrandTotal()+"0");
            	Discount.setText("0");
            	
    			if(Cart_Item_List.size() == 0){
    				priceTotal.setText("0.00");
    				Discount.setText("0");
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
		buyer_obj = control.select();
		SP_buyername.setAdapter(new BuyerSpinnerAdapter(this,buyer_obj));
		Log.i("","Buyer List:" + buyer_obj.toString());
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == change_mode)
			{
				if(!clicked)
				{
					clicked = true;
					change_mode.setText("Change Scanner Mode");
					categories.setVisibility(View.VISIBLE);
					getCategories();
					scanner_mode.setVisibility(LinearLayout.INVISIBLE);
					picker_mode.setVisibility(LinearLayout.VISIBLE);
					scanner_mode.setTranslationX(DeviceUtil.getInstance(SaleActivity.this).getWidth());
					picker_mode.setTranslationX(0);
				}
				else{
					clicked = false;
					change_mode.setText("Change Picker Mode");
					categories.setVisibility(View.GONE);
					scanner_mode.setVisibility(LinearLayout.VISIBLE);
					picker_mode.setVisibility(LinearLayout.INVISIBLE);
					scanner_mode.setTranslationX(0);
					picker_mode.setTranslationX(DeviceUtil.getInstance(SaleActivity.this).getWidth());
				}
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
					
					double discount_amount = ( defaultGrandTotal() * discount_percent ) / 100; 
					double discounted_total = defaultGrandTotal() - discount_amount ;
					priceTotal.setText(discounted_total+"0");
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
						
						double discount_amount = ( defaultGrandTotal() * discount_percent ) / 100; 
						double discounted_total = defaultGrandTotal() - discount_amount ;
						priceTotal.setText(discounted_total+"0");
					}
				} else {
					
					//minus.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
					alert.setTitle("No Items!");
					alert.show();
					alert.setCancelable(true);
				}
				
			}
			if( v == deleteItem)
			{
				if (Cart_Item_List.size() != 0) {
					
					//deleteItem.setClickable(true);
					
					Cart_Item_List.clear();
					itemAdapter.notifyDataSetChanged();

	    			if(Cart_Item_List.size() == 0){
	    				priceTotal.setText("0.00");
	    				Discount.setText("0");
	    			}
				} else {
					
					//deleteItem.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
					alert.setTitle("No Items!");
					alert.show();
					alert.setCancelable(true);
				}
				
			}
			if(v == CheckOut)
			{
				if (Cart_Item_List.size() != 0) {
					
					//CheckOut.setEnabled(true);
					
					saveVouncher();
					
					
					
				} else {
					
					//CheckOut.setClickable(false);
					AlertDialog.Builder alert = new AlertDialog.Builder(SaleActivity.this);
					alert.setTitle("No Items!");
					alert.show();
					alert.setCancelable(true);
				}
			}
		}
	};
	
	private BundleListObjet bundleListObjet;
	private List<Object> listItems;
	private Integer stock_balance_qty;
	
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
			((SaleVouncher)Cart_Item_List.get(i)).setVid(vouncherno.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setCusname(buyerName);
			((SaleVouncher)Cart_Item_List.get(i)).setItemid(ItemID);
			((SaleVouncher)Cart_Item_List.get(i)).setQty(txt_qty.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setPrice(txt_price.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setItemname(txt_item_name.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setItemtotal(txt_total.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setVdate(currentDate);
			((SaleVouncher)Cart_Item_List.get(i)).setTotal(priceTotal.getText().toString());
			((SaleVouncher)Cart_Item_List.get(i)).setSalePerson(login.getText().toString());
			Cart_Item_List.get(i).setDiscount(Discount.getText().toString());
			
			bundleListObjet.getSaleVouncher().add(Cart_Item_List.get(i));
			
			saleVouncher.add((Object) Cart_Item_List.get(i));
		}
		
		control.save(saleVouncher);
		
		Log.i("", "Sale Voucher List in DB : " + control.select().toString());
		
		
		
		//Get Stock Qty & decrease stock qty after check out
			/*for (int i = 0; i < saleVouncher.size(); i++) {
				
				List<Object> saleVoucherList = new ArrayList<Object>();
				saleVoucherList = control.select();
				
				SaleVouncher salevoucherobj = (SaleVouncher) saleVoucherList.get(i);
				String id = salevoucherobj.getItemid();
				
				Log.i("", "voucher list qty: "+saleVouncher.size());
				Log.i("", "Item Id: "+salevoucherobj.getItemid());
				
				dbManager = new ItemListController(getApplicationContext());
				ItemListController controller = (ItemListController) dbManager;
				
				listItems = new ArrayList<Object>();
				listItems = controller.select(id);
				
				Log.i("", "Stock item list: "+listItems.toString());
				
				if (listItems.size() > 0) {
					
					ItemList itemList = (ItemList) listItems.get(0);
					stock_balance_qty = Integer.valueOf(itemList.getQty()) - Integer.valueOf(salevoucherobj.getQty());
					
					Log.i("", "stock balance qty: "+stock_balance_qty);
					itemList.setQty(stock_balance_qty.toString());
				}
			}*/
		
		auto_voucher_id = autoId.generateAutoID();
		vouncherno.setText(auto_voucher_id);
		
		Cart_Item_List.clear();
		
		//SKToastMessage.showMessage(getApplicationContext(), "Thank You. Check Out Successful!", SKToastMessage.SUCCESS);
		
		priceTotal.setText("0.00");
		Discount.setText("0");
		itemAdapter.notifyDataSetChanged();
		
		
		startActivity(new Intent(getApplicationContext(), 
				VoucherSlipActivity.class).putExtra("saleVoucher", new Gson().toJson(bundleListObjet)));
		
	}
	
	private void getScannerItem(String ItemID)
	{
		dbManager = new ItemListController(this);
		ItemListController item_controller = (ItemListController)dbManager;
		list_obj = new ArrayList<Object>();
		list_obj = item_controller.select(scan.getText().toString());
		
		List<SaleVouncher> cartItems = new ArrayList<SaleVouncher>();
		
		Log.i("","ItemID from DB :" + ItemID + " and " + list_obj.toString());
		
		if (list_obj.size() > 0) {
			
			boolean isExist = false;
			for (int i = 0; i < Cart_Item_List.size(); i++) {
				if(((ItemList)list_obj.get(0)).getItemId().equals(((SaleVouncher)Cart_Item_List.get(i)).getItemid()))
				{
					isExist = true;
					Integer plusOne = Integer.valueOf(((SaleVouncher)Cart_Item_List.get(i)).getQty()) + 1 ; 
					((SaleVouncher)Cart_Item_List.get(i)).setQty(plusOne.toString());
					
					break;
				}
			}
			if(!isExist){
				cartItems.add(new SaleVouncher(vouncherno.getText().toString(), buyerName, ((ItemList) list_obj.get(0)).getItemId()
						, ((ItemList)list_obj.get(0)).getItemName(), "1"
						, ((ItemList)list_obj.get(0)).getItemPrice(), ((ItemList)list_obj.get(0)).getCategoryId()
						, ((ItemList)list_obj.get(0)).getSubCategoryId(), "0", currentDate, "0", SaleLoginActivity.strname, "0"));
				Cart_Item_List.addAll(cartItems);
			}
			itemAdapter.notifyDataSetChanged();
			priceTotal.setText(defaultGrandTotal()+"0");
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
			list_obj = new ArrayList<Object>();
			list_obj = item_control.select(CatID,SubID);
			
			Log.i("","Get Item List :"+ list_obj.toString());
			
			if(list_obj != null && list_obj.size() > 0)
			{
				grid_categories.setAdapter(new ItemGridAdapter(SaleActivity.this, list_obj));
				grid_categories.setOnItemClickListener(itemClickListener);
			}
		}
	};
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i("TAG", "--> onItemClick listener...");    
			dbManager = new ItemListController(SaleActivity.this);
			itemObj = (ItemList)list_obj.get(position);
			ItemID = String.valueOf(itemObj.getItemId());
			
			Log.i("TAG", "ItemID --> " + ItemID);
			
			List<SaleVouncher> cartItems = new ArrayList<SaleVouncher>();
			
			if (itemObj != null) {
				
				boolean isExist = false;
				
				for (int i = 0; i < Cart_Item_List.size(); i++) {
					if(itemObj.getItemId().equals(((SaleVouncher)Cart_Item_List.get(i)).getItemid())){
						
						isExist = true;
						Integer plusOne = Integer.valueOf(((SaleVouncher)Cart_Item_List.get(i)).getQty()) + 1 ; 
						((SaleVouncher)Cart_Item_List.get(i)).setQty(plusOne.toString());
						
						break;
					}
				}
				
				if(!isExist){
						
					cartItems.add(new SaleVouncher(vouncherno.getText().toString(), buyerName, itemObj.getItemId()
								, itemObj.getItemName(), "1", itemObj.getItemPrice()
								, itemObj.getCategoryId(), itemObj.getSubCategoryId()
								, "0", currentDate, "0", SaleLoginActivity.strname, "0"));
				}
				
				Cart_Item_List.addAll(cartItems);
				
			}
			
			itemAdapter.notifyDataSetChanged();
			priceTotal.setText(defaultGrandTotal()+"0");
		}
	};

	private double defaultGrandTotal()
	{		
			double grandTotal = 0; 
			
			for (int i = 0; i < Cart_Item_List.size(); i++) {
				double total = Double.valueOf(Cart_Item_List.get(i).getPrice()) * Double.valueOf(Cart_Item_List.get(i).getQty());
				grandTotal += total;
			}
			
			return grandTotal;
			
	}
	
	private ItemListAdapter.Callback callback = new  ItemListAdapter.Callback() {
		
		public void onPlusClick(Integer pos, Double price) {
			// TODO Auto-generated method stub
			
			//Grand Total after plus one
			Cart_Item_List.get(pos).setQty(String.valueOf(Integer.valueOf(Cart_Item_List.get(pos).getQty()) + 1));
			Double grandTotal = Double.valueOf(priceTotal.getText().toString()) + price;
			priceTotal.setText(grandTotal+"0");
		}
		
		public void onMinusClick(Integer pos, Double price) {
			// TODO Auto-generated method stub
			
			//Grand Total after minus one
			Cart_Item_List.get(pos).setQty(String.valueOf(Integer.valueOf(Cart_Item_List.get(pos).getQty()) - 1));
			Double grandTotal = Double.valueOf(priceTotal.getText().toString()) - price;
			priceTotal.setText(grandTotal+"0");
			
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
	
	public boolean checkFields() {
		if (scan.getText().toString().length() == 0) {
			scan.setError("Tab here to scan");
			return false;
		}
		
		return true;
	}
	
}

	