package com.roch.fupin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.ViewPagerAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.dialog.AddPopWindow;
import com.roch.fupin.dialog.AddPopWindow.ShowMessageListener;
import com.roch.fupin.entity.ListMenu;
import com.roch.fupin.entity.PoorFamilyPhoto;
import com.roch.fupin.entity.PoorVillage;
import com.roch.fupin.entity.PoorVillageBase;
import com.roch.fupin.entity.PoorVillageListDetailResult;
import com.roch.fupin.entity.PoorVillageListResult;
import com.roch.fupin.utils.BitmapUtil;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.LogUtil;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author ZhaoDongShao
 * 2016年5月24日 
 */
@ContentView(R.layout.activity_poorhouse_detail)
public class PoorVillageDetailActivity extends MainBaseActivity implements ShowMessageListener, View.OnClickListener {

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.tv_jiahao)
	TextView tv_jiahao;
	@ViewInject(R.id.tv_jiahao_lingfushuoming)
	TextView tv_jiahao_lingfushuoming;
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
	Bundle bundle = null;

	//进度条
	private ProgressDialog progressDialog;
	private static final int PRO = 0;
	private static final int MAX_PROGRESS=100;  
	private int progress=10;  

	//照片的路径
	private String photo = null;
	private Uri imageUri = null;

	private static final int CROP = 1;

	PoorVillageBase poorVillageBase = null;
	/**
	 * 贫困村详情activity中自定义的广播---选择照片上传成功后再次请求服务器
	 */
	mybroadCastReceiver mybroadCastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		//初始化toolbar，并设置 + 号的点击监听
		initToolbar();
		MyApplication.getInstance().addActivity(mActivity);
		//初始化贫困村page页面，并请求服务的贫困村详情数据
		initView();
		//注册广播---选择照片上传成功后再次请求服务器
		registerBroadCast();
	}

	/**
	 * 注册广播---选择照片上传成功后再次请求服务器
	 */
	private void registerBroadCast() {
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("photo_shangchuan_cun");
		mybroadCastReceiver=new mybroadCastReceiver();
		registerReceiver(mybroadCastReceiver, intentFilter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_jiahao: //点击加号时
				int xPox = (int)(Common.Width * 0.7 - 10);
				AddPopWindow window = new AddPopWindow(mActivity,1);
				window.setShowMessageListener(PoorVillageDetailActivity.this);
				window.showPopupWindow(toolbar, xPox);
				break;

			case R.id.tv_jiahao_lingfushuoming: //点击加号时---另附说明
//				int xPox2 = (int)(Common.Width * 0.7 - 10);
//				AddPopWindow window2 = new AddPopWindow(mActivity,2);
//				window2.setShowMessageListener(PoorVillageDetailActivity.this);
//				window2.showPopupWindow(toolbar, xPox2);
				Intent intent = new Intent(mContext, LingFuShuoMingDetailActivity.class);
				intent.putExtra("type_lingfushuoming", "pinkuncun");
				intent.putExtra("type_lingfushuoming_zengjia", true);
				intent.putExtra("houseid", poorVillageBase.getId());
				startActivity(intent);
				break;

			default:
				break;
		}
	}

	/**
	 * 贫困村详情activity中自定义的广播---选择照片上传成功后再次请求服务器
	 */
	class mybroadCastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			RequestParams rp = new RequestParams();
			rp.addBodyParameter("id", poorVillageBase.getId());
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
					URLs.POOR_VILLAGE_DETAIL, rp,
					new MyRequestCallBack(PoorVillageDetailActivity.this, MyConstans.THIRD));
		}
	}

	/**
	 * 初始化toolbar，并设置 + 号的点击监听
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
		tv_jiahao.setOnClickListener(this);
		tv_jiahao_lingfushuoming.setOnClickListener(this);
	}

	/**
	 * 点击返回按钮时关闭activity
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

	/**
	 * 初始化贫困村page页面，并请求服务的贫困村详情数据
	 * 2016年5月9日
	 * ZhaoDongShao
	 */
	private void initView() {
		Intent intent = getIntent();
		bundle = intent.getBundleExtra(Common.INTENT_KEY);
		poorVillageBase = (PoorVillageBase) bundle.getSerializable(Common.BUNDEL_KEY);
		if (!StringUtil.isEmpty(poorVillageBase)) {
			tv_title.setText(poorVillageBase.getVillagename());
//			RequestParams rp = new RequestParams();
//			rp.addBodyParameter("id", poorVillageBase.getId());
//			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
//					URLs.POOR_VILLAGE_DETAIL, rp,
//					new MyRequestCallBack(this, MyConstans.FIRST));
//			System.out.println("贫困村详情界面请求服务器的网址为：==="+URLs.POOR_VILLAGE_DETAIL);
			
			RequestParams rp2 = new RequestParams();
			rp2.addBodyParameter("id", poorVillageBase.getId());
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
					URLs.POOR_VILLAGE_DETAIL_2, rp2,
					new MyRequestCallBack(this, MyConstans.SECOND));
			System.out.println("贫困村详情界面请求服务器的网址为：===" + URLs.POOR_VILLAGE_DETAIL_2);
		}

		layout_title_name.setVisibility(View.VISIBLE);
		title_names = ResourceUtil.getInstance().getStringArrayById(R.array.tab_village_title);
		for (int i = 0; i < title_names.length; i++) {
			final TextView tv_title_name = new TextView(mContext);
			tv_title_name.setGravity(Gravity.CENTER);
			tv_title_name.setPadding(5, 5, 5, 5);
			tv_title_name.setTextSize(Common.TEXT_SIZE);
			tv_title_name.setId(i);
			tv_title_name.setText(title_names[i]);
			layout_title_name.addView(tv_title_name, Common.Width / 4,LinearLayout.LayoutParams.WRAP_CONTENT);

			listviews.add(tv_title_name);
			tv_title_name.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
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
		super.onSuccessResult(str, flag);
		switch (flag) {
		case MyConstans.FIRST:
			System.out.println("贫困村详情界面请求服务器数据成功：==="+str);
			PoorVillageListDetailResult poorVillageListDetailResult = PoorVillageListDetailResult.parseToT(str, PoorVillageListDetailResult.class);
			if (poorVillageListDetailResult != null) {
				if (poorVillageListDetailResult.getSuccess()) {
					List<PoorVillage> lVillages = poorVillageListDetailResult.getJsondata();
					if (!StringUtil.isEmpty(lVillages) && lVillages.size() > 0) {
//						PoorVillage poorVillage=lVillages.get(0);
//						setBundle(poorVillage);
//						for (PoorVillage poorVillage : lVillages) {
//							if (!StringUtil.isEmpty(poorVillage)) {
////								initPage(poorVillage);
//							}
//						}
					}
				} else {
					ShowNoticDialog();
				}
			}
			break;
			
		case MyConstans.SECOND:
			System.out.println("贫困村详情界面请求服务器数据成功：==="+str);
			PoorVillageListResult poorVillageListResult=PoorVillageListResult.parseToT(str, PoorVillageListResult.class);
			if(null!=poorVillageListResult){
				if(poorVillageListResult.getSuccess()){
					List<PoorVillageBase> poorVillageBases=poorVillageListResult.getJsondata();
					if(null!=poorVillageBases){
						initPage(null,poorVillageBases);
					}
				}
			}
			break;

			case MyConstans.THIRD:
				System.out.println("贫困村请求服务器选择照片上传成功：==="+str);
				PoorVillageListDetailResult poorVillageListDetailResult1 = PoorVillageListDetailResult.parseToT(str, PoorVillageListDetailResult.class);
				if (poorVillageListDetailResult1 != null) {
					if (poorVillageListDetailResult1.getSuccess()) {
						List<PoorVillage> lVillages = poorVillageListDetailResult1.getJsondata();
						if (!StringUtil.isEmpty(lVillages) && lVillages.size() > 0) {
							List<PoorFamilyPhoto> poorFamilyPhotos=lVillages.get(0).getLe();

							Intent intent=new Intent();
							intent.setAction("photo_shangchuan2");
							intent.putExtra("poorFamilyPhotos", (Serializable) poorFamilyPhotos);
							sendBroadcast(intent);
						}
					} else {
						ShowNoticDialog();
					}
				}
				break;

		default:
			break;
		}
	}

	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		System.out.println("贫困村详情界面请求服务器数据失败：==="+str);
		showToast(str);
	}

	/**
	 * @param poorVillage
	 * 2016年6月12日
	 * ZhaoDongShao
	 */
	@SuppressWarnings("deprecation")
	private void initPage(PoorVillage poorVillage,List<PoorVillageBase> poorVillageBases) {
		PoorVillageBaseFragment baseFragment = new PoorVillageBaseFragment();
		bundle = new Bundle();
		bundle.putSerializable(Common.BUNDEL_KEY, poorVillageBases.get(0));//poorVillage.getPn()
		baseFragment.setArguments(bundle);
		
//		PoorVillageAccountPrintFragment printFragment = new PoorVillageAccountPrintFragment();
//		bundle = new Bundle();
//		bundle.putSerializable(Common.BUNDEL_KEY, poorVillage.getPt());
//		printFragment.setArguments(bundle);

		//3-1.另附说明fragment
		LingFuShuoMingFragment_New lingFuShuoMingFragment = new LingFuShuoMingFragment_New();
		bundle = new Bundle();
		bundle.putString(Common.TITLE_KEY, poorVillageBase.getId()); //获取村ID
		bundle.putString("type_lingfushuoming","pinkuncun");
		bundle.putString("qingkuangshuoming",poorVillageBases.get(0).getVillageinfo());
		lingFuShuoMingFragment.setArguments(bundle);

//		PoorVillagePhotoFragment photoFragment = new PoorVillagePhotoFragment();
		PoorHousePhotoFragment_New photoFragment = new PoorHousePhotoFragment_New();
		bundle = new Bundle();
//		bundle.putSerializable(Common.BUNDEL_KEY, (Serializable) poorVillage.getLe());
		bundle.putSerializable(Common.BUNDEL_KEY,poorVillageBase);
		bundle.putString("villageId", poorVillageBase.getId());
		bundle.putString("type_poor_cun_hu", "pinkuncun");
		photoFragment.setArguments(bundle);
		
		list.add(baseFragment);
//		list.add(printFragment);
		list.add(lingFuShuoMingFragment);
		list.add(photoFragment);
		viewPagerAdapter = new ViewPagerAdapter(list, PoorVillageDetailActivity.this);
//		viewPagerAdapter.setTitle(title_names);
		//默认显示第一页
		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
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
				if(currPage==2){
					tv_jiahao.setVisibility(View.VISIBLE);
				}else {
					tv_jiahao.setVisibility(View.GONE);
				}
				if(currPage==1){
					tv_jiahao_lingfushuoming.setVisibility(View.VISIBLE);
				}else {
					tv_jiahao_lingfushuoming.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
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

	@Override
	public void Message(Object object) {
		ListMenu menu = (ListMenu)object;
		if (menu.getName().equals("照片")) { //点击选则照片上传
			Intent intent = new Intent(mContext, SelectPhotoActivity.class);
			intent.putExtra(Common.INTENT_KEY, poorVillageBase.getId());
			intent.putExtra("shangchuan_type", "hu");
			intent.putExtra(Common.UP_LOAD_PHOTO_KEY, URLs.VILLAGE_IMAGE_UP_LOAD);
			startActivity(intent);
		}else if (menu.getName().equals("拍照")) { //点击拍照上传
			Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			photo = Common.CACHE_DIR + "/" + UUID.randomUUID().toString() + ".jpg";
			Log.i("photo", photo);
			imageUri = Uri.fromFile(new File(photo));
			Log.i("imageuri", imageUri.getHost());
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intentFromCapture, CROP);
		}else if(menu.getName().equals("添加")){
			Intent intent = new Intent(mContext, LingFuShuoMingDetailActivity.class);
			intent.putExtra("type_lingfushuoming", "pinkuncun");
			intent.putExtra("type_lingfushuoming_zengjia", true);
			intent.putExtra("houseid", poorVillageBase.getId());
			startActivity(intent);
		}
	}

	@SuppressLint("HandlerLeak")
	Handler handler=new Handler(){  
		@Override  
		public void handleMessage(Message msg) {  
			super.handleMessage(msg);  
			switch (msg.what) {  
			case PRO: //拍照并上传服务器成功后，再次请求服务器中的贫困村照片
//				if (!StringUtil.isEmpty(poorVillageBase)) {
//					RequestParams rp = new RequestParams();
//					rp.addBodyParameter("id", poorVillageBase.getId());
//					MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
//							URLs.POOR_VILLAGE_DETAIL, rp,
//							new MyRequestCallBack(PoorVillageDetailActivity.this, MyConstans.THIRD));
//				}
				Intent intent=new Intent();
				intent.setAction("photo_shangchuan");
//				intent.putExtra("poorFamilyPhotos", (Serializable) poorFamilyPhotos);
				sendBroadcast(intent);

				if(progress>=MAX_PROGRESS){  
					//重新设置  
					progress=0;  
					progressDialog.dismiss();//销毁对话框  
					showToast("照片上传成功");
				} 
				break;

			case 1:
				progressDialog.dismiss();//销毁对话框
				showToast("照片上传失败");
				break;

			default:  
				break;  
			}  
		}  
	};  

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case CROP:
				if (imageUri == null) {
					imageUri = data.getData();
				}
				if (MyApplication.getInstance().getNetConnectInstance().ischeackNet(mContext)) {
					getImageToView(data);
					if (TextUtils.isEmpty(photo)) {
						showToast("剪切照片失败...");
						return;
					}

					progressDialog = new ProgressDialog(this);
					progressDialog.setIcon(R.drawable.ic_launcher);
					progressDialog.setTitle("提示");
					progressDialog.setMessage("正在上传照片...");
					progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					progressDialog.setMax(100);
					progressDialog.show();

					SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
					String loginUserId=sp.getString("loginUserId", "");
					LogUtil.println("上传照片时获取登陆用户的ID==" + loginUserId);

					HttpUtils httpUtils = new HttpUtils();
					RequestParams rp = new RequestParams();
					rp.addBodyParameter("userid", loginUserId);
					rp.addBodyParameter("householderid", poorVillageBase.getId());
					rp.addBodyParameter("file", new File(photo));
					//					String[] strings = photo.split("\\.");
					//					rp.addBodyParameter("fileSuffix", strings[1]);
					//					rp.addBodyParameter("username", "zhaodongshao");
					//					String url = "http://192.168.1.161:8080/upload/UploadServlet";

					httpUtils.configCurrentHttpCacheExpiry(1000 * 10);
					httpUtils.send(HttpMethod.POST, URLs.VILLAGE_IMAGE_UP_LOAD,
							rp, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							progress = 100;
							handler.sendEmptyMessage(PRO);
							System.out.println("上传成功");
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							handler.sendEmptyMessage(1);
							System.out.println("上传失败");
						}

						@Override
						public void onLoading(long total, long current, boolean isUploading) {
							int count = (int) ((current * 1.0 / total) * 100);
							//必须设置到show之后   上传进度显示
							progressDialog.setProgress(count);
							Log.i("上传 Progress>>>>>", "count=" + count + "--" + current + " / " + total);
							//
						}
					});
				}
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 保存照片
	 * @param data
	 * 2016年6月21日
	 * ZhaoDongShao
	 */
	private void getImageToView(Intent data) {
		try {
			Bundle extras = data.getExtras();
			if (extras != null) {
				if (Build.MODEL.equals("M9")) {
					@SuppressWarnings("unused")
					Drawable drawable = Drawable.createFromPath(photo);
				} else {
					Bitmap drawable = extras.getParcelable("data");
					File faceFile = BitmapUtil.saveBitmapTofile(drawable, photo);
					photo = faceFile.getAbsolutePath();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
//			showToast("抱歉，您的手机没有本地图库，无法保存图片!");
		}
	}

}
