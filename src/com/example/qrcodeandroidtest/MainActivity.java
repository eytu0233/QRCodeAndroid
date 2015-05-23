package com.example.qrcodeandroidtest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// 掃描一維條碼
	public void scanBar(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE"); // 一維條碼模式
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {

		}
	}

	// 掃描QRcode
	public void scanQR(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // QR code模式
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {

		}
	}

	// 傳回結果
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				// get the extras that are returned from the intent
				String contents = intent.getStringExtra("SCAN_RESULT"); // 掃描結果
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");// 掃描格式
				Toast toast = Toast.makeText(this, "內容:" + contents + " 格式:"
						+ format, Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}
}
