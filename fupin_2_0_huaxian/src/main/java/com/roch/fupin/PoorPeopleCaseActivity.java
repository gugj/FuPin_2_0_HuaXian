package com.roch.fupin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.PeopleCaseEntity;
import com.roch.fupin.entity.PoorPeopleCase;
import com.roch.fupin.entity.PoorPeopleCaseListResult;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * 致贫原因统计页面--饼图
 * @author ZhaoDongShao
 * 2016年6月27日 
 */
@ContentView(R.layout.activity_poor_people_case)
public class PoorPeopleCaseActivity extends MainBaseActivity{

	public static final String TAG = "PoorPeopleCaseActivity";
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.pie_chart)
	PieChartView chart;

	PieChartData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(mActivity);
		// 只初始化toolbar
		initToolbar();
		MyApplication.getInstance().addActivity(mActivity);
		//请求服务器中致贫原因统计的网址
		initData();
	}

	/**
	 * 只初始化toolbar
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

	/**
	 * 点击返回时关闭activity
	 */
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

	/**
	 * 请求服务器中致贫原因统计的网址
	 * 2016年6月25日
	 * ZhaoDongShao
	 */
	private void initData() {
		Intent intent = getIntent();
		String title = intent.getStringExtra(Common.INTENT_KEY);

//		AdlCode adlCode = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mContext,Common.LoginName);
//		if (adlCode != null && adlCode.getAd_nm() != null) {
//			tv_title.setText(adlCode.getAd_nm() + title);
//		}else {
//			tv_title.setText(title);
//		}
		tv_title.setText("致贫原因统计");
		RequestParams rp = getRequestParams();
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, 
				URLs.POOR_PEOPLE_CASE, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.FIRST));
	}



	/**
	 * 获取致贫原因统计的查询条件
	 * @return
	 * 2016年7月2日
	 * ZhaoDongShao
	 */
	private RequestParams getRequestParams() {
		RequestParams rp = new RequestParams();
		String adl_cd = getAdl_Cd();
		if (!adl_cd.equals("")) {
			rp.addBodyParameter("adl_cd", adl_cd);
		}
		return rp;
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag) {
		case MyConstans.FIRST:
			PoorPeopleCaseListResult caseListResult = PoorPeopleCaseListResult.parseToT(str, PoorPeopleCaseListResult.class);
			if (caseListResult != null && caseListResult.getSuccess()) {
				List<PoorPeopleCase> list = caseListResult.getJsondata();
				if (list != null) {
					for (PoorPeopleCase poorPeopleCase : list) {
						List<PeopleCaseEntity> lEntities = poorPeopleCase.getData();
//						for (int i = 0; i < lEntities.size(); i++) {
//							if (lEntities.get(i).getKey().equals("无")) {
//								lEntities.remove(i);
//							}
//						}
//						for (int i = 0; i < lEntities.size(); i++) {
//							if (lEntities.get(i).getKey().equals("其他")) {
//								lEntities.remove(i);
//							}
//						}
						generateData(lEntities);
					}
				}
			}
			break;
			
		default:
			break;
		}
	}

	/**
	 * 初始化饼状图
	 * 2016年6月27日
	 * ZhaoDongShao
	 * @param lEntities 
	 */
	private void generateData(List<PeopleCaseEntity> lEntities){
		//获取饼图所有列
		int numValues = lEntities.size();
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);

		//获取总人数
		int people_count = 0;
		for (int i = 0; i < lEntities.size(); i++) {
			people_count += Integer.parseInt(lEntities.get(i).getValue());
		}

		List<SliceValue> values = new ArrayList<SliceValue>();
		SliceValue sliceValue = null;
		for (int i = 0; i < numValues; ++i) {
			if (Integer.parseInt(lEntities.get(i).getValue()) <= 0) {
				continue;
			}
			if (lEntities.get(i).getKey().equals("因病")) {  // 1111111111111111111111
				String string = numberFormat.format((Float.parseFloat(lEntities.get(i).getValue()) / people_count) * 100);
				sliceValue = new SliceValue();
				sliceValue.setValue(Float.parseFloat(string));
				sliceValue.setColor(ResourceUtil.getInstance().getColorById(R.color.magenta));
				sliceValue.setLabel("因病" + lEntities.get(i).getValue() + "户("+string + "%)");
			}else if (lEntities.get(i).getKey().equals("因残")) { //2222222222222222222222222
				String string = numberFormat.format((Float.parseFloat(lEntities.get(i).getValue()) / people_count) * 100);
				sliceValue = new SliceValue();
				sliceValue.setValue(Float.parseFloat(string));
				sliceValue.setColor(ResourceUtil.getInstance().getColorById(R.color.cornflowerblue));
				sliceValue.setLabel("因残" + lEntities.get(i).getValue() + "户("+string + "%)");
			}else if (lEntities.get(i).getKey().equals("缺资金")) { //333333333333333333333
				String string = numberFormat.format((Float.parseFloat(lEntities.get(i).getValue()) / people_count) * 100);
				sliceValue = new SliceValue();
				sliceValue.setValue(Float.parseFloat(string));
				sliceValue.setColor(ResourceUtil.getInstance().getColorById(R.color.peachpuff));
				sliceValue.setLabel("缺资金" + lEntities.get(i).getValue() + "户("+string + "%)");
			}else if (lEntities.get(i).getKey().equals("缺劳动能力")) { //4444444444444444444444
				String string = numberFormat.format((Float.parseFloat(lEntities.get(i).getValue()) / people_count) * 100);
				sliceValue = new SliceValue();
				sliceValue.setValue(Float.parseFloat(string));
				sliceValue.setColor(ResourceUtil.getInstance().getColorById(R.color.sandybrown));
				sliceValue.setLabel("缺劳动能力" + lEntities.get(i).getValue() + "户("+string + "%)");
			}else if (lEntities.get(i).getKey().equals("缺技术")) { //55555555555555555555555555
				String string = numberFormat.format((Float.parseFloat(lEntities.get(i).getValue()) / people_count) * 100);
				sliceValue = new SliceValue();
				sliceValue.setValue(Float.parseFloat(string));
				sliceValue.setColor(ResourceUtil.getInstance().getColorById(R.color.greenyellow));
				sliceValue.setLabel("缺技术" + lEntities.get(i).getValue() + "户("+string + "%)");
			}else if (lEntities.get(i).getKey().equals("因学")) { //66666666666666666666666666666
				String string = numberFormat.format((Float.parseFloat(lEntities.get(i).getValue()) / people_count) * 100);
				sliceValue = new SliceValue();
				sliceValue.setValue(Float.parseFloat(string));
				sliceValue.setColor(ResourceUtil.getInstance().getColorById(R.color.aquamarine));
				sliceValue.setLabel("因学" + lEntities.get(i).getValue() + "户("+string + "%)");
			}else if (lEntities.get(i).getKey().equals("自身发展动力不足")) { //7777777777777777777777777777
				String string = numberFormat.format((Float.parseFloat(lEntities.get(i).getValue()) / people_count) * 100);
				sliceValue = new SliceValue();
				sliceValue.setValue(Float.parseFloat(string));
				sliceValue.setColor(ResourceUtil.getInstance().getColorById(R.color.zishenfazhanli));
				sliceValue.setLabel("自身发展动力不足" + lEntities.get(i).getValue() + "户("+string + "%)");
			}
			values.add(sliceValue);
		}
		data = new PieChartData(values);
		data.setHasLabels(true);
		data.setHasLabelsOnlyForSelected(false);
		data.setHasLabelsOutside(false);
		chart.setOnValueTouchListener(new ValueTouchListener());
		chart.setPieChartData(data);
	}

	class ValueTouchListener implements PieChartOnValueSelectListener{
		@Override
		public void onValueDeselected() {
		}

		@Override
		public void onValueSelected(int arcIndex, SliceValue value) {
			String string_lableaschart = String.valueOf(value.getLabelAsChars());
			Intent intent = null;
			if (string_lableaschart.contains("因学")) {
				intent = new Intent(mContext, PoorPeopleCaseYXActivity.class);
				intent.putExtra(Common.INTENT_KEY, string_lableaschart);
				startActivity(intent);
			}else {
				intent = new Intent(mContext, PoorPeopleCaseDetailActivity.class);
				intent.putExtra(Common.INTENT_KEY, string_lableaschart);
				startActivity(intent);
			}
		}
	}

}
