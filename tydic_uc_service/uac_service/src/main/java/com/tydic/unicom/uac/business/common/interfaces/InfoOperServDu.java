package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.business.common.vo.InfoOperVo;

public interface InfoOperServDu {

	public List<InfoOperVo> queryInfoOperByOperNoAndOperName(String currDeptNo,String operNo, String operName) throws Exception;
}
