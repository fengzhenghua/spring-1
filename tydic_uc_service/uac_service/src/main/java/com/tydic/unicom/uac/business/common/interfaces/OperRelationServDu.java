package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.business.common.vo.OperRelationVo;

public interface OperRelationServDu {

	public List<OperRelationVo> queryOperRelationByUniformInfoOper(OperRelationVo operRelationVo);
}
