package com.riis.loginexample;

import com.riis.loginexample.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity 
{
	Button btnSignIn,btnSignUp;
	LoginDataBaseAdapter loginDataBaseAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     // create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     // Get Button Reference 
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
			
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			}
		});
	}
	// Methos to handleClick Event of Sign In Button
	public void signIn(View V)
	   {
			final Dialog dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.login);
		    dialog.setTitle("Login");
	
		    // get the View References
		    final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		    final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
		    
			Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
				
			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
					String userName=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					
					Boolean loggedIn = loginDataBaseAdapter.checkLogin(userName,password);
					// Boolean loggedIn = loginDataBaseAdapter.checkSecureLogin(userName,password);
					
					if(loggedIn)
					{
						Toast.makeText(HomeActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
					else
					{
						Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			dialog.show();
	}


	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		loginDataBaseAdapter.close();
	}
}

