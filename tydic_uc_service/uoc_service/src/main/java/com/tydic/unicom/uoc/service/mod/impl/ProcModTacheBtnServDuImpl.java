package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheBtnPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheBtnServ;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheBtnServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheBtnVo;

@Service("ProcModTacheBtnServDu")
public class ProcModTacheBtnServDuImpl implements ProcModTacheBtnServDu{

	@Autowired
	private ProcModTacheBtnServ procModTacheBtnServ;

	@Override
	public boolean create(ProcModTacheBtnVo ProcModTacheBtnVo) throws Exception {
		ProcModTacheBtnPo po = new ProcModTacheBtnPo();
		BeanUtils.copyProperties(ProcModTacheBtnVo, po);
		boolean res = procModTacheBtnServ.create(po);
		return res;
	}

	@Override
	public boolean update(ProcModTacheBtnVo ProcModTacheBtnVo) throws Exception {
		ProcModTacheBtnPo po = new ProcModTacheBtnPo();
		BeanUtils.copyProperties(ProcModTacheBtnVo, po);
		boolean res = procModTacheBtnServ.update(po);
		return res;
	}

	@Override
	public boolean delete(ProcModTacheBtnVo ProcModTacheBtnVo) throws Exception {
		ProcModTacheBtnPo po = new ProcModTacheBtnPo();
		BeanUtils.copyProperties(ProcModTacheBtnVo, po);
		boolean res = procModTacheBtnServ.delete(po);
		return res;
	}
	

	@Override
	public int queryProcModTacheBtnListCount(ProcModTacheBtnVo ProcModTacheBtnVo)
			throws Exception {
		ProcModTacheBtnPo po = new ProcModTacheBtnPo();
		BeanUtils.copyProperties(ProcModTacheBtnVo, po);
		int res = procModTacheBtnServ.queryProcModTacheBtnListCount(po);
		return res;
	}

	@Override
	public List<ProcModTacheBtnVo> queryProcModTacheBtnList(
			ProcModTacheBtnVo ProcModTacheBtnVo, int pageNo, int pageSize)
			throws Exception {
		ProcModTacheBtnPo po = new ProcModTacheBtnPo();
		BeanUtils.copyProperties(ProcModTacheBtnVo, po);
		List<ProcModTacheBtnVo> listVo = new ArrayList<ProcModTacheBtnVo>();
		List<ProcModTacheBtnPo> listPo =procModTacheBtnServ.queryProcModTacheBtnList(po, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(ProcModTacheBtnPo poTemp : listPo){
				ProcModTacheBtnVo ordVo = new ProcModTacheBtnVo();
				BeanUtils.copyProperties(poTemp, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<ProcModTacheBtnVo> queryProcModTacheBtnAll() throws Exception {
		List<ProcModTacheBtnPo> list = procModTacheBtnServ.queryProcModTacheBtnAll();
		if(list != null && list.size()>0){
			List<ProcModTacheBtnVo> listOut = new ArrayList<ProcModTacheBtnVo>();
			for(int i=0;i<list.size();i++){
				ProcModTacheBtnVo procModTacheBtnVo = new ProcModTacheBtnVo();
				BeanUtils.copyProperties(list.get(i), procModTacheBtnVo);
				listOut.add(procModTacheBtnVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
