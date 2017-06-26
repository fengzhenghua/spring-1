package com.tydic.unicom.crawler.business.task;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.business.task.order5180.OrderConfirm;
import com.tydic.unicom.crawler.business.task.order5180.OrderConfirm_ToDetail;
import com.tydic.unicom.crawler.business.task.order5250.ManualAccount;
import com.tydic.unicom.crawler.business.task.order5250.ManualAccount_ToDetail;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoServ;
import com.tydic.unicom.crawlerframe.exception.NoLogonException;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;

import net.sf.json.JSONObject;

/**
 * +++++++++++++++++++++++++++++手动开户++++++++++++++++++++
 *  详细信息 order_malltype : 
 * 	1：订单领取 OrderClaimTask 
 * 	2：外呼确认 OrderConfirm 
 * 	3：手动开户
 * @author xx
 */
@Service()
public class OrderManualAccount extends OrderBaseTask {
	public static final Logger logger = LoggerFactory.getLogger(OrderManualAccount.class);
	
	@Autowired
	ManualAccount manualaccount ;
	
	@Autowired
	ManualAccount_ToDetail manualaccount_todetail;
	ArrayList orderidlist = new ArrayList();
	
	@Autowired
	Crawler_InfoServ crawler_infoserv;

//	String json_fd = "{" + "\"oper_no\":\"CF0540\"," + "\"order_id\":\"000001\"," + "\"pay_type\":\"20\","
//			+ "\"serial_number\":\"#context.1.号码\"," + "\"recom_person_id\":\"101\"," + "\"recom_person_name\":\"101\","
//			+ "\"first_mon_bill_mode\":\"01\","
//			+ "\"product_id\":\"#context.1.套餐\","
//			+ "\"cert_address\":\"#context.2.地址\"," + "\"cert_expire_date\":\"2059-12-30\","
//			+ "\"customer_name\":\"#context.2.姓名\"," + "\"customer_sex\":\"1\"," + "\"cert_num\":\"#context.2.证件\","
//			+ "\"cert_type\":\"#context.2.证件\","
//			+ "\"contact_address\":\"#other.收货地址\"," + "\"contact_phone\":\"#电话\","
//			+ "\"receive_name\":\"#context.2.姓名\"," + "\"receive_phone\":\"#电话\","
//			+ "\"receive_province\":\"#other.收货地址\"," + "\"receive_city\":\"#other.收货地址\","
//			+ "\"receive_area\":\"#other.收货地址\"," + "\"receive_address\":\"#other.收货地址\","
//			+ "\"total_fee\":\"#费用.共需支付\"," + "\"oper_code\":\"openHn01\"," + "\"cod_charge\":\"#费用.共需支付\"," + "}";


	
	@Override
	public void exe(CrawlDatum datum) {
		initjson();
		try {
			boolean isend = false;
			// 开始得到详细连接
			int crwalcon = 0;
			int crwalOK = 0;
			int crawlNO = 0;
			int crawlrepeat = 0;
			// 通过首页，得到列表中所有订单的 orderid ，注意orderid和orderNUM 不一样
			while (!isend) {
				manualaccount.execute(datum);
				// 得到返回结果
				ArrayList tmpseares = manualaccount.getsearchResultlist();
				if (tmpseares == null || tmpseares.isEmpty()) {
					break;
				}
				manualaccount.activepage = manualaccount.activepage + 1;
				if (!checkrepetition(tmpseares)){
					orderidlist.addAll(tmpseares);
				} 
				else {
					break;
				}
			}
			logger.debug("====  得到列表数："+orderidlist.size());
			
			if(orderidlist.size() == 0){
				brmsg.setRespCode(BusRespMessage.SUCCESS);
				brmsg.setContent("爬取成功，爬取数据：0条");
				brmsg.addArg("crwalsize", 0);
				brmsg.addArg("crwalOK", 0);
				brmsg.addArg("crawlNO", 0);
				return;
			}
			
			crwalcon = orderidlist.size();
			for (int i = 0; i < orderidlist.size(); i++) {
				logger.debug("#####得到订单ID : "+orderidlist.get(i));
				manualaccount_todetail.orderid = (String) orderidlist.get(i);
				if (manualaccount_todetail.orderid == null || manualaccount_todetail.orderid.isEmpty())
					continue;
				datum.url("");
				manualaccount_todetail.execute(datum);
				String ret = manualaccount_todetail.getResult();
				//通过模板，把返回的ret映射为模板的内容
				String parsestr = SysUtil.templatetransformation(ret, json_fd);
				//单独的业务 转换使用，通过转换不能瞒住的情况使用 重写busparse方法，再次转换
				parsestr = busparse(parsestr);
				// 后边在封装，这里先每个任务都自己写
				if (checkCommodity(SysUtil.jsonfindall(parsestr, "params.json_info.product_id"))) {
					//如果得到套餐代码就更换
					JSONObject json = JSONObject.fromObject(parsestr);
					String keyname = SysUtil.jsonfindall(json.toString(), "params.json_info.product_id");
					String proid  = SysPar.sukdef.get(keyname).getSku_id();
					json = SysUtil.jsonreplace(json.toString(), "params.json_info.product_id",proid);
					String opercode = SysPar.sukdef.get(keyname).getOper_code();
					json = SysUtil.jsonreplace(json.toString(), "params.json_info.oper_code",opercode);
					
					parsestr = json.toString();
					
					//Crawler_InfoPo cpp = createIteminfo(manualaccount_todetail.orderid,ret,parsechinatocode(parsestr),"4");
					
					Crawler_InfoPo cpp = new Crawler_InfoPo();
					cpp.setOrder_id(manualaccount_todetail.orderid);
					cpp.setOrder_sourceinfo(ret);
					cpp.setOrder_info(parsechinatocode(parsestr));
					cpp.setOrder_malltype("4");
					cpp.setCrawmu_uuid(datum.meta("crawmu_uuid"));
					
					cpp.setProduct_id(SysUtil.jsonfindall(parsestr, "params.json_info.product_id"));
					cpp.setSerial_number(SysUtil.jsonfindall(parsestr, "params.json_info.serial_number"));
					cpp.setCustomer_name(SysUtil.jsonfindall(parsestr, "params.json_info.customer_name"));
					cpp.setCert_num(SysUtil.jsonfindall(parsestr, "params.json_info.cert_num"));
					
					logger.debug("====得到ORDER_ID " + manualaccount_todetail.orderid);
					logger.debug("====得到ORDER_PARESSTR " + parsestr);
					try {
						if(crawler_infoserv.create(cpp))
							crwalOK++;
						else
							crawlrepeat++;
						logger.info("====爬取成功！");
					} catch (Exception e) {
						// 没有执行插入操作
						e.printStackTrace();
						crawlNO++;
						logger.info("====爬取外呼信息成功，写入数据库失败");
						continue;
					}
				}else{
					crawlNO++;
					logger.info("====未找到对应的套餐代码");
				}
			}
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			//brmsg.setContent("爬取数据完成-----找到数据:"+crwalcon + ",成功爬取："+crwalOK+",未爬取："+crawlNO);
			brmsg.setContent("爬取数据完成");
			brmsg.addArg("crwalsize", crwalcon-crawlrepeat);
			brmsg.addArg("crwalOK", crwalOK);
			brmsg.addArg("crawlNO", crawlNO);
			brmsg.addArg("crawlrepeat", crawlrepeat);
		} 
		catch (NoLogonException e) {
			logger.debug("登陆失效，请重新登录！");
			e.printStackTrace();
			brmsg.setRespCode("9000");
			brmsg.setContent("登陆失效，请重新登录！");
		}
		catch (Exception e) {
			e.printStackTrace();
			brmsg.setRespCode("8999");
			brmsg.setContent("爬取网站人工审核数据错误，请确认网站是否已经更改！");
		}
		// HashMap HashMap = orderclaim.getsearchResultlist();
	}

