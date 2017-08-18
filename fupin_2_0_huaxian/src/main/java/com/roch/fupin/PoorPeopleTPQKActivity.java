/**
 * 
 */
package com.roch.fupin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.dialog.NoPoorProject_tpqk_FilterPopWindow;
import com.roch.fupin.dialog.NoPoorProject_tpqk_FilterPopWindow.ShowMessageListener;
import com.roch.fupin.entity.MapEntity;
import com.roch.fupin.entity.NoPoorSituation;
import com.roch.fupin.entity.NoPoorSituationListResult;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin.view.TPQKTableScrollView;
import com.roch.fupin_2_0.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 
 * 脱贫情况统计
 * 
 * @author ZhaoDongShao
 *
 * 2016年8月11日 
 *
 */

@ContentView(R.layout.activity_poor_people_tpqk_statistic)
public class PoorPeopleTPQKActivity extends MainBaseActivity implements ShowMessageListener{

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.tv_time)
	TextView tv_time;

	NoPoorProject_tpqk_FilterPopWindow filterPopWindow;

	private ListView mListView;
	protected List<TPQKTableScrollView> mHScrollViews =new ArrayList<TPQKTableScrollView>();

	public TPQKTableScrollView mTouchView;

	//创建数组保存表头
	private String[] cols = {"title","户数","人数","户数1","人数1"};

	private  ScrollAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		MyApplication.getInstance().addActivity(mActivity);

		initToolbar();
		initViews();
	}


	/**
	 *
	 *
	 * 2016年8月5日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initToolbar() {
		// TODO Auto-generated method stub
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}
		toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				// TODO Auto-generated method stub

				switch (menuItem.getItemId()) {
				case R.id.select:

					int xPox = (int)(Common.Width * 0.9);
					filterPopWindow = new NoPoorProject_tpqk_FilterPopWindow(mContext);
					filterPopWindow.setShowMessageListener(PoorPeopleTPQKActivity.this);
					filterPopWindow.setSelectionAdapter(maps);
					filterPopWindow.showPopupWindow(toolbar,xPox);

					break;

				default:
					break;
				}

				return false;
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.select_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

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
	 *
	 *
	 * 2016年8月11日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initViews() {

		Intent intent = getIntent();
		String name = intent.getStringExtra(Common.INTENT_KEY);

		if (name != null && !name.equals("")) {
			tv_title.setText(name);
		}

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		String time = year + "年" + month + "月份";
		tv_time.setText(time + "脱贫情况汇总统计");


		MyApplication.getInstance().getHttpUtilsInstance().send(
				HttpMethod.POST, URLs.POOR_PROPLE_TPQK, 
				new MyRequestCallBack(mActivity, MyConstans.FIRST));
	}


	@Override
	public void onSuccessResult(String str, int flag) {
		// TODO Auto-generated method stub
		super.onSuccessResult(str, flag);

		List<NoPoorSituation> list = new ArrayList<NoPoorSituation>();

		NoPoorSituationListResult listResult = NoPoorSituationListResult.parseToT(str, NoPoorSituationListResult.class);
		if (listResult != null ) {

			if (listResult.getSuccess()) {

				list.addAll(listResult.jsondata);

				if (StringUtil.isNotEmpty(list)) {


					List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
					Map<String, String> data = null;
					TPQKTableScrollView headerScroll = (TPQKTableScrollView) findViewById(R.id.item_scroll_title);
					mHScrollViews.add(headerScroll);
					mListView = (ListView) findViewById(R.id.hlistview_scroll_list);
					for(int i = 0; i < list.size(); i++) {
						data = new HashMap<String, String>();
						data.put("title", list.get(i).adl_nm);
						for (int j = 1; j < 9; j++) {

							switch (j) {
							case 1:

								data.put("户数", String.valueOf(list.get(i).pass_f_c));

								break;
							case 2:

								data.put("人数", String.valueOf(list.get(i).pass_p_c));

								break;
							case 3:

								data.put("户数1", String.valueOf(list.get(i).unpass_f_c));

								break;
							case 4:

								data.put("人数1", String.valueOf(list.get(i).unpass_p_c));

								break;
							default:
								break;
							}
						}

						datas.add(data);
					}
					mAdapter = new ScrollAdapter(this, datas, R.layout.listitem_poor_people_tpqk_statistic_listitem //R.layout.item
							, cols
							, new int[] { R.id.item_titlev
									, R.id.item_datav1
									, R.id.item_datav2
									, R.id.item_datav3
									, R.id.item_datav4
					});
					mListView.setAdapter(mAdapter);
					showToast("数据加载完成");
				}else {
					showToast("当前没有更多数据");
				}
			}else {
				showToast("数据加载失败");
			}

		}else {
			ShowNoticDialog();
		}



	}





	public void addHViews(final TPQKTableScrollView hScrollView) {
		if(!mHScrollViews.isEmpty()) {
			int size = mHScrollViews.size();
			TPQKTableScrollView scrollView = mHScrollViews.get(size - 1);
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

		for (TPQKTableScrollView tableScrollView : mHScrollViews) {

			if (mTouchView != tableScrollView) {

				tableScrollView.smoothScrollTo(l, t);

			}

		}

	}


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
				addHViews((TPQKTableScrollView) v.findViewById(R.id.item_chscroll_scroll));
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

				Map<String, ?> map = this.datas.get(position);
				String name = (String)map.get(from[i]);
				//				
				TextView textView = (TextView)holders[i];
				//				
				if (name != null) {
					textView.setText(name);
				}else {
					textView.setText("");
				}

				////				((TextView)holders[i]).setText(name);
				//				((TextView)holders[i]).setText(this.datas.get(position).get(from[i]).toString());
				//				Map<String, ?> map = this.datas.get(j);
				//				
				//				for(int i = 0 ; i < len; i++) {
				//					
				//					String name = (String)map.get(from[i]);
				//					
				//					TextView textView = (TextView)holders[i];
				//					
				//					textView.setText(name);
				////					((TextView)holders[i]).setText(name);
				//				}

			}

			return v;
		}

	}

	List<MapEntity> maps = new ArrayList<MapEntity>();
	@Override
	public void Message(String time_end) {
		// TODO Auto-generated method stub
		maps.clear();
		String times = "";
		RequestParams rp = new RequestParams();
		if (!time_end.equals("")) {

			int year = Integer.parseInt(time_end.split("-")[0]);
			int months = Integer.parseInt(time_end.split("-")[1]);


			Calendar calendar = Calendar.getInstance();
			if (year > calendar.get(Calendar.YEAR)) {
				showToast("请选择正确的查询日期");
				return;
			}else if (year == calendar.get(Calendar.YEAR)) {

				if (months > calendar.get(Calendar.MONTH) + 1) {

					showToast("请选择正确的查询日期");
					return;

				}else {

					times += year + "年" + months + "月份";
					maps.add(new MapEntity("wejend", time_end));
					if (months > 0 && months < 10) {
						time_end = year + "-0" + months;
					}
					rp.addBodyParameter("lastMonth", time_end);
				}
			}else {

				times += year + "年" + months + "月份";
				maps.add(new MapEntity("wejend", time_end));
				if (months > 0 && months < 10) {
					time_end = year + "-0" + months;
				}
				rp.addBodyParameter("lastMonth", time_end);

			}


		}else {

			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;

			String a = year + "年" + month + "月份";

			times += a;
		}
		tv_time.setText(times + "脱贫情况汇总统计");
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
				URLs.POOR_PROPLE_TPQK, rp,
				new MyRequestCallBack(this, MyConstans.FIRST));
		filterPopWindow.dismiss();
	}

}
