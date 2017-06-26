/**
 * @ProjectName: uss_service
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author wanghao
 * @date: 2014年10月11日 下午11:21:18
 * @Title: ZBInfoAgentVo.java
 * @Package com.tydic.unicom.crm.uss.vo.pub
 * @Description: TODO
 */
package com.tydic.unicom.uac.business.common.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.tydic.unicom.webUtil.BaseVo;

/**
 * <p>
 * </p>
 * @author wanghao 2014年10月11日 下午11:21:18
 * @ClassName ZBInfoAgentVo
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2014年10月11日
 * @modify by reason:{方法名}:{原因}
 */
public class ZBInfoAgentVo extends BaseVo {
	
	/** 类变量---------------------------------------------------------------- */
	private static final long serialVersionUID = 1L;
	/** 实例变量-------------------------------------------------------------- */
	
	/** 共有字段-------------------------------------------------------------- */
	
	/** 受保护字段------------------------------------------------------------ */
	
	/** 私有字段-------------------------------------------------------------- */
	// private String chnl_code;// 渠道编码
	// private String province_code;// 省份
	// private String city_code;// 市区
	// private String manager_area_code;// 地区
	// private String chnl_kind_id;// 渠道类型编码
	
	@XStreamAlias("OPERATE_TYPE")
	private String operate_type;//
	@XStreamAlias("CHNL_CODE")
	private String chnl_code;//
	@XStreamAlias("CHNL_NAME")
	private String chnl_name;//
	@XStreamAlias("CHNL_DESC")
	private String chnl_desc;//
	@XStreamAlias("CHNL_ORG_ID")
	private String chnl_org_id;//
	@XStreamAlias("STATE")
	private String state;//
	@XStreamAlias("STATE_DESC")
	private String state_desc;//
	@XStreamAlias("CHNL_KIND_ID")
	private String chnl_kind_id;//
	@XStreamAlias("LOCAL_KIND_ID")
	private String local_kind_id;//
	@XStreamAlias("CHNL_CLASS_ID")
	private String chnl_class_id;//
	@XStreamAlias("CHAIN_FLAG")
	private String chain_flag;//
	@XStreamAlias("IS_RWD_CNT")
	private String is_rwd_cnt;//
	@XStreamAlias("PAY_SCOPE")
	private String pay_scope;//
	@XStreamAlias("PAY_CHNL_CODE")
	private String pay_chnl_code;//
	@XStreamAlias("SUPER_CHNL_CODE")
	private String super_chnl_code;//
	@XStreamAlias("SEFL_CHNL_CODE")
	private String sefl_chnl_code;//
	@XStreamAlias("RWD_CNT_DATE")
	private String rwd_cnt_date;//
	@XStreamAlias("PROVINCE_CODE")
	private String province_code;//
	@XStreamAlias("CITY_CODE")
	private String city_code;//
	@XStreamAlias("MANAGER_AREA_CODE")
	private String manager_area_code;//
	@XStreamAlias("AREA_TYPE")
	private String area_type;//
	@XStreamAlias("CHNL_CHAIN_LEVEL")
	private String chnl_chain_level;//
	@XStreamAlias("CHNL_LEVEL")
	private String chnl_level;//
	@XStreamAlias("IS_INPUT_SYSTEM")
	private String is_input_system;//
	@XStreamAlias("SYSTEM_NUM")
	private String system_num;//
	@XStreamAlias("IS_MINI_HALL")
	private String is_mini_hall;//
	@XStreamAlias("CHNL_AREA_KIND_ID")
	private String chnl_area_kind_id;//
	@XStreamAlias("BANK_CODE")
	private String bank_code;//
	@XStreamAlias("BANK_NO")
	private String bank_no;//
	@XStreamAlias("BANK_ACCT_NAME")
	private String bank_acct_name;//
	@XStreamAlias("ADDRESS")
	private String address;//
	@XStreamAlias("CHNL_LINKMAN_NAME")
	private String chnl_linkman_name;//
	@XStreamAlias("CHNL_LINKMAN_SEX")
	private String chnl_linkman_sex;//
	@XStreamAlias("CHNL_EMAIL")
	private String chnl_email;//
	@XStreamAlias("CHNL_FAX")
	private String chnl_fax;//
	@XStreamAlias("CHNL_ADDR")
	private String chnl_addr;//
	@XStreamAlias("CHNL_OFFICE_PHONE")
	private String chnl_office_phone;//
	@XStreamAlias("CHNL_PHONE")
	private String chnl_phone;//
	@XStreamAlias("CHNL_POSTALCODE")
	private String chnl_postalcode;//
	@XStreamAlias("MANAGER_DEPT_CODE")
	private String manager_dept_code;//
	@XStreamAlias("MANAGER_STAFF_CODE")
	private String manager_staff_code;//
	@XStreamAlias("MANAGER_PHONE")
	private String manager_phone;//
	@XStreamAlias("REMARK")
	private String remark;//
	@XStreamAlias("AFFILIATETIME")
	private String affiliatetime;//
	@XStreamAlias("CREATE_STAFF_CODE")
	private String create_staff_code;//
	@XStreamAlias("CREATE_TIME")
	private String create_time;//
	@XStreamAlias("DEV_OPERATE")
	private String dev_operate;//
	@XStreamAlias("DEV_CODE")
	private String dev_code;//
	@XStreamAlias("DEV_TYPE_ID")
	private String dev_type_id;//
	@XStreamAlias("DEV_NAME")
	private String dev_name;//
	@XStreamAlias("DEV_STAFF_CODE")
	private String dev_staff_code;//
	@XStreamAlias("USER_PID")
	private String user_pid;//
	@XStreamAlias("LINKMAN_PHONE")
	private String linkman_phone;//
	@XStreamAlias("LINKMAN_EMAIL")
	private String linkman_email;//
	@XStreamAlias("GROUP_ACCT")
	private String group_acct;//
	@XStreamAlias("LINKMAN_ADDR")
	private String linkman_addr;//
	@XStreamAlias("LINKMAN_POSTCODE")
	private String linkman_postcode;//
	@XStreamAlias("D_REMARK")
	private String d_remark;//
	@XStreamAlias("BSS_SYS_CODE")
	private String bss_sys_code;//
	@XStreamAlias("BSS_SYS_CODE2")
	private String bss_sys_code2;//
	@XStreamAlias("FLAG_JL")
	private String flag_jl;//
	@XStreamAlias("VALID_FLAG")
	private String valid_flag;//
	@XStreamAlias("LIQUIDATION_PAY_FLAG")
	private String liquidation_pay_flag;//
	@XStreamAlias("LIQUIDATION_START_DATE")
	private String liquidation_start_date;//
	private String OPER_TYPE;//
	@XStreamAlias("JOINT_FLAG")
	private String joint_flag;//
	@XStreamAlias("BUILD_AREA")
	private String build_area;//
	@XStreamAlias("LONGITUDE")
	private String longitude;//
	@XStreamAlias("LATITUDE")
	private String latitude;//
	@XStreamAlias("JOINT_SOC_FLAG")
	private String joint_soc_flag;//
	@XStreamAlias("AGENT_BUSINESS_FORM")
	private String agent_business_form;//
	@XStreamAlias("TERMINAL_TYPE")
	private String terminal_type;//
	@XStreamAlias("AUTH_POWER_TYPE")
	private String auth_power_type;//
	@XStreamAlias("APPLE_ID")
	private String apple_id;//
	@XStreamAlias("AUTH_TYPE")
	private String auth_type;//
	
