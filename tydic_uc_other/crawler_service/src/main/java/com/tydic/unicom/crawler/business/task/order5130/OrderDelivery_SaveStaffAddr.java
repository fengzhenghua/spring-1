package com.tydic.unicom.crawler.business.task.order5130;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.business.task.BaseTask;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;

/**
 * 订单管理>发货处理 - 发货/退货信息 修改
 * @author xx
 */
@Service()
public class OrderDelivery_SaveStaffAddr extends BaseTask{
	public static final Logger LOG = LoggerFactory.getLogger(OrderDelivery_SaveStaffAddr.class);
	public OrderDelivery_SaveStaffAddr() {
		super();
		
	}
	
	@Override
	public void init(CrawlDatum datum) throws Exception {
		super.init(datum);
		url = SysPar.ppvo.getBaselink()+"/Odm/OrderDelivery/saveStaffAddr";
	}
	
	
	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		
		try {
			MallHttpRequest mall = new MallHttpRequest(url);
			mall.setMethod("POST");
			
			mall.setHeader("Referer", SysPar.ppvo.getBaselink()+"/Odm/OrderDelivery/toDetail");
			mall.setHeader("Origin",SysPar.ppvo.getBaselink());
			mall.setHeader("X-Requested-With","XMLHttpRequest");
			mall.setHeader("Accept","application/json, text/javascript");
			
			mall.setPar("addrType", "A");
			mall.setPar("provinceCode", "500000");
			mall.setPar("cityCode", "500100");
			mall.setPar("districtCode", "500107");
			mall.setPar("address", "测试");
			mall.setPar("contact", "测试21212121212");
			mall.setPar("telphone", "13212312311");
			
			mall.setCookie(crawlDatum.meta("sessioncookie"));
			MallHttpResponse mhrs = mall.response();
			
			if(mhrs.getCode() != 200){
				String f = new String(mhrs.getContent(),"UTF-8");
				LOG.debug("回写总部商城[订单管理>发货处理 发货/退货信息 修改]失败，请确认数据是否正确或是接口已经改变");
			}
			return mhrs;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
