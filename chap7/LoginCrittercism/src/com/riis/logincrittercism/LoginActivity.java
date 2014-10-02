package com.riis.logincrittercism;

import com.riis.logincrittercism.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.crittercism.app.Crittercism;  // line 10


public class LoginActivity extends Activity {

	private Button loginButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        Crittercism.initialize(getApplicationContext(), "53fbdb9283fb796ab8000001");   // line 22
        
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
    }
    
    private boolean areFieldsEmpty(EditText... fields) {
        for(int i = 0; i < fields.length; i++) {
                if(fields[i].getText().toString().matches("")) {
                        return true;
                }
        }

        return false;
   }
    
}
