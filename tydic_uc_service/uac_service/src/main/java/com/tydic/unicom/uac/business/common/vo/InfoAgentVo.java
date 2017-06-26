/**
 * @ProjectName: uss_service
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author wanghao
 * @date: 2014年10月11日 下午11:19:59
 * @Title: InfoAgentVo.java
 * @Package com.tydic.unicom.crm.uss.vo.pub
 * @Description: TODO
 */
package com.tydic.unicom.uac.business.common.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.tydic.unicom.webUtil.BaseVo;

/**
 * <p>
 * </p>
 * @author wanghao 2014年10月11日 下午11:19:59
 * @ClassName InfoAgentVo
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2014年10月11日
 * @modify by reason:{方法名}:{原因}
 */
public class InfoAgentVo extends BaseVo {
	
	private static final long serialVersionUID = 1L;
	/** 私有字段-------------------------------------------------------------- */
	@XStreamAlias("AGENT_NO")
	private String agent_no;// 代理商编码
	@XStreamAlias("PARENT_AGENT_NO")
	private String parent_agent_no;// 父代理商编码
	@XStreamAlias("CHANNEL_TYPE")
	private String channel_type;// 渠道类型
	@XStreamAlias("SUB_CHANNEL_TYPE")
	private String sub_channel_type;// 渠道子类型
	@XStreamAlias("AGENT_TYPE")
	private String agent_type;// 代理商类型
	@XStreamAlias("OWNER")
	private String owner;// 组织者
	@XStreamAlias("AGENT_PASSWD")
	private String agent_passwd;// 密码
	@XStreamAlias("AGENT_NAME")
	private String agent_name;// 代理商名称
	@XStreamAlias("AGENT_ENG_NAME")
	private String agent_eng_name;// 代理商英文名称
	
	@XStreamAlias("DEPT_NO")
	private String dept_no;// 部门编码
	@XStreamAlias("STATISTICAL_DEPT_NO")
	private String statistical_dept_no;// 统计部门编码
	@XStreamAlias("AREA_ID")
	private String area_id;// 区域编码
	@XStreamAlias("CREATE_DATE")
	private String create_date;// 创建日期
	@XStreamAlias("EFF_DATE")
	private String eff_date;// 有效日期
	@XStreamAlias("DESTROY_DATE")
	private String destroy_date;// 销毁日期
	@XStreamAlias("EXP_DATE")
	private String exp_date;// 失效日期
	@XStreamAlias("AGENT_STATUS_CODE")
	private String agent_status_code;// 代理商状态编码
	@XStreamAlias("AGENT_AREA_TYPE")
	private String agent_area_type;// 代理商区域类型
	@XStreamAlias("AGENT_DEAL_TYPE")
	private String agent_deal_type;// 代理商受理类型
	@XStreamAlias("AGENT_LEVEL")
	private String agent_level;// 代理商等级
	
	@XStreamAlias("CORP_LICENSE_TYPE")
	private String corp_license_type;// 企业协议类型
	@XStreamAlias("CORP_LICENSE_NO")
	private String corp_license_no;// 企业协议编码
	@XStreamAlias("CORP_CREATE_DATE")
	private String corp_create_date;// 企业创建日期
	@XStreamAlias("CORP_SIZE")
	private String corp_size;// 企业范围
	@XStreamAlias("CORP_KIND")
	private String corp_kind;// 企业种类
	@XStreamAlias("CORP_REGISTER_FUND")
	private String corp_register_fund;// 企业注册资金
	@XStreamAlias("CORP_ADDRESS")
	private String corp_address;// 企业地址
	@XStreamAlias("CORP_POST")
	private String corp_post;// 企业邮编
	@XStreamAlias("CORP_WEB_ADDRESS")
	private String corp_web_address;// 企业网址
	@XStreamAlias("CACHET_NAME")
	private String cachet_name;// 企业简称
	
