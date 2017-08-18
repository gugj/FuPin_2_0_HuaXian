package com.roch.fupin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.roch.fupin.adapter.PoorHouseAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.AdlCode;
import com.roch.fupin.entity.Basic_DistrictAppModel;
import com.roch.fupin.entity.PoorFamilyBase;
import com.roch.fupin.entity.PoorFamilyListResult;
import com.roch.fupin.entity.T_SYS_DATADICTAppModel;
import com.roch.fupin.entity.User;
import com.roch.fupin.entity.ZidianAppEntity;
import com.roch.fupin.entity.ZidianAppEntityListResult;
import com.roch.fupin.utils.AdlcdUtil;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.LogUtil;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin.view.ExpandTabView;
import com.roch.fupin.view.MenuLeft;
import com.roch.fupin.view.MenuRight;
import com.roch.fupin.view.MenuRight.OnSelectListener;
import com.roch.fupin_2_0.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 贫困户页面
 * @author Administrator
 */
@ContentView(R.layout.activity_poorhouse)
public class PoorHouseActivity extends MainBaseActivity implements OnClickListener {

	/**
	 * 请求贫困户List列表时的当前页参数
	 */
	public static final String EXTS_PAGE = "page";

	/**
	 * 自定义的LinearLayout布局---筛选条件的容器
	 */
	@ViewInject(R.id.expandtab_view)
	ExpandTabView expandTabView;
	@ViewInject(R.id.refresh_poorhouse)
	PullToRefreshListView listview;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.tv_chaxunhushu)
	TextView tv_chaxunhushu;   //已查询到户数
	/**
	 * 高级搜索筛选按钮
	 */
	@ViewInject(R.id.tv_sousuo)
	TextView tv_sousuo;

	int current_page = 1; //当前页码

	String title_name,search_content = "";

	private static int FRIST = 0;
	
	PoorHouseAdapter adapter;
	/**
	 * 查询条件
	 */
	String adl_cd = "",zhipin_case = "",fupin_cs = "",sex = "",tpnr="";

	/**
	 * 自定义的RelativeLayout---进行乡、镇、村进行的筛选
	 */
	MenuLeft mMenuLeft;
	/**
	 * 自定义的RelativeLayout---进行帮扶措施的筛选
	 */
	MenuRight mMenuRight_1;
	/**
	 * 自定义的RelativeLayout---进行致贫原因的筛选
	 */
	MenuRight mMenuRight_2;
	/**
	 * 自定义的RelativeLayout---进行性别的筛选
	 */
	MenuRight mMenuRight;
	/**
	 * 存储自定义的筛选条件布局（即RelativeLayout）的集合
	 */
	List<View> mViewArray = new ArrayList<View>();

	int i = 0;

	/**
	 * 贫困村的集合
	 */
	List<Basic_DistrictAppModel> models = new ArrayList<Basic_DistrictAppModel>();

	/**
	 * 帮扶措施
	 */
	List<T_SYS_DATADICTAppModel> models_bfcs = new ArrayList<T_SYS_DATADICTAppModel>();
	/**
	 * 致贫原因
	 */
	List<T_SYS_DATADICTAppModel> models_zpyy = new ArrayList<T_SYS_DATADICTAppModel>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		//初始化Toobar
		initToolbar();
		//将自定义的LinearLayout布局---筛选条件的容器，显示出来
		expandTabView.setVisibility(View.VISIBLE);
		MyApplication.getInstance().addActivity(mActivity);

		Intent intent = getIntent();
		String title_name = intent.getStringExtra(Common.INTENT_KEY);
		if (title_name != null && !title_name.equals("")) {
			tv_title.setText(title_name);
		}
		
		//自定义的RelativeLayout---乡、镇、村进行筛选
		mMenuLeft = new MenuLeft(mContext);
		//自定义的RelativeLayout---帮扶措施筛选
		mMenuRight_1 = new MenuRight(mContext);
		//自定义的RelativeLayout---致贫原因筛选
		mMenuRight_2 = new MenuRight(mContext);
		//自定义的RelativeLayout---性别筛选
		mMenuRight = new MenuRight(mContext);
		
		//存储自定义的筛选条件布局（即RelativeLayout）的集合---添加筛选条件布局
		mViewArray.add(mMenuLeft);
