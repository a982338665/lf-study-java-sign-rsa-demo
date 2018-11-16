package pers.li;

import java.security.DomainCombiner;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import static pers.li.GenerateKeyPair.hexStrToBytes;

/**
 * 编写接收者的功能：使用发送者的公钥来验证发送过来的加密Data，判断签名的合法性
 */
public class VerifySignature {

    public static void run1() {
        try {
            //这是GenerateKeyPair输出的公钥编码
            String pubkeyvalue = "305c300d06092a864886f70d0101010500034b003" +
                    "04802410080b1626365f29a7656cd62b9346a6b5befd728" +
                    "a918cadff9d91da6bbeb4b3f1e54367e83f4de3e9cb0f37db613" +
                    "06c08b2a3e01580a04ad993b0ba35dbaa5aae70203010001";
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(hexStrToBytes(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);


            String info = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=200605093";
            //这是SignatureData输出的数字签名
            byte[] signed = hexStrToBytes("48e2aa6069855183e8671f44f54f92f6a0a2e9c8439eb0a60cd1a764b24d236d511512fa383a3f371105e27a10491baddb84fb2524defe5864e8cc110a3f4b4c");
            java.security.Signature signetcheck = java.security.Signature.getInstance("MD5withRSA");
            signetcheck.initVerify(pubKey);
            if (signetcheck.verify(signed)) {
                System.out.println("info=" + info);
                System.out.println("签名正常");
            } else {
                System.out.println("非签名正常");
            }
            signetcheck.update(info.getBytes());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        run1();
    }
}