package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.OperRelationPo;

public interface OperRelationServ {

	public List<OperRelationPo> queryOperRelationByUniformInfoOper(OperRelationPo operRelationPo);
}
