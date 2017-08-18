package com.roch.fupin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.entity.LingFuShuoMing;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin_2_0.R;

import java.util.List;

public class LingFuShuoMingAdapter extends BaseAdapter{

	Context mContext;
	List<LingFuShuoMing> lingFuShuoMings;

	public LingFuShuoMingAdapter(Context mContext, List<LingFuShuoMing> lingFuShuoMings) {
		this.mContext = mContext;
		this.lingFuShuoMings = lingFuShuoMings;
	}

	@Override
	public int getCount() {
		if (lingFuShuoMings != null) {
			return lingFuShuoMings.size();
		}
		return 0;
	}

	@Override
	public LingFuShuoMing getItem(int position) {
		return lingFuShuoMings != null ? lingFuShuoMings.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		View view = convertView;
		if (view == null) {
			holderView = new HolderView();
			view = LayoutInflater.from(mContext).inflate(R.layout.listview_lingfushuoming_list_item, parent, false);
			ViewUtils.inject(holderView,view);
			view.setTag(holderView);
		}else {
			holderView = (HolderView)view.getTag();
		}

		LingFuShuoMing lingFuShuoMing = getItem(position);
		if (lingFuShuoMing != null) {
			if(!StringUtil.isEmpty(lingFuShuoMing.getFamilyinfo())){
				holderView.tv_name.setText(lingFuShuoMing.getFamilyinfo());  //另附说明内容
			}else {
				holderView.tv_name.setText(lingFuShuoMing.getVillageinfo());  //另附说明内容
			}
			holderView.tv_sex.setText(lingFuShuoMing.getUsername());		//另附说明作者
			holderView.tv_age.setText("创建时间:"+lingFuShuoMing.getSubCreattime());
			if(StringUtil.isEmpty(lingFuShuoMing.getSubLastupdatetime())){
				holderView.tv_xl.setText("修改时间:");
			}else {
				holderView.tv_xl.setText("修改时间:"+lingFuShuoMing.getSubLastupdatetime());
			}
		}
		return view;
	}

	class HolderView {
		@ViewInject(R.id.tv_name)
		TextView tv_name;
		@ViewInject(R.id.tv_sex)
		TextView tv_sex;
		@ViewInject(R.id.tv_yhzgx)
		TextView tv_yhzgx;
		@ViewInject(R.id.tv_age)
		TextView tv_age;
		@ViewInject(R.id.tv_xl)
		TextView tv_xl;
		@ViewInject(R.id.tv_card_num)
		TextView tv_card_num;
	}

}
