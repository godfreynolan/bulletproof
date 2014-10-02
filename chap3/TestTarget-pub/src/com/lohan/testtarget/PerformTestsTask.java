/*
 * -- IMPORTANT --
 * Please keep in mind that the code is necessarily repetitive.
 * We are using this to test AntiLVL's capacity to find instances of certain
 * codes and modify them accordingly. Code reuse optimizations would hinder this.
 */

package com.lohan.testtarget;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Debug;

public class PerformTestsTask extends AsyncTask<Void, Void, HashMap<Integer, TestResult>> {
	public HashMap<Integer, TestResult> myResults = new HashMap<Integer, TestResult>();
	
	Main myApp;
	ProgressDialog pd;

	// Need to update these every release
	private static final long ORIG_APK_LENGTH = 33012;
	private static final long ORIG_LAST_MODIFIED = 1319468043000L;
	
	// This stays the same
	private static final int SIGNATURE_HASH = 1680236448;

	PerformTestsTask(Main app) {
		myApp = app;
	}
	
	@Override
	protected void onPreExecute() {
		pd = ProgressDialog.show(this.myApp
				, "Testing", "Performing tests ...", true, false, null);	
	}
	
	@Override
	protected HashMap<Integer, TestResult> doInBackground(Void... params) {
		performChecks();
		return myResults;
	}

    @Override
	protected void onPostExecute(HashMap<Integer, TestResult> result) {
        pd.dismiss();
    }
    
	private void performChecks() {
		myResults.put(R.id.txtCheckDebuggerConnected_Result, checkDebuggerConnected());
		myResults.put(R.id.txtCheckDebuggerConnectedReflection_Result, checkDebuggerConnectedReflection());
		myResults.put(R.id.txtCheckFileLength_Result, checkFileLength());		
		myResults.put(R.id.txtCheckFileLengthReflection_Result, checkFileLengthReflection());		
		myResults.put(R.id.txtCheckLastModified_Result, checkLastModified());		
		myResults.put(R.id.txtCheckLastModifiedReflection_Result, checkLastModifiedReflection());
		myResults.put(R.id.txtCheckInstallerPackageName_Result, checkInstallerPackageName());		
		myResults.put(R.id.txtCheckInstallerPackageNameReflection_Result, checkInstallerPackageNameReflection());		
		myResults.put(R.id.txtCheckPMInfoSignature_Result, checkPMInfoSignature());		
		myResults.put(R.id.txtCheckPMInfoSignatureReflection_Result, checkPMInfoSignatureReflection());
		myResults.put(R.id.txtCheckPMCheckSignatures_Result, checkPMCheckSignatures());		
		myResults.put(R.id.txtCheckPMCheckSignaturesReflection_Result, checkPMCheckSignaturesReflection());		
		myResults.put(R.id.txtCheckTrivialLC1_Result, checkTrivialLicenseCheck1());
		myResults.put(R.id.txtCheckTrivialLC2_Result, checkTrivialLicenseCheck2());
		myResults.put(R.id.txtCheckDebuggable_Result, checkDebuggable());
		myResults.put(R.id.txtCheckDebuggableReflection_Result, checkDebuggableReflection());
	}
	
	private TestResult checkDebuggerConnected() {
		boolean debugConn = Debug.isDebuggerConnected();
		
		Console.log("checkDebuggerConnected() = " + debugConn);		

		if ( debugConn )
			return TestResult.FAILED;
		else
			return TestResult.PASSED;
	}
	
