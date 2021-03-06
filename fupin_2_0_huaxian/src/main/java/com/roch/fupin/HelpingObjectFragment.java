package com.roch.fupin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.roch.fupin.MainActivity.MyTouchListener;
import com.roch.fupin.adapter.DragMoreAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.HelpObjectMenu;
import com.roch.fupin.entity.Menu;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.GSONUtil;
import com.roch.fupin.utils.OpenActivityUtil;
import com.roch.fupin.view.DragMoreGrid;
import com.roch.fupin_2_0.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 扶贫对象
 * 
 * @author Administrator
 *
 */
public class HelpingObjectFragment extends BaseFragment {

	private Context mContext;
	@ViewInject(R.id.gv_menu)
	DragMoreGrid mDragMoreGrid;

	DragMoreAdapter mDragMoreAdapter;

	List<Menu> dragviews;

	MyTouchListener myHomeTouchListener;
	//获取测量后的gridview高度
	int height;

	DbUtils dbUtils;

	boolean isHide = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_more_gridview, container);
		ViewUtils.inject(this, view);
		this.mContext = getContext();

		myHomeTouchListener = new MyTouchListener() {
			@Override
			public void onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					//获取当前按下的点
					int DownY = (int)event.getY();
					//获取当前GridView的高
					if (!isHide && DownY - height - MainActivity.StruesHeight> 0 && mDragMoreGrid.isDrag) {
						LogUtils.i("点击的位置在控件之外");
						mDragMoreGrid.refresh();
					}
					break;

				default:
					break;
				}
			}
		};
		((MainActivity)getActivity()).registerMyTouchListener(myHomeTouchListener);

		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			isHide = hidden;
			initDate();
			ViewTreeObserver vto = mDragMoreGrid.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onGlobalLayout() {
					// TODO Auto-generated method stub
					mDragMoreGrid.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					height = mDragMoreGrid.getHeight();
				}
			});
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initDate();
	}

	/**
	 * 加载数据
	 */
	private void initDate() {
		// TODO Auto-generated method stub
		dragviews = new ArrayList<Menu>();

		dbUtils = MyApplication.getInstance().getDbUtilsInstance(Common.LoginName);
		try {
			List<HelpObjectMenu> list = dbUtils.findAll(HelpObjectMenu.class);
			for (int i = 0; i < list.size(); i++) {

				String helpObjectJson = GSONUtil.objectToJson(list.get(i));

				Menu dragIconInfo = (Menu) GSONUtil.fromJson(helpObjectJson, Menu.class);
				dragviews.add(dragIconInfo);
			}
			mDragMoreAdapter = new DragMoreAdapter(mContext, dragviews, Common.EXTS_HELP_OBJECT,mDragMoreGrid);
			mDragMoreGrid.setAdapter(mDragMoreAdapter);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnItemClick(R.id.gv_menu)
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Menu dragView = (Menu)parent.getItemAtPosition(position);


		if (dragView != null) {
//			if (OpenActivityUtil.getInstance().getisAdl_CD(mContext, Common.LoginName)) {
//				startActivity(OpenActivityUtil.getInstance().OpenActivity(mContext, dragView.getName()));
//			}else {
//				ShowToast("请选择您所管辖的城市进行查看");
//			}

			// TODO 检查上面的判断条件是否正确****************************************************************
			startActivity(OpenActivityUtil.getInstance().OpenActivity(mContext, dragView.getName()));
			//		if (dragView.getName().equals(Common.EXTS_HELP_OBJECT_FAMILY_NAME) || dragView.getName().equals(Common.EXTS_HELP_OBJECT_VILLAGE_NAME)) {
			//
			//			AdlCode adlCode = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mContext,Common.LoginName);
			//			if (adlCode.getAd_cd()==null) {
			//					MyApplication.getInstance().getToastUtilsInstance().showNormalToast(mContext, "请选择您所要查看的行政区县");
			//			}else {
			//				if (adlCode.getAd_cd().equals("")) {
			//					MyApplication.getInstance().getToastUtilsInstance().showNormalToast(mContext, "请选择您所要查看的行政区县");
			//				}else {
			//					startActivity(OpenActivityUtil.getInstance().OpenActivity(mContext, dragView.getName()));
			//				}	
			//			}
			//
			//		}else {
			//			startActivity(OpenActivityUtil.getInstance().OpenActivity(mContext, dragView.getName()));
			//		}
		}
	}

}
