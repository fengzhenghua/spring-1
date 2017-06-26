package com.tydic.unicom.crawler.business.task;

import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.service.impl.AbilityPlatformServDuImpl;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoServ;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoCallbackServ;

import net.sf.json.JSONObject;

/**
 * 把得到的爬取数据放入到订单中心中
 * 
 * @author xx
 *
 */
@Service()
public class MallDatatoOrderCentre extends BaseTask {
	public static final Logger logger = LoggerFactory.getLogger(MallDatatoOrderCentre.class);

	@Autowired
	ProParamVo ppvo;

	@Autowired
	Crawler_InfoServ crawler_infoserv;
	@Autowired
	Crawler_InfoCallbackServ crawler_infoservcallback;

	@Autowired
	AbilityPlatformServDuImpl aps;

	// 流程状态
	String flow;

	public MallDatatoOrderCentre() {

	}

	/**
	 * 这里调用能力平台，创建订单然后持久化。 </br>
	 * 备注： Crawler_InfoPo类中的状态 02失败，这个状态不使用，方便SQL查询。所以界面只能看见每次操作的结果。刷新就无数据。
	 * @param datum
	 */
	public void exe(CrawlDatum datum) {
		Crawler_InfoPo cpp = new Crawler_InfoPo();
		cpp.setOrder_status("0");
		cpp.setOrder_malltype("4");
		cpp.setCrawmu_uuid(datum.meta("crawmu_uuid"));
		int cratouoccount = 0;
		int cratouoccountok = 0;
		int cratouoccountno = 0;
		JSONObject listjson =JSONObject.fromObject("{}");
		JSONArray arrayjson = new JSONArray();
		brmsg = new BusRespMessage();
		try {
			List<Crawler_InfoPo> list = crawler_infoserv.query(cpp);
			
			
			//有可能爬虫接口爬取了很多借口，因为数据库中已经存在商城的订单ID ，所以就不需要在中台创建
			if (list == null) {
				LOG.debug("#####未找到手动开户信息");
				brmsg.setRespCode(BusRespMessage.SUCCESS);
				brmsg.setContent("完成订单创建。");
				brmsg.addArg("cratouoccount", "0");
				brmsg.addArg("cratouoccountok", "0");
				brmsg.addArg("cratouoccountno", "0");
				brmsg.addArg("listjson", "{}");
				return;
			}
			cratouoccount = list.size();
			for (Crawler_InfoPo crainfo : list) {
				LOG.debug("装备数据，开始创建订单： "+crainfo.convertToMap());
				JSONObject listitem = new JSONObject();
				JSONObject sourceinfo = JSONObject.fromObject(crainfo.getOrder_sourceinfo());
				JSONObject orderinfo = JSONObject.fromObject(crainfo.getOrder_info());
				listitem.put("ciuuid", crainfo.getCi_uuid());
				listitem.put("orderid", crainfo.getOrder_id());
				listitem.put("product", SysUtil.jsonfindall(sourceinfo.toString(), "context.1.套餐"));
				listitem.put("serial_number", SysUtil.jsonfindall(orderinfo.toString(), "params.json_info.serial_number"));
				listitem.put("customer_name", SysUtil.jsonfindall(orderinfo.toString(), "params.json_info.customer_name"));
				try {
					BusRespMessage uocmsg = aps.CallAbilityPlatform(crainfo.getOrder_info());
					// uocmsg.getRespCode();
					if (uocmsg.getRespCode().equals(BusRespMessage.SUCCESS)) {
						JSONObject json = JSONObject.fromObject(uocmsg.getArgs().get("json_info"));
						// 如果返回成功，就把JSON 里边的数据填入数据库中
						String sale_order_no = SysUtil.getStr(json.get("sale_order_no"));
						
						//如果服务订单为空，说明已经创建过，不需要在创建，数据必然有问题。
						if(json.get("serv_order_no_list")==null){
							logger.debug("失败"+"订单已经创建，销售订单号"+sale_order_no);
							listitem.put("stateimgs", "0.png");
							listitem.put("saleorderno", sale_order_no);
							listitem.put("servorderno", crainfo.getServ_order_no_list());
							listitem.put("statecon", "失败"+"订单已经创建，销售订单号"+sale_order_no);
							arrayjson.put(listitem);
							logger.debug(arrayjson.toString());
							cratouoccountno++;
							continue;
						}
						String serv_order_no_list = SysUtil.getStr(json.getJSONArray("serv_order_no_list").get(0));
						logger.debug("#####创建订单成功 sale_order_no "+ sale_order_no);
						crainfo.setSale_order_no(sale_order_no);
						crainfo.setServ_order_no_list(serv_order_no_list);
						crainfo.setOrder_status(Crawler_InfoPo.CREATORDEROK);
						crawler_infoserv.update(crainfo);
						listitem.put("stateimgs", "1.png");
						listitem.put("saleorderno", sale_order_no);
						listitem.put("servorderno", serv_order_no_list);
						listitem.put("state", "创建订单成功");
						arrayjson.put(listitem);
						cratouoccountok++;
					}else{
						 logger.debug("#####创建订单失败 "+uocmsg.getContent());
						 listitem.put("stateimgs", "0.png");
						 listitem.put("state", "创建订单失败，请确认服务器网络连接！");
						 arrayjson.put(listitem);
						 cratouoccountno++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					listitem.put("stateimgs", "0.png");
					listitem.put("state", "失败:"+e.getMessage());
					arrayjson.put(listitem);
					cratouoccountno++;
				}
			}
			brmsg.setRespCode("0000");
			brmsg.setContent("创建订单完成！");
			brmsg.addArg("cratouoccount", cratouoccount);
			brmsg.addArg("cratouoccountok", cratouoccountok);
			brmsg.addArg("cratouoccountno", cratouoccountno);
			listjson.element("data",arrayjson.toString());
			brmsg.addArg("listjson", listjson.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
			brmsg.setRespCode("9999");
			brmsg.setContent("系统错误,停止订单创建:" + e1.getMessage());
			brmsg.addArg("cratouoccount", cratouoccount);
			brmsg.addArg("cratouoccountok", cratouoccountok);
			brmsg.addArg("cratouoccountno", cratouoccountno);
			listjson.element("data",arrayjson.toString());
			brmsg.addArg("listjson", listjson.toString());
		}
	}
}
