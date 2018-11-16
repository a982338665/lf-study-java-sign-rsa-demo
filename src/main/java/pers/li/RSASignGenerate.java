package pers.li;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * create by lishengbo on 2018-04-24 17:48
 * <p>
 * 数字签名生成工具类
 */
public class RSASignGenerate {


    /**
     * RSA -- 数字签名生成公私钥
     * 公钥公布出去，私钥自己留着
     * @Param name-- 针对哪个公司生成的名称
     * @return
     */
    public static Map<String, String> GenerateKeyPair(String name) {
        Map<String, String> map = new HashMap<>();
        try {
            java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
                    .getInstance("RSA");
            SecureRandom secrand = new SecureRandom();
            // 初始化随机产生器
            secrand.setSeed(name.getBytes());
            keygen.initialize(512, secrand);
            KeyPair keys = keygen.genKeyPair();

            PublicKey pubkey = keys.getPublic();
            PrivateKey prikey = keys.getPrivate();

            String pubKey = bytesToHexStr(pubkey.getEncoded());

            String priKey = bytesToHexStr(prikey.getEncoded());

            System.out.println("pubKey=" + pubKey);
            System.out.println("priKey=" + priKey);
            map.put("priKey", priKey);
            map.put("pubKey", pubKey);
            System.out.println("生成密钥对成功");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            System.out.println("生成密钥对失败");
        };
        return map;
    }


    /**
     * Transform the specified byte into a Hex String form.
     */
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }

    /**
     * Transform the specified Hex String into a byte array.
     */
    public static final byte[] hexStrToBytes(String s) {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bytes;

    }

    private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static void main(String[] args) {
        Map<String, String> map = GenerateKeyPair("www.com");
    }






}