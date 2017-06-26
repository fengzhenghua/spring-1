package com.tydic.unicom.uoc.base.uochis.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoOfrOrderInvoiceHisPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String invoice_sn;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String invoice_type;
	private String invoice_head;
	private String note_sn;
	private String print_fee;
	private String invoice_flag;
	private String need_invoice;
	private String send_flag;
	private String print_num;
	
	
	
	public String getInvoice_flag() {
		return invoice_flag;
	}
	public void setInvoice_flag(String invoice_flag) {
		this.invoice_flag = invoice_flag;
	}
	public String getNeed_invoice() {
		return need_invoice;
	}
	public void setNeed_invoice(String need_invoice) {
		this.need_invoice = need_invoice;
	}
	public String getSend_flag() {
		return send_flag;
	}
	public void setSend_flag(String send_flag) {
		this.send_flag = send_flag;
	}
	public String getPrint_num() {
		return print_num;
	}
	public void setPrint_num(String print_num) {
		this.print_num = print_num;
	}
	public String getInvoice_sn() {
		return invoice_sn;
	}
	public void setInvoice_sn(String invoice_sn) {
		this.invoice_sn = invoice_sn;
	}
	public String getOfr_order_no() {
		return ofr_order_no;
	}
	public void setOfr_order_no(String ofr_order_no) {
		this.ofr_order_no = ofr_order_no;
	}
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getInvoice_head() {
		return invoice_head;
	}
	public void setInvoice_head(String invoice_head) {
		this.invoice_head = invoice_head;
	}
	public String getNote_sn() {
		return note_sn;
	}
	public void setNote_sn(String note_sn) {
		this.note_sn = note_sn;
	}
	public String getPrint_fee() {
		return print_fee;
	}
	public void setPrint_fee(String print_fee) {
		this.print_fee = print_fee;
	}
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	
}
