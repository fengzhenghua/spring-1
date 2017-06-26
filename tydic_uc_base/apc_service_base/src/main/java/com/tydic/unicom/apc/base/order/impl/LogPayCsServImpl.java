package com.tydic.unicom.apc.base.order.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.apc.base.order.interfaces.LogPayCsServ;
import com.tydic.unicom.apc.base.order.po.LogPayCsPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

/**
 * <p>
 * </p>
 * 
 * @author huangweixing 2014-9-19 下午4:25:54
 * @ClassName OrderServImpl
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-19
 * @modify by reason:{方法名}:{原因}
 */
@Service("LogPayCsServ")
public class LogPayCsServImpl extends BaseServImpl<LogPayCsPo> implements LogPayCsServ {

	private static Logger logger = Logger.getLogger(LogPayCsServImpl.class);

	/**
	 * @author huangweixing 2014-9-22 上午11:29:58
	 * @Method: queryInfoOrderByOrderId
	 * @Description: TODO 订单Id查询订单信息
	 * @param infoOrder
	 * @return
	 * @see com.tydic.unicom.order.service.interfaces.OrderServ#queryInfoOrderByOrderId(com.tydic.unicom.order.po.InfoOrder)
	 */

	/**
	 * @author huangweixing 2014年9月28日 下午10:04:01
	 * @Method: updateOrderByOrderId
	 * @Description: TODO 根据订单ID更新order_status和update_date
	 * @param InfoOrder
	 * @see com.tydic.unicom.order.service.interfaces.OrderServ#updateOrderByOrderId(com.tydic.unicom.order.po.InfoOrder)
	 */

	@Override
	public List<LogPayCsPo> queryLogPayCs(LogPayCsPo logPayCs) {
		// TODO Auto-generated method stub
		Map<String, Object> map = logPayCs.convertThis2Map();
		String opereId = logPayCs.getOper_id();
		String sQryDateType = logPayCs.getQry_date_type();
		if(null!= opereId){
			map.put("oper_id",opereId);
		}

		Map<String, Date> mapDateMap = GetStrDate(sQryDateType);
		if(mapDateMap.get("start_date")!=null)
		{
			map.put("start_date",mapDateMap.get("start_date"));
		}
		if(mapDateMap.get("end_date")!=null)
		{
			map.put("end_date",mapDateMap.get("end_date"));
		}
		
		Condition con = Condition.build("queryLogPayCs").filter(map);
		List<LogPayCsPo> list = S.get(LogPayCsPo.class).query(con);
		return (list == null || list.isEmpty()) ? null : list;
	}
 
	@Override
	public List<LogPayCsPo> queryLogPayCsPc(LogPayCsPo logPayCs) {
		// TODO Auto-generated method stub
		Map<String, Object> map = logPayCs.convertThis2Map();
//		String sQryDateType = logPayCs.getQry_date_type();
		String opereId = logPayCs.getOper_id();
		String startDate = logPayCs.getCreate_date()+" "+"00:00:00";
		String endDate = logPayCs.getEnd_date()+" "+"23:59:59";
		String deviceNumber = logPayCs.getDevice_number();
		String teleType = logPayCs.getTele_type();
		if(null!= opereId){
			map.put("oper_id",opereId);
		}
		if(null!=startDate){
			map.put("start_date",startDate);	
				}
		if(null!=endDate){
			map.put("end_date",endDate);
		}
		if(null!=deviceNumber){
			map.put("device_number",deviceNumber);
		}
		if(null != teleType){
			map.put("tele_type", teleType);
		}
		
		Condition con = Condition.build("queryLogPayCsPc").filter(map);
		List<LogPayCsPo> list = S.get(LogPayCsPo.class).query(con);
		return (list == null || list.isEmpty()) ? null : list;
	}
	
	@Override
	public List<LogPayCsPo> queryLogPayCsListByCsOrderId(LogPayCsPo logPayCs) {
		// TODO Auto-generated method stub
		Map<String, Object> map = logPayCs.convertThis2Map();
		map.put("cs_order_id", logPayCs.getCs_order_id());
		Condition con = Condition.build("queryLogPayCsListByCsOrderId").filter(map);
		List<LogPayCsPo> list = S.get(LogPayCsPo.class).query(con);
		return (list == null || list.isEmpty()) ? null : list;
	}
	@Override
	public Boolean insertLogPayCs(LogPayCsPo logPayCs) {
		create(LogPayCsPo.class, logPayCs);
		return true;
	}

	@Override
	public Boolean updateLogPayCs(LogPayCsPo logPayCs) {
		// TODO Auto-generated method stub
		if(null==logPayCs){
			return null;
		}
		update(LogPayCsPo.class,logPayCs);
		return true;
	}
	
	private Map<String,Date> GetStrDate(String sQryDateType)
	{	
		Calendar calendar = Calendar.getInstance(); 
		Map<String,Date> map= new HashMap<String,Date>();
		//转换时间
		if("1".equals(sQryDateType))
		{
			//本月 
		    calendar.set( calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		    Date strDate = calendar.getTime();  
			map.put("start_date", strDate);
		}else if("2".equals(sQryDateType))
		{
			//本周
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
            Date strDate = calendar.getTime();
            map.put("start_date", strDate);
		}else if("3".equals(sQryDateType))
		{
			//上月
			calendar.add(Calendar.MONTH, -1);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
			map.put("start_date", calendar.getTime());
			int MaxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), MaxDay, 23, 59, 59);
			map.put("end_date", calendar.getTime());
		}
		else 
		{
			//其他，默认查本月
			//得到月初  
		    calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
		    Date strDateFrom = calendar.getTime();  
			map.put("start_date", strDateFrom);
		}
		return map;
	}


	@Override
	public List<LogPayCsPo> queryLogPayCsByPayCsId(LogPayCsPo logPayCs) {
		// TODO Auto-generated method stub
		Map<String, Object> map = logPayCs.convertThis2Map();

		
		Condition con = Condition.build("queryLogPayCs").filter(map);
		List<LogPayCsPo> list = S.get(LogPayCsPo.class).query(con);
		return (list == null || list.isEmpty()) ? null : list;
	}
	
	@Override
	public LogPayCsPo queryLogPayCsByCsOrderId(String cs_order_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cs_order_id", cs_order_id);
		Condition con = Condition.build("queryLogPayCsByCsOrderId").filter(map);
		List<LogPayCsPo> list = S.get(LogPayCsPo.class).query(con);
		if(list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public void deleteLogPayCsByCsOrderId(String cs_order_id) throws Exception {
		remove(LogPayCsPo.class, cs_order_id);
	}

	@Override
	public LogPayCsPo queryLogPayCsLikeCsOrderId(String cs_order_id)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cs_order_id", cs_order_id);
		Condition con = Condition.build("queryLogPayCsLikeCsOrderId").filter(map);
		List<LogPayCsPo> list = S.get(LogPayCsPo.class).query(con);
		if(list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public LogPayCsPo queryLogPayCsByservOrderNo(String serv_order_no)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serv_order_no", serv_order_no);
		Condition con = Condition.build("queryLogPayCsByservOrderNo").filter(map);
		List<LogPayCsPo> list = S.get(LogPayCsPo.class).query(con);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else {
			return null;
		}
	}
}
