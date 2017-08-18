package com.roch.fupin;

import org.json.JSONException;
import org.json.JSONObject;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * 帮扶记录的详情页面，点击帮扶记录的ListView后跳转到此页面
 * 2016年11月1日 
 */
public class ActivityBangFuJiLuHelpDetail extends MainBaseActivity{

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	
	@ViewInject(R.id.wv_notic)
	WebView wv_notic;
	
	/**
	 * toolbar的标题title
	 */
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	
	@ViewInject(R.id.tv_msg_title)
	TextView tv_msg_title;
	
	@ViewInject(R.id.tv_date)
	TextView tv_data;
	@ViewInject(R.id.tv_name)
	TextView tv_name;
	
//	/**
//	 * 显示帮扶记录详情的详情detail
//	 */
////	@ViewInject(R.id.tv_bfjl_helpdetail)
////	TextView tv_bfjl_helpdetail;
//	@ViewInject(R.id.tv_bfjl_helpdetail)
//	WebView tv_bfjl_helpdetail;
//	
//	/**
//	 * 显示帮扶记录详情的title
//	 */
//	@ViewInject(R.id.tv_bfjl_helptitle)
//	TextView tv_bfjl_helptitle;
//	
//	/**
//	 * 显示帮扶记录详情的时间date
//	 */
//	@ViewInject(R.id.tv_bfjl_helpdate)
//	TextView tv_bfjl_helpdate;

	/**
	 * 帮扶记录jsondata中的id，和helptitle、helpdate同级
	 */
	private String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bangfujilu_detail);
		ViewUtils.inject(this);
		
		MyApplication.getInstance().addActivity(this);
		
		// 初始化toolbar
		initToolbar();
		tv_title.setText("帮扶详情");
		
		// 展示帮扶记录的title和时间date
		String helptitle = getIntent().getStringExtra("helptitle");
		tv_msg_title.setText(helptitle);
		String helpdate = getIntent().getStringExtra("helpdate");
		tv_data.setText(helptitle);
		
		// 获取请求网络数据的参数，即帮扶详情的id
		id = getIntent().getStringExtra("id");
			
		// 请求网络数据，展示帮扶详情
		requestHttpNetData();
	}
	
	/**
	 *请求网络数据，展示帮扶详情
	 * 2016年11月1日
	 */
	private void requestHttpNetData() {
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("id", id);
		// 通过post请求网络数据，请求参数为户IDhouseholderid
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.POOR_HOUSE_BangFuJiLu_bfjlID, rp,
				new MyRequestCallBack(this, MyConstans.BangFuJiLu_Id));
	}
	
	/**
	 * 请求网络数据成功后调用该方法,解析数据并将帮扶详情的detail展示出来
	 */
	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		
		switch(flag){
		case MyConstans.BangFuJiLu_Id:
//			System.out.println("解析到数据--***----"+str);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(str);
				JSONObject dataJSONObject = jsonObject.optJSONObject("data");
				String helpdetail = dataJSONObject.optString("helpdetail");
				
//				tv_bfjl_helpdetail.setText(helpdetail);
				
//				tv_bfjl_helpdetail.loadUrl(helpdetail);
				System.out.println("帮扶记录中解析的图片helpdetail数据为------图片url==："+helpdetail);
				wv_notic.loadDataWithBaseURL(null, helpdetail, "text/html", "utf-8", null);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * 初始化toolbar信息
	 * 2016年11月1日
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
	 * 点击toolbar上的返回箭头时，关闭activity
	 */
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
}
