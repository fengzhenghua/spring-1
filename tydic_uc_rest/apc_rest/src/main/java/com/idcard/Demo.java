package com.idcard;

import org.apache.log4j.Logger;


public class Demo {
    private static Logger logger = Logger.getLogger(Demo.class);
	static 
    {
	    try {
		System.loadLibrary("OCRDLL_THR_x64");
	    } catch (Throwable e) {
		logger.info("这是一个try catch抛出的错误，原因是找不到一个外部库，不影响tomcat启动和其它程序运行，若不想再报此错误，请将WeiXinAuditRest里的实例变量Demo设为null。");
		logger.info(e.getStackTrace());
	    }
			
    } 
	public native static int RECOCRBoot(Object object);// 引擎初始化 返回值1：正常   -1：未绑定设备  100：时间过期    0：初始化引擎失败
	public native static byte[] RECOCROFPATH(int typeid, String path);// 识别入口
	public native static byte[] RECOCROFMEM(int typeid, byte [] pImagebuf, int len);// 识别入口 
    public native static int TerminateOCRHandle();// 释放引擎内存
    
  public Demo(){
	  /*
      int ret = Demo.RECOCRBoot(null);
	if (ret == 100) {
	    logger.error("该版本为试用版本，时间过期，请联系技术员\n");
	    return;
	}
	else if (ret != 1) {
	    logger.error("引擎初始化失败，请联系技术员\n");
	    return;
	}*/
  }
}
