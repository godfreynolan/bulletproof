package com.riis.validatephone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.riis.validatephone.AlertDialogs;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;

public class LoginActivity extends Activity {

	private Button loginButton;
	private SharedPreferences sharedPrefs;
	
	public static final String APP_TAG = "com.riis.validatephone";
	public static final int MinPassLen = 6;
	public static final String SHARED_PREF_NAME = "mySharedPrefs";
	public static final String DEVICE_ID = "lastDevice";
	public static String androidID = ""; 
	public static String spAndroidID = ""; 

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        sharedPrefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        
        initializeViews();
        bindListenersToViews();
        
    }

	private void initializeViews() {
	    loginButton = (Button) findViewById(R.id.login_button);
		
	}
	
    private void bindListenersToViews() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     loginToApp();
            }
    });
	}

    private void loginToApp() {
        EditText usernameField = (EditText) findViewById(R.id.username_field);
        EditText passwordField = (EditText) findViewById(R.id.password_field);
        EditText emailField = (EditText) findViewById(R.id.email_field);
        /*
        if(areFieldsEmpty(usernameField, passwordField, emailField)) {
                AlertDialogs.showEmptyFieldsAlertDialog(this);
                return;
        }
        
        if(invalidEmail(emailField)) {
            AlertDialogs.showBadEmailAlertDialog(this);
            return;
        }
               
        if(shortPassword(passwordField)) {
            AlertDialogs.showBadPasswordAlertDialog(this,MinPassLen);
            return;
        }
        */
        getDeviceID();
        
    }

	private boolean areFieldsEmpty(EditText... fields) {
        for(int i = 0; i < fields.length; i++) {
                if(fields[i].getText().toString().matches("")) {
                	    Log.d(APP_TAG,"Empty Field");
                        return true;
                }
        }
        return false;
   }
     
    private boolean invalidEmail(EditText email) {
   	    Pattern pattern;
   	    Matcher matcher;
    	String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    	pattern = Pattern.compile(EMAIL_PATTERN);
    	matcher = pattern.matcher(email.getText().toString());
        if(matcher.matches()){
            Log.d(APP_TAG,"Valid Email" + matcher);
        	return false;
        } 
        Log.d(APP_TAG,"Invalid Email" + matcher);
        return true;
    }
    
    private boolean shortPassword(EditText passwordField) {

        if(passwordField.getText().toString().length() > (MinPassLen-1)){
            Log.d(APP_TAG,"Valid Password");
        	return false;
        } 
        Log.d(APP_TAG,"Invalid Password");
        return true;
    }
    
	private void getDeviceID() {
		spAndroidID = sharedPrefs.getString(DEVICE_ID, "");
		
		if(spAndroidID.length() == 0) {
			// Phone has not been used before 
			androidID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
			Log.d(APP_TAG,"New Shared Prefs DeviceID " + androidID);
			
			// ask additional security questions as it's a new phone??
			
			// Save AndroidID to Shared Preferences
			SharedPreferences.Editor editor = sharedPrefs.edit();
			editor.putString(DEVICE_ID, androidID);
			editor.commit();
			
		} else {
			// Phone has a saved deviceID
			if (spAndroidID.equals(androidID)){
				// do nothing 
				Log.d(APP_TAG,"Shared Prefs DeviceID " + spAndroidID);
			} else {
				// something wrong ask additional security questions
			}
		}
			
	}
	
	

}