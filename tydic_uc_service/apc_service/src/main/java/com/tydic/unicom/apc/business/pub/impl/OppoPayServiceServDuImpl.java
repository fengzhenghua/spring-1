package com.tydic.unicom.apc.business.pub.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.business.order.vo.LogPayCsVo;
import com.tydic.unicom.apc.business.pub.interfaces.OppoPayServiceServDu;
import com.tydic.unicom.apc.business.pub.vo.WXPayRestVo;
import com.tydic.unicom.apc.common.wxpay.NotifyReqData;
import com.tydic.unicom.apc.common.wxpay.RefundResData;
import com.tydic.unicom.apc.common.wxpay.WXPayUtilsServ;
import com.tydic.unicom.apc.service.order.interfaces.LogPayCsServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OppoPayServiceServDuImpl implements OppoPayServiceServDu {

	private static Logger logger = Logger.getLogger(OppoPayServiceServDuImpl.class);
	
	@Autowired
	private LogPayCsServDu logPayCsServDu;
	
	@Autowired
	private WXPayUtilsServ wXPayUtilsServ;
	
	@Override
	public UocMessage getWxQrCode(WXPayRestVo reqVo,String oper_no) throws Exception {
		
		UocMessage message = new UocMessage();
		
		//校验入参
		if(StringUtils.isEmpty(oper_no)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("入参oper_no不能为空！！！");
			return message;
		}
		
		//校验入参
		if(StringUtils.isEmpty(reqVo.getOrder_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("入参order_id不能为空！！！");
			return message;
		}
				
		
		String order_id = reqVo.getOrder_id();
		
		//在code_list中微信支付类型为20
		String pay_type = "20";
		String mchtFlag = reqVo.getMcht_flag();
		if(reqVo.getPay_type() != null && !reqVo.getPay_type().equals("")){
			pay_type = reqVo.getPay_type();
		}
		
		LogPayCsVo logPayCsVo = new LogPayCsVo();
		logPayCsVo.setTele_type(reqVo.getTele_type());
		logPayCsVo.setDevice_number(reqVo.getDeviceInfo());
		logPayCsVo.setCs_order_id(order_id);
		logPayCsVo.setTotal_fee(reqVo.getTotalFee());
		logPayCsVo.setDiscount_fee("0");
		logPayCsVo.setOper_id(oper_no);
		logPayCsVo.setInvoice_flag("0");
		logPayCsVo.setPayed_fee("0");
		logPayCsVo.setFlag("0");//未提交
		logPayCsVo.setPay_type(pay_type);
		logPayCsVo.setMcht_flag(mchtFlag);
		//先查询日志表，如果有该订单，且该订单支付未成功，则删除该记录，如果有该订单，并支付成功，则提示该订单已经支付！（这里正常逻辑来说是不会出现订单已经支付的情况）
		LogPayCsVo vo = null;
		try{
			vo = logPayCsServDu.queryLogPayCsByCsOrderId(logPayCsVo.getCs_order_id());
			if(vo != null){
				//存在该记录,且是支付成功状态
				if("1".equals(vo.getFlag()) || "2".equals(vo.getFlag())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("该订单已经支付！");
					return message;
				}else{
					//更新订单支付方式
					//vo.setPay_type(logPayCsVo.getPay_type());
					//vo.setTotal_fee(reqVo.getTotalFee());
					vo.setFlag("-1");
					logPayCsServDu.updateLogPayCs(vo);
					vo = logPayCsServDu.insertLogPayCs(logPayCsVo);
				}
			}else{//日志表没有记录，则新增
				vo = logPayCsServDu.insertLogPayCs(logPayCsVo);
			}
		}catch(Exception e){
			logger.error("操作订单表失败！");
			e.printStackTrace();
		}
		String content = null;
		String error = "";
		try {
			String deviceInfo = reqVo.getDeviceInfo();
			String body = reqVo.getBody();
			String outTradeNo = vo.getPay_cs_id();
			
			//微信的金额是以分为单位的整数
			int totalFee = (int)(Float.parseFloat(vo.getTotal_fee())*100);
			String productId = outTradeNo;
			String expireTime = reqVo.getExpireTime();
			logger.info("mchtFlag"+mchtFlag);
			content = wXPayUtilsServ.unifiedOrderNative(deviceInfo, body, outTradeNo, totalFee, productId, expireTime, mchtFlag);
		} catch (Exception e) {
			logger.error("获取微信二维码失败！");
			e.printStackTrace();
			error = e.getMessage();
		}
		if(content == null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("生成二维码失败！"+error);
		}else{
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent(content);
		}
		return message;
	}

	@Override
	public boolean getWxCallBack(String resString) throws Exception {
        
        logger.info("----接收到notify_url的返回，内容为：----------");
        logger.info(resString);
        
        //验证签名，并且返回通知结果对象
        NotifyReqData reqData = wXPayUtilsServ.scanNotify(resString);
        
        
        if(reqData != null){
			if(reqData.getReturn_code().equals("SUCCESS")){
				LogPayCsVo vo = new LogPayCsVo();
				vo = logPayCsServDu.queryLogPayCsByPayCsId(reqData.getOut_trade_no());
				vo.setOper_sn(reqData.getTransaction_id());
				
				//支付成功
				if(reqData.getResult_code().equals("SUCCESS")){
					vo.setFlag("1");
					vo.setPayed_fee(((float)reqData.getTotal_fee())/100+"");
				}
				
				else{
					
					//其它状态记录为失败
					vo.setFlag("-1");
					vo.setPayed_fee(((float)reqData.getTotal_fee())/100+"");
				}
				
				logPayCsServDu.updateLogPayCs(vo);
				
				return true;
			}
			else{
				return false;
			}
        }
        else{
        	return false;
        }
	}

	@Override
	public UocMessage getPayResult(String cs_order_id, String oper_no)throws Exception {
		UocMessage message = new UocMessage();
		//校验入参
		if(StringUtils.isEmpty(cs_order_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("入参订单id不能为空！！！");
			return message;
		}
		
		LogPayCsVo vo = logPayCsServDu.queryLogPayCsByCsOrderId(cs_order_id);
		if(vo == null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("未查询到该笔订单的缴费记录");
			return message;
		}
		if(Integer.parseInt(vo.getFlag()) == 1 ||  Integer.parseInt(vo.getFlag()) == 2){//支付成功
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("支付成功！");
		}else{
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			
			//隔两秒查询一次，查询10次
			/*for(int i= 0 ;i< 10;i++){
				Thread.sleep(2000);
				vo = logPayCsServDu.queryLogPayCsByCsOrderId(cs_order_id);
				if(Integer.parseInt(vo.getFlag()) >=1 ){//支付成功
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("支付成功！");
					break;
				}
			}*/
			
			//现在前台显示支付成功时间太长了，优化优化
			vo = logPayCsServDu.queryLogPayCsByCsOrderId(cs_order_id);
			if(Integer.parseInt(vo.getFlag()) >=1 ){//支付成功
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("支付成功！");
			}
		}
		return message;
	}

	@Override
	public UocMessage wxpayRefund(String order_id, String oper_no)
			throws Exception {

		logger.info("微信退款------------------------------------------");
		UocMessage message =new UocMessage();
		if( StringUtils.isEmpty(order_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_id为空！");
			return message;
		}
		
		if( StringUtils.isEmpty(oper_no)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_no为空！");
			return message;
		}
		
		//查看支付日志
		LogPayCsVo logPayCsVo = new LogPayCsVo();
		try{
		logPayCsVo.setCs_order_id(order_id);
		logPayCsVo =logPayCsServDu.queryLogPayCsByCsOrderId(logPayCsVo.getCs_order_id());
		if(logPayCsVo!=null && logPayCsVo.getFlag().equals("1") ){
			RefundResData data =wXPayUtilsServ.refund(logPayCsVo.getDevice_number(), logPayCsVo.getOper_sn(), "", logPayCsVo.getPay_cs_id(),  (int)(Float.parseFloat(logPayCsVo.getTotal_fee())*100),  (int)(Float.parseFloat(logPayCsVo.getPayed_fee())*100), oper_no,logPayCsVo.getMcht_flag());
			logger.info("result++++++++"+data.getResult_code()+"++++++++++++");
			
			if("SUCCESS".equals(data.getResult_code())){
				//退款成功后更新支付日志表
				logPayCsVo.setFlag("-1");
				//2016-11-22起不再修改原纪录状态，新增修改记录
				if( !StringUtils.isEmpty( logPayCsVo.getTotal_fee() ) ){
					logPayCsVo.setTotal_fee( "-"+logPayCsVo.getTotal_fee() );
				}
				if( !StringUtils.isEmpty( logPayCsVo.getPayed_fee() )  ){
					logPayCsVo.setPayed_fee( "-"+logPayCsVo.getPayed_fee() );
				}
				logPayCsServDu.insertLogPayCs(logPayCsVo);
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("退款成功！");
				return message;	
			}else{
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("退款失败！");
				return message;
				
			}
			
			
		  }else {
			  message.setRespCode(RespCodeContents.SERVICE_FAIL);
			  message.setContent("退款失败！");
			  return message;
		}
		}catch(Exception e){
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("退款出现异常！");
			return message;
		}
	}

}

