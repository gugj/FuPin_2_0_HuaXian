package com.roch.fupin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.AdlCode;
import com.roch.fupin.entity.Basic_DistrictAppModel;
import com.roch.fupin.entity.PinKunCun;
import com.roch.fupin.entity.PoorcityLedgerModel;
import com.roch.fupin.entity.PoorcityLedgerModelListResult;
import com.roch.fupin.entity.User;
import com.roch.fupin.entity.ZidianAppEntity;
import com.roch.fupin.entity.ZidianAppEntityListResult;
import com.roch.fupin.utils.AdlcdUtil;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin.utils.Utils;
import com.roch.fupin.view.ExpandTabView;
import com.roch.fupin.view.MenuRight;
import com.roch.fupin.view.MenuRight_2;
import com.roch.fupin_2_0.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 贫困人口统计
 *
 * @author ZhaoDongShao
 * 2016年6月25日
 */
@ContentView(R.layout.activity_poor_people_statistics)
public class PoorPeopleStatisticsActivity extends MainBaseActivity {

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;

	/**
	 * 贫困人口统计的表格tablayout
	 */
	@ViewInject(R.id.ll)
	TableLayout layout;
	TableRow row;

	/**
	 * 自定义的LinearLayout布局---筛选条件的容器
	 */
	@ViewInject(R.id.expandtab_view)
	ExpandTabView expandTabView;
	/**
	 * 自定义的RelativeLayout---进行乡镇的筛选
	 */
	MenuRight mMenuRight_1;
	/**
	 * 自定义的RelativeLayout---进行村的筛选
	 */
	MenuRight_2 mMenuRight_2;
	/**
	 * 存储自定义的筛选条件布局（即RelativeLayout）的集合
	 */
	List<View> mViewArray = new ArrayList<View>();
	/**
	 * 查询条件
	 */
	String adl_cd = "", poor_zhen = "", poor_cun = "";
	List<Basic_DistrictAppModel> models = new ArrayList<Basic_DistrictAppModel>();

	/**
	 * 标志为---1为乡镇、2为贫困村
	 */
	int flag_zhen_cun=1;
	/**
	 * 当前选择的乡镇的adl_cd
	 */
	String adl_cd_zhen="";
	/**
	 * 当前选择的贫困村的adl_cd
	 */
	String adl_cd_cun="";
	String select_poor_zhen="";

	/**
	 * 贫困人口统计的表格tablayout的标题
	 */
//	String[] column = {"行政区","总人口数","贫困户数","贫困人数","贫困比例"};
	String[] column = {"行政区", "贫困户数", "贫困人数", // 0-1-2
			"已脱贫人口数","已脱贫户数",  // 3-4
			"未脱贫人口数", "未脱贫户数", // 5-6
			"返贫人口数","返贫户数",      // 7-8
			"2014年已脱贫人数","2014年已脱贫户数",  // 9-10
			"2015年已脱贫人数","2015年已脱贫户数",  // 11-12
			"2016年已脱贫人数", "2016年已脱贫户数",  // 13-14
			"2017年已脱贫人数","2017年已脱贫户数",  // 15-16
			"因病致贫人数", "因病致贫户数",        // 17-18
			"因残致贫人数", "因残致贫户数",       // 19-20
			"因学致贫人数","因学致贫户数",       // 21-22
			"缺技术致贫人数", "缺技术致贫户数",  // 23-24
			"缺资金致贫人数","缺资金致贫户数"};  // 25-26

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(mActivity);
		initToolbar();

		//将自定义的LinearLayout布局---筛选条件的容器，显示出来
		expandTabView.setVisibility(View.VISIBLE);
		MyApplication.getInstance().addActivity(mActivity);
		//自定义的RelativeLayout---乡镇筛选
		mMenuRight_1 = new MenuRight(mContext);
		//自定义的RelativeLayout---村筛选
		mMenuRight_2 = new MenuRight_2(mContext);

		mViewArray.add(mMenuRight_1);
		mViewArray.add(mMenuRight_2);

		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("镇");
		mTextArray.add("村");

		expandTabView.setValue(mTextArray, mViewArray);

