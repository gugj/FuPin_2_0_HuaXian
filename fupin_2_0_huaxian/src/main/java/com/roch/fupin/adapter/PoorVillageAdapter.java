package com.roch.fupin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.entity.PoorVillageBase;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.FileUtils;
import com.roch.fupin.view.CircleImageView;
import com.roch.fupin_2_0.R;

import java.util.List;

/**
 * 贫困村适配器
 * @author ZhaoDongShao
 * 2016年5月24日
 */
public class PoorVillageAdapter extends BaseAdapter{

	Context mContext;

	List<PoorVillageBase> poorVillages;

	BitmapUtils utils;

	FileUtils fileUtil;

	public PoorVillageAdapter(Context mContext,List<PoorVillageBase> poorVillages) {
		this.mContext = mContext;
		this.poorVillages = poorVillages;

		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cache = maxMemory / 8;
		fileUtil = new FileUtils(mContext, Common.CACHE_DIR);
		utils = new BitmapUtils(mContext, fileUtil.getCacheDir(), cache);
		//  /storage/emulated/0/Android/data/com.roch.fupin_2_0/cache
//		System.out.println("贫困村list图片设置设置的缓存路径为："+fileUtil.getCacheDir());
		//  /storage/emulated/0/Android/data/com.roch.fupin_2_0/cache/xBitmapCache
//		MyApplication.getBitmapUtilsInstance();
	}

	@Override
	public int getCount() {
		if (poorVillages != null) {
			return poorVillages.size();
		}
		return 0;
	}

	@Override
	public PoorVillageBase getItem(int position) {
		return poorVillages != null ? poorVillages.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		View view = convertView;
		if (view == null) {
			holderView = new HolderView();
			view = LayoutInflater.from(mContext).inflate(R.layout.poorvillage_itemview, parent, false);
			ViewUtils.inject(holderView,view);
			
			LayoutParams lParams = holderView.iv_photo.getLayoutParams();

			lParams.width = Common.Width / 5;
			lParams.height = Common.Width / 5;

			holderView.iv_photo.setLayoutParams(lParams);
			view.setTag(holderView);
		}else {
			holderView = (HolderView)view.getTag();
		}

		final PoorVillageBase poorHouse = getItem(position);
		if (poorHouse != null) {
			if (poorHouse.getPicturePath()!=null && !poorHouse.getPicturePath().equals("")) {
//				utils.configDiskCacheFileNameGenerator(new FileNameGenerator() {
//					@Override
//					public String generate(String key) {
//						return position+"";
//					}
//				});
//				File bitmapFileFromDiskCache = utils.getBitmapFileFromDiskCache(fileUtil.getCacheDir()+"/"+position+"");
//				if(bitmapFileFromDiskCache!=null){
//					System.out.println("使用了缓存");
//				}else {
//					System.out.println("没有使用缓存，从网络上加载");
//				}
				utils.display(holderView.iv_photo, poorHouse.getPicturePath());
			}
			holderView.tv_address.setText(poorHouse.getCountryname()+poorHouse.getTownName());
			holderView.tv_name.setText(poorHouse.getVillagename());
			String people_count = "共有"+poorHouse.getPopulationnumber()+"人";
			holderView.tv_people_count.setText(people_count);
			String poor_count = "贫困户"+poorHouse.getPoorhousenm()+"户";
			holderView.tv_poor_count.setText(poor_count);

			holderView.tv_phone.setText(poorHouse.getPersonphone());
			holderView.tv_zeren_people.setText(poorHouse.getPersonname());
//			holderView.tv_phone.setText(poorHouse.getSecretaryphone());
//			holderView.tv_zeren_people.setText(poorHouse.getSecretaryname());

			holderView.tv_zhiwei.setText("");
		}
		return view;
	}

	class HolderView{
		@ViewInject(R.id.tv_poor_count)
		TextView tv_poor_count;//贫困人数

		@ViewInject(R.id.tv_people_count)
		TextView tv_people_count;//总人数

		@ViewInject(R.id.iv_user_head)
		CircleImageView iv_photo;

		@ViewInject(R.id.tv_phone)
		TextView tv_phone;//电话
		@ViewInject(R.id.tv_zeren_people)
		TextView tv_zeren_people;//责任人

		@ViewInject(R.id.tv_name)
		TextView tv_name;//村名
		@ViewInject(R.id.tv_address)
		TextView tv_address;//所在乡
		
		@ViewInject(R.id.tv_zhiwei)
		TextView tv_zhiwei;
	}


	/**
	 * 增加刷新后的数据
	 * 2016年5月17日
	 * ZhaoDongShao
	 */
	public void addList(List<PoorVillageBase> list) {
		this.poorVillages.addAll(list);
		notifyDataSetChanged();
	}

	/**
	 * 刷新数据
	 * @param lPoorHouses
	 * 2016年6月13日
	 * ZhaoDongShao
	 */
	public void onRefsh(List<PoorVillageBase> lPoorHouses) {
		this.poorVillages.clear();
		this.poorVillages.addAll(lPoorHouses);
		notifyDataSetChanged();
	}

}
