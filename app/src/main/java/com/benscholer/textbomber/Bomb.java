package com.benscholer.textbomber;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by benscholer on 4/17/15.
 */
public class Bomb {
	public String number;
	public String message;
	public int repeat;

	public Bomb() {
	}

	public Bomb(String number, String message, int repeat) {
		this.number = number;
		this.message = message;
		this.repeat = repeat;
	}

	public void send(Context context) {
		for (int i = 0; i < repeat; i++) {
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(number, null, message, null, null);
		}

		Toast.makeText(context, "Sending complete.", Toast.LENGTH_SHORT).show();
	}
}
