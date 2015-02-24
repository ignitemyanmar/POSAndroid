package com.ignite.codegenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat") public class MainActivity extends Activity {

    private TextView txt_code;
	private ActionBar actionbar;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        txt_code = (TextView)findViewById(R.id.txt_code);
        
        getSysLongDate();
        
    }
	
	/**
	 *  Get Code
	 * @return
	 */
	private void getSysLongDate(){
		long long_date = 0;
		Calendar c = Calendar.getInstance(Locale.getDefault());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:59");
		String formattedDate = df.format(c.getTime());
		try {
			Log.i("","Hello Date String: "+ formattedDate);
			
			long_date =  df.parse(formattedDate).getTime() / 1000;
			
			Log.i("","Hello Long Date: "+ long_date);
			Log.i("","Hello - Long Date: "+ long_date / 3600);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long code_generate = long_date / 3600;
		
		txt_code.setText(code_generate+"");
		
		//return long_date;
	}
}