		String ad_cd = "";
		User user = MyApplication.getInstance().getLogin(Common.LoginName);
		if (user.getAdl_cd() != null && user.getAdl_cd() != null && !user.getAdl_cd().equals("")) {
			if (AdlcdUtil.isCountry(user.getAdl_cd()) || AdlcdUtil.isTown(user.getAdl_cd())) {
				ad_cd = user.getAdl_cd();
			} else if (AdlcdUtil.isVillage(user.getAdl_cd())) {
				ad_cd = AdlcdUtil.generateTownCode(user.getAdl_cd());
			} else {
				AdlCode adlCode = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mContext, Common.LoginName);
				ad_cd = adlCode.getAd_cd();
			}
		}
		// 请求服务器---乡镇数据
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("ad_cd", ad_cd);
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.TOWN, rp, new MyRequestCallBack(this, MyConstans.SEVEN));
		System.out.println("贫困人口统计页面一进来初始化时请求服务器中筛选条件的乡镇(flag=7)网址为：==" + URLs.TOWN + "?&ad_cd=" + ad_cd);

		initData();

		//给ExtendListView即贫困镇、贫困村菜单选项设置点击监听，点击后弹出筛选条件
		initListener();
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

	/**
	 * 给ExtendListView即乡镇、村菜单选项设置点击监听，点击后弹出筛选条件
	 * 2016年11月2日
	 */
	private void initListener() {
		// 给贫困镇菜单设置点击监听
		mMenuRight_1.setOnSelectListener(new MenuRight.OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				flag_zhen_cun = 1;
				if(!"全部".equals(showText)){
					//点击贫困乡镇时请求服务器数据---该乡镇下的村
					onRefresh(mMenuRight_1, showText);
					//点击贫困乡镇flag=1或贫困村flag=2时请求服务器的贫困人口统计数据
					getData();
				}else {
					expandTabView.onPressBack();
					// 获取其在储存菜单中的位置position
					int position = getPositon(mMenuRight_1);
					if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
						expandTabView.setTitle(showText, position);
					}
					poor_zhen = mMenuRight_1.getShowText();    //贫困镇
					select_poor_zhen=poor_zhen;

					poor_cun_list.clear();
					mMenuRight_2.setStringArray(poor_cun_list);
					expandTabView.initSecetText(1);

					initData();
				}
			}
		});

		// 给贫困村菜单设置点击监听
		mMenuRight_2.setOnSelectListener(new MenuRight_2.OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				flag_zhen_cun=2;
//				if(!"全部".equals(showText)){
//					//点击贫困村时获取选中的贫困村的adl_cd
//					onRefresh2(mMenuRight_2, showText);
//				}else {
//
//				}
				//点击贫困村时获取选中的贫困村的adl_cd
				onRefresh2(mMenuRight_2, showText);
				//点击贫困乡镇flag=1或贫困村flag=2时请求服务器的贫困人口统计数据
				getData();
			}
		});
	}

	/**
	 * 点击贫困村时请求服务器数据---该贫困村的贫困人口统计
	 * @param mMenuRight_2
	 * @param showText
	 */
	private void onRefresh2(MenuRight_2 mMenuRight_2, String showText) {
		expandTabView.onPressBack();
		// 获取其在储存菜单中的位置position
		int position = getPositon(mMenuRight_2);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		poor_cun = mMenuRight_2.getShowText(); //贫困村
		if(null!=models &&models.size()>0){
			for (int i = 0; i < models.size(); i++) {
				if(null!=poor_cun && poor_cun.equals(models.get(i).getAd_nm())){
					adl_cd_cun=models.get(i).getAd_cd();
				}
			}
		}
	}

	/**
	 * 点击贫困乡镇flag=1或贫困村flag=2时请求服务器的贫困人口统计数据
	 */
	private void getData() {
		RequestParams rp = new RequestParams();
		if(flag_zhen_cun==1){
			if(StringUtil.isNotEmpty(adl_cd_zhen)){
				rp.addBodyParameter("adl_cd",adl_cd_zhen);
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.POOR_PEOPLE_STATISTIC, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
				System.out.println("贫困人口统计请求服务器网址为：===" + URLs.POOR_PEOPLE_STATISTIC);
			}
		}else if(flag_zhen_cun==2){
			if(StringUtil.isNotEmpty(adl_cd_cun)){
				rp.addBodyParameter("adl_cd",adl_cd_cun);
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.POOR_PEOPLE_STATISTIC, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
				System.out.println("贫困人口统计请求服务器网址为：===" + URLs.POOR_PEOPLE_STATISTIC);
			}
		}
	}

	/**
	 * 点击贫困乡镇时请求服务器数据---该乡镇下的村和贫困人口统计
	 * @param view
	 * @param showText
	 */
	private void onRefresh(View view, String showText) {
		expandTabView.onPressBack();
		// 获取其在储存菜单中的位置position
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}

		poor_zhen = mMenuRight_1.getShowText();    //贫困镇
		select_poor_zhen=poor_zhen;

		if(null!=mAppModel &&mAppModel.size()>0){
			for (int i = 0; i < mAppModel.size(); i++) {
				if(null!=poor_zhen && poor_zhen.equals(mAppModel.get(i).getAd_nm())){
					adl_cd_zhen=mAppModel.get(i).getAd_cd();
					RequestParams rp = new RequestParams();
					rp.addBodyParameter("ad_cd", adl_cd_zhen);
					// 请求网络数据---贫困户List列表
					MyApplication.getInstance().getHttpUtilsInstance()
							.send(HttpMethod.POST, URLs.VILLAGE, rp,
									new MyRequestCallBack((BaseActivity) mActivity, MyConstans.EIGHT));
					System.out.println("筛选条件设置好以后请求服务器中的贫困村List(flag=1)网址为：===" + URLs.POOR_HOUSE_LISE+"?&ad_cd="+adl_cd_zhen);
				}
			}
		}
	}

	/**
	 * 根据展示的popuwindow窗口，判断其在储存菜单的位置position
	 *
	 * @param tView 展示的popuwindow窗口
	 * @return 返回其在储存菜单的位置position
	 * 2016年11月2日
	 */
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				MyApplication.getInstance().finishActivity(this);
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
		AdlCode adlCode = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mContext, Common.LoginName);

