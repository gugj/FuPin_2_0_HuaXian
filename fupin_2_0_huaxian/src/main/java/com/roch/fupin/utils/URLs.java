package com.roch.fupin.utils;

public class URLs {

        //荣聪服务器
//    	public static String IP = "192.168.1.113:8080";
	//公共版---邓州、朔州、济源正式库
//      public static String IP = "101.200.190.254:9102";
	//滑县正式库----老阿里云
//      public static String IP = "101.200.190.254:9300";
    //滑县正式库----新阿里云
    public static String IP = "47.93.120.179:9300";
    //武飞服务器
//	    public static String IP = "192.168.1.18:8080";
    //王子松服务器
//	    public static String IP = "192.168.1.153:8080";


    /**
     * WebService服务器端接口的BaseUrl <br/>
     * http://101.200.190.254:9100/poverty/app/
     */
    public static String WEB_SERVICE_URL = "http://" + IP + "/poverty/app/";
//    public static String WEB_SERVICE_URL = "http://" + IP + "/poverty_huaxian/app/";

    /**
     * image——url服务器端图片的BaseUrl <br/>
     * http://101.200.190.254:9100/poverty
     */
    public static String IMAGE_URL = "http://" + IP + "/poverty/";
//    public static String IMAGE_URL = "http://" + IP + "/poverty_huaxian/";

    /**
     * image——url服务器端图片的BaseUrl <br/>
     * http://101.200.190.254:9100/
     */
    public static String IMAGE_URL_New = "http://" + IP + "/";

//	public URLs(String address) {
//		if (address.equals("")) {
//			IP = "101.200.190.254:8999";
//		}else {
//			IP = address;
//		}
//	}

    /**
     * 登陆方法 <br/>
     * http://101.200.190.254:9100/poverty/app/sys_user/login.do
     */
    public static String LOGIN = WEB_SERVICE_URL + "sys_user/login.do";

    /**
     * 获取贫困户数据 <br/>
     * http://101.200.190.254:9100/poverty/app/poorfamily/dataList.do
     */
    public static String POOR_HOUSE_LISE = WEB_SERVICE_URL + "poorfamily/dataList.do";
    /**
     * APP版本更新的服务器地址<br/>
     * http://101.200.190.254:9100/poverty/app/poorfamily/dataList.do
     */
    public static String APP_Update = WEB_SERVICE_URL + "bbgx/getBbgxID.do";
    /**
     * 贫困户获取另附说明List列表<br/>
     * http://101.200.190.254:9100/poverty/app/basic_poorfamilyinfoapp/getFamilyinfoByHouseholderid.do
     */
    public static String PoorHouse_LingFuShuoMing_List = WEB_SERVICE_URL + "basic_poorfamilyinfoapp/getFamilyinfoByHouseholderid.do";
    /**
     * 贫困村获取另附说明List列表<br/>
     * http://101.200.190.254:9100/poverty/app/basic_poorvillageinfoapp/getVillageinfoByvillageid.do
     */
    public static String PoorVillage_LingFuShuoMing_List = WEB_SERVICE_URL + "basic_poorvillageinfoapp/getVillageinfoByvillageid.do";
    /**
     * 修改密码 <br/>
     * http://101.200.190.254:9100/poverty/app/sys_user/updatepassword.do
     */
    public static String Update_password = WEB_SERVICE_URL + "sys_user/updatepassword.do";
    /**
     * 获取贫困户数据 <br/>
     * http://101.200.190.254:9100/poverty/app/poorfamily/dataList.do
     */
//    public static String POOR_HOUSE_LISE = WEB_SERVICE_URL + "poorfamily/queryPoorFamilyApp";

