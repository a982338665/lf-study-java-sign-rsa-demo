package pers.li;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 调用方法参见main方法，需要事先指定偏移量 iv和密钥参数，这种方法密钥必须是16位的<br>
 * 公开的密钥和偏移量都是16位的
 */
public class AESUtil {

	public static  String key = "1233333333333333";
	public static  String iv  = "3333333333333333";

	/**
	 * 偏移量获取
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static IvParameterSpec getIV() throws UnsupportedEncodingException {
		return new IvParameterSpec(iv.getBytes());
	}

	/**
	 * 密钥生成
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKeySpec getKey()   {
 		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		return keySpec;
	}

	/**
	 * 解密
	 * @return
	 */
	public static String decrypt(String src) {
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, getKey(), getIV());
			byte[] decodedByte = new BASE64Decoder().decodeBuffer(src);
			byte[] original = cipher.doFinal(decodedByte);
			String result = new String(original);
			return result;
		} catch(IOException e){
			System.out.println(e.toString());
		} catch (IllegalBlockSizeException e) {
			System.out.println(e.toString());
		} catch (BadPaddingException e) {
			System.out.println(e.toString());
		} catch (InvalidKeyException e) {
			System.out.println(e.toString());
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.toString());
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		} catch (NoSuchPaddingException e) {
			System.out.println(e.toString());
		};

		return null;
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @return
	 */
	public static String encrypt(String content) {

		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIV());
			byte[] contentByte = content.getBytes();
			byte[] result = cipher.doFinal(contentByte);
			return new BASE64Encoder().encode(result);
		} catch (IllegalBlockSizeException e) {
			System.out.println(e.toString());
		} catch (BadPaddingException e) {
			System.out.println(e.toString());
		} catch (InvalidKeyException e) {
			System.out.println(e.toString());
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.toString());
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.toString());
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		} catch (NoSuchPaddingException e) {
			System.out.println(e.toString());
		};

		return null;
	}

	

	 
	/**
	 * 调用的测试主方法
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String content = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=200605093&name='啪啪啪'";

//		AESUtil.iv=ISchoolConstants.AES_IV;
//		AESUtil.key=ISchoolConstants.AES_KEY;
		// 加密
		System.out.println("加密前：" + content);
		String resultString = AESUtil.encrypt(content);
		System.out.println("加密后：" + resultString);
		// 解密
		String decryptResult = AESUtil.decrypt(resultString);
		System.out.println("解密后：" + decryptResult);
		String dd="dD5NaGAYDZf1ru2nCoB6SNczjQXSQppTYh6ZrQcfmkJPcYlkZx5Tq3Ai4+gNO1Eb3rD5KwBIummuW+RZEMULOo0BRjSpJviPlMAhVGBR8gg=";
		System.out.println("密文:"+dd+",解密后：" + AESUtil.decrypt(dd));
	}

}