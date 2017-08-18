package com.roch.fupin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roch.fupin.adapter.MyAdapter;
import com.roch.fupin.adapter.MyAdapter.OnItemClickListener;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.dialog.ListImageDirPopupWindow;
import com.roch.fupin.dialog.ListImageDirPopupWindow.OnImageDirSelected;
import com.roch.fupin.entity.ImageFloder;
import com.roch.fupin.entity.ImageItem;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin_2_0.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SelectPhotoActivity extends MainBaseActivity implements OnImageDirSelected,OnItemClickListener {

	private static final String TAG = "SelectPhotoActivity";
	/**
	 * 搜索手机中所有图片时的进度条
	 */
	private ProgressDialog mProgressDialog;

	private Intent intent;
	/**
	 * 存储文件夹中的图片数量
	 */
	private int mPicsSize;
	/**
	 * 手机中照片数量最多的文件夹---File
	 */
	private File mImgDir;
	/**
	 * 手机中照片数量最多的文件夹中的所有照片的集合
	 */
	private List<ImageItem> mImgs;

	/**
	 * 选择照片页面的GridView
	 */
	private GridView mGirdView;
	/**
	 * 选择照片页面的GridView的适配器
	 */
	private MyAdapter mAdapter;
	/**
	 * 临时的辅助类，用于防止同一个文件夹的多次扫描
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();

	Toolbar toolbar;
	/**
	 * 上传图片的服务器地址
	 */
	private String url = "";
	/**
	 * 贫困户houseHolderId或贫困村Id
	 */
	private String id = "";

	/**
	 * 手机中所有图片的文件夹的集合
	 */
	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();
	/**
	 * 所有图片的容器
	 */
	private RelativeLayout mBottomLy;
	/**
	 * 所有图片按钮---TextView
	 */
	private TextView mChooseDir;
	/**
	 * 手机中所有照片的数量
	 */
	private TextView mImageCount;
	/**
	 * 预览按钮
	 */
	private TextView mYuLanTextView;
	private TextView tv_title;
	/**
	 * toolbar中的选择照片完成按钮
	 */
	private TextView mSelectPhoto;
	/**
	 * 手机中所有文件夹照片的总张数
	 */
	int totalCount = 0;

	private int mScreenHeight;

	/**
	 * 自定义的展示照片ListView的PopupWindow，继承自定义的PopupWindow---BasePopupWindowForListView，父类里有3个抽象方法1.initView、2.initEvent、3.init
	 */
	private ListImageDirPopupWindow mListImageDirPopupWindow;

	/**
	 * 扫描手机中所有照片完成后调用此方法
	 */
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mProgressDialog.dismiss();
			// 设置选择照片Gridview的适配器，并设置所有图片、预览按钮、上传按钮的初始化状态
			data2View();
			// 设置所有图片按钮点击弹出PopupWindow的监听
			initListDirPopupWindw();
		}
	};

	/**
	 * 设置选择照片Gridview的适配器，并设置所有图片、预览按钮、上传按钮的初始化状态
	 */
	private void data2View() {
		if (mImgDir == null) {
			Toast.makeText(getApplicationContext(), "一张图片没扫描到", Toast.LENGTH_SHORT).show();
			return;
		}
		List<String> list = Arrays.asList(mImgDir.list());
		mImgs = new ArrayList<ImageItem>();
		for (int i = 0; i < list.size(); i++) {
			mImgs.add(new ImageItem(list.get(i), false));
		}
		Collections.reverse(mImgs);
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new MyAdapter(getApplicationContext(), mImgs, R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		mImageCount.setText(totalCount + "张");
		//给适配器设置条目点击监听
		mAdapter.setOnItemClickListener(this);
		//设置完成和预览显示的照片数量
		initText();
	};

	/**
	 * 初始化展示文件夹的popupWindw
	 */
	@SuppressLint("InflateParams")
	private void initListDirPopupWindw() {
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT,
				(int) (mScreenHeight * 0.7),
				mImageFloders,
				LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_dir, null));

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_select);

		MyApplication.getInstance().addActivity(mActivity);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;

		//1.初始化View和toolbar，并设置选择照片完成的点击监听
		initView();
		//2.利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
		getImages();
		//3.通过intent获取传输的数据，并设置选择图片完成、所有图片、预览图片的点击监听
		initEvent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		toolbar.setTitle("选择照片");
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 2.利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */
	private void getImages() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable() {
			@Override
			public void run() {
				String firstImage = null;
				//获取手机系统提供照片查询的uri
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				//获取内容提供者
				ContentResolver mContentResolver = SelectPhotoActivity.this.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri,
								null,
								MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
								new String[] { "image/jpeg", "image/png" },
								MediaStore.Images.Media.DATE_MODIFIED);
//				Log.e("System.out", "手机中内容提供者提供的照片数量：==="+mCursor.getCount());

				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
//					Log.e("System.out", "手机中内容提供者提供的照片路径：==="+path);
					// 拿到第一张图片的路径
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath(); //获取每一张图片的父文件的绝对路径
					ImageFloder imageFloder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
					if (mDirPaths.contains(dirPath)) {
						continue;
					} else {
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}
					//获取手机中每一个文件夹中的照片数量
					int picSize = parentFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String filename) {
							if (
									filename.endsWith(".jpg") ||
											filename.endsWith(".png") ||
											filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					}).length;
					totalCount += picSize; //获取照片的张数
					//设置该文件夹中的照片数量
					imageFloder.setCount(picSize);
					mImageFloders.add(imageFloder);

					if (picSize > mPicsSize) {
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();

				// 扫描完成，辅助的HashSet也就可以释放内存了
				mDirPaths = null;
				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);
			}
		}).start();
	}

	/**
	 * 1.初始化View和toolbar，并设置选择照片完成的点击监听
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		mGirdView = (GridView) findViewById(R.id.id_gridView);
		mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
		mImageCount = (TextView) findViewById(R.id.id_total_count);
		mSelectPhoto = (TextView) findViewById(R.id.tv_sure);
		mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
		mYuLanTextView = (TextView) findViewById(R.id.id_yulan_count);
		//title标题隐藏
		tv_title.setVisibility(View.GONE);
		//显示上传按钮字样
		mSelectPhoto.setVisibility(View.VISIBLE);
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}
	}

	/**
	 * 点击toolbar上的返回按钮时清空选择照片的集合并推退出
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			MyAdapter.mSelectedImage.clear();
			initText();
			MyApplication.getInstance().finishActivity(this);
			break;

		default:
			break;
		}
		return true;
	}

	/**
	 * 当点击返回按钮时清空选择照片的集合并退出
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			MyAdapter.mSelectedImage.clear();
			initText();
			MyApplication.getInstance().finishActivity(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 标志位---如果是"hu"为贫困户选择照片，如果是"cun"为贫困村选择照片
	 */
	String shangchuan_type="";

	/**
	 * 通过intent获取传输的数据，并设置选择图片完成、所有图片、预览图片的点击监听
	 */
	private void initEvent() {
		Intent it = getIntent();
		if (it != null) {
			url = it.getStringExtra(Common.UP_LOAD_PHOTO_KEY);
			id = it.getStringExtra(Common.INTENT_KEY);
			shangchuan_type=it.getStringExtra("shangchuan_type");
		}
		//选择照片完成按钮设置点击监听
		mSelectPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<String> photos = new ArrayList<String>();
				for (int i = 0; i < MyAdapter.mSelectedImage.size(); i++) {
					photos.add(MyAdapter.mSelectedImage.get(i).getPath());
					Log.i(TAG, MyAdapter.mSelectedImage.get(i).getPath());
				}
				//选择照片完成后进入上传页面
				intent = new Intent();
				intent.setClass(mContext, UploadPhotoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putStringArrayList(Common.BUNDEL_KEY, (ArrayList<String>) photos);
				bundle.putString(Common.TITLE_KEY, id);
				bundle.putString(Common.UP_LOAD_PHOTO_KEY, url);
				bundle.putString("shangchuan_type", shangchuan_type);
				intent.putExtra(Common.INTENT_KEY, bundle);
				startActivity(intent);
				//点击选择照片完成按钮后，清空选择照片的集合
				MyAdapter.mSelectedImage.clear();
				initText();
			}
		});
		//为底部的布局设置点击事件，弹出popupWindow
		mBottomLy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});
		//为预览按钮增加点击事件
		mYuLanTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MyAdapter.mSelectedImage.size() > 0) {
					intent = new Intent();
					intent.putExtra("position", "1");
					intent.putExtra(Common.INTENT_KEY, id);
					intent.setClass(SelectPhotoActivity.this, GalleryActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
	}

//	@Override
//	public void onSuccessResult(String str, int flag) {
//		super.onSuccessResult(str, flag);
//		switch (flag) {
//		case MyConstans.FIRST:
//			System.out.println("正在上传...");
//			break;
//
//		default:
//			break;
//		}
//	}

	/**
	 * 展示照片的ListView的文件夹被选中
	 * @param floder
	 */
	@Override
	public void onImageDirSelected(ImageFloder floder) {
		mImgDir = new File(floder.getDir());
		List<String> list = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (
						filename.endsWith(".jpg") ||
								filename.endsWith(".png") ||
								filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		mImgs = new ArrayList<ImageItem>();
		for (int i = 0; i < list.size(); i++) {
			mImgs.add(new ImageItem(list.get(i), false));
		}

		Collections.reverse(mImgs);
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new MyAdapter(getApplicationContext(), mImgs, R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		mImageCount.setText(floder.getCount() + "张");
		mChooseDir.setText(floder.getName());
		mListImageDirPopupWindow.dismiss();
		//给适配器设置条目点击监听
		mAdapter.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(int position) {
		if (MyAdapter.mSelectedImage.size() >= 15) {
			Toast.makeText(SelectPhotoActivity.this, "最多不能超过15张", Toast.LENGTH_SHORT).show();
			mSelectPhoto.setText("完成"+"(" + MyAdapter.mSelectedImage.size() + "/"+ 15 +")");
			mYuLanTextView.setText("预览(" + MyAdapter.mSelectedImage.size() + ")");
			return;
		}else {
			//设置完成和预览显示的照片数量
			initText();
		}
	}

	/**
	 * 设置完成和预览显示的照片数量
	 * 2016年6月22日
	 * ZhaoDongShao
	 */
	private void initText() {
		if (MyAdapter.mSelectedImage.size() <= 0) {
			mSelectPhoto.setEnabled(false);
			mSelectPhoto.setTextColor(ResourceUtil.getInstance().getColorById(R.color.gainsboro));
			mYuLanTextView.setEnabled(false);
			mYuLanTextView.setTextColor(ResourceUtil.getInstance().getColorById(R.color.gainsboro));
		}else {
			mSelectPhoto.setEnabled(true);
			mSelectPhoto.setTextColor(ResourceUtil.getInstance().getColorById(R.color.white));
			mYuLanTextView.setEnabled(true);
			mYuLanTextView.setTextColor(ResourceUtil.getInstance().getColorById(R.color.white));
		}
		mSelectPhoto.setText("完成"+"(" + MyAdapter.mSelectedImage.size() + "/"+ 15 +")");
		mYuLanTextView.setText("预览(" + MyAdapter.mSelectedImage.size() + ")");
	}

}
