package com.tydic.unicom.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

import com.fasterxml.uuid.Logger;
import com.tydic.unicom.webUtil.Message;
import com.tydic.unicom.webUtil.Message.Type;

public class RsaEncodeUtil {

	private static final String KEY_ALGORITHM = "RSA";
	private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	/** *//** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** *//** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;  

	/**
	 * 生成公钥和私钥 public 公钥(RSAPublicKey) private 私钥(RSAPrivateKey)
	 * 
	 * @throws NoSuchAlgorithmException
	 *
	 */
	public static Map<String, String> generateRSAKeys() throws NoSuchAlgorithmException {
		HashMap<String, String> map = new HashMap<String, String>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		byte[] pk = publicKey.getEncoded();
		byte[] privk = privateKey.getEncoded();
		String strpk = new String(Base64.encodeBase64(pk));
		String strprivk = new String(Base64.encodeBase64(privk));

		map.put("public", strpk);
		map.put("private", strprivk);

		return map;
	}

	/**
	 * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey restorePublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));

		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
		return publicKey;
	}

	/**
	 * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public static PrivateKey restorePrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
		return privateKey;
	}

	/**
	 * 加密，三步走。
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] rsaPublicEncode(PublicKey key, byte[] encodeData) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLen = encodeData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(encodeData, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encodeData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        return decryptedData;
	}
	
	public static byte[] rsaPrivateEncode(PrivateKey key, byte[] encodeData) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLen = encodeData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(encodeData, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encodeData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        return decryptedData;
	}

	/**
	 * 解密，三步走。
	 * 
	 * @param key
	 * @param encodedText
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws IOException 
	 */
	public static byte[] rsaPrivateDecode(PrivateKey key, byte[] encryptedData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;
	}
	
	public static byte[] rsaPublicDecode(PublicKey key, byte[] encryptedData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;
	}
	
	/**
	 * 签名
	 * @param map
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String sha1Sign(Map<String,Object> map, String key) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        
        //指定sha1算法  
        MessageDigest digest = MessageDigest.getInstance("SHA-1");  
        digest.update(result.getBytes("UTF-8"));  
        //获取字节数组  
        byte messageDigest[] = digest.digest();  
        // Create Hex String  
        StringBuffer hexString = new StringBuffer();  
        // 字节数组转换为 十六进制 数  
        for (int i = 0; i < messageDigest.length; i++) {  
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
            if (shaHex.length() < 2) {  
                hexString.append(0);  
            }  
            hexString.append(shaHex);  
        }  
        return hexString.toString().toUpperCase();  
    }
	
	
	/**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    /**
	 * xml转成map
	 * @param xmlString
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
     * @throws DocumentException 
	 * @throws SAXException
	 */
	public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, DocumentException {
		if(!xmlString.startsWith("<xml")){
			xmlString = "<xml>"+xmlString+"</xml>";
		}
		Document document = DocumentHelper.parseText(xmlString);  
		Element root = document.getRootElement();
		
		Map<String, Object> map = new HashMap<>();
		Iterator<Element> rootItor = root.elementIterator();  
		
		while (rootItor.hasNext()) {  
			Element tmpElement = rootItor.next();  
			String name = tmpElement.getName();
			String text = tmpElement.getTextTrim();
			
			map.put(name, text);
		}
		
		return map;
    }
	
