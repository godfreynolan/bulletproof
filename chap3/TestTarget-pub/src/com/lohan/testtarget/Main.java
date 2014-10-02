/*
 * TODO:
 */
package com.lohan.testtarget;

import java.util.HashMap;

import com.lohan.testtarget.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity {
	public static Context MyContext = null;
	public static Activity MyActivity = null;
	public static PackageManager MyPM = null;
	public static String MyPackageName = null;
	public static TelephonyManager MyTM = null;
	public static WifiManager MyWifi = null;
	public static BluetoothAdapter MyBTA = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		MyContext = this;
		MyActivity = this.getParent();
		MyPM = MyContext.getPackageManager();
		MyPackageName = MyContext.getPackageName();
		MyTM = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		MyWifi = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		MyBTA = BluetoothAdapter.getDefaultAdapter();
		
        final Button btn = (Button) findViewById(R.id.btnRefresh);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refresh();
            }
        });
        
		refresh();
	}

	private void refresh() {
		AsyncTask<Void, Void, HashMap<Integer, String>> giTask =
			new GatherInfoTask(Main.this).execute();
		HashMap<Integer, String> infoResults = null;
		try {
			infoResults = giTask.get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ( infoResults != null ) {
			for ( Integer viewId : infoResults.keySet() ) {
				TextView tv = (TextView)findViewById(viewId);
				tv.setText(infoResults.get(viewId));
			}
		}

		
		AsyncTask<Void, Void, HashMap<Integer, TestResult>> ptTask =
			new PerformTestsTask(Main.this).execute();
		HashMap<Integer, TestResult> testResults = null;
		try {
			testResults = ptTask.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if ( testResults != null ) {
			for ( Integer viewId : testResults.keySet() )
				setResult((TextView)findViewById(viewId), testResults.get(viewId));
		}
	}


	private void setResult(TextView tv, TestResult tr) {
		switch ( tr ) {
		case PASSED:
			tv.setText("passed :D");
			tv.setTextColor(Color.GREEN);
			break;
		case FAILED:
			tv.setText("failed :(");
			tv.setTextColor(Color.RED);
			break;
		case ERROR:
			tv.setText("error!");
			tv.setTextColor(Color.GRAY);
			break;
		case NOKEY:
			tv.setText("no key!");
			tv.setTextColor(Color.YELLOW);
			break;
		}
	}
}