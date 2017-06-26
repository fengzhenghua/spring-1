package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.ArtificialTaskPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ProcInstTaskInstServ")
public class ProcInstTaskInstServImpl extends BaseServImpl<ProcInstTaskInstPo> implements ProcInstTaskInstServ{

	Logger logger = Logger.getLogger(ProcInstTaskInstServImpl.class);
	@Override
	public ProcInstTaskInstPo queryProcInstTaskInstByTacheCodeAndProcInstId(
			ProcInstTaskInstPo procInstTaskInstPo,String ord) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(ord);
		procInstTaskInstPo.setPart_month(strMap.get("part_month"));
		procInstTaskInstPo.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskInstByTacheCodeAndProcInstId").filter(procInstTaskInstPo.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);

	}

	@Override
	public boolean create(ProcInstTaskInstPo procInstTaskInstPo) throws Exception {
		create(ProcInstTaskInstPo.class,procInstTaskInstPo);
		return true;
	}

	@Override
	public ProcInstTaskInstPo queryProcInstTaskInstByOrderNoAndTaskState(
			ProcInstTaskInstPo procInstTaskInstPo) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(procInstTaskInstPo.getOrder_no());
		procInstTaskInstPo.setPart_month(strMap.get("part_month"));
		procInstTaskInstPo.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskInstByOrderNoAndTaskState").filter(procInstTaskInstPo.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public List<ProcInstTaskInstPo> queryProcInstTaskInstByOrderNo(ProcInstTaskInstPo procInstTaskInstPo)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(procInstTaskInstPo.getOrder_no());
		procInstTaskInstPo.setPart_month(strMap.get("part_month"));
		procInstTaskInstPo.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskInstByOrderNo").filter(procInstTaskInstPo.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public boolean deleteProcInstTaskInstByOrderNo(ProcInstTaskInstPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("deleteProcInstTaskInstByOrderNo").filter(po.convertThis2Map());
		int i=S.get(ProcInstTaskInstPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateByOrderNo(ProcInstTaskInstPo procInstTaskInstPo) throws Exception{
		Condition condition = Condition.build("updateTaskInstByPo").filter(procInstTaskInstPo.convertThis2Map());
		int i = S.get(ProcInstTaskInstPo.class).update(condition);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ProcInstTaskInstPo> queryProcInstTaskInst(ProcInstTaskInstPo procInstTaskInstPo) throws Exception{
		Condition con = Condition.build("queryProcInstTaskInst").filter(procInstTaskInstPo.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public ProcInstTaskInstPo queryProcInstTaskInstByTaskState(
			ProcInstTaskInstPo procInstTaskInstPo) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(procInstTaskInstPo.getOrder_no());
		procInstTaskInstPo.setPart_month(strMap.get("part_month"));
		procInstTaskInstPo.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskInstByTaskState").filter(procInstTaskInstPo.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public ProcInstTaskInstPo queryProcInstTaskInstByTaskId(String task_id) throws Exception {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("task_id", task_id);
		Condition con = Condition.build("queryProcInstTaskInstByTaskId").filter(param);
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public List<ProcInstTaskInstPo> querytaskInstByOrderNoAndTacheCode(String order_no, String tache_code)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("order_no", order_no);
		param.put("tache_code", tache_code);
		param.put("part_month", strMap.get("part_month"));
		param.put("area_code",strMap.get("area_code"));
		Condition con = Condition.build("querytaskInstByOrderNoAndTacheCode").filter(param);
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		return (list==null||list.isEmpty())?null:list;
	}

	@Override
	public List<ProcInstTaskInstPo> queryProcInstTaskInstByTacheCode(ProcInstTaskInstPo po) throws Exception{
		Condition con = Condition.build("queryProcInstTaskInstByTacheCode").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public int countProcInstTaskInst(
			ProcInstTaskInstPo procInstTaskInstPo) throws Exception {
		Condition con = Condition.build("countProcInstTaskInst").filter(procInstTaskInstPo.convertThis2Map());
		int totalNum= S.get(ProcInstTaskInstPo.class).queryFor().queryForInt(con);
		return totalNum;
	}

	@Override
	public List<ProcInstTaskInstPo> queryProcInstTaskInstListByServOrderNo(ArtificialTaskPo po,int pageNo,int pageSize) throws Exception{
		Condition con = Condition.build("queryProcInstTaskInstListByServOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list=  S.get(ProcInstTaskInstPo. class).page(con ,pageNo ,pageSize );
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}

	}

	@Override
	public int queryProcInstTaskInstListCountByServOrderNo(ArtificialTaskPo po) throws Exception{
		logger.info("person_flag"+po.getPerson_flag());
		Condition con = Condition.build("queryProcInstTaskInstListCountByServOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		return list.size();
	}


	@Override
	public List<ProcInstTaskInstPo> queryProcInstTaskInstListBySaleOrderNo(ArtificialTaskPo po,int pageNo,int pageSize) throws Exception{
		Condition con = Condition.build("queryProcInstTaskInstListBySaleOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list= S.get(ProcInstTaskInstPo. class).page(con ,pageNo ,pageSize );
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}

	}

	@Override
	public int queryProcInstTaskInstListCountBySaleOrderNo(ArtificialTaskPo po) throws Exception{
		Condition con = Condition.build("queryProcInstTaskInstListCountBySaleOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		return list.size();
	}

	@Override
	public List<ProcInstTaskInstPo> queryDepartTaskInstByCode(ProcInstTaskInstPo po) throws Exception {
		Condition con = Condition.build("queryDepartTaskInstByCode").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		return (list == null || list.isEmpty()) ? null : list;
	}

	@Override
	public List<ProcInstTaskInstPo> queryAllTaskInstGroupByTacheCode(ArtificialTaskPo po) throws Exception{
		Condition con = Condition.build("queryAllTaskInstGroupByTacheCode").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public int queryCountAllTaskInstGroupByTacheCode(ArtificialTaskPo po) throws Exception{
		Condition con = Condition.build("queryCountAllTaskInstGroupByTacheCode").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list= query(ProcInstTaskInstPo.class,con);
		if(list !=null&&list.size()>0){
			return list.size() ;
		}
		else{
			return 0 ;
		}
	}



	@Override
	public List<ProcInstTaskInstPo> queryTaskInstByTask(ProcInstTaskInstPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryTaskInstByTask").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public int queryCountOverdueLessOneHour(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception {
		Map<String,Object> map = procInstTaskInstPo.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueLessOneHour").filter(map);
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueOneToTwoHour(ProcInstTaskInstPo procInstTaskInstPo, String startTime,String endTime,String state) throws Exception {
		Map<String,Object> map = procInstTaskInstPo.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueOneToTwoHour").filter(map);
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueTwoToTwelveHour(ProcInstTaskInstPo procInstTaskInstPo, String startTime,String endTime,String state) throws Exception {
		Map<String,Object> map = procInstTaskInstPo.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueTwoToTwelveHour").filter(map);
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueTwelveToTwentyFourHour(ProcInstTaskInstPo procInstTaskInstPo, String startTime,String endTime,String state) throws Exception {
		Map<String,Object> map = procInstTaskInstPo.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueTwelveToTwentyFourHour").filter(map);
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountOverdueMoreTwentyFourHour(ProcInstTaskInstPo procInstTaskInstPo, String startTime,String endTime,String state) throws Exception {
		Map<String,Object> map = procInstTaskInstPo.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountOverdueMoreTwentyFourHour").filter(map);
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountNotOverdue(ProcInstTaskInstPo procInstTaskInstPo,String startTime, String endTime,String state) throws Exception {
		Map<String,Object> map = procInstTaskInstPo.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountNotOverdue").filter(map);
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public int queryCountTaskAll(ProcInstTaskInstPo procInstTaskInstPo,String startTime, String endTime,String state) throws Exception {
		Map<String,Object> map = procInstTaskInstPo.convertThis2Map();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("state", state);
		Condition con = Condition.build("queryCountTaskAll").filter(map);
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if(list != null && list.size()>0){
			return list.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public List<ProcInstTaskInstPo> queryTaskInstByOrderNo1(
			ProcInstTaskInstPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryTaskInstByOrderNo1").filter(po.convertThis2Map());
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<ProcInstTaskInstPo> queryTaskInstForCancleOrder(ProcInstTaskInstPo procInstTaskInstPo) throws Exception {
		Condition con = Condition.build("queryTaskInstForCancleOrder").filter(procInstTaskInstPo.convertThis2Map());
		List<ProcInstTaskInstPo> list = query(ProcInstTaskInstPo.class, con);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

}
