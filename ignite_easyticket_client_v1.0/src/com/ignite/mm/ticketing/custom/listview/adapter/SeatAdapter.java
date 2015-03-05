package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.SelectingSeatActivity;
import com.ignite.mm.ticketing.sqlite.database.model.Seat;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SeatAdapter extends BaseAdapter
{
    private final Context _context;
    private final List<Seat> list;
    protected ViewHolder[] mHolder;
	private int pos;
	
    public SeatAdapter(Activity atx, List<Seat> list)
    {
        super();
        this._context = atx;
        this.list = list;
        mHolder = new ViewHolder[list.size()];
       
    }

    public Seat getItem(int position)
    {
        return list.get(position);
    }

    public int getCount()
    {
        return list.size();
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){

    	final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(R.layout.list_item_seat, null);
            holder = new ViewHolder();
            holder.seat = (CheckBox) convertView.findViewById(R.id.chk_seat);
            holder.cover = (View) convertView.findViewById(R.id.v_cover);
            convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		mHolder[position] = holder;
		pos = position + 1;
		
        // Get a reference to the Day gridcell
        if(SelectingSeatActivity.SelectedPriceValue == null){
        	        	
        	if(list.get(position).getStatus().equals("4")){
        		holder.seat.setEnabled(true);
            	holder.seat.setTag(position);
        		holder.seat.setButtonDrawable(R.drawable.rdo_shape_couple);
        		holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        			
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						if(isChecked){
							mHolder[isCouple(position)].seat.setChecked(true);
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								boolean isExisted = false;
								for (int i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}

						}else{
							
							mHolder[isCouple(position)].seat.setChecked(false);
							//If unchecked the seat.
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								SelectingSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										SelectingSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
								
							}
						}
						
					}
				});
        		
        	}else{
        		holder.seat.setButtonDrawable(R.drawable.rdo_shape_0);
        	}
        	
        	if(list.get(position).getStatus().equals("2")){
            	holder.seat.setEnabled(false);
            }
            
            if(list.get(position).getStatus().equals("1")){
            	holder.seat.setChecked(true);
            }
            
            if(list.get(position).getStatus().equals("0")){
            	holder.seat.setEnabled(true);
            	holder.seat.setTag(position);
            	holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							//If checked the seat.
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								boolean isExisted = false;
								for (int i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}
							
							
						}else{
							//If unchecked the seat.
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								SelectingSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										SelectingSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
								
							}
						}
						
					}
				});
            }
                        
            if(list.get(position).getStatus().equals("3")){
            	holder.seat.setVisibility(View.INVISIBLE);
            }
            
        }
        else{
        	if(list.get(position).getStatus().equals("4")){
        		holder.seat.setEnabled(true);
            	holder.seat.setTag(position);
        		holder.seat.setButtonDrawable(R.drawable.rdo_shape_couple);
        		holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        			
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							mHolder[isCouple(position)].seat.setChecked(true);
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								boolean isExisted = false;
								for (int i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}
							
						}else{
							mHolder[isCouple(position)].seat.setChecked(false);
							//If unchecked the seat.
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								SelectingSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										SelectingSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
								
							}
						}
						
					}
				});
        	}else{
        		holder.seat.setButtonDrawable(R.drawable.rdo_shape_0);
        	}
        	
        	// with choose price
        	
        	if(SelectingSeatActivity.SelectedPriceValue.equals(list.get(position).getPrice())){
        		holder.cover.setVisibility(View.INVISIBLE);
        		holder.cover.setClickable(false);
        	}else{
        		holder.cover.setVisibility(View.VISIBLE);
        		holder.cover.setClickable(true);
        	}
        	if(list.get(position).getStatus().equals("2")){
            	holder.seat.setEnabled(false);
            }
            
            if(list.get(position).getStatus().equals("1")){
            	holder.seat.setChecked(true);
            }
            
            if(list.get(position).getStatus().equals("0")){
            	holder.seat.setEnabled(true);
            	holder.seat.setTag(position);
            	holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							//If checked the seat.
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								boolean isExisted = false;
								for (int i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								SelectingSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}
							
							
						}else{
							//If unchecked the seat.
							String[] seleted = SelectingSeatActivity.SelectedSeat.split(",");
							if(!SelectingSeatActivity.SelectedSeat.isEmpty()){
								SelectingSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										SelectingSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
								
							}
						}
						
					}
				});
            }            
            
            if(list.get(position).getStatus().equals("3")){
            	holder.seat.setVisibility(View.INVISIBLE);
            	holder.cover.setVisibility(View.INVISIBLE);
        		holder.cover.setClickable(false);
            }
        }
        
        
        return convertView;
    }  
    
    public int isCouple(int pos){
    	int single = 0;
    	int[][] couple = SelectingSeatActivity.couple_list;
    	for (int i = 0; i < couple.length; i++) {
			for (int j = 0; j < 2; j++) {
				if(couple[i][0] == pos){
					single = couple[i][1];
				}
				if(couple[i][1] == pos){
					single = couple[i][0];
				}
			}
		}
    	return single;
    }
    
    static class ViewHolder {
		CheckBox seat;
		View cover;
	}
}

