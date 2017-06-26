package com.tydic.unicom.crawler.business.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crawler.common.BaseVoPo;
import com.tydic.unicom.crawler.common.BustoMallVo;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawler.service.interfaces.Crawler_Mall_UserServ;

import net.sf.json.JSONObject;

/**
 * 业务内基类，通用业务处理全在这里
 * 
 * @author xx
 */
public class BusBase {
	private static Logger logger = Logger.getLogger(BusBase.class);

	@Autowired
	Crawler_Mall_UserServ crawlermalluserservimpl;
	
	interface Execinterface<T> {
		public Object begin(T a1);

		public Object v(T a2);

		public Object end(T a3);
	}

	abstract class Exec<T> implements Execinterface<T> {

		@Override
		public Object begin(T a1) {
			return null;
		}

		@Override
		public Object v(T a2) {
			return null;
		}

		@Override
		public Object end(T a3) {
			return null;
		}

	}

	public Object exec(Exec e, Object o) {
		Object f = e.begin(o);
		if (f == null)
			return f;
		e.v(o);
		e.end(o);
		return null;
	}

	/**
	 * 开始日志，打印继承BaseVoPo的bean的内容
	 * 
	 * @param log
	 * @param funname
	 * @param bvp
	 */
	public void beginlogger(Logger log, String funname, BaseVoPo bvp) {
		loginfo(log, this.getClass().getName() + "->[" + funname + "] 开始参数:" + bvp.convertToMap().toString());
	}

	public void loginfo(Logger log, String con) {
		log.info(con);
	}

	public void endloginfo(Logger log, String string) {
		loginfo(log, string);
	}

	public void logdebug(Logger log, String con){
		log.debug(con);
	}
	
	
	
	protected BusRespMessage checkinput(BusinessResParamVo businessResParamVo) {
		BusRespMessage brmsg = new BusRespMessage();
		if (businessResParamVo==null){
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("传入参数错误");
			return brmsg;
		}
			
		if (businessResParamVo.getChecklist() != null && !businessResParamVo.getChecklist().isEmpty()) {
			String[] namearray = businessResParamVo.getChecklist().split(",");
			for (int i = 0; i < namearray.length; i++) {
				String cname = namearray[i];
				switch (cname) {
				case "user":
					if (businessResParamVo.getUser() == null || businessResParamVo.getUser().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数user不能为空！");
						return brmsg;
					}
					break;
				case "password":
					if (businessResParamVo.getPassword() == null || businessResParamVo.getPassword().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数password不能为空！");
						return brmsg;
					}
					break;
				case "type":
					if (businessResParamVo.getType() == null || businessResParamVo.getType().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数type不能为空！");
						return brmsg;
					}
					break;
				case "jsession_id":
					if (businessResParamVo.getJsession_id() == null || businessResParamVo.getJsession_id().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数jsession_id不能为空！");
						return brmsg;
					}
					break;
				case "safecode":
					if (businessResParamVo.getSafecode() == null || businessResParamVo.getSafecode().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数safecode不能为空！");
						return brmsg;
					}
					break;
					
				case "jsonInfo":
					if (businessResParamVo.getJsonInfo() == null || businessResParamVo.getJsonInfo().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数jsonInfo不能为空！");
						return brmsg;
					}
					break;
				case "userid":
					if (businessResParamVo.getUserid() == null || businessResParamVo.getUserid().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数userid不能为空！");
						return brmsg;
					}
					break;
				case "order_id":
					if (businessResParamVo.getOrder_id() == null || businessResParamVo.getOrder_id().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数order_id不能为空！");
						return brmsg;
					}
					break;
				case "other":
					if (businessResParamVo.getOther() == null || businessResParamVo.getOther().isEmpty()) {
						brmsg.setRespCode(BusRespMessage.ERR);
						brmsg.setContent("传入参数不能为空！");
						return brmsg;
					}
					break;
				default:
					logger.debug("未找到检查字段:"+ cname);
					break;
				}
			}
		}

		return null;
	}

	
	/**
	 * 检查用户
	 * @param businessResParamVo
	 * @return
	 * @throws Exception
	 */
	public Crawler_Mall_UserPo checkuser(BusinessResParamVo businessResParamVo) throws Exception{
		beginlogger(logger,"checkuser", businessResParamVo);
		BustoMallVo bmv = new BustoMallVo();
		bmv.setCrawmu_uuid(businessResParamVo.getUserid());
		bmv.setCrawmu_name(businessResParamVo.getUser());
//		bmv.setCrawmu_pwd(businessResParamVo.getPassword());
//		bmv.setSafecode(businessResParamVo.getSafecode());
		Crawler_Mall_UserPo cmu = crawlermalluserservimpl.get(bmv);
		if(cmu == null ){
			return null;
		}
		endloginfo(logger,this.getClass().getName()+"->[checkuser] 结束参数:"+cmu.convertToMap().toString());
		return cmu;
	}
	
	public JSONObject createCrawlerInfo_JSON(List<Crawler_InfoPo> plist){
		JSONObject listjson =JSONObject.fromObject("{}");
		JSONArray arrayjson = new JSONArray();
		
		for (Crawler_InfoPo crainfo : plist) {
			logger.debug("装备数据，查询订单： "+crainfo.convertToMap());
			JSONObject listitem = new JSONObject();
			JSONObject sourceinfo = JSONObject.fromObject(crainfo.getOrder_sourceinfo());
			JSONObject orderinfo = JSONObject.fromObject(crainfo.getOrder_info());
			listitem.put("ciuuid", crainfo.getCi_uuid());
			listitem.put("orderid", crainfo.getOrder_id());
			listitem.put("product", SysUtil.jsonfindall(sourceinfo.toString(), "context.1.套餐"));
			listitem.put("serial_number", SysUtil.jsonfindall(orderinfo.toString(), "params.json_info.serial_number"));
			listitem.put("customer_name", SysUtil.jsonfindall(orderinfo.toString(), "params.json_info.customer_name"));
			listitem.put("stateimgs", "order.png");
			listitem.put("saleorderno", crainfo.getSale_order_no());
			listitem.put("servorderno", crainfo.getServ_order_no_list());
			listitem.put("statecon", statustocnstatus(crainfo.getOrder_status()));
			listitem.put("logistics_no", (crainfo.getLogistics_no()));
			listitem.put("cbssnum", (crainfo.getCbssnum()));
			listitem.put("usim", crainfo.getUsim());
			arrayjson.put(listitem);
		}
		listjson.element("data",arrayjson.toString());
		return listjson;
	}
	
	private String statustocnstatus(String status){
		if("0".equals(status)){
			return "创单";
		}
		if("410".equals(status)){
			return "写卡";
		}
		if("430".equals(status)){
			return "同步写卡";
		}
		if("450".equals(status)){
			return "物流";
		}
		if("530".equals(status)){
			return "同步物流";
		}
		if("550".equals(status)){
			return "完成";
		}
		if("990".equals(status)){
			return "退单";
		}else{
			return "其它【"+status+"】";
		}
	}
	
	
	
	
	
	
}
