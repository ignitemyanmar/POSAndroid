package com.ignite.mm.ticketing.application;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class BookingDialog extends Dialog {

	private Button btn_cancel;
	private Button btn_save;
	private Callback mCallback;
	private AutoCompleteTextView edt_change;
	
	private Context ctx;
	private EditText edt_name;
	private EditText edt_phone;
	private LinearLayout layout_remark;
	private Spinner sp_remark_type;
	private EditText edt_remark;
	protected int selectedRemarkType;

	public BookingDialog(Context context, List<Agent> list) {
		super(context);
		// TODO Auto-generated constructor stub
		agentList = list;
		ctx = context;
		setContentView(R.layout.dialog_booking);
		edt_name = (EditText) findViewById(R.id.edt_name);
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		edt_change = (AutoCompleteTextView) findViewById(R.id.edt_agent);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		sp_remark_type = (Spinner) findViewById(R.id.sp_remark_type);
		edt_remark = (EditText) findViewById(R.id.edt_remark);
		btn_save = (Button) findViewById(R.id.btn_change_agent);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add("မွတ္ခ်က္ အမ်ိဳးအစား  ေရြးရန္");
		remarkTypes.add("လမ္းၾကိဳ");
		remarkTypes.add("ေတာင္းရန္");
		remarkTypes.add("ခုံေရြ႕ရန္");
		remarkTypes.add("Date Change ရန္");
		remarkTypes.add("စီးျဖတ္");
		remarkTypes.add("ေတာင္းေရာင္း");
		remarkTypes.add("ဆက္သြား");
		
		ArrayAdapter<String> remarkAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_dropdown_item_1line, remarkTypes);
		sp_remark_type.setAdapter(remarkAdapter);
		sp_remark_type.setOnItemSelectedListener(remarkTypeSelectedListener);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		setupAgents(edt_change);
		setTitle("Booking Order");
	}
	
	private OnItemSelectedListener remarkTypeSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2 == 0){
				layout_remark.setVisibility(View.GONE);
			}else{
				layout_remark.setVisibility(View.VISIBLE);
			}
			selectedRemarkType = arg2;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_cancel){
				if(mCallback != null){
					dismiss();
					mCallback.onCancel();
				}
			}
			if(v == btn_save){
				if(mCallback != null){
					if(AgentID.length() != 0){
						dismiss();
						mCallback.onSave(AgentID,edt_name.getText().toString(),edt_phone.getText().toString(), selectedRemarkType, edt_remark.getText().toString());
					}else{
						edt_change.setError("Please Choose Agents.");
					}
					
				}
			}
		}
	};
	protected String AgentID = "";
	private List<Agent> agentList;
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	private void setupAgents(AutoCompleteTextView textView){
		ArrayAdapter<Agent> agentListAdapter = new ArrayAdapter<Agent>(ctx, android.R.layout.simple_dropdown_item_1line, agentList);
		textView.setAdapter(agentListAdapter);
		textView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
	           	AgentID = ((Agent)arg0.getAdapter().getItem(arg2)).getId().toString();
			}
		});
		
		textView.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				Log.i("","Hello Text Change: "+ s);
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public interface Callback{
		void onSave(String agentId, String custName, String custPhone, int remarkType, String remark);
		void onCancel();
	}

}