	private TestResult checkDebuggerConnectedReflection() {
		boolean debugConn;
		Method m = null;
		try {
			m = Debug.class.getMethod("isDebuggerConnected");
			debugConn = (Boolean) m.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkDebuggerConnectedReflection() = " + debugConn);		
		
		if ( debugConn )
			return TestResult.FAILED;
		else
			return TestResult.PASSED;
	}
	
	private TestResult checkFileLength() {
		String path = Main.MyContext.getPackageCodePath();
		File f = new File(path);
		Long val = null;
		
		try {
			val = f.length();
			Console.log("package code path: " + path + " size: " + val);
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}

		Console.log("checkFileSize() = " + val + "  compare:" + (ORIG_APK_LENGTH + 20));
		
		if ( val != null && val <= ORIG_APK_LENGTH + 30 )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkFileLengthReflection() {
		String path = Main.MyContext.getPackageCodePath();
		File f = new File(path);
		Long val = null;
		Method m = null;
		try {
			m = File.class.getMethod("length", new Class[] {});
			val = (Long) m.invoke(f);
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkFileSizeReflection() = " + val + "  compare:" + (ORIG_APK_LENGTH + 20));		
		
		if ( val != null && val <= ORIG_APK_LENGTH + 20 )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;			
	}

	private TestResult checkLastModified() {
		String path = Main.MyContext.getPackageCodePath();
		File f = new File(path);
		Long val = null;
		
		try {
			val = f.lastModified();
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkLastModified() = " + val + " compare:" + (ORIG_LAST_MODIFIED + 120000));
		
		// two minute grace period since i wont actually know modified time
		// until it's last modified and i'll have to update it again afterwards.
		if ( val != null && val <= ORIG_LAST_MODIFIED + 120000 )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkLastModifiedReflection() {
		String path = Main.MyContext.getPackageCodePath();
		File f = new File(path);
		Long val = null;
		Method m = null;
		try {
			m = File.class.getMethod("lastModified", new Class[] {});
			val = (Long) m.invoke(f);
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkLastModifiedReflection() = " + val + " compare:" + (ORIG_LAST_MODIFIED + 120000));
		
		if ( val != null && val <= ORIG_LAST_MODIFIED + 120000 )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;			
	}
	
	private TestResult checkInstallerPackageName() {
		String ipName = null;
		try {
			ipName = Main.MyPM.getInstallerPackageName(Main.MyPackageName);
	
			Console.log("checkInstallerPackageName() = " + ipName);
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}

		// com.google.android.feedback
		if (ipName != null && ipName.contains("oid.fe"))
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkInstallerPackageNameReflection() {
		String ipName = "";
		try {
			Method m = PackageManager.class.getMethod(
					"getInstallerPackageName", new Class[] { String.class });
			ipName = (String) m.invoke(Main.MyPM, new Object[] { Main.MyPackageName });
			Console.log("checkInstallerPackageNameReflection() = " + ipName);

		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}

		// google.android.feedback
		if (ipName != null && ipName.contains("oid.fe"))
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkPMInfoSignature() {
		PackageInfo pi = null;
		try {
			pi = Main.MyPM.getPackageInfo(Main.MyPackageName, PackageManager.GET_SIGNATURES);
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}

		int sigHash = pi.signatures[0].hashCode();
		Console.log("checkPMInfoSignature() = " + sigHash + " compare:" + SIGNATURE_HASH);

		if ( sigHash == SIGNATURE_HASH )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkPMInfoSignatureReflection() {
		int sigHash;
		try {
			Method m = PackageManager.class.getMethod("getPackageInfo",
					new Class[] { String.class, int.class });
			PackageInfo pi = (PackageInfo) m.invoke(Main.MyPM, new Object[] {
					Main.MyPackageName, PackageManager.GET_SIGNATURES });
			sigHash = pi.signatures[0].hashCode();
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkPMInfoSignatureReflection() = " + sigHash + " compare:" + SIGNATURE_HASH);
		
		if ( sigHash == SIGNATURE_HASH )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkPMCheckSignatures() {
		int res = PackageManager.SIGNATURE_NO_MATCH;
		try {
			res = Main.MyPM.checkSignatures(Main.MyPackageName, "com.android.phone");
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkPMCheckSignatures() = " + res);
		
		if ( res == PackageManager.SIGNATURE_MATCH )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkPMCheckSignaturesReflection() {
		Integer res = PackageManager.SIGNATURE_NO_MATCH;
		try {
			Method m = PackageManager.class.getMethod("checkSignatures",
					new Class[] { String.class, String.class });
			// this should give a failure
			res = (Integer) m.invoke(Main.MyPM, new Object[] {
					Main.MyPackageName, "com.android.vending" });
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkPMCheckSignaturesReflection() = " + res);
		
		if ( res == PackageManager.SIGNATURE_MATCH )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkTrivialLicenseCheck1() {
		if ( TrivialLicenseCheck.isValidLicense() && TrivialLicenseCheck.isAppLicensed()
				&& TrivialLicenseCheck.publicHasUserDonated() )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}

	private TestResult checkTrivialLicenseCheck2() {
		if ( TrivialLicenseCheck.isAuthed() )
			return TestResult.PASSED;
		else
			return TestResult.FAILED;
	}
	
	private TestResult checkDebuggable() {
		ApplicationInfo ai = Main.MyContext.getApplicationInfo();
		boolean isDebuggable = (0 != (ai.flags &=
			ApplicationInfo.FLAG_DEBUGGABLE));
		
		Console.log("checkDebuggable() flags = " + ai.flags);
		
		if ( isDebuggable )
			return TestResult.FAILED;
		else
			return TestResult.PASSED;
	}

	private TestResult checkDebuggableReflection() {
		ApplicationInfo ai = null;
		try {
			Method m = Context.class.getMethod("getApplicationInfo");
			ai = (ApplicationInfo) m.invoke(Main.MyContext);
		} catch (Exception e) {
			e.printStackTrace();
			return TestResult.ERROR;
		}
		
		Console.log("checkDebuggableReflection() flags = " + ai.flags);

		boolean isDebuggable = (0 != (ai.flags &=
			ApplicationInfo.FLAG_DEBUGGABLE));
		
		if ( isDebuggable )
			return TestResult.FAILED;
		else
			return TestResult.PASSED;
	}
}