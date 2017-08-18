package com.roch.fupin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roch.fupin.dialog.DutyCompanyDialog;
import com.roch.fupin.entity.HelpCompany;
import com.roch.fupin.entity.PoorVillageBase;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin_2_0.R;

import java.io.Serializable;
import java.util.List;

/**
 * 贫困村基本情况
 * @author ZhaoDongShao
 * 2016年5月24日 
 */
public class PoorVillageBaseFragment extends BaseFragment {
	@ViewInject(R.id.tv_poor)
	TextView tv_poor;  //脱贫情况
	@ViewInject(R.id.tv_fuzeren) //村负责人
	TextView tv_fuzeren;
	@ViewInject(R.id.tv_phone)
	TextView tv_phone;  //村办电话
	@ViewInject(R.id.tv_num)
	TextView tv_num; //总人口数
	@ViewInject(R.id.tv_poor_num)
	TextView tv_poor_num; //贫困户数
	@ViewInject(R.id.rl_dutycompany)
	RelativeLayout rl_help_company; //帮扶单位
	
	@ViewInject(R.id.tv_cunshuxing)
	TextView tv_cunshuxing;  //村属性
	@ViewInject(R.id.tv_baocunganbu_dianhua_0)
	TextView tv_baocunganbu_dianhua_0;  //包村干部电话
	@ViewInject(R.id.tv_baocunganbu_dianhua)
	TextView tv_baocunganbu_dianhua;  //包村干部电话
	@ViewInject(R.id.tv_baocunganbu_mingcheng)
	TextView tv_baocunganbu_mingcheng;  //包村干部名称
	@ViewInject(R.id.tv_baocunganbu_danwei)
	TextView tv_baocunganbu_danwei;  //包村干部单位
	@ViewInject(R.id.tv_diyishuji_danwei)
	TextView tv_diyishuji_danwei;  //第一书记单位
	@ViewInject(R.id.tv_diyishuji)
	TextView tv_diyishuji;  //第一书记
	@ViewInject(R.id.tv_diyishuji_dianhua)
	TextView tv_diyishuji_dianhua;  //第一书记电话
	@ViewInject(R.id.tv_zonghushu)
	TextView tv_zonghushu;  //总户数
	@ViewInject(R.id.tv_pinkunrenkoushu)
	TextView tv_pinkunrenkoushu;  //贫困人口数
	@ViewInject(R.id.tv_dibaohushuo)
	TextView tv_dibaohushuo;  //低保户数
	@ViewInject(R.id.tv_dibaorenkoushu)
	TextView tv_dibaorenkoushu;  //低保人口数
	@ViewInject(R.id.poor_wubaorenhushu)
	TextView poor_wubaorenhushu;  //五保户数
	@ViewInject(R.id.poor_wubaorenkoushu)
	TextView poor_wubaorenkoushu;  //五保人口数
	@ViewInject(R.id.poor_shaoshuminzushu)
	TextView poor_shaoshuminzushu;  //少数民族人口数
	@ViewInject(R.id.poor_funvrenkoushu)
	TextView poor_funvrenkoushu;  //妇女人口数
	@ViewInject(R.id.poor_canjirenkoushu)
	TextView poor_canjirenkoushu;  //残疾人口数
	@ViewInject(R.id.poor_laodonglirenshu)
	TextView poor_laodonglirenshu;  //劳动力人数
	@ViewInject(R.id.poor_waicuwugongshu)
	TextView poor_waicuwugongshu;  //外出务工人数
	@ViewInject(R.id.poor_weizhi)
	TextView poor_weizhi;  //位置
	@ViewInject(R.id.poor_pinkunfashenglv)
	TextView poor_pinkunfashenglv;  //贫困发生率
	@ViewInject(R.id.poor_cunqingkuangshuoming)
	TextView poor_cunqingkuangshuoming;  //村情况说明
	@ViewInject(R.id.tv_yituopinrenkoushu)
	TextView tv_yituopinrenkoushu;  //已脱贫人口数
	@ViewInject(R.id.tv_weituopinrenkoushu)
	TextView tv_weituopinrenkoushu;  //未脱贫人口数
	@ViewInject(R.id.tv_fantuopinrenkoushu)
	TextView tv_fantuopinrenkoushu;  //返脱贫人口数
	@ViewInject(R.id.tv_yinbingzhipinrenshu)
	TextView tv_yinbingzhipinrenshu;  //因病致贫人数
	@ViewInject(R.id.tv_yincanzhipinrenshu)
	TextView tv_yincanzhipinrenshu;  //因残致贫人数
	@ViewInject(R.id.tv_yinxuezhipinrenshu)
	TextView tv_yinxuezhipinrenshu;  //因学致贫人数
	@ViewInject(R.id.tv_quejishu)
	TextView tv_quejishu;  //缺技术致贫人数
	@ViewInject(R.id.tv_quezijin)
	TextView tv_quezijin;  //缺资金致贫人数
	@ViewInject(R.id.tv_yituopin_2014)
	TextView tv_yituopin_2014;  //2014已脱贫人数
	@ViewInject(R.id.tv_yituopin_2015)
	TextView tv_yituopin_2015;  //2015已脱贫人数
	@ViewInject(R.id.tv_yituopin_2016)
	TextView tv_yituopin_2016;  //2015已脱贫人数
	@ViewInject(R.id.tv_yituopin_2017)
	TextView tv_yituopin_2017;  //2017已脱贫人数

