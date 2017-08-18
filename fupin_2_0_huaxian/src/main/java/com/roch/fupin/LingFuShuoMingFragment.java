package com.roch.fupin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

/**
 * 另附说明fragment
 */
public class LingFuShuoMingFragment extends BaseFragment implements View.OnClickListener {

	/**
	 * 标志位，标志初始化已经完成
	 */
	private boolean isPrepared;
	/**
	 * 标识当前fragment是否可见
	 */
	private boolean isVisible;
	/**
	 * 标志位---如果为"pinkunhu"则为贫困户情况说明，如果为"pinkuncun"则为贫困村情况说明
	 */
	String type_lingfushuoming;
	/**
	 * 标志位---是否是第一次显示“暂无情况说明”，默认为true
	 */
	boolean isFirstComeIn=true;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_lingfushuoming, container, false);
		ViewUtils.inject(this, view);
		btn_commit.setOnClickListener(this);
		ll_bg.setOnClickListener(this);
		et_lingfushuoming.setOnClickListener(this);
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
	 * 如果没有初始化完成或当前fragment不可见，就不去请求网络加载数据；否则开始请求网络加载数据
	 */
	private void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
		//初始化数据，即通过Bundle获取传输过来的hourseid,然后给情况说明赋值
		initData();
	}

	/**
	 * 初始化数据，即通过Bundle获取传输过来的hourseid,然后给情况说明赋值
	 */
	private void initData() {
		Bundle bundle = getArguments();
		if (!StringUtil.isEmpty(bundle)) {
//			@SuppressWarnings("unchecked")
			houseid = bundle.getString(Common.TITLE_KEY);
			type_lingfushuoming=bundle.getString("type_lingfushuoming");
			//获取贫困户、贫困村情况说明
			String qingkuangshuoming=bundle.getString("qingkuangshuoming");
			if(StringUtil.isEmpty(qingkuangshuoming)){
				if(isFirstComeIn){
					isFirstComeIn=false;
					ShowToast("暂无情况说明");
				}
			}else {
				et_lingfushuoming.setText(qingkuangshuoming);
			}
		}
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				ShowToast("保存成功");
				break;

			default:
				break;
		}
	}


	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				ShowToast(str);
				System.out.println("另附说明请求服务器数据失败：==="+str);
				break;

			default:
				break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_commit: //点击提交按钮
				//edittext失去焦点
				et_lingfushuoming.setFocusable(false);
				String content=et_lingfushuoming.getText().toString();
				if("pinkunhu".equals(type_lingfushuoming)){
					//提交情况说明到贫困户
//						ShowToast("贫困户提交情况说明=="+content);
					RequestParams rp = new RequestParams();
					rp.addBodyParameter("householderid", houseid);
					rp.addBodyParameter("familyinfo", content);
					MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST, URLs.POOR_HOUSE_QingKuangShuoMing, rp,
							new MyRequestCallBack(LingFuShuoMingFragment.this, MyConstans.FIRST));
					System.out.println("贫困户另附说明请求服务器数据的网址为：===" + URLs.POOR_HOUSE_QingKuangShuoMing + "&?householderid="+houseid);
				}else if ("pinkuncun".equals(type_lingfushuoming)){
					//提交情况说明贫困村
//						ShowToast("贫困村提交情况说明=="+content);
					RequestParams rp = new RequestParams();
					rp.addBodyParameter("id", houseid);
					rp.addBodyParameter("villageinfo", content);
					MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST, URLs.POOR_Village_QingKuangShuoMing, rp,
							new MyRequestCallBack(LingFuShuoMingFragment.this, MyConstans.FIRST));
					System.out.println("贫困村另附说明请求服务器数据的网址为：===" + URLs.POOR_Village_QingKuangShuoMing+"&?id="+houseid);
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

}
