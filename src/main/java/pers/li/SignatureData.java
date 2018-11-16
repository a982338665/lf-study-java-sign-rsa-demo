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

            String prikeyvalue = "30820154020100300d06092a864886f70d01010105000482013e3" +
                    "082013a02010002410080b1626365f29a7656cd62b9346a6b5befd728a918cadff9d91da6bbeb4b3f" +
                    "1e54367e83f4de3e9cb0f37db61306c08b2a3e01580a04ad993b0ba35dbaa5aae7020301000102403f4" +
                    "779aa8879872c0338907fa2df6514dad500204d998c124d88fff04d1d3dd6539751bca8608dc746723f46" +
                    "30797f7be7e8ebc715f76ae41ee500d56cf9a591022100e505552f22a429e9ed06b29e283a3dc7c90b" +
                    "424fe06c6916642ed30d4eae69b90221008fda6cb8bfe9f88bada94d579562cc64edbb42c709d" +
                    "68afab3c5b4d4faae899f02201ab8ff928b693a56c84872c90f8a9430de9d88b4474c7f" +
                    "0a94cffde25c9eef49022029437f7253629aeffe259550ed4204dd62b0" +
                    "178c9e8ed318ecde666bbd68b983022100c8abe3d55ecef9" +
                    "ff5a0bfa2ec19a533391ca9a366a74c3eeac4a1262e8a844b1" ;
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