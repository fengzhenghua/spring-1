package com.tydic.unicom.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.po.CodeList;
import com.tydic.unicom.service.cache.po.CodeType;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;
import com.tydic.unicom.webUtil.SpringBean;

public class RadioTag extends Tag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MemQueryServ memQueryServ;
	
	private String codeType;
	private String checkIndex;
	private String checkFirst;
	private String warpPre;
	private String warpNext;
	
	@Override
	public int doStartTag() throws JspException {
		memQueryServ = SpringBean.getBean("MemQueryServ",MemQueryServ.class);
		CodeType  code = memQueryServ.queryCodeType("code_type_"+ codeType);
		JspWriter out = this.pageContext.getOut();
		
		try {
			for(CodeList codeList:code.getCodeList()){
				if(null != warpPre){
					out.print(warpPre);
				}
				String classRadio = "";
				if(getClazz()==null||"".equals(getClazz())){
					 classRadio = "class='"+getClazz()+"'";
					
				}
				if(null != checkFirst && "true".equals(checkFirst) && codeList.getCode_id().equals(code.getCodeList().get(0).getCode_id())){
					out.print("<input "+classRadio+"' type='radio' checked='checked' value='"+codeList.getCode_id()+"'" );
					outProtites(out);
					out.print("/>");
				}else if(null != checkIndex &&  codeList.getCode_id().equals(checkIndex)){
					out.print("<input "+classRadio+"' type='radio' checked='checked' value='"+codeList.getCode_id()+"'" );
					outProtites(out);
					out.print("/>");
				}else{
					out.print("<input  "+classRadio+"' type='radio' value='"+codeList.getCode_id()+"'" );
					outProtites(out);
					out.print("/>");
				}
				out.print(codeList.getCode_name());
				if(null != warpNext){
					out.print(warpNext);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	public MemQueryServ getMemQueryServ() {
		return memQueryServ;
	}
	public void setMemQueryServ(MemQueryServ memQueryServ) {
		this.memQueryServ = memQueryServ;
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

	public String getWarpPre() {
		return warpPre;
	}

	public void setWarpPre(String warpPre) {
		this.warpPre = warpPre;
	}

	public String getWarpNext() {
		return warpNext;
	}

	public void setWarpNext(String warpNext) {
		this.warpNext = warpNext;
	}
	
}
