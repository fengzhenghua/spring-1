/**
 * @ProjectName: uss_service
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author wanghao
 * @date: 2014年10月11日 下午11:23:03
 * @Title: ZBInfoAgentRelationVo.java
 * @Package com.tydic.unicom.crm.uss.vo.pub
 * @Description: TODO
 */
package com.tydic.unicom.uac.business.common.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.tydic.unicom.webUtil.BaseVo;

/**
 * <p>
 * </p>
 * @author wanghao 2014年10月11日 下午11:23:03
 * @ClassName ZBInfoAgentRelationVo
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2014年10月11日
 * @modify by reason:{方法名}:{原因}
 */
public class ZBInfoAgentRelationVo extends BaseVo {
	
	/** 类变量---------------------------------------------------------------- */
	private static final long serialVersionUID = 1L;
	
	/** 私有字段-------------------------------------------------------------- */
	@XStreamAlias("AGENT_NO")
	private String agent_no; // 本地编码
	@XStreamAlias("CHNL_CODE")
	private String chnl_code;// 总部渠道编码
	@XStreamAlias("DEV_CODE")
	private String dev_code;// 总部发展工号
	@XStreamAlias("CHNL_NAME")
	private String chnl_name;// 总部渠道名称
	@XStreamAlias("MANAGE_DEPT_NO")
	private String manage_dept_no;// 总部部门
	@XStreamAlias("BELONG_TYPE")
	private String belong_type;
	
	@XStreamAlias("FLAG_JL")
	private String flag_jl;
	@XStreamAlias("ZB_DEV_CODE")
	private String zb_dev_code;
	@XStreamAlias("MAIN_FLAG")
	private String main_flag;// 1 默认对应（1对1上报总部，允许本地进行业务受理） 2 主对应 （1对多，但是一个本地对应的发展人必须是唯一，不允许业务受理，用于上报业务量之类） 0 非主对应 （1对多不允许业务受理，用于历史佣金上报）
	
	/** get/set方法----------------------------------------------------------- */
	
	public String getChnl_code() {
		return chnl_code;
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
	
	public String getDev_code() {
		return dev_code;
	}
	
	public void setDev_code(String dev_code) {
		this.dev_code = dev_code;
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
