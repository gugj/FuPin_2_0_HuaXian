package com.roch.fupin;

import java.util.ArrayList;
import java.util.List;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.AdlCode;
import com.roch.fupin.entity.BasicPoorpeopleModel;
import com.roch.fupin.entity.BasicPoorpeopleModelListResult;
import com.roch.fupin.entity.MapEntity;
import com.roch.fupin.entity.PoorPeopleAgeListResult;
import com.roch.fupin.entity.RateofProjectsModel;
import com.roch.fupin.entity.RateofProjectsModelListResult;
import com.roch.fupin.entity.UsePayModel;
import com.roch.fupin.entity.UsePayModelListResult;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin.utils.Utils;
import com.roch.fupin_2_0.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * 统计信息文化程度、年龄，工作收入
 * @author ZhaoDongShao
 * 2016年6月27日 
 */
@ContentView(R.layout.activity_poor_people_whcd)
public class PoorPeopleWHCDActivity extends MainBaseActivity{

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.column_chart)
	ColumnChartView chart;

	@ViewInject(R.id.iv_lx_num)
	ImageView iv_lx_num;
	@ViewInject(R.id.iv_kg_num)
	ImageView iv_kg_num;
	@ViewInject(R.id.iv_wg_num)
	ImageView iv_wg_num;
	
	@ViewInject(R.id.iv_totle)
	ImageView iv_totle;
	@ViewInject(R.id.iv_sqbf)
	ImageView iv_sqbf;
	@ViewInject(R.id.iv_sjbf)
	ImageView iv_sjbf;
	
	boolean lx = true,kg = true, wg = true;
	boolean totle = true,sqbf = true,sjbf = true;
	
	List<RateofProjectsModel> list = null;
	List<UsePayModel> list2 = null;
	ColumnChartData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initToolbar();
		MyApplication.getInstance().addActivity(this);
		initData();
		resetViewport();
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
	
	/**
	 * 初始化数据
	 * 2016年6月25日
	 * ZhaoDongShao
	 */
	private void initData() {
		Intent intent = getIntent();
		String title = intent.getStringExtra(Common.INTENT_KEY);

		AdlCode adlCode = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mContext,Common.LoginName);
		if (adlCode != null && adlCode.getAd_nm() != null) {
			tv_title.setText(adlCode.getAd_nm() + title);
		}else {
			tv_title.setText(title);
		}

		RequestParams rp = getRequestParams();
		if (title.equals(Common.EXTS_AGE)) {
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, 
					URLs.POOR_PEOPLE_AGE, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.FIRST));
		}else if (title.equals(Common.EXTS_WHCD)) {
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, 
					URLs.POOR_PEOPLE_WHCD, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.SECOND));
		}else if (title.equals(Common.EXTS_WORK)) {
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, 
					URLs.POOR_PEOPLE_WORK, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.THIRD));
		}else if (title.equals(Common.EXTS_PROJECT_MONEY)) { //项目资金使用
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, 
					URLs.POOR_PEOPEL_PROJECT_MONEY, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.FORTH));
		}else if (title.equals(Common.EXTS_XMJD)) { //项目进度
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, 
					URLs.POOR_PEOPEL_XMJD, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.FIFTH));
		}
	}

	/**
	 * 增加查询条件
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
	
	@OnClick({R.id.iv_lx_num,R.id.iv_kg_num,R.id.iv_wg_num,R.id.iv_totle,R.id.iv_sqbf,R.id.iv_sjbf})
	public void onClick(View v){
		if (v.getId() == R.id.iv_lx_num) {
			lx = !lx;
			if (lx) {
				iv_lx_num.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.magenta));
			}else {
				iv_lx_num.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.gray_878787));
			}
			generateProjectData(list);
		}else if (v.getId() == R.id.iv_kg_num) {
			kg = !kg;
			if (kg) {
				iv_kg_num.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.darkkhaki));
			}else {
				iv_kg_num.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.gray_878787));
			}
			generateProjectData(list);
		}else if (v.getId() == R.id.iv_wg_num) {
			wg = !wg;
			if (wg) {
				iv_wg_num.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.cornflowerblue));
			}else {
				iv_wg_num.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.gray_878787));
			}
			generateProjectData(list);
		}else if (v.getId() == R.id.iv_totle) {
			totle = !totle;
			if (totle) {
				iv_totle.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.magenta));
			}else {
				iv_totle.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.gray_878787));
			}
			generateUsePayData(list2);
		}else if (v.getId() == R.id.iv_sqbf) {
			sqbf = !sqbf;
			if (sqbf) {
				iv_sqbf.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.darkkhaki));
			}else {
				iv_sqbf.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.gray_878787));
			}
			generateUsePayData(list2);
		}else if (v.getId() == R.id.iv_sjbf) {
			sjbf = !sjbf;
			if (sjbf) {
				iv_sjbf.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.cornflowerblue));
			}else {
				iv_sjbf.setBackgroundColor(ResourceUtil.getInstance().getColorById(R.color.gray_878787));
			}
			generateUsePayData(list2);
		}
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag) {
		case MyConstans.FIRST:
			PoorPeopleAgeListResult ageListResult = PoorPeopleAgeListResult.parseToT(str, PoorPeopleAgeListResult.class);
			if (ageListResult != null && ageListResult.getSuccess()) {
				List<MapEntity> list = ageListResult.getJsondata();
				if (list != null) {
					generateAgeData(list);
				}
			}
			break;
			
		case MyConstans.SECOND: //文化程度
			BasicPoorpeopleModelListResult result = BasicPoorpeopleModelListResult.parseToT(str, BasicPoorpeopleModelListResult.class);
			if (result != null && result.getSuccess()) {
				List<BasicPoorpeopleModel> list = result.getJsondata();
				if (list != null) {
					generateData(list);
				}
			}
			break;
			
		case MyConstans.THIRD: //就业收入
			PoorPeopleAgeListResult peopleAgeListResult = PoorPeopleAgeListResult.parseToT(str, PoorPeopleAgeListResult.class);
			if (peopleAgeListResult != null && peopleAgeListResult.getSuccess()) {
				List<MapEntity> list = peopleAgeListResult.getJsondata();
				if (list != null) {
					generateMoneyData(list);
				}
			}
			break;
			
		case MyConstans.FORTH://资金统计
			UsePayModelListResult usePayModelListResult = UsePayModelListResult.parseToT(str, UsePayModelListResult.class);
			if (usePayModelListResult != null && usePayModelListResult.getSuccess()) {
				list2 = usePayModelListResult.getJsondata();
				if (list2 != null) {
					generateUsePayData(list2);
				}
			}
			break;
			
		case MyConstans.FIFTH: //项目进度
			RateofProjectsModelListResult rateofProjectsModelListResult = RateofProjectsModelListResult.parseToT(str, RateofProjectsModelListResult.class);
			if (rateofProjectsModelListResult != null && rateofProjectsModelListResult.getSuccess()) {
				list = rateofProjectsModelListResult.getJsondata();
				if (list != null) {
					generateProjectData(list);
				}
			}
			break;
			
		default:
			break;
		}
	}

	/**
	 * 项目进度
	 * @param list
	 * 2016年6月27日
	 * ZhaoDongShao
	 */
	private void generateProjectData(List<RateofProjectsModel> list) {
		int numSubcolumns = 3;
		int numColumns = list.size();

		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values;
		List<AxisValue> axisXValues = new ArrayList<AxisValue>();
		for (int i = 0; i < numColumns; ++i) {
			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
				if (j == 0) {
					if (lx) {
						values.add(new SubcolumnValue(Integer.parseInt(list.get(i).getLx_c()),ResourceUtil.getInstance().getColorById(R.color.magenta)));
						axisXValues.add(new AxisValue(i).setLabel(list.get(i).getName()));
					}
				}else if (j==1) {
					if (kg) {
						values.add(new SubcolumnValue(Integer.parseInt(list.get(i).getKg_c()),ResourceUtil.getInstance().getColorById(R.color.darkkhaki)));
						axisXValues.add(new AxisValue(i).setLabel(list.get(i).getName()));
					}
				}else if (j==2) {
					if (wg) {
						values.add(new SubcolumnValue(Integer.parseInt(list.get(i).getWg_c()),ResourceUtil.getInstance().getColorById(R.color.cornflowerblue)));
						axisXValues.add(new AxisValue(i).setLabel(list.get(i).getName()));
					}
				}
			}
			Column column = new Column(values);
			column.setHasLabels(true);
			column.setHasLabelsOnlyForSelected(false);
			columns.add(column);
		}

		data = new ColumnChartData(columns);
		data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("局委").setHasTiltedLabels(true).setMaxLabelChars(5));
		data.setAxisYLeft(new Axis().setHasLines(true).setName("个数").setTextColor(Color.BLACK).setMaxLabelChars(3));
		
		LayoutParams lp = (LayoutParams) chart.getLayoutParams();
		lp.setMargins(0, 0, 0, Utils.dip2px(mContext, 30));
		chart.setLayoutParams(lp);
		chart.setColumnChartData(data);

		LinearLayout layout = (LinearLayout)findViewById(R.id.ll);
		layout.setVisibility(View.VISIBLE);
	}

	/**
	 * 项目资金
	 * @param list
	 * 2016年6月27日
	 * ZhaoDongShao
	 */
	private void generateUsePayData(List<UsePayModel> list) {
		int numSubcolumns = 3;
		int numColumns = list.size();

		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values;
		List<AxisValue> axisXValues = new ArrayList<AxisValue>();
		for (int i = 0; i < numColumns; ++i) {
			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
//				values.add(new SubcolumnValue(Float.parseFloat(list.get(i).getInvestTotal() == null ? "0.00" : list.get(i).getInvestTotal()),ChartUtils.pickColor()));
//				axisXValues.add(new AxisValue(i).setLabel(list.get(i).getDepName()));
				if (j == 0) {
					if (totle) { //计划投资总金额
						values.add(new SubcolumnValue(Float.parseFloat(list.get(i).getInvestTotal() == null ? "0.00" : list.get(i).getInvestTotal()),ResourceUtil.getInstance().getColorById(R.color.magenta)));
						axisXValues.add(new AxisValue(i).setLabel(list.get(i).getDepName()));
					}
				}else if (j==1) {
					if (sqbf) { //申请拨付
						values.add(new SubcolumnValue(Float.parseFloat(list.get(i).getApproveAmount() == null ? "0.00" : list.get(i).getApproveAmount()),ResourceUtil.getInstance().getColorById(R.color.darkkhaki)));
						axisXValues.add(new AxisValue(i).setLabel(list.get(i).getDepName()));
					}
				}else if (j==2) { //实际拨付
					if (sjbf) { //实际拨付
						values.add(new SubcolumnValue(Float.parseFloat(list.get(i).getPaidAmount() == null ? "0.00" : list.get(i).getPaidAmount()),ResourceUtil.getInstance().getColorById(R.color.cornflowerblue)));
						axisXValues.add(new AxisValue(i).setLabel(list.get(i).getDepName()));
					}
				}
			}
			Column column = new Column(values);
			column.setHasLabels(true);
			column.setHasLabelsOnlyForSelected(false);
			columns.add(column);
		}

		data = new ColumnChartData(columns);
		data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("局委").setHasTiltedLabels(true).setMaxLabelChars(5));
		data.setAxisYLeft(new Axis().setHasLines(true).setName("资金(万元)").setTextColor(Color.BLACK).setMaxLabelChars(5));

		LayoutParams lp = (LayoutParams) chart.getLayoutParams();
		lp.setMargins(0, 0, 0, Utils.dip2px(mContext, 30));
		chart.setLayoutParams(lp);
		chart.setColumnChartData(data);

		LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
		layout.setVisibility(View.VISIBLE);
	}

	/**
	 * 年龄段统计
	 * @param list
	 * 2016年6月27日
	 * ZhaoDongShao
	 */
	private void generateAgeData(List<MapEntity> list) {
		int numSubcolumns = 1;
		int numColumns = list.size();

		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values;
		List<AxisValue> axisXValues = new ArrayList<AxisValue>();
		for (int i = 0; i < numColumns; ++i) {
			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
				values.add(new SubcolumnValue(Integer.parseInt(list.get(i).getValue()),ChartUtils.pickColor()));
				axisXValues.add(new AxisValue(i).setLabel(list.get(i).getKey()));
			}
			Column column = new Column(values);
			column.setHasLabels(true);
			column.setHasLabelsOnlyForSelected(false);
			columns.add(column);
		}
		data = new ColumnChartData(columns);
		data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("年龄段").setHasTiltedLabels(true).setMaxLabelChars(8));
		data.setAxisYLeft(new Axis().setHasLines(true).setName("人数").setTextColor(Color.BLACK).setMaxLabelChars(5));
		chart.setColumnChartData(data);
	}

	private void resetViewport() {
		final Viewport v = new Viewport();
		v.bottom = 0;
		v.top = 1000;
		v.left = 0;
		chart.setMaximumViewport(v);
		chart.setCurrentViewport(v);
	}

	/**
	 * 初始化文化程度柱状图
	 * 2016年6月27日
	 * ZhaoDongShao
	 * @param list 
	 */
	private void generateData(List<BasicPoorpeopleModel> list){
		int numSubcolumns = 1;
		int numColumns = list.size();

		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values;
		List<AxisValue> axisXValues = new ArrayList<AxisValue>();
		for (int i = 0; i < numColumns; ++i) {
			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
				values.add(new SubcolumnValue(list.get(i).getWhcd_pc(),ChartUtils.pickColor()));
				axisXValues.add(new AxisValue(i).setLabel(list.get(i).getCulturelevelid()));
			}
			Column column = new Column(values);
			column.setHasLabels(true);
			column.setHasLabelsOnlyForSelected(false);
			columns.add(column);
		}

		data = new ColumnChartData(columns);
		data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("学历").setHasTiltedLabels(true).setMaxLabelChars(8));
		data.setAxisYLeft(new Axis().setHasLines(true).setName("人数").setTextColor(Color.BLACK).setMaxLabelChars(5));
		chart.setColumnChartData(data);

	}

	/**
	 * 初始化就业收入
	 * @param list
	 * 2016年6月27日
	 * ZhaoDongShao
	 */
	private void generateMoneyData(List<MapEntity> list) {
		int numSubcolumns = 1;
		int numColumns = list.size();

		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values;
		List<AxisValue> axisXValues = new ArrayList<AxisValue>();
		for (int i = 0; i < numColumns; ++i) {
			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
				values.add(new SubcolumnValue(Integer.parseInt(list.get(i).getValue()),ChartUtils.pickColor()));
				axisXValues.add(new AxisValue(i).setLabel(list.get(i).getKey()));
			}
			Column column = new Column(values);
			column.setHasLabels(true);
			column.setHasLabelsOnlyForSelected(false);
			columns.add(column);
		}

		data = new ColumnChartData(columns);
		data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("收入(元)").setMaxLabelChars(10).setHasTiltedLabels(true));
		data.setAxisYLeft(new Axis().setHasLines(true).setName("人数").setTextColor(Color.BLACK).setMaxLabelChars(5));
		chart.setColumnChartData(data);
	}
	
}
