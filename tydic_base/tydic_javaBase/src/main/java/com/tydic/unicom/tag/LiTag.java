package com.tydic.unicom.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.po.CodeList;
import com.tydic.unicom.service.cache.po.CodeType;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;
import com.tydic.unicom.webUtil.SpringBean;

public class LiTag extends Tag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemQueryServ memQueryServ;
	
	private String codeType;
	
	private String checkIndex;
	
	private String checkFirst;
	
	private String checkClass;
	
	
	@Override
	public int doStartTag() throws JspException {
		memQueryServ = SpringBean.getBean("MemQueryServ",MemQueryServ.class);
		CodeType  code = memQueryServ.queryCodeType("code_type_"+ codeType);
		JspWriter out = this.pageContext.getOut();
		
		try {
			for(CodeList codeList : code.getCodeList()){
				if(null != checkFirst && "true".equals(checkFirst) && codeList.getCode_id().equals(code.getCodeList().get(0).getCode_id())){
					out.print("<li value='"+codeList.getCode_id()+"' class='common_li "+getClazz()+" "+checkClass+"'");
					outProtites(out);
					out.print(">"+codeList.getCode_name()+"</li>");
				}else if(null != checkIndex &&  codeList.getCode_id().equals(checkIndex)){
					out.print("<li value='"+codeList.getCode_id()+"' class='common_li "+getClazz()+" "+checkClass+"'");
					outProtites(out);
					out.print(">"+codeList.getCode_name()+"</li>");
				}else{
					out.print("<li value='"+codeList.getCode_id()+"' class='common_li "+getClazz()+"'");
					outProtites(out);
					out.print(">"+codeList.getCode_name()+"</li>");
				}
			}
		} catch (IOException e) {
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

	public String getCheckIndex() {
		return checkIndex;
	}

	public void setCheckIndex(String checkIndex) {
		this.checkIndex = checkIndex;
	}

	public String getCheckFirst() {
		return checkFirst;
	}

	public void setCheckFirst(String checkFirst) {
		this.checkFirst = checkFirst;
	}

	public String getCheckClass() {
		return checkClass;
	}

	public void setCheckClass(String checkClass) {
		this.checkClass = checkClass;
	}
	
}
