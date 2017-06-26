package com.tydic.unicom.uoc.service.mod.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcInstTaskInstServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;

@Service("ProcInstTaskInstServDu")
public class ProcInstTaskInstServDuImpl implements ProcInstTaskInstServDu{

	Logger logger = Logger.getLogger(ProcInstTaskInstServDuImpl.class);
	
	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;
	
	@Override
	public ProcInstTaskInstVo queryProcInstTaskInstByTacheCodeAndProcInstId(ProcInstTaskInstVo procInstTaskInstVo,String ord) throws Exception{
		ProcInstTaskInstPo procPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procPo);
		procPo =  procInstTaskInstServ.queryProcInstTaskInstByTacheCodeAndProcInstId(procPo,ord);
		BeanUtils.copyProperties(procPo, procInstTaskInstVo);
		return procInstTaskInstVo;
	}

	@Override
	public boolean create(ProcInstTaskInstVo procInstTaskInstVo) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.create(procInstTaskInstPo);
	}

	@Override
	public ProcInstTaskInstVo queryProcInstTaskInstByOrderNoAndTaskState(
			ProcInstTaskInstVo procInstTaskInstVo) throws Exception {
		ProcInstTaskInstPo procPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procPo);
		procPo =  procInstTaskInstServ.queryProcInstTaskInstByOrderNoAndTaskState(procPo);
		if(procPo ==null){
			return null;
		}
		BeanUtils.copyProperties(procPo, procInstTaskInstVo);
		return procInstTaskInstVo;
	}

	@Override
	public List<ProcInstTaskInstVo> queryProcInstTaskInstByOrderNo(ProcInstTaskInstVo procInstTaskInstVo) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		List<ProcInstTaskInstPo> list = procInstTaskInstServ.queryProcInstTaskInstByOrderNo(procInstTaskInstPo);
		if(list != null && list.size()>0){
			List<ProcInstTaskInstVo> listOut = new ArrayList<ProcInstTaskInstVo>();
			for(int i=0;i<list.size();i++){
				ProcInstTaskInstVo procInstTaskInstVoOut = new ProcInstTaskInstVo();
				BeanUtils.copyProperties(list.get(i), procInstTaskInstVoOut);
				listOut.add(procInstTaskInstVoOut);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public ProcInstTaskInstVo queryProcInstTaskInstByTaskState(
			ProcInstTaskInstVo procInstTaskInstVo) throws Exception {
		ProcInstTaskInstPo procPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procPo);
		procPo =  procInstTaskInstServ.queryProcInstTaskInstByTaskState(procPo);
		if(procPo ==null){
			return null;
		}
		BeanUtils.copyProperties(procPo, procInstTaskInstVo);
		return procInstTaskInstVo;
	}

	@Override
	public boolean updateByOrderNo(ProcInstTaskInstVo procInstTaskInstVo) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.updateByOrderNo(procInstTaskInstPo);
	}

	@Override
	public int queryCountOverdueLessOneHour(ProcInstTaskInstVo procInstTaskInstVo, String startTime,String endTime,String state) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.queryCountOverdueLessOneHour(procInstTaskInstPo, startTime, endTime,state);
	}

	@Override
	public int queryCountOverdueOneToTwoHour(ProcInstTaskInstVo procInstTaskInstVo, String startTime,String endTime,String state) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.queryCountOverdueOneToTwoHour(procInstTaskInstPo, startTime, endTime,state);
	}

	@Override
	public int queryCountOverdueTwoToTwelveHour(ProcInstTaskInstVo procInstTaskInstVo, String startTime,String endTime,String state) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.queryCountOverdueTwoToTwelveHour(procInstTaskInstPo, startTime, endTime,state);
	}

	@Override
	public int queryCountOverdueTwelveToTwentyFourHour(ProcInstTaskInstVo procInstTaskInstVo, String startTime,String endTime,String state) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.queryCountOverdueTwelveToTwentyFourHour(procInstTaskInstPo, startTime, endTime,state);
	}

	@Override
	public int queryCountOverdueMoreTwentyFourHour(ProcInstTaskInstVo procInstTaskInstVo, String startTime,String endTime,String state) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.queryCountOverdueMoreTwentyFourHour(procInstTaskInstPo, startTime, endTime,state);
	}

	@Override
	public int queryCountNotOverdue(ProcInstTaskInstVo procInstTaskInstVo,String startTime, String endTime,String state) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.queryCountNotOverdue(procInstTaskInstPo, startTime, endTime,state);
	}

	@Override
	public int queryCountTaskAll(ProcInstTaskInstVo procInstTaskInstVo,String startTime, String endTime,String state) throws Exception {
		ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, procInstTaskInstPo);
		return procInstTaskInstServ.queryCountTaskAll(procInstTaskInstPo, startTime, endTime,state);
	}

}
