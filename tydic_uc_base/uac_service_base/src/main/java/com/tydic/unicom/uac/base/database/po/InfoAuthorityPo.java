/**
 * @ProjectName: uss_service_pub
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author haichao
 * @date: 2014年9月15日 下午4:30:42
 * @Title: InfoAuthority.java
 * @Package com.tydic.unicom.pub.po
 * @Description: TODO
 */
package com.tydic.unicom.uac.base.database.po;

import java.util.Date;

import com.tydic.uda.core.Sort;
import com.tydic.unicom.vdsBase.annotation.like;
import com.tydic.unicom.vdsBase.annotation.sort;
import com.tydic.unicom.vdsBase.po.BasePo;


/**
 * <p></p>
 * @author haichao 2014年9月15日 下午4:30:42
 * @ClassName InfoAuthority
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月15日
 * @modify by reason:{方法名}:{原因}
 */
public class InfoAuthorityPo extends BasePo {
	/**常量------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	/**类变量----------------------------------------------------------------*/

	/**实例变量--------------------------------------------------------------*/
	
	/**共有字段--------------------------------------------------------------*/
	
	/**受保护字段------------------------------------------------------------*/
	
	/**私有字段--------------------------------------------------------------*/
	private String authority_id;
	@like
	private String authority_name;
	@like
	private String authority_url;
	@like
	private String authority_comment;
	@sort(order=Sort.ASC)
	private String order_id;
	
	private String authority_index;
	private String style_image;
	
	private Date  create_date;
	private String  create_operator_id;
	/**构造方法--------------------------------------------------------------*/
	
	/**公共方法--------------------------------------------------------------*/
	public String getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}
	public String getAuthority_name() {
		return authority_name;
	}
	public void setAuthority_name(String authority_name) {
		this.authority_name = authority_name;
	}
	public String getAuthority_url() {
		return authority_url;
	}
	public void setAuthority_url(String authority_url) {
		this.authority_url = authority_url;
	}
	public String getAuthority_comment() {
		return authority_comment;
	}
	public void setAuthority_comment(String authority_comment) {
		this.authority_comment = authority_comment;
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getStyle_image() {
		return style_image;
	}
	public void setStyle_image(String style_image) {
		this.style_image = style_image;
	}
	public String getAuthority_index() {
		return authority_index;
	}
	public void setAuthority_index(String authority_index) {
		this.authority_index = authority_index;
	}
	/**受保护方法------------------------------------------------------------*/
	
	/**私有方法--------------------------------------------------------------*/
	
	/**重载Object方法--------------------------------------------------------*/
	
	/**get/set方法-----------------------------------------------------------*/
}
