package com.tydic.unicom.uoc.business.common.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.uoc.business.common.interfaces.StatisticalTaskOverdueRateServDu;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcInstTaskInstServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.his.interfaces.ProcInstTaskInstHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.ProcInstTaskInstHisVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class StatisticalTaskOverdueRateServDuImpl implements StatisticalTaskOverdueRateServDu{

	private static Logger logger = Logger.getLogger(StatisticalTaskOverdueRateServDuImpl.class);
	
	@Autowired
	private OperServDu operServDu;
	
	@Autowired
	private ProcInstTaskInstServDu procInstTaskInstServDu;
	@Autowired
	private ProcInstTaskInstHisServDu procInstTaskInstHisServDu;
	
	@Override
	public UocMessage getStatisticalTaskOverdueRate(ParaVo paraVo) throws Exception {
		logger.info("================任务逾期率统计(rest)===============");
		
		UocMessage uocMessage = new UocMessage();
		String startTime = paraVo.getStart_time();
		String endTime = paraVo.getEnd_time();
		String jsessionId = paraVo.getJsession_id();
		String departNo = paraVo.getDepart_no();
		String startMonth = "";
		String endModth = "";
		String state=paraVo.getState();
		if(StringUtils.isEmpty(jsessionId)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		
		if(StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
			startTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00";
			endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 23:59:59";
			startMonth = startTime.split("-")[1];
		}
		//校验是否夸月
		else if((!StringUtils.isEmpty(startTime)) && (!StringUtils.isEmpty(endTime))){
			startMonth = startTime.split("-")[1];
			endModth = endTime.split("-")[1];
			if(!startMonth.equals(endModth)){
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("开始时间与结束时间不能跨月");
				return uocMessage;
			}
		}
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("开始时间与结束时间不能为空");
			return uocMessage;
		}
		
		//处理月份信息
		String submonth = startMonth.substring(0, 1);
		if("0".equals(submonth)){
			startMonth = startMonth.substring(1, 2);
		}
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(paraVo.getJsession_id());
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		Map<String,Object> oper_info =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");
		
		//校验传入的部门是否为空
		if(StringUtils.isEmpty(departNo)){
			departNo = oper_info.get("depart_no").toString();
		}
		//逾期0-1小时任务数
		ProcInstTaskInstVo procInstTaskInstVoLateNum1 = setProcInstTaskInstVoData(departNo,paraVo.getOper_no(),
				paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
				oper_info.get("area_code").toString(),startMonth);
		int late_num1 = procInstTaskInstServDu.queryCountOverdueLessOneHour(procInstTaskInstVoLateNum1, startTime, endTime,state);
		//逾期1-2小时任务数
		ProcInstTaskInstVo procInstTaskInstVoLateNum2 = setProcInstTaskInstVoData(departNo,paraVo.getOper_no(),
				paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
				oper_info.get("area_code").toString(),startMonth);
		int late_num2 = procInstTaskInstServDu.queryCountOverdueOneToTwoHour(procInstTaskInstVoLateNum2, startTime, endTime,state);
		//逾期2-12小时任务数
		ProcInstTaskInstVo procInstTaskInstVoLateNum3 = setProcInstTaskInstVoData(departNo,paraVo.getOper_no(),
				paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
				oper_info.get("area_code").toString(),startMonth);
		int late_num3 = procInstTaskInstServDu.queryCountOverdueTwoToTwelveHour(procInstTaskInstVoLateNum3, startTime, endTime,state);
		//逾期12-24小时任务数
		ProcInstTaskInstVo procInstTaskInstVoLateNum4 = setProcInstTaskInstVoData(departNo,paraVo.getOper_no(),
				paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
				oper_info.get("area_code").toString(),startMonth);
		int late_num4 = procInstTaskInstServDu.queryCountOverdueTwelveToTwentyFourHour(procInstTaskInstVoLateNum4, startTime, endTime,state);
		//逾期时间大于24小时任务数
		ProcInstTaskInstVo procInstTaskInstVoLateNum5 = setProcInstTaskInstVoData(departNo,paraVo.getOper_no(),
				paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
				oper_info.get("area_code").toString(),startMonth);
		int late_num5 = procInstTaskInstServDu.queryCountOverdueMoreTwentyFourHour(procInstTaskInstVoLateNum5, startTime, endTime,state);
		//未逾期任务数
		ProcInstTaskInstVo procInstTaskInstVoOtherNum = setProcInstTaskInstVoData(departNo,paraVo.getOper_no(),
				paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
				oper_info.get("area_code").toString(),startMonth);
		int other_num = procInstTaskInstServDu.queryCountNotOverdue(procInstTaskInstVoOtherNum, startTime, endTime,state);
		//任务总数
		ProcInstTaskInstVo procInstTaskInstVoTotalNum = setProcInstTaskInstVoData(departNo,paraVo.getOper_no(),
				paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
				oper_info.get("area_code").toString(),startMonth);
		int total_num = procInstTaskInstServDu.queryCountTaskAll(procInstTaskInstVoTotalNum, startTime, endTime,state);
		
		/**
		 * 统计逾期占比时，筛选条件增加未完成任务、已完成任务、全部任务，默认选择“未完成任务”，
		 * 并修改服务UOC0054增加state入参，注意统计已完成任务跟全部任务时需要同时去历史表中查询对应数据
		 */
		if(!StringUtils.isEmpty(state)&&("1".equals(state)||"2".equals(state))){
			ProcInstTaskInstHisVo procInstTaskInstHisVo=setProcInstTaskInstHisVoData(departNo,paraVo.getOper_no(),
					paraVo.getOper_code(),paraVo.getProd_code(),paraVo.getTache_code(),oper_info.get("province_code").toString(),
					oper_info.get("area_code").toString(),startMonth);
			
			total_num = total_num+procInstTaskInstHisServDu.queryTotalCountTaskHis(procInstTaskInstHisVo, startTime, endTime,state);
			other_num = other_num+procInstTaskInstHisServDu.queryCountNotOverdueTaskHis(procInstTaskInstHisVo, startTime, endTime,state);
			late_num5 = late_num5+procInstTaskInstHisServDu.queryCountOverdueMoreTwentyFourHourTaskHis(procInstTaskInstHisVo, startTime, endTime,state);
			late_num4 = late_num4+procInstTaskInstHisServDu.queryCountOverdueTwelveToTwentyFourHourTaskHis(procInstTaskInstHisVo, startTime, endTime,state);
			late_num3 = late_num3+procInstTaskInstHisServDu.queryCountOverdueTwoToTwelveHourTaskHis(procInstTaskInstHisVo, startTime, endTime,state);
			late_num2 = late_num2+procInstTaskInstHisServDu.queryCountOverdueOneToTwoHourTaskHis(procInstTaskInstHisVo, startTime, endTime,state);
			late_num1 = late_num1+procInstTaskInstHisServDu.queryCountOverdueLessOneHourTaskHis(procInstTaskInstHisVo, startTime, endTime,state);
			
		}
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.addArg("late_num1", late_num1);
		uocMessage.addArg("late_num2", late_num2);
		uocMessage.addArg("late_num3", late_num3);
		uocMessage.addArg("late_num4", late_num4);
		uocMessage.addArg("late_num5", late_num5);
		uocMessage.addArg("other_num", other_num);
		uocMessage.addArg("total_num", total_num);
		return uocMessage;
	}

	public ProcInstTaskInstVo setProcInstTaskInstVoData(String accept_depart_no,String accept_oper_no,String oper_code,String proc_code,
			String tache_code,String province_code,String area_code,String part_month) throws Exception{
		ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
		procInstTaskInstVo.setAccept_depart_no(accept_depart_no);
		procInstTaskInstVo.setAccept_oper_no(accept_oper_no);
		procInstTaskInstVo.setOper_code(oper_code);
		procInstTaskInstVo.setProd_code(proc_code);
		procInstTaskInstVo.setTache_code(tache_code);
		procInstTaskInstVo.setProvince_code(province_code);
		procInstTaskInstVo.setArea_code(area_code);
		procInstTaskInstVo.setPart_month(part_month);
		return procInstTaskInstVo;
	}
	
	public ProcInstTaskInstHisVo setProcInstTaskInstHisVoData(String accept_depart_no,String accept_oper_no,String oper_code,String proc_code,
			String tache_code,String province_code,String area_code,String part_month) throws Exception{
		ProcInstTaskInstHisVo procInstTaskInstHisVo = new ProcInstTaskInstHisVo();
		procInstTaskInstHisVo.setAccept_depart_no(accept_depart_no);
		procInstTaskInstHisVo.setAccept_oper_no(accept_oper_no);
		procInstTaskInstHisVo.setOper_code(oper_code);
		procInstTaskInstHisVo.setProd_code(proc_code);
		procInstTaskInstHisVo.setTache_code(tache_code);
		procInstTaskInstHisVo.setProvince_code(province_code);
		procInstTaskInstHisVo.setArea_code(area_code);
		procInstTaskInstHisVo.setPart_month(part_month);
		return procInstTaskInstHisVo;
	}
}
