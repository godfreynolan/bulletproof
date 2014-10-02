package com.riis.xss;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MainActivity extends Activity {

    @SuppressLint("SetJavaScriptEnabled") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        WebView myWebView = (WebView) findViewById(R.id.webview);
        // myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
    	
        String customHtml = "<html><body>";
        customHtml += "<form><input type=\"text\" name=\"xss\">";
        customHtml += "<input type=\"submit\"></form>";
        customHtml += "</body></html>";
 	    myWebView.loadData(customHtml, "text/html", "UTF-8");
 	    
 	    Log.d("com.riis.xss","customHTML is " + customHtml);
        
    }
    
}