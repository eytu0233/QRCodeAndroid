package com.example.qrcodeandroidtest;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SocketCreateTask extends AsyncTask<Void, String, Socket> {
	
	String address;
	int port;
	Activity a;
	
	public SocketCreateTask(String address, int port, Activity a) {
		this.address = address;
		this.port = port;
		this.a = a;
	}

	@Override
	protected Socket doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Socket client = null;

		try {
			// client = new Socket());
			Log.d("debug", "Try to connect'");
			client = new Socket(address, port);
			Log.d("debug", "Connection sucess'");
			publishProgress("Connection sucess");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("debug", "NumberFormatException'");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("debug", "UnknownHostException'");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			publishProgress("No Network");
			return null;
		}
		return client;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		Toast.makeText(a, values[0],
				Toast.LENGTH_SHORT).show();
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Socket result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	
}
