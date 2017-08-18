package com.roch.fupin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
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
import com.roch.fupin.entity.PeopleCaseDetailEntity;
import com.roch.fupin.entity.PinKunCun;
import com.roch.fupin.entity.PoorPeopleCaseListDetailResult;
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
 * 致贫原因详情
 * @author ZhaoDongShao
 * 2016年8月9日 
 */
@ContentView(R.layout.activity_poor_people_case_detail)
public class PoorPeopleCaseDetailActivity extends MainBaseActivity{

	public final static int MP = LayoutParams.MATCH_PARENT;
	public final static int WC = LayoutParams.WRAP_CONTENT;

	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tl)
	TableLayout layout;
	TableRow row;

	LX leixing;

	//致贫类型
	enum LX{
		bing,
		xue,
		can,
		jishu,
		money,
		no_laodongnengli,
		zishenfazhanli
	}

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
	String adl_cd_zhen;
	/**
	 * 当前选择的贫困村的adl_cd
	 */
	String adl_cd_cun;
	String select_poor_zhen="";

	List<Basic_DistrictAppModel> mAppModel;

	String case_detail="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		MyApplication.getInstance().addActivity(this);

		Intent intent = getIntent();
		String title = intent.getStringExtra(Common.INTENT_KEY);
		if (title.equals("") || title == null) {
			tv_title.setText("");
		}

		initToolbar();
		
		if (title.contains("因病")) { //111111111111
			leixing = LX.bing;
			tv_title.setText("因病致贫");
			initData("因病");
			case_detail="因病";
		}else if (title.contains("因学")) { //2222222222222222
			leixing = LX.xue;
			tv_title.setText("因学致贫");
			initData("因学");
			case_detail="因学";
		}else if (title.contains("缺技术")) { //3333333333333
			leixing = LX.jishu;
			tv_title.setText("缺技术致贫");
			initData("缺技术");
			case_detail="缺技术";
		}else if (title.contains("缺资金")) { //444444444444
			leixing = LX.money;
			tv_title.setText("缺资金致贫");
			initData("缺资金");
			case_detail="缺资金";
		}else if (title.contains("缺劳动能力")) { //5555555555555555
			leixing = LX.no_laodongnengli;
			tv_title.setText("缺劳动能力致贫");
			initData("缺劳动能力");
			case_detail="缺劳动能力";
		}else if (title.contains("因残")) { //66666666666666666
			leixing = LX.can;
			tv_title.setText("因残致贫");
			initData("因残");
			case_detail="因残";
		}else if (title.contains("自身发展动力不足")) { //77777777777777
			leixing = LX.zishenfazhanli;
			tv_title.setText("自身发展动力不足");
			initData("自身发展动力不足");
			case_detail="自身发展动力不足";
		}

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

		//给ExtendListView即贫困镇、贫困村菜单选项设置点击监听，点击后弹出筛选条件
		initListener();
	}

	/**
	 * 获取标题栏
	 * 2016年8月9日
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

	List<PinKunCun> poor_cun_list;
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

					initData(case_detail);
				}
			}
		});

		// 给贫困村菜单设置点击监听
		mMenuRight_2.setOnSelectListener(new MenuRight_2.OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				flag_zhen_cun=2;
				//点击贫困村时获取选中的贫困村的adl_cd
				onRefresh2(mMenuRight_2, showText);
				//点击贫困乡镇flag=1或贫困村flag=2时请求服务器的贫困人口统计数据
				getData();
			}
		});
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

	/**
	 * 点击贫困乡镇flag=1或贫困村flag=2时请求服务器的贫困人口统计数据
	 */
	private void getData() {
		RequestParams rp = new RequestParams();
		if(flag_zhen_cun==1){
			if(StringUtil.isNotEmpty(adl_cd_zhen)){
				rp.addBodyParameter("adl_cd",adl_cd_zhen);
				rp.addBodyParameter("pp_poorreason", case_detail);
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.CASE_DETAIL, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
				System.out.println("贫困人口统计请求服务器网址为：===" + URLs.CASE_DETAIL);
			}
		}else if(flag_zhen_cun==2){
			if(StringUtil.isNotEmpty(adl_cd_cun)){
				rp.addBodyParameter("adl_cd",adl_cd_cun);
				rp.addBodyParameter("pp_poorreason", case_detail);
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.CASE_DETAIL, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
				System.out.println("贫困人口统计请求服务器网址为：===" + URLs.CASE_DETAIL);
			}
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
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	private void initData(String pp_poorreason) {
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("pp_poorreason", pp_poorreason);
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, URLs.CASE_DETAIL,rp
				, new MyRequestCallBack(mActivity, MyConstans.FIRST));
		System.out.println(pp_poorreason + "致贫原因统计页面请求服务器详情网址为：===" + URLs.CASE_DETAIL + "&pp_poorreason=" + pp_poorreason);
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				System.out.println("致贫原因(因学除外)统计页面请求服务器详情成功：==="+str);
				PoorPeopleCaseListDetailResult caseListResult = PoorPeopleCaseListDetailResult.parseToT(str, PoorPeopleCaseListDetailResult.class);
				if (caseListResult != null && caseListResult.getSuccess()) {
					List<PeopleCaseDetailEntity> list = caseListResult.getJsondata();
					if (list != null && list.size() > 0) {
						//先清空之前请求的数据表格
						int childCount=layout.getChildCount();
						if(childCount>0){
							layout.removeAllViews();
						}
						generateData(list);
						showToast("数据加载完成");
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
		}
	}
	
	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		System.out.println("致贫原因(因学除外)统计页面请求服务器详情失败：==="+str);
		showToast(str);
	}

	/**
	 * @param lEntities
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	private void generateData(List<PeopleCaseDetailEntity> lEntities) {
//		String[] case_can = {"行政区","户数","人数","有证人数","无证人数"};//因残
		String[] case_can = {"行政区","户数","人数"};//因残
		String[] case_xue = {"行政区","户数","人数","幼儿园","高中生","职专","大学生"};//因学
		String[] case_bing_laodong_money_jishu = {"行政区","户数","人数"};//因病
		switch (leixing) {
		case bing:
			//因病
			getBing(case_bing_laodong_money_jishu, lEntities);
			break;
			
		case xue: //因学不用这个表格了
			getXue(case_xue, lEntities);
			break;
			
		case can:
			//因残
			getCan(case_can, lEntities);
			break;
			
		case no_laodongnengli:
			//缺劳动能力
			getNengli(case_bing_laodong_money_jishu, lEntities);
			break;
			
		case money:
			//缺资金
			getMoney(case_bing_laodong_money_jishu, lEntities);
			break;
			
		case jishu:
			//缺技术
			getJishu(case_bing_laodong_money_jishu, lEntities);
			break;

		case zishenfazhanli:
			//自身发展动力不足
			getZiShenFaZhanLi(case_bing_laodong_money_jishu, lEntities);
			break;

		default:
			break;
		}
	}

	/**
	 * 自身发展动力不足
	 * @param case_bing_laodong_money_jishu
	 * @param lEntities
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	@SuppressLint("InflateParams")
	private void getZiShenFaZhanLi(String[] case_bing_laodong_money_jishu, List<PeopleCaseDetailEntity> lEntities) {
		for (int i = 0; i < lEntities.size() + 1; i++) {
			row = new TableRow(mContext);
			if (i == 0) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(1,1,1,0));
					row.addView(getLinearLayout(getTextView(case_bing_laodong_money_jishu[j])));
				}
			}else if (i > 0 && i != lEntities.size()) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
						case 0:
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(1,1,1,0));
							row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
							break;

						case 1:
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(1,1,1,0));
							row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_m_fc()))));
							break;

						case 2:
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(1,1,1,0));
							row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_m_pc()))));
							break;

						default:
							break;
					}
				}
			}else {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
						case 0:
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(1,1,1,1));
							row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
							break;

						case 1:
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(1,1,1,1));
							row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_m_fc()))));
							break;

						case 2:
							row.setGravity(Gravity.CENTER);
							row.setLayoutParams(getLayoutParams(1,1,1,1));
							row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_m_pc()))));
							break;

						default:
							break;
					}
				}
			}
			layout.addView(row);
		}
	}

	/**
	 * 缺技术
	 * @param case_bing_laodong_money_jishu
	 * @param lEntities
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	@SuppressLint("InflateParams")
	private void getJishu(String[] case_bing_laodong_money_jishu, List<PeopleCaseDetailEntity> lEntities) {
		for (int i = 0; i < lEntities.size() + 1; i++) {
			row = new TableRow(mContext);
			if (i == 0) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(1,1,1,0));
					row.addView(getLinearLayout(getTextView(case_bing_laodong_money_jishu[j])));
				}
			}else if (i > 0 && i != lEntities.size()) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_j_fc()))));
						break;
						
					case 2:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_j_pc()))));
						break;
						
					default:
						break;
					}
				}
			}else {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_j_fc()))));
						break;
						
					case 2:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_j_pc()))));
						break;
						
					default:
						break;
					}
				}
			}
			layout.addView(row);
		}
	}

	/**
	 * 缺资金
	 * @param case_bing_laodong_money_jishu
	 * @param lEntities
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	@SuppressLint("InflateParams")
	private void getMoney(String[] case_bing_laodong_money_jishu, List<PeopleCaseDetailEntity> lEntities) {
		for (int i = 0; i < lEntities.size() + 1; i++) {
			row = new TableRow(mContext);
			if (i == 0) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(1,1,1,0));
					row.addView(getLinearLayout(getTextView(case_bing_laodong_money_jishu[j])));
				}
			}else if (i > 0 && i != lEntities.size()) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(lEntities.get(i -1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1 ).getPoorreson_z_fc()))));
						break;
						
					case 2:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_z_pc()))));
						break;
						
					default:
						break;
					}
				}
			}else {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(lEntities.get(i -1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_z_fc()))));
						break;
						
					case 2:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_z_pc()))));
						break;
						
					default:
						break;
					}
				}
			}	
			layout.addView(row);
		}
	}

	/**
	 * 缺劳动能力
	 * @param case_bing_laodong_money_jishu
	 * @param lEntities
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	@SuppressLint("InflateParams")
	private void getNengli(String[] case_bing_laodong_money_jishu, List<PeopleCaseDetailEntity> lEntities) {
		for (int i = 0; i < lEntities.size() + 1; i++) {
			row = new TableRow(mContext);
			if (i == 0) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(1,1,1,0));
					row.addView(getLinearLayout(getTextView(case_bing_laodong_money_jishu[j])));
				}
			}else if (i > 0 && i != lEntities.size()) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_l_fc()))));
						break;
						
					case 2:	
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_l_pc()))));
						break;
						
					default:
						break;
					}
				}
			}else {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:	
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(lEntities.get(i -1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_l_fc()))));
						break;
						
					case 2:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_l_pc()))));
						break;
						
					default:
						break;
					}
				}
			}	
			layout.addView(row);
		}
	}

	/**
	 * 因学
	 * @param case_xue
	 * @param lEntities
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	@SuppressLint("InflateParams")
	private void getXue(String[] case_xue, List<PeopleCaseDetailEntity> lEntities) {
		for (int i = 0; i < lEntities.size() + 1; i++) {
			row = new TableRow(mContext);
			if (i == 0) {
				for (int j = 0; j < case_xue.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(1,1,1,0));
					row.addView(getLinearLayout(getTextView(case_xue[j])));
				}
			}else if (i > 0 && i!=lEntities.size()) {
				for (int j = 0; j < case_xue.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(lEntities.get(i -1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_fc()))));
						break;
						
					case 2:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc()))));
						break;
						
					case 3:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_yey()))));
						break;
						
					case 4:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_xxs()))));				
						break;
						
					case 5:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_czs()))));
						break;
						
					case 6:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_gzs()))));
						break;
						
					case 7:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_zz()))));
						break;
						
					case 8:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_dx()))));
						break;
						
					default:
						break;
					}
				}
			}else {
				for (int j = 0; j < case_xue.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(lEntities.get(i -1).getAdl_nm())));
						break;
						
					case 1:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_fc()))));
						break;
						
					case 2:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc()))));
						break;
						
					case 3:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_yey()))));
						break;
						
					case 4:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_xxs()))));
						break;
						
					case 5:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_czs()))));
						break;
						
					case 6:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_gzs()))));
						break;
						
					case 7:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_zz()))));
						break;
						
					case 8:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_x_pc_dx()))));
						break;
						
					default:
						break;
					}
				}
			}
			layout.addView(row);
		}
	}

	/**
	 * 因病
	 * @param case_bing_laodong_money_jishu
	 * @param lEntities
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	@SuppressLint("InflateParams")
	private void getBing(String[] case_bing_laodong_money_jishu, List<PeopleCaseDetailEntity> lEntities) {
		for (int i = 0; i < lEntities.size() + 1; i++) {
			row = new TableRow(mContext);
			if (i == 0) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(1,1,1,0));
					row.addView(getLinearLayout(getTextView(case_bing_laodong_money_jishu[j])));
				}
			}else if (i > 0 && i!=lEntities.size()) {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
						break;
						
					case 1:
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_b_fc()))));
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.setGravity(Gravity.CENTER);
						break;
						
					case 2:
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_b_pc()))));
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.setGravity(Gravity.CENTER);
						break;
						
					default:
						break;
					}
				}
			}else {
				for (int j = 0; j < case_bing_laodong_money_jishu.length; j++) {
					switch (j) {
					case 0:
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
						break;
						
					case 1:
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_b_fc()))));
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.setGravity(Gravity.CENTER);
						break;
						
					case 2:
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_b_pc()))));
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.setGravity(Gravity.CENTER);
						break;
						
					default:
						break;
					}
				}
			}	
			layout.addView(row);
		}
	}

	/**
	 * 因残
	 * @param  case_can
	 * 2016年8月9日
	 * ZhaoDongShao
	 * @param lEntities 
	 */
	@SuppressLint("InflateParams")
	private void getCan(String[] case_can, List<PeopleCaseDetailEntity> lEntities) {
		for (int i = 0; i < lEntities.size() + 1; i++) {
			row = new TableRow(mContext);
			if (i == 0) { //第一行---表头
				for (int j = 0; j < case_can.length; j++) {
					row.setGravity(Gravity.CENTER);
					row.setLayoutParams(getLayoutParams(1,1,1,0));
					row.addView(getLinearLayout(getTextView(case_can[j])));
				}
			}else if (i > 0 && i != lEntities.size()) { //第一行~倒数第二行
				for (int j = 0; j < case_can.length; j++) {
					switch (j) {
					case 0: //行政区
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
						break;
						
					case 1: //因残致贫户数
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_fc()))));
						break;
						
					case 2: //因残致贫人数
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,0));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_pc()))));
						break;
						
