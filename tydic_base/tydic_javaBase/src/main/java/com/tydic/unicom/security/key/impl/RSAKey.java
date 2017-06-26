package com.tydic.unicom.security.key.impl;
import java.util.Date;

import com.tydic.unicom.security.enums.EncryptType;
import com.tydic.unicom.security.enums.SecretKeyType;
import com.tydic.unicom.security.utils.RSAUtils;
/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月5日 下午4:43:05
 * @ClassName AESKey
 * @Description RSA密钥
 * @version V1.0
 */
public class RSAKey extends AbstractSecretKey{
	private static final long serialVersionUID = -6348779105331653244L;
	/**
	 * 公钥
	 */
	private byte[] publicKey;
	/**
	 * 私钥
	 */
	private byte[] privateKey;
	/**
	 * 构造函数
	 * 
	 * @param publicKey
	 *            公钥
	 * @param privateKey
	 *            私钥
	 */
	public RSAKey(byte[] publicKey, byte[] privateKey) {
		super();
		if (publicKey != null) {
			this.publicKey = publicKey.clone();
		}
		if (privateKey != null) {
			this.privateKey = privateKey.clone();
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param publicKey
	 *            公钥
	 * @param privateKey
	 *            私钥
	 * @param generatedTime
	 *            密钥生成时间
	 */
	public RSAKey(byte[] publicKey, byte[] privateKey, Date generatedTime) {
		super();
		if (publicKey != null) {
			this.publicKey = publicKey.clone();
		}
		if (privateKey != null) {
			this.privateKey = privateKey.clone();
		}
	}

	public byte[] getPublicKey() {
		if (publicKey != null) {
			return publicKey.clone();
		}
		return null;
	}

	public byte[] getPrivateKey() {
		if (privateKey != null) {
			return privateKey.clone();
		}
		return null;
	}

	@Override
	public byte[] getEncryptKey() {
		if (publicKey != null) {
			return publicKey.clone();
		}
		return publicKey;
	}

	@Override
	public byte[] decrypt(byte[] data) throws Exception {
		return RSAUtils.decryptByPrivateKey(data, this.getPrivateKey());
	}

	@Override
	public byte[] encrypt(byte[] data) throws Exception {
		return RSAUtils.encryptByPublicKey(data, this.getPublicKey());
	}

	@Override
	public SecretKeyType getKeyType() {
		return SecretKeyType.RSA;
	}

	@Override
	public byte[] getDecryptKey() {
		if(privateKey!=null){
			return privateKey.clone();
		}
		return privateKey;
	}

	@Override
	public byte[] encrypt(byte[] data, EncryptType encryptType)
			throws Exception {
		return encrypt(data);
	}

	@Override
	public byte[] decrypt(byte[] data, EncryptType encryptType)
			throws Exception {
		return decrypt(data);
	}
}
