package com.roch.fupin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.roch.fupin.adapter.TextAdapter;
import com.roch.fupin.entity.Basic_DistrictAppModel;
import com.roch.fupin.entity.PinKunCun;
import com.roch.fupin_2_0.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义的RelativeLayout---进行乡镇、村进行的筛选
 * @author ZhaoDongShao
 * 2016年5月30日 
 */
public class MenuLeft extends RelativeLayout implements ViewBaseAction{

	/**
	 * 乡镇、村筛选时的一级ListView
	 */
	private ListView regionListView;
	/**
	 * 乡镇、村筛选时的二级ListView
	 */
	private ListView plateListView;
	/**
	 * 存放筛选条件中的乡镇信息的集合
	 */
	private List<PinKunCun> groups = new ArrayList<>();
//	private LinkedList<String> childrenItem = new LinkedList<String>();
	private LinkedList<PinKunCun> childrenItem = new LinkedList<PinKunCun>();
	private SparseArray<LinkedList<PinKunCun>> children = new SparseArray<LinkedList<PinKunCun>>();
	/**
	 * 乡镇、村筛选时的二级ListView的适配器
	 */
	private TextAdapter plateListViewAdapter;
	/**
	 * 乡镇、村筛选时的一级ListView的适配器
	 */
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	/**
	 * 乡镇、村筛选时的一级ListView的适配器的默认选中的索引
	 */
	private int tEaraPosition = 0;
	/**
	 * 乡镇、村筛选时的二级ListView的适配器的默认选中的索引
	 */
	private int tBlockPosition = 0;
	/**
	 * 乡镇、村筛选时选中的数据
	 */
	private String showString = "全部";
	Context mContext;

	public MenuLeft(Context context) {
		super(context);
		init(context);
	}

	public MenuLeft(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getName().equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).getName().replace("全部", "").equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		//设置乡镇、村筛选时默认选中的数据
//		setDefaultSelect();
	}

	@SuppressWarnings("deprecation")
	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.menu_left, this, true);
		//乡镇、村筛选时的一级ListView
		regionListView = (ListView) findViewById(R.id.listView);
		//乡镇、村筛选时的二级ListView
		plateListView = (ListView) findViewById(R.id.listView2);
		//设置背景颜色
		setBackgroundDrawable((getResources().getDrawable(R.drawable.choosearea_bg)));
	}

	/**
	 * 设置乡镇、村筛选时的一级、二级数据和默认选中的数据
	 * @param list
	 * 2016年5月31日
	 * ZhaoDongShao
	 */
	public void setStringArray(final List<Basic_DistrictAppModel> list){
		//先获取父数据
		List<String> super_datas1=new ArrayList<>();
		if(null!=list){
			for (int i = 0; i < list.size(); i++) {
				super_datas1.add(list.get(i).getAd_nm());
			}
		}

		LinkedList<PinKunCun> tItem = new LinkedList<PinKunCun>();
		//存放筛选条件中的乡镇信息的集合
		for (int i = 0; i < list.size(); i++) {
			PinKunCun pinKunCun=new PinKunCun();
			pinKunCun.setName(list.get(i).getAd_nm());
			pinKunCun.setPoor_type(list.get(i).getPovertystatus());
			//将乡镇信息保存在list里面
			groups.add(pinKunCun);
			//循环调用接口，获取村庄的数据
			//			mOnSelectListener.getArray(list.get(i).getAd_cd());
		}
		children.put(0, tItem);
		//创建乡镇ListView的适配器
		earaListViewAdapter = new TextAdapter(mContext, groups,super_datas1,
				R.drawable.choose_item_selected_1,
				R.drawable.choose_eara_item_selector);
		//设置字体大小
		earaListViewAdapter.setTextSize(17);
		//设置乡镇ListView的默认选中---0
//		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		//乡镇ListView设置适配器
		regionListView.setAdapter(earaListViewAdapter);
		//设置乡镇ListView适配器的条目点击监听
		earaListViewAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				String name = groups.get(position).getName();
				String adl_cd = "";
				if (name != null && !name.equals("")) {
					for (int j = 0; j < list.size(); j++) {
						if (name.equals(list.get(j).getAd_nm())) {
							adl_cd = list.get(j).getAd_cd();
						}
					}
				}
				if (!adl_cd.equals("")) {
					mOnSelectListener.getArray(adl_cd,position);
				}
			}
		});

		//如果当前索引小于链表总数，就进行添加到子列表
		if (tEaraPosition < children.size())
			childrenItem.addAll(children.get(tEaraPosition));
		plateListViewAdapter = new TextAdapter(mContext, childrenItem,super_datas,
				R.drawable.choose_item_right,
				R.drawable.choose_plate_item_selector);
		plateListViewAdapter.setTextSize(15);
//		plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		plateListView.setAdapter(plateListViewAdapter);
		plateListViewAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, final int position) {
				showString = childrenItem.get(position).getName();
				if (mOnSelectListener != null) {
					mOnSelectListener.getValue(showString);
				}
			}
		});
		if (tBlockPosition < childrenItem.size())
			showString = childrenItem.get(tBlockPosition).getName();
		if (showString.contains("全部")) {
			showString = showString.replace("全部", "");
		}
		//设置乡镇、村筛选时默认选中的数据
//		setDefaultSelect();
	}

	public int getSecltIndex(){
		return tEaraPosition;
	}

	int j = 1;
	List<String> super_datas=new ArrayList<>();
	/**
	 * 设置筛选时的二级村数据
	 * @param list
	 * 2017年4月1日
	 * ZhaoDongShao
	 */
	public void setVillage(List<Basic_DistrictAppModel> list){
//		List<String> aList = new ArrayList<String>();
		List<PinKunCun> pinKunCuns=new ArrayList<>();
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				PinKunCun pinKunCun=new PinKunCun();
				pinKunCun.setName(list.get(i).getAd_nm());
				pinKunCun.setPoor_type(list.get(i).getPovertystatus());
				pinKunCuns.add(pinKunCun);
//				aList.add(list.get(i).getAd_nm());
			}
		}

		childrenItem.clear();
		childrenItem.addAll(pinKunCuns);

		if(null!=childrenItem){
			super_datas.clear();
			for (int i = 0; i < childrenItem.size(); i++) {
				super_datas.add(childrenItem.get(i).getName());
			}
		}
		plateListViewAdapter.notifyDataSetChanged();
	}

	public void clearErJiListView(){
		childrenItem.clear();
		plateListViewAdapter.notifyDataSetChanged();
	}

	/**
	 * 重置乡镇ListView、村ListView的选中索引
	 * @param position
	 */
	public void initSelectPosition(int position){
		regionListView.setSelection(position);
		plateListView.setSelection(position);
		earaListViewAdapter.setSelectedPosition(position);
		childrenItem.clear();
		plateListViewAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置乡镇、村筛选时默认选中的数据
	 * 2016年5月31日
	 * ZhaoDongShao
	 */
	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	/**
	 * 设置自定义的乡镇、村筛选时的选择监听器---用以记录选中的文字内容和乡镇的adl_cd
	 * @param onSelectListener
	 * 2017年4月1日
	 * ZhaoDongShao
	 */
	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	/**
	 * 自定义的乡镇、村筛选时的选择监听器---用以记录选中的文字内容和乡镇的adl_cd
	 * @author ZhaoDongShao
	 * 2017年4月1日 
	 */
	public interface OnSelectListener {
		 void getValue(String showText);
		 void getArray(String id,int position);
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
	}

}
