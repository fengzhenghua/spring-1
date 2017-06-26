package com.tydic.unicom.apc.base.order.po;

import java.util.Date;

import javax.persistence.Id;

import com.tydic.unicom.vdsBase.annotation.filter;
import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoOrderPo extends BasePo{
	private static final long serialVersionUID = 1L;
	@filter
	private String order_id ;
	private String parent_order_id;
	private String cust_id;
	private String express_flag;
	@filter
	private String order_status;
	private String storage_id;
	private Date finish_date;
	@filter
	private String dev_channel_id;
	private String dev_id;
	@filter
	private String create_operator_id;
	private String order_type;
	private Date update_date;
	@filter
	private String tele_type;
	private String start_time;
	private String end_time;
	
	private String order_source;//ORDER_SOURCE//订单来源
	private String order_sub_type;//ORDER_SUB_TYPE//订单子类型
	@filter
	private String pay_flag;
	private String order_flag;
	private String sign_flag;
	private String create_date;
	private String eparchy_code;
	private String related_sn;
	private String ctrl_sn;
	private String mobile_number;
	private String mobile_kind;
	private String recommend_id;
	private String create_uniform_oper;//新增：统一工号
	private String attr_value;//重庆新增业务变更号码
	private String bill_flag;//新增开户发票打印标志
	private String handle_oper_no;//处理工号 海南需求添加
	
	public String getSign_flag() {
		return sign_flag;
	}

	public void setSign_flag(String sign_flag) {
		this.sign_flag = sign_flag;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getEparchy_code() {
		return eparchy_code;
	}

	public void setEparchy_code(String eparchy_code) {
		this.eparchy_code = eparchy_code;
	}

	public String getRelated_sn() {
		return related_sn;
	}

	public void setRelated_sn(String related_sn) {
		this.related_sn = related_sn;
	}

	public String getCtrl_sn() {
		return ctrl_sn;
	}

	public void setCtrl_sn(String ctrl_sn) {
		this.ctrl_sn = ctrl_sn;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getMobile_kind() {
		return mobile_kind;
	}

	public void setMobile_kind(String mobile_kind) {
		this.mobile_kind = mobile_kind;
	}

	public String getRecommend_id() {
		return recommend_id;
	}

	public void setRecommend_id(String recommend_id) {
		this.recommend_id = recommend_id;
	}
    
    public String getBill_flag() {
		return bill_flag;
	}

	public void setBill_flag(String bill_flag) {
		this.bill_flag = bill_flag;
	}

	public String getOrder_source() {
    	return order_source;
    }
	
    public void setOrder_source(String order_source) {
    	this.order_source = order_source;
    }
	
    public String getOrder_sub_type() {
    	return order_sub_type;
    }
	
    public void setOrder_sub_type(String order_sub_type) {
    	this.order_sub_type = order_sub_type;
    }
   
	
    public String getTele_type() {
    	return tele_type;
    }



	
    public void setTele_type(String tele_type) {
    	this.tele_type = tele_type;
    }



	public Date getUpdate_date() {
    	return update_date;
    }


	
    public void setUpdate_date(Date update_date) {
    	this.update_date = update_date;
    }


	public String getOrder_type() {
    	return order_type;
    }

	public void setOrder_type(String order_type) {
    	this.order_type = order_type;
    }
	
	@Id
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getParent_order_id() {
		return parent_order_id;
	}
	public void setParent_order_id(String parent_order_id) {
		this.parent_order_id = parent_order_id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getExpress_flag() {
		return express_flag;
	}
	public void setExpress_flag(String express_flag) {
		this.express_flag = express_flag;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getStorage_id() {
		return storage_id;
	}
	public void setStorage_id(String storage_id) {
		this.storage_id = storage_id;
	}
	public Date getFinish_date() {
		return finish_date;
	}
	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}
	public String getDev_channel_id() {
		return dev_channel_id;
	}
	public void setDev_channel_id(String dev_channel_id) {
		this.dev_channel_id = dev_channel_id;
	}
	public String getDev_id() {
		return dev_id;
	}
	public void setDev_id(String dev_id) {
		this.dev_id = dev_id;
	}
	public String getCreate_operator_id() {
		return create_operator_id;
	}
	public void setCreate_operator_id(String create_operator_id) {
		this.create_operator_id = create_operator_id;
	}

	
    public String getStart_time() {
    	return start_time;
    }

	
    public void setStart_time(String start_time) {
    	this.start_time = start_time;
    }

	
    public String getEnd_time() {
    	return end_time;
    }

	
    public void setEnd_time(String end_time) {
    	this.end_time = end_time;
    }

	
    public String getPay_flag() {
    	return pay_flag;
    }

	
    public void setPay_flag(String pay_flag) {
    	this.pay_flag = pay_flag;
    }

	public String getOrder_flag() {
		return order_flag;
	}

	public void setOrder_flag(String order_flag) {
		this.order_flag = order_flag;
	}

	public String getCreate_uniform_oper() {
		return create_uniform_oper;
	}

	public void setCreate_uniform_oper(String create_uniform_oper) {
		this.create_uniform_oper = create_uniform_oper;
	}

	public String getAttr_value() {
		return attr_value;
	}

	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}

	public String getHandle_oper_no() {
		return handle_oper_no;
	}

	public void setHandle_oper_no(String handle_oper_no) {
		this.handle_oper_no = handle_oper_no;
	}
	
    
}
