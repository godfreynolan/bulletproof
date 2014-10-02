package com.lohan.testtarget;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.net.wifi.WifiInfo;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

public class GatherInfoTask extends AsyncTask<Void, Void, HashMap<Integer, String>> {
	public HashMap<Integer, String> myResults = new HashMap<Integer, String>();
	
	Main myApp;
	ProgressDialog pd;
	
	public GatherInfoTask(Main app) {
		myApp = app;
	}
	
	@Override
	protected HashMap<Integer, String> doInBackground(Void... params) {
		gatherInfo();
		return myResults;
	}

	@Override
	protected void onPreExecute() {
		pd = ProgressDialog.show(this.myApp
				, "Gathering Info", "Gathering information ...", true, false, null);
	}
	
	@Override
	protected void onPostExecute(HashMap<Integer, String> myResults) {
        pd.dismiss();
    }
    
	private void gatherInfo() {
		String val = Main.MyTM.getDeviceId();
		myResults.put(R.id.txtDeviceID, val);
		
		try {
			Method SomeMethod = TelephonyManager.class.getMethod("getDeviceId");
			val = (String) SomeMethod.invoke(Main.MyTM);
		} catch (Exception e) {
			Console.log("gatherInfo() exception getting reflected id: " + e);
			e.printStackTrace();
			val = "error!";
		}
		myResults.put(R.id.txtDeviceIDReflection, val);

		val = Main.MyTM.getSimSerialNumber();
		myResults.put(R.id.txtSimSerial, val);
		
		WifiInfo wifo = (WifiInfo)Main.MyWifi.getConnectionInfo();
		val = wifo.getMacAddress();
		myResults.put(R.id.txtWirelessMAC, (val == null ? "n/a" : val));

		val = (Main.MyBTA == null ? "n/a" : Main.MyBTA.getAddress());
		myResults.put(R.id.txtBluetoothMAC, val);

		File apk = new File(Main.MyContext.getPackageCodePath());
		val = apk.length() + "";
		myResults.put(R.id.txtApkLength, val);
		
		val = apk.lastModified() + "";
		myResults.put(R.id.txtApkLastMod, val);
		
		val = "" + (System.currentTimeMillis() / 1000);
		myResults.put(R.id.txtTime, val);
	}
}
