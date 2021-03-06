package com.roch.fupin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.PoorHousePhotoAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.dialog.NormalDailog;
import com.roch.fupin.entity.Photo;
import com.roch.fupin.entity.PoorFamilyPhoto;
import com.roch.fupin.entity.PoorVillage;
import com.roch.fupin.entity.PoorVillageBase;
import com.roch.fupin.entity.PoorVillageListDetailResult;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhaoDongShao
 * 2016年5月24日
 */
public class PoorVillagePhotoFragment extends BaseFragment {

	@ViewInject(R.id.gv_photo)
	GridView gv_photo;
	@ViewInject(R.id.tv_notic)
	TextView tv_notic;
	@ViewInject(R.id.rl)
	RelativeLayout rl;
	List<Photo> lists = new ArrayList<Photo>();

	PoorHousePhotoAdapter mAdapter;
	Context mContext;

	/**
	 * 标志位，标志初始化已经完成
	 */
	private boolean isPrepared;

	/**
	 * 标识当前fragment是否可见
	 */
	private boolean isVisible;
	/**
	 * 贫困村照片fragment中自定义的广播---点击拍照或选择照片上传成功后重新加载照片
	 */
	mybroadCastReceiver mybroadCastReceiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_poorhouse_photo, container, false);
		ViewUtils.inject(this,view);
		this.mContext = getActivity();
		tv_notic.setText("还没有贫困村照片，去上传吧");
		//注册广播---点击拍照或选择照片上传成功后重新加载照片
		registerBroadCast();

		isPrepared = true;
		lazyLoad();
		return view;
	}

	/**
	 * 注册广播---点击拍照或选择照片上传成功后重新加载照片
	 */
	private void registerBroadCast() {
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("photo_shangchuan2");
		mybroadCastReceiver=new mybroadCastReceiver();
		getActivity().registerReceiver(mybroadCastReceiver,intentFilter);
	}

	/**
	 * 贫困村照片fragment中自定义的广播---点击拍照或选择照片上传成功后重新加载照片
	 */
	class mybroadCastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			List<PoorFamilyPhoto> poorFamilyPhotos= (List<PoorFamilyPhoto>) intent.getSerializableExtra("poorFamilyPhotos");
			//加载照片GridView
			initData(null,poorFamilyPhotos);
		}
	}

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

	boolean isFirstClick=true;
	private void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
		if(isFirstClick){
			//请求服务器的贫困村照片
			initNetData();
		}else {
		    initData(lVillages.get(0).getLe(),null);
		}
	}

	/**
	 * 贫困村对象
	 */
	PoorVillageBase poorVillageBase;
	/**
	 * 请求服务器的贫困村照片
	 */
	private void initNetData() {
		isFirstClick=false;
		Bundle bundle = getArguments();
		if (bundle == null) {
			ShowToast("服务器开小差了，请稍后再试");
			return;
		}

		poorVillageBase= (PoorVillageBase) bundle.getSerializable(Common.BUNDEL_KEY);
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("id", poorVillageBase.getId());
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
				URLs.POOR_VILLAGE_DETAIL, rp,
				new MyRequestCallBack(this, MyConstans.FIRST));
		System.out.println("贫困村照片fragment请求服务器的网址为：===" + URLs.POOR_VILLAGE_DETAIL);
	}

	/**
	 * 贫困村照片的集合getLe、基本信息getPn、和getPt
	 */
	List<PoorVillage> lVillages;
	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				System.out.println("贫困村照片fragment请求服务器数据成功：===" + str);
				PoorVillageListDetailResult poorVillageListDetailResult = PoorVillageListDetailResult.parseToT(str, PoorVillageListDetailResult.class);
				if (poorVillageListDetailResult != null) {
					if (poorVillageListDetailResult.getSuccess()) {
						lVillages = poorVillageListDetailResult.getJsondata();
						if (!StringUtil.isEmpty(lVillages) && lVillages.size() > 0) {
							for (PoorVillage poorVillage : lVillages) {
								if (!StringUtil.isEmpty(poorVillage)) {
									//加载照片GridView
									initData(poorVillage.getLe(),null);
								}
							}
						}
					} else {
						ShowNoticDialog();
					}
				}
				break;

			case MyConstans.SECOND:
				//删除照片
				deletePhoto(deletePosition);
				ShowToast("照片删除成功");
				break;
		}
	}

	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				break;

			case MyConstans.SECOND:
				ShowToast("照片删除失败");
				break;
		}
	}

	/**
	 * 加载照片GridView
	 * 2016年5月10日
	 * ZhaoDongShao
	 * @param lFamilyIncomes   进入贫困村fragment后请求服务器的照片集合
	 * @param poorFamilyPhotos 广播中接收到的选择照片或拍照并请求服务器的照片集合
	 */
	private void initData(List<PoorFamilyPhoto> lFamilyIncomes,List<PoorFamilyPhoto> poorFamilyPhotos) {
		Photo photo = null;
		if(null==poorFamilyPhotos){
			if (lFamilyIncomes != null && lFamilyIncomes.size() <= 0) {
				gv_photo.setVisibility(View.GONE);
				rl.setVisibility(View.VISIBLE);
			}else {
				gv_photo.setVisibility(View.VISIBLE);
				rl.setVisibility(View.GONE);
				if (lists != null && lists.size() > 0) {
					lists.clear();
				}
				if(null!=lFamilyIncomes){
					for (PoorFamilyPhoto poorFamilyPhoto : lFamilyIncomes) {
						photo = new Photo();
						photo.setUrl(poorFamilyPhoto.getImagepath());
						photo.setId(poorFamilyPhoto.getId());
						lists.add(photo);
					}
				}

				mAdapter = new PoorHousePhotoAdapter(mContext, lists);
				gv_photo.setAdapter(mAdapter);

				gv_photo.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						//点击照片---加载照片详情
						imageBrower(position, lists);
					}
				});
				gv_photo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						//显示是否删除照片的对话框
						showDeleteDialog(position);
						return true;
					}
				});
			}
		}else {
			if (poorFamilyPhotos != null && poorFamilyPhotos.size() <= 0) {
				gv_photo.setVisibility(View.GONE);
				rl.setVisibility(View.VISIBLE);
			}else {
				gv_photo.setVisibility(View.VISIBLE);
				rl.setVisibility(View.GONE);
				if (lists != null && lists.size() > 0) {
					lists.clear();
				}
				if(null!=poorFamilyPhotos){
					for (PoorFamilyPhoto poorFamilyPhoto : poorFamilyPhotos) {
						photo = new Photo();
						photo.setUrl(poorFamilyPhoto.getImagepath());
						photo.setId(poorFamilyPhoto.getId());
						lists.add(photo);
					}
				}

				mAdapter = new PoorHousePhotoAdapter(mContext, lists);
				gv_photo.setAdapter(mAdapter);

				gv_photo.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						//点击照片---加载照片详情
						imageBrower(position, lists);
					}
				});
				gv_photo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						//显示是否删除照片的对话框
						showDeleteDialog(position);
						return true;
					}
				});
			}
		}
	}

	int deletePosition;
	/**
	 * 显示是否删除照片的对话框
	 */
	private void showDeleteDialog(final int position) {
		final NormalDailog normalDailog=new NormalDailog(getActivity(),R.style.popup_dialog_style);
		normalDailog.show();
		normalDailog.setTitleText("删除提示");
		normalDailog.setContentText("确定要删除这张照片吗？");
		normalDailog.setOnClickLinener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.normal_dialog_done: //确定
						//请求服务器删除照片
						requestNetDeletePhoto(position);
						deletePosition=position;
						normalDailog.dismiss();
						break;

					case R.id.normal_dialog_cancel: //取消
						normalDailog.dismiss();
						break;
				}
			}
		});
	}

	/**
	 * 删除照片
	 * @param position
	 */
	private void deletePhoto(int position) {
		lists.remove(position);
		mAdapter.notifyDataSetChanged();
		if(lists.size()<=0){
			gv_photo.setVisibility(View.GONE);
			rl.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 请求服务器删除照片
	 */
	private void requestNetDeletePhoto(int position) {
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("id", lists.get(position).getId());
		MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
				URLs.POOR_Village_Delete_Photo, rp,
				new MyRequestCallBack(this, MyConstans.SECOND));
	}

	/**
	 * 点击照片---加载照片详情
	 * @param position
	 * @param urls
	 * 2016年5月10日
	 * ZhaoDongShao
	 */
	protected void imageBrower(int position, List<Photo> urls) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(ImagePagerActivity.EXTRA_IMAGE_URLS, (Serializable)urls);
		bundle.putInt(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS_KEY, bundle);
		startActivity(intent);
	}

}
