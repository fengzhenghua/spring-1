/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年9月13日 下午3:30:12
 * @Title: BaseVo.java
 * @Package com.tydic.unicom.webUtil
 * @Description: TODO
 */
package com.tydic.unicom.webUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tydic.unicom.vdsBase.po.BasePo;


/**
 * <p></p>
 * @author yangfei 2014年9月13日 下午3:30:12
 * @ClassName BaseVo
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月13日
 * @modify by reason:{方法名}:{原因}
 */
public class BaseVo extends BasePo{
	
	/*
	public String jsessionid = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getId();
	public String getJsessionid() {
    	return jsessionid;
    }

	
    public void setJsessionid(String jsessionid) {
    	this.jsessionid = jsessionid;
    }*/
	public  BasePo convert2po(Class class1) {
		// TODO Auto-generated method stub
		BasePo po = null;
		try {
			po = (BasePo) class1.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this!=null){
			BeanUtils.copyProperties(this, po);
		}
		return po;
	}
}