	@XStreamAlias("TAX_NUMBER")
	private String tax_number;// 税务号码
	@XStreamAlias("MAN_LICENSE_NUMBER")
	private String man_license_number;//
	@XStreamAlias("CORPER_NAME")
	private String corper_name;// 企业名称
	@XStreamAlias("CORPER_ID_TYPE")
	private String corper_id_type;// 企业证件类型
	@XStreamAlias("CORPER_ID")
	private String corper_id;// 企业证件号码
	@XStreamAlias("CORPER_PHONE")
	private String corper_phone;// 企业联系电话
	@XStreamAlias("PRINCIPAL_NAME")
	private String principal_name;// 负责人名称
	@XStreamAlias("PRINCIPAL_ID_TYPE")
	private String principal_id_type;// 负责人证件类型
	@XStreamAlias("PRINCIPAL_ID")
	private String principal_id;// 负责人证件号码
	@XStreamAlias("PRINCIPAL_PHONE")
	private String principal_phone;// 负责人电话
	
	@XStreamAlias("PRINCIPAL_ADDRESS")
	private String principal_address;// 负责人地址
	@XStreamAlias("PRINCIPAL_EMAIL")
	private String principal_email;// 负责人邮箱
	@XStreamAlias("LINK_NAME")
	private String link_name;// 联系人姓名
	@XStreamAlias("LINK_ID_TYPE")
	private String link_id_type;// 联系人证件类型
	@XStreamAlias("LINK_ID")
	private String link_id;// 联系人证件号码
	@XStreamAlias("LINK_PHONE")
	private String link_phone;// 联系人电话
	@XStreamAlias("LINK_ADDRESS")
	private String link_address;// 联系人地址
	@XStreamAlias("LINK_EMAIL")
	private String link_email;// 联系人邮箱
	@XStreamAlias("FAX_NUMBER")
	private String fax_number;// 传真号码
	@XStreamAlias("SALEPOINT_TYPE")
	private String salepoint_type;//
	
	@XStreamAlias("MEMO")
	private String memo;//
	@XStreamAlias("LOCAL_NET")
	private String local_net;// 地市编码
	@XStreamAlias("SUB_AREA_ID")
	private String sub_area_id;// 区域编码
	@XStreamAlias("AGENT_ENG_C_NAME")
	private String agent_eng_c_name;//
	@XStreamAlias("EXP_FLAG")
	private String exp_flag;//
	@XStreamAlias("COMPANY_NAME")
	private String company_name;//
	@XStreamAlias("AIRCHARGE_FLAG")
	private String aircharge_flag;//
	@XStreamAlias("DERATE_FLAG")
	private String derate_flag;//
	@XStreamAlias("FEE_DEPT_TYPE")
	private String fee_dept_type;//
	@XStreamAlias("VALID_FLAG")
	private String valid_flag;// 是否有效标志
	
	@XStreamAlias("CHANNEL_AREA_TYPE")
	private String channel_area_type;//
	@XStreamAlias("CHANNEL_LEVEL_TYPE")
	private String channel_level_type;//
	@XStreamAlias("CHANNEL_GROUP_TYPE")
	private String channel_group_type;//
	@XStreamAlias("CHNL_CODE")
	private String chnl_code;//
	@XStreamAlias("JIAO_FEI_ZHAN_FLAG")
	private String jiao_fei_zhan_flag;//
	@XStreamAlias("SERVICEAREA")
	private String servicearea;//
	@XStreamAlias("DEVICE_NUMBER")
	private String device_number;//
	@XStreamAlias("WEBACCESS")
	private String webaccess;//
	@XStreamAlias("CALLINGNUMBERANDTYPE1")
	private String callingnumberandtype1;//
	@XStreamAlias("CALLINGNUMBERANDTYPE2")
	private String callingnumberandtype2;//
	@XStreamAlias("CALLINGNUMBERANDTYPE3")
	private String callingnumberandtype3;//
	@XStreamAlias("CALLINGNUMBERANDTYPE4")
	private String callingnumberandtype4;//
	@XStreamAlias("CALLINGNUMBERANDTYPE5")
	private String callingnumberandtype5;//
	@XStreamAlias("MANAGER_AREA_CODE")
	private String manager_area_code;//
	@XStreamAlias("MANAGER_DEPT_CODE")
	private String manager_dept_code;//
	private String mobile_no;//代理商号码
	private String oper_no;//操作员编码
	
