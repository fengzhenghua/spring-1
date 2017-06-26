/**
 * @ProjectName: uss_service_pub
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangyi
 * @date: 2015年5月18日 
 * @Title: OperPasswordServ.java
 * @Package com.tydic.unicom.pub.service.interfaces
 * @Description: 
 */
package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;
import com.tydic.unicom.uac.base.database.po.InfoOperPasswordPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;

/**
 * 
 * <p>
 * </p>
 * @author yangyi 2015年5月18日 
 * @ClassName OperPasswordServ
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2014年10月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface OperPasswordServ {	
	
	public List<InfoOperPasswordPo> queryInfoOperPasswordByOperNo(InfoOperPo infoOperPo);
	

	

}
