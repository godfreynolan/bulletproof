package com.riis.validatelogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.riis.validatelogin.AlertDialogs;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private Button loginButton;
	public static final String APP_TAG = "com.riis.validatelogin";
	public static final int MinPassLen = 6;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        initializeViews();
        bindListenersToVies();
        
    }

	private void initializeViews() {
	    loginButton = (Button) findViewById(R.id.login_button);
		
	}
	
    private void bindListenersToVies() {
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

}