    /**
     * 贫困户台账信息url <br/>
     * http://101.200.190.254:9100/poverty/app/poorfamily/queryPoorfamilyincomeApp.do
     */
    public static String POOR_HOUSE_ACCENT = WEB_SERVICE_URL + "poorfamily/queryPoorfamilyincomeApp.do";
    /**
     * 贫困户修改情况说明 <br/>
     * http://101.200.190.254:9100/poverty/app/basic_poorfamilyinfoapp/update.do
     */
    public static String POOR_HOUSE_QingKuangShuoMing = WEB_SERVICE_URL + "basic_poorfamilyinfoapp/update.do";
    /**
     * 贫困户添加情况说明 <br/>
     * http://101.200.190.254:9100/poverty/app/basic_poorfamilyinfoapp/save.do
     */
    public static String POOR_HOUSE_QingKuangShuoMing_Add = WEB_SERVICE_URL + "basic_poorfamilyinfoapp/save.do";
    /**
     * 贫困户删除情况说明 <br/>
     * http://101.200.190.254:9100/poverty/appbasic_poorfamilyinfoapp/delete.do
     */
    public static String POOR_HOUSE_QingKuangShuoMing_Delete = WEB_SERVICE_URL + "basic_poorfamilyinfoapp/delete.do";
    /**
     * 贫困村删除情况说明 <br/>
     * http://101.200.190.254:9100/poverty/basic_poorvillageinfoapp/delete.do
     */
    public static String POOR_Village_QingKuangShuoMing_Delete = WEB_SERVICE_URL + "basic_poorvillageinfoapp/delete.do";
    /**
     * 贫困村修改情况说明 <br/>
     * http://101.200.190.254:9100/poverty/app/basic_poorvillageinfoapp/update.do
     */
    public static String POOR_Village_QingKuangShuoMing = WEB_SERVICE_URL + "basic_poorvillageinfoapp/update.do";
    /**
     * 贫困村添加情况说明 <br/>
     * http://101.200.190.254:9100/poverty/app/basic_poorvillageinfoapp/save.do
     */
    public static String POOR_Village_QingKuangShuoMing_Add = WEB_SERVICE_URL + "basic_poorvillageinfoapp/save.do";

    /**
     * 贫困户帮扶记录--信息传参户IDurl <br/> ------------------------------------------------
     * http://101.200.190.254:9003/poverty/app/poorfamilyhelp/dataList
     */
    public static String POOR_HOUSE_BangFuJiLu_HourseId = WEB_SERVICE_URL + "poorfamilyhelp/dataList";
    
    /**
     * 贫困户帮扶记录信息--传参帮扶记录IDurl <br/> ------------------------------------------------
     * http://101.200.190.254:9003/poverty/app/poorfamilyhelp/getEntityById
     */
    public static String POOR_HOUSE_BangFuJiLu_bfjlID = WEB_SERVICE_URL + "poorfamilyhelp/getEntityById";

    /**
     * 获取贫困户详细数据 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorfamily/queryPoorfamilyApp.do
     */
    public static String POOR_HOUSE_DETAIL = WEB_SERVICE_URL + "poorfamily/queryPoorfamilyApp.do";
    /**
     * 获取贫困户照片数据 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorfamily/listPic.do
     */
    public static String POOR_HOUSE_Photo = WEB_SERVICE_URL + "poorfamily/listPic.do";
    /**
     * 获取贫困村照片数据 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorvillage/listPic.do
     */
    public static String POOR_Village_Photo = WEB_SERVICE_URL + "poorvillage/listPic.do";
    /**
     * 获取贫困户基本情况数据 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorfamily/queryPFamilyApp.do
     */
    public static String POOR_HOUSE_DETAIL_jiben = WEB_SERVICE_URL + "poorfamily/queryPFamilyApp.do";

    /**
     * 获取贫困村数据 <br/>
     *   http://101.200.190.254:9100/poverty/app/poorvillage/dataList.do
     */
    public static String POOR_VILLAGE_LIST = WEB_SERVICE_URL + "poorvillage/dataList.do";

    /**
     * 获取贫困村详细数据 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorvillage/queryPoorvillageApp.do
     */
    public static String POOR_VILLAGE_DETAIL = WEB_SERVICE_URL + "poorvillage/queryPoorvillageApp.do";
    /**
     * 贫困户删除照片 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorfamily/deleteImg.do
     */
    public static String POOR_House_Delete_Photo = WEB_SERVICE_URL + "poorfamily/deleteImg.do";
    /**
     * 贫困村删除照片 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorvillage/deleteImg.do
     */
    public static String POOR_Village_Delete_Photo = WEB_SERVICE_URL + "poorvillage/deleteImg.do";
    /**
     * 获取贫困村详细数据---2 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorvillage/queryPoorvillageByid
     */
    public static String POOR_VILLAGE_DETAIL_2 = WEB_SERVICE_URL + "poorvillage/queryPoorvillageByid";

    /**
     * 帮扶责任人 <br/>
     *  http://101.200.190.254:9100/poverty/app/helpdutypersonapp/dataList.do
     */
    public static String HELP_PEOPLE_LIST = WEB_SERVICE_URL + "helpdutypersonapp/dataList.do";

    /**
     * 帮扶责任人负责的贫困户 <br/>
     *  http://101.200.190.254:9100/poverty/app/helpdutypersonapp/queryPoorfamily.do
     */
    public static String HELP_PEOPLE_POOR_FAMILY_LIST = WEB_SERVICE_URL + "helpdutypersonapp/queryPoorfamily.do";