	public static InputStream getStringStream(String sInputString) throws UnsupportedEncodingException {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("UTF-8"));
        }
        return tInputStringStream;
    }
	
	/**
	 * map转成xml
	 * @param map
	 * @return
	 */
	public static String getXmlFromMap(Map<String,Object> map){
		if(map == null || map.size() == 0){
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		for(String key : map.keySet()){
			builder.append("<"+key+"><![CDATA["+map.get(key)+"]]>"+"</"+key+">");
		}
		
		return builder.toString();
	}
	
	/** json转换为map*/
	public static Map<String,Object> getMapFromJson(String json){
		System.out.println("传进来的jsonStr" + json );
		Map<String, Object> map = new HashMap<String, Object>(); 		
		JSONObject jsonObject = JSONObject.fromObject(json);
		 Iterator it = jsonObject.keys();  
         //StringBuilder strBuilder = new StringBuilder();  
         while (it.hasNext())  
         {  
             String key = String.valueOf(it.next());  
             Object value = jsonObject.get(key);
             map.put(key, value);
         }  
         System.out.println("Map = " + map);  
	/*	System.out.println(jsonObject);
	       Iterator it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           String value = (String) jsonObject.get(key);  
	           map.put(key, value);  
	       } 
	       return map;*/
	      // System.err.println(map);
		/*try {
			 ObjectMapper mapper = new ObjectMapper();

			 map = mapper.readValue(json, new TypeReference<HashMap<String,Object>>(){});
			 
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}catch(Exception e){  
        	System.out.println(e.getMessage());
            e.printStackTrace();  
        }  */
        return map;
	}
	
	/** map转为json*/
	public static String getJsonFromMap(Map<String,Object> map){
			JSONObject jsonObject = new JSONObject();
			if(map == null || map.size() == 0){
				return "";
			}
		/*	Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Object> entry = it.next();
				jsonObject.put(entry.getKey(), entry.getValue());
			}*/
			jsonObject =JSONObject.fromObject(map);
			return jsonObject.toString();
	}
	/**
	 * 重庆统一将实时返回对象转为json字符串
	 * @param message
	 * @return
	 */
	public static String getJsonFromMessage(Message message){
		JSONObject json =  new JSONObject();
		json.put("type", message.getType());
		json.put("content", message.getContent());
		json.put("args", message.getArgs());
		return json.toString();
	}
	public static void main(String args[]){
		
		Message message  = new Message();
		message.setType(Type.success);
		message.setContent("成功");
		message.addArg("id", 1253553);
		message.addArg("key","snail&ry");
		String msg =  getJsonFromMessage(message);
		System.out.println(msg);
		
		JSONObject json = JSONObject.fromObject(msg);
		String type = json.getString("type");
		JSONObject arg = json.getJSONObject("args");
		String id = arg.getString("id");
		String key = arg.getString("key");
		
		System.out.println("---------------------");
		System.out.println("type = " + type);
		System.out.println("id = " + id);
		System.out.println("key = " + key);
		//String str =  "{\"redirect_url\":\"\",\"goods_detail\":[{\"goods_id\":\"11111111\",\"goods_name\":\"测试商品1\",\"goods_num\":1,\"goods_price\":0.01},{\"goods_id\":\"22222222\",\"goods_name\":\"测试商品2\",\"goods_num\":1,\"goods_price\":0.01}],\"out_order_id\":\"1488850511816\",\"remark\":\"请返回给我原值\",\"req_way\":\"APP\",\"detail_name\":\"这只是一个支付测试\",\"real_fee\":0.01,\"order_type\":\"01\",\"busi_id\":\"10000001\"}";
		//String str =  "{\"redirect_url\":\"\",\"out_order_id\":\"1488850511816\",\"remark\":\"请返回给我原值\",\"req_way\":\"APP\",\"detail_name\":\"这只是一个支付测试\",\"real_fee\":0.01,\"order_type\":\"01\",\"busi_id\":\"10000001\"}";
       /* try {
			str = java.net.URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		
		//String str = "{\"busi_id\":\"10000002\",\"content\":{\"redirect_url\":\"\",\"goods_detail\":\"[{\"goods_id\":\"11111111\",\"goods_name\":\"测试商品1\",\"goods_num\":1,\"goods_price\":0.01}]\",\"out_order_id\":\"1489062932477\",\"remark\":\"请返回给我原值\",\"req_way\":\"PC\",\"detail_name\":\"这只是一个支付测试\",\"real_fee\":0.01,\"order_type\":\"01\"}}";
       // System.out.println("str = " + str);
		//getMapFromJson(str);
      /*Map<String, Object> map = new HashMap<String,Object>();  
      map.put("name", "zitong");  
      map.put("age", "26"); 
      getJsonFromMap(map);*/
		//String str = "<busi_id>10000000</busi_id><content>HC2bZZnWOR9Xa2Npcmouv59mnZhP+ibCydP7TbDdcLtsBvqpgZoF7QGK3ZRoMisCqUtqtclaGQtKczU/UWatLoaSeTwrEkxDmBXRETclKMct7Gysvb6qpeh40JG69TTXmJrNpZhBtBxth5E8rrgQmiyiBMWZceDuoRzRSoalUjsD7So5TmrKKbYwKW5apRdHftOn0TkrkUzoz52Bp1joqTJ7fBjX2UJ61DKJPoVTKkuQDQYlmhm/asVzKvEZfV/yOXR+yBsPA6FuIe4FK5+Xpz3A2ubqw14+5etM92TyXKW7HGrKAVlmw1+es+HcJNz+NJoOVijD+4e5WI7iGIf4OwjHiHzYfhtsc9GJrSdfh0KmC2QOiJ76v19c88jtSRLx2OavsCqBTnp2R0pmpeftA0e2+ipiblkGJ4xtqeHosptK3X9IzF9lqEspU5rrSDnJh9NgtnjSnhJ8sGlBTj9DzTPPESWSdG8m3xN263xtglp0jlArYIFjjTLUbMbhy4tVHOP0nAJz7lyLw64h7byY2vtIA5yFEzzVEdm8DmUePFmiG2+9jOckSm6i3b+5mBAZ2FFtrGgKSWQliJosaJz6+rz2uPu6khgV2+2uqhxkVY9a7c/zJ0E3/Ard1ZCGC+G1VgWrG+MtzD4ovd8r34I0Ia41cqBAri5d2MvHA4X1JgNL+dRLkxYUI4dsZUv1WEh5WbbK0sF7c3XpG7K7HCuH4KcMenzK9f5v8aceJYWNs62GBh/EM71J8HasV7c1RYsWH4EpHV/jAl17nIEuLmlyCPlRGKNfWzM/vNwlBmqPEsizne3SY+mMecEBcFpscjUzuyNbrPHBwbs1sN/Xs9GmnWGP4SeWtMcVmw4zDiVXTxXXBJc8zgH9hKAFGe+6gUYfwP/DdWDmh/FJY8nyxXFmAl1agwOSeLMTn3EvfyXIGbG4YzsiejV9VijIX6B+AUI95cpizLujGgatMRHa/xO84PnAZMBJDB38F8sXrmgtymlVuhpH3J5htFiIConxG4zi</content>";
/*		JSONObject json =new JSONObject();
		json.put("a","123456");
		json.put("b", "23456");
		json.put("c","4565");
		Map<String,Object> map = getMapfromJson(json);
		System.out.println(map.get("a"));
		System.out.println(map.toString());*/
	/*	try {
			Map<String,Object> map = getMapFromXML(str);
			
			
			System.out.println(map.get("busi_id"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	/*	
		String xmldata = "<xml><busi_id>10000001</busi_id><content>fyflUYwvLD9mMLBNpKFX6Gw/0Vkj+uO18MegPR67iPed8nI6V4zbEBsnGowWvWmfP6UMzodtSkcY53Ny9OZMFsS4REgMeX8+0Q1DXUlO4eQVx/0KPvDNr4lHPHUI6YotgL0aVQANXWqrnZoF2wt/J81v8egJVIfhPuhx+WQwV24XeUB5tPkTG8sNpo9GISdwBRqMDwY3uWnY1FCDMgu793zhJ+0c34ykZAB9n0Z0TTWZ+6QdF2hyzkH0mO58MlZjhqwYh1DdZAAZMMqpM5URnKw2uwlBtpEy6MYXMszHcp4YNRyOpGVZMTdHByuR98Tg0JuYm2MMvhmJwhopYh4kux1qHQMlx3nYzCvjVzlBKQTPZs3wjeSP6p7efPGeMpDFmckpAtk7mRVeiFam5bmxxMpLrS8M8k/08t+EC8ZjLKrS2IgyOEvSAYgqKQovpMSiTMNOyKWReRZUFuLlRT6QJBC4uHFKuTjTv0eKWqAh5aMJ05ttXEgupqiZBdCMQfoia2quB7rp/4GBnD3BlFmO9YWmMVUwOqlXRFrvSJk7mr96Sg4QiDJOyatwOZUNzGVeAOeZ3XOOUTZZPN6+lAiAWH6+CelzFm8bNQ8rHtHAoc77M1sZEVeSDJum8Olr1eRR7r1228nl0pKDj8JMScgS1AwNDIZrmf1iDx4wsu5hafsKysoaS4qiWRyfqXaQ6oDmkn3J3KC2LSxYOaZCIEq4x6g7WKALtxOtF665H5Q/iidUEAcxRIMXW6CYIJy/x1e4jXIX2ED1fH82jfaE/VOaSgUYhhNa+mcutJnZhmF1pDxDxNd9bfB0bfNFpmkpRnNsUoACLyqZ16awMv8xyu3G2A==</content></xml>";
		Map<String,Object> map =  new HashMap<String,Object>();
		try {
			map = getMapFromXML(xmldata);
			System.out.println(map);
			System.out.println("busi_id =" + map.get("busi_id"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
}
