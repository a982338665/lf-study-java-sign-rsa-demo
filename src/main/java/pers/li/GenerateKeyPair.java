package pers.li;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * 生成一对密钥，即私钥和公钥，
 * 对于密钥的保存可以使用对象流的方式进行保存和传送,也可以使用编码的方式保存；
 * 在这里基于方便，我是使用编码方式进行保存的
 */
public class GenerateKeyPair {

    private String priKey;
    private String pubKey;

    public void run() {
        try {
            java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
                    .getInstance("RSA");
            SecureRandom secrand = new SecureRandom();
            // 初始化随机产生器
            secrand.setSeed("21cn".getBytes());
            keygen.initialize(512, secrand);
            KeyPair keys = keygen.genKeyPair();

            PublicKey pubkey = keys.getPublic();
            PrivateKey prikey = keys.getPrivate();

            pubKey = bytesToHexStr(pubkey.getEncoded());

            priKey = bytesToHexStr(prikey.getEncoded());

            System.out.println("pubKey=" + pubKey);
            System.out.println("priKey=" + priKey);


            System.out.println("写入对象 pubkeys ok");
            System.out.println("生成密钥对成功");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            System.out.println("生成密钥对失败");
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
        GenerateKeyPair n = new GenerateKeyPair();
        n.run();
    }
}