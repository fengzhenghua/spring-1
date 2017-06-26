package com.tydic.unicom.upc.base.database.inst.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.IllegalClassException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.inst.interfaces.PaySettleTransServ;
import com.tydic.unicom.upc.base.database.po.inst.PaySettleTransPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("PaySettleTransServ")
public class PaySettleTransServImp extends BaseServImpl<PaySettleTransPo> implements PaySettleTransServ {
	private static Logger logger = Logger.getLogger(PaySettleTransServImp.class);
	@Override
	public int isBillByDate(String bill_date) {
		//如果传入的时间为空  默认时间为前一天
		if(bill_date == null ||"".equals(bill_date)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
			bill_date = sdf.format(calendar.getTime());
		}
		logger.info("开始查询时间为" + bill_date +"的对账记录总数" );
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("bill_date", bill_date);
		Condition condition = Condition.build("isBillByDate").filter(map);
		return  S.get(PaySettleTransPo.class).queryFor().queryForInt(condition);
	}

	@Override
	public List<PaySettleTransPo> getBillByDate_busi(String bill_date,String busi_id) {
		if(busi_id== null|| "".equals(busi_id)){
			throw new IllegalClassException("未找到业务系统id");
		}
		// 没有指定时间  默认为前一个天
		if(bill_date == null ||"".equals(bill_date)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
			bill_date = sdf.format(calendar.getTime());
		}
		logger.info("查询 busi=" + busi_id +  "对账时间 bill_date=" + bill_date + "的对账记录");
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("bill_date", bill_date);
		map.put("busi_id", busi_id);
		Condition condition = Condition.build("getBillByDate_busi").filter(map);
		return 	S.get(PaySettleTransPo.class).query(condition);
	}
	
}
