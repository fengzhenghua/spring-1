package com.tydic.unicom.uoc.service.common.impl;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class UocTool {

	
	public static BufferedImage toBufferedImage(Image image) {  
	        if (image instanceof BufferedImage) {  
	            return (BufferedImage)image;  
	         }  
	   
	         image = new ImageIcon(image).getImage();  

	         BufferedImage bimage = null;  
	         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
	        try {  
	            int transparency = Transparency.OPAQUE;  
	     
	             GraphicsDevice gs = ge.getDefaultScreenDevice();  
	             GraphicsConfiguration gc = gs.getDefaultConfiguration();  
	             bimage = gc.createCompatibleImage(  
	             image.getWidth(null), image.getHeight(null), transparency);  
	         } catch (HeadlessException e) {  
	         }  
	        
	        if (bimage == null) {  
	            int type = BufferedImage.TYPE_INT_RGB;  
	   
	             bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);  
	         }  
	        
	         Graphics g = bimage.createGraphics();  
	        
	         g.drawImage(image, 0, 0, null);  
	         g.dispose();  
	        
	        return bimage;  
	    }  
	
	
	public static byte[] hexStringToByte(String hex) {
		   int len = (hex.length() / 2);
		   byte[] result = new byte[len];
		   char[] achar = hex.toCharArray();
		   for (int i = 0; i < len; i++) {
		    int pos = i * 2;
		    result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		   }
		   return result;
		  }
	private static int toByte(char c) {
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);
	    return b;
	 }
}