	/** get/set方法----------------------------------------------------------- */
	public String getLocal_net() {
		return local_net;
	}
	
	public void setLocal_net(String local_net) {
		this.local_net = local_net;
	}
	
	public String getAgent_no() {
		return agent_no;
	}
	
	public void setAgent_no(String agent_no) {
		this.agent_no = agent_no;
	}
	
	public String getDept_no() {
		return dept_no;
	}
	
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	
	public String getParent_agent_no() {
		return parent_agent_no;
	}
	
	public void setParent_agent_no(String parent_agent_no) {
		this.parent_agent_no = parent_agent_no;
	}
	
	public String getChannel_type() {
		return channel_type;
	}
	
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	
	public String getSub_channel_type() {
		return sub_channel_type;
	}
	
	public void setSub_channel_type(String sub_channel_type) {
		this.sub_channel_type = sub_channel_type;
	}
	
	public String getAgent_type() {
		return agent_type;
	}
	
	public void setAgent_type(String agent_type) {
		this.agent_type = agent_type;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getAgent_passwd() {
		return agent_passwd;
	}
	
	public void setAgent_passwd(String agent_passwd) {
		this.agent_passwd = agent_passwd;
	}
	
	public String getAgent_name() {
		return agent_name;
	}
	
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	
	public String getAgent_eng_name() {
		return agent_eng_name;
	}
	
	public void setAgent_eng_name(String agent_eng_name) {
		this.agent_eng_name = agent_eng_name;
	}
	
	public String getStatistical_dept_no() {
		return statistical_dept_no;
	}
	
	public void setStatistical_dept_no(String statistical_dept_no) {
		this.statistical_dept_no = statistical_dept_no;
	}
	
	public String getArea_id() {
		return area_id;
	}
	
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	public String getEff_date() {
		return eff_date;
	}
	
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	
	public String getDestroy_date() {
		return destroy_date;
	}
	
	public void setDestroy_date(String destroy_date) {
		this.destroy_date = destroy_date;
	}
	
	public String getExp_date() {
		return exp_date;
	}
	
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	
	public String getAgent_status_code() {
		return agent_status_code;
	}
	
	public void setAgent_status_code(String agent_status_code) {
		this.agent_status_code = agent_status_code;
	}
	
	public String getAgent_area_type() {
		return agent_area_type;
	}
	
	public void setAgent_area_type(String agent_area_type) {
		this.agent_area_type = agent_area_type;
	}
	
	public String getAgent_deal_type() {
		return agent_deal_type;
	}
	
	public void setAgent_deal_type(String agent_deal_type) {
		this.agent_deal_type = agent_deal_type;
	}
	
	public String getAgent_level() {
		return agent_level;
	}
	
	public void setAgent_level(String agent_level) {
		this.agent_level = agent_level;
	}
	
	public String getCorp_license_type() {
		return corp_license_type;
	}
	
	public void setCorp_license_type(String corp_license_type) {
		this.corp_license_type = corp_license_type;
	}
	
	public String getCorp_license_no() {
		return corp_license_no;
	}
	
	public void setCorp_license_no(String corp_license_no) {
		this.corp_license_no = corp_license_no;
	}
	
	public String getCorp_create_date() {
		return corp_create_date;
	}
	
	public void setCorp_create_date(String corp_create_date) {
		this.corp_create_date = corp_create_date;
	}
	
	public String getCorp_size() {
		return corp_size;
	}
	
	public void setCorp_size(String corp_size) {
		this.corp_size = corp_size;
	}
	
	public String getCorp_kind() {
		return corp_kind;
	}
	
	public void setCorp_kind(String corp_kind) {
		this.corp_kind = corp_kind;
	}
	
	public String getCorp_register_fund() {
		return corp_register_fund;
	}
	
	public void setCorp_register_fund(String corp_register_fund) {
		this.corp_register_fund = corp_register_fund;
	}
	
	public String getCorp_address() {
		return corp_address;
	}
	
	public void setCorp_address(String corp_address) {
		this.corp_address = corp_address;
	}
	
	public String getCorp_post() {
		return corp_post;
	}
	
	public void setCorp_post(String corp_post) {
		this.corp_post = corp_post;
	}
	
	public String getCorp_web_address() {
		return corp_web_address;
	}
	
	public void setCorp_web_address(String corp_web_address) {
		this.corp_web_address = corp_web_address;
	}
	
	public String getCachet_name() {
		return cachet_name;
	}
	
	public void setCachet_name(String cachet_name) {
		this.cachet_name = cachet_name;
	}
	
	public String getTax_number() {
		return tax_number;
	}
	
	public void setTax_number(String tax_number) {
		this.tax_number = tax_number;
	}
	
	public String getMan_license_number() {
		return man_license_number;
	}
	
	public void setMan_license_number(String man_license_number) {
		this.man_license_number = man_license_number;
	}
	
	public String getCorper_name() {
		return corper_name;
	}
	
	public void setCorper_name(String corper_name) {
		this.corper_name = corper_name;
	}
	
	public String getCorper_id_type() {
		return corper_id_type;
	}
	
	public void setCorper_id_type(String corper_id_type) {
		this.corper_id_type = corper_id_type;
	}
	
	public String getCorper_id() {
		return corper_id;
	}
	
	public void setCorper_id(String corper_id) {
		this.corper_id = corper_id;
	}
	
	public String getCorper_phone() {
		return corper_phone;
	}
	
	public void setCorper_phone(String corper_phone) {
		this.corper_phone = corper_phone;
	}
	
	public String getPrincipal_name() {
		return principal_name;
	}
	
	public void setPrincipal_name(String principal_name) {
		this.principal_name = principal_name;
	}
	
	public String getPrincipal_id_type() {
		return principal_id_type;
	}
	
	public void setPrincipal_id_type(String principal_id_type) {
		this.principal_id_type = principal_id_type;
	}
	
	public String getPrincipal_id() {
		return principal_id;
	}
	
	public void setPrincipal_id(String principal_id) {
		this.principal_id = principal_id;
	}
	
	public String getPrincipal_phone() {
		return principal_phone;
	}
	
	public void setPrincipal_phone(String principal_phone) {
		this.principal_phone = principal_phone;
	}
	
	public String getPrincipal_address() {
		return principal_address;
	}
	
	public void setPrincipal_address(String principal_address) {
		this.principal_address = principal_address;
	}
	
	public String getPrincipal_email() {
		return principal_email;
	}
	
	public void setPrincipal_email(String principal_email) {
		this.principal_email = principal_email;
	}
	
	public String getLink_name() {
		return link_name;
	}
	
	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}
	
