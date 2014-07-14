package com.example.hackathon1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,OnItemClickListener {

	Button addeventButton;
	private List<Schedule> scheduleList = new ArrayList<Schedule>();
	private ScheduleAdapter scheduleAdapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		addeventButton = (Button) findViewById(R.id.main_addevent);
		addeventButton.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.list_ListView);
		scheduleAdapter = new ScheduleAdapter();
		listView.setAdapter(scheduleAdapter);
		listView.setOnItemClickListener((OnItemClickListener) this);
		GetScheduleList getTask = new GetScheduleList();
		getTask.execute();
		
		SharedPreferences pref = getSharedPreferences(Schedule.PREF_KEY, Activity.MODE_PRIVATE);
        if(!pref.getString(Schedule.KEY_NAME_TEXT, "").equals("")){
        	Schedule schedule = new Schedule(
        			pref.getInt(Schedule.KEY_ID_INT, 0), 
        			pref.getString(Schedule.KEY_TIME_TEXT,""), 
        			pref.getString(Schedule.KEY_COMMENT_TEXT,""), 
        			pref.getString(Schedule.KEY_START_LOCATION_TEXT,""), 
        			pref.getString(Schedule.KEY_END_LOCATION_TEXT,""), 
        			new Driver(
        					pref.getString(Schedule.KEY_NAME_TEXT, ""),
        					pref.getInt(Schedule.KEY_AGE_TEXT, 0),
        					pref.getString(Schedule.KEY_TELEPHONE_TEXT, "000-0000-0000")
        			)
        	);
        	scheduleList.add(schedule);
        }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_addevent:
			Intent intent = new Intent(getApplicationContext(),YoteiActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(intent);
			finish();
			break;
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Schedule schedule = (Schedule) scheduleAdapter.getItem(position);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// アラートダイアログのタイトルを設定します
		alertDialogBuilder.setTitle("運転手情報");
		// アラートダイアログのメッセージを設定します
		alertDialogBuilder.setMessage("電話番号:" + schedule.driver.telephone);
		// アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		alertDialogBuilder.setPositiveButton("電話する",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 電話をかけるIntent処理
					}
				});
		// アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		alertDialogBuilder.setNegativeButton("キャンセル",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ダイアログを閉じる
						dialog.dismiss();
					}
				});
		// アラートダイアログのキャンセルが可能かどうかを設定します
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		// アラートダイアログを表示します
		alertDialog.show();
	}

	public class ScheduleAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return scheduleList.size();
		}

		@Override
		public Object getItem(int position) {
			return scheduleList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView nameText;
			TextView timeText;
			TextView locationText;
			View v = convertView;

			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.schedule, null);
			}
			
			nameText = (TextView)v.findViewById(R.id.listitem_nameText);
			timeText = (TextView)v.findViewById(R.id.listitem_timeText);
			locationText = (TextView)v.findViewById(R.id.listitem_locationText);

			Schedule schedule = (Schedule)getItem(position);
			try{
				nameText.setText(schedule.driver.name);
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			timeText.setText(schedule.time);
			locationText.setText(schedule.startLocation + "~"
						+ schedule.endLocation);
			return v;
		}
	}

	public class GetScheduleList extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... arg0) {
			// 指定する先のURL
			String baseurl = "https://dl.dropboxusercontent.com/u/31455721/test.json";
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(baseurl);
			HttpResponse httpResponse = null;
			
			try {
				httpResponse = httpClient.execute(request);
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int status = httpResponse.getStatusLine().getStatusCode();
			// 通信が成功した時　200
			if (status == HttpStatus.SC_OK) {
				// HTTPレスポンスから値を取り出す
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				String result="";
				try {
					//httpResponse.getEntity().writeTo(outputStream);
					result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
					Log.d("JSON",result);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return result;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject rootObj = new JSONObject(result);
				JSONArray dataArray = rootObj.getJSONArray("data");
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject scheduleObj = dataArray.getJSONObject(i);
					int id = scheduleObj.getInt("id");
					String time = scheduleObj.getString("time");
					String comment = scheduleObj.getString("comment");
					String startLocation = scheduleObj
							.getString("start-location");
					String endLocation = scheduleObj.getString("end-location");

					JSONObject driverObj = scheduleObj.getJSONObject("driver");
					String name = driverObj.getString("name");
					int age = driverObj.getInt("age");
					String telephone = driverObj.getString("telephone");
					Driver driver = new Driver(name, age, telephone);
					Schedule schedule = new Schedule(id, time, comment,
							startLocation, endLocation, driver);
					scheduleList.add(schedule);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			scheduleAdapter.notifyDataSetChanged();
		}
	}
}
