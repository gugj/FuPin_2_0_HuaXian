package com.roch.fupin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roch.fupin.entity.PoorFamilyBase;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin_2_0.R;

/**
 * 贫困户基本情况
 * @author ZhaoDongShao
 * 2016年5月9日 
 */
public class PoorHouseBaseFragment extends BaseFragment {

	@ViewInject(R.id.tv_poor)
	TextView tv_poor;  //脱贫情况
	@ViewInject(R.id.tv_phone)
	TextView tv_phone;  //户主电话
	@ViewInject(R.id.tv_num)
	TextView tv_num; //家庭人数
	@ViewInject(R.id.tv_poorfamilypropert)
	TextView tv_poorfamilypropert; //贫困户属性
	@ViewInject(R.id.tv_cause)
	TextView tv_cause; //致贫原因
	
	@ViewInject(R.id.tv_location)
	TextView tv_location; //位置
	@ViewInject(R.id.tv_renjunshouru)
	TextView tv_renjunshouru; //人均纯收入
	@ViewInject(R.id.tv_tuopin_year)
	TextView tv_tuopin_year; //脱贫年份
	@ViewInject(R.id.tv_huqingkuang_shuoming)
	TextView tv_huqingkuang_shuoming; //户情况说明

//	@ViewInject(R.id.tv_plan)
//	TextView tv_plan; //脱贫计划
//	@ViewInject(R.id.tv_train_num)
//	TextView tv_job_num; //就业人数
//	@ViewInject(R.id.rl_dutyperson)
//	RelativeLayout rl_zeren_people; //责任人
//
//	@ViewInject(R.id.tv_ifooperative)
//	TextView tv_ifooperative;
//	@ViewInject(R.id.tv_productionpower)
//	TextView tv_productionpower;
//	@ViewInject(R.id.tv_housesecurity)
//	TextView tv_housesecurity;
//	@ViewInject(R.id.tv_ifagreetomove)
//	TextView tv_ifagreetomove;
//	@ViewInject(R.id.tv_housingtype)
//	TextView tv_housingtype;
//	@ViewInject(R.id.tv_ifnoroom)
//	TextView tv_ifnoroom;
//	
//	@ViewInject(R.id.water_is_aq)
//	TextView tv_water_is_aq;
//	@ViewInject(R.id.house_is_d)
//	TextView tv_house_is_d;
//	@ViewInject(R.id.woter_is_kn)
//	TextView tv_water_is_kn;
//	@ViewInject(R.id.house_area)
//	TextView tv_house_area;
	
