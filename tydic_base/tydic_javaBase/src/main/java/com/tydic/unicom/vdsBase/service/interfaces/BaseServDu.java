/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author wanghao
 * @date: 2014年9月19日 上午10:07:46
 * @Title: BaseServDu.java
 * @Package com.tydic.unicom.vdsBase.service.interfaces
 * @Description: TODO
 */
package com.tydic.unicom.vdsBase.service.interfaces;

import java.util.List;

import com.tydic.unicom.vdsBase.po.Pageable;
import com.tydic.unicom.webUtil.Page;


/**
 * <p></p>
 * @author wanghao 2014年9月19日 上午10:07:46
 * @ClassName BaseServDu
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2014年9月19日
 * @modify by reason:{方法名}:{原因}
 */
public interface BaseServDu {
	/**
	 * 
	 * @author wanghao 2014年9月19日 上午10:08:16
	 * @Method: convertPage 
	 * @Description: TODO
	 * @param pageable
	 * @param dataRows
	 * @return 
	 * @throws
	 */
	<T> Page<T> convertPage(Pageable<T> pageable,List<T> dataRows);

	<T> Page<T> convertPageNew(Pageable<T> pageable, List<T> dataRows);
}
