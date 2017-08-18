package com.roch.fupin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.PoorHouseFamilyPeopleDetailAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.MapEntity;
import com.roch.fupin.entity.PoorFamilyPeople;
import com.roch.fupin.entity.Yhzgx;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin_2_0.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 家庭成员详细情况信息的Activity
 * 
 * @author ZhaoDongShao
 * 2016年5月18日 
 */
@ContentView(R.layout.activity_poorfamilypepoledetail)
public class PoorFamilyPeopleDetailActivity extends MainBaseActivity{

	private static int HEALTH = 1; //健康状况

	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.tv_title)
	TextView tv_title;

	@ViewInject(R.id.lv_poor)
	ListView lv;

	PoorHouseFamilyPeopleDetailAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(mActivity);
		ViewUtils.inject(this);
		initToolbar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initDate();
	}

	/**
	 *初始化数据
	 * 2016年5月18日
	 * ZhaoDongShao
	 */
	private void initDate() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra(Common.INTENT_KEY);
		if (bundle != null) {
			PoorFamilyPeople people = (PoorFamilyPeople) bundle.getSerializable(Common.BUNDEL_KEY);
			if (people != null) {

				tv_title.setText(people.getPersonname());

				List<MapEntity> mapEntities = new ArrayList<MapEntity>();
				MapEntity mapEntity = null;
				mapEntity = new MapEntity(getTextName(R.string.people_name), people.getPersonname());
				mapEntities.add(mapEntity);
				String familyrelationid = people.getFamilyrelationid();//与户主关系id
				if (familyrelationid!=null&&!familyrelationid.equals("")) {
					List<Yhzgx> list = MyApplication.lYhzgx;
					if (list != null && list.size() > 0) {
						for (Yhzgx yhzgx : list) { //与户主关系
							if (yhzgx.getValue().equals(familyrelationid)) {
								mapEntity = new MapEntity(getTextName(R.string.people_yhzgn), yhzgx.getText());
								mapEntities.add(mapEntity);
							}
						}
					}
				}
				//性别
				mapEntity = new MapEntity(getTextName(R.string.people_sex), people.getSexName());
				mapEntities.add(mapEntity);
//				if (people.getBirthday() != null) {
//					String[] date = people.getBirthday().split(" ");
//					mapEntity = new MapEntity(getTextName(R.string.people_date), date[0]);
//					mapEntities.add(mapEntity);
//				}
				//身份证号
				mapEntity = new MapEntity(getTextName(R.string.people_card_num), people.getIdno());
				mapEntities.add(mapEntity);
				//联系电话
				mapEntity = new MapEntity(getTextName(R.string.people_lianxidianh), people.getPp_phone());
				mapEntities.add(mapEntity);
				//主要致贫原因
				mapEntity = new MapEntity(getTextName(R.string.poor_cause), people.getPp_poorreason());
				mapEntities.add(mapEntity);
//				if (people.getPp_poorreason() != null && people.getPp_poorreason().contains("因病")) {
//					mapEntity = new MapEntity(getTextName(R.string.people_ifecologyname), people.getIfecologyname());
//					mapEntities.add(mapEntity);
//				}
//				mapEntity = new MapEntity(getTextName(R.string.no_poor_plan), people.getPp_helpplan());
//				mapEntities.add(mapEntity);
//				mapEntity = new MapEntity(getTextName(R.string.people_iftrained), people.getIfwanttrained());
//				mapEntities.add(mapEntity);
				
				mapEntity = new MapEntity(getTextName(R.string.people_ifxnh), people.getIfxnhname());
				mapEntities.add(mapEntity);
				mapEntity = new MapEntity(getTextName(R.string.people_ifxnbx), people.getIfxnbxname());
				mapEntities.add(mapEntity);
				mapEntity = new MapEntity(getTextName(R.string.people_ifjbbx), people.getIfjbbxname());
				mapEntities.add(mapEntity);
				//大病
				mapEntity = new MapEntity(getTextName(R.string.people_ifdbbx), people.getIfdbbxname());
				mapEntities.add(mapEntity);
				//人数
//				mapEntity = new MapEntity(getTextName(R.string.people_renshu), people.getHousecount());
//				mapEntities.add(mapEntity);
				//民族
				mapEntity = new MapEntity(getTextName(R.string.people_minzu), people.getNationname());
				mapEntities.add(mapEntity);
				//文化程度
				mapEntity = new MapEntity(getTextName(R.string.people_wenhuachengdu), people.getWhname());
				mapEntities.add(mapEntity);
				//在校生状况
				mapEntity = new MapEntity(getTextName(R.string.people_zaixiaosheng), people.getInschoolname());
				mapEntities.add(mapEntity);
				//健康状况
				mapEntity = new MapEntity(getTextName(R.string.people_health), people.getJkzkname());
				mapEntities.add(mapEntity);
				//劳动技能
				mapEntity = new MapEntity(getTextName(R.string.people_laodongjineng), people.getPp_laborskillname());
				mapEntities.add(mapEntity);
				//务工情况
				mapEntity = new MapEntity(getTextName(R.string.people_wugongqingkuang), people.getWorkstatusname());
				mapEntities.add(mapEntity);
				//务工时间
				mapEntity = new MapEntity(getTextName(R.string.people_wugongshijian), people.getWorkmonth());
				mapEntities.add(mapEntity);
				//开户银行名称
				mapEntity = new MapEntity(getTextName(R.string.people_yinhangmingcheng), people.getBankname());
				mapEntities.add(mapEntity);
				//银行卡号
				mapEntity = new MapEntity(getTextName(R.string.people_yinhangkahao), people.getBankcardno());
				mapEntities.add(mapEntity);
				
				//婚迁
//				mapEntity = new MapEntity(getTextName(R.string.people_hunqian), people.getPp_marriagemovename());
//				mapEntities.add(mapEntity);
//				//婚迁时间
//				mapEntity = new MapEntity(getTextName(R.string.people_hunqian_time), CommonUtil.getSpliteDate(people.getPp_marriagemovetime()));
//				mapEntities.add(mapEntity);

//				mapEntity = new MapEntity(getTextName(R.string.people_health), people.getJkzkname());
//				mapEntities.add(mapEntity);

//				if (!StringUtil.isEmpty(people.getPp_health())) {
//					HEALTH = Integer.parseInt(people.getPp_health());
//					switch (HEALTH) {
//					case 5:
//						//残疾证
//						mapEntity = new MapEntity(getTextName(R.string.people_deformityno), people.getPp_deformityno());
//						mapEntities.add(mapEntity);
//						break;
//					case 6:
//						//精神病
//						mapEntity = new MapEntity(getTextName(R.string.people_spiritno), people.getPp_spiritno());
//						mapEntities.add(mapEntity);
//						break;
//					case 7:
//						//智障
//						mapEntity = new MapEntity(getTextName(R.string.people_retardedno), people.getPp_retardedno());
//						mapEntities.add(mapEntity);
//					default:
//						break;
//					}
//				}
//				mapEntity = new MapEntity(getTextName(R.string.people_ifoutcontactname), people.getIfoutcontactname());
//				mapEntities.add(mapEntity);
//				if (people.getIfoutcontactname().equals("是")) {
//					mapEntity = new MapEntity(getTextName(R.string.people_outcontacttime), people.getPp_outcontacttime());
//					mapEntities.add(mapEntity);
//				}
//				mapEntity = new MapEntity(getTextName(R.string.people_ifdeathname), people.getIfdeathname());
//				mapEntities.add(mapEntity);
//				mapEntity = new MapEntity(getTextName(R.string.people_ifdeathtime), people.getIfdeathtime());

				adapter = new PoorHouseFamilyPeopleDetailAdapter(mContext, mapEntities);
				lv.setAdapter(adapter);
			}
		}
	}

	/**
	 * 返回资源文件文本
	 * @param resid
	 * @return
	 * 2016年8月6日
	 * ZhaoDongShao
	 */
	private String getTextName(int resid){
		return ResourceUtil.getInstance().getStringById(resid);
	}

	/**
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
}