//		if (adlCode != null && adlCode.getAd_nm() != null) {
//			tv_title.setText(adlCode.getAd_nm() + title);
//		} else {
//			tv_title.setText(title);
//		}
		tv_title.setText("贫困人口统计");
		RequestParams rp = getRequestParams();
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.POOR_PEOPLE_STATISTIC, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
		System.out.println("贫困人口统计请求服务器网址为：===" + URLs.POOR_PEOPLE_STATISTIC);
	}

	/**
	 * 增加查询条件
	 *
	 * @return 2016年7月2日
	 * ZhaoDongShao
	 */
	private RequestParams getRequestParams() {
		RequestParams rp = new RequestParams();
//		String adl_cd = getAdl_Cd();
		if(adl_cd.equals("")){
			SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
			adl_cd =sp.getString("adl_cd","");
		}
		if (!adl_cd.equals("")) {
			rp.addBodyParameter("adl_cd", adl_cd);
		}
		return rp;
	}

	List<PinKunCun> poor_cun_list;
	@SuppressLint("InflateParams")
	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag) {
			case MyConstans.FIRST:
				System.out.println("贫困人口统计请求服务器的数据成功：===" + str);
				PoorcityLedgerModelListResult modelListResult = PoorcityLedgerModelListResult.parseToT(str, PoorcityLedgerModelListResult.class);
				if (modelListResult != null && modelListResult.getSuccess()) {
					List<PoorcityLedgerModel> models = modelListResult.getJsondata();
//					List<PoorcityLedgerModel> ledgerModels = getPoorcityLedgerModels(models);
					if (models != null) {
						//生成贫困人口统计表格
						getPinKunRen_Tab(column, models);
					}
				}
				break;

			case MyConstans.SEVEN:
				System.out.println("贫困人口统计页面请求筛选条件中的一级乡镇数据成功：===" + str);
				ZidianAppEntityListResult listResult = ZidianAppEntityListResult.parseToT(str, ZidianAppEntityListResult.class);
				if (listResult != null && listResult.getSuccess()) {
					List<ZidianAppEntity> zidianAppEntitys = listResult.getJsondata();
					if (zidianAppEntitys != null && zidianAppEntitys.size() > 0) {
						for (ZidianAppEntity zidianAppEntity : zidianAppEntitys) {
							mAppModel = zidianAppEntity.getTab();
//							List<T_SYS_DATADICTAppModel> mAppModels = zidianAppEntity.getTda();
							//贫困镇
							List<String> poor_zhen = new ArrayList<String>();
							for (int i = 0; i < mAppModel.size(); i++) {
								poor_zhen.add(mAppModel.get(i).getAd_nm());
							}
							if (poor_zhen != null) {
								mMenuRight_1.setStringArray(poor_zhen);
							}
						}
					}
				}
				break;

			case MyConstans.EIGHT:
				System.out.println("贫困人口统计页面请求筛选条件中的二级村数据成功：===" + str);
				ZidianAppEntityListResult modelListResult1 = ZidianAppEntityListResult.parseToT(str, ZidianAppEntityListResult.class);
				if (modelListResult1 != null && modelListResult1.getSuccess()) {
					List<ZidianAppEntity> mAppModels = modelListResult1.getJsondata();
					if(null!=mAppModels && mAppModels.size()>0){
						for (int i = 0; i < mAppModels.size(); i++) {
							if(models.size()>0){
								models.clear();
							}
							models.addAll(mAppModels.get(i).getTab());
							//贫困村
							poor_cun_list = new ArrayList<>();
							for (int j = 0;j < models.size(); j++) {
								PinKunCun pinKunCun=new PinKunCun();
								pinKunCun.setName(models.get(j).getAd_nm());
								pinKunCun.setPoor_type(models.get(j).getPovertystatus());
								poor_cun_list.add(pinKunCun);
							}
							if (poor_zhen != null) {
								mMenuRight_2.setStringArray(poor_cun_list);
//								ArrayList<String> mTextArray = new ArrayList<String>();
//								mTextArray.add(select_poor_zhen);
//								mTextArray.add("村");
//								//将贫困村显示文字初始化
//								expandTabView.setValue(mTextArray, mViewArray);
								expandTabView.initSecetText(1);
							}
						}
					}
				}
				break;

			default:
				break;
		}
	}
	List<Basic_DistrictAppModel> mAppModel;

	/**
	 * 生成贫困人口统计表格
	 *
	 * @param column       表格头
	 * @param ledgerModels 表格数据List
	 */
	private void getPinKunRen_Tab(String[] column, List<PoorcityLedgerModel> ledgerModels) {
		int childCount=layout.getChildCount();
		if(childCount>0){
			layout.removeAllViews();
		}
		for (int i = 0; i < ledgerModels.size() + 1; i++) { // 有多少列
			row = new TableRow(mContext);
			if (i == 0) {  // 第一列
				for (int j = 0; j < column.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
					row.addView(getLinearLayout(getTextView(column[j])));
				}
			} else if (i > 0 && i != ledgerModels.size()) { // 第二列 ~ 倒数第二列
				for (int j = 0; j < column.length; j++) {
					switch (j) {
						case 0: //行政区
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.addView(getLinearLayout(getTextView(ledgerModels.get(i - 1).getAdl_nm())));
							break;

						case 1: //贫困户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorhousenm()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 2: //贫困人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorpeoplenm()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 3: //已脱贫人口数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getYtp_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 4: //已脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getYtp_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 5: //未脱贫人口数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getWtp_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 6: //未脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getWtp_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 7: //返贫人口数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFp_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 8: //返贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFp_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 9: //2014年脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzof_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 10: //2014年脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzof_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 11: //2015年脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzoff_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 12: //2015年脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzoff_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 13: //2016年脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzox_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 14: //2016年脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzox_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 15: //2017年脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzos_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 16: //2017年脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzos_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 17: //因病致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_b()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 18: //因病致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_b()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 19: //因残致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_c()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 20: //因残致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_c()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 21: //因学致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_x()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 22: //因学致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_x()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 23: //缺技术致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_j()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 24: //缺技术致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_j()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 25: //缺资金致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_z()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;

						case 26: //缺资金致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_z()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 0));
							row.setGravity(Gravity.CENTER);
							break;
					}
				}
			} else {
				for (int j = 0; j < column.length; j++) {
					switch (j) {
						case 0: //行政区
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.addView(getLinearLayout(getTextView(ledgerModels.get(i - 1).getAdl_nm())));
							break;

						case 1: //贫困户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorhousenm()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 2: //贫困人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorpeoplenm()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 3: //已脱贫人口数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getYtp_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 4: //已脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getYtp_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 5: //未脱贫人口数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getWtp_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 6: //未脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getWtp_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 7: //返贫人口数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFp_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 8: //返贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFp_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 9: //2014已脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzof_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 10: //2014已脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzof_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 11: //2015已脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzoff_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 12: //2015已脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzoff_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 13: //2016已脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzox_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 14: //2016已脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzox_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 15: //2017已脱贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzos_p()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 16: //2017已脱贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getTzos_f()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 17: //因病致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_b()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 18: //因病致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_b()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 19: //因残致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_c()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 20: //因残致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_c()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 21: //因学致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_x()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 22: //因学致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_x()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 23: //缺技术致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_j()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 24: //缺技术致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_j()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 25: //缺资金致贫人数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getPoorreson_z()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;

						case 26: //缺资金致贫户数
							row.addView(getLinearLayout(getTextView(String.valueOf(ledgerModels.get(i - 1).getFamilyreson_z()))));
							row.setLayoutParams(getLayoutParams(2, 2, 2, 2));
							row.setGravity(Gravity.CENTER);
							break;
					}
				}
			}
			layout.addView(row);
		}
	}

	public final static int MP = ViewGroup.LayoutParams.MATCH_PARENT;

	/**
	 * 声明属性
	 *
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return 2016年8月10日
	 * ZhaoDongShao
	 */
	private ViewGroup.LayoutParams getLayoutParams(int left, int top, int right, int bottom) {
		TableLayout.LayoutParams lParams = new TableLayout.LayoutParams(MP, MP);
		lParams.setMargins(left, top, right, bottom);
		return lParams;
	}

	/**
	 * 创建线性布局
	 *
	 * @param tv
	 * @return 2016年8月10日
	 * ZhaoDongShao
	 */
	private LinearLayout getLinearLayout(TextView tv) {
		LinearLayout layout = new LinearLayout(mContext);
		layout.addView(tv);
		return layout;
	}

	/**
	 * 返回textview
	 *
	 * @param name
	 * @return 2016年8月10日
	 * ZhaoDongShao
	 */
	private TextView getTextView(String name) {
		TextView tView = new TextView(mContext);
		tView.setTextSize(15);
		tView.setText(name);
		tView.setBackgroundColor(Color.WHITE);
		tView.setGravity(Gravity.CENTER);
		tView.setPadding(10,5,10,5);

		int dp = Utils.dip2px(mContext, 30);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MP, dp);
		lp.setMargins(0, 0, 2, 0);
		tView.setLayoutParams(lp);
		return tView;
	}

	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		System.out.println("贫困人口统计请求服务器数据失败：===" + str);
		showToast(str);
	}

	/**
	 * @param models
	 * @return 2016年8月4日
	 * ZhaoDongShao
	 */
	private List<PoorcityLedgerModel> getPoorcityLedgerModels(List<PoorcityLedgerModel> models) {

		int populationnumber = 0;
		int poorhousenm = 0;
		int poorpeoplenm = 0;

		for (int i = 0; i < models.size(); i++) {
			populationnumber += models.get(i).getPopulationnumber();
			poorhousenm += models.get(i).getPoorhousenm();
			poorpeoplenm += models.get(i).getPoorpeoplenm();
		}

		PoorcityLedgerModel model = new PoorcityLedgerModel();
		model.setAdl_nm("合计");
		model.setPopulationnumber(populationnumber);
		model.setPoorhousenm(poorhousenm);
		model.setPoorpeoplenm(poorpeoplenm);
		models.add(model);
		return models;
	}
}
