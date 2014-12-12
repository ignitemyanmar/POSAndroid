
package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.model.PurchaseVoucher;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PurchaseUpdateItemListAdapter extends BaseAdapter{
	private List<Object> listObj;
	private LayoutInflater mInflater;
	private Activity aty;
	//private Callback mCallback;
	private ItemListController dbManager;
	private List<Object> listItems;

	public PurchaseUpdateItemListAdapter(Activity aty, List<Object> cart_Item_List) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.listObj = cart_Item_List;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listObj.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listObj.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView =  mInflater.inflate(R.layout.activity_voucher_list_item, null);
			
			holder = new ViewHolder();
			
			holder.item_name = (TextView)convertView.findViewById(R.id.txtItem_name);
			holder.price = (TextView)convertView.findViewById(R.id.txtUnitPrice);
			holder.total = (TextView)convertView.findViewById(R.id.txtTotal);
			holder.qty = (TextView)convertView.findViewById(R.id.txtQty);
			//holder.btnPlus = (TextView)convertView.findViewById(R.id.btnPlus);
			//holder.btnMinus = (TextView)convertView.findViewById(R.id.btnMinus);
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
			PurchaseVoucher pv = (PurchaseVoucher) getItem(position);	
			
			holder.item_name.setText(pv.getItemname());
			holder.qty.setText(pv.getQty());
			holder.price.setText(pv.getPurchasePrice());
			
			Integer totalAmount = Integer.valueOf(holder.qty.getText().toString()) * Integer.valueOf(holder.price.getText().toString());
			holder.total.setText(totalAmount.toString());
			holder.total.setTag(position);
			
			//holder.btnPlus.setTag(holder);
			/*holder.btnPlus.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ViewHolder viewHolder = (ViewHolder) v.getTag();
					int qty = Integer.valueOf( viewHolder.qty.getText().toString()) + 1;
					
					viewHolder.qty.setText(String.valueOf(qty));
					Integer price = Integer.valueOf((viewHolder.price.getText().toString()));
					Integer total = price * qty;
					viewHolder.total.setText(total+"");
					
					if(mCallback != null){
						mCallback.onPlusClick(position, price);
					}
					
				}
			});*/
			
			//holder.btnMinus.setTag(holder);
			/*holder.btnMinus.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ViewHolder viewHolder = (ViewHolder) v.getTag();
					int qty = Integer.valueOf( viewHolder.qty.getText().toString()) - 1;
					if (qty > 0) {
						viewHolder.qty.setText(String.valueOf(qty));
						Integer price = Integer.valueOf(viewHolder.price.getText().toString());
						Integer total = price * qty;
						viewHolder.total.setText(total+"");
						
						if(mCallback != null){
							mCallback.onMinusClick(position, price);
						}
					}
				}
			});*/
			
		
		return convertView;
	}
	
	/*public void setCallbackListiner(Callback callback){
		mCallback = callback;
	}*/
	
	/*public interface Callback{
		public void onPlusClick(Integer pos,Integer price);
		public void onMinusClick(Integer pos,Integer price);
		
	}*/
	
	 static class ViewHolder
	{
		TextView item_name , price, total , qty;
		//TextView btnPlus , btnMinus;
	}

}