//		mViewArray.add(mMenuRight_1);
		mViewArray.add(mMenuRight_2);
		mViewArray.add(mMenuRight);
		
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("行政区");
//		mTextArray.add("帮扶措施");
		mTextArray.add("脱贫情况"); // 由  致贫原因改为脱贫情况
		mTextArray.add("脱贫年份");
		expandTabView.setValue(mTextArray, mViewArray);

		String ad_cd = "";
		//获取当前定位信息
		//		AdlCode code = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mActivity);
		User user = MyApplication.getInstance().getLogin(Common.LoginName);
		if (user.getAdl_cd() != null && user.getAdl_cd() != null && !user.getAdl_cd().equals("")) {
			if (AdlcdUtil.isCountry(user.getAdl_cd()) || AdlcdUtil.isTown(user.getAdl_cd())) {
				ad_cd = user.getAdl_cd();
			}else if (AdlcdUtil.isVillage(user.getAdl_cd())) {
				ad_cd = AdlcdUtil.generateTownCode(user.getAdl_cd());
			}else {
				AdlCode adlCode = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mContext, Common.LoginName);
				ad_cd = adlCode.getAd_cd();
			}
		}
		
		// 请求服务器---乡镇数据，访问接口是：镇  http://101.200.190.254:9100/poverty/app/sys_user/getTownAdcd.do
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("ad_cd", ad_cd);
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.TOWN, rp, new MyRequestCallBack(this, MyConstans.SEVEN));
		System.out.println("贫困户页面一进来初始化时请求服务器中筛选条件的乡镇(flag=7)网址为：==" + URLs.TOWN + "?&ad_cd=" + ad_cd);

		//初始化筛选条件的数据源
//		initShaiXuanData();
		initDataTPSR();
		//访问服务器数据---贫困户List列表（使用基本查询条件，页数为=current_page）
		initData();
		//给ExtendListView即乡镇村、帮扶措施、致贫原因、性别菜单选项设置点击监听，点击后弹出筛选条件
		initListener();

		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				isXingZhengQuMode=true;
				isZPYYMode=true;
//				isSexMode=true;
				isTpnfMode=true;

				FRIST = 0;
				current_page = 1;
				flag_gaojichaxun=false;

				//下拉刷新时重新设置adl_cd和脱贫情况、贫困村属性
				SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
				//获取登陆用户的adl_cd
				adl_cd =sp.getString("adl_cd","");
				//重置行政区、脱贫情况、村属性的显示文字
				expandTabView.initSelectText3();
				//重置行政区的选中索引
				mMenuLeft.initSelectPosition(0);
				if(null!=zpyy && zpyy.size()>0){
					mMenuRight_2.setStringArray(zpyy);
				}else {
					//重置致贫原因的选中索引
					mMenuRight_2.setSelectPostion(0);
				}
				//重置性别的选中索引
//				mMenuRight.setSelectPostion(0);
				mMenuRight.setStringArray(selectors_tpsr);

				//重新赋值
				zhipin_case = ""; //致贫原因
//				sex = "不限";           //性别
				tpnr="";          //脱贫年份

				RequestParams rp = new RequestParams();
				rp.addBodyParameter("adl_cd", adl_cd);
