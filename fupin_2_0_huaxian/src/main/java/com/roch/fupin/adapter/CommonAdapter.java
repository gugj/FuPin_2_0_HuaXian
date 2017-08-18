package com.roch.fupin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.roch.fupin.photo.ViewHolder;

import java.util.List;

/**
 * 自定义的adapter，继承自BaseAdapter
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	/**
	 * 适配器中的Item的布局View
	 */
	protected final int mItemLayoutId;

	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
		//给ViewHolder中的View赋值
		convert(viewHolder, getItem(position), position);
		return viewHolder.getConvertView();
	}

	/**
	 * 给ViewHolder中的View赋值
	 * @param holder  ViewHolder
	 * @param item	  数据源中的每一条数据
	 * @param position	数据源中的每一条数据的索引
	 */
	public abstract void convert(ViewHolder holder, T item, int position);

	private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
	}

}
