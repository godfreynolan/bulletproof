package com.lohan.testtarget;

public class TrivialLicenseCheck {
	public static final boolean isAppLicensed() { return false; }
	
	public static boolean isValidLicense() { return false; }

	public static boolean publicHasUserDonated() { return hasUserDonated(); }

	public static final boolean isAuthed() { boolean AUTHED = false; return AUTHED; }

	private static boolean hasUserDonated() { return false; }
}