	public String getLink_id_type() {
		return link_id_type;
	}
	
	public void setLink_id_type(String link_id_type) {
		this.link_id_type = link_id_type;
	}
	
	public String getLink_id() {
		return link_id;
	}
	
	public void setLink_id(String link_id) {
		this.link_id = link_id;
	}
	
	public String getLink_phone() {
		return link_phone;
	}
	
	public void setLink_phone(String link_phone) {
		this.link_phone = link_phone;
	}
	
	public String getLink_address() {
		return link_address;
	}
	
	public void setLink_address(String link_address) {
		this.link_address = link_address;
	}
	
	public String getLink_email() {
		return link_email;
	}
	
	public void setLink_email(String link_email) {
		this.link_email = link_email;
	}
	
	public String getFax_number() {
		return fax_number;
	}
	
	public void setFax_number(String fax_number) {
		this.fax_number = fax_number;
	}
	
	public String getSalepoint_type() {
		return salepoint_type;
	}
	
	public void setSalepoint_type(String salepoint_type) {
		this.salepoint_type = salepoint_type;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getSub_area_id() {
		return sub_area_id;
	}
	
	public void setSub_area_id(String sub_area_id) {
		this.sub_area_id = sub_area_id;
	}
	
	public String getAgent_eng_c_name() {
		return agent_eng_c_name;
	}
	
	public void setAgent_eng_c_name(String agent_eng_c_name) {
		this.agent_eng_c_name = agent_eng_c_name;
	}
	
	public String getExp_flag() {
		return exp_flag;
	}
	
	public void setExp_flag(String exp_flag) {
		this.exp_flag = exp_flag;
	}
	
	public String getCompany_name() {
		return company_name;
	}
	
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	public String getAircharge_flag() {
		return aircharge_flag;
	}
	
	public void setAircharge_flag(String aircharge_flag) {
		this.aircharge_flag = aircharge_flag;
	}
	
	public String getDerate_flag() {
		return derate_flag;
	}
	
	public void setDerate_flag(String derate_flag) {
		this.derate_flag = derate_flag;
	}
	
	public String getFee_dept_type() {
		return fee_dept_type;
	}
	
	public void setFee_dept_type(String fee_dept_type) {
		this.fee_dept_type = fee_dept_type;
	}
	
	public String getValid_flag() {
		return valid_flag;
	}
	
	public void setValid_flag(String valid_flag) {
		this.valid_flag = valid_flag;
	}
	
	public String getChannel_area_type() {
		return channel_area_type;
	}
	
	public void setChannel_area_type(String channel_area_type) {
		this.channel_area_type = channel_area_type;
	}
	
	public String getChannel_level_type() {
		return channel_level_type;
	}
	
	public void setChannel_level_type(String channel_level_type) {
		this.channel_level_type = channel_level_type;
	}
	
	public String getChannel_group_type() {
		return channel_group_type;
	}
	
	public void setChannel_group_type(String channel_group_type) {
		this.channel_group_type = channel_group_type;
	}
	
	public String getChnl_code() {
		return chnl_code;
	}
	
	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}
	
