package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskDealRecordHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskDealRecordHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskDealRecordVo;
import com.tydic.unicom.uoc.service.order.interfaces.ProcInstTaskDealRecordServDu;
@Service("ProcInstTaskDealRecordServDu")
public class ProcInstTaskDealRecordServDuImpl implements ProcInstTaskDealRecordServDu{

	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;
	@Autowired
	private ProcInstTaskDealRecordHisServ procInstTaskDealRecordHisServ;
	@Override
	public List<ProcInstTaskDealRecordVo> queryProcInstTaskDealRecordByOrderNo(
			ProcInstTaskDealRecordVo vo) throws Exception {
		ProcInstTaskDealRecordPo procInstTaskDealRecord = new ProcInstTaskDealRecordPo();
		BeanUtils.copyProperties(vo, procInstTaskDealRecord);
		List<ProcInstTaskDealRecordPo> listPo = procInstTaskDealRecordServ.queryProcInstTaskDealRecordByOrderNo(procInstTaskDealRecord);
		List<ProcInstTaskDealRecordVo> listVo = new ArrayList<ProcInstTaskDealRecordVo>();
		if(listPo != null && listPo.size()>0){
			for(ProcInstTaskDealRecordPo poTemp : listPo){
				ProcInstTaskDealRecordVo procVo = new ProcInstTaskDealRecordVo();
				BeanUtils.copyProperties(poTemp, procVo);
				listVo.add(procVo);
			}
		}else{
			return null;
		}
		return listVo;
	}

	@Override
	public List<ProcInstTaskDealRecordVo> queryTaskDealRecordHisByOrderNo(ProcInstTaskDealRecordVo vo) throws Exception {
		ProcInstTaskDealRecordHisPo procInstTaskDealRecord = new ProcInstTaskDealRecordHisPo();
		BeanUtils.copyProperties(vo, procInstTaskDealRecord);
		List<ProcInstTaskDealRecordHisPo> listPo = procInstTaskDealRecordHisServ.queryTaskDealRecordHisByOrderNo(procInstTaskDealRecord);
		List<ProcInstTaskDealRecordVo> listVo = new ArrayList<ProcInstTaskDealRecordVo>();
		if (listPo != null && listPo.size() > 0) {
			for (ProcInstTaskDealRecordHisPo poTemp : listPo) {
				ProcInstTaskDealRecordVo procVo = new ProcInstTaskDealRecordVo();
				BeanUtils.copyProperties(poTemp, procVo);
				listVo.add(procVo);
			}
		} else {
			return null;
		}
		return listVo;
	}

}
