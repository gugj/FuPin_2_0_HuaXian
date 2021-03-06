package com.roch.fupin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin_2_0.R;

/**
 * @author ZhaoDongShao
 * 2016年5月12日
 */
@ContentView(R.layout.activity_no_internet)
public class NoInternetActivity extends MainBaseActivity{

	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initToolbar();
		MyApplication.getInstance().addActivity(this);
	}

	/**
	 * 2016年8月5日
	 * ZhaoDongShao
	 */
	private void initToolbar() {
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			MyApplication.getInstance().finishActivity(this);
			break;

		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		String title = intent.getStringExtra("name");
		if (title != null && !title.equals("")) {
			tv_title.setText(title);
		}
	}

}
