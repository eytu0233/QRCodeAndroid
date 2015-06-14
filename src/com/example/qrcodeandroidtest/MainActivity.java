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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

	private ImageView direction;
	private AbsoluteLayout layout;

	private DataOutputStream dos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		direction = (ImageView) findViewById(R.id.imageDirection);
		layout = (AbsoluteLayout) findViewById(R.id.touchPanel);
		layout.setOnTouchListener(new OnTouchListener() {

			float downXValue = 0, downYValue = 0;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN: {
					// store the X value when the user's finger was pressed down
					downXValue = event.getX();
					downYValue = event.getY();
					Log.v("debug", "= " + downYValue);
					break;
				}

				case MotionEvent.ACTION_UP: {
					// Get the X value when the user released his/her finger
					float currentX = event.getX();
					float currentY = event.getY();
					// check if horizontal or vertical movement was bigger

					if (Math.abs(downXValue - currentX) > Math.abs(downYValue
							- currentY)) {
						Log.v("debug", "x");
						// going backwards: pushing stuff to the right
						if (downXValue < currentX) {
							Log.v("debug", "right");
							direction.setImageDrawable(getResources().getDrawable( R.drawable.direction_right_normal ));
						}

						// going forwards: pushing stuff to the left
						if (downXValue > currentX) {
							Log.v("debug", "left");
							direction.setImageDrawable(getResources().getDrawable( R.drawable.direction_left_normal ));

						}

					} else {
						Log.v("debug", "y ");

						if (downYValue < currentY) {
							Log.v("debug", "down");
							direction.setImageDrawable(getResources().getDrawable( R.drawable.direction_down_normal ));

						}
						if (downYValue > currentY) {
							Log.v("debug", "up");
							direction.setImageDrawable(getResources().getDrawable( R.drawable.direction_up_normal ));

						}
					}
					break;
				}

				}
				return true;
			}

		});

		// scanIntent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // QR code模式
		// startActivityForResult(scanIntent, 0);
	}

	// 傳回結果
	/*
	 * public void onActivityResult(int requestCode, int resultCode, Intent
	 * intent) { if (requestCode == 0) { if (resultCode == RESULT_OK) { // get
	 * the extras that are returned from the intent String contents =
	 * intent.getStringExtra("SCAN_RESULT"); // 掃描結果 String[] tips =
	 * contents.split(":"); if (tips.length != 2 ||
	 * !IP_PATTERN.matcher(tips[0]).matches() ||
	 * !PORT_PATTERN.matcher(tips[1]).matches()) { Toast.makeText( this,
	 * "QRCode資料格式不符，請掃描SnakeGameServer上的QRCode : " + contents,
	 * Toast.LENGTH_LONG).show(); startActivityForResult(scanIntent, 0); } else
	 * { String ip = tips[0], port = tips[1]; Toast.makeText(this, "IP:" + ip +
	 * " Port:" + port, Toast.LENGTH_LONG).show(); SocketCreateTask
	 * socketCreateTask = new SocketCreateTask( ip, Integer.parseInt(port),
	 * this); socketCreateTask.execute(); try { Socket client =
	 * socketCreateTask.get(); while(client == null); dos = new
	 * DataOutputStream(client.getOutputStream());
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (ExecutionException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } } else { Toast.makeText(this,
	 * "掃描失敗!請重新掃描QRCode!", Toast.LENGTH_LONG) .show();
	 * startActivityForResult(scanIntent, 0); } } }
	 */

}
