package com.riis.validatelogin;

import android.app.AlertDialog;
import android.content.Context;

public class AlertDialogs {
	
    public static void showEmptyFieldsAlertDialog(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("Please fill in every field.");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.create().show();
    }

    public static void showBadEmailAlertDialog(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("Please enter a valid email.");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.create().show();
    }
    
    public static void showBadPasswordAlertDialog(Context context, int passLen) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("Password needs to be at least " + passLen + " characters.");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.create().show();
    }


}