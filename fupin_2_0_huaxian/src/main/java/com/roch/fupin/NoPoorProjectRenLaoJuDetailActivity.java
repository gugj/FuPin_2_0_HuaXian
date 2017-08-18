/**
 * 
 */
package com.roch.fupin;

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
import com.roch.fupin.entity.ProjectRenlaoAppModel;
import com.roch.fupin.entity.ProjectRenlaoAppModelListResult;
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
 * 民政局
 * 
 * @author ZhaoDongShao
 *
 * 2016年6月2日 
 *
 */
@ContentView(R.layout.activity_poorhouse_detail)
public class NoPoorProjectRenLaoJuDetailActivity extends MainBaseActivity{

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


	ProjectRenlaoAppModel lAppModels;

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
		ProjectRenlaoAppModel appModel = (ProjectRenlaoAppModel) bundle.getSerializable(Common.BUNDEL_KEY);
		if (!StringUtil.isEmpty(appModel)) {
			tv_title.setText(Common.PROJECT_DETAIL);

			RequestParams rp = new RequestParams();
			rp.addBodyParameter("id", appModel.getId());
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
					URLs.NO_POOR_PROJECT_RENLAOJU_DETAIL, rp,
					new MyRequestCallBack(this, MyConstans.FIRST));
		}


		title_names = ResourceUtil.getInstance().getStringArrayById(R.array.renlaoju_title);
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

			ProjectRenlaoAppModelListResult listResult = ProjectRenlaoAppModelListResult.parseToT(str, ProjectRenlaoAppModelListResult.class);

			if (listResult != null && listResult.getSuccess()) {
				List<ProjectRenlaoAppModel> lAppModels = listResult.getJsondata();
				for (int i = 0; i < lAppModels.size(); i++) {
					this.lAppModels = lAppModels.get(i);
				}
				
				initPage(this.lAppModels);
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
	private void initPage(ProjectRenlaoAppModel lAppModels) {
		// TODO Auto-generated method stub
		RenLaoJu_XmxxFragment xmxxFragment = new RenLaoJu_XmxxFragment();
		Bundle bundle_xmxx = new Bundle();
		bundle_xmxx.putSerializable(Common.BUNDEL_KEY, lAppModels);
		xmxxFragment.setArguments(bundle_xmxx);
		
		RenLaoJu_PxryFragment pxryFragment = new RenLaoJu_PxryFragment();
		Bundle bundle_pxry = new Bundle();
		bundle_pxry.putSerializable(Common.BUNDEL_KEY, lAppModels);
		pxryFragment.setArguments(bundle_pxry);
		
		list.add(xmxxFragment);
		list.add(pxryFragment);

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
