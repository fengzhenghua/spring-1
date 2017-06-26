package com.tydic.unicom.uac.base.database.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoCommendOperPo extends BasePo{

	private static final long serialVersionUID = 1L;

    private String  commend_no;//推荐人号码

    private String oper_no;//操作员工号
    private String create_date;//创建日期
    private String flag;//0--表示有效1--表示无效

	private String oper_name;

    public String getCommend_no() {
    	return commend_no;
    }

    public void setCommend_no(String commend_no) {
    	this.commend_no = commend_no;
    }

    public String getOper_no() {
    	return oper_no;
    }

    public void setOper_no(String oper_no) {
    	this.oper_no = oper_no;
    }

    public String getCreate_date() {
    	return create_date;
    }

    public void setCreate_date(String create_date) {
    	this.create_date = create_date;
    }

    public String getFlag() {
    	return flag;
    }

    public void setFlag(String flag) {
    	this.flag = flag;
    }

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

}
