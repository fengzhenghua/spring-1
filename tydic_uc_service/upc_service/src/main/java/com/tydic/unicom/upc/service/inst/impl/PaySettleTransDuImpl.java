package com.tydic.unicom.upc.service.inst.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.IllegalClassException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.inst.impl.PaySettleTransServImp;
import com.tydic.unicom.upc.base.database.inst.interfaces.PaySettleTransServ;
import com.tydic.unicom.upc.base.database.po.inst.PaySettleTransPo;
import com.tydic.unicom.upc.service.inst.interfaces.PaySettleTransDu;
import com.tydic.unicom.upc.vo.inst.PaySettleTransVo;

public class PaySettleTransDuImpl implements PaySettleTransDu {
	private static Logger logger = Logger.getLogger(PaySettleTransDuImpl.class);
	@Autowired
	private PaySettleTransServ paySettleTransServ;
	@Override
	public int isBillByDate(String bill_date) {
		if(bill_date == null ||"".equals(bill_date)|| "null".equals(bill_date)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
			bill_date = sdf.format(calendar.getTime());
		}
		logger.info("PaySettleTransDuImpl 方法开始查询时间为" + bill_date +"的对账记录总数" );
		return paySettleTransServ.isBillByDate(bill_date);
	}

	@Override
	public List<PaySettleTransVo> getBillByDate_busi(String bill_date,String busi_id) {
		if(busi_id== null|| "".equals(busi_id)){
			throw new IllegalClassException("未找到业务系统id");
		}
		// 没有指定时间  默认为前一个天
		if(bill_date == null ||"".equals(bill_date)|| "null".equals(bill_date)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
			bill_date = sdf.format(calendar.getTime());
		}
		logger.info("PaySettleTransDuImpl 方法查询 busi=" + busi_id +  "对账时间 bill_date=" + bill_date + "的对账记录");
		List<PaySettleTransPo> pstPo = new ArrayList<PaySettleTransPo>();
		List<PaySettleTransVo> pstVo = new ArrayList<PaySettleTransVo>();
		PaySettleTransVo vo = new PaySettleTransVo();
		pstPo = paySettleTransServ.getBillByDate_busi(bill_date, busi_id);
		if(pstPo != null && pstPo.size() > 0){
			for(PaySettleTransPo po:pstPo){
				vo = new PaySettleTransVo();
				logger.info("po  = " + po.getOrder_id());
				BeanUtils.copyProperties(po, vo);
				pstVo.add(vo);
			}
			return pstVo;
		}
		return null;
	}

}
