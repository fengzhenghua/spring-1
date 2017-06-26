package com.tydic.unicom.crawler.business.task.order5270;

import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tydic.unicom.crawler.business.task.BaseTask;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.model.CrawlDatums;
import com.tydic.unicom.crawlerframe.model.Page;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;

/**
 * 订单领取 查询列表
 * @author xx
 */
@Component()
public class OrderClaim extends BaseTask {
	public static final Logger LOG = LoggerFactory.getLogger(OrderClaim.class);
	CrawlDatum maincrawldatum = new CrawlDatum();
	ArrayList al;
	
	public int activepage = 1;
	public String maxtime = "";
	
	public OrderClaim() {
		super();
	}
	@Override
	public void init(CrawlDatum datum) throws Exception {
		super.init(datum);
		url = SysPar.ppvo.getBaselink()+"/Odm/OrderClaim/queryOrderForClaim";
	}
	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		try {
			MallHttpRequest mall = new MallHttpRequest(url);
			mall.setMethod("POST");
//			mall.setCookie(SysPar.getSessionCookie());
			mall.setPar("claimType", "01");
			mall.setPar("maxTime", SysUtil.getDateYMD());
			mall.setPar("pageSize", "30");
			mall.setPar("page.webPager.action", ""+activepage);
			mall.setPar("page.webPager.pageInfo.totalSize", "1000");
			mall.setPar("page.webPager.pageInfo.pageSize", "30");
			mall.setPar("page.webPager.currentPage", "1");
			//**配送方式：	全选快递
			mall.setPar("dlvType", "01");
			
//			LOG.info("================================="+mall.nvps.toArray().toString() + activepage);
			MallHttpResponse mhrs = mall.response();
			return mhrs;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// TODO Auto-generated method stub
//		String a = page.select("*").html();
//		System.out.println(page.content().length);
//		System.out.println(page.select("*").html().length());
		al = new ArrayList();
		int i =0;
		Elements els = page.select(".orderDetailLink");
		for(Element el : els){
			al.add(el.attr("orderid"));
		}
	}

	public ArrayList getsearchResultlist() {
		return al;
	}

	
	public static void main(String[] args) {
		OrderClaim or = new OrderClaim();
		/*or.orderid = "9217032822822220";or.orderid = "1117032924120743";or.orderid = "5917032823643036";*/
		
		try {
//			or.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
