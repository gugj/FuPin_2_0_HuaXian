package com.roch.fupin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.LoginEntity;
import com.roch.fupin.entity.LoginResult;
import com.roch.fupin.entity.Menu;
import com.roch.fupin.entity.Sroles;
import com.roch.fupin.entity.User;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.DbUtil;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.MyRequestCallBack.SuccessResult;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin.view.ClearEditText;
import com.roch.fupin_2_0.R;

import java.io.Serializable;
import java.util.List;

/**
 * 登录界面
 * @author ZhaoDongShao
 * 2016年5月9日
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements SuccessResult {

	@ViewInject(R.id.btn_login)
	Button btn_login;
	@ViewInject(R.id.cb_remember)
	CheckBox cb_remember;

	@ViewInject(R.id.edt_username)
	ClearEditText edt_username;
	@ViewInject(R.id.edt_password)
	ClearEditText edt_password;

	@ViewInject(R.id.tv_server)
	private TextView tv_server;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
			View statusBarView = mContentView.getChildAt(0);
			// 移除假的 View
			if (statusBarView != null && statusBarView.getLayoutParams() != null
					&& statusBarView.getLayoutParams().height == getStatusBarHeight()) {
				mContentView.removeView(statusBarView);
			}
			// 不预留空间
			if (mContentView.getChildAt(0) != null) {
				ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
			}
		}

		ViewUtils.inject(this);
		tv_server.setVisibility(View.GONE);
		MyApplication.getInstance().addActivity(mActivity);
		// 第一次进入APP的时候初始化为没有定位
		MyApplication.getInstance().getSharePreferencesUtilInstance().saveLoction(mContext, false);

		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Login();
				System.out.println("用户名为："+getEditText(edt_username)+",密码为："+ getEditText(edt_password)+",选中状态为："+checked);
				MyApplication.getInstance().getSharePreferencesUtilInstance().saveNameAndPassword(LoginActivity.this, getEditText(edt_username), getEditText(edt_password),checked);
			}
		});
		
		checked = MyApplication.getInstance().getSharePreferencesUtilInstance().getLonginChecked(this);
		if(checked){	
			// 初始化记住用户名和密码，并设置checkbox的状态
			initLoginNameAndPassword();
		}
	}

	/**
	 * 初始化记住用户名和密码，并设置checkbox的状态  <br/>
	 * 2016年11月8日
	 */
	private void initLoginNameAndPassword() {
		String longinName = MyApplication.getInstance().getSharePreferencesUtilInstance().getLonginName(this);
		String longinPassword = MyApplication.getInstance().getSharePreferencesUtilInstance().getLonginPassword(this);
//		checked = MyApplication.getInstance().getSharePreferencesUtilInstance().getLonginChecked(this);
		edt_username.setText(longinName);
		edt_password.setText(longinPassword);
		cb_remember.setChecked(checked);
	}

	/**
	 * 是否记住用户名和密码
	 */
	public boolean checked=false;
	
	@OnClick({ R.id.btn_login ,R.id.cb_remember})
	public void onClick(View v) {
		if (v.getId() == R.id.btn_login) {
			Login();
			System.out.println("用户名为："+getEditText(edt_username)+",密码为："+ getEditText(edt_password)+",选中状态为："+checked);
			MyApplication.getInstance().getSharePreferencesUtilInstance().saveNameAndPassword(this, getEditText(edt_username), getEditText(edt_password),checked);
		}else if (v.getId() == R.id.cb_remember) {
//			showToast("点击了记住用户名和密码");
			checked = cb_remember.isChecked();
		}
	}

	/**
	 * 登陆用户的密码
	 */
	String password;
	/**
	 * 2016年5月13日
	 * ZhaoDongShao
	 */
	private void Login() {
		String username = getEditText(edt_username);
		password = getEditText(edt_password);
		View focusView = null;
		boolean isNot = false; // 判断输入框是否为空

		if (StringUtil.isEmpty(username)) {
			focusView = edt_password;
			isNot = true;
		} else if (StringUtil.isEmpty(password)) {// Check for a valid password.
			focusView = edt_password;
			isNot = true;
		}
		// Check for a valid email address.
		if (isNot) {
			focusView.requestFocus();
			showToast("请输入用户名或密码");
		} else {
			RequestParams rp = new RequestParams();
			rp.addBodyParameter("loginname", username);
			rp.addBodyParameter("password", password);
			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST, URLs.LOGIN, rp,
					new MyRequestCallBack(this, MyConstans.FIRST));
			System.out.println("登陆页面请求服务器的网址为：==="+URLs.LOGIN+"?&loginname="+username+"&password="+password);
		}
	}

	@Override
	public void onSuccessResult(String str, int flag) {
		super.onSuccessResult(str, flag);
		switch (flag) {
		case MyConstans.FIRST:
			System.out.println("登陆页面请求服务器数据成功：==="+str);
			LoginResult loginResult = LoginResult.parseToT(str, LoginResult.class);
			if (loginResult.getSuccess()) {
				Common.isLogin = loginResult.getSuccess();
				List<LoginEntity> menus = loginResult.getJsondata();
				LoginEntity loginEntity = null;
				List<Menu> lMenus = null;
				User user = null;
				Sroles sroles = null;
				if (menus != null && menus.size() > 0) {
					for (int i = 0; i < menus.size(); i++) {
						loginEntity = menus.get(i);
						if (!StringUtil.isEmpty(loginEntity)) {
							lMenus = loginEntity.getNavigation();
							user = loginEntity.getLoginUser();
							sroles = loginEntity.getSroles();
							if (!StringUtil.isEmpty(user)) {
								Common.LoginName = user.getLoginname();
								MyApplication.getInstance().saveLogin(user);

								SharedPreferences sp = getSharedPreferences("loginactivty", Context.MODE_APPEND);
								SharedPreferences.Editor ed = sp.edit();
								ed.putString("adl_cd",user.getAdl_cd());
								ed.putString("password",password);
								ed.putString("loginUserId",user.getId());
								System.out.println("在登陆页面保存用户的adl_cd成功==="+user.getAdl_cd());
								ed.commit();

								MyApplication.getInstance().saveSroles(sroles);
							}
						}
					}
					try {
						DbUtil.SaveMenu(lMenus);
						DbUtil.deleteMenu(lMenus);
						if (lMenus != null && user != null) {
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable(Common.MENU_BUNDEL_KEY, (Serializable) lMenus);
							bundle.putSerializable(Common.USER_BUNDEL_KEY, (Serializable) user);
							intent.putExtra(Common.MENU_INTENT_KEY, bundle);
							startActivity(intent);
							MyApplication.getInstance().finishActivity(LoginActivity.this);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// MyApplication.getInstance().saveLogin(menu);
			} else {
				MyApplication.getInstance().getToastUtilsInstance().showNormalToast(LoginActivity.this,
						loginResult.getMsg());
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onFaileResult(String str, int flag) {
		super.onFaileResult(str, flag);
		switch (flag) {
		case MyConstans.FIRST:
			System.out.println("登陆页面请求服务器数据失败：==="+str);
			showToast(str);
			break;

		default:
			break;
		}
	}

}
