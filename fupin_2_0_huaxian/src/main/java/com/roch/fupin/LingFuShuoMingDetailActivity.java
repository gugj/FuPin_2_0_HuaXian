package com.roch.fupin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.LingFuShuoMing;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

/**
 * 另附说明详情Activity
 * @author ZhaoDongShao
 * 2016年5月18日 
 */
@ContentView(R.layout.fragment_lingfushuoming)
public class LingFuShuoMingDetailActivity extends MainBaseActivity implements View.OnClickListener {

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;

	/**
	 * 标志位---如果为"pinkunhu"则为贫困户情况说明，如果为"pinkuncun"则为贫困村情况说明
	 */
	String type_lingfushuoming;
	/**
	 * 贫困户householderid或贫困村id
	 */
	String houseid;

	@ViewInject(R.id.et_lingfushuoming)
	EditText et_lingfushuoming;
	@ViewInject(R.id.ll_bg)
	LinearLayout ll_bg;
	@ViewInject(R.id.btn_commit)
	Button btn_commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(mActivity);
		ViewUtils.inject(this);

		initDate();

		btn_commit.setOnClickListener(this);
		ll_bg.setOnClickListener(this);
		et_lingfushuoming.setOnClickListener(this);

		initToolbar();
	}

	LingFuShuoMing lingFuShuoMing;
	boolean type_lingfushuoming_zengjia=false;
	/**
	 *初始化数据
	 * 2016年5月18日
	 * ZhaoDongShao
	 */
	private void initDate() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra(Common.INTENT_KEY);
		if (bundle != null) {
			lingFuShuoMing = (LingFuShuoMing) bundle.getSerializable(Common.BUNDEL_KEY);
			type_lingfushuoming =  intent.getStringExtra("type_lingfushuoming");
			houseid =  intent.getStringExtra("houseid");
			if (lingFuShuoMing != null) {
				String qingkuangshuoming_hu=lingFuShuoMing.getFamilyinfo();
				String qingkuangshuoming_cun=lingFuShuoMing.getVillageinfo();
				if(StringUtil.isEmpty(qingkuangshuoming_hu) && StringUtil.isNotEmpty(qingkuangshuoming_cun)){
					et_lingfushuoming.setText(qingkuangshuoming_cun);
				}else if(StringUtil.isEmpty(qingkuangshuoming_cun) && StringUtil.isNotEmpty(qingkuangshuoming_hu)){
					et_lingfushuoming.setText(qingkuangshuoming_hu);
				}
			}else {
				et_lingfushuoming.setText("");
			}
			SharedPreferences sp = mContext.getSharedPreferences("loginactivty", Context.MODE_APPEND);
			String loginUserId=sp.getString("loginUserId", "");
			if(null!=lingFuShuoMing){
				if(!loginUserId.equals(lingFuShuoMing.getUserid())){
					//设置另附说明不可编辑
					et_lingfushuoming.setClickable(false);
				}
			}
		}else { //以下是添加另附说明---没有另附说明的userID
			type_lingfushuoming =  intent.getStringExtra("type_lingfushuoming");
			houseid = intent.getStringExtra("houseid");
			type_lingfushuoming_zengjia =  intent.getBooleanExtra("type_lingfushuoming_zengjia",false);
			et_lingfushuoming.setText("");
		}
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
		if(type_lingfushuoming_zengjia){
			tv_title.setText("添加另附说明");
		}else{
			tv_title.setText("另附说明详情");
		}
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

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_commit: //点击提交按钮
				//edittext失去焦点
				et_lingfushuoming.setFocusable(false);
				String content=et_lingfushuoming.getText().toString();
				SharedPreferences sp = mContext.getSharedPreferences("loginactivty", Context.MODE_APPEND);
				String loginUserId=sp.getString("loginUserId", "");
				if(null!=lingFuShuoMing){
					if(!loginUserId.equals(lingFuShuoMing.getUserid())){
						showToast("无权限修改");
						return;
					}
				}
				if("pinkunhu".equals(type_lingfushuoming)){
					if(type_lingfushuoming_zengjia){
						//增加情况说明到贫困户
//						ShowToast("贫困户增加情况说明=="+content);
						RequestParams rp = new RequestParams();
						rp.addBodyParameter("householderid", houseid);
						rp.addBodyParameter("familyinfo", content);
						MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST, URLs.POOR_HOUSE_QingKuangShuoMing_Add, rp,
								new MyRequestCallBack(this, MyConstans.FIRST));
						System.out.println("贫困户增加另附说明请求服务器数据的网址为：===" + URLs.POOR_HOUSE_QingKuangShuoMing_Add + "&?householderid="+houseid+"&familyinfo="+content);
					}else {
						//修改情况说明到贫困户
//						ShowToast("贫困户修改情况说明=="+content);
						RequestParams rp = new RequestParams();
						rp.addBodyParameter("familyinfoid", lingFuShuoMing.getFamilyinfoid());
						rp.addBodyParameter("familyinfo", content);
						MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST, URLs.POOR_HOUSE_QingKuangShuoMing, rp,
								new MyRequestCallBack(this, MyConstans.FIRST));
						System.out.println("贫困户上传修改另附说明请求服务器数据的网址为：===" + URLs.POOR_HOUSE_QingKuangShuoMing + "&?familyinfoid="+lingFuShuoMing.getFamilyinfoid()+"&familyinfo="+content);
					}
				}else if ("pinkuncun".equals(type_lingfushuoming)){
					if(type_lingfushuoming_zengjia){
						//增加情况说明贫困村
//						ShowToast("贫困村增加情况说明=="+content);
						RequestParams rp = new RequestParams();
						rp.addBodyParameter("villageid", houseid);
						rp.addBodyParameter("villageinfo", content);
						MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST, URLs.POOR_Village_QingKuangShuoMing_Add, rp,
								new MyRequestCallBack(this, MyConstans.FIRST));
						System.out.println("贫困村增加另附说明请求服务器数据的网址为：===" + URLs.POOR_Village_QingKuangShuoMing_Add+"&?villageid="+houseid+"&villageinfo="+content);
					}else {
						//修改情况说明贫困村
//						ShowToast("贫困村修改情况说明=="+content);
						RequestParams rp = new RequestParams();
						rp.addBodyParameter("villageinfoid", lingFuShuoMing.getVillageinfoid());
						rp.addBodyParameter("villageinfo", content);
						MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST, URLs.POOR_Village_QingKuangShuoMing, rp,
								new MyRequestCallBack(this, MyConstans.FIRST));
						System.out.println("贫困村修改另附说明请求服务器数据的网址为：===" + URLs.POOR_Village_QingKuangShuoMing+"&?villageinfoid="+lingFuShuoMing.getVillageinfoid()+"&villageinfo="+content);
					}
				}
				break;

			case R.id.ll_bg: //点击背景，让edittext失去焦点
				et_lingfushuoming.setFocusable(false);
				break;

			case R.id.et_lingfushuoming: //点击edittext，获取焦点
				et_lingfushuoming.setFocusable(true);
				et_lingfushuoming.setFocusableInTouchMode(true);
				et_lingfushuoming.requestFocus();
				et_lingfushuoming.findFocus();
				break;
		}
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				showToast("保存成功");
				break;
		}
	}


	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				showToast(str);
				System.out.println("另附说明请求服务器数据失败：===" + str);
				break;
		}
	}

}