    /**
     * 帮扶单位 <br/>
     *  http://101.200.190.254:9100/poverty/app/helpdutycompanyapp/dataList.do
     */
    public static String HELP_COMPANY_LIST = WEB_SERVICE_URL + "helpdutycompanyapp/dataList.do";

    /**
     * 帮扶单位负责的贫困村 <br/>
     *  http://101.200.190.254:9100/poverty/app/helpdutycompanyapp/queryPoorvillage.do
     */
    public static String HELP_COMPANY_POOR_VILLAGE_LIST = WEB_SERVICE_URL + "helpdutycompanyapp/queryPoorvillage.do";

    /**
     * 信息宣传 <br/>
     *  http://101.200.190.254:9100/poverty/app/info_propaganda/dataList.do
     */
    public static String INFORMATION_LIST = WEB_SERVICE_URL + "info_propaganda/dataList.do";

    /**
     * 公告栏收件箱 <br/>
     *  http://101.200.190.254:9100/poverty/app/oa_notice/dataList.do
     */
    public static String NOTIC_BOARD_INBOX = WEB_SERVICE_URL + "oa_notice/dataList.do";

    /**
     * 公告栏发件箱 <br/>
     *  http://101.200.190.254:9100/poverty/app/oa_notice/dataList_post.do
     */
    public static String NOTIC_BOARD_OUTBOX = WEB_SERVICE_URL + "oa_notice/dataList_post.do";

//	/**
//	 * 获取乡镇
//	 */
//	public static String TOWNLET_LIST = WEB_SERVICE_URL + "zidian/queryZidian.do";
//	/**
//	 * 根据乡镇去查找村
//	 */
//	public static String VILLIGE_LIST = WEB_SERVICE_URL + "zidian/queryXcun.do";

    /**
     * 交通局项目 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_jiaotong/dataList.do
     */
    public static String NO_POOR_PROJECT_JAOTONG = WEB_SERVICE_URL + "project_jiaotong/dataList.do";

    /**
     * 水利局 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_shuili/dataList.do
     */
    public static String NO_POOR_PROJECT_WATER = WEB_SERVICE_URL + "project_shuili/dataList.do";

    /**
     * 新村办 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_xincunban/dataList.do
     */
    public static String NO_POOR_PROJECT_XINCUNBAN = WEB_SERVICE_URL + "project_xincunban/dataList.do";

    /**
     * 民政局 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_minzhengapp/dataList.do
     */
    public static String NO_POOR_PROJECT_MINZHENGJU = WEB_SERVICE_URL + "project_minzhengapp/dataList.do";

    /**
     * 民政局详情 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_minzhengapp/queryminzhengApp.do
     */
    public static String NO_POOR_PROJECT_MINZHENGJU_DETAIL = WEB_SERVICE_URL + "project_minzhengapp/queryminzhengApp.do";

    /**
     * 农委 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_nongwei/dataList.do
     */
    public static String NO_POOR_PROJECT_NONGWEI = WEB_SERVICE_URL + "project_nongwei/dataList.do";

    /**
     * 农委详情 <br/>
     *   http://101.200.190.254:9100/poverty/app/project_nongwei/querynongweiApp.do
     */
    public static String NO_POOR_PROJECT_NONGWEI_DETAIL = WEB_SERVICE_URL + "project_nongwei/querynongweiApp.do";

    /**
     * 教体局 <br/>
     *   http://101.200.190.254:9100/poverty/app/project_jiaoti/dataList.do
     */
    public static String NO_POOR_PROJECT_JIAOTIJU = WEB_SERVICE_URL + "project_jiaoti/dataList.do";

    /**
     * 教体局详情 <br/>
     *   http://101.200.190.254:9100/poverty/app/project_jiaoti/queryjiaotiApp.do
     */
    public static String NO_POOR_PROJECT_JIAOTIJU_DETAIL = WEB_SERVICE_URL + "project_jiaoti/queryjiaotiApp.do";

    /**
     * 残联——就业培训详情 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_canlian/querycljyApp.do
     */
    public static String NO_POOR_PROJECT_CANLIAN_JYPX_DETAIL = WEB_SERVICE_URL + "project_canlian/querycljyApp.do";

    /**
     * 残联——危房改造 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_canlian/dataList.do
     */
    public static String NO_POOR_PROJECT_CANLIAN_WFGZ = WEB_SERVICE_URL + "project_canlian/dataList.do";

