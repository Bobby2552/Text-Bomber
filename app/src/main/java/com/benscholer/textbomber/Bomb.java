package com.benscholer.textbomber;

import android.content.Context;

/**
 * Created by benscholer on 4/17/15.
 */
public class Bomb {
	public String number;
	public String message;
	public int repeat;

	public Bomb () {}

	public Bomb (String number, String message, int repeat) {
		this.number = number;
		this.message = message;
		this.repeat = repeat;
	}

	public void send(Context context) {
		
	}
}
