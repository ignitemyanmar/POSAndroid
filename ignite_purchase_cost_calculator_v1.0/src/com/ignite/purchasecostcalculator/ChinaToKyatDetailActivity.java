package com.ignite.purchasecostcalculator;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import com.ignite.purchasecostcalculator.adapter.DetailLvAdapter;
import com.ignite.purchasecostcalculator.application.AddNewExchangeRateDialog;
import com.ignite.purchasecostcalculator.application.AddNewItemDialog;
import com.ignite.purchasecostcalculator.application.BaseActivity;
import com.ignite.purchasecostcalculator.application.ChoiceDialog;
import com.ignite.purchasecostcalculator.application.UpdateDialog;
import com.ignite.purchasecostcalculator.application.BaseActivity.Callback;
import com.ignite.purchasecostcalculator.database.controller.ExchangeRateController;
import com.ignite.purchasecostcalculator.database.controller.ItemController;
import com.ignite.purchasecostcalculator.database.util.DatabaseManager;
import com.ignite.purchasecostcalculator.model.ExchangeRate;
import com.ignite.purchasecostcalculator.model.Item;
import com.smk.skalertmessage.SKToastMessage;

public class ChinaToKyatDetailActivity extends BaseActivity{

	private ListView lv_change_kyat;
	private ImageView img_back;
	private TextView txt_title;
	private LinearLayout ly_new_exchange_rate;
	private LinearLayout ly_new_item;
	private DetailLvAdapter adapter;
	private DatabaseManager dbManager;
	private TextView txt_foreign_currency;
	private List<Object> exchangeRate;
	private ExchangeRateController control;
	public static List<Object> itemList;
	private ItemController itemControl;
	public static Double chinaExRate;
	public static Double yuan_rate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foreign_to_kyat_detail);
		
		actionbar.setCustomView(R.layout.action_bar);
		img_back = (ImageView)actionbar.getCustomView().findViewById(R.id.img_back);
		txt_title = (TextView)actionbar.getCustomView().findViewById(R.id.txt_title);
		ly_new_exchange_rate = (LinearLayout)actionbar.getCustomView().findViewById(R.id.ly_new_exchange_rate);
		ly_new_item = (LinearLayout)actionbar.getCustomView().findViewById(R.id.ly_new_item);
		txt_title.setText("China Items");
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		img_back.setOnClickListener(clicklistener);
		ly_new_exchange_rate.setOnClickListener(clicklistener);
		ly_new_item.setOnClickListener(clicklistener);
		
		//Call Database & Check Rate exist
		txt_foreign_currency = (TextView)findViewById(R.id.txt_foreign_currency);
		getExhangeRate();
		
		itemList = new ArrayList<Object>();
		lv_change_kyat = (ListView)findViewById(R.id.lv_changekyat);
		getItems();
		
		lv_change_kyat.setOnItemLongClickListener(itemLongClickListener);
	}
	
	/**
	 * Get Final Exchange Rate
	 */
	public void getExhangeRate() {
		// TODO Auto-generated method stub
		exchangeRate = new ArrayList<Object>();
		dbManager = new ExchangeRateController(ChinaToKyatDetailActivity.this);
		control = (ExchangeRateController)dbManager;
		exchangeRate = control.select();
		
		if (exchangeRate.size() > 0 && exchangeRate != null) {
			chinaExRate = ((ExchangeRate)exchangeRate.get(0)).getExchangeRateChina();
			
			//Yuan Rate
			if (chinaExRate > 0) {
				yuan_rate = 1 / chinaExRate; 
			}else {
				yuan_rate = 0.00;
			}
		}else {
			chinaExRate = 0.00;
			yuan_rate = 0.00;
		}
		
		txt_foreign_currency.setText("ယြမ္ေစ်းႏႈန္း("+String.format("%.5f", chinaExRate)+")");
	}
	
	/**
	 * Get Items
	 */
	public void getItems() {
		// TODO Auto-generated method stub
		dbManager = new ItemController(ChinaToKyatDetailActivity.this);
		itemControl = (ItemController)dbManager;
		itemList = itemControl.select("china");
		
		Log.i("", "Item List : "+itemList.toString());
		
		if (itemList.size() > 0 && itemList != null) {
			//adapter.notifyDataSetChanged();
			adapter = new DetailLvAdapter(ChinaToKyatDetailActivity.this, itemList);
			lv_change_kyat.setAdapter(adapter);
		}else {
			alertDialog("No Item Yet!");
		}
	}
	
	private OnClickListener clicklistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == img_back) {
				finish();
			}
			
			if (v == ly_new_exchange_rate) {
				
				AddNewExchangeRateDialog exchangeRateDialog = new AddNewExchangeRateDialog(ChinaToKyatDetailActivity.this);
				exchangeRateDialog.setCallbackListener(new AddNewExchangeRateDialog.Callback() {
					
					public void onCancel() {
						// TODO Auto-generated method stub
					}

					@Override
					public void onUpdate(Double newRate) {
						// TODO Auto-generated method stub
						if (exchangeRate != null && exchangeRate.size() > 0) {
							
							ExchangeRate ex = (ExchangeRate)exchangeRate.get(0);
							List<Object> updateList = new ArrayList<Object>();
							updateList.add(new ExchangeRate(ex.getRateId(), newRate, ex.getExchangeRateThai()));
							control.update(updateList);
							
							Log.i("", "Success Update: "+updateList.toString());
						}else {
							List<Object> saveList = new ArrayList<Object>();
							saveList.add(new ExchangeRate(newRate, 0.0));
							control.save(saveList);
							Log.i("", "Success Save: "+saveList.toString());
						}
						
						getExhangeRate();
						getItems();
					}
				});
				
		        exchangeRateDialog.show();
		        return;
			}
			
			if (v == ly_new_item) {
				
				AddNewItemDialog newItemDialog = new AddNewItemDialog(ChinaToKyatDetailActivity.this);
				newItemDialog.setCallbackListener(new AddNewItemDialog.Callback() {
					
					public void onCancel() {
						// TODO Auto-generated method stub
					}

					@SuppressLint("DefaultLocale")
					@Override
					public void onSave(String itemName, Double purchasePrice,
							Double transportCost, Double otherCost) {
						// TODO Auto-generated method stub
						
						//Check Duplicate 
						if (itemList.size() > 0) {
							
							boolean isExist = false;
							
							for (int i = 0; i < itemList.size(); i++) {
								
								Item item = (Item)itemList.get(i);
								
								if (itemName.toLowerCase().equals(item.getItemName().toLowerCase())) {
									
									isExist = true;
									
									SKToastMessage.showMessage(getApplicationContext(),  itemName +" is already exist!", SKToastMessage.ERROR);
									
									break;
								}
							}

							if (!isExist) {
								saveItems(itemName, purchasePrice, transportCost, otherCost);
							}
						}else {
							saveItems(itemName, purchasePrice, transportCost, otherCost);
						}
					}

				});
				
				newItemDialog.show();
		        return;
			
			}
		}
	};
	
	/**
	 * Save Items 
	 */
	private void saveItems(String itemName, Double purchasePrice,
			Double transportCost, Double otherCost) {
		// TODO Auto-generated method stub
		List<Object> itemList = new ArrayList<Object>();
		dbManager = new ItemController(ChinaToKyatDetailActivity.this);
		ItemController control = (ItemController)dbManager;
		itemList.add(new Item(itemName, purchasePrice, transportCost, otherCost, "china"));
		control.save(itemList);
			
		Log.i("", "Success Save: "+itemList.toString());
		
		getItems();
	}
	
	public static String selected_item_name;
	public static String selected_purchase_price;
	public static String selected_transport_cost;
	public static String selected_other_cost;
	private Integer selectedId;
	private ItemController iControl; 
	
	private OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			// TODO Auto-generated method stub
			
			/*View childView = (View) lv_change_kyat.getChildAt(position);
			TextView txt_item_name = (TextView) childView.findViewById(R.id.txt_item_name);
			TextView txt_purchase_price = (TextView)childView.findViewById(R.id.txt_foreign_currency);
			TextView txt_transport_cost = (TextView)childView.findViewById(R.id.txt_transport_cost);
			TextView txt_other_cost = (TextView)childView.findViewById(R.id.txt_other_cost);*/
			
			Item item = (Item) parent.getAdapter().getItem(position);
			selected_item_name = item.getItemName();
			selected_purchase_price = item.getPurchasePrice().toString();
			selected_transport_cost = item.getTransportCost().toString();
			selected_other_cost = item.getOtherCost().toString();
			
			/*selected_item_name = txt_item_name.getText().toString();
			selected_purchase_price = txt_purchase_price.getText().toString();
			selected_transport_cost = txt_transport_cost.getText().toString();
			selected_other_cost = txt_other_cost.getText().toString();*/
			
			dbManager = new ItemController(ChinaToKyatDetailActivity.this);
			iControl = (ItemController)dbManager;
			//itemList = iControl.select("china");
			
			selectedId = ((Item)itemList.get(position)).getItemId();
			
			ChoiceDialog choiceDialog = new ChoiceDialog(ChinaToKyatDetailActivity.this);
			choiceDialog.setCallbackListener(new ChoiceDialog.Callback() {
				
				@Override
				public void onUpdate() {
					// TODO Auto-generated method stub
					UpdateDialog updateDialog = new UpdateDialog(ChinaToKyatDetailActivity.this);
					updateDialog.setCallbackListener(new UpdateDialog.Callback() {
						
						@Override
						public void onUpdate(String itemName, Double purchasePrice,
								Double transportCost, Double otherCost) {
							// TODO Auto-generated method stub
							
							List<Object> updateList = new ArrayList<Object>();
							
							updateList.add(new Item(selectedId, itemName, purchasePrice, transportCost, otherCost, "china"));
							iControl.update(updateList);
								
							Log.i("", "Success Update: "+control.select());
							
							getItems();
						}
						
						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
							
						}
					});
					
					updateDialog.show();
			        return;
				}
				
				@Override
				public void onDelete() {
					// TODO Auto-generated method stub
						
					removeItemFromList(position, selected_item_name);					
					setCallbackListener(new Callback() {
						
						@Override
						public void onDelete() {
							// TODO Auto-generated method stub
							iControl.delete(String.valueOf(selectedId));
							
							Log.i("", "Delete Success: "+iControl.select("thai"));
							
							itemList.remove(position);
							adapter = new DetailLvAdapter(ChinaToKyatDetailActivity.this, itemList);
							lv_change_kyat.setAdapter(adapter);
						}
					});
				}
			});
			
			choiceDialog.show();
			return true;
		}
	};
}


