package com.roch.fupin.utils;

import android.content.Context;
import android.content.Intent;

import com.roch.fupin.HelpCompanyActivity;
import com.roch.fupin.HelpPeopleActivity;
import com.roch.fupin.InformationActivity;
import com.roch.fupin.MoreActivity;
import com.roch.fupin.NoPoorProjectActivity;
import com.roch.fupin.NoPoorProjectCaiZhengJuActivity;
import com.roch.fupin.NoPoorProjectCanLianActivity;
import com.roch.fupin.NoPoorProjectFuLianActivity;
import com.roch.fupin.NoPoorProjectFuPinBanActivity;
import com.roch.fupin.NoPoorProjectJiaoTiJuActivity;
import com.roch.fupin.NoPoorProjectJinDuTiXingActivity;
import com.roch.fupin.NoPoorProjectLinYeJuActivity;
import com.roch.fupin.NoPoorProjectMinZhengJuActivity;
import com.roch.fupin.NoPoorProjectNongWeiActivity;
import com.roch.fupin.NoPoorProjectRenLaoJuActivity;
import com.roch.fupin.NoPoorProjectWeiJiWeiActivity;
import com.roch.fupin.NoPoorProjectZhuJianJuActivity;
import com.roch.fupin.NoticBoardActivity;
import com.roch.fupin.PoorHouseActivity;
import com.roch.fupin.PoorPeopleBFCSActivity;
import com.roch.fupin.PoorPeopleCaseActivity;
import com.roch.fupin.PoorPeopleStatisticsActivity;
import com.roch.fupin.PoorPeopleTPQKActivity;
import com.roch.fupin.PoorPeopleWHCDActivity;
import com.roch.fupin.PoorVillageActivity;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.AdlCode;
import com.roch.fupin.entity.User;

/**
 * 打开Activity的工具类
 * @author ZhaoDongShao
 * 2016年5月26日
 */
public class OpenActivityUtil implements IOpenActivity {

	static OpenActivityUtil util;

	/**
	 * 获取打开Activity的工具类OpenActivityUtil的对象
	 * @return
	 * 2016年11月3日
	 */
	public static OpenActivityUtil getInstance() {
		if (util == null) {
			util = new OpenActivityUtil();
		}
		return util;
	}

