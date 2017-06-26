package com.tydic.unicom.uoc.business.order.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.vo.InfoServiceOrderAuidtVo;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月14日 上午10:33:34
 * @ClassName ServiceOrderExprotServDu
 * @Description 服务订单报表
 * @version V1.0
 */
public interface ServiceOrderExprotServDu {
	/**
	 * 
	 * @author heguoyong 2017年4月14日 上午11:06:49
	 * @Method: getServiceOrderAudit 
	 * @param InfoServiceOrderVo
	 * @Description: TODO
	 */
	List<InfoServiceOrderAuidtVo> getServiceOrderAudit(ParaVo paraVo) throws Exception;
}
