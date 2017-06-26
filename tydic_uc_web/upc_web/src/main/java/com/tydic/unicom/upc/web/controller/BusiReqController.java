package com.tydic.unicom.upc.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.tools.ResouresUtil;
import com.tydic.unicom.upc.service.activemq.interfaces.CustNotifyActivemqServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiPayRelationServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoPayParaAttrServDu;
import com.tydic.unicom.upc.service.code.interfaces.SystemBusiParaServDu;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderGoodsDetailServDu;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderServDu;
import com.tydic.unicom.upc.service.inst.interfaces.PaySettleTransDu;
import com.tydic.unicom.upc.service.pay.interfaces.AliPayServDu;
import com.tydic.unicom.upc.service.pay.interfaces.WXPayServDu;
import com.tydic.unicom.upc.vo.code.InfoBusiPayRelationVo;
import com.tydic.unicom.upc.vo.code.InfoBusiVo;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrValueVo;
import com.tydic.unicom.upc.vo.inst.InfoOrderGoodsDetailVo;
import com.tydic.unicom.upc.vo.inst.InfoOrderVo;
import com.tydic.unicom.upc.vo.inst.PaySettleTransVo;
import com.tydic.unicom.upc.web.constants.ControllerMappings;
import com.tydic.unicom.upc.web.constants.Views;
import com.tydic.unicom.upc.web.vo.BusiReqDataVo;
import com.tydic.unicom.util.RsaEncodeUtil;
import com.tydic.unicom.util.UpcEncodeUtilForJson;
import com.tydic.unicom.webUtil.Message;
import com.tydic.unicom.webUtil.UocMessage;
import com.tydic.unicom.webUtil.Message.Type;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ControllerMappings.BUSI_REQ_CONTROLLER)
public class BusiReqController {

	private static Logger logger = Logger.getLogger(BusiReqController.class);
	
	@Autowired
	private InfoBusiServDu infoBusiServDu;
	
	@Autowired
	private InfoOrderServDu infoOrderServDu;
	
	@Autowired
	private InfoPayParaAttrServDu infoPayParaAttrServDu;
	
	@Autowired
	private WXPayServDu wxPayService;
	
	@Autowired
	private AliPayServDu aliPayService;
	
	@Autowired
	private InfoBusiPayRelationServDu infoBusiPayRelationServDu;
	
	@Autowired
	private CustNotifyActivemqServDu custNotifyActivemqServDu;
	
	@Autowired
	private SystemBusiParaServDu systemBusiParaServDu;
	
	@Autowired
	private InfoOrderGoodsDetailServDu infoOrderGoodsDetailServDu;
	
	@Autowired
	private PaySettleTransDu paySettleTransDu;
	
