/**
 * @ProjectName: uss_service
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author wanghao
 * @date: 2014年11月17日 上午12:37:29
 * @Title: InfoDeptVo.java
 * @Package com.tydic.unicom.crm.uss.vo.pub
 * @Description: TODO
 */
package com.tydic.unicom.uac.business.common.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.tydic.unicom.webUtil.BaseVo;

/**
 * <p>
 * </p>
 * @author wanghao 2014年11月17日 上午12:37:29
 * @ClassName InfoDeptVo
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: wanghao 2014年11月17日
 * @modify by reason:{方法名}:{原因}
 */
public class InfoDeptVo extends BaseVo {
	
	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("DEPT_NO")
	private String dept_no;//
	@XStreamAlias("PARENT_DEPT_NO")
	private String parent_dept_no;//
	@XStreamAlias("PATH_CODE")
	private String path_code;//
	@XStreamAlias("DEPT_TYPE")
	private String dept_type;//
	@XStreamAlias("DEPT_NAME")
	private String dept_name;//
	@XStreamAlias("LOCAL_NET")
	private String local_net;//
	@XStreamAlias("COMMENTS")
	private String comments;//
	@XStreamAlias("PHONE")
	private String phone;//
	@XStreamAlias("FAX")
	private String fax;//
	@XStreamAlias("ADDRESS")
	private String address;//
	@XStreamAlias("FLAG")
	private String flag;//
	@XStreamAlias("RES_INIT1")
	private String res_init1;//
	@XStreamAlias("RES_INIT2")
	private String res_init2;//
	@XStreamAlias("RES_CHAR1")
	private String res_char1;//
	@XStreamAlias("RES_CHAR2")
	private String res_char2;//
	@XStreamAlias("AREA_ID")
	private String area_id;//
	@XStreamAlias("CHANNEL_TYPE")
	private String channel_type;//
	@XStreamAlias("SUB_CHANNEL_TYPE")
	private String sub_channel_type;//
	@XStreamAlias("DEPT_LEVEL")
	private String dept_level;//
	@XStreamAlias("CHANNEL_RUN_TYPE")
	private String channel_run_type;//
	@XStreamAlias("DEPARTMENT_TYPE")
	private String department_type;//
	@XStreamAlias("TYPE_CHANNEL")
	private String type_channel;//
	@XStreamAlias("CHANNEL_PROPERTIES")
	private String channel_properties;//
	@XStreamAlias("TYPE_SUB_CHANNEL")
	private String type_sub_channel;//
	@XStreamAlias("CHANNEL_REGION_TYPE")
	private String channel_region_type;//
	@XStreamAlias("LEVEL_CHANNEL")
	private String level_channel;//
	@XStreamAlias("CHANNEL_ASSORT")
	private String channel_assort;//
	@XStreamAlias("BELONG_TO_DEPT")
	private String belong_to_dept;//
	@XStreamAlias("CHANNEL_ASSORT_2")
	private String channel_assort_2;//
	@XStreamAlias("TYPE_CHANNEL_3G")
	private String type_channel_3g;//
	@XStreamAlias("APPLE")
	private String apple;//
	@XStreamAlias("BUSINESS_ANALYSIS_FLAG")
	private String business_analysis_flag;//
	@XStreamAlias("COUNT_SCORE_FLAG")
	private String count_score_flag;//
	@XStreamAlias("MANAGER_AREA_CODE")
	private String manager_area_code;//
	@XStreamAlias("PAGE")
	private int page;//
	@XStreamAlias("PAGESIZE")
	private int pagesize;//

	public String getDept_no() {
		return dept_no;
	}
	
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	
	public String getParent_dept_no() {
		return parent_dept_no;
	}
	
	public void setParent_dept_no(String parent_dept_no) {
		this.parent_dept_no = parent_dept_no;
	}
	
	public String getPath_code() {
		return path_code;
	}
	
	public void setPath_code(String path_code) {
		this.path_code = path_code;
	}
	
	public String getDept_type() {
		return dept_type;
	}
	
	public void setDept_type(String dept_type) {
		this.dept_type = dept_type;
	}
	
	public String getDept_name() {
		return dept_name;
	}
	
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
	public String getLocal_net() {
		return local_net;
	}
	
	public void setLocal_net(String local_net) {
		this.local_net = local_net;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
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
	
	public String getArea_id() {
		return area_id;
	}
	
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	
	public String getChannel_type() {
		return channel_type;
	}
	
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	
	public String getSub_channel_type() {
		return sub_channel_type;
	}
	
	public void setSub_channel_type(String sub_channel_type) {
		this.sub_channel_type = sub_channel_type;
	}
	
	public String getDept_level() {
		return dept_level;
	}
	
	public void setDept_level(String dept_level) {
		this.dept_level = dept_level;
	}
	
	public String getChannel_run_type() {
		return channel_run_type;
	}
	
	public void setChannel_run_type(String channel_run_type) {
		this.channel_run_type = channel_run_type;
	}
	
	public String getDepartment_type() {
		return department_type;
	}
	
	public void setDepartment_type(String department_type) {
		this.department_type = department_type;
	}
	
	public String getType_channel() {
		return type_channel;
	}
	
	public void setType_channel(String type_channel) {
		this.type_channel = type_channel;
	}
	
	public String getChannel_properties() {
		return channel_properties;
	}
	
	public void setChannel_properties(String channel_properties) {
		this.channel_properties = channel_properties;
	}
	
	public String getType_sub_channel() {
		return type_sub_channel;
	}
	
	public void setType_sub_channel(String type_sub_channel) {
		this.type_sub_channel = type_sub_channel;
	}
	
	public String getChannel_region_type() {
		return channel_region_type;
	}
	
	public void setChannel_region_type(String channel_region_type) {
		this.channel_region_type = channel_region_type;
	}
	
	public String getLevel_channel() {
		return level_channel;
	}
	
	public void setLevel_channel(String level_channel) {
		this.level_channel = level_channel;
	}
	
	public String getChannel_assort() {
		return channel_assort;
	}
	
	public void setChannel_assort(String channel_assort) {
		this.channel_assort = channel_assort;
	}
	
	public String getBelong_to_dept() {
		return belong_to_dept;
	}
	
	public void setBelong_to_dept(String belong_to_dept) {
		this.belong_to_dept = belong_to_dept;
	}
	
	public String getChannel_assort_2() {
		return channel_assort_2;
	}
	
	public void setChannel_assort_2(String channel_assort_2) {
		this.channel_assort_2 = channel_assort_2;
	}
	
	public String getType_channel_3g() {
		return type_channel_3g;
	}
	
	public void setType_channel_3g(String type_channel_3g) {
		this.type_channel_3g = type_channel_3g;
	}
	
	public String getApple() {
		return apple;
	}
	
	public void setApple(String apple) {
		this.apple = apple;
	}
	
	public String getBusiness_analysis_flag() {
		return business_analysis_flag;
	}
	
	public void setBusiness_analysis_flag(String business_analysis_flag) {
		this.business_analysis_flag = business_analysis_flag;
	}
	
	public String getCount_score_flag() {
		return count_score_flag;
	}
	
	public void setCount_score_flag(String count_score_flag) {
		this.count_score_flag = count_score_flag;
	}
	
	public String getManager_area_code() {
		return manager_area_code;
	}
	
	public void setManager_area_code(String manager_area_code) {
		this.manager_area_code = manager_area_code;
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
}
