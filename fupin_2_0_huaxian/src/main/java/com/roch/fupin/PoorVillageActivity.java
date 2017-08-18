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
import com.roch.fupin.adapter.PoorVillageAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.AdlCode;
import com.roch.fupin.entity.Basic_DistrictAppModel;
import com.roch.fupin.entity.PoorVillageBase;
import com.roch.fupin.entity.PoorVillageListResult;
import com.roch.fupin.entity.User;
import com.roch.fupin.entity.ZidianAppEntity;
import com.roch.fupin.entity.ZidianAppEntityListResult;
import com.roch.fupin.utils.AdlcdUtil;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.LogUtil;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin.view.ExpandTabView;
import com.roch.fupin.view.MenuLeft;
import com.roch.fupin.view.MenuRight;
import com.roch.fupin_2_0.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 贫困村列表页面
 * @author Administrator
 */
@ContentView(R.layout.activity_poorhouse)
public class PoorVillageActivity extends MainBaseActivity implements View.OnClickListener {

	private static final String EXTS_PAGE = "page";

	@ViewInject(R.id.refresh_poorhouse)
	PullToRefreshListView listview;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.tv_chaxunhushu)
	TextView tv_chaxunhushu;
	//	@ViewInject(R.id.tv_search)
	//	TextView tv_search;
	@ViewInject(R.id.expandtab_view)
	ExpandTabView expandTabView;

	String adl_cd = "";String tpqk = ""; String pkhs = "";//村、脱贫情况、贫困户数
	/**
	 * 贫困村属性
	 */
	String pccsx="";
	
	int current_page = 1; //当前页码

	String title_name,search_content = "";

	private static int FRIST = 0;

	/**
	 * 筛选条件---乡镇、村
	 */
	MenuLeft mMenuLeft;
	/**
	 * 筛选条件---脱贫情况
	 */
	MenuRight mMenuRight_1;
	/**
	 * 筛选条件---贫困户数
	 */
	MenuRight mMenuRight_2;
	/**
	 * 筛选条件---贫困村属性
	 */
	MenuRight mMenuRight_3;
	//存储菜单
	List<View> mViewArray = new ArrayList<View>();

	PoorVillageAdapter adapter;
	//村信息
	List<Basic_DistrictAppModel> models = new ArrayList<Basic_DistrictAppModel>();
	/**
	 * 高级搜索筛选按钮
	 */
	@ViewInject(R.id.tv_sousuo)
	TextView tv_sousuo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initToolbar();
		MyApplication.getInstance().addActivity(mActivity);
		
		expandTabView.setVisibility(View.VISIBLE);
		
		Intent intent = getIntent();
		String title_name = intent.getStringExtra(Common.INTENT_KEY);
		if (title_name != null && !title_name.equals("")) {
			tv_title.setText(title_name);
		}
		
		//添加查询条件
		mMenuLeft = new MenuLeft(mContext);
		mMenuRight_1 = new MenuRight(mContext);
		mMenuRight_2 = new MenuRight(mContext);
		mMenuRight_3 = new MenuRight(mContext);
		mViewArray.add(mMenuLeft);
		mViewArray.add(mMenuRight_1);
//		mViewArray.add(mMenuRight_2);
		mViewArray.add(mMenuRight_3);

		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("行政区");
		mTextArray.add("脱贫情况");
//		mTextArray.add("贫困户数");
		mTextArray.add("村属性");
		expandTabView.setValue(mTextArray, mViewArray);

		String ad_cd = "";
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
		//请求贫困村中的镇数据
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("ad_cd", ad_cd);
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.TOWN, rp, new MyRequestCallBack(this, MyConstans.SEVEN));

		//初始化筛选条件的数据源
		initShaiXuanData();
		//请求贫困村List列表数据
		initData();
		//初始化查询监听
		initListener();

		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				isXingZhengQuMode=true;
				isTPQKQuMode=true;
				isPCCSXMode=true;

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
//				if(null!=mAppModel){
//					mMenuLeft.setStringArray(mAppModel);
//					mMenuLeft.clearErJiListView();
//				}
				//重置脱贫情况的选中索引
//				mMenuRight_1.setSelectPostion(0);
				mMenuRight_1.setStringArray(tpqks);
				//重置村属性的选中索引
