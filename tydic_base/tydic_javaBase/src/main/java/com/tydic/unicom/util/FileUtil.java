package com.tydic.unicom.util;

import java.io.File;

/**
 * 文件工具
 * */
public class FileUtil {
	/**
	 * 获取文件扩展名
	 * @param file 文件
	 * @return 正常：file的扩展名</br>
	 * 				 null：file为null时；file无后缀名时
	 * */	
	 public static String getFileExtension(File file) {
		 String fileExtension= null;
		 if (file!= null) {
			 String fileName= file.getName();
			 int dot = fileName.lastIndexOf('.'); 
			 if ((dot >-1) && (dot < (fileName.length() - 1))) { 
				 fileExtension= fileName.substring(dot + 1); 
			 } 
		 }
     return fileExtension; 		 
	}
	 
		/**
		 * 获取文件不带扩展名的文件名
		 * @param file 文件
		 * @return 正常：file的不带扩展名的文件名</br>
		 * 				 null：file为null时；file无后缀名时
		 * 				 与文件名相同值：file的文件名无扩展名时
		 * */		 
   public static String getFileNameNoExtension(File file) { 
  	 String fileNameNoExtension= null;
  	 if (file!= null) {
  		 String fileName= file.getName();
       if ((fileName != null) && (fileName.length() > 0)) { 
      	 int dot = fileName.lastIndexOf('.'); 
      	 if ((dot >-1) && (dot < (fileName.length()))) { 
      		 fileNameNoExtension= fileName.substring(0, dot); 
      	 } else {
      		 fileNameNoExtension= fileName;
      	 }
       }   		 
  	 }
     return fileNameNoExtension; 
	} 	 
}