	/**
	 * 标志位，标志初始化已经完成
	 */
	private boolean isPrepared;

	PoorVillageBase lBases = null;
	/**
	 * 标识当前fragment是否可见
	 */
	private boolean isVisible;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_poorvillage_base, container, false);
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
	 * 2016年5月17日
	 * ZhaoDongShao
	 */
	private void initData() {
		Bundle bundle = getArguments();
		if (!StringUtil.isEmpty(bundle)) {
			lBases = (PoorVillageBase) bundle.getSerializable(Common.BUNDEL_KEY);
			tv_num.setText(String.valueOf(lBases.getPopulationnumber()));
//			tv_fuzeren.setText(lBases.getSecretaryname());
//			tv_phone.setText(lBases.getSecretaryphone());
			tv_fuzeren.setText(lBases.getPersonname());
			tv_phone.setText(lBases.getPersonphone());
			tv_poor.setText(lBases.getPkctpname());
			tv_poor_num.setText(String.valueOf(lBases.getPoorhousenm()));

			if (StringUtil.checkPhone(getTextView(tv_phone))) {
				tv_phone.setTextColor(ResourceUtil.getInstance().getColorById(R.color.color_00aff0));
				tv_phone.setClickable(true);
			}else {
				tv_phone.setTextColor(ResourceUtil.getInstance().getColorById(R.color.black));
				tv_phone.setClickable(false);
			}

			tv_cunshuxing.setText(String.valueOf(lBases.getPkcsxname())); //村属性
			tv_zonghushu.setText(String.valueOf(lBases.getPopzh())); //总户数
			tv_pinkunrenkoushu.setText(String.valueOf(lBases.getPoorpeoplenm())); //贫困人口数
			tv_dibaohushuo.setText(String.valueOf(lBases.getPopdbh())); //低保户数
			tv_dibaorenkoushu.setText(String.valueOf(lBases.getPopdbr())); //低保人口数
			poor_wubaorenhushu.setText(String.valueOf(lBases.getPopwbh())); //五保户数
			
			if (null==lBases.getPopwbr()) {
				poor_wubaorenkoushu.setText(" "); //五保人口数
			}else {
				poor_wubaorenkoushu.setText(String.valueOf(lBases.getPopwbr())); //五保人口数
			}
			if (null==lBases.getPopssmzr()) {
				poor_shaoshuminzushu.setText(" "); //少数民人口数
			}else {
				poor_shaoshuminzushu.setText(String.valueOf(lBases.getPopssmzr())); //少数民人口数
			}
			
			if(null==lBases.getPopfnr()){
				poor_funvrenkoushu.setText(" "); //妇女人口数
			}else {
				poor_funvrenkoushu.setText(String.valueOf(lBases.getPopfnr())); //妇女人口数
			}

			poor_canjirenkoushu.setText(String.valueOf(lBases.getPopcjr())); //残疾人口数
			poor_laodonglirenshu.setText(String.valueOf(lBases.getPopldr())); //劳动力人口数
			poor_waicuwugongshu.setText(String.valueOf(lBases.getPopdwg())); //外出务工人口数
			
			if(null==lBases.getLocation()){
				poor_weizhi.setText(" "); //位置
			}else {
				poor_weizhi.setText(String.valueOf(lBases.getLocation())); //位置
			}

			tv_yituopinrenkoushu.setText(String.valueOf(lBases.getYtp_p())); //已脱贫人口数
			tv_weituopinrenkoushu.setText(String.valueOf(lBases.getWtp_p())); //未脱贫人口数
			tv_fantuopinrenkoushu.setText(String.valueOf(lBases.getFp_p())); //返脱贫人口数
			tv_yinbingzhipinrenshu.setText(String.valueOf(lBases.getPoorreson_b())); //因病致贫人数
			tv_yincanzhipinrenshu.setText(String.valueOf(lBases.getPoorreson_c())); //因残致贫人数
			tv_yinxuezhipinrenshu.setText(String.valueOf(lBases.getPoorreson_x())); //因学致贫人数
			tv_quejishu.setText(String.valueOf(lBases.getPoorreson_j())); //缺技术致贫人数
			tv_quezijin.setText(String.valueOf(lBases.getPoorreson_z())); //缺资金致贫人数
			tv_yituopin_2014.setText(String.valueOf(lBases.getTzof_p())); //2014年已脱贫人数
			tv_yituopin_2015.setText(String.valueOf(lBases.getTzoff_p())); //2015年已脱贫人数
			tv_yituopin_2016.setText(String.valueOf(lBases.getTzox_p())); //2016年已脱贫人数
			tv_yituopin_2017.setText(String.valueOf(lBases.getTzos_p())); //2017年已脱贫人数

			tv_diyishuji_danwei.setText(lBases.getFirsecretaryunit());
			tv_diyishuji_dianhua.setText(lBases.getFirsecretaryphone());
			tv_diyishuji.setText(lBases.getFirsecretaryname());
			tv_baocunganbu_danwei.setText(lBases.getVillagecadresunit());
			if(StringUtil.isNotEmpty(lBases.getVillagecadresphone())){
				String[] baocunganbun_phones=lBases.getVillagecadresphone().split("、");
				if(null!=baocunganbun_phones){
					if(baocunganbun_phones.length==1){
						tv_baocunganbu_dianhua.setText(baocunganbun_phones[0]);
					}else if(baocunganbun_phones.length==2){
						tv_baocunganbu_dianhua_0.setText(baocunganbun_phones[0]);
						tv_baocunganbu_dianhua.setText(baocunganbun_phones[1]);
					}else {
						tv_baocunganbu_dianhua_0.setText("");
						tv_baocunganbu_dianhua.setText("");
					}
				}
			}
			tv_baocunganbu_mingcheng.setText(lBases.getVillagecadresname());

			tv_baocunganbu_dianhua_0.setTextColor(ResourceUtil.getInstance().getColorById(R.color.color_00aff0));
			tv_baocunganbu_dianhua_0.setClickable(true);
			tv_baocunganbu_dianhua.setTextColor(ResourceUtil.getInstance().getColorById(R.color.color_00aff0));
			tv_baocunganbu_dianhua.setClickable(true);
//			if (StringUtil.checkPhone(getTextView(tv_baocunganbu_dianhua))) {
//			}else {
//				tv_baocunganbu_dianhua.setTextColor(ResourceUtil.getInstance().getColorById(R.color.black));
//				tv_baocunganbu_dianhua.setClickable(false);
//			}

			if (StringUtil.checkPhone(getTextView(tv_diyishuji_dianhua))) {
				tv_diyishuji_dianhua.setTextColor(ResourceUtil.getInstance().getColorById(R.color.color_00aff0));
				tv_diyishuji_dianhua.setClickable(true);
			}else {
				tv_diyishuji_dianhua.setTextColor(ResourceUtil.getInstance().getColorById(R.color.black));
				tv_diyishuji_dianhua.setClickable(false);
			}

			String villageInfo=lBases.getVillageinfo();
			if(StringUtil.isEmpty(villageInfo)){
				poor_cunqingkuangshuoming.setText(""); //村情况说明--------------------
			}else {
				poor_cunqingkuangshuoming.setText(villageInfo); //村情况说明--------------------
			}

			poor_pinkunfashenglv.setText(lBases.getPovertypercent()); //贫困发生率
		}
	}

	@OnClick({R.id.rl_dutycompany,R.id.tv_phone,R.id.tv_baocunganbu_dianhua_0,R.id.tv_baocunganbu_dianhua,R.id.tv_diyishuji_dianhua}) //,R.id.tv_first_shuji_phone,R.id.tv_cunzhishu_phone
	public void onClick(View v){
		if (v.getId() == R.id.rl_dutycompany) { //帮扶责任人
			List<HelpCompany> list = lBases.getDutycompany();
			if (list!= null) {
				if (list.size()>0) {
					DutyCompanyDialog dialog = new DutyCompanyDialog();
					Bundle bundle = new Bundle();
					bundle.putSerializable(Common.BUNDEL_KEY, (Serializable) lBases.getDutycompany());
					dialog.setArguments(bundle);
					dialog.show(getFragmentManager(), "PoorVillageBaseFragment");
				}else {
					ShowToast("没有帮扶单位数据");
				}
			}else {
				ShowToast("没有帮扶单位数据");
			}
		}else if(v.getId()==R.id.tv_baocunganbu_dianhua_0){ //包村干部电话_0
			showPhoneDialog(getTextView(tv_baocunganbu_dianhua_0));
		}else if(v.getId()==R.id.tv_baocunganbu_dianhua){ //包村干部电话
			showPhoneDialog(getTextView(tv_baocunganbu_dianhua));
		}else if(v.getId()==R.id.tv_diyishuji_dianhua){ //第一书记电话
			showPhoneDialog(getTextView(tv_diyishuji_dianhua));
		}
//		else if (R.id.tv_first_shuji_phone == v.getId()) {
//			showPhoneDialog(getTextView(tv_first_shuji_phone));
//		}else if (R.id.tv_cunzhishu_phone == v.getId()) {
//			showPhoneDialog(getTextView(tv_cunzhishu_phone));
//		}
		else if (R.id.tv_phone == v.getId()) { //村负责人电话
			showPhoneDialog(getTextView(tv_phone));
		}
	}
	
}
