package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.sale.interfaces.InfoSaleOrderBusinessServDu;
import com.tydic.unicom.uoc.business.order.sale.interfaces.PreOrderServDu;
import com.tydic.unicom.uoc.business.order.sale.interfaces.QuerySalesOrderListServDu;
import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDetailServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.SALE_ORDER_REST)
public class SaleOrderRest {

	Logger logger = Logger.getLogger(SaleOrderRest.class);

	@Autowired
	private InfoSaleOrderBusinessServDu infoSaleOrderBusinessServDu;
	@Autowired
	private OrderDetailServDu orderDetailServDu;
	@Autowired
	private PreOrderServDu preOrderServDu;

	@Autowired
	private QuerySalesOrderListServDu querySalesOrderListServDu;

	/**
	 * 销售订单创建 UOC0003
	 * 
	 * @param jsession_id
	 *            String Y order_type String Y accept_no String N flow_flag
	 *            String N accept_type String N accept_system String Y
	 *            accept_time String N province_code String N area_code String N
	 *            pay_flag String N pay_type String N express_flag String N
	 *            json_info String Y
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.CREATE_SALE_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createSaleOrder(ParaVo paraVo) {
		logger.info("rest-----------createSaleOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.createSaleOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("创建订单异常");
			return message;
		}
		if ("0000".equals(message.getRespCode())) {
			logger.info("sale_order_no-----------" + message.getArgs().get("sale_order_no"));
		} else {
			logger.info(message.getContent());
		}

		return message;

	}

	/**
	 * 销售订单修改UOC0004
	 * 
	 * @param jsession_id
	 *            String Y 登陆验证串 sale_order_no String N 销售订单号 json_info String Y
	 *            Json格式数据串，用于传入订单数据 update_type String N 更新类型：0或空全量更新、1增量更新
	 *            flow_type String Y 流程流转类型:0自动流转、1按操作编码流转、2不流转 action_code
	 *            String N 操作编码,流转类型为1时必填 pay_flag String N 是否需要支付 pay_type
	 *            String N 支付类型 express_flag String N 是否需要配送 edit_time String N
	 *            修订时间 edit_desc String N 修订描述 edit_system String N 修订系统
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.UPDATE_SALE_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage updateSaleOrder(ParaVo paraVo) {
		logger.info("rest-----------updateSaleOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.updateSaleOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("修改订单异常");
			return message;
		}
		return message;
	}

	/**
	 * 销售订单确认UOC0005
	 * 
	 * @param jsession_id
	 *            String Y 登陆验证串 sale_order_no String Y 销售订单号
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.CONFIRM_SALE_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage confirmSaleOrder(ParaVo paraVo) {
		logger.info("rest-----------confirmSaleOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.confirmSaleOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单确认异常");
			return message;
		}
		return message;
	}

	/**
	 * 销售订单提交UOC0019
	 * 
	 * @param all_json_info
	 *            String N 全部信息JSON jsession_id String Y 登陆验证串 sale_order_no
	 *            String Y 销售订单号 manual_flag String Y 人工操作标志：0人工，1非人工
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.SUBMIT_SALE_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage submitSaleOrder(ParaVo paraVo) {
		logger.info("rest-----------submitSaleOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.submitSaleOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单提交异常");
			return message;
		}
		return message;
	}

	/**
	 * 销售订单处理UOC0020
	 * 
	 * @param all_json_info
	 *            String N 全部信息JSON jsession_id String Y 登陆验证串 sale_order_no
	 *            String Y 销售订单号 flow_type String Y
	 *            流程流转类型:0自动流转、1按操作编码流转、2不流转、3仅流转流程不做其他处理 action_code String N
	 *            操作编码,流转类型为1时必填 manual_flag String Y 人工操作标志：0人工，1非人工
	 * 
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.DEAL_SALE_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage dealSaleOrder(ParaVo paraVo) {
		logger.info("rest-----------dealSaleOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.dealSaleOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单处理异常");
			return message;
		}
		return message;
	}

	/**
	 * 销售订批量修改信息UOC0021
	 * 
	 * @param jsession_id
	 *            String Y 登陆验证串 sale_order_no String Y 销售订单号 serv_order_no_list
	 *            List Y 服务订单号List serv_order_no String Y 服务订单号 json_info_list
	 *            List Y Json费用信息串List json_info String Y 传入Json费用信息串
	 *            manual_flag String Y 人工操作标志：0人工，1非人工
	 * 
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.UPDATE_SALE_ORDER_BATCH, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage updateSaleOrderBatch(ParaVo paraVo) {
		logger.info("rest-----------updateSaleOrderBatch");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.updateSaleOrderBatch(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单批量修改异常");
			return message;
		}
		return message;
	}

	/**
	 * 生成支付订单UOC0022
	 * 
	 * @param all_json_info
	 *            String N 全部信息JSON jsession_id String Y 登陆验证串 sale_order_no
	 *            String Y 销售订单号
	 * 
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.CREATE_INFO_PAY_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createInfoPayOrder(ParaVo paraVo) {
		logger.info("rest-----------createInfoPayOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.createInfoPayOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("生成支付订单异常");
			return message;
		}
		return message;
	}

	/**
	 * 销售订单收费结果通知 UOC0023
	 * 
	 * @param jsession_id
	 *            String Y 登陆验证串 sale_order_no String Y 销售订单号 real_pay_sn String
	 *            Y 支付流水 pay_type String Y 支付方式 pay_time String N 支付时间
	 *            pay_system_code String N 支付系统编码
	 * 
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.PAY_FOR_NOTIFY_INFO_SALE_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage payForNotifyInfoSaleOrder(ParaVo paraVo) {
		logger.info("rest-----------payForNotifyInfoSaleOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.payForNotifyInfoSaleOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单收费结果通知异常");
			return message;
		}
		return message;
	}

	/**
	 * 订单撤单 UOC0007
	 * 
	 * @param jsession_id
	 *            String Y 登陆验证串 order_no String Y 订单号 query_type String Y
	 *            100销售订单、101服务订单、102支付订单、103交付订单、105商品订单
	 * 
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.CANCEL_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage cancelOrder(ParaVo paraVo) {
		logger.info("rest-----------cancelOrder");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.cancelOrder(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("订单撤单异常");
			return message;
		}
		return message;
	}

	/**
	 * ⦁ 预订单流程启动 UOC0025
	 * 
	 * @param jsession_id
	 *            String Y 登陆验证串 sale_order_no String Y 销售订单号
	 * 
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.ADVANCE_ORDER_START, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage advanceOrderStart(ParaVo paraVo) {
		logger.info("rest-----------advanceOrderStart");
		UocMessage message = new UocMessage();
		try {
			message = preOrderServDu.preOrderStart(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("订单流程启动异常");
			return message;
		}
		return message;
	}

	/**
	 * 订单详情查询 UOC0010
	 * 
	 * @param paraVo
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.GET_ORDER_DETAIL, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrderDetail(ParaVo paraVo) throws Exception {
		UocMessage message = new UocMessage();
		try {
			logger.info("rest-----------getOrderDetail");
			message = orderDetailServDu.getOrderDetail(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单列表查询异常");
			return message;
		}
		return message;
	}

	/**
	 * 销售订单列表查询 UOC0006
	 * 
	 * @param paraVo
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.QUERY_SALE_ORDER_LIST, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage querySalesOrderList(SaleOrderInVo vo) {
		logger.info("rest-----------querySalesOrderList");
		UocMessage message = new UocMessage();
		try {
			message = querySalesOrderListServDu.SalesOrderList(vo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单列表查询异常");
			return message;
		}
		return message;
	}

	/**
	 * 服务订单列表查询 UOC0035
	 * 
	 * @param paraVo
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.QUERY_SERV_ORDER_LIST, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage queryServOrderList(SaleOrderInVo vo) {
		logger.info("rest-----------queryServOrderList");
		UocMessage message = new UocMessage();
		try {
			message = querySalesOrderListServDu.ServOrderList(vo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("服务订单列表查询异常");
			return message;
		}
		return message;
	}

	/**
	 * 订单数据备份 UOC0069 (1000条数据备份)
	 * 
	 * @param paraVo
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.CREATE_ORDER_BACKUP, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createOrderBackUp(String total_num, String remainder) {
		logger.info("rest-----------createOrderBackUp");
		UocMessage message = new UocMessage();
		try {
			message = infoSaleOrderBusinessServDu.createOrderBackUp(total_num, remainder);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("订单数据备份异常");
			return message;
		}
		return message;
	}
}
