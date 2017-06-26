package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgAppServ;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgAppVo;
import com.tydic.unicom.uoc.service.mod.interfaces.CodeTaskPkgAppServDu;
@Service("CodeTaskPkgAppServDu")
public class CodeTaskPkgAppServDuImpl implements CodeTaskPkgAppServDu{

	@Autowired
	private CodeTaskPkgAppServ codeTaskPkgAppServ;
	@Override
	public boolean create(CodeTaskPkgAppVo vo) throws Exception {
		CodeTaskPkgAppPo po = new CodeTaskPkgAppPo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgAppServ.create(po);
		return res;
	}

	@Override
	public boolean update(CodeTaskPkgAppVo vo) throws Exception {
		CodeTaskPkgAppPo po = new CodeTaskPkgAppPo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgAppServ.update(po);
		return res;
	}

	@Override
	public boolean delete(CodeTaskPkgAppVo vo) throws Exception {
		CodeTaskPkgAppPo po = new CodeTaskPkgAppPo();
		BeanUtils.copyProperties(vo, po);
		boolean res = codeTaskPkgAppServ.delete(po);
		return res;
	}

	@Override
	public List<CodeTaskPkgAppVo> queryCodeTaskPkgApp(CodeTaskPkgAppVo vo,
			int pageNo, int pageSize) throws Exception {
		CodeTaskPkgAppPo po = new CodeTaskPkgAppPo();
		BeanUtils.copyProperties(vo, po);
		List<CodeTaskPkgAppVo> listVo = new ArrayList<CodeTaskPkgAppVo>();
		List<CodeTaskPkgAppPo> listPo = codeTaskPkgAppServ.queryCodeTaskPkgApp(po, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(CodeTaskPkgAppPo codeTaskPkgAppPo : listPo){
				CodeTaskPkgAppVo appVo = new CodeTaskPkgAppVo();
				BeanUtils.copyProperties(codeTaskPkgAppPo, appVo);
				listVo.add(appVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public int queryCodeTaskPkgAppCount(CodeTaskPkgAppVo vo) throws Exception {
		CodeTaskPkgAppPo po = new CodeTaskPkgAppPo();
		BeanUtils.copyProperties(vo, po);
		int res = codeTaskPkgAppServ.queryCodeTaskPkgAppCount(po);
		return res;
	}

	@Override
	public List<CodeTaskPkgAppVo> queryCodeTaskPkgAppByVo(CodeTaskPkgAppVo vo)
			throws Exception {
		CodeTaskPkgAppPo po = new CodeTaskPkgAppPo();
		BeanUtils.copyProperties(vo, po);
		List<CodeTaskPkgAppVo> listVo = new ArrayList<CodeTaskPkgAppVo>();
		List<CodeTaskPkgAppPo> listPo = codeTaskPkgAppServ.queryCodeTaskPkgAppById(po);
		if(listPo != null && listPo.size()>0){
			for(CodeTaskPkgAppPo codeTaskPkgAppPo : listPo){
				CodeTaskPkgAppVo appVo = new CodeTaskPkgAppVo();
				BeanUtils.copyProperties(codeTaskPkgAppPo, appVo);
				listVo.add(appVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

}