//					rp=getRequestParams_GaoJiChaXun(rp);
				rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.POOR_HOUSE_LISE, rp,
						new MyRequestCallBack(PoorHouseActivity.this, MyConstans.FIRST));
				System.out.println("贫困户页面进行高级查询时请求服务器中的贫困户List(flag=1)的网址为：==" + URLs.POOR_HOUSE_LISE + "?&page=" + String.valueOf(page_gaojiechaxun));
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(flag_gaojichaxun){
					FRIST = 1;
					current_page++;
					RequestParams rp = getRequestParams();
					rp=getRequestParams_GaoJiChaXun(rp);
					rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
					MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
							URLs.POOR_HOUSE_LISE, rp,
							new MyRequestCallBack(PoorHouseActivity.this, MyConstans.FIRST));
					System.out.println("贫困户页面进行高级查询时请求服务器中的贫困户List(flag=1)的网址为：==" + URLs.POOR_HOUSE_LISE + "?&page=" + String.valueOf(page_gaojiechaxun));
				}else {
					FRIST = 1;
					current_page++;
					//上拉加载时请求贫困户List数据
					initData();
				}
			}
		});
	}

	//致贫原因
	List<String> zpyy = new ArrayList<String>();
	//脱贫情况
	List<String> tpqk = new ArrayList<String>();
	//帮扶措施
	List<String> bfcs = new ArrayList<String>();
	//性别
	List<String> sexs = new ArrayList<String>();
	/**
	 * 筛选条件的集合---脱贫、收入年份
	 */
	private List<String> selectors_tpsr = new ArrayList<String>();

	/**
	 * 初始化筛选条件的数据源
	 */
	private void initShaiXuanData() {
		sexs.add("不限");
		sexs.add("男");
		sexs.add("女");
	}
	private int year=2017;

	private void initDataTPSR() {
		for (int i = 0; i < 5; i++) {
			selectors_tpsr.add(String.valueOf(year--));
		}
	}

	/**
	 * 标志位---是否为筛选模式---默认为false
	 */
	private boolean isXingZhengQuMode=false;
	/**
	 * 标志位---是否为致贫原因模式---默认为false
	 */
	private boolean isZPYYMode=false;
	/**
	 * 标志位---是否为性别模式---默认为false
	 */
	private boolean isSexMode=false;
	/**
	 * 标志位---是否为脱贫年份模式---默认为false
	 */
	private boolean isTpnfMode=false;

	@Override
	protected void onNewIntent(Intent intent) {
		search_content = intent.getStringExtra(SearchManager.QUERY);
		initData();
	};
	
	/**
	 * 初始化Toobar
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
		tv_sousuo.setVisibility(View.VISIBLE);
		tv_sousuo.setOnClickListener(this);
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
	 * 返回查询条件
	 * @return
	 * 2016年7月1日
	 * ZhaoDongShao
	 */
	private RequestParams getRequestParams(){
		RequestParams rp = new RequestParams();
//		if (!village.equals("不限")&&!village.equals("")) {
//			rp.addBodyParameter("villagename", village);
//		}
		// 由 致贫原因改为---脱贫情况
		if (!zhipin_case.equals("不限")&&!zhipin_case.equals("")) {
			rp.addBodyParameter("povertystatusid", zhipin_case);
			LogUtil.println("脱贫情况查询条件选择状态为：==="+zhipin_case);
		}
//		if (!fupin_cs.equals("不限")&&!fupin_cs.equals("")) {
//			rp.addBodyParameter("helpplan", fupin_cs);
//		}
//		if (!sex.equals("不限")&&!sex.equals("")) {
//			if (sex.equals("男")) {
//				rp.addBodyParameter("sexName", "男");
//			}else if(sex.equals("女")){
//				rp.addBodyParameter("sexName", "女");
//			}
//		}
		if(!"".equals(tpnr) && !"不限".equals(tpnr)){
			rp.addBodyParameter("tp_year", tpnr);
		}
		if(StringUtil.isEmpty(houseYearShouRuNianFen)){ //默认加上年份
			rp.addBodyParameter("year", "2017");
		}else {
//			rp.addBodyParameter("year", houseYearShouRuNianFen);  //与高级查询重复---去掉不要
		}

		if (adl_cd.equals("")) {
//			adl_cd = getAdl_Cd();
			SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
			adl_cd =sp.getString("adl_cd","");
			System.out.println("adl_cd为空时获取后为=="+adl_cd);
		}
		if (!adl_cd.equals("")&&!adl_cd.equals("全部")) {
			System.out.println("adl_cd不为空时请求参数为==" + adl_cd);
			rp.addBodyParameter("adl_cd", adl_cd);
		}
		if (search_content != null) {
			if (!search_content.equals("")) {
				rp.addBodyParameter("personname", search_content);
			}
		}
		return rp;
	}

	/**
	 * 访问服务器数据---贫困户List列表（使用基本查询条件，页数为=current_page）
	 * <br/>2016年5月6日
	 * ZhaoDongShao
	 */
	private void initData() {
		RequestParams rp = getRequestParams();
		rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.POOR_HOUSE_LISE, rp,
				new MyRequestCallBack(this, MyConstans.FIRST));
		System.out.println("贫困户页面一进来初始化页面时请求服务器中的贫困户List(flag=1)的网址为：==" + URLs.POOR_HOUSE_LISE + "?&page=" + String.valueOf(current_page));
	}

	List<Basic_DistrictAppModel> list=new ArrayList<>();
	/**
	 * 给ExtendListView即乡镇村、帮扶措施、致贫原因、性别菜单选项设置点击监听，点击后弹出筛选条件
	 * 2016年11月2日
	 */
	private void initListener() {
		// 给乡镇村菜单设置点击监听
		mMenuLeft.setOnSelectListener(new MenuLeft.OnSelectListener() {
			@Override
			public void getValue(String showText) {
				current_page = 1;
				search_content = "";
				FRIST = 0;
				flag_gaojichaxun=false;
				isXingZhengQuMode=false;
				if(!"全部".equals(showText)){ //点击的不是全部
					onRefresh(mMenuLeft, showText);
				}else { //点击了全部
					expandTabView.onPressBack();
					// 获取其在储存菜单中的位置position
					int position = getPositon(mMenuLeft);
					if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
						expandTabView.setTitle(showText, position);
					}

					String village = mMenuLeft.getShowText();
					zhipin_case = mMenuRight_2.getShowText(); //致贫原因
//					fupin_cs = mMenuRight_1.getShowText();    //扶贫措施
//					sex = mMenuRight.getShowText();           //性别
					tpnr = mMenuRight.getShowText();           //脱贫年份
					for (Basic_DistrictAppModel appModel : models) {
						if (appModel.getAd_nm().equals(village) && !village.equals("")) {
							adl_cd = appModel.getAd_cd();
						}
					}

//					RequestParams rp = getRequestParams();
//					rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
//					MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
//							URLs.POOR_HOUSE_LISE, rp,
//							new MyRequestCallBack(PoorHouseActivity.this, MyConstans.FIRST));
//					System.out.println("贫困户页面一进来初始化页面时请求服务器中的贫困户List(flag=1)的网址为：==" + URLs.POOR_HOUSE_LISE + "?&page=" + String.valueOf(current_page));
					initData();
				}
			}

			@Override
			public void getArray(String id,int selectPosition) {
				System.out.println("点击乡镇的index为：=="+selectPosition);
				String village = mAppModel.get(selectPosition).getAd_nm();
				System.out.println("选择的乡镇是：==="+village);
				if("全部".equals(village)){
					expandTabView.onPressBack();
					// 获取其在储存菜单中的位置position
					int position = getPositon(mMenuLeft);
					if (position >= 0 && !expandTabView.getTitle(position).equals("全部")) {
						expandTabView.setTitle("全部", position);
					}

					String village1 = mMenuLeft.getShowText();
					zhipin_case = mMenuRight_2.getShowText(); //致贫原因
//					fupin_cs = mMenuRight_1.getShowText();    //扶贫措施
//					sex = mMenuRight.getShowText();           //性别
					tpnr = mMenuRight.getShowText();           //脱贫年份

					SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
					adl_cd =sp.getString("adl_cd","");
					System.out.println("点了全部获取getArray中获取的adl_cd====" + adl_cd);
					initData();
					mMenuLeft.setVillage(list);
				}else {
					RequestParams rp = new RequestParams();
					rp.addBodyParameter("ad_cd", id);
					MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
							URLs.VILLAGE, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.EIGHT));
					System.out.println("贫困户页面请求筛选条件中的二级List(flag=8)即村的网址为：==="+URLs.VILLAGE+"?&ad_cd="+id);
				}
//				RequestParams rp = new RequestParams();
//				rp.addBodyParameter("ad_cd", id);
//				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
//						URLs.VILLAGE, rp, new MyRequestCallBack((BaseActivity)mActivity, MyConstans.EIGHT));
//				System.out.println("贫困户页面请求筛选条件中的二级List(flag=8)即村的网址为：==="+URLs.VILLAGE+"?&ad_cd="+id);
			}
		});

		// 给帮扶措施菜单设置点击监听
