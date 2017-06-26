package com.tydic.unicom.crawler.business.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.tydic.unicom.crawler.business.interfaces.WriteBackServiceBus;
import com.tydic.unicom.crawler.business.task.order5130.OrderDelivery_SaveStaffAddr;
import com.tydic.unicom.crawler.business.task.order5250.ManualAccout_CallBack;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.dao.po.Crawler_InfocallbackPo;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawler.service.impl.AbilityPlatformServDuImpl;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoCallbackServ;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoServ;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;
import com.tydic.unicom.webUtil.RespCodeContents;

import net.sf.json.JSONObject;

public class WriteBackServiceBusImpl extends BusBase implements WriteBackServiceBus {

	private static Logger logger = Logger.getLogger(WriteBackServiceBusImpl.class);

	@Autowired
	private Crawler_InfoCallbackServ crawler_InfoCallbackServ;

	@Autowired
	ManualAccout_CallBack manualaccout_callback;

	@Autowired
	AbilityPlatformServDuImpl aps;
	@Autowired
	Crawler_InfoServ crawler_infoserv;
	@Autowired
	OrderDelivery_SaveStaffAddr orderDelivery_SaveStaffAddr;

	//BusRespMessage brmsg;
	@Override
	public BusRespMessage writeBackManualAccountMethod(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger, "writeBackManualAccountMethod", businessResParamVo);
		BusRespMessage brmsg = new BusRespMessage();
		
		if(!SysPar.ppvo.isOpencallback()){
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("回写总部商城开关未打开");
			return brmsg;
		}
		
