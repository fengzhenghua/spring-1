package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgAppServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeTaskPkgAppServ")
public class CodeTaskPkgAppServImpl extends BaseServImpl<CodeTaskPkgAppPo> implements CodeTaskPkgAppServ {

	@Override
	public List<CodeTaskPkgAppPo> queryCodeTaskPkgAppById(CodeTaskPkgAppPo po) throws Exception {
		Condition con = Condition.build("queryCodeTaskPkgAppById").filter(po.convertThis2Map());
		List<CodeTaskPkgAppPo> list = query(CodeTaskPkgAppPo.class, con);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public boolean create(CodeTaskPkgAppPo po) throws Exception {
		create(CodeTaskPkgAppPo.class,po);
		return true;
	}

	@Override
	public boolean update(CodeTaskPkgAppPo po) throws Exception {
		update(CodeTaskPkgAppPo.class,po);
		return true;
	}

	@Override
	public boolean delete(CodeTaskPkgAppPo po) throws Exception {
		remove(CodeTaskPkgAppPo.class,po);
		return true;
	}

	@Override
	public List<CodeTaskPkgAppPo> queryCodeTaskPkgApp(CodeTaskPkgAppPo po,
			int pageNo, int pageSize) throws Exception {
		int number = (pageNo-1)* pageSize;
		List<CodeTaskPkgAppPo> list = S.get(CodeTaskPkgAppPo.class).page(Condition.build("queryCodeTaskPkgApp").filter(po.convertThis2Map()), number,pageSize);
		if(list == null || list.size() <= 0){
			return null;
		}
		return list;
	}

	@Override
	public int queryCodeTaskPkgAppCount(CodeTaskPkgAppPo po) throws Exception {
		Condition con = Condition.build("queryCodeTaskPkgAppCount").filter(po.convertThis2Map());
		List<CodeTaskPkgAppPo> list = query(CodeTaskPkgAppPo.class, con);
		return list.size();
	}

}