	/**
	 * 检查最后一个数据是否重复，如果重复就返回 TRUE
	 * 
	 * @param tmpseares
	 * @return
	 */
	private boolean checkrepetition(ArrayList tmpseares) {
		if (orderidlist.size() > 0) {
			if (tmpseares.size() > 0) {
				if (orderidlist.get(orderidlist.size() - 1).equals(tmpseares.get(tmpseares.size() - 1))) {
					return true;
				}
			}
		}
		return false;
	}

	
	@Override
	public String busparse(String str) {
		JSONObject json = JSONObject.fromObject(str);
		String ss = SysUtil.jsonfindall(json.toString(), "params.json_info.cert_type");
		ss  = SysPar.getCert_type(ss);
		json = SysUtil.jsonreplace(json.toString(), "params.json_info.cert_type",ss);	
		
		String receive_province = SysUtil.jsonfindall(json.toString(), "params.json_info.receive_province");
		receive_province  = SysUtil.getStr(SysPar.coderegion.get(receive_province));
		json = SysUtil.jsonreplace(json.toString(), "params.json_info.receive_province",receive_province);

		String receive_city = SysUtil.jsonfindall(json.toString(), "params.json_info.receive_city");
		receive_city  = SysUtil.getStr(SysPar.coderegion.get(receive_city));
		json = SysUtil.jsonreplace(json.toString(), "params.json_info.receive_city",receive_city);

		String receive_area = SysUtil.jsonfindall(json.toString(), "params.json_info.receive_area");
		receive_area  = SysUtil.getStr(SysPar.coderegion.get(receive_area));
		json = SysUtil.jsonreplace(json.toString(), "params.json_info.receive_area",receive_area);
		
		String customer_sex = SysUtil.jsonfindall(json.toString(), "params.json_info.customer_sex");
		customer_sex  = SysUtil.getStr(SysPar.getCustomer_sex(customer_sex));
		
		json = SysUtil.jsonreplace(json.toString(), "params.json_info.customer_sex",customer_sex);
		
		//json = SysUtil.jsonreplace(json.toString(), "params.json_info.oper_code",customer_sex);
		
		
		return json.toString();
	}
	
