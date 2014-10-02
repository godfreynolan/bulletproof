package com.riis.readlogs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity {
	public static final String APP_TAG = "com.riis.readlogs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			  Process process = Runtime.getRuntime().exec("logcat -d com.riis.logfiles:D *:S");
			  BufferedReader bufferedReader = new BufferedReader(
			  new InputStreamReader(process.getInputStream()));

			  StringBuilder log=new StringBuilder();
			  String line = "";
			  while ((line = bufferedReader.readLine()) != null) {
			    log.append(line);
			  }
			  Log.d(APP_TAG, log.toString());

		} 
		catch (IOException e) 
		{
			// todo 
		}
		
	}

}
