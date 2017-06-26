package com.tydic.unicom.crawler.business.task;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;

/**
 * 连续的多个爬取业务基类，包含多个单个的 basetask接口业务
 * @author xx
 *
 */
public class OrderBaseTask implements IBaseTask{

//	CrwlerSerServDuImpl crwlerSerServDu;
	BusRespMessage brmsg = new BusRespMessage();
	String json_fd ;
	//openHn01
	
	@Override
	public void exe(CrawlDatum datum) {
//		checklist();
//		getDetails();
//		adddatabase();
		
	}
	
	/**
	 * 检查商品是否可以被爬取，在SysPar中查找，通过CrawlerInit初始化进来 
	 */
	public boolean checkCommodity(String str){
		
		if(str!= null && !str.isEmpty()){
			String f = SysUtil.getStr(SysPar.sukdef.get(str));
			if(f.isEmpty())
				return false;
			else
				return true;
		}
		return false;
	}
	
	public String parsechinatocode(String str){
		//
		return str;
	}
	
	
//	/**
//	 * 创建信息
//	 */
//	public Crawler_InfoPo createIteminfo(String id,String sstr,String pstr,String t){
//		Crawler_InfoPo cpp = new Crawler_InfoPo();
//		cpp.setOrder_id(id);
//		cpp.setOrder_sourceinfo(sstr);
//		cpp.setOrder_info(parsechinatocode(pstr));
//		cpp.setOrder_malltype(t);
//		return cpp;
//	}
	
	/** 业务转换使用 **/
	public String busparse(String str){
//		JSONObject json = JSONObject.fromObject(str);
//		String ss = SysUtil.jsonfindall(json.toString(), "params.json_info.serial_number");
//		System.out.println(ss.substring(0,ss.indexOf("(")));
//		json.toString().replaceAll(ss, ss.substring(0,ss.indexOf("(")));
//		System.out.println(json);
		return str;
	}
	
	public void initjson(){
//		System.out.println(SysPar.ppvo.getUoccallback());
		json_fd ="{"
				+ "\"SERVICE_NAME\":\"createSaleOrder\","
				+ "\"params\":{"
				+ "\"jsession_id\":\"CF0540\","
				+ "\"order_type\":\"A101\","
				+ "\"accept_no\":\"#ordernum\","
				+ "\"flow_flag\":\"100\","
				+ "\"accept_type\":\"101\","
				+ "\"accept_system\":\"CRAWLER\","
				+ "\"accept_time\":\"`DATE32\","
				+ "\"province_code\":\"83\","
				+ "\"area_code\":\"831\","
				+ "\"pay_flag\":\"11\","
				+ "\"express_flag\":\"10\","
				+ "\"auto_confirm\":\"1\","
				+ "\"callback_url\":\""+SysPar.ppvo.getUoccallback()+"\","
				+ "\"json_info\":{"
				+ "\"recom_person_name\":\"ceshi\","
				+ "\"recom_person_id\":\"ceshi0001\","
				+ "\"serial_number\":\"#R{(\\\\d{11})}#,context.1.号码\","
				+ "\"first_mon_bill_mode\":\"01\","
				+ "\"product_id\":\"#context.1.套餐\","
//				+ "\"product_id\":\"P4192\","
				+ "\"ser_type\":\"2\","
				+ "\"cert_address\":\"#context.2.地址\","
				+ "\"cert_expire_date\":\"2050-12-31\","
				+ "\"cert_num\":\"#R{(.*)\\\\(}#,context.2.证件\","
				+ "\"cert_type\":\"#R{\\\\((.*)\\\\)}#,context.2.证件\","
				
				+ "\"contact_address\":\"#other.收货地址\","
				+ "\"customer_name\":\"#context.2.姓名\","
				+ "\"contact_phone\":\"#R{(\\\\d{11})}#,电话\","
				+ "\"oper_code\":\"\","
				+ "\"customer_sex\":\"#R{(.*)\\\\(}#,context.2.证件\","
				
				+ "\"receive_name\":\"#context.2.姓名\","
				+ "\"receive_phone\":\"#R{(\\\\d{11})}#,电话\","
				
				+ "\"receive_province\":\"#J{var t=$0;f=t.split('，')[0]}#,other.收货地址\","
				+ "\"receive_city\":\"#J{var t=$0;f=t.split('，')[1]}#,other.收货地址\","
				+ "\"receive_area\":\"#J{var t=$0;f=t.split('，')[2]}#,other.收货地址\","
				+ "\"receive_address\":\"#other.收货地址\","
				+ "\"total_fee\":\"0\","
				+ "\"fee_info\":\"[]\","
				+ "\"pay_type\":\"10\""
				+ "}"
				+ "}"
				+ "}";
	}

	public BusRespMessage getBrmsg() {
		return brmsg;
	}

	public void setBrmsg(BusRespMessage brmsg) {
		this.brmsg = brmsg;
	}

}
