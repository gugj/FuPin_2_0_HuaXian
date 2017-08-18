package com.roch.fupin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.ListMonthAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.PoorFamilyAccountPrint;
import com.roch.fupin.entity.PoorFamilyAccountPrintListResult;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 贫困户台账信息
 * 
 * @author ZhaoDongShao
 *
 * 2016年5月9日
 *
 */
public class PoorHouseAccountPrintFragment extends BaseFragment {

	@ViewInject(R.id.lv)
	ListView listView;

	@ViewInject(R.id.tv_db)
	TextView tv_db;
	@ViewInject(R.id.tv_yl)
	TextView tv_yl;
	@ViewInject(R.id.tv_xnh)
	TextView tv_xnh;
	@ViewInject(R.id.tv_hl)
	TextView tv_hl;
	@ViewInject(R.id.tv_stl)
	TextView tv_stl;
	@ViewInject(R.id.tv_jy)
	TextView tv_jy;
	@ViewInject(R.id.tv_ls)
	TextView tv_ls;
	@ViewInject(R.id.tv_yz)
	TextView tv_yz;
	@ViewInject(R.id.tv_gp)
	TextView tv_gp;
	@ViewInject(R.id.tv_sm)
	TextView tv_sm;
	@ViewInject(R.id.tv_gz)
	TextView tv_gz;
	@ViewInject(R.id.tv_shjz)
	TextView tv_shjz;
	@ViewInject(R.id.tv_zfww)
	TextView tv_zfww;
	@ViewInject(R.id.tv_qt)
	TextView tv_qt;
	@ViewInject(R.id.tv_totle)
	TextView tv_totle;
	@ViewInject(R.id.tv_rj)
	TextView tv_rj;
//	@ViewInject(R.id.tv_dilibaohu)
//	TextView tv_diLiBuTie; // 地力保护补贴
//	@ViewInject(R.id.tv_weizhi)
//	TextView tv_weiZhi; // 未知
	@ViewInject(R.id.tv_jy_sr)
	TextView tv_jy_sr;// 经营性收入
	@ViewInject(R.id.tv_cyfc)
	TextView tv_cyfc;
	@ViewInject(R.id.tv_dlzs)
	TextView tv_dlzs;
	
	/**
	 * 所有标题
	 */
	List<View> listviews = new ArrayList<View>();
	
	/**
	 * 标志位，标志初始化已经完成
	 */
	private boolean isPrepared;

	/**
	 * 标识当前fragment是否可见
	 */
	private boolean isVisible;

