package com.riis.login;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	Button loginButton;
	EditText usernameField, passwordField, emailField;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        usernameField = (EditText) findViewById	(R.id.username_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        emailField = (EditText) findViewById(R.id.email_field);
	    loginButton = (Button) findViewById(R.id.login_button);
	    
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	onSubmit(v);
            }
        });	    
        
    }
    
    public void onSubmit(View v){
    	
    	// implicit
    	// Intent intent = new Intent();  
    	
    	// explicit
    	Intent intent = new Intent(this, IntentReceiverActivity.class);  // line 41

    	Bundle bundle = new Bundle();
    	
    	bundle.putString("Username", usernameField.getText().toString());
    	bundle.putString("Password", passwordField.getText().toString());
    	bundle.putString("Email", emailField.getText().toString());
    	
    	intent.setAction("com.riis.login.IntentReceiverActivity");
    	intent.addCategory(Intent.CATEGORY_DEFAULT);
    	intent.putExtras(bundle);
    	
    	startActivity(intent);
    	
    	
    }
    
}
