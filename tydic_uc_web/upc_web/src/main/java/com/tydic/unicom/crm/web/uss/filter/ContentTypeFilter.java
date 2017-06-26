package com.tydic.unicom.crm.web.uss.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.owasp.esapi.Logger;

import com.tydic.unicom.crm.web.commTools.DesEncrypt;


/**
 * 解决中文乱码问题
 * 
 * @author lichao
 * 
 */
public class ContentTypeFilter implements Filter {

	private String charset = "UTF-8";

	private FilterConfig config;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ContentTypeFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
		String charset = config.getServletContext().getInitParameter("charset");
		if (charset != null && charset.trim().length() != 0) {
			this.charset = charset;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 设置请求响应字符编码
		HttpServletResponse res = (HttpServletResponse)response;
		res.setCharacterEncoding(charset);
		res.setCharacterEncoding(charset);
		res.setContentType("text/plain;charset=utf-8");
		res.setHeader("Access-Control-Allow-Origin", "*");
		
		
//		Map<String,String[]> params=request.getParameterMap();
//		java.lang.reflect.Method method;
//		try {
//			method = params.getClass().getMethod("setLocked",new Class[]{boolean.class});
//			method.invoke(params,new Object[]{new Boolean(false)});
//		} catch (NoSuchMethodException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		HttpServletRequest req = (HttpServletRequest) request;
//		String pwd=params.get("oper_pwd")[0];
//		pwd="2";
//		params.put("answer", new String[]{"aaa"});
//		params.put("oper_pwd", new String[]{"bbb"});
		if (req.getMethod().equalsIgnoreCase("get")) {
			req = new GetHttpServletRequestWrapper(req, charset);
		}
		
		Map<String,String[]> m = new HashMap<String,String[]>(req.getParameterMap());  
//		m.put("oper_pwd", new String[]{"223"});  
//		m.put("answer", new String[]{"aaa"});  
		req = new ParameterRequestWrapper((HttpServletRequest)req, m);  
		
		String encryptParams=req.getParameter("encrypt");
		logger.info("encryptParams:"+encryptParams);
		
		
		if(encryptParams!=null&&encryptParams.trim().length()>0){
			DesEncrypt des = new DesEncrypt();
			String deStr = des.decrypt(encryptParams);
			logger.info("DesEncrypt:"+deStr);
			if(deStr.startsWith("{")){
				JSONObject jsonObject=JSONObject.fromObject(deStr);
				for (Iterator iter = jsonObject.keys(); iter.hasNext();) { //先遍历整个 people 对象
				    try {
						String key = (String)iter.next();
						String value=jsonObject.getString(key);
						m.put(key, new String[]{value});
					} catch (Exception e) {
					}  
				}
			}
			else{
				String params[]=deStr.split("&");
		//		String url="";
				for (String param : params) {
					try {
						String key=param.split("=")[0];
						String value=param.split("=")[1];
						m.put(key, new String[]{value});  
					} catch (Exception e) {
					}
				}
			}
		}
		

		// 传�?给目标servlet或jsp的实际上时包装器对象的引用，而不是原始的HttpServletRequest对象
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		charset = null;
		config = null;
	}

}
