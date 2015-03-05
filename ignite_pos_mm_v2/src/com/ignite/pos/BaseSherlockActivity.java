package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Admin;
import com.smk.skalertmessage.SKToastMessage;

public class BaseSherlockActivity extends SherlockActivity{
	private EditText editTxt_admin_username;
	private EditText editTxt_password;
	private String strUsername;
	private String strPassword;
	private DatabaseManager dbManager;
	private List<Object> isadminList;
	private List<Object> adminList;
	public LoginCallbacks mCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	
	public void showAdminDialog(){
		
		// get admin_login.xml view
					LayoutInflater li = LayoutInflater.from(BaseSherlockActivity.this);
					View promptsView = li.inflate(R.layout.prompt_admin_login, null);

					final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							BaseSherlockActivity.this);
					
					// set prompt_admin_login.xml to alertdialog builder
					alertDialogBuilder.setView(promptsView);

					editTxt_admin_username = (EditText) promptsView.findViewById(R.id.editTxt_admin_username);
					editTxt_password = (EditText) promptsView.findViewById(R.id.editTxt_password);
					
					View dialogView = View.inflate(BaseSherlockActivity.this, R.layout.dialog_title, null);
					TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_dialog_title);
					dialogTitle.setText("Admin Log in");
					alertDialogBuilder.setCustomTitle(dialogView);
					
					// set dialog message
					alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Login", new DialogInterface.OnClickListener()
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
				              
				              if (checkFields()) {
				            	  
									strUsername = editTxt_admin_username.getText().toString();
									strPassword = editTxt_password.getText().toString();
									
									dbManager = new AdminController(BaseSherlockActivity.this);
									AdminController admin_control = (AdminController)dbManager;
									isadminList = new ArrayList<Object>();
									isadminList = admin_control.select();
										
									if (checkisExistAdmin()) {
										
										dbManager = new AdminController(BaseSherlockActivity.this);
										AdminController adminControl = (AdminController)dbManager;
										adminList = new ArrayList<Object>();
										adminList = adminControl.select(strUsername , strPassword);
										
										if (checkAdminAccount()) {
											
											wantToCloseDialog = true;
											
											Admin admin = (Admin) adminList.get(0);
											
											SharedPreferences sharedPreferences = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
											SharedPreferences.Editor editor = sharedPreferences.edit();
											
											editor.clear();
											editor.commit();
											
											editor.putString("admin_id", admin.getID());
											editor.putString("admin_name", admin.getAdminname());
											editor.putString("admin_password", admin.getAdminpassword());
											editor.putString("admin_userlevel", admin.getUserLevel());
											
											editor.commit();
											
											Log.i("", "Admin Name: "+admin.getAdminname());
											
											if (admin.getUserLevel().equals("Manager")) {
												Log.i("", "User Level(M): "+admin.getUserLevel());
											}else {
												Log.i("", "User Level(S): "+admin.getUserLevel());
											}
										}
									}
									
								}
				              
				              if(wantToCloseDialog){
				            	  alertDialog.dismiss();
				              }
				              
				        	  if(mCallback != null){
				        		  mCallback.onLogin();
				        	  }
				          }
				      });
	}
	
	public void setOnLoginListener(LoginCallbacks callbacks){
		this.mCallback = callbacks;
	}
	
	public interface LoginCallbacks{
		void onLogin();
	}
	
	public boolean checkFields() {
		if (editTxt_admin_username.getText().toString().length() == 0) {
			editTxt_admin_username.setError("Enter Admin Name");
			return false;
		}
		if (editTxt_password.getText().toString().length() == 0) {
			editTxt_password.setError("Enter Password");
			return false;
		}
		
		return true;
	}
	
	public boolean checkisExistAdmin() {
		if (isadminList == null || isadminList.size() == 0) {
			SKToastMessage.showMessage(BaseSherlockActivity.this, "No user account yet!.", SKToastMessage.ERROR);
			return false;
		}
		
		return true;
	}
	
	public boolean checkAdminAccount() {
		if (adminList == null || adminList.size() == 0) {
			SKToastMessage.showMessage(BaseSherlockActivity.this, "Invalid username & password.", SKToastMessage.ERROR);
			return false;
		}
		
		return true;
	}
}
