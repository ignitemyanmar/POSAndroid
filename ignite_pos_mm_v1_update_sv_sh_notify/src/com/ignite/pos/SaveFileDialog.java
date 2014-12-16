package com.ignite.pos;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SaveFileDialog extends Dialog {

	private EditText edt_file_name;
	private Button btn_cancel;
	private Button btn_save;
	private Callback mCallback;
	private CheckBox chk_pdf;
	private CheckBox chk_excel;

	public SaveFileDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_file_name);
		edt_file_name = (EditText) findViewById(R.id.txt_file_name);
		chk_pdf = (CheckBox) findViewById(R.id.chk_pdf);
		chk_excel = (CheckBox) findViewById(R.id.chk_excel);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_save = (Button) findViewById(R.id.btn_save);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		
		setTitle("PDF File Name");

	}
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
					
					if (checkField()) {
						String fileName = edt_file_name.getText().toString();
						
						mCallback.onSave(fileName,chk_pdf.isChecked(), chk_excel.isChecked());
						dismiss();
					}

				}
			}
		}
	};
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	protected boolean checkField() {
		// TODO Auto-generated method stub
		if (edt_file_name.getText().toString().length() == 0) {
			edt_file_name.setError("Enter File Name");
			return false;
		}
		if (chk_excel.isChecked() == false) {
			chk_excel.setError("Check File Type");
			return false;
		}
		return true;
	}

	public interface Callback{
		void onSave(String filename, boolean PDFChecked, boolean ExcelChecked);
		void onCancel();
	}
	
	

}
