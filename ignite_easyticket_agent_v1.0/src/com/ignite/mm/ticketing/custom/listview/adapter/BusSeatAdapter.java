package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.BusSelectSeatActivity;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorGroupUser;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;

public class BusSeatAdapter extends BaseAdapter{
	 private final Context _context;
	    private final List<Seat_list> list;
	  	    
	    public BusSeatAdapter(Activity atx, List<Seat_list> seat_list)
	    {
	        super();
	        this._context = atx;
	        this.list = seat_list;
	       
	    }
		public int getCount() {
			// TODO Auto-generated method stub
			return  list.size();
		}
		
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null)
	        {
	            convertView =LayoutInflater.from(_context).inflate(R.layout.list_item_seat, null);
	            holder = new ViewHolder();
	            holder.seat = (CheckBox) convertView.findViewById(R.id.chk_seat);
	            holder.seatNo = (TextView) convertView.findViewById(R.id.txt_seat_no);
	            holder.layout_customer_info = (RelativeLayout) convertView.findViewById(R.id.layout_customer_info);
	            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
	            holder.txt_phone = (TextView) convertView.findViewById(R.id.txt_phone);
	            holder.txt_nrc = (TextView) convertView.findViewById(R.id.txt_nrc);
	            holder.txt_ticket_no = (TextView) convertView.findViewById(R.id.txt_ticket_no);
	            holder.txt_agent = (TextView) convertView.findViewById(R.id.txt_agent);
	            holder.txt_seating_no = (TextView) convertView.findViewById(R.id.txt_seating_no);
	            holder.cover = (View) convertView.findViewById(R.id.v_cover);
	            convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			switch(getColor(list.get(position).getOperatorgroup_id())){
				case 1:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_1);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_1_1);
					break;
				case 2:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_2);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_2_1);
					break;
				case 3:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_3);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_3_1);
					break;
				case 4:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_4);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_4_1);
					break;
				default:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0_1);
					
			}
			
			//Already Purchase or Booking
			if(list.get(position).getStatus() == 2){
				holder.seat.setEnabled(false);
            	if(list.get(position).getCustomerInfo() != null){
            		holder.layout_customer_info.setVisibility(View.VISIBLE);
            		holder.txt_name.setText(list.get(position).getCustomerInfo().getName());
            		holder.txt_phone.setText(list.get(position).getCustomerInfo().getPhone());
            		holder.txt_nrc.setText(list.get(position).getCustomerInfo().getNrcNo());
            		holder.txt_ticket_no.setText(list.get(position).getCustomerInfo().getTicketNo());
            		holder.txt_agent.setText(list.get(position).getCustomerInfo().getAgentName());
            		holder.txt_seating_no.setText(list.get(position).getSeat_no());
            		//Check Remark
        			if(list.get(position).getRemark_type() != 0){
        				holder.txt_seating_no.setBackgroundColor(Color.YELLOW);
        			}
            	}else{
            		holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	}
            }
            
            if(list.get(position).getStatus() == 3){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seat.setChecked(true);
            	holder.seatNo.setText(list.get(position).getSeat_no());
            }
            
            if(list.get(position).getStatus() == 1){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seatNo.setText(list.get(position).getSeat_no());
            	holder.seat.setEnabled(true);
            	holder.seat.setTag(position);
            	holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							//If checked the seat.
							String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
							if(!BusSelectSeatActivity.SelectedSeat.isEmpty()){
								boolean isExisted = false;
								for (int i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}
							
							
						}else{
							//If unchecked the seat.
							String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
							if(!BusSelectSeatActivity.SelectedSeat.isEmpty()){
								BusSelectSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										BusSelectSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
								
							}
						}
						
					}
				});
            }
            
            if(list.get(position).getStatus() == 0){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seat.setVisibility(View.INVISIBLE);
            	holder.seatNo.setVisibility(View.INVISIBLE);
            }
            
	        return convertView;
		}
		
		private int getColor(int Id){
			int color = 0;
			for(OperatorGroupUser groupUser: BusSelectSeatActivity.groupUser){
				if(groupUser.getId() == Id){
					color = groupUser.getColor();
					break;
				}
			}
			return color;
		}
		
		 static class ViewHolder {
				CheckBox seat;
				RelativeLayout layout_customer_info;
				TextView txt_name;
				TextView txt_phone;
				TextView txt_nrc;
				TextView txt_ticket_no;
				TextView txt_agent;
				TextView txt_seating_no;
				View cover;
				TextView seatNo;
			}
}
