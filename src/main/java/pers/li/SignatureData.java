package pers.li;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 编写发送者的功能：首先通过私钥加密待输出数据Data，并输出Data和签名后的Data；
 */
public class SignatureData {

    public void run() {
        try {

            String prikeyvalue = "30820154020100300d06092a864886f70d01010105000482013e3082013a020100024100a105bbcd7074497468a27011d98ee656293920b9d3c3c10d9f1d55e0d84fd981cebc5bb75aa54e02b05448258a001afa63150f7a2dff8491112786a80abc57e7020301000102405372d13d4ac6393b26eee7fd982e4298ec8c3ab59355a3bb1776f086b213cfb86f789d4e32cdf8adfb2da5a38ff19ca5ae78b7a259a234626c42a935382eb561022100d3959791a7ddca9c7de3980a51866f4f21d6680f76fb12fb2d4ebd7ded0a2b6b022100c2d2f9855cb377bb4f726678d2b62d22f48e69f2ebce19553e96bf46bf63807502204c29b218bd6b8a2e90e6676977754406213113de553f05d322b9105f0effb52702210097024ca2105e9359be94dfd49b0ed6219809a319c5a8f47ddc8ba02b4841e9d90220242d9afc92c2eedc85a4a1262377f864f320798568f054fabbd8757b36c24751" ;
            //这是GenerateKeyPair输出的私钥编码
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(hexStrToBytes(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);

            // 要签名的信息
            String myinfo = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509";
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature
                    .getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(myinfo.getBytes("ISO-8859-1"));
            // 对信息的数字签名
            byte[] signed = signet.sign();

            System.out.println("signed(签名内容)原值=" + bytesToHexStr(signed));
            System.out.println("info（原值）=" + myinfo);

            System.out.println("签名并生成文件成功");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            System.out.println("签名并生成文件失败");
        }
        ;

    }
    public static String  sign(String priKey,String info) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(hexStrToBytes(priKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
            signet.initSign(myprikey);
            // 要签名的信息
            signet.update(info.getBytes("ISO-8859-1"));
            // 对信息的数字签名
            byte[] signed = signet.sign();
            System.out.println("signed(签名内容)原值=" + bytesToHexStr(signed));
            System.out.println("info（原值）=" + info);
            System.out.println("签名并生成文件成功");
            return bytesToHexStr(signed);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            System.out.println("签名并生成文件失败");
        };
        return null;
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

    /**
     * @param args
     */
    public static void main(String[] args) {
// TODO Auto-generated method stub
        SignatureData s = new SignatureData();
        s.run();

    }

}