//					case 3: //因残致贫有证人数----去掉不要
//						row.setGravity(Gravity.CENTER);
//						row.setLayoutParams(getLayoutParams(1,1,1,0));
//						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_yz_pc()))));
//						break;
//
//					case 4: //因残致贫无证人数---去掉不要
//						row.setGravity(Gravity.CENTER);
//						row.setLayoutParams(getLayoutParams(1,1,1,0));
//						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_wz_pc()))));
//						break;
						
					default:
						break;
					}
				}
			}else { //最后一行
				for (int j = 0; j < case_can.length; j++) {
					switch (j) {
					case 0: //行政区
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(lEntities.get(i - 1).getAdl_nm())));
						break;
						
					case 1: //因残致贫户数
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_fc()))));
						break;
						
					case 2: //因残致贫人数
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_pc()))));
						break;
						
					case 3: //因残致贫有证人数----去掉不要
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_yz_pc()))));
						break;
						
					case 4:	 //因残致贫无证人数---去掉不要
						row.setGravity(Gravity.CENTER);
						row.setLayoutParams(getLayoutParams(1,1,1,1));
						row.addView(getLinearLayout(getTextView(String.valueOf(lEntities.get(i - 1).getPoorreson_c_wz_pc()))));
						break;
						
					default:
						break;
					}
				}
			}
			layout.addView(row);
		}
	}

	/**
	 * 返回textview
	 * @param name
	 * @return
	 * 2016年8月10日
	 * ZhaoDongShao
	 */
	private TextView getTextView(String name){
		TextView tView = new TextView(mContext);
		tView.setTextSize(15);
		tView.setTextSize(15);
		tView.setText(name);
		tView.setBackgroundColor(Color.WHITE);
		tView.setGravity(Gravity.CENTER);
		
		int dp = Utils.dip2px(mContext, 30);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MP, dp);  
		lp.setMargins(0, 0, 1, 0);  
		tView.setLayoutParams(lp);
		return tView;
	}

	/**
	 * 创建线性布局
	 * @param tv
	 * @return
	 * 2016年8月10日
	 * ZhaoDongShao
	 */
	private LinearLayout getLinearLayout(TextView tv){
		LinearLayout layout = new LinearLayout(mContext);
		layout.addView(tv);
		return layout;
	}

	/**
	 * 声明属性
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 * 2016年8月10日
	 * ZhaoDongShao
	 */
	private LayoutParams getLayoutParams(int left, int top, int right, int bottom){
		TableLayout.LayoutParams lParams = new TableLayout.LayoutParams(MP,MP);
		lParams.setMargins(left, top, right, bottom);
		return lParams;
	}
	
	 @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果点击的是后退键  首先判断webView是否能够后退
            //如果点击的是后退键  首先判断webView是否能够后退   返回值是boolean类型的
        	finish();
        	return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
}
