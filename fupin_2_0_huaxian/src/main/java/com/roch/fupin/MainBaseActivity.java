package com.roch.fupin;

import android.os.Build;
import android.os.Bundle;

import com.roch.fupin.utils.CommonUtil;

/**
 * 继承自BaseActivity，里面仅只有一个方法，就是在onCreate中根据版本确定状态栏的颜色
 * @author ZhaoDongShao
 * 2016年8月12日 
 */
public class MainBaseActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//如果当前手机版本 >= 4.4，即api为19，并且 < 5.0，即api为21，就设置状态栏颜色
		if (Build.VERSION_CODES.LOLLIPOP > Build.VERSION.SDK_INT && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //KITKAT
			CommonUtil.getInstance(this).getState();
		}
	}
	
}
