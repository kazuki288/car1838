package com.example.hackathon1;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Build;
import android.app.Activity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.content.Intent;
import android.content.SharedPreferences;


@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class YoteiActivity extends Activity {
	
	DatePicker datePicker;
	EditText endLocationEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yotei);
        ActionBar actionBar = getActionBar();
		actionBar.hide();
		this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Button bt=(Button)findViewById(R.id.button2);
        bt.setOnClickListener(new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	        	saveSchedule();
	        	Intent intent = new Intent(YoteiActivity.this, MainActivity.class);
	            startActivity(intent);
	            finish();
	            }
	        }
        );
        
        
	}
	
	private void saveSchedule(){
		datePicker = (DatePicker)findViewById(R.id.datePicker1);
		endLocationEdit = (EditText)findViewById(R.id.goal);
		SharedPreferences driverPref = getSharedPreferences(Driver.PREF_KEY, Activity.MODE_PRIVATE);
		Driver driver = new Driver(
				driverPref.getString(Driver.KEY_NAME_TEXT, ""),
				driverPref.getInt(Driver.KEY_AGE_TEXT,0),
				driverPref.getString(Driver.KEY_TELEPHONE_TEXT,"090-1649-5233")
		);
		
		SharedPreferences pref = getSharedPreferences(Schedule.PREF_KEY, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(Schedule.KEY_ID_INT, 100);
		String date = Integer.toString(datePicker.getYear())+"/"+Integer.toString(datePicker.getMonth())+"/"+Integer.toString(datePicker.getDayOfMonth());
		editor.putString(Schedule.KEY_TIME_TEXT,date); 
        editor.putString(Schedule.KEY_START_LOCATION_TEXT,""); 
        editor.putString(Schedule.KEY_END_LOCATION_TEXT,endLocationEdit.getText().toString()); 
        
        editor.putString(Schedule.KEY_NAME_TEXT, driver.name);
        editor.putInt(Schedule.KEY_AGE_TEXT, driver.age);
        editor.putString(Schedule.KEY_TELEPHONE_TEXT, driver.telephone);
        editor.commit();
	}
	
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}


		
		
		return super.onOptionsItemSelected(item);
//		Intent intent = new Intent(YoteiActivity.this, MainActivity.class);
//        startActivity(MainActivity);
//

}
	}
