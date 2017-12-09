package cn.we.base.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.we.base.App;
import cn.we.base.BasicContant;

/**
 * Created by cuiruolei on 2016/9/20.
 */

public class EncryptUtils {
    public static String encodeByMD5(String pwd) {
        try {
            pwd += "WE2016";
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            byte[] digest = md.digest();

            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString().substring(8, 24).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encryptMail(String mail) {
        char[] chars = mail.toCharArray();
        int split = mail.lastIndexOf("@");
        for (int i = 0; i < chars.length; i++) {
            if (i > 0 && i < split - 1) {
                chars[i] = '*';
            }
        }
        return new String(chars);
    }

    public static String encryMobile(String mobile) {
        char[] chars = mobile.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i > 2 && i < 7) {
                chars[i] = '*';
            }
        }
        return new String(chars);
    }

    public static String encodeTestMsg(String source) {
//        try {
//            String encryptKey = SPUtils.getString(App.appContext, BasicContant.USERID);
//            return encrypt(source, encryptKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return source;
    }

    public static String decodeTestMsg(String key) {
        try {
            return decrypt(key, SPUtils.getString(App.appContext, BasicContant.USERID));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeTestMsg(String content, String key) {
//        try {
//            return decrypt(content, key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return content;
    }

    public static boolean checkTestMsg(String key, String result) {
        return TextUtils.equals(key, result);
    }

    public static boolean checkMsgReceipt(String key, String result) {
        return TextUtils.equals(key, result);
    }

    public static boolean msgReceive(String key) {
        return key.startsWith("#");
    }

    public static boolean isSendTestFlag(String receipt) {
        return receipt.startsWith("#") && receipt.endsWith("#");
    }

    public static boolean isSendCheckFlag(String receipt) {
        return receipt.startsWith("$");
    }


    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes, "utf-8");
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return TextUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(Base64Utils.decodeFromString(encryptStr), decryptKey);
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return Base64Utils.encodeToString(bytes);
    }
    //加密密钥是自己的 userid
    //test 消息 内容为自己的 userid
    //test 消息回执 采用 aes 加密   将自己的 userid 加密

    //单条消息回执 将 imid 进行 aes 加密


    // /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/ECB/PKCS5Padding";



    // /** 加密字节数据 **/
    public static byte[] encrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            System.out.println(key);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    ///** 创建密钥 **/
    private static SecretKeySpec createKey(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(32);
        sb.append(password);
        while (sb.length() < 32) {
            sb.append("0");
        }
        if (sb.length() > 32) {
            sb.setLength(32);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }

    ///** 加密(结果为16进制字符串) **/
    public static String encrypt(String content, String password) {
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = encrypt(data, password);
        String result = byte2hex(data);
        return result;
    }

    // /** 字节数组转成16进制字符串 **/
    public static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    // /** 解密字节数组 **/
    public static byte[] decrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    ///** 解密16进制的字符串为字符串 **/
    public static String decrypt(String content, String password) {
        byte[] data = null;
        try {
            data = hex2byte(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, password);
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



    // /** 将hex字符串转换成字节数组 **/
    private static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

    public static String encodePerMsgCheck(String source) {
//        try {
//            String encryptKey = SPUtils.getString(App.appContext, BasicContant.USERID);
//            return encrypt(source, encryptKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return source;

    }






        private static String Key="14091";

        public static String encode(String stringToEncode) throws NullPointerException {

            try {
                SecretKeySpec skeySpec = getKey(Key);
                byte[] clearText = stringToEncode.getBytes("UTF-8");
                final byte[] iv = new byte[16];
                Arrays.fill(iv, (byte) 0x00);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
                String encrypedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
                return encrypedValue;

            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return "";
        }

        private static SecretKeySpec getKey(String password) throws UnsupportedEncodingException {
            int keyLength = 256;
            byte[] keyBytes = new byte[keyLength / 8];
            Arrays.fill(keyBytes, (byte) 0x0);
            byte[] passwordBytes = password.getBytes("UTF-8");
            int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
            System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            return key;
        }

}