	/**
	 * 标志位，标志初始化已经完成
	 */
	private boolean isPrepared;
	PoorFamilyBase lBases = null;
	/**
	 * 标识当前fragment是否可见
	 */
	private boolean isVisible;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_poorhouse_base, container, false);
		ViewUtils.inject(this, view);
		isPrepared = true;
		lazyLoad();
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			isVisible = true;
			lazyLoad();
		}else {
			isVisible = false;
		}
	}

	private void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
		initData();
	}

	/**
	 *
	 *
	 * 2016年5月17日
	 *
	 * ZhaoDongShao
	 *
	 */
	private void initData() {
		Bundle bundle = getArguments();
		if (!StringUtil.isEmpty(bundle)) {
			lBases = (PoorFamilyBase) bundle.getSerializable(Common.BUNDEL_KEY);
			PoorFamilyBase poorHouse= (PoorFamilyBase) bundle.getSerializable("poorHouse");
			//			String uid = poorhouse.getHouseholderid();
			//			RequestParams rp = new RequestParams();
			//			rp.addBodyParameter("householderid", uid);
			//			MyApplication.getInstance().getHttpUtilsInstance().send(HttpMethod.POST,
			//					URLs.POOR_HOUSE_DETAIL, rp,
			//					new MyRequestCallBack(this, MyConstans.FIRST));
			if (!StringUtil.isEmpty(lBases)) {
				tv_poor.setText(lBases.getTpname()); //脱贫情况
				tv_cause.setText(lBases.getPoorreason()); //致贫原因
				tv_num.setText(String.valueOf(lBases.getHousecount())); //家庭人口
				tv_phone.setText(lBases.getPhone()); //电话
//				tv_plan.setText(lBases.getHelpplan());
				
				tv_poorfamilypropert.setText(lBases.getPoorfamilypropert()); //贫困户属性
				tv_location.setText(lBases.getLocation()); //位置
				tv_renjunshouru.setText(poorHouse.getYear_income_perp()); //人均纯收入
				tv_tuopin_year.setText(lBases.getTp_year()); //脱贫年度
				tv_huqingkuang_shuoming.setText(lBases.getFamilyinfo()); //户情况说明---------------

//				tv_job_num.setText(String.valueOf(lBases.getJyrs()));
//				
//				tv_water_is_aq.setText(lBases.getWatersafe());
//				tv_water_is_kn.setText(lBases.getWatertrouble());
//				//家里是否通电
//				tv_house_is_d.setText(lBases.getIselectricity());
////				tv_house_is_poor.setText(lBases.getHousesafe());
//				tv_house_area.setText(lBases.getHousearea());
				
				// 贫困户属性
				tv_poorfamilypropert.setText(lBases.pkhsxname);
//				tv_ifooperative.setText(lBases.getIfooperative());//是否参与农民专业合作社
//				tv_productionpower.setText(lBases.getProductionpower());//用电
//				tv_housesecurity.setText(lBases.housesecurityname);//住房安全
//				tv_ifagreetomove.setText(lBases.getIfagreetomove());//
//				tv_housingtype.setText(lBases.housingtypename);// 房屋类型
//				tv_ifnoroom.setText(lBases.getIfnoroom());//是否无房
				
				//				List<DutyPerson> lDutyPersons = lBases.getDutyPerson();
				//				String duty_person = "";
				//				if (lDutyPersons!=null&&lDutyPersons.size()>0) {
				//					for (DutyPerson dutyPerson : lDutyPersons) {
				//						duty_person += dutyPerson.getName() + "、";
				//					}
				//				}
				//				if (!duty_person.equals("")) {
				//					duty_person = duty_person.substring(0, duty_person.length()-1);
				//					tv_zeren_people.setText(duty_person);
				//				}
				
				if (StringUtil.checkPhone(getTextView(tv_phone))) {
					tv_phone.setTextColor(ResourceUtil.getInstance().getColorById(R.color.color_00aff0));
					tv_phone.setClickable(true);
				}else {
					tv_phone.setTextColor(ResourceUtil.getInstance().getColorById(R.color.black));
					tv_phone.setClickable(false);
				}
			}
		}
	}

	@OnClick({R.id.tv_phone}) //,R.id.rl_dutyperson
	public void onClick(View v){
		
		if (v.getId() == R.id.tv_phone) {
			
			showPhoneDialog(getTextView(tv_phone));
			
		}
//		else if (v.getId() == R.id.rl_dutyperson) {
//			
//			List<DutyPerson> list = lBases.getDutyPerson();
//			if (list != null && list.size() >0 ) {
//				DutyPersonDialog dialog = new DutyPersonDialog();
//				Bundle bundle = new Bundle();
//				bundle.putSerializable(Common.BUNDEL_KEY, (Serializable) lBases.getDutyPerson());
//				dialog.setArguments(bundle);
//				dialog.show(getFragmentManager(), "PoorHouseBaseFragment");
//			}else {
//				ShowToast("没有帮扶责任人数据");
//			}
//			
//		}
		
	}
	
	//	/* (non-Javadoc)
	//	 * @see com.roch.fupin.BaseFragment#onSuccessResult(java.lang.String, int)
	//	 */
	//	@Override
	//	public void onSuccessResult(String str, int flag) {
	//		// TODO Auto-generated method stub
	//		super.onSuccessResult(str, flag);
	//		switch (flag) {
	//		case MyConstans.FIRST:
	//
	//			PoorFamilyListDetailResult poorFamilyListResult = PoorFamilyListDetailResult.parseToT(str, PoorFamilyListDetailResult.class);
	//			if (poorFamilyListResult != null) {
	//				if (poorFamilyListResult.getSuccess()) {
	//					List<PoorFamily> lFamilies = poorFamilyListResult.getJsondata();
	//					if (!StringUtil.isEmpty(lFamilies) && lFamilies.size() > 0) {
	//						for (PoorFamily poorFamily : lFamilies) {
	//							PoorHouseBaseFragment.poorFamily = poorFamily;
	//							PoorFamilyBase lBases = poorFamily.getPn();
	//							if (!StringUtil.isEmpty(lBases)) {
	//
	//							}
	//						}
	//					}
	//				}
	//				else {
	//					ShowNoticDialog();
	//				}
	//			}
	//			break;
	//
	//		default:
	//			break;
	//		}
	//	}
	//
	//	/* (non-Javadoc)
	//	 * @see com.roch.fupin.BaseFragment#onFaileResult(java.lang.String, int)
	//	 */
	//	@Override
	//	public void onFaileResult(String str, int flag) {
	//		// TODO Auto-generated method stub
	//		super.onFaileResult(str, flag);
	//		ShowToast(str);
	//	}


}
