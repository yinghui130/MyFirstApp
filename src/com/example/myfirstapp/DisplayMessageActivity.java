package com.example.myfirstapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.yzforex.tools.*;

public class DisplayMessageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_display_message);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent =this.getIntent();
		String message= intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		TextView textView= new TextView(this);
		textView.setTextSize(10);
		textView.setText(message);
		//WebHelper helper =new WebHelper("http://tool.yzforex.com/ajax_hbdhq.php?q=1&from=USD&to=CNY");
		 WebHelper helper =new WebHelper("http://www.ip168.com/chxip/doConveRate.do?fromcurrency=CNY&tocurrency=CNY&keyword=12312");
		helper.GetResult();
		this.setContentView(textView);
	}
 
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.liuyinghui
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
