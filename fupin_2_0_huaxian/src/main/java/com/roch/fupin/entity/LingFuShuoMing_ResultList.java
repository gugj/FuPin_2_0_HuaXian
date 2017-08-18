package com.roch.fupin.entity;

import java.util.List;

/**
 * 另附说明ResultList
 */
public class LingFuShuoMing_ResultList extends BaseResult{

	private static final long serialVersionUID = 1L;

	private String total;
	private List<LingFuShuoMing> rows;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<LingFuShuoMing> getRows() {
		return rows;
	}

	public void setRows(List<LingFuShuoMing> rows) {
		this.rows = rows;
	}

}
