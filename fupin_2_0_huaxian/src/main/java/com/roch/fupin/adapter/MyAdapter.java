package com.roch.fupin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.roch.fupin.entity.ImageItem;
import com.roch.fupin.photo.ViewHolder;
import com.roch.fupin_2_0.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的adapter，继承自自定义的CommonAdapter(ImageItem)，父类又继承自BaseAdapter
 */
public class MyAdapter extends CommonAdapter<ImageItem> {

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<ImageItem> mSelectedImage = new ArrayList<ImageItem>();

	/**
	 * 手机中照片数量最多的文件夹的路径---File路径
	 */
	private String mDirPath;

	public MyAdapter(Context context, List<ImageItem> mDatas, int itemLayoutId, String dirPath) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
	}

	/**
	 * 给ViewHolder中的View赋值
	 * @param holder  ViewHolder
	 * @param item      数据源中的每一条数据
	 * @param position    数据源中的每一条数据的索引
	 */
	@Override
	public void convert(final ViewHolder holder, final ImageItem item, final int position) {
		//设置no_pic
		holder.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		//设置no_selected
		holder.setImageResource(R.id.id_item_select, R.drawable.checkbox_unselected);
		//设置图片
		holder.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item.getPath());

		final ImageView mImageView = holder.getView(R.id.id_item_image);
		final ImageView mSelect = holder.getView(R.id.id_item_select);

		mImageView.setColorFilter(null);
		//设置ImageView的点击事件
		mImageView.setOnClickListener(new OnClickListener() {
			//选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v) {
				if (mSelectedImage.size() <= 0) {
					mSelectedImage.add(new ImageItem(mDirPath + "/" + item.getPath(), true));
					mSelect.setImageResource(R.drawable.checkbox_selected);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
				}else if(mSelectedImage.size() < 15){
					ImageItem item1 = new ImageItem();
					item1.setPath(mDirPath + "/" + item.getPath());
					item1.setSelected(true);
					if (mSelectedImage.contains(item1)) {
						mSelectedImage.remove(item1);
						mSelect.setImageResource(R.drawable.checkbox_unselected);
						mImageView.setColorFilter(null);
					}else {
						mSelectedImage.add(item1);
						mSelect.setImageResource(R.drawable.checkbox_selected);
						mImageView.setColorFilter(Color.parseColor("#77000000"));
					}
				}else {
					ImageItem item1 = new ImageItem();
					item1.setPath(mDirPath + "/" + item.getPath());
					item1.setSelected(true);
					if (mSelectedImage.contains(item1)) {
						mSelectedImage.remove(item1);
						mSelect.setImageResource(R.drawable.checkbox_unselected);
						mImageView.setColorFilter(null);
					}else {
						if (mOnItemClickListener != null) {
							mOnItemClickListener.onItemClick(position);
						}
					}
				}
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(position);
				}
			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		for (int i = 0; i < mSelectedImage.size(); i++) {
			if (mSelectedImage.get(i).getPath().equals(mDirPath + "/" + item.getPath())) {
				mSelect.setImageResource(R.drawable.checkbox_selected);
				mImageView.setColorFilter(Color.parseColor("#77000000"));
			}
		}
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	public interface OnItemClickListener {
		void onItemClick(int position);
	}

}
