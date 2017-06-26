/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author wanghao
 * @date: 2014年9月19日 上午10:12:57
 * @Title: BaseServDuImpl.java
 * @Package com.tydic.unicom.vdsBase.service.impl
 * @Description: TODO
 */
package com.tydic.unicom.vdsBase.service.impl;

import java.util.List;

import com.tydic.uda.service.S;
import com.tydic.unicom.vdsBase.po.Pageable;
import com.tydic.unicom.vdsBase.service.interfaces.BaseServDu;
import com.tydic.unicom.webUtil.Page;


/**
 * <p></p>
 * @author wanghao 2014年9月19日 上午10:12:57
 * @ClassName BaseServDuImpl
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2014年9月19日
 * @modify by reason:{方法名}:{原因}
 */
public class BaseServDuImpl implements BaseServDu {
	
	/**
	 * @author wanghao 2014年9月19日 上午10:12:57
	 * @Method: convertPage 
	 * @Description: TODO  分页数据获取－当前页面数据
	 * @param pageable
	 * @param dataRows
	 * @return 
	 * @see com.tydic.unicom.vdsBase.service.interfaces.BaseServDu#convertPage(com.tydic.unicom.vdsBase.po.Pageable, java.util.List) 
	 */
	
	@Override
	public <T> Page<T> convertPage(Pageable<T> pageable, List<T> dataRows) {
		Page<T> page = new Page<T>();
		page.setCurPage(pageable.getPageNum());
		page.setPageSize(pageable.getPageSize());
		page.setDataRows(dataRows);
		Integer count=dataRows.size();
		page.setTotalRecords(count);
		page.setTotalPages(count%pageable.getPageSize() == 0 ? count / pageable.getPageSize() : (count / pageable.getPageSize() + 1));
		return page;
		
	}
	
	//实现真正的分页 --by yangzhifan 20160706
	@Override
	public <T> Page<T> convertPageNew(Pageable<T> pageable, List<T> dataRows) {
		Page<T> page = convertPage(pageable, dataRows);
		
		int start_idx = (page.getCurPage()-1)*page.getPageSize();
		int end_idx = page.getCurPage()*page.getPageSize();
		if (page.getTotalRecords()<end_idx) {
			end_idx = page.getTotalRecords();
		}
		
		if (start_idx<0 || end_idx<=0 || start_idx>end_idx) {
			return page;
		}
		
		page.setDataRows(dataRows.subList(start_idx, end_idx)); //分页后的List
		
		return page;
	}
	
}
