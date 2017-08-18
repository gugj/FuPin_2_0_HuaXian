package com.roch.fupin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import com.roch.fupin.view.CaseXueTableScrollView;
import com.roch.fupin.view.ExpandTabView;
import com.roch.fupin.view.MenuRight;
import com.roch.fupin.view.MenuRight_2;
import com.roch.fupin_2_0.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 致贫原因（因学致贫）统计
 * @author ZhaoDongShao
 * 2016年8月11日 
 */
@ContentView(R.layout.activity_poor_people_case_xue_statistic)
public class PoorPeopleCaseYXActivity extends MainBaseActivity{

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	/**
	 * 因学致贫表格的数据---ListView
	 */
	private ListView mListView;
	/**
	 * 盛放因学致贫表格Title的集合
	 */
	protected List<CaseXueTableScrollView> mHScrollViews =new ArrayList<CaseXueTableScrollView>();
	public CaseXueTableScrollView mTouchView;

	//创建数组保存表头
	private String[] cols = {"title","户数","人数","幼儿园","小学生","初中生","高中生","职专","大学生"};
	/**
	 * 因学致贫表格ListView的适配器
	 */
	private  ScrollAdapter mAdapter;

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
	List<Basic_DistrictAppModel> mAppModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		MyApplication.getInstance().addActivity(mActivity);
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

		initViews();

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

					initData("因学");
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
	 * 点击贫困乡镇flag=1或贫困村flag=2时请求服务器的贫困人口统计数据
	 */
	private void getData() {
		RequestParams rp = new RequestParams();
		if(flag_zhen_cun==1){
			if(StringUtil.isNotEmpty(adl_cd_zhen)){
				rp.addBodyParameter("adl_cd",adl_cd_zhen);
				rp.addBodyParameter("pp_poorreason","因学");
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.CASE_DETAIL, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
				System.out.println("贫困人口统计请求服务器网址为：===" + URLs.CASE_DETAIL);
			}
		}else if(flag_zhen_cun==2){
			if(StringUtil.isNotEmpty(adl_cd_cun)){
				rp.addBodyParameter("adl_cd",adl_cd_cun);
				rp.addBodyParameter("pp_poorreason","因学");
				MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
						URLs.CASE_DETAIL, rp, new MyRequestCallBack((BaseActivity) mActivity, MyConstans.FIRST));
				System.out.println("贫困人口统计请求服务器网址为：===" + URLs.CASE_DETAIL);
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

		default:
			break;
		}
		return true;
	}
	
	/**
	 * 2016年8月11日
	 * ZhaoDongShao
	 */
	private void initViews() {
		Intent intent = getIntent();
		String name = intent.getStringExtra(Common.INTENT_KEY);
		if (name != null && !name.equals("")) {
			tv_title.setText(name.substring(0, 2)+"致贫");
		}
		initData("因学");
	}

