/**
 * 
 */
package com.roch.fupin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin_2_0.R;
import com.roch.fupin.adapter.ViewPagerAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.ProjectFulianAppModel;
import com.roch.fupin.entity.ProjectFulianAppModelListResult;
import com.roch.fupin.entity.ProjectFulianItemAppModel;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author ZhaoDongShao
 *
 * 2016年6月2日 
 *
 */
@ContentView(R.layout.activity_poorhouse_detail)
public class NoPoorProjectFuLianDetailActivity extends MainBaseActivity{

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.ll_navibar)
	private LinearLayout layout_title_name;
	@ViewInject(R.id.vp_pager)
	ViewPager viewPager;

	List<Fragment> list = new ArrayList<Fragment>();
	ViewPagerAdapter viewPagerAdapter;

	/**
	 * 当前fragment索引
	 */
	private int currPage = 0;
	/**
	 * table页标题
	 */
	private String[] title_names;
	/**
	 * 所有标题
	 */
	List<View> listviews = new ArrayList<View>();


	ProjectFulianAppModel appModel;

	List<ProjectFulianItemAppModel> itemAppModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initToolbar();
		MyApplication.getInstance().addActivity(mActivity);
		initView();
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
	 * 2016年5月9日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initView() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra(Common.INTENT_KEY);
		this.appModel = (ProjectFulianAppModel) bundle.getSerializable(Common.BUNDEL_KEY);
		if (!StringUtil.isEmpty(appModel)) {
			tv_title.setText(Common.PROJECT_DETAIL);

			RequestParams rp = new RequestParams();
			rp.addBodyParameter("id", appModel.getId());
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
					URLs.NO_POOR_PROJECT_FULIAN_DETAIL, rp,
					new MyRequestCallBack(this, MyConstans.FIRST));
		}


		title_names = ResourceUtil.getInstance().getStringArrayById(R.array.fulian_title);
		for (int i = 0; i < title_names.length; i++) {

			final TextView tv_title_name = new TextView(mContext);
			tv_title_name.setGravity(Gravity.CENTER);
			tv_title_name.setPadding(5, 5, 5, 5);
			tv_title_name.setTextSize(Common.TEXT_SIZE);
			tv_title_name.setId(i);
			tv_title_name.setText(title_names[i]);
			layout_title_name.addView(tv_title_name, Common.Width / 4,LinearLayout.LayoutParams.WRAP_CONTENT);

			listviews.add(tv_title_name);

			tv_title_name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (currPage == v.getId()){

					}else {
						viewPager.setCurrentItem(v.getId());
					}
				}
			});
		}
	}
	@Override
	public void onSuccessResult(String str, int flag) {
		// TODO Auto-generated method stub
		super.onSuccessResult(str, flag);
		switch (flag) {
		case MyConstans.FIRST:

			ProjectFulianAppModelListResult listResult = ProjectFulianAppModelListResult.parseToT(str, ProjectFulianAppModelListResult.class);

			if (listResult != null && listResult.getSuccess()) {
				List<ProjectFulianAppModel> appModels = listResult.getJsondata();
				if (appModels != null && appModels.size() > 0) {
					
					for (int i = 0; i < appModels.size(); i++) {
						List<ProjectFulianItemAppModel> list = appModels.get(i).getPam();
						if (list != null) {
							initPage(list);
						}
					}
				
					
				}
				
			}
			break;

		default:
			break;
		}
	}


	/**
	 *
	 * @param lAppModels2
	 *
	 * 2016年6月3日
	 *
	 * ZhaoDongShao
	 *
	 */
	@SuppressWarnings("deprecation")
	private void initPage(List<ProjectFulianItemAppModel> itemAppModel) {
		// TODO Auto-generated method stub
		FuLian_XmxxFragment xmxxFragment = new FuLian_XmxxFragment();
		Bundle bundle_xmxx = new Bundle();
		bundle_xmxx.putSerializable(Common.BUNDEL_KEY, this.appModel);
		xmxxFragment.setArguments(bundle_xmxx);

		FuLian_PxryFragment ffryFragment = new FuLian_PxryFragment();
		Bundle bundle_ffry = new Bundle();
		bundle_ffry.putSerializable(Common.BUNDEL_KEY, (Serializable) itemAppModel);
		ffryFragment.setArguments(bundle_ffry);

		list.add(xmxxFragment);
		list.add(ffryFragment);

		viewPagerAdapter = new ViewPagerAdapter(list, this);
		viewPagerAdapter.setTitle(title_names);
		//默认显示第一页
		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < listviews.size(); i++) {
					if (arg0 == i) {
						listviews.get(arg0).setSelected(true);
						TextView textView = (TextView)listviews.get(arg0);
						textView.setTextColor(ResourceUtil.getInstance().getColorById(R.color.bule_155bbb));
						Drawable drawable = ResourceUtil.getInstance().getDrawableByID(R.drawable.blueyes_03);
						textView.setBackgroundDrawable(drawable);
						currPage = arg0;
					}else {
						listviews.get(i).setSelected(false);
						TextView textView = (TextView)listviews.get(i);
						textView.setTextColor(ResourceUtil.getInstance().getColorById(R.color.black));
						Drawable drawable = ResourceUtil.getInstance().getDrawableByID(R.drawable.blusno_03);
						textView.setBackgroundDrawable(drawable);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		for (int i = 0; i < listviews.size(); i++) {
			if (currPage == i) {
				listviews.get(currPage).setSelected(true);
				TextView textView = (TextView)listviews.get(currPage);
				textView.setTextColor(ResourceUtil.getInstance().getColorById(R.color.bule_155bbb));
				Drawable drawable = ResourceUtil.getInstance().getDrawableByID(R.drawable.blueyes_03);
				textView.setBackgroundDrawable(drawable);
				viewPager.setCurrentItem(currPage);
			}else {
				listviews.get(i).setSelected(false);
				TextView textView = (TextView)listviews.get(i);
				textView.setTextColor(ResourceUtil.getInstance().getColorById(R.color.black));
				Drawable drawable = ResourceUtil.getInstance().getDrawableByID(R.drawable.blusno_03);
				textView.setBackgroundDrawable(drawable);
			}
		}

	}
}
