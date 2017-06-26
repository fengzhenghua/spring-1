package com.tydic.unicom.uac.base.database.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ZBInfoAgentRelationPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String agent_no; // 本地编码
	private String chnl_code;// 总部渠道编码
	private String dev_code;// 总部发展工号
	private String chnl_name;// 总部渠道名称
	private String manage_dept_no;// 总部部门
	private String belong_type;
	
	private String flag_jl;
	private String zb_dev_code;
	private String main_flag;// 1 默认对应（1对1上报总部，允许本地进行业务受理） 2 主对应 （1对多，但是一个本地对应的发展人必须是唯一，不允许业务受理，用于上报业务量之类） 0 非主对应 （1对多不允许业务受理，用于历史佣金上报）
	
	public String getChnl_code() {
		return chnl_code;
	}
	
	public String getDev_code() {
		return dev_code;
	}
	
	public void setDev_code(String dev_code) {
		this.dev_code = dev_code;
	}
	
	public String getChnl_name() {
		return chnl_name;
	}
	
	public void setChnl_name(String chnl_name) {
		this.chnl_name = chnl_name;
	}
	
	public String getManage_dept_no() {
		return manage_dept_no;
	}
	
	public void setManage_dept_no(String manage_dept_no) {
		this.manage_dept_no = manage_dept_no;
	}
	
	public String getBelong_type() {
		return belong_type;
	}
	
	public void setBelong_type(String belong_type) {
		this.belong_type = belong_type;
	}
	
	public String getAgent_no() {
		return agent_no;
	}
	
	public void setAgent_no(String agent_no) {
		this.agent_no = agent_no;
	}
	
	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}
	
	public String getFlag_jl() {
		return flag_jl;
	}
	
	public void setFlag_jl(String flag_jl) {
		this.flag_jl = flag_jl;
	}
	
	public String getZb_dev_code() {
		return zb_dev_code;
	}
	
	public void setZb_dev_code(String zb_dev_code) {
		this.zb_dev_code = zb_dev_code;
	}
	
	public String getMain_flag() {
		return main_flag;
	}
	
	public void setMain_flag(String main_flag) {
		this.main_flag = main_flag;
	}
}
