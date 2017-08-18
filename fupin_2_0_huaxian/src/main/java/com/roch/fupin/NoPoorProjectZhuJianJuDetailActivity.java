/**
 * 
 */
package com.roch.fupin;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin_2_0.R;
import com.roch.fupin.adapter.NoPoorProjectFuLian_XMXX_Adapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.MapEntity;
import com.roch.fupin.entity.ProjectZhujianAppModel;
import com.roch.fupin.utils.Common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 住建局
 * 
 * @author ZhaoDongShao
 *
 * 2016年6月2日 
 *
 */
@ContentView(R.layout.activity_helppeople_familypeople)
public class NoPoorProjectZhuJianJuDetailActivity extends MainBaseActivity{

	@ViewInject(R.id.lv_poor)
	ListView listview;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;

	static String uid = "id";

	NoPoorProjectFuLian_XMXX_Adapter adapter;
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
	 * 2016年5月25日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initData() {

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra(Common.INTENT_KEY);
		ProjectZhujianAppModel appModel = (ProjectZhujianAppModel) bundle.getSerializable(Common.BUNDEL_KEY);
		if (appModel != null) {

			tv_title.setText(Common.PROJECT_DETAIL);

			List<MapEntity> xmxxs = new ArrayList<MapEntity>(); //项目信息
			xmxxs.add(new MapEntity("项目名称", appModel.getProjectname()));
			xmxxs.add(new MapEntity("户主姓名", appModel.getPersonname()));
			xmxxs.add(new MapEntity("旧房改造年代", appModel.getOldbuildyear()));
			xmxxs.add(new MapEntity("改造原因", appModel.getRebuildreasonidcall()));
			xmxxs.add(new MapEntity("改造方式", appModel.getRebuildmodeidcall()));
			xmxxs.add(new MapEntity("旧房建筑面积", String.valueOf(appModel.getOldarea())));
			xmxxs.add(new MapEntity("改造后建筑面积", String.valueOf(appModel.getNewarea())));
			xmxxs.add(new MapEntity("旧房房屋结构", appModel.getOldstructidcall()));
			xmxxs.add(new MapEntity("改造房屋后结构", appModel.getNewstructidcall()));
			xmxxs.add(new MapEntity("总投资金额（元）", String.valueOf(appModel.getTotalamount())));
			xmxxs.add(new MapEntity("农户其他自筹金额（元）", String.valueOf(appModel.getSelfotheramount())));
			xmxxs.add(new MapEntity("农户贷款（元）", String.valueOf(appModel.getSelfloan())));
			xmxxs.add(new MapEntity("申请进度", appModel.getApprovestatusidcall()));
			xmxxs.add(new MapEntity("申请日期", appModel.getApprovedate() != null ? appModel.getApprovedate().split(" ")[0] : ""));
			xmxxs.add(new MapEntity("申请补助资金", String.valueOf(appModel.getApproveamount())));
			xmxxs.add(new MapEntity("实际补助资金", String.valueOf(appModel.getFactamount())));
			adapter = new NoPoorProjectFuLian_XMXX_Adapter(mContext, xmxxs);
			listview.setAdapter(adapter);
		}
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

			MyApplication.getInstance().finishActivity(this);

			break;

		default:
			break;
		}

		return true;
	}
}
