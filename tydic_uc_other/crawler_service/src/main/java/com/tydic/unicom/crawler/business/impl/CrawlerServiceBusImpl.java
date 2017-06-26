package com.tydic.unicom.crawler.business.impl;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.support.SQLExceptionSubclassTranslator;

import com.tydic.unicom.crawler.business.interfaces.CrawlerServiceBus;
import com.tydic.unicom.crawler.business.task.MallDatatoOrderCentre;
import com.tydic.unicom.crawler.business.task.OrderManualAccount;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crawler.common.CrawlerInit;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawler.dao.po.Crawler_SKU_definePo;
import com.tydic.unicom.crawler.service.interfaces.Crawler_SKU_defineServ;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 和爬取业务相关的所有实现
 * @author xx
 *
 */
public class CrawlerServiceBusImpl extends BusBase implements CrawlerServiceBus{

	private static Logger logger = Logger.getLogger(CrawlerServiceBusImpl.class);
	
	@Autowired
	OrderManualAccount ordermanualaccount;
	
	@Autowired
	MallDatatoOrderCentre malldatatoordercentre;
	
	@Autowired
	private Crawler_SKU_defineServ crawler_SKU_defineServ;
	
	BusRespMessage brmsg;
	
	/**
	 * 爬取商城手工审核
	 */
	@Override
	public BusRespMessage crawlerManualAccountMethod(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"crawlerManualAccountMethod", businessResParamVo);
		brmsg = new BusRespMessage();
		HashMap<String, String> map = new HashMap();
		businessResParamVo.setChecklist("user");
		//检查输入
		logdebug(logger, "检查输入！");
		brmsg = checkinput(businessResParamVo);
		if(brmsg!=null){
			endloginfo(logger, this.getClass().getName() + "->[checkinput] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		logdebug(logger, "检查输入成功！");
		
		//检查获取的对象
		Crawler_Mall_UserPo cmu = checkuser(businessResParamVo);
		if(cmu==null){
			brmsg = new BusRespMessage();
			logger.debug("#####未找到对应的用户:"+ businessResParamVo.getUser());
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("未找到对应的用户"+businessResParamVo.getUser());
			endloginfo(logger,this.getClass().getName()+"->[crawlerManualAccountMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		
		if(cmu.getCrawmu_sessioncookie()==null){
			brmsg = new BusRespMessage();
			logger.debug("#####"+businessResParamVo.getUser()+"-登录信息不全请重新登录");
			brmsg.setRespCode("9999");
			brmsg.setContent(businessResParamVo.getUser()+"-登录信息不全请重新登录");
			endloginfo(logger,this.getClass().getName()+"->[crawlerManualAccountMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		
		CrawlDatum datum = new CrawlDatum();
		
		map.put("crawmu_uuid", cmu.getCrawmu_uuid());
		map.put("staffCode", cmu.getCrawmu_name());
		map.put("passWd", cmu.getCrawmu_pwd());
		map.put("sessioncookie", cmu.getCrawmu_sessioncookie());
		datum.setMetaData(map);
		
		ordermanualaccount.exe(datum);
		BusRespMessage crawlerInfomsg = ordermanualaccount.getBrmsg();
		if(crawlerInfomsg.getRespCode().equals("9999")){
			endloginfo(logger,this.getClass().getName()+"->[crawlerManualAccountMethod]爬取数据结束。结束参数:"+crawlerInfomsg.toString());
			return crawlerInfomsg;
		}
		
//		malldatatoordercentre.exe(datum);
//		BusRespMessage mdtrcmsg = malldatatoordercentre.getBrmsg();
//		if(crawlerInfomsg.getArgs().size()>0){
//			for(Entry<String, Object> vo : crawlerInfomsg.getArgs().entrySet()){ 
//	            vo.getKey(); 
//	            vo.getValue(); 
//	            mdtrcmsg.addArg(vo.getKey(), vo.getValue());
//			}
//		}
//		
//		endloginfo(logger,this.getClass().getName()+"->[crawlerManualAccountMethod]写入订单中心结束。结束参数:"+mdtrcmsg.toString());
		return crawlerInfomsg;
	}
	
	
	
	/**
	 * 订单中心创建订单
	 */
	@Override
	public BusRespMessage crawlerManualAccountCreateOrder(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"crawlerManualAccountCreateOrder", businessResParamVo);
		HashMap<String, String> map = new HashMap();
		businessResParamVo.setChecklist("user");
		//检查输入
		logdebug(logger, "检查输入！");
		brmsg = checkinput(businessResParamVo);
		if(brmsg!=null){
			endloginfo(logger, this.getClass().getName() + "->[crawlerManualAccountCreateOrder->checkinput] 结束参数:" + brmsg.toString());
			return brmsg;
		}
		logdebug(logger, "检查输入成功！");
		
		//检查获取的对象
		Crawler_Mall_UserPo cmu = checkuser(businessResParamVo);
		if(cmu==null){
			brmsg = new BusRespMessage();
			logger.debug("#####未找到对应的用户:"+ businessResParamVo.getUser());
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("未找到对应的用户"+businessResParamVo.getUser());
			endloginfo(logger,this.getClass().getName()+"->[crawlerManualAccountMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		
		if(cmu.getCrawmu_sessioncookie()==null){
			brmsg = new BusRespMessage();
			logger.debug("#####"+businessResParamVo.getUser()+"-登录信息不全请重新登录");
			brmsg.setRespCode("9999");
			brmsg.setContent(businessResParamVo.getUser()+"-登录信息不全请重新登录");
			endloginfo(logger,this.getClass().getName()+"->[crawlerManualAccountMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		
		CrawlDatum datum = new CrawlDatum();
		map.put("crawmu_uuid", cmu.getCrawmu_uuid());
		malldatatoordercentre.exe(datum);
		BusRespMessage mdtrcmsg = malldatatoordercentre.getBrmsg();
		endloginfo(logger,this.getClass().getName()+"->[crawlerManualAccountCreateOrder]写入订单中心结束。结束参数:"+mdtrcmsg.toString());
		return mdtrcmsg;
	}
	
	//商品查询 
	
	//商品插入
	
	//商品更新
	
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");

		CrawlerInit crawlerInit = (CrawlerInit) context.getBean("crawlerInit");
		try {
			crawlerInit.init();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		CrawlerServiceBusImpl lm = (CrawlerServiceBusImpl) context.getBean("CrawlerServiceBus");

		BusinessResParamVo bvo = new BusinessResParamVo();
		bvo.setUser("CQCSGZ1");
		bvo.setPassword("ran20070706");
		bvo.setSafecode("215946");
		bvo.setType("1");

		try {
			lm.crawlerManualAccountCreateOrder(bvo);
			// lm.isLogin(bvo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	@Override
	public BusRespMessage createSKU(BusinessResParamVo businessResParamVo) throws Exception {
		
		brmsg = new BusRespMessage();
		try {
			Crawler_SKU_definePo crawler_SKU_definePo = new Crawler_SKU_definePo();
			crawler_SKU_definePo.setCra_sku_name(businessResParamVo.getParmapItem("insertskuname"));
			crawler_SKU_definePo.setSku_id(businessResParamVo.getParmapItem("insertskuid"));
			crawler_SKU_definePo.setCra_sku_stats(businessResParamVo.getParmapItem("insertskustats"));
			crawler_SKU_definePo.setOper_code(businessResParamVo.getParmapItem("insertopercode"));

			crawler_SKU_defineServ.create(crawler_SKU_definePo);
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("创建对照表成功");
		} 
		catch (DataIntegrityViolationException e){
			throw(new Exception("商品名称不能重复【"+businessResParamVo.getParmapItem("insertskuname") +"】"));
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("创建套餐对照表错误"+e.getMessage());
			brmsg.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			brmsg.setContent("创建套餐对照表错误"+e.getMessage());
			throw(new Exception(e.getMessage()));
		}
		return brmsg;
		
	}



	@Override
	public BusRespMessage updateSKU(BusinessResParamVo businessResParamVo) throws Exception {
		brmsg = new BusRespMessage();
		try {
			Crawler_SKU_definePo crawler_SKU_definePo = new Crawler_SKU_definePo();
			crawler_SKU_definePo.setCra_sku_uuid(businessResParamVo.getParmapItem("updateskuuuid"));
			crawler_SKU_definePo.setCra_sku_name(businessResParamVo.getParmapItem("updateskuname"));
			crawler_SKU_definePo.setSku_id(businessResParamVo.getParmapItem("updateskuid"));
			crawler_SKU_definePo.setCra_sku_stats(businessResParamVo.getParmapItem("updateskustats"));
			crawler_SKU_definePo.setOper_code(businessResParamVo.getParmapItem("updateopercode"));
			crawler_SKU_defineServ.update(crawler_SKU_definePo);
			
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("更新对照表成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("更新套餐对照表错误"+e.getMessage());
			brmsg.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			brmsg.setContent("更新套餐对照表错误"+e.getMessage());
			throw(new Exception("更新套餐对照表错误！"));
		}
		return brmsg;
	}



	@Override
	public BusRespMessage selectSKU(BusinessResParamVo businessResParamVo) throws Exception {
		brmsg = new BusRespMessage();
		/** 创建套餐对象  **/
		try {
			Crawler_SKU_definePo crawler_SKU_definePo = new Crawler_SKU_definePo();
			
			crawler_SKU_definePo.setCra_sku_stats(businessResParamVo.getParmapItem("stats"));
			crawler_SKU_definePo.setCra_sku_name(businessResParamVo.getParmapItem("name"));
			crawler_SKU_definePo.setSku_id(businessResParamVo.getParmapItem("id"));
			crawler_SKU_definePo.setOper_code(businessResParamVo.getParmapItem("opercode"));
			List<Crawler_SKU_definePo> l = crawler_SKU_defineServ.query(crawler_SKU_definePo);

			HashMap map = new HashMap();
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("得到套餐对照表成功");
			
			if(l==null){
				brmsg.addArg("list", "[]");
				return brmsg;
			}
			
			JSONArray jsonarr = new JSONArray();
			JSONObject json ;
			for(int i = 0;i< l.size();i++){
				json = new JSONObject();
				json.putAll(l.get(i).convertToMap());
				jsonarr.add(json);
			}
			brmsg.addArg("list", jsonarr);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("得到套餐对照表错误"+e.getMessage());
			brmsg.setRespCode("9999");
			brmsg.setContent("得到套餐对照表错误"+e.getMessage());
			brmsg.addArg("list", "{}");
			throw(new Exception("得到套餐对照表错误！"));
		}
		
		return brmsg;
	}
}