	/**
	 * 打开activity的方法，返回一个intent对象，将点击时的名字传到打开的activity中，里面带有参数activity的名字和打开的activity的对象
	 */
	@Override
	public Intent OpenActivity(Context mContext, String ActivityName) {

		Intent intent = null;
		if (ActivityName == null || ActivityName.equals("")) {
			return intent;
		}

		if (ActivityName.equals(Common.EXTS_HELP_OBJECT_FAMILY_NAME)) { // 贫困户
			intent = new Intent(mContext, PoorHouseActivity.class);
		} else if (ActivityName.equals(Common.EXTS_HELP_OBJECT_VILLAGE_NAME)
				   || ActivityName.equals("行政村")) { // 贫困村改为行政村
			intent = new Intent(mContext, PoorVillageActivity.class);
		} else if (ActivityName.equals(Common.EXTS_HELP_SUBJECT_PROPLE_NAME)) { // 帮扶责任人
			intent = new Intent(mContext, HelpPeopleActivity.class);
		} else if (ActivityName.equals(Common.EXTS_HELP_SUBJECT_COMPANY_NAME)) { // 帮扶单位
			intent = new Intent(mContext, HelpCompanyActivity.class);
		} else if (ActivityName.equals(Common.EXTS_MORE_NAME)) { // 更多
			intent = new Intent(mContext, MoreActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NOTIC_BOARD_NAME)) { // 通知公告
			intent = new Intent(mContext, NoticBoardActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NOTIC_INFOMATION_NAME)) { // 信息宣传
			intent = new Intent(mContext, InformationActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_JAOTONG)
				|| ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_WATER)
				|| ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_XINCUNBAN)

		) { // 交通局、水利局、新村办
			intent = new Intent(mContext, NoPoorProjectActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_MINZHENGJU)) { // 民政局
			intent = new Intent(mContext, NoPoorProjectMinZhengJuActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_NONGWEI)) { // 农委
			intent = new Intent(mContext, NoPoorProjectNongWeiActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_JIAOTIJU)) { // 教体局
			intent = new Intent(mContext, NoPoorProjectJiaoTiJuActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_CANLIAN)) { // 残联
			intent = new Intent(mContext, NoPoorProjectCanLianActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_FULIAN)) { // 妇联
			intent = new Intent(mContext, NoPoorProjectFuLianActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_JINDUTIXING)) { // 进度提醒
			intent = new Intent(mContext, NoPoorProjectJinDuTiXingActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_CAIZHENGJU)) { // 财政局
			intent = new Intent(mContext, NoPoorProjectCaiZhengJuActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_ZHUJIANJU)) { // 财政局
			intent = new Intent(mContext, NoPoorProjectZhuJianJuActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_RENLAO)) {
			intent = new Intent(mContext, NoPoorProjectRenLaoJuActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_FUPUNBAN)) {
			intent = new Intent(mContext, NoPoorProjectFuPinBanActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_LINYEJU)) {
			intent = new Intent(mContext, NoPoorProjectLinYeJuActivity.class);
		} else if (ActivityName.equals(Common.EXTS_NO_POOR_PROJECT_WEIJIWEI)) {
			intent = new Intent(mContext, NoPoorProjectWeiJiWeiActivity.class);
		} else if (ActivityName.equals(Common.EXTS_STATISTIC_POOR_PEOPLE)) { // 贫困人口统计报表
			intent = new Intent(mContext, PoorPeopleStatisticsActivity.class);
		} else if (ActivityName.equals(Common.EXTS_WHCD)) { // 文化程度
			intent = new Intent(mContext, PoorPeopleWHCDActivity.class);
		} else if (ActivityName.equals(Common.EXTS_POOR_CASE)) { // 致贫原因信息
			intent = new Intent(mContext, PoorPeopleCaseActivity.class);
		} else if (ActivityName.equals(Common.EXTS_AGE)) { // 年龄
			intent = new Intent(mContext, PoorPeopleWHCDActivity.class);
		} else if (ActivityName.equals(Common.EXTS_WORK)) { // 就业收入
			intent = new Intent(mContext, PoorPeopleWHCDActivity.class);
		} else if (ActivityName.equals(Common.EXTS_PROJECT_MONEY)) { // 项目资金使用分析
			intent = new Intent(mContext, PoorPeopleWHCDActivity.class);
		} else if (ActivityName.equals(Common.EXTS_XMJD)) { // 项目进度分析
			intent = new Intent(mContext, PoorPeopleWHCDActivity.class);
		} else if (ActivityName.equals(Common.EXTS_BFCS_NAME)) { // 帮扶措施
			intent = new Intent(mContext, PoorPeopleBFCSActivity.class);
		} else if (ActivityName.equals(Common.EXTS_TPQK)) { // 脱贫情况
			intent = new Intent(mContext, PoorPeopleTPQKActivity.class);
		}else {
			intent=new Intent();
		}
		intent.putExtra(Common.INTENT_KEY, ActivityName);
		return intent;
	}

	/**
	 * 根据登陆用户的行政区划代码和选择城市后的行政区划代码，进行对比，判断选择的adl_cd是否属于自己管辖范围
	 * @return
	 * 2016年8月9日
	 * ZhaoDongShao
	 */
	public boolean getisAdl_CD(Context mContext, String username) {
		boolean is = false;
		User user = MyApplication.getInstance().getSharePreferencesUtilInstance().getLoginUser(mContext, username);
		AdlCode adlCode = MyApplication.getInstance().getSharePreferencesUtilInstance().getNowCity(mContext, username);

		if (user == null || user.getAdl_cd() == null || user.getAdl_cd().equals("")) {
			is = false;
		}
		if (adlCode == null || adlCode.getAd_cd() == null || adlCode.getAd_cd().equals("")) {
			is = false;
		}

		String adl_cd = user.getAdl_cd(); // 登陆用户的行政区划代码
		String select_adl_cd = adlCode.getAd_cd(); // 选择城市后的行政区划代码
		// 是否为市级
		boolean isCity = AdlcdUtil.isCity(adl_cd);
		// 是否为乡镇级
		boolean isTown = AdlcdUtil.isTown(adl_cd);
		// 是否为村级
		boolean isVillage = AdlcdUtil.isVillage(adl_cd);
		// 是否为区县级
		boolean isCountry = AdlcdUtil.isCountry(adl_cd);

		if (isCity) {
			String city_cd = AdlcdUtil.generateCityCode(select_adl_cd);// 获取选择城市的市级行政区划代码
			if (adl_cd.equals(city_cd)) {
				is = true;
			}
		} else if (isVillage) {
			if (adl_cd.equals(select_adl_cd)) {
				is = true;
			}
		} else if (isCountry) {
			String country_cd = AdlcdUtil.generateCountryCode(select_adl_cd); // 获取选择城市的区县级行政区代码
			if (adl_cd.equals(country_cd)) {
				is = true;
			}
		} else if (isTown) {
			String town_cd = AdlcdUtil.generateTownCode(select_adl_cd); // 获取选择城市的乡镇级行政区代码
			if (adl_cd.equals(town_cd)) {
				is = true;
			}
		}
		return is;
	}

	/**
	 * 根据点击的item的名字返回对应的url
	 * @param titlename
	 * @return
	 * 2016年6月1日
	 * ZhaoDongShao
	 */
	public static String getUrlString(String titlename) {
		String url = "";

		if (titlename == null || titlename.equals("")) {
			return url;
		}
		if (titlename.equals(Common.EXTS_NO_POOR_PROJECT_JAOTONG)) {
			url = URLs.NO_POOR_PROJECT_JAOTONG;
		} else if (titlename.equals(Common.EXTS_NO_POOR_PROJECT_WATER)) {
			url = URLs.NO_POOR_PROJECT_WATER;
		} else if (titlename.equals(Common.EXTS_NO_POOR_PROJECT_XINCUNBAN)) {
			url = URLs.NO_POOR_PROJECT_XINCUNBAN;
		}
		return url;
	}
}
