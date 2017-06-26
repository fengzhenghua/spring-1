package com.tydic.unicom.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class Tag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String clazz;
	private String disabled;
	private String onblur;
	private String onchange;
	private String onclick;
	private String ondblclick;
	private String onfocus;
	private String onhelp;
	private String onkeydown;
	private String onkeypress;
	private String onkeyup;
	private String onmousedown;
	private String onmousemove;
	private String onmouseout;
	private String onmouseover;
	private String onmouseup;
	private String style;
	private String title;
	protected void outProtites(JspWriter out) throws IOException {
		if(null != id){
			out.print(" id='"+id+"'");
		}
		if(null != name){
			out.print(" name='"+name+"'");
		}
		if(null != disabled){
			out.print(" disabled='"+disabled+"'");
		}
		if(null != onblur){
			out.print(" onblur='"+onblur+"'");
		}
		if(null != onchange){
			out.print(" onchange='"+onchange+"'");
		}
		if(null != onclick){
			out.print(" onclick='"+onclick+"'");
		}
		if(null != ondblclick){
			out.print(" ondblclick='"+ondblclick+"'");
		}
		if(null != onfocus){
			out.print(" onfocus='"+onfocus+"'");
		}
		if(null != onclick){
			out.print(" onclick='"+onclick+"'");
		}
		
		if(null != onhelp){
			out.print(" onhelp='"+onhelp+"'");
		}
		if(null != onmousedown){
			out.print(" onmousedown='"+onmousedown+"'");
		}
		
		if(null != onmousemove){
			out.print(" onmousemove='"+onmousemove+"'");
		}
		if(null != onmouseout){
			out.print(" onmouseout='"+onmouseout+"'");
		}
		if(null != onmouseover){
			out.print(" onmouseover='"+onmouseover+"'");
		}
		if(null != onmouseup){
			out.print(" onmouseup='"+onmouseup+"'");
		}
		if(null != style){
			out.print(" style='"+style+"'");
		}
		if(null != title){
			out.print(" title='"+title+"'");
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getOnblur() {
		return onblur;
	}
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getOndblclick() {
		return ondblclick;
	}
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	public String getOnhelp() {
		return onhelp;
	}
	public void setOnhelp(String onhelp) {
		this.onhelp = onhelp;
	}
	public String getOnkeydown() {
		return onkeydown;
	}
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}
	public String getOnkeypress() {
		return onkeypress;
	}
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}
	public String getOnkeyup() {
		return onkeyup;
	}
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}
	public String getOnmousedown() {
		return onmousedown;
	}
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}
	public String getOnmousemove() {
		return onmousemove;
	}
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}
	public String getOnmouseout() {
		return onmouseout;
	}
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}
	public String getOnmouseover() {
		return onmouseover;
	}
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}
	public String getOnmouseup() {
		return onmouseup;
	}
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
}
