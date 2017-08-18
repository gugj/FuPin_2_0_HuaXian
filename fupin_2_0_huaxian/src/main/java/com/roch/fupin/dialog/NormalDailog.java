package com.roch.fupin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roch.fupin_2_0.R;

public class NormalDailog extends Dialog {

	private android.view.View.OnClickListener mOnClick;
	private TextView content, title;
	private Button doneBtn, cancelBtn;
	LinearLayout doneBtnLayout;

	public NormalDailog(Context context) {
		super(context);
	}

	public NormalDailog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_normal_dialog);
		// 使dialog全局
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		initViews();
	}
	
	//dialog 控件初始化
	private void initViews() {
		title = (TextView) findViewById(R.id.product_search_dialog_title);
		content = (TextView) findViewById(R.id.normal_dialog_content);
		doneBtn = (Button) findViewById(R.id.normal_dialog_done);
		cancelBtn = (Button) findViewById(R.id.normal_dialog_cancel);
		//doneBtnLayout = (LinearLayout) findViewById(R.id.normal_dialog_done_layout);
	}

	/**
	 * 按钮点击事件
	 * 
	 * @param l
	 */
	public void setOnClickLinener(android.view.View.OnClickListener l) {
		this.mOnClick = l;
		doneBtn.setOnClickListener(mOnClick);
		cancelBtn.setOnClickListener(mOnClick);
	}

	/**
	 * 设置title 文字
	 * @param text
	 */
	public void setTitleText(String text){
		title.setText(text);
	}

	/**
	 * 设置dialog提示内容
	 * 
	 * @param text
	 */
	public void setContentText(String text) {
		content.setText(text);
	}

	/**
	 * 设置dialog提示内容 颜色
	 * 
	 * @param color
	 */
	public void setContentTextColor(int color)
	{
		content.setTextColor(color);
	}

	/**
	 * 设置完成按钮文字描述
	 * @param text
	 */
	public void setDoneButtonText(String text){
		doneBtn.setText(text);
	}

	/**
	 * 设置取消按钮文字描述
	 * @param text
	 */
	public void setCancelButtonText(String text){
		cancelBtn.setText(text);
	}

	/**
	 * 设置确定按钮隐藏 or 显示
	 * @param visibility
	 */
	public void setDoneBtnVisible(int visibility){
		//doneBtnLayout.setVisibility(visibility);
		doneBtn.setVisibility(visibility);
	}

	/**
	 * 设置取消按钮隐藏 or 显示
	 * @param visibility
	 */
	public void setCancelVisible(int visibility){
		//doneBtnLayout.setVisibility(visibility);
		cancelBtn.setVisibility(visibility);
	}

	public void setCancelBtnText(String text){
		cancelBtn.setText(text);
	}
	public void setDoneBtnText(String text){
		doneBtn.setText(text);
	}

}
