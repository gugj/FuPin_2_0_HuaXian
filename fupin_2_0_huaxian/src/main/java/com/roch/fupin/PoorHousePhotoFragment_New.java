package com.roch.fupin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.PoorHousePhotoAdapter;
import com.roch.fupin.adapter.fragmentAdapter3;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.dialog.NormalDailog;
import com.roch.fupin.entity.Photo;
import com.roch.fupin.entity.PoorHousePhoto;
import com.roch.fupin.entity.PoorHousePhoto_ResultList;
import com.roch.fupin.entity.PoorPhoto;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 贫困户、贫困村照片fragment---通用
 * @author ZhaoDongShao
 * 2016年5月9日
 */
public class PoorHousePhotoFragment_New extends BaseFragment implements com.roch.fupin.adapter.fragmentAdapter3.OnClickListner, com.roch.fupin.adapter.fragmentAdapter3.OnLongClickListner {

	/**
	 * 按日期排序的ListView
	 */
	@ViewInject(R.id.datalist)
	ListView mGroupListView;

	@ViewInject(R.id.rl)
	RelativeLayout rl;
	@ViewInject(R.id.tv_notic)
	TextView tv_notic;

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
	 * 贫困户照片fragment中自定义的广播---点击拍照或选择照片上传成功后重新加载照片
	 */
	mybroadCastReceiver mybroadCastReceiver;

	/**
	 * 按日期排序的ListView的适配器
	 */
	private fragmentAdapter3 fragmentAdapter3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_poorhouse_photo_new, container, false);
		ViewUtils.inject(this,view);
		this.mContext = getActivity();
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
		intentFilter.addAction("photo_shangchuan");
		mybroadCastReceiver=new mybroadCastReceiver();
		getActivity().registerReceiver(mybroadCastReceiver, intentFilter);
	}

	@Override
	public void onClick(int position, int pos) {
//		Toast.makeText(getActivity(), "第" + position + "个条目的第" + pos + "项被点击", Toast.LENGTH_SHORT).show();
		int total=0;
		if(null!=poorHousePhotos && poorHousePhotos.size()>0){
				for (int i = 0; i < poorHousePhotos.size(); i++) {
					int guoqu=position-i-1;
					if(guoqu>=0){
						int zong=poorHousePhotos.get(guoqu).getLiam().size();
						total+=zong;
					}
				}
				total=total+pos;
			}
		System.out.println("点击照片的位置=="+total);
			//点击照片---加载照片详情
			imageBrower(total, lists);
	}

	int selectPosition,selectPos;
	@Override
	public void onLongClick(int position, int pos) {
//		Toast.makeText(getActivity(), "第" + position + "个条目的第" + pos + "项被长按", Toast.LENGTH_SHORT).show();
		int total=0;
		if(null!=poorHousePhotos && poorHousePhotos.size()>0){
			for (int i = 0; i < poorHousePhotos.size(); i++) {
				int guoqu=position-i-1;
				if(guoqu>=0){
					int zong=poorHousePhotos.get(guoqu).getLiam().size();
					total+=zong;
				}
			}
			total=total+pos;
		}
		selectPosition=position;
		selectPos=pos;
		//显示是否删除照片的对话框
		showDeleteDialog(total);
	}

	/**
	 * 贫困户照片fragment中自定义的广播---点击拍照或选择照片上传成功后重新加载照片
	 */
	class mybroadCastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			//请求服务器中的照片
			requestNetPhotoData();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().unregisterReceiver(mybroadCastReceiver);
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

	private void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
		Bundle bundle = getArguments();
		if(null!=bundle){
			houseHolderId=bundle.getString("houseHolderId");
			villageId=bundle.getString("villageId");
			type_poor_cun_hu=bundle.getString("type_poor_cun_hu");
			if("pinkuncun".equals(type_poor_cun_hu)){
				tv_notic.setText("还没有贫困村照片，去上传吧");
			}
		}
		//请求服务器中的照片
		requestNetPhotoData();
	}

