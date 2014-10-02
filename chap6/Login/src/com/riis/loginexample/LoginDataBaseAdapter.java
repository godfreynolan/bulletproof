package com.riis.loginexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LoginDataBaseAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		// TODO: Create public field for each column in your table.
		// SQL Statement to create a new database.
		static final String DATABASE_CREATE = "create table "+"LOGIN"+
		                             "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";
		// Variable to hold the database instance
		public  SQLiteDatabase db;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		public  LoginDataBaseAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public  LoginDataBaseAdapter open() throws SQLException 
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}
		public void close() 
		{
			db.close();
		}

		public  SQLiteDatabase getDatabaseInstance()
		{
			return db;
		}

		public void insertEntry(String userName,String password)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("USERNAME", userName);
			newValues.put("PASSWORD",password);
			
			// Insert the row into your table
			db.insert("LOGIN", null, newValues);
			///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
		}
		public int deleteEntry(String UserName)
		{
			//String id=String.valueOf(ID);
		    String where="USERNAME=?";
		    int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
	       // Toast.makeText(context, "Number of Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}	
		
		public boolean checkLogin(String param1, String param2)
		{
			boolean bool = false;
			Cursor cursor = db.rawQuery("select * from login where USERNAME = '" + param1 
					+ "' and PASSWORD = '" + param2 + "';", null);
			
			Log.d("com.riis.loginexample","select * from login where USERNAME = '" + param1 + "' and PASSWORD = '" + param2 + "';");
			
			if (cursor != null) {
				if (cursor.moveToFirst())
					bool = true;
				cursor.close();
			}
			return bool;
		}
		
		public boolean checkSecureLogin(String param1, String param2)
		{
			boolean bool = false;
			Cursor cursor = db.rawQuery("select * from login where USERNAME = ? and PASSWORD = ?",
					new String[]{param1, param2});
			
			if (cursor != null) {
				if (cursor.moveToFirst())
					bool = true;
				cursor.close();
			}
			return bool;
		}
		
		public void  updateEntry(String userName,String password)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("USERNAME", userName);
			updatedValues.put("PASSWORD",password);
			
	        String where="USERNAME = ?";
		    db.update("LOGIN",updatedValues, where, new String[]{userName});			   
		}		
}

