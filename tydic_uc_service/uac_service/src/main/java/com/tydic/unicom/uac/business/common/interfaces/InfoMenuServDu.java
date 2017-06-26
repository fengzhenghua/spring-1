package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.business.common.vo.InfoAuthorityVo;
import com.tydic.unicom.uac.business.common.vo.InfoMenuVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;

public interface InfoMenuServDu {

	public List<InfoMenuVo> queryMenuByInfoOper(InfoOperVo infoOperVo,InfoAuthorityVo infoAuthorityVo) throws Exception;
}