    /**
     * 残联——就业培训 <br/>
     *   http://101.200.190.254:9100/poverty/app/project_canlian/dataList1.do
     */
    public static String NO_POOR_PROJECT_CANLIAN_JYPX = WEB_SERVICE_URL + "project_canlian/dataList1.do";

    /**
     * 妇联 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_fulian/dataList.do
     */
    public static String NO_POOR_PROJECT_FULIAN = WEB_SERVICE_URL + "project_fulian/dataList.do";

    /**
     * 妇联详情 <br/>
     *   http://101.200.190.254:9100/poverty/app/project_fulian/queryfulianApp.do
     */
    public static String NO_POOR_PROJECT_FULIAN_DETAIL = WEB_SERVICE_URL + "project_fulian/queryfulianApp.do";

    /**
     * 进度提醒 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_jindutixing/dataList.do
     */
    public static String NO_POOR_PROJECT_JINDUTIXING = WEB_SERVICE_URL + "project_jindutixing/dataList.do";

    /**
     * 财政局 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_caizhengjuapp/dataList.do
     */
    public static String NO_POOR_PROJECT_CAIZHENGJU = WEB_SERVICE_URL + "project_caizhengjuapp/dataList.do";

    /**
     * 财政局详情页 <br/>
     *   http://101.200.190.254:9100/poverty/app/project_caizhengjuapp/queryxingmuApp.do
     */
    public static String NO_POOR_PROJECT_CAIZHENGJU_DETAIL = WEB_SERVICE_URL + "project_caizhengjuapp/queryxingmuApp.do";

    /**
     * 住建局 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_zhujianapp/dataList.do
     */
    public static String NO_POOR_PROJECT_ZHUJIANJU = WEB_SERVICE_URL + "project_zhujianapp/dataList.do";

    /**
     * 住建局详情页 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_zhujianapp/queryxingmuApp.do
     */
    public static String NO_POOR_PROJECT_ZHUJIANJU_DETAIL = WEB_SERVICE_URL + "project_zhujianapp/queryxingmuApp.do";

    /**
     * 人劳局 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_renlao/dataList.do
     */
    public static String NO_POOR_PROJECT_RENLAOJU = WEB_SERVICE_URL + "project_renlao/dataList.do";

    /**
     * 人劳局详情页 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_renlao/queryrenlaoApp.do
     */
    public static String NO_POOR_PROJECT_RENLAOJU_DETAIL = WEB_SERVICE_URL + "project_renlao/queryrenlaoApp.do";

    /**
     * 扶贫办 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_fupinban_train/dataList.do
     */
    public static String NO_POOR_PROJECT_FUPINBAN = WEB_SERVICE_URL + "project_fupinban_train/dataList.do";

    /**
     * 扶贫办详情 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_fupinban_train/queryfupinbanApp.do
     */
    public static String NO_POOR_PROJECT_FUPINBAN_DETAIL = WEB_SERVICE_URL + "project_fupinban_train/queryfupinbanApp.do";

    /**
     * 林业局---道路绿化 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_linyeju/dataList.do
     */
    public static String NO_POOR_PROJECT_LINYEJU_DLLH = WEB_SERVICE_URL + "project_linyeju/dataList.do";

    /**
     * 林业局---林下经济  <br/>
     *  http://101.200.190.254:9100/poverty/app/project_linyeeconomyapp/dataList.do
     */
    public static String NO_POOR_PROJECT_LINYEJU_LXJJ = WEB_SERVICE_URL + "project_linyeeconomyapp/dataList.do";

    /**
     * 林业局---林下经济详情 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_linyeeconomyapp/queryxingmuApp.do
     */
    public static String NO_POOR_PROJECT_LINYEJU_LXJJ_DETAIL = WEB_SERVICE_URL + "project_linyeeconomyapp/queryxingmuApp.do";

    /**
     * 卫计委 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_weijiweiapp/dataList.do
     */
    public static String NO_POOR_PROJECT_WEIJIWEI = WEB_SERVICE_URL + "project_weijiweiapp/dataList.do";

    /**
     * 卫计委详情 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_weijiweiapp/queryxingmuApp.do
     */
    public static String NO_POOR_PROJECT_WEIJIWEI_DETAIL = WEB_SERVICE_URL + "project_weijiweiapp/queryxingmuApp.do";

    /**
     * 县 <br/>
     *  http://101.200.190.254:9100/poverty/app/sys_user/getCountryAdcd.do
     */
    public static String COUNTY = WEB_SERVICE_URL + "sys_user/getCountryAdcd.do";

