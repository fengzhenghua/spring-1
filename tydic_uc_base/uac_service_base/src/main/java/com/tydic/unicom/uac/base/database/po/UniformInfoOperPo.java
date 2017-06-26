package com.tydic.unicom.uac.base.database.po;

import javax.persistence.Id;

import com.tydic.unicom.vdsBase.annotation.filter;
import com.tydic.unicom.vdsBase.po.BasePo;

public class UniformInfoOperPo extends BasePo{

	private static final long serialVersionUID = 1L;
	@filter
	private String uniform_info_oper;// 统一工号
	@filter
	private String uniform_oper_name;// 统一工号姓名
	@filter
	private String oper_id_card;// 身份证
	@filter
	private String oper_device_number;// 手机号码
	private String salary_account;// 工资帐号
	@filter
	private String oper_pwd;// 密码
	private String oper_dept_no;// 操作员部门
	private String oper_local_net;// 操作员本地网
	private String create_date;// 创建时间
	private String eff_date;// 生效时间
	private String exp_date;// 失效时间
	private String flag;// 生效标识 1－失效0－生效
	private String question;// 密码提示问题
	private String answer;// 问题答案
	private String pwd_encode;// 特征编码
	private String memo;// 备注
	private String check_mode;// 登陆验证方式; SMS：短信验证码，空为不验证
	private String err_count;// 密码输入错误次数,当操作员输入的密码错误此次大于等于ulp.password_parameter.
	private String lock_time;// 工号锁定时间，锁定时间参考ulp.password_parameter.lock_time_lim
	private String ignore_login_errcount;// 1 不受 password_parameter.err_count 的登录错误次数的限制
	private String sub_oper_flag;//（1不需要，0需要）字段，来判断是否需要进入选择子工号页面
	private String oper_no;// 子工号
	
	private String uniform_jsessionid;
	
	// private String land_flag;// 登录标示 0未登录 1代表登录
	
	private String jsessionid;
	
	
	private String CID;//手机的平台版本
	private String phone_flag;//手机设备标示 0 代表android  1 代表iPhone
	
	
	private String uniform_oper_role_type;//角色类型
	
	private String ms_flag;		//0或者空为非末梢代理商，1为末梢代理商 
	
	private String chnl_code;
	private String chnl_kind_id;
	private String city_code;
	private String manager_area_code;
	
	private String account_name;
	private String account_number;
	private String account_type;
	private String bind_flag;//1为绑定了手机号码
	private String uniform_oper_agent_type;//海南专用区分实体工号和虚拟工号区别
	private String pay_pwd;//支付密码

	private String pay_rate; //（上海）操作员结酬比例

	
	public String getPay_pwd() {
		return pay_pwd;
	}

	public void setPay_pwd(String pay_pwd) {
		this.pay_pwd = pay_pwd;
	}

	public String getBind_flag() {
		return bind_flag;
	}

	public void setBind_flag(String bind_flag) {
		this.bind_flag = bind_flag;
	}

	@Id
	public String getUniform_jsessionid() {
		return uniform_jsessionid;
	}
	
	public void setUniform_jsessionid(String uniform_jsessionid) {
		this.uniform_jsessionid = uniform_jsessionid;
	}
	
	public String getUniform_info_oper() {
		return uniform_info_oper;
	}
	
	public void setUniform_info_oper(String uniform_info_oper) {
		this.uniform_info_oper = uniform_info_oper;
	}
	
	public String getUniform_oper_name() {
		return uniform_oper_name;
	}
	
	public void setUniform_oper_name(String uniform_oper_name) {
		this.uniform_oper_name = uniform_oper_name;
	}
	
	public String getOper_id_card() {
		return oper_id_card;
	}
	
	public void setOper_id_card(String oper_id_card) {
		this.oper_id_card = oper_id_card;
	}
	
	public String getOper_device_number() {
		return oper_device_number;
	}
	
	public void setOper_device_number(String oper_device_number) {
		this.oper_device_number = oper_device_number;
	}
	
	public String getSalary_account() {
		return salary_account;
	}
	
	public void setSalary_account(String salary_account) {
		this.salary_account = salary_account;
	}
	
	public String getOper_pwd() {
		return oper_pwd;
	}
	
	public void setOper_pwd(String oper_pwd) {
		this.oper_pwd = oper_pwd;
	}
	
	public String getOper_dept_no() {
		return oper_dept_no;
	}
	
	public void setOper_dept_no(String oper_dept_no) {
		this.oper_dept_no = oper_dept_no;
	}
	
	public String getOper_local_net() {
		return oper_local_net;
	}
	
	public void setOper_local_net(String oper_local_net) {
		this.oper_local_net = oper_local_net;
	}
	
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	public String getEff_date() {
		return eff_date;
	}
	
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	
	public String getExp_date() {
		return exp_date;
	}
	
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getPwd_encode() {
		return pwd_encode;
	}
	
	public void setPwd_encode(String pwd_encode) {
		this.pwd_encode = pwd_encode;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getCheck_mode() {
		return check_mode;
	}
	
	public void setCheck_mode(String check_mode) {
		this.check_mode = check_mode;
	}
	
	public String getErr_count() {
		return err_count;
	}
	
	public void setErr_count(String err_count) {
		this.err_count = err_count;
	}
	
	public String getLock_time() {
		return lock_time;
	}
	
	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}
	
	public String getIgnore_login_errcount() {
		return ignore_login_errcount;
	}
	
	public void setIgnore_login_errcount(String ignore_login_errcount) {
		this.ignore_login_errcount = ignore_login_errcount;
	}
	
	public String getOper_no() {
		return oper_no;
	}
	
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	
	public String getJsessionid() {
		return jsessionid;
	}
	
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	
    public String getCID() {
    	return CID;
    }

	
    public void setCID(String cID) {
    	CID = cID;
    }

	
    public String getPhone_flag() {
    	return phone_flag;
    }

	
    public void setPhone_flag(String phone_flag) {
    	this.phone_flag = phone_flag;
    }

	
    public String getUniform_oper_role_type() {
    	return uniform_oper_role_type;
    }

	
    public void setUniform_oper_role_type(String uniform_oper_role_type) {
    	this.uniform_oper_role_type = uniform_oper_role_type;
    }

	public String getSub_oper_flag() {
		return sub_oper_flag;
	}

	public void setSub_oper_flag(String sub_oper_flag) {
		this.sub_oper_flag = sub_oper_flag;
	}

	public String getMs_flag() {
		return ms_flag;
	}

	public void setMs_flag(String ms_flag) {
		this.ms_flag = ms_flag;
	}
	
	

	public String getChnl_code() {
		return chnl_code;
	}

	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}

	public String getChnl_kind_id() {
		return chnl_kind_id;
	}

	public void setChnl_kind_id(String chnl_kind_id) {
		this.chnl_kind_id = chnl_kind_id;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getManager_area_code() {
		return manager_area_code;
	}

	public void setManager_area_code(String manager_area_code) {
		this.manager_area_code = manager_area_code;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getUniform_oper_agent_type() {
		return uniform_oper_agent_type;
	}

	public void setUniform_oper_agent_type(String uniform_oper_agent_type) {
		this.uniform_oper_agent_type = uniform_oper_agent_type;
	}

	public String getPay_rate() {
		return pay_rate;
	}

	public void setPay_rate(String pay_rate) {
		this.pay_rate = pay_rate;
	}
}
