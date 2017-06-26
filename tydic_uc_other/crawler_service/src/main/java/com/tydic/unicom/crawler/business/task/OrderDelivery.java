package com.tydic.unicom.crawler.business.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;

/**
 * 回调总部商城 【订单管理>发货处理 - 发货/退货信息 修改】</br>
 * @author xx
 */
public class OrderDelivery extends BaseTask{
	public static final Logger logger = LoggerFactory.getLogger(OrderDelivery.class);

	@Autowired
	ProParamVo ppvo;

	/**
	 * 这里调用能力平台，创建订单然后持久化。 </br>
	 * 备注： Crawler_InfoPo类中的状态 02失败，这个状态不使用，方便SQL查询。所以界面只能看见每次操作的结果。刷新就无数据。
	 * @param datum
	 */
	public void exe(CrawlDatum datum) {
		
		
		
		
		
	}
	
}
