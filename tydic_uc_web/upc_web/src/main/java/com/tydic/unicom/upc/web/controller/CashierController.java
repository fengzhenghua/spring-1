package com.tydic.unicom.upc.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.tools.ResouresUtil;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderGoodsDetailServDu;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderServDu;
import com.tydic.unicom.upc.vo.inst.InfoOrderGoodsDetailVo;
import com.tydic.unicom.upc.vo.inst.InfoOrderVo;
import com.tydic.unicom.upc.web.constants.ControllerMappings;
import com.tydic.unicom.upc.web.constants.Views;
import com.tydic.unicom.webUtil.Message;
import com.tydic.unicom.webUtil.Message.Type;

/**
 * 收银台上页面内容的控制
 * 包括页面的跳转
 * @author dell
 *
 */
@Controller
@RequestMapping(value = ControllerMappings.CASHIER_CONTROLLER)
public class CashierController {
	
	private static Logger logger = Logger.getLogger(CashierController.class);

	@Autowired
	private InfoOrderServDu infoOrderServDu;
	
	@Autowired
	private InfoOrderGoodsDetailServDu infoOrderGoodsDetailServDu;
	
	@RequestMapping(value = "wxScanPay" , method = RequestMethod.GET)
	public String wxScanPay(Model model, String id, String key, String pay_type, String req_way){
		String errorMsg = "";
		try{
			if(validateKey(id, key)){
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				
				model.addAttribute("info_order", infoOrderVo);
				model.addAttribute("pay_type", pay_type);
			}
			else{
				errorMsg = "订单数据非法，请重新发起支付请求。";
			}
			
			if(errorMsg.equals("")){
				if("APP".equalsIgnoreCase(req_way)){
					return Views.UPC_APP_WX_SCAN_PAY;
				}else{
					return Views.UPC_BUSI_WX_SCAN_PAY;
				}
			}
			else{
				model.addAttribute("errorMsg", errorMsg);
				return "";
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		model.addAttribute("errorMsg", errorMsg);
		return "";
	
	}
	
	@RequestMapping(value = "wxQrPay" , method = RequestMethod.GET)
	public String wxQrPay(Model model, String id, String key, String pay_type ,String req_way){
		String errorMsg = "";
		try{
			if(validateKey(id, key)){
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				
				model.addAttribute("info_order", infoOrderVo);
				model.addAttribute("pay_type", pay_type);
			}
			else{
				errorMsg = "订单数据非法，请重新发起支付请求。";
			}
			
			if(errorMsg.equals("")){
				if("APP".equalsIgnoreCase(req_way)){
				
					return Views.UPC_APP_WX_QR_PAY;
				}else{
					return Views.UPC_BUSI_WX_QR_PAY;
				}
			}
			else{
				model.addAttribute("errorMsg", errorMsg);
				return "";
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		model.addAttribute("errorMsg", errorMsg);
		return "";
	
	}
	
	@RequestMapping(value = "aliQrPay" , method = RequestMethod.GET)
	public String aliQrPay(Model model, String id, String key, String pay_type , String req_way){
		String errorMsg = "";
		try{
			if(validateKey(id, key)){
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				
				model.addAttribute("info_order", infoOrderVo);
				model.addAttribute("pay_type", pay_type);
			}
			else{
				errorMsg = "订单数据非法，请重新发起支付请求。";
			}
			
			if(errorMsg.equals("")){
				if("APP".equalsIgnoreCase(req_way)){
					
					return Views.UPC_APP_ALI_QR_PAY;
				}else{
					
					return Views.UPC_BUSI_ALI_QR_PAY;
				}
			}
			else{
				model.addAttribute("errorMsg", errorMsg);
				return "";
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		model.addAttribute("errorMsg", errorMsg);
		return "";
	
	}
	
	@RequestMapping(value = "aliScanPay" , method = RequestMethod.GET)
	public String aliScanPay(Model model, String id, String key, String pay_type, String req_way){
		String errorMsg = "";
		try{
			if(validateKey(id, key)){
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				
				model.addAttribute("info_order", infoOrderVo);
				model.addAttribute("pay_type", pay_type);
			}
			else{
				errorMsg = "订单数据非法，请重新发起支付请求。";
			}
			
			if(errorMsg.equals("")){
				if("APP".equalsIgnoreCase(req_way)){
					return Views.UPC_APP_ALI_SCAN_PAY;
				}else{
					return Views.UPC_BUSI_ALI_SCAN_PAY;
				}
			}
			else{
				model.addAttribute("errorMsg", errorMsg);
				return "";
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		model.addAttribute("errorMsg", errorMsg);
		return "";
	
	}
	
	@RequestMapping(value = "queryGoodsDetail" , method = RequestMethod.POST)
	@ResponseBody
	public Message queryGoodsDetail(String id, String key){
		Message message = new Message();
		try{
			if(validateKey(id, key)){
				
				InfoOrderGoodsDetailVo goodsDetail = new InfoOrderGoodsDetailVo();
				goodsDetail.setOrder_id(id);
				
				List<InfoOrderGoodsDetailVo> goodsDetailList = infoOrderGoodsDetailServDu.queryGoodsDetailByOrderId(goodsDetail);
				
				message.addArg("list", goodsDetailList);
				message.setType(Type.success);
				return message;
			}
			else{
				message.setType(Type.error);
				message.setContent("订单数据非法，请重新发起支付请求。");
				return message;
			}
		}catch(Exception e){
			logger.error("支付明细查询出现异常!"+e.getMessage(), e);
			message.setType(Type.error);
			message.setContent("支付明细查询出现异常!"+e.getMessage());
			return message;
		}
		
	}
	
	/**
	 * 校验订单号和key
	 * @param id
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private boolean validateKey(String id, String key) throws Exception{
		String redisOrderId = ResouresUtil.getPutKey(key);
		return id.equals(redisOrderId);
	}
}
