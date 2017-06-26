/**
 * @ProjectName: uss_service_pub
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author haichao
 * @date: 2014年9月15日 下午4:37:13
 * @Title: InfoMenu.java
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
 * @author haichao 2014年9月15日 下午4:37:13
 * @ClassName InfoMenu
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月15日
 * @modify by reason:{方法名}:{原因}
 */
public class InfoMenuPo extends BasePo {
	/**常量------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	/**类变量----------------------------------------------------------------*/
	
	/**实例变量--------------------------------------------------------------*/
	
	/**共有字段--------------------------------------------------------------*/
	
	/**受保护字段------------------------------------------------------------*/
	
	/**私有字段--------------------------------------------------------------*/
	private String menu_id;
	private String authority_id;
	
	@like
	private String menu_name;
	@like
	private String menu_url;
	@like
	private String menu_comment;
	@sort(order = Sort.DESC)
	private Date  create_date;
	private String  create_operator_id;
	
	private String  up_menu_id;
	
	private Integer sort_mark;//排序标示
	
	private String flag;//生效标示  0生效  1 实效
	
	private String menu_ctrl;//(菜单显示控制) 0：全显示；1：营业座席显示；2:共享座席显示
	/**构造方法--------------------------------------------------------------*/
	
	/**公共方法--------------------------------------------------------------*/

	public String getMenu_ctrl() {
		return menu_ctrl;
	}
	public void setMenu_ctrl(String menu_ctrl) {
		this.menu_ctrl = menu_ctrl;
	}
	
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getMenu_comment() {
		return menu_comment;
	}
	public void setMenu_comment(String menu_comment) {
		this.menu_comment = menu_comment;
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
	public String getUp_menu_id() {
		return up_menu_id;
	}
	public void setUp_menu_id(String up_menu_id) {
		this.up_menu_id = up_menu_id;
	}
	
    public Integer getSort_mark() {
    	return sort_mark;
    }
	
    public void setSort_mark(Integer sort_mark) {
    	this.sort_mark = sort_mark;
    }
	
    public String getFlag() {
    	return flag;
    }
	
    public void setFlag(String flag) {
    	this.flag = flag;
    }
	
}
