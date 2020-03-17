package com.lp.jianlai.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class RSAUtil {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA"; // ALGORITHM ['ælgərɪð(ə)m] 算法的意思

    public static Map<String, String> createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        // map装载公钥和私钥
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        // 返回map
        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey  密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     * @param privateKey  密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            //每个Cipher初始化方法使用一个模式参数opmod，并用此模式初始化Cipher对象。此外还有其他参数，包括密钥key、包含密钥的证书certificate、算法参数params和随机源random。
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    //rsa切割解码  , ENCRYPT_MODE,加密数据   ,DECRYPT_MODE,解密数据
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;  //最大块
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    //可以调用以下的doFinal（）方法完成加密或解密数据：
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }


    // 简单测试____________
    public static void main(String[] args) throws Exception {
        Map<String, String> keyMap = RSAUtil.createKeys(2048);
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        System.out.println("公钥加密——私钥解密");
        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" + "有关的命令，但看到大批盛装的官员来临，也就\n" + "以为确系举行大典，因而未加询问。进大明门即\n" + "为皇城。文武百官看到端门午门之前气氛平静，\n" + "城楼上下也无朝会的迹象，既无几案，站队点名\n" + "的御史和御前侍卫“大汉将军”也不见踪影，不免\n"
                + "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        String encodedData = RSAUtil.publicEncrypt(str, RSAUtil.getPublicKey(publicKey));  //传入明文和公钥加密,得到密文
        System.out.println("密文：\r\n" + encodedData);

        publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvc8jdtI4y68rvFvRULCY\n" +
                "hprieJINbeOkzUBoQVRl2o2VNE5qWy9lNmS7reCfCwqq/YQpbKH2dHrhiICviNiM\n" +
                "DKRLw1NH+fGDzje3qCHm8tG5EHZQSTyqDe7rI8UDN1W3vk28Snwz97XQ+toVfiA3\n" +
                "4zGNbWYsKmEBjxXR502ZLwf2oCx64zFZLNJeub0UVrZMLOTSnClPHT0cfFvRdzHB\n" +
                "qDGx8KuOUgKBzuPyrUYwF8t5byXdxWwPOaNQu/aoEecZX0wbxvu06LmKxfJ6kaUE\n" +
                "hoe9ztH4XQNcpxF68O3Z7BNsitkDEzV8G40t/uLoE09WHtOD/YEW0zLCOlSb74pw\n" +
                "twIDAQAB";
        privateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9zyN20jjLryu8\n" +
                "W9FQsJiGmuJ4kg1t46TNQGhBVGXajZU0TmpbL2U2ZLut4J8LCqr9hClsofZ0euGI\n" +
                "gK+I2IwMpEvDU0f58YPON7eoIeby0bkQdlBJPKoN7usjxQM3Vbe+TbxKfDP3tdD6\n" +
                "2hV+IDfjMY1tZiwqYQGPFdHnTZkvB/agLHrjMVks0l65vRRWtkws5NKcKU8dPRx8\n" +
                "W9F3McGoMbHwq45SAoHO4/KtRjAXy3lvJd3FbA85o1C79qgR5xlfTBvG+7TouYrF\n" +
                "8nqRpQSGh73O0fhdA1ynEXrw7dnsE2yK2QMTNXwbjS3+4ugTT1Ye04P9gRbTMsI6\n" +
                "VJvvinC3AgMBAAECggEADMCE3m/DNxP+uTnl9yjWJnzzaCxP0KkxokSIfJMiAKvw\n" +
                "cUIvbQ7jGuNrpIPpy4Ec+clOSykaeLrkuhoDMQtzZUoIeQf4Vvd95nXh1d7pODkU\n" +
                "2OFKBZGYzzTVAWM0ExykKpnoY4yypRrG/oN2XDHn71Cd8tKhdvtrcmSB5KtW9kzS\n" +
                "CE3NJrAQ4ABbK5O7+9zaUfsKzhDTjFIu1XX2NYvZQOEP+6z69cA1tg5O1KiPkJT/\n" +
                "QdHemXhc+PvIbp0txhtX9NWJahz7wmpWoRluCAk7xR+bFFLFiCyhT9TV4Plg8nWW\n" +
                "6vtKPy6UPzs7nZW6EKxcR4vIMEOrycdzEoslAqA90QKBgQDiyUpWrN2TsMjcbuRN\n" +
                "y0YVg6f6jWdmt1MNWjpt0MTgtdU5dROYWJxSM3z+RF4q9+1gDBQ7lnPgzDeK5b9U\n" +
                "ArQJRgDkT2kV0QXlyY6Tu7KAk9rhfJyeJRIAp8dZWF8uRPb91xuhhX7NYnniIu4m\n" +
                "WN3XPzBcwRjDK3OIiHRRLXnDiQKBgQDWQnS/43ZGcKC6Dfo7m7pGqO0G+Nv6Wv/L\n" +
                "l8ORJVioKFtfurz136HfAaxTLvlVWjWrru/i/gQTI3p1Zy/r9+Cr97eUSEevABcf\n" +
                "hi8K+RuiGl1Pcl6kS3gaywLdo4f9Ho/hf+xJ3L0rZAsqUl8tYmQvDMCHzrOPSHAp\n" +
                "H+05vFpCPwKBgFyOYFuNg4TyQpfMXjrtujWvnM/iBBBkw22QpIYLDT7UlygzAwNL\n" +
                "LslRoDK5vmD0/JkVdPB4z+QwFH8IkxR7YfTi8Bw92JqBy1Fj+F+M3CYjsdITJGxa\n" +
                "nsVLtE1fhsEAfnlM4EcEj6LNDQdg0CcwX6GodVl1qWCGDEntvuMelzJZAoGBAK4N\n" +
                "yHgFmRR59CeQqUH5LOav3fV2/oSvnfLPM8DafN4GAsyOj2iRphbabhoZI9Vxdf4l\n" +
                "G5zjy32cqaNsEuL3N92bW9eqrAj+4snqIJcibI9QKZMbjsSaxlPFrWtNqHA4fpuq\n" +
                "ZtJN7qKsH+HejpD4x/fsvQ7WHMn+B5dw5y6q0wvTAoGARyJjwklaCLJ09+pWlX+d\n" +
                "5ygIH+psQ1PuZ/avkBViUUQb1HhhBfdzm88Wo5eSlh+1dAsFIZrOQlwQgzyLocPZ\n" +
                "YIsKLcrR0RAepLL9wCEgtuPACr2RGBr6Aanzzaa7fiMuZbnS9lGCus/w0p95iSD/\n" +
                "QbBGDdt5W9nzPaIStoLLSDM=";
        encodedData = "KQnI2dxj8+ClkE2+GIi/GWugs5ZtXIHNeubL1nosCserr/dX+UVf8eI4oygfEEncCs1xqooqHEEZ\n" +
                "yfl2CGKc/ikp3aWzd7c0c2wbrS2uhuFISM5Lhl60OmKRnRYwxOKljNL1EfXc28uMq+iYIQGvaWvZ\n" +
                "b8TL7/sA5X8okVg5lYxATYpwqVKLAASGayoETwTZxG1LRSiN8bcKLt9ijSyntfMlafSmUyYAg1+s\n" +
                "wIykyyxBWXpnYXlbGQheR0BHBfWuuRrb76drv90NlKwRj4qhdq1S52UZW4Md36fFU8Lu07HYYdD7\n" +
                "gFCSCPnYyc43jgipxLXE3YTmaLLC7X5yN8WfHw==";
        String decodedData = RSAUtil.privateDecrypt(encodedData, RSAUtil.getPrivateKey(privateKey)); //传入密文和私钥,得到明文
        System.out.println("解密后文字: \r\n" + decodedData);

    }

}