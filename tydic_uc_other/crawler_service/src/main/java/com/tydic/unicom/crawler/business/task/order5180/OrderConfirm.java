package com.tydic.unicom.crawler.business.task.order5180;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.business.task.BaseTask;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.model.CrawlDatums;
import com.tydic.unicom.crawlerframe.model.Page;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;

@Service
public class OrderConfirm extends BaseTask {
	public static final Logger LOG = LoggerFactory.getLogger(OrderConfirm.class);
	CrawlDatum maincrawldatum = new CrawlDatum();
	ArrayList al;
	
	public int activepage = 1;
	public String maxtime = "";
	
	public OrderConfirm() {
		super();
	}
	@Override
	public void init(CrawlDatum datum) throws Exception {
		super.init(datum);
		url = SysPar.ppvo.getBaselink()+"/Odm/OrderConfirm/query";
	}
	
	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		
		try {
			MallHttpRequest mall = new MallHttpRequest(url);
			mall.setMethod("POST");
//			mall.setCookie(SysPar.getSessionCookie());
			mall.setPar("pageSize", "30");
			mall.setPar("page.webPager.action", ""+activepage);
			mall.setPar("page.webPager.pageInfo.totalSize", "1000");
			mall.setPar("page.webPager.pageInfo.pageSize", "30");
			mall.setPar("page.webPager.currentPage", "1");
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
		Elements els = page.select("[class='allotBtn shortBlueBtn']");
		for(Element el : els){
			al.add(el.attr("orderid"));
		}
	}
	
	public ArrayList getsearchResultlist() {
		return al;
	}

	
	public static void main(String[] args) {
		OrderConfirm or = new OrderConfirm();
//		or.orderid = "9217032822822220";
//		or.orderid = "1117032924120743";
//		or.orderid = "5917032823643036";
		
		try {
//			or.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
