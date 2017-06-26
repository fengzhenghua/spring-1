package com.tydic.unicom.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tydic.unicom.service.cache.po.CodeList;
import com.tydic.unicom.service.cache.po.CodeType;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;
import com.tydic.unicom.webUtil.SpringBean;
@Component
public class SelectTag extends Tag{/*
	*//**
	 * 从内存中加载下拉列表
	 *//*
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemQueryServ memQueryServ;
	
	private String codeType;
	private String selectIndex;
	private String selectFirst;
	
	@Override
	public int doStartTag() throws JspException {
		memQueryServ = SpringBean.getBean("MemQueryServ",MemQueryServ.class);
		CodeType  code = memQueryServ.queryCodeType("code_type_"+ codeType);
		JspWriter out = this.pageContext.getOut();
		
		try {
			String classStr = (null==getClazz()||"".equals(getClazz()))?"":"class='"+getClazz()+"'";
			out.print("<select "+classStr);
			outProtites(out);
			out.print(">");
			for(CodeList codeList : code.getCodeList()){
				if(null != selectFirst && "true".equals(selectFirst) && codeList.getCode_id().equals(code.getCodeList().get(0).getCode_id())){
					out.print("<option value='"+codeList.getCode_id()+"' selected='selected'>"+codeList.getCode_name()+"</option>");
				}else if(null != selectIndex &&  codeList.getCode_id().equals(selectIndex)){
					out.print("<option value='"+codeList.getCode_id()+"' selected='selected'>"+codeList.getCode_name()+"</option>");
				}else{
					out.print("<option value='"+codeList.getCode_id()+"'>"+codeList.getCode_name()+"</option>");
				}
			}
			out.print("</select>");
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
	public String getSelectIndex() {
		return selectIndex;
	}
	public void setSelectIndex(String selectIndex) {
		this.selectIndex = selectIndex;
	}
	public String getSelectFirst() {
		return selectFirst;
	}
	public void setSelectFirst(String selectFirst) {
		this.selectFirst = selectFirst;
	}

*/}
