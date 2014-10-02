package com.example.registeredemails;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class RegisteredEmailAccounts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.registered_email_account);
		final TextView accountsData = (TextView) findViewById(R.id.accounts);
		
		String possibleEmail="";
		
		   try{
			       possibleEmail += "************* Get Registered Gmail Account *************\n\n";
			       Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
			       
			       for (Account account : accounts) {
			         
			    	 possibleEmail += " --> "+account.name+" : "+account.type+" , \n";
			         possibleEmail += " \n\n";
			         
			       }
			  }
		      catch(Exception e)
		      {
		    	   Log.i("Exception", "Exception:"+e) ; 
		      }
		      
		      
		      try{
		    	   possibleEmail += "**************** Get All Registered Accounts *****************\n\n";
		    	  
			       Account[] accounts = AccountManager.get(this).getAccounts();
			       for (Account account : accounts) {
			         
			    	  possibleEmail += " --> "+account.name+" : "+account.type+" , \n";
			          possibleEmail += " \n";
			         
			       }
			  }
		      catch(Exception e)
		      {
		    	   Log.i("Exception", "Exception:"+e) ; 
		      }
		   
		   // Show on screen    
		   accountsData.setText(possibleEmail);
	       
	       Log.i("Exception", "mails:"+possibleEmail) ;
	}

	
}
