package com.tydic.unicom.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class InputTag extends Tag {

	/**
	 * text tag
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String value;
	private String checked;
	private String maxlength;
	private String readonly;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try{
			out.print("<input class='common_input "+getClazz()+"'");
			outProtites(out);
			if(type != null){
				out.print(" type='"+type+"'");
			}
			if(value != null){
				out.print(" value='"+value+"'");
			}
			if(checked != null){
				out.print(" checked='"+checked+"'");
			}
			if(maxlength != null){
				out.print(" maxlength='"+maxlength+"'");
			}
			if(readonly != null){
				out.print(" readonly='"+readonly+"'");
			}
			out.print("/>");
		}catch(Exception e){
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
	
}
