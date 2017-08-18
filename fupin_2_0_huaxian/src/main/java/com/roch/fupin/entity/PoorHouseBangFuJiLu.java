package com.roch.fupin.entity;

import java.util.List;

/**
 * 贫困户帮扶记录的实体类，里面含有帮扶记录的内部类
 *
 * 2016年10月31日 
 *
 */
public class PoorHouseBangFuJiLu extends BaseResult{

	private static final long serialVersionUID = 1L;
	
	/**
	 * list集合，里面含有多条帮扶记录对象的信息
	 */
	private List<BangFuJiLu> jsondata;
	
	/**
	 * 获取List<PoorFamily>类型的jsondata集合
	 * @return
	 * 2016年10月31日
	 */
	public List<BangFuJiLu> getJsondata() {
		return jsondata;
	}

	/**
	 * 设置List<PoorFamily>类型的jsondata集合
	 * @param jsondata
	 * 2016年10月31日
	 */
	public void setJsondata(List<BangFuJiLu> jsondata) {
		this.jsondata = jsondata;
	}
	
	public class BangFuJiLu{
		public String helpdate;
		public String helpdetail;
		public String helptitle;
		public String householderid;
		public String id;
		public String page;
		public String rows;
	}
}
