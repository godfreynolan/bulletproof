package com.riis.hellointent;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Toast.makeText(getBaseContext(), "username: " + this.getIntent().getStringExtra("Username")+
				  "\npassword: "+this.getIntent().getStringExtra("Password")+
				    "\nemail: "+this.getIntent().getStringExtra("Email"),
				  Toast.LENGTH_LONG).show();
		
	}

}