	@RequestMapping(value = "pay" , method = RequestMethod.GET)
	public String pay(Model model, String id, String key , String req_way){
		
		String errorMsg = "";
		try{
			String sorder = ResouresUtil.getPutKey(key);
			if(id.equals(sorder)){
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				
				model.addAttribute("info_order", infoOrderVo);
				
				
				//获取支付方式权限
				List<InfoBusiPayRelationVo> payTypeList = infoBusiPayRelationServDu.queryByBusiId(infoOrderVo.getBusi_id());
				model.addAttribute("payTypeList", payTypeList);
			}
			else{
				errorMsg = "参数配置异常，请重新发起支付";
			}
			
			if(errorMsg.equals("")){
				if("APP".equalsIgnoreCase(req_way)){
					return Views.UPC_APP_PAY_CHOICE;
				}else{
					
					return Views.UPC_BUSI_PAY_CHOICE;
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
	
	@RequestMapping(value = "payrdr" , method = RequestMethod.GET)
	public String payrdr(HttpServletRequest request, String data,Model model, String req_way){
		
		String errorMsg = "";
		try{
			/*Message message = createPayOrder(request, data);
			if(message.getType().equals(Type.success)){
				
				String id = message.getArgs().get("id").toString();
				String key = message.getArgs().get("key").toString();
				
				model.addAttribute("id", id);
				model.addAttribute("key", key);
				model.addAttribute("req_way", req_way);
				
				return Views.UPC_BUSI_CASHIER_REDIRECT;
			}*/
			String msg = createPayOrder(request, data);
			JSONObject message = JSONObject.fromObject(msg);
			if("success".equalsIgnoreCase(message.getString("type"))){
				JSONObject args = message.getJSONObject("args");
				String id = args.getString("id");
				String key = args.getString("key");
				model.addAttribute("id", id);
				model.addAttribute("key", key);
				model.addAttribute("req_way", req_way);
				
				return Views.UPC_BUSI_CASHIER_REDIRECT;
			}
			else{
				errorMsg = message.getString("content");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			errorMsg = e.getMessage();
		}
		
		model.addAttribute("errorMsg", errorMsg);
		return "";
	}
	
	/**
	 * 创建支付订单  由支付中心 创建
	 * @param request
	 * @param model
	 * @param xmldata
	 * @return
	 */
	@RequestMapping(value = "createPayOrder" , method = RequestMethod.POST)
	@ResponseBody
	public String createPayOrder(HttpServletRequest request, String data){
		Message message = new Message();  // new String(data.getBytes("iso-8859-1"),"utf-8")
		String errorMsg = "";
		try{
			BusiReqDataVo busiReqDataVo = validReqData(data);
			if(busiReqDataVo.isSign()){
				
				String busi_id = busiReqDataVo.getBusiId();
				Map<String, Object> contentMap = busiReqDataVo.getContentMap();
				
				//必传参数校验
				String order_type = ""+contentMap.get("order_type");
				String req_way = ""+contentMap.get("req_way");
				String out_order_id = ""+contentMap.get("out_order_id");
				String real_fee = ""+contentMap.get("real_fee");
				String detail_name = ""+contentMap.get("detail_name");
				String redirect_url = ""+contentMap.get("redirect_url");
				String remark = ""+contentMap.get("remark");
				
				String goods_detail = ""+contentMap.get("goods_detail");
				
				
				if(!"01".equals(order_type)){
					errorMsg = "order_type数据不正确";
				}
				else if(!"APP".equals(req_way) && !"PC".equals(req_way)){
					errorMsg = "req_way数据不正确";
				}
				else if(isStringEmpty(out_order_id)){
					errorMsg = "out_order_id数据不正确";
				}
				else if(isStringEmpty(real_fee)){
					errorMsg = "real_fee数据不正确";
				}
				else if(isStringEmpty(detail_name)){
					errorMsg = "detail_name数据不正确";
				}
				
				float real_fee_value = 0;
				try{
					real_fee_value = Float.parseFloat(real_fee);
				}catch(Exception ee){
					errorMsg = "real_fee数据不正确";
				}
				
				logger.info("errorMsg = "+errorMsg);
				if(errorMsg.equals("")){
					//校验是否已经存在支付订单
					InfoOrderVo infoOrderVo = new InfoOrderVo();
					infoOrderVo.setBusi_id(busi_id);
					infoOrderVo.setOut_order_id(out_order_id);
					infoOrderVo.setOrder_type(order_type);
					infoOrderVo = infoOrderServDu.queryInfoOrderByOutOrderId(infoOrderVo);
					
					if(infoOrderVo != null && !infoOrderVo.getOrder_status().equals("A00")){
						errorMsg = "此订单已经进行过支付请求，不可重复发起";
					}
					else{
						//新建订单
						InfoOrderVo vo = new InfoOrderVo();
						vo.setBusi_id(busi_id);
						vo.setOrder_type(order_type);
						vo.setOrder_status("A00");
						vo.setCreate_time(new Date());
						vo.setOut_order_id(out_order_id);
						vo.setCreate_ip_address(getIpAddr(request));
						vo.setTotal_fee(new BigDecimal(real_fee_value));
						vo.setDiscount_fee(new BigDecimal(0));
						vo.setReal_fee(new BigDecimal(real_fee_value));
						vo.setReq_way(req_way);
						vo.setDetail_name(detail_name);
						vo.setOut_remark(remark);
						String order_id = infoOrderServDu.addInfoOrder(vo);
						vo.setOrder_id(order_id);
						
						
						//创建支付明细数据
						if(!isStringEmpty(goods_detail)){
							try{
								JSONArray jsonArray = JSONArray.fromObject(goods_detail);
								List<InfoOrderGoodsDetailVo> goodsDetaiList = new ArrayList<>();
								for(int i=0; i<jsonArray.size(); i++){
									JSONObject json = jsonArray.getJSONObject(i);
									InfoOrderGoodsDetailVo goodsDetail = new InfoOrderGoodsDetailVo();
									goodsDetail.setBusi_id(busi_id);
									goodsDetail.setOrder_id(order_id);
									goodsDetail.setGoods_name(json.getString("goods_name"));
									goodsDetail.setGoods_id(json.getString("goods_id"));
									goodsDetail.setGoods_num(json.getInt("goods_num"));
									goodsDetail.setGoods_price(new BigDecimal(json.getString("goods_price")));
									
									goodsDetaiList.add(goodsDetail);
								}
								
								infoOrderGoodsDetailServDu.addInfoOrderGoodsDetail(goodsDetaiList);
								
							}catch(Exception e){
								errorMsg = "支付明细列表数据格式不正确";
								logger.error("支付明细列表数据格式不正确"+e.getMessage(), e);
							}
						}
						
						if(errorMsg.equals("")){
							String order_key = UUID.randomUUID().toString().replace("-", "").toUpperCase();
							
							ResouresUtil.putPayKey(order_key, order_id);
							
							message.addArg("id", order_id);
							message.addArg("key", order_key);
						}
					}
				}
			}
			else{
				errorMsg = "签名校验失败";
			}
			
			if(errorMsg.equals("")){
				message.setType(Type.success);
			}
			else{
				message.setType(Type.error);
				message.setContent(errorMsg);
			}
			
			return RsaEncodeUtil.getJsonFromMessage(message);
			
		}catch(Exception e){
			logger.error("创建支付订单数据异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			message.setContent("创建支付订单数据异常!"+e.getMessage());
			return RsaEncodeUtil.getJsonFromMessage(message);
		}
		
	}
	
	
	@RequestMapping(value = "refund" , method = RequestMethod.POST)
	@ResponseBody
	public String refund(HttpServletRequest request, String data){
		Message message = new Message();
		String errorMsg = "";
		String refund_time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 退款时间默认为当前时间
		String order_id = ""; // 当前创建的订单号 需要返回给业务系统
		String busi_id =""; 
		String result = ""; // 返回信息
		try{
			BusiReqDataVo busiReqDataVo = validReqData(data);
			if(busiReqDataVo.isSign()){
				
				 busi_id = busiReqDataVo.getBusiId();
				Map<String, Object> contentMap = busiReqDataVo.getContentMap();
				
				//必传参数校验
				String order_type = ""+contentMap.get("order_type");
				String req_way = ""+contentMap.get("req_way");
				String out_order_id = ""+contentMap.get("out_order_id");
				String real_fee = ""+contentMap.get("real_fee");
				String out_refund_id = ""+contentMap.get("out_refund_id");
				String total_fee = ""+contentMap.get("total_fee");
				String remark = ""+contentMap.get("remark");
				
				if(!"02".equals(order_type)){
					errorMsg = "order_type数据不正确";
				}
				else if(!"APP".equals(req_way) && !"PC".equals(req_way)){
					errorMsg = "req_way数据不正确";
				}
				else if(isStringEmpty(out_order_id)){
					errorMsg = "out_order_id数据不正确";
				}
				else if(isStringEmpty(real_fee)){
					errorMsg = "real_fee数据不正确";
				}
				else if(isStringEmpty(total_fee)){
					errorMsg = "total_fee数据不正确";
				}
				else if(isStringEmpty(out_refund_id)){
					errorMsg = "out_refund_id数据不正确";
				}
				
				float real_fee_value = 0;
				float total_fee_value = 0;
				try{
					real_fee_value = Float.parseFloat(real_fee);
					total_fee_value = Float.parseFloat(total_fee);
					
				}catch(Exception ee){
					errorMsg = "金额数据格式不正确";
				}
				
				if(real_fee_value > total_fee_value){
					errorMsg = "退款金额不允许大于原请求金额";
				}
				
				logger.info("errorMsg = "+errorMsg);
				if(errorMsg.equals("")){
					//校验是否已经存在支付订单
					InfoOrderVo infoOrderVo = new InfoOrderVo();
					infoOrderVo.setBusi_id(busi_id);
					infoOrderVo.setOut_order_id(out_refund_id);
					infoOrderVo.setOrder_type("01");
					infoOrderVo = infoOrderServDu.queryInfoOrderByOutOrderId(infoOrderVo);
					
					if(infoOrderVo == null || !infoOrderVo.getOrder_status().equals("A10")){
						errorMsg = "未找到此订单的支付成功数据!";
					}
					else{
						
						//根据原支付类型， 发起支付退款
						String pay_type = infoOrderVo.getPay_type();
						
						//判断原总金额与传入金额比较
						if(infoOrderVo.getReal_fee().floatValue() != total_fee_value){
							errorMsg = "传入的原请求金额与实际支付金额不一致!";
						}
						//现金支付的退款做一些特殊的限制
						else{
							if("30".equals(pay_type)){
							//必须是全额退款
								if(infoOrderVo.getReal_fee().floatValue() != real_fee_value){
									errorMsg = "退款金额与支付金额不一致!";
								}
								else if(infoOrderVo.getOrder_refund_id() != null && !infoOrderVo.getOrder_refund_id().equals("")){
									errorMsg = "此交易已完成过退款，不可重复发起!";
								}
							}
							if("".equals(errorMsg)){
								//新建订单
								InfoOrderVo vo = new InfoOrderVo();
								vo.setBusi_id(busi_id);
								vo.setOrder_type(order_type);
								vo.setOrder_status("B00");
								vo.setCreate_time(new Date());
								vo.setOut_order_id(out_order_id);
								vo.setCreate_ip_address(getIpAddr(request));
								vo.setTotal_fee(new BigDecimal(total_fee_value));
								vo.setDiscount_fee(new BigDecimal(0));
								vo.setReal_fee(new BigDecimal(real_fee_value));
								vo.setReq_way(req_way);
								vo.setOut_remark(remark);
								vo.setOut_refund_no(out_refund_id);
								vo.setPay_type(infoOrderVo.getPay_type());
								order_id = infoOrderServDu.addInfoOrder(vo);
								vo.setOrder_id(order_id);
								
								
								//if(!"30".equals(pay_type)){}
								InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
								// 30为现金支付 不需要配置参数
								if(!"30".equals(pay_type)&&attrValueVo == null){
									errorMsg = "找不到对应的支付类型参数配置数据!";
								}
								else if(pay_type.equals("10") || pay_type.equals("11")){
									String deviceInfo = "";
									int totalFee = (int)(total_fee_value*100);
									int refundFee = (int)(real_fee_value*100);
									String opUserID = busi_id;
									String refundFeeType = "";
									
									//获取微信退款证书的目录
	//								SystemBusiParaVo systemBusiParaVo = systemBusiParaServDu.queryByParaCode("wx_cert_path");
	//								if(systemBusiParaVo == null){
	//									errorMsg = "找不到退款证书，无目录，无法完成退款!";
	//								}
									if(attrValueVo.getCertname() == null || attrValueVo.getCertpassword() == null){
										errorMsg = "退款证书参数未配置数据!";
									}
									else{
	//									String certPath = systemBusiParaVo.getParam_value()+"/"+attrValueVo.getCertname();
										String certPassword = attrValueVo.getCertpassword();
										
										Map<String, String> resultMap = wxPayService.refund(attrValueVo.getAppid(), attrValueVo.getMchid(), attrValueVo.getSignkey(),
												infoOrderVo.getPay_order_id(), "", out_order_id, deviceInfo, totalFee, refundFee, opUserID, refundFeeType, attrValueVo.getCertname(), certPassword);
										
										vo.setPay_notify_time(new Date());
										if("SUCCESS".equals(resultMap.get("result_code"))){
											//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
											vo.setPay_notify_code("0");
											vo.setPay_notify_msg("");
											vo.setPay_notify_trans_id(resultMap.get("transaction_id"));
											vo.setOrder_status("B10");
											vo.setTrade_time(resultMap.get("trade_time"));
											refund_time = vo.getTrade_time();
											
											//退款成功更新原支付信息
											if(infoOrderVo.getOrder_refund_id() == null || infoOrderVo.getOrder_refund_id().equals("")){
												infoOrderVo.setOrder_refund_id(vo.getOrder_id());
											}
											else{
												infoOrderVo.setOrder_refund_id(infoOrderVo.getOrder_refund_id()+","+vo.getOrder_id());
											}
											infoOrderServDu.updateInfoOrder(infoOrderVo);
											
										}
										else{
											vo.setPay_notify_code("1");
											vo.setPay_notify_msg(resultMap.get("return_msg"));
											vo.setOrder_status("B20");
										}
										
										vo.setCust_notify_code("-");
										vo.setPay_order_id(infoOrderVo.getPay_order_id());
										infoOrderServDu.updateInfoOrder(vo);
									}
								}
								else if(pay_type.equals("20") || pay_type.equals("21")){
									//支付宝
									String refundAmount = real_fee_value+"";
									String refundReason = "正常退款";
									String outRequestNo = out_order_id;
									String storeId = busi_id;
									Map<String, String> resultMap = aliPayService.refund(attrValueVo.getAppid(), attrValueVo.getPrivateKey(), attrValueVo.getPublicKey(), 
											infoOrderVo.getPay_order_id(), refundAmount, outRequestNo, refundReason, storeId);
									String tradeStatus = resultMap.get("tradeStatus");
									//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
									//Date trade_time = sdf.parse();
									String trade_time = resultMap.get("trade_time");
									refund_time = trade_time;
									if(tradeStatus.equals("SUCCESS")){
						            	vo.setPay_notify_code("0");
										vo.setPay_notify_msg("");
										vo.setPay_notify_trans_id(resultMap.get("tradeNo"));
										vo.setOrder_status("B10");
										vo.setTrade_time(trade_time);
										
										//退款成功更新原支付信息
										if(infoOrderVo.getOrder_refund_id() == null || infoOrderVo.getOrder_refund_id().equals("")){
											infoOrderVo.setOrder_refund_id(vo.getOrder_id());
										}
										else{
											infoOrderVo.setOrder_refund_id(infoOrderVo.getOrder_refund_id()+","+vo.getOrder_id());
										}
										infoOrderServDu.updateInfoOrder(infoOrderVo);
										
									}
									else{
						            	vo.setPay_notify_code("1");
										vo.setPay_notify_msg(resultMap.get("subMsg"));
										vo.setOrder_status("B20");
										vo.setTrade_time(trade_time);
									}
									
									vo.setCust_notify_code("-");
									vo.setPay_order_id(infoOrderVo.getPay_order_id());
									infoOrderServDu.updateInfoOrder(vo);
								}else if("30".equals(pay_type)){
									// 现金支付直接更新订单
									vo.setPay_notify_code("0");
									vo.setPay_notify_msg("");
									vo.setOrder_status("B10");
									vo.setTrade_time(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
									refund_time = vo.getTrade_time();
									//现金缴费时没有更改info_order 的数据
									if(infoOrderVo.getOrder_refund_id() == null || infoOrderVo.getOrder_refund_id().equals("")){
										infoOrderVo.setOrder_refund_id(vo.getOrder_id());
									}
									else{
										infoOrderVo.setOrder_refund_id(infoOrderVo.getOrder_refund_id()+","+vo.getOrder_id());
									}
									infoOrderServDu.updateInfoOrder(infoOrderVo);
									vo.setCust_notify_code("-");
									infoOrderServDu.updateInfoOrder(vo);
								}
							/*	
								if(errorMsg.equals("")){
									//发起通知回调
									logger.info("发起消息队列"+order_id);
									UocMessage msg = new UocMessage();
									msg.addArg("order_id", order_id);
									int result = custNotifyActivemqServDu.sendMessage(msg);
									if(result == 0){
										logger.info("发起消息队列"+order_id+", 成功!");
									}
									else{
										logger.error("发起消息队列"+order_id+", 失败!"+result);
									}
								}*/
							}
						}
					}
				}
			}
			else{
				errorMsg = "签名校验失败";
			}
			
			if(errorMsg.equals("")){
				message.setType(Type.success);
				message.setContent("退款成功");
				/*message.addArg("trade_time", refund_time);
				message.addArg("transactions_id",order_id);*/
				result = EnvenlopedRefundDate(busi_id,message,refund_time,order_id);
			}
			else{
				message.setType(Type.error);
				message.setContent(errorMsg);
				/*message.addArg("trade_time", refund_time);
				message.addArg("transactions_id",order_id);*/
				result = EnvenlopedRefundDate(busi_id,message,refund_time,order_id);
			}
			
			return RsaEncodeUtil.getJsonFromMessage(message);
			
		}catch(Exception e){
			logger.error("退款出现异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			result = EnvenlopedRefundDate(busi_id,message,refund_time,order_id);
			/*message.setContent("退款出现异常!"+e.getMessage());
			message.addArg("trade_time", refund_time);
			message.addArg("transactions_id",order_id);
			return RsaEncodeUtil.getJsonFromMessage(message);*/
		}
		return result;
	}
	
	public String EnvenlopedRefundDate(String busi_id,Message message,String trade_time,String transactions_id){
	//获取密钥信息
	InfoBusiVo infoBusiVo = new InfoBusiVo();
	infoBusiVo.setBusi_id(busi_id);
	try {
		infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
		if(infoBusiVo == null){
			throw new Exception("busi_id="+busi_id+"，系统未找到相应的业务配置数据");
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("trade_time", trade_time);
		resultMap.put("transactions_id", transactions_id);
		String paramStr = RsaEncodeUtil.getJsonFromMap(resultMap);
		String resultdata = "";
		if("1".equals(infoBusiVo.getEncrypt())){//数据需要加密
			//加密
			String content = UpcEncodeUtilForJson.privateEncode(paramStr, infoBusiVo.getRsa_private_key(), infoBusiVo.getSign_key());
			 resultdata = content;
		}else{
			resultdata = paramStr;
		}
		JSONObject json = new JSONObject();
		json.put("busi_id", busi_id);
		json.put("content", resultdata);
		message.addArg("data", json.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}
		return RsaEncodeUtil.getJsonFromMessage(message);
		
	}
	
	/**
	 * 查询接口
	 * @param xmldata
	 * @return
	 */
	@RequestMapping(value = "payQuery" , method = RequestMethod.POST)
	@ResponseBody
	public String payQuery(HttpServletRequest request,String data){
		Message message = new Message();
		String errorMsg = "";
		String order_id = "";
		InfoOrderVo vo = new InfoOrderVo();
		try{
			BusiReqDataVo busiReqDataVo = validReqData(data);
			if(busiReqDataVo.isSign()){
				
				String busi_id = busiReqDataVo.getBusiId();
				Map<String, Object> contentMap = busiReqDataVo.getContentMap();
				
				//必传参数校验
				String order_type = ""+contentMap.get("order_type");
				String req_way = ""+contentMap.get("req_way");
				String out_order_id = ""+contentMap.get("out_order_id");
				String ori_order_id = ""+contentMap.get("ori_order_id");
				
				
				if(!"03".equals(order_type)){
					errorMsg = "order_type数据不正确";
				}
				else if(!"APP".equals(req_way) && !"PC".equals(req_way)){
					errorMsg = "req_way数据不正确";
				}
				else if(isStringEmpty(out_order_id)){
					errorMsg = "out_order_id数据不正确";
				}
				else if(isStringEmpty(ori_order_id)){
					errorMsg = "ori_order_id数据不正确";
				}
				
				//获取业务信息
				InfoBusiVo infoBusiVo = new InfoBusiVo();
				infoBusiVo.setBusi_id(busi_id);;
				infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
				
				if(infoBusiVo == null){
					throw new Exception("busi_id="+busi_id+"，系统未找到相应的业务配置数据");
				}
				
				//创建查询订单
				//InfoOrderVo vo = new InfoOrderVo();
				vo.setBusi_id(busi_id);
				vo.setOrder_type(order_type);
				vo.setOrder_status("C00");
				vo.setCreate_time(new Date());
				vo.setOut_order_id(out_order_id);
				vo.setCreate_ip_address(getIpAddr(request));
				vo.setTotal_fee(new BigDecimal(0));
				vo.setDiscount_fee(new BigDecimal(0));
				vo.setReal_fee(new BigDecimal(0));
				vo.setReq_way(req_way);
				vo.setOut_remark("");
				order_id = infoOrderServDu.addInfoOrder(vo);
				vo.setOrder_id(order_id);
				
				
				//查询原订单信息
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setBusi_id(busi_id);
				infoOrderVo.setOut_order_id(ori_order_id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOutOrderId(infoOrderVo);
				
				if(infoOrderVo == null || (!infoOrderVo.getOrder_status().startsWith("A") && !infoOrderVo.getOrder_status().startsWith("B"))){
					errorMsg = "未找到此订单的数据!";
				}
				else{
					String result_code = "0".equals(infoOrderVo.getPay_notify_code()) ? "SUCCESS" : "FAIL";
					//对通知内容进行加密和签名
					//通知的格式依然是 <xml><busi_id>BUSI_ID</busi_id><content>CONTENT</content></xml>
					Map<String, Object> resultMap = new HashMap<>();
					resultMap.put("result_code", result_code);
					resultMap.put("result_msg", infoOrderVo.getPay_notify_msg());
					resultMap.put("req_type", infoOrderVo.getOrder_type());
					resultMap.put("req_way", infoOrderVo.getReq_way());
					resultMap.put("out_order_id", out_order_id);
					resultMap.put("ori_order_id", ori_order_id);
					resultMap.put("pay_type", infoOrderVo.getPay_type());
					resultMap.put("total_fee", infoOrderVo.getTotal_fee());
					resultMap.put("real_fee", infoOrderVo.getReal_fee());
					resultMap.put("remark", infoOrderVo.getOut_remark());
					resultMap.put("pay_status", infoOrderVo.getOrder_status());
					
					//如果成功，返回支付流水号
					/*if("0".equals(infoOrderVo.getPay_notify_code())){
						resultMap.put("transactions_id", infoOrderVo.getPay_notify_trans_id());
					}*/
					resultMap.put("transactions_id", infoOrderVo.getOrder_id()); // 业务系统改为外部订单号
					//contentMap.put("trade_time", infoOrderVo.getTrade_time()); 
					resultMap.put("trade_time", infoOrderVo.getTrade_time());// 新加对账标志支付时间
					if("A00".equals(infoOrderVo.getOrder_status())){
						resultMap.put("result_msg", "订单未完成支付");
					}
					else if("B00".equals(infoOrderVo.getOrder_status())){
						resultMap.put("result_msg", "订单未完成退款");
					}
					// 改为json字符串
					//String paramStr = RsaEncodeUtil.getXmlFromMap(resultMap);
					/*if("1".equals(infoBusiVo.getEncrypt())){//能力平台必须封装service_name'字段
						if(infoBusiVo.getAopname() == null||"".equals(infoBusiVo.getAopname())){
							throw new IllegalArgumentException("请配置回调能力平台的service_name(aopname)");
						}
						resultMap.put("service_name",infoBusiVo.getAopname());
					}*/
					//String paramStr = RsaEncodeUtil.getJsonFromMap(resultMap);
					String paramStr = RsaEncodeUtil.getJsonFromMap(resultMap);
					String resultdata = "";
					if("1".equals(infoBusiVo.getEncrypt())){//数据需要加密
						//加密
						String content = UpcEncodeUtilForJson.privateEncode(paramStr, infoBusiVo.getRsa_private_key(), infoBusiVo.getSign_key());
						 resultdata = content;
					}else{
						resultdata = paramStr;
					}
					if("10000002".equals(busi_id)){// BSS已经上线，下次需要更改
						message.addArg("data",resultdata);
					}else{
						JSONObject json = new JSONObject();
						json.put("busi_id", busi_id);
						json.put("content", resultdata);
						
						message.addArg("data", json.toString());
					}
					
					
					//更新通知状态
					vo.setCust_notify_code("0");
					vo.setCust_notify_msg("SUCCESS");
					vo.setCust_notify_time(new Date());
					infoOrderServDu.updateInfoOrder(vo);
					
				}
				
			}
		
			else{
				errorMsg = "签名校验失败";
			}
			
			if(errorMsg.equals("")){
				vo.setOrder_status("C10");
				message.setType(Type.success);
				message.setContent("查询成功");
			}
			else{
				vo.setOrder_status("C20");
				message.setType(Type.error);
				message.setContent(errorMsg);
			}
			infoOrderServDu.updateInfoOrder(vo);
			return  RsaEncodeUtil.getJsonFromMessage(message);
			
		}catch(Exception e){
			logger.error("查询出现异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			message.setContent("查询出现异常!"+e.getMessage());
			vo.setOrder_status("C20");
			infoOrderServDu.updateInfoOrder(vo);
			return RsaEncodeUtil.getJsonFromMessage(message);
		}
	}
	
	@RequestMapping(value = "checkPayBill" , method = RequestMethod.POST)
	@ResponseBody
	public String checkPayBill(HttpServletRequest request,String data){
		Message message = new Message();
		InfoOrderVo infoOrderVo = new InfoOrderVo();
		String order_id= "";
		try{
			BusiReqDataVo busiReqDataVo = validReqData(data);
			if(busiReqDataVo.isSign()){
				String busi_id = busiReqDataVo.getBusiId();
				Map<String, Object> contentMap = busiReqDataVo.getContentMap();
				String order_type = ""+contentMap.get("order_type");
				String req_way = ""+contentMap.get("req_way");
				String out_order_id = ""+contentMap.get("out_order_id");
				String remark = ""+contentMap.get("remark");
				String bill_date = "" + contentMap.get("bill_date");
				
				// 创建对账订单下载记录
			/*	InfoOrderVo orderVo = new InfoOrderVo();
				orderVo.setBusi_id(busi_id);
				orderVo.setOut_order_id(out_order_id);
				orderVo = infoOrderServDu.queryInfoOrderByOutOrderId(orderVo);
				
				if(orderVo != null&&orderVo.getOrder_status().equals("E10")){
					errorMsg = "账单已经下载了!";
					logger.info(errorMsg);
					message.setType(Type.error);
					message.setContent(errorMsg);
				}else{*/
						
					infoOrderVo.setBusi_id(busi_id);
					infoOrderVo.setOut_order_id(out_order_id);
					infoOrderVo.setOrder_type(order_type);
					infoOrderVo.setReq_way(req_way);
					infoOrderVo.setOrder_status("E00");
					infoOrderVo.setCreate_ip_address(getIpAddr(request));
					infoOrderVo.setOut_remark(remark);
					infoOrderVo.setCreate_time(new Date());
					order_id = infoOrderServDu.addInfoOrder(infoOrderVo);
					
					//查询账单
					List<PaySettleTransVo> pstVo = new ArrayList<PaySettleTransVo>();
					pstVo = paySettleTransDu.getBillByDate_busi(bill_date,busi_id);
					message.setType(Type.success);
					logger.info("账单下载成功");
					message.setContent("下载账单成功");
					infoOrderVo.setOrder_status("E10");
					infoOrderVo.setOrder_id(order_id);
					infoOrderServDu.updateInfoOrder(infoOrderVo);
					if(pstVo!= null && pstVo.size()>0){
						message.addArg("data", JSONArray.fromObject(pstVo).toString());
					}else{
						message.addArg("data","");
					}
						
					//}
				return RsaEncodeUtil.getJsonFromMessage(message);
			}
		}catch(Exception e){
				e.printStackTrace();
				infoOrderVo.setOrder_status("E20");
				infoOrderVo.setOrder_id(order_id);
				infoOrderServDu.updateInfoOrder(infoOrderVo);
				message.setType(Type.error);
				message.setContent(e.getMessage());
				message.addArg("data","");
				return RsaEncodeUtil.getJsonFromMessage(message);
			}
		return null;
	}
	
	/* xml转换为 json*/
	/**
	 * 请求数据安全性校验
	 * @param model
	 * @param map
	 * @return
	 */
	private BusiReqDataVo validReqData(String data) throws Exception{
		try{
			/*data= new String(data.getBytes("iso-8859-1"),"utf-8");
			data = UpcEncodeUtilForJson.decodeUtf(data); */
			String oridata = data;
			logger.info("收到的请求内容：\n"+data);
			data = UpcEncodeUtilForJson.decodeUtf(data); 
			logger.info("解密的请求内容：\n"+data);
			/*
			Map<String, Object> map = RsaEncodeUtil.getMapFromXML(xmldata);
			String busi_id = map.get("busi_id").toString();
			
			if(map.get("content") == null || map.get("content").toString().equals("")){
				throw new IllegalArgumentException("找不到content参数");
			}
			String content = map.get("content").toString();*/
			
			
			//Map<String,Object> map = new HashMap<String,Object>();
			//map =  RsaEncodeUtil.getMapFromJson(data);
			
			//String busi_id = map.get("busi_id").toString();
			/*if(map.get("busi_id") == null || map.get("busi_id").toString().equals("")){
				throw new IllegalArgumentException("找不到busi_id参数");
			}*/
			// busi_id = "10000001";
			//Map<String,Object> map = new HashMap<String,Object>();
			//map = RsaEncodeUtil.getMapFromJson(data);
			JSONObject json = JSONObject.fromObject(data);
			String busi_id = json.getString("busi_id");
			String content = json.getString("content");
			 if(busi_id == null ||"".equals(busi_id)){
				 throw new IllegalArgumentException("找不到busi_id参数");

			 }
			 if("".equals(content)||content == null){
					throw new IllegalArgumentException("请传入必要的参数");
				}
			//获取密钥信息
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			
			if(infoBusiVo == null){
				throw new Exception("busi_id="+busi_id+"，系统未找到相应的业务配置数据");
			}
//			if(infoBusiVo.getBusi_name().startsWith("BSS")){
//				content= new String(content.getBytes("iso-8859-1"),"utf-8");
//			}
			
			String decodeStr = "";
			boolean signResult = false;
			if("1".equals(infoBusiVo.getEncrypt())){//是加密数据需要进行减密
				
				//BASE64位加密的， 不需要使用
				JSONObject orijson = JSONObject.fromObject(oridata);
				content = orijson.getString("content");
				
				//解密需要更改为json的URL的参数解密， 不然数据会丢失
				decodeStr = UpcEncodeUtilForJson.privateDecode(content, infoBusiVo.getRsa_private_key());
				signResult = UpcEncodeUtilForJson.signValid(decodeStr, infoBusiVo.getSign_key());
				logger.info("收到请求内容：busi_id="+busi_id+", content="+decodeStr);
				
			}else{
				decodeStr = content;
				signResult = true;
			}
			
			BusiReqDataVo busiReqDataVo = new BusiReqDataVo();
			busiReqDataVo.setSign(signResult);
			busiReqDataVo.setBusiId(busi_id);
			busiReqDataVo.setContentMap(RsaEncodeUtil.getMapFromJson(decodeStr));
			
			return busiReqDataVo;
			
		}catch(Exception e){
			logger.error("请求数据校验失败!"+e.getMessage(), e);
			throw new Exception("请求数据校验失败!"+e.getMessage());
		}
	}
	
	/**
	 * 获取请求地址
	 * @param request
	 * @return
	 */
	private static String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
	
	
	private boolean isStringEmpty(String str){
		return str == null || str.equals("") || str.equals("null");
	}
}
