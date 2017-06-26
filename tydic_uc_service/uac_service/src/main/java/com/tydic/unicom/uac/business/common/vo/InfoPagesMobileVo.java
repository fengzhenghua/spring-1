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
 * @ClassName InfoPagesMobile
 * @Description TODO 
 * @version V1.0
 */
public class InfoPagesMobileVo extends BaseVo {
	
	private static final long serialVersionUID = 1L;
	/** 私有字段-------------------------------------------------------------- */
	private String element_id;
	private String authority_id;
	private String element_name;	
	private String element_type;
	private String page_id;
	private String menu_comment;
	private String create_date;
	private String create_operator_id;
	private String flag;

	
	/** get/set方法----------------------------------------------------------- */
	public String getElement_id() {
		return element_id;
	}
	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}
	public String getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}
	public String getElement_name() {
		return element_name;
	}
	public void setElement_name(String element_name) {
		this.element_name = element_name;
	}
	public String getElement_type() {
		return element_type;
	}
	public void setElement_type(String element_type) {
		this.element_type = element_type;
	}
	public String getPage_id() {
		return page_id;
	}
	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}
	public String getMenu_comment() {
		return menu_comment;
	}
	public void setMenu_comment(String menu_comment) {
		this.menu_comment = menu_comment;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_operator_id() {
		return create_operator_id;
	}
	public void setCreate_operator_id(String create_operator_id) {
		this.create_operator_id = create_operator_id;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	/** 构造方法-------------------------------------------------------------- */
	
	/** 公共方法-------------------------------------------------------------- */
	
	/** 受保护方法------------------------------------------------------------ */
	
	/** 私有方法-------------------------------------------------------------- */
	
	/** 重载Object方法-------------------------------------------------------- */
	
}