    /**
     * 镇 <br/>
     *  http://101.200.190.254:9100/poverty/app/sys_user/getTownAdcd.do
     */
    public static String TOWN = WEB_SERVICE_URL + "sys_user/getTownAdcd.do";

    /**
     * 村 <br/>
     *  http://101.200.190.254:9100/poverty/app/sys_user/getVillageAdcd.do
     */
    public static String VILLAGE = WEB_SERVICE_URL + "sys_user/getVillageAdcd.do";

    /**
     * 贫困人口统计报表 <br/>
     *  http://101.200.190.254:9100/poverty/app/pkrk/dataList.do
     */
    public static String POOR_PEOPLE_STATISTIC = WEB_SERVICE_URL + "pkrk/dataList.do";

    /**
     * 文化程度统计 <br/>
     *  http://101.200.190.254:9100/poverty/app/wenhua/initEduTabData.do
     */
    public static String POOR_PEOPLE_WHCD = WEB_SERVICE_URL + "wenhua/initEduTabData.do";

    /**
     * 年龄统计 <br/>
     *  http://101.200.190.254:9100/poverty/app/anage/initAgeTabData.do
     */
    public static String POOR_PEOPLE_AGE = WEB_SERVICE_URL + "anage/initAgeTabData.do";

    /**
     * 就业收入统计 <br/>
     *  http://101.200.190.254:9100/poverty/app/jysr/initIncomeTabData.do
     */
    public static String POOR_PEOPLE_WORK = WEB_SERVICE_URL + "jysr/initIncomeTabData.do";

    /**
     * 致贫原因 <br/>
     *  http://101.200.190.254:9100/poverty/app/zpyy/initPieData.do
     */
    public static String POOR_PEOPLE_CASE = WEB_SERVICE_URL + "zpyy/initPieData.do";

    /**
     * 项目资金使用 <br/>
     *  http://101.200.190.254:9100/poverty/app/xmzjsy/charts.do
     */
    public static String POOR_PEOPEL_PROJECT_MONEY = WEB_SERVICE_URL + "xmzjsy/charts.do";

    /**
     * 项目进度分析 <br/>
     *  http://101.200.190.254:9100/poverty/app/xmjd/chartstu.do
     */
    public static String POOR_PEOPEL_XMJD = WEB_SERVICE_URL + "xmjd/chartstu.do";

    /**
     * 图片上传 <br/>
     *  http://101.200.190.254:9100/poverty/app/poorfamily/upload.do
     */
    public static String IMAGE_UP_LOAD = WEB_SERVICE_URL + "poorfamily/upload.do";

    public static String VILLAGE_IMAGE_UP_LOAD = WEB_SERVICE_URL + "poorvillage/uploadvillage.do";

    /**
     * 获取行政层级代码 <br/>
     *  http://101.200.190.254:9100/poverty/app/tsad/queryadlcd.do
     */
    public static String CITY_CODE = WEB_SERVICE_URL + "tsad/queryadlcd.do";

    /**
     * 致贫原因详情 <br/>
     *   http://101.200.190.254:9100/poverty/app/zhipinyuanyin/dataList.do
     */
    public static String CASE_DETAIL = WEB_SERVICE_URL + "zhipinyuanyin/dataList.do";

    /**
     * 帮扶措施统计 <br/>
     *   http://101.200.190.254:9100/poverty/app/bangfucuoshi/dataList.do
     */
    public static String POOR_PEOPLE_BFCS = WEB_SERVICE_URL + "bangfucuoshi/dataList.do";

    /**
     * 脱贫情况 <br/>
     *  http://101.200.190.254:9100/poverty/app/tuopinqingkuang/dataList.do
     */
    public static String POOR_PROPLE_TPQK = WEB_SERVICE_URL + "tuopinqingkuang/dataList.do";

    /**
     * 民政局 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_minzhengapp/queryminzhenghz.do
     */
    public static String MINZHENGJU_STISTIC = WEB_SERVICE_URL + "project_minzhengapp/queryminzhenghz.do";

    /**
     * 卫计委 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_weijiweiapp/wjwbbhz.do
     */
    public static String WEIJIWEI_STISTIC = WEB_SERVICE_URL + "project_weijiweiapp/wjwbbhz.do";

    /**
     * 教体局 <br/>
     *  http://101.200.190.254:9100/poverty/app/project_jiaoti/queryjiaotihz.do
     */
    public static String JIAOTIJU_STISTIC = WEB_SERVICE_URL + "project_jiaoti/queryjiaotihz.do";
}
