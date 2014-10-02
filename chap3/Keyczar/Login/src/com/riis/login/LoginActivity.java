package com.riis.login;

import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




public class LoginActivity extends Activity {

	private EditText usernameField, passwordField, emailField;
	private Button loginButton;	
    private Crypter mCrypter;
    
    private static final String TAG = "RSADemo";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        initializeViews();
        bindListenersToViews();
        
    }

	private void initializeViews() {
        usernameField = (EditText) findViewById(R.id.username_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        emailField = (EditText) findViewById(R.id.email_field);
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

        
		String rsaPassword = encryptPassword (passwordField.getText().toString());
        displaySafePassword(rsaPassword);
        // displaySafePassword(passwordField.getText().toString());
    }


	private String encryptPassword(String pass) {
		
	       try {
	            mCrypter = new Crypter(new AndroidKeyczarReader(getResources(), "keys"));
	        } catch (KeyczarException e) {
	            Log.d(TAG, "Couldn't load keyczar keys", e);
	            return null;
	        }
	        
	        try {
	            final String cipherText = mCrypter.encrypt(pass);
	            return cipherText;
	            
	        } catch (KeyczarException e) {
	            Log.d(TAG, "Couldn't encrypt message", e);
	        	return null;
	        }
	}
	
	private void displaySafePassword(String safePass) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Password is " + safePass);
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.create().show();	
	}

}
