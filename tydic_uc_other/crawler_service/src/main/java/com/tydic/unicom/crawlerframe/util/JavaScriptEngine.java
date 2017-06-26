package com.tydic.unicom.crawlerframe.util;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaScriptEngine {
	public static final Logger LOG = LoggerFactory.getLogger(JavaScriptEngine.class);
	private static ScriptEngineManager manager = new ScriptEngineManager();
	private static ScriptEngine engine = manager.getEngineByName("JavaScript");

	public JavaScriptEngine() {
	}

	public String eval(String fun) throws ScriptException {
		return eval(fun, null);
	}

	public String eval(String fun, String[] par) {
		synchronized (fun) {
			int i = 0;
			if (par != null) {
				for (String p : par) {
					if(p!=null){
						engine.put("$" + i, p.toString());
					}else{
						engine.put("$" + i, "");
					}
					i++;
				}
			}
			Object o = null;
			try {
				o = engine.eval(fun);
			} catch (Exception e) {
				LOG.info("JAVASCRIPT 运行异常" + e.getMessage());
				LOG.info("#####" + fun + "###");
				o = "";
			}
			return o.toString();
		}
	}

	// public String eval(String fun,List<String> par) throws ScriptException{
	// if(par != null){
	// String[] s = new String[par.size()];
	// for(String s : par){
	//
	// }
	// return eval(fun, s);
	// }
	// else
	// return "";
	// }

	public static void main(String[] args) {
		JavaScriptEngine js;
		try {
			js = new JavaScriptEngine();
			String[] f = { "1", "2", "3" };
			String f1 = js.eval("0+''+1+'';");
			System.out.println(f1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
