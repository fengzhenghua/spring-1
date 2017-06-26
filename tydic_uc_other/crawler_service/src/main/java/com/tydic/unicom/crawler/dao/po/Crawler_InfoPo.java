package com.tydic.unicom.crawler.dao.po;

import org.springframework.stereotype.Component;

//import com.tydic.unicom.vdsBase.po.BasePo;
public class Crawler_InfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	
	public static final String CREATORDEROK = "410";
	public static final String CREATORDERERR = "420";
	
	/**
	 * 订单中心回调爬取平台成功-写卡
	 */
	/**订单中心写卡回调信息**/
	public static final String CREATORDERCALLBACKOK = "430";
	public static final String CREATORDERCALLBACKERR = "440";
	/**回写总部商城成功**/
	public static final String CREATORDERWRITEMALLCRADOK = "450";
	public static final String CREATORDERWRITEMALLCRADERR = "460";
	
	/**
	 * 订单中心回调爬取平台成功-物流
	 */
	public static final String LOGISTICSCALLBACKOK = "530";
	public static final String LOGISTICSCALLBACKERR = "540";
	
	public static final String LOGISTICSWRITEMALLOK = "550";
	public static final String LOGISTICSWRITEMALLERR = "560";
	
	public static final String CANCELAPPLYOK = "990";
	public static final String CANCELAPPLYERR = "991";		
	 
	/*
	 * 唯一ID,数据库自动生成
	 */
	private String ci_uuid;
	/**
	 * 订单ID ，爬虫数据爬取的商城
	 */
	private String order_id;
	/**
	 * 爬取后的元数据
	 */
	private String order_sourceinfo;
	/**
	 * 通过元数据转换成报文的数据信息
	 */
	private String order_info;
	/**
	 * 从总部商城的什么功能里边爬取的
	 * 1、订单审核
	 * 2、订单分配
	 * 3、订单领取
	 * 4、手动开户
	 * 5、自动开户
	 * 6、物流处理
	 * 7、归档操作
	 */
	private String order_malltype;
	/**
	 * 爬取的时间
	 */
	private String order_createtime;
	/**
	 * 流程状态</br>
	 * 0-10 ：代表爬虫的基本状态，暂时只用到 0 ，表示抓取完成，未处理</br>
	 * 编码规范 </br>
	 *  *? （0000）: *（多位）代表流程的编码，?（2位）代表流程的状态。</br>
	 * 状态规划：</br>
	 * 状态编码： 
	 * 		00：初始状态（代表在此流程中但是没有任何的处理，一般是其它流程转创建到本流程的时候使用）</br>
	 * 		01：成功
	 * 		02：失败
	 * 		03：中台回调成功
	 * 		04：中台回调失败
	 * 		05：回写商城成功
	 * 		06：回写商城失败 
	 * 
	 * *：对应order_malltype的值
	 */
	private String order_status;
	/**
	 * 商城对应的用户
	 */
	private String crawmu_uuid;
	/**
	 * 中台对应的用户
	 */
	private String jsessionid;
	/**
	 * 销售订单
	 */
	private String sale_order_no;
	/**
	 * 服务订单
	 */
	private String serv_order_no_list;
	

	private String product_id;
	private String product_idcn;
	private String serial_number;
	private String customer_name;
	private String cert_num;
	//物流单回填信息
	private String logistics_repcon;
	//发货信息
	private String shipments_repcon;
	private String cbssnum;
	private String usim;
	private String logistics_no;
	
	
	public String getCbssnum() {
		return cbssnum;
	}
	public void setCbssnum(String cbssnum) {
		this.cbssnum = cbssnum;
	}
	public String getUsim() {
		return usim;
	}
	public void setUsim(String usim) {
		this.usim = usim;
	}
	public String getShipments_repcon() {
		return shipments_repcon;
	}
	public void setShipments_repcon(String shipments_repcon) {
		this.shipments_repcon = shipments_repcon;
	}
	public String getLogistics_repcon() {
		return logistics_repcon;
	}
	public void setLogistics_repcon(String logistics_repcon) {
		this.logistics_repcon = logistics_repcon;
	}
	public String getLogistics_no() {
		return logistics_no;
	}
	public void setLogistics_no(String logistics_no) {
		this.logistics_no = logistics_no;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_idcn() {
		return product_idcn;
	}
	public void setProduct_idcn(String product_idcn) {
		this.product_idcn = product_idcn;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCert_num() {
		return cert_num;
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}
	public String getCi_uuid() {
		return ci_uuid;
	}
	public void setCi_uuid(String ci_uuid) {
		this.ci_uuid = ci_uuid;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_sourceinfo() {
		return order_sourceinfo;
	}
	public void setOrder_sourceinfo(String order_sourceinfo) {
		this.order_sourceinfo = order_sourceinfo;
	}
	public String getOrder_info() {
		return order_info;
	}
	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	public String getOrder_malltype() {
		return order_malltype;
	}
	public void setOrder_malltype(String order_malltype) {
		this.order_malltype = order_malltype;
	}
	public String getOrder_createtime() {
		return order_createtime;
	}
	public void setOrder_createtime(String order_createtime) {
		this.order_createtime = order_createtime;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getJsessionid() {
		return jsessionid;
	}
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}
	public String getServ_order_no_list() {
		return serv_order_no_list;
	}
	public void setServ_order_no_list(String serv_order_no_list) {
		this.serv_order_no_list = serv_order_no_list;
	}
	public String getCrawmu_uuid() {
		return crawmu_uuid;
	}
	public void setCrawmu_uuid(String crawmu_uuid) {
		this.crawmu_uuid = crawmu_uuid;
	}
	
	
}
