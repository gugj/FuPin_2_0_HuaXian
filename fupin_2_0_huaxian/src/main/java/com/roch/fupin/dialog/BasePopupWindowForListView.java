package com.roch.fupin.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;

import java.util.List;

/**
 * 自定义的以ListView形式展示的PopupWindow
 * @param <T>
 */
public abstract class BasePopupWindowForListView<T> extends PopupWindow {

	/**
	 * 自定义的PopupWindow的布局
	 */
	protected View mContentView;
	/**
	 * 上下文
	 */
	protected Context context;
	/**
	 * ListView的数据源
	 */
	protected List<T> mDatas;

	public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable) {
		this(contentView, width, height, focusable, null);
	}

	public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable, List<T> mDatas) {
		this(contentView, width, height, focusable, mDatas, new Object[0]);
	}

	public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable, List<T> mDatas, Object... params) {
		super(contentView, width, height, focusable);
		this.mContentView = contentView;
		context = contentView.getContext();
		if (mDatas != null)
			this.mDatas = mDatas;

		if (params != null && params.length > 0) {
			//0.如果自定义的PopupWindow中有其他参数
			beforeInitWeNeedSomeParams(params);
		}

		setBackgroundDrawable(new BitmapDrawable());
		setTouchable(true);
		setOutsideTouchable(true);
		setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		//1.初始化View
		initViews();
		//2.初始化Event
		initEvents();
		//3.初始化
		init();
	}

	/**
	 * 0.如果自定义的PopupWindow中有其他参数
	 * @param params
	 */
	protected abstract void beforeInitWeNeedSomeParams(Object... params);

	/**
	 * 1.初始化View
	 */
	public abstract void initViews();

	/**
	 * 2.初始化Event
	 */
	public abstract void initEvents();

	/**
	 * 3.初始化
	 */
	public abstract void init();

	/**
	 * 查找PopupWindow中自定义布局文件中的View
	 * @param id
	 * @return
	 */
	public View findViewById(int id) {
		return mContentView.findViewById(id);
	}

	protected static int dpToPx(Context context, int dp) {
		return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
	}

}
