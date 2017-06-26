package com.tydic.unicom.uoc.service.order.his.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskInstHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskInstHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.ProcInstTaskInstHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.ProcInstTaskInstHisVo;
@Service("ProcInstTaskInstHisServDu")
public class ProcInstTaskInstHisServDuImpl implements ProcInstTaskInstHisServDu{

	@Autowired
	private ProcInstTaskInstHisServ procInstTaskInstHisServ;
	@Override
	public ProcInstTaskInstHisVo queryProcInstTaskInstHisByOrderNo(
			ProcInstTaskInstHisVo vo) throws Exception {
		ProcInstTaskInstHisPo ProcInstTaskInstHis = new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, ProcInstTaskInstHis);
		ProcInstTaskInstHisPo po = procInstTaskInstHisServ.queryProcInstTaskInstHisByOrderNo(ProcInstTaskInstHis);
		if(po!=null){
			ProcInstTaskInstHisVo orderVo = new ProcInstTaskInstHisVo();
			BeanUtils.copyProperties(po, orderVo);
			return orderVo;
		}else{
			return null;
		}		
	}
	@Override
	public int queryTotalCountTaskHis(ProcInstTaskInstHisVo vo, String startTime,
			String endTime,String state) throws Exception {
		ProcInstTaskInstHisPo po=new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, po);
		return procInstTaskInstHisServ.queryTotalCountTaskHis(po, startTime, endTime,state);
	}
	@Override
	public int queryCountNotOverdueTaskHis(ProcInstTaskInstHisVo vo,
			String startTime, String endTime,String state) throws Exception {
		ProcInstTaskInstHisPo po=new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, po);
		return procInstTaskInstHisServ.queryCountNotOverdueTaskHis(po, startTime, endTime,state);
	}
	@Override
	public int queryCountOverdueLessOneHourTaskHis(ProcInstTaskInstHisVo vo,
			String startTime, String endTime,String state) throws Exception {
		ProcInstTaskInstHisPo po=new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, po);
		return procInstTaskInstHisServ.queryCountOverdueLessOneHourTaskHis(po, startTime, endTime,state);
	}
	@Override
	public int queryCountOverdueOneToTwoHourTaskHis(ProcInstTaskInstHisVo vo,
			String startTime, String endTime,String state) throws Exception {
		ProcInstTaskInstHisPo po=new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, po);
		return procInstTaskInstHisServ.queryCountOverdueOneToTwoHourTaskHis(po, startTime, endTime,state);
	}
	@Override
	public int queryCountOverdueTwoToTwelveHourTaskHis(
			ProcInstTaskInstHisVo vo, String startTime, String endTime,String state)
			throws Exception {
		ProcInstTaskInstHisPo po=new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, po);
		return procInstTaskInstHisServ.queryCountOverdueTwoToTwelveHourTaskHis(po, startTime, endTime,state);
	}
	@Override
	public int queryCountOverdueTwelveToTwentyFourHourTaskHis(
			ProcInstTaskInstHisVo vo, String startTime, String endTime,String state)
			throws Exception {
		ProcInstTaskInstHisPo po=new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, po);
		return procInstTaskInstHisServ.queryCountOverdueTwelveToTwentyFourHourTaskHis(po, startTime, endTime,state);
	}
	@Override
	public int queryCountOverdueMoreTwentyFourHourTaskHis(
			ProcInstTaskInstHisVo vo, String startTime, String endTime,String state)
			throws Exception {
		ProcInstTaskInstHisPo po=new ProcInstTaskInstHisPo();
		BeanUtils.copyProperties(vo, po);
		return procInstTaskInstHisServ.queryCountOverdueMoreTwentyFourHourTaskHis(po, startTime, endTime,state);
	}

}