//				mMenuRight_3.setSelectPostion(0);
				mMenuRight_3.setStringArray(pccsxs);

				//重新给脱贫情况赋值
				tpqk = "不限";//脱贫情况
				//重新给村属性赋值
				pccsx = "不限";//贫困村属性
				LogUtil.println("下拉刷新过之后的adl_cd=="+adl_cd+",脱贫情况=="+tpqk+",村属性=="+pccsx);
				//下拉刷新过之后的adl_cd==410526000000,脱贫情况==未脱贫,村属性==贫困村

				RequestParams rp = new RequestParams();
				rp.addBodyParameter("adl_cd", adl_cd);
				rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.POOR_VILLAGE_LIST, rp,
						new MyRequestCallBack(PoorVillageActivity.this, MyConstans.FIRST));
				System.out.println("贫困村页面进行高级查询时请求服务器中的贫困村List(flag=1)的网址为：==" + URLs.POOR_VILLAGE_LIST + "?&page=" + String.valueOf(page_gaojiechaxun));
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(flag_gaojichaxun){ //如果当前开启了高级查询
					FRIST = 1;
					current_page++;
					RequestParams rp = getRequestParams();
					rp=getRequestParams_GaoJiChaXun(rp);
					rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
					MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
							URLs.POOR_VILLAGE_LIST, rp,
							new MyRequestCallBack(PoorVillageActivity.this, MyConstans.FIRST));
					System.out.println("贫困户页面进行高级查询时请求服务器中的贫困户List(flag=1)的网址为：==" + URLs.POOR_VILLAGE_LIST + "?&page=" + String.valueOf(page_gaojiechaxun));
				}else {
					FRIST = 1;
					current_page++;
					//上拉加载时请求贫困户List数据
					initData();
				}
			}
		});
	}

	//脱贫情况
	List<String> tpqks = new ArrayList<String>();
	//贫困户数
	List<String> pkhss = new ArrayList<String>();
	//贫困村属性
	List<String> pccsxs=new ArrayList<>();
	/**
	 * 初始化筛选条件的数据源
	 */
	private void initShaiXuanData() {
		String[] tpqk = ResourceUtil.getInstance().getStringArrayById(R.array.no_poor_state);
		tpqks.add("不限");
		for (int i = 0; i < tpqk.length; i++) {
			tpqks.add(tpqk[i]);
		}

		String[] pkhs = ResourceUtil.getInstance().getStringArrayById(R.array.no_poor_num);
		pkhss.add("不限");
		for (int i = 0; i < pkhs.length; i++) {
			pkhss.add(pkhs[i]);
		}

		String[] pccsx = ResourceUtil.getInstance().getStringArrayById(R.array.pincuncun_shuxing);
		pccsxs.add("不限");
		for (int i = 0; i < pccsx.length; i++) {
			pccsxs.add(pccsx[i]);
		}
	}

	/**
	 * 标志位---是否为筛选模式---默认为false
	 */
	private boolean isXingZhengQuMode=false;
	/**
	 * 标志位---是否脱贫情况模式---默认为false
	 */
	private boolean isTPQKQuMode=false;
	/**
	 * 标志位---是否为贫困村属性模式---默认为false
	 */
	private boolean isPCCSXMode=false;

	/**
	 * 高级查询的页数---默认为1
	 */
	private int page_gaojiechaxun=1;
	@Override
	protected void onNewIntent(Intent intent) {
		search_content = intent.getStringExtra(SearchManager.QUERY);
		initData();
	};
	
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
		tv_sousuo.setVisibility(View.VISIBLE);
		tv_sousuo.setOnClickListener(this);
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
	 * 请求贫困村List列表数据
	 * 2016年5月6日
	 * ZhaoDongShao
	 */
	private void initData() {
		RequestParams rp = getRequestParams();
		rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.POOR_VILLAGE_LIST, rp,
				new MyRequestCallBack(this, MyConstans.FIRST));
		System.out.println("贫困村请求服务器中贫困村List列表的网址为：==="+URLs.POOR_VILLAGE_LIST+"&page="+String.valueOf(current_page));
	}

	/**
	 * 获取查询条件
	 * @return
	 * 2016年7月1日
	 * ZhaoDongShao
	 */
	private RequestParams getRequestParams() {
		RequestParams rp = new RequestParams();
//		if (!village.equals("")&&!village.equals("不限")) {
//			rp.addBodyParameter("villagename", village);
//		}
//		if (!pkhs.equals("不限")&&!pkhs.equals("")) {
//			if (pkhs != null && !pkhs.equals("")) {
//				String[] a = pkhs.split("~");
//				rp.addBodyParameter("littlepoorhouse", a[0]);
//				if (!a[0].equals("100")) {
//					rp.addBodyParameter("bigpoorhouse", a[1]);
//				}
//			}
//		}
		if (!tpqk.equals("不限")&&!tpqk.equals("")) {
			rp.addBodyParameter("tpName", tpqk);
		}
		if(!pccsx.equals("不限")&&!pccsx.equals("")){
			rp.addBodyParameter("pkcsxname",pccsx);
		}
		if (adl_cd.equals("")) {
//			adl_cd = getAdl_Cd();
			SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
			adl_cd =sp.getString("adl_cd","");
			System.out.println("adl_cd为空时获取后为=="+adl_cd);
		}
		if (!adl_cd.equals("")) {
			rp.addBodyParameter("adl_cd", adl_cd);
			System.out.println("adl_cd不为空时请求参数为==" + adl_cd);
		}
		return rp;
	}

	@OnItemClick(R.id.refresh_poorhouse)
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PoorVillageBase poorHouse = (PoorVillageBase)parent.getItemAtPosition(position);
		if (poorHouse != null) {
			Intent intent = new Intent(mContext, PoorVillageDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(Common.BUNDEL_KEY, poorHouse);
			intent.putExtra(Common.INTENT_KEY, bundle);
			startActivity(intent);
		}
	}

	List<Basic_DistrictAppModel> list=new ArrayList<>();
	private void initListener() {
		//筛选条件---乡镇、村
		mMenuLeft.setOnSelectListener(new MenuLeft.OnSelectListener() {
			@Override
			public void getValue(String showText) {
				current_page = 1;
				search_content = "";
				FRIST = 0;
				flag_gaojichaxun=false;
				isXingZhengQuMode=false;
				if(!"全部".equals(showText)){
					onRefresh(mMenuLeft, showText);
				}else {
					expandTabView.onPressBack();
					int position = getPositon(mMenuLeft);
					if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
						expandTabView.setTitle(showText, position);
					}

					String village = mMenuLeft.getShowText();
					tpqk = mMenuRight_1.getShowText();//脱贫情况
//					pkhs = mMenuRight_2.getShowText();//贫困户数
					pccsx = mMenuRight_3.getShowText();//贫困村属性
					for (Basic_DistrictAppModel appModel : models) {
						if (appModel.getAd_nm().equals(village) && !village.equals("")) {
							adl_cd = appModel.getAd_cd();
						}
					}
					initData();
				}
			}

			@Override
			public void getArray(String id,int selectPosition) {
				System.out.println("点击乡镇的index为：==" + selectPosition);
				String village = mAppModel.get(selectPosition).getAd_nm();
				System.out.println("选择的乡镇是：===" + village);
				if("全部".equals(village)){
					expandTabView.onPressBack();
					// 获取其在储存菜单中的位置position
					int position = getPositon(mMenuLeft);
					if (position >= 0 && !expandTabView.getTitle(position).equals("全部")) {
						expandTabView.setTitle("全部", position);
					}

					String village1 = mMenuLeft.getShowText();
					tpqk = mMenuRight_1.getShowText();//脱贫情况
//					pkhs = mMenuRight_2.getShowText();//贫困户数
					pccsx = mMenuRight_3.getShowText();//贫困村属性
//					for (Basic_DistrictAppModel appModel : mAppModel) {
//						if (appModel.getAd_nm().equals(village1) && !village1.equals("")) {
//							adl_cd = appModel.getAd_cd();
//						}
//					}
					SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
					adl_cd =sp.getString("adl_cd","");
					System.out.println("点了全部获取getArray中获取的adl_cd====" + adl_cd);

					initData();
					mMenuLeft.setVillage(list);
				}else {
					RequestParams rp = new RequestParams();
					rp.addBodyParameter("ad_cd", id);
					MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
							URLs.VILLAGE, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.EIGHT));
				}
			}
		});
		//筛选条件---脱贫情况
		mMenuRight_1.setOnSelectListener(new MenuRight.OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				current_page = 1;
				search_content = "";
				FRIST = 0;
				flag_gaojichaxun=false;
				isTPQKQuMode=false;
				onRefresh(mMenuRight_1, showText);
			}
		});
		//筛选条件---贫困户数
