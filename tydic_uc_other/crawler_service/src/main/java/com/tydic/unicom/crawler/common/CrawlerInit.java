package com.tydic.unicom.crawler.common;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.dao.po.CoderegionPo;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawler.dao.po.Crawler_SKU_definePo;
import com.tydic.unicom.crawler.service.interfaces.CoderegionServ;
import com.tydic.unicom.crawler.service.interfaces.Crawler_Mall_UserServ;
import com.tydic.unicom.crawler.service.interfaces.Crawler_SKU_defineServ;

@Service
public class CrawlerInit {
	
	public static final Logger logger = LoggerFactory.getLogger(CrawlerInit.class);
	@Autowired
	ProParamVo proparamvo;
	
	@Autowired
	CoderegionServ coderegionserv;
	
	@Autowired
	Crawler_SKU_defineServ crawler_sku_defineserv;
	
	@Autowired
	Crawler_Mall_UserServ crawler_mall_userserv;
	
	
	public CrawlerInit() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean init() throws Exception{
		
		if(SysPar.ppvo == null)
			SysPar.ppvo = proparamvo;
		
		
		/** 创建行政区划对象  **/
		CoderegionPo cpo = new CoderegionPo();
		cpo.setRegion_name(SysPar.ppvo.getRegionname());
		try {
			List<CoderegionPo> l = coderegionserv.query(cpo);
			HashMap map = new HashMap();
			for(int i = 0;i< l.size();i++){
				map.put(l.get(i).getRegion_name(), l.get(i).getRegion_code());
//				System.out.println(l.get(i).getRegion_name()+"   " +l.get(i).getRegion_code());
			}
			SysPar.coderegion = map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw(new Exception("得到行政区划错误，程序退出！"));
		}
		if(SysPar.coderegion == null)
			throw(new Exception("行政区划表为空请配置，程序退出！"));
		
		/** 创建套餐对象  **/
		try {
			Crawler_SKU_definePo crawler_SKU_definePo = new Crawler_SKU_definePo();
			crawler_SKU_definePo.setCra_sku_stats("1");
			List<Crawler_SKU_definePo> l = crawler_sku_defineserv.query(crawler_SKU_definePo);
			HashMap map = new HashMap();
			for(int i = 0;i< l.size();i++){
				map.put(l.get(i).getCra_sku_name(), l.get(i));
			}
			SysPar.sukdef = map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw(new Exception("得到套餐对照表错误，程序退出！"));
		}
		if(SysPar.sukdef == null)
			throw(new Exception("套餐对照表为空请配置，程序退出！"));
		
		/** 创建用户列表  **/
		try {
			List<Crawler_Mall_UserPo> l = crawler_mall_userserv.query(null);
			HashMap<String,Crawler_Mall_UserPo> map = new HashMap();
			for(int i = 0;i< l.size();i++){
				map.put(l.get(i).getCrawmu_name(), l.get(i));
				//map.put(l.get(i).getCrawmu_name(), l.get(i).getCrawmu_uuid());
				if(l.get(i).getCrawmu_uacname()!=null && !l.get(i).getCrawmu_uacname().isEmpty())
					map.put(l.get(i).getCrawmu_uacname(), l.get(i));
			}
			SysPar.user = map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw(new Exception("得到用户表错误，程序退出！"));
		}
		if(SysPar.user == null)
			throw(new Exception("得到用户表为空请配置，程序退出！"));
		return true;
	}

	/**
	 * 强制刷新
	 * @return
	 */
	public BusRespMessage reset(){
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
