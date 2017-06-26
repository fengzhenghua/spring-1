package com.tydic.unicom.security.key.impl;

import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tydic.unicom.security.PKCS7Encoder;
import com.tydic.unicom.security.enums.EncryptType;
import com.tydic.unicom.security.enums.SecretKeyType;
import com.tydic.unicom.security.utils.ByteGroup;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月5日 下午4:43:05
 * @ClassName AESKey
 * @Description AES密钥
 * @version V1.0
 */
public class AESKey extends AbstractSecretKey {
	
	private static final long serialVersionUID = -2331109539099369396L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 构造函数，只能在当前包中被实例化
	 * 
	 * @param key 密钥
	 */
	public AESKey(byte[] key) {
		if (key != null) {
			this.key = Arrays.copyOf(key, key.length);
		} else {
			this.key = null;
		}
	}
	
	/**
	 * 构造函数，只能在当前包中被实例化
	 * 
	 * @param key 密钥
	 * @param generatedTime 密钥生成时间
	 */
	public AESKey(byte[] key, Date generatedTime) {
		if (key != null) {
			this.key = Arrays.copyOf(key, key.length);
		} else {
			this.key = null;
		}
		// 设置密钥生成时间
		setGeneratedTime(generatedTime);
	}
	
	private byte[] key;
	
	public byte[] getKey() {
		if (key != null) {
			return key.clone();
		}
		return null;
	}
	
	@Override
	public byte[] getEncryptKey() {
		if (key != null) {
			return key.clone();
		}
		return null;
	}
	
	@Override
	public byte[] decrypt(byte[] data) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(getDecryptKey(), "AES");
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(data);
	}
	
	@Override
	public byte[] encrypt(byte[] data) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(getEncryptKey(), "AES");
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(data);
		
	}
	
	@Override
	public SecretKeyType getKeyType() {
		return SecretKeyType.AES;
	}
	
	@Override
	public byte[] getDecryptKey() {
		if (key != null) {
			return key.clone();
		}
		return null;
	}
	
	@Override
	public byte[] encrypt(byte[] data, EncryptType encryptType) {
		try {
			if (key == null) {
				logger.error("Key为空null");
				return null;
			}
			if (encryptType.equals(EncryptType.AES_CBC_PKCS7Padding)) {
				return encryptWithPKCS7Padding(data);
			}
			SecretKeySpec skeySpec = new SecretKeySpec(this.getEncryptKey(), "AES");
			Cipher cipher = Cipher.getInstance(encryptType.getType());
			if (!encryptType.equals(EncryptType.AES_ECB_PKCS5Padding)) {
				IvParameterSpec iv = new IvParameterSpec(this.getEncryptKey(), 0, 16);
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			}
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("加密异常", e);
		}
		return null;
	}
	
	@Override
	public byte[] decrypt(byte[] data, EncryptType encryptType) {
		try {
			// 判断Key是否正确
			if (key == null) {
				logger.error("Key为空null");
				return null;
			}
			if (encryptType.equals(EncryptType.AES_CBC_PKCS7Padding)) {
				return decryptWithPKCS7Padding(data);
			}
			SecretKeySpec skeySpec = new SecretKeySpec(this.getDecryptKey(), "AES");
			Cipher cipher = Cipher.getInstance(encryptType.getType());
			if (!encryptType.equals(EncryptType.AES_ECB_PKCS5Padding)) {
				IvParameterSpec iv = new IvParameterSpec(this.getDecryptKey(), 0, 16);
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			}
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("解密异常", e);
		}
		return null;
	}
	
	/**
	 * 使用PKCS7填充加密
	 * 
	 * @param data 明文数据
	 * @return
	 */
	private byte[] encryptWithPKCS7Padding(byte[] data) {
		try {
			// 判断Key是否正确
			if (key == null) {
				logger.error("Key为空null");
				return null;
			}
			ByteGroup byteCollector = new ByteGroup();
			byteCollector.addBytes(data);
			byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
			byteCollector.addBytes(padBytes);
			byte[] unencrypted = byteCollector.toBytes();
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(this.getEncryptKey(), "AES");
			IvParameterSpec iv = new IvParameterSpec(this.getEncryptKey(), 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
			// 加密
			return cipher.doFinal(unencrypted);
		} catch (Exception e) {
			logger.error("加密异常", e);
		}
		return null;
	}
	
	/**
	 * 解密使用PKCS7 填充的密文
	 * 
	 * @param data 密文数据
	 * @return
	 */
	private byte[] decryptWithPKCS7Padding(byte[] data) {
		try {
			// 判断Key是否正确
			if (key == null) {
				logger.error("Key为空null");
				return null;
			}
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(this.getDecryptKey(), "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(this.getDecryptKey(), 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			byte[] original = cipher.doFinal(data);
			// 去除补位字符
			return PKCS7Encoder.decode(original);
		} catch (Exception e) {
			logger.error("解密异常", e);
		}
		return null;
	}
}
