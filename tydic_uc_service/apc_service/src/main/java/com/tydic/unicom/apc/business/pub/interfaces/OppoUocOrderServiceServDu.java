package com.tydic.unicom.apc.business.pub.interfaces;

import com.tydic.unicom.apc.business.pub.vo.ApcUocOrderVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OppoUocOrderServiceServDu {

	public UocMessage createUocOrder(ApcUocOrderVo apcUocOrderVo) throws Exception;
}