	/** get/set方法----------------------------------------------------------- */
	public String getChnl_code() {
		return chnl_code;
	}
	
	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}
	
	public String getProvince_code() {
		return province_code;
	}
	
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	
	public String getCity_code() {
		return city_code;
	}
	
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	
	public String getManager_area_code() {
		return manager_area_code;
	}
	
	public void setManager_area_code(String manager_area_code) {
		this.manager_area_code = manager_area_code;
	}
	
	public String getChnl_kind_id() {
		return chnl_kind_id;
	}
	
	public void setChnl_kind_id(String chnl_kind_id) {
		this.chnl_kind_id = chnl_kind_id;
	}
	
	public String getOperate_type() {
		return operate_type;
	}
	
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}
	
	public String getChnl_name() {
		return chnl_name;
	}
	
	public void setChnl_name(String chnl_name) {
		this.chnl_name = chnl_name;
	}
	
	public String getChnl_desc() {
		return chnl_desc;
	}
	
	public void setChnl_desc(String chnl_desc) {
		this.chnl_desc = chnl_desc;
	}
	
	public String getChnl_org_id() {
		return chnl_org_id;
	}
	
	public void setChnl_org_id(String chnl_org_id) {
		this.chnl_org_id = chnl_org_id;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState_desc() {
		return state_desc;
	}
	
	public void setState_desc(String state_desc) {
		this.state_desc = state_desc;
	}
	
	public String getLocal_kind_id() {
		return local_kind_id;
	}
	
	public void setLocal_kind_id(String local_kind_id) {
		this.local_kind_id = local_kind_id;
	}
	
	public String getChnl_class_id() {
		return chnl_class_id;
	}
	
	public void setChnl_class_id(String chnl_class_id) {
		this.chnl_class_id = chnl_class_id;
	}
	
	public String getChain_flag() {
		return chain_flag;
	}
	
	public void setChain_flag(String chain_flag) {
		this.chain_flag = chain_flag;
	}
	
	public String getIs_rwd_cnt() {
		return is_rwd_cnt;
	}
	
	public void setIs_rwd_cnt(String is_rwd_cnt) {
		this.is_rwd_cnt = is_rwd_cnt;
	}
	
	public String getPay_scope() {
		return pay_scope;
	}
	
	public void setPay_scope(String pay_scope) {
		this.pay_scope = pay_scope;
	}
	
	public String getPay_chnl_code() {
		return pay_chnl_code;
	}
	
	public void setPay_chnl_code(String pay_chnl_code) {
		this.pay_chnl_code = pay_chnl_code;
	}
	
	public String getSuper_chnl_code() {
		return super_chnl_code;
	}
	
	public void setSuper_chnl_code(String super_chnl_code) {
		this.super_chnl_code = super_chnl_code;
	}
	
	public String getSefl_chnl_code() {
		return sefl_chnl_code;
	}
	
	public void setSefl_chnl_code(String sefl_chnl_code) {
		this.sefl_chnl_code = sefl_chnl_code;
	}
	
	public String getRwd_cnt_date() {
		return rwd_cnt_date;
	}
	
	public void setRwd_cnt_date(String rwd_cnt_date) {
		this.rwd_cnt_date = rwd_cnt_date;
	}
	
	public String getArea_type() {
		return area_type;
	}
	
	public void setArea_type(String area_type) {
		this.area_type = area_type;
	}
	
	public String getChnl_chain_level() {
		return chnl_chain_level;
	}
	
	public void setChnl_chain_level(String chnl_chain_level) {
		this.chnl_chain_level = chnl_chain_level;
	}
	
	public String getChnl_level() {
		return chnl_level;
	}
	
	public void setChnl_level(String chnl_level) {
		this.chnl_level = chnl_level;
	}
	
	public String getIs_input_system() {
		return is_input_system;
	}
	
	public void setIs_input_system(String is_input_system) {
		this.is_input_system = is_input_system;
	}
	
	public String getSystem_num() {
		return system_num;
	}
	
	public void setSystem_num(String system_num) {
		this.system_num = system_num;
	}
	
	public String getIs_mini_hall() {
		return is_mini_hall;
	}
	
	public void setIs_mini_hall(String is_mini_hall) {
		this.is_mini_hall = is_mini_hall;
	}
	
	public String getChnl_area_kind_id() {
		return chnl_area_kind_id;
	}
	
	public void setChnl_area_kind_id(String chnl_area_kind_id) {
		this.chnl_area_kind_id = chnl_area_kind_id;
	}
	
	public String getBank_code() {
		return bank_code;
	}
	
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	
	public String getBank_no() {
		return bank_no;
	}
	
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	
	public String getBank_acct_name() {
		return bank_acct_name;
	}
	
	public void setBank_acct_name(String bank_acct_name) {
		this.bank_acct_name = bank_acct_name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getChnl_linkman_name() {
		return chnl_linkman_name;
	}
	
	public void setChnl_linkman_name(String chnl_linkman_name) {
		this.chnl_linkman_name = chnl_linkman_name;
	}
	
	public String getChnl_linkman_sex() {
		return chnl_linkman_sex;
	}
	
	public void setChnl_linkman_sex(String chnl_linkman_sex) {
		this.chnl_linkman_sex = chnl_linkman_sex;
	}
	
	public String getChnl_email() {
		return chnl_email;
	}
	
	public void setChnl_email(String chnl_email) {
		this.chnl_email = chnl_email;
	}
	
	public String getChnl_fax() {
		return chnl_fax;
	}
	
	public void setChnl_fax(String chnl_fax) {
		this.chnl_fax = chnl_fax;
	}
	
	public String getChnl_addr() {
		return chnl_addr;
	}
	
	public void setChnl_addr(String chnl_addr) {
		this.chnl_addr = chnl_addr;
	}
	
	public String getChnl_office_phone() {
		return chnl_office_phone;
	}
	
	public void setChnl_office_phone(String chnl_office_phone) {
		this.chnl_office_phone = chnl_office_phone;
	}
	
	public String getChnl_phone() {
		return chnl_phone;
	}
	
	public void setChnl_phone(String chnl_phone) {
		this.chnl_phone = chnl_phone;
	}
	
	public String getChnl_postalcode() {
		return chnl_postalcode;
	}
	
	public void setChnl_postalcode(String chnl_postalcode) {
		this.chnl_postalcode = chnl_postalcode;
	}
	
	public String getManager_dept_code() {
		return manager_dept_code;
	}
	
	public void setManager_dept_code(String manager_dept_code) {
		this.manager_dept_code = manager_dept_code;
	}
	
	public String getManager_staff_code() {
		return manager_staff_code;
	}
	
	public void setManager_staff_code(String manager_staff_code) {
		this.manager_staff_code = manager_staff_code;
	}
	
	public String getManager_phone() {
		return manager_phone;
	}
	
	public void setManager_phone(String manager_phone) {
		this.manager_phone = manager_phone;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAffiliatetime() {
		return affiliatetime;
	}
	
	public void setAffiliatetime(String affiliatetime) {
		this.affiliatetime = affiliatetime;
	}
	
	public String getCreate_staff_code() {
		return create_staff_code;
	}
	
	public void setCreate_staff_code(String create_staff_code) {
		this.create_staff_code = create_staff_code;
	}
	
	public String getCreate_time() {
		return create_time;
	}
	
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	public String getDev_operate() {
		return dev_operate;
	}
	
	public void setDev_operate(String dev_operate) {
		this.dev_operate = dev_operate;
	}
	
	public String getDev_code() {
		return dev_code;
	}
	
	public void setDev_code(String dev_code) {
		this.dev_code = dev_code;
	}
	
	public String getDev_type_id() {
		return dev_type_id;
	}
	
	public void setDev_type_id(String dev_type_id) {
		this.dev_type_id = dev_type_id;
	}
	
	public String getDev_name() {
		return dev_name;
	}
	
	public void setDev_name(String dev_name) {
		this.dev_name = dev_name;
	}
	
	public String getDev_staff_code() {
		return dev_staff_code;
	}
	
	public void setDev_staff_code(String dev_staff_code) {
		this.dev_staff_code = dev_staff_code;
	}
	
	public String getUser_pid() {
		return user_pid;
	}
	
	public void setUser_pid(String user_pid) {
		this.user_pid = user_pid;
	}
	
	public String getLinkman_phone() {
		return linkman_phone;
	}
	
	public void setLinkman_phone(String linkman_phone) {
		this.linkman_phone = linkman_phone;
	}
	
	public String getLinkman_email() {
		return linkman_email;
	}
	
	public void setLinkman_email(String linkman_email) {
		this.linkman_email = linkman_email;
	}
	
	public String getGroup_acct() {
		return group_acct;
	}
	
	public void setGroup_acct(String group_acct) {
		this.group_acct = group_acct;
	}
	
	public String getLinkman_addr() {
		return linkman_addr;
	}
	
	public void setLinkman_addr(String linkman_addr) {
		this.linkman_addr = linkman_addr;
	}
	
	public String getLinkman_postcode() {
		return linkman_postcode;
	}
	
	public void setLinkman_postcode(String linkman_postcode) {
		this.linkman_postcode = linkman_postcode;
	}
	
	public String getD_remark() {
		return d_remark;
	}
	
	public void setD_remark(String d_remark) {
		this.d_remark = d_remark;
	}
	
	public String getBss_sys_code() {
		return bss_sys_code;
	}
	
	public void setBss_sys_code(String bss_sys_code) {
		this.bss_sys_code = bss_sys_code;
	}
	
	public String getBss_sys_code2() {
		return bss_sys_code2;
	}
	
	public void setBss_sys_code2(String bss_sys_code2) {
		this.bss_sys_code2 = bss_sys_code2;
	}
	
	public String getFlag_jl() {
		return flag_jl;
	}
	
	public void setFlag_jl(String flag_jl) {
		this.flag_jl = flag_jl;
	}
	
	public String getValid_flag() {
		return valid_flag;
	}
	
	public void setValid_flag(String valid_flag) {
		this.valid_flag = valid_flag;
	}
	
	public String getLiquidation_pay_flag() {
		return liquidation_pay_flag;
	}
	
	public void setLiquidation_pay_flag(String liquidation_pay_flag) {
		this.liquidation_pay_flag = liquidation_pay_flag;
	}
	
	public String getLiquidation_start_date() {
		return liquidation_start_date;
	}
	
	public void setLiquidation_start_date(String liquidation_start_date) {
		this.liquidation_start_date = liquidation_start_date;
	}
	
	public String getOPER_TYPE() {
		return OPER_TYPE;
	}
	
	public void setOPER_TYPE(String oPER_TYPE) {
		OPER_TYPE = oPER_TYPE;
	}
	
	public String getJoint_flag() {
		return joint_flag;
	}
	
	public void setJoint_flag(String joint_flag) {
		this.joint_flag = joint_flag;
	}
	
	public String getBuild_area() {
		return build_area;
	}
	
	public void setBuild_area(String build_area) {
		this.build_area = build_area;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getJoint_soc_flag() {
		return joint_soc_flag;
	}
	
	public void setJoint_soc_flag(String joint_soc_flag) {
		this.joint_soc_flag = joint_soc_flag;
	}
	
	public String getAgent_business_form() {
		return agent_business_form;
	}
	
	public void setAgent_business_form(String agent_business_form) {
		this.agent_business_form = agent_business_form;
	}
	
	public String getTerminal_type() {
		return terminal_type;
	}
	
	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	
	public String getAuth_power_type() {
		return auth_power_type;
	}
	
	public void setAuth_power_type(String auth_power_type) {
		this.auth_power_type = auth_power_type;
	}
	
	public String getApple_id() {
		return apple_id;
	}
	
	public void setApple_id(String apple_id) {
		this.apple_id = apple_id;
	}
	
	public String getAuth_type() {
		return auth_type;
	}
	
	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}
	
}
