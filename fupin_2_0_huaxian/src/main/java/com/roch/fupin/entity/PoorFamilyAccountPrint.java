package com.roch.fupin.entity;

/**
 * 贫困户台账信息实体
 * 
 * @author wf
 *
 */
public class PoorFamilyAccountPrint extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String householderiD;// 贫困户id
	private String incomemonth;// 月份
	private String total_income;// 总收入
	private String numcount;// 贫困人个数
	private double dibao;//
	private double yanglao;//
	private double xinnonghe;//
	private double huanlin;//
	private double shengtailin;//
	private double jiaoyu;//
	private double liangshi;//
	private double yangzhi;//
	private double guopin;//
	private double shumu;//
	private double jiuye;//
//	private double dilibaohu;// 地力保护补贴
	private double linggong;//
	private double juanzeng;//
	private double weiwen;//
	private double qita;//
	private String jobincome;// 工作收入
	private double businessexpenditure; // 生产经营性支出
	private double chanYeFuChi;// 产业扶持
	private double daoHuZengShou;// 到户增收
	private double total;// 总收入
	private double total_perp;// 人均收入

	public void setNumcount(String numcount) {
		this.numcount = numcount;
	}

	public String getNumcount() {
		return numcount;
	}

	public String getHouseholderiD() {
		return householderiD;
	}

	public void setHouseholderiD(String householderiD) {
		this.householderiD = householderiD;
	}

	public String getIncomemonth() {
		return incomemonth;
	}

	public void setIncomemonth(String incomemonth) {
		this.incomemonth = incomemonth;
	}

	public String getTotal_income() {
		return total_income;
	}

	public void setTotal_income(String total_income) {
		this.total_income = total_income;
	}

	public double getDibao() {
		return dibao;
	}

	public void setDibao(double dibao) {
		this.dibao = dibao;
	}

	public double getYanglao() {
		return yanglao;
	}

	public void setYanglao(double yanglao) {
		this.yanglao = yanglao;
	}

	public double getXinnonghe() {
		return xinnonghe;
	}

	public void setXinnonghe(double xinnonghe) {
		this.xinnonghe = xinnonghe;
	}

	public double getHuanlin() {
		return huanlin;
	}

	public void setHuanlin(double huanlin) {
		this.huanlin = huanlin;
	}

	public double getShengtailin() {
		return shengtailin;
	}

	public void setShengtailin(double shengtailin) {
		this.shengtailin = shengtailin;
	}

	public double getJiaoyu() {
		return jiaoyu;
	}

	public void setJiaoyu(double jiaoyu) {
		this.jiaoyu = jiaoyu;
	}

	public double getLiangshi() {
		return liangshi;
	}

	public void setLiangshi(double liangshi) {
		this.liangshi = liangshi;
	}

	public double getYangzhi() {
		return yangzhi;
	}

	public void setYangzhi(double yangzhi) {
		this.yangzhi = yangzhi;
	}

	public double getGuopin() {
		return guopin;
	}

	public void setGuopin(double guopin) {
		this.guopin = guopin;
	}

	public double getShumu() {
		return shumu;
	}

	public void setShumu(double shumu) {
		this.shumu = shumu;
	}

	public double getJiuye() {
		return jiuye;
	}

	public void setJiuye(double jiuye) {
		this.jiuye = jiuye;
	}

	public double getLinggong() {
		return linggong;
	}

	public void setLinggong(double linggong) {
		this.linggong = linggong;
	}

	public double getJuanzeng() {
		return juanzeng;
	}

	public void setJuanzeng(double juanzeng) {
		this.juanzeng = juanzeng;
	}

	public double getWeiwen() {
		return weiwen;
	}

	public void setWeiwen(double weiwen) {
		this.weiwen = weiwen;
	}

	public double getQita() {
		return qita;
	}

	public void setQita(double qita) {
		this.qita = qita;
	}

	public String getJobincome() {
		return jobincome;
	}

	public void setJobincome(String jobincome) {
		this.jobincome = jobincome;
	}

	public double getBusinessexpenditure() {
		return businessexpenditure;
	}

	public void setBusinessexpenditure(double businessexpenditure) {
		this.businessexpenditure = businessexpenditure;
	}

	public double getChanYeFuChi() {
		return chanYeFuChi;
	}

	public void setChanYeFuChi(double chanYeFuChi) {
		this.chanYeFuChi = chanYeFuChi;
	}

	public double getDaoHuZengShou() {
		return daoHuZengShou;
	}

	public void setDaoHuZengShou(double daoHuZengShou) {
		this.daoHuZengShou = daoHuZengShou;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotal_perp() {
		return total_perp;
	}

	public void setTotal_perp(double total_perp) {
		this.total_perp = total_perp;
	}

//	public double getDilibaohu() {
//		return dilibaohu;
//	}
//
//	public void setDilibaohu(double dilibaohu) {
//		this.dilibaohu = dilibaohu;
//	}
		
}
