package com.hntxrj.txerp.core.util;


import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;

public class RSAUtil {

    //指定加密算法为RSA
    private static String ALGORITHM = "RSA";
    //指定公钥存放文件和私钥存放文件
    private static String PUBLIC_KEY_FILE = "src/public.key";
    private static String PRIVATE_KEY_FILE = "src/private.key";

    //生成公钥和私钥并分别存放在文件中
    private static void generateKeyPair() throws Exception {
        //生成密钥对
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        //指定key的大小
        int KEYSIZE = 1024;
        kpg.initialize(KEYSIZE, new SecureRandom());
        KeyPair kp = kpg.generateKeyPair();
        //通过密钥对分别得到公钥和私钥
        Key publicKey = kp.getPublic();
        Key privateKey = kp.getPrivate();
        //将生成的密钥写入文件
        ObjectOutputStream output1 =
                new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
        ObjectOutputStream output2 =
                new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
        output1.writeObject(publicKey);
        output2.writeObject(privateKey);
        output1.close();
        output2.close();
    }

    //RSA加密方法
    private static String encrypt(String source, String publicKeyFile) throws Exception {
        //读出文件中的公钥对象
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(publicKeyFile));
        Key key = (Key) ois.readObject();
        ois.close();
        //得到Cipher对象来实现对源数据的RSA加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] b = source.getBytes();
        return Base64.getEncoder().encodeToString(cipher.doFinal(b));
    }

    //RSA解密方法
    private static String decrypt(String cryptograph, String privateKeyFile) throws Exception {
        //读出文件中的私钥对象
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(privateKeyFile));
        Key key = (Key) input.readObject();
        input.close();
        //对已经加密的数据进行RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] b1 = Base64.getDecoder().decode(cryptograph);
        //执行解密操作
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }

    //调用方法举例
//    public static void main(String[] args) {
//        String source = "黑客帝国三部曲";
//        System.out.println("明文字符串：[" + source + "]");
//        try {
//            //生成可用的密钥对并分别保存在文件中
//            generateKeyPair();
//            System.out.println("生成的公钥文件为：" + PUBLIC_KEY_FILE + ", 生成的私钥文件为：" + PRIVATE_KEY_FILE);
//            String cryptograph = encrypt(source, PUBLIC_KEY_FILE);//生成的密文
//            System.out.println("加密之后的字符串为：[" + cryptograph + "]");
//            String text = decrypt(cryptograph, PRIVATE_KEY_FILE);//解密密文
//            System.out.println("解密之后的字符串为：[" + text + "]");
//        } catch (Exception e) {
//            System.out.println("加解密过程中发生错误：" + e.getMessage());
//            return;
//        }
//    }
}