//		mMenuRight_1.setOnSelectListener(new OnSelectListener() {
//			@Override
//			public void getValue(String distance, String showText) {
//				onRefresh(mMenuRight_1,showText);
//			}
//		});

		// 给致贫原因菜单设置点击监听
		mMenuRight_2.setOnSelectListener(new OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				current_page = 1;
				search_content = "";
				FRIST = 0;
				flag_gaojichaxun=false;
				isSexMode=false;
				isTpnfMode=false;
				isZPYYMode=false;
				//获取所有的基本筛选条件，再次请求服务器数据---贫困户List列表
				onRefresh(mMenuRight_2, showText);
			}
		});
		
		// 给性别菜单设置点击监听
		mMenuRight.setOnSelectListener(new OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				current_page = 1;
				search_content = "";
				FRIST = 0;
				flag_gaojichaxun=false;
				isSexMode=false;
				isTpnfMode=false;
				//获取所有的基本筛选条件，再次请求服务器数据---贫困户List列表
				onRefresh(mMenuRight,showText);
			}
		});
	}

	/**
	 * 获取所有的基本筛选条件，再次请求服务器数据---贫困户List列表
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

		String village = mMenuLeft.getShowText();
		if(!isZPYYMode){
			zhipin_case = mMenuRight_2.getShowText(); //致贫原因
		}
//		fupin_cs = mMenuRight_1.getShowText();    //扶贫措施
//		if(!isSexMode){
//			sex = mMenuRight.getShowText();           //性别
//		}
		if(!isTpnfMode){
			tpnr = mMenuRight.getShowText();           //脱贫年份
		}
		if(!isXingZhengQuMode){
			for (Basic_DistrictAppModel appModel : models) {
				if (appModel.getAd_nm().equals(village) && !village.equals("")) {
					adl_cd = appModel.getAd_cd();
				}
			}
		}

		RequestParams rp = getRequestParams();
		rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
		// 请求网络数据---贫困户List列表
		MyApplication.getInstance().getHttpUtilsInstance()
		.send(HttpMethod.POST, URLs.POOR_HOUSE_LISE, rp,
				new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
		System.out.println("筛选条件设置好以后请求服务器中的贫困户List(flag=1)网址为：==="+URLs.POOR_HOUSE_LISE);
	}

	/**
	 * 根据展示的popuwindow窗口，判断其在储存菜单的位置position
	 * @param tView  展示的popuwindow窗口
	 * @return       返回其在储存菜单的位置position
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
	 * 贫困户页面的条目点击事件，点击后进入贫困户详情页面的activity，并通过Bundle把贫困户对象PoorFamilyBase传过去，这个对象包括户ID等全部基本信息
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 * 2016年10月31日
	 */
	@OnItemClick(R.id.refresh_poorhouse)
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PoorFamilyBase poorHouse = (PoorFamilyBase)parent.getItemAtPosition(position);
		if (poorHouse != null) {
			Intent intent = new Intent(mContext, PoorHouseDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(Common.BUNDEL_KEY, poorHouse);
			intent.putExtra(Common.INTENT_KEY, bundle);
			startActivity(intent);
		}
	}

	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		listview.onRefreshComplete();
		switch (flag) {
			case MyConstans.FIRST:
				showToast(str);
				System.out.println("贫困户页面请求贫困户List数据失败：==="+str);
			break;

		case MyConstans.SEVEN:
			System.out.println("贫困户页面请求筛选条件中的一级乡、镇数据失败：==="+str);
			break;
				
		case MyConstans.EIGHT:
			System.out.println("贫困户页面请求筛选条件中的二级村数据失败：==="+str);
			break;
		}
	}

	List<PoorFamilyBase> lPoorHouses;
	/**
	 * 贫困乡镇的集合
	 */
	List<Basic_DistrictAppModel> mAppModel;
	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		PoorFamilyListResult mHoustListResult = null;
		switch (flag) {
		case MyConstans.FIRST:
			System.out.println("贫困户页面请求贫困户List数据成功：==="+str);
			mHoustListResult = PoorFamilyListResult.parseToT(str, PoorFamilyListResult.class);
			if (!StringUtil.isEmpty(mHoustListResult)) {
				if (mHoustListResult.getSuccess()) {
					lPoorHouses = mHoustListResult.getJsondata();
					tv_chaxunhushu.setText("已查询到"+mHoustListResult.getTotal()+"户建档立卡贫困户");
					if (!StringUtil.isEmpty(lPoorHouses)) {
						switch (FRIST) {
						case 0: //下拉刷新
							if (adapter != null) {
								adapter.onRefsh(lPoorHouses);
							}else {
								adapter = new PoorHouseAdapter(mContext, lPoorHouses);
								listview.setAdapter(adapter);
							}
							break;
							
						case 1: //上拉加载
							if (adapter == null) {
								adapter = new PoorHouseAdapter(mContext, lPoorHouses);
								listview.setAdapter(adapter);
							}else {
								adapter.addList(lPoorHouses);
							}
							break;
							
						default:
							break;
						}
					}else {
						showToast("没有更多数据");
						listview.onRefreshComplete();
					}
				}else {
					ShowNoticDialog();
				}
			}
			listview.onRefreshComplete();
			break;
			
		case MyConstans.SEVEN:
			System.out.println("贫困户页面请求筛选条件中的一级乡镇数据成功：==="+str);
			ZidianAppEntityListResult listResult = ZidianAppEntityListResult.parseToT(str, ZidianAppEntityListResult.class);
			if (listResult != null && listResult.getSuccess()) {
				List<ZidianAppEntity> zidianAppEntitys = listResult.getJsondata();
				if (zidianAppEntitys != null && zidianAppEntitys.size() > 0) {
					for (ZidianAppEntity zidianAppEntity : zidianAppEntitys) {
						mAppModel = zidianAppEntity.getTab();
						List<T_SYS_DATADICTAppModel> mAppModels = zidianAppEntity.getTda();
//						zpyy.add("不限"); //致贫原因
						bfcs.add("不限"); //帮扶措施
						if(null!=mAppModels){
							for (int i = 0; i < mAppModels.size(); i++) {
								if (mAppModels.get(i).getName().equals(Common.EXTS_BFCS)) {
									bfcs.add(mAppModels.get(i).getCode());
									models_bfcs.add(mAppModels.get(i)); //添加帮扶措施到list
								}else if (mAppModels.get(i).getName().equals(Common.EXTS_ZPYY)) {
//									zpyy.add(mAppModels.get(i).getCode());
									tpqk.add(mAppModels.get(i).getCode());
//									models_zpyy.add(mAppModels.get(i)); //致贫原因
								}
							}
						}
						zpyy.add("未脱贫");
						zpyy.add("已脱贫");
						zpyy.add("预脱贫");
						zpyy.add("返贫");
						zpyy.add("注销");
						//给乡镇、村筛选条件进行赋值
						if (mAppModel != null) {
							mMenuLeft.setStringArray(mAppModel);
						}
						//给帮扶措施筛选条件进行赋值---去掉不要了
						if (bfcs != null) {
							mMenuRight_1.setStringArray(bfcs);
						}
						//给致贫原因筛选条件进行赋值
						if (zpyy != null) {
							mMenuRight_2.setStringArray(zpyy);
						}
						//给性别筛选条件进行赋值
//						if (sexs != null) {
//							mMenuRight.setStringArray(sexs);
//						}
						//给脱贫年份筛选条件进行赋值
						if (selectors_tpsr != null) {
							mMenuRight.setStringArray(selectors_tpsr);
						}
					}
				}
			}
			break;
			
		case MyConstans.EIGHT:
			System.out.println("贫困户页面请求筛选条件中的二级村数据成功：==="+str);
			ZidianAppEntityListResult modelListResult = ZidianAppEntityListResult.parseToT(str, ZidianAppEntityListResult.class);
			if(null!=modelListResult){
				if (modelListResult.getSuccess()) {
					List<ZidianAppEntity> mAppModels = modelListResult.getJsondata();
					if (null!=mAppModels && mAppModels.size() > 0) {
						for (int i = 0; i < mAppModels.size(); i++) {
							mMenuLeft.setVillage(mAppModels.get(i).getTab());
							models.addAll(mAppModels.get(i).getTab());
						}
					}
				}
			}
			break;
			
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (expandTabView.isShowing()) {
				expandTabView.onPressBack();
			}else {
				MyApplication.getInstance().finishActivity(mActivity);	
			}
		}
		return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_sousuo:
