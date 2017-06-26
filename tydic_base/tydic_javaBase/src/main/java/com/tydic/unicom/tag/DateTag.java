package com.tydic.unicom.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class DateTag extends Tag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private String maxDate;
	private String minDate;
	private String maxDateElId;
	private String minDateElId;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try{
			out.print("<input class='text Wdate "+getClazz()+"'");
			if(maxDate != null){
				out.print(" onfocus=\"WdatePicker({maxDate: \'"+maxDate+"\'});\"");
			}
			if(maxDateElId != null){
				out.print(" onfocus=\"WdatePicker({ maxDate: \'#F{$dp.$D(\\\'"+maxDateElId+"\\\')}\'  });\"");
			}
			if(minDate != null){
				out.print(" onfocus=\"WdatePicker({minDate: \'"+minDate+"\'});\"");
			}
			if(minDateElId != null){
				out.print(" onfocus=\"WdatePicker({ minDate: \'#F{$dp.$D(\\\'"+minDateElId+"\\\')}\'  });\"");
			}
			if(null == maxDate && null == maxDateElId && null == minDate && null == minDateElId){
				out.print(" onfocus=\"WdatePicker();\"");
			}
			if(value != null){
				out.print(" value='"+value+"'");
			}
			outProtites(out);
			out.print("/>");
		}catch(Exception e){
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDateElId() {
		return maxDateElId;
	}
	public void setMaxDateElId(String maxDateElId) {
		this.maxDateElId = maxDateElId;
	}
	public String getMinDateElId() {
		return minDateElId;
	}
	public void setMinDateElId(String minDateElId) {
		this.minDateElId = minDateElId;
	}
}
