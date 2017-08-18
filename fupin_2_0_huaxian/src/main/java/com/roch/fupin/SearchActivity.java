package com.roch.fupin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roch.fupin.adapter.PopupViewAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin_2_0.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 贫困户高级查询页面---增加了很多查询条件
 * @author ZhaoDongShao
 * 2017年4月1日 
 */
@ContentView(R.layout.activity_search_gaoji)
public class SearchActivity extends MainBaseActivity implements OnItemClickListener {

	@ViewInject(R.id.tv_house_name)
	EditText tv_house_name;
	
	@ViewInject(R.id.tv_house_phone)
	EditText tv_house_phone;
	
	@ViewInject(R.id.tv_house_cardid)
	EditText tv_house_cardid;
	
	@ViewInject(R.id.tv_house_tuopinqingkuang)
	TextView tv_house_tuopinqingkuang;
	
	@ViewInject(R.id.tv_house_pkhsx)
	TextView tv_house_pkhsx;
	
	@ViewInject(R.id.tv_house_zaixiaosheng)
	TextView tv_house_zaixiaosheng;
	
	@ViewInject(R.id.tv_house_canjiren)
	TextView tv_house_canjiren;
	
	@ViewInject(R.id.tv_house_jingshenbing)
	TextView tv_house_jingshenbing;
	
	@ViewInject(R.id.tv_house_zhizhang)
	TextView tv_house_zhizhang;
	
//	@ViewInject(R.id.tv_house_shilian)
//	TextView tv_house_shilian;
	
	@ViewInject(R.id.tv_house_cxjm_yiliao)
	TextView tv_house_cxjm_yiliao;
	
//	@ViewInject(R.id.tv_house_czzg_yl)
//	TextView tv_house_czzg_yl;
	
//	@ViewInject(R.id.tv_house_cxjm_yanglao)
//	TextView tv_house_cxjm_yanglao;
	
	@ViewInject(R.id.tv_housen_dabing)
	TextView tv_housen_dabing;
	
	@ViewInject(R.id.tv_shourunianfen)
	TextView tv_shourunianfen;
//	@ViewInject(R.id.tv_tuopinnianfen)
//	TextView tv_tuopinnianfen;

	@ViewInject(R.id.tv_youwuzhaopian)
	TextView tv_youwuzhaopian;
	@ViewInject(R.id.tv_sex)
	TextView tv_sex;
	@ViewInject(R.id.tv_youwu_qingkuangshuoming)
	TextView tv_youwu_qingkuangshuoming;

	@ViewInject(R.id.tv_title)
	TextView tv_title;
	
	@ViewInject(R.id.btn_quxiao)
	Button btn_quxiao;
	
	@ViewInject(R.id.btn_quding)
	Button btn_quding;
	
	/**
     * 筛选条件的集合---贫困户属性
     */
    private List<String> selectors_pkhsx = new ArrayList<String>();
    /**
     * 筛选条件的集合---有无......
     */
    private List<String> selectors_youwu = new ArrayList<String>();
    /**
     * 筛选条件的集合---是否......
     */
    private List<String> selectors_shifou = new ArrayList<String>();
    /**
     * 筛选条件的集合---性别
     */
    private List<String> selectors_xingbie = new ArrayList<String>();
    /**
     * 筛选条件的集合---脱贫情况
     */
    private List<String> selectors_tpqk = new ArrayList<String>();
    /**
     * 筛选条件的集合---脱贫、收入年份
     */
    private List<String> selectors_tpsr = new ArrayList<String>();
    /**
     * 弹出窗口PopupWindow
     */
    private PopupWindow popupWindow;

    /**
     * 弹出窗口PopupWindow中填充的View
     */
    private View popupView;

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		tv_title.setText("高级查询");