	public static void main(String[] args) {
		OrderBaseTask o = new OrderBaseTask();
		String f = "{\"SERVICE_NAME\":\"createSaleOrder\",\"params\":{\"jsession_id\":\"SF0540\",\"order_type\":\"A101\",\"accept_no\":\"7815822049\",\"flow_flag\":\"101\",\"accept_type\":\"101\",\"accept_system\":\"001\",\"accept_time\":\"2017-04-01 03:28:57\",\"province_code\":\"83\",\"pay_flag\":\"11\",\"express_flag\":\"10\",\"auto_confirm\":\"1\",\"json_info\":{\"serial_number\":\"18523195774(重庆)\",\"first_mon_bill_mode\":\"01\",\"product_id\":\"全国版\",\"ser_type\":\"2\",\"cert_address\":\"重庆市巴南区接龙镇桥边村黄家屋基组37号\",\"cert_expire_date\":\"2050-12-31\",\"cert_num\":\"500113199403016725(18位身份证)\",\"cert_type\":\"500113199403016725(18位身份证)\",\"contact_address\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"customer_name\":\"胡冰洁\",\"contact_phone\":\"18623457767\",\"oper_code\":\"openLocal4G\",\"customer_sex\":\"1\",\"receive_name\":\"胡冰洁\",\"receive_phone\":\"18623457767\",\"receive_province\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"receive_city\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"receive_area\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"receive_address\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"total_fee\":\"openHn01\",\"pay_type\":\"10\"}}}";
		String f1 =o.busparse(f);
		System.out.println(f1);
		
	}
}
