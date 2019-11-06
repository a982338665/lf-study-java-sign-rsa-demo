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




//    pubKey=305c300d06092a864886f70d0101010500034b003048024100a105bbcd7074497468a27011d98ee656293920b9d3c3c10d9f1d55e0d84fd981cebc5bb75aa54e02b05448258a001afa63150f7a2dff8491112786a80abc57e70203010001
//            priKey=30820154020100300d06092a864886f70d01010105000482013e3082013a020100024100a105bbcd7074497468a27011d98ee656293920b9d3c3c10d9f1d55e0d84fd981cebc5bb75aa54e02b05448258a001afa63150f7a2dff8491112786a80abc57e7020301000102405372d13d4ac6393b26eee7fd982e4298ec8c3ab59355a3bb1776f086b213cfb86f789d4e32cdf8adfb2da5a38ff19ca5ae78b7a259a234626c42a935382eb561022100d3959791a7ddca9c7de3980a51866f4f21d6680f76fb12fb2d4ebd7ded0a2b6b022100c2d2f9855cb377bb4f726678d2b62d22f48e69f2ebce19553e96bf46bf63807502204c29b218bd6b8a2e90e6676977754406213113de553f05d322b9105f0effb52702210097024ca2105e9359be94dfd49b0ed6219809a319c5a8f47ddc8ba02b4841e9d90220242d9afc92c2eedc85a4a1262377f864f320798568f054fabbd8757b36c24751
//            生成密钥对成功

}