	private void initData(String cause) {
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("pp_poorreason", cause);
		MyApplication.getInstance().getHttpUtilsInstance().send(
				HttpMethod.POST, URLs.CASE_DETAIL, rp,
				new MyRequestCallBack(mActivity, MyConstans.FIRST));
		System.out.println("因学致贫原因统计页面请求服务器详情网址为：==="+URLs.CASE_DETAIL+"&pp_poorreason="+cause);
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				System.out.println("因学致贫原因统计页面请求服务器详情成功：==="+str);
				// 因学致贫表格的数据源
				List<PeopleCaseDetailEntity> list = new ArrayList<PeopleCaseDetailEntity>();
				PoorPeopleCaseListDetailResult listResult = PoorPeopleCaseListDetailResult.parseToT(str, PoorPeopleCaseListDetailResult.class);
				if (listResult != null ) {
					if (listResult.getSuccess()) {
						list.addAll(listResult.getJsondata());
					}
				}

				List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
				Map<String, String> data = null;
				// 因学致贫表格的Title---布局View
				CaseXueTableScrollView headerScroll = (CaseXueTableScrollView) findViewById(R.id.item_scroll_title);
				// 盛放因学致贫表格Title的集合
				mHScrollViews.add(headerScroll);
				// 因学致贫表格的数据---ListView
				mListView = (ListView) findViewById(R.id.hlistview_scroll_list);
				for(int i = 0; i < list.size(); i++) { // 遍历因学致贫表格的数据源
					data = new HashMap<String, String>();
					data.put("title", list.get(i).getAdl_nm()); // 将因学致贫表格的title列放入Map集合
					for (int j = 1; j < 9; j++) {
						switch (j) {
							case 1:
								data.put("户数", String.valueOf(list.get(i).getPoorreson_x_fc()));
								break;

							case 2:
								data.put("人数", String.valueOf(list.get(i).getPoorreson_x_pc()));
								break;

							case 3:
								data.put("幼儿园", String.valueOf(list.get(i).getPoorreson_x_pc_yey()));
								break;

							case 4:
								data.put("小学生", String.valueOf(list.get(i).getPoorreson_x_pc_xxs()));
								break;

							case 5:
								data.put("初中生", String.valueOf(list.get(i).getPoorreson_x_pc_czs()));
								break;

							case 6:
								data.put("高中生", String.valueOf(list.get(i).getPoorreson_x_pc_gzs()));
								break;

							case 7:
								data.put("职专", String.valueOf(list.get(i).getPoorreson_x_pc_zz()));
								break;

							case 8:
								data.put("大学生", String.valueOf(list.get(i).getPoorreson_x_pc_dx()));
								break;

							default:
								break;
						}
					}
					// 转存因学致贫表格的数据源到List<Map<String,String>>中
					datas.add(data);
				}
				mAdapter = new ScrollAdapter(this, datas, R.layout.listitem_poor_people_case_xue_statistic_listitem //R.layout.item
						, cols
						, new int[] { R.id.item_titlev
						, R.id.item_datav1
						, R.id.item_datav2
						, R.id.item_datav3
						, R.id.item_datav4
						, R.id.item_datav5
						, R.id.item_datav6
						, R.id.item_datav7
						, R.id.item_datav8 });
				mListView.setAdapter(mAdapter);
				showToast("数据加载完成");
				break;

			case MyConstans.SEVEN:
				System.out.println("贫困人口统计页面请求筛选条件中的一级乡镇数据成功：===" + str);
				ZidianAppEntityListResult listResult1 = ZidianAppEntityListResult.parseToT(str, ZidianAppEntityListResult.class);
				if (listResult1 != null && listResult1.getSuccess()) {
					List<ZidianAppEntity> zidianAppEntitys = listResult1.getJsondata();
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
		System.out.println("因学致贫原因统计页面请求服务器详情失败：==="+str);
		showToast(str);
	}
	
	public void addHViews(final CaseXueTableScrollView hScrollView) {
		if(!mHScrollViews.isEmpty()) {
			int size = mHScrollViews.size();
			CaseXueTableScrollView scrollView = mHScrollViews.get(size - 1);
			final int scrollX = scrollView.getScrollX();
			if(scrollX != 0) {
				mListView.post(new Runnable() {
					@Override
					public void run() {
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mHScrollViews.add(hScrollView);
	}

	public void onScrollChanged(int l, int t, int oldl, int oldt){
		for (CaseXueTableScrollView tableScrollView : mHScrollViews) {
			if (mTouchView != tableScrollView) {
				tableScrollView.smoothScrollTo(l, t);
			}
		}
	}

	/**
	 * 自定义的因学致贫表格ListView的适配器
	 */
	class ScrollAdapter extends SimpleAdapter{

		private List<? extends Map<String, ?>> datas;
		private int res;
		private String[] from;
		private int[] to;
		private Context context;

		public ScrollAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
						String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.datas = data;
			this.res = resource;
			this.from = from;
			this.to = to;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if(v == null) {
				v = LayoutInflater.from(context).inflate(res, null);
				addHViews((CaseXueTableScrollView) v.findViewById(R.id.item_chscroll_scroll));
				View[] views = new View[to.length];
				for(int i = 0; i < to.length; i++) {
					View tv = v.findViewById(to[i]);
					views[i] = tv;
				}
				v.setTag(views);
			}
			View[] holders = (View[]) v.getTag();
			int len = holders.length;
			
			for (int i = 0; i < len; i++) {
				((TextView)holders[i]).setText(this.datas.get(position).get(from[i]).toString());
//				Map<String, ?> map = this.datas.get(j);
//				for(int i = 0 ; i < len; i++) {
//					String name = (String)map.get(from[i]);
//					TextView textView = (TextView)holders[i];
//					textView.setText(name);
////					((TextView)holders[i]).setText(name);
//				}
			}
			return v;
		}
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
