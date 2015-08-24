package com.example.myfirstapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.*;

import com.yzforex.object.ForexObject;
import com.yzforex.object.RetJsonHelper;
import com.yzforex.tools.*;

public class DisplayMessageActivity extends Activity {
	TextView textView = null;
	Context context = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = this.getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		textView = new TextView(this);
		textView.setTextSize(10);
		new Thread(networkTask).start();
		textView.setText(message);
		// WebHelper helper =new
		// WebHelper("http://tool.yzforex.com/ajax_hbdhq.php?q=1&from=USD&to=CNY");
		this.setContentView(textView);
		context = this.getApplicationContext();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String val = data.getString("value");
			ForexObject forexObject = RetJsonHelper.ToObject(val);
			try {
				JSONObject object = new JSONObject(val);
				object.get("");
			} catch (JSONException e) {
				// TODO: handle exception
			}

			Log.i("mylog", "������Ϊ-->" + val);
			// TODO
			// UI����ĸ��µ���ز���
			if (val != null && !val.isEmpty())
				textView.setText(val);
		}
	};
	/**
	 * ���������ص����߳�
	 */
	Runnable networkTask = new Runnable() {

		@Override
		public void run() {
			// TODO
			// ��������� http request.����������ز���
			Message msg = new Message();
			Bundle data = new Bundle();
			WebHelper helper = new WebHelper(
					"http://ip168.com/chxip/doConveRate.do?fromcurrency=CNY&tocurrency=CNY&keyword=12312");
			// WebHelper helper =new WebHelper("http://www.baidu.com");
			// helper.NetType(context);
			String valString = helper.GetResult();
			data.putString("value", valString);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is
	 * available.liuyinghui
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
