package com.roch.fupin;

import java.util.List;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.BangFuJiLuAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.PoorHouseBangFuJiLu;
import com.roch.fupin.entity.PoorHouseBangFuJiLu.BangFuJiLu;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 贫困户帮扶记录的fragment，是五个fragment的其中之一，继承自BaseFragment，实现了OnItemClickListener
 * @author 
 * 2016年10月31日 
 */
public class PoorHourseBangFuJiLuFragment extends BaseFragment implements OnItemClickListener {

	/**
	 * 展示帮扶记录的listview，如帮扶记录1，帮扶记录2......
	 */
	@ViewInject(R.id.listView_bfjl)
	ListView listView_bfjl; 
	
	/**
	 * 标志位，标志初始化已经完成
	 */
	private boolean isPrepared;
	
	/**
	 * 标识当前fragment是否可见
	 */
	private boolean isVisible;

	private List<BangFuJiLu> jsondata;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_poorhourse_bangfujilu, container, false);
		ViewUtils.inject(this,view);
		this.mContext = getActivity();
		isPrepared = true;
		
		// 如果没有初始化或当前fragment不可见就return
		lazyLoad();

		return view;
	}
	
	/**
	 * 这个是fragment的方法，该方法用于告诉系统，这个Fragment的UI是否是可见的。所以我们只需要继承Fragment并重写该方法，
	 * 即可实现在fragment可见时才进行数据加载操作，即Fragment的懒加载
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			isVisible = true;
			lazyLoad();
		}else {
			isVisible = false;
		}
	}
	
	/**
	 * 如果没有初始化或当前fragment不可见就return;否则就开始初始化数据initData <br/>
	 * 2016年10月31日
	 */
	private void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
		initData();
	}
	
	/**
	 * 初始化数据  <br/>
	 * 2016年10月31日
	 *
	 */
	private void initData() {
		Bundle bundle = getArguments();
		String houseHolderId = bundle.getString(Common.BUNDEL_KEY);
		System.out.println(houseHolderId+"********************获取到householderid********************");
		
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("householderid", houseHolderId);
		// 通过post请求网络数据，请求参数为户IDhouseholderid
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.POOR_HOUSE_BangFuJiLu_HourseId, rp,
				new MyRequestCallBack(this, MyConstans.BangFuJiLu));
	}
	
	/**
	 * 当通过户ID请求网络数据成功时调用该方法，这个是Success接口类里面的方法，而当前的父类实现了该接口，
	 * 当前类请求了网络数据，所以就一定会调用该方法
	 * @param str
	 * @param flag
	 * 2016年10月31日
	 */
	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag) {
		case MyConstans.BangFuJiLu:

			System.out.println("帮扶记录请求网络时服务器返回的json数据为：："+str);
			
			// 使用Gson解析帮扶记录到其对象中
			PoorHouseBangFuJiLu poorHouseBangFuJiLu=PoorHouseBangFuJiLu.parseToT(str, PoorHouseBangFuJiLu.class);
			// 贫困户帮扶记录的对象不为空
			if(poorHouseBangFuJiLu!=null){
				// 贫困户帮扶记录的对象中的success字段为true
				if(poorHouseBangFuJiLu.getSuccess()){
					
					System.out.println("解析帮扶记录：："+poorHouseBangFuJiLu.getMsg());
					jsondata = poorHouseBangFuJiLu.getJsondata();
					
					// 解析服务器返回的List类型的jsondata集合不为空并且size大于0
				    if(jsondata!=null && jsondata.size()>0){
				    	// 展示帮扶记录的数据--使用adapter
				    	BangFuJiLuAdapter bangFuJiLuAdapter=new BangFuJiLuAdapter(jsondata, getContext());
				    	listView_bfjl.setAdapter(bangFuJiLuAdapter);
				    	// 设置条目点击事件
				    	listView_bfjl.setOnItemClickListener(this);
				    }
				}
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 帮扶记录的ListView条目被点击后调用此方法
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 * 2016年11月1日
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		 // 点击后跳转到帮扶记录详情页面
		Intent intent=new Intent(mContext, ActivityBangFuJiLuHelpDetail.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// 把帮扶记录的title和时间date传到详情的activity中
		String helptitle = jsondata.get(position).helptitle;
		String helpdate = jsondata.get(position).helpdate;
		intent.putExtra("helptitle", helptitle);
		intent.putExtra("helpdate", helpdate);
		
		// 把帮扶记录的json中的id传过去，作为请求参数请求网络数据
		String id2 = jsondata.get(position).id;
		intent.putExtra("id", id2);
		
		startActivity(intent);
	}
}