		// 这里检查输入不使用通用方法，因为有组合条件。
		if (businessResParamVo == null) {
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("传入参数错误");
			endloginfo(logger, this.getClass().getName() + "->[writeBackManualAccountMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		if (businessResParamVo.getUserid() == null || businessResParamVo.getUserid().isEmpty()) {
			if (businessResParamVo.getUser() == null || businessResParamVo.getUser().isEmpty()) {
				brmsg.setRespCode(BusRespMessage.ERR);
				brmsg.setContent("传入参数user不能为空！");
				endloginfo(logger, this.getClass().getName() + "->[writeBackManualAccountMethod] 结束参数:" + brmsg.toString());
				return brmsg;
			}
			if (businessResParamVo.getPassword() == null || businessResParamVo.getPassword().isEmpty()) {
				brmsg.setRespCode(BusRespMessage.ERR);
				brmsg.setContent("传入参数password不能为空！");
				endloginfo(logger, this.getClass().getName() + "->[writeBackManualAccountMethod] 结束参数:" + brmsg.toString());
				return brmsg;
			}
		}

		// 检查获取的对象
		Crawler_Mall_UserPo cmu = checkuser(businessResParamVo);
		if (cmu == null) {
			brmsg = new BusRespMessage();
			logger.debug("#####未找到对应的用户:" + businessResParamVo.getUser());
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("未找到对应的用户" + businessResParamVo.getUser());
			endloginfo(logger, this.getClass().getName() + "writeBackManualAccountMethod->[crawlerManualAccountMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		if (cmu.getCrawmu_sessioncookie() == null) {
			brmsg = new BusRespMessage();
			logger.debug("#####writeBackManualAccountMethod" + businessResParamVo.getUser() + "-登录信息不全请重新登录");
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent(businessResParamVo.getUser() + "-登录信息不全请重新登录");
			endloginfo(logger, this.getClass().getName() + "->[writeBackManualAccountMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		// 查找爬取信息表中用户的未回写总部商城的记录进行回写。
		BusRespMessage busRespMessage = new BusRespMessage();
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setCrawmu_uuid(cmu.getCrawmu_uuid());
		
		if(cmu.getCrawmu_uuid() == null || cmu.getCrawmu_uuid().isEmpty()){
			checkstatus();
		}
		
		
		//查找创建订单成功的信息
		cpp.setOrder_status(Crawler_InfoPo.CREATORDERCALLBACKOK);
		if(businessResParamVo.getParmap()!=null){
			cpp.setCi_uuid(businessResParamVo.getParmapItem("ci_uuid"));
		}
		
		
		int writeMallICCount = 0;
		int writeMallICOK = 0;
		int writeMallICNO = 0;
		try {
			List<Crawler_InfoPo> list = crawler_infoserv.query(cpp);

			if (list == null) {
				busRespMessage.setRespCode(BusRespMessage.SUCCESS);
				busRespMessage.setContent("回写完成，没有需要回写的信息。");
				busRespMessage.addArg("writeMallICCount", 0);
				busRespMessage.addArg("writeMallICOK", 0);
				busRespMessage.addArg("writeMallICNO", 0);
				endloginfo(logger, this.getClass().getName() + "->[writeBackManualAccountMethod] 结束参数:" + busRespMessage.toString());
				return busRespMessage;
			}

			// 开始得到详细连接
			logdebug(logger, "找到需要回写商城数据:" + list.size());
			writeMallICCount = list.size();
			for (Crawler_InfoPo crainfo : list) {
				logger.debug(crainfo.getOrder_sourceinfo());
				try {
					// 查找对应的回调表中的信息，如果存在就继续，如果不存在就返回错误
					Crawler_InfocallbackPo addCrawler_InfocallbackPo = new Crawler_InfocallbackPo();
					addCrawler_InfocallbackPo.setCi_uuid(crainfo.getCi_uuid());
					addCrawler_InfocallbackPo.setTache_code(addCrawler_InfocallbackPo.CRAWLERTOMALLICC);
					Crawler_InfocallbackPo po = crawler_InfoCallbackServ.get(addCrawler_InfocallbackPo);
					// 如果为空，说明数据有问题，如果没有就没有问题
					if (po == null) {
						logger.debug("未找到回写数据" + crainfo.convertToMap());
						writeMallICNO++;
						continue;
					}

					JSONObject json = JSONObject.fromObject(crainfo.getOrder_sourceinfo());
					json = json.getJSONObject("cbinfo");
					json.put("certType", json.get("certNum").toString().length() == 15 ? "01" : "02");
					String tmplID = json.getString("tmplId");
					if (tmplID.equals("60000005") || tmplID.equals("10000005") || tmplID.equals("10000006")) {
						json.put("goodsType", "LP");
						// 裸终端、配件
					} else if (tmplID.equals("60000019") || tmplID.equals("60000022") || tmplID.equals("60000023") || tmplID.equals("60000025") || tmplID.equals("60000026") || tmplID.equals("60000020") || tmplID.equals("60000028") || tmplID.equals("60000009") || tmplID.equals("60000004")) {
						// CBSS单号
						json.put("goodsType", "4G");
					} else if (tmplID.equals("60000014")) {
						// BSS单号
						json.put("goodsType", "2G");
					} else {
						// ESS单号
						json.put("goodsType", "3G");
					}

					MallHttpRequest mall = new MallHttpRequest(SysPar.ppvo.getBaselink() + "/Odm/ManualAccount/submitOrderDetails");
					mall.setMethod("POST");
					// mall.setCookie(SysPar.getSessionCookie());
					mall.setHeader("Referer", SysPar.ppvo.getBaselink() + "/Odm/ManualAccount/toDetail");
					mall.setHeader("Origin", "http://admin.mall.10010.com");
					mall.setHeader("X-Requested-With", "XMLHttpRequest");

					mall.setPar("orderId", json.getString("orderId"));
					mall.setPar("tmplId", json.getString("tmplId"));
					mall.setPar("goodsInstId", json.getString("goodInstId"));
					mall.setPar("certType", json.getString("certType"));
					mall.setPar("certNum", json.getString("certNum"));

					mall.setPar("manualImeiCode", "");
					mall.setPar("manualNetCardNum", json.getString("manualNetCardNum"));

					mall.setPar("manualUsimCardNum", po.getUsim());
					mall.setPar("manualOrderNo", po.getCbssnum());

					mall.setPar("goodsType", json.getString("goodsType"));
					mall.setCookie(cmu.getCrawmu_sessioncookie());

					MallHttpResponse mhrs = mall.response();

					if (mhrs.getCode() != 200) {
						String f = new String(mhrs.getContent(), "UTF-8");
						logger.info("回写总部商城数据失败，请确认数据是否正确或是接口已经改变");
						busRespMessage.setRespCode(BusRespMessage.ERR);
						busRespMessage.setContent("回写总部商城数据失败，请确认数据是否正确或是接口已经改变。");
						writeMallICNO++;
					} else {
						//修改数据库状态
						crainfo.setOrder_status(Crawler_InfoPo.CREATORDERWRITEMALLCRADOK);
						crawler_infoserv.update(crainfo);
						busRespMessage.setRespCode(BusRespMessage.SUCCESS);
						busRespMessage.setContent(crainfo.getCi_uuid() + ":" + crainfo.getCrawmu_uuid() + ",回写成功。");
						writeMallICOK++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("回写总部商城异常，请确认数据是否正确或是接口已经改变");
					busRespMessage.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
					busRespMessage.setContent("回写总部商城数据异常，请确认数据是否正确或是接口已经改变。");
					writeMallICNO++;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.info("查询回写数据异常！");
			busRespMessage.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			busRespMessage.setContent("查询回写数据异常！");
		}

		busRespMessage.addArg("writeMallICCount", writeMallICCount);
		busRespMessage.addArg("writeMallICOK", writeMallICOK);
		busRespMessage.addArg("writeMallICNO", writeMallICNO);
		endloginfo(logger, this.getClass().getName() + "->[writeBackManualAccountMethod] 结束参数:" + busRespMessage.toString());
		return busRespMessage;
	}

	/**
	 * 暂时不增加，因为还没有一个很好的解决方法，现在直接在界面中修改数据到某个状态
	 * 根据数据库中的信息，整理错误的数据
	 * 1、写卡回调接口，数据库存储了回调数据，但是主表中的状态没有改变，还是创建订单
	 * 2、回写总部商城后，回写成功但是没有更新状态
	 * 3、物流回调接口，数据库存储了回调数据，但是主表中的状态没有改变，还是等待同步卡数据
	 */
	private void checkstatus() {
	//	select * from crawler_info a where a.ci_uuid in (select ci_uuid from crawler_infocallback where tache_code = 'S00005' and ci_uuid=a.ci_uuid) and order_status <= 410 and order_status > 0
	//select * from crawler_info a where a.ci_uuid in (select ci_uuid from crawler_infocallback where tache_code = 'S00005' and ci_uuid=a.ci_uuid) and order_status in {410,430}

	}


	@Override
	/**
	 * 快递回写
	 */
	public BusRespMessage writeBackExpressMethod(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger, "writeBackExpressMethod", businessResParamVo);
		BusRespMessage brmsg = new BusRespMessage();
		if(!SysPar.ppvo.isOpencallback()){
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("回写总部商城开关未打开");
			return brmsg;
		}
		
		// 这里检查输入不使用通用方法，因为有组合条件。
		if (businessResParamVo == null) {
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("传入参数错误");
			endloginfo(logger, this.getClass().getName() + "->[writeBackExpressMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		if (businessResParamVo.getUserid() == null || businessResParamVo.getUserid().isEmpty()) {
			if (businessResParamVo.getUser() == null || businessResParamVo.getUser().isEmpty()) {
				brmsg.setRespCode(BusRespMessage.ERR);
				brmsg.setContent("传入参数user不能为空！");
				endloginfo(logger, this.getClass().getName() + "->[writeBackExpressMethod] 结束参数:" + brmsg.toString());
				return brmsg;
			}
			if (businessResParamVo.getPassword() == null || businessResParamVo.getPassword().isEmpty()) {
				brmsg.setRespCode(BusRespMessage.ERR);
				brmsg.setContent("传入参数password不能为空！");
				endloginfo(logger, this.getClass().getName() + "->[writeBackExpressMethod] 结束参数:" + brmsg.toString());
				return brmsg;
			}
		}

		// 检查获取的对象
		Crawler_Mall_UserPo cmu = checkuser(businessResParamVo);
		if (cmu == null) {
			brmsg = new BusRespMessage();
			logger.debug("#####未找到对应的用户:" + businessResParamVo.getUser());
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("未找到对应的用户" + businessResParamVo.getUser());
			endloginfo(logger, this.getClass().getName() + "writeBackExpressMethod->[writeBackExpressMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		if (cmu.getCrawmu_sessioncookie() == null) {
			brmsg = new BusRespMessage();
			logger.debug("#####writeBackExpressMethod" + businessResParamVo.getUser() + "-登录信息不全请重新登录");
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent(businessResParamVo.getUser() + "-登录信息不全请重新登录");
			endloginfo(logger, this.getClass().getName() + "->[writeBackExpressMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		BusRespMessage busRespMessage = new BusRespMessage();
		// 调用回写 发货/退货信息 ， 还未考证是否填写一个后就可以了，其它就不用填写了。如果这样就去除，直接商城中定好直接用，这里只是测试
//		try {
//			CrawlDatum datum = new CrawlDatum();
//			HashMap<String, String> map = new HashMap();
//			map.put("crawmu_uuid", cmu.getCrawmu_uuid());
//			map.put("sessioncookie", cmu.getCrawmu_sessioncookie());
//			datum.setMetaData(map);
//			orderDelivery_SaveStaffAddr.execute(datum);
//			MallHttpResponse mhrs = orderDelivery_SaveStaffAddr.getResponse();
//			if (mhrs.getCode() != 200) {
//				logger.info("回写总部商城【快递回写->发货/退货信息】失败，请确认数据是否正确或是接口已经改变");
//				busRespMessage.addArg("writeMallICCount", 0);
//				busRespMessage.addArg("writeMallICOK", 0);
//				busRespMessage.addArg("writeMallICNO", 0);
//				busRespMessage.setRespCode(BusRespMessage.ERR);
//				busRespMessage.setContent("回写总部商城【快递回写->发货/退货信息】失败，请确认数据是否正确或是接口已经改变。");
//				return busRespMessage;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.debug("回写总部商城【快递回写->发货/退货信息】异常，请确认数据是否正确或是接口已经改变");
//			busRespMessage.addArg("writeMallICCount", 0);
//			busRespMessage.addArg("writeMallICOK", 0);
//			busRespMessage.addArg("writeMallICNO", 0);
//			busRespMessage.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
//			busRespMessage.setContent("回写总部商城【快递回写->发货/退货信息】异常，请确认数据是否正确或是接口已经改变。");
//		}
//		logger.info("回写总部商城【快递回写->发货/退货信息】成功！");
		/** ======================回写快递信息，单号=================== **/
		// 查找创建订单成功的信息
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setCrawmu_uuid(cmu.getCrawmu_uuid());
		cpp.setOrder_status(Crawler_InfoPo.LOGISTICSCALLBACKOK);
		if(businessResParamVo.getParmap()!=null){
			cpp.setCi_uuid(businessResParamVo.getParmapItem("ci_uuid"));
		}
		int writeMallICCount = 0;
		int writeMallICOK = 0;
		int writeMallICNO = 0;
		try {
			List<Crawler_InfoPo> list = crawler_infoserv.query(cpp);

			if (list == null) {
				busRespMessage.setRespCode(BusRespMessage.SUCCESS);
				busRespMessage.setContent("回写完成，没有需要回写的信息。");
				busRespMessage.addArg("writeMallICCount", 0);
				busRespMessage.addArg("writeMallICOK", 0);
				busRespMessage.addArg("writeMallICNO", 0);
				endloginfo(logger, this.getClass().getName() + "->[writeBackExpressMethod] 结束参数:" + busRespMessage.toString());
				return busRespMessage;
			}

			// 开始得到详细连接
			logdebug(logger, "找到需要回写商城物流数据:" + list.size());
			writeMallICCount = list.size();
			for (Crawler_InfoPo crainfo : list) {
				logger.debug(crainfo.getOrder_sourceinfo());
				try {
					// 查找对应的回调表中的信息，如果存在就继续，如果不存在就返回错误
					Crawler_InfocallbackPo addCrawler_InfocallbackPo = new Crawler_InfocallbackPo();
					addCrawler_InfocallbackPo.setCi_uuid(crainfo.getCi_uuid());
					addCrawler_InfocallbackPo.setTache_code(addCrawler_InfocallbackPo.CRAWLERTOMALLLOGISTICS);
					Crawler_InfocallbackPo po = crawler_InfoCallbackServ.get(addCrawler_InfocallbackPo);
					// 如果为空，说明数据有问题，如果没有就没有问题
					if (po == null) {
						logger.debug("未找到回写数据" + crainfo.convertToMap());
						writeMallICNO++;
						continue;
					}

					JSONObject json = JSONObject.fromObject(crainfo.getOrder_sourceinfo());
					json = json.getJSONObject("cbinfo");

					//物流单单号回填操作
					MallHttpRequest mall = new MallHttpRequest(SysPar.ppvo.getBaselink() + "/Odm/OrderDelivery/subLgtsOrder");
					mall.setMethod("POST");
					mall.setHeader("Referer", SysPar.ppvo.getBaselink() + "/Odm/OrderDelivery/toDetail");
					mall.setHeader("Origin", "http://admin.mall.10010.com");
					mall.setHeader("X-Requested-With", "XMLHttpRequest");
					
					mall.setPar("billType", "paperBill"); // 顺丰的代码
					mall.setPar("chinaUnicomSelfFetch", "0"); // 顺丰的代码
					mall.setPar("chinaUnicomSelfFetchAddrId", ""); // 顺丰的代码
					mall.setPar("companyId", "1002"); // 顺丰的代码
					mall.setPar("companyName", "顺丰速运"); // 顺丰的代码
					mall.setPar("deliver_type_code", "01"); // 顺丰的代码
					mall.setPar("isNeedLgtsRtn", "0"); // 顺丰的代码
					mall.setPar("lgtsOrder", po.getLogistics_no());
					mall.setPar("lgtsRemark", "");
					mall.setPar("logisticType", "2");
					mall.setPar("orderId", crainfo.getOrder_id());
					mall.setPar("self_Type", "0");
					mall.setCookie(cmu.getCrawmu_sessioncookie());
					MallHttpResponse mhrs = mall.response();

					if (mhrs.getCode() != 200) {
						String repcon = "NULL";
						if(mhrs!=null && mhrs.getContent() !=null){
							repcon = new String(mhrs.getContent(), "UTF-8");
						}
						logger.info("回写总部商城【发货单号】失败！"+repcon);
						busRespMessage.setRespCode(BusRespMessage.ERR);
						busRespMessage.setContent("回写总部商城【发货单号】失败！");
						writeMallICNO++;
						continue;
					} else {
						//{"LGTS_REMARK":"","LGTS_ORDER_RTN":"","BILL_TYPE":"0","LGTS_RTN_REMARK":"","LGTS_ORDER":"1111111111","RESP_CODE":"SUCCESS"}
						JSONObject mhrscontent = JSONObject.fromObject(new String(mhrs.content(), "UTF-8"));
						crainfo.setLogistics_repcon(mhrscontent.toString());
						if(mhrscontent.optString("RESP_CODE","").equals("SUCCESS")){
							busRespMessage.setRespCode(BusRespMessage.SUCCESS);
							busRespMessage.setContent("回写成功。");
							writeMallICOK++;
						}else{
							logger.info("回写总部商城数据失败!");
							busRespMessage.setRespCode(BusRespMessage.ERR);
							busRespMessage.setContent("回写总部商城数据失败。"+mhrscontent.toString());
							writeMallICNO++;
							continue;
						}
						crawler_infoserv.update(crainfo);
					}
					
					//发货操作
					MallHttpRequest mallfh = new MallHttpRequest(SysPar.ppvo.getBaselink() + "/Odm/OrderDelivery/doDelivery");
					mallfh.setMethod("POST");
					mallfh.setHeader("Referer", SysPar.ppvo.getBaselink() + "/Odm/OrderDelivery/toDetail");
					mallfh.setHeader("Origin", "http://admin.mall.10010.com");
					mallfh.setHeader("X-Requested-With", "XMLHttpRequest");
					
					mallfh.setPar("billType", "paperBill"); // 顺丰的代码
					mallfh.setPar("companyId", "1002"); // 顺丰的代码
					mallfh.setPar("companyName", "顺丰速运"); // 顺丰的代码
					mallfh.setPar("deliver_type_code", "01"); // 顺丰的代码
					mallfh.setPar("lgtsOrder", po.getLogistics_no());
					mallfh.setPar("lgtsRemark", "");
					mallfh.setPar("logisticType", "2");
					mallfh.setPar("orderId", crainfo.getOrder_id());
					mallfh.setPar("callPlatform", "1");
					mallfh.setPar("lgtsRtnOrder", "");
					mallfh.setPar("lgtsRtnRemark", "");
					mallfh.setCookie(cmu.getCrawmu_sessioncookie());
					MallHttpResponse mhrsfh = mallfh.response();
					
					if (mhrsfh.getCode() != 200) {
						String f = new String(mhrsfh.getContent(), "UTF-8");
						logger.info("回写总部商城【发货】失败！");
						busRespMessage.setRespCode(BusRespMessage.ERR);
						busRespMessage.setContent("回写总部商城【发货】失败！");
					} else {
						//{"CHECK_SUCCESS":true,"UPDATE_SUCCESS":true}
						JSONObject mhrscontent = JSONObject.fromObject(new String(mhrsfh.content(), "UTF-8"));
						logger.debug("========================="+mhrscontent.toString());
						// 修改数据库状态
						crainfo.setShipments_repcon(mhrscontent.toString());
						if(mhrscontent.optString("CHECK_SUCCESS","").equals("true")){
							busRespMessage.setRespCode(BusRespMessage.SUCCESS);
							busRespMessage.setContent("回写成功。");
							crainfo.setOrder_status(Crawler_InfoPo.LOGISTICSWRITEMALLOK);
						}else{
							logger.info("回写总部商城【发货】失败!");
							busRespMessage.setRespCode(BusRespMessage.ERR);
							busRespMessage.setContent("回写总部商城【发货】失败。"+mhrscontent.toString());
						}
						crawler_infoserv.update(crainfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("回写总部【发货】异常！");
					busRespMessage.setRespCode(BusRespMessage.ERR);
					busRespMessage.setContent("回写总部商城【发货】异常！");
					writeMallICNO++;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.info("查询回写数据异常！");
			busRespMessage.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			busRespMessage.setContent("查询回写数据异常！");
		}

		busRespMessage.addArg("writeMallICCount", writeMallICCount);
		busRespMessage.addArg("writeMallICOK", writeMallICOK);
		busRespMessage.addArg("writeMallICNO", writeMallICNO);
		endloginfo(logger, this.getClass().getName() + "->[writeBackExpressMethod] 结束参数:" + busRespMessage.toString());

		return busRespMessage;
	}

	/**
	 * {"receive_area":"500103","receive_address":"重庆，重庆市，渝中区，1","receive_phone":"18623457767","receive_name":"胡冰洁","modi_time":"20170425112008","accept_no":"7815822049","receive_city":"5000","tache_code":"S00011","SERVICE_NAME":"tms.ztorder.modifyOrder","order_no":"3831170425000124519","receive_province":"50","order_type":"101"}
	 * {"sim_id":"8986011688300201628","proc_id":"8317050356865879","modi_time":"20170503160836","accept_no":"4368972159","tache_code":"S00005","order_no":"3831170503000124616","order_type":"101"}
	 * {"logistics_no":"1111111111","modi_time":"20170503160836","accept_no":"4368972159","tache_code":"S00013","order_no":"3831170503000124616","order_type":"101"}
	 */
	@Override
	public BusRespMessage crawlerRecallMethod(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger, "crawlerRecallMethod", businessResParamVo);
		BusRespMessage brmsg = new BusRespMessage();
		
		JSONObject paramsObj = JSONObject.fromObject(businessResParamVo.getJsonInfo());
		// JSONObject paramsObj = reqObj.getJSONObject("params");

		// 订单类型
		String order_type = paramsObj.getString("order_type");
		// 订单环节
		String tache_code = paramsObj.getString("tache_code");
		// 根据销售订单号去查询数据
		Crawler_InfoPo queryCrawlerInfoPo = new Crawler_InfoPo();

		Crawler_InfoPo ret_crawlerInfoDataPo = new Crawler_InfoPo();

		if ("100".equals(order_type)) {
			queryCrawlerInfoPo.setSale_order_no(paramsObj.getString("order_no"));
			// 查询数据是否存在
			ret_crawlerInfoDataPo = crawler_infoserv.queryCrawlerInfoByOrderNo(queryCrawlerInfoPo);
		}
		// 根据服务订单号去查询数据
		else if ("101".equals(order_type)) {
			queryCrawlerInfoPo.setServ_order_no_list(paramsObj.getString("order_no"));
			// 查询数据是否存在
			ret_crawlerInfoDataPo = crawler_infoserv.queryCrawlerInfoByOrderNo(queryCrawlerInfoPo);
		} else {
			brmsg.setRespCode(BusRespMessage.PARAM_ERROR);
			brmsg.setContent("传入的订单类型有误");
			endloginfo(logger, this.getClass().getName() + "->[crawlerRecallMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		if (ret_crawlerInfoDataPo == null) {
			brmsg.setRespCode(BusRespMessage.SERVICE_FAIL);
			brmsg.setContent("数据不存在，无法更新");
			endloginfo(logger, this.getClass().getName() + "->[crawlerRecallMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		Crawler_InfocallbackPo addCrawler_InfocallbackPo = new Crawler_InfocallbackPo();
		if ("S00005".equals(tache_code)) {
			addCrawler_InfocallbackPo.setCi_uuid(ret_crawlerInfoDataPo.getCi_uuid());
			addCrawler_InfocallbackPo.setCicb_callbackinfo(businessResParamVo.getJsonInfo());
			addCrawler_InfocallbackPo.setModi_time(paramsObj.getString("modi_time"));
			addCrawler_InfocallbackPo.setTache_code(tache_code);
			addCrawler_InfocallbackPo.setUsim(paramsObj.getString("sim_id"));
			addCrawler_InfocallbackPo.setCbssnum(paramsObj.getString("proc_id"));
			
			ret_crawlerInfoDataPo.setUsim(paramsObj.getString("sim_id"));
			ret_crawlerInfoDataPo.setCbssnum(paramsObj.getString("proc_id"));
			ret_crawlerInfoDataPo.setOrder_status(Crawler_InfoPo.CREATORDERCALLBACKOK);
		} else if ("S00013".equals(tache_code)) {
			String logistics_no = paramsObj.getString("logistics_no");
			if (StringUtils.isEmpty(logistics_no)) {
				brmsg.setRespCode(BusRespMessage.PARAM_ERROR);
				brmsg.setContent("S00013下，物流单号为空");
				return brmsg;
			}
			addCrawler_InfocallbackPo.setCi_uuid(ret_crawlerInfoDataPo.getCi_uuid());
			addCrawler_InfocallbackPo.setCicb_callbackinfo(businessResParamVo.getJsonInfo());
			addCrawler_InfocallbackPo.setModi_time(paramsObj.getString("modi_time"));
			addCrawler_InfocallbackPo.setTache_code(tache_code);
			addCrawler_InfocallbackPo.setLogistics_no(logistics_no);
			// 回写商城成功的状态为 450，修改状态到530
			if (Crawler_InfoPo.CREATORDERWRITEMALLCRADOK.equals(ret_crawlerInfoDataPo.getOrder_status())) {
				ret_crawlerInfoDataPo.setLogistics_no(logistics_no);
				ret_crawlerInfoDataPo.setOrder_status(Crawler_InfoPo.LOGISTICSCALLBACKOK);
			}
		} else {
			addCrawler_InfocallbackPo.setCi_uuid(ret_crawlerInfoDataPo.getCi_uuid());
			addCrawler_InfocallbackPo.setCicb_callbackinfo(businessResParamVo.getJsonInfo());
			addCrawler_InfocallbackPo.setModi_time(paramsObj.getString("modi_time"));
			addCrawler_InfocallbackPo.setTache_code(tache_code);
		}

		// 插入数据crawler_InfoCallback表
		logdebug(logger, "crawler_InfoCallback：" + addCrawler_InfocallbackPo.convertToMap().toString());
		boolean Result = crawler_InfoCallbackServ.addCrawlerInfoCallBack(addCrawler_InfocallbackPo);
		// 更新数据crawler_Info表
		try {
			logger.debug("=============================crawler_Info：" + ret_crawlerInfoDataPo.convertToMap().toString());
			Result = crawler_infoserv.update(ret_crawlerInfoDataPo);
		} catch (Exception e) {
			e.printStackTrace();
			brmsg.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			brmsg.setContent("回写(crawler_Info)状态异常");
			endloginfo(logger, this.getClass().getName() + "->[crawlerRecallMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		if (!Result) {
			brmsg.setRespCode(BusRespMessage.SERVICE_FAIL);
			brmsg.setContent("回写(crawler_Info)状态失败");

		} else {
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("回写(crawler_Info)状态成功");
			// 如果成功，就进行总部商城回调，回调出错不记录任何信息，也不报错。
			try {
				businessResParamVo.setUserid(ret_crawlerInfoDataPo.getCrawmu_uuid());
				HashMap map = (HashMap) ret_crawlerInfoDataPo.convertToMap();
				businessResParamVo.setParmap(map);
				if ("S00005".equals(tache_code)) {
					writeBackManualAccountMethod(businessResParamVo);
				}
				if ("S00013".equals(tache_code)) {
					writeBackExpressMethod(businessResParamVo);
				}
			} catch (Exception e) {
				// 查询用户信息失败，回调结束
			}
		}
		endloginfo(logger, this.getClass().getName() + "->[crawlerRecallMethod] 结束参数:" + brmsg.toString());
		return brmsg;
	}

	@Override
	public BusRespMessage crawlerCancelApplyMethod(BusinessResParamVo businessResParamVo) throws Exception {
		BusRespMessage brmsg = new BusRespMessage();
		beginlogger(logger, "crawlerCancelApplyMethod", businessResParamVo);
		if(SysPar.ppvo.isOpencallback()){
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("回写总部商城开关未打开");
			endloginfo(logger, this.getClass().getName() + "->[crawlerCancelApplyMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		
		// 这里检查输入不使用通用方法，因为有组合条件。
		if (businessResParamVo == null) {
			brmsg.setRespCode("9999");
			brmsg.setContent("传入参数错误");
			endloginfo(logger, this.getClass().getName() + "->[crawlerCancelApplyMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		if (businessResParamVo.getUserid() == null || businessResParamVo.getUserid().isEmpty()) {
			if (businessResParamVo.getUser() == null || businessResParamVo.getUser().isEmpty()) {
				brmsg.setRespCode(BusRespMessage.ERR);
				brmsg.setContent("传入参数user不能为空！");
				endloginfo(logger, this.getClass().getName() + "->[crawlerCancelApplyMethod] 结束参数:" + brmsg.toString());
				return brmsg;
			}
			if (businessResParamVo.getPassword() == null || businessResParamVo.getPassword().isEmpty()) {
				brmsg.setRespCode(BusRespMessage.ERR);
				brmsg.setContent("传入参数password不能为空！");
				endloginfo(logger, this.getClass().getName() + "->[crawlerCancelApplyMethod] 结束参数:" + brmsg.toString());
				return brmsg;
			}
		}

		if (businessResParamVo.getOrder_id() == null || businessResParamVo.getOrder_id().isEmpty()) {
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("传入参数Order_id【订单ID】不能为空！");
			endloginfo(logger, this.getClass().getName() + "->[crawlerCancelApplyMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		// 检查获取的对象
		Crawler_Mall_UserPo cmu = checkuser(businessResParamVo);
		if (cmu == null) {
			brmsg = new BusRespMessage();
			logger.debug("#####未找到对应的用户:" + businessResParamVo.getUser());
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("未找到对应的用户" + businessResParamVo.getUser());
			endloginfo(logger, this.getClass().getName() + "->[crawlerManualAccountMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		if (cmu.getCrawmu_sessioncookie() == null) {
			brmsg = new BusRespMessage();
			logger.debug("#####writeBackManualAccountMethod" + businessResParamVo.getUser() + "-登录信息不全请重新登录");
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent(businessResParamVo.getUser() + "-登录信息不全请重新登录");
			endloginfo(logger, this.getClass().getName() + "->[crawlerCancelApplyMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}

		BusRespMessage busRespMessage = new BusRespMessage();
		/** ======================回写退单=================== **/
		// 查找创建订单成功的信息
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setCrawmu_uuid(cmu.getCrawmu_uuid());
		cpp.setOrder_id(businessResParamVo.getOrder_id());

		try {
			List<Crawler_InfoPo> list = crawler_infoserv.query(cpp);

			if (list == null) {
				busRespMessage.setRespCode(BusRespMessage.SUCCESS);
				busRespMessage.setContent("回写完成，没有需要回写的信息。");
				endloginfo(logger, this.getClass().getName() + "->[crawlerCancelApplyMethod] 结束参数:" + busRespMessage.toString());
				return busRespMessage;
			}

			// 开始得到详细连接
			logdebug(logger, "找到需要回写的退单数据:" + list.size());
			for (Crawler_InfoPo crainfo : list) {
				logger.debug(crainfo.getOrder_sourceinfo());
				try {
					JSONObject json = JSONObject.fromObject(crainfo.getOrder_sourceinfo());
					json = json.getJSONObject("cbinfo");

					MallHttpRequest mall = new MallHttpRequest(SysPar.ppvo.getBaselink() + "/Odm/OrderDelivery/cancelApply");
					mall.setMethod("POST");
					// mall.setCookie(SysPar.getSessionCookie());
					mall.setHeader("Referer", SysPar.ppvo.getBaselink() + "/Odm/OrderDelivery/toDetail");
					mall.setHeader("Origin", "http://admin.mall.10010.com");
					mall.setHeader("X-Requested-With", "XMLHttpRequest");

					mall.setPar("reasonCode", "502"); // 顺丰的代码
					mall.setPar("cancelRemark", "");// 备注
					mall.setPar("orderId", crainfo.getOrder_id());

					mall.setCookie(cmu.getCrawmu_sessioncookie());
					MallHttpResponse mhrs = mall.response();

					if (mhrs.getCode() != 200) {
						String f = new String(mhrs.getContent(), "UTF-8");
						logger.info("回写总部商城退单数据失败，请确认数据是否正确或是接口已经改变");
						busRespMessage.setRespCode("9999");
						busRespMessage.setContent("回写总部商城退单数据失败，请确认数据是否正确或是接口已经改变。");
					} else {
						// 修改数据库状态
						crainfo.setOrder_status(Crawler_InfoPo.CANCELAPPLYOK);
						crawler_infoserv.update(crainfo);
						busRespMessage.setRespCode("0");
						busRespMessage.setContent(crainfo.getCi_uuid() + ":" + crainfo.getCrawmu_uuid() + ",回写退单成功。");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("回写总部商城退单异常，请确认数据是否正确或是接口已经改变");
					busRespMessage.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
					busRespMessage.setContent("回写总部商城退单数据异常，请确认数据是否正确或是接口已经改变。");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.info("回写退单数据异常！");
			busRespMessage.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			busRespMessage.setContent("回写数据异常！");
		}
		endloginfo(logger, this.getClass().getName() + "->[writeBackManualAccountMethod] 结束参数:" + busRespMessage.toString());
		return busRespMessage;
	}

	public static void main(String[] args) {
		//{jsonInfo={"sim_id":"8986011688300201628","proc_id":"8317050356865879","modi_time":"20170503160836","accept_no":"4368972159","tache_code":"S00005","order_no":"3831170503000124616","order_type":"101"}}
		//"{\"receive_area\":\"500103\",\"receive_address\":\"重庆，重庆市，渝中区，1\",\"receive_phone\":\"18623457767\",\"receive_name\":\"胡冰洁\",\"modi_time\":\"20170425112008\",\"accept_no\":\"7815822049\",\"receive_city\":\"5000\",\"tache_code\":\"S00011\",\"SERVICE_NAME\":\"tms.ztorder.modifyOrder\",\"order_no\":\"3831170425000124519\",\"receive_province\":\"50\",\"order_type\":\"101\"}";

		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");
		WriteBackServiceBus lm = (WriteBackServiceBus) context.getBean("WriteBackServiceBus");
		BusinessResParamVo bvo = new BusinessResParamVo();
		bvo.setUserid("4CB9095662E6DF4CE050937B5DDF767C");
		
		 String jsonInfo ="{\"sim_id\":\"8986011688300201628\",\"proc_id\":\"8317050356865879\",\"modi_time\":\"20170503160836\",\"accept_no\":\"4368972159\",\"tache_code\":\"S00005\",\"order_no\":\"3831170517000124845\",\"order_type\":\"101\"}";
//		 String jsonInfo ="{\"logistics_no\":\"1111111111\",\"modi_time\":\"20170503160836\",\"accept_no\":\"4368972159\",\"tache_code\":\"S00013\",\"order_no\":\"3831170517000124845\",\"order_type\":\"101\"}";
		 bvo.setJsonInfo(jsonInfo);

		try {
//			lm.writeBackExpressMethod(bvo);
			 lm.crawlerRecallMethod(bvo);
			// lm.isLogin(bvo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public BusRespMessage updateOrderStatusMethod(BusinessResParamVo businessResParamVo) throws Exception {
		
		beginlogger(logger,"updateOrderStatusMethod", businessResParamVo);
		BusRespMessage brmsg;
		
		businessResParamVo.setChecklist("user,password,order_id,other");
		//检查输入 注意这里会返回NULL ，后边需要在创建一下
		brmsg = checkinput(businessResParamVo);
		if(brmsg!=null){
			endloginfo(logger, this.getClass().getName() + "->[updateOrderStatusMethod] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		brmsg = new BusRespMessage();
		if (SysPar.user.get(businessResParamVo.getUser()) == null) {
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("未绑定商城操作员");
			endloginfo(logger,this.getClass().getName()+"->[updateOrderStatusMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}

		Crawler_Mall_UserPo cmu = (Crawler_Mall_UserPo) SysPar.user.get(businessResParamVo.getUser());
		if(!cmu.getCrawmu_pwd().equals(businessResParamVo.getPassword())){
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("验证用户信息错误");
			return brmsg;
		}
		
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setOrder_id(businessResParamVo.getOrder_id());
		Crawler_InfoPo cirep= crawler_infoserv.get(cpp);
		if(cirep!= null){
			cirep.setOrder_status(businessResParamVo.getOther());
			crawler_infoserv.update(cirep);
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("修改状态成功！");
		}else{
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("修改状态错误！");
		}
		endloginfo(logger,this.getClass().getName()+"->[updateOrderStatusMethod] 结束参数:"+brmsg.toString());

		return brmsg;
	}
	
	
	
}
