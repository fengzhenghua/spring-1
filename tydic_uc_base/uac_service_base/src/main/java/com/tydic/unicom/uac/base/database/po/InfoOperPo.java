package com.tydic.unicom.uac.base.database.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoOperPo extends BasePo{
	private static final long serialVersionUID = 1L;
	
	private String oper_id;
	private String uni_oper_id;
	private String oper_no;// 操作员编码
	private String region_id;// 所属区域ID
	private String dept_no;// 部门编码
	private String role_code;// 拥有角色
	
	private String oper_name;// 操作员名称
	private String login_name;// 系统登录名称
	private String oper_pwd;// 操作员密码
	private String question;// 密码提示问题
	private String answer;// 问题答案
	
	private String gender;// 性别
	
	private String position;// 职务
	private String phone;// 联系电话
	
	private String email;// E-Mail
	
	private String mobile_no;// 移动电话
	private String need_sms;// 是否消息预警
	private String oper_message;// 操作员提示信息
	private String eff_date;// 生效日期
	private String exp_hint_date;// 失效提醒日期
	private String exp_date;// 失效日期
	private String init_flag;// 首次登录标志
	
	private String deal_type;// 收银类型
	private String app_type;// 应用类型
	private String statistical_dept_no;// 统计部门
	private String oper_alias;// 编码别名
	private String login_count;// 允许登录次数
	private String res_init1;// 保留字段1（数值型）
	private String res_init2;// 保留字段2（数值型）
	private String res_char1;// 保留字段1（字符型）
	private String res_char2;// 保留字段2（字符型）
	private String pwd_encode;// 特征编码
	
	private String oper_status;// 操作员状态
	private String oper_kind;// 操作员等级,取值code_list
	private String flag;// 有效标志,0,有效;1失效
	private String acct_status;// 帐务状态
	private String busi_type;// 客服标志,客服标志,0否，1是
	private String derate_flag;// 减免标志,减免标志 ,0允许 1不允许
	private String is_online;// 是否在线,1在0否
	private String login_ip;// 登陆ip
	private String bind_flag;// 初始值为0，缺省值为0，如果工号跟统一工号绑定以后，更改为1
	private String load_flag;// weblogic刷参加载标志，0加载 1不加载
	private String memo;// 备注
	private String jsessionid;
	// 新增字段－渠道编码
	// private String channel_id;
	// 新增字段-pad密码
	private String oper_pwd_cb;

	private String menu_flag;  //代理商部门屏蔽信息显示权限,0屏蔽，1不屏蔽
	private String id_type_flag; //其他证件选择权限,0不可选，1可选
	private String phone_flag; //代理商终端权限控制,0按代理商，1按自有渠道
	
	//add by dengren
	private String oper_agent_type;
	private String area_code;
	private String channel_type;
	private String is_commission_out;
	private String commission_out_type;
	private String pay_mode;
	private String oper_phone;

	private String account_type;
	private String account_name;
	private String account_number;
	private String oper_dept;
	private String a_oper_no;// 对应A工号
	private String oper_gender;
	private String oper_email;
	private String oper_mobile;
	private String eff_flag;
	private String oper_online;
	private String oper_comment;
	private String create_operator_id;
	private String create_date;
	
	private String commission_pay_type;
	
	private String identityNumber = "";//身份证号码
	private String u_code = "";//用户编码
	private String virtual_name = "";//虚拟工号名称海南专用
	private String a_oper_id = "";//海南专用 oper_relation_ab
	/*分页用*/
	 private int page = 0;

	 private int pagesize =10 ;
	 
	 private String dept_name;//部门名称
	 private String role_id;//角色标志ID
	 private String role_name;//角色名称
	 private String parent_dept_type ="";
	 private String sub_dept_type ="";
	 
	public String getParent_dept_type() {
		return parent_dept_type;
	}

	public void setParent_dept_type(String parent_dept_type) {
		this.parent_dept_type = parent_dept_type;
	}

	public String getSub_dept_type() {
		return sub_dept_type;
	}

	public void setSub_dept_type(String sub_dept_type) {
		this.sub_dept_type = sub_dept_type;
	}

	public String getOper_pwd_cb() {
		return oper_pwd_cb;
	}
	
	public String getOper_id() {
		return oper_id;
	}
	
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	
	public void setOper_pwd_cb(String oper_pwd_cb) {
		this.oper_pwd_cb = oper_pwd_cb;
	}
	
	public String getOper_no() {
		return oper_no;
	}
	
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	
	public String getRegion_id() {
		return region_id;
	}
	
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	
	public String getDept_no() {
		return dept_no;
	}
	
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	
	public String getRole_code() {
		return role_code;
	}
	
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	
	public String getOper_name() {
		return oper_name;
	}
	
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	
	public String getLogin_name() {
		return login_name;
	}
	
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	
	public String getOper_pwd() {
		return oper_pwd;
	}
	
	public void setOper_pwd(String oper_pwd) {
		this.oper_pwd = oper_pwd;
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
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile_no() {
		return mobile_no;
	}
	
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	
	public String getNeed_sms() {
		return need_sms;
	}
	
	public void setNeed_sms(String need_sms) {
		this.need_sms = need_sms;
	}
	
	public String getOper_message() {
		return oper_message;
	}
	
	public void setOper_message(String oper_message) {
		this.oper_message = oper_message;
	}
	
	public String getEff_date() {
		return eff_date;
	}
	
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	
	public String getExp_hint_date() {
		return exp_hint_date;
	}
	
	public void setExp_hint_date(String exp_hint_date) {
		this.exp_hint_date = exp_hint_date;
	}
	
	public String getExp_date() {
		return exp_date;
	}
	
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	
	public String getInit_flag() {
		return init_flag;
	}
	
	public void setInit_flag(String init_flag) {
		this.init_flag = init_flag;
	}
	
	public String getDeal_type() {
		return deal_type;
	}
	
	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}
	
	public String getApp_type() {
		return app_type;
	}
	
	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}
	
	public String getStatistical_dept_no() {
		return statistical_dept_no;
	}
	
	public void setStatistical_dept_no(String statistical_dept_no) {
		this.statistical_dept_no = statistical_dept_no;
	}
	
	public String getOper_alias() {
		return oper_alias;
	}
	
	public void setOper_alias(String oper_alias) {
		this.oper_alias = oper_alias;
	}
	
	public String getLogin_count() {
		return login_count;
	}
	
	public void setLogin_count(String login_count) {
		this.login_count = login_count;
	}
	
	public String getRes_init1() {
		return res_init1;
	}
	
	public void setRes_init1(String res_init1) {
		this.res_init1 = res_init1;
	}
	
	public String getRes_init2() {
		return res_init2;
	}
	
	public void setRes_init2(String res_init2) {
		this.res_init2 = res_init2;
	}
	
	public String getRes_char1() {
		return res_char1;
	}
	
	public void setRes_char1(String res_char1) {
		this.res_char1 = res_char1;
	}
	
	public String getRes_char2() {
		return res_char2;
	}
	
	public void setRes_char2(String res_char2) {
		this.res_char2 = res_char2;
	}
	
	public String getPwd_encode() {
		return pwd_encode;
	}
	
	public void setPwd_encode(String pwd_encode) {
		this.pwd_encode = pwd_encode;
	}
	
	public String getOper_status() {
		return oper_status;
	}
	
	public void setOper_status(String oper_status) {
		this.oper_status = oper_status;
	}
	
	public String getOper_kind() {
		return oper_kind;
	}
	
	public void setOper_kind(String oper_kind) {
		this.oper_kind = oper_kind;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getAcct_status() {
		return acct_status;
	}
	
	public void setAcct_status(String acct_status) {
		this.acct_status = acct_status;
	}
	
	public String getBusi_type() {
		return busi_type;
	}
	
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	
	public String getDerate_flag() {
		return derate_flag;
	}
	
	public void setDerate_flag(String derate_flag) {
		this.derate_flag = derate_flag;
	}
	
	public String getIs_online() {
		return is_online;
	}
	
	public void setIs_online(String is_online) {
		this.is_online = is_online;
	}
	
	public String getLogin_ip() {
		return login_ip;
	}
	
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	
	public String getBind_flag() {
		return bind_flag;
	}
	
	public void setBind_flag(String bind_flag) {
		this.bind_flag = bind_flag;
	}
	
	public String getLoad_flag() {
		return load_flag;
	}
	
	public void setLoad_flag(String load_flag) {
		this.load_flag = load_flag;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	public String getJsessionid() {
		return jsessionid;
	}
	
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	public String getOper_agent_type() {
		return oper_agent_type;
	}

	public void setOper_agent_type(String oper_agent_type) {
		this.oper_agent_type = oper_agent_type;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getIs_commission_out() {
		return is_commission_out;
	}

	public void setIs_commission_out(String is_commission_out) {
		this.is_commission_out = is_commission_out;
	}

	public String getCommission_out_type() {
		return commission_out_type;
	}

	public void setCommission_out_type(String commission_out_type) {
		this.commission_out_type = commission_out_type;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
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

	public String getOper_phone() {
		return oper_phone;
	}

	public void setOper_phone(String oper_phone) {
		this.oper_phone = oper_phone;
	}

	public String getA_oper_no() {
		return a_oper_no;
	}

	public void setA_oper_no(String a_oper_no) {
		this.a_oper_no = a_oper_no;
	}
	
	public String getOper_dept() {
		return oper_dept;
	}

	public void setOper_dept(String oper_dept) {
		this.oper_dept = oper_dept;
	}
	
	public String getOper_gender() {
		return oper_gender;
	}

	public void setOper_gender(String oper_gender) {
		this.oper_gender = oper_gender;
	}
	
	public String getOper_email() {
		return oper_email;
	}

	public void setOper_email(String oper_email) {
		this.oper_email = oper_email;
	}
	
	public String getOper_mobile() {
		return oper_mobile;
	}

	public void setOper_mobile(String oper_mobile) {
		this.oper_mobile = oper_mobile;
	}
	
	public String getEff_flag() {
		return eff_flag;
	}

	public void setEff_flag(String eff_flag) {
		this.eff_flag = eff_flag;
	}
	
	public String getOper_online() {
		return oper_online;
	}

	public void setOper_online(String oper_online) {
		this.oper_online = oper_online;
	}
	
	public String getOper_comment() {
		return oper_comment;
	}

	public void setOper_comment(String oper_comment) {
		this.oper_comment = oper_comment;
	}
	
	public String getCreate_operator_id() {
		return create_operator_id;
	}

	public void setCreate_operator_id(String create_operator_id) {
		this.create_operator_id = create_operator_id;
	}
	
	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getCommission_pay_type() {
		return commission_pay_type;
	}

	public void setCommission_pay_type(String commission_pay_type) {
		this.commission_pay_type = commission_pay_type;
	}
	public String getMenu_flag() {
		return menu_flag;
	}

	public void setMenu_flag(String menu_flag) {
		this.menu_flag = menu_flag;
	}

	public String getId_type_flag() {
		return id_type_flag;
	}

	public void setId_type_flag(String id_type_flag) {
		this.id_type_flag = id_type_flag;
	}

	public String getPhone_flag() {
		return phone_flag;
	}

	public void setPhone_flag(String phone_flag) {
		this.phone_flag = phone_flag;
	}

	public String getUni_oper_id() {
		return uni_oper_id;
	}

	public void setUni_oper_id(String uni_oper_id) {
		this.uni_oper_id = uni_oper_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}


	public String getU_code() {
		return u_code;
	}

	public void setU_code(String u_code) {
		this.u_code = u_code;
	}

	public String getVirtual_name() {
		return virtual_name;
	}

	public void setVirtual_name(String virtual_name) {
		this.virtual_name = virtual_name;
	}

	public String getA_oper_id() {
		return a_oper_id;
	}

	public void setA_oper_id(String a_oper_id) {
		this.a_oper_id = a_oper_id;
	}
	
}
