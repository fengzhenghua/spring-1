/**
 * @ProjectName: store_web
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年8月13日 上午10:43:41
 * @Title: StoreIndexMenuTag.java
 * @Package com.tydic.unicom.crm.web.store.tag
 * @Description: TODO
 */
package com.tydic.unicom.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.po.CodeList;
import com.tydic.unicom.service.cache.po.CodeType;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;
import com.tydic.unicom.tag.Tag;
import com.tydic.unicom.webUtil.SpringBean;

/**
 * <p>
 * </p>
 * @author yangfei 2014年8月13日 上午10:43:41
 * @ClassName StoreIndexMenuTag
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年8月13日
 * @modify by reason:{方法名}:{原因}
 */
public class StoreIndexMenuTag extends Tag {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/** 常量------------------------------------------------------------------ */
	
	/** 类变量---------------------------------------------------------------- */
	
	/** 实例变量-------------------------------------------------------------- */
	
	/** 共有字段-------------------------------------------------------------- */
	
	/** 受保护字段------------------------------------------------------------ */
	@Autowired
	private MemQueryServ memQueryServ;
	
	private String codeType = "index_left_menu";
	
	/** 私有字段-------------------------------------------------------------- */
	
	/** 构造方法-------------------------------------------------------------- */
	
	/** 公共方法-------------------------------------------------------------- */
	
	/** 受保护方法------------------------------------------------------------ */
	
	/** 私有方法-------------------------------------------------------------- */
	
	/** 重载Object方法-------------------------------------------------------- */
	@Override
	public int doStartTag() throws JspException {
		memQueryServ = SpringBean.getBean("MemQueryServ", MemQueryServ.class);
		CodeType code = memQueryServ.queryCodeType("code_type_" + codeType);
		JspWriter out = this.pageContext.getOut();
		try {
			out.print("<div class='allSortIndex' style='z-index: 99;display: none;' ");
			out.print("date-module-edit='false' date-layout-edit='false' ");
			out.print("date-unit-type='i_center980' ");
			out.print("date-block-type='i_1_1_navigationOccupation' date-type='2'> ");
			out.print("<div class='mmodule mpd_no_option' date-type='3' ");
			out.print("date-module-def='i_Category'> ");
			out.print("<div class='category' style='display: block;' id='store_menu_div'> ");
			out.print("<div class='allSortDetail'> ");
			out.print("<ul class='items ie6Png'> ");
			List<CodeList> list = code.getCodeList();
			for (int i = 0; i < list.size(); i++) {
				CodeList codeList = list.get(i);
				if ("0".equals(codeList.getParent_code_id())) {
					out.print("<li class='item rela' style='padding-left: 0px; line-height: 20px;height: 36px;'>");
					out.print("<div class='overr'> ");
					out.print("<h3 class=''> ");
					out.print("<table> ");
					out.print("<tr> ");
					out.print("<td valign='top'><i class='newNumberIcon'></i></td> ");
					out.print("<td valign='top'> ");
					out.print("<a href='viewMore?menuId=" + codeList.getCode_id() + "'  style='color: #000000;'>"
					        + codeList.getCode_name() + "</a> ");
					out.print("</td> ");
					out.print("</tr> ");
					out.print("</table> ");
					out.print("</h3> ");
					out.print("</div> ");
					out.print("<div class='allSortList moreW moreAuto position' style='display: none;'> ");
					int dl_num = 0;
					for (int j = 0; j < list.size(); j++) {
						CodeList codeList2 = list.get(j);
						if (codeList2.getParent_code_id().equals(codeList.getCode_id())) {
							out.print("<dl class='fore " + (dl_num == 0 ? "fore_first" : "") + " '> ");
							out.print("<dt> ");
							out.print("<a href='viewMore?menuId=" + codeList2.getCode_id() + "'  style='color: #000000;font-size:12px;'>"
							        + codeList2.getCode_name() + "</a>  ");
							out.print("</dt> ");
							for (int n = 0; n < list.size(); n++) {
								CodeList codeList3 = list.get(n);
								if (codeList3.getParent_code_id().equals(codeList2.getCode_id())) {
									out.print("<em> ");
									out.print("<a href='viewMore?menuId=" + codeList3.getCode_id()
									        + "'  style='color: #000000;font-size:12px;font-weight: normal;'>" + codeList3.getCode_name() + "</a>   ");
									out.print("</em> ");
								}
							}
							out.print("</dl>");
							dl_num++;
						}
					}
					out.print("</div> ");
					out.print("</li> ");
				}
			}
			out.print("</ul> ");
			out.print("</div> ");
			out.print("</div> ");
			out.print("</div> ");
			out.print("</div> ");
		} catch (Exception e) {
		}
		return super.doStartTag();
	}
	
	/** get/set方法----------------------------------------------------------- */
	public String getCodeType() {
		return codeType;
	}
	
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
}