//		mMenuRight_2.setOnSelectListener(new MenuRight.OnSelectListener() {
//			@Override
//			public void getValue(String distance, String showText) {
//				current_page = 1;
//				search_content = "";
//				FRIST = 0;
//				flag_gaojichaxun=false;
//				onRefresh(mMenuRight_2, showText);
//			}
//		});
		//筛选条件---贫困村属性
		mMenuRight_3.setOnSelectListener(new MenuRight.OnSelectListener() {
			@Override
			public void getValue(String distance, String showText) {
				current_page = 1;
				search_content = "";
				FRIST = 0;
				flag_gaojichaxun = false;
				isPCCSXMode = false;
				onRefresh(mMenuRight_3, showText);
			}
		});
	}

	private void onRefresh(View view, String showText) {
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}

		String village = mMenuLeft.getShowText();
		if(!isTPQKQuMode){
			tpqk = mMenuRight_1.getShowText();//脱贫情况
		}
//		pkhs = mMenuRight_2.getShowText();//贫困户数
		if(!isPCCSXMode){
			pccsx = mMenuRight_3.getShowText();//贫困村属性
		}
		if(!isXingZhengQuMode){
			for (Basic_DistrictAppModel appModel : models) {
				if (appModel.getAd_nm().equals(village) && !village.equals("")) {
					adl_cd = appModel.getAd_cd();
				}
			}
		}

		LogUtil.println("下拉刷新过之后选中筛选条件再次请求服务器的adl_cd=="+adl_cd+",脱贫情况=="+tpqk+",村属性=="+pccsx);
		//下拉刷新过之后选中帅选条件再次请求服务器的adl_cd==410526106000,脱贫情况==未脱贫,村属性==非贫困村

		RequestParams rp = getRequestParams();
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, 
				URLs.POOR_VILLAGE_LIST, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
		System.out.println("点击贫困村属性查询后网址为：==="+URLs.POOR_VILLAGE_LIST+"&?pkcsxname ="+pccsx);
	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	List<Basic_DistrictAppModel> mAppModel;
	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		PoorVillageListResult mVillageListResult = null;
		switch (flag) {
		case MyConstans.FIRST:
			System.out.println("贫困村页面请求服务器List列表数据成功：==="+str);
			mVillageListResult = PoorVillageListResult.parseToT(str, PoorVillageListResult.class);
			if (!StringUtil.isEmpty(mVillageListResult)) {
				if (mVillageListResult.getSuccess()) {
					List<PoorVillageBase> lPoorHouses = mVillageListResult.getJsondata();
					tv_chaxunhushu.setText("已查询到"+mVillageListResult.getTotal()+"个行政村");
					if (!StringUtil.isEmpty(lPoorHouses)) {
						switch (FRIST) {
						case 0:
							if (adapter != null) {
								adapter.onRefsh(lPoorHouses);
							}else {
								adapter = new PoorVillageAdapter(mContext, lPoorHouses);
								listview.setAdapter(adapter);
							}
							break;
							
						case 1:
							if (adapter == null) {
								adapter = new PoorVillageAdapter(mContext, lPoorHouses);
								listview.setAdapter(adapter);
							}else {
								adapter.addList(lPoorHouses);
							}
							break;
							
						default:
							break;
						}
					}else {
						showToast("当前没有更多数据");
						listview.onRefreshComplete();
					}
				}
				else {
					ShowNoticDialog();
				}
			}
			listview.onRefreshComplete();
			break;
			
		case MyConstans.SEVEN: //请求贫困村页面中的---乡镇筛选条件
			ZidianAppEntityListResult listResult = ZidianAppEntityListResult.parseToT(str, ZidianAppEntityListResult.class);
			if (listResult != null && listResult.getSuccess()) {
				List<ZidianAppEntity> zidianAppEntitys = listResult.getJsondata();
				if (zidianAppEntitys != null && zidianAppEntitys.size() > 0) {
					for (ZidianAppEntity zidianAppEntity : zidianAppEntitys) {
						mAppModel = zidianAppEntity.getTab();
						mMenuLeft.setStringArray(mAppModel);
					}

					if (tpqks != null) {
						mMenuRight_1.setStringArray(tpqks);
					}

					mMenuRight_3.setStringArray(pccsxs);

					//贫困户数no_poor_num
					if (pkhss != null) {
						mMenuRight_2.setStringArray(pkhss);
					}
				}
			}
			break;
			
		case MyConstans.EIGHT: //请求贫困村页面中的---村筛选条件
			ZidianAppEntityListResult modelListResult = ZidianAppEntityListResult.parseToT(str, ZidianAppEntityListResult.class);
			if (modelListResult.getSuccess()) {
				List<ZidianAppEntity> mAppModels = modelListResult.getJsondata();
				if (mAppModels.size() > 0) {
					for (int i = 0; i < mAppModels.size(); i++) {
						mMenuLeft.setVillage(mAppModels.get(i).getTab());
						models.addAll(mAppModels.get(i).getTab());
					}
				}
			}
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		listview.onRefreshComplete();
		switch (flag){
			case MyConstans.FIRST:
				LogUtil.println("贫困村页面请求服务器List列表数据失败：==="+str);
				showToast(str);
				break;

			case MyConstans.SEVEN:
				LogUtil.println("贫困村请求乡镇数据失败===" + str);
				break;

			case MyConstans.EIGHT:
				LogUtil.println("贫困村请求村数据失败==="+str);
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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_sousuo:
//			showToast("点击了高级查询筛选按钮");
				Intent intent=new Intent(PoorVillageActivity.this,SearchActivity_pinkuncun.class);
				startActivityForResult(intent, 1);
				break;
		}
	}

	/**
	 * 标志位---标识当前是否为高级查询模式
	 */
	private boolean flag_gaojichaxun=false;
	//高级查询条件
	String cunFuZeRenName="",cunFuZeRenPhone="",diTiShuJiName="",diTiShuJiPhone="",baoCunGanBuName="",baoCunGanBuPhone="";
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1){
			if(resultCode==2){
				current_page = 1;
				FRIST=0;
				flag_gaojichaxun=true;
				cunFuZeRenName=data.getStringExtra("cunFuZeRenName");
				cunFuZeRenPhone=data.getStringExtra("cunFuZeRenPhone");
				diTiShuJiName=data.getStringExtra("diTiShuJiName");
				diTiShuJiPhone=data.getStringExtra("diTiShuJiPhone");
				baoCunGanBuName=data.getStringExtra("baoCunGanBuName");
				baoCunGanBuPhone=data.getStringExtra("baoCunGanBuPhone");

				LogUtil.println("村负责人="+cunFuZeRenName+",村负责人电话="+cunFuZeRenPhone+",第一书记="+diTiShuJiName
								+",第一书记电话="+diTiShuJiPhone+",包村干部="+baoCunGanBuName+",包村干部电话="+baoCunGanBuPhone);

				RequestParams rp = getRequestParams();
				rp=getRequestParams_GaoJiChaXun(rp);
				rp.addBodyParameter(EXTS_PAGE, String.valueOf(current_page));
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.POOR_VILLAGE_LIST, rp,
						new MyRequestCallBack(this, MyConstans.FIRST));
				System.out.println("贫困村页面进行高级查询时请求服务器中的贫困村List(flag=1)的网址为：==" + URLs.POOR_VILLAGE_LIST + "?&page=" + String.valueOf(1));
			}
		}
	}

	private RequestParams getRequestParams_GaoJiChaXun(RequestParams rp) {
		rp.addBodyParameter("secretaryname", cunFuZeRenName);
		rp.addBodyParameter("secretaryphone", cunFuZeRenPhone);
		rp.addBodyParameter("firsecretaryname", diTiShuJiName);
		rp.addBodyParameter("firsecretaryphone", diTiShuJiPhone);
		rp.addBodyParameter("villagecadresname", baoCunGanBuName);
		rp.addBodyParameter("villagecadresphone", baoCunGanBuPhone);
		return rp;
	}
}
