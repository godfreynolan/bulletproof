package com.riis.restfulwebservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import org.json.JSONObject;

import com.riis.restfulwebservice.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RestFulWebservice extends Activity {
	
	final String URL1 = "api.wunderground.com/api/";
	final String URL2 = "/conditions/q/MI/Troy.json";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_ful_webservice);  
        final Button HTTP = (Button) findViewById(R.id.HTTP);
        HTTP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		        new HTTPCall().execute("http://" + URL1 + ((EditText) findViewById(R.id.api)).getText().toString() + URL2);
			}
        });    
        final Button HTTPS = (Button) findViewById(R.id.HTTPS);
        HTTPS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		        new HTTPCall().execute("https://" + URL1 + ((EditText) findViewById(R.id.api)).getText().toString() + URL2);
			}
        });   
    }
    
    private class HTTPCall  extends AsyncTask<String, Void, Void> {
    	
        private ProgressDialog dialog = new ProgressDialog(RestFulWebservice.this);
        String responseData = "";
        
        protected void onPreExecute() {
            dialog.setMessage("Please wait..");
            dialog.show();
            ((TextView) findViewById(R.id.output)).setText("");
        }
 
        protected Void doInBackground(String... urls) {
    		try {
    			HttpResponse response = (new DefaultHttpClient()).execute((new HttpGet(urls[0])), (new BasicHttpContext()));	
    			StringBuilder builder = new StringBuilder();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    			String line;
    			while((line = reader.readLine()) != null) {
    				builder.append(line);
    			}
    			responseData = builder.toString();
    			response.getEntity().consumeContent();
    		} catch (Exception ex) {
    			responseData = ex.getMessage();
    			ex.printStackTrace();
    		}
    		return null;
        }
         
        protected void onPostExecute(Void unused) {
        	try {
        		JSONObject observation = new JSONObject(responseData).getJSONObject("current_observation");
        		String result = "";
				result += "Location: " + observation.getJSONObject("display_location").getString("full") + "\n";
				result += "Weather: " + observation.getString("weather") + "\n";
				result += "Temperature: " + observation.getString("temperature_string");
	            ((TextView) findViewById(R.id.output)).setText(result);
			} catch (Exception ex) {
	            ((TextView) findViewById(R.id.output)).setText(responseData);
				ex.printStackTrace();
			}
            dialog.dismiss();
        }
    }
}
