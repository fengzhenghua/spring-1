package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDesignPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgDesignServ;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgDesignVo;
import com.tydic.unicom.uoc.service.mod.interfaces.CodeTaskPkgDesignServDu;
@Service("CodeTaskPkgDesignServDu")
public class CodeTaskPkgDesignServDuImpl implements CodeTaskPkgDesignServDu{

	@Autowired
	private CodeTaskPkgDesignServ codeTaskPkgDesignServ;
	@Override
	public boolean create(CodeTaskPkgDesignVo vo) throws Exception {
		CodeTaskPkgDesignPo po = new CodeTaskPkgDesignPo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgDesignServ.create(po);
		return res;
	}

	@Override
	public boolean update(CodeTaskPkgDesignVo vo) throws Exception {
		CodeTaskPkgDesignPo po = new CodeTaskPkgDesignPo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgDesignServ.update(po);
		return res;
	}

	@Override
	public boolean delete(CodeTaskPkgDesignVo vo) throws Exception {
		CodeTaskPkgDesignPo po = new CodeTaskPkgDesignPo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgDesignServ.delete(po);
		return res;
	}

	@Override
	public List<CodeTaskPkgDesignVo> queryCodeTaskPkgDesign(
			CodeTaskPkgDesignVo vo, int pageNo, int pageSize) throws Exception {
		CodeTaskPkgDesignPo po = new CodeTaskPkgDesignPo();
		BeanUtils.copyProperties(vo, po);
		List<CodeTaskPkgDesignPo> listPo = codeTaskPkgDesignServ.queryCodeTaskPkgDesign(po, pageNo, pageSize);
		List<CodeTaskPkgDesignVo> listVo = new ArrayList<CodeTaskPkgDesignVo>();
		if(listPo != null && listPo.size() > 0){
			for(CodeTaskPkgDesignPo codeTaskPkgDesign : listPo){
				CodeTaskPkgDesignVo designVo = new CodeTaskPkgDesignVo();
				BeanUtils.copyProperties(codeTaskPkgDesign, designVo);
				listVo.add(vo);
			}
			return listVo;
		}
		return null;
	}

	@Override
	public int queryCodeTaskPkgDesignCount(CodeTaskPkgDesignVo vo)
			throws Exception {
		CodeTaskPkgDesignPo po = new CodeTaskPkgDesignPo();
		BeanUtils.copyProperties(vo, po);
		int res = codeTaskPkgDesignServ.queryCodeTaskPkgDesignCount(po);
		return res;
	}

	@Override
	public List<CodeTaskPkgDesignVo> queryCodeTaskPkgDesignByVo(
			CodeTaskPkgDesignVo vo) throws Exception {
		CodeTaskPkgDesignPo po = new CodeTaskPkgDesignPo();
		BeanUtils.copyProperties(vo, po);
		List<CodeTaskPkgDesignPo> listPo = codeTaskPkgDesignServ.queryCodeTaskPkgDesignByPo(po);
		List<CodeTaskPkgDesignVo> listVo = new ArrayList<CodeTaskPkgDesignVo>();
		if(listPo != null && listPo.size() > 0){
			for(CodeTaskPkgDesignPo codeTaskPkgDesign : listPo){
				CodeTaskPkgDesignVo designVo = new CodeTaskPkgDesignVo();
				BeanUtils.copyProperties(codeTaskPkgDesign, designVo);
				listVo.add(vo);
			}
			return listVo;
		}
		return null;
	}

}
