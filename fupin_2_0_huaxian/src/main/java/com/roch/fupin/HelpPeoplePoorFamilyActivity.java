/**
 * 
 */
package com.roch.fupin;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin_2_0.R;
import com.roch.fupin.adapter.HelpPeoplePoorFamilyListAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.HelpPeople;
import com.roch.fupin.entity.PoorFamilyBase;
import com.roch.fupin.entity.PoorFamilyListResult;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 帮扶责任人负责的贫困户
 * 
 * @author ZhaoDongShao
 *
 * 2016年5月25日 
 *
 */
@ContentView(R.layout.activity_helppeople_familypeople)
public class HelpPeoplePoorFamilyActivity extends BaseActivity{

	@ViewInject(R.id.lv_poor)
	ListView listview;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;

	HelpPeoplePoorFamilyListAdapter adapter;

	static String uid = "id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initToolbar();
		MyApplication.getInstance().addActivity(this);

		initData();

	}

	/**
	 *
	 *
	 * 2016年8月5日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initToolbar() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:  
			
			MyApplication.getInstance().finishActivity(mActivity);
			
			break;

		default:
			break;
		}

		return true;
	}

	
	/**
	 *
	 *
	 * 2016年5月25日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initData() {

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra(Common.INTENT_KEY);
		HelpPeople helpPeople = (HelpPeople) bundle.getSerializable(Common.BUNDEL_KEY);
		if (helpPeople != null) {

			tv_title.setText(helpPeople.getName()+ "——帮扶贫困户列表" );

		}

		String uid = helpPeople.getId();
		RequestParams rp = new RequestParams();
		rp.addBodyParameter(HelpPeoplePoorFamilyActivity.uid, uid);
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.HELP_PEOPLE_POOR_FAMILY_LIST, rp,
				new MyRequestCallBack(this, MyConstans.FIRST));

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				PoorFamilyBase poorHouse = (PoorFamilyBase)parent.getItemAtPosition(position);
				if (poorHouse != null) {
					Intent intent = new Intent(mContext, PoorHouseDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable(Common.BUNDEL_KEY, poorHouse);
					intent.putExtra(Common.INTENT_KEY, bundle);
					startActivity(intent);
				}
			}
		});

	}


	@Override
	public void onSuccessResult(String str, int flag) {
		// TODO Auto-generated method stub
		super.onSuccessResult(str, flag);

		PoorFamilyListResult poorFamilyListResult = null;

		switch (flag) {
		case MyConstans.FIRST:

			poorFamilyListResult = PoorFamilyListResult.parseToT(str, PoorFamilyListResult.class);
			if (!StringUtil.isEmpty(poorFamilyListResult)) {
				if (poorFamilyListResult.getSuccess()) {
					List<PoorFamilyBase> poorFamilyBases = poorFamilyListResult.getJsondata();

					if (!StringUtil.isEmpty(poorFamilyBases) && poorFamilyBases.size() > 0) {
						adapter = new HelpPeoplePoorFamilyListAdapter(mContext, poorFamilyBases);
						listview.setAdapter(adapter);
					}

				}
				else {
					ShowNoticDialog();
				}
			}
			break;
		default:
			break;
		}
	}


	@Override
	public void onFaileResult(String str, int flag) {
		// TODO Auto-generated method stub
		super.onFaileResult(str, flag);
	}

}
