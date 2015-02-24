package com.ignite.purchasecostcalculator;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ActionBar;
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
import android.widget.Toast;

import com.ignite.purchasecostcalculator.adapter.DetailLvAdapter;
import com.ignite.purchasecostcalculator.application.AddNewExchangeRateDialog;
import com.ignite.purchasecostcalculator.application.AddNewItemDialog;
import com.ignite.purchasecostcalculator.application.BaseActivity;
import com.ignite.purchasecostcalculator.application.ChoiceDialog;
import com.ignite.purchasecostcalculator.application.UpdateDialog;
import com.ignite.purchasecostcalculator.database.controller.ExchangeRateController;
import com.ignite.purchasecostcalculator.database.controller.ItemController;
import com.ignite.purchasecostcalculator.database.util.DatabaseManager;
import com.ignite.purchasecostcalculator.model.ExchangeRate;
import com.ignite.purchasecostcalculator.model.Item;
import com.smk.skalertmessage.SKToastMessage;

public class ThaiToKyatDetailActivity extends BaseActivity{

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
	private List<Object> itemList;
	private ItemController itemControl;
	private ImageView img_currency;
	public static Double thaiExRate;
	public static Double baht_rate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foreign_to_kyat_detail);
		
		actionbar.setCustomView(R.layout.action_bar);
		img_back = (ImageView)actionbar.getCustomView().findViewById(R.id.img_back);
		txt_title = (TextView)actionbar.getCustomView().findViewById(R.id.txt_title);
		img_currency = (ImageView)actionbar.getCustomView().findViewById(R.id.img_currency);
		img_currency.setImageResource(R.drawable.baht_white);
		ly_new_exchange_rate = (LinearLayout)actionbar.getCustomView().findViewById(R.id.ly_new_exchange_rate);
		ly_new_item = (LinearLayout)actionbar.getCustomView().findViewById(R.id.ly_new_item);
		txt_title.setText("Thai Items");
		
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
		dbManager = new ExchangeRateController(ThaiToKyatDetailActivity.this);
		control = (ExchangeRateController)dbManager;
		exchangeRate = control.select();
		
		if (exchangeRate.size() > 0 && exchangeRate != null) {
			thaiExRate = ((ExchangeRate)exchangeRate.get(0)).getExchangeRateThai();
			
			//1 kyat = baht ? 
			if (thaiExRate > 0) {
				baht_rate = 1 / thaiExRate; 
			}else {
				baht_rate = 0.00;
			}
		}else {
			thaiExRate = 0.00;
			baht_rate = 0.00;
		}
		
		txt_foreign_currency.setText("ဘတ္ေစ်းႏႈန္း("+String.format("%.5f", thaiExRate)+")");
	}
	
	/**
	 * Get Items
	 */
	public void getItems() {
		// TODO Auto-generated method stub
		dbManager = new ItemController(ThaiToKyatDetailActivity.this);
		itemControl = (ItemController)dbManager;
		itemList = itemControl.select("thai");
		
		Log.i("", "Item List : "+itemList.toString());
		
		if (itemList.size() > 0 && itemList != null) {
			//adapter.notifyDataSetChanged();
			adapter = new DetailLvAdapter(ThaiToKyatDetailActivity.this, itemList);
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
				
				AddNewExchangeRateDialog exchangeRateDialog = new AddNewExchangeRateDialog(ThaiToKyatDetailActivity.this);
				exchangeRateDialog.setTitle("Add New (Thai) Exchange Rate");
				exchangeRateDialog.edt_new_rate.setHint("1 က်ပ္ ၏ ဘတ္ေစ်း ကုိ ထည့္ပါ");
				exchangeRateDialog.txt_current_rate.setText("Old Rate: "+String.format("%.5f", thaiExRate));
				exchangeRateDialog.setCallbackListener(new AddNewExchangeRateDialog.Callback() {
					
					public void onCancel() {
						// TODO Auto-generated method stub
					}

					@Override
					public void onUpdate(Double newRate) {
						// TODO Auto-generated method stub
						if (exchangeRate != null && exchangeRate.size() > 0) {
							
							ExchangeRate ex = (ExchangeRate)exchangeRate.get(0);
							ExchangeRate exchangeObj = new ExchangeRate(ex.getRateId(), ex.getExchangeRateChina(), newRate);
							control.update(exchangeObj);
							
							Log.i("", "Success Update (Thai): "+control.select());
						}else {
							List<Object> saveList = new ArrayList<Object>();
							saveList.add(new ExchangeRate(0.0, newRate));
							control.save(saveList);
							Log.i("", "Success Save (Thai): "+control.select());
						}
						
						getExhangeRate();
						getItems();
					}
				});
				
		        exchangeRateDialog.show();
		        return;
			}
			
			if (v == ly_new_item) {
				
				AddNewItemDialog newItemDialog = new AddNewItemDialog(ThaiToKyatDetailActivity.this);
				newItemDialog.setTitle("Add New Item (Thai)");
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
		dbManager = new ItemController(ThaiToKyatDetailActivity.this);
		ItemController control = (ItemController)dbManager;
		itemList.add(new Item(itemName, purchasePrice, transportCost, otherCost, "thai"));
		control.save(itemList);
			
		Log.i("", "Success Save (Thai): "+control.select("thai"));
		
		getItems();
	}
	
	public static String selected_item_name;
	public static String selected_purchase_price;
	public static String selected_transport_cost;
	public static String selected_other_cost;
	private Integer selectedId;
	public static ItemController iControl; 
	
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
			
			dbManager = new ItemController(ThaiToKyatDetailActivity.this);
			iControl = (ItemController)dbManager;
			//itemList = new ArrayList<>();
			//itemList = iControl.select("thai");
			
			selectedId = ((Item)itemList.get(position)).getItemId();
			
			ChoiceDialog choiceDialog = new ChoiceDialog(ThaiToKyatDetailActivity.this);
			choiceDialog.setCallbackListener(new ChoiceDialog.Callback() {
				
				@Override
				public void onUpdate() {
					// TODO Auto-generated method stub
					UpdateDialog updateDialog = new UpdateDialog(ThaiToKyatDetailActivity.this);
					updateDialog.setTitle("Update - "+selected_item_name);
					updateDialog.edt_item_name.setText(selected_item_name);
					updateDialog.edt_purchase_price.setText(selected_purchase_price);
					updateDialog.edt_transport_cost.setText(selected_transport_cost);
					updateDialog.edt_other_cost.setText(selected_other_cost);
					updateDialog.setCallbackListener(new UpdateDialog.Callback() {
						
						@Override
						public void onUpdate(String itemName, Double purchasePrice,
								Double transportCost, Double otherCost) {
							// TODO Auto-generated method stub
							
							List<Object> updateList = new ArrayList<Object>();
							
							updateList.add(new Item(selectedId, itemName, purchasePrice, transportCost, otherCost, "thai"));
							iControl.update(updateList);
								
							Log.i("", "Success Update(Thai): "+iControl.selectByItemID(selectedId.toString()));
							
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
							//adapter.notifyDataSetChanged();
							adapter = new DetailLvAdapter(ThaiToKyatDetailActivity.this, itemList);
							lv_change_kyat.setAdapter(adapter);
						}
					});
				}
			});
			
			//choiceDialog.getWindow().setLayout(200, 120);
			choiceDialog.show();
			return true;
		}
	};
}



