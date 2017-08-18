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
import com.roch.fupin.adapter.NoPoorProjectCanLian_JYPX_PXRY_Adapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.ProjectCanlianTrainAppModel;
import com.roch.fupin.entity.ProjectCanlianTrainAppModelListResult;
import com.roch.fupin.entity.ProjectCanlianTrainitemAppModel;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.URLs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 就业培训，培训人员详情
 * 
 * @author ZhaoDongShao
 *
 * 2016年6月2日 
 *
 */
@ContentView(R.layout.activity_helppeople_familypeople)
public class CanLian_Jypx_Fragment_Activity extends MainBaseActivity{

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.lv_poor)
	ListView listview;

	Context mContext;
	Activity mActivity;

	NoPoorProjectCanLian_JYPX_PXRY_Adapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		this.mContext = this;
		this.mActivity = this;
		initToolbar();
		MyApplication.getInstance().addActivity(mActivity);
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
		ProjectCanlianTrainAppModel appModel = (ProjectCanlianTrainAppModel) bundle.getSerializable(Common.BUNDEL_KEY);
		if (appModel != null) {
			tv_title.setText(appModel.getTrainname());

			RequestParams rp = new RequestParams();
			rp.addBodyParameter("id", appModel.getId());
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
					URLs.NO_POOR_PROJECT_CANLIAN_JYPX_DETAIL, rp,
					new MyRequestCallBack(this, MyConstans.FIRST));

		}
	}


	/* (non-Javadoc)
	 * @see com.roch.fupin.BaseActivity#onSuccessResult(java.lang.String, int)
	 */
	@Override
	public void onSuccessResult(String str, int flag) {
		// TODO Auto-generated method stub
		super.onSuccessResult(str, flag);
		switch (flag) {
		case MyConstans.FIRST:
			ProjectCanlianTrainAppModelListResult listResult = ProjectCanlianTrainAppModelListResult.parseToT(str, ProjectCanlianTrainAppModelListResult.class);

			if (listResult.getSuccess() && listResult != null) {

				List<ProjectCanlianTrainAppModel> list = listResult.getJsondata();

				for (int i = 0; i < list.size(); i++) {

					List<ProjectCanlianTrainitemAppModel> lAppModels = list.get(i).getPam();

					adapter = new NoPoorProjectCanLian_JYPX_PXRY_Adapter(mContext, lAppModels);

					listview.setAdapter(adapter);
				}

			}
			break;

		default:
			break;
		}
	}
}