//			showToast("点击了高级查询筛选按钮");
			Intent intent=new Intent(PoorHouseActivity.this,SearchActivity.class);
			intent.putExtra("tpqk", (Serializable) tpqk);
			startActivityForResult(intent, 1);
			break;

		default:
			break;
		}
	}

	/**
	 * 标志位---标识当前是否为高级查询模式
	 */
	private boolean flag_gaojichaxun=false;
	//高级查询条件
	String houseName="",housePhone="",houseCardID="",houseTPQK="",housePKUSX="",houseTouPinNianFen="",houseZaiXiaoSheng="",houseCanJiRen=""
			,houseJingShenBing="",houseZhiZhang="",house_cxjm_yiliao="",house_czzg_yl="",house_cxjm_yanglao="",houseDaBing="",houseYearShouRuNianFen=""
			,youwuzhaopian="",youwu_qingkuangshuoming="",xingBie="";

	/**
	 * 高级查询的页数---默认为1
	 */
	private int page_gaojiechaxun=1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
        	if(resultCode==2){
				current_page = 1;
				FRIST=0;
				flag_gaojichaxun=true;
				houseName=data.getStringExtra("houseName");
				housePhone=data.getStringExtra("housePhone");
				houseCardID=data.getStringExtra("houseCardID");
				houseTPQK=data.getStringExtra("houseTPQK");   //脱贫情况----未脱贫等
				housePKUSX=data.getStringExtra("housePKUSX");
//				houseTouPinNianFen=data.getStringExtra("houseTouPinNianFen");
				houseYearShouRuNianFen=data.getStringExtra("houseYearShouRuNianFen");
				houseZaiXiaoSheng=data.getStringExtra("houseZaiXiaoSheng");
				houseCanJiRen=data.getStringExtra("houseCanJiRen");
				houseJingShenBing=data.getStringExtra("houseJingShenBing");
				houseZhiZhang=data.getStringExtra("houseZhiZhang");
//        		String houseShiLian=data.getStringExtra("houseShiLian");
				house_cxjm_yiliao=data.getStringExtra("house_cxjm_yiliao");
//				house_czzg_yl=data.getStringExtra("house_czzg_yl");
//				house_cxjm_yanglao=data.getStringExtra("house_cxjm_yanglao");
				houseDaBing=data.getStringExtra("houseDaBing");
				youwuzhaopian=data.getStringExtra("youwuzhaopian");
				xingBie=data.getStringExtra("xingBie");
				youwu_qingkuangshuoming=data.getStringExtra("youwu_qingkuangshuoming");
//        		showToast("接收筛选后的数据为:"+houseName);
				RequestParams rp = getRequestParams();
        		rp=getRequestParams_GaoJiChaXun(rp);
				rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.POOR_HOUSE_LISE, rp,
						new MyRequestCallBack(this, MyConstans.FIRST));
				System.out.println("贫困户页面进行高级查询时请求服务器中的贫困户List(flag=1)的网址为：==" + URLs.POOR_HOUSE_LISE + "?&page=" + String.valueOf(1));
        	}
        }
    }

	private RequestParams getRequestParams_GaoJiChaXun(RequestParams rp) {
		rp.addBodyParameter("allPersonname", houseName);
		rp.addBodyParameter("allPhone", housePhone);
		rp.addBodyParameter("idno", houseCardID);
//		if(houseTPQK.equals("未脱贫")){
//			rp.addBodyParameter("povertystatusid", "01");
//		}else if(houseTPQK.equals("已脱贫")){
//			rp.addBodyParameter("povertystatusid", "02");
//		}else if(houseTPQK.equals("预脱贫")){
//			rp.addBodyParameter("povertystatusid", "03");
//		}else if(houseTPQK.equals("返贫")){
//			rp.addBodyParameter("povertystatusid", "04");
//		}else if(houseTPQK.equals("注销")){
//			rp.addBodyParameter("povertystatusid", "05");
//		}
//		rp.addBodyParameter("povertystatusid", houseTPQK);
		rp.addBodyParameter("poorreason", houseTPQK);

		rp.addBodyParameter("poorfamilyproperttest", housePKUSX);
//		rp.addBodyParameter("tp_year", houseTouPinNianFen);
		if(StringUtil.isEmpty(houseYearShouRuNianFen)){
//			rp.addBodyParameter("year", "2017");
		}else {
			rp.addBodyParameter("year", houseYearShouRuNianFen);
		}
		rp.addBodyParameter("zaixiao_info", houseZaiXiaoSheng);
		rp.addBodyParameter("canji_info", houseCanJiRen);
		rp.addBodyParameter("jingshen_info", houseJingShenBing);
		rp.addBodyParameter("zhizhang_info", houseZhiZhang);
//        		rp.addBodyParameter("houseShiLian", houseShiLian);
		rp.addBodyParameter("xnh_info", house_cxjm_yiliao);
//		rp.addBodyParameter("xnbx_info", house_czzg_yl);
//		rp.addBodyParameter("jbbx_info", house_cxjm_yanglao);
		rp.addBodyParameter("dbbx_info", houseDaBing);
		rp.addBodyParameter("sexName", xingBie);
		rp.addBodyParameter("ifhasphoto", youwuzhaopian); //有无照片----------------------
		rp.addBodyParameter("ifhasfamileinfo", youwu_qingkuangshuoming);  //有无情况说明------------
		System.out.println("高级查询中脱贫情况的选则情况是：======"+houseTPQK);
		return rp;
	}

}
