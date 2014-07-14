package com.example.hackathon1;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class RegisterActivity extends Activity implements OnClickListener {
	
    private Button tourokuButton;
    private EditText nameEdit;
    private EditText ageEdit;
    private EditText telephoneEdit;
    
    private SharedPreferences pref;  
    private SharedPreferences.Editor editor;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getActionBar();
		actionBar.hide();
		this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
        
        tourokuButton = (Button)findViewById(R.id.register_touroku);
        tourokuButton.setOnClickListener(this);
        nameEdit = (EditText)findViewById(R.id.register_EditName);
       /* ageEdit = (EditText)findViewById(R.id.register_EditAge);
        telephoneEdit = (EditText)findViewById(R.id.register_EditTelephone);*/
        pref = getSharedPreferences(Driver.PREF_KEY, Activity.MODE_PRIVATE);
        nameEdit.setText(pref.getString(Driver.KEY_NAME_TEXT, ""));
       /* ageEdit.setText(pref.getInt(Driver.KEY_AGE_TEXT, 20));
        telephoneEdit.setText(pref.getString(Driver.KEY_TELEPHONE_TEXT, "090-1947-3846"));*/
      
    }
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.register_touroku:
			// Editor の設定  
		      editor = pref.edit();  
		      // Editor に値を代入  
		      editor.putString(
		          Driver.KEY_NAME_TEXT, 
		          nameEdit.getText().toString()
		      );  
		     /* editor.putString(Driver.KEY_AGE_TEXT, ageEdit.getText().toString());
		      editor.putString(Driver.KEY_TELEPHONE_TEXT, telephoneEdit.getText().toString());*/
		      // データの保存  
		      editor.commit();
			Intent intent = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(intent);
			break;
		}
	}

}
