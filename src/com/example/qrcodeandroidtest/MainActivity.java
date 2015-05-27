package com.example.qrcodeandroidtest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	static final Pattern PORT_PATTERN = Pattern.compile("\\d{1,}");
	static final Pattern IP_PATTERN = Pattern
			.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	private static final int UP = 32;
	private static final int LEFT = 33;
	private static final int RIGHT = 34;
	private static final int DOWN = 35;

	static Intent scanIntent = new Intent(ACTION_SCAN);

	private ImageButton btnUp, btnLeft, btnRight, btnDown;

	private DataOutputStream dos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnUp = (ImageButton) findViewById(R.id.imageButtonUp);
		btnLeft = (ImageButton) findViewById(R.id.imageButtonleft);
		btnRight = (ImageButton) findViewById(R.id.imageButtonRight);
		btnDown = (ImageButton) findViewById(R.id.imageButtonDown);

		scanIntent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // QR code模式
		startActivityForResult(scanIntent, 0);
	}

	public void cmdUp(View v) {
		Log.d("debug", "up");
		if (dos != null) {
			SendMsgTask sendMsgTask = new SendMsgTask(dos, UP, this);
			sendMsgTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	public void cmdLeft(View v) {
		Log.d("debug", "left");
		if (dos != null) {
			SendMsgTask sendMsgTask = new SendMsgTask(dos, LEFT, this);
			sendMsgTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	public void cmdRight(View v) {
		Log.d("debug", "right");
		if (dos != null) {
			SendMsgTask sendMsgTask = new SendMsgTask(dos, RIGHT, this);
			sendMsgTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	public void cmdDown(View v) {
		Log.d("debug", "down");
		if (dos != null) {
			SendMsgTask sendMsgTask = new SendMsgTask(dos, DOWN, this);
			sendMsgTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	// 傳回結果
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				// get the extras that are returned from the intent
				String contents = intent.getStringExtra("SCAN_RESULT"); // 掃描結果
				String[] tips = contents.split(":");
				if (tips.length != 2 || !IP_PATTERN.matcher(tips[0]).matches()
						|| !PORT_PATTERN.matcher(tips[1]).matches()) {
					Toast.makeText(
							this,
							"QRCode資料格式不符，請掃描SnakeGameServer上的QRCode : "
									+ contents, Toast.LENGTH_LONG).show();
					startActivityForResult(scanIntent, 0);
				} else {
					String ip = tips[0], port = tips[1];
					Toast.makeText(this, "IP:" + ip + " Port:" + port,
							Toast.LENGTH_LONG).show();
					SocketCreateTask socketCreateTask = new SocketCreateTask(
							ip, Integer.parseInt(port), this);
					socketCreateTask.execute();
					try {
						Socket client = socketCreateTask.get();
						while(client == null);
						dos = new DataOutputStream(client.getOutputStream());
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				Toast.makeText(this, "掃描失敗!請重新掃描QRCode!", Toast.LENGTH_LONG)
						.show();
				startActivityForResult(scanIntent, 0);
			}
		}
	}

}
