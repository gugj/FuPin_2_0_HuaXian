package com.roch.fupin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.roch.fupin.adapter.TextAdapter;
import com.roch.fupin_2_0.R;

import java.util.List;

/**
 * 自定义的RelativeLayout---进行帮扶措施、致贫原因、性别的筛选
 * @author ZhaoDongShao
 * 2017年3月31日 
 */
public class MenuRight extends RelativeLayout implements ViewBaseAction{

	/**
	 * 进行筛选时的单级ListView
	 */
	private ListView mListView;
//	private final String[] items = new String[] { "item1", "item2", "item3", "item4", "item5", "item6" };//显示字段
//	private final String[] itemsVaule = new String[] { "1", "2", "3", "4", "5", "6" };//隐藏id
	/**
	 * 筛选时的数据
	 */
	String[] items;
	/**
	 * 筛选时的数据的索引
	 */
	String[] itemsVaule;
	/**
	 * 自定义的选中时的监听器---在筛选时记录选中的数据和索引
	 */
	private OnSelectListener mOnSelectListener;
	/**
	 * 进行筛选时的单级ListView的适配器---自定义的ArrayAdapter适配器
	 */
	private TextAdapter adapter;
	/**
	 * 筛选时选中的数据的索引
	 */
	private String mDistance;
	/**
	 * 显示的筛选数据
	 */
	private String showText = "不限";
	Context mContext;
	
	public String getShowText() {
		return showText;
	}

	public MenuRight(Context context) {
		super(context);
		init(context);
	}

	public MenuRight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MenuRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.menu_right, this, true);
		//设置背景颜色
		setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg));
		mListView = (ListView) findViewById(R.id.listView);
	}

	/**
	 * 设置筛选时的数据---帮扶措施、致贫原因、性别等筛选条件
	 * @param list
	 * 2016年5月30日
	 * ZhaoDongShao
	 */
	public void setStringArray(List<String> list){
		items = new String[list.size()];
		itemsVaule = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			items[i] = list.get(i);
			itemsVaule[i] = String.valueOf(i);
		}
		adapter = new TextAdapter(mContext, items, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
		adapter.setTextSize(17);
		if (mDistance != null) {
			for (int i = 0; i < itemsVaule.length; i++) {
				if (itemsVaule[i].equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = items[i];
					break;
				}
			}
		}
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position], items[position]);
				}
			}
		});
	}

	/**
	 * 设置选中的位置
	 */
	public void setSelectPostion(int position){
		mListView.setSelection(position);
		adapter.setSelectedPosition(position);
	}

	/**
	 * 设置自定义的选中时的监听器---在筛选时记录选中的数据和索引
	 * @param onSelectListener
	 */
	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	/**
	 * 自定义的选中时的监听器---在筛选时记录选中的数据和索引
	 * @author ZhaoDongShao
	 * 2017年4月1日 
	 */
	public interface OnSelectListener {
		void getValue(String distance, String showText);
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
	}

}
