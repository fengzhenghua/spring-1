package com.tydic.unicom.apc.service.order.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.apc.base.order.interfaces.LogPayCsServ;
import com.tydic.unicom.apc.base.order.po.LogPayCsPo;
import com.tydic.unicom.apc.base.pub.interfaces.CodeListServ;
import com.tydic.unicom.apc.base.pub.po.CodeListPo;
import com.tydic.unicom.apc.business.order.vo.LogPayCsVo;
import com.tydic.unicom.apc.common.utils.ParaTool;
import com.tydic.unicom.apc.service.order.interfaces.LogPayCsServDu;

/**
 * <p>
 * </p>
 * 
 * @author shibing 2015-11-12 下午3:28:05
 * @ClassName PayCsServDuImpl
 * @Description TODO 支付流水表操作
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-15
 * @modify by reason:{方法名}:{原因}
 */
@Service("LogPayCsServDu")
public class LogPayCsServDuImpl implements LogPayCsServDu {


	private static Logger logger = Logger.getLogger(LogPayCsServDuImpl.class);
	
	@Autowired
	private LogPayCsServ logPayCsServ;
	
	@Autowired
	private CodeListServ codeListServ;

	@Override
	public LogPayCsVo insertLogPayCs(LogPayCsVo logPayCsVo) throws Exception {
		String province = "";
		List<CodeListPo> list = codeListServ.queryCodeListByTypeCode("province_code");
		if(list != null && list.size()>0){
			province=list.get(0).getCode_id(); 
		}
		if(!province.equals("") || province != null){
			province = province.toUpperCase();
		}
		Calendar now = Calendar.getInstance();
		String month  =Integer.toString(now.get(Calendar.MONTH) + 1);
		String day = Integer.toString(now.get(Calendar.DAY_OF_MONTH));
		String hours = Integer.toString(now.get(Calendar.HOUR_OF_DAY)); 
		String minutes = Integer.toString(now.get(Calendar.MINUTE));
		String seconds = Integer.toString(now.get(Calendar.SECOND));
		if(month.length()==1){
			month="0"+month;
		}
		if(day.length()==1){
			day="0"+day;
		}
		if(hours.length()==1){
			hours="0"+hours;
		}
		if(minutes.length()==1){
			minutes="0"+minutes;
		}
		if(seconds.length()==1){
			seconds="0"+seconds;
		}
		//TODO 后期优化
		Long seq = System.currentTimeMillis();
		String payCsId=seq.toString().substring(0, 12);
		payCsId =province + now.get(Calendar.YEAR)+month+day+hours+minutes+seconds+ payCsId;
		LogPayCsPo logPayCs=(LogPayCsPo) ParaTool.copyProperties(logPayCsVo, LogPayCsPo.class);
		logPayCs.setPay_cs_id(payCsId);
		logPayCsVo.setPay_cs_id(payCsId);
		boolean t = logPayCsServ.insertLogPayCs(logPayCs);
		if(t)
			return logPayCsVo;
		else
			return null;
	}

	@Override
	public boolean updateLogPayCs(LogPayCsVo logPayCsVo) throws Exception {
		LogPayCsPo logPayCs=(LogPayCsPo) ParaTool.copyProperties(logPayCsVo, LogPayCsPo.class);
		logPayCsServ.updateLogPayCs(logPayCs);
		return true;
	}
	
	public LogPayCsVo queryLogPayCsByCsOrderId(String cs_order_id) throws Exception{
		LogPayCsPo logPayCs = logPayCsServ.queryLogPayCsByCsOrderId(cs_order_id); 
		if(logPayCs != null && logPayCs.getPay_cs_id() != null && logPayCs.getPay_cs_id().length() > 0)
			return (LogPayCsVo) ParaTool.copyProperties(logPayCs, LogPayCsVo.class);
		else
			return null;
	}
	
	public void deleteLogPayCsByCsOrderId(String cs_order_id) throws Exception{
		logPayCsServ.deleteLogPayCsByCsOrderId(cs_order_id); 
		
	}

	@Override
	public LogPayCsVo queryLogPayCsByPayCsId(String pay_cs_id)throws Exception {
		LogPayCsPo log = new LogPayCsPo();
		log.setPay_cs_id(pay_cs_id);
		List<LogPayCsPo> list = logPayCsServ.queryLogPayCsByPayCsId(log);
		if(list != null && list.size() > 0)
			return (LogPayCsVo) ParaTool.copyProperties(list.get(0),LogPayCsVo.class);
		return null;
	}

	@Override
	public LogPayCsVo queryLogPayCsLikeCsOrderId(String cs_order_id)
			throws Exception {
		LogPayCsPo logPayCs = logPayCsServ.queryLogPayCsLikeCsOrderId(cs_order_id); 
		if(logPayCs != null && logPayCs.getPay_cs_id() != null && logPayCs.getPay_cs_id().length() > 0)
			return (LogPayCsVo) ParaTool.copyProperties(logPayCs, LogPayCsVo.class);
		else
			return null;
	}
}
