/**
 * @ProjectName: uss_service
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author wanghao
 * @date: 2015年1月20日 下午5:09:45
 * @Title: InfoCommendOperVo.java
 * @Package com.tydic.unicom.crm.uss.vo.pub
 * @Description: TODO
 */
package com.tydic.unicom.uac.business.common.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.tydic.unicom.webUtil.BaseVo;

/**
 * <p>
 * </p>
 * @author wanghao 2015年1月20日 下午5:09:45
 * @ClassName InfoCommendOperVo
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2015年1月20日
 * @modify by reason:{方法名}:{原因}
 */
public class InfoCommendOperVo extends BaseVo {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	@XStreamAlias("COMMEND_NO")
	private String commend_no;// 推荐人号码
	@XStreamAlias("OPER_NO")
	private String oper_no;// 操作员工号
	@XStreamAlias("CREATE_DATE")
	private String create_date;// 创建日期
	@XStreamAlias("FLAG")
	private String flag;// 0--表示有效1--表示无效
	
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
	
	public InfoCommendOperVo() {
		super();
	}
	
	public InfoCommendOperVo(String commend_no, String oper_no, String create_date, String flag) {
		super();
		this.commend_no = commend_no;
		this.oper_no = oper_no;
		this.create_date = create_date;
		this.flag = flag;
	}
	
}