	public String getJiao_fei_zhan_flag() {
		return jiao_fei_zhan_flag;
	}
	
	public void setJiao_fei_zhan_flag(String jiao_fei_zhan_flag) {
		this.jiao_fei_zhan_flag = jiao_fei_zhan_flag;
	}
	
	public String getServicearea() {
		return servicearea;
	}
	
	public void setServicearea(String servicearea) {
		this.servicearea = servicearea;
	}
	
	public String getDevice_number() {
		return device_number;
	}
	
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	
	public String getWebaccess() {
		return webaccess;
	}
	
	public void setWebaccess(String webaccess) {
		this.webaccess = webaccess;
	}
	
	public String getCallingnumberandtype1() {
		return callingnumberandtype1;
	}
	
	public void setCallingnumberandtype1(String callingnumberandtype1) {
		this.callingnumberandtype1 = callingnumberandtype1;
	}
	
	public String getCallingnumberandtype2() {
		return callingnumberandtype2;
	}
	
	public void setCallingnumberandtype2(String callingnumberandtype2) {
		this.callingnumberandtype2 = callingnumberandtype2;
	}
	
	public String getCallingnumberandtype3() {
		return callingnumberandtype3;
	}
	
	public void setCallingnumberandtype3(String callingnumberandtype3) {
		this.callingnumberandtype3 = callingnumberandtype3;
	}
	
	public String getCallingnumberandtype4() {
		return callingnumberandtype4;
	}
	
	public void setCallingnumberandtype4(String callingnumberandtype4) {
		this.callingnumberandtype4 = callingnumberandtype4;
	}
	
	public String getCallingnumberandtype5() {
		return callingnumberandtype5;
	}
	
	public void setCallingnumberandtype5(String callingnumberandtype5) {
		this.callingnumberandtype5 = callingnumberandtype5;
	}
	
	public String getManager_area_code() {
		return manager_area_code;
	}
	
	public void setManager_area_code(String manager_area_code) {
		this.manager_area_code = manager_area_code;
	}
	
	public String getManager_dept_code() {
		return manager_dept_code;
	}
	
	public void setManager_dept_code(String manager_dept_code) {
		this.manager_dept_code = manager_dept_code;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getOper_no() {
		return oper_no;
	}

	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	
}
