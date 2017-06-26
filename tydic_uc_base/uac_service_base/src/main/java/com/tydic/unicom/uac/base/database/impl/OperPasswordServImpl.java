/**
 * @ProjectName: uss_service_pub
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangyi
 * @date: 2015年5月18日 
 * @Title: OperPasswordServImpl.java
 * @Package com.tydic.unicom.pub.service.impl
 * @Description: TODO
 */
package com.tydic.unicom.uac.base.database.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.OperPasswordServ;
import com.tydic.unicom.uac.base.database.po.InfoOperPasswordPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;


@Service("OperPasswordServ")
public class OperPasswordServImpl extends BaseServImpl<InfoOperPasswordPo> implements OperPasswordServ {
	
	@Override
	public List<InfoOperPasswordPo> queryInfoOperPasswordByOperNo(InfoOperPo infoOper) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oper_id", infoOper.getOper_id());
		Condition con = Condition.build("queryInfoOperPasswordByOperId").filter(map);
		List<InfoOperPasswordPo> list = query(InfoOperPasswordPo.class, con);
		return list;
	}
	
}
