package com.roch.fupin.dialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.roch.fupin.adapter.CommonAdapter;
import com.roch.fupin.entity.ImageFloder;
import com.roch.fupin.photo.ViewHolder;
import com.roch.fupin_2_0.R;

import java.util.List;

/**
 * 自定义的展示照片ListView的PopupWindow，继承自定义的PopupWindow---BasePopupWindowForListView，父类里有3个抽象方法1.initView、2.initEvent、3.init
 */
public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFloder> {

	/**
	 * 展示照片的ListView
	 */
	private ListView mListDir;

	public ListImageDirPopupWindow(int width, int height, List<ImageFloder> datas, View convertView) {
		super(convertView, width, height, true, datas);
	}

	//1.初始化View
	@Override
	public void initViews() {
		mListDir = (ListView) findViewById(R.id.id_list_dir);
		mListDir.setAdapter(new CommonAdapter<ImageFloder>(context, mDatas, R.layout.list_dir_item) {
			@Override
			public void convert(ViewHolder helper, ImageFloder item, int position) {
				helper.setText(R.id.id_dir_item_name, item.getName());
				helper.setImageByUrl(R.id.id_dir_item_image, item.getFirstImagePath());
				helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
				helper.setVisible(R.id.iv_select, item.isSelected());
			}
		});
	}

	//2.初始化Event
	@Override
	public void initEvents() {
		mListDir.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (mImageDirSelected != null) {
					mImageDirSelected.onImageDirSelected(mDatas.get(position));
					for (int i = 0; i < mDatas.size(); i++) {
						if (i == position) {
							mDatas.get(i).setSelected(true);
						}else {
							mDatas.get(i).setSelected(false);
						}
					}
				}
			}
		});
	}

	//3.初始化
	@Override
	public void init() {
	}

	//0.如果自定义的PopupWindow中有其他参数
	@Override
	protected void beforeInitWeNeedSomeParams(Object... params) {
	}

	/**
	 * 接口---当选择照片的PopupWindow中的ListView被选中时
	 */
	private OnImageDirSelected mImageDirSelected;

	public interface OnImageDirSelected {
		/**
		 * 展示照片的ListView的文件夹被选中
		 * @param floder
		 */
		void onImageDirSelected(ImageFloder floder);
	}

	public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
		this.mImageDirSelected = mImageDirSelected;
	}

}
