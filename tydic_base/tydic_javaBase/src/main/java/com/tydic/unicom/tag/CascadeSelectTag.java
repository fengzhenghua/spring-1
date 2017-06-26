/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年10月15日 下午7:15:01
 * @Title: CascadeSelectTag.java
 * @Package com.tydic.unicom.tag
 * @Description: TODO
 */
package com.tydic.unicom.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.po.CodeList;
import com.tydic.unicom.service.cache.po.CodeType;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;
import com.tydic.unicom.webUtil.SpringBean;


/**
 * <p></p>
 * @author yangfei 2014年10月15日 下午7:15:01
 * @ClassName CascadeSelectTag 级联下拉列表
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年10月15日
 * @modify by reason:{方法名}:{原因}
 */
public class CascadeSelectTag extends Tag {
	/**
	 * 从内存中加载级联下拉列表
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemQueryServ memQueryServ;
	
	private String codeType;
	
	private String rootValue;
	
	private String childElId;
	
	private String selectIndex;
	
	private String selectFirst = "true";
	
	@Override
	public int doStartTag() throws JspException {
		memQueryServ = SpringBean.getBean("MemQueryServ",MemQueryServ.class);
		CodeType  code = memQueryServ.queryCodeType("code_type_"+ codeType);
		JspWriter out = this.pageContext.getOut();
		
		try {
			
			
			if(rootValue.indexOf(">") < 0){
				List<CodeList> list = new ArrayList<CodeList>();
				for(CodeList codeList : code.getCodeList()){
					if(codeList.getParent_code_id().equals(rootValue)){
						list.add(codeList);
					}
				}
				if(null != childElId){
					out.print("<script  type='text/javascript'> ");
					out.print("function send"+childElId+"Topic(t){");
					out.print("var v = $(t).val();");
					out.print("var id = $(t).attr('id');");
					out.print("var u = $('#cascadeSelect_ul_"+childElId+"').children();");
					out.print("var c = $('#"+childElId+"');");
					out.print("var cId;");
					out.print("do{");
					out.print("cId = c.attr('childElId');");
					out.print("c.empty();");
					out.print("var flag = true;");
					out.print("var v2 = '';");
					out.print("u.each(function(i){");
					out.print("var pId = $(this).attr('parentId');");
					out.print("if(pId == v){");
					out.print("var op = '<option value=\'+$(this).attr(\'value\')+\' parentId=\'+pId+\'>'");
					out.print("+ $.trim($(this).text())");
					out.print("+ '</option>';");
					out.print("c.append(op);");
					out.print("if (flag) {");
					out.print("v2 = $(this).attr('value');");
					out.print("flag = false;");
					out.print("}");
					out.print("}");
					out.print("});");
					out.print("c = $('#'+cId);");
					out.print("if(cId){");
					out.print("u = $('#cascadeSelect_ul_'+cId).children();");
					out.print("}");
					out.print("v = v2;");
					out.print("}while(cId)");
					out.print("} ");
					out.print("</script> ");
					out.print("<select class='common_select "+getClazz()+"' onchange='send"+childElId+"Topic(this);' childElId='"+childElId+"'");
					outProtites(out);
					out.print(">");
				}else{
					out.print("<select class='common_select "+getClazz()+"' ");
					outProtites(out);
					out.print(">");
				}
				for(CodeList codeList : list){
					if(null != selectFirst && "true".equals(selectFirst) && codeList.getCode_id().equals(code.getCodeList().get(0).getCode_id())){
						out.print("<option value='"+codeList.getCode_id()+"' selected='selected' parentId='"+codeList.getParent_code_id()+"'>"+codeList.getCode_name()+"</option>");
					}else if(null != selectIndex &&  codeList.getCode_id().equals(selectIndex)){
						out.print("<option value='"+codeList.getCode_id()+"' selected='selected' parentId='"+codeList.getParent_code_id()+"'>"+codeList.getCode_name()+"</option>");
					}else{
						out.print("<option value='"+codeList.getCode_id()+"' parentId='"+codeList.getParent_code_id()+"'>"+codeList.getCode_name()+"</option>");
					}
				}
				out.print("</select>");
			}else{
				String[] value = rootValue.split(">");
				List<CodeList> list = new ArrayList<CodeList>();
				for(CodeList codeList : code.getCodeList()){
					if(!codeList.getParent_code_id().equals(value[0])){
						list.add(codeList);
					}
				}
				if(null != childElId){
					out.print("<script  type='text/javascript'> ");
					out.print("function send"+childElId+"Topic(t){");
					out.print("var v = $(t).val();");
					out.print("var id = $(t).attr('id');");
					out.print("var u = $('#cascadeSelect_ul_"+childElId+"').children();");
					out.print("var c = $('#"+childElId+"');");
					out.print("var cId;");
					out.print("do{");
					out.print("cId = c.attr('childElId');");
					out.print("c.empty();");
					out.print("var flag = true;");
					out.print("var v2 = '';");
					out.print("u.each(function(i){");
					out.print("var pId = $(this).attr('parentId');");
					out.print("if(pId == v){");
					out.print("var op = '<option value=\'+$(this).attr(\'value\')+\' parentId=\'+pId+\'>'");
					out.print("+ $.trim($(this).text())");
					out.print("+ '</option>';");
					out.print("c.append(op);");
					out.print("if (flag) {");
					out.print("v2 = $(this).attr('value');");
					out.print("flag = false;");
					out.print("}");
					out.print("}");
					out.print("});");
					out.print("c = $('#'+cId);");
					out.print("if(cId){");
					out.print("u = $('#cascadeSelect_ul_'+cId).children();");
					out.print("}");
					out.print("v = v2;");
					out.print("}while(cId)");
					out.print("} ");
					out.print("</script> ");
					out.print("<select class='common_select "+getClazz()+"' onchange='send"+childElId+"Topic(this);' childElId='"+childElId+"'");
					outProtites(out);
					out.print(">");
				}else{
					out.print("<select class='common_select "+getClazz()+"' ");
					outProtites(out);
					out.print(">");
				}
				for(CodeList codeList : list){
					if(codeList.getParent_code_id().equals(value[value.length - 1])){
						if(null != selectFirst && "true".equals(selectFirst) && codeList.getCode_id().equals(code.getCodeList().get(0).getCode_id())){
							out.print("<option value='"+codeList.getCode_id()+"' selected='selected' parentId='"+codeList.getParent_code_id()+"'>"+codeList.getCode_name()+"</option>");
						}else if(null != selectIndex &&  codeList.getCode_id().equals(selectIndex)){
							out.print("<option value='"+codeList.getCode_id()+"' selected='selected' parentId='"+codeList.getParent_code_id()+"'>"+codeList.getCode_name()+"</option>");
						}else{
							out.print("<option value='"+codeList.getCode_id()+"' parentId='"+codeList.getParent_code_id()+"'>"+codeList.getCode_name()+"</option>");
						}
					}
				}
				out.print("</select>");
				out.print("<ul style='display:none;' id='cascadeSelect_ul_"+getId()+"'>");
				for(CodeList codeList : list){
					out.print("<li value='"+codeList.getCode_id()+"'  parentId='"+codeList.getParent_code_id()+"'>"+codeList.getCode_name()+"</li>");
				}
				out.print("</ul>");
			}
		}catch (Exception e) {
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

	
    public String getRootValue() {
    	return rootValue;
    }

	
    public void setRootValue(String rootValue) {
    	this.rootValue = rootValue;
    }

	
    public String getChildElId() {
    	return childElId;
    }

	
    public void setChildElId(String childElId) {
    	this.childElId = childElId;
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
	
}
