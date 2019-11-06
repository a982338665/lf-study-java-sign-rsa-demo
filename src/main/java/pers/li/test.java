package pers.li;

import java.util.Map;

public class test {

    public static void main(String[] args) {

        Map<String, String> map = RSASignGenerate.GenerateKeyPair("www.baidu.com");
        String priKey = map.get("priKey");
        String pubKey = map.get("pubKey");

        String info = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509";
        String signed = SignatureData.sign(priKey, info);

        boolean b = VerifySignature.verifySign(pubKey, info, signed);
    }
}
