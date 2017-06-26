	package com.tydic.unicom.crawler.business.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import com.tydic.unicom.crawler.business.interfaces.GetInfoServiceBus;
import com.tydic.unicom.crawler.business.task.SafetyCodeEmail;
import com.tydic.unicom.crawler.business.task.SafetyCodePhone;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crawler.common.CrawlerInit;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawler.service.impl.Crawler_Mall_UserServImpl;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoServ;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;

import net.sf.json.JSONObject;

public class GetInfoServiceBusImpl extends BusBase implements GetInfoServiceBus {

	private static Logger logger = Logger.getLogger(GetInfoServiceBusImpl.class);

	@Autowired
	SafetyCodePhone safetycodephone;
	@Autowired
	SafetyCodeEmail safetycodeemail;
	@Autowired
	Crawler_Mall_UserServImpl crawlermalluserservimpl;
	@Autowired
	private Crawler_InfoServ crawler_InfoServ;
	
	@Autowired
	private CrawlerInit crawlerInit;


	@Override
	public BusRespMessage getSafeCode(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"getSafeCode", businessResParamVo);
		BusRespMessage msg;
		businessResParamVo.setChecklist("user,type");
		//检查输入
		msg = checkinput(businessResParamVo);
//		logdebug(logger,"", businessResParamVo);
		logdebug(logger, "检查输入！");
		if(msg!=null){
			endloginfo(logger, this.getClass().getName() + "->[getSafeCode] 结束参数:" + msg.toString());
			return msg;
		}
		logdebug(logger, "检查输入成功！");
		msg = new BusRespMessage();
		if (businessResParamVo.getType().equals("0")) {
			if (SysPar.user.get(businessResParamVo.getUser()) != null) {
				CrawlDatum datum = new CrawlDatum();
				datum.setPar("CRAWMU_NAME", businessResParamVo.getUser());
				safetycodeemail.execute(datum);
				String basereqcookie = safetycodeemail.requestcookie;
				String rescookie = safetycodeemail.getResponse().cookie;
				Crawler_Mall_UserPo cmu = new Crawler_Mall_UserPo();
				cmu.setCrawmu_basereqcookie(basereqcookie);
				cmu.setCrawmu_rescookie(rescookie);
				cmu.setCrawmu_name(businessResParamVo.getUser());
				crawlermalluserservimpl.updatesession(cmu);
			} else {
				msg.setRespCode(BusRespMessage.ERR);
				msg.setContent("未绑定商城操作员");
				endloginfo(logger,this.getClass().getName()+"->[getSafeCode] 结束参数:"+msg.toString());
				return msg;
			}
			msg.setRespCode("0");
			msg.setContent("成功发送验证码" + businessResParamVo.getUser());
		}
		if (businessResParamVo.getType().equals("1")) {
			if (SysPar.user.get(businessResParamVo.getUser()) != null) {
				CrawlDatum datum = new CrawlDatum();
				datum.setPar("CRAWMU_NAME", businessResParamVo.getUser());
				safetycodephone.execute(datum);
				if (safetycodephone.getResponse().getCode() == 200) {
					String basereqcookie = safetycodephone.requestcookie;
					String rescookie = safetycodephone.getResponse().cookie;
					Crawler_Mall_UserPo cmu = new Crawler_Mall_UserPo();
					cmu.setCrawmu_basereqcookie(basereqcookie);
					cmu.setCrawmu_rescookie(rescookie);
					cmu.setCrawmu_name(businessResParamVo.getUser());
					crawlermalluserservimpl.updatesession(cmu);
					msg.setRespCode("0");
					msg.setContent("成功发送验证码：" + businessResParamVo.getUser());
					endloginfo(logger,this.getClass().getName()+"->[getSafeCode] 结束参数:"+msg.toString());
					return msg;
				}
				msg.setRespCode("9999");
				msg.setContent("获取验证码失败，网站状态" + safetycodephone.getResponse().getCode());
			} else {
				msg.setRespCode("9999");
				msg.setContent("未绑定商城操作员");
				endloginfo(logger,this.getClass().getName()+"->[getSafeCode] 结束参数:"+msg.toString());
				return msg;
			}

		}
		endloginfo(logger,this.getClass().getName()+"->[getSafeCode] 结束参数:"+msg.toString());
		return msg;
	}

	@Override
	public BusRespMessage getUserNameMethod(BusinessResParamVo businessResParamVo) throws Exception{
		
		if(SysPar.user.isEmpty()){
			crawlerInit.init();
		}
		
		beginlogger(logger,"getUserNameMethod", businessResParamVo);
		BusRespMessage msg;
		businessResParamVo.setChecklist("jsession_id");
		//检查输入
		msg = checkinput(businessResParamVo);
		logdebug(logger, "检查输入！");
		if(msg!=null){
			endloginfo(logger, this.getClass().getName() + "->[getUserNameMethod] 结束参数:" + msg.toString());
			return msg;
		}
		logdebug(logger, "检查输入成功！");
		msg = new BusRespMessage();
		if (SysPar.user.get(businessResParamVo.getJsession_id()) != null) {
			Crawler_Mall_UserPo cmu = (Crawler_Mall_UserPo) SysPar.user.get(businessResParamVo.getJsession_id());
			
			if(cmu.getCrawmu_uacname()!=null && !cmu.getCrawmu_uacname().isEmpty()){
				msg.setRespCode("0000");
				msg.setContent(cmu.getCrawmu_name());
				endloginfo(logger,this.getClass().getName()+"->[getUserNameMethod] 结束参数:"+msg.toString());
				return msg;
			}
			else{
				msg.setRespCode("9999");
				msg.setContent("未绑定商城操作员");
				endloginfo(logger,this.getClass().getName()+"->[getUserNameMethod] 结束参数:"+msg.toString());
				return msg;
			}
		}
		msg.setRespCode("9999");
		msg.setContent("未绑定商城操作员");
		endloginfo(logger,this.getClass().getName()+"->[getUserNameMethod] 结束参数:"+msg.toString());
		return msg;
	}
	
	@Override
	public BusRespMessage initSysParamsMethod(BusinessResParamVo businessResParamVo) throws Exception {
		boolean result = crawlerInit.init();
		BusRespMessage busRespMessage = new BusRespMessage();
		if (!result) {
			busRespMessage.setRespCode(BusRespMessage.SERVICE_FAIL);
			busRespMessage.setContent("初始化失败");
		} else {
			busRespMessage.setRespCode(BusRespMessage.SUCCESS);
			busRespMessage.setContent("初始化成功");
		}
		return busRespMessage;
	}
	
	//得到查询列表
	
	
	
	
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");

		CrawlerInit crawlerInit = (CrawlerInit) context.getBean("crawlerInit");
		crawlerInit.init();

		GetInfoServiceBusImpl lm = (GetInfoServiceBusImpl) context.getBean("getInfoServiceBusImpl");

		BusinessResParamVo bvo = new BusinessResParamVo();
		bvo.setUser("CQCSGZ1");
		bvo.setPassword("ran20070706");
		bvo.setSafecode("215946");
		bvo.setType("1");

		try {
			lm.getSafeCode(bvo);
			// lm.isLogin(bvo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public BusRespMessage getOrderInfoList(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"getOrderInfoList", businessResParamVo);
		BusRespMessage brmsg;
		businessResParamVo.setChecklist("user");
		//检查输入 注意这里会返回NULL ，后边需要在创建一下
		brmsg = checkinput(businessResParamVo);
		if(brmsg!=null){
			endloginfo(logger, this.getClass().getName() + "->[getOrderInfoList] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		brmsg = new BusRespMessage();
		if (SysPar.user.get(businessResParamVo.getUser()) == null) {
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("未绑定商城操作员");
			endloginfo(logger,this.getClass().getName()+"->[getOrderInfoList] 结束参数:"+brmsg.toString());
			return brmsg;
		}

		Crawler_Mall_UserPo cmu = (Crawler_Mall_UserPo) SysPar.user.get(businessResParamVo.getUser());
		JSONObject json = JSONObject.fromObject(businessResParamVo.getOther());
		brmsg = new BusRespMessage();
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setCi_uuid(json.getString("ci_uuid"));
		cpp.setOrder_status(json.getString("order_status"));
		cpp.setCrawmu_uuid(cmu.getCrawmu_uuid());
		
		List<Crawler_InfoPo> list = crawler_InfoServ.likequery(cpp);
		
		//有可能爬虫接口爬取了很多借口，因为数据库中已经存在商城的订单ID ，所以就不需要在中台创建
		if (list == null) {
			logger.debug("#####未找到手动开户信息");
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("完成订单查询。");
			brmsg.addArg("cratouoccount", "0");
			brmsg.addArg("cratouoccountok", "0");
			brmsg.addArg("cratouoccountno", "0");
			brmsg.addArg("gridlist", "");
			return brmsg;
		}
		JSONObject createjson = createCrawlerInfo_JSON(list);
		logger.debug(createjson);
		brmsg.addArg("listjson", createjson);
		brmsg.setRespCode(BusRespMessage.SUCCESS);
		brmsg.setContent("查询完成！");
		
		endloginfo(logger,this.getClass().getName()+"->[getOrderInfoList] 结束参数:"+brmsg.toString());

		return brmsg;
	}

	@Override
	public BusRespMessage getCountStatus(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"getOrderInfoList", businessResParamVo);
		BusRespMessage brmsg;
		businessResParamVo.setChecklist("user");
		//检查输入 注意这里会返回NULL ，后边需要在创建一下
		brmsg = checkinput(businessResParamVo);
		if(brmsg!=null){
			endloginfo(logger, this.getClass().getName() + "->[getOrderInfoList] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		brmsg = new BusRespMessage();
		if (SysPar.user.get(businessResParamVo.getUser()) == null) {
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("未绑定商城操作员");
			endloginfo(logger,this.getClass().getName()+"->[getOrderInfoList] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		Crawler_Mall_UserPo cmu = (Crawler_Mall_UserPo) SysPar.user.get(businessResParamVo.getUser());
		JSONObject json = JSONObject.fromObject(businessResParamVo.getOther());
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setCrawmu_uuid(cmu.getCrawmu_uuid());
		
		List<Crawler_InfoPo> list = crawler_InfoServ.getCountStatus(cpp);
		if (list == null) {
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("完成订单查询。");
			brmsg.addArg("gridlist", "");
			return brmsg;
		}
		JSONObject createitem = new JSONObject();
		for(int i = 0 ; i < list.size() ; i++){
			Crawler_InfoPo cpo = list.get(i);
			//这里直接使用的CPO bean，没有创建新的请注意，这个是个SQL聚合函数返回的统计数
			createitem.put(cpo.getOrder_id(),cpo.getOrder_info());
		}
		brmsg.addArg("listjson", createitem);
		brmsg.setRespCode(BusRespMessage.SUCCESS);
		brmsg.setContent("查询完成！");
		return brmsg;
	}

	@Override
	public BusRespMessage getOrderInfoItem(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"getOrderInfoItem", businessResParamVo);
		BusRespMessage brmsg;
		
		businessResParamVo.setChecklist("user");
		//检查输入 注意这里会返回NULL ，后边需要在创建一下
		brmsg = checkinput(businessResParamVo);
		if(brmsg!=null){
			endloginfo(logger, this.getClass().getName() + "->[getOrderInfoItem] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		brmsg = new BusRespMessage();
		if (SysPar.user.get(businessResParamVo.getUser()) == null) {
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("未绑定商城操作员");
			endloginfo(logger,this.getClass().getName()+"->[getOrderInfoItem] 结束参数:"+brmsg.toString());
			return brmsg;
		}

		Crawler_Mall_UserPo cmu = (Crawler_Mall_UserPo) SysPar.user.get(businessResParamVo.getUser());
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		
		//如果不是修改的管理员就需要通过 ci_uuid和用户进行查找，如果是就通过订单号查找
		if(!businessResParamVo.getUser().equals("MODIFY")){
			cpp.setCrawmu_uuid(cmu.getCrawmu_uuid());
			cpp.setCi_uuid(businessResParamVo.getOther());
		}else{
			cpp.setOrder_id(businessResParamVo.getOrder_id());
		}
		
		List<Crawler_InfoPo>  list= crawler_InfoServ.query(cpp);
		//有可能爬虫接口爬取了很多借口，因为数据库中已经存在商城的订单ID ，所以就不需要在中台创建
		if (list == null) {
			logger.debug("#####未找到信息 Ci_uuid"+cpp.getCi_uuid() + "  Crawmu_uuid" + cpp.getCrawmu_uuid());
			brmsg.setRespCode(BusRespMessage.ERR);
			brmsg.setContent("未找到订单信息。");
			brmsg.addArg("item", "{}");
			return brmsg;
		}
		Crawler_InfoPo resp = list.get(0);
		JSONObject createjson = JSONObject.fromObject(resp.convertToMap());
		brmsg.addArg("item", createjson);
		brmsg.setRespCode(BusRespMessage.SUCCESS);
		brmsg.setContent("查询完成！");
		
		endloginfo(logger,this.getClass().getName()+"->[getOrderInfoItem] 结束参数:"+brmsg.toString());

		return brmsg;
	}

}
