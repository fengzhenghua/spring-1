package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgDefineServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeTaskPkgDefineServ")
public class CodeTaskPkgDefineServImpl extends BaseServImpl<CodeTaskPkgDefinePo> implements CodeTaskPkgDefineServ {

	@Override
	public List<CodeTaskPkgDefinePo> queryCodeTaskPkgDefineByPo(CodeTaskPkgDefinePo po) throws Exception {
		Condition con = Condition.build("queryCodeTaskPkgDefineByPo").filter(po.convertThis2Map());
		List<CodeTaskPkgDefinePo> list = query(CodeTaskPkgDefinePo.class, con);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public boolean create(CodeTaskPkgDefinePo po) throws Exception {
		create(CodeTaskPkgDefinePo.class,po);
		return true;
	}

	@Override
	public boolean update(CodeTaskPkgDefinePo po) throws Exception {
		update(CodeTaskPkgDefinePo.class,po);
		return true;
	}

	@Override
	public boolean delete(CodeTaskPkgDefinePo po) throws Exception {
		remove(CodeTaskPkgDefinePo.class,po);
		return true;
	}

	@Override
	public List<CodeTaskPkgDefinePo> queryCodeTaskPkgDefine(
			CodeTaskPkgDefinePo po, int pageNo, int pageSize) throws Exception {
		int number = (pageNo-1)* pageSize;
		List<CodeTaskPkgDefinePo> list = S.get(CodeTaskPkgDefinePo.class).page(Condition.build("queryCodeTaskPkgDefine").filter(po.convertThis2Map()), number, pageSize);
		if(list == null || list.size() <= 0){
			return null;
		}
		return list;
	}

	@Override
	public int queryCodeTaskPkgDefineCount(CodeTaskPkgDefinePo po)
			throws Exception {
		Condition con = Condition.build("queryCodeTaskPkgDefineCount").filter(po.convertThis2Map());
		List<CodeTaskPkgDefinePo> list = query(CodeTaskPkgDefinePo.class, con);
		return list.size();
	}

}
