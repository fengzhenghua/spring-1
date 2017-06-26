package com.tydic.unicom.uoc.service.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskRuleDepartPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTaskRuleDepartServ;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleDepartVo;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleDepartServDu;

@Service("ProcTaskRuleDepartServDu")
public class ProcTaskRuleDepartServDuImpl implements ProcTaskRuleDepartServDu {

	@Autowired
	private ProcTaskRuleDepartServ procTaskRuleDepartServ;

	@Override
	public boolean create(ProcTaskRuleDepartVo vo) throws Exception {
		ProcTaskRuleDepartPo po = new ProcTaskRuleDepartPo();
		BeanUtils.copyProperties(vo, po);
		return procTaskRuleDepartServ.create(po);
	}

	@Override
	public boolean delete(ProcTaskRuleDepartVo vo) throws Exception {
		ProcTaskRuleDepartPo po = new ProcTaskRuleDepartPo();
		BeanUtils.copyProperties(vo, po);
		return procTaskRuleDepartServ.delete(po);
	}

	@Override
	public boolean update(ProcTaskRuleDepartVo vo) throws Exception {
		ProcTaskRuleDepartPo po = new ProcTaskRuleDepartPo();
		BeanUtils.copyProperties(vo, po);
		return procTaskRuleDepartServ.update(po);
	}

	@Override
	public List<ProcTaskRuleDepartVo> queryProcTaskRuleDepartByWeb(ProcTaskRuleDepartVo vo) throws Exception {
		ProcTaskRuleDepartPo po = new ProcTaskRuleDepartPo();
		BeanUtils.copyProperties(vo, po);
		List<ProcTaskRuleDepartPo> listPo = procTaskRuleDepartServ.queryProcTaskRuleDepartByWeb(po);
		List<ProcTaskRuleDepartVo> listVo = new ArrayList<ProcTaskRuleDepartVo>();
		if (listPo != null && listPo.size() > 0) {
			for (ProcTaskRuleDepartPo departRulePo : listPo) {
				ProcTaskRuleDepartVo departRuleVo = new ProcTaskRuleDepartVo();
				BeanUtils.copyProperties(departRulePo, departRuleVo);
				listVo.add(departRuleVo);
			}
			return listVo;
		}
		return null;
	}

	@Override
	public int queryProcTaskRuleDepartCount(ProcTaskRuleDepartVo vo) throws Exception {
		ProcTaskRuleDepartPo po = new ProcTaskRuleDepartPo();
		BeanUtils.copyProperties(vo, po);
		return procTaskRuleDepartServ.queryProcTaskRuleDepartCount(po);
	}

	@Override
	public List<ProcTaskRuleDepartVo> queryProcTaskRuleDepartByVo(ProcTaskRuleDepartVo vo) throws Exception {
		ProcTaskRuleDepartPo po = new ProcTaskRuleDepartPo();
		BeanUtils.copyProperties(vo, po);
		List<ProcTaskRuleDepartPo> departRuleList = procTaskRuleDepartServ.queryProcTaskRuleDepartByPo(po);
		List<ProcTaskRuleDepartVo> listVo = new ArrayList<ProcTaskRuleDepartVo>();
		if (departRuleList != null && departRuleList.size() > 0) {
			for (ProcTaskRuleDepartPo departRulePo : departRuleList) {
				ProcTaskRuleDepartVo departRuleVo = new ProcTaskRuleDepartVo();
				BeanUtils.copyProperties(departRulePo, departRuleVo);
				listVo.add(departRuleVo);
			}
			return listVo;
		}
		return null;
	}

}
