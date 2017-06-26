package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskInstHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskInstHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskInstHisServ")
public class ProcInstTaskInstHisServImpl extends BaseServImpl<ProcInstTaskInstHisPo> implements ProcInstTaskInstHisServ{

	@Override
	public boolean createProcInstTaskInstHisPo(ProcInstTaskInstHisPo po)throws Exception{
		if(po==null){
			return false;
		}
		create(ProcInstTaskInstHisPo.class,po);
		return true;
	}

	@Override
	public List<ProcInstTaskInstHisPo> queryTaskInstHisByOrderNo1(ProcInstTaskInstHisPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryTaskInstHisByOrderNo1").filter(po.convertThis2Map());
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public ProcInstTaskInstHisPo queryProcInstTaskInstHisByOrderNo(
			ProcInstTaskInstHisPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskInstHisByOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskInstHisPo> list= query(ProcInstTaskInstHisPo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public List<ProcInstTaskInstHisPo> queryProcInstTaskInstHisByTacheCode(ProcInstTaskInstHisPo po) throws Exception{

		Condition con = Condition.build("queryProcInstTaskInstHisByTacheCode").filter(po.convertThis2Map());
		List<ProcInstTaskInstHisPo> list= query(ProcInstTaskInstHisPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}

	}

	@Override
	public int queryTotalCountTaskHis(ProcInstTaskInstHisPo po, String startTime,
			String endTime,String state) throws Exception {
		Map<String,Object> map = po.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryTotalCountTaskHis").filter(map);
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountNotOverdueTaskHis(ProcInstTaskInstHisPo po,
			String startTime, String endTime,String state) throws Exception {
		Map<String,Object> map = po.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountNotOverdueTaskHis").filter(map);
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueLessOneHourTaskHis(ProcInstTaskInstHisPo po,
			String startTime, String endTime,String state) throws Exception {
		Map<String,Object> map = po.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueLessOneHourTaskHis").filter(map);
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueOneToTwoHourTaskHis(ProcInstTaskInstHisPo po,
			String startTime, String endTime,String state) throws Exception {
		Map<String,Object> map = po.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueOneToTwoHourTaskHis").filter(map);
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueTwoToTwelveHourTaskHis(
			ProcInstTaskInstHisPo po, String startTime, String endTime,String state)
			throws Exception {
		Map<String,Object> map = po.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueTwoToTwelveHourTaskHis").filter(map);
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueTwelveToTwentyFourHourTaskHis(
			ProcInstTaskInstHisPo po, String startTime, String endTime,String state)
			throws Exception {
		Map<String,Object> map = po.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueTwelveToTwentyFourHourTaskHis").filter(map);
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueMoreTwentyFourHourTaskHis(
			ProcInstTaskInstHisPo po, String startTime, String endTime,String state)
			throws Exception {
		Map<String,Object> map = po.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueMoreTwentyFourHourTaskHis").filter(map);
		List<ProcInstTaskInstHisPo> list = query(ProcInstTaskInstHisPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public List<ProcInstTaskInstHisPo> queryAllTaskInstHisGroupByTacheCode(
			ProcInstTaskInstHisPo po) throws Exception {
		Condition con = Condition.build("queryAllTaskInstHisGroupByTacheCode").filter(po.convertThis2Map());
		List<ProcInstTaskInstHisPo> list= query(ProcInstTaskInstHisPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public int queryCountAllTaskInstHisGroupByTacheCode(ProcInstTaskInstHisPo po)
			throws Exception {
		Condition con = Condition.build("queryCountAllTaskInstHisGroupByTacheCode").filter(po.convertThis2Map());
		List<ProcInstTaskInstHisPo> list= query(ProcInstTaskInstHisPo.class,con);
		if(list !=null&&list.size()>0){
			return list.size() ;
		}
		else{
			return 0 ;
		}
	}




}
