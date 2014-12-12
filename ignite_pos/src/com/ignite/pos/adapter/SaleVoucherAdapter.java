package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleReturn;
import com.ignite.pos.model.SaleVouncher;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SaleVoucherAdapter extends BaseAdapter{

	private List<Object> sv_list_item;
	private LayoutInflater mInflater;
	private Activity aty;
	private DatabaseManager dbManager;
	private List<Object> item_list;
	private String itemName;
	private Integer return_qty;
	private SaleReturnAdapter saleReturnAdapter;
	private ListView lvReturn;
	public static List<Object> returnList;
	private String selected_item_name;
	
	
	//variables of Prompt of Add Prices
	TextView txt_return_qty;
	Button btn_plus;
	Button btn_minus;
	
	
	
	public SaleVoucherAdapter(Activity aty, List<Object> sv_list_item, ListView lvReturn) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.sv_list_item = sv_list_item;
		this.lvReturn = lvReturn;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return sv_list_item.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return sv_list_item.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView =  mInflater.inflate(R.layout.activity_sale_panel_list_item, null);
			
			holder = new ViewHolder();
			
			holder.txt_sale_item_name = (TextView)convertView.findViewById(R.id.txt_sale_item_name);
			holder.txt_sale_qty = (TextView)convertView.findViewById(R.id.txt_sale_qty);
			holder.txt_sale_price = (TextView)convertView.findViewById(R.id.txt_sale_price);
			holder.txt_item_total = (TextView)convertView.findViewById(R.id.txt_item_total);
			holder.btn_return = (Button)convertView.findViewById(R.id.btn_return);
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
			final SaleVouncher sv = (SaleVouncher)getItem(position);
			
			returnList = new ArrayList<Object>();
			
	        saleReturnAdapter = new SaleReturnAdapter(aty, returnList);
	        lvReturn.setAdapter(saleReturnAdapter);
	        
			//Get Item name
			dbManager = new ItemListController(aty);
			ItemListController itemControl = (ItemListController)dbManager;
			item_list = itemControl.select(sv.getItemid());
			
			if (item_list != null && item_list.size() > 0) {
				
				itemName = ((ItemList)item_list.get(0)).getItemName();
				holder.txt_sale_item_name.setText(itemName);
			}
			
			holder.txt_sale_qty.setText(sv.getQty());
			holder.txt_sale_price.setText(sv.getPrice()+"");
			
			Integer totalAmount = Integer.valueOf(holder.txt_sale_qty.getText().toString()) * Integer.valueOf(holder.txt_sale_price.getText().toString());
			holder.txt_item_total.setText(totalAmount+"");
			
			holder.btn_return.setId(position);
			//holder.btn_return.setTag(sv.getItemid());
			holder.btn_return.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					QtyAlert(v.getId(), sv.getItemid(), sv.getItemname(), sv.getQty(), sv.getPrice(), sv.getVdate(), sv.getVid(), sv.getReturnableQty());
					
				}
			});
		
		return convertView;
	}

		public void QtyAlert(int position, final String selected_item_id, final String selected_item_name, final String selected_qty, final String salePrice, final String saleDate, final String saleVouId, final Integer returnalbeQty) {
	    	
			Log.i("", "Returanable Qty: "+returnalbeQty);
			
	        final int pos = position;
	        
			// get prompt_add_price.xml view
			LayoutInflater li = LayoutInflater.from(aty);
			View promptsView = li.inflate(R.layout.prompt_return_qty, null);
			
	        final AlertDialog.Builder alert = new AlertDialog.Builder(
	                aty);
	        
			// set prompt_return_qty.xml to alertdialog builder
			alert.setView(promptsView);
			
			txt_return_qty =  (TextView)promptsView.findViewById(R.id.txt_return_qty);
			
			if (returnalbeQty == 0) {
				txt_return_qty.setText(selected_qty);
			}else {
				txt_return_qty.setText(returnalbeQty+"");
			}
			
			
			btn_plus =  (Button)promptsView.findViewById(R.id.btn_plus);
			btn_plus.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Integer qty = Integer.valueOf(txt_return_qty.getText().toString()) + 1;
					
					if (returnalbeQty == 0) {
						
						if (qty <= Integer.valueOf(selected_qty)) {
							txt_return_qty.setText(qty+"");
						}
					}else {
						if (qty <= returnalbeQty) {
							txt_return_qty.setText(qty+"");
						}
					}

				}
			});
			
			btn_minus =  (Button)promptsView.findViewById(R.id.btn_minus);
			btn_minus.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Integer qty = Integer.valueOf(txt_return_qty.getText().toString()) - 1;
					
					if (qty > 0) {
						txt_return_qty.setText(qty+"");
					}
				}
			});

	        alert.setTitle("Return Item - "+selected_item_name);
	        
	        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					return_qty = Integer.valueOf(txt_return_qty.getText().toString());

					List<Object> cartItems = new ArrayList<Object>();
					
						boolean isExist = false;
						
						for (int i = 0; i < returnList.size(); i++) {
							
							SaleReturn sr = (SaleReturn)returnList.get(i);
							
							if(selected_item_id.equals(sr.getItemid())){
								
								isExist = true;
								
								sr.setReturnQty(return_qty);
								
								break;
							}
						}
						
						if(!isExist){
							
							cartItems.add(new SaleReturn("", selected_item_id, Integer.valueOf(selected_qty)
							, return_qty, Integer.valueOf(salePrice), 0, "", "", selected_item_name, saleDate, saleVouId));
							
						}
						
						returnList.addAll(cartItems);
						
						Log.i("", "Retun List: "+returnList.toString());

						saleReturnAdapter.notifyDataSetChanged();
						setListViewHeightBasedOnChildren(lvReturn);
					
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
		
		

	static class ViewHolder
		{
			TextView txt_sale_item_name , txt_sale_qty, txt_sale_price , txt_item_total;
			Button btn_return;
		}

}


