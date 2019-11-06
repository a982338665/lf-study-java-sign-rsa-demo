package pers.li;

import java.security.DomainCombiner;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import static pers.li.GenerateKeyPair.hexStrToBytes;

/**
 * 编写接收者的功能：使用发送者的公钥来验证发送过来的加密Data，判断签名的合法性
 * signed(签名内容)原值=8db568aca58e874b618247e7737e90b9a55fc81849b4056623937e4d93bea2f7841893133f480590cc8e8abaa7bbe6fdf9b5d91e89428238645fb99ea38f5ee6
 * info（原值）=orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509
 * 签名并生成文件成功
 */
public class VerifySignature {

    public static void run1() {
        try {
            //这是GenerateKeyPair输出的公钥编码
            String pubkeyvalue = "305c300d06092a864886f70d0101010500034b003048024100a105bbcd7074497468a27011d98ee656293920b9d3c3c10d9f1d55e0d84fd981cebc5bb75aa54e02b05448258a001afa63150f7a2dff8491112786a80abc57e70203010001";
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(hexStrToBytes(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);


            String info = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509";
            //这是SignatureData输出的数字签名
            byte[] signed = hexStrToBytes("8db568aca58e874b618247e7737e90b9a55fc81849b4056623937e4d93bea2f7841893133f480590cc8e8abaa7bbe6fdf9b5d91e89428238645fb99ea38f5ee6");
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

    public static boolean verifySign(String pubkeyvalue, String info, String sign) {
        try {
            //这是GenerateKeyPair输出的公钥编码
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(hexStrToBytes(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            //这是SignatureData输出的数字签名
            byte[] signed = hexStrToBytes(sign);
            java.security.Signature signetcheck = java.security.Signature.getInstance("MD5withRSA");
            signetcheck.initVerify(pubKey);
            signetcheck.update(info.getBytes());
            if (signetcheck.verify(signed)) {
                System.out.println("签名正常");
            } else {
                System.out.println("非签名正常");
            }
            return signetcheck.verify(signed);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        run1();
    }
}
