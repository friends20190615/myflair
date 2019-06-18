package com.myflair.common.utils;



import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

public class EncryptUtils {
	private final static Logger logger = Logger.getLogger(EncryptUtils.class);
	private static String DEFAULT_CHARSET = "utf-8";
	private final static String DES = "DES";

	public final static String LX_KEY = "myflairs20180420";


	
	/**
	 * des 加密
	 * @param key
	 * @param content 要加密的内容
	 * @return
	 */
	public static String encrypt(String key, String content) {
		try {

			byte[] keyBytes = key.getBytes(DEFAULT_CHARSET);
			byte[] contentBytes = content.getBytes(DEFAULT_CHARSET);
			byte[] encryptBytes = encrypt(contentBytes, keyBytes);
			String encryptStr = byte2hex(encryptBytes);
			return encryptStr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return null;

		}
	}
	
	/**
	 * des 解密
	 * @param key
	 * @param content 要解密的内容
	 * @return
	 */
	public static String decrypt(String key, String content) {
		try {

			byte[] keyBytes = key.getBytes(DEFAULT_CHARSET);
			byte[] contentBytes = content.getBytes(DEFAULT_CHARSET);
			byte[] decryptBytes = decrypt(hex2byte(contentBytes), keyBytes);
			String encryptStr = "";
			if(decryptBytes != null){
				encryptStr = new String(decryptBytes, DEFAULT_CHARSET);
			}
			return encryptStr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            明文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 密文(字节)
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            密文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 明文(字节)
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		if(src == null){
			return null;
		}
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0){
			logger.error("错误的密文:"+new String(b));
			return null;
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			try{
				b2[n / 2] = (byte) Integer.parseInt(item, 16);
			}catch(Exception e){
				return null;
			}
		}
		return b2;
	}
}