//	@Override
//	public void onResume() {
//		super.onResume();
//		Bundle bundle = getArguments();
//		if(null!=bundle){
//			houseHolderId=bundle.getString("houseHolderId");
//			villageId=bundle.getString("villageId");
//			type_poor_cun_hu=bundle.getString("type_poor_cun_hu");
//		}
//		//请求服务器中的照片
//		requestNetPhotoData();
//	}

	/**
	 * 标志位---如果是"pinkuncun"即贫困村，如果是"pinkunhu"即贫困户
	 */
	String type_poor_cun_hu;
	/**
	 * 请求服务器中的照片
	 */
	private void requestNetPhotoData() {
		if("pinkunhu".equals(type_poor_cun_hu)){
			RequestParams rp = new RequestParams();
			rp.addBodyParameter("householderid", houseHolderId);
			// 通过post请求网络数据，请求参数为户IDhouseholderid
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
					URLs.POOR_HOUSE_Photo, rp,
					new MyRequestCallBack(this, MyConstans.FIRST));
			System.out.println("贫困户请求服务器中照片的网址为："+URLs.POOR_HOUSE_Photo+"?&householderid="+houseHolderId);
		}else if("pinkuncun".equals(type_poor_cun_hu)){
			RequestParams rp = new RequestParams();
			rp.addBodyParameter("id", villageId);
			// 通过post请求网络数据，请求参数为户IDhouseholderid
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
					URLs.POOR_Village_Photo, rp,
					new MyRequestCallBack(this, MyConstans.FIRST));
			System.out.println("贫困村请求服务器中照片的网址为："+URLs.POOR_Village_Photo+"?&=id"+villageId);
		}
	}

	String houseHolderId;
	String villageId;
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
						deletePosition = position;
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
	 * @param selectPostion
	 * @param selectPos
	 */
	private void deletePhoto(int deletePosition,int selectPostion,int selectPos) {
		poorHousePhotos.get(selectPostion).getLiam().remove(selectPos);
		if(poorHousePhotos.get(selectPostion).getLiam().size()<=0){
			poorHousePhotos.remove(selectPostion);
		}
		fragmentAdapter3.notifyDataSetChanged();
		lists.remove(deletePosition);
		if(lists.size()<=0){
			mGroupListView.setVisibility(View.GONE);
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
				URLs.POOR_House_Delete_Photo, rp,
				new MyRequestCallBack(this, MyConstans.SECOND));
	}

	List<PoorHousePhoto> poorHousePhotos;
	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				System.out.println("贫困户或贫困村请求服务中的照片成功==="+str);
				PoorHousePhoto_ResultList poorHousePhoto_resultList=PoorHousePhoto_ResultList.parseToT(str,PoorHousePhoto_ResultList.class);
				if(null!=poorHousePhoto_resultList){
					poorHousePhotos = poorHousePhoto_resultList.getJsondata();
					if(StringUtil.isNotEmpty(poorHousePhotos) && poorHousePhotos.size()>0){
						mGroupListView.setVisibility(View.VISIBLE);
						rl.setVisibility(View.GONE);

						fragmentAdapter3 = new fragmentAdapter3(mContext, poorHousePhotos);
						fragmentAdapter3.setOnClickListner(PoorHousePhotoFragment_New.this);
						fragmentAdapter3.setOnLongClickListner(PoorHousePhotoFragment_New.this);
						mGroupListView.setAdapter(fragmentAdapter3);

						if (lists != null && lists.size() > 0) {
							lists.clear();
						}
						Photo photo = null;
						for (int i = 0; i < poorHousePhotos.size(); i++) {
							List<PoorPhoto> liam = poorHousePhotos.get(i).getLiam();
							if(null!=liam && liam.size()>0){
								for (int j = 0; j < liam.size(); j++) {
									photo = new Photo();
									photo.setUrl(URLs.IMAGE_URL_New+liam.get(j).getImagepath());
									photo.setId(liam.get(j).getId());
									lists.add(photo);
								}
							}
						}
					}else {
						mGroupListView.setVisibility(View.GONE);
						rl.setVisibility(View.VISIBLE);
						if("pinkuncun".equals(type_poor_cun_hu)){
							tv_notic.setText("还没有贫困村照片，去上传吧");
						}
					}
				}
				break;

			case MyConstans.SECOND:
				ShowToast("照片删除成功");
				//删除照片
				deletePhoto(deletePosition,selectPosition,selectPos);
				break;
		}
	}

	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		switch (flag){
			case MyConstans.FIRST:
				System.out.println("贫困户或贫困村请求服务中的照片失败==="+str);
				break;

			case MyConstans.SECOND:
				ShowToast("照片删除失败");
				break;
		}
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
