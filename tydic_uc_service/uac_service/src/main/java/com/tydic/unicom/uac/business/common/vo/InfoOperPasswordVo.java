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
import com.tydic.unicom.webUtil.BaseVo;

/**
 * 
 * <p>
 * </p>
 * @author yangyi 2015年5月18日 
 * @ClassName InfoOperPassword
 * @Description TODO 子工号在不同系统登录对应的密码
 * @version V1.0
 */
public class InfoOperPasswordVo extends BaseVo {
	
	private static final long serialVersionUID = 1L;
	/** 私有字段-------------------------------------------------------------- */
	private String oper_id;
	private String oper_pw;
	private String system;
	private String province_code ;
	
	
	/** get/set方法----------------------------------------------------------- */
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getOper_pw() {
		return oper_pw;
	}
	public void setOper_pw(String oper_pw) {
		this.oper_pw = oper_pw;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	
	
	/** 构造方法-------------------------------------------------------------- */
	
	/** 公共方法-------------------------------------------------------------- */
	
	/** 受保护方法------------------------------------------------------------ */
	
	/** 私有方法-------------------------------------------------------------- */
	
	/** 重载Object方法-------------------------------------------------------- */
	
}
