package com.tydic.unicom.webUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class getInfoFromText {
	
	private static Logger LOGGER = LoggerFactory.getLogger(getInfoFromText.class);
	
	public static String getNewMessage(String oldStr) {
		String newStr="";
		String Url = getSrcPath();
		//LOGGER.info("Url:"+Url);
		Url += "messageChange.txt";
        try {
            Scanner in = new Scanner(new File(Url),"utf-8");
            while (in.hasNextLine()) {
                String str = in.nextLine();
                newStr = splitt(str,oldStr);
                if(newStr.equals("not found")){
                	
                }else{
                	in.close();
                	return newStr;
                }
            }
            in.close();
        } catch (Exception e) {
        	LOGGER.info("getNewMessage 失败："+e.getMessage());
        }
        return oldStr;
    }
	
	
	public static  String getSrcPath()  
	{  
	    String result=null;  
	    URL urlpath = getInfoFromText.class.getClassLoader().getResource("");   
	    String path=urlpath.toString();  
	    //remove the head "file:",if it exists  
	    if(path.startsWith("file"))  
	    {  
	        path=path.substring(5);  
	    }  
	    path.replace("/", File.separator);  
	    result=path;  
	    return result;  
	}  

	private static String splitt(String str,String oldStr){
		String strr = str.trim();
		String[] abc = strr.split("[\\p{Space}]+");
		String str1 = abc[0];
		String str2 = abc[1];
		if(oldStr.indexOf(str1) != -1){
			return str2;
		}else{
			return "not found";
		}
		
		
	}
	
}