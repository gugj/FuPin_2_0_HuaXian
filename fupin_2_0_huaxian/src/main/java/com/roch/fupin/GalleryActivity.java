package com.roch.fupin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.MyAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.photo.Bimp;
import com.roch.fupin.photo.PhotoView;
import com.roch.fupin.photo.ViewPagerFixed;
import com.roch.fupin.utils.Common;
import com.roch.fupin_2_0.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 这个是用于进行图片浏览时的界面
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:47:53
 */
@ContentView(R.layout.plugin_camera_gallery)
public class GalleryActivity extends MainBaseActivity {
	private Intent intent;

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	// 发送按钮
	@ViewInject(R.id.send_button)
	Button send_bt;

	private int position;
	private String id = "";
	//当前的位置
	private int location = 0;

	private ArrayList<View> listViews = null;
	@ViewInject(R.id.gallery01)
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();

	RelativeLayout photo_relativeLayout;
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);

		initToolbar();

		MyApplication.getInstance().addActivity(GalleryActivity.this);

		//		tv_back = (TextView) findViewById(R.id.tv_back);
		//		send_bt = (Button) findViewById(R.id.send_button);
		//		del_bt = (ImageView)findViewById(R.id.gallery_del);
		//		tv_back.setOnClickListener(new BackListener());
		send_bt.setOnClickListener(new GallerySendListener());
		//		del_bt.setOnClickListener(new DelListener());
		intent = getIntent();
		//		Bundle bundle = intent.getExtras();
		position = Integer.parseInt(intent.getStringExtra("position"));
		System.out.println(position+"");
		id = intent.getStringExtra(Common.INTENT_KEY);
		isShowOkBt();
		// 为发送按钮设置文字
		//		pager = (ViewPagerFixed) findViewById(R.id.gallery01);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < MyAdapter.mSelectedImage.size(); i++) {
			try {
				initListViews(Bimp.revitionImageSize(MyAdapter.mSelectedImage.get(i).getPath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin((int)getResources().getDimensionPixelOffset(R.dimen.ui_10_dip));
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	/**
	 *
	 *
	 * 2016年8月17日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initToolbar() {
		// TODO Auto-generated method stub
		tv_title.setVisibility(View.GONE);
		toolbar.setTitle("图片预览");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.photo_delete_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:

			intent.setClass(GalleryActivity.this, SelectPhotoActivity.class);
			intent.putExtra(Common.INTENT_KEY, id);
			startActivity(intent);
			MyApplication.getInstance().finishActivity(GalleryActivity.this);

			break;
		case R.id.delete:

			if (listViews.size() == 1) {
				MyAdapter.mSelectedImage.clear();
				Bimp.max = 0;
				send_bt.setText("完成(" + MyAdapter.mSelectedImage.size() + "/"+9+")");
				Intent intent = new Intent("data.broadcast.action");  
				sendBroadcast(intent);  
				finish();
			} else {
				MyAdapter.mSelectedImage.remove(location);
				Bimp.max--;
				pager.removeAllViews();
				listViews.remove(location);
				adapter.setListViews(listViews);
				send_bt.setText("完成(" + MyAdapter.mSelectedImage.size() + "/"+9+")");
				adapter.notifyDataSetChanged();
			}

			break;
		default:
			break;
		}
		return true;
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}

	//	// 返回按钮添加的监听器
	//	private class BackListener implements OnClickListener {
	//
	//		public void onClick(View v) {
	//			intent.setClass(GalleryActivity.this, SelectPhotoActivity.class);
	//			intent.putExtra(Common.INTENT_KEY, poorFamily);
	//			startActivity(intent);
	//			MyApplication.getInstance().finishActivity(GalleryActivity.this);
	//		}
	//	}
	//
	//	// 删除按钮添加的监听器
	//	private class DelListener implements OnClickListener {
	//
	//		public void onClick(View v) {
	//			if (listViews.size() == 1) {
	//				MyAdapter.mSelectedImage.clear();
	//				Bimp.max = 0;
	//				send_bt.setText("完成(" + MyAdapter.mSelectedImage.size() + "/"+9+")");
	//				Intent intent = new Intent("data.broadcast.action");  
	//				sendBroadcast(intent);  
	//				finish();
	//			} else {
	//				MyAdapter.mSelectedImage.remove(location);
	//				Bimp.max--;
	//				pager.removeAllViews();
	//				listViews.remove(location);
	//				adapter.setListViews(listViews);
	//				send_bt.setText("完成(" + MyAdapter.mSelectedImage.size() + "/"+9+")");
	//				adapter.notifyDataSetChanged();
	//			}
	//		}
	//	}

	// 完成按钮的监听
	private class GallerySendListener implements OnClickListener {
		public void onClick(View v) {
			//			finish();
			//			intent.setClass(mContext,MainActivity.class);
			//			startActivity(intent);
		}

	}





	public void isShowOkBt() {
		if (MyAdapter.mSelectedImage.size() > 0) {
			send_bt.setText("完成(" + MyAdapter.mSelectedImage.size() + "/" + 15 + ")");
			send_bt.setPressed(true);
			send_bt.setClickable(true);
			send_bt.setTextColor(Color.WHITE);
		} else {
			send_bt.setPressed(false);
			send_bt.setClickable(false);
			send_bt.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	/**
	 * 监听返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			this.finish();
			intent.setClass(GalleryActivity.this, SelectPhotoActivity.class);
			intent.putExtra(Common.INTENT_KEY, id);
			startActivity(intent);
		}
		return true;
	}





	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;
		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
