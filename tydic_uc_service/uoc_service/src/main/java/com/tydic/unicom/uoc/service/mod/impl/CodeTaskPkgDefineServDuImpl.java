package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgDefineServ;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgDefineVo;
import com.tydic.unicom.uoc.service.mod.interfaces.CodeTaskPkgDefineServDu;
@Service("CodeTaskPkgDefineServDu")
public  class CodeTaskPkgDefineServDuImpl implements CodeTaskPkgDefineServDu{

	@Autowired
	private CodeTaskPkgDefineServ codeTaskPkgDefineServ;
	@Override
	public boolean create(CodeTaskPkgDefineVo vo) throws Exception {
		CodeTaskPkgDefinePo po = new CodeTaskPkgDefinePo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgDefineServ.create(po);		
		return res;
	}

	@Override
	public boolean update(CodeTaskPkgDefineVo vo) throws Exception {
		CodeTaskPkgDefinePo po = new CodeTaskPkgDefinePo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgDefineServ.update(po);		
		return res;
	}

	@Override
	public boolean delete(CodeTaskPkgDefineVo vo) throws Exception {
		CodeTaskPkgDefinePo po = new CodeTaskPkgDefinePo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgDefineServ.delete(po);		
		return res;
	}

	@Override
	public List<CodeTaskPkgDefineVo> queryCodeTaskPkgDefine(
			CodeTaskPkgDefineVo vo, int pageNo, int pageSize) throws Exception {
		CodeTaskPkgDefinePo po = new CodeTaskPkgDefinePo();
		BeanUtils.copyProperties(vo, po);
		List<CodeTaskPkgDefinePo> listPo = codeTaskPkgDefineServ.queryCodeTaskPkgDefine(po, pageNo, pageSize);
		List<CodeTaskPkgDefineVo> listVo = new ArrayList<CodeTaskPkgDefineVo>();
		if(listPo != null && listPo.size()>0){
			for(CodeTaskPkgDefinePo codeTaskPkgDefine : listPo){
				CodeTaskPkgDefineVo defineVo = new CodeTaskPkgDefineVo();
				BeanUtils.copyProperties(codeTaskPkgDefine, defineVo);
				listVo.add(defineVo);
			}
			return listVo;
		}
		return null;
	}

	@Override
	public int queryCodeTaskPkgDefineCount(CodeTaskPkgDefineVo vo)
			throws Exception {
		CodeTaskPkgDefinePo po = new CodeTaskPkgDefinePo();
		BeanUtils.copyProperties(vo, po);
		int res = codeTaskPkgDefineServ.queryCodeTaskPkgDefineCount(po);
		return res;
	}

	@Override
	public CodeTaskPkgDefineVo queryCodeTaskPkgDefineByPkgId(
			CodeTaskPkgDefineVo vo) throws Exception {
		CodeTaskPkgDefinePo po = new CodeTaskPkgDefinePo();
		BeanUtils.copyProperties(vo, po);
		List<CodeTaskPkgDefinePo> listPo  = codeTaskPkgDefineServ.queryCodeTaskPkgDefineByPo(po);
		CodeTaskPkgDefineVo defineVo = new CodeTaskPkgDefineVo();
		if(listPo != null && listPo.size()>0){							
			BeanUtils.copyProperties(listPo.get(0), defineVo);
			return defineVo;
		}
		return null;
	}

}
