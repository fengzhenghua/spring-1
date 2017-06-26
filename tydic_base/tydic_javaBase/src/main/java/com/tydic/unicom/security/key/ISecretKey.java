package com.tydic.unicom.security.key;

import java.util.Date;

import com.tydic.unicom.security.enums.EncryptType;
import com.tydic.unicom.security.enums.SecretKeyType;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月5日 下午4:26:13
 * @ClassName ISecretKey
 * @Description 密钥接口
 * @version V1.0
 */
public interface ISecretKey {
	/**
	 * 获取密钥生成时间
	 * @return
	 */
	Date getGeneratedTime();

	/**
	 * 获取加密密钥
	 * @return
	 */
	byte[] getEncryptKey();

	/**
	 * 加密
	 * @param data 明文
	 * @return
	 * @throws Exception 
	 */
	byte[] decrypt(byte[] data) throws Exception;

	/**
	 * 解密
	 * @param data 密文
	 * @return
	 * @throws Exception 
	 */
	byte[] encrypt(byte[] data,EncryptType encryptType) throws Exception;
	
	/**
	 * 加密
	 * @param data 明文
	 * @return
	 * @throws Exception 
	 */
	byte[] decrypt(byte[] data, EncryptType encryptType) throws Exception;

	/**
	 * 解密
	 * @param data 密文
	 * @return
	 * @throws Exception 
	 */
	byte[] encrypt(byte[] data) throws Exception;

	/**
	 * 获取密钥类型
	 * @return
	 */
	SecretKeyType getKeyType();

	/**
	 * 获取解密密钥
	 * @return
	 */
	byte[] getDecryptKey();
}
