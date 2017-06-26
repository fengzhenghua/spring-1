/**
 * @ProjectName: uss_service_pub
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author haichao
 * @date: 2014年9月15日 下午4:40:52
 * @Title: RuleOperRole.java
 * @Package com.tydic.unicom.pub.po
 * @Description: TODO
 */
package com.tydic.unicom.uac.base.database.po;

import java.util.Date;

import com.tydic.uda.core.Sort;
import com.tydic.unicom.vdsBase.annotation.sort;
import com.tydic.unicom.vdsBase.po.BasePo;


/**
 * <p></p>
 * @author haichao 2014年9月15日 下午4:40:52
 * @ClassName RuleOperRole
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月15日
 * @modify by reason:{方法名}:{原因}
 */
public class RuleOperRolePo extends BasePo {
	/**常量------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	/**类变量----------------------------------------------------------------*/

	/**实例变量--------------------------------------------------------------*/

	/**共有字段--------------------------------------------------------------*/

	/**受保护字段------------------------------------------------------------*/

	/**私有字段--------------------------------------------------------------*/
	private String oper_role_id;
	private String oper_id;
	private String oper_name;
	private String role_id;
	@sort(order = Sort.DESC)
	private Date create_date;
	private String create_operator_id;
	private String role_name;
	/**构造方法--------------------------------------------------------------*/

	/**公共方法--------------------------------------------------------------*/
	public String getOper_role_id() {
		return oper_role_id;
	}
	public void setOper_role_id(String oper_role_id) {
		this.oper_role_id = oper_role_id;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getCreate_operator_id() {
		return create_operator_id;
	}
	public void setCreate_operator_id(String create_operator_id) {
		this.create_operator_id = create_operator_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	/**受保护方法------------------------------------------------------------*/

	/**私有方法--------------------------------------------------------------*/

	/**重载Object方法--------------------------------------------------------*/

	/**get/set方法-----------------------------------------------------------*/
}
