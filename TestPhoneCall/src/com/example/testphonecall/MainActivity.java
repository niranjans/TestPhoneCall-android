package com.example.testphonecall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button button;
	final Context context = this;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 
		button = (Button) findViewById(R.id.buttonCall);
 
		// add PhoneStateListener
		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
			.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
 
		// add button listener
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				// Adding a new comment in this feature branch
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:0377778888"));
				startActivity(callIntent);
 
			}
 
		});
 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	//monitor phone call activities
	private class PhoneCallListener extends PhoneStateListener {
 
		private boolean isPhoneCalling = false;
 
		String LOG_TAG = "LOGGING 123";
 
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
 
			if (TelephonyManager.CALL_STATE_RINGING == state) {
				// phone ringing
				Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
			}
 
			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// active
				Log.i(LOG_TAG, "OFFHOOK");
 
				isPhoneCalling = true;
			}
 
			if (TelephonyManager.CALL_STATE_IDLE == state) {
				// run when class initial and phone call ended, 
				// need detect flag from CALL_STATE_OFFHOOK
				Log.i(LOG_TAG, "IDLE");
 
				if (isPhoneCalling) {
 
					Log.i(LOG_TAG, "restart app");
 
					// restart app
					Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(
							getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
 
					isPhoneCalling = false;
				}
 
			}
		}
	}
    
}
