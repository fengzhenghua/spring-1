package com.tydic.unicom.uac.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class RewardOrderInfoVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String jsession_id;
	private String order_id;
	private String acc_nbr;
	private String oper_id;

	public String getJsession_id() {
		return jsession_id;
	}

	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	@Override
	public String toString() {
		return "RewardOrderInfoVo [jsession_id=" + jsession_id + ", order_id=" + order_id + ", acc_nbr=" + acc_nbr + ", oper_id=" + oper_id
				+ "]";
	}

}