		initToolbar();
		//初始化数据---将筛选条件添加到集合中
        initDataPKHSX();
        //初始化有无...数据---将筛选条件添加到集合中
        initDataYouWu();
        //初始化是否...数据---将筛选条件添加到集合中
        initDataShiFou();
        //初始化性别数据---将筛选条件添加到集合中
        initDataXingBie();
        //初始化脱贫情况数据---将筛选条件添加到集合中
        initDataTPQK();
      //初始化脱贫收入年份数据---将筛选条件添加到集合中
        initDataTPSR();
        //初始化PopupWindow的数据
//        initPopup();
	}

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

	private int year=2017;
	/**
	 */
	private void initDataTPSR() {
		for (int i = 0; i < 5; i++) {
			selectors_tpsr.add(String.valueOf(year--));
		}
	}

	/**
	 * 初始化脱贫情况数据---将筛选条件添加到集合中
	 * 2017年4月1日
	 * ZhaoDongShao
	 */
	private void initDataTPQK() {
//		selectors_tpqk.add("未脱贫");
//		selectors_tpqk.add("已脱贫");
//		selectors_tpqk.add("预脱贫");
//		selectors_tpqk.add("返贫");
//		selectors_tpqk.add("注销");
		List<String> zpyy = (List<String>) getIntent().getSerializableExtra("tpqk");
		selectors_tpqk.addAll(zpyy);
	}

	/**
	 * 初始化有无...数据---将筛选条件添加到集合中
	 * 2017年4月1日
	 * ZhaoDongShao
	 */
	private void initDataYouWu() {
		selectors_youwu.add("有");
		selectors_youwu.add("无");
	}
	/**
	 * 初始化是否...数据---将筛选条件添加到集合中
	 * 2017年4月1日
	 * ZhaoDongShao
	 */
	private void initDataShiFou() {
		selectors_shifou.add("是");
		selectors_shifou.add("否");
	}
	/**
	 * 初始化是否...数据---将筛选条件添加到集合中
	 * 2017年4月1日
	 * ZhaoDongShao
	 */
	private void initDataXingBie() {
		selectors_xingbie.add("男");
		selectors_xingbie.add("女");
	}

	/**
     * 初始化贫困户属性数据---将筛选条件添加到集合中
     */
    private void initDataPKHSX() {

    	selectors_pkhsx.add("一般贫困户");
    	selectors_pkhsx.add("低保贫困户");
    	selectors_pkhsx.add("五保贫困户");
    	selectors_pkhsx.add("一般农户");
    	selectors_pkhsx.add("低保户");
    	selectors_pkhsx.add("五保户");
    }
    
    /**
     * 初始化PopupWindow的数据
     */
    private void initPopup(List<String> selectors) {
        popupView = View.inflate(this, R.layout.view_popup, null);
        ListView lv_popup = (ListView) popupView.findViewById(R.id.lv_popup);
        PopupViewAdapter popupViewAdapter = new PopupViewAdapter(this, selectors);
        lv_popup.setAdapter(popupViewAdapter);
        lv_popup.setOnItemClickListener(this);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 标志位---记录当前点击的筛选条件是哪个
     */
    private int selectTeye=-1;
    
	@OnClick({R.id.tv_house_name,R.id.tv_house_phone,R.id.tv_house_cardid,R.id.tv_house_tuopinqingkuang,R.id.tv_house_pkhsx
		,R.id.tv_house_zaixiaosheng,R.id.tv_house_canjiren,R.id.tv_house_jingshenbing,R.id.tv_house_zhizhang
		,R.id.tv_house_cxjm_yiliao,R.id.tv_housen_dabing,R.id.tv_shourunianfen
		,R.id.tv_youwuzhaopian,R.id.tv_sex,R.id.tv_youwu_qingkuangshuoming,R.id.btn_quding,R.id.btn_quxiao})
	public void onClick(View view){ //,R.id.tv_house_shilian
		switch (view.getId()) {
		case R.id.tv_house_name:
//			showToast("姓名");
			break;
			
		case R.id.tv_house_phone:
//			showToast("电话");
			break;
			
		case R.id.tv_house_cardid:
//			showToast("身份证");
			break;
			
		case R.id.tv_house_tuopinqingkuang:
//			showToast("脱贫情况");
			selectTeye=R.id.tv_house_tuopinqingkuang;
			initPopup(selectors_tpqk);
			showPopupWindow();
			break;
			
		case R.id.tv_house_pkhsx:
//			showToast("贫困户属性");
			selectTeye=R.id.tv_house_pkhsx;
			initPopup(selectors_pkhsx);
			showPopupWindow();
			break;
			
//		case R.id.tv_tuopinnianfen:
////			showToast("脱贫年份");
//			selectTeye=R.id.tv_tuopinnianfen;
//			initPopup(selectors_tpsr);
//			showPopupWindow();
//			break;
			
		case R.id.tv_shourunianfen:
//			showToast("收入年份");
			selectTeye=R.id.tv_shourunianfen;
			initPopup(selectors_tpsr);
			showPopupWindow();
			break;
			
		case R.id.tv_house_zaixiaosheng:
//			showToast("有无在校生");
			selectTeye=R.id.tv_house_zaixiaosheng;
			initPopup(selectors_youwu);
			showPopupWindow();
			break;
			
		case R.id.tv_house_canjiren:
//			showToast("有无残疾人");
			selectTeye=R.id.tv_house_canjiren;
			initPopup(selectors_youwu);
			showPopupWindow();
			break;
			
		case R.id.tv_house_jingshenbing:
//			showToast("有无精神病");
			selectTeye=R.id.tv_house_jingshenbing;
			initPopup(selectors_youwu);
			showPopupWindow();
			break;
			
		case R.id.tv_house_zhizhang:
//			showToast("有无智障人员");
			selectTeye=R.id.tv_house_zhizhang;
			initPopup(selectors_youwu);
			showPopupWindow();
			break;
			
//		case R.id.tv_house_shilian:
////			showToast("有无失联人员");
//			selectTeye=R.id.tv_house_shilian;
//			initPopup(selectors_youwu);
//			showPopupWindow();
//			break;
			
		case R.id.tv_house_cxjm_yiliao:
//			showToast("是否参加城乡居民医疗保险");
			selectTeye=R.id.tv_house_cxjm_yiliao;
			initPopup(selectors_shifou);
			showPopupWindow();
			break;
			
//		case R.id.tv_house_czzg_yl:
////			showToast("有无参加城镇职工养老保险");
//			selectTeye=R.id.tv_house_czzg_yl;
//			initPopup(selectors_youwu);
//			showPopupWindow();
//			break;
			
//		case R.id.tv_house_cxjm_yanglao:
////			showToast("有无参加城乡居民养老保险");
//			selectTeye=R.id.tv_house_cxjm_yanglao;
//			initPopup(selectors_youwu);
//			showPopupWindow();
//			break;
			
		case R.id.tv_housen_dabing:
//			showToast("是否参加大病保险");
			selectTeye=R.id.tv_housen_dabing;
			initPopup(selectors_shifou);
			showPopupWindow();
			break;

		case R.id.tv_youwuzhaopian:
//			showToast("有无照片");
				selectTeye=R.id.tv_youwuzhaopian;
				initPopup(selectors_youwu);
				showPopupWindow();
				break;

		case R.id.tv_sex:
//			showToast("性别");
				selectTeye=R.id.tv_sex;
				initPopup(selectors_xingbie);
				showPopupWindow();
				break;

		case R.id.tv_youwu_qingkuangshuoming:
//			showToast("有无情况说明");
				selectTeye=R.id.tv_youwu_qingkuangshuoming;
				initPopup(selectors_youwu);
				showPopupWindow();
				break;

		case R.id.btn_quxiao: //取消筛选
			finish();
			break;
			
		case R.id.btn_quding: //确定
			String houseName=tv_house_name.getText().toString();
			String housePhone=tv_house_phone.getText().toString();
			String houseCardID=tv_house_cardid.getText().toString();
			String houseTPQK=tv_house_tuopinqingkuang.getText().toString(); //脱贫情况
			String housePKUSX=tv_house_pkhsx.getText().toString();
//			String houseTouPinNianFen=tv_tuopinnianfen.getText().toString(); //脱贫年份
			String houseYearShouRuNianFen=tv_shourunianfen.getText().toString();//收入年份
			String houseZaiXiaoSheng=tv_house_zaixiaosheng.getText().toString();
			String houseCanJiRen=tv_house_canjiren.getText().toString();
			String houseJingShenBing=tv_house_jingshenbing.getText().toString();
			String houseZhiZhang=tv_house_zhizhang.getText().toString();
//			String houseShiLian=tv_house_shilian.getText().toString();
			String house_cxjm_yiliao=tv_house_cxjm_yiliao.getText().toString();
//			String house_czzg_yl=tv_house_czzg_yl.getText().toString();
//			String house_cxjm_yanglao=tv_house_cxjm_yanglao.getText().toString();
			String houseDaBing=tv_housen_dabing.getText().toString();
			String youwuzhaopian=tv_youwuzhaopian.getText().toString();
			String xingBie=tv_sex.getText().toString();
			String youwu_qingkuangshuoming=tv_youwu_qingkuangshuoming.getText().toString();
//			showToast("大病未选："+houseDaBing);
			Intent intent=new Intent();
			intent.putExtra("houseName", houseName);
			intent.putExtra("housePhone", housePhone);
			intent.putExtra("houseCardID", houseCardID);
			intent.putExtra("houseTPQK", houseTPQK);
			intent.putExtra("housePKUSX", housePKUSX);
//			intent.putExtra("houseTouPinNianFen", houseTouPinNianFen);
			intent.putExtra("houseYearShouRuNianFen", houseYearShouRuNianFen);
			intent.putExtra("houseZaiXiaoSheng", houseZaiXiaoSheng);
			intent.putExtra("houseCanJiRen", houseCanJiRen);
			intent.putExtra("houseJingShenBing", houseJingShenBing);
			intent.putExtra("houseZhiZhang", houseZhiZhang);
//			intent.putExtra("houseShiLian", houseShiLian);
			intent.putExtra("house_cxjm_yiliao", house_cxjm_yiliao);
//			intent.putExtra("house_czzg_yl", house_czzg_yl);
//			intent.putExtra("house_cxjm_yanglao", house_cxjm_yanglao);
			intent.putExtra("houseDaBing", houseDaBing);
			intent.putExtra("youwuzhaopian", youwuzhaopian);
			intent.putExtra("xingBie", xingBie);
			intent.putExtra("youwu_qingkuangshuoming", youwu_qingkuangshuoming);
			setResult(2, intent);
			finish();
			break;
			
		default:
			selectTeye=-1;
			break;
		}
	}
	
	 /**
     * 显示PopupWindow
     */
    private void showPopupWindow() {
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(R.style.PopupWindowTimerAnimation);
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
	
	/**
     * 当PopupWindow中的ListView上的Item被点击时调用此方法
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	switch (selectTeye) {
//    	case R.id.tv_tuopinnianfen:
////			showToast("脱贫年份");
//    		tv_tuopinnianfen.setText(selectors_tpsr.get(position));
//			break;
    	
    	case R.id.tv_shourunianfen:
//			showToast("收入年份");
    		tv_shourunianfen.setText(selectors_tpsr.get(position));
			break;
    	
		case R.id.tv_house_tuopinqingkuang:
//			showToast("脱贫情况");
			tv_house_tuopinqingkuang.setText(selectors_tpqk.get(position));
			break;
			
		case R.id.tv_house_pkhsx:
//			showToast("贫困户属性");
			tv_house_pkhsx.setText(selectors_pkhsx.get(position));
			break;
			
		case R.id.tv_house_zaixiaosheng:
//			showToast("有无在校生");
			tv_house_zaixiaosheng.setText(selectors_youwu.get(position));
			break;
			
		case R.id.tv_house_canjiren:
//			showToast("有无残疾人");
			tv_house_canjiren.setText(selectors_youwu.get(position));
			break;
			
		case R.id.tv_house_jingshenbing:
//			showToast("有无精神病");
			tv_house_jingshenbing.setText(selectors_youwu.get(position));
			break;
			
		case R.id.tv_house_zhizhang:
//			showToast("有无智障人员");
			tv_house_zhizhang.setText(selectors_youwu.get(position));
			break;
			
//		case R.id.tv_house_shilian:
////			showToast("有无失联人员");
//			tv_house_shilian.setText(selectors_youwu.get(position));
//			break;
			
		case R.id.tv_house_cxjm_yiliao:
//			showToast("是否参加城乡居民医疗保险");
			tv_house_cxjm_yiliao.setText(selectors_shifou.get(position));
			break;
			
//		case R.id.tv_house_czzg_yl:
////			showToast("有无参加城镇职工养老保险");
//			tv_house_czzg_yl.setText(selectors_youwu.get(position));
//			break;
			
//		case R.id.tv_house_cxjm_yanglao:
////			showToast("有无参加城乡居民养老保险");
//			tv_house_cxjm_yanglao.setText(selectors_youwu.get(position));
//			break;
			
		case R.id.tv_housen_dabing:
//			showToast("是否参加大病保险");
			tv_housen_dabing.setText(selectors_shifou.get(position));
			break;

		case R.id.tv_youwuzhaopian:
//			showToast("有无照片");
				tv_youwuzhaopian.setText(selectors_youwu.get(position));
				break;

		case R.id.tv_sex:
//			showToast("性别");
			tv_sex.setText(selectors_xingbie.get(position));
				break;

		case R.id.tv_youwu_qingkuangshuoming:
//			showToast("有无情况说明");
				tv_youwu_qingkuangshuoming.setText(selectors_youwu.get(position));
				break;

		default:
			break;
		}
//        tv_device_name.setText(deviceNames.get(position));
        popupWindow.setFocusable(false);
        popupWindow.dismiss();
    }
	
}
