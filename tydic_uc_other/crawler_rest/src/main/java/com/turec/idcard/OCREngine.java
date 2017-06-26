package com.turec.idcard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import sun.misc.BASE64Decoder;

import com.idcard.Demo;
import com.idcard.GlobalData;

public class OCREngine {
    private static Logger LOGGER = LoggerFactory.getLogger(OCREngine.class);
	public String pathString;
	public Demo engineDemo;
	public int id;
	public int i = 0;
	public int num1 =0;
	
	public OCREngine(int id,Demo demo, String paString ) {
		
		this.pathString = paString;
		this.engineDemo = demo;
		this.id = id;
	}
	public  byte[] GetImgByte(String imgStr) throws Exception {		
		byte[] buffer = new BASE64Decoder().decodeBuffer(imgStr);
		return buffer;
	}
	
	public Map<String, String> idFront() throws Exception {
    	    String str = RecOcrMain(GlobalData.TIDCARD2);
    	    LOGGER.info("识图身份证正面后返回的字符串："+str);
    	    Map<String, String> idCartMap = new HashMap<String, String>();
    	    if( !StringUtils.isEmpty(str) ){
    		
        	    String ResString[] = str.split("\r\n");
        	    idCartMap.put("name",checkInfo(ResString[GlobalData.NAME]) );
        	    idCartMap.put("sex",checkInfo(ResString[GlobalData.SEX]) );
        	    idCartMap.put("folk",checkInfo(ResString[GlobalData.FOLK]) );
        	    idCartMap.put("bir",checkInfo(ResString[GlobalData.BIRTHDAY]) );
        	    idCartMap.put("address",checkInfo(ResString[GlobalData.ADDRESS]) );
        	    idCartMap.put("cardnum",checkInfo(ResString[GlobalData.NUM]) );
        	    
    		}
    	    return idCartMap;
	}
	
	public Map<String, String> idBack() throws Exception {
	    String str = RecOcrMain(GlobalData.TIDCARDBACK);
	    LOGGER.info("识图身份证背面后返回的字符串："+str);
    	    Map<String, String> idCartMap = new HashMap<String, String>();
    	    if( !StringUtils.isEmpty(str) ){
    		
        	    String ResString[] = str.split("\r\n");
        	    
        	    idCartMap.put("issue",checkInfo(ResString[GlobalData.issue]) );
        	    idCartMap.put("period", checkInfo(ResString[GlobalData.period]) );
        	    
    		}
    	    return idCartMap;
	}
	private String checkInfo( String data ){
	    if(!StringUtils.isEmpty(data)){
		String[] strs = data.split(":");
		if( strs.length>1 ){
		    return strs[1];
		}
	    }
	    return "";
	}
	
	private String RecOcrMain( int card_type ) throws Exception {
	    
	    byte [] data = GetImgByte(pathString); // 获取图像流数组
	    byte [] buf = Demo.RECOCROFMEM( card_type,data, data.length);
		String str = null;
        	try {
        	    /**
        	     * 因ocr在高并发下会有少量线程识图返回null值，当出现这类情况时，
        	     * 线程睡眠100毫秒后再次识图，循环10次代表有10次机会等待资源空闲时获取资源用来识图
        	     */
        	    for (int i = 0; i < 10; i++) {
			if( buf!=null ){
			    str = new String(buf,"GB2312");
			    /*if(i>0){
				System.out.println("此线程终于可以识图："+id+",i="+i);
			    }*/
			    break;
			}
			else{
			    Thread.sleep(100);
//			    System.out.println("此线程无法识图："+id+",等待100毫秒后继续识图,data.length="+data.length);
			    buf = Demo.RECOCROFMEM( card_type,data, data.length);
			}
		    }
        	    
        	} catch (UnsupportedEncodingException e) {
        		
        		e.printStackTrace();
        	}
	    
	    return str;
	}
	

}