	ListMonthAdapter adapter;
	// String[] months;
	// Float[] moneys;
	// int numberOfLines = 1;
	// int maxNumberOfLines;
	// int numberOfPoints;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_poorhouse_accountprint, container, false);
		ViewUtils.inject(this, view);
		isPrepared = true;
		lazyLoad();
		return view;
	}

	/**
	 * 判断当前的fragment是否可见，如果可见就请求网络加载数据；否则，不请求网络加载数据
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			isVisible = true;
			lazyLoad();
		} else {
			isVisible = false;
		}
	}

	/**
	 * 
	 * 2016年10月27日
	 *
	 * 如果没有初始化完成或当前fragment不可见，就不去请求网络加载数据；否则开始请求网络加载数据
	 */
	private void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
		initData();
	}

	/**
	 *
	 *
	 * 2016年7月20日
	 *
	 * ZhaoDongShao
	 *
	 * private void initData() { 
	 * 
	 * Bundle bundle = getArguments(); if (!StringUtil.isEmpty(bundle))
	 * { @SuppressWarnings("unchecked") List
	 * <PoorFamilyAccountPrint> lFamilyIncomes = (List<PoorFamilyAccountPrint>)
	 * bundle.getSerializable(Common.BUNDEL_KEY); final String houseid =
	 * bundle.getString(Common.TITLE_KEY);
	 * 
	 * List<String> months = new ArrayList<String>(); for (int i = 0; i <
	 * lFamilyIncomes.size(); i++) {
	 * months.add(lFamilyIncomes.get(i).getIncomemonth()); }
	 * 
	 * 
	 * if (months.size() > 0) {
	 * 
	 * adapter = new ListMonthAdapter(getActivity(), months,
	 * R.drawable.choose_item_selected_2, R.drawable.choose_eara_item_selector);
	 * listView.setAdapter(adapter); adapter.setSelectedPosition(0);
	 * adapter.setOnItemClickListener(new
	 * ListMonthAdapter.OnItemClickListener(){
	 * 
	 * @Override public void onItemClick(String month, int position) { // TODO
	 *           Auto-generated method stub
	 * 
	 * 
	 *           init(houseid, month);
	 * 
	 *           }
	 * 
	 *           }); init(houseid, months.get(0)); } } }
	 */

	/**
	 * 2016年7月20日   ZhaoDongShao <br/>
	 *
	 * 初始化数据，即通过Bundle获取传输过来的hourseid,然后初始化请求网络数据
	 */
	private void initData() {
		Bundle bundle = getArguments();
		if (!StringUtil.isEmpty(bundle)) {
			@SuppressWarnings("unchecked")
			final String houseid = bundle.getString(Common.TITLE_KEY);
			init(houseid);
		}
	}

	/**
	 *
	 * 2016年7月21日  ZhaoDongShao <br/>
	 * 
	 * 初始化网络请求，使用xUtils的post请求方式将RequestParams参数进行封装，请求参数为贫困户id
	 * 
	 * @param houseid 贫困户id
	 *
	 */
	protected void init(String houseid) {
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("householderid", houseid);
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, URLs.POOR_HOUSE_ACCENT, rp,
				new MyRequestCallBack(PoorHouseAccountPrintFragment.this, MyConstans.FIRST));
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);

		PoorFamilyAccountPrintListResult result = null;
		switch (flag) {
		case MyConstans.FIRST:

			result = PoorFamilyAccountPrintListResult.parseToT(str, PoorFamilyAccountPrintListResult.class);

			if (result != null && result.getSuccess()) {
				final List<PoorFamilyAccountPrint> list = result.getJsondata();
				if (list != null && list.size() > 0) {
					List<String> months = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getIncomemonth().equals("total")) {
							months.add("年度合计");
						} else {
							months.add(list.get(i).getIncomemonth());
						}
					}

					if (months.size() > 0) {

						adapter = new ListMonthAdapter(getActivity(), months, R.drawable.choose_item_selected_2,
								R.drawable.choose_eara_item_selector);
						listView.setAdapter(adapter);
						adapter.setSelectedPosition(0);
						adapter.setOnItemClickListener(new ListMonthAdapter.OnItemClickListener() {

							@Override
							public void onItemClick(String month, int position) {
								for (int i = 0; i < list.size(); i++) {

									String incomemonth = list.get(i).getIncomemonth();
									if (month.equals(incomemonth)||month.equals("年度合计")) {

										bindMonth(list.get(i));  
										break;
									}
								}
							}
						});
					}
					bindMonth(list.get(0));  
				}
			}
			break;

		default:
			break;
		}
	}
	
	private void bindMonth(PoorFamilyAccountPrint model) {
		 
		tv_db.setText("低保补助：" + String.valueOf(model.getDibao()) + "元");
		tv_yl.setText("养老保险：" + String.valueOf(model.getYanglao()) + "元");
		tv_xnh.setText("新农合补助：" + String.valueOf(model.getXinnonghe()) + "元");
		tv_hl.setText("还林补偿：" + String.valueOf(model.getHuanlin()) + "元");
		tv_stl.setText("生态林补偿：" + String.valueOf(model.getShengtailin()) + "元");
//		tv_diLiBuTie.setText("地力保护补贴：" + String.valueOf(model.getDilibaohu()) + "元");
//		tv_weiZhi.setText("未知：" );
		tv_jy.setText("教育补助：" + String.valueOf(model.getJiaoyu()) + "元");
		tv_ls.setText("粮食收入：" + String.valueOf(model.getLiangshi()) + "元");
		tv_yz.setText("养殖收入：" + String.valueOf(model.getYangzhi()) + "元");
		tv_gp.setText("果品收入：" + String.valueOf(model.getGuopin()) + "元");
		tv_sm.setText("树木收入：" + String.valueOf(model.getShumu()) + "元");
		tv_gz.setText(String.valueOf(model.getJiuye()) + "元");
		tv_jy_sr.setText(String.valueOf(model.getBusinessexpenditure()) + "元");
		tv_cyfc.setText(String.valueOf(model.getChanYeFuChi()) + "元");
		tv_shjz.setText("社会捐赠：" + String.valueOf(model.getJuanzeng()) + "元");
		tv_zfww.setText("政府慰问：" + String.valueOf(model.getWeiwen()) + "元");
		tv_qt.setText(String.valueOf(model.getQita()) + "元");

		tv_totle.setText(model.getTotal() + "元");
		tv_dlzs.setText(String.valueOf(model.getDaoHuZengShou()) + "元");
		// tv_totle.setTextColor(Color.RED);
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(2);

		tv_rj.setText(model.getTotal_perp() + "元");
		// tv_rj.setText(format.format(Double.parseDouble(model.getTotal_income())
		// Integer.parseInt(model.getNumcount()))
		// + "元");
		tv_rj.setTextColor(Color.RED);
  
	}
	// /**
	// * 2016年5月10日
	// *
	// * ZhaoDongShao
	// *
	// */
	// @SuppressWarnings("unchecked")
	// private void initData() {
	// List<String> month = new ArrayList<String>();
	// List<Float> money = new ArrayList<Float>();
	//
	// Bundle bundle = getArguments();
	// if (!StringUtil.isEmpty(bundle)) {
	// List<PoorFamilyAccountPrint> lFamilyIncomes =
	// (List<PoorFamilyAccountPrint>) bundle.getSerializable(Common.BUNDEL_KEY);
	//
	// // List<PoorFamilyAccountPrint> lFamilyIncomes =
	// PoorHouseBaseFragment.poorFamily.getPia();
	// if (!StringUtil.isEmpty(lFamilyIncomes)&&lFamilyIncomes.size() > 0) {
	//
	// for (int i = 0; i< lFamilyIncomes.size(); i++) {
	//
	// month.add(lFamilyIncomes.get(i).getIncomemonth());
	// money.add(Float.valueOf(lFamilyIncomes.get(i).getTotal_income()));
	//
	// }
	// maxNumberOfLines = month.size();
	// months = month.toArray(new String[maxNumberOfLines]);
	// numberOfPoints = money.size();
	// moneys = money.toArray(new Float[numberOfPoints]);
	//
	// generateDefaultData();
	// }
	// }else {
	// ShowToast("当前无台账信息");
	// }
	// }

	// /**
	// *
	// * 2016年5月10日
	// *
	// * ZhaoDongShao
	// *
	// */
	// private void generateDefaultData() {
	// numberOfPoints = months.length;
	// // Column can have many subcolumns, here by default I use 1 subcolumn in
	// each of 8 columns.
	// List<AxisValue> axisXValues = new ArrayList<AxisValue>();
	// List<Line> lines = new ArrayList<Line>();
	// for (int i = 0; i < numberOfLines; ++i) {
	// List<PointValue> values = new ArrayList<PointValue>();
	// for (int j = 0; j < numberOfPoints; ++j) {
	// values.add(new PointValue(j,moneys[j]));
	//
	// axisXValues.add(new AxisValue(j).setLabel(months[j]));
	// }
	//
	// Line line = new Line(values);
	// line.setColor(ChartUtils.COLORS[i]);
	// line.setShape(ValueShape.CIRCLE); //节点的形状
	// line.setHasLabels(true); //是否显示标签
	// line.setHasLabelsOnlyForSelected(false); //标签是否只能选中
	// line.setHasLines(true); //是否显示折线
	// line.setHasPoints(true); //是否显示节点
	// line.setPointColor(ChartUtils.COLORS[(i + 1) %
	// ChartUtils.COLORS.length]);
	// lines.add(line);
	// }
	//
	// data = new LineChartData(lines);
	//
	// if (hasAxes) {
	// data.setAxisXBottom(new
	// Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("月份").setHasTiltedLabels(true).setMaxLabelChars(7));
	// data.setAxisYLeft(new
	// Axis().setHasLines(true).setName("收入").setTextColor(Color.BLACK).setMaxLabelChars(4));
	// } else {
	// data.setAxisXBottom(null);
	// data.setAxisYLeft(null);
	// }
	//
	// data.setBaseValue(Float.NEGATIVE_INFINITY);
	// chart.setValueSelectionEnabled(false);
	// chart.setLineChartData(data);
	// }

}
