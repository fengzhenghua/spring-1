package com.tydic.unicom.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.po.CodeList;
import com.tydic.unicom.service.cache.po.CodeType;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;
import com.tydic.unicom.webUtil.SpringBean;

/**
 * 
 * <p></p>
 * @author supeng 2014年8月6日 上午10:59:57
 * @ClassName LiTagForCodeAttr
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年8月6日
 * @modify by reason:{方法名}:{原因}
 */
public class LiTagForCodeAttr extends Tag {

	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 1L;
    @Autowired
	private MemQueryServ memQueryServ;
	
	private String checkAttr;
	
	private String checkClass;
	
	private String nameClass;
	
    private String ofrAttrGroupMap;
//    private Map<String,List<String>> ofrAttrGroupMap;
    
    
    @Override
    public int doStartTag() throws JspException {
    	
    	memQueryServ = SpringBean.getBean("MemQueryServ",MemQueryServ.class);
		
    	JspWriter out = this.pageContext.getOut();
    	if(ofrAttrGroupMap==null||ofrAttrGroupMap.equals("")) return super.doStartTag();
    	if(checkAttr==null||checkAttr.equals("")) return super.doStartTag();
    	Map<String,List<String>> ofrAttrGroupMaps = json2Map(ofrAttrGroupMap);
    	try {
    		JSONObject checkAttrJson = JSONObject.fromObject(checkAttr);
			Set<Entry<String, String>> entrySet = checkAttrJson.entrySet(); 
    		  //遍历当前商品的属性
            for (Entry<String, String> entry :entrySet) {
	            //商品的属性组
            	String skuGroup = entry.getKey();
            	//商品的属性
            	String skuAttr = entry.getValue();
            	//属性组翻译
            	CodeType  codes;
            	String liName = (codes=memQueryServ.queryCodeType("code_type_"+skuGroup))==null?"":codes.getType_name();
            	out.print("<dl>");
            	out.print("<dt class="+nameClass+">"+liName+"："+"</dt>");
            	out.print("<dd class=\"selected_mobi_color\">");
            	//获取此skuGroup的list
            	//得到此商品组下的所有属性值
    			List<String> codeListes = ofrAttrGroupMaps.get(skuGroup);
	            for(String attrId:codeListes){
	            	CodeList codeList = memQueryServ.queryCodeList("code_list_"+skuGroup+"_"+attrId);
	            	String info = "sku_group=\""+skuGroup+"\"  attr_id=\""+attrId+"\" ";
	            	if(skuAttr.equals(attrId)){
	            		out.print("<a  href=\"javascript:void(0);\"  ofrAttrGroup=1 "+info);
    					outProtites(out);
    					out.print(" >");
    					out.print(codeList.getCode_name());
    					out.print(checkClass);
    					out.print("</a>");
	            	}else{
    					out.print("<a  href=\"javascript:void(0);\" "+info+" class='"+getClazz()+"'");
    					outProtites(out);
    					out.print(" >");
    					out.print(codeList.getCode_name());
    					out.print("</a>");
	            	}
	            }
	            
	            out.print("</dd>");
	            out.print("<div class=\"clear\"></div>");
	            out.print("</dl>");
            }
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}

		return super.doStartTag();
		
		
    }
	
    private Map<String, List<String>> json2Map(String ofrAttrGroupMap2) {
    	 Map<String, List<String>> map = new HashMap<String, List<String>>();
         //最外层解析
         JSONObject json = JSONObject.fromObject(ofrAttrGroupMap2);
         for(Object k : json.keySet()){
             Object v = json.get(k); 
             List<String> list = (List<String>)v;
            map.put(k.toString(), list);
         }
         return map;
}

	public MemQueryServ getMemQueryServ() {
    	return memQueryServ;
    }
	
    public void setMemQueryServ(MemQueryServ memQueryServ) {
    	this.memQueryServ = memQueryServ;
    }

	
    public String getCheckClass() {
    	return checkClass;
    }
	
    public void setCheckClass(String checkClass) {
    	this.checkClass = checkClass;
    }

	
    
    public String getNameClass() {
    	return nameClass;
    }

	
    public void setNameClass(String nameClass) {
    	this.nameClass = nameClass;
    }

	public String getCheckAttr() {
    	return checkAttr;
    }
    
    public String getOfrAttrGroupMap() {
    	return ofrAttrGroupMap;
    }

	
    public void setOfrAttrGroupMap(String ofrAttrGroupMap) {
    	this.ofrAttrGroupMap = ofrAttrGroupMap;
    }

	public void setCheckAttr(String checkAttr) {
    	this.checkAttr = checkAttr;
    }
	
}
