/**
 * 
 */
package com.roch.fupin.view;

import com.roch.fupin.PoorPeopleCaseYXActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 因学
 * 
 * @author ZhaoDongShao
 *
 * 2016年8月11日 
 *
 */
public class CaseXueTableScrollView extends HorizontalScrollView{

	PoorPeopleCaseYXActivity activity;

	public CaseXueTableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.activity = (PoorPeopleCaseYXActivity) context;
	}


	public CaseXueTableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.activity = (PoorPeopleCaseYXActivity) context;
	}

	public CaseXueTableScrollView(Context context) {
		super(context);
		this.activity = (PoorPeopleCaseYXActivity) context;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		activity.mTouchView = this;
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		if(activity.mTouchView == this) {
			activity.onScrollChanged(l, t, oldl, oldt);
		}else{
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}

}
