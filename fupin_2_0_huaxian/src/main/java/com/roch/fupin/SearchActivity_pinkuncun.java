package com.roch.fupin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin_2_0.R;

/**
 * 贫困户高级查询页面---增加了很多查询条件
 * @author ZhaoDongShao
 * 2017年4月1日 
 */
@ContentView(R.layout.activity_search_gaoji_pinkuncun)
public class SearchActivity_pinkuncun extends MainBaseActivity {

	@ViewInject(R.id.tv_cunfuzeren_name)
	EditText tv_cunfuzeren_name;
	
	@ViewInject(R.id.tv_cunfuzeren_phone)
	EditText tv_cunfuzeren_phone;

	@ViewInject(R.id.tv_diyishuji_name)
	EditText tv_diyishuji_name;

	@ViewInject(R.id.tv_diyishuji_phone)
	EditText tv_diyishuji_phone;

	@ViewInject(R.id.tv_baocunganbu_name)
	EditText tv_baocunganbu_name;

	@ViewInject(R.id.tv_baocunganbu_phone)
	EditText tv_baocunganbu_phone;

	@ViewInject(R.id.tv_title)
	TextView tv_title;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		tv_title.setText("高级查询");

		initToolbar();
	}

	/**
	 * 初始化Toobar
	 * 2016年8月5日
	 * ZhaoDongShao
	 */
	private void initToolbar() {
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				MyApplication.getInstance().finishActivity(this);
				break;

			default:
				break;
		}
		return true;
	}

    /**
     * 标志位---记录当前点击的筛选条件是哪个
     */
    private int selectTeye=-1;
    
	@OnClick({R.id.btn_quxiao,R.id.btn_quding})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn_quxiao: //取消筛选
			finish();
			break;
			
		case R.id.btn_quding: //确定
			String cunFuZeRenName=tv_cunfuzeren_name.getText().toString();
			String cunFuZeRenPhone=tv_cunfuzeren_phone.getText().toString();
			String diTiShuJiName=tv_diyishuji_name.getText().toString();
			String diTiShuJiPhone=tv_diyishuji_phone.getText().toString();
			String baoCunGanBuName=tv_baocunganbu_name.getText().toString();
			String baoCunGanBuPhone=tv_baocunganbu_phone.getText().toString();

			Intent intent=new Intent();
			intent.putExtra("cunFuZeRenName", cunFuZeRenName);
			intent.putExtra("cunFuZeRenPhone", cunFuZeRenPhone);
			intent.putExtra("diTiShuJiName", diTiShuJiName);
			intent.putExtra("diTiShuJiPhone", diTiShuJiPhone);
			intent.putExtra("baoCunGanBuName", baoCunGanBuName);
			intent.putExtra("baoCunGanBuPhone", baoCunGanBuPhone);

			setResult(2, intent);
			finish();
			break;
			
		default:
			selectTeye=-1;
			break;
		}
	}
	
}
