/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年9月29日 下午3:01:38
 * @Title: LableTag.java
 * @Package com.tydic.unicom.tag
 * @Description: TODO
 */
package com.tydic.unicom.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.po.CodeList;
import com.tydic.unicom.service.cache.po.CodeType;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;
import com.tydic.unicom.webUtil.SpringBean;


/**
 * <p></p>
 * @author yangfei 2014年9月29日 下午3:01:38
 * @ClassName LableTag
 * @Description TODO 从MEMCACHE读CODE_LIST生成lable
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class LableTag extends Tag {

	@Autowired
	private MemQueryServ memQueryServ;
	
	private String codeType;
	private String codeId;
	
	@Override
	public int doStartTag() throws JspException {
		memQueryServ = SpringBean.getBean("MemQueryServ",MemQueryServ.class);
		CodeType  code = memQueryServ.queryCodeType("code_type_"+ codeType);
		JspWriter out = this.pageContext.getOut();
		try {
			for(CodeList codeList : code.getCodeList()){
				if(codeList.getCode_id().equals(codeId)){
					out.print("<lable value='"+codeList.getCode_id()+"' class='common_lable "+getClazz()+" '");
					outProtites(out);
					out.print(">"+codeList.getCode_name()+"</lable>");
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	
    public String getCodeType() {
    	return codeType;
    }

	
    public void setCodeType(String codeType) {
    	this.codeType = codeType;
    }

	
    public String getCodeId() {
    	return codeId;
    }

	
    public void setCodeId(String codeId) {
    	this.codeId = codeId;
    }
}
