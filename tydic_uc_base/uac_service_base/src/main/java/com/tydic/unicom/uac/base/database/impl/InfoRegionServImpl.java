package com.tydic.unicom.uac.base.database.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uac.base.database.interfaces.InfoRegionServ;
import com.tydic.unicom.uac.base.database.po.InfoRegionPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

/**
 * 对表ZB_INFO_REGION的DAO类-实现类
 * @author ZhangCheng
 * @date 2017-04-12
 */
@Service("InfoRegionServ")
public class InfoRegionServImpl extends BaseServImpl<InfoRegionPo> implements InfoRegionServ {

	@Override
	public List<InfoRegionPo> queryInfoRegions(InfoRegionPo infoRegionPo) {
		Condition con = Condition.build("queryInfoRegions").filter(infoRegionPo.convertThis2Map());
		List<InfoRegionPo> infoRegionPos = S.get(InfoRegionPo.class).query(con);
		return infoRegionPos;
	}
}
