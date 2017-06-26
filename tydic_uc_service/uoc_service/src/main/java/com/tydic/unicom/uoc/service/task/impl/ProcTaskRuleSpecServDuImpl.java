package com.tydic.unicom.uoc.service.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskRuleSpecPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTaskRuleSpecServ;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleSpecVo;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleSpecServDu;

@Service("ProcTaskRuleSpecServDu")
public class ProcTaskRuleSpecServDuImpl implements ProcTaskRuleSpecServDu {

	@Autowired
	private ProcTaskRuleSpecServ procTaskRuleSpecServ;
	@Override
	public boolean create(ProcTaskRuleSpecVo vo) throws Exception {
		ProcTaskRuleSpecPo po=new ProcTaskRuleSpecPo();
		BeanUtils.copyProperties(vo, po);
		boolean res=procTaskRuleSpecServ.create(po);
		return res;
	}

	@Override
	public boolean delete(ProcTaskRuleSpecVo vo) throws Exception {
		ProcTaskRuleSpecPo po=new ProcTaskRuleSpecPo();
		BeanUtils.copyProperties(vo, po);
		boolean res=procTaskRuleSpecServ.delete(po);
		return res;
	}

	@Override
	public boolean update(ProcTaskRuleSpecVo vo) throws Exception {
		ProcTaskRuleSpecPo po=new ProcTaskRuleSpecPo();
		BeanUtils.copyProperties(vo, po);
		boolean res=procTaskRuleSpecServ.update(po);
		return res;
	}

	@Override
	public ProcTaskRuleSpecVo queryProcTaskRuleSpecById(ProcTaskRuleSpecVo vo)
			throws Exception {
		ProcTaskRuleSpecPo po = new ProcTaskRuleSpecPo();
		BeanUtils.copyProperties(vo, po);
		ProcTaskRuleSpecPo specRulePo=procTaskRuleSpecServ.queryProcTaskRuleSpecById(po);
		ProcTaskRuleSpecVo specRuleVo=new ProcTaskRuleSpecVo();
		if (specRulePo != null) {
			BeanUtils.copyProperties(specRulePo, specRuleVo);
		} else {
			return null;
		}
		return specRuleVo;
	}

	@Override
	public List<ProcTaskRuleSpecVo> queryProcTaskRuleSpecByWeb(ProcTaskRuleSpecVo vo) throws Exception {
		ProcTaskRuleSpecPo po = new ProcTaskRuleSpecPo();
		BeanUtils.copyProperties(vo, po);
		List<ProcTaskRuleSpecPo> listPo = procTaskRuleSpecServ.queryProcTaskRuleSpecByWeb(po);
		List<ProcTaskRuleSpecVo> listVo = new ArrayList<ProcTaskRuleSpecVo>();
		if(listPo != null && listPo.size()>0){
			for(ProcTaskRuleSpecPo specRulePo : listPo){
				ProcTaskRuleSpecVo specRuleVo = new ProcTaskRuleSpecVo();
				BeanUtils.copyProperties(specRulePo, specRuleVo);
				listVo.add(specRuleVo);
			}
			return listVo;
		}
		return null;
	}

	@Override
	public int queryProcTaskRuleSpecCount(ProcTaskRuleSpecVo vo)
			throws Exception {
		ProcTaskRuleSpecPo po = new ProcTaskRuleSpecPo();
		BeanUtils.copyProperties(vo, po);
		int res=procTaskRuleSpecServ.queryProcTaskRuleSpecCount(po);
		return res;
	}

	@Override
	public List<ProcTaskRuleSpecVo> queryTaskRuleSpecGroupByRuleId(String rule_id) throws Exception {
		ProcTaskRuleSpecPo po = new ProcTaskRuleSpecPo();
		po.setRule_id(rule_id);
		List<ProcTaskRuleSpecPo> specRuleList = procTaskRuleSpecServ.queryTaskRuleSpecGroupByRuleId(po);
		List<ProcTaskRuleSpecVo> resultList = new ArrayList<ProcTaskRuleSpecVo>();
		if (specRuleList != null && specRuleList.size() > 0) {
			for (ProcTaskRuleSpecPo resultPo : specRuleList) {
				ProcTaskRuleSpecVo specRuleVo = new ProcTaskRuleSpecVo();
				BeanUtils.copyProperties(resultPo, specRuleVo);
				resultList.add(specRuleVo);
			}
			return resultList;
		}
		return null;
	}

}
