package com.riis.logincrashlytics;

import android.app.AlertDialog;
import android.content.Context;

public class AlertDialogs {
	
    public static void showEmptyFieldsAlertDialog(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("Please fill in every field.");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.create().show();
    }
    
}
