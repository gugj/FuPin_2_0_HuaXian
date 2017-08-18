/**
 * 
 */
package com.roch.fupin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin_2_0.R;

/**
 * 初始页
 * 
 * @author ZhaoDongShao
 *
 * 2016年5月11日 
 *
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity{

	@ViewInject(R.id.rl_splash_root)
	private RelativeLayout rl_splash_boot;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_splash);
//		rl_splash_boot = (RelativeLayout)findViewById(R.id.rl_splash_root);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();  
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  

			ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);  
			View statusBarView = mContentView.getChildAt(0);  
			//移除假的 View  
			if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == getStatusBarHeight()) {  
				mContentView.removeView(statusBarView);  
			}  
			//不预留空间  
			if (mContentView.getChildAt(0) != null) {  
				ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);  
			} 
		}

		ViewUtils.inject(this);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Animation animation = new AlphaAnimation(0.1f, 1.0f);
				animation.setDuration(3000);
				rl_splash_boot.startAnimation(animation);
				animation.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}
				});
			}
		},10);

//		Animation animation = new AlphaAnimation(0.1f, 1.0f);
//		animation.setDuration(5000);
//		animation.setAnimationListener(new AnimationListener() {
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				int type = SharePreferencesUtil.getGuide(SplashActivity.this);
//				if (type == -1) {
//					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//					startActivity(intent);
//					finish();
//				}
//			}
//		});
////		rl_splash_boot.setAnimation(animation);
//		rl_splash_boot.startAnimation(animation);
	}

}
