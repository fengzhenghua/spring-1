package com.tydic.unicom.crawler.business.task.order5250;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.service.impl.AbilityPlatformServDuImpl;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoServ;

import net.sf.json.JSONObject;

/**
 * 订单管理>手动开户 回调商城
 * 未使用，，直接写在 WriteBackServiceBusImpl -> writeBackManualAccountMethod 里边的业务方法中
 * @author xx
 */
@Service
public class ManualAccout_CallBack {
	public static final Logger LOG = LoggerFactory.getLogger(ManualAccout_CallBack.class);
	@Autowired
	ProParamVo ppvo;
	@Autowired
	AbilityPlatformServDuImpl aps;
	@Autowired
	Crawler_InfoServ crawler_infoserv;
	
	
	// 流程状态
	String flow;

	public ManualAccout_CallBack() {
	}

	
	public void start() {
		// 开始转换数据
		// 1、查找表
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setOrder_status("42");
		try {
			List<Crawler_InfoPo> list = crawler_infoserv.query(cpp);
			if (list == null) {
				LOG.info("#####未找到完成写卡数据信息");
				return;
			}
			for (Crawler_InfoPo crainfo : list) {
				LOG.info(crainfo.getOrder_sourceinfo());
				try {
					JSONObject json = JSONObject.fromObject(crainfo.getOrder_sourceinfo());
					json = json.getJSONObject("cbinfo");
					json.put("certType",json.get("certNum").toString().length()==15?"01":"02");
					String tmplID = json.getString("tmplId");
			          if(tmplID.equals("60000005") || tmplID.equals("10000005") || tmplID.equals("10000006")){
			        	  json.put("goodsType", "LP");
			              // 裸终端、配件
			          }else if(tmplID.equals("60000019")||tmplID.equals("60000022")||tmplID.equals("60000023")||tmplID.equals("60000025")
			                		  ||tmplID.equals("60000026")||tmplID.equals("60000020")||tmplID.equals("60000028")||tmplID.equals("60000009")
			                		  ||tmplID.equals("60000004")){
			        	  //CBSS单号
			        	  json.put("goodsType", "4G");
			          }else if(tmplID.equals("60000014")){
			        	  //BSS单号
			        	  json.put("goodsType", "2G");
			          }else{
			        	  //ESS单号
			        	  json.put("goodsType", "3G");
			          }
					
					MallHttpRequest mall = new MallHttpRequest(SysPar.ppvo.getBaselink()+"/Odm/ManualAccount/submitOrderDetails");
					mall.setMethod("POST");
//					mall.setCookie(SysPar.getSessionCookie());
					mall.setHeader("Referer", SysPar.ppvo.getBaselink()+"/Odm/ManualAccount/toDetail");
					mall.setHeader("Origin","http://admin.mall.10010.com");
					mall.setHeader("X-Requested-With","XMLHttpRequest");
					
					
					mall.setPar("orderId", json.getString("orderId"));
					mall.setPar("tmplId", json.getString("tmplId"));
					mall.setPar("goodsInstId", json.getString("goodInstId"));
					mall.setPar("certType", json.getString("certType"));
					mall.setPar("certNum", json.getString("certNum"));
					
					mall.setPar("manualImeiCode", "");
					mall.setPar("manualNetCardNum", json.getString("manualNetCardNum"));
//					mall.setPar("manualUsimCardNum",crainfo.getUsim() );
//					mall.setPar("manualOrderNo", crainfo.getCbssnum());
					mall.setPar("goodsType", json.getString("goodsType"));
					MallHttpResponse mhrs = mall.response();
					
					if(mhrs.getCode() != 200){
						String f = new String(mhrs.getContent(),"UTF-8");
						LOG.debug("回写总部商城数据失败，请确认数据是否正确或是接口已经改变");
						
					}else{
						//修改数据库状态
						crainfo.setOrder_status("43");
						crawler_infoserv.update(crainfo);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 2 发送数据到能力平台
		// String json_info =
		// "{\"params\":{\"jsession_id\":\"CF0540\",\"order_type\":\"A101\",\"accept_no\":\"2017033105564450294237\",\"flow_flag\":\"100\",\"accept_type\":\"101\",\"accept_system\":\"001\",\"accept_time\":\"2017-04-01
		// 09:18:13\",\"province_code\":\"83\",\"area_code\":\"831\",\"pay_flag\":\"20\",\"pay_type\":\"11\",\"express_flag\":\"10\",\"auto_confirm\":\"1\",\"callback_url\":\"\",\"json_info\":{\"fee_info\":[],\"cod_charge\":\"\",\"serial_number\":\"13038351702\",\"recom_person_id\":\"8305162412\",\"recom_person_name\":\"秦禹\",\"first_mon_bill_mode\":\"01\",\"product_id\":\"P4192\",\"ser_type\":\"1\",\"cert_address\":\"黎村镇六振村旱塘队16号\",\"cert_expire_date\":\"\",\"customer_name\":\"冯洪常\",\"customer_sex\":\"M\",\"cert_num\":\"45092119900119283X\",\"cert_type\":\"02\",\"contact_address\":\"重庆
		// 重庆市
		// 大渡口区朝天门\",\"contact_phone\":\"18378821257\",\"oper_code\":\"openLocal4G\",\"receive_name\":\"冯洪常\",\"receive_phone\":\"18378821257\",\"receive_province\":\"50\",\"receive_city\":\"5000\",\"receive_area\":\"500104\",\"receive_address\":\"重庆
		// 重庆市
		// 爬虫测试\",\"total_fee\":\"0.02\",\"pay_type\":\"20\"}},\"SERVICE_NAME\":\"createSaleOrder\"}";
		// 根据返回回填数据到表中
	}
}
