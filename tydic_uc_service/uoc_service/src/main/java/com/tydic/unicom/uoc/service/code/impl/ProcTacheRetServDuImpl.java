package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.ProcTacheRetPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTacheRetServ;
import com.tydic.unicom.uoc.service.code.interfaces.ProcTacheRetServDu;
import com.tydic.unicom.uoc.service.code.vo.ProcTacheRetVo;

@Service("ProcTacheRetServDu")
public class ProcTacheRetServDuImpl implements ProcTacheRetServDu {

	@Autowired
	private ProcTacheRetServ procTacheRetServ;

	@Override
	public List<ProcTacheRetVo> queryProcTacheRetByVo(ProcTacheRetVo vo) throws Exception {
		ProcTacheRetPo paramPo = new ProcTacheRetPo();
		BeanUtils.copyProperties(vo, paramPo);
		List<ProcTacheRetPo> listPo = procTacheRetServ.queryProcTacheRetByPo(paramPo);
		List<ProcTacheRetVo> listVo = new ArrayList<ProcTacheRetVo>();
		if (listPo != null && listPo.size() > 0) {
			for (ProcTacheRetPo resultPo : listPo) {
				ProcTacheRetVo resultVo = new ProcTacheRetVo();
				BeanUtils.copyProperties(resultPo, resultVo);
				listVo.add(resultVo);
			}
			return listVo;
		} else {
			return null;
		}
	}

}
