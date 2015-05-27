package com.example.qrcodeandroidtest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class SendMsgTask extends AsyncTask<Void, Void, Void> {
	
	static final Object lock = new Object();
	
	DataOutputStream dos;
	int cmd;
	Activity a;
	Exception e;

	SendMsgTask(DataOutputStream dos, int cmd, Activity a) {
		this.dos = dos;
		this.cmd = cmd;
		this.a = a;
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		try {
			
			synchronized(lock){
				Log.d("debug", String.valueOf(cmd));
				dos.writeByte(cmd);
				Log.d("debug", "Send end : " + cmd);
			}			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			Log.d("debug", "SocketException");
//			e.printStackTrace();
			this.e = e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("debug", "IOException");
//			e.printStackTrace();
			this.e = e;
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// StringWriter errors = new StringWriter();
		// e.printStackTrace(new PrintWriter(errors));
		// Toast.makeText(v.getContext(), (CharSequence) errors,
		// Toast.LENGTH_SHORT).show();
		super.onPostExecute(result);
	}
	